package com.api.common.entity.contact;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.googlecode.objectify.annotation.*;
import com.api.common.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 09/09/17.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserRole extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712239888615129334L;

    @Index
    private String contactId;

    @Unindex
    private Role role;

    @Unindex
    private EntityStatus status;

    @OnSave
    public void defaultSave(){
        if(this.status == null){
            this.status = EntityStatus.ACTIVE;
        }

        if(this.role == null){
            this.role = Role.MEMBER;
        }
    }
}
