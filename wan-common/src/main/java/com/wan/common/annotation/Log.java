package com.wan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wan.common.enumerate.OperationType;

/**
 * 自定义操作日志注解
 * @author wmj
 *
 */
@Target(ElementType.METHOD) // 注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) // 注解在哪个阶段执行
@Documented
public @interface Log {

	String BusinessName() default ""; // 业务名称
	OperationType OperationType() default OperationType.UNKNOWN; // 操作类型
	String Content() default ""; // 内容
	
}
