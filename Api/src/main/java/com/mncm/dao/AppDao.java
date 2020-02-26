package com.mncm.dao;

import com.api.common.entity.common.App;
import com.api.common.model.request.AppRequestModel;
import com.api.common.model.response.CollectionResponse;

/**
 * Created by sonudhakar on 27/01/18.
 */
public interface AppDao {

    public App get(String id);
    public App getByBrandId(String brandId);
    public App createNewApplication(AppRequestModel appRequestModel) throws Exception;
    public App updateApplication(App application) throws Exception;
    public App saveApplication(App application);
    public App deleteApplication(String id);
    public CollectionResponse<App> getAll(int limit, String cursorString);
}
