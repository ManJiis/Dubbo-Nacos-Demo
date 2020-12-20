
package cn.tlh.admin.common.base.vo.req;

import lombok.Data;

/**
 * 修改密码的 Vo 类
 * @author TANG
 * @date 2019年7月11日13:59:49
 */
@Data
public class UserPassVo {

    private String oldPass;

    private String newPass;
}
