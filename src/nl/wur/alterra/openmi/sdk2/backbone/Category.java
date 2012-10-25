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

import org.openmi.standard2.ICategory;


public class Category extends DescribableOmiObject implements ICategory {

    // messages
    private final static String UNKOWN = "unkown";

    // fields
    private Object value;


    public static Category newInstance(ICategory category) {
        Category result = new Category();
        if (category != null) {
            result.setCaption(category.getCaption());
            result.setDescription(category.getDescription());
            result.setValue(category.getValue());
        }
        return result;
    }


    public static Category newInstance(String caption, String description, Object value) {
        Category result = new Category();
        result.setCaption(caption);
        result.setDescription(description);
        result.setValue(value);
        return result;
    }


    public static Category newInstance(Object value) {
        Category result = new Category();
        result.setValue(value);
        if (value != null) {
            result.setCaption(value.toString());
        } else {
            result.setCaption(UNKOWN);
        }
        result.setDescription("");
        return result;
    }


    public Category() {
        super();
        this.value = null;
        this.setCaption(UNKOWN);
    }


    @Override
    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        if (!nullEquals(this.value, value)) {
            this.value = value;
            sendObjectChangedNotification(this.value);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        // if (!super.equals(o)) return false;

        Category category = (Category) o;

        if (value != null ? !value.equals(category.value) : category.value != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        // int result = super.hashCode();
        int result = 7;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Category{" +
                "caption=" + getCaption() +
                ", value=" + value +
                '}';
    }
}
