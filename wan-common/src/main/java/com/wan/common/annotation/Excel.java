package com.wan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Excel导出注解
 * @author wmj
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) // 注解在哪个阶段执行
@Documented
public @interface Excel {

	String Name() default ""; // 字段名
	String Dict() default ""; // 数据字典
	
}
