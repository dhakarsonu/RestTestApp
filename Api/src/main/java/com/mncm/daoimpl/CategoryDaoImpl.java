package com.mncm.daoimpl;

import com.api.common.entity.common.Category;
import com.api.common.enums.CategoryType;
import com.api.common.model.common.CategoryModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.CategoryDao;
import com.api.common.model.request.CategoryRequestModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 24/01/18.
 */
@Slf4j
public class CategoryDaoImpl   extends OfyService implements CategoryDao {

    @Override
    public Category get(String id) {
        return get(Category.class, id);
    }

    @Override
    public Category getByType(CategoryType type) {
        if (type == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Category.class).filter("type", type).first().now();
    }

    @Override
    public CollectionResponse<Category> getByApplicationId(String applicationId, int limit, String cursorString) {
        Preconditions.checkArgument(ObjUtil.isBlank(applicationId), "invalid applicationId");

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Category> query = ofy().load().type(Category.class);

        if (!ObjUtil.isBlank(applicationId))
            query = query.filter("applicationId", applicationId);


        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public Category createNewCategory(CategoryRequestModel categoryRequestModel) throws Exception {

        CategoryModel categoryModel = new CategoryModel(categoryRequestModel);

        Category category = new Category(categoryModel);

        if (categoryRequestModel.getId() != null) {
            category.setId(categoryRequestModel.getId());
        } else {
            category.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        return saveCategory(category);

    }

    @Override
    public Category updateCategory(Category category) throws Exception {

        return saveCategory(category);

    }

    @Override
    public Category saveCategory(Category category) {

        return save(category) != null ? category : null;

    }

    @Override
    public Category deleteCategory(String id) {

        Category category = get(id);

        if (category == null)
            return null;

        return delete(category) ? category : null;

    }
}
