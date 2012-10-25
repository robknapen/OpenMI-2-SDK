package nl.wur.alterra.openmi.sdk2.samples.annotations;

import nl.wur.alterra.openmi.sdk2.annotations.*;
import org.openmi.standard2.timespace.IElementSet;
import org.openmi.standard2.timespace.ITimeSet;


/**
 * Sample annotated POJO model class for testing the usefulness of the annotations.
 * <p/>
 * General idea:
 * Make the OpenMI 2 SDK more lightweight by hiding as much boilerplate code as possible
 * behind annotations and convention over configuration. This should make the framework
 * as non-invasive as possible.
 * <p/>
 * A properly annotated model can be give to a ComponentBuilder that uses
 * reflection to parse the code and generate an OpenMI 2 compatible ILinkableComponent
 * instance, using functionality of the SDK:
 * <p/>
 * ILinkableComponent lc = ComponentBuilder(SuitabilityModel.class);
 * <p/>
 * A builder can also be implemented to create an OpenMI composition of a list of models:
 * <p/>
 * OmiComposition c = CompositionBuilder(SuitabilityModel.class, HeightData.class, SoilData.class);
 * <p/>
 * The builders should try to figure out as much as possible automatically. E.g. use the
 * variable name as id for an input or output, check for matching data types, create
 * automatic references (links) between models. When needed explicit connectors (and
 * input-output adapters) can be added to the builder. Based on annotations the builder
 * knows what type of object is intended and how to handle it. Last resort would be to
 * use @In and @Out ids to find matches.
 * <p/>
 * The OmiComposition should have an initialize(Object... args) method to prepare the
 * whole composition and set the arguments for all included components. Annotations should
 * help to assign the proper argument values to each model.
 * <p/>
 * The builder should verify the annotations. @Initialize and @Finalize are optional,
 * but @Model and @Execute are required.
 * <p/>
 * System arguments are @Arg variables with a predefined name and type. When they match the
 * framework runtime will fill in model calculation context information, before calling the
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 * @Execute method of the model.
 * @Quantity and @Quality can be used to provide OpenMI specific variable meta-data. When
 * available on both sides of a model connection they will be validated. When not only the
 * data type will be compared. Other kinds of meta-data (semantic) can later be added.
 * <p/>
 * The linkable component created for a model should execute in its own thread.
 */
@Model(
        author = "Rob Knapen, Wageningen UR",
        description = "Sample model for testing purposes only.",
        status = Status.TEST
)
public class SuitabilityModel {

    @Quantity(unit = "m")
    @In
    private double[][] inHeight;

    @Quality(categories = "0=Grass|1=Forest|2=Sand|3=Water")
    @In
    private int[][] inSoilType;

    @Quality(categories = "0=ok|1=bad")
    @Out
    private int[][] outSuitability;

    // model arguments
    @Arg
    private double argHeightMultiplier;

    // system arguments (could use native or OGC types as well)
    @Arg("_outputs")
    private String[] calculationOutputIds;
    @Arg("_time")
    private ITimeSet calculationTimes;
    @Arg("_space")
    private IElementSet calculationSpace;


    @Initialize
    public void initialize() {
        // Initialize the model (optional method). The framework will
        // set all model specific arguments and then call this method
        // so the model can do its own initialization.
    }


    @Execute
    public void execute() {
        // Run the model calculation based on the context that the
        // framework will inject as the system arguments (_outputs,
        // _time, _space) before calling this method. By default
        // everything should be calculated.
    }


    @Finalize
    public void finalize() {
        // Free all resources used by the model (optional method).
    }

}
