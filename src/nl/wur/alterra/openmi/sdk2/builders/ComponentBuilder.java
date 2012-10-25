package nl.wur.alterra.openmi.sdk2.builders;

import org.openmi.standard2.IBaseLinkableComponent;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class ComponentBuilder {

    public static IBaseLinkableComponent build(Class model) {

        // TODO: write implementation
        // create instance of specified model class
        // parse annotations in model object and use it to create a standard IBaseLinkableComponent
        // should throw exceptions when failed
        // TODO: decide how to do logging (Apache log4j)

        // verify and parse general model info
        // verify general IEF annotations (must be present)
        // create instance of model class and instance of wrapping linkable component
        // add model to component and delegate IEF methods from component to model
        // parse input annotations in model and create input exchange items in component
        // parse output annotations in model and create output exchange items in component
        // parse argument annotations in model and create arguments in component

        return ReflectionLinkableComponent.newInstance(model);

    }


    // How this could work:
    // The argument objects should contain fields marked as @Arg
    // matching values will be assigned to the @arg marked field
    // in this model. Previously set values will be overwritten.

    // Note: this code should go in the SDK and probably should
    // be handled by the LinkableComponent wrapper, which after
    // filling in the models arguments calls its init method, and
    // no parameters are needed here.

    // SDK could also pass key-value pairs of arguments.
    /*

        Object[] arguments = null;
        for (Object argument : arguments) {
            Field[] fields = argument.getClass().getDeclaredFields();
            for (Field field : fields) {
                Annotation a = field.getAnnotation(Arg.class);
                if (a instanceof Arg) {
                    // an outside loop for the models arguments should
                    // be added to avoid naming fields directly here
                    if (((Arg)a).value().equals("heightFactor")) {
                        try {
                            argHeightMultiplier = field.getDouble(argument);
                        } catch (IllegalAccessException ex) {
                            // field not accessible
                        }
                    }
                }

            }
        }
    */
}
