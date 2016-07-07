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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import au.com.cybersearch2.controls.ControlFactory;


/**
 * StatusBarToolControlTest
 * @author Andrew Bowley
 * 29 Jun 2016
 */
public class StatusBarToolControlTest
{
    @Test
    public void test_createGui()
    {
        StatusBarToolControl underTest = new StatusBarToolControl();
        ControlFactory controlFactory = mock(ControlFactory.class);
        StatusBar statusBar = mock(StatusBar.class);
        Composite parent = mock(Composite.class);
        Shell shell = mock(Shell.class);
        underTest.controlFactory = controlFactory;
        underTest.statusBar = statusBar;
        Composite composite = mock(Composite.class);
        Accessible accessible = mock(Accessible.class);
        when(composite.getAccessible()).thenReturn(accessible );
        when(controlFactory.compositeInstance(parent)).thenReturn(composite);
        underTest.createGui(parent, shell);
        ArgumentCaptor<StatusLineLayout> layoutCaptor = ArgumentCaptor.forClass(StatusLineLayout.class);
        verify(composite).setLayout(layoutCaptor.capture());
        assertThat(layoutCaptor.getValue().shell).isEqualsToByComparingFields(shell);
        ArgumentCaptor<AccessibleControlAdapter> listenerCaptor = ArgumentCaptor.forClass(AccessibleControlAdapter.class);
        verify(accessible).addAccessibleControlListener(listenerCaptor.capture());
        AccessibleControlEvent event = new AccessibleControlEvent(underTest);
        listenerCaptor.getValue().getRole(event);
        assertThat(event.detail).isEqualTo(ACC.ROLE_STATUSBAR);
        verify(statusBar).setToolControl(underTest);
    }

    @Test
    public void test_fill()
    {
        StatusBarToolControl underTest = new StatusBarToolControl();
        ControlFactory controlFactory = mock(ControlFactory.class);
        underTest.controlFactory = controlFactory;
        Composite composite = mock(Composite.class);
        underTest.composite = composite;
        StatusControl item = mock(StatusControl.class);
        int childCount = 1;
        Control[] newChildren = new Control[2];
        when(composite.getChildren()).thenReturn(newChildren);
        Control newControl = mock(Control.class);
        newChildren[1] = newControl;
        assertThat(underTest.fill(item, childCount)).isEqualTo(2);
        verify(item).fill(controlFactory, composite);
        verify(newControl).setData(item);
    }

    @Test
    public void test_redraw()
    {
        StatusBarToolControl underTest = new StatusBarToolControl();
        ControlFactory controlFactory = mock(ControlFactory.class);
        underTest.controlFactory = controlFactory;
        Composite composite = mock(Composite.class);
        Control control1 = mock(Control.class);
        Control control2 = mock(Control.class);
        Control[] initialChildren = new Control[]{};
        when(composite.getChildren()).thenReturn(initialChildren, initialChildren, new Control[]{control1}, new Control[]{control1,control2});
        underTest.composite = composite;
        List<StatusControl> controlList = new ArrayList<StatusControl>();
        StatusControl statusControl1 = mock(StatusControl.class);
        when(statusControl1.isVisible()).thenReturn(true);
        controlList.add(statusControl1);
        StatusControl statusControl2 = mock(StatusControl.class);
        when(statusControl2.isVisible()).thenReturn(true);
        controlList.add(statusControl2);
        underTest.redraw(controlList);
        verify(composite).setRedraw(false);
        verify(statusControl1, times(0)).separate(controlFactory, composite);
        verify(statusControl1).fill(controlFactory, composite);
        verify(control1).setData(statusControl1);
        verify(statusControl2).separate(controlFactory, composite);
        verify(statusControl2).fill(controlFactory, composite);
        verify(control2).setData(statusControl2);
        verify(composite).layout();
        verify(composite).setRedraw(true);
    }

    @Test
    public void test_redraw_rewrite()
    {
        StatusControl statusControl1 = mock(StatusControl.class);
        when(statusControl1.isVisible()).thenReturn(true);
        StatusControl statusControl2 = mock(StatusControl.class);
        when(statusControl2.isVisible()).thenReturn(true);
        StatusBarToolControl underTest = new StatusBarToolControl();
        ControlFactory controlFactory = mock(ControlFactory.class);
        underTest.controlFactory = controlFactory;
        Composite composite = mock(Composite.class);
        Control control01 = mock(Control.class);
        when(control01.getData()).thenReturn(statusControl1);
        Control control02 = mock(Control.class);
        when(control02.getData()).thenReturn(statusControl2);
        Control control1 = mock(Control.class);
        Control control2 = mock(Control.class);
        Control[] initialChildren = new Control[]{control01,control02};
        when(composite.getChildren()).thenReturn(initialChildren, new Control[]{}, new Control[]{control1}, new Control[]{control1,control2});
        underTest.composite = composite;
        List<StatusControl> controlList = new ArrayList<StatusControl>();
        controlList.add(statusControl1);
        controlList.add(statusControl2);
        underTest.redraw(controlList);
        verify(composite).setRedraw(false);
        verify(control01).dispose();
        verify(control02).dispose();
        verify(statusControl1, times(0)).separate(controlFactory, composite);
        verify(statusControl1).fill(controlFactory, composite);
        verify(control1).setData(statusControl1);
        verify(statusControl2).separate(controlFactory, composite);
        verify(statusControl2).fill(controlFactory, composite);
        verify(control2).setData(statusControl2);
        verify(composite).layout();
        verify(composite).setRedraw(true);
    }
    
    @Test
    public void test_redraw_null_and_not_visible()
    {
        StatusBarToolControl underTest = new StatusBarToolControl();
        ControlFactory controlFactory = mock(ControlFactory.class);
        underTest.controlFactory = controlFactory;
        Composite composite = mock(Composite.class);
        Control control1 = mock(Control.class);
        Control control2 = mock(Control.class);
        Control[] initialChildren = new Control[]{};
        when(composite.getChildren()).thenReturn(initialChildren, initialChildren, new Control[]{control1}, new Control[]{control1,control2});
        underTest.composite = composite;
        List<StatusControl> controlList = new ArrayList<StatusControl>();
        StatusControl statusControl1 = mock(StatusControl.class);
        when(statusControl1.isVisible()).thenReturn(true);
        controlList.add(statusControl1);
        StatusControl statusControl2 = mock(StatusControl.class);
        when(statusControl2.isVisible()).thenReturn(false);
        controlList.add(statusControl2);
        controlList.add(null);
        StatusControl statusControl3 = mock(StatusControl.class);
        when(statusControl3.isVisible()).thenReturn(true);
        controlList.add(statusControl3);
        underTest.redraw(controlList);
        verify(composite).setRedraw(false);
        verify(statusControl1, times(0)).separate(controlFactory, composite);
        verify(statusControl1).fill(controlFactory, composite);
        verify(control1).setData(statusControl1);
        verify(statusControl3).separate(controlFactory, composite);
        verify(statusControl3).fill(controlFactory, composite);
        verify(control2).setData(statusControl3);
        verify(composite).layout();
        verify(composite).setRedraw(true);
    }


}
