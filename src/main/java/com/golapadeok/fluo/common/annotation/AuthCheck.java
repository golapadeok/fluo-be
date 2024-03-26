package com.golapadeok.fluo.common.annotation;

import com.golapadeok.fluo.domain.role.domain.Credential;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    Credential[] credential();

}
