package org.jumbo.utils.annotations;

/**
 * Created by Return on 02/09/2014.
 */

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Target(ElementType.FIELD)
public @interface GameNetwork {}
