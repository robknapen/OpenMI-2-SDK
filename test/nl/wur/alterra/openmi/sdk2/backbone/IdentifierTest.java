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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class IdentifierTest {

    // test subjects
    private String identifierId1 = "id1";
    private String identifierCaption1 = "id1_caption";
    private String identifierDescr1 = "id1_description";
    private String identifierCaption2 = "random_id1_caption";
    private String identifierDescr2 = "random_id1_description";
    private String identifierId3 = "random_id2_caption";
    private String identifierDescr3 = "random_id2_description";
    private Identifier id1;
    private Identifier randomId1;
    private Identifier randomId2;


    @Before
    public void setUp() {
        id1 = Identifier.newInstance(identifierId1, identifierCaption1, identifierDescr1);
        randomId1 = Identifier.newRandomUuid(identifierCaption2, identifierDescr2);
        randomId2 = Identifier.newRandomUuid(identifierId3, identifierDescr3);
    }


    @Test
    public void testConstructor() {
        Assert.assertEquals(identifierId1, id1.getId());
        Assert.assertEquals(identifierCaption1, id1.getCaption());
        Assert.assertEquals(identifierDescr1, id1.getDescription());

        Assert.assertEquals(identifierCaption2, randomId1.getCaption());
        Assert.assertEquals(identifierDescr2, randomId1.getDescription());
    }


    @Test
    public void testRandomIds() {
        Assert.assertNotSame(randomId1.getId(), randomId2.getId());
    }


    @Test
    public void testCopyFactory() {
        Identifier newId1 = Identifier.newInstance(id1);
        Assert.assertEquals(identifierId1, newId1.getId());
        Assert.assertEquals(identifierCaption1, newId1.getCaption());
        Assert.assertEquals(identifierDescr1, newId1.getDescription());

        Identifier newId2 = Identifier.newInstanceFromIdentifiable(id1);
        Assert.assertEquals(identifierId1, newId2.getId());
        Assert.assertEquals(identifierCaption1, newId2.getCaption());
        Assert.assertEquals(identifierDescr1, newId2.getDescription());

        Identifier newId3 = Identifier.newInstanceFromIdentifiableWithRandomUuid(id1);
        Assert.assertNotSame(identifierId1, newId3.getId());
        Assert.assertEquals(identifierCaption1, newId3.getCaption());
        Assert.assertEquals(identifierDescr1, newId3.getDescription());
    }

}
