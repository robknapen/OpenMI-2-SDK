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
package nl.wur.alterra.openmi.sdk2.extras;

import org.openmi.standard2.IBaseLinkableComponent;


/**
 * As simple as possible interface to communicate with a model calculation engine. Methods need
 * to be convertable to C and other programming languages, so do not use objects, generics, etc.
 * This does result in many methods, but will also increase clarity for both engine developers
 * and error reporting / debugging.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public interface IEngine {

    // initialize the calculation core

    public void initialize(IBaseLinkableComponent component);

    // TODO should be output parameters...
    public boolean validate(String[] errors, String[] warning, String[] information);

    // prepare for run

    /* SetInput(...) and SetOutput(...) are called immediatly before Prepare()
    * They tell the engine which inputs and outputs are active for this engine
    * run and that the engine must deal with.
    * They also provide the information that the engine needs to pack and unpack
    * the data transfer arrays.
    */
    public void setInput(String id, int elementCount, int elementValueCount, int vectorLength);

    public void setInput(String id, int elementCount, int[] elementValueCounts, int vectorLength);

    public void setOutput(String id, int elementCount, int elementValueCount, int vectorLength);

    public void setOutput(String id, int elementCount, int[] elementValueCounts, int vectorLength);

    public void prepare();

    // set inputs

    public void setStrings(String id, double modifiedJulianDay, String missingValue,
                           String[] values);

    public void setInt32s(String id, double modifiedJulianDay, int missingValue, int[] values);

    public void setDoubles(String id, double modifiedJulianDay, double missingValue,
                           double[] values);

    public void setBooleans(String id, double modifiedJulianDay, boolean missingValue,
                            boolean[] values);

    public void update();


    // get outputs

    public String[] getStrings(String id, double modifiedJulianDay, String missingValue);

    public int[] getInt32s(String id, double modifiedJulianDay, int missingValue);

    public double[] getDoubles(String id, double modifiedJulianDay, double missingValue);

    public boolean[] getBooleans(String id, double modifiedJulianDay, boolean missingValue);

    // finalize the calculation core

    public void finish(boolean inFailedState);

}
