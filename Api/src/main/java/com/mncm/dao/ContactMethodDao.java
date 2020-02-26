package com.mncm.dao;

import com.api.common.entity.contact.ContactMethod;
import com.api.common.model.contact.ContactMethodModel;

import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
public interface ContactMethodDao {
    public ContactMethod get(String id);
    public ContactMethod createNew(ContactMethodModel contactMethodModel) throws Exception;
    public ContactMethod update(ContactMethod contactMethod) throws Exception;
    public ContactMethod saveContactMethods(ContactMethod contactMethod);
    public ContactMethod deleteContactMethods(String id);
    public List<ContactMethod> createBulk(List<ContactMethodModel> contactMethodModels) throws Exception;
}
