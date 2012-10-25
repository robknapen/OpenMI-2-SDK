package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;


/**
 * This class provides a sample AdaptedOutput for testing purposes. The adapted
 * output contains a multiplier argument and on a refresh call it retrieves the
 * values from the adaptee and applies the multiplier to each value to create a
 * new value set.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class MultiplierAdaptedOutput extends BaseAdaptedOutput {

    public static final String ARG_MULTIPLIER = "multiplier";

    // fields
    private int multiplier = 1;
    private IArgument multiplierArg;
    private BaseValueSet adaptedValues;


    private class MultiplierIntOp implements BaseValueSet.INodeOperation {
        @Override
        public boolean perform(int[] indices, Object value) {
            int val = multiplier * (Integer) value;
            adaptedValues.setValue(indices, val);
            return true;
        }
    }


    public static MultiplierAdaptedOutput newInstance(String id, String caption,
                                                      String description,
                                                      IBaseLinkableComponent owner,
                                                      int multiplier) {
        MultiplierAdaptedOutput result = new MultiplierAdaptedOutput(id, caption, owner,
                multiplier);
        result.setDescription(description);
        return result;
    }


    public MultiplierAdaptedOutput() {
        this(null, "multiplier_adapted_output", null, 1);
    }


    public MultiplierAdaptedOutput(String id, String caption, IBaseLinkableComponent owner,
                                   int multiplier) {
        super(owner, null, null);
        if (id != null) {
            setId(id);
        }
        setCaption(caption);

        this.adaptedValues = BaseValueSet.new2DInt();
        this.multiplier = multiplier;

        multiplierArg = Argument.newInstance(ARG_MULTIPLIER, "multiplier", "",
                multiplier, true, false);
        addArgument(multiplierArg);
    }


    @Override
    public void initialize() {
        multiplier = (Integer) multiplierArg.getValue();
    }


    @Override
    public void refresh() {
        if (getAdaptee() != null) {
            adaptedValues = BaseValueSet.newInstance(getAdaptee().getValues());
            adaptedValues.performNodeOperation(new MultiplierIntOp());
        }
        super.refresh();
    }


    @Override
    public IBaseValueSet getValues() {
        return adaptedValues;
    }


    public int getMultiplier() {
        return multiplier;
    }


    @Override
    public void setAdaptee(IBaseOutput output) {
        // validate if this adapter can work with the specified output
        if ((output != null) && (output.getValueDefinition() != null)) {
            IValueDefinition vd = output.getValueDefinition();
            if (vd.getValueType().equals(Integer.TYPE)) {
                throw new OmiException("This adapter can only work on outputs that provide " +
                        "data of type Integer, not " + vd.getValueType());
            }
        }
        super.setAdaptee(output);
    }

}
