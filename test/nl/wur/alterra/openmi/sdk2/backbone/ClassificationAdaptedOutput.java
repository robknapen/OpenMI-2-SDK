package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;


/**
 * This class provides a sample AdaptedOutput for testing purposes. The adapted
 * output contains a classification argument and on a refresh call it retrieves the
 * values from the adaptee and classifies each value.
 * new value set.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class ClassificationAdaptedOutput extends BaseAdaptedOutput {

    // names of arguments
    public static final String ARG_LOW_BOUND = "low_bound";
    public static final String ARG_HIGH_BOUND = "high_bound";

    // fields
    private String[] classifications = {"low", "medium", "high"};
    private int lowBound = Integer.MIN_VALUE;
    private int highBound = Integer.MAX_VALUE;
    private IArgument lowBoundArg;
    private IArgument highBoundArg;
    private BaseValueSet adaptedValues;


    /**
     * Classification operation. It expects an integer value and will produce the matching
     * classification. Source and target value sets are expected to have the same dimensions.
     */
    private class ClassificationOp implements BaseValueSet.INodeOperation {
        @Override
        public boolean perform(int[] indices, Object value) {
            String classification = classifications[1];
            if ((Integer) value < lowBound) {
                classification = classifications[0];
            } else if ((Integer) value > highBound) {
                classification = classifications[2];
            }
            adaptedValues.setValue(indices, classification);
            return true;
        }
    }


    public static ClassificationAdaptedOutput newInstance(String id, String caption,
                                                          String description,
                                                          IBaseLinkableComponent owner,
                                                          int lowBound, int highBound) {
        ClassificationAdaptedOutput result = new ClassificationAdaptedOutput(id, caption, owner,
                lowBound, highBound);
        result.setDescription(description);
        return result;
    }


    /**
     * Nullary constructor, required to be able to use the adapter with the adapted output factory.
     */
    public ClassificationAdaptedOutput() {
        this(null, "classification_adapted_output", null, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    /**
     * Creates an instance based on the specified parameters. The adapted output has a quality
     * as value definition that has three categories (low - medium - high), and a value set that
     * holds a 2D matrix of String type values. The numerical bounds for the classification can
     * be set by the two provided arguments ARG_LOW_BOUND and ARG_HIGH_BOUND.
     *
     * @param id
     * @param caption
     * @param owner
     * @param lowBound
     * @param highBound
     */
    public ClassificationAdaptedOutput(String id, String caption,
                                       IBaseLinkableComponent owner,
                                       int lowBound,
                                       int highBound) {
        super(owner, null, null);
        if (id != null) {
            setId(id);
        }
        setCaption(caption);

        this.adaptedValues = BaseValueSet.new2DString();
        this.lowBound = lowBound;
        this.highBound = highBound;

        // create arguments
        lowBoundArg = Argument.newInstance(ARG_LOW_BOUND, "low classification bound", "",
                lowBound, true, false);
        addArgument(lowBoundArg);
        highBoundArg = Argument.newInstance(ARG_HIGH_BOUND, "high classification bound", "",
                highBound, true, false);
        addArgument(highBoundArg);

        // create value definition
        Quality q = Quality.newStringInstance("classification", "Three classes classification " +
                "based on an lower and higher bound", true,
                Category.newInstance(classifications[0]),
                Category.newInstance(classifications[1]),
                Category.newInstance(classifications[2]));
        setValueDefinition(q);
    }


    @Override
    public void initialize() {
        lowBound = (Integer) lowBoundArg.getValue();
        highBound = (Integer) highBoundArg.getValue();
    }


    @Override
    public void refresh() {
        adaptedValues.clear();
        if ((getAdaptee() != null) && (getAdaptee().getValues() instanceof BaseValueSet)) {
            // This adapter uses a visitor that visits the value set of the adaptee, calculates
            // the adapted values from it and stores them in the value set of the adapter.
            ((BaseValueSet) (getAdaptee().getValues())).performNodeOperation(new ClassificationOp());
        }
        super.refresh();
    }


    /**
     * Note that this adapted output has its own value set and does not use the value set defined
     * in the parent BaseExchangeItem (which limits the type to a IBaseValueSet and the visitor
     * pattern for processing value sets is only available in the BaseValueSet class).
     *
     * @return
     */
    @Override
    public IBaseValueSet getValues() {
        return adaptedValues;
    }


    public double getLowBound() {
        return lowBound;
    }


    public double getHighBound() {
        return highBound;
    }


    /**
     * Additional check if the output being set as adaptee for the adapted output can be used.
     *
     * @param output
     */
    @Override
    public void setAdaptee(IBaseOutput output) {
        // validate if this adapter can work with the specified output
        if (output != null) {
            IValueDefinition vd = output.getValueDefinition();
            if (vd.getValueType().equals(Integer.TYPE)) {
                throw new OmiException("This adapter can only work on outputs that provide " +
                        "data of type Integer, not " + vd.getValueType());
            }
        }
        super.setAdaptee(output);
    }
}
