package nl.wur.alterra.openmi.sdk2.samples.annotations;

import nl.wur.alterra.openmi.sdk2.annotations.*;


/**
 * Sample annotated POJO model class for testing.
 * <p/>
 * Represents data, has no inputs.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Model(
        author = "Rob Knapen",
        description = "Sample model for testing purposes only.",
        status = Status.TEST
)
public class HeightData {

    @Quantity(
            unit = "m",
            description = "Cell based average height of area."
    )
    private
    @Out
    double[][] outHeight;


    @Initialize
    public void initialise() {
        // initialize the model for calculation
    }


    @Execute
    public void execute() {
        // run the model -> makes data available in outputs
    }


    @Finalize
    public void finalize() {
        // finalize the model
    }


}
