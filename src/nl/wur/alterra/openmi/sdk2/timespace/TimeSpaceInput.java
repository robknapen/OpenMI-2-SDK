package nl.wur.alterra.openmi.sdk2.timespace;

import nl.wur.alterra.openmi.sdk2.backbone.BaseInput;
import org.openmi.standard2.timespace.ISpatialDefinition;
import org.openmi.standard2.timespace.ITimeSet;
import org.openmi.standard2.timespace.ITimeSpaceInput;
import org.openmi.standard2.timespace.ITimeSpaceValueSet;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class TimeSpaceInput extends BaseInput implements ITimeSpaceInput {

    @Override
    public ITimeSet getTimeSet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public ISpatialDefinition getSpatialDefinition() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public ITimeSpaceValueSet getValues() {
        return null;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
