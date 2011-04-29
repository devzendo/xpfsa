#include <jni.h>
#include <jni_md.h>

#ifdef Darwin
#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <dirent.h>
#endif

#include "RefCache.h"
#include "GlobalRefCache.h"
#include "JNIUtils.h"
#include "org_devzendo_xpfsa_FileSystemAccess.h"
#include "org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_MacOSXDirectoryIterator.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDirectoryIterator_opendir
  (JNIEnv *env, jobject obj)
{
	DIR *dirp = NULL;
	jfieldID absPathFieldID = fieldRefCache[MacOSXDirectoryIterator_mAbsolutePath].fieldID;
	jstring jstrAbsPath = (jstring) env->GetObjectField(obj, absPathFieldID);
	const char *szAbsPath = env->GetStringUTFChars(jstrAbsPath, NULL);
	if (szAbsPath != NULL) {
		dirp = opendir(szAbsPath);
		if (dirp == NULL) {
			//logDebug(env, "opendir failed");
			throwFileSystemAccessException(env, strerror(errno));
		}
		env->ReleaseStringUTFChars(jstrAbsPath, szAbsPath);
	}
	return (jlong) dirp;
}

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDirectoryIterator_readdir
  (JNIEnv *env, jobject obj, jlong dirp)
{
	return NULL;
}


#ifdef __cplusplus
}
#endif
