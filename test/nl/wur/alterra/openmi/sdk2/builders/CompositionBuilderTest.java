package nl.wur.alterra.openmi.sdk2.builders;

import nl.wur.alterra.openmi.sdk2.annotations.Execute;
import nl.wur.alterra.openmi.sdk2.annotations.In;
import nl.wur.alterra.openmi.sdk2.annotations.Model;
import nl.wur.alterra.openmi.sdk2.annotations.Out;
import nl.wur.alterra.openmi.sdk2.composition.Composition;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * This unit test should prove the working of the CompositionBuilder. It attempts to use it to
 * create OpenMI compliant linkable components from a set of properly annotated model classes and
 * then execute the created composition (model chain) and verify the results (all rather simple
 * and strait forward).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class CompositionBuilderTest {

    @Model
    private class ConstantProvider {
        @Out
        public int constant;


        private ConstantProvider(int constant) {
            this.constant = constant;
        }


        @Execute
        public void setConstant() {
            // void
        }
    }


    @Model
    private class ConstantMultiplier {
        @In
        public int constant;
        @In
        public int multiplier;
        @Out
        public int result;


        private ConstantMultiplier(int multiplier) {
            this.multiplier = multiplier;
        }


        @Execute
        public void multiplyConstant() {
            result = multiplier * constant;
        }
    }


    @Test
    public void testCompositionBuilder() {
        Integer result = new Integer(0);

        Composition c = CompositionBuilder.build(
                new ConstantProvider(42), new ConstantMultiplier(5));
        c.run(result);
        assertEquals(42 * 5, result.intValue());
    }
}
