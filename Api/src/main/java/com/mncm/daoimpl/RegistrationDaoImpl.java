package com.mncm.daoimpl;

import com.api.common.entity.address.Address;
import com.api.common.entity.common.Account;
import com.api.common.entity.common.Token;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.enums.EntityStatus;
import com.api.common.model.common.AccountModel;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.contact.ContactModel;
import com.api.common.model.request.AccountRequestModel;
import com.api.common.model.request.ContactRequestModel;
import com.api.common.model.response.AccountResponseModel;
import com.api.common.model.response.ContactResponse;
import com.api.common.model.response.SignupResponse;
import com.api.common.services.Authentication;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.*;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.UserRole;
import com.api.common.enums.Role;
import com.api.common.model.request.RegistrationRequestModel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Slf4j
public class RegistrationDaoImpl  extends OfyService implements RegistrationDao {

    @Override
    public Contact get(String id){
        return get(Contact.class,id);
    }

    @Override
    public Contact getByEmail(String login) throws Exception{
        if (login == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Contact.class).filter("login", login).first().now();
    }

    @Override
    public String createNewRegistration(RegistrationRequestModel registrationRequestModel) throws Exception{

       // SignupResponse signupResponse = new SignupResponse();
        ContactRequestModel contactRequestModel = registrationRequestModel.getContact();
        AccountRequestModel accountRequestModel = registrationRequestModel.getAccount();


        ContactModel contactModel = contactRequestModel.getContactModel();
        ContactMethodDao contactMethodDao = new ContactMethodDaoImpl();
        try{
            List<ContactMethod> contactMethods = contactMethodDao.createBulk(contactRequestModel.getLinkedContactMethods());
            Set<String> contactMethodIds = new HashSet<>();
            Iterator iterator = contactMethods.iterator();
            while(iterator.hasNext()){
                ContactMethod contactMethod = (ContactMethod) iterator.next();
                if(contactMethod == null)
                    continue;
                contactMethodIds.add(contactMethod.getId());
            }
            contactModel.setLinkedContactMethods(contactMethodIds);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        if(contactModel.getId() == null){
            contactModel.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        Contact contact = new Contact(contactModel);


        AccountDao accountDao = new AccountDaoImpl();
        Account account = accountDao.createAccount(accountRequestModel,contactModel);

        contact.setAccountId(account.getId());
        contact.setStatus(EntityStatus.ACTIVE);

        try{
            UserRole userRole = new UserRole();
            userRole.setRole(Role.OWNER);
            userRole.setStatus(EntityStatus.ACTIVE);
            userRole.setContactId(contact.getId());

            UserRoleDao userRoleDao = new UserRoleDaoImpl();
            userRoleDao.createNewRole(userRole);

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        contact = saveRegistration(contact);

        Map<String,String> request = new HashMap<>();
        request.put("user_id",contact.getId());
        request.put("scopes","mncm.full.access,lixiram.full.access");
        String jwt = Authentication.createJWT(request);
        TokenDaoImpl tokenDao = new TokenDaoImpl();
        Token token = tokenDao.getByEmail(contact.getLogin());
        if(token != null){
            token.setAccessToken(jwt);
            token = tokenDao.saveToken(token);
        }else {
            token = tokenDao.createToken(jwt);
        }
        return token.getId();

    }

    @Override
    public  Contact saveRegistration(Contact registration){

        return save(registration) != null ? registration : null;

    }

    @Override
    public  Contact deleteRegistration(String id){

        Contact registration = get(id);

        if(registration == null)
            return null;

        return delete(registration) ? registration : null;

    }

    @Override
    public ContactResponse attachContactMethods(Contact contact) throws Exception{

        if(contact == null)
            return null;

        List<ContactMethod> linkedContactMethodsObj = new ArrayList<>();

        ContactMethodDao contactMethodDao = new ContactMethodDaoImpl();

        Set<String> linkedContactMethods = contact.getLinkedContactMethods();

        if(linkedContactMethods != null) {
            Iterator iterator = linkedContactMethods.iterator();

            while (iterator.hasNext()) {

                String id = (String) iterator.next();
                ContactMethod contactMethod = contactMethodDao.get(id);
                linkedContactMethodsObj.add(contactMethod);
            }
        }

        ContactResponse contactResponse = new ContactResponse();

        contactResponse.setContact(contact);
        contactResponse.setLinkedContactMethods(linkedContactMethodsObj);

        return contactResponse;
    }

}
