package com.mncm.daoimpl;

import com.api.common.entity.common.App;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.ContactMethodDao;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
public class ContactMethodDaoImpl extends OfyService implements ContactMethodDao{

    @Override
    public ContactMethod get(String id) {
        return get(ContactMethod.class, id);
    }

    @Override
    public ContactMethod createNew(ContactMethodModel contactMethodModel) throws Exception{

        if(contactMethodModel == null)
            return null;

        ContactMethod contactMethod = new ContactMethod(contactMethodModel);

        if (contactMethod.getId() != null) {
            contactMethod.setId(contactMethod.getId());
        } else {
            contactMethod.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        return saveContactMethods(contactMethod);

    }

    @Override
    public List<ContactMethod> createBulk(List<ContactMethodModel> contactMethodModels) throws Exception{

        List<ContactMethod> contactMethodList = new ArrayList<>();
        if(contactMethodModels == null)
            return contactMethodList;

        Iterator iterator = contactMethodModels.iterator();

        while (iterator.hasNext()){
            ContactMethodModel contactMethodModel = (ContactMethodModel) iterator.next();
            if (contactMethodModel.getId() == null){
                contactMethodModel.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
            }
            ContactMethod contactMethod = new ContactMethod(contactMethodModel);
            contactMethod = saveContactMethods(contactMethod);
            contactMethodList.add(contactMethod);
        }

        return contactMethodList;
    }

    @Override
    public ContactMethod update(ContactMethod contactMethod) throws Exception {

        return saveContactMethods(contactMethod);

    }

    @Override
    public ContactMethod saveContactMethods(ContactMethod contactMethod) {

        return save(contactMethod) != null ? contactMethod : null;

    }

    @Override
    public ContactMethod deleteContactMethods(String id) {

        ContactMethod contactMethod = get(id);

        if (contactMethod == null)
            return null;

        return delete(contactMethod) ? contactMethod : null;

    }

}
