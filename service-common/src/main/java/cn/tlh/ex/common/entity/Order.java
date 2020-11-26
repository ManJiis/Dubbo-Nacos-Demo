package cn.tlh.ex.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 今日订单(TOrder)实体类
 *
 * @author makejava
 * @since 2020-11-25 10:33:00
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
     * 交易方式 1：额度交易 2：银行卡交易 3：余额交易
     */
    private Integer tradeWay;
    /**
     * 账单交易类型 1：即时，2：远期
     */
    private Integer tradeType;
    /**
     * 交易状态 0:待支付 1:交易成功 2:交易失败  (具体失败原因参考respCode)3:远期账单待确认（供应商确认） 4:远期账单待退款 7: 远期账单待结算（商户确认）
     */
    private Integer state;
    /**
     * 退款状态 0：无退款 1：退款成功 2：退款失败 3：发起退款 4：客服处理中
     */
    private Integer refundState;
    /**
     * 清算状态  -1: 等待清算  0:发起清算  1:清算成功  2:清算失败
     */
    private Integer settleSt;
    /**
     * 客服介入 1：无 2：响应中 3：已处理
     */
    private Integer applyCustomer;
    /**
     * 操作类型 0:默认无需操作 1:强制平账 2:勾兑 3:退单 4：客服操作
     */
    private Integer markSt;
    /**
     * 对账状态 0:未对账 1:对账成功(正常) 2:强制平账   3:短款(平台缺失) 4:长款(银行缺失) 5:交易状态不符 6:清算状态不符 7: 交易金额不符 8:清算金额不符
     */
    private Integer checkSt;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 清算金额
     */
    private BigDecimal amountSettle;
    /**
     * 平台返回码
     */
    private String respCode;
    /**
     * 是否触发风控
     */
    private Integer isRisk;
    /**
     * 支付账号
     */
    private String cardNo;
    /**
     * 交易账号，商户操作员姓名
     */
    private String merOperatorName;
    /**
     * 平台商户号
     */
    private String merNo;
    /**
     * 商户名
     */
    private String merName;
    /**
     * app商户操作员唯一ID
     */
    private String appMerId;
    /**
     * 受理账号，APP是供应商手机号，POS是终端号
     */
    private String terminalNo;
    /**
     * 受理账号，供应商操作员姓名
     */
    private String supOperatorName;
    /**
     * 供应商号
     */
    private String supId;
    /**
     * 供应商名
     */
    private String supName;
    /**
     * app供应商操作员唯一ID
     */
    private String appSupId;
    /**
     * 商户交易卡号（银行卡交易时有该字段）
     */
    private String merCard;
    /**
     * 供应商清算卡号
     */
    private String supCard;
    /**
     * 订单描述
     */
    private String body;
    /**
     * 失败原因
     */
    private String errorMsg;
    /**
     * 上送的银行
     */
    private String bankCode;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 银行订单id
     */
    private String bankOrderNo;
    /**
     * 银行订单状态  0：发起  1：成功 2：失败
     */
    private String bankOrderSt;
    /**
     * 银行订单返回描述
     */
    private String bankOrderMsg;
    /**
     * 银行订单清算状态  0:发起  1: 成功  2: 失败
     */
    private String bankOrderSettleSt;
    /**
     * 银行订单清算描述
     */
    private String bankRespMsg;
    /**
     * 账单疑义ID
     */
    private String billingDoubtId;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 平台对账人员id
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String propitiationId;
    /**
     * 平台对账人处理(退单等操作)时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime propitiationTime;
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
     * 清算开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settleTimeStart;
    /**
     * 清算完成时间(银行返回)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settleTimeEnd;
    /**
     * 远期账单，设定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endSettleTime;
    /**
     * 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime finishTime;
    /**
     * 订单创建事件-系统时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate createTime;

}