/**
 * Copyright (C) 2008-2010 Matt Gumbley, DevZendo.org <http://devzendo.org>
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

import org.apache.log4j.Logger;

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
    
    /**
     * Load the JNI Library.
     */
    public FileSystemAccess() {
        NarSystem.loadLibrary();
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
}
