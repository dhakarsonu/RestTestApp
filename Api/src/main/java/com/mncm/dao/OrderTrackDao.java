package com.mncm.dao;

import com.api.common.entity.OM.Track;
import com.api.common.model.OM.TrackModel;

/**
 * Created by sonudhakar on 28/10/17.
 */
public interface OrderTrackDao {

    public Track get(String id) throws Exception;

    public Track getByOrder(String orderId) throws Exception;

    public Track createTrackDetail(TrackModel trackModel) throws Exception;

    public Track saveTrackDetail(Track track) throws Exception;

    public Track deleteTrackDetail(Track track) throws Exception;
}
