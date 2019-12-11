package ink.girigiri.easy_permission.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Sony implements IMenu {

    @Override
    public Intent getIntent(Context context) {
        Intent intent = new Intent(context.getPackageName());
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        return intent;
    }
}
