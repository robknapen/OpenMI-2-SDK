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
import org.openmi.standard2.IBaseExchangeItem;
import org.openmi.standard2.IBaseLinkableComponent;
import org.openmi.standard2.IBaseValueSet;

import static org.junit.Assert.*;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseOutputTest {

    // test subjects
    private String id1 = "output1";
    private String caption1 = "Output 1";
    private String description1 = "Description of output 1.";
    private Quality valueDef1;
    private BaseValueSet valueSet1;
    private IBaseLinkableComponent component1;
    private BaseOutput output1;


    private class TestAdaptedOutput extends BaseAdaptedOutput {

        @Override
        public void initialize() {
        }


        @Override
        public void refresh() {
        }


        @Override
        public IBaseValueSet getValues() {
            return null;
        }


        @Override
        public IBaseValueSet getValues(IBaseExchangeItem querySpecifier) {
            return null;
        }
    }


    @Before
    public void setUp() {
        valueDef1 = Quality.newStringInstance("Habitat Suitability", "", true,
                Category.newInstance("Good"), Category.newInstance("Bad"));
        valueSet1 = BaseValueSet.new1DString();

        output1 = BaseOutput.newInstance(id1, caption1, description1, component1, valueDef1, valueSet1);
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(id1, output1.getId());
        assertEquals(caption1, output1.getCaption());
        assertEquals(description1, output1.getDescription());
        assertEquals(component1, output1.getComponent());
        assertEquals(valueDef1, output1.getValueDefinition());
        assertEquals(valueSet1, output1.getValues());
    }


    @Test
    public void testCopyFactory() {
        BaseOutput newOutput = BaseOutput.newInstance(output1);
        assertEquals(output1, newOutput);
    }


    @Test
    public void testAccessors() {
        assertEquals(id1, output1.getId());
        output1.setId("newId");
        assertEquals("newId", output1.getId());
        assertEquals(caption1, output1.getCaption());
        output1.setCaption("newCaption");
        assertEquals("newCaption", output1.getCaption());
        output1.setDescription("newDescription");
        assertEquals("newDescription", output1.getDescription());
    }


    @Test
    public void testEquals() {
        BaseOutput newOutput = BaseOutput.newInstance(output1);
        assertEquals(output1, newOutput);

        // output is identifiable, so only id should matter
        newOutput.setCaption("newCaption");
        assertEquals(output1, newOutput);

        newOutput.setId("newId");
        assertNotSame(output1, newOutput);
    }


    @Test
    public void testConsumerManagement() {
        BaseInput input1 = BaseInput.newInstance("inId1", "in1", "descr1", null, valueDef1, null);
        BaseInput input2 = BaseInput.newInstance("inId2", "in2", "descr2", null, valueDef1, null);

        assertNotNull(output1.getConsumers());
        assertEquals(0, output1.getConsumers().size());

        output1.addConsumer(input1);
        output1.addConsumer(input2);
        assertEquals(2, output1.getConsumers().size());
        assertEquals(input1, output1.getConsumers().get(0));
        assertEquals(input2, output1.getConsumers().get(1));

        output1.addConsumer(input2);
        assertEquals(2, output1.getConsumers().size());

        output1.removeConsumer(input2);
        assertEquals(1, output1.getConsumers().size());
        assertEquals(input1, output1.getConsumers().get(0));

        output1.removeConsumer(input2);
        assertEquals(1, output1.getConsumers().size());
        output1.removeConsumer(input1);
        assertEquals(0, output1.getConsumers().size());
    }


    @Test
    public void testAdaptedOutputManagement() {
        TestAdaptedOutput adapter1 = new TestAdaptedOutput();
        TestAdaptedOutput adapter2 = new TestAdaptedOutput();

        assertNotNull(output1.getAdaptedOutputs());
        assertEquals(0, output1.getAdaptedOutputs().size());

        output1.addAdaptedOutput(adapter1);
        output1.addAdaptedOutput(adapter2);
        assertEquals(2, output1.getAdaptedOutputs().size());
        assertEquals(adapter1, output1.getAdaptedOutputs().get(0));
        assertEquals(adapter2, output1.getAdaptedOutputs().get(1));

        output1.addAdaptedOutput(adapter2);
        assertEquals(2, output1.getAdaptedOutputs().size());

        output1.removeAdaptedOutput(adapter2);
        assertEquals(1, output1.getAdaptedOutputs().size());
        assertEquals(adapter1, output1.getAdaptedOutputs().get(0));

        output1.removeAdaptedOutput(adapter2);
        assertEquals(1, output1.getAdaptedOutputs().size());
        output1.removeAdaptedOutput(adapter1);
        assertEquals(0, output1.getAdaptedOutputs().size());
    }


    @Test
    public void testGetValues() {
        assertEquals(valueSet1, output1.getValues());
        assertEquals(valueSet1, output1.getValues(null));
        assertNull(output1.getValues(new BaseInput()));
        BaseInput newInput = BaseInput.newInstance("id", "in", "descr", null, valueDef1, null);
        assertEquals(valueSet1, output1.getValues(newInput));
    }

}
