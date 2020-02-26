package com.api.common.model.request;

import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.contact.ContactModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 24/03/18.
 */
@Data
public class ContactRequestModel {

    @JsonProperty("contactModel")
    private ContactModel contactModel;

    @JsonProperty("linkedContactMethods")
    private List<ContactMethodModel> linkedContactMethods;

}
