package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.AccountType;
import com.api.common.enums.EntityStatus;
import com.api.common.model.common.AccountModel;
import com.api.common.model.common.AppModel;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Account extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712139888615129334L;

    @Unindex
    private String name;

    @Index
    private AccountType type;

    @Index
    private String brandId;

    @Index
    private String createdBy;

    @Unindex
    private Set<String> linkedContactMethods;

    @Unindex
    private Set<String> linkedAddresses;

    @Unindex
    private EntityStatus status;

    public Account(String id,String name,AccountType type,Set<String> linkedContactMethods,Set<String> linkedAddresses,String createdBy){
        this.id = id;
        this.name = name;
        this.linkedContactMethods = linkedContactMethods;
        this.linkedAddresses = linkedAddresses;
        this.type            = type;
        this.createdBy       = createdBy;
    }

    @OnSave
    public void defaultAccount(){
        if(this.linkedContactMethods == null)
            this.linkedContactMethods = new HashSet<>();

        if(this.linkedAddresses == null)
            this.linkedAddresses = new HashSet<>();

        if(this.status == null){
            this.status = EntityStatus.ACTIVE;
        }

    }
}
