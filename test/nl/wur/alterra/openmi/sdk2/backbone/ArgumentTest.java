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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ArgumentTest implements Observer {

    // test subjects
    private String argId1 = "double1";
    private String argDescr1 = "Double argument";
    private String argId2 = "string1";
    private String argDescr2 = "String argument";
    private double argValue1 = 1.0;
    private String argValue2 = "Foo";
    private Argument argDouble;
    private Argument argString;


    @Before
    public void setUp() {
        argDouble = Argument.newInstance(argId1, argId1, argDescr1, argValue1, true, false);
        argString = Argument.newInstance(argId2, argId2, argDescr2, argValue2, true, true);

        argDouble.addObserver(this);
        argString.addObserver(this);
    }


    @Test
    public void testArgumentConstruction() {
        Assert.assertEquals(false, argDouble.isReadOnly());
        Assert.assertEquals(argId1, argDouble.getId());
        Assert.assertEquals(argDescr1, argDouble.getDescription());
        Assert.assertEquals(argValue1, argDouble.getValue());

        Assert.assertEquals(true, argString.isReadOnly());
        Assert.assertEquals(argId2, argString.getId());
        Assert.assertEquals(argDescr2, argString.getDescription());
        Assert.assertEquals(argValue2, argString.getValue());
    }


    @Test
    public void testArgumentType() {
        Assert.assertTrue(argDouble.getValueType().equals(Double.class));
        Assert.assertTrue(argString.getValueType().equals(String.class));
    }


    @Test
    public void testCopyFactory() {
        Argument newArg = Argument.newInstance(argDouble);
        Assert.assertEquals(false, newArg.isReadOnly());
        Assert.assertEquals(argId1, newArg.getId());
        Assert.assertEquals(argDescr1, newArg.getDescription());
        Assert.assertEquals(argValue1, newArg.getValue());
    }


    @Test
    public void testPossibleValues() {
        List<Integer> possibleValues = new ArrayList<Integer>();
        possibleValues.add(1);
        possibleValues.add(2);
        possibleValues.add(3);

        Argument arg1 = Argument.newInstance("arg1", "arg1", "", 0, false, false);

        try {
            arg1.setPossibleValues(possibleValues);
            Assert.fail();
        } catch (IllegalStateException ex) {
            // ok
        }

        arg1.setValue(1);
        arg1.setDefaultValue(1);
        arg1.setPossibleValues(possibleValues);

        try {
            arg1.setValue(5);
            Assert.fail();
        } catch (IllegalStateException ex) {
            // ok
        }

        arg1.setValue(2);
        Assert.assertEquals((Integer) 2, arg1.getValue());
    }


    @Test
    public void testIsAllowedValue() throws Exception {
        List<Integer> possibleValues = new ArrayList<Integer>();
        possibleValues.add(1);
        possibleValues.add(2);
        possibleValues.add(3);

        Argument arg1 = Argument.newInstance("arg1", "arg1", "", 1, false, false);
        arg1.setPossibleValues(possibleValues);

        Assert.assertEquals(true, arg1.isAllowedValue(1));
        Assert.assertEquals(false, arg1.isAllowedValue(0));

    }


    @Test
    public void testValue() {
        Argument arg = Argument.newInstance("double", "double", "", argValue1, true, false);
        Assert.assertEquals(argValue1, arg.getValue());
        arg.setValue(2.0);
        Assert.assertEquals(2.0, arg.getValue());
    }


    @Test
    public void testGetValueType() {
        Assert.assertTrue(argDouble.getValueType().equals(Double.class));
        Assert.assertTrue(argString.getValueType().equals(String.class));
    }


    @Test
    public void testIsReadOnly() {
        argDouble.setValue("Bla");

        try {
            argString.setValue(argValue1);
        } catch (IllegalStateException ex) {
            // ok
        }
    }


    @Test
    public void testValueAsString() {
        argDouble.setValueAsString("2.3");
        Assert.assertEquals(2.3, argDouble.getValue());
        argString.setValueAsString("Bar");
        Assert.assertEquals("Bar", argString.getValue());

        try {
            argDouble.setValueAsString("Bar");
        } catch (RuntimeException ex) {
            // ok
        }
    }


    @Test
    public void testEquals() {
        // argument is id-based, only id should be used in equal check
        Argument newArg = Argument.newInstance(argDouble);
        Assert.assertEquals(argDouble, newArg);
        newArg.setCaption("newCaption");
        Assert.assertEquals(argDouble, newArg);
        newArg.setDescription("newDescription");
        Assert.assertEquals(argDouble, newArg);
        newArg.setDefaultValue(-argValue1);
        Assert.assertEquals(argDouble, newArg);
        newArg.setValue(-argValue1);
        Assert.assertEquals(argDouble, newArg);
        newArg.setId("newId");
        Assert.assertNotSame(argDouble, newArg);
    }


    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof OmiNotification) {
            if (((OmiNotification) o).hasMessage()) {
                System.out.println(((OmiNotification) o).getMessage());
            }
        }
    }
}
