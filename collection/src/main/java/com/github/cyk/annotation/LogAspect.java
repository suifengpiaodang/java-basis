package com.github.cyk.annotation;

import com.alibaba.fastjson.JSONObject;
import com.github.cyk.util.AnnotationUtils;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.github.cyk.collection..*.*(..))")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object aroundMethod(ProceedingJoinPoint pdj){

        String className = pdj.getTarget().getClass().getName();
        String methodName = pdj.getSignature().getName();

        Object result = null;
        String logStr = null;
        try {
            logStr = AnnotationUtils.get().getAnnotatioinFieldValue(className, methodName, FlightLog.class.getName(), "logStr");
            if (!StringUtils.isEmpty(logStr)) {
                log.info("请求方法 ：" + logStr);
                log.info("方法名称 ：" + methodName);
                log.info("请求参数 ：" + JSONObject.toJSONString(Arrays.asList(pdj.getArgs())));
            }
            Stopwatch otaWatch = Stopwatch.createStarted();
            result = pdj.proceed();
            otaWatch.stop();
            log.info(logStr+" 耗时  : [{}]", otaWatch.elapsed(TimeUnit.MILLISECONDS));
            if (!StringUtils.isEmpty(logStr)) {
                log.info(logStr +" "+methodName+" 返回结果 ：" + JSONObject.toJSONString(result));
            }

        } catch (Throwable e) {
           // log.error("调用相应接口异常", e.getMessage());
           // throw new StandardException(SystemErrorEnum.SYSTEM_ERROR.getCode(),SystemErrorEnum.SYSTEM_ERROR.getCode(),"日志处理报错");
        }
        return result;
    }

}
