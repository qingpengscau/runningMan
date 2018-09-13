package scau.zxck.utils;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;
import java.security.Key;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.Map;
//Sample method to construct a JWT


public class JwtUtil {
    private final  String apiKey="we have a good team,and we can write excellent codes,so we can success";
    private final  String napiKey="we  a good team,and we can write excellent codes,so we can success";
    public   String createJWT(String id,long lastMills ) {

//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        //Let's set the JWT Claims
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("Issuer",user);
//        jsonObject.put("Password",password);
//        String r=jsonObject.toJSONString();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .signWith(signatureAlgorithm, signingKey);

        if(lastMills>=0){
            long expMills=nowMillis+lastMills;
            Date date=new Date(expMills);
            builder.setExpiration(date);
        }
//if it has been specified, let's add the expiration
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }

//Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public Claims parseJWT(String jwt) throws Exception {
//This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(apiKey))
                    .parseClaimsJws(jwt).getBody();
         return claims;
//        String temp=claims.getSubject();
//        JSONObject jsonObject=JSONObject.parseObject(temp);
//        System.out.println(jsonObject.get("Pass_Word"));
//       System.out.println("Expiration: " + claims.getExpiration());

    }

    public static void main(String str[]){
//        JwtUtil jwtUtil=new JwtUtil();
//        String jwt=jwtUtil.createJWT("123","qingpeng",18000000);
//        System.out.println(jwt);
//        //jwtUtil.parseJWT(jwt);
    }
}
