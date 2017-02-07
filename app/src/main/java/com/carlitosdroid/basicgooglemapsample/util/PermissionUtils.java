package com.carlitosdroid.basicgooglemapsample.util;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.carlitosdroid.basicgooglemapsample.view.dialog_fragment.LocationNeededDialogFragment;

/**
 * Created by carlos on 2/6/17.
 *
 */

public class PermissionUtils {

    /**
     * Requests the fine location permission. If a rationale with an additional explanation should
     * be shown to the user, displays a dialog that triggers the request.
     */
    public static void requestPermission(AppCompatActivity activity, int requestId,
                                         String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Display a dialog with rationale.
            Log.e("VEAMOS 1111","VEAMOS 1111");
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
        } else {
            Log.e("VEAMOS 2222","VEAMOS 2222");
            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
//            LocationNeededDialogFragment.newInstance(requestId)
//                    .show(activity.getSupportFragmentManager(), "dialog");
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
