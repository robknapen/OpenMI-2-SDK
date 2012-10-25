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

import org.openmi.standard2.IValueDefinition;

import java.io.Serializable;
import java.lang.reflect.Type;


/**
 * Unintentionally left blank.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class ValueDefinition extends DescribableOmiObject implements IValueDefinition,
        Serializable {

    private static final long serialVersionUID = 1L;

    private Type valueType;
    private Object missingDataValue = null;


    public ValueDefinition() {
        this(Object.class, null);
    }


    public ValueDefinition(Type valueType, Object missingDataValue) {
        super();
        this.valueType = valueType;
        this.missingDataValue = missingDataValue;
    }


    public ValueDefinition(Type valueType, Object missingDataValue, String caption,
                           String description) {
        super(caption, description);
        this.valueType = valueType;
        this.missingDataValue = missingDataValue;
    }


    public Type getValueType() {
        return valueType;
    }


    public Object getMissingDataValue() {
        return missingDataValue;
    }


    public void setMissingDataValue(Object missingDataValue) {
        if (!nullEquals(this.missingDataValue, missingDataValue)) {
            this.missingDataValue = missingDataValue;
            sendObjectChangedNotification(this.missingDataValue);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueDefinition)) return false;
        if (!super.equals(o)) return false;

        ValueDefinition that = (ValueDefinition) o;

        if (missingDataValue != null ? !missingDataValue.equals(that.missingDataValue) : that.missingDataValue != null)
            return false;
        if (valueType != null ? !valueType.equals(that.valueType) : that.valueType != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (valueType != null ? valueType.hashCode() : 0);
        result = 31 * result + (missingDataValue != null ? missingDataValue.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "ValueDefinition{" +
                "caption=" + getCaption() +
                ", valueType=" + valueType +
                ", missingDataValue=" + missingDataValue +
                '}';
    }
}
