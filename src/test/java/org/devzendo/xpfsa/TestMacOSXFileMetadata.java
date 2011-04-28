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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.devzendo.commoncode.executor.IteratorExecutor;
import org.devzendo.commoncode.logging.LoggingUnittestHelper;
import org.devzendo.commoncode.os.OSTypeDetect;
import org.devzendo.commoncode.os.OSTypeDetect.OSType;
import org.devzendo.xpfsa.impl.MacOSXFileStatusImpl;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Preconditions: Mac OS X file data has been set up in the
 * testdata subdir with src/test/sh/setupmacosxtestdata.sh
 * 
 * @author matt
 *
 */
public final class TestMacOSXFileMetadata {
    private static final Logger LOGGER = Logger
            .getLogger(TestMacOSXFileMetadata.class);

    private static final Object TEST_GID = 20; // staff
    private static final Object TEST_UID = 501; // matt
    
    private static OSType osType = OSTypeDetect.getInstance().getOSType();

    @BeforeClass
    public static void setupLogging() {
        LoggingUnittestHelper.setupLogging();
    }
    
    @Rule
    public final TemporaryFolder tempDir = new TemporaryFolder();
    private String mTestDir;
    private FileSystemAccess mFsa;

    /**
     * Create a temporary file to hold prefs data, that's deleted after
     * the JVM exits.
     * 
     * @throws IOException on failure
     * @throws FileSystemAccessException 
     */
    @Before
    public void setUp() throws IOException, FileSystemAccessException {
        Assume.assumeTrue(osType == OSType.MacOSX);
        LoggingUnittestHelper.setupLogging();
        
        tempDir.create();
        mTestDir = tempDir.getRoot().getAbsolutePath();
        final IteratorExecutor it = new IteratorExecutor(new String[] 
            {"/bin/sh", "src/test/sh/setupmacosxtestdata.sh", mTestDir});
        while (it.hasNext()) {
            LOGGER.info(">> " + it.next());
        }
        final int exitValue = it.getExitValue();
        assertThat(exitValue, equalTo(0));

        mFsa = new FileSystemAccess();
    }
    
    @Test
    public void directoryOwnershipObtained() throws IOException, FileSystemAccessException {
        final File directory = new File(mTestDir);
        assertThat("test data dir " + directory.getAbsolutePath() + " does not exist", directory.exists(), equalTo(true));

        final File testDir = new File(mTestDir, "directory");
        assertThat("'directory' (" + testDir.getAbsolutePath() + ") does not exist", testDir.exists(), equalTo(true));
        
        final DetailedFile detailedFile = mFsa.getDetailedFile(testDir);
        assertThat(detailedFile.getFile().getAbsolutePath(), equalTo(testDir.getAbsolutePath()));
        
        final FileStatus fs = detailedFile.getFileStatus();
        
        assertThat(fs, instanceOf(MacOSXFileStatus.class));
        final MacOSXFileStatus mfs = (MacOSXFileStatus) fs;
        assertThat(mfs.isDirectory(), equalTo(true));
        assertThat(mfs.isRegularFile(), equalTo(false));
        assertThat(mfs.getPermissions(), equalTo(0755));
        assertThat(mfs.getUserID(), equalTo(TEST_UID));
        assertThat(mfs.getGroupID(), equalTo(TEST_GID));
    }

    @Test
    public void fileStatusObtained() throws FileSystemAccessException {
        final File directory = new File(mTestDir);
        assertThat("test data dir " + directory.getAbsolutePath() + " does not exist", directory.exists(), equalTo(true));

        final File f = new File(mTestDir, "testfile");
        assertThat(f.exists(), equalTo(true));
        final DetailedFile df = mFsa.getDetailedFile(f);
        assertThat(df.getFile().getAbsolutePath(), equalTo(f.getAbsolutePath()));
        
        final FileStatus fs = df.getFileStatus();
        assertThat(fs, instanceOf(MacOSXFileStatus.class));
        final MacOSXFileStatus mfs = (MacOSXFileStatus) fs;
        LOGGER.info(mfs);
        assertThat(mfs.getPermissions(), equalTo(0644));
        assertThat(mfs.isRegularFile(), equalTo(true));
        assertThat(mfs.getUserID(), equalTo(TEST_UID));
        assertThat(mfs.getGroupID(), equalTo(TEST_GID));
        assertThat(mfs.getNumberOfLinks(), equalTo(1));
        assertThat(mfs.getSize(), equalTo(6L));
    }

    @Test
    public void shouldFailToGetFileStatusForNonexistentFile() {
        try {
            final DetailedFile f = mFsa.getDetailedFile(new File("doesnotexist"));
            f.getFileStatus();
            final String msg = "Did not fail to get FileStatus for a file that doesn't exist";
            LOGGER.warn(msg);
            Assert.fail(msg);
        } catch (final FileSystemAccessException e) {
            final String msg = "Correct error from stat of nonexistent file: " + e.getMessage();
            LOGGER.info(msg);
            assertThat(e.getMessage(), equalTo("No such file or directory"));
        }
    }

    @Test
    @Ignore
    public void canIterateOverDirectory() throws FileSystemAccessException {
        final Iterator<DetailedFile> it = mFsa.getDirectoryIterator(new File(mTestDir, "tree"));
        while(it.hasNext()) {
            final DetailedFile df = it.next();
        }
    }
}
