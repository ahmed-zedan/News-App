#include <jni.h>
#include <string>
using namespace std;

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_zedan_newsapp_MainActivity_stringFromJNI(
//        JNIEnv* env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

extern "C" JNIEXPORT jstring JNICALL
Java_com_zedan_newsapp_util_Preference_sharedPrefName(
        JNIEnv* env,
        jobject /* this */){
    string sharedPrefName = "hello_world_com.zedan.news_app.name_shared_pref";
    return env->NewStringUTF(sharedPrefName.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_zedan_newsapp_util_Preference_getKeys(
        JNIEnv* env,
        jobject /* this */,
        jint code
        ){
    string key = "null";
    if (code == 1) key = "zedan_news_app_language_key";
    return env->NewStringUTF(key.c_str());
}

