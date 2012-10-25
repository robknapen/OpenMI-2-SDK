package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IBaseValueSet;

import java.lang.reflect.Array;


/**
 * One dimensional value set that stores values in an array of Objects. Somewhat flexible in
 * handling different types, but might cause a lot of garbage collection activity when used with
 * primitive types (because of auto-boxing).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseValueSet1D<E> extends OmiValueSet implements IBaseValueSet {

    // TODO: Add unit test for this class!

    // fields
    private E[] mValues;
    private int mCapacity;


    public BaseValueSet1D(Class<E> clazz, int capacity) {
        super();
        mCapacity = capacity;
        setValueType(clazz);
        mValues = (E[]) Array.newInstance(getValueType().getClass(), mCapacity);
    }


    @Override
    public void clear() {
        mValues = (E[]) Array.newInstance(getValueType().getClass(), mCapacity);
    }


    @Override
    public int getNumberOfIndices() {
        return 1;
    }


    @Override
    public int getIndexCount(int[] indices) {
        if ((indices == null) || (indices.length == 0)) {
            return mCapacity;
        } else {
            return -1;
        }
    }


    @Override
    public Object getValue(int[] indices) {
        if ((indices == null) || (indices.length != 1)) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }
        return mValues[indices[0]];
    }


    @Override
    public void setValue(int[] indices, Object value) {
        if ((indices == null) || (indices.length != 1)) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }
        if (!value.getClass().equals(getValueType())) {
            throw new IllegalArgumentException(INVALID_VALUE_TYPE);
        }

        mValues[indices[0]] = (E) value;
    }

}
