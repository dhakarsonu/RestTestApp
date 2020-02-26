package com.api.common.entity.contact;

import com.api.common.entity.AbstractBaseEntity;
import com.googlecode.objectify.annotation.*;
import com.api.common.model.contact.ContactModel;
import com.api.common.entity.contact.ContactMethod;
import com.api.common.enums.EntityStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 13/01/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Contact extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712139888616129334L;
    @Index
    private String accountId;

    @Index
    private String login;

    @Unindex
    private String firstName;

    @Unindex
    private String password;

    @Unindex
    private String lastName;

    @Unindex
    private Set<String> linkedContactMethods;

    @Unindex
    private String photoId;

    @Unindex
    private EntityStatus status;


    public Contact(ContactModel contactModel){
        this.id = contactModel.getId();
        this.login = contactModel.getLogin();
        this.accountId = contactModel.getAccountId();
        this.firstName = contactModel.getFirstName();
        this.password = contactModel.getPassword();
        this.lastName = contactModel.getLastName();
        this.linkedContactMethods = contactModel.getLinkedContactMethods();
        this.photoId = contactModel.getPhotoId();
    }

    @OnSave
    public void saveDefault(){
        if(this.linkedContactMethods == null){
            this.linkedContactMethods = new HashSet<>();
        }

        if(this.photoId == null){
            this.photoId = "";
        }

        if(this.status == null){
            this.status = EntityStatus.ACTIVE;
        }
    }



}
