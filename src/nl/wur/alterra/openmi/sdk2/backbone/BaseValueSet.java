package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IBaseValueSet;

import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * Ordered N-dimensional list of values. Note that this class is very generic and not optimized
 * for (high) performance. In case this is needed it is suggested to not derive from this class
 * but instead construct a more specialized version.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseValueSet extends OmiValueSet implements IBaseValueSet {

    private HashMap<String, Object> mValueMap;
    private HashMap<String, Integer> mIndexCountMap;
    private int mNumberOfIndices;


    public class CopyOperation implements INodeOperation {
        private IBaseValueSet target;


        public CopyOperation(IBaseValueSet targetValueSet) {
            target = targetValueSet;
        }


        @Override
        public boolean perform(int[] indices, Object value) {
            target.setValue(indices, value);
            return true;
        }
    }


    public static BaseValueSet newInstance(Type t, int numberOfIndices) {
        BaseValueSet result = new BaseValueSet();
        result.setValueType(t);
        result.setNumberOfIndices(numberOfIndices);
        return result;
    }


    public static BaseValueSet new1DInt() {
        return newInstance(Integer.class, 1);
    }


    public static BaseValueSet new2DInt() {
        return newInstance(Integer.class, 2);
    }


    public static BaseValueSet new1DDouble() {
        return newInstance(Double.class, 1);
    }


    public static BaseValueSet new2DDouble() {
        return newInstance(Double.class, 2);
    }


    public static BaseValueSet new1DString() {
        return newInstance(String.class, 1);
    }


    public static BaseValueSet new2DString() {
        return newInstance(String.class, 2);
    }


    public static BaseValueSet newInstance(IBaseValueSet valueSet) {
        BaseValueSet result = new BaseValueSet();
        result.setValueType(valueSet.getValueType());
        result.setNumberOfIndices(valueSet.getNumberOfIndices());

        if (valueSet instanceof BaseValueSet) {
            result.copyValues((BaseValueSet) valueSet);
        } else {
            // recursively copy all values
            int[] indices = new int[valueSet.getNumberOfIndices()];
            int indexCount = valueSet.getIndexCount(new int[]{});
            for (int i = 0; i < indexCount; i++) {
                indices[0] = i;
                copyValues(valueSet, result, 0, indices);
            }
        }
        return result;
    }


    private static void copyValues(IBaseValueSet source, IBaseValueSet target, int indexPos,
                                   int[] indices) {
        if (indexPos == indices.length - 1) {
            target.setValue(indices, source.getValue(indices));
        } else {
            int nextPos = indexPos + 1;
            int[] subset = new int[nextPos];
            for (int j = 0; j < nextPos; j++) {
                subset[j] = indices[j];
            }
            int indexCount = source.getIndexCount(subset);
            for (int i = 0; i < indexCount; i++) {
                indices[nextPos] = i;
                copyValues(source, target, nextPos, indices);
            }
        }
    }


    private void copyValues(BaseValueSet sourceValueSet) {
        INodeOperation copyOp = new CopyOperation(this);
        sourceValueSet.performNodeOperation(copyOp);
    }


    public BaseValueSet() {
        super();
        mValueMap = new HashMap<String, Object>();

        mNumberOfIndices = 1;
        mIndexCountMap = new HashMap<String, Integer>();

        mIndexCountMap.put(indexCountMapKey(new int[]{0}), 0);
    }


    private String valueMapKey(int[] indices) {
        StringBuilder key = new StringBuilder();
        for (int index : indices) {
            if (key.length() > 0) {
                key.append("_");
            }
            key.append(index);
        }
        return key.toString();
    }


    private String indexCountMapKey(int[] indices) {
        StringBuilder key = new StringBuilder();
        if ((indices == null) || indices.length == 0) {
            key.append("*");
        } else {
            for (int index : indices) {
                if (key.length() > 0) {
                    key.append("_");
                }
                key.append(index);
            }
        }
        return key.toString();
    }


    public void clear() {
        mValueMap.clear();
        mIndexCountMap.clear();
    }


    @Override
    public int getNumberOfIndices() {
        return mNumberOfIndices;
    }


    public void setNumberOfIndices(int numberOfIndices) {
        if (this.mNumberOfIndices != numberOfIndices) {
            clear();
            this.mNumberOfIndices = numberOfIndices;
        }
    }


    @Override
    public int getIndexCount(int[] indices) {
        Integer val = mIndexCountMap.get(indexCountMapKey(indices));
        return (val == null) ? -1 : val;
    }


    private void setIndexCount(int[] indices, int indexCount) {
        mIndexCountMap.put(indexCountMapKey(indices), indexCount);
    }


    @Override
    public Object getValue(int[] indices) {
        if (indices.length != mNumberOfIndices) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }

        Object val = mValueMap.get(valueMapKey(indices));
        return val;
    }


    @Override
    public void setValue(int[] indices, Object value) {
        if (indices.length != mNumberOfIndices) {
            throw new IllegalArgumentException(INVALID_INDICES_SIZE);
        }

        if (!value.getClass().equals(getValueType())) {
            throw new IllegalArgumentException(INVALID_VALUE_TYPE);
        }

        // store value
        mValueMap.put(valueMapKey(indices), value);

        // update max index counts
        for (int i = indices.length - 1; i >= 0; i--) {
            int[] subset = new int[i];
            for (int j = 0; j < i; j++) {
                subset[j] = indices[j];
            }
            int indexCount = getIndexCount(subset);
            if (indices[i] + 1 > indexCount) {
                setIndexCount(subset, indices[i] + 1);
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseValueSet)) return false;

        BaseValueSet that = (BaseValueSet) o;

        if (mNumberOfIndices != that.mNumberOfIndices) return false;
        if (mIndexCountMap != null ? !mIndexCountMap.equals(that.mIndexCountMap) : that.mIndexCountMap != null)
            return false;
        if (mValueMap != null ? !mValueMap.equals(that.mValueMap) : that.mValueMap != null)
            return false;
        if (getValueType() != null ? !getValueType().equals(that.getValueType()) : that.getValueType() !=
                null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = getValueType() != null ? getValueType().hashCode() : 0;
        result = 31 * result + (mValueMap != null ? mValueMap.hashCode() : 0);
        result = 31 * result + (mIndexCountMap != null ? mIndexCountMap.hashCode() : 0);
        result = 31 * result + mNumberOfIndices;
        return result;
    }
}
