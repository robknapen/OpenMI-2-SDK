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

import org.openmi.standard2.IArgument;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Type safe collection of Argument instances.
 */
public class Arguments extends ArrayList<IArgument> {

    private static final long serialVersionUID = 1L;


    public static Arguments newInstance(IArgument... args) {
        Arguments result = new Arguments();
        result.addAll(args);
        return result;
    }


    public static Arguments newInstance(Collection<? extends IArgument> args) {
        return newInstance(args.toArray(new IArgument[]{}));
    }


    public static Arguments newCopiedInstance(IArgument... args) {
        Arguments result = new Arguments();
        result.addAllCopied(args);
        return result;
    }


    public static Arguments newCopiedInstance(Collection<? extends IArgument> args) {
        return newCopiedInstance(args.toArray(new IArgument[]{}));
    }


    /**
     * Returns the Value for the first found Argument in the collection that
     * has the specified id.
     *
     * @param id
     * @return Object Value, empty if key does not exist
     */
    public Object getValueForId(String id) {
        for (IArgument a : this) {
            if (a.getId().equals(id)) {
                return a.getValue();
            }
        }

        return "";
    }


    /**
     * Changes values of all Arguments with the specified id and that are
     * not ReadOnly to the specified string.
     *
     * @param id    The key of arguments to find and change
     * @param value The value to set for matching arguments
     */
    public void setValueForId(String id, Object value) {
        for (IArgument a : this) {
            if (a.getId().equals(id) && !a.isReadOnly()) {
                a.setValue(value);
            }
        }
    }


    /**
     * Returns true if the specified id exists in the collection.
     *
     * @param id The id to locate
     * @return boolean, true if key exists
     */
    public boolean containsId(String id) {
        for (IArgument a : this) {
            if (a.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Adds all the Argument instances from the given collection to
     * this collection. No checks will be performed!
     *
     * @param c The arguments to add
     */
    @Override
    public boolean addAll(Collection<? extends IArgument> c) {
        if (c != null) {
            for (IArgument item : c) {
                add(item);
            }
        }

        return true;
    }


    /**
     * Adds all the Argument instances from the given array to the
     * collection. No checks will be performed!
     *
     * @param arguments The arguments to add
     */
    public void addAll(IArgument[] arguments) {
        for (IArgument arg : arguments) {
            add(arg);
        }
    }


    public void addAllCopied(IArgument[] arguments) {
        for (IArgument arg : arguments) {
            Argument dupArg = Argument.newInstance(arg);
            add(dupArg);
        }
    }


    /**
     * Appends the specified element to the end of this list.
     *
     * @param o element to be appended to this list.
     * @return <tt>true</tt> (as per the general contract of Collection.add).
     */
    public boolean add(IArgument o) {
        if (!contains(o)) {
            return super.add(o);
        } else {
            return false;
        }
    }


    /**
     * Returns the index for the specified Argument.
     *
     * @param elem
     * @return index
     */
    public int indexOf(IArgument elem) {
        return super.indexOf(elem);
    }


    /**
     * Returns the index for the specified argument id.
     *
     * @param id
     * @return index, -1 if not found
     */
    public int indexOfId(String id) {
        if (id != null) {
            for (int i = 0; i < size(); i++) {
                if (get(i).getId().equals(id)) {
                    return i;
                }
            }
        }

        return -1;
    }

}
