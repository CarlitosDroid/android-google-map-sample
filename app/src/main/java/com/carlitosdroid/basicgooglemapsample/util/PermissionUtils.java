package com.carlitosdroid.basicgooglemapsample.util;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
                                         String permission, boolean finishActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Display a dialog with rationale.
            Log.e("REQUESTP RATIONALE","RATIONALES");

//            PermissionUtils.RationaleDialog.newInstance(requestId, finishActivity)
//                    .show(activity.getSupportFragmentManager(), "dialog");
        } else {
            Log.e("NO RATIONALE","NO RATIONALE");

            // Location permission has not been granted yet, request it.
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);

        }
    }

}
