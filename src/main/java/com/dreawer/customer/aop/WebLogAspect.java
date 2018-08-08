package com.dreawer.customer.aop;

import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.utils.JsonFormatUtil;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


    @Pointcut("execution(public * com.dreawer.customer.web.*.*(..))")
	public void webLog(){
		logger.debug("aop start ....................................................................");
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            logger.debug(request);
        }catch (Throwable e){
		    logger.error(e);
        }
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		try {
            if( ret instanceof ResponseCode ) {
                ResponseCode responseCode = (ResponseCode)ret ;
                logger.debug(responseCode);
            }
        }catch (Throwable e){
            logger.error(e);
        }
	}

    @Around("execution(com.dreawer.responsecode.rcdt.ResponseCode com.dreawer.customer.web.member.*.*(..))")
    public ResponseCode handlerControllerMethod(ProceedingJoinPoint pjp){
        long startTime = System.currentTimeMillis();
        ResponseCode result;
        try{
            result = (ResponseCode) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        }catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        logger.info(JsonFormatUtil.formatJson(result));
        return result;
    }

    private ResponseCode handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResponseCode result;
        // 已知异常
        if (e instanceof ResponseCodeException) {
            logger.error("其他系统异常",e);
            result = ((ResponseCodeException) e).getResponseCode();
        } else {
            logger.error(pjp.getSignature() + " error ", e);
            result = Error.APPSERVER;
        }

        return result;
    }
}
