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
import org.openmi.standard2.timespace.ITimeSet;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class TimeSet extends OmiObject implements ITimeSet {

    private ArrayList<ITime> times = new ArrayList<ITime>();
    private boolean hasDurations;
    private ITime timeHorizon;
    private double offsetFromUtcInHours;


    public TimeSet() {
        hasDurations = false;
        timeHorizon = new Time();
        offsetFromUtcInHours = 0.0;
    }


    public void setSingleTimeStamp(double timeStampAsMJD) {
        ITime timeStamp = new Time(timeStampAsMJD);
        times.clear();
        times.add(timeStamp);
    }


    public List<ITime> getTimes() {
        return times;
    }


    public boolean hasDurations() {
        return hasDurations;
    }


    public double getOffsetFromUtcInHours() {
        return offsetFromUtcInHours;
    }


    public ITime getTimeHorizon() {
        return timeHorizon;
    }


}
