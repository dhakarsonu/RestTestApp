package com.api.common.model.response;

import com.api.common.entity.address.Address;
import com.api.common.entity.common.Account;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.model.common.AccountModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sonudhakar on 01/05/18.
 */
@Data
public class AccountResponseModel {

    @JsonProperty("account")
    private Account account;

    @JsonProperty("linkedContactMethods")
    private List<ContactMethod> linkedContactMethods;

    @JsonProperty("linkedAddresses")
    private List<Address> linkedAddresses;

}
