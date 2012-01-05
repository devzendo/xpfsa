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

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests that the exception can be thrown from JNI.
 * @author matt
 *
 */
public final class TestFileSystemAccessException {

    /**
     * Use unit test helper code to throw an exception. 
     * @throws FileSystemAccessException 
     */
    @Test
    public void exceptionIsThrown() throws FileSystemAccessException {
        final DefaultFileSystemAccess fsa = new DefaultFileSystemAccess();
        final String message = "Test Message";
        try {
            fsa.throwFileSystemAccessExceptionNative(message);
            Assert.fail("Should have thrown an exception but didn't");
        } catch (final FileSystemAccessException e) {
            Assert.assertEquals(message, e.getMessage());
        }
    }

}
