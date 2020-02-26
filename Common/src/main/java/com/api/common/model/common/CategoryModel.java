package com.api.common.model.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.CategoryType;
import com.api.common.model.request.CategoryRequestModel;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.RandomUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 26/01/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625946224949L;

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

    public CategoryModel(CategoryRequestModel categoryRequestModel){
        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getName()))
            this.name = categoryRequestModel.getName();

        if (categoryRequestModel.getType() != null)
            this.type = categoryRequestModel.getType();

        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getPhotoId()))
            this.photoId = categoryRequestModel.getPhotoId();

        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getApplicationId()))
            this.applicationId = categoryRequestModel.getApplicationId();
        else
            this.applicationId = RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC);

        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getPriority()))
            this.priority = categoryRequestModel.getPriority();

        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getParentCategoryId()))
            this.parentCategoryId = categoryRequestModel.getParentCategoryId();

        if (!ObjUtil.isNullOrEmpty(categoryRequestModel.getId()))
            this.id = categoryRequestModel.getId();
    }

}
