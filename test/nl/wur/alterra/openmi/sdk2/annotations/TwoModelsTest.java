package nl.wur.alterra.openmi.sdk2.annotations;

import nl.wur.alterra.openmi.sdk2.builders.CompositionBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class TwoModelsTest {

    @Model
    private class Constant {
        @Out
        public int c = 42;
    }


    @Model
    private class Multiply {
        @In
        public int c;
        @In
        public double var;
        @Out
        public double result;
    }


    @Test
    public void testTwoModelsRun() {
        double var = 5;
        Double result = new Double(Double.NaN);

        CompositionBuilder.build(new Constant(), new Multiply()).run(var, result);
        assertEquals(42 * 5, result);
    }
}
