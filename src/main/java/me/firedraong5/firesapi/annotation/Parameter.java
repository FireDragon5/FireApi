package me.firedraong5.firesapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {
	String value();
	int minArgs() default 0;
	boolean requiresPlayer() default false;
	String permission() default "";

	boolean showInArgs() default true;

	int showPlayerNames() default 0;
}

