/**
    Copyright (C) 2015  www.cybersearch2.com.au

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
// Copyright from original StatusLine class
/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 440270
 *******************************************************************************/
package au.com.cybersearch2.statusbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

import au.com.cybersearch2.statusbar.controls.StatusControl;

/**
 * ChatStatusLineLayout
 * Lays out controls in status line to occupy whole of client area width, 
 * while control height is fixed at 20 pixels.
 * The control size is set using setBounds() which sets the receiver's size 
 * and location to the rectangular area specified by the arguments. 
 * A label defined with default width layout hint is used as a separator between
 * each content item. 
 * 
 * @author Andrew Bowley
 * 17 Nov 2015
 */
public class StatusLineLayout extends Layout
{
    /** Height of status line in pixels. Set for 16-pixel high icons */
    public static final int STATUS_LINE_HEIGHT = 21;
    /** Active shell. Shell client area provides default width. */
    private Shell shell;

    /**
     * Create ChatStatusLineLayout object
     * @param shell Active shell
     */
    public StatusLineLayout(Shell shell)
    {
        this.shell = shell;
    }

    /**
     * 
     * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite, int, int, boolean)
     */
    @Override
    public Point computeSize(Composite composite, int wHint, int hHint,
            boolean changed) 
    {
        if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) 
            return new Point(wHint, hHint);
        return new Point(shell.getClientArea().width, STATUS_LINE_HEIGHT);
    }

    /**
     * 
     * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
     */
    @Override
    public void layout(Composite composite, boolean changed) 
    {

        if (composite == null) 
            return;
        // Client area provides top, height and width of area to accommodate controls
        Rectangle rect = composite.getClientArea();
        // Check for zero width which means client area still to be rendered
        if (rect.width == 0)
            return;

        Control[] children = composite.getChildren();
        StatusControl[] controls = new StatusControl[children.length];
        int count = 0;
        // Track available space in case there is insufficient room
        int left = rect.width;
        for (Control control: children)
        {
            StatusControl statusControl = new StatusControl(control, rect.y, rect.height, changed);
            controls[count] = statusControl;
            int width = statusControl.getWidth();
            if (left - width < StatusControl.GAP) 
            { // No room for remaining controls, if any
                statusControl.setWidth(left - StatusControl.GAP);
                while (++count < children.length)
                {
                    statusControl = new StatusControl(control, rect.y, rect.height, changed);
                    statusControl.setWidth(0);
                    controls[count] = statusControl;
                }
                break;
            }
            ++count;
            left -= width;
        }
        if (count == 0) // No children not expected
            return; 
        // Horizontal position in status line, initially on left side of client area
        int x = rect.x;
        // First control is aligned left
        x = controls[0].position(x);
        if (count < 3) // Count = 2 not expected as there should be a separator
             return;
        // Last control is aligned right
        // Mark start position of aligned control
        left = controls[count - 1].alignRight(rect.width);
         // Insert separator
        controls[count-2].position(left);
        if (count == 3)
            return;
        // Insert remaining controls, stretching the last one to fill the remaining space.
        for (int i = 1; i < count - 3; i++) 
            x = controls[i].position(x);
        controls[count - 3].position(x, left - x);
    }
}
