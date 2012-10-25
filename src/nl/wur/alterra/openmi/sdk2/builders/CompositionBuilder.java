package nl.wur.alterra.openmi.sdk2.builders;

import nl.wur.alterra.openmi.sdk2.composition.Composition;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class CompositionBuilder {


    public static Composition build(Object... compositionParts) {

        // turn an array of objects into a valid OpenMI composition
        // each compositionPart should parse into a valid model component or connector
        // need real instances so that same model can be added multiple times
        // the builder can try to infer missing model in-out connections
        // can be simple at start by giving quantity / quality annotations an id element
        // it returns a composition object that can be executed
        // composition should have an initialize(Object[] arguments) method
        // arguments will be given to model objects in the initialize call

        // each linkable component should be runnable (?) and execute in its own thread
        // ... although at first parallel executing will probably not be implemented further

        return new Composition();

    }

}
