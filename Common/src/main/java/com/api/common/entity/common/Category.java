package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.CategoryType;
import com.api.common.model.common.CategoryModel;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 26/01/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Category extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712133488515129334L;

    @Unindex
    private String name;

    @Unindex
    private String photoId;

    @Unindex
    private String priority;

    @Index
    private CategoryType type;

    @Unindex
    private String parentCategoryId;

    @Index
    private String applicationId;

    public Category(CategoryModel categoryModel){
        this.id = categoryModel.getId();
        this.name = categoryModel.getName();
        this.photoId = categoryModel.getPhotoId();
        this.priority = categoryModel.getPriority();
        this.applicationId = categoryModel.getApplicationId();
        this.type = categoryModel.getType();
        this.parentCategoryId = categoryModel.getParentCategoryId();
    }

    @OnSave
    public void defaultSave(){

        if(this.priority == null)
            this.priority = "";

        if(this.parentCategoryId == null)
            this.parentCategoryId = "";

        if(this.photoId == null)
            this.photoId = "";
        if(this.type == null)
            this.type = CategoryType.MAIN;
    }
}
