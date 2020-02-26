package com.api.common.model.request;

import com.api.common.entity.address.Address;
import com.api.common.enums.AccountType;
import com.api.common.model.address.AddressModel;
import com.api.common.model.common.AccountModel;
import com.api.common.model.contact.ContactMethodModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Data
public class AccountRequestModel {

    @JsonProperty("accountModel")
    private AccountModel accountModel;

    @JsonProperty("linkedContactMethods")
    private List<ContactMethodModel> linkedContactMethods;

    @JsonProperty("linkedAddresses")
    private List<AddressModel> linkedAddresses;
}
