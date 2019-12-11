package ink.girigiri.easy_permission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import ink.girigiri.easy_permission.annotation.PermissionCanceled;
import ink.girigiri.easy_permission.annotation.PermissionDenied;
import ink.girigiri.easy_permission.annotation.PermissionRequest;
import ink.girigiri.easy_permission.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }
    @PermissionRequest({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void requestPermission(){

    }
    @PermissionCanceled
    public void canceled(){
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }
    @PermissionDenied
    public void Denied(){
        Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
        PermissionUtils.goToMenu(this);
    }
}
