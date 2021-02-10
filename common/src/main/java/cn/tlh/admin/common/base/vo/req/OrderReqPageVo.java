package cn.tlh.admin.common.base.vo.req;

import cn.tlh.admin.common.base.common.PageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * @author TANG
 * @description:
 * @date: 2020-11-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderReqPageVo extends PageVo {
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
