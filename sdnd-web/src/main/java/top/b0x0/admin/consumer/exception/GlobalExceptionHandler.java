package top.b0x0.admin.consumer.exception;

import org.apache.dubbo.rpc.RpcException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.b0x0.admin.common.exception.BusinessErrorException;
import top.b0x0.admin.common.exception.EntityExistException;
import top.b0x0.admin.common.exception.EntityNotFoundException;
import top.b0x0.admin.common.util.ThrowableUtils;
import top.b0x0.admin.common.util.constants.CommonConstants;
import top.b0x0.admin.common.util.enums.BusinessMsgEnum;
import top.b0x0.admin.common.vo.R;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TANG
 * @date 2020-11-23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * shiro未授权异常
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public R unauthorizedExceptionHandler() {
        return R.fail(403, "No permission!");
    }

    /**
     * 处理shiro异常
     *
     * @param e /
     * @return /
     */
    @ExceptionHandler(ShiroException.class)
    public R handleShiroException(ShiroException e) {
        if (e instanceof UnknownAccountException) {
            log.error("ShiroException -->UnknownAccountException {}", "账号不存在");
            return R.fail("账号不存在");
        } else if (e instanceof IncorrectCredentialsException) {
            log.error("ShiroException -->IncorrectCredentialsException {}", "密码错误");
            return R.fail("密码错误");
        } else if (e instanceof LockedAccountException) {
            log.error("ShiroException -->LockedAccountException {}", "账户已被禁用");
            return R.fail("账户已被禁用");
        } else if (e instanceof AuthorizationException) {
            log.error("ShiroException -->AuthorizationException {}", "没有权限");
            return R.fail("没有权限");
        }
        return R.fail(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
    }

    /**
     * 处理dubbo异常，可在代码中手动抛出rpcException，自定义异常信息
     * throw new RpcException("自定义异常信息");
     *
     * @param e /
     * @return /
     */
    @ExceptionHandler(RpcException.class)
    public R rpcExceptionHandler(RpcException e) {
        log.error("服务器运行异常 Message: " + e.getMessage());
        return R.fail(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
    }

    /**
     * 系统异常 预期以外异常
     *
     * @param ex /
     * @return /
     */
    @ExceptionHandler(Exception.class)
    public R handleUnexpectedServer(Exception ex) {
        log.error("系统异常：", ex);
        return R.fail(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
    }

    /**
     * 拦截自定义业务异常，返回业务异常信息
     *
     * @param bex /
     * @return /
     */
    @ExceptionHandler(BusinessErrorException.class)
    public R handleBusinessError(BusinessErrorException bex) {
        Integer code = bex.getCode();
        String message = bex.getMessage();
        return R.fail(code, message);
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public R entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        String message = e.getMessage();
        return R.fail(400, message);
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public R entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtils.getStackTrace(e));
        String message = e.getMessage();
        return R.fail(400, message);
    }


    /**
     * 缺少请求参数异常
     *
     * @param ex HttpMessageNotReadableException
     * @return /
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleHttpMessageNotReadableException(MissingServletRequestParameterException ex) {
        log.error("缺少请求参数，{}", ex.getMessage());
        return R.fail(400, "缺少必要的请求参数");
    }

    //  Validated 请求参数校验异常处理

    /**
     * <1> 处理 form data方式调用接口校验失败抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return R.fail(collect, 400, CommonConstants.BAD_REQUEST_MSG);
    }

    /**
     * <2> 处理 json 请求体调用接口校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return R.fail(collect, 400, CommonConstants.BAD_REQUEST_MSG);
    }

    /**
     * <3> 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return R.fail(collect, 400, CommonConstants.BAD_REQUEST_MSG);
    }
}
