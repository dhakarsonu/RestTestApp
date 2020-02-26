package com.api.common.model.request;

import com.api.common.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by sonudhakar on 03/06/18.
 */
@Data
public class AddressRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("homeNumber")
    private String homeNumber;

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("region")
    private String region;

    @JsonProperty("country")
    private String country;

    @JsonProperty("pincode")
    private String pincode;

    @JsonProperty("primary")
    private Boolean primary;

    @JsonProperty("type")
    private AddressType type;
}
