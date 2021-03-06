#include <stdio.h>
#include <stdlib.h>

#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "GlobalRefCache.h"
#include "JNIUtils.h"

//------------------------------------------------------------------------------
// JNI essentials
//------------------------------------------------------------------------------

void throwStringMessageException(JNIEnv *env, const char *className,
		const char *message) {
	jclass classID = env->FindClass(className);
	if (classID != NULL) {
		env->ThrowNew(classID, message);
		env->DeleteLocalRef(classID);
	}
}

extern void throwFileSystemAccessException(JNIEnv *env, const char *msg) {
	jstring msgString = env->NewStringUTF(msg);

	if (msgString != NULL) {
		jthrowable exception = (jthrowable)env->NewObject(
				classRefCache[FileSystemAccessException].classID,
				methodRefCache[FileSystemAccessException_CTOR].methodID,
				msgString);

		if (exception != NULL) {
			env->Throw(exception);
			env->DeleteLocalRef(exception);
		}
		env->DeleteLocalRef(msgString);
	}
}

char *strToNative(JNIEnv *env, jstring jstr) {
	jint len = env->GetStringUTFLength(jstr);
	char *result = (char *) malloc(len + 1);
	if (result == NULL) {
		throwStringMessageException(env, "java/lang/OutOfMemoryError",
				"Could not allocate memory for String copy");
		return NULL;
	}
	env->GetStringUTFRegion(jstr, 0, len, result);
	return result;
}

void logDebug(JNIEnv *env, const char *msg) {
	jstring msgString = env->NewStringUTF(msg);
	jclass classID = classRefCache[DefaultFileSystemAccess].classID;
	jmethodID methodID = staticMethodRefCache[DefaultFileSystemAccess_logDebug].methodID;
	if (methodID == NULL) {
		env->DeleteLocalRef(msgString);
		return;
	}
	env->CallStaticVoidMethod(classID, methodID, msgString);
	env->DeleteLocalRef(msgString);
}
