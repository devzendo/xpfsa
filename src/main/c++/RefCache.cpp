#include <jni.h>
#include <jni_md.h>
#include <stdio.h>

#include "RefCache.h"

jint RefCache::setUp(JNIEnv *env) {
	jclass classID;
#ifdef DEBUG
	printf("start\n");
#endif

	int i=0;
	while (classRefCache[i].className != NULL) {
#ifdef DEBUG
		printf("class %s\n", classRefCache[i].className);
#endif
		classID = env->FindClass(classRefCache[i].className);
		if (classID == NULL)
			return JNI_ERR;
		classRefCache[i].classID = (jclass)env->NewWeakGlobalRef(classID);
		i++;
	}
	i=0;
	while (methodRefCache[i].methodName != NULL) {
#ifdef DEBUG
		printf("method %s\n", methodRefCache[i].methodName);
#endif
		classID = classRefCache[methodRefCache[i].classIndex].classID;
		methodRefCache[i].methodID = env->GetMethodID(classID, methodRefCache[i].methodName, methodRefCache[i].methodSignature);
		if (methodRefCache[i].methodID == NULL) {
			printf("no instance method %s found\n", methodRefCache[i].methodName);
			return JNI_ERR;
		}
		i++;
	}
	i=0;
	while (staticMethodRefCache[i].methodName != NULL) {
#ifdef DEBUG
		printf("static method %s\n", staticMethodRefCache[i].methodName);
#endif
		classID = classRefCache[staticMethodRefCache[i].classIndex].classID;
		staticMethodRefCache[i].methodID = env->GetStaticMethodID(classID, staticMethodRefCache[i].methodName, staticMethodRefCache[i].methodSignature);
		if (staticMethodRefCache[i].methodID == NULL) {
			printf("no static method %s found\n", staticMethodRefCache[i].methodName);
			return JNI_ERR;
		}
		i++;
	}
	i=0;
	while (fieldRefCache[i].fieldName != NULL) {
#ifdef DEBUG
		printf("field %s\n", fieldRefCache[i].fieldName);
#endif
		classID = classRefCache[fieldRefCache[i].classIndex].classID;
		fieldRefCache[i].fieldID = env->GetFieldID(classID, fieldRefCache[i].fieldName, fieldRefCache[i].fieldSignature);
		if (fieldRefCache[i].fieldID == NULL) {
			printf("no field %s found\n", fieldRefCache[i].fieldName);
			return JNI_ERR;
		}
		i++;
	}
#ifdef DEBUG
	printf("done\n");
#endif
	return JNI_VERSION_1_2;
}

void RefCache::tearDown(JNIEnv *env) {
	int i=0;
	while (classRefCache[i].className != NULL) {
		if (classRefCache[i].classID != NULL) {
			env->DeleteWeakGlobalRef(classRefCache[i].classID);
			classRefCache[i].classID = NULL;
		}
		i++;
	}
}
