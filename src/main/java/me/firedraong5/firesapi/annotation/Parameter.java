package me.firedraong5.firesapi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

	String value();

	boolean requiresPlayer() default false;

}
