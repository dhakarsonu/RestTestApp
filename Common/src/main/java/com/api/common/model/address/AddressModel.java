package com.api.common.model.address;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.AddressType;
import com.api.common.model.request.AddressRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 29/07/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AddressModel extends AbstractBaseEntity implements Serializable{

    private static final long serialVersionUID = 8103501625951284949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("homeNumber")
    private String homeNumber;

    @JsonProperty("street") //degree course | diploma course | other course
    private String street;

    @JsonProperty("city") // streamId
    private String city;

    @JsonProperty("region") // 3 Years | 6 Month | other
    private String region;

    @JsonProperty("country")
    private String country;

    @JsonProperty("pincode") // linkedSubjects
    private String pincode;

    @JsonProperty("primary")
    private Boolean primary;

    @JsonProperty("type")
    private AddressType type;

    public AddressModel(AddressRequestModel requestModel){
        this.id = requestModel.getId();
        this.homeNumber = requestModel.getHomeNumber();
        this.street = requestModel.getStreet();
        this.city = requestModel.getCity();
        this.region = requestModel.getRegion();
        this.country = requestModel.getCountry();
        this.pincode = requestModel.getPincode();
        this.primary = requestModel.getPrimary();
        this.type = requestModel.getType();
    }
    public AddressModel(){

    }

}
