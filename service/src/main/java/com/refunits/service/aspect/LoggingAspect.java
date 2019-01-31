package com.refunits.service.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = Logger.getLogger(LoggingAspect.class);

    @Pointcut("within(com.refunits.service.service.*)")
    public void addLogging() {
    }

    @Before("addLogging()")
    public void loggingBefore(JoinPoint joinPoint) {
        System.out.println("Start method " + joinPoint.toString());
        LOG.info("Start method " + joinPoint.toString());
    }

    @AfterReturning("addLogging()")
    public void loggingAfterReturning(JoinPoint joinPoint) {
        System.out.println("Finish method successfully " + joinPoint.toString());
        LOG.info("Finish method successfully " + joinPoint.toString());
    }

    @AfterThrowing(value = "addLogging()", throwing = "ex")
    public void loggingAfterThrowing(Throwable ex) {
        System.out.println("Throw exception: " + ex.getMessage());
        LOG.info("Throw exception in method ", ex);
    }
}