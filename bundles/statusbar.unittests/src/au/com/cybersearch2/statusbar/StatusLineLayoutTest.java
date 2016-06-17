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

import static org.mockito.Mockito.*;

import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

/**
 * ChatStatusLineLayoutTest
 * @author Andrew Bowley
 * 21 Mar 2016
 */
public class StatusLineLayoutTest
{
    int C1_WIDTH = 50;
    int C2_WIDTH = 105;
    int C3_WIDTH = 30;
    
    @Test
    public void test_computeSize_default()
    {
        Shell shell = mock(Shell.class);
        Rectangle clientArea = new Rectangle(0, 0, 500, 499);
        when(shell.getClientArea()).thenReturn(clientArea);
        StatusLineLayout underTest = new StatusLineLayout(shell);
        Composite composite = mock(Composite.class);
        boolean changed = true;
        Point point = underTest.computeSize(composite, SWT.DEFAULT, SWT.DEFAULT, changed);
        assertThat(point.x).isEqualTo(500);
        assertThat(point.y).isEqualTo(21);
    }

    @Test
    public void test_computeSize_custom()
    {
        Shell shell = mock(Shell.class);
        StatusLineLayout underTest = new StatusLineLayout(shell);
        Composite composite = mock(Composite.class);
        boolean changed = true;
        Point point = underTest.computeSize(composite, 900, 21, changed);
        assertThat(point.x).isEqualTo(900);
        assertThat(point.y).isEqualTo(21);
    }
    
    @Test
    public void test_layout()
    {
        Composite composite = mock(Composite.class);
        Rectangle clientArea = new Rectangle(5, 800, 500, 16);
        when(composite.getClientArea()).thenReturn(clientArea);
        Shell shell = mock(Shell.class);
        StatusLineLayout underTest = new StatusLineLayout(shell);
        Control[] children = new Control[5];
        Control control1 = mock(Control.class);
        when(control1.computeSize(C1_WIDTH, 16, false)).thenReturn(new Point(C1_WIDTH, 16));
        StatusLineLayoutData c1Layout = new StatusLineLayoutData();
        c1Layout.widthHint = C1_WIDTH;
        when(control1.getLayoutData()).thenReturn(c1Layout);
        children[0] = control1;
        Control seperator1 = mock(Control.class);
        when(seperator1.computeSize(SWT.DEFAULT, 16, false)).thenReturn(new Point(0, 16));
        StatusLineLayoutData s1Layout = new StatusLineLayoutData();
        s1Layout.widthHint = SWT.DEFAULT;
        when(seperator1.getLayoutData()).thenReturn(s1Layout);
        children[1] = seperator1;
        Control control2 = mock(Control.class);
        when(control2.computeSize(C2_WIDTH, 16, false)).thenReturn(new Point(C2_WIDTH, 16));
        StatusLineLayoutData c2Layout = new StatusLineLayoutData();
        c2Layout.widthHint = C2_WIDTH;
        when(control2.getLayoutData()).thenReturn(c2Layout);
        children[2] = control2;
        Control seperator2 = mock(Control.class);
        when(seperator2.computeSize(SWT.DEFAULT, 16, false)).thenReturn(new Point(0, 16));
        StatusLineLayoutData s2Layout = new StatusLineLayoutData();
        s2Layout.widthHint = SWT.DEFAULT;
        when(seperator2.getLayoutData()).thenReturn(s2Layout);
        children[3] = seperator2;
        Control control3 = mock(Control.class);
        when(control3.computeSize(C3_WIDTH, 16, false)).thenReturn(new Point(C3_WIDTH, 16));
        StatusLineLayoutData c3Layout = new StatusLineLayoutData();
        c3Layout.widthHint = C3_WIDTH;
        when(control3.getLayoutData()).thenReturn(c3Layout);
        children[4] = control3;
        when(composite.getChildren()).thenReturn(children);
        underTest.layout(composite, false);
        final int LEFT = 500 - C3_WIDTH - 3;
        final int STRETCH_POS = 5 + C1_WIDTH + 3;
        verify(control1).setBounds(5, 800, C1_WIDTH, 16);
        verify(seperator1).setBounds(STRETCH_POS, 800, 0, 16);
        verify(control2).setBounds(STRETCH_POS, 800, LEFT - STRETCH_POS, 16);
        verify(seperator2).setBounds(LEFT, 800, 0, 16);
        verify(control3).setBounds(500 - C3_WIDTH, 800, C3_WIDTH, 16);
    }
}
