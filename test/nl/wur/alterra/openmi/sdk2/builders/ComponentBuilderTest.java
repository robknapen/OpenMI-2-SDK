package nl.wur.alterra.openmi.sdk2.builders;

import nl.wur.alterra.openmi.sdk2.annotations.Execute;
import nl.wur.alterra.openmi.sdk2.annotations.In;
import nl.wur.alterra.openmi.sdk2.annotations.Model;
import nl.wur.alterra.openmi.sdk2.annotations.Out;
import nl.wur.alterra.openmi.sdk2.backbone.BaseValueSet;
import org.junit.Test;
import org.openmi.standard2.IBaseLinkableComponent;
import org.openmi.standard2.IBaseValueSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * This unit test should prove the working of the ComponentBuilder. It attempts to use it to
 * create a OpenMI compliant linkable component from a properly annotated model class and then
 * execute this class and verify the results (all rather simple and strait forward).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class ComponentBuilderTest {

    @Model
    private class Calculator {
        @In
        public String input = "";
        @Out
        public String result = "";


        @Execute
        public void run() {
            result = input;
        }
    }


    @Test
    public void testComponentBuilder() {
        IBaseLinkableComponent c = ComponentBuilder.build(this.getClass());

        // component should have been created
        assertNotNull(c);

        // one input and one output should have been created
        assertEquals(1, c.getInputs().size());
        assertEquals(1, c.getOutputs().size());

        // create a valueset for testing
        String value = "ok";
        IBaseValueSet vs = new BaseValueSet();
        int[] indexes = new int[]{0};
        vs.setValue(indexes, value);

        // component should be able to update and produce result
        c.initialize();
        c.update(null);
        assertEquals(value, c.getOutputs().get(0).getValues().getValue(new int[]{0}));
        c.finish();
    }

}
