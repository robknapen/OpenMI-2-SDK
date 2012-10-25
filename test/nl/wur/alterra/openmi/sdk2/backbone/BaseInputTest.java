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
import org.openmi.standard2.IBaseLinkableComponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


/**
 * Unit test for BaseInput class.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseInputTest {

    // test subjects
    private String id1 = "input1";
    private String caption1 = "Input 1";
    private String description1 = "Description of input 1.";
    private IBaseLinkableComponent component1;
    private Quality valueDef1;
    private BaseValueSet valueSet1;
    private BaseInput input1;


    @Before
    public void setUp() {
        valueDef1 = Quality.newStringInstance("Habitat suitability", "", true,
                Category.newInstance("good"), Category.newInstance("bad"));
        valueSet1 = BaseValueSet.new1DString();

        input1 = BaseInput.newInstance(id1, caption1, description1, component1,
                valueDef1, valueSet1);
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(id1, input1.getId());
        assertEquals(caption1, input1.getCaption());
        assertEquals(description1, input1.getDescription());
        assertEquals(component1, input1.getComponent());
        assertEquals(valueDef1, input1.getValueDefinition());
        assertEquals(valueSet1, input1.getValues());
    }


    @Test
    public void testCopyFactory() {
        BaseInput newInput = BaseInput.newInstance(input1);
        assertEquals(input1, newInput);
    }


    @Test
    public void testAccessors() {
        assertEquals(id1, input1.getId());
        input1.setId("newId");
        assertEquals("newId", input1.getId());
        input1.setCaption("newCaption");
        assertEquals("newCaption", input1.getCaption());
        input1.setDescription("newDescription");
        assertEquals("newDescription", input1.getDescription());

        BaseOutput provider = BaseOutput.newInstanceWithRandomId("output", "", null,
                valueDef1, BaseValueSet.new1DString());
        input1.setProvider(provider);
        assertEquals(provider, input1.getProvider());

        BaseValueSet newValueSet = BaseValueSet.new1DString();
        input1.setValues(newValueSet);
        assertEquals(newValueSet, input1.getValues());
    }


    @Test
    public void testEquals() {
        BaseInput newInput = BaseInput.newInstance(input1);
        assertEquals(input1, newInput);

        // input is identifiable, so only id should matter
        newInput.setCaption("newCaption");
        assertEquals(input1, newInput);

        newInput.setId("newId");
        assertNotSame(input1, newInput);
    }

}
