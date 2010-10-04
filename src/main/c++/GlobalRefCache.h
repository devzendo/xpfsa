#ifndef __GLOBALREFCACHE_H__
#define __GLOBALREFCACHE_H__

// Cache of frequently-used class references
extern classRef classRefCache[];
enum ClassNames {
	FileSystemAccessException = 0,
	FileSystemAccess,
};

// Cache of frequently-used method references
extern methodRef methodRefCache[];
enum MethodNames {
	FileSystemAccessException_CTOR = 0,
	FileSystemAccess_logDebug,
};

// Cache of frequently-used field reference
extern fieldRef fieldRefCache[];
enum FieldNames {
};

extern RefCache globalCache;

#endif // __GLOBALREFCACHE_H__
