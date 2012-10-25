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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Unit test for base value set.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseValueSetTest {

    // test subjects
    private BaseValueSet intValues;
    private BaseValueSet doubleValues;


    @Before
    public void setUp() {
        int[] i = new int[2];

        intValues = BaseValueSet.new2DInt();
        for (i[0] = 0; i[0] < 10; i[0]++) {
            for (i[1] = 0; i[1] < 10; i[1]++) {
                intValues.setValue(i, i[0] * i[1]);
            }
        }

        doubleValues = BaseValueSet.new2DDouble();
        for (i[0] = 0; i[0] < 10; i[0]++) {
            for (i[1] = 0; i[1] < 10; i[1]++) {
                doubleValues.setValue(i, new Double(i[0] * i[1]));
            }
        }
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(Integer.class, intValues.getValueType());
        assertEquals(2, intValues.getNumberOfIndices());
    }


    @Test
    public void testCopyFactory() {
        BaseValueSet duplicate = BaseValueSet.newInstance(intValues);

        assertEquals(intValues.getValueType(), duplicate.getValueType());
        assertEquals(intValues.getNumberOfIndices(), duplicate.getNumberOfIndices());

        int[] i = new int[2];
        for (i[0] = 0; i[0] < 10; i[0]++) {
            for (i[1] = 0; i[1] < 10; i[1]++) {
                assertEquals(i[0] * i[1], duplicate.getValue(i));
            }
        }
    }


    @Test
    public void testAccessors() {
        assertEquals(Integer.class, intValues.getValueType());
        intValues.setValueType(String.class);
        assertEquals(String.class, intValues.getValueType());

        intValues.setNumberOfIndices(5);
        assertEquals(5, intValues.getNumberOfIndices());
    }


    @Test
    public void testEquals() {
        BaseValueSet duplicate = BaseValueSet.newInstance(intValues);
        assertEquals(intValues, duplicate);

        duplicate.setValue(new int[]{2, 2}, 42);
        assertNotSame(intValues, duplicate);
    }


    @Test
    public void testStoreAndRetrieve() {
        int[] indices = new int[]{0, 0};
        intValues.setValue(indices, 42);
        assertEquals(42, intValues.getValue(indices));

        int[] i = new int[2];
        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                intValues.setValue(i, i[0] * i[1]);
            }
        }

        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                assertEquals(i[0] * i[1], intValues.getValue(i));
            }
        }
    }


    @Test
    public void testValueTypeValidation() {
        int[] indices = new int[]{0, 0};

        try {
            intValues.setValue(indices, "string");
            fail();
        } catch (Exception ex) {
            if (!(ex instanceof IllegalArgumentException)) {
                fail();
            }
        }
    }


    @Test
    public void testIndexCount() {
        assertEquals(10, intValues.getIndexCount(new int[]{}));
        assertEquals(10, intValues.getIndexCount(new int[]{0}));
        assertEquals(10, intValues.getIndexCount(new int[]{1}));
        assertEquals(10, intValues.getIndexCount(new int[]{9}));
        assertEquals(-1, intValues.getIndexCount(new int[]{10}));
        assertEquals(-1, intValues.getIndexCount(new int[]{0, 0}));

        intValues.setValue(new int[]{9, 14}, 42);
        assertEquals(10, intValues.getIndexCount(new int[]{}));
        assertEquals(15, intValues.getIndexCount(new int[]{9}));
    }

}
