package cn.tlh.admin.service.serviceImpl.jobhandler;

import cn.tlh.admin.common.pojo.BrokerMessageLog;
import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.constants.RabbitMqConstants;
import cn.tlh.admin.common.util.json.JackJsonUtils;
import cn.tlh.admin.dao.BrokerMessageLogDao;
import cn.tlh.admin.service.rabbitmq.MqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.tlh.admin.common.util.BuildDlxMessageUtils.buildOrderDelayDlxMessage;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、任务开发：在Spring Bean实例中，开发Job方法；
 * 2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 * 4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 * @author TANG
 * @since 2021-02-16
 */
@Component
public class XxlJobHandler {
    private static final Logger log = LoggerFactory.getLogger(XxlJobHandler.class);

    @Resource
    MqService mqService;
    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        log.info("XXL-JOB, Hello World.");
        XxlJobHelper.log("demoJob running -----> 当前时间: {}", LocalDateTime.now());
        log.info("demoJob running -----> 当前时间: {}", LocalDateTime.now());
        XxlJobHelper.handleSuccess();
        // default success
    }

    /**
     * 处理延迟订单 重发机制, 超过3次还未发送成功  状态改为发送失败
     */
    @XxlJob("timeOutOrderReSendJobHandler")
    public void timeOutOrderReSendJobHandler() {
        log.info("当前时间: {}, timeOutOrderReSendJobHandler start....", LocalDateTime.now());
        // pull status = 0 and timeout message
        List<BrokerMessageLog> list = brokerMessageLogDao.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            if (messageLog.getTryCount() >= 3) {
                brokerMessageLogDao.changeBrokerMessageLogStatus(messageLog.getMessageId(), RabbitMqConstants.MSG_SEND_FAILURE, LocalDateTime.now());
            } else {
                // resend
                brokerMessageLogDao.update4ReSend(messageLog.getMessageId(), LocalDateTime.now());
                Order reSendOrder;
                try {
                    reSendOrder = JackJsonUtils.toObject(messageLog.getMessage(), Order.class);
                    if (reSendOrder != null) {
                        try {
                            DlxMessage dlxMessage = buildOrderDelayDlxMessage(reSendOrder);
                            mqService.sendDelayOrder(dlxMessage);
                        } catch (Exception e) {
                            System.err.println("-----------异常处理-----------");
                            log.error("消息重发出错: messageId: {},message: {}, error: {}", Objects.requireNonNull(reSendOrder).getId(), JackJsonUtils.toJsonString(reSendOrder), e.getMessage());
                        }
                    }
                } catch (JsonProcessingException e) {
                    log.error("订单消息转换异常, JackJsonUtils.toJsonString(reSendOrder) error: {}", e.getMessage());
                }
            }
        });
    }

}
