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
import org.openmi.standard2.IUnit;


public class QuantityTest {

    // test subjects
    private String testCaption1 = "quantity1";
    private String testDescription1 = "Description for quantity1.";
    private Quantity testQuantity1;
    private IUnit testUnitMPerSec;
    private String testCaption2 = "quantity2";
    private String testDescription2 = "Description for quantity2.";
    private Quantity testQuantity2;
    private IUnit testUnitMMPerDay;


    @Before
    public void setUp() {
        testUnitMPerSec = Unit.newPredefinedUnit(Unit.PredefinedUnits.METER_PER_SECOND);
        testQuantity1 = Quantity.newDoubleInstance(testCaption1, testDescription1, testUnitMPerSec);

        testUnitMMPerDay = Unit.newPredefinedUnit(Unit.PredefinedUnits.MILLIMETER_PER_DAY);
        testQuantity2 = Quantity.newDoubleInstance(testCaption2, testDescription2, testUnitMMPerDay);
    }


    @Test
    public void testConstructor() {
        Assert.assertEquals(testCaption1, testQuantity1.getCaption());
        Assert.assertEquals(testDescription1, testQuantity1.getDescription());
        Assert.assertEquals(testUnitMPerSec, testQuantity1.getUnit());
        Assert.assertEquals(testCaption2, testQuantity2.getCaption());
        Assert.assertEquals(testDescription2, testQuantity2.getDescription());
        Assert.assertEquals(testUnitMMPerDay, testQuantity2.getUnit());
    }


    @Test
    public void testAccessors() {
        Quantity newQuantity = Quantity.newDoubleInstance(testCaption1, testDescription1, testUnitMPerSec);

        Assert.assertEquals(testCaption1, newQuantity.getCaption());
        newQuantity.setCaption("newCaption");
        Assert.assertEquals("newCaption", newQuantity.getCaption());

        Assert.assertEquals(testDescription1, newQuantity.getDescription());
        newQuantity.setDescription("newDescription");
        Assert.assertEquals("newDescription", newQuantity.getDescription());

        Assert.assertEquals(testUnitMPerSec, newQuantity.getUnit());
    }


    @Test
    public void testEquals() {
        // quantity is not identifiable, so all fields must be included in equals check
        Unit newUnit = Unit.newPredefinedUnit(Unit.PredefinedUnits.METER_PER_SECOND);
        Quantity newQuantity = Quantity.newDoubleInstance(testCaption1, testDescription1, newUnit);

        Assert.assertEquals(testQuantity1, newQuantity);

        newQuantity.setCaption("newCaption");
        Assert.assertNotSame(testQuantity1, newQuantity);
        newQuantity.setCaption(testQuantity1.getCaption());

        newQuantity.setDescription("newDescription");
        Assert.assertNotSame(testQuantity1, newQuantity);
        newQuantity.setDescription(testQuantity1.getDescription());

        String oldCaption = newUnit.getCaption();
        newUnit.setCaption("newCaption");
        Assert.assertNotSame(testQuantity1, newQuantity);
        newUnit.setCaption(oldCaption);
        Assert.assertEquals(testQuantity1, newQuantity);
        newUnit.setOffsetToSI(3.4);
        Assert.assertNotSame(testQuantity1, newQuantity);
    }
}
