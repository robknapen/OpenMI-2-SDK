/*
 * Copyright (c) 2005-2011 Alterra, Wageningen UR, The Netherlands and the
 * OpenMI Association.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  - The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  - Neither the name of the OpenMI Association nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;


/**
 * Abstract root class for linkable components.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class BaseLinkableComponent extends IdentifiableOmiObject implements
        IBaseLinkableComponent {

    // constants
    private static final String MISSING_IMPLEMENTATION = "your derived model component needs to " +
            "provide an implementation for this method!";

    // fields
    private Arguments arguments;
    private LinkableComponentStatus status;
    private StatusChangedObservable statusChangedObservable;
    private List<IBaseInput> inputs;
    private List<IBaseOutput> outputs;
    private List<IAdaptedOutputFactory> adaptedOutputFactories;


    private class StatusChangedObservable extends Observable {

        public void sendNotification(IBaseLinkableComponent component,
                                     LinkableComponentStatus oldStatus,
                                     LinkableComponentStatus newStatus, String message) {

            LinkableComponentStatusChangeEventArgs args = new
                    LinkableComponentStatusChangeEventArgs();
            args.setLinkableComponent(component);
            args.setOldStatus(oldStatus);
            args.setNewStatus(newStatus);
            args.setMessage(message);

            setChanged();
            notifyObservers(args);
        }
    }


    public BaseLinkableComponent() {
        this("New Component", "");
    }


    public BaseLinkableComponent(String id, String caption, String description) {
        this(caption, description);
        setId(id);
    }


    public BaseLinkableComponent(String caption, String description) {
        super(caption, description);
        arguments = new Arguments();
        status = LinkableComponentStatus.CREATED;
        statusChangedObservable = new StatusChangedObservable();
        inputs = new ArrayList<IBaseInput>();
        outputs = new ArrayList<IBaseOutput>();
        adaptedOutputFactories = new ArrayList<IAdaptedOutputFactory>();
    }


    @Override
    public List<IArgument> getArguments() {
        return Collections.unmodifiableList(arguments);
    }


    @Override
    public LinkableComponentStatus getStatus() {
        return status;
    }


    @Override
    public Observable getStatusChangedObservable() {
        return statusChangedObservable;
    }


    @Override
    public List<IBaseInput> getInputs() {
        return Collections.unmodifiableList(inputs);
    }


    @Override
    public List<IBaseOutput> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }


    @Override
    public List<IAdaptedOutputFactory> getAdaptedOutputFactories() {
        return Collections.unmodifiableList(adaptedOutputFactories);
    }


    @Override
    public void initialize() {
        // initialize all assigned adapted outputs
        for (IBaseOutput output : outputs) {
            List<IBaseAdaptedOutput> adaptedOutputs = output.getAdaptedOutputs();
            for (IBaseAdaptedOutput adaptedOutput : adaptedOutputs) {
                adaptedOutput.initialize();
            }
        }
    }


    @Override
    public String[] validate() {
        // functionality MUST be provided by overwriting this method!
        throw OmiException.newNotImplementedException(this.getClass().getName(),
                "validate - " + MISSING_IMPLEMENTATION);
    }


    @Override
    public void prepare() {
        // functionality MUST be provided by overwriting this method!
        throw OmiException.newNotImplementedException(this.getClass().getName(),
                "prepare - " + MISSING_IMPLEMENTATION);
    }


    @Override
    public void update(IBaseOutput[] requiredOutputs) {
        // functionality MUST be provided by overwriting this method!
        throw OmiException.newNotImplementedException(this.getClass().getName(),
                "update - " + MISSING_IMPLEMENTATION);
    }


    @Override
    public void finish() {
        // functionality MUST be provided by overwriting this method!
        throw OmiException.newNotImplementedException(this.getClass().getName(),
                "finish - " + MISSING_IMPLEMENTATION);
    }


    protected boolean addArgument(IArgument arg) {
        return arguments.add(arg);
    }


    protected boolean removeArgument(IArgument arg) {
        return arguments.remove(arg);
    }


    protected boolean addExchangeItem(IBaseExchangeItem exchangeItem) {
        if (exchangeItem instanceof IBaseInput) {
            return addInput((IBaseInput) exchangeItem);
        } else if (exchangeItem instanceof IBaseOutput) {
            return addOutput((IBaseOutput) exchangeItem);
        }
        return false;
    }


    protected boolean removeExchangeItem(IBaseExchangeItem exchangeItem) {
        if (exchangeItem instanceof IBaseInput) {
            return removeInput((IBaseInput) exchangeItem);
        } else if (exchangeItem instanceof IBaseOutput) {
            return removeOutput((IBaseOutput) exchangeItem);
        }
        return false;
    }


    protected boolean addInput(IBaseInput input) {
        boolean result = false;
        if ((input != null) && (this.equals(input.getComponent())) && (!inputs.contains(input))) {
            result = inputs.add(input);
            if (result) {
                sendObjectChangedNotification(inputs);
            }
        }
        return result;
    }


    protected boolean removeInput(IBaseInput input) {
        boolean result = false;
        if (inputs.contains(input)) {
            result = inputs.remove(input);
            if (result) {
                sendObjectChangedNotification(inputs);
            }
        }
        return result;
    }


    protected boolean addOutput(IBaseOutput output) {
        boolean result = false;
        if ((output != null) && (this.equals(output.getComponent())) && (!outputs.contains(output))) {
            result = outputs.add(output);
            if (result) {
                sendObjectChangedNotification(outputs);
            }
        }
        return result;
    }


    protected boolean removeOutput(IBaseOutput output) {
        boolean result = false;
        if (outputs.contains(output)) {
            result = outputs.remove(output);
            if (result) {
                sendObjectChangedNotification(outputs);
            }
        }
        return result;
    }


    protected boolean addAdaptedOutputFactory(IAdaptedOutputFactory factory) {
        boolean result = false;
        if (!adaptedOutputFactories.contains(factory)) {
            result = adaptedOutputFactories.add(factory);
            if (result) {
                sendObjectChangedNotification(adaptedOutputFactories);
            }
        }
        return result;
    }


    protected boolean removeAdaptedOutputFactory(IAdaptedOutputFactory factory) {
        boolean result = false;
        if (adaptedOutputFactories.contains(factory)) {
            result = adaptedOutputFactories.remove(factory);
            if (result) {
                sendObjectChangedNotification(adaptedOutputFactories);
            }
        }
        return result;
    }


    private void updateStatus(LinkableComponentStatus newStatus, String message) {
        if (!status.equals(newStatus)) {
            LinkableComponentStatus oldStatus = status;
            status = newStatus;
            sendObjectChangedNotification(status);
            statusChangedObservable.sendNotification(this, oldStatus, status, message);
        }

    }

}
