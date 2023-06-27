package com.treppenwitz.recorder;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class PermissionUtility {

    public static boolean isApplicationPermitted(Context context) {
        int micPermitted = ContextCompat.checkSelfPermission(context, RECORD_AUDIO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            boolean filePermitted = Environment.isExternalStorageManager();

            return filePermitted && micPermitted == PackageManager.PERMISSION_GRANTED;
        } else {
            int readPermitted = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
            int writePermitted = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);

            return readPermitted == PackageManager.PERMISSION_GRANTED && writePermitted == PackageManager.PERMISSION_GRANTED
                    && micPermitted == PackageManager.PERMISSION_GRANTED;
        }
    }
    public static void requestPermissions(MainActivity activity) {
        String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, RECORD_AUDIO};
        ActivityResultLauncher<Intent> activityResultLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                if (Environment.isExternalStorageManager()) {
                                    Snackbar.make(activity, activity.content_view, "Permission Granted", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(activity, activity.content_view, "Permission Denied", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", new Object[]{ activity.getPackageName() })));
                activityResultLauncher.launch(intent);
            } catch (Exception exception) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activityResultLauncher.launch(intent);
            }

            ActivityCompat.requestPermissions(activity, new String[] {RECORD_AUDIO}, 40);
        } else {
            ActivityCompat.requestPermissions(activity, permissions, 30);
        }
    }
}
