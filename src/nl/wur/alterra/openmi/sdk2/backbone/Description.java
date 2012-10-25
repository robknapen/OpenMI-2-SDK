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

import org.openmi.standard2.IDescribable;


/**
 * The Description class provides a straight forward implementation of the
 * IDescribable interface of OpenMI. When an object is IDescribable it can be
 * assigned a caption and a description. Both can change and are in no way
 * guaranteed to be unique.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands
 */
public class Description implements IDescribable {

    private String caption;
    private String description;


    public static Description newInstance(String caption, String description) {
        Description result = new Description();
        result.caption = caption;
        result.description = description;
        return result;
    }


    public static Description newInstance(Description description) {
        Description result = new Description();
        if (description != null) {
            result.caption = description.caption;
            result.description = description.description;
        }
        return result;
    }


    public static Description newInstanceFromDescribable(IDescribable describable) {
        Description result = new Description();
        if (describable != null) {
            result.caption = describable.getCaption();
            result.description = describable.getDescription();
        }
        return result;
    }


    public Description() {
        caption = "";
        description = "";
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Description other = (Description) obj;
        if ((this.caption == null) ? (other.caption != null) : !this.caption.equals(other.caption)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash +
                (this.caption != null ? this.caption.hashCode() : 0) +
                (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return "Description{" +
                "caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    public String getCaption() {
        return caption;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
