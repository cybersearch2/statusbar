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
package au.com.cybersearch2.statusbar.controls;

import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import au.com.cybersearch2.controls.StatusBarControlFactory;

/**
 * ControlFactory
 * SWT widget factory for testability
 * @author Andrew Bowley
 * 2 Mar 2016
 */
public class ControlFactory implements StatusBarControlFactory
{
    
    /**
     * @see au.com.cybersearch2.controls.StatusBarControlFactory#compositeInstance(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public Composite compositeInstance(Composite parent)
    {
        return new Composite(parent, SWT.NONE);
    }
    
    /**
     * @see au.com.cybersearch2.controls.StatusBarControlFactory#separatorInstance(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public Label separatorInstance(Composite parent)
    {
        return new Label(parent, SWT.SEPARATOR);
    }

    /**
     * @see au.com.cybersearch2.controls.StatusBarControlFactory#customLabelInstance(org.eclipse.swt.widgets.Composite, java.lang.String, org.eclipse.swt.graphics.Image, int)
     */
    @Override
    public CLabel customLabelInstance(Composite parent, String text, Image image, int widthHint)
    {
        CLabel label = new CLabel(parent, SWT.SHADOW_NONE);
        StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
        GC gc = new GC(parent);
        gc.setFont(parent.getFont());
        FontMetrics fm = gc.getFontMetrics();
        Point extent = gc.textExtent(text);
        if (widthHint > 0) 
            statusLineLayoutData.widthHint = fm.getAverageCharWidth() * widthHint;
        else 
            statusLineLayoutData.widthHint = extent.x +label.getLeftMargin() + label.getRightMargin();
        gc.dispose();

        statusLineLayoutData.heightHint = fm.getHeight();
        label.setLayoutData(statusLineLayoutData);
        label.setText(text);
        label.setImage(image);
        return label;
    }
}
