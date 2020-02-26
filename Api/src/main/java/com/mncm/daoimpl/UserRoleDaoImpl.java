package com.mncm.daoimpl;

import com.api.common.enums.EntityStatus;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.UserRoleDao;
import com.api.common.entity.contact.UserRole;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 09/09/17.
 */
@Slf4j
public class UserRoleDaoImpl  extends OfyService implements UserRoleDao {

    @Override
    public UserRole get(String id){
        return get(UserRole.class,id);
    }

    @Override
    public UserRole getByUserId(String contactId) throws Exception{
        if (contactId == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(UserRole.class).filter("contactId", contactId).first().now();
    }

    @Override
    public UserRole createNewRole(UserRole userRole) throws Exception{

        if(userRole.getId() == null){
            userRole.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        userRole.setStatus(EntityStatus.ACTIVE);

        return saveRole(userRole);

    }

    @Override
    public  UserRole saveRole(UserRole userRole){

        return save(userRole) != null ? userRole : null;

    }

    @Override
    public  UserRole deleteRole(String id){

        UserRole role = get(id);

        if(role == null)
            return null;

        return delete(role) ? role : null;

    }

}
