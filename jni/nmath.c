#ifndef NELEM
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif
#include "com_davis_practice4android_jni_NMath.h"
#include <android/log.h>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include "debug.h"

static int count;
static const char *classPathName = "com/davis/practice4android/jni/NMath";

JNIEXPORT jint JNICALL Java_com_example_jni_NMath_add
  (JNIEnv * env, jobject j, jint a, jint b){
	LOGE("Method add The first para is %d,the second para %d",a,b);
    jclass clazz;
    clazz = (*env)->FindClass(env,classPathName);
    jmethodID callCountID = (*env)->GetMethodID(env,clazz, "callCount", "(I)V");
    (*env)->CallVoidMethod(env,j,callCountID,++count);
	return (a+b);
}
JNIEXPORT jint JNICALL Java_com_example_jni_NMath_sub
  (JNIEnv * env, jobject j, jint a, jint b){
	LOGE("Method sub The first para is %d,the second para %d",a,b);
    jclass clazz;
    clazz = (*env)->FindClass(env,classPathName);
    jmethodID callCountID = (*env)->GetMethodID(env,clazz, "callCount", "(I)V");
    (*env)->CallVoidMethod(env,j,callCountID,++count);
	return (a-b);
}

static JNINativeMethod methods[] = {
  {"add", "(II)I", (void*)Java_com_example_jni_NMath_add },
  {"sub", "(II)I", (void*)Java_com_example_jni_NMath_sub },
};

/*
 * Register several native methods for one class.
 */
static int registerNativeMethods(JNIEnv* env, const char* className,
    JNINativeMethod* gMethods, int numMethods)
{
    jclass clazz;

    clazz = (*env)->FindClass(env,className);
    if (clazz == NULL) {
        LOGE("Native registration unable to find class '%s'", className);
        return JNI_FALSE;
    }
    if ((*env)->RegisterNatives(env,clazz, gMethods, numMethods) < 0) {
        LOGE("RegisterNatives failed for '%s'", className);
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

static int registerNatives(JNIEnv* env)
{
  if (!registerNativeMethods(env, classPathName,
                 methods, NELEM(methods))) {
    return JNI_FALSE;
  }

  return JNI_TRUE;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    void *venv;
    LOGE("JNI_OnLoad!");
    if ((*vm)->GetEnv(vm, (void**)&venv, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: GetEnv failed");
        return -1;
    }
    if (registerNatives(venv) != JNI_TRUE) {
            LOGE("ERROR: registerNatives failed");
            return -1;
    }
    LOGE("JNI_VERSION_1_4 %d",JNI_VERSION_1_4);
    return JNI_VERSION_1_4;
}

void JNI_OnUnLoad(JavaVM* vm, void* reserved)
{
    LOGE("JNI_OnUnLoad!");
}
