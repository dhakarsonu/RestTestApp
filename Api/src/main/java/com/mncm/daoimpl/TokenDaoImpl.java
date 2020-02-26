package com.mncm.daoimpl;

import com.api.common.entity.common.Token;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.TokenDao;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 26/08/18.
 */
@Slf4j
public class TokenDaoImpl extends OfyService implements TokenDao {

    @Override
    public Token get(String id){
        return get(Token.class,id);
    }

    @Override
    public Token getByEmail(String email){
        if (email == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Token.class).filter("email", email).first().now();
    }

    @Override
    public Token createToken(String jwToken) throws Exception{

        Token token = new Token();
        String id = RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC);
        id       = id + ":" +RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC);
        token.setId(id);
        token.setAccessToken(jwToken);

        return saveToken(token);

    }

    @Override
    public  Token saveToken(Token token){

        return save(token) != null ? token : null;

    }

    @Override
    public  Token deleteToken(String id){

        Token token = get(id);

        if(token == null)
            return null;

        return delete(token) ? token : null;

    }


}
