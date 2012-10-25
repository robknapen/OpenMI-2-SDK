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

import org.openmi.standard2.IQuantity;
import org.openmi.standard2.IUnit;

import java.lang.reflect.Type;


/**
 * The quantity class contains a unit, a description and dimensions.
 */
public class Quantity extends ValueDefinition implements IQuantity {

    private IUnit unit;


    public static Quantity newDoubleInstance(String caption, String description, IUnit unit) {
        Quantity result = new Quantity(Double.class, unit, caption, description);
        return result;
    }


    public static Quantity newIntegerInstance(String caption, String description, IUnit unit) {
        Quantity result = new Quantity(Integer.class, unit, caption, description);
        return result;
    }


    public static Quantity newInstance(IQuantity quantity) {
        Quantity result = new Quantity(quantity.getValueType(), quantity.getUnit(),
                quantity.getCaption(), quantity.getDescription());
        return result;
    }


    /**
     * Creates an instance with all default values, i.e. empty ID and
     * description, default dimensionless Unit and Double as value type.
     */
    public Quantity() {
        this(Double.class, new Unit(), "", "");
    }


    public Quantity(Type valueType, IUnit unit, String caption, String description) {
        super(valueType, null, caption, description);
        this.unit = unit;
    }


    /**
     * Gets the Unit.
     *
     * @return The Unit
     */
    public IUnit getUnit() {
        return unit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;
        if (!super.equals(o)) return false;

        Quantity quantity = (Quantity) o;

        if (unit != null ? !unit.equals(quantity.unit) : quantity.unit != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Quantity{" +
                "caption=" + getCaption() +
                ", unit=" + unit +
                '}';
    }
}
