package com.mncm.dao;

import com.api.common.entity.contact.UserRole;

/**
 * Created by sonudhakar on 09/09/17.
 */
public interface UserRoleDao {

    UserRole get(String id);
    UserRole getByUserId(String userId) throws Exception;
    UserRole createNewRole(UserRole userRole) throws Exception;
    UserRole saveRole(UserRole userRole);
    UserRole deleteRole(String id);
}
