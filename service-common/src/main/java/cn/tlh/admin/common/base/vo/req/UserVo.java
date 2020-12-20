package cn.tlh.admin.common.base.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TANG
 */
@Getter
@Setter
public class UserVo {
    private String username;

    private String password;

    private String email;
    // 验证码
    private String code;


}