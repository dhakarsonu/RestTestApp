package com.api.common.entity.address;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.AddressType;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.OnSave;
import com.googlecode.objectify.annotation.Unindex;
import com.api.common.model.address.AddressModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 29/07/17.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712139888615129354L;

    @Unindex
    private String homeNumber;

    @Unindex
    private String street;

    @Unindex
    private String city;

    @Unindex
    private String region;

    @Unindex
    private String country;

    @Unindex
    private String pincode;

    @Unindex
    private AddressType type;

    @Unindex
    private Boolean primary;


    public Address(AddressModel addressModel){
        this.homeNumber = addressModel.getHomeNumber();
        this.street = addressModel.getStreet();
        this.city = addressModel.getCity();
        this.region = addressModel.getRegion();
        this.country = addressModel.getCountry();
        this.pincode = addressModel.getPincode();
        this.primary = addressModel.getPrimary();
        this.type = addressModel.getType();
        this.id   =addressModel.getId();

    }

    @OnSave
    public void saveDefault(){
        if(this.homeNumber == null)
            this.homeNumber = "";

        if(this.type == null)
            this.type = AddressType.HOME;

        if(this.primary == null)
            this.primary = false;
    }
}
