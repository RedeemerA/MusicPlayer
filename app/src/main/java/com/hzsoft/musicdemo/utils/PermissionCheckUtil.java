package com.hzsoft.musicdemo.utils;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Describe:
 * <p>权限检查</p>
 *
 * @author zhouhuan
 * @Date 2020/11/20
 */
public class PermissionCheckUtil {
    /**
     * 检查定位，存储权限
     *
     * @param activity
     */
    public static void checkLocation(FragmentActivity activity) {
        new RxPermissions(activity).request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);


                    ToastUtil.showToast("缺少定位权限、存储权限，这会导致地图、导航、拍照等部分功能无法使用");
                }
            }
        });
    }

    /**
     * 检查网络权限
     *
     * @param activity
     */
    public static void checkInternet(FragmentActivity activity) {
        new RxPermissions(activity).request(
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 103);
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 104);
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 105);

                    ToastUtil.showToast("缺少网络权限");
                }
            }
        });
    }


    public static void checkPermission(Activity activity) {//开启本地的照片读取与写入权限
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,//读内存权限
                WRITE_EXTERNAL_STORAGE};//写内存权限

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }

            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE},1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
