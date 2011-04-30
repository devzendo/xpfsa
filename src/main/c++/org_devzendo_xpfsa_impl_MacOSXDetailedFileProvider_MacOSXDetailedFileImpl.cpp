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

jobject CreateUNIXFileStatus(JNIEnv *env, struct stat *buf) {
	//logDebug(env, "Creating a UNIXFileStatus");
	jobject obj = env->NewObject(classRefCache[MacOSXFileStatusImpl].classID,
		methodRefCache[MacOSXFileStatusImpl_CTOR].methodID,
		buf->st_dev, buf->st_ino, buf->st_mode,
		buf->st_nlink, buf->st_uid, buf->st_gid,
		buf->st_rdev, buf->st_size,
		buf->st_blksize, buf->st_blocks,
		buf->st_atime, buf->st_mtime, buf->st_ctime);
	return obj;
}

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_00024MacOSXDetailedFileImpl_getFileStatus
  (JNIEnv *env, jobject obj)
{
	jobject result = NULL;
	jfieldID absPathFieldID = fieldRefCache[MacOSXDetailedFileImpl_mAbsolutePath].fieldID;
	jstring jstrAbsPath = (jstring) env->GetObjectField(obj, absPathFieldID);
	const char *szAbsPath = env->GetStringUTFChars(jstrAbsPath, NULL);
	if (szAbsPath != NULL) {
		//char msg[512];
		struct stat statBuf;
		//logDebug(env, szAbsPath);
		if (stat(szAbsPath, &statBuf) == -1) {
			//logDebug(env, "stat failed");
			throwFileSystemAccessException(env, strerror(errno));
		} else {
			//sprintf(msg, "dev %lld, ino %lld, mode 0%o, nlink %d, uid %d, gid %d, rdev %lld, size %lld, blksize %d, blocks %lld, atime %d mtime %d ctime %d",
			//statBuf.st_dev, statBuf.st_ino, statBuf.st_mode, statBuf.st_nlink,
			//statBuf.st_uid, statBuf.st_gid, statBuf.st_rdev, statBuf.st_size,
			//statBuf.st_blksize, statBuf.st_blocks, statBuf.st_atime,
			//statBuf.st_mtime, statBuf.st_ctime);
			//logDebug(env, msg);
			//hexdump(env, (unsigned char *)&statBuf, sizeof (statBuf));
			result = CreateUNIXFileStatus(env, &statBuf);
		}
		env->ReleaseStringUTFChars(jstrAbsPath, szAbsPath);
	}
	return result;
}

#ifdef __cplusplus
}
#endif

