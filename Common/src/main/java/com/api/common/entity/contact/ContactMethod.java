package com.api.common.entity.contact;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.ContactMethodType;
import com.googlecode.objectify.annotation.*;
import com.api.common.model.contact.ContactMethodModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 29/07/17.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class ContactMethod extends AbstractBaseEntity{

    private static final long serialVersionUID = 1702139888615129334L;

    @Unindex
    private String key;

    @Unindex
    private String value;

    @Unindex
    private ContactMethodType type;

    @Unindex
    private Boolean primary;


    public ContactMethod(ContactMethodModel contactMethodModel){

        this.key = contactMethodModel.getKey();
        this.value = contactMethodModel.getValue();
        this.type = contactMethodModel.getType();
        this.primary = contactMethodModel.getPrimary();
        this.id = contactMethodModel.getId();

    }

    @OnSave
    public void saveDefault(){
        if(this.primary == null)
            this.primary = false;

        if(this.key == null)
            this.key = "";
    }
}
