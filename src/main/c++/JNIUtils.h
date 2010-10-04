#ifndef __JNIUTILS_H__
#define __JNIUTILS_H__

// JNI essentials
extern void logDebug(JNIEnv *env, const char *msg);
extern void throwStringMessageException(JNIEnv *env, const char *className, const char *message);
extern char *strToNative(JNIEnv *env, jstring jstr);

#endif // __JNIUTILS_H__
