package com.api.common.model.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.request.AppRequestModel;
import com.api.common.utils.ObjUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 27/01/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 9083509625946224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoId")
    private String photoId;

    @JsonProperty("brandId")
    private String brandId;

    public AppModel(AppRequestModel requestModel){
        if(!ObjUtil.isNullOrEmpty(requestModel.getId()))
            this.id = requestModel.getId();

        if(!ObjUtil.isNullOrEmpty(requestModel.getName()))
            this.name = requestModel.getName();

        if(!ObjUtil.isNullOrEmpty(requestModel.getBrandId()))
            this.brandId = requestModel.getBrandId();

        if(!ObjUtil.isNullOrEmpty(requestModel.getPhotoId()))
            this.photoId = requestModel.getPhotoId();
    }
}
