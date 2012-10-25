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

import org.openmi.standard2.IDimension;

import java.util.Arrays;


/**
 * The dimension class contains the dimension for a unit.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Dimension implements IDimension {

    private double[] powers;


    /**
     * Creates an instance.
     */
    public Dimension() {
        powers = new double[DimensionBase.values().length];
    }


    /**
     * Returns the power of a base quantity.
     *
     * @param baseQuantity Dimension to get power of
     * @return The power for the specified base quantity dimension
     */
    public double getPower(DimensionBase baseQuantity) {
        return powers[baseQuantity.ordinal()];
    }


    /**
     * Sets a power for a base quantity.
     *
     * @param baseQuantity The base quantity
     * @param power        The power
     */
    public void setPower(DimensionBase baseQuantity, double power) {
        powers[baseQuantity.ordinal()] = power;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dimension other = (Dimension) obj;
        if (!Arrays.equals(this.powers, other.powers)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Arrays.hashCode(this.powers);
        return hash;
    }


    @Override
    public String toString() {
        return "Dimension{" +
                "powers=" + powers +
                '}';
    }
}
