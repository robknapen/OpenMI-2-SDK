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

import nl.wur.alterra.openmi.sdk2.backbone.Identifier;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The element class contains a spatial element.
 */
public class Element implements Serializable {

    private static final long serialVersionUID = 1L;

    private Vertices vertices;
    private ArrayList<int[]> faces;
    private Identifier identifier;


    /**
     * Creates an Element with an empty ID.
     */
    public Element() {
        this("");
    }


    /**
     * Creates an Element as a (shallow) copy of another element.
     *
     * @param source The element to copy
     */
    public Element(Element source) {
        this(source.getId());
        vertices = source.getVertices();
    }


    /**
     * Creates an Element with the specified id.
     *
     * @param id The element id
     */
    public Element(String id) {
        identifier = Identifier.newInstance(id, "", "");
        vertices = new Vertices();
        faces = new ArrayList<int[]>();
    }


    /**
     * Gets the vertices collection.
     *
     * @return the Vertices
     */
    public Vertices getVertices() {
        return vertices;
    }


    /**
     * Sets the vertices collection to the specified one.
     *
     * @param vertices Vertices collection to set
     */
    public void setVertices(Vertices vertices) {
        this.vertices = vertices;
    }


    public Identifier getIdentifier() {
        return identifier;
    }


    /**
     * Gets the element id.
     *
     * @return element id
     */
    public String getId() {
        return identifier.getId();
    }


    /**
     * Sets the element id.
     *
     * @param id The id
     */
    public void setId(String id) {
        identifier.setId(id);
    }


    /**
     * Returns the vertex for a given index.
     *
     * @param index Index of the vertex to return
     * @return The vertex
     */
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }


    /**
     * Returns the number of vertices.
     *
     * @return Number of vertices
     */
    public int getVertexCount() {
        return vertices.size();
    }


    /**
     * Adds a vertex.
     *
     * @param vertex The vertex to be added
     */
    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }


    /**
     * Gets the number of faces in the element.
     *
     * @return Number of faces
     */
    public int getFaceCount() {
        return faces.size();
    }


    /**
     * Adds a face to the element.
     *
     * @param vertexIndices The vertex indices for the face
     */
    public void addFace(int[] vertexIndices) {
        faces.add(vertexIndices);
    }


    /**
     * Gets the face vertex indices array for a given face index.
     *
     * @param faceIndex The index of the desired face
     * @return The vertex indices for the face
     */
    public int[] getFaceVertexIndices(int faceIndex) {
        return faces.get(faceIndex);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Element e = (Element) obj;

        if (!this.getIdentifier().equals(e.getIdentifier())) {
            return false;
        }

        if (!this.getVertices().equals(e.getVertices())) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return super.hashCode() + getIdentifier().hashCode() + getVertices().hashCode();
    }

}
