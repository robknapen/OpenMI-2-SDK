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
public class SoilData {

    @Quality(
            categories = "0=Grass|1=Forest|2=Sand|3=Water",
            description = "Soil type per cell."
    )
    private
    @Out
    int[][] outSoilType;


    @Initialize
    public void initialise() {
        // initialize the model for calculation -> connect to data store (file, DB, etc.)
    }


    @Execute
    public void execute() {
        // run the model -> makes data available in outputs
    }


    @Finalize
    public void finalize() {
        // finalize the model -> close data store connection
    }


}
