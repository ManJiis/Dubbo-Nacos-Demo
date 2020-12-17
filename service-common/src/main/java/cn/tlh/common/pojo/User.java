package cn.tlh.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author TANG
 */
@Data
@TableName("sys_user")
public class User {
    private String username;
    private String password;
    private String email;

}