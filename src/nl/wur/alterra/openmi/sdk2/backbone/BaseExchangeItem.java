package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IBaseExchangeItem;
import org.openmi.standard2.IBaseLinkableComponent;
import org.openmi.standard2.IBaseValueSet;
import org.openmi.standard2.IValueDefinition;

import java.util.Observable;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class BaseExchangeItem extends IdentifiableOmiObject implements IBaseExchangeItem {

    private Observable observable;
    private IBaseLinkableComponent component;
    private IValueDefinition valueDef;
    protected IBaseValueSet valueSet;


    public BaseExchangeItem(IBaseLinkableComponent owner, IValueDefinition valueDef,
                            IBaseValueSet valueSet) {
        super();
        observable = new Observable();
        component = owner;
        this.valueSet = valueSet;
        this.valueDef = valueDef;
    }


    @Override
    public IValueDefinition getValueDefinition() {
        return valueDef;
    }


    @Override
    public IBaseLinkableComponent getComponent() {
        return component;
    }


    public void setComponent(IBaseLinkableComponent component) {
        if (!nullEquals(this.component, component)) {
            this.component = component;
            sendObjectChangedNotification(this.component);
        }
    }


    @Override
    public Observable getItemChangedObservable() {
        return observable;
    }


    public void setValueDefinition(IValueDefinition valueDefinition) {
        if (!nullEquals(valueDef, valueDefinition)) {
            valueDef = valueDefinition;
            sendObjectChangedNotification(valueDef);
        }
    }
}
