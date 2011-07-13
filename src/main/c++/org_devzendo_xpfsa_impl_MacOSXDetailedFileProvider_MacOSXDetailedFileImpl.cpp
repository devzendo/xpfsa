#include <stdio.h>
#include <stdlib.h>

#include <jni.h>
#include <jni_md.h>

#ifdef Darwin

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/statvfs.h>
#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <limits.h>
#endif

#include "RefCache.h"
#include "GlobalRefCache.h"
#include "JNIUtils.h"
#include "org_devzendo_xpfsa_FileSystemAccess.h"
#include "org_devzendo_xpfsa_impl_UnixDetailedFileProvider.h"
#include "org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_MacOSXDetailedFileImpl.h"

#ifdef __cplusplus
extern "C" {
#endif

jobject CreateMacOSXFileStatus(JNIEnv *env, struct stat *buf) {
	jobject obj = env->NewObject(classRefCache[MacOSXFileStatusImpl].classID,
		methodRefCache[MacOSXFileStatusImpl_CTOR].methodID,
		buf->st_dev, buf->st_ino, buf->st_mode,
		buf->st_nlink, buf->st_uid, buf->st_gid,
		buf->st_rdev, buf->st_size,
		buf->st_blksize, buf->st_blocks,
		buf->st_atime, buf->st_mtime, buf->st_ctime);
	return obj;
}

jobject CreateMacOSXDetailedFile(JNIEnv *env, jstring jstrParentPath, const char *linkNameBuf) {
	return env->NewObject(classRefCache[MacOSXDetailedFileImpl].classID,
		methodRefCache[MacOSXDetailedFileImpl_CTOR].methodID,
		jstrParentPath, env->NewStringUTF(linkNameBuf));
}

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDetailedFileImpl_getFileStatus
  (JNIEnv *env, jobject obj)
{
	jobject result = NULL;
	jfieldID absPathFieldID = fieldRefCache[MacOSXDetailedFileImpl_mAbsolutePath].fieldID;
	jstring jstrAbsPath = (jstring) env->GetObjectField(obj, absPathFieldID);
	const char *szAbsPath = env->GetStringUTFChars(jstrAbsPath, NULL);
	if (szAbsPath != NULL) {
		struct stat statBuf;
		if (lstat(szAbsPath, &statBuf) == -1) {
			throwFileSystemAccessException(env, strerror(errno));
		} else {
			result = CreateMacOSXFileStatus(env, &statBuf);
		}
		env->ReleaseStringUTFChars(jstrAbsPath, szAbsPath);
	}
	return result;
}

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDetailedFileImpl_getLinkDetailedFile
  (JNIEnv *env, jobject obj, jstring jstrParentPath)
{
	jobject result = NULL;
	jfieldID absPathFieldID = fieldRefCache[MacOSXDetailedFileImpl_mAbsolutePath].fieldID;
	jstring jstrAbsPath = (jstring) env->GetObjectField(obj, absPathFieldID);
	const char *szAbsPath = env->GetStringUTFChars(jstrAbsPath, NULL);

	if (szAbsPath != NULL) {
		char *readlinkBuf = (char *) malloc(NAME_MAX);
		if (readlinkBuf != NULL) {
			int linkLen = readlink(szAbsPath, readlinkBuf, NAME_MAX);
			if (linkLen != -1) {
				readlinkBuf[linkLen] = '\0';
				result = CreateMacOSXDetailedFile(env, jstrParentPath, readlinkBuf);
			} else {
				throwFileSystemAccessException(env, strerror(errno));
			}
			free(readlinkBuf);
		} else {
			throwStringMessageException(env, "java/lang/OutOfMemoryError", "Could not allocate buffer for readlink");
		}
		env->ReleaseStringUTFChars(jstrAbsPath, szAbsPath);
	}
	return result;
}

#ifdef __cplusplus
}
#endif

