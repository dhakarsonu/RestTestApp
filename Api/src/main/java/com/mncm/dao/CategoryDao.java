package com.mncm.dao;

import com.api.common.entity.common.Category;
import com.api.common.enums.CategoryType;
import com.api.common.model.request.CategoryRequestModel;
import com.api.common.model.response.CollectionResponse;

/**
 * Created by sonudhakar on 24/01/18.
 */
public interface CategoryDao {

    public Category get(String id);
    public Category getByType(CategoryType type);
    public CollectionResponse<Category> getByApplicationId(String applicationId, int limit, String cursorString);
    public Category createNewCategory(CategoryRequestModel categoryRequestModel) throws Exception;
    public Category updateCategory(Category category) throws Exception;
    public Category saveCategory(Category category);
    public Category deleteCategory(String id);
}
