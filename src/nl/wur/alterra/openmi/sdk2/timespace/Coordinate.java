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
package nl.wur.alterra.openmi.sdk2.timespace;

/**
 * Coordinate stores x,y,z and m values; representing a point in space, or more likely a point
 * on the earth's surface.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class Coordinate {

    // fields
    double x;
    double y;
    double z;
    double m;


    public Coordinate newInstance(Coordinate coordinate) {
        Coordinate c = new Coordinate(coordinate.x, coordinate.y, coordinate.z, coordinate.m);
        return c;
    }


    public Coordinate newXY(double x, double y) {
        Coordinate c = new Coordinate(x, y, Double.NaN, Double.NaN);
        return c;
    }


    public Coordinate newXYM(double x, double y, double m) {
        Coordinate c = new Coordinate(x, y, Double.NaN, m);
        return c;
    }


    public Coordinate newXYZ(double x, double y, double z) {
        Coordinate c = new Coordinate(x, y, z, Double.NaN);
        return c;
    }


    public Coordinate newXYZM(double x, double y, double z, double m) {
        Coordinate c = new Coordinate(x, y, z, m);
        return c;
    }


    public Coordinate(double x, double y, double z, double m) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }


    public boolean hasZ() {
        return (Double.isNaN(z));
    }


    public boolean hasM() {
        return (Double.isNaN(m));
    }


    public double getX() {
        return x;
    }


    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }


    public void setY(double y) {
        this.y = y;
    }


    public double getZ() {
        return z;
    }


    public void setZ(double z) {
        this.z = z;
    }


    public double getM() {
        return m;
    }


    public void setM(double m) {
        this.m = m;
    }
}
