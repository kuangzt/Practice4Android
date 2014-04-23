package com.davis.practice4android.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


public class AppUtil {
	
	/**
	 * 获取当前Acitivty栈顶(正在显示的activity)类名
	 * @param context
	 * @return
	 */
	public static String getTopActivityClassName(Context context)
    {
        try {
            ActivityManager am = (ActivityManager) context
            .getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            return cn.getClassName();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Get the App PackageName
	 * @param context
	 * @return packeagename
	 */
     public static String getAppPackageName(Context context)
     {
    	 return context.getPackageName();
     }
     
     /**
      * Get the App VersionName
      * @param context
      * @return versionname
      */
     public static String getAppVersionName(Context context)
     {
    	 try {
			PackageInfo info = context.getPackageManager().getPackageInfo(getAppPackageName(context), 0);
			return info.versionName;
    	 } catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return null;
     }
     
     /**
      * Get the App versioncode
      * @param context
      * @return if return == -1 means errors happen
      */
     public static int getAppVersionCode(Context context)
     {
    	 try {
 			PackageInfo info = context.getPackageManager().getPackageInfo(getAppPackageName(context), 0);
 			return info.versionCode;
     	 } catch (NameNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
     	 return -1;
     }
     
     	@SuppressLint("NewApi")
	public int[] getLcdSize(Context context) {
		int screenHeight = 0;
		int ver = Build.VERSION.SDK_INT;
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		display.getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		if (ver < 13) {
			screenHeight = metrics.heightPixels;
		} else if (ver == 13) {
			try {
				Method method = display.getClass().getMethod("getRealHeight");
				screenHeight = (Integer) method.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ver < 17) {
			try {
				Method method = display.getClass().getMethod("getRawHeight");
				screenHeight = (Integer) method.invoke(display);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
		{
			try {
				display.getRealMetrics(metrics);
				screenHeight = metrics.heightPixels;
		        String hardware = Build.HARDWARE;
		        if(null!=hardware){
		        	if(hardware.startsWith("tcc893")){
		                int orient =  getOrientation();
		        		if(orient == LY_LANDSCAPE)
		        		{
		        		}
		        		else
		        		{
		        			((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		        		}
		        	}
		        	
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new int[] { screenWidth, screenHeight };
	}
     
}
