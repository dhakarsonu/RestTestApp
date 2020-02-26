package com.api.common.model.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.entity.address.Address;
import com.api.common.entity.common.Account;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.enums.AccountType;
import com.api.common.enums.EntityStatus;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.request.AccountRequestModel;
import com.api.common.model.request.AppRequestModel;
import com.api.common.utils.ObjUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 9083509625046246949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private AccountType type;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("linkedContactMethods")
    private Set<String> linkedContactMethods;

    @JsonProperty("linkedAddresses")
    private Set<String> linkedAddresses;

    @JsonProperty("brandId")
    private String brandId;

}
