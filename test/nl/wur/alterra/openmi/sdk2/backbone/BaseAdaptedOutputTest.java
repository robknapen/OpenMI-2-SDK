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
import org.openmi.standard2.IArgument;
import org.openmi.standard2.IBaseValueSet;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for BaseAdaptedOutput.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseAdaptedOutputTest {

    // test subjects
    private String id1 = "id1";
    private String caption1 = "Adapted Output";
    private MultiplierAdaptedOutput adaptedOutput1;
    private String id2 = "id2";
    private String caption2 = "Base Output";
    private BaseOutput output1;
    private Quantity quantity1;


    @Before
    public void setUp() {
        quantity1 = Quantity.newIntegerInstance("q1", "", Unit.newPredefinedUnit(Unit
                .PredefinedUnits.MILLIMETER_PER_DAY));
        BaseValueSet v1 = BaseValueSet.new2DInt();

        int[] i = new int[2];
        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                v1.setValue(i, i[0] * i[1]);
            }
        }

        output1 = BaseOutput.newInstance(id2, caption2, "", null, quantity1, v1);
        adaptedOutput1 = MultiplierAdaptedOutput.newInstance(id1, caption1, "", null, 10);
        adaptedOutput1.setAdaptee(output1);
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(id1, adaptedOutput1.getId());
        assertEquals(caption1, adaptedOutput1.getCaption());
        assertEquals(10, adaptedOutput1.getMultiplier());
        assertEquals(output1, adaptedOutput1.getAdaptee());
        assertNotNull(adaptedOutput1.getArguments());
        assertEquals(1, adaptedOutput1.getArguments().size());
        assertNotNull(adaptedOutput1.getValues());
        assertNotNull(adaptedOutput1.getAdaptedOutputs());
        assertEquals(0, adaptedOutput1.getAdaptedOutputs().size());
    }


    @Test
    public void testAccessors() {
        assertEquals(id1, adaptedOutput1.getId());
        adaptedOutput1.setId("newId");
        assertEquals("newId", adaptedOutput1.getId());
        adaptedOutput1.setCaption("newCaption");
        assertEquals("newCaption", adaptedOutput1.getCaption());
        adaptedOutput1.setDescription("newDescription");
        assertEquals("newDescription", adaptedOutput1.getDescription());

        BaseOutput out1 = BaseOutput.newInstance("id", "", "", null, null, null);
        adaptedOutput1.setAdaptee(out1);
        assertEquals(out1, adaptedOutput1.getAdaptee());
    }


    @Test
    public void testEquals() {
        // BaseAdaptedOutput has id, so only that should affect equals
        BaseAdaptedOutput adaptedOutput2 = MultiplierAdaptedOutput.newInstance(id1,
                caption1, "", null, 10);
        assertEquals(adaptedOutput1, adaptedOutput2);

        adaptedOutput2.setCaption("newCaption");
        assertEquals(adaptedOutput1, adaptedOutput2);

        adaptedOutput2.setId("newId");
        assertNotSame(adaptedOutput1, adaptedOutput2);
    }


    @Test
    public void testAdapterInitialisation() {
        List<IArgument> args = adaptedOutput1.getArguments();
        for (IArgument arg : args) {
            if (arg.getId().equals(MultiplierAdaptedOutput.ARG_MULTIPLIER)) {
                arg.setValue(5);
            }
        }

        adaptedOutput1.initialize();
        assertEquals(5, adaptedOutput1.getMultiplier());
    }


    @Test
    public void testValueAdaption() {
        adaptedOutput1.initialize();
        adaptedOutput1.refresh();

        IBaseValueSet v2 = adaptedOutput1.getValues();
        int[] i = new int[2];
        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                assertEquals(i[0] * i[1] * adaptedOutput1.getMultiplier(), v2.getValue(i));
            }
        }
    }


    @Test
    public void testRefreshChaining() {
        int[] i = new int[]{0, 0};

        output1.setValue(i, 42, false, true);
        assertEquals(42 * adaptedOutput1.getMultiplier(), adaptedOutput1.getValues().getValue
                (i));
    }


    @Test
    public void testCanAdaptTo() {
        adaptedOutput1.setValueDefinition(quantity1);

        // same value definition, should be ok
        BaseInput input1 = new BaseInput(null, quantity1, BaseValueSet.new2DInt());
        assertTrue(adaptedOutput1.canAdaptTo(input1));

        // different value definition, same value set type, should fail
        Quantity q2 = Quantity.newIntegerInstance("q2", "", Unit.newPredefinedUnit(Unit
                .PredefinedUnits.CUBIC_METER_PER_SECOND));
        BaseInput input2 = new BaseInput(null, q2, BaseValueSet.new2DInt());
        assertFalse(adaptedOutput1.canAdaptTo(input2));

        // same value definition but different value set type, should fail
        BaseInput input3 = new BaseInput(null, quantity1, BaseValueSet.new2DDouble());
        assertFalse(adaptedOutput1.canAdaptTo(input3));

        // both null value definition should be ok too
        BaseOutput output2 = new BaseOutput(null, null, null);
        BaseInput input4 = new BaseInput(null, null, null);
        adaptedOutput1.setAdaptee(output2);
        adaptedOutput1.setValueDefinition(null);
        assertTrue(adaptedOutput1.canAdaptTo(input4));
    }

}
