#include <jni.h>
#include <jni_md.h>

#include "RefCache.h"
#include "GlobalRefCache.h"

// Cache of frequently-used class references
classRef classRefCache[6] = {
	{ NULL, "org/devzendo/xpfsa/impl/MacOSXDetailedFileProvider$MacOSXDetailedFileImpl" },
	{ NULL, "org/devzendo/xpfsa/impl/MacOSXDetailedFileProvider$MacOSXDirectoryIterator" },
	{ NULL, "org/devzendo/xpfsa/impl/MacOSXFileStatusImpl" },
	{ NULL, "org/devzendo/xpfsa/FileSystemAccessException" },
	{ NULL, "org/devzendo/xpfsa/DefaultFileSystemAccess" },
	{ NULL, NULL }, // END OF CACHE MARKER
};

// Cache of frequently-used method references
methodRef methodRefCache[4] = {
	{ NULL, MacOSXFileStatusImpl, "<init>", "(JJIIIIJJIJIII)V" },
	{ NULL, MacOSXDetailedFileImpl, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V" },
	{ NULL, FileSystemAccessException, "<init>", "(Ljava/lang/String;)V" },
	{ NULL, 0, NULL, NULL }, // END OF CACHE MARKER
};

// Cache of frequently-used static method references
staticMethodRef staticMethodRefCache[2] = {
	{ NULL, DefaultFileSystemAccess, "logDebug", "(Ljava/lang/String;)V" },
	{ NULL, 0, NULL, NULL }, // END OF CACHE MARKER
};

// Cache of frequently-used field reference
fieldRef fieldRefCache[3] = {
	{ NULL, MacOSXDetailedFileImpl, "mAbsolutePath", "Ljava/lang/String;" },
	{ NULL, MacOSXDirectoryIterator, "mAbsolutePath", "Ljava/lang/String;" },
	{ NULL, 0, NULL, NULL } // END OF CACHE MARKER
};

RefCache globalCache(classRefCache, methodRefCache, staticMethodRefCache, fieldRefCache);

