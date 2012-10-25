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
import org.openmi.standard2.*;

import java.util.*;

import static org.junit.Assert.*;


/**
 * Unit test for base linkable component.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseLinkableComponentTest implements Observer {

    // test subjects
    private TestLinkableComponent component1;
    private String componentId1 = "component1";
    private String componentCaption1 = "Test Component 1";
    private String componentDescr1 = "Description of test component 1.";

    private String inputId1 = "input1_protected_areas";
    private String inputCaption1 = "protected_areas";
    private String inputDescr1 = "Protected Areas.";
    private Quality inputValueDef1;
    private BaseValueSet inputValueSet1;
    private BaseInput input1;

    private String inputId2 = "input2_landcover";
    private String inputCaption2 = "landcover";
    private String inputDescr2 = "Landcover.";
    private Quality inputValueDef2;
    private BaseValueSet inputValueSet2;
    private BaseInput input2;

    private String outputId1 = "output1_greenness";
    private String outputCaption1 = "Greenness";
    private String outputDescr1 = "Greenness.";
    private Quality outputValueDef1;
    private BaseValueSet outputValueSet1;
    private BaseOutput output1;

    private String outputId2 = "output2_greenness_change";
    private String outputCaption2 = "greenness_change";
    private String outputDescr2 = "Change in greenness, expressed as -1 for decline, " +
            "0 for status quo (though focus might shift) and +1 for increase.";
    private Quantity outputValueDef2;
    private BaseValueSet outputValueSet2;
    private BaseOutput output2;

    private IAdaptedOutputFactory factory1;
    private IAdaptedOutputFactory factory2;
    private IBaseAdaptedOutput output3;


    /**
     * Linkable Component for testing purposes.
     */
    private class TestLinkableComponent extends BaseLinkableComponent {

        private Map<String, ICategory> kmatrix;


        public TestLinkableComponent(String id, String caption, String description) {
            super(id, caption, description);

            // create input for protected area data
            inputValueDef1 = Quality.newStringInstance(inputCaption1, inputDescr1, false,
                    Category.newInstance("None"),
                    Category.newInstance("Designated Areas (CDDA) only"),
                    Category.newInstance("Natura 2000 only"),
                    Category.newInstance("N2000 and CDDA"));
            inputValueSet1 = BaseValueSet.new2DString();

            input1 = BaseInput.newInstance(inputId1, inputCaption1, inputDescr1, this,
                    inputValueDef1, inputValueSet1);
            addInput(input1);

            // create input for landcover data
            inputValueDef2 = Quality.newStringInstance(inputCaption2, inputDescr2, false,
                    Category.newInstance("Artificial Surfaces"),
                    Category.newInstance("Arable Land"),
                    Category.newInstance("Pastures"),
                    Category.newInstance("Forests"),
                    Category.newInstance("Natural Grassland"),
                    Category.newInstance("Open Spaces"),
                    Category.newInstance("Wetlands"),
                    Category.newInstance("Water Bodies"),
                    Category.newInstance("Urban Green"));
            inputValueSet2 = BaseValueSet.new2DString();

            input2 = BaseInput.newInstance(inputId2, inputCaption2, inputDescr2, this,
                    inputValueDef2, inputValueSet2);
            addInput(input2);

            // create output for greenness data
            outputValueDef1 = Quality.newStringInstance(outputCaption1, outputDescr1, false,
                    Category.newInstance("- green, - protected area"),
                    Category.newInstance("- green, + protected area"),
                    Category.newInstance("+ green, - protected area"),
                    Category.newInstance("+ green, + protected area"));
            outputValueSet1 = BaseValueSet.new2DString();

            output1 = BaseOutput.newInstance(outputId1, outputCaption1, outputDescr1, this,
                    outputValueDef1, outputValueSet1);
            addOutput(output1);

            // create output for greenness change
            outputValueDef2 = Quantity.newIntegerInstance(outputCaption2, outputDescr2,
                    Unit.newPredefinedUnit(Unit.PredefinedUnits.DIMENSIONLESS));
            outputValueSet2 = BaseValueSet.new2DInt();

            output2 = BaseOutput.newInstance(outputId2, outputCaption2, outputDescr2, this,
                    outputValueDef2, outputValueSet2);
            addOutput(output2);

            // sample adapted output factories
            factory1 = new TestAdaptedOutputFactory1(this);
            factory2 = new TestAdaptedOutputFactory2(this);
            addAdaptedOutputFactory(factory1); // provides multipliers
            addAdaptedOutputFactory(factory2); // provides classifiers

            kmatrix = new HashMap<String, ICategory>();
        }


        private void initKnowledgeMatrix() {
            kmatrix = new HashMap<String, ICategory>();
            List<ICategory> dim1 = inputValueDef1.getCategories();
            List<ICategory> dim2 = inputValueDef2.getCategories();
            List<ICategory> val = outputValueDef1.getCategories();

            // protected area = none
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(0).getValue(), val.get(0));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(1).getValue(), val.get(0));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(2).getValue(), val.get(0));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(3).getValue(), val.get(0));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(4).getValue(), val.get(2));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(5).getValue(), val.get(2));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(6).getValue(), val.get(2));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(7).getValue(), val.get(0));
            kmatrix.put(dim1.get(0).getValue() + "|" + dim2.get(8).getValue(), val.get(2));

            // protected area = Designated areas only
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(0).getValue(), val.get(1));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(1).getValue(), val.get(1));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(2).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(3).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(4).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(5).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(6).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(7).getValue(), val.get(3));
            kmatrix.put(dim1.get(1).getValue() + "|" + dim2.get(8).getValue(), val.get(3));

            // protected area = Natura 2000 only
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(0).getValue(), val.get(1));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(1).getValue(), val.get(1));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(2).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(3).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(4).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(5).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(6).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(7).getValue(), val.get(3));
            kmatrix.put(dim1.get(2).getValue() + "|" + dim2.get(8).getValue(), val.get(3));

            // protected area = Designated areas + Natura 2000
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(0).getValue(), val.get(1));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(1).getValue(), val.get(1));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(2).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(3).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(4).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(5).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(6).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(7).getValue(), val.get(3));
            kmatrix.put(dim1.get(3).getValue() + "|" + dim2.get(8).getValue(), val.get(3));
        }


        @Override
        public void initialize() {
            super.initialize();
            initKnowledgeMatrix();
        }


        @Override
        public String[] validate() {
            ArrayList<String> messages = new ArrayList<String>();
            // verify knowledge data
            if (kmatrix == null) {
                messages.add("Knowledge matrix has not been initialised!");
            }
            return messages.toArray(new String[]{});
        }


        @Override
        public void prepare() {
            // void - nothing to do here
        }


        @Override
        public void update(IBaseOutput[] requiredOutputs) {
            // clear current output
            outputValueSet1.clear();
            outputValueSet2.clear();

            // check required outputs and see if processing is needed
            List<IBaseOutput> required;
            boolean process = false;
            if (requiredOutputs == null) {
                required = new ArrayList<IBaseOutput>();
                required.add(output1);
                required.add(output2);
                process = true;
            } else {
                required = Arrays.asList(requiredOutputs);
                process = required.contains(output1) || required.contains(output2);
            }

            if (!process) {
                return;
            }

            // determine minimum overlapping dimensions of input
            int vs1dim1Length = inputValueSet1.getIndexCount(new int[]{});
            int vs1dim2Length = inputValueSet1.getIndexCount(new int[]{0});
            int vs2dim1Length = inputValueSet2.getIndexCount(new int[]{});
            int vs2dim2Length = inputValueSet2.getIndexCount(new int[]{0});
            int dim1Length = Math.min(vs1dim1Length, vs2dim1Length);
            int dim2Length = Math.min(vs1dim2Length, vs2dim2Length);

            int i[] = new int[2];
            for (i[0] = 0; i[0] < dim1Length; i[0]++) {
                for (i[1] = 0; i[1] < dim2Length; i[1]++) {
                    String cat1 = inputValueSet1.getValue(i).toString();
                    String cat2 = inputValueSet2.getValue(i).toString();
                    ICategory val = kmatrix.get(cat1 + "|" + cat2);
                    if (val != null) {
                        // put result in output 1
                        if (required.contains(output1)) {
                            outputValueSet1.setValue(i, val.getValue().toString());
                        }
                        // translated result to change value and put in output 2
                        if (required.contains(output2)) {
                            int index = outputValueDef1.indexOf(val.getValue());
                            if (index == 0) {
                                outputValueSet2.setValue(i, -1);
                            } else if ((index == 1) || (index == 2)) {
                                outputValueSet2.setValue(i, 0);
                            } else if (index == 3) {
                                outputValueSet2.setValue(i, 1);
                            } else {
                                outputValueSet2.setValue(i, Integer.MIN_VALUE);
                            }
                        }
                    }
                }
            }

            // assure outputs are updated, since we changed the value sets directly
            output1.setValues(outputValueSet1, true, true);
            output2.setValues(outputValueSet2, true, true);
        }


        @Override
        public void finish() {
            kmatrix = null;
        }
    }


    /**
     * Sample adapted output factory that provides MultiplierAdaptedOutputs.
     */
    private class TestAdaptedOutputFactory1 extends BaseAdaptedOutputFactory {
        public TestAdaptedOutputFactory1(IBaseLinkableComponent owner) {
            super();
            addAdaptedOutput(MultiplierAdaptedOutput.newInstance(
                    "x10_adapter",
                    "multiplier_x10",
                    "Output adapter that multiplies integer values in a 2D matrix by 10.",
                    owner, 10));
            addAdaptedOutput(MultiplierAdaptedOutput.newInstance(
                    "x100_adapter",
                    "multiplier_x100",
                    "Output adapter that multiplies integer values in a 2D matrix by 100.",
                    owner, 100));
        }
    }


    /**
     * Sample adapted output factory that provides ClassificationAdaptedOutputs.
     */
    private class TestAdaptedOutputFactory2 extends BaseAdaptedOutputFactory {
        public TestAdaptedOutputFactory2(IBaseLinkableComponent owner) {
            super();
            addAdaptedOutput(ClassificationAdaptedOutput.newInstance(
                    "low_1000_medium_10000_high_classification_adapter",
                    "classify_{<1000;[1000-10000];>10000}",
                    "Output adapter that classifies " +
                            "quantity integer values in a 2D matrix into low-medium-high classes and " +
                            "provides them as a quality in a 2D matrix of type string. The " +
                            "classification considers values < 1000 as low, " +
                            "values [1000 - 10000] as medium, and values > 10000 as high.",
                    owner, 1000, 10000));
            addAdaptedOutput(ClassificationAdaptedOutput.newInstance(
                    "low_0_medium_0_high_classification_adapter",
                    "classify_{<0;[0];>0}",
                    "Output adapter that classifies " +
                            "quantity integer values in a 2D matrix into low-medium-high classes and " +
                            "provides them as a quality in a 2D matrix of type string. The " +
                            "classification considers values < 0 as low, " +
                            "values equal to 0 as medium, and values > 0 as high.",
                    owner, 0, 0));
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Notification received: " + o);
    }


    @Before
    public void SetUp() {
        // sample component
        component1 = new TestLinkableComponent(componentId1, componentCaption1, componentDescr1);
        component1.getStatusChangedObservable().addObserver(this);
        component1.addObserver(this);

        // create adapted output
        assertEquals(2, component1.getAdaptedOutputFactories().size());
        IIdentifiable[] ids = component1.getAdaptedOutputFactories().get(1).getAvailableAdapterIds
                (output2, null); // input == null to get all
        assertEquals(2, ids.length);

        output3 = component1.getAdaptedOutputFactories().get(1).createAdaptedOutput(ids[1],
                output2, null);
        assertNotNull(output3);

        // output should not be made part of the component
        assertEquals(2, component1.getOutputs().size());
    }


    @Test
    public void testInstanceCreation() {
        assertEquals(componentId1, component1.getId());
        assertEquals(componentCaption1, component1.getCaption());
        assertEquals(componentDescr1, component1.getDescription());

        assertNotNull(component1.getArguments());
        assertEquals(0, component1.getArguments().size());

        assertNotNull(component1.getInputs());
        assertEquals(2, component1.getInputs().size());

        assertNotNull(component1.getOutputs());
        assertEquals(2, component1.getOutputs().size());

        assertNotNull(component1.getAdaptedOutputFactories());
        assertEquals(2, component1.getAdaptedOutputFactories().size());
    }


    @Test
    public void testAccessors() {
        component1.setId("newId");
        assertEquals("newId", component1.getId());
        component1.setCaption("newCaption");
        assertEquals("newCaption", component1.getCaption());
        component1.setDescription("newDescription");
        assertEquals("newDescription", component1.getDescription());
    }


    @Test
    public void testEquals() {
        // identifiable object, only id should be tested for equals
        TestLinkableComponent component2 = new TestLinkableComponent(componentId1,
                componentCaption1, componentDescr1);
        assertEquals(component1, component2);
        component2.setCaption("newCaption");
        assertEquals(component1, component2);
        component2.setId("newId");
        assertNotSame(component1, component2);
    }


    @Test
    public void testExchangeItemsManagement() {
        // lists must exists contain the exchange items
        assertNotNull(component1.getInputs());
        assertEquals(2, component1.getInputs().size());
        assertNotNull(component1.getOutputs());
        assertEquals(2, component1.getOutputs().size());

        // do not accept duplicates
        assertFalse(component1.addInput(input1));
        assertEquals(2, component1.getInputs().size());
        assertFalse(component1.addOutput(output1));
        assertEquals(2, component1.getOutputs().size());

        // can only remove once
        assertTrue(component1.removeInput(input1));
        assertEquals(1, component1.getInputs().size());
        assertFalse(component1.removeInput(input1));
        assertTrue(component1.removeOutput(output1));
        assertEquals(1, component1.getOutputs().size());
        assertFalse(component1.removeOutput(output1));

        // same with exchange item
        assertTrue(component1.addExchangeItem(input1));
        assertTrue(component1.addExchangeItem(output1));
        assertEquals(2, component1.getInputs().size());
        assertEquals(2, component1.getOutputs().size());
        assertFalse(component1.addExchangeItem(input1));
        assertFalse(component1.addExchangeItem(output1));
        assertTrue(component1.removeExchangeItem(input1));
        assertTrue(component1.removeExchangeItem(output1));
        assertEquals(1, component1.getInputs().size());
        assertEquals(1, component1.getOutputs().size());
        assertFalse(component1.removeExchangeItem(input1));
        assertFalse(component1.removeExchangeItem(output1));

        // can not add exchange item from different component
        TestLinkableComponent component2 = new TestLinkableComponent("newId", "foo", "bar");
        BaseInput newInput = BaseInput.newInstance("newId", "foo", "bar", component2,
                inputValueDef1, inputValueSet1);
        assertFalse(component1.addInput(newInput));
        assertFalse(component1.addInput(null));

        BaseOutput newOutput = BaseOutput.newInstance("newId", "foo", "bar", component2,
                outputValueDef1, outputValueSet1);
        assertFalse(component1.addOutput(newOutput));
        assertFalse(component1.addOutput(null));
    }


    @Test
    public void testArgumentsManagement() {
        // list must exist and contain the argument
        assertNotNull(component1.getArguments());
        assertEquals(0, component1.getArguments().size());

        // can add sample argument
        Argument newArg = Argument.newOptionalReadOnlyInstance("foob", 42);
        assertTrue(component1.addArgument(newArg));
        assertEquals(1, component1.getArguments().size());
        assertEquals(newArg, component1.getArguments().get(0));

        // can not add duplicate arguments
        assertFalse(component1.addArgument(newArg));
        assertEquals(1, component1.getArguments().size());

        // can not alter arguments through list
        try {
            component1.getArguments().add(Argument.newOptionalInstance("test", "string"));
            fail();
        } catch (Exception e) {
            // ok
        }
    }


    @Test
    public void testAdaptedOutputFactoriesManagement() {
        List<IAdaptedOutputFactory> factories = component1.getAdaptedOutputFactories();
        assertNotNull(factories);
        assertEquals(2, factories.size());
        assertEquals(factory1, factories.get(0));
        assertEquals(factory2, factories.get(1));

        // can not add same factory
        assertFalse(component1.addAdaptedOutputFactory(factory1));
        assertEquals(2, component1.getAdaptedOutputFactories().size());

        // can remove existing factory
        assertTrue(component1.removeAdaptedOutputFactory(factory1));
        assertEquals(1, component1.getAdaptedOutputFactories().size());

        // can add factory
        assertTrue(component1.addAdaptedOutputFactory(factory1));
        assertEquals(2, component1.getAdaptedOutputFactories().size());
        assertEquals(factory2, factories.get(0));
        assertEquals(factory1, factories.get(1));
    }


    @Test
    public void testInitialization() {
        // TODO: how to test?
        component1.initialize();
    }


    @Test
    public void testCalculation() {
        // set data for input 1 (protected areas)
        inputValueSet1.clear();
        inputValueSet1.setValue(new int[]{0, 0}, inputValueDef1.get(0).getValue()); // None
        inputValueSet1.setValue(new int[]{0, 1}, inputValueDef1.get(2).getValue()); // Natura 2000
        inputValueSet1.setValue(new int[]{1, 0}, inputValueDef1.get(3).getValue()); // N2000 + CDDA
        inputValueSet1.setValue(new int[]{1, 1}, inputValueDef1.get(1).getValue()); // CDDA

        // set data for input 2 (landcover)
        inputValueSet2.clear();
        inputValueSet2.setValue(new int[]{0, 0}, inputValueDef2.get(0).getValue()); // Artificial Surfaces
        inputValueSet2.setValue(new int[]{0, 1}, inputValueDef2.get(6).getValue()); // Wetlands
        inputValueSet2.setValue(new int[]{1, 0}, inputValueDef2.get(4).getValue()); // Natural Grassland
        inputValueSet2.setValue(new int[]{1, 1}, inputValueDef2.get(7).getValue()); // Water bodies

        // run model
        component1.validate();
        component1.initialize();
        component1.prepare();
        component1.update(null);

        // retrieve output for output 1 (greenness classification strings)
        assertEquals(outputValueDef1.get(0).getValue(), outputValueSet1.getValue(new int[]{0, 0}));
        assertEquals(outputValueDef1.get(3).getValue(), outputValueSet1.getValue(new int[]{0, 1}));
        assertEquals(outputValueDef1.get(3).getValue(), outputValueSet1.getValue(new int[]{1, 0}));
        assertEquals(outputValueDef1.get(3).getValue(), outputValueSet1.getValue(new int[]{1, 1}));

        // retrieve output for output 2 (change as -1, 0, +1)
        assertEquals(-1, outputValueSet2.getValue(new int[]{0, 0}));
        assertEquals(+1, outputValueSet2.getValue(new int[]{0, 1}));
        assertEquals(+1, outputValueSet2.getValue(new int[]{1, 0}));
        assertEquals(+1, outputValueSet2.getValue(new int[]{1, 1}));

        // retrieve output for adapted output 3 (change as low, medium, high)
        Quality outputValueDef3 = (Quality) output3.getValueDefinition();
        BaseValueSet outputValueSet3 = (BaseValueSet) output3.getValues();
        assertEquals(outputValueDef3.get(0).getValue(), outputValueSet3.getValue(new int[]{0, 0}));
        assertEquals(outputValueDef3.get(2).getValue(), outputValueSet3.getValue(new int[]{0, 1}));
        assertEquals(outputValueDef3.get(2).getValue(), outputValueSet3.getValue(new int[]{1, 0}));
        assertEquals(outputValueDef3.get(2).getValue(), outputValueSet3.getValue(new int[]{1, 1}));
    }


    @Test
    public void testFinalization() {
        // TODO: how to test?
        component1.finish();
    }

}
