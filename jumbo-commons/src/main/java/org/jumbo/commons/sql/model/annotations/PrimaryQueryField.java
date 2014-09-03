package org.jumbo.commons.sql.model.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Return on 03/09/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
@Target(ElementType.FIELD)
public @interface PrimaryQueryField {}
