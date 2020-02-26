package com.api.common.model.request;

import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.contact.ContactModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Data
public class RegistrationRequestModel {

    @JsonProperty("contact")
    private ContactRequestModel contact;

    @JsonProperty("account")
    private AccountRequestModel account;
}
