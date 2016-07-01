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

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import au.com.cybersearch2.controls.CustomLabelSpec;
import au.com.cybersearch2.controls.LabelLayoutData;

/**
 * StatusItemLayoutData
 * Customises Status Line layout data to adjust height and width hints according to font
 * @author Andrew Bowley
 * 25 May 2016
 */
public class StatusItemLayoutData extends LabelLayoutData
{

    /**
     * Create default StatusItemLayoutData object
     */
    public StatusItemLayoutData()
    {
        super();
    }
    
    /**
     * Create StatusItemLayoutData object
     * @param label A label control which supports aligned text and/or an image
     * @param text Text to display in control
     * @param widthHint Given width hint in characters. A value of 0 means use text width.
     */
    public StatusItemLayoutData(CLabel label, CustomLabelSpec specification)
    {
        Composite parent = label.getParent();
        GC gc = new GC(parent);
        gc.setFont(parent.getFont());
        FontMetrics fontMetrics = gc.getFontMetrics();
        String text = specification.getText();
        if (specification.getWidth() > 0) 
            this.widthHint = specification.getWidth() * fontMetrics.getAverageCharWidth();
        else 
        {
            this.widthHint = label.getLeftMargin() + label.getRightMargin(); 
            if ((text != null) && !text.isEmpty())
                this.widthHint += gc.textExtent(text).x;
            Image image = specification.getImage();
            if (image != null)
                this.widthHint += image.getBounds().width + 5;
        }
        gc.dispose();
        heightHint = fontMetrics.getHeight();
    }

}
