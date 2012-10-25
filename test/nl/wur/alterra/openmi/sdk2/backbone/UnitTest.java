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


public class UnitTest {

    // test subjects
    private Unit unit;


    @Before
    public void setUp() {
        unit = Unit.newPredefinedUnit(Unit.PredefinedUnits.CUBIC_METER_PER_SECOND);
    }


    @Test
    public void testConstructor() {
        Unit newUnit = new Unit();
        Assert.assertEquals("[-]", newUnit.getCaption());

        newUnit = Unit.newInstance(unit);
//        Assert.assertEquals( "speed", newUnit.getId() );
        Assert.assertEquals(1, newUnit.getConversionFactorToSI(), Double.MIN_VALUE);
        Assert.assertEquals(0, newUnit.getOffsetToSI(), Double.MIN_VALUE);
        Assert.assertEquals("m3/s", newUnit.getCaption());
        Assert.assertEquals("cubic meter per second", newUnit.getDescription());

        newUnit = Unit.newInstance("speed (m/s)", "Speed as meter per second", 1.0, 0.0);
        Assert.assertEquals("speed (m/s)", newUnit.getCaption());
        Assert.assertEquals("Speed as meter per second", newUnit.getDescription());
        Assert.assertEquals(1, newUnit.getConversionFactorToSI(), Double.MIN_VALUE);
        Assert.assertEquals(0, newUnit.getOffsetToSI(), Double.MIN_VALUE);
    }


    @Test
    public void testAccessors() {
        Unit newUnit = Unit.newInstance("caption", "description", 3.4, 6.7);
        Assert.assertEquals("caption", newUnit.getCaption());
        Assert.assertEquals("description", newUnit.getDescription());
        Assert.assertEquals(3.4, newUnit.getConversionFactorToSI(), 1e-6);
        Assert.assertEquals(6.7, newUnit.getOffsetToSI(), 1e-6);

        newUnit.setCaption("newCaption");
        Assert.assertEquals("newCaption", newUnit.getCaption());

        newUnit.setDescription("newDescription");
        Assert.assertEquals("newDescription", newUnit.getDescription());

        newUnit.setConversionFactorToSI(3.5);
        Assert.assertEquals(3.5, newUnit.getConversionFactorToSI(), 1e-6);

        newUnit.setOffsetToSI(6.7);
        Assert.assertEquals(6.7, newUnit.getOffsetToSI(), 1e-6);
    }


    @Test
    public void testEquals() {
        Unit newUnit = Unit.newPredefinedUnit(Unit.PredefinedUnits.CUBIC_METER_PER_SECOND);
        Assert.assertEquals(unit, newUnit);

        newUnit.setCaption("newCaption");
        Assert.assertNotSame(unit, newUnit);
        newUnit.setCaption(unit.getCaption());

        newUnit.setDescription("newDescription");
        Assert.assertNotSame(unit, newUnit);
        newUnit.setDescription(unit.getDescription());

        newUnit.setOffsetToSI(6.7);
        Assert.assertNotSame(unit, newUnit);
        newUnit.setOffsetToSI(unit.getOffsetToSI());

        newUnit.setConversionFactorToSI(3.4);
        Assert.assertNotSame(unit, newUnit);
        newUnit.setConversionFactorToSI(unit.getConversionFactorToSI());

        Assert.assertNotSame(unit, null);
        Assert.assertNotSame(unit, "string");
    }
}
