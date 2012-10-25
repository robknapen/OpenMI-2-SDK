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

import nl.wur.alterra.openmi.sdk2.backbone.DescribableOmiObject;
import org.openmi.standard2.IIdentifiable;
import org.openmi.standard2.timespace.ElementType;
import org.openmi.standard2.timespace.IElementSet;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * The ElementSet class describes a collection of spatial elements.
 */
public class ElementSet extends DescribableOmiObject implements IElementSet, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default static ID-based ElementSet.
     */
    public static final ElementSet DEFAULT = new ElementSet(null, "default", "A default ID-based element set.", ElementType.ID_BASED, null);

    private ArrayList<Element> elements;
    private ElementType elementType;
    private String spatialReferenceSystemWkt;


    /**
     * Creates an instance with default values.
     */
    public ElementSet() {
        this(null, "default", "A default element set for points.", ElementType.POINT, "");
    }


    /**
     * Creates an instance and copy values of the specified instance.
     *
     * @param source The IElementSet to copy from
     */
    @SuppressWarnings("unchecked")
    public ElementSet(IElementSet source) {
        this(null, source.getCaption(), source.getDescription(),
                source.getElementType(), source.getSpatialReferenceSystemWkt());

        // can copy faster with some inside knowledge
        if (source instanceof ElementSet) {
            elements = (ArrayList<Element>) ((ElementSet) source).elements.clone();
        } else {
            for (int i = 0; i < source.getElementCount(); i++) {
                Element element = new Element(source.getElementId(i).getId());
                for (int j = 0; j < source.getVertexCount(i); j++) {
                    double x = source.getVertexXCoordinate(i, j);
                    double y = source.getVertexYCoordinate(i, j);
                    double z = source.getVertexZCoordinate(i, j);
                    // TODO add checks and M property
                    element.addVertex(new Vertex(x, y, z));
                }
                addElement(element);
            }
        }
    }


    /**
     * Creates an instance with the specified values.
     *
     * @param id                        The id
     * @param caption                   The caption
     * @param description               The description
     * @param elementType               The ElementType
     * @param spatialReferenceSystemWkt The spatial reference system as WKT
     */
    public ElementSet(String id, String caption, String description, ElementType elementType,
                      String spatialReferenceSystemWkt) {
        elements = new ArrayList<Element>();
        this.setCaption(caption);
        this.setDescription(description);
        this.elementType = elementType;
        this.spatialReferenceSystemWkt = spatialReferenceSystemWkt;
    }


    /**
     * Gets an element.
     *
     * @param index Index for element to get
     * @return The selected element
     */
    public Element getElement(int index) {
        return elements.get(index);
    }


    /**
     * Gets a copy of the elements as an array.
     *
     * @return Returns the elements
     */
    public Element[] getElements() {
        return elements.toArray(new Element[0]);
    }


    /**
     * Sets the elements from the contents of an array.
     *
     * @param values The elements to set
     */
    public void setElements(Element[] values) {
        elements.clear();
        for (Element e : values) {
            elements.add(e);
        }
    }


    /**
     * Gets the type of elements.
     *
     * @return The type of elements
     */
    public ElementType getElementType() {
        return elementType;
    }


    /**
     * Sets the type of elements.
     *
     * @param elementType The elementType to set
     */
    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }


    /**
     * Returns the identifier for the element at the given index.
     *
     * @param index The index for the element
     * @return The identifier of the element
     */
    public IIdentifiable getElementId(int index) {
        return (elements.get(index)).getIdentifier();
    }


    /**
     * Gets the X coordinate for a certain element and vertex.
     *
     * @param elementIndex Index for the element
     * @param vertexIndex  Index for the vertex
     * @return X coordinate
     */
    public double getXCoordinate(int elementIndex, int vertexIndex) {
        return elements.get(elementIndex).getVertex(vertexIndex).getX();
    }


    /**
     * Gets the Y coordinate for a certain element and vertex.
     *
     * @param elementIndex Index for the element
     * @param vertexIndex  Index for the vertex
     * @return Y coordinate
     */
    public double getYCoordinate(int elementIndex, int vertexIndex) {
        return elements.get(elementIndex).getVertex(vertexIndex).getY();
    }


    /**
     * Gets the Z coordinate for a certain element and vertex.
     *
     * @param elementIndex Index for the element
     * @param vertexIndex  Index for the vertex
     * @return Y coordinate
     */
    public double getZCoordinate(int elementIndex, int vertexIndex) {
        return elements.get(elementIndex).getVertex(vertexIndex).getZ();
    }


    /**
     * Returns the number of elements.
     *
     * @return Number of elements
     */
    public int getElementCount() {
        return elements.size();
    }


    /**
     * Returns the number of vertices for an element.
     *
     * @param index Index of the element
     * @return Number of vertices in the element
     */
    public int getVertexCount(int index) {
        Element element = elements.get(index);
        return element.getVertexCount();
    }


    /**
     * Returns the element index for a given element identifier.
     *
     * @param elementId The identifier of the element
     * @return The index of the element with the specified identifier, -1 when not found
     * @throws Exception when no match was found
     */
    @Override
    public int getElementIndex(IIdentifiable elementId) {
        for (Element e : elements) {
            if (elementId.equals(e.getIdentifier())) {
                return elements.indexOf(e);
            }
        }
        return -1;
    }


    /**
     * Gets the version of the ElementSet.
     *
     * @return Integer version number
     */
    public int getVersion() {
        return 0;
    }


    /**
     * Returns the number of faces for a given element.
     *
     * @param index Index for the element
     * @return The number of faces
     */
    public int getFaceCount(int index) {
        return (elements.get(index)).getFaceCount();
    }


    /**
     * Returns the list of face vertex indices for a given element and face.
     *
     * @param elementIndex The index for the element
     * @param faceIndex    The index for the face
     * @return Integer array with the face vertex indices
     */
    public int[] getFaceVertexIndices(int elementIndex, int faceIndex) {
        return (elements.get(elementIndex)).getFaceVertexIndices(faceIndex);
    }


    /**
     * Adds an element.
     *
     * @param element Element to be added
     */
    public void addElement(Element element) {
        elements.add(element);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElementSet other = (ElementSet) obj;
        if (this.elements != other.elements && (this.elements == null || !this.elements.equals(other.elements))) {
            return false;
        }
        if ((this.spatialReferenceSystemWkt == null) ? (other.spatialReferenceSystemWkt != null) : !this.spatialReferenceSystemWkt.equals(other.spatialReferenceSystemWkt)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.elements != null ? this.elements.hashCode() : 0);
        hash = 79 * hash + this.elementType.hashCode();
        hash = 79 * hash + (this.spatialReferenceSystemWkt != null ? this.spatialReferenceSystemWkt.hashCode() : 0);
        return hash;
    }


    public String getSpatialReferenceSystemWkt() {
        return spatialReferenceSystemWkt;
    }


    public enum SpatialDimension {
        X, Y, Z, M
    }


    public boolean hasSpatialDimension(SpatialDimension dim) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public double getVertexCoordinate(int elementIndex, int vertexIndex, SpatialDimension dim) {
        switch (dim) {
            case X:
                return elements.get(elementIndex).getVertex(vertexIndex).getX();
            case Y:
                return elements.get(elementIndex).getVertex(vertexIndex).getY();
            case Z:
                return elements.get(elementIndex).getVertex(vertexIndex).getZ();

        }
        throw new UnsupportedOperationException("Spatial Dimension " + dim + " not supported yet.");
    }


    @Override
    public double getVertexMCoordinate(int elementIndex, int vertexIndex) {
        return getVertexCoordinate(elementIndex, vertexIndex, SpatialDimension.M);
    }


    @Override
    public double getVertexXCoordinate(int elementIndex, int vertexIndex) {
        return getVertexCoordinate(elementIndex, vertexIndex, SpatialDimension.X);
    }


    @Override
    public double getVertexYCoordinate(int elementIndex, int vertexIndex) {
        return getVertexCoordinate(elementIndex, vertexIndex, SpatialDimension.Y);
    }


    @Override
    public double getVertexZCoordinate(int elementIndex, int vertexIndex) {
        return getVertexCoordinate(elementIndex, vertexIndex, SpatialDimension.Z);
    }


    @Override
    public boolean hasM() {
        return hasSpatialDimension(SpatialDimension.M);
    }


    @Override
    public boolean hasZ() {
        return hasSpatialDimension(SpatialDimension.Z);
    }

}
