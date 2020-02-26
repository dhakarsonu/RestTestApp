package com.mncm.daoimpl;

import com.api.common.services.objectify.OfyService;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.api.common.entity.OM.Track;
import com.api.common.model.OM.TrackModel;
import com.mncm.dao.OrderTrackDao;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 28/10/17.
 */
@Slf4j
public class OrderTrackDaoImpl extends OfyService implements OrderTrackDao {

    @Override
    public Track get(String id) throws Exception{
        return get(Track.class,id);
    }

    @Override
    public Track getByOrder(String orderId) throws Exception{
        if (orderId == null)
            return null;

        return ofy().load().type(Track.class).filter("orderId", orderId).first().now();
    }

    @Override
    public Track createTrackDetail(TrackModel trackModel) throws Exception{

        Preconditions.checkArgument(trackModel == null,"tracking payload is not valid");

        Track track = new Track(trackModel);

        if(trackModel.getId() != null)
            track.setId(trackModel.getId());
        else
            track.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));

        return saveTrackDetail(track);

    }

    @Override
    public Track saveTrackDetail(Track track) throws Exception{
        if(track == null)
            return null;

        return save(track) != null ? track : null;
    }

    @Override
    public Track deleteTrackDetail(Track track) throws Exception{
        if(track == null)
            return null;

        return delete(track) ? track : null;
    }
}
