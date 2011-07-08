#ifndef __GLOBALREFCACHE_H__
#define __GLOBALREFCACHE_H__

// Cache of frequently-used class references
extern classRef classRefCache[];
enum ClassNames {
	MacOSXDetailedFileImpl = 0,
	MacOSXDirectoryIterator,
	MacOSXFileStatusImpl,
	FileSystemAccessException,
	FileSystemAccess,
};

// Cache of frequently-used method references
extern methodRef methodRefCache[];
enum MethodNames {
	MacOSXFileStatusImpl_CTOR = 0,
	MacOSXDetailedFileImpl_CTOR,
	FileSystemAccessException_CTOR,
};

// Cache of frequently-used static method references
extern staticMethodRef staticMethodRefCache[];
enum StaticMethodNames {
	FileSystemAccess_logDebug,
};

// Cache of frequently-used field reference
extern fieldRef fieldRefCache[];
enum FieldNames {
	MacOSXDetailedFileImpl_mAbsolutePath = 0,
	MacOSXDirectoryIterator_mAbsolutePath,
};

extern RefCache globalCache;

#endif // __GLOBALREFCACHE_H__
