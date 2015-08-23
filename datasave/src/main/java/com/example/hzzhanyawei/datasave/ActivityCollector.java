package com.example.hzzhanyawei.datasave;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzzhanyawei on 2015/8/21.
 * Email hzzhanyawei@corp.netease.com
 */
public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
