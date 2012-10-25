package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Implementation of IBaseOutput.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseOutput extends BaseExchangeItem implements IBaseOutput {

    // fields
    private List<IBaseInput> consumers;
    private List<IBaseAdaptedOutput> adaptedOutputs;


    public static BaseOutput newInstance(String id, String caption, String description,
                                         IBaseLinkableComponent owner,
                                         IValueDefinition valueDef, IBaseValueSet valueSet
    ) {
        BaseOutput result = new BaseOutput(owner, valueDef, valueSet);
        result.setId(id);
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public static BaseOutput newInstanceWithRandomId(String caption, String description,
                                                     IBaseLinkableComponent owner,
                                                     IValueDefinition valueDef, IBaseValueSet valueSet
    ) {
        BaseOutput result = new BaseOutput(owner, valueDef, valueSet);
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public static BaseOutput newInstance(IBaseOutput baseOutput) {
        BaseOutput result = new BaseOutput(baseOutput.getComponent(),
                baseOutput.getValueDefinition(), baseOutput.getValues());
        result.setId(baseOutput.getId());
        result.setCaption(baseOutput.getCaption());
        result.setDescription(baseOutput.getDescription());
        return result;
    }


    public BaseOutput() {
        this(null, null, null);
    }


    public BaseOutput(IBaseLinkableComponent owner, IValueDefinition valueDef,
                      IBaseValueSet valueSet) {
        super(owner, valueDef, valueSet);
        consumers = new ArrayList<IBaseInput>();
        adaptedOutputs = new ArrayList<IBaseAdaptedOutput>();
    }


    @Override
    public List<IBaseInput> getConsumers() {
        return Collections.unmodifiableList(consumers);
    }


    @Override
    public void addConsumer(IBaseInput consumer) {
        if ((consumer != null) && (!consumers.contains(consumer))) {
            consumer.setProvider(this);
            consumers.add(consumer);
            sendObjectChangedNotification(consumers);
        }
    }


    @Override
    public void removeConsumer(IBaseInput consumer) {
        if ((consumer != null) && (consumers.contains(consumer))) {
            consumers.remove(consumer);
            consumer.setProvider(null);
            sendObjectChangedNotification(consumers);
        }
    }


    @Override
    public List<IBaseAdaptedOutput> getAdaptedOutputs() {
        return Collections.unmodifiableList(adaptedOutputs);
    }


    @Override
    public void addAdaptedOutput(IBaseAdaptedOutput adaptedOutput) {
        if ((adaptedOutput != null) && (!adaptedOutputs.contains(adaptedOutput))) {
            adaptedOutputs.add(adaptedOutput);
            sendObjectChangedNotification(adaptedOutputs);
        }
    }


    @Override
    public void removeAdaptedOutput(IBaseAdaptedOutput adaptedOutput) {
        if ((adaptedOutput != null) && (adaptedOutputs.contains(adaptedOutput))) {
            adaptedOutputs.remove(adaptedOutput);
            sendObjectChangedNotification(adaptedOutputs);
        }
    }


    @Override
    public IBaseValueSet getValues() {
        return valueSet;
    }


    @Override
    public IBaseValueSet getValues(IBaseExchangeItem querySpecifier) {
        if (querySpecifier == null) {
            return getValues();
        } else {
            if (nullEquals(querySpecifier.getValueDefinition(), getValueDefinition())) {
                return valueSet;
            }
        }
        return null;
    }


    public void setValues(IBaseValueSet values, boolean forced, boolean notify) {
        if (forced || (!nullEquals(this.valueSet, values))) {
            this.valueSet = values;
            refreshAdaptedOutputs();
            if (notify) {
                sendObjectChangedNotification(this.valueSet);
            }
        }
    }


    public void setValue(int[] indices, Object value, boolean forced, boolean notify) {
        if (this.valueSet != null) {
            if (forced || (!nullEquals(valueSet.getValue(indices), value))) {
                valueSet.setValue(indices, value);
                refreshAdaptedOutputs();
                if (notify) {
                    sendObjectChangedNotification(this.valueSet);
                }
            }
        }
    }


    public void initializeAdaptedOutputs() {
        for (IBaseAdaptedOutput adaptedOutput : adaptedOutputs) {
            adaptedOutput.initialize();
        }
    }


    public void refreshAdaptedOutputs() {
        for (IBaseAdaptedOutput adaptedOutput : adaptedOutputs) {
            adaptedOutput.refresh();
        }
    }

}
