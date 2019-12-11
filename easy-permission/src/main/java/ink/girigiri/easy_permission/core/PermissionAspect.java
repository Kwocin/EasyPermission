package ink.girigiri.easy_permission.core;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


import ink.girigiri.easy_permission.PermissionActivity;
import ink.girigiri.easy_permission.annotation.PermissionCanceled;
import ink.girigiri.easy_permission.annotation.PermissionDenied;
import ink.girigiri.easy_permission.annotation.PermissionRequest;
import ink.girigiri.easy_permission.utils.PermissionUtils;

@Aspect
public class PermissionAspect {

    private static final String TAG="PermissionAspect";

    @Pointcut("execution(@ink.girigiri.easy_permission.annotation.PermissionRequest * *(..))" +
            "&&@annotation(permission)")
    public void requestPermission(PermissionRequest permission){

    }
    @Around("requestPermission(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, PermissionRequest permission)throws Throwable{
        Context context=null;
        final Object object = joinPoint.getThis();

        if (joinPoint.getThis() instanceof Context) {
            context = (Context) object;
        } else if (joinPoint.getThis() instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else {
        }
        if (context == null || permission == null) {
            Log.d(TAG, "aroundJonitPoint error ");
            return;
        }
        final Context finalContext = context;

        PermissionActivity.requestPermission(context, permission.value(), permission.requestCode(),
                new IPermission() {
            @Override
            public void ganted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void canceled() {
                PermissionUtils.invokAnnotation(object, PermissionCanceled.class);
            }

            @Override
            public void denied() {
                PermissionUtils.invokAnnotation(object, PermissionDenied.class);

            }
        });

    }
}
