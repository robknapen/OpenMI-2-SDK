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

import java.io.Serializable;

/**
 * The Vertex class contains a (x,y,z) coordinate. It is part of the
 * implementation of the OpenMI ElementSet in the backbone package.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, Netherlands
 */
public class Vertex implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private double z;

    /**
     * Creates a default (0, 0, 0) vertex.
     */
    public Vertex() {
        this(0, 0, 0);
    }

    /**
     * Creates a vertex copied from a specified vertex.
     *
     * @param source The vertex to copy
     */
    public Vertex(Vertex source) {
        this(source.x, source.y, source.z);
    }

    /**
     * Creates a vertex with the specified coordinates.
     *
     * @param x X position
     * @param y Y position
     * @param z Z position
     */
    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the X position.
     *
     * @return X position
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the Y position.
     *
     * @return Y position
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the Z position.
     *
     * @return Z position
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the X position.
     *
     * @param d The X position
     */
    public void setX(double d) {
        x = d;
    }

    /**
     * Sets the Y position.
     *
     * @param d The Y position
     */
    public void setY(double d) {
        y = d;
    }

    /**
     * Sets the Z position.
     *
     * @param d The Z position
     */
    public void setZ(double d) {
        z = d;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Vertex v = (Vertex) obj;
        return (this.x == v.x && this.y == v.y && this.z == v.z);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Double.valueOf(x).hashCode()
                + Double.valueOf(y).hashCode() + Double.valueOf(z).hashCode();
    }

    @Override
    public String toString() {
        return String.format("Vertex: %f %f %f", x, y, z);
    }

}
