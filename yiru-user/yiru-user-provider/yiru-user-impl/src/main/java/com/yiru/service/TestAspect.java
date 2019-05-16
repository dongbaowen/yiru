package com.yiru.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component("testAspect")
public class TestAspect {

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知的之前部分");
        Object proceed = pjp.proceed();
        System.out.println("环绕通知的之后部分");
        return proceed;
    }
}
