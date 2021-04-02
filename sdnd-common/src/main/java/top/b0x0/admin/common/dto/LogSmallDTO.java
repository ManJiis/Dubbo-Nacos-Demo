package top.b0x0.admin.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author TANG
 * @date 2019-5-22
 */
@Data
public class LogSmallDTO implements Serializable {

    private static final long serialVersionUID = -2108551258379191292L;
    private String description;

    private String requestIp;

    private Long time;

    private String address;

    private String browser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
