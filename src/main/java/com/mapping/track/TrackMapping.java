package com.mapping.track;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET, headers = "X-HTTP-Method-Override=TRACK")
public @interface TrackMapping {

    @AliasFor(annotation = RequestMapping.class) String name() default "";

    @AliasFor(annotation = RequestMapping.class) String[] value() default {};

    @AliasFor(annotation = RequestMapping.class) String[] path() default {};

    @AliasFor(annotation = RequestMapping.class) String[] params() default {};

    @AliasFor(annotation = RequestMapping.class) String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class) String[] consumes() default {};

    @AliasFor(annotation = RequestMapping.class) String[] produces() default {};

}