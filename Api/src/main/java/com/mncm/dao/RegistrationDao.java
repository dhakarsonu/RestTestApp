package com.mncm.dao;

import com.api.common.enums.Role;
import com.api.common.entity.contact.Contact;
import com.api.common.model.request.RegistrationRequestModel;
import com.api.common.model.response.ContactResponse;
import com.api.common.model.response.SignupResponse;

/**
 * Created by sonudhakar on 03/09/17.
 */
public interface RegistrationDao {

    public Contact get(String id);
    public Contact getByEmail(String email) throws Exception;
    public String createNewRegistration(RegistrationRequestModel registrationRequestModel) throws Exception;
    public Contact saveRegistration(Contact registration);
    public Contact deleteRegistration(String id);
    public ContactResponse attachContactMethods(Contact contact) throws Exception;
}
