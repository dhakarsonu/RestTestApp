package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 26/08/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Token extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712139128615129334L;

    @Unindex
    private String accessToken;

    @Index
    private String email;

}
