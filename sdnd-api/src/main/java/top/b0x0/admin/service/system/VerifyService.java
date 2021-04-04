
package top.b0x0.admin.service.system;


import top.b0x0.admin.common.vo.req.EmailVo;

/**
 * @author TANG
 * @date 2020-12-26
 */
public interface VerifyService {

    /**
     * 发送验证码
     * @param email /
     * @param key /
     * @return /
     */
    EmailVo sendEmail(String email, String key);


    /**
     * 验证
     * @param code /
     * @param key /
     */
    void validated(String key, String code);
}
