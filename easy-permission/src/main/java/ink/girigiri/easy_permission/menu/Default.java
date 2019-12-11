package ink.girigiri.easy_permission.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

public class Default implements IMenu {
    @Override
    public Intent getIntent(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }
}
