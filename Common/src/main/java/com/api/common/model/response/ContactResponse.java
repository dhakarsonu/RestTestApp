package com.api.common.model.response;

import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.model.request.ContactRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sonudhakar on 01/05/18.
 */
@Data
public class ContactResponse {

    @JsonProperty("contact")
    private Contact contact;

    @JsonProperty("linkedContactMethods")
    private List<ContactMethod> linkedContactMethods;
}
