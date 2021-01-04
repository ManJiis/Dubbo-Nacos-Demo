package cn.tlh.admin.common.util.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * @author TANG
 * @description jwt工具类
 * <p>
 * JWT构成
 * 1. header
 * jwt 的头部承载两部分信息:
 * - 声明类型，这里是 jwt
 * - 声明加密的算法 通常直接使用 HMAC SHA256
 * 例如:
 * <pre>
 *                  {
 *                      'typ': 'JWT',
 *                      'alg': 'HS256'
 *                  }
 *              </pre>
 * 然后进行base64加密: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
 * 2. payload
 * 存放有效信息的地方,分三个部分:
 * - 标准中注册的声明
 * - 公共的声明
 * - 私有的声明
 * 例如:
 * <pre>
 *                  {
 *                      "sub": "1234567890",
 *                      "name": "John Doe",
 *                      "admin": true
 *                  }
 *              </pre>
 * 然后进行base64加密: eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
 * 3. signature
 * jwt 的第三部分是一个签证信息，这个签证信息由三部分组成：
 * - header (base64 后的)
 * - payload (base64 后的)
 * - secret
 * 这部分 base64 加密后的 header 和 base64 加密后的 payload 使用. 连接组成的字符串，然后通过 header 中声明的加密方式进行加盐 secret 组合加密，然后就构成了 jwt 的第三部分。
 * var encodedString = base64UrlEncode(header) + '.' + base64UrlEncode(payload);
 * var signature = HMACSHA256(encodedString, 'secret'); // TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
 * 将这三部分用  . 连接成一个完整的字符串，构成了最终的 jwt:
 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
 * eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.
 * TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
 * 应用:
 * 一般是在请求头里加入 Authorization，并加上 Bearer 标注：
 * <pre>
 *          fetch('api/user/1', {
 *              headers: {
 *                  'Authorization': 'Bearer ' + token
 *              }
 *          })
 *         <pre>
 * <p/>
 * @date 2021-1-4
 */
public class JwtUtil {
    private final static Logger logger = LogManager.getLogger(JwtUtil.class);

    /**
     * 一小时
     */
    private static final long ONE_HOUR = 60 * 60 * 1000;
    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = ONE_HOUR;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userInfo, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userInfo", userInfo)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Token getToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return JSON.parseObject(jwt.getClaim("userInfo").asString(), Token.class);
        } catch (JWTDecodeException e) {
            return new Token();
        }
    }

    /**
     * 生成签名,一小时后过期
     *
     * @return 加密的token
     */
    public static String sign(Token token, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //使用秘钥
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        String jwtString = JWT.create()
                .withClaim("userInfo", JSON.toJSONString(token))
                .withExpiresAt(date)
                .sign(algorithm);
        logger.debug(String.format("JWT:%s", jwtString));
        return jwtString;
    }

    public static void main(String[] args) {
        Token token = Token.builder().userId("1033-0001").userName("张三").loginName("admin").tel("17680112222").salt("FahkewhqWEIadadw").build();
        String sign = JwtUtil.sign(token, token.getSalt());
        System.out.println("sign = " + sign);
    }

}
