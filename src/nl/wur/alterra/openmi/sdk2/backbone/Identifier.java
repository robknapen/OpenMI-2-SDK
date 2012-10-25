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

import org.openmi.standard2.IIdentifiable;

import java.util.UUID;


/**
 * The Identifier class provides a straight forward implementation of the
 * IIdentifiable interface of OpenMI. It can be used to give objects a unique
 * ID, as a long type. The scope of the uniqueness of the ID is up to the
 * developer.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Identifier extends Description implements IIdentifiable {

    private String id;


    public static Identifier newInstance(String id, String caption, String description) {
        Identifier result = new Identifier();
        result.id = id;
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public static Identifier newInstance(Identifier identifier) {
        Identifier result = new Identifier();
        if (identifier != null) {
            result.id = identifier.getId();
            result.setCaption(identifier.getCaption());
            result.setDescription(identifier.getDescription());
        }
        return result;
    }


    public static Identifier newInstanceFromIdentifiable(IIdentifiable identifiable) {
        Identifier result = new Identifier();
        if (identifiable != null) {
            result.id = identifiable.getId();
            result.setCaption(identifiable.getCaption());
            result.setDescription(identifiable.getDescription());
        }
        return result;
    }


    public static Identifier newInstanceFromIdentifiableWithRandomUuid(IIdentifiable identifiable) {
        Identifier result = new Identifier();
        result.id = UUID.randomUUID().toString();
        if (identifiable != null) {
            result.setCaption(identifiable.getCaption());
            result.setDescription(identifiable.getDescription());
        }
        return result;
    }


    public static Identifier newRandomUuid(String caption, String description) {
        Identifier result = new Identifier();
        result.id = UUID.randomUUID().toString();
        result.setCaption(caption);
        result.setDescription(description);
        return result;
    }


    public Identifier() {
        id = "";
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Identifier other = (Identifier) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return "Identifier{" +
                "id='" + id + '\'' +
                '}';
    }
}
