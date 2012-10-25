/*
 * Copyright (c) 2005-2010 Alterra, Wageningen UR, The Netherlands and the
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
package nl.wur.alterra.openmi.sdk2.timespace;

import nl.wur.alterra.openmi.sdk2.backbone.OmiObject;
import org.openmi.standard2.timespace.ITime;


/**
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Time extends OmiObject implements ITime, Comparable<ITime> {

    private double durationInDays;
    private double timeStampAsModifiedJulianDay;


    /**
     * Creates a zero time instance.
     */
    public Time() {
        durationInDays = 0;
        timeStampAsModifiedJulianDay = 0;
    }


    /**
     * Creates an instance based on the values of the specified instance.
     *
     * @param source The instance to copy values from
     */
    public Time(ITime source) {
        timeStampAsModifiedJulianDay = source.getStampAsModifiedJulianDay();
        durationInDays = source.getDurationInDays();
    }


    /**
     * Creates an instance with the specified value.
     *
     * @param timeStampAsModifiedJulianDay The modified julian day for the time stamp
     */
    public Time(double timeStampAsModifiedJulianDay) {
        this.timeStampAsModifiedJulianDay = timeStampAsModifiedJulianDay;
    }


    /**
     * Creates an instance with the specified values.
     *
     * @param timeStampAsModifiedJulianDay The modified julian day for the time stamp
     * @param durationInDays               The duration expressed in (fractions of) days
     */
    public Time(double timeStampAsModifiedJulianDay, double durationInDays) {
        this.timeStampAsModifiedJulianDay = timeStampAsModifiedJulianDay;
        this.durationInDays = durationInDays;
    }


    public double getStampAsModifiedJulianDay() {
        return timeStampAsModifiedJulianDay;
    }


    public double getDurationInDays() {
        return durationInDays;
    }


    public void setDurationInDays(double durationInDays) {
        this.durationInDays = durationInDays;
    }


    public void setTimeStampAsModifiedJulianDay(double timeStampAsModifiedJulianDay) {
        this.timeStampAsModifiedJulianDay = timeStampAsModifiedJulianDay;
    }


    public int compareTo(ITime arg0) {
        return Double.compare(this.timeStampAsModifiedJulianDay, arg0.getStampAsModifiedJulianDay());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Time other = (Time) obj;
        if (this.durationInDays != other.durationInDays) {
            return false;
        }
        if (this.timeStampAsModifiedJulianDay != other.timeStampAsModifiedJulianDay) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.durationInDays) ^ (Double.doubleToLongBits(this.durationInDays) >>> 32));
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.timeStampAsModifiedJulianDay) ^ (Double.doubleToLongBits(this.timeStampAsModifiedJulianDay) >>> 32));
        return hash;
    }


    /**
     * Converts the time stamp to a string.
     *
     * @return String converted time stamp
     */
    @Override
    public String toString() {
        if (durationInDays != 0 && durationInDays != Double.NaN) {
            return String.format("%s - %s", timeStampAsModifiedJulianDay, timeStampAsModifiedJulianDay + durationInDays);
        }
        return String.format("%s", timeStampAsModifiedJulianDay);
    }


}
