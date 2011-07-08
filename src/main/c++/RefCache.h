#ifndef __REFCACHE_H__
#define __REFCACHE_H__

// Class/Method/Field ID caching

// Cache of frequently-used class references
struct _classRef {
	jclass classID;
	const char *className;
};
typedef struct _classRef classRef;

// Cache of frequently-used method references
struct _methodRef {
	jmethodID methodID;
	int classIndex; // Would be const int if it weren't for VC6
	const char *methodName;
	const char *methodSignature;
};
typedef struct _methodRef methodRef;

// Cache of frequently-used static method references
struct _staticMethodRef {
	jmethodID methodID;
	int classIndex; // Would be const int if it weren't for VC6
	const char *methodName;
	const char *methodSignature;
};
typedef struct _staticMethodRef staticMethodRef;

// Cache of frequently-used field references
struct _fieldRef {
	jfieldID fieldID;
	int classIndex; // Would be const int if it weren't for VC6
	const char *fieldName;
	const char *fieldSignature;
};
typedef struct _fieldRef fieldRef;

class RefCache {
public:
	RefCache(classRef *cr, methodRef *mr, staticMethodRef *smr, fieldRef *fr) {
		classRefCache = cr;
		methodRefCache = mr;
		staticMethodRefCache = smr;
		fieldRefCache = fr;
	}
	jint setUp(JNIEnv *env);
	void tearDown(JNIEnv *env);

private:
	classRef *classRefCache;
	methodRef *methodRefCache;
	staticMethodRef *staticMethodRefCache;
	fieldRef *fieldRefCache;
};

#endif // __REFCACHE_H__
