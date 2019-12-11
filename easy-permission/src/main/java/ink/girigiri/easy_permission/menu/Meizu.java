package ink.girigiri.easy_permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Meizu implements IMenu {

    @Override
    public Intent getIntent(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());

        return intent;
    }
}
