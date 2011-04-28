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
    
    boolean isDirectory() throws FileSystemAccessException;

    FileStatus getFileStatus() throws FileSystemAccessException;

    boolean isSymbolicLink() throws FileSystemAccessException;
    FileStatus getLinkFileStatus() throws FileSystemAccessException;
}
