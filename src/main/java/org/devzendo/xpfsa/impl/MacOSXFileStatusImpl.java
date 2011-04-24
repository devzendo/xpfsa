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

import org.devzendo.xpfsa.MacOSXFileStatus;

public class MacOSXFileStatusImpl extends UnixFileStatusImpl implements MacOSXFileStatus {

    public MacOSXFileStatusImpl(final long dev, final long ino, final int mode, final int nlink,
            final int uid, final int gid, final long rdev, final long size, final int blksize, final long blocks,
            final int atime, final int mtime, final int ctime) {
        super(dev, ino, mode, nlink, uid, gid, rdev, size, blksize, blocks, atime,
                mtime, ctime);
    }
}
