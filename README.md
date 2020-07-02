## 使用说明
在项目的 build.gradle 文件下，加入如下内容
```gradle
 dependencies {
        classpath 'com.android.tools.build:gradle:3.5.3'
        //引用Maven库
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
```
app 模块 build.gradle 文件 加入
```gradle
apply plugin: 'android-aspectjx'
```
使用
```Java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }
    //授权成功回调
    @PermissionRequest({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void requestPermission(){

    }
    //拒绝授权回调
    @PermissionCanceled
    public void canceled(){
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }
    //勾选不再提示回调
    @PermissionDenied
    public void Denied(){
        Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
        //权限设置
        PermissionUtils.goToMenu(this);
    }
}
```
