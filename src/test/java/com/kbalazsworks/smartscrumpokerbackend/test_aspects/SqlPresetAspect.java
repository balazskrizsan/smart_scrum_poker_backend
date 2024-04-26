package com.kbalazsworks.smartscrumpokerbackend.test_aspects;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.IInsert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

@Aspect
public class SqlPresetAspect
{
    @Autowired
    private BeforeService beforeService;

    @Pointcut("execution(* com.kbalazsworks.smartscrumpokerbackend..*(..))")
    protected void findAllSocketDomainTestClasses()
    {
    }

    @Pointcut("@annotation(com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset)")
    protected void findSqlPresetAnnotatedTests()
    {
    }

    @Around("findAllSocketDomainTestClasses() && findSqlPresetAnnotatedTests()")
    public Object setup(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SqlPreset annotation = method.getAnnotation(SqlPreset.class);

        if (annotation.truncate())
        {
            beforeService.truncateDb();
        }

        Class<? extends IInsert>[] presets = annotation.presets();
        if (presets.length > 0)
        {
            beforeService.setupDb(presets);
        }

        return joinPoint.proceed();
    }
}
