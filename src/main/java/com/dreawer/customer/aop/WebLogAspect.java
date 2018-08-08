package com.dreawer.customer.aop;

import com.dreawer.responsecode.rcdt.ResponseCode;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
}
