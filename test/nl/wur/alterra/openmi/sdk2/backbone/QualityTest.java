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

import org.junit.Before;
import org.junit.Test;
import org.openmi.standard2.ICategory;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


/**
 * Tests for the OpenMI Quality implementation.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class QualityTest {

    // test subjects
    private String soilCaption = "soil1";
    private String soilDescription = "Soil type.";
    private String performanceCaption = "performance1";
    private String performanceDescription = "Performance.";
    private Category[] soilCategories;
    private Category[] performanceCategories;
    private Quality soilQuality;
    private Quality performanceQuality;


    @Before
    public void setUp() {

        soilCategories = new Category[]{
                Category.newInstance("Grass"), Category.newInstance("Desert"),
                Category.newInstance("Mountain")};

        soilQuality = Quality.newStringInstance(soilCaption, soilDescription, false, soilCategories);

        performanceCategories = new Category[]{
                Category.newInstance("Worse"), Category.newInstance("Same"),
                Category.newInstance("Better")};

        performanceQuality = Quality.newStringInstance(performanceCaption, performanceDescription, true,
                performanceCategories);
    }


    @Test
    public void testConstructor() {
        assertEquals(soilCaption, soilQuality.getCaption());
        assertEquals(soilDescription, soilQuality.getDescription());
        assertEquals(soilCategories[0], soilQuality.get(0));
        assertEquals(soilCategories[1], soilQuality.get(1));
        assertEquals(soilCategories[2], soilQuality.get(2));
    }


    @Test
    public void testCopyFactory() {
        Quality newQuality = Quality.newIstance(soilQuality);

        assertEquals(soilCaption, newQuality.getCaption());
        assertEquals(soilDescription, newQuality.getDescription());
        assertEquals(soilCategories[0], newQuality.get(0));
        assertEquals(soilCategories[1], newQuality.get(1));
        assertEquals(soilCategories[2], newQuality.get(2));
    }


    @Test
    public void testAccessors() {
        Quality q1 = Quality.newIstance(soilQuality);
        assertEquals(soilQuality, q1);
        assertEquals(soilCaption, q1.getCaption());
        q1.setCaption("newCaption");
        assertEquals("newCaption", q1.getCaption());
        q1.setDescription("newDescription");
        assertEquals("newDescription", q1.getDescription());
    }


    @Test
    public void testValueType() {
        assertEquals(String.class, soilQuality.getValueType());
    }


    @Test
    public void testEquals() {
        // quality is not identifiable, so all fields must be included in equals check

        Category[] newCategories = new Category[]{
                Category.newInstance("Grass"), Category.newInstance("Desert"),
                Category.newInstance("Mountain")};

        Quality newQuality = Quality.newStringInstance(soilCaption, soilDescription, false, newCategories);

        assertEquals(soilQuality, newQuality);

        newQuality.setCaption("newCaption");
        assertNotSame(soilQuality, newQuality);
        newQuality.setCaption(soilCaption);

        newQuality.setDescription("newDescription");
        assertNotSame(soilQuality, newQuality);
        newQuality.setDescription(soilDescription);

        List<ICategory> categories = newQuality.getCategories();
        categories.get(0).setCaption("newCaption");
        assertNotSame(soilQuality, newQuality);

        newCategories[2] = Category.newInstance("foo");
        newQuality = Quality.newStringInstance(soilCaption, soilDescription, false, newCategories);
        assertNotSame(soilQuality, newQuality);
    }


    @Test
    public void testOrderedQualityEquality() {
        Category[] newSoilCategories = new Category[]{soilCategories[0], soilCategories[2],
                soilCategories[1]};

        Quality orderedQ1 = Quality.newStringInstance("ordered", "", true, soilCategories);
        int hash1 = orderedQ1.hashCode();
        Quality orderedQ2 = Quality.newStringInstance("ordered", "", true, newSoilCategories);
        int hash2 = orderedQ2.hashCode();

        assertNotSame(orderedQ1, orderedQ2);
        assertNotSame(hash1, hash2);
    }


    @Test
    public void testUnorderedQualityEquality() {
        Category[] newSoilCategories = new Category[]{soilCategories[0], soilCategories[2],
                soilCategories[1]};

        Quality notOrderedQ1 = Quality.newStringInstance("notOrdered", "", false, soilCategories);
        int hash1 = notOrderedQ1.hashCode();
        Quality notOrderedQ2 = Quality.newStringInstance("notOrdered", "", false,
                newSoilCategories);
        int hash2 = notOrderedQ2.hashCode();

        assertEquals(notOrderedQ1, notOrderedQ2);
        assertEquals(hash1, hash2);
    }

}
