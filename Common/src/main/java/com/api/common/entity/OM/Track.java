package com.api.common.entity.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.api.common.model.OM.TrackModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 28/10/17.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Track extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712139888614129334L;

    @Index
    private String orderId;

    @Unindex
    private String description;

    @Unindex
    private String status;

    public Track(TrackModel trackModel){

        this.orderId = trackModel.getOrderId();
        this.id = trackModel.getId();
        this.description = trackModel.getDescription();
        this.status = trackModel.getStatus();

    }
}
