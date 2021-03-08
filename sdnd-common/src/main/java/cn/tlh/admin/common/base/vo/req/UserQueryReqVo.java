
package cn.tlh.admin.common.base.vo.req;

import cn.tlh.admin.common.base.common.PageVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author TANG
 * @date 2020-11-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReqVo extends PageVo implements Serializable {

    private Long id;
    private String email;
    private String username;
    private String nickName;
    private Boolean enabled;
    private Long deptId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
