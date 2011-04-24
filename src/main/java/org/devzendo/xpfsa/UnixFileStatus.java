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

public interface UnixFileStatus extends FileStatus {
    public static final int S_IFMT = 0170000; //   bitmask for the file type bitfields
    public static final int S_IFSOCK = 0140000; //   socket
    public static final int S_IFLNK = 0120000; //   symbolic link
    public static final int S_IFREG = 0100000; //   regular file
    public static final int S_IFBLK = 0060000; //   block device
    public static final int S_IFDIR = 0040000; //   directory
    public static final int S_IFCHR = 0020000; //   character device
    public static final int S_IFIFO = 0010000; //   FIFO
    public static final int S_ISUID = 0004000; //   set UID bit
    public static final int S_ISGID = 0002000; //   set-group-ID bit (see below)
    public static final int S_ISVTX = 0001000; //   sticky bit (see below)
    public static final int S_IRWXU = 00700; //   mask for file owner permissions
    public static final int S_IRUSR = 00400; //   owner has read permission
    public static final int S_IWUSR = 00200; //   owner has write permission
    public static final int S_IXUSR = 00100; //   owner has execute permission
    public static final int S_IRWXG = 00070; //   mask for group permissions
    public static final int S_IRGRP = 00040; //   group has read permission
    public static final int S_IWGRP = 00020; //   group has write permission
    public static final int S_IXGRP = 00010; //   group has execute permission
    public static final int S_IRWXO = 00007; //   mask for permissions for others (not in group)
    public static final int S_IROTH = 00004; //   others have read permission
    public static final int S_IWOTH = 00002; //   others have write permission
    public static final int S_IXOTH = 00001; //   others have execute permission

    boolean isSocket();

    boolean isSymbolicLink();

    boolean isRegularFile();

    boolean isBlockSpecialFile();

    boolean isDirectory();

    boolean isCharacterSpecialFile();

    boolean isFIFO();

    boolean isSetUID();

    boolean isSetGID();

    boolean isSticky();

    int getPermissions();

    String toString();

    long getDeviceID();

    long getInodeNumber();

    int getMode();

    int getNumberOfLinks();

    int getUserID();

    int getGroupID();

    long getSpecialDeviceID();

    long getSize();

    int getBlockSize();

    long getNumberOfBlocks();
}
