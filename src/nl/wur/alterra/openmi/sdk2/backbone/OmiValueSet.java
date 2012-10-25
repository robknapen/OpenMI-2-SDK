package nl.wur.alterra.openmi.sdk2.backbone;

import java.lang.reflect.Type;


/**
 * Root class for value set implementations.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class OmiValueSet {

    // messages
    protected static final String INVALID_INDICES_SIZE = "Specified number of indices does not " +
            "match value set dimensions.";
    protected static final String INVALID_VALUE_TYPE = "Can not store value with mismatching type in value " +
            "set";


    // fields
    private Type mValueType;


    public OmiValueSet() {
        mValueType = Object.class;
    }


    public Type getValueType() {
        return mValueType;
    }


    public void setValueType(Type t) {
        if (mValueType != t) {
            clear();
            mValueType = t;
        }
    }


    public abstract void clear();

    public abstract int getNumberOfIndices();

    public abstract int getIndexCount(int[] indices);

    public abstract Object getValue(int[] indices);


    // interface for performing an operation on each value set item
    public interface INodeOperation {
        public boolean perform(int[] indices, Object value);
    }


    public void performNodeOperation(INodeOperation operation) {
        int[] indices = new int[getNumberOfIndices()];
        int indexCount = getIndexCount(new int[]{});
        for (int i = 0; i < indexCount; i++) {
            indices[0] = i;
            visitNodes(0, indices, operation);
        }
    }


    public void visitNodes(int indexPos, int[] indices, INodeOperation operation) {
        if (indexPos == indices.length - 1) {
            // TODO handle error result
            operation.perform(indices, getValue(indices));
        } else {
            int nextPos = indexPos + 1;
            int[] subset = new int[nextPos];
            for (int j = 0; j < nextPos; j++) {
                subset[j] = indices[j];
            }
            int indexCount = getIndexCount(subset);
            for (int i = 0; i < indexCount; i++) {
                indices[nextPos] = i;
                visitNodes(nextPos, indices, operation);
            }
        }
    }

}
