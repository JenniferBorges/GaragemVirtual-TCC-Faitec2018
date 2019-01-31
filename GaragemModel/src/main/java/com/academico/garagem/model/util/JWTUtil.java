package com.academico.garagem.model.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {

    private static final String KEY = "rtzWigpJ.1\"6cX|7zho9m,[`x?-x]i(+3,HPIe*.HlaLck>rGd:qJJ1/[uW;l@w";

    public static String create(String subject) throws Exception {
        return JWT.create().withSubject(subject).sign(Algorithm.HMAC256(KEY));
    }

    public static String decode(String token) throws Exception {
        return JWT.require(Algorithm.HMAC256(KEY)).build().verify(token).getSubject();
    }
}
