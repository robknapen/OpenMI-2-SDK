package nl.wur.alterra.openmi.sdk2.timespace;

import nl.wur.alterra.openmi.sdk2.backbone.BaseValueSet2D;
import org.openmi.standard2.timespace.ITimeSpaceValueSet;

import java.util.ArrayList;
import java.util.List;


/**
 * A two dimensional value set with one dimension representing time and one representing space.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class TimeSpaceValueSet<E> extends BaseValueSet2D<E> implements ITimeSpaceValueSet {

    // TODO: Add unit test for this class!

    // fields
    private int mTimeCapacity;
    private int mSpaceCapacity;


    public TimeSpaceValueSet(Class<E> clazz, int timeCapacity, int spaceCapacity) {
        super(clazz, timeCapacity, spaceCapacity);
        mTimeCapacity = timeCapacity;
        mSpaceCapacity = spaceCapacity;
    }


    @Override
    public List<List> getValues2D() {
        List<List> result = new ArrayList<List>();
        int[] index = new int[2];
        for (index[0] = 0; index[0] < mTimeCapacity; index[0]++) {
            List values = new ArrayList();
            for (index[1] = 0; index[1] < mSpaceCapacity; index[1]++) {
                values.add(getValue(index));
            }
        }
        return result;
    }


    @Override
    public void setValues2D(List<List> values) {
        clear();
        int[] index = new int[2];
        for (index[0] = 0; index[0] < mTimeCapacity; index[0]++) {
            List v = values.get(index[0]);
            for (index[1] = 0; index[1] < mSpaceCapacity; index[1]++) {
                setValue(index, v.get(index[1]));
            }
        }
    }


    @Override
    public Object getValue(int timeIndex, int elementIndex) {
        return super.getValue(new int[]{timeIndex, elementIndex});
    }


    @Override
    public void setValue(int timeIndex, int elementIndex, Object value) {
        super.setValue(new int[]{timeIndex, elementIndex}, value);
    }


    @Override
    public List getTimeSeriesValuesForElement(int elementIndex) {
        List result = new ArrayList();
        int[] index = new int[2];
        index[1] = elementIndex;
        for (index[0] = 0; index[0] < mTimeCapacity; index[0]++) {
            result.add(getValue(index));
        }
        return result;
    }


    @Override
    public void setTimeSeriesValuesForElement(int elementIndex, List values) {
        int[] index = new int[2];
        index[1] = elementIndex;
        for (index[0] = 0; index[0] < values.size(); index[0]++) {
            setValue(index, values.get(index[0]));
        }
    }


    @Override
    public List getElementValuesForTime(int timeIndex) {
        List result = new ArrayList();
        int[] index = new int[2];
        index[0] = timeIndex;
        for (index[1] = 0; index[1] < mSpaceCapacity; index[1]++) {
            result.add(getValue(index));
        }
        return result;
    }


    @Override
    public void setElementValuesForTime(int timeIndex, List values) {
        int[] index = new int[2];
        index[0] = timeIndex;
        for (index[1] = 0; index[1] < values.size(); index[1]++) {
            setValue(index, values.get(index[1]));
        }
    }
}
