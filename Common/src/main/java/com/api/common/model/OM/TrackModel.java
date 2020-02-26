package com.api.common.model.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 28/10/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TrackModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;



}
