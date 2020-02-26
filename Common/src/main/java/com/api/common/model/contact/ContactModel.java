package com.api.common.model.contact;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.entity.contact.ContactMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 13/01/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactModel extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("linkedContactMethods")
    private Set<String> linkedContactMethods;

    @JsonProperty("photoId")
    private String photoId;
}
