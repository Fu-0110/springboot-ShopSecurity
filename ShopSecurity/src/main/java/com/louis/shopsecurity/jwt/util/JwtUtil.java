package com.louis.shopsecurity.jwt.util;

import cn.hutool.json.JSONUtil;
import com.louis.shopsecurity.jwt.dto.PayloadDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;
import java.util.Date;

public class JwtUtil {

    public static final String DEFAULT_SECRET = "mySecret";      // 預設金鑰

    /**
     * 使用HMAC SHA-256
     *
     * @param payloadStr 有效酬載
     * @param secret     金鑰
     * @return JWS串
     * @throws JOSEException
     */
    public static String generateTokenByHMAC (String payloadStr , String secret) throws JOSEException {

        //建立JWS標頭，設定簽名演算法和類型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT)
                                                                       .build();

        //將酬載資訊封裝到Payload中
        Payload payload = new Payload(payloadStr);

        //建立JWS物件
        JWSObject jwsObject = new JWSObject(jwsHeader , payload);

        //建立HMAC簽名器
        JWSSigner jwsSigner = new MACSigner(secret);

        //簽名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 驗證簽名，提取有效酬載，以PayloadDto物件形式傳回
     *
     * @param token  JWS串
     * @param secret 金鑰
     * @return PayloadDto物件
     * @throws ParseException
     * @throws JOSEException
     */
    public static PayloadDto verifyTokenByHMAC (String token , String secret) throws ParseException, JOSEException {

        //從token中解析JWS物件
        JWSObject jwsObject = JWSObject.parse(token);

        //建立HMAC驗證器
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new JOSEException("token簽名不合法！");
        }
        String payload = jwsObject.getPayload()
                                  .toString();
        PayloadDto payloadDto = JSONUtil.toBean(payload , PayloadDto.class);
        if (payloadDto.getExp() < new Date().getTime()) {
            throw new JOSEException("token已過期");
        }
        return payloadDto;
    }
}
