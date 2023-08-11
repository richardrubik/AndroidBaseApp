#include <jni.h>
#include "libavcodec/codec.h"

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("notesrecorder2");
//    }
//
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_notesrecorder2_Transcoder_convert3gpToMp3(JNIEnv *env, jclass clazz,
                                                           jobject input, jobject output) {
    // TODO: implement convert3gpToMp3()
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_notesrecorder2_Transcoder_test(JNIEnv *env, jclass clazz) {
    // TODO: implement test()
    char msg[] = "HelloJNI!";
    jstring result = env->NewStringUTF(msg); // C style string to Java String
    return result;
}