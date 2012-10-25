package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;

import java.util.Collections;
import java.util.List;


/**
 * Implementation of the BaseAdaptedOutput.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class BaseAdaptedOutput extends BaseOutput implements IBaseAdaptedOutput {

    // fields
    private Arguments arguments;
    private IBaseOutput adaptee;


    public BaseAdaptedOutput() {
        this(null, null, null);
    }


    public BaseAdaptedOutput(IBaseLinkableComponent owner, IValueDefinition valueDef,
                             IBaseValueSet valueSet) {
        super(owner, valueDef, valueSet);
        arguments = new Arguments();
    }


    @Override
    public void initialize() {
        super.initializeAdaptedOutputs();
    }


    @Override
    public void refresh() {
        super.refreshAdaptedOutputs();
    }


    @Override
    public List<IArgument> getArguments() {
        return Collections.unmodifiableList(arguments);
    }


    @Override
    public IBaseOutput getAdaptee() {
        return adaptee;
    }


    @Override
    public void setAdaptee(IBaseOutput output) {
        if (!nullEquals(adaptee, output)) {
            if (adaptee != null) {
                adaptee.removeAdaptedOutput(this);
            }

            adaptee = output;
            setComponent(null);

            if (adaptee != null) {
                adaptee.addAdaptedOutput(this);
                setComponent(adaptee.getComponent());
            }

            sendObjectChangedNotification(adaptee);
        }
    }


    @Override
    public IBaseLinkableComponent getComponent() {
        if (adaptee != null) {
            return adaptee.getComponent();
        } else {
            return super.getComponent();
        }
    }


    /**
     * Checks if this AdaptedOutput can be used to adapt the values of its adaptee
     * (the BaseOutput it wraps) to values compatible with the specified input. The
     * base implementation compares the ValueDefinition of itself with that of the
     * input and returns true when they are equal or both null.
     *
     * @param input to check adaptability for
     * @return true if adapter is usable, false otherwise
     */
    public boolean canAdaptTo(IBaseInput input) {
        boolean result = false;
        if (input != null) {
            result = true;
            // check value definition, ignore when null
            if ((getValueDefinition() != null) && (input.getValueDefinition() != null)) {
                result = nullEquals(input.getValueDefinition(), getValueDefinition());
            }
            if (!result) {
                return false;
            } else {
                // check on value set types
                IBaseValueSet vs1 = input.getValues();
                IBaseValueSet vs2 = this.getValues();
                if ((vs1 != null) && (vs2 != null)) {
                    result = nullEquals(vs1.getValueType(), vs2.getValueType());
                }
            }
        }
        return result;
    }


    public boolean addArgument(IArgument arg) {
        return arguments.add(arg);
    }


    public boolean removeArgument(IArgument arg) {
        return arguments.remove(arg);
    }

}
