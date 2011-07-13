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

/**
 * A DetailedFile is constructed by the FileSystemAccess.getDetailedFile(...)
 * methods, or by the FileSystemAccess.getDirectoryIterator(...) / next()
 * iterator.
 * 
 * It provides access to detailed information about files, such as permissions,
 * ownership, symbolic link details, ACLs. This base interface provides the
 * essential details of files, and there are several subinterfaces
 * of it for specific operating systems and filesystems.
 * 
 * @author matt
 *
 */
public interface DetailedFile {
    File getFile();

    /**
     * Obtain status information about a file.
     * <p>
     * Symbolic link behaviour:
     * If the file is a symbolic link, details of the link are returned. If 
     * isSymbolicLink() is true, the DetailedFile pointed to by the link can be
     * obtained using the getLinkDetailedFile() method.
     * 
     * @param file a file or directory (or socket, special file, etc.) which
     * must exist. A FileNotFoundException will be thrown if it does not.
     * @return A FileStatus object which can be further interrogated for
     * platform/filesystem-specific details.
     * @throws IOException on error, typically a FileNotFoundException.
     */
    FileStatus getFileStatus() throws FileSystemAccessException;

    /**
     * Obtain the name of the file pointed to by this DetailedFile, since it is
     * a symbolic link.
     * 
     * @return A DetailedFile describing the file pointed to by this
     * DetailedFile's symbolic link. Note that the name of this may not 
     * be absolute: it contains the exact contents of the link.
     * @throws FileSystemAccessException if this DetailedFile is not a symbolic
     * link, or some other error occurs.
     */
    DetailedFile getLinkDetailedFile() throws FileSystemAccessException;
}
