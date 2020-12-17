package cn.tlh.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 今日订单(TOrder)实体类
 *
 * @author makejava
 * @since 2020-11-25
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 590166862808902373L;
    /**
     * 地区号（4位置） +  7（7位自增）+机器编号 如 1或者2或者3
     */
    private String id;
    /**
     * 订单来源 0：pos 1：app
     */
    private Integer orderSource;
    /**
     * 交易状态 0:待支付 1:交易成功 2:交易失败  (具体失败原因参考respCode)3:远期账单待确认（供应商确认） 4:远期账单待退款 7: 远期账单待结算（商户确认）
     */
    private Integer state;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 支付账号
     */
    private String cardNo;
    /**
     * 平台商户号
     */
    private String merNo;
    /**
     * 商户名
     */
    private String merName;

    /**
     * 订单描述
     */
    private String body;
    /**
     * 失败原因
     */
    private String errorMsg;

    /**
     * 支付开始时间(pos时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTimeStart;
    /**
     * 支付结束时间(银行返回)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTimeEnd;
    /**
     * 订单完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;
    /**
     * 订单创建事件-系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createTime;


}