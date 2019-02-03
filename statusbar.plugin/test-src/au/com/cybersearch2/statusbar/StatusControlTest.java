/**
    Copyright (C) 2016  www.cybersearch2.com.au

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/> */
package au.com.cybersearch2.statusbar;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import au.com.cybersearch2.controls.LabelLayoutData;
import au.com.cybersearch2.controls.ControlFactory;
import au.com.cybersearch2.statusbar.LabelItem.Field;

/**
 * StatusControlTest
 * @author Andrew Bowley
 * 28 Jun 2016
 */
public class StatusControlTest
{
    static final String TEST_MESSAGE = "Testing 123";
    static final String TOOL_TIP = "Tool tip";

    @Test
    public void test_fill()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(1);
        when(statusItem.isVisible()).thenReturn(true);
        StatusControl underTest = new StatusControl(statusItem);
        Composite parent = mock(Composite.class);
        Label seperator = mock(Label.class);
        underTest.seperator = seperator;
        CLabel label = mock(CLabel.class);
        when(statusItem.labelInstance(controlFactory, parent)).thenReturn(label);
        LabelLayoutData layoutData = new LabelLayoutData();
        layoutData.heightHint = 16;
        when(label.getLayoutData()).thenReturn(layoutData);
        underTest.fill(controlFactory, parent);
        ArgumentCaptor<LabelLayoutData> layoutDataCaptor = ArgumentCaptor.forClass(LabelLayoutData.class);
        verify(seperator).setLayoutData(layoutDataCaptor.capture());
        assertThat(layoutDataCaptor.getValue().heightHint).isEqualTo(16);
        assertThat(underTest.getStatusItem()).isEqualTo(statusItem);
        assertThat(underTest.isVisible()).isTrue();
        
    }

    @Test
    public void test_separate()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        StatusItem statusItem = mock(StatusItem.class);
        StatusControl underTest = new StatusControl(statusItem);
        Composite parent = mock(Composite.class);
        Label seperator = mock(Label.class);
        when(controlFactory.separatorInstance(parent)).thenReturn(seperator);
        underTest.separate(controlFactory, parent);
        
    }


    @Test
    public void test_fill_first_item()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        StatusItem statusItem = mock(StatusItem.class);
        StatusControl underTest = new StatusControl(statusItem);
        Composite parent = mock(Composite.class);
        CLabel label = mock(CLabel.class);
        when(statusItem.labelInstance(controlFactory, parent)).thenReturn(label);
        underTest.fill(controlFactory, parent);
    }

    @Test
    public void test_fill_reuse()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(1);
        StatusControl underTest = new StatusControl(statusItem);
        CLabel originalLabel = mock(CLabel.class);
        Label originalSeperator = mock(Label.class);
        underTest.label = originalLabel;
        underTest.seperator = originalSeperator;
        Composite parent = mock(Composite.class);
        Label seperator = mock(Label.class);
        when(controlFactory.separatorInstance(parent)).thenReturn(seperator);
        CLabel label = mock(CLabel.class);
        when(statusItem.labelInstance(controlFactory, parent)).thenReturn(label);
        LabelLayoutData layoutData = new LabelLayoutData();
        layoutData.heightHint = 16;
        when(label.getLayoutData()).thenReturn(layoutData);
        underTest.separate(controlFactory, parent);
        underTest.fill(controlFactory, parent);
        ArgumentCaptor<LabelLayoutData> layoutDataCaptor = ArgumentCaptor.forClass(LabelLayoutData.class);
        verify(seperator).setLayoutData(layoutDataCaptor.capture());
        assertThat(layoutDataCaptor.getValue().heightHint).isEqualTo(16);
        verify(originalLabel).dispose();
        verify(originalSeperator).dispose();
    }

    @Test
    public void test_update()
    {
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getText()).thenReturn(TEST_MESSAGE);
        Display display = mock(Display.class);
        Image image = new Image(display, "test-src/resources/bullet.gif");
        when(statusItem.getImage()).thenReturn(image);
        when(statusItem.getTooltip()).thenReturn(TOOL_TIP);
        when(statusItem.getId()).thenReturn(1);
        StatusControl underTest = new StatusControl(statusItem);
        CLabel label = mock(CLabel.class);
        underTest.label = label;
        Field[] updateFields = new Field[]{Field.text, Field.image,Field.tooltip};
        underTest.update(updateFields);
        verify(label).setText(TEST_MESSAGE);
        verify(label).setImage(image);
        verify(label).setToolTipText(TOOL_TIP);
        underTest.label = null;
        underTest.update(updateFields);
    }
    
    @Test
    public void test_dispose()
    {
        StatusItem statusItem = mock(StatusItem.class);
        StatusControl underTest = new StatusControl(statusItem);
        CLabel label = mock(CLabel.class);
        underTest.label = label;
        Label seperator = mock(Label.class);
        underTest.seperator = seperator;
        underTest.dispose();
        verify(label).dispose();
        verify(seperator).dispose();
        underTest.label = null;
        underTest.seperator = null;
        underTest.dispose();
    }
}
