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


/**
 * Unit test for the Category class.
 */
public class CategoryTest {

    // test subjects
    private Double categoryDoubleValue = 1.23;
    private String categoryStringValue = "string1";
    private Category categoryDouble;
    private Category categoryString;


    @Before
    public void setUp() {
        categoryDouble = Category.newInstance(categoryDoubleValue);
        categoryString = Category.newInstance(categoryStringValue);
    }


    @Test
    public void testConstructor() {
        Assert.assertEquals(categoryDoubleValue, categoryDouble.getValue());
        Assert.assertEquals(categoryStringValue, categoryString.getValue());
    }


    @Test
    public void testAccessors() {
        Category cat = Category.newInstance("foo", "bar", 42.0);
        Assert.assertEquals("foo", cat.getCaption());
        Assert.assertEquals("bar", cat.getDescription());
        Assert.assertEquals(42.0, cat.getValue());

        cat.setCaption("foobar");
        Assert.assertEquals("foobar", cat.getCaption());
        cat.setDescription("foobar");
        Assert.assertEquals("foobar", cat.getDescription());
        cat.setValue("value");
        Assert.assertEquals("value", cat.getValue());
    }


    @Test
    public void testValue() {
        categoryDouble.setValue(4.56);
        Assert.assertEquals(4.56, categoryDouble.getValue());

        categoryString.setValue("newString");
        Assert.assertEquals("newString", categoryString.getValue());
    }


    @Test
    public void testEquals() {
        Assert.assertNotSame(categoryDouble, categoryString);

        // only value should be used for equal check, caption and description ignored
        Category cat1 = Category.newInstance(categoryDouble);
        Assert.assertEquals(categoryDouble, cat1);

        cat1.setCaption("newCaption");
        Assert.assertEquals(categoryDouble, cat1);
        cat1.setDescription("newDescription");
        Assert.assertEquals(categoryDouble, cat1);
        cat1.setValue("42.0");
        Assert.assertNotSame(categoryDouble, cat1);
    }
}
