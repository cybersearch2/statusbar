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

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import au.com.cybersearch2.controls.LabelLayoutData;
import au.com.cybersearch2.controls.ControlFactory;
import au.com.cybersearch2.statusbar.LabelItem.Field;
import au.com.cybersearch2.statusbar.controls.StatusItemLayoutData;

/**
 * StatusControl
 * Control for one status line item
 * @author Andrew Bowley
 * 27 Jun 2016
 */
public class StatusControl
{
    /** Configures and controls one status line item */
    StatusItem statusItem;
    /** The custom label */
    CLabel label;
    /** The seperator - omitted for first item in status line */
    Label seperator;

    /**
     * Construct StatusControl object
     * @param statusItem Configures and controls one status line item
     */
    public StatusControl(StatusItem statusItem)
    {
        this.statusItem = statusItem;
    }

    /**
     * @return StatusItem object
     */
    public StatusItem getStatusItem()
    {
        return statusItem;
    }
 
    /**
     * @return the label
     */
    public CLabel getLabel()
    {
        return label;
    }
    /**
     * @return isVisible flag
     */
    public boolean isVisible()
    {
        return statusItem.isVisible();
    }
 
    /**
     * Add widgets to status line
     * @param controlFactory SWT widget factory
     * @param parent Parent composite
     */
    public void fill(ControlFactory controlFactory, Composite parent) 
    {   
        if (label != null)
             disposeLabel();
        label = statusItem.labelInstance(controlFactory, parent);
        if (seperator != null)
        {
            // Separator is same height as label and width determine by layout setter
            StatusItemLayoutData statusLineLayoutData = new StatusItemLayoutData();
            statusLineLayoutData.heightHint = ((LabelLayoutData)label.getLayoutData()).heightHint;
            seperator.setLayoutData(statusLineLayoutData);
        }
    }

    /**
     * Add widgets to status line
     * @param controlFactory SWT widget factory
     * @param parent Parent composite
     */
    public void separate(ControlFactory controlFactory, Composite parent) 
    {   
        if (seperator != null)
            seperator.dispose();
        seperator = controlFactory.separatorInstance(parent);
    }

    /**
     * Update specified fields 
     * @param updateFields Array of field identifiers
     */
    public void update(Field[] updateFields)
    {
        if (label != null)
            for (Field field: updateFields)
            {
                if (field == Field.text)
                    label.setText(statusItem.getText());
                else if (field == Field.image)
                    label.setImage(statusItem.getImage());
                else if (field == Field.tooltip)
                    label.setToolTipText(statusItem.getTooltip());
        }
    }

    /**
     * Dispose widgets
     */
    public void dispose()
    {
        if ((seperator != null) && !seperator.isDisposed())
            seperator.dispose();
        disposeLabel();
    }

    /**
     * Dispose widgets
     */
    public void disposeLabel()
    {
        if ((label != null) && !label.isDisposed())
            label.dispose();
    }
}
