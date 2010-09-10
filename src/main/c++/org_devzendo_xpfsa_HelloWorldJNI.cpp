#include <stdio.h>
//#include <jni.h>
//#include <jni_md.h>
#include "org_devzendo_xpfsa_HelloWorldJNI.h"

JNIEXPORT jstring JNICALL Java_org_devzendo_xpfsa_HelloWorldJNI_sayHello( JNIEnv *env, jobject obj ) {
	jstring value;           /* the return value */

	char buf[40];            /* working buffer (really only need 20 ) */


	sprintf(buf, "%s", "Hello NAR World!");

	value = env->NewStringUTF(buf);

	return value;
}
