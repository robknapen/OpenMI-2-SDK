/*
 * Copyright (c) 2005-2011 Alterra, Wageningen UR, The Netherlands and the
 * OpenMI Association.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  - The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  - Neither the name of the OpenMI Association nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.wur.alterra.openmi.sdk2.backbone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ArgumentsTest {

    // test subjects
    private String argId1 = "double1";
    private String argId2 = "double2";
    private Arguments argList;
    private Argument arg1;
    private Argument arg2;


    @Before
    public void setUp() throws Exception {
        arg1 = Argument.newInstance(argId1, "", "", 1.0, false, false);
        arg2 = Argument.newInstance(argId2, "", "", 2.0, false, false);
        argList = Arguments.newInstance(arg1, arg2);
    }


    @Test
    public void testValueForId() throws Exception {
        Assert.assertEquals(1.0, argList.getValueForId(argId1));
        argList.setValueForId(argId1, 2.0);
        Assert.assertEquals(2.0, argList.getValueForId(argId1));
    }


    @Test
    public void testContainsId() throws Exception {
        Assert.assertTrue(argList.containsId(argId1));
        Assert.assertFalse(argList.containsId("double3"));
    }


    @Test
    public void testCanAddAllNew() throws Exception {
        int size = argList.size();

        Argument newArg1 = Argument.newInstance("double3", "", "", 3.0, false, false);
        Argument newArg2 = Argument.newInstance("double4", "", "", 4.0, false, false);
        Arguments newArgs = Arguments.newInstance(newArg1, newArg2);

        argList.addAll(newArgs);

        Assert.assertEquals(size + 2, argList.size());
        Assert.assertEquals(newArg1, argList.get(size));
        Assert.assertEquals(newArg2, argList.get(size + 1));
    }


    @Test
    public void testCanNotAddAllDuplicates() throws Exception {
        int size = argList.size();

        Argument newArg1 = Argument.newInstance(argList.get(0));
        Argument newArg2 = Argument.newInstance(argList.get(1));
        Arguments newArgs = Arguments.newInstance(newArg1, newArg2);

        argList.addAll(newArgs);

        Assert.assertEquals(size, argList.size());
    }


    @Test
    public void testCanAddNew() throws Exception {
        int size = argList.size();
        Argument newArg = Argument.newInstance("double3", "", "", 3.0, false, false);
        argList.add(newArg);
        Assert.assertEquals(size + 1, argList.size());
        Assert.assertEquals(newArg, argList.get(size));
    }


    @Test
    public void testCanNotAddDuplicate() throws Exception {
        int size = argList.size();
        Argument dubArg = Argument.newInstance(argList.get(0));
        Assert.assertFalse(argList.add(dubArg));
        Assert.assertEquals(size, argList.size());
    }


    @Test
    public void testIndexOf() throws Exception {
        Assert.assertEquals(0, argList.indexOf(arg1));
        Assert.assertEquals(1, argList.indexOf(arg2));
    }


    @Test
    public void testIndexOfId() throws Exception {
        Assert.assertEquals(0, argList.indexOfId(arg1.getId()));
        Assert.assertEquals(1, argList.indexOfId(arg2.getId()));
    }
}
