package com.api.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 27/01/18.
 */
@Data
@NoArgsConstructor
public class AppRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("brandId")
    private String brandId;

    @JsonProperty("photoId")
    private String photoId;

}
