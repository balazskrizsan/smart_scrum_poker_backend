package com.kbalazsworks.smartscrumpokerbackend.test_aspects;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.IInsert;
import org.junit.platform.commons.annotation.Testable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Testable
public @interface SqlPreset
{
    Class<? extends IInsert>[] presets() default {};

    // @todo: implement
    boolean transactional() default true;

    boolean truncate() default true;

    boolean truncateAfter() default true;
}
