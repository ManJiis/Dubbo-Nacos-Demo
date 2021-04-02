
package top.b0x0.admin.common.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import top.b0x0.admin.common.vo.PageVo;

import java.time.LocalDateTime;

/**
 * @author TANG
 * 公共查询类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleReqVo extends PageVo {

    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
