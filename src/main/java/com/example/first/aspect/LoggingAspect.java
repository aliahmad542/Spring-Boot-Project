package com.example.first.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() {
    }

    @Before("controllerPointcut() || servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("▶▶ start: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

        if (joinPoint.getArgs().length > 0) {
            log.info("▶▶ Arguments: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    @AfterReturning(pointcut = "controllerPointcut() || servicePointcut()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info(" COMPLETED: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

        if (result != null) {
            log.info("Result: {}", result.toString());
        }
    }

    @AfterThrowing(pointcut = "controllerPointcut() || servicePointcut()",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("ERROR in: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        log.error("Error Cause: {}", error.getMessage());
    }

    @Pointcut("@annotation(com.example.first.aspect.LogExecutionTime)")
    public void executionTimePointcut() {
    }

    @Around("executionTimePointcut()")
    public Object logExecutionTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info("⏱️ {}.{}() executed in {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                executionTime);

        return proceed;
    }
}