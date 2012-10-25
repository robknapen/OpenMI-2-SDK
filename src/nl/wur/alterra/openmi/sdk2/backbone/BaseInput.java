package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseInput extends BaseExchangeItem implements IBaseInput {

    // fields
    private IBaseOutput provider;


    public static BaseInput newInstance(String id, String caption, String description,
                                        IBaseLinkableComponent owner, IValueDefinition valueDef,
                                        IBaseValueSet valueSet) {
        BaseInput result = new BaseInput(owner, valueDef, valueSet);
        result.setId(id);
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public static BaseInput newInstanceWithRandomId(String caption, String description,
                                                    IBaseLinkableComponent owner,
                                                    IValueDefinition valueDef,
                                                    IBaseValueSet valueSet) {
        BaseInput result = new BaseInput(owner, valueDef, valueSet);
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public static BaseInput newInstance(IBaseInput baseInput) {
        BaseInput result = new BaseInput(baseInput.getComponent(), baseInput.getValueDefinition()
                , baseInput.getValues());
        result.setId(baseInput.getId());
        result.setCaption(baseInput.getCaption());
        result.setDescription(baseInput.getDescription());
        return result;
    }


    public BaseInput() {
        this(null, null, null);
    }


    public BaseInput(IBaseLinkableComponent owner, IValueDefinition valueDef,
                     IBaseValueSet valueSet) {
        super(owner, valueDef, valueSet);
        provider = null;
    }


    @Override
    public IBaseOutput getProvider() {
        return provider;
    }


    @Override
    public void setProvider(IBaseOutput provider) {
        if (!nullEquals(this.provider, provider)) {
            this.provider = provider;
            sendObjectChangedNotification(this.provider);
        }
    }


    @Override
    public IBaseValueSet getValues() {
        return valueSet;
    }


    @Override
    public void setValues(IBaseValueSet values) {
        if (!nullEquals(this.valueSet, values)) {
            this.valueSet = values;
            sendObjectChangedNotification(this.valueSet);
        }
    }
}
