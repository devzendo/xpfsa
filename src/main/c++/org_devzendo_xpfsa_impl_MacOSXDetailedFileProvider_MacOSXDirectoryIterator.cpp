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
#include "org_devzendo_xpfsa_DefaultFileSystemAccess.h"
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

jobject direntToDetailedFile(JNIEnv *env, jobject obj, struct dirent *buf) {
	jobject detailedFile = NULL;
	jfieldID absPathFieldID = fieldRefCache[MacOSXDirectoryIterator_mAbsolutePath].fieldID;
	jstring jstrAbsPath = (jstring) env->GetObjectField(obj, absPathFieldID);
	const char *szAbsPath = env->GetStringUTFChars(jstrAbsPath, NULL);
	if (szAbsPath != NULL) {
		//logDebug(env, "Creating a UNIXFileStatus");
		jstring jstrDirEntName = env->NewStringUTF(buf->d_name);
		if (jstrDirEntName != NULL) {
		    detailedFile = env->NewObject(classRefCache[MacOSXDetailedFileImpl].classID,
				methodRefCache[MacOSXDetailedFileImpl_CTOR].methodID,
				jstrAbsPath, jstrDirEntName);
			env->DeleteLocalRef(jstrDirEntName);
		}
		env->ReleaseStringUTFChars(jstrAbsPath, szAbsPath);
	}
	return detailedFile;
}


JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDirectoryIterator_readdir
  (JNIEnv *env, jobject obj, jlong dirp)
{
	struct dirent entry;
	struct dirent *direntp;
	int ret = readdir_r((DIR *) dirp, &entry, &direntp);
    if (ret != 0) {
    	// failure
    	logDebug(env, "readdir failed");
		throwFileSystemAccessException(env, strerror(ret)); // not errno? TODO should be runtime exception?
    } else {
    	if (direntp != NULL) {
    		return direntToDetailedFile(env, obj, direntp);
    	}
		// end of directory
    }
    // end of directory or failure
    closedir((DIR *) dirp);
	return NULL;
}

#ifdef __cplusplus
}
#endif
