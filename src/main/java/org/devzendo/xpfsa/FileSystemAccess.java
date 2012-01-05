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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public interface FileSystemAccess {
    /**
     * Obtain detailed information about a file.
     * <p>
     * Symbolic link behaviour:
     * If the file is a symbolic link, details of the link are returned. The
     * FileStatus can be obtained to determine whether this is a link. If so,
     * the DetailedFile pointed to by the link can be obtained using the
     * DetailedFile's getLinkFileStatus() method.
     * 
     * @param file a file or directory (or socket, special file, etc.) which
     * must exist. A FileNotFoundException will be thrown if it does not.
     * @return A DetailedFile object which can be further interrogated for
     * platform/filesystem-specific details.
     * @throws IOException on error, typically a FileNotFoundException.
     */
    DetailedFile getDetailedFile(final File file) throws FileSystemAccessException;
    
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
    Iterator<DetailedFile> getDirectoryIterator(final File directory) throws FileSystemAccessException;
}