package ink.girigiri.easy_permission.utils;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.collection.SimpleArrayMap;
import androidx.core.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import ink.girigiri.easy_permission.menu.*;


public class PermissionUtils {
    public static final int REQUEST_CODE = 0x31;
    private static SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }
    private static HashMap<String, Class<? extends IMenu>> permissionMenu = new HashMap<>();
    private static final String MANUFACTURER_DEFAULT = "Default";//默认
    public static final String MANUFACTURER_HUAWEI = "huawei";//华为
    public static final String MANUFACTURER_MEIZU = "meizu";//魅族
    public static final String MANUFACTURER_XIAOMI = "xiaomi";//小米
    public static final String MANUFACTURER_SONY = "sony";//索尼
    public static final String MANUFACTURER_OPPO = "oppo";
    public static final String MANUFACTURER_LG = "lg";
    public static final String MANUFACTURER_VIVO = "vivo";
    public static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    public static final String MANUFACTURER_LETV = "letv";//乐视
    public static final String MANUFACTURER_ZTE = "zte";//中兴
    public static final String MANUFACTURER_YULONG = "yulong";//酷派
    public static final String MANUFACTURER_LENOVO = "lenovo";//联想
    static {
        permissionMenu.put(MANUFACTURER_DEFAULT, Default.class);
        permissionMenu.put(MANUFACTURER_OPPO, OPPO.class);
        permissionMenu.put(MANUFACTURER_VIVO, VIVO.class);
        permissionMenu.put(MANUFACTURER_SONY, Sony.class);
        permissionMenu.put(MANUFACTURER_HUAWEI, Huawei.class);
        permissionMenu.put(MANUFACTURER_XIAOMI, Xiaomi.class);
        permissionMenu.put(MANUFACTURER_MEIZU, Meizu.class);
    }
    public static boolean hasPermission(Context context, String[] mPermissions) {
        for (String mPermission : mPermissions) {
            if (permissionExists(mPermission) && !checkPermissionGanted(context, mPermission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean permissionExists(String mPermission) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(mPermission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    private static boolean checkPermissionGanted(Context context, String mPermission) {
        return ContextCompat.checkSelfPermission(context, mPermission) == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean verifyPermission(int[] grantResults) {
        if (grantResults == null || grantResults.length == 0) {
            return false;
        }

        for (int ganted : grantResults) {
            if (ganted != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }


    public static void invokAnnotation(Object object, Class annotationClass) {
        //反射调用

        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            boolean isHasAnnotation = method.isAnnotationPresent(annotationClass);
            if (isHasAnnotation) {
                method.setAccessible(true);
                try {
                    method.invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * @date 创建时间 2018/4/18
     * @author Jiang zinc
     * @Description 前往权限设置菜单的工具类
     * @version
     */
    public static void goToMenu(Context context) {

        Class clazz = permissionMenu.get(Build.MANUFACTURER.toLowerCase());
        if (clazz == null) {
            clazz = permissionMenu.get(MANUFACTURER_DEFAULT);
        }

        try {
            IMenu iMenu = (IMenu) clazz.newInstance();

            Intent menuIntent = iMenu.getIntent(context);
            if (menuIntent == null) return;
            context.startActivity(menuIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
