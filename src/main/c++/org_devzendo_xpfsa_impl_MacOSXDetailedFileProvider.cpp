#include <stdio.h>
#include <stdlib.h>

#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "JNIUtils.h"
#include "org_devzendo_xpfsa_FileSystemAccess.h"
#include "org_devzendo_xpfsa_impl_UnixDetailedFileProvider.h"
#include "org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_getDetailedFile
  (JNIEnv *env, jobject obj, jstring absolutePath)
{
	return NULL;
}

JNIEXPORT jobject JNICALL Java_org_devzendo_xpfsa_impl_MacOSXDetailedFileProvider_getDirectoryIterator
  (JNIEnv *env, jobject obj, jstring absolutePath)
{
	return NULL;
}


#ifdef __cplusplus
}
#endif

