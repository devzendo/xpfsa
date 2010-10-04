#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "GlobalRefCache.h"

// Cache of frequently-used class references
classRef classRefCache[3] = {
	{ NULL, "org/devzendo/xpfsa/FileSystemAccessException" },
	{ NULL, "org/devzendo/xpfsa/FileSystemAccess" },
	{ NULL, NULL }, // END OF CACHE MARKER
};

// Cache of frequently-used method references
methodRef methodRefCache[3] = {
	{ NULL, FileSystemAccessException, "<init>", "(Ljava/lang/String;)V" },
	{ NULL, FileSystemAccess, "logDebug", "(Ljava/lang/String;)V" },
	{ NULL, 0, NULL, NULL }, // END OF CACHE MARKER
};

// Cache of frequently-used field reference
fieldRef fieldRefCache[1] = {
	{ NULL, 0, NULL, NULL }, // END OF CACHE MARKER
};

RefCache globalCache(classRefCache, methodRefCache, fieldRefCache);

