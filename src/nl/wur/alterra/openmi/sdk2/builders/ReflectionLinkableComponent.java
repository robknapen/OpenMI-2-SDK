package nl.wur.alterra.openmi.sdk2.builders;

import nl.wur.alterra.openmi.sdk2.backbone.BaseLinkableComponent;


/**
 * Linkable Component that configures itself by using reflection and parsing annotations in a
 * Java class.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class ReflectionLinkableComponent extends BaseLinkableComponent {


    public static ReflectionLinkableComponent newInstance(Class modelClass) {
        ReflectionLinkableComponent result = new ReflectionLinkableComponent();

        // TODO parse modelClass and configure linkable component accordingly
        // delegate IBaseLinkableComponent method calls to modelClass methods
        // lazy create an instance of modelClass for execution

        return result;
    }


    public ReflectionLinkableComponent() {
        super();
    }


}
