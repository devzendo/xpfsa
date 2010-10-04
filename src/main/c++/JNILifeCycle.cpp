#include <stdio.h>
#include <stdlib.h>

#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "GlobalRefCache.h"

//------------------------------------------------------------------------------
// Load and unload
//------------------------------------------------------------------------------

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	JNIEnv *env;
	if (vm->GetEnv((void **) &env, JNI_VERSION_1_2) != JNI_OK)
		return JNI_ERR;
	jint ret = globalCache.setUp(env);
	return ret;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
	JNIEnv *env;

	if (vm->GetEnv((void **) &env, JNI_VERSION_1_2) == JNI_OK) {
		globalCache.tearDown(env);
    }
}
