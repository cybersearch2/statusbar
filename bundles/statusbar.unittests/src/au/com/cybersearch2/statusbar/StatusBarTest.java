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

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;

import au.com.cybersearch2.controls.StatusBarControlFactory;
import au.com.cybersearch2.statusbar.LabelItem.Field;

/**
 * StatusBarTest
 * @author Andrew Bowley
 * 29 Jun 2016
 */
public class StatusBarTest
{
    static final String TEST_MESSAGE = "Testing 123";
    static final String TOOL_TIP = "Tool tip";

    @Test
    public void test_postConstruct()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        assertThat(underTest.capacity).isEqualTo(5);
        assertThat(underTest.controlList).isNotNull();
    }
    
    @Test
    public void test_setToolControl()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        StatusControl statusControl = mock(StatusControl.class);
        when(statusControl.isVisible()).thenReturn(true);
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(0);
        when(statusControl.getStatusItem()).thenReturn(statusItem);
        underTest.controlList.add(statusControl);
        StatusBarToolControl statusBarToolControl = mock(StatusBarToolControl.class);
        underTest.setToolControl(statusBarToolControl );
        assertThat(underTest.statusBarToolControl).isEqualTo(statusBarToolControl);
        verify(statusBarToolControl).redraw(underTest.controlList);
    }

    @Test
    public void test_setCapacity()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        StatusControl statusControl = mock(StatusControl.class);
        underTest.controlList.add(statusControl);
        underTest.setCapacity(3);
        verify(statusControl).dispose();
        assertThat(underTest.controlList).isEmpty();
        assertThat(underTest.getCapacity()).isEqualTo(3);
        try
        {
            underTest.setCapacity(0);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Parameter \"capacity\" is invalid");
        }
        try
        {
            underTest.setCapacity(21);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Parameter \"capacity\" is invalid");
        }
   }
    
    @Test
    public void test_addStatusItem()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(0);
        underTest.addStatusItem(statusItem);
        verify(statusItem).setLabelItemListener(underTest);
        assertThat(underTest.controlList.get(0).getStatusItem()).isEqualTo(statusItem);
        CLabel label = mock(CLabel.class);
        underTest.controlList.get(0).label = label;
        // Replace
        StatusItem statusItem2 = mock(StatusItem.class);
        when(statusItem2.getId()).thenReturn(0);
        underTest.addStatusItem(statusItem2);
        verify(statusItem2).setLabelItemListener(underTest);
        assertThat(underTest.controlList.get(0).getStatusItem()).isEqualTo(statusItem2);
        verify(label).dispose();
        // Insert out of order
        StatusItem statusItem3 = mock(StatusItem.class);
        when(statusItem3.getId()).thenReturn(2);
        underTest.addStatusItem(statusItem3);
        verify(statusItem3).setLabelItemListener(underTest);
        assertThat(underTest.controlList.get(2).getStatusItem()).isEqualTo(statusItem3);
        assertThat(underTest.controlList.get(1)).isNull();
        assertThat(underTest.controlList.get(0).getStatusItem()).isEqualTo(statusItem2);
     }
    
    @Test
    public void test_onUpdate()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(0);
        when(statusItem.getText()).thenReturn(TEST_MESSAGE);
        Display display = mock(Display.class);
        Image image = new Image(display, "icons/bullet.gif");
        when(statusItem.getImage()).thenReturn(image);
        when(statusItem.getTooltip()).thenReturn(TOOL_TIP);
        underTest.addStatusItem(statusItem);
        StatusBarControlFactory controlFactory = mock(StatusBarControlFactory.class);
        Composite parent = mock(Composite.class);
        CLabel label = mock(CLabel.class);
        when(statusItem.labelInstance(controlFactory, parent)).thenReturn(label);
        underTest.controlList.get(0).fill(controlFactory, parent);
        Field[] updateFields = new Field[]{Field.text, Field.image,Field.tooltip};
        underTest.onUpdate(statusItem, updateFields);
        verify(label).setText(TEST_MESSAGE);
        verify(label).setImage(image);
        verify(label).setToolTipText(TOOL_TIP);
        when(statusItem.getId()).thenReturn(-1);
        try
        {
            underTest.onUpdate(statusItem, updateFields);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Id = " + -1 + " is outside range of 0 - 4");
        }
        when(statusItem.getId()).thenReturn(5);
        try
        {
            underTest.onUpdate(statusItem, updateFields);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Id = " + 5 + " is outside range of 0 - 4");
        }
    }
    
    @Test
    public void test_onRedraw()
    {
        StatusBar underTest = new StatusBar();
        underTest.postConstruct();
        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(0);
        StatusBarToolControl statusBarToolControl = mock(StatusBarToolControl.class);
        underTest.statusBarToolControl = statusBarToolControl;
        underTest.onRedraw(statusItem);
        verify(statusBarToolControl).redraw(underTest.controlList);
        when(statusItem.getId()).thenReturn(-1);
        try
        {
            underTest.onRedraw(statusItem);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Id = " + -1 + " is outside range of 0 - 4");
        }
        when(statusItem.getId()).thenReturn(5);
        try
        {
            underTest.onRedraw(statusItem);
            failBecauseExceptionWasNotThrown(StatusBarException.class);
        }
        catch (StatusBarException e)
        {
            assertThat(e.getMessage()).isEqualTo("Id = " + 5 + " is outside range of 0 - 4");
        }
    }
}
