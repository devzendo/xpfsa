/**
 * Copyright (C) 2008-2011 Matt Gumbley, DevZendo.org <http://devzendo.org>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.devzendo.xpfsa;

/**
 * An Exception thrown by the native code in this project.
 * 
 * @author matt
 *
 */
@SuppressWarnings("serial")
public class FileSystemAccessException extends Exception {
    /**
     * An exception with no detail
     * 
     */
    public FileSystemAccessException() {
        super();
    }

    /**
     * An exception thrown by this layer - this is the only one in use by JNI
     * 
     * @param message
     *        the detail
     */
    public FileSystemAccessException(final String message) {
        super(message);
    }

    /**
     * 
     * @param message
     *        detail of the problem
     * @param cause
     *        the cause, for exception chaining
     */
    public FileSystemAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param cause
     *        the cause, for exception chaining
     */
    public FileSystemAccessException(final Throwable cause) {
        super(cause);
    }
}
