package cn.tlh.admin.common.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PanXunYi
 * @description token对象
 * @date 2021-1-4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    private String userName;

    private String loginName;

    private String userId;

    private String permission;

    private String role;

    private String tel;

    private String office;

    private String regionLevel;
    private String county;
    private String city;

    private String salt;
}



