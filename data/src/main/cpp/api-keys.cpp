//
// Created by ahmed on 4/21/2020.
//
#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring JNICALL
Java_com_zedan_newsapp_data_internet_OkHttpNetwork_getApiKey(
        JNIEnv* env,
        jobject /* this */){
    std::string apiKey = "e1224082026c44df8b3547f4420d6fb1";
    return env->NewStringUTF(apiKey.c_str());
}

extern "C" JNIEXPORT jstring  JNICALL
Java_com_zedan_newsapp_data_internet_RetrofitClient_getBaseLink(
        JNIEnv* env,
        jobject /* this */){
    std::string apiKey = "https://newsapi.org/v2/";
    return env->NewStringUTF(apiKey.c_str());
}
