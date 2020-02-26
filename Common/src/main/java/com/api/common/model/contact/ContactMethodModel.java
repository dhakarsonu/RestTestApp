package com.api.common.model.contact;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.ContactMethodType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 29/07/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactMethodModel extends AbstractBaseEntity implements Serializable{

    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @JsonProperty("type") // type - PHONE | EMAIL | MOBILE
    private ContactMethodType type;

    @JsonProperty("primary")
    private Boolean primary;

}
