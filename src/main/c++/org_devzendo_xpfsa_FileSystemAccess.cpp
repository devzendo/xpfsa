#include <stdio.h>
#include <stdlib.h>

#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "JNIUtils.h"
#include "org_devzendo_xpfsa_FileSystemAccess.h"

#ifdef __cplusplus
extern "C" {
#endif


// Called by unit test to test logging back out through JVM and log4j
JNIEXPORT void JNICALL Java_org_devzendo_xpfsa_FileSystemAccess_logDebugNative
  (JNIEnv *env, jobject obj, jstring message)
{
	char * msgChars = strToNative(env, message);
	if (msgChars != NULL) {
		logDebug(env, msgChars);
		free(msgChars);
	}
}

// Called by unit test to test throwing of exception back out through JVM
JNIEXPORT void JNICALL Java_org_devzendo_xpfsa_FileSystemAccess_throwFileSystemAccessExceptionNative
  (JNIEnv *env, jobject obj, jstring message)
{
	char * msgChars = strToNative(env, message);
	if (msgChars != NULL) {
		throwFileSystemAccessException(env, msgChars);
		free(msgChars);
	}

}

#ifdef __cplusplus
}
#endif

