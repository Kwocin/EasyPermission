package ink.girigiri.easy_permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ink.girigiri.easy_permission.core.IPermission;
import ink.girigiri.easy_permission.utils.PermissionUtils;

public class PermissionActivity extends Activity {
    private static final String PARAM_PERMISSION="PARAM_PERMISSION";
    private static final String PARAM_REQUEST_PERMISSION="PARAM_REQUEST_PERMISSION";

    private String[] mPermissions;
    private int mRequestCode;
    private static IPermission mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Bundle bundle=getIntent().getExtras();
        mPermissions=bundle.getStringArray(PARAM_PERMISSION);
        mRequestCode=bundle.getInt(PARAM_REQUEST_PERMISSION,-1);

        if (mPermissions.length<=0|mRequestCode<0|mPermissions==null){
            finish();
            return;
        }
        if (PermissionUtils.hasPermission(this,mPermissions)){
            mPermissionListener.ganted();
            finish();
            return;
        }
        requestPermissions(mPermissions,mRequestCode);

    }

    /**
     * 外部调用
     * @param context 外部上下文
     * @param permissions 请求权限
     * @param requestCode   请求码
     * @param listener  请求结果回调
     */
    public static void  requestPermission(Context context
            , String[] permissions,int requestCode,IPermission listener){
        mPermissionListener=listener;
        Intent intent=new Intent(context,PermissionActivity.class);
        //开启独立的栈
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle=new Bundle();
        bundle.putStringArray(PARAM_PERMISSION,permissions);
        bundle.putInt(PARAM_REQUEST_PERMISSION,requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            //关闭动画
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(PermissionUtils.verifyPermission(grantResults)){
            mPermissionListener.ganted();
            finish();
            return;
        }
        //用户点击了不再显示
        if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
            mPermissionListener.denied();
            finish();
            return;
        }

        //用户取消
        mPermissionListener.canceled();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
