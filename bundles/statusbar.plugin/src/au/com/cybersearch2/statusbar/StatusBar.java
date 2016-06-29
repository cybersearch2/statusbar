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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import au.com.cybersearch2.statusbar.LabelItem.Field;

/**
 * StatusBar
 * Container and controller for items on the status line.
 * @author Andrew Bowley
 * 27 Jun 2016
 */
public class StatusBar implements LabelItemListener
{
    /** Default number of status line items. Call {@link #setCapacity(int)} to adjust. */
    public static int DEFAULT_MAX_ITEMS = 5;
    /** Maximum permitted number of status line items - required for safety */
    public static int MAX_ITEMS_LIMIT = 20;

    /** Number of items supported. Must be greater than 0 but not more than MAX_ITEMS_LIMIT */
    int capacity;
    /** The status line controls, each containing a StatusItem object to configure it */
    List<StatusControl> controlList;
    StatusBarToolControl statusBarToolControl;
 
    /**
     * postConstruct
     * @param controlFactory SWT widget factory
     */
    @PostConstruct
    void postConstruct()
    {
        capacity = DEFAULT_MAX_ITEMS;
        controlList = new ArrayList<StatusControl>(DEFAULT_MAX_ITEMS);
    }
 
    /**
     * Set control which arranges the status line itms in a window trim.
     * This control is created upon completion of application start up,
     * and therefore has be injected post construction.
     * @param composite Parent composite
     */
    public void setToolControl(StatusBarToolControl statusBarToolControl)
    {
        this.statusBarToolControl = statusBarToolControl;
        // Render status line if at least one control is visible
        for (StatusControl control: controlList)
            if (control.isVisible())
            {
                onRedraw(control.getStatusItem());
                return;
            }
    }

    /**
     * Set maximum status line item limit. Clears all controls as well to allow reduction in capacity.
     * @param capacity int value
     */
    public void setCapacity(int capacity)
    {
        if ((capacity < 1) || (capacity > MAX_ITEMS_LIMIT))
            throw new StatusBarException("Parameter \"capacity\" is invalid");
        dispose();
        this.capacity = capacity;
    }
 
    /**
     * @return maximum status line item limit
     */
    public int getCapacity()
    {
        return capacity;
    }

    /**
     * Dispose of status line widgets
     */
    public void dispose()
    {
        for (StatusControl control: controlList)
            if (control != null)
                control.dispose();
        controlList.clear();
    }

    /**
     * Add item
     * @param statusItem SatusItem object
     */
    public void addStatusItem(StatusItem statusItem)
    {
        int index = statusItem.getId();
        validateId(index);
        statusItem.setLabelItemListener(this);
        StatusControl newControl = new StatusControl(statusItem);
        if (index == controlList.size())
            // Normal progression
            controlList.add(newControl);
        else if (index < controlList.size())
        {   // Replacement
            StatusControl control = controlList.get(index);
            if (control != null)
                control.dispose();
            controlList.set(index, newControl);
        }
        else
        {   // Insert out of order. Fill in any gap with nulls.
            while (controlList.size() < index)
                controlList.add(null);
            controlList.add(newControl);
        }
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItemListener#onUpdate(au.com.cybersearch2.statusbar.LabelItem, au.com.cybersearch2.statusbar.LabelItem.Field[])
     */
    @Override
    public void onUpdate(LabelItem labelItem, Field[] updateFields)
    {
        int index = labelItem.getId();
        validateId(index);
        controlList.get(index).update(updateFields);
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItemListener#onRedraw(au.com.cybersearch2.statusbar.LabelItem)
     */
    @Override
    public void onRedraw(LabelItem labelItem)
    {
        validateId(labelItem.getId());
        if (statusBarToolControl != null)
            statusBarToolControl.redraw(controlList);
    }

    /**
     * Validate status item id
     * @param id int value
     */
    void validateId(int id)
    {
        if ((id < 0) || (id >= capacity))
            throw new StatusBarException("Id = " + id + " is outside range of 0 - " + (capacity - 1));
    }


}
