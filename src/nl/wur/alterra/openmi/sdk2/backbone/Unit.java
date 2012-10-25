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
import org.openmi.standard2.IUnit;


/**
 * The Unit class defines a unit for a quantity.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Unit extends DescribableOmiObject implements IUnit {

    public enum PredefinedUnits {
        DIMENSIONLESS,
        METER,
        METER_PER_SECOND,
        CUBIC_METER_PER_SECOND,
        MILLIMETER_PER_DAY
    }


    private Dimension dimension;
    private double conversionFactor;
    private double conversionOffset;


    public static Unit newInstance(String caption, String description, double conversionFactor,
                                   double conversionOffSet) {
        Unit result = new Unit();
        result.setCaption(caption);
        result.setDescription(description);
        result.setConversionFactorToSI(conversionFactor);
        result.setOffsetToSI(conversionOffSet);

        // clear all dimensions (make it dimensionless)
        for (IDimension.DimensionBase dim : IDimension.DimensionBase.values()) {
            result.dimension.setPower(dim, 0);
        }

        return result;
    }


    public static Unit newInstance(IUnit unit) {
        Unit result = new Unit();
        if (unit != null) {
            result.setCaption(unit.getCaption());
            result.setDescription(unit.getDescription());
            result.setConversionFactorToSI(unit.getConversionFactorToSI());
            result.setOffsetToSI(unit.getOffsetToSI());

            // copy dimensions
            IDimension sourceDim = unit.getDimension();
            for (IDimension.DimensionBase dim : IDimension.DimensionBase.values()) {
                result.dimension.setPower(dim, sourceDim.getPower(dim));
            }
        }
        return result;
    }


    public static Unit newPredefinedUnit(PredefinedUnits predefinedUnit) {
        Unit result = new Unit();

        // default values for the properties
        double conversionFactorToBeUsed = 1.0;
        double conversionOffsetToBeUsed = 0.0;
        Dimension dimensionToBeUsed = new Dimension();

        switch (predefinedUnit) {
            case DIMENSIONLESS:
                result.setCaption("[-]");
                result.setDescription("dimensionless");
                break;
            case METER:
                result.setCaption("m");
                result.setDescription("meter");
                dimensionToBeUsed.setPower(IDimension.DimensionBase.LENGTH, 1);
                break;
            case METER_PER_SECOND:
                result.setCaption("m/s");
                result.setDescription("meter per second");
                dimensionToBeUsed.setPower(IDimension.DimensionBase.LENGTH, 1);
                dimensionToBeUsed.setPower(IDimension.DimensionBase.TIME, -1);
                break;
            case CUBIC_METER_PER_SECOND:
                result.setCaption("m3/s");
                result.setDescription("cubic meter per second");
                dimensionToBeUsed.setPower(IDimension.DimensionBase.LENGTH, 3);
                dimensionToBeUsed.setPower(IDimension.DimensionBase.TIME, -1);
                break;
            case MILLIMETER_PER_DAY:
                result.setCaption("mm/day");
                result.setDescription("millimeters per day");
                conversionFactorToBeUsed = 1.15741E-08;
                dimensionToBeUsed.setPower(IDimension.DimensionBase.LENGTH, 1);
                dimensionToBeUsed.setPower(IDimension.DimensionBase.TIME, -1);
                break;
        }

        // set properties
        result.dimension = dimensionToBeUsed;
        result.conversionFactor = conversionFactorToBeUsed;
        result.conversionOffset = conversionOffsetToBeUsed;

        return result;
    }


    /**
     * Creates an instance with default values, i.e. a random ID, a caption
     * "[-]", an empty description, a conversion factor of 1.0 and a conversion
     * offset of 0.0. Furthermore the unit is dimensionless.
     */
    public Unit() {
        super();
        setCaption("[-]");
        setDescription("");
        dimension = new Dimension();
        conversionFactor = 1.0;
        conversionOffset = 0.0;
    }


    /**
     * Gets the conversion Factor to SI.
     *
     * @return The conversion factor to SI for this unit
     */
    public double getConversionFactorToSI() {
        return conversionFactor;
    }


    /**
     * Gets the conversion offset to SI.
     *
     * @return The conversion offset to SI
     */
    public double getOffsetToSI() {
        return conversionOffset;
    }


    /**
     * Sets the conversion Factor to SI.
     *
     * @param conversionFactor The conversionFactor to set
     */
    public void setConversionFactorToSI(double conversionFactor) {
        // don't to anything smart with epsilons etc., stick to the core here
        if (Double.compare(this.conversionFactor, conversionFactor) != 0) {
            this.conversionFactor = conversionFactor;
            sendObjectChangedNotification(this.conversionFactor);
        }
    }


    /**
     * Sets the conversion offset to SI.
     *
     * @param conversionOffset The conversionOffset to set.
     */
    public void setOffsetToSI(double conversionOffset) {
        if (Double.compare(this.conversionOffset, conversionOffset) != 0) {
            this.conversionOffset = conversionOffset;
            sendObjectChangedNotification(this.conversionOffset);
        }
    }


    public IDimension getDimension() {
        return dimension;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        if (!super.equals(o)) return false;

        Unit unit = (Unit) o;

        if (Double.compare(unit.conversionFactor, conversionFactor) != 0) return false;
        if (Double.compare(unit.conversionOffset, conversionOffset) != 0) return false;
        if (dimension != null ? !dimension.equals(unit.dimension) : unit.dimension != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        temp = conversionFactor != +0.0d ? Double.doubleToLongBits(conversionFactor) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = conversionOffset != +0.0d ? Double.doubleToLongBits(conversionOffset) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public String toString() {
        return "Unit{" +
                "caption=" + getCaption() +
                ", dimension=" + dimension +
                ", conversionFactor=" + conversionFactor +
                ", conversionOffset=" + conversionOffset +
                '}';
    }
}
