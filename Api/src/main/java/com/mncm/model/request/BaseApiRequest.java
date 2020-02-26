package com.mncm.model.request;

import lombok.Data;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * AnywhereWorks
 * Created by Sonu on 03/17/18.
 */
@Data
public class BaseApiRequest implements Serializable {

    private static final long serialVersionUID = 7595035688697483553L;

    @PathParam("applicationId")
    @DefaultValue("")
    private String applicationId;

    @PathParam("accountId")
    @DefaultValue("")
    private String accountId;

    @PathParam("categoryId")
    @DefaultValue("")
    private String categoryId;

    @PathParam("userId")
    @DefaultValue("")
    private String userId;

    @QueryParam("limit")
    private int limit;

    @QueryParam("cursor")
    private String cursor;

    @QueryParam("since")
    private long since;

    public int getLimit() {
        return limit <= 0 ? 10 : limit;
    }
}
