package top.b0x0.admin.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Getter
@Setter
public class UserDto extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -7189222197789096299L;
    private Long id;

    private Set<RoleSmallDto> roles;

    private Long deptId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    @JsonIgnore
    private String password;

    private Integer enabled;

    @JsonIgnore
    private Boolean isAdmin = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pwdResetTime;
}
