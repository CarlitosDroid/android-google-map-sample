package com.carlitosdroid.basicgooglemapsample.util;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.carlitosdroid.basicgooglemapsample.view.dialog_fragment.LocationNeededDialogFragment;

/**
 * Created by carlos on 2/6/17.
 *
 */

public class PermissionUtils {


    public static void requestPermission(AppCompatActivity appCompatActivity, int requestId,
                                                 String permission){
        if(ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity, permission)){
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{permission}, requestId);
        }else{
            LocationNeededDialogFragment.newInstance()
                    .show(appCompatActivity.getSupportFragmentManager(), "dialog");
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{permission}, requestId);
        }
    }

    /**
     * Checks if the result contains a {@link PackageManager#PERMISSION_GRANTED} result for a
     * permission from a runtime permissions request.
     *
     * @see android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }
}
