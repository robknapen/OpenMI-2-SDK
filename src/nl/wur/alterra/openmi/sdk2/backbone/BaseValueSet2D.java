package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IBaseValueSet;

import java.lang.reflect.Array;


/**
 * Two dimensional value set that stores values in an array of Objects. Somewhat flexible in
 * handling different types, but might cause a lot of garbage collection activity when used
 * with primitive data types (because of auto-boxing).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseValueSet2D<E> extends OmiValueSet implements IBaseValueSet {

    // TODO: Add unit test for this class!

    // fields
    private E[][] mValues;
    private int mCapacity1;
    private int mCapacity2;


    public BaseValueSet2D(Class<E> clazz, int capacity1, int capacity2) {
        super();
        mCapacity1 = capacity1;
        mCapacity2 = capacity2;
        setValueType(clazz);
        mValues = (E[][]) Array.newInstance(getValueType().getClass(), capacity1, capacity2);
    }


    @Override
    public void setValue(int[] indices, Object value) {
        if ((indices == null) || (indices.length != 2)) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }
        if (!value.getClass().equals(getValueType())) {
            throw new IllegalArgumentException(INVALID_VALUE_TYPE);
        }

        mValues[indices[0]][indices[1]] = (E) value;
    }


    @Override
    public void clear() {
        mValues = (E[][]) Array.newInstance(getValueType().getClass(), mCapacity1, mCapacity2);
    }


    @Override
    public int getNumberOfIndices() {
        return 2;
    }


    @Override
    public int getIndexCount(int[] indices) {
        if ((indices == null) || (indices.length == 0)) {
            return mCapacity1;
        } else if ((indices != null) && (indices.length == 1) && (indices[0] < mCapacity1)) {
            return mCapacity2;
        }
        return -1;
    }


    @Override
    public Object getValue(int[] indices) {
        if ((indices == null) || (indices.length != 2)) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }
        return mValues[mCapacity1][mCapacity2];
    }
}
