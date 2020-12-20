
package cn.tlh.admin.common.base.dto;

import cn.tlh.admin.common.base.vo.req.common.PageVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

/**
 * @author TANG
 * 公共查询类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleReq extends PageVo {

    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
