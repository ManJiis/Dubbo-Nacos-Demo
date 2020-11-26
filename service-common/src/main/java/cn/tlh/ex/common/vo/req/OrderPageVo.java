package cn.tlh.ex.common.vo.req;

import lombok.*;



/**
 * @author Ling
 * @description:
 * @date: 2020-11-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderPageVo extends PageVo {
    /**
     * 交易方式 1：额度交易 2：银行卡交易 3：余额交易
     */
    private Integer tradeWay;
    /**
     * 订单来源 0：pos 1：app
     */
    private Integer orderSource;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 订单完成时间
     */
    private String startTime;

    private String endTime;
}
