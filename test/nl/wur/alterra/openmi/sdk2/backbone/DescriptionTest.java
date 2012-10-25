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


public class DescriptionTest {

    // test subjects
    private String caption1 = "caption";
    private String description1 = "description";
    private Description description;


    @Before
    public void setUp() {
        description = Description.newInstance(caption1, description1);
    }


    @Test
    public void testConstructor() {
        Assert.assertEquals(caption1, description.getCaption());
        Assert.assertEquals(description1, description.getDescription());
    }


    @Test
    public void testCaption() {
        Assert.assertEquals(caption1, description.getCaption());
        description.setCaption("newCaption");
        Assert.assertEquals("newCaption", description.getCaption());
    }


    @Test
    public void testDescription() {
        Assert.assertEquals(description1, description.getDescription());
        description.setDescription("newDescription");
        Assert.assertEquals("newDescription", description.getDescription());
    }


    @Test
    public void testCopyFactory() {
        Description newDescr1 = Description.newInstance(description);
        Assert.assertEquals(caption1, newDescr1.getCaption());
        Assert.assertEquals(description1, newDescr1.getDescription());

        Description newDescr2 = Description.newInstanceFromDescribable(description);
        Assert.assertEquals(caption1, newDescr2.getCaption());
        Assert.assertEquals(description1, newDescr2.getDescription());
    }

}
