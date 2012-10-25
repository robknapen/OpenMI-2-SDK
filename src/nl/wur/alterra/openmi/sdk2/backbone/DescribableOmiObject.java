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
 * OmiObject with base implementation of the IDescribable interface.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class DescribableOmiObject extends OmiObject implements IDescribable {

    private Description description;


    public static DescribableOmiObject newInstance(String caption, String description) {
        DescribableOmiObject result = new DescribableOmiObject();
        result.description.setCaption(caption);
        result.description.setDescription(description);
        return result;
    }


    public DescribableOmiObject() {
        super();
        description = Description.newInstance("", "");
    }


    public DescribableOmiObject(String caption, String description) {
        super();
        this.description = Description.newInstance(caption, description);
    }


    /**
     * Gets the description of the object. This is like an extended caption,
     * but might not be editable all the time by the user.
     *
     * @return String Description of the object
     */
    public String getDescription() {
        return description.getDescription();
    }


    /**
     * Sets the description of the object. This is like an extended caption,
     * but might not be editable all the time by the user.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }

        if (!description.equals(this.description.getDescription())) {
            this.description.setDescription(description);
            sendObjectChangedNotification(this.description);
        }
    }


    /**
     * Gets the caption of the object. The caption is typically used for
     * display in an user interface, and the user might be allowed to change
     * it at will. So it is best to not rely on it to have a specific value.
     *
     * @return String The caption
     */
    public String getCaption() {
        return description.getCaption();
    }


    /**
     * Sets the caption of the object.
     *
     * @param caption The new caption
     */
    public void setCaption(String caption) {
        if (caption == null) {
            caption = "";
        }

        // update description if it is an exact match to the caption
        if (!caption.equals(description.getCaption())) {
            if (description.getDescription().equals(description.getCaption())) {
                description.setDescription(caption);
            }
            description.setCaption(caption);
            sendObjectChangedNotification(description);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescribableOmiObject)) return false;

        DescribableOmiObject that = (DescribableOmiObject) o;

        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "DescribableOmiObject{" +
                "description=" + description +
                '}';
    }

}
