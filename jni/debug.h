#ifndef _GOLD_DEBUG_H
#define _GOLD_DEBUG_H



#ifdef __cplusplus
#if __cplusplus
extern "C"{
#endif
#endif /* __cplusplus */

#define GI_DEBUG

#ifdef GI_DEBUG

#ifndef TAG
#define TAG "EXAMPLE"
#endif

#define GI_DEBUG_PRINTF(level,...) __android_log_print((level), TAG, ##__VA_ARGS__)
#define GI_DEBUG_POINT()   GI_DEBUG_PRINTF(ANDROID_LOG_DEBUG,"\n[File:%s Line:%d] Fun:%s\n", __FILE__, __LINE__, __FUNCTION__)

#define GI_ASSERT(expr)                                     \
    do{                                                     \
        if (!(expr)) { \
        	GI_DEBUG_PRINTF(ANDROID_LOG_ASSERT,"\nASSERT failed at:\n  >File name: %s\n  >Function : %s\n  >Line No. : %d\n  >Condition: %s\n", \
                    __FILE__,__FUNCTION__, __LINE__, #expr);\
        } \
    }while(0);

/*调试宏, 用于暂停*/
#define GI_DEBUG_PAUSE()           \
 do               \
 {               \
  GI_DEBUG_POINT();          \
  GI_DEBUG_PRINTF(ANDROID_LOG_DEBUG,"pause for debug, press 'q' to exit!\n");  \
  char c;             \
  while( ( c = getchar() ) )        \
   {             \
    if('q' == c)         \
     {           \
      getchar();        \
      break;         \
     }           \
   }             \
 }while(0);
#define GI_DEBUG_PAUSE_ARG(...)          \
  do               \
  {               \
   GI_DEBUG_PRINTF(ANDROID_LOG_DEBUG,##__VA_ARGS__);           \
   GI_DEBUG_PAUSE()          \
  }while(0);


#define GI_DEBUG_ASSERT(expression)      \
if(!(expression))                        \
{                                  \
	GI_DEBUG_PRINTF(ANDROID_LOG_DEBUG,"[ASSERT],%s,%s:%d\n", __FILE__,  __FUNCTION__, __LINE__);\
    exit(-1);             \
}
#define LOGV(...)   __android_log_print((ANDROID_LOG_VERBOSE), TAG, ##__VA_ARGS__)
#define LOGD(...)   __android_log_print((ANDROID_LOG_DEBUG), TAG, ##__VA_ARGS__)
#define LOGI(...)   __android_log_print((ANDROID_LOG_INFO), TAG, ##__VA_ARGS__)
#define LOGW(...)   __android_log_print((ANDROID_LOG_WARN), TAG, ##__VA_ARGS__)
#define LOGE(...)   __android_log_print((ANDROID_LOG_ERROR), TAG, ##__VA_ARGS__)
#define LOGA(...)   __android_log_print((ANDROID_LOG_ASSERT), TAG, ##__VA_ARGS__)
#else
#define GI_ASSERT(expr)
#define GI_DEBUG_PAUSE()
#define GI_DEBUG_PAUSE_ARG(...)
#define GI_DEBUG_POINT()
#define GI_DEBUG_PRINTF(level,...)
#define GI_DEBUG_ASSERT(expression)
#define LOGV(...)
#define LOGD(...)
#define LOGI(...)
#define LOGW(...)
#define LOGE(...)
#define LOGA(...)

#endif

#ifdef __cplusplus
#if __cplusplus
}
#endif
#endif /* __cplusplus */


#endif
