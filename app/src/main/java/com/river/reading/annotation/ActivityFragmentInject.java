package com.river.reading.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/12/12.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityFragmentInject {
  int contentViewId() default  -1;
  int toolbarTitle() default  -1;
  /**
   * 是否存在NavigationView
   *
   * @return
   */
  boolean hasNavigationView() default false;
}
