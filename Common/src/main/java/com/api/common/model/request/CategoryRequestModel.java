package com.api.common.model.request;

import com.api.common.enums.CategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 24/01/18.
 */
@Data
@NoArgsConstructor
public class CategoryRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoId")
    private String photoId;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("type")
    private CategoryType type;

    @JsonProperty("parentCategoryId")
    private String parentCategoryId;

    @JsonProperty("applicationId")
    private String applicationId;
}
