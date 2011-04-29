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
package org.devzendo.xpfsa.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.devzendo.xpfsa.DetailedFile;
import org.devzendo.xpfsa.FileStatus;
import org.devzendo.xpfsa.FileSystemAccessException;
import org.devzendo.xpfsa.MacOSXDetailedFile;

public class MacOSXDetailedFileProvider implements DetailedFileProvider {
    @Override
    public DetailedFile getDetailedFile(final String absolutePath) {
        return new MacOSXDetailedFileImpl(absolutePath);
    }

    @Override
    public Iterator<DetailedFile> getDirectoryIterator(final String absolutePath) throws FileSystemAccessException {
        return null;
    }

    private class MacOSXDetailedFileImpl implements MacOSXDetailedFile {
        private final String mAbsolutePath; // Accessed via JNI

        public MacOSXDetailedFileImpl(final String absolutePath) {
            mAbsolutePath = absolutePath;
        }

        @Override
        public File getFile() {
            return new File(mAbsolutePath);
        }

        @Override
        public native FileStatus getFileStatus() throws FileSystemAccessException;

        @Override
        public FileStatus getLinkFileStatus() throws FileSystemAccessException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isDirectory() throws FileSystemAccessException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isSymbolicLink() throws FileSystemAccessException {
            // TODO Auto-generated method stub
            return false;
        }

    }
}
