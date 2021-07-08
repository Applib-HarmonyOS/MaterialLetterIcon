/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ivbaranov.mli.example;

import static org.junit.Assert.*;
import static java.lang.Math.abs;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.agp.utils.Color;
import ohos.app.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;

public class MaterialLetterIconTest {
    private Context context;
    private AttrSet attrSet;
    private MaterialLetterIcon materialLetterIcon;

    @Before
    public void setUp() throws Exception {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        attrSet = new AttrSet() {
            @Override
            public Optional<String> getStyle() {
                return Optional.empty();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Optional<Attr> getAttr(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<Attr> getAttr(String s) {
                return Optional.empty();
            }
        };

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMaterialLetterIcon() {
        materialLetterIcon = new MaterialLetterIcon(context);
        assertNotNull(materialLetterIcon);
    }

    @Test
    public void testMaterialLetterIconAttrSet() {
        materialLetterIcon = new MaterialLetterIcon(context, attrSet);
        assertNotNull(materialLetterIcon);
    }

    @Test
    public void testMaterialLetterIconDefStyleAttr() {
        materialLetterIcon = new MaterialLetterIcon(context, attrSet, 0);
        assertNotNull(materialLetterIcon);
    }

    @Test
    public void testMaterialLetterIconDefStyleRes() {
        materialLetterIcon = new MaterialLetterIcon(context, attrSet, 0, 0);
        assertNotNull(materialLetterIcon);
    }

    @Test
    public void testShapeColor() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.shapeColor(Color.GREEN);
        materialLetterIcon = builder.create();
        assertEquals(Color.GREEN, materialLetterIcon.getShapeColor());
    }

    @Test
    public void testShapeType() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.shapeType(MaterialLetterIcon.Shape.CIRCLE);
        materialLetterIcon = builder.create();
        assertEquals(MaterialLetterIcon.Shape.CIRCLE, materialLetterIcon.getShapeType());
    }

    @Test
    public void testBorder() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.border(true);
        materialLetterIcon = builder.create();
        assertTrue(materialLetterIcon.hasBorder());
    }

    @Test
    public void testBorderColor() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.borderColor(Color.GREEN);
        materialLetterIcon = builder.create();
        assertEquals(Color.GREEN, materialLetterIcon.getBorderColor());
    }

    @Test
    public void testBorderSize() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.borderSize(10);
        materialLetterIcon = builder.create();
        assertEquals(10, materialLetterIcon.getBorderSize());
    }

    @Test
    public void testLetter() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.letter("M");
        materialLetterIcon = builder.create();
        assertEquals("M", materialLetterIcon.getLetter());
    }

    @Test
    public void testsetLetter() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        materialLetterIcon = builder.create();
        materialLetterIcon.setLetter("Material Letter Icon");
        assertEquals("M", materialLetterIcon.getLetter());
    }

    @Test
    public void testsetLetterWithNull() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        materialLetterIcon = builder.create();
        String nullString = null;
        materialLetterIcon.setLetter(nullString);
        assertNull(materialLetterIcon.getLetter());
    }

    @Test
    public void testsetLetterWithEmpty() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        materialLetterIcon = builder.create();
        materialLetterIcon.setLetter("");
        assertNull(materialLetterIcon.getLetter());
    }

    @Test
    public void testLetterColor() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.letterColor(Color.GREEN);
        materialLetterIcon = builder.create();
        assertEquals(Color.GREEN, materialLetterIcon.getLetterColor());
    }

    @Test
    public void testLetterSize() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.letterSize(10);
        materialLetterIcon = builder.create();
        assertEquals(10, materialLetterIcon.getLetterSize());
    }

    @Test
    public void testLettersNumber() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.lettersNumber(3);
        materialLetterIcon = builder.create();
        assertEquals(3, materialLetterIcon.getLettersNumber());
    }

    @Test
    public void testInitials() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.initials(true);
        materialLetterIcon = builder.create();
        assertTrue(materialLetterIcon.isInitials());
    }

    @Test
    public void testInitialsNumber() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.initialsNumber(3);
        materialLetterIcon = builder.create();
        assertEquals(3, materialLetterIcon.getInitialsNumber());
    }

    @Test
    public void testRoundRectRx() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.roundRectRx(10);
        materialLetterIcon = builder.create();
        assertTrue(abs(10 - materialLetterIcon.getRoundRectRx()) < 0.1);
    }

    @Test
    public void testRoundRectRy() {
        MaterialLetterIcon.Builder builder = new MaterialLetterIcon.Builder(context);
        builder.roundRectRy(10);
        materialLetterIcon = builder.create();
        assertTrue(abs(10 - materialLetterIcon.getRoundRectRy()) < 0.1);
    }
}