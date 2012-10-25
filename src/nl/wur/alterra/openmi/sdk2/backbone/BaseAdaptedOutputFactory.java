package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Base implementation of the IAdaptedOutputFactory class. Note that to work with this factory
 * an adapted output class has to be derived from the BaseAdaptedOutput class of this SDK. To
 * allow creating new instances it also can not be a nested class and it needs to provide a
 * nullary constructor (a constructor that has no arguments).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class BaseAdaptedOutputFactory extends IdentifiableOmiObject implements
        IAdaptedOutputFactory {


    private class AdapterInfo {
        public Class clazz;
        public BaseAdaptedOutput sampleInstance;
        public IIdentifiable id;
        public Arguments arguments;
    }


    private ArrayList<AdapterInfo> adapters;


    public BaseAdaptedOutputFactory() {
        super();
        adapters = new ArrayList<AdapterInfo>();
    }


    private void createSampleInstance(AdapterInfo ai) {
        ai.sampleInstance = null;
        if (ai.clazz != null) {
            try {
                Object obj = ai.clazz.newInstance();
                if (obj instanceof BaseAdaptedOutput) {
                    ai.sampleInstance = (BaseAdaptedOutput) obj;
                } else {
                    throw new OmiException("Trying to use a " + obj.getClass().getSimpleName() +
                            " as a BaseAdaptedOutput in the BaseAdaptedOutputFactory!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new OmiException("Could not create instance of requested AdaptedOutput! " +
                        e.getMessage());
            }
        }
    }


    private void prepareSampleInstance(AdapterInfo ai, IBaseOutput adaptee) {
        if (ai.sampleInstance == null) {
            createSampleInstance(ai);
        }
        ai.sampleInstance.setAdaptee(adaptee);
        assignArgumentsToAdaptedOutput(ai.sampleInstance, ai.arguments);
        ai.sampleInstance.initialize();
    }


    private void assignArgumentsToAdaptedOutput(BaseAdaptedOutput output, Arguments args) {
        List<IArgument> outputArgs = output.getArguments();
        for (IArgument outputArg : outputArgs) {
            int index = args.indexOfId(outputArg.getId());
            if (index >= 0) {
                outputArg.setValue(args.get(index).getValue());
            }
        }
    }


    @Override
    public IIdentifiable[] getAvailableAdapterIds(IBaseOutput adaptee, IBaseInput target) {
        ArrayList<IIdentifiable> result = new ArrayList<IIdentifiable>();
        for (AdapterInfo ai : adapters) {
            prepareSampleInstance(ai, adaptee);
            if ((target == null) || (ai.sampleInstance.canAdaptTo(target))) {
                result.add(ai.id);
            }
            ai.sampleInstance.setAdaptee(null);
        }
        return result.toArray(new IIdentifiable[]{});
    }


    @Override
    public IBaseAdaptedOutput createAdaptedOutput(IIdentifiable adaptedOutputId, IBaseOutput adaptee, IBaseInput target) {
        for (AdapterInfo ai : adapters) {
            if (ai.id.equals(adaptedOutputId)) {
                try {
                    // attempt to create new instance of this class
                    BaseAdaptedOutput newBao = (BaseAdaptedOutput) ai.clazz.newInstance();
                    newBao.setAdaptee(adaptee); // owner of adapter will be made same as adaptee
                    assignArgumentsToAdaptedOutput(newBao, ai.arguments);
                    // TODO: allow adapted output to adjust to specified input? (add method?)
                    return newBao;
                } catch (InstantiationException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                    throw new OmiException("Could not create new instance of AdaptedOutput", ex);
                } catch (IllegalAccessException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                    throw new OmiException("Could not create new instance of AdaptedOutput", ex);
                }
            }
        }
        return null;
    }


    public boolean containsAdapter(BaseAdaptedOutput adaptedOutput) {
        return indexOfAdapterInfoForAdaptedOutput(adaptedOutput) != -1;
    }


    public int indexOfAdapterInfoForAdaptedOutput(BaseAdaptedOutput adaptedOutput) {
        for (AdapterInfo ai : adapters) {
            if (ai.clazz.equals(adaptedOutput.getClass()) && hasArgumentsWithSameValues
                    (adaptedOutput, ai.arguments)) {
                return adapters.indexOf(ai);
            }
        }
        return -1;
    }


    public List<Class> getAvailableAdapters() {
        ArrayList<Class> result = new ArrayList<Class>();
        for (AdapterInfo ai : adapters) {
            result.add(ai.clazz);
        }
        return Collections.unmodifiableList(result);
    }


    private boolean addAdapterInfoForAdaptedOutput(BaseAdaptedOutput adaptedOutput) {
        if (!(containsAdapter(adaptedOutput))) {
            AdapterInfo ai = new AdapterInfo();
            ai.clazz = adaptedOutput.getClass();
            ai.id = adaptedOutput.getIdentifier();
            ai.sampleInstance = null;
            ai.arguments = Arguments.newCopiedInstance(adaptedOutput.getArguments());
            adapters.add(ai);
            return true;
        }
        return false;
    }


    private boolean hasArgumentsWithSameValues(BaseAdaptedOutput output, Arguments args) {
        List<IArgument> outputArgs = output.getArguments();
        if (args.size() != outputArgs.size()) {
            return false;
        }
        for (IArgument outputArg : outputArgs) {
            // check for arg with same id and same value (ignore other fields)
            Object value = args.getValueForId(outputArg.getId());
            if (!(outputArg.getValue().equals(value))) {
                return false;
            }
        }
        return true;
    }


    private boolean removeAdapterInfoForAdaptedOutput(BaseAdaptedOutput adaptedOutput) {
        int index = indexOfAdapterInfoForAdaptedOutput(adaptedOutput);
        if (index >= 0) {
            adapters.remove(index);
            return true;
        }
        return false;
    }


    public boolean addAdaptedOutput(BaseAdaptedOutput adaptedOutput) {
        return addAdapterInfoForAdaptedOutput(adaptedOutput);
    }


    public boolean removeAdaptedOutput(BaseAdaptedOutput adaptedOutput) {
        return removeAdapterInfoForAdaptedOutput(adaptedOutput);
    }


    public void removeAdaptedOutputs(Class<? extends BaseAdaptedOutput> clazz) {
        Iterator<AdapterInfo> iter = adapters.iterator();
        while (iter.hasNext()) {
            AdapterInfo ai = iter.next();
            if (ai.clazz.equals(clazz)) {
                iter.remove();
            }
        }
    }
}
