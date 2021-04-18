package top.b0x0.admin.common.vo.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import top.b0x0.admin.common.vo.PageVo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * callSuper = true 作用: 将其父类属性也进行比较
 *
 * @author TANG
 * @date 2020-11-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends PageVo implements Serializable {
    private static final long serialVersionUID = 8234558996610141904L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String email;
    private String username;
    private String nickName;
    private Boolean enabled;
    private Long deptId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
