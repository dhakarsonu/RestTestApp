package com.api.common.services;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import com.api.common.utils.ObjUtil;
import io.jsonwebtoken.*;

import java.util.*;

/**
 * Created by sonudhakar on 22/08/18.
 */
public class Authentication {

    private final static String apiKey = "123456789-mncm-lixiram-software";
    private final static String issuer = "1z6Sz7qSqomxQEcXCI68rbipoHru1OkC";
    private final static long ttlMillis = 604800000;

    public static String createJWT(Map<String,String> request) {

        if(request == null)
            return null;

        request.put("token_type","BEARER");
        request.put("access_type","ONLINE");
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = apiKey.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(ObjUtil.getJson(request))
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Map<String, Object> parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Map<String,Object> response = new HashMap<>();
        Claims claims = Jwts.parser()
                .setSigningKey(apiKey.getBytes())
                .parseClaimsJws(jwt).getBody();
        Map<String,Object> subject = ObjUtil.getMapFromJson(claims.getSubject());
        String scopes = (String) subject.get("scopes");
        if(scopes != null) {
            List<String> arrayList = Arrays.asList(subject.get("scopes").toString().split(","));
            subject.put("scopes", arrayList);
        }
        subject.put("expire",claims.getExpiration());


        return subject;
    }
}
