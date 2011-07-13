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
import java.util.Iterator;

import org.devzendo.commoncode.string.StringUtils;
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
    public Iterator<DetailedFile> getDirectoryIterator(final String absolutePath)
            throws FileSystemAccessException {
        return new MacOSXDirectoryIterator(absolutePath);
    }

    private static class MacOSXDirectoryIterator implements Iterator<DetailedFile> {
        private final String mAbsolutePath; // Accessed via JNI

        private final long mOpenDirDescriptor;

        private DetailedFile nextDetailedFile = null;

        public MacOSXDirectoryIterator(final String absolutePath)
                throws FileSystemAccessException {
            mAbsolutePath = absolutePath;
            mOpenDirDescriptor = opendir();
        }

        /**
         * open dir directory given by mAbsolutePath
         * 
         * @return a DIR *, cast to a long
         * @throws FileSystemAccessException
         *         if the opendir fails
         */
        private native long opendir() throws FileSystemAccessException;

        /**
         * @param dirPointer
         *        a DIR *, as obtained by opendir
         * @return a DetailedFile, containing a File with parent being
         *         absolutePath, and the file being the current file read by
         *         readdir. Will return null if the last entry in the directory
         *         has been read. In this case, the DIR * will be closed
         *         automatically.
         */
        private native DetailedFile readdir(long dirPointer);

        @Override
        public boolean hasNext() {
            nextDetailedFile = readdir(mOpenDirDescriptor);
            return nextDetailedFile != null;
        }

        @Override
        public DetailedFile next() {
            return nextDetailedFile;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Cannot remove from a directory iterator");
        }
    }

    private static class MacOSXDetailedFileImpl implements MacOSXDetailedFile {
        private final String mAbsolutePath; // Accessed via JNI

        public MacOSXDetailedFileImpl(final String absolutePath) {
            mAbsolutePath = absolutePath;
        }

        public MacOSXDetailedFileImpl(final String directoryPath,
                final String fileName) {
            mAbsolutePath = joinWithFileSeparator(directoryPath, fileName);
        }

        private String joinWithFileSeparator(
                final String directoryPath,
                final String fileName) {
            final StringBuilder sb = new StringBuilder(
                    StringUtils.unSlashTerminate(directoryPath));
            sb.append(File.separatorChar);
            sb.append(unslashPrefix(fileName));
            return sb.toString();
        }

        private String unslashPrefix(final String fileName) {
            final StringBuilder sb = new StringBuilder(fileName);
            while (sb.length() != 0 && sb.charAt(0) == File.separatorChar) {
                sb.deleteCharAt(0);
            }
            return sb.toString();
        }

        @Override
        public File getFile() {
            return new File(mAbsolutePath);
        }

        @Override
        public native FileStatus getFileStatus()
                throws FileSystemAccessException;

        @Override
        public DetailedFile getLinkDetailedFile()
                throws FileSystemAccessException {
            return getLinkDetailedFile(new File(mAbsolutePath).getParent());
        }
        public native DetailedFile getLinkDetailedFile(final String parentPath)
            throws FileSystemAccessException;
    }
}
