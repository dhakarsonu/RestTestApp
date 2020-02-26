package com.mncm.dao;

import com.api.common.entity.common.Token;

/**
 * Created by sonudhakar on 26/08/18.
 */
public interface TokenDao {

    public Token get(String id);

    public Token createToken(String jwToken) throws Exception;

    public  Token saveToken(Token token);

    public  Token deleteToken(String id);

    public Token getByEmail(String email);


}
