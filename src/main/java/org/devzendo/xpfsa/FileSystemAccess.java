/**
 * Copyright (C) 2008-2011 Matt Gumbley, DevZendo.org <http://devzendo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.devzendo.xpfsa;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.devzendo.commoncode.os.OSTypeDetect;
import org.devzendo.commoncode.os.OSTypeDetect.OSType;
import org.devzendo.xpfsa.impl.DetailedFileProvider;
import org.devzendo.xpfsa.impl.MacOSXDetailedFileProvider;
import org.devzendo.xpfsa.impl.UnixDetailedFileProvider;
import org.devzendo.xpfsa.impl.WindowsDetailedFileProvider;

/**
 * The contructor for the FileSystemAccess library. Instantiate one of these
 * once to load the JNI library.
 *  
 * @author matt
 *
 */
public class FileSystemAccess {
    private static final Logger LOGGER = Logger
            .getLogger(FileSystemAccess.class);
    private final DetailedFileProvider mDetailedFileProvider;
    
    /**
     * Load the JNI Library.
     * @throws FileSystemAccessException 
     */
    public FileSystemAccess() throws FileSystemAccessException {
        NarSystem.loadLibrary();
        mDetailedFileProvider = createProvider(OSTypeDetect.getInstance().getOSType());
    }
    
    private DetailedFileProvider createProvider(final OSType type) throws FileSystemAccessException {
        switch (type) {
            case Linux:
            case Solaris:
                return new UnixDetailedFileProvider();
            case MacOSX:
                return new MacOSXDetailedFileProvider();
            case Windows:
                return new WindowsDetailedFileProvider();
            default:
                    throw new FileSystemAccessException("Operating system not supported");
        }
    }
    
    /**
     * Called by JNI code.
     * @param message the message to output.
     */
    @SuppressWarnings("unused")
    private void logDebug(final String message) {
        LOGGER.debug(message);
    }

    /**
     * Called by unit test to log a String back to log4j.
     * 
     * @param message the test message.
     */
    final native void logDebugNative(final String message);
    
    /**
     * Called by unit test to throw an exception back.
     * @param message the text of the exception
     * 
     * @throws FileSystemAccessException the exception that's thrown
     */
    final native void throwFileSystemAccessExceptionNative(final String message) throws FileSystemAccessException;
    
    /**
     * Obtain detailed information about a file.
     * 
     * @param file a file or directory (or socket, special file, etc.) which
     * must exist. A FileNotFoundException will be thrown if it does not.
     * @return A DetailedFile object which can be further interrogated for
     * platform/filesystem-specific details.
     * @throws IOException on error, typically a FileNotFoundException.
     */
    final DetailedFile getDetailedFile(final File file) throws FileSystemAccessException {
        return mDetailedFileProvider.getDetailedFile(file.getAbsolutePath());
    }
    
    /**
     * Obtain an Iterator for accessing the detailed information about files
     * in a directory. Note that this method does not descend into
     * subdirectories.
     * 
     * @param directory a directory which must exist. A FileNotFoundException
     * will be thrown if it does not.
     * @return An Iterator<DetailedFile> object which can be used to list the
     * directory.
     * @throws IOException on error, typically a FileNotFoundException.
     */
    final Iterator<DetailedFile> getDirectoryIterator(final File directory) throws FileSystemAccessException {
        return mDetailedFileProvider.getDirectoryIterator(directory.getAbsolutePath());
    }
}
