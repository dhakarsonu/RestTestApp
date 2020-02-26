package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.common.AppModel;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 27/01/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class App extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712139888515129334L;

    @Unindex
    private String name;

    @Unindex
    private String photoId;

    @Index
    private String brandId;

    public App(AppModel appModel){
        this.id = appModel.getId();
        this.name = appModel.getName();
        this.photoId = appModel.getPhotoId();
        this.brandId = appModel.getBrandId();
    }

    @OnSave
    public void defaultSave(){
        if(this.photoId == null)
            this.photoId = "";
        if(this.brandId == null)
            this.brandId = RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC);
    }
}
