package nl.wur.alterra.openmi.sdk2.timespace;

import nl.wur.alterra.openmi.sdk2.backbone.BaseLinkableComponent;
import org.openmi.standard2.timespace.ITimeSet;
import org.openmi.standard2.timespace.ITimeSpaceComponent;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class TimeSpaceComponent extends BaseLinkableComponent implements ITimeSpaceComponent {

    @Override
    public ITimeSet getTimeExtent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
