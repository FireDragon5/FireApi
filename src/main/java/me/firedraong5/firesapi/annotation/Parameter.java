package me.firedraong5.firesapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

	//	@NotNull
	String value();


	int minArgs() default 0;
	//	@NotNull
	boolean requiresPlayer() default false;

	boolean checkConsole() default false;

	String permission() default "";


}
