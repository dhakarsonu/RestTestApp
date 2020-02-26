package com.mncm.daoimpl;

import com.api.common.entity.common.App;
import com.api.common.entity.common.LinkedProduct;
import com.api.common.model.common.AppModel;
import com.api.common.model.request.AppRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.AppDao;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 27/01/18.
 */
@Slf4j
public class AppDaoImpl extends OfyService implements AppDao {

    @Override
    public App get(String id) {
        return get(App.class, id);
    }

    @Override
    public App getByBrandId(String brandId) {
        if (brandId == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(App.class).filter("brandId", brandId).first().now();
    }

    @Override
    public CollectionResponse<App> getAll(int limit, String cursorString) {

        //TODO it can be cached
        Query<App> query = ofy().load().type(App.class);

        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public App createNewApplication(AppRequestModel appRequestModel) throws Exception {

        AppModel appModel = new AppModel(appRequestModel);

        App application = new App(appModel);

        if (appRequestModel.getBrandId() != null) {
            application.setBrandId(appRequestModel.getBrandId());
        } else {
            application.setBrandId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        if (appRequestModel.getId() != null) {
            application.setId(appRequestModel.getId());
        } else {
            application.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        return saveApplication(application);

    }

    @Override
    public App updateApplication(App application) throws Exception {

        return saveApplication(application);

    }

    @Override
    public App saveApplication(App application) {

        return save(application) != null ? application : null;

    }

    @Override
    public App deleteApplication(String id) {

        App application = get(id);

        if (application == null)
            return null;

        return delete(application) ? application : null;

    }
}
