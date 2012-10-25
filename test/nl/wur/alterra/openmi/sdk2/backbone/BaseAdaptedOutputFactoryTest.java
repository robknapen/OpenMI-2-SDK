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
import org.openmi.standard2.IBaseAdaptedOutput;
import org.openmi.standard2.IBaseValueSet;
import org.openmi.standard2.IIdentifiable;
import org.openmi.standard2.IValueDefinition;

import static org.junit.Assert.*;


/**
 * Unit test for BaseAdaptedOutputFactory.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseAdaptedOutputFactoryTest {

    // test subjects
    private BaseAdaptedOutputFactory factory1;
    private String id1 = "id1";
    private String caption1 = "Adapted Output";
    private MultiplierAdaptedOutput adaptedOutput1;
    private String id2 = "id2";
    private String caption2 = "Adapted Output";
    private MultiplierAdaptedOutput adaptedOutput2;
    private String id3 = "id3";
    private String caption3 = "Base Output";
    private Quantity quantity1;
    private BaseOutput output1;
    private String id4 = "id4";
    private String caption4 = "Base Input";
    private BaseInput input1;

    // TODO: Add more sample adapters
    // - Imperial to Metric units (Fahrenheit to Celsius)
    // - Format adapter (double to string, date formatting)
    // - XML adapter (double value to xml)


    @Before
    public void setUp() {
        // create a factory
        factory1 = new BaseAdaptedOutputFactory();

        // create a sample base output to be used as adaptee
        quantity1 = Quantity.newIntegerInstance("q1", "", Unit.newPredefinedUnit(Unit
                .PredefinedUnits.MILLIMETER_PER_DAY));
        BaseValueSet v1 = BaseValueSet.new2DInt();

        int[] i = new int[2];
        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                v1.setValue(i, i[0] * i[1]);
            }
        }

        output1 = BaseOutput.newInstance(id3, caption3, "", null, quantity1, v1);

        BaseValueSet v2 = BaseValueSet.new2DInt();
        input1 = BaseInput.newInstance(id4, caption4, "", null, quantity1, v2);

        // adapted outputs and add them to the factory
        adaptedOutput1 = MultiplierAdaptedOutput.newInstance(id1, caption1, "", null, 10);
        assertTrue(factory1.addAdaptedOutput(adaptedOutput1));
        adaptedOutput2 = MultiplierAdaptedOutput.newInstance(id2, caption2, "", null, 20);
        assertTrue(factory1.addAdaptedOutput(adaptedOutput2));
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(id1, adaptedOutput1.getId());
        assertEquals(caption1, adaptedOutput1.getCaption());
        assertEquals(10, adaptedOutput1.getMultiplier());

        assertNotNull(factory1);
        assertNotNull(factory1.getAvailableAdapters());
        assertEquals(2, factory1.getAvailableAdapters().size());
        assertEquals(adaptedOutput1.getClass(), factory1.getAvailableAdapters().get(0));
        assertEquals(adaptedOutput2.getClass(), factory1.getAvailableAdapters().get(1));
    }


    @Test
    public void testAdaptedOutputManagement() {
        // add same adapter with same arguments (should fail)
        assertFalse(factory1.addAdaptedOutput(adaptedOutput1));
        assertEquals(2, factory1.getAvailableAdapters().size());

        // add same adapter with different arguments
        adaptedOutput1.getArguments().get(0).setValue(42);
        assertTrue(factory1.addAdaptedOutput(adaptedOutput1));
        assertEquals(3, factory1.getAvailableAdapters().size());

        // remove adapter based on arguments
        assertTrue(factory1.removeAdaptedOutput(adaptedOutput1));
        assertEquals(2, factory1.getAvailableAdapters().size());
        assertEquals(adaptedOutput1.getClass(), factory1.getAvailableAdapters().get(0));
        assertEquals(adaptedOutput2.getClass(), factory1.getAvailableAdapters().get(1));

        // bulk remove adapters based on class
        factory1.removeAdaptedOutputs(adaptedOutput1.getClass());
        assertEquals(0, factory1.getAvailableAdapters().size());
    }


    @Test
    public void testFindAdaptedOutputs() {
        // create instance of adapter with same arguments
        IIdentifiable[] ids = factory1.getAvailableAdapterIds(output1, input1);
        assertNotNull(ids);
        assertEquals(2, ids.length);
        assertEquals(adaptedOutput1.getIdentifier(), ids[0]);
        assertEquals(adaptedOutput2.getIdentifier(), ids[1]);
    }


    @Test
    public void testCreateAdaptedOutput() {
        IIdentifiable[] ids = factory1.getAvailableAdapterIds(output1, input1);
        assertEquals(2, ids.length);
        IBaseAdaptedOutput adaptedOutput = factory1.createAdaptedOutput(ids[0], output1, input1);
        assertNotNull(adaptedOutput);
        assertNotSame(adaptedOutput, adaptedOutput1);
        assertNotSame(adaptedOutput, adaptedOutput2);
        assertEquals(output1, adaptedOutput.getAdaptee());
        assertEquals(10, adaptedOutput.getArguments().get(0).getValue());
    }


    @Test
    public void testClassificationAdapter() {
        // create classification adapter and add to factory
        ClassificationAdaptedOutput cao = ClassificationAdaptedOutput.newInstance(
                "classificationId1", "classification_adapted_output", "", null, 2500, 7500);
        factory1.addAdaptedOutput(cao);

        // create sample output
        Quantity q1 = Quantity.newIntegerInstance("quantity1", "", Unit.newPredefinedUnit(Unit
                .PredefinedUnits.MILLIMETER_PER_DAY));
        BaseValueSet v1 = BaseValueSet.new2DInt();

        int[] i = new int[2];
        for (i[0] = 0; i[0] < 100; i[0]++) {
            for (i[1] = 0; i[1] < 100; i[1]++) {
                v1.setValue(i, (i[0] + 1) * (i[1] + 1));
            }
        }

        BaseOutput output1 = BaseOutput.newInstance("output1", "output1", "", null, q1, v1);

        // create sample input
        IValueDefinition q2 = cao.getValueDefinition();
        BaseValueSet v2 = BaseValueSet.new2DString();
        BaseInput input1 = BaseInput.newInstance("input1", "input1", "", null, q2, v2);

        // set up the adapter from the factory
        IIdentifiable[] ids = factory1.getAvailableAdapterIds(output1, input1);
        assertNotNull(ids);
        assertEquals(1, ids.length);
        IBaseAdaptedOutput bao = factory1.createAdaptedOutput(ids[0], output1, input1);
        assertNotNull(bao);
        assertEquals(ClassificationAdaptedOutput.class, bao.getClass());

        // connect input to adapter (not really needed for this test)
        bao.addConsumer(input1);

        // update values in the adapted output
        bao.initialize();
        bao.refresh();
        IBaseValueSet v3 = bao.getValues();

        // verify results
        String[] cat = new String[]{
                (String) (((Quality) q2).getCategories().get(0).getValue()),
                (String) (((Quality) q2).getCategories().get(1).getValue()),
                (String) (((Quality) q2).getCategories().get(2).getValue())};

        // check sample values
        assertEquals(cat[0], v3.getValue(new int[]{41, 0}));
        assertEquals(cat[0], v3.getValue(new int[]{41, 58}));
        assertEquals(cat[0], v3.getValue(new int[]{80, 0}));
        assertEquals(cat[1], v3.getValue(new int[]{79, 92}));
        assertEquals(cat[1], v3.getValue(new int[]{41, 59}));
        assertEquals(cat[1], v3.getValue(new int[]{41, 99}));
        assertEquals(cat[2], v3.getValue(new int[]{79, 93}));
        assertEquals(cat[2], v3.getValue(new int[]{79, 99}));

        // check totals
        int[] catCount = new int[cat.length];
        int[] j = new int[2];
        for (j[0] = 0; j[0] < 100; j[0]++) {
            for (j[1] = 0; j[1] < 100; j[1]++) {
                Object val = v3.getValue(j);
                if (val != null) {
                    for (int catIndex = 0; catIndex < cat.length; catIndex++) {
                        if (cat[catIndex].equals(val.toString())) {
                            catCount[catIndex]++;
                        }
                    }
                }
            }
        }
        assertEquals(5893, catCount[0]);
        assertEquals(3741, catCount[1]);
        assertEquals(366, catCount[2]);
    }
}
