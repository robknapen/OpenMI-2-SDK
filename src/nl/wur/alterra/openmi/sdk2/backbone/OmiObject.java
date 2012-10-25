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

import java.util.Observable;


/**
 * Abstract base class for classes in the OpenMI backbone.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public abstract class OmiObject extends Observable {


    /**
     * Checks if two objects are equal, taking into account that one or both
     * can be a null reference. When both objects are not null references they
     * are checked using the equals() method, otherwise the references are
     * compared with "==".
     *
     * @param obj1 The first object
     * @param obj2 The second object
     * @return True when objects are considered equal
     */
    protected static boolean nullEquals(Object obj1, Object obj2) {
        if ((obj1 != null) && (obj2 != null)) {
            return obj1.equals(obj2);
        } else {
            return (obj1 == obj2);
        }
    }


    /**
     * Creates an instance.
     */
    public OmiObject() {
        super();
    }


    /**
     * Sends the specified notification to all observers.
     *
     * @param notification
     */
    protected void notify(OmiNotification notification) {
        super.setChanged();
        notifyObservers(notification);
    }


    public void sendNotificationIfObjectChanged(Object oldVar, Object newVar) {
        if (!nullEquals(oldVar, newVar)) {
            OmiNotification n = OmiNotification.newModifiedNotification(this, newVar, true);
            notify(n);
        }
    }


    public void sendObjectChangedNotification(Object changedVar) {
        if (changedVar != null) {
            OmiNotification n = OmiNotification.newModifiedNotification(this, changedVar, true);
            notify(n);
        }
    }


    public void sendObjectDeletedNotification(Object deletedVar) {
        if (deletedVar != null) {
            OmiNotification n = OmiNotification.newDeleteNotification(this, deletedVar, true);
            notify(n);
        }
    }


    public void sendObjectAddedNotification(Object newVar) {
        if (newVar != null) {
            OmiNotification n = OmiNotification.newAddNotification(this, newVar, true);
            notify(n);
        }
    }

}
