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
package org.devzendo.xpfsa.impl;

import org.devzendo.xpfsa.UnixFileStatus;

public class UnixFileStatusImpl implements UnixFileStatus {
    private final long st_dev;
    private final long st_ino;
    private final int st_mode;
    private final int st_nlink;
    private final int st_uid;
    private final int st_gid;
    private final long st_rdev;
    private final long st_size;
    private final int st_blksize;
    private final long st_blocks;
    private final int st_atime;
    private final int st_mtime;
    private final int st_ctime;
    
    public UnixFileStatusImpl(final long dev, final long ino, final int mode,
            final int nlink, final int uid, final int gid,
            final long rdev, final long size,
            final int blksize, final long blocks,
            final int atime, final int mtime, final int ctime) {
        super();
        st_dev = dev;
        st_ino = ino;
        st_mode = mode;
        st_nlink = nlink;
        st_uid = uid;
        st_gid = gid;
        st_rdev = rdev;
        st_size = size;
        st_blksize = blksize;
        st_blocks = blocks;
        st_atime = atime;
        st_mtime = mtime;
        st_ctime = ctime;
    }

    public boolean isSocket() {
        return (st_mode & S_IFMT) == S_IFSOCK;
    }

    public boolean isSymbolicLink() {
        return (st_mode & S_IFMT) == S_IFLNK;
    }

    public boolean isRegularFile() {
        return (st_mode & S_IFMT) == S_IFREG;
    }

    public boolean isBlockSpecialFile() {
        return (st_mode & S_IFMT) == S_IFBLK;
    }

    public boolean isDirectory() {
        return (st_mode & S_IFMT) == S_IFDIR;
    }

    public boolean isCharacterSpecialFile() {
        return (st_mode & S_IFMT) == S_IFCHR;
    }

    public boolean isFIFO() {
        return (st_mode & S_IFMT) == S_IFIFO;
    }

    public boolean isSetUID() {
        return (st_mode & S_ISUID) == S_ISUID;
    }

    public boolean isSetGID() {
        return (st_mode & S_ISGID) == S_ISGID;
    }

    public boolean isSticky() {
        return (st_mode & S_ISVTX) == S_ISVTX;
    }

    public int getPermissions() {
        return st_mode & (S_IRWXU | S_IRWXG | S_IRWXO);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Dev ");
        sb.append(st_dev);
        sb.append(", Inode ");
        sb.append(st_ino);
        sb.append(", Mode 0");
        sb.append(Integer.toOctalString(st_mode));
        sb.append(", NLink ");
        sb.append(st_nlink);
        sb.append(", UID ");
        sb.append(st_uid);
        sb.append(", GID ");
        sb.append(st_gid);
        sb.append(", RDev ");
        sb.append(st_rdev);
        sb.append(", Size ");
        sb.append(st_size);
        sb.append(", Blksize ");
        sb.append(st_blksize);
        sb.append(", Blocks ");
        sb.append(st_blocks);
        sb.append(", ATime ");
        sb.append(st_atime);
        sb.append(", MTime ");
        sb.append(st_mtime);
        sb.append(", CTime ");
        sb.append(st_ctime);
        return sb.toString();
    }

    public long getDeviceID() {
        return st_dev;
    }

    public long getInodeNumber() {
        return st_ino;
    }

    public int getMode() {
        return st_mode;
    }

    public int getNumberOfLinks() {
        return st_nlink;
    }

    public int getUserID() {
        return st_uid;
    }

    public int getGroupID() {
        return st_gid;
    }

    public long getSpecialDeviceID() {
        return st_rdev;
    }

    public long getSize() {
        return st_size;
    }

    public int getBlockSize() {
        return st_blksize;
    }

    public long getNumberOfBlocks() {
        return st_blocks;
    }
}
