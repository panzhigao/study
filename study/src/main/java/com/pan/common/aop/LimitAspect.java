package com.pan.common.aop;

import com.pan.common.annotation.RateLimit;
import com.pan.common.exception.BusinessException;
import com.pan.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * @author panzhigao
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private DefaultRedisScript<Number> redisLuaScript;

    @Bean
    public DefaultRedisScript<Number> createDefaultRedisScript(){
        return new DefaultRedisScript<Number>();
    }

    /**
     * 读取限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Number> redisLuaScript() {
        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("file/rateLimit.lua")));
        redisScript.setResultType(Number.class);
        return redisScript;
    }



    @Around("execution(* com.pan.controller..*(..))")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if (rateLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = IPUtils.getIpAddress(request);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ipAddress).append("-")
                    .append(targetClass.getName()).append("-")
                    .append(method.getName()).append("-")
                    .append(rateLimit.key());

            List<String> keys = Collections.singletonList(stringBuffer.toString());
            Number number = redisTemplate.execute(redisLuaScript, keys, rateLimit.count(), rateLimit.time());
            if (number != null && number.intValue() != 0 && number.intValue() <= rateLimit.count()) {
                log.info("限流时间段【{}秒】内访问第【{}】 次", rateLimit.time() ,number.toString());
                return joinPoint.proceed();
            }
        } else {
            return joinPoint.proceed();
        }
        throw new BusinessException("已经超过设置限流次数，请稍后重试");
    }
}
