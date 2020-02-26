package com.mncm.daoimpl;

import com.api.common.entity.address.Address;
import com.api.common.entity.common.Account;
import com.api.common.entity.common.LinkedProduct;
import com.api.common.entity.common.App;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.enums.AccountType;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.address.AddressModel;
import com.api.common.model.common.AccountModel;
import com.api.common.model.common.LinkedProductModel;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.contact.ContactModel;
import com.api.common.model.request.AccountRequestModel;
import com.api.common.model.request.AddressRequestModel;
import com.api.common.model.response.AccountResponseModel;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.AccountDao;
import com.mncm.dao.AddressDao;
import com.mncm.dao.ContactMethodDao;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
public class AccountDaoImpl extends OfyService implements AccountDao{

    private final static String brandId = "1z6Sz7qSqomxQEcXCI68rbipoHru1OkC";
    @Override
    public Account get(String id) {
        return get(Account.class, id);
    }

    @Override
    public Account getByContactId(String contactId) {
        if (contactId == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Account.class).filter("contactId", contactId).first().now();
    }

    @Override
    public Account createAccount(AccountRequestModel accountRequestModel, ContactModel contactModel) throws Exception {

        Set<String> contactMethodIds = new HashSet<>();
        Set<String> addressIds = new HashSet<>();

        AccountModel accountModel = accountRequestModel.getAccountModel();

        AccountType accountType = accountModel.getType();

        String accountId = "";
        if (accountModel.getId() != null) {
            accountId = accountModel.getId();
        } else {
            accountId = RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC);
        }

        try {
            ContactMethodDao contactMethodDao = new ContactMethodDaoImpl();
            List<ContactMethod> contactMethods = contactMethodDao.createBulk(accountRequestModel.getLinkedContactMethods());
            Iterator iterator = contactMethods.iterator();

            while (iterator.hasNext()){
                ContactMethod contactMethod = (ContactMethod) iterator.next();
                contactMethodIds.add(contactMethod.getId());
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        try {
            AddressDao addressDao = new AddressDaoImpl();
            List<Address> addressSet = addressDao.createBulk(accountRequestModel.getLinkedAddresses());
            Iterator iterator = addressSet.iterator();

            while (iterator.hasNext()){
                Address address = (Address) iterator.next();
                addressIds.add(address.getId());
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        Account account = new Account(accountId,accountModel.getName(),accountType,contactMethodIds,addressIds,contactModel.getId());

        if(accountModel.getBrandId() != null)
            account.setBrandId(accountModel.getBrandId());
        else{
            account.setBrandId(brandId);
        }

        return saveAccount(account);

    }

    @Override
    public Account saveAccount(Account account) throws Exception{
        return save(account) != null ? account : null;
    }

    @Override
    public Account deleteAccount(Account account) throws Exception{

        return delete(account) ? account : null;

    }

    @Override
    public List<Address> saveAddress(String accountId, List<AddressRequestModel> addressRequestModelList) throws Exception{

        Preconditions.checkArgument(ObjUtil.isBlank(accountId),"Invalid accountId");
        Preconditions.checkArgument(addressRequestModelList == null,"Invalid request payload");

        Account account = get(accountId);
        if(account == null)
            throw new EntityException(ApiErrorCode.BAD_REQUEST, "account does not exist");

        List<AddressModel> addressModelList = new ArrayList<>();
        Iterator iterator = addressRequestModelList.iterator();

        while (iterator.hasNext()){
            AddressRequestModel addressRequestModel = (AddressRequestModel) iterator.next();
            AddressModel addressModel = new AddressModel(addressRequestModel);
            addressModelList.add(addressModel);
        }

        AddressDao addressDao = new AddressDaoImpl();
        List<Address> addressList = addressDao.createBulk(addressModelList);

        Set<String> linkedAddressIds = new HashSet<>();

        if(account.getLinkedAddresses() != null)
            linkedAddressIds = account.getLinkedAddresses();

        iterator = addressList.iterator();

        while (iterator.hasNext()){
            Address address = (Address) iterator.next();
            linkedAddressIds.add(address.getId());
        }

        account.setLinkedAddresses(linkedAddressIds);

        saveAccount(account);

        return addressList;

    }

    @Override
    public AccountResponseModel attachContactMethods(Account account) throws Exception{

        if(account == null)
            return null;

        List<ContactMethod> linkedContactMethodsObj = new ArrayList<>();
        List<Address> linkedContactAddressObj = new ArrayList<>();

        ContactMethodDao contactMethodDao = new ContactMethodDaoImpl();
        AddressDao addressDao             = new AddressDaoImpl();

        Set<String> linkedContactMethods = account.getLinkedContactMethods();
        Set<String> linkedContactAddress = account.getLinkedAddresses();

        if(linkedContactMethods != null) {

            Iterator iterator = linkedContactMethods.iterator();

            while (iterator.hasNext()) {

                String id = (String) iterator.next();
                ContactMethod contactMethod = contactMethodDao.get(id);
                linkedContactMethodsObj.add(contactMethod);
            }
        }

        if(linkedContactAddress != null) {
            Iterator iterator = linkedContactAddress.iterator();

            while (iterator.hasNext()) {

                String id = (String) iterator.next();
                Address address = addressDao.get(id);
                linkedContactAddressObj.add(address);
            }
        }

        AccountResponseModel accountResponseModel = new AccountResponseModel();

        accountResponseModel.setAccount(account);
        accountResponseModel.setLinkedContactMethods(linkedContactMethodsObj);
        accountResponseModel.setLinkedAddresses(linkedContactAddressObj);

        return accountResponseModel;
    }


}
