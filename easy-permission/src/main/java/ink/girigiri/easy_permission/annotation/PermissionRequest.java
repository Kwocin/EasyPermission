package ink.girigiri.easy_permission.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ink.girigiri.easy_permission.utils.PermissionUtils.REQUEST_CODE;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionRequest {
    String[] value();
    int requestCode() default REQUEST_CODE;
}
