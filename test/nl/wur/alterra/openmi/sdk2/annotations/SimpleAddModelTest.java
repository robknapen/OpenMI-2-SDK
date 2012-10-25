package nl.wur.alterra.openmi.sdk2.annotations;

import nl.wur.alterra.openmi.sdk2.builders.CompositionBuilder;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Example of a very simple model and running it.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Model
public class SimpleAddModelTest {

    @In
    public int x;
    @In
    public int y;
    @Out
    public int sum;


    @Execute
    public void add() {
        sum = x + y;
    }


    @Test
    public void testSimpleAddModelRun() {
        Integer x = 40;
        Integer y = 2;
        Integer sum = new Integer(0);

        CompositionBuilder.build(SimpleAddModelTest.class).run(x, y, sum);
        assertEquals(42, sum.intValue());
    }

}
