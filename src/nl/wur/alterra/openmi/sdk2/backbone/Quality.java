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
import org.openmi.standard2.IQuality;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Quality extends ValueDefinition implements IQuality {

    private List<ICategory> categories = new ArrayList<ICategory>();
    private boolean ordered;


    public static Quality newStringInstance(String caption, String description,
                                            boolean ordered, ICategory... categories) {
        Quality result = new Quality(String.class, caption, description, ordered);
        for (ICategory c : categories) {
            result.addCategory(c);
        }
        return result;
    }


    public static Quality newIstance(IQuality quality) {
        Quality result = new Quality(quality.getValueType(), quality.getCaption(),
                quality.getDescription(), quality.isOrdered());
        for (ICategory c : quality.getCategories()) {
            result.addCategory(c);
        }
        return result;
    }


    public Quality() {
        this(Object.class, "", "", false);
    }


    public Quality(Type valueType, String caption, String description, boolean ordered) {
        super(valueType, null, caption, description);
        this.ordered = ordered;
        this.categories.clear();
    }


    /**
     * Adds the specified category to the quality, if it is of the proper type and not already
     * part of the quality.
     *
     * @param category to add
     * @return true when sucessful
     */
    public boolean addCategory(ICategory category) {
        if (isValidCategory(category) && (!categories.contains(category))) {
            return categories.add(category);
        }
        return false;
    }


//    private Category createCategoryForValue(Object value) {
//        if (value != null) {
//            return new Category<Object>(value);
//        }
//        return null;
//    }


    /**
     * Removes the specified category from the quality.
     *
     * @param category to remove
     * @return true when succesful
     */
    public boolean removeCategory(ICategory category) {
        return categories.remove(category);
    }


    public ICategory removeCategoryWithValue(Object categoryValue) {
        int index = indexOf(categoryValue);
        if (index >= 0) {
            return categories.remove(index);
        }
        return null;
    }


    private boolean isValidCategory(ICategory c) {
        if (c == null)
            return false;

        Object catValue = c.getValue();
        if ((catValue != null) && (catValue.getClass() != getValueType()))
            return false;

        return true;
    }


    @Override
    public List<ICategory> getCategories() {
        // have to change the type of the list
        // to avoid this the interface should be changed into List<? extends ICategory>
        ArrayList<ICategory> result = new ArrayList<ICategory>(categories);
        return Collections.unmodifiableList(result);
    }


    /**
     * Checks if the quality has a category with the specified value.
     *
     * @param categoryValue to check for
     * @return true if the quality has a category with the given value
     */
    public boolean hasCategoryWithValue(Object categoryValue) {
        return indexOf(categoryValue) != -1;
    }


    /**
     * Returns the index of the category of the quality with the specified value. Returns
     * -1 when no such category exists.
     *
     * @param categoryValue to return index for
     * @return index of the matching category, or -1
     */
    public int indexOf(Object categoryValue) {
        if ((categoryValue != null) && (categoryValue.getClass() == getValueType())) {
            for (ICategory cat : categories) {
                if (cat.getValue().equals(categoryValue))
                    return categories.indexOf(cat);
            }
        }
        return -1;
    }


    /**
     * Returns the category of the quality with the specified index. Throws an exception when
     * the index is out of range.
     *
     * @param index to get category for
     * @return category
     */
    public ICategory get(int index) {
        return categories.get(index);
    }


    /**
     * Returns the category of the quality with the specified value. When no match is found
     * null will be returned.
     *
     * @param categoryValue to return category for
     * @return category when found, or null
     */
    public ICategory get(Object categoryValue) {
        int index = indexOf(categoryValue);
        if (index >= 0) {
            return get(index);
        } else {
            return null;
        }
    }


    public int compareCategoryValues(Object categoryValue1,
                                     Object categoryValue2) {
        if (!isOrdered())
            throw new IllegalStateException(
                    "Can not compare category values in unordered quality "
                            + this);
        if (!hasCategoryWithValue(categoryValue1))
            throw new IllegalStateException("Can not compare category value "
                    + categoryValue1 + " that is not part of the quality "
                    + this);
        if (!hasCategoryWithValue(categoryValue2))
            throw new IllegalStateException("Can not compare category value "
                    + categoryValue2 + " that is not part of the quality "
                    + this);

        if (categoryValue1.equals(categoryValue2)) {
            return 0;
        } else {
            if (indexOf(categoryValue1) < indexOf(categoryValue2))
                return -1;
            else
                return 1;
        }
    }


    @Override
    public boolean isOrdered() {
        return ordered;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quality)) return false;
        if (!super.equals(o)) return false;

        Quality quality = (Quality) o;

        if (ordered != quality.ordered) return false;

        if (categories != null ? !hasSameCategories(quality.categories) : quality.categories != null)
            return false;

        return true;
    }


    public boolean hasSameCategories(List<ICategory> categories) {
        if (categories == null) {
            return this.categories == null;
        }

        if (this.categories == null) {
            return false;
        }

        if (ordered) {
            return this.categories.equals(categories);
        } else {
            // compare categories, disregarding order, duplicates can not exist (in internal list)
            if (this.categories.size() == categories.size()) {
                for (ICategory category : this.categories) {
                    if (!categories.contains(category)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();

        //  calculate hashcode differently for ordered and unordered categories
        int categoriesHash = 0;
        if (categories != null) {
            if (ordered) {
                categoriesHash = categories.hashCode();
            } else {
                for (ICategory category : categories) {
                    categoriesHash += category.hashCode();
                }
            }
        }

        result = 31 * result + categoriesHash;
        result = 31 * result + (ordered ? 1 : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Quality{" +
                "caption=" + getCaption() +
                ", categories=" + categories +
                ", ordered=" + ordered +
                '}';
    }
}
