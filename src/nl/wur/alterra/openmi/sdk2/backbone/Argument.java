/*
 * Copyright (c) 2005-2012 Alterra, Wageningen UR, The Netherlands and the
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

import nl.wur.alterra.openmi.sdk2.extras.Stringifiable;
import org.openmi.standard2.IArgument;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Argument defines a value (Object) with some characteristics.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Argument extends IdentifiableOmiObject implements IArgument {

    // messages
    private static final String ARGUMENT_ALREADY_HAS_A_NON_MATCHING_VALUE = "Argument already has a value that does not match with the new possible values being set.";
    private static final String ARGUMENT_ALREADY_HAS_A_NON_MATCHING_DEFAULT_VALUE = "Argument already has a default value that does not match with the new possible values being set.";
    private static final String ARGUMENT_IS_READ_ONLY = "Argument is read only, can not change it's value.";
    private static final String DONT_KNOW_HOW_TO_SET_THE_ARGUMENT_VALUE_OF_TYPE = "Don't know how to set the Argument value of Type ";

    // fields
    private Object value;
    private Object defaultValue;
    private List<?> possibleValues;
    private boolean optional;
    private boolean readOnly;


    public static Argument newInstance(IArgument argument) {
        Argument result = new Argument();
        if (argument != null) {
            result.setId(argument.getId());
            result.setCaption(argument.getCaption());
            result.setDescription(argument.getDescription());

            result.value = argument.getValue();
            result.defaultValue = argument.getDefaultValue();
            result.possibleValues = argument.getPossibleValues();
            result.optional = argument.isOptional();
            result.readOnly = argument.isReadOnly();
        }
        return result;
    }


    public static Argument newInstance(String id, String caption, String description,
                                       Object value, boolean optional,
                                       boolean readOnly) {
        Argument result = new Argument();

        result.setId(id);
        result.setCaption(caption);
        result.setDescription(description);

        result.value = value;
        result.defaultValue = value;
        result.possibleValues = new ArrayList<Object>();
        result.optional = optional;
        result.readOnly = readOnly;
        return result;
    }


    public static Argument newOptionalInstance(String id, Object value) {
        Argument result = new Argument();

        result.setId(id);
        result.setCaption(id);
        result.setDescription("");

        result.value = value;
        result.defaultValue = value;
        result.possibleValues = new ArrayList<Object>();
        result.optional = true;
        result.readOnly = false;
        return result;
    }


    public static Argument newOptionalReadOnlyInstance(String id, Object value) {
        Argument result = new Argument();

        result.setId(id);
        result.setCaption(id);
        result.setDescription("");

        result.value = value;
        result.defaultValue = value;
        result.possibleValues = new ArrayList<Object>();
        result.optional = true;
        result.readOnly = true;
        return result;
    }


    public static Argument newInstanceWithRandomId(String caption, String description,
                                                   Object value, boolean optional,
                                                   boolean readOnly) {
        Argument result = new Argument();

        result.setCaption(caption);
        result.setDescription(description);

        result.value = value;
        result.defaultValue = value;
        result.possibleValues = new ArrayList<Object>();
        result.optional = optional;
        result.readOnly = readOnly;
        return result;
    }


    public Argument() {
        super();
        value = null;
        defaultValue = null;
        optional = false;
        readOnly = false;
        possibleValues = new ArrayList<Object>();
    }


    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(Object defaultValue) {
        if (isAllowedValue(defaultValue)) {
            if (!nullEquals(this.defaultValue, defaultValue)) {
                this.defaultValue = defaultValue;
                sendObjectAddedNotification(this.defaultValue);
            }
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getPossibleValues() {
        return Collections.unmodifiableList(possibleValues);
    }


    public void setPossibleValues(List<?> possibleValues) {
        if ((possibleValues != null) && (possibleValues.size() > 0)) {
            if ((value != null) && !possibleValues.contains(value))
                throw new IllegalStateException(ARGUMENT_ALREADY_HAS_A_NON_MATCHING_VALUE);
            if ((defaultValue != null) && !possibleValues.contains(defaultValue))
                throw new IllegalStateException(ARGUMENT_ALREADY_HAS_A_NON_MATCHING_DEFAULT_VALUE);
        }

        if (!nullEquals(this.possibleValues, possibleValues)) {
            this.possibleValues = possibleValues;
            sendObjectChangedNotification(this.possibleValues);
        }
    }


    public boolean isAllowedValue(Object value) {
        if ((possibleValues == null) || (possibleValues.size() == 0)) {
            return true;
        } else {
            return possibleValues.contains(value);
        }
    }


    @Override
    public Object getValue() {
        return value;
    }


    @Override
    public Type getValueType() {
        return value.getClass();
    }


    @Override
    public boolean isOptional() {
        return optional;
    }


    @Override
    public boolean isReadOnly() {
        return readOnly;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object value) {
        if ((!isReadOnly()) && isAllowedValue(value)) {
            if (!nullEquals(this.value, value)) {
                this.value = value;
                sendObjectChangedNotification(this.value);
            }
        } else {
            throw new IllegalStateException(ARGUMENT_IS_READ_ONLY);
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void setValueAsString(String value) {
        Object oldValue = this.value;

        if (getValueType() == String.class) {
            this.value = value;
        } else if (getValueType() == Boolean.class) {
            this.value = Boolean.valueOf(value);
        } else if (getValueType() == Integer.class) {
            this.value = Integer.valueOf(value);
        } else if (getValueType() == Double.class) {
            this.value = Double.valueOf(value);
        } else if (getValueType() == Float.class) {
            this.value = Float.valueOf(value);
        } else if (getValueType() == Long.class) {
            this.value = Long.valueOf(value);
        } else if (getValueType() == Byte.class) {
            this.value = Byte.valueOf(value);
        } else if (getValueType() == Short.class) {
            this.value = Short.valueOf(value);
        } else if (this.value instanceof Stringifiable) {
            ((Stringifiable) this.value).assignFromRepresentationString(value);
        } else {
            throw new IllegalStateException(DONT_KNOW_HOW_TO_SET_THE_ARGUMENT_VALUE_OF_TYPE + getValueType().getClass().getSimpleName() + " from the string " + value);
        }

        if (!nullEquals(oldValue, this.value)) {
            sendObjectChangedNotification(this.value);
        }
    }


    @Override
    public String getValueAsString() {
        if (value instanceof Stringifiable) {
            return ((Stringifiable) value).toRepresentationString();
        }
        return value.toString();
    }


    @Override
    public String toString() {
        return "Argument{" +
                "caption=" + getCaption() +
                ", value=" + value +
                ", defaultValue=" + defaultValue +
                ", possibleValues=" + possibleValues +
                ", optional=" + optional +
                ", readOnly=" + readOnly +
                '}';
    }
}
