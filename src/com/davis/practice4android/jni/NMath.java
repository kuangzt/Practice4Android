package com.davis.practice4android.jni;

import android.util.Log;

public class NMath {

	public native int add(int a,int b);
	
	public native int sub(int a,int b);
	
	static{
		System.loadLibrary("nmath");
	}
	
	public void callCount(int count){
		Log.e("EXAMPLE", ""+count);
	}
}
