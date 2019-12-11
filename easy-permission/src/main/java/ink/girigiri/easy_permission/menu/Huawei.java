package ink.girigiri.easy_permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Huawei implements IMenu {

    @Override
    public Intent getIntent(Context context) {
        Intent intent = new Intent(context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        return intent;
    }
}
