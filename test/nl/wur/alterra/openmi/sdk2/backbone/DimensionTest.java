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
import org.openmi.standard2.IDimension;


public class DimensionTest {

    // test subjects
    private Dimension dimension;


    @Before
    public void setUp() {
        dimension = new Dimension();
        dimension.setPower(IDimension.DimensionBase.LENGTH, 3);
        dimension.setPower(IDimension.DimensionBase.TIME, -1);
    }


    @Test
    public void testPower() {
        Assert.assertEquals(3, dimension.getPower(IDimension.DimensionBase.LENGTH), Double.MIN_VALUE);
        Assert.assertEquals(-1, dimension.getPower(IDimension.DimensionBase.TIME), Double.MIN_VALUE);
    }


    @Test
    public void testEquals() {
        Dimension newDimension = new Dimension();
        newDimension.setPower(IDimension.DimensionBase.LENGTH, 3);
        newDimension.setPower(IDimension.DimensionBase.TIME, -1);

        Assert.assertTrue(dimension.equals(newDimension));
        newDimension.setPower(IDimension.DimensionBase.LENGTH, 2);
        Assert.assertFalse(dimension.equals(newDimension));
    }
}
