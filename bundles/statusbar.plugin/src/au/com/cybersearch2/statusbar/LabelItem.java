/**
    Copyright (C) 2016  www.cybersearch2.com.au

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General License for more details.

    You should have received a copy of the GNU General License
    along with this program.  If not, see <http://www.gnu.org/licenses/> */
package au.com.cybersearch2.statusbar;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;

import au.com.cybersearch2.controls.CustomLabelSpec;
import au.com.cybersearch2.controls.ControlFactory;

/**
 * LabelItem
 * @author Andrew Bowley
 * 26 Jun 2016
 */
interface LabelItem
{
    enum Field
    {
        text,
        image,
        tooltip
    }
    
    /**
     * Returns identity
     * @return int value
     */
    int getId();

    /**
     * @return isVisible flag
     */
    boolean isVisible();
    
    /**
     * @return the image
     */
    Image getImage();

    /**
     * @return the text
     */
    String getText();

    /**
     * @return the width hint in number of characters
     */
    int getWidth();

    /**
     * Set image
     * @param image Image object
     */
    void setImage(Image image);

    /**
     * Set the text
     * @param text String
     */
    void setText(String text);

    /**
     * Set width hint
     * @param width Width in number of characters
     */
    void setWidth(int width);

    /**
     * Set both text and image
     * @param text String
     * @param image Image object
     */
    void setLabel(String text, Image image);

    /**
     * Update this object using given specification
     * @param updateSpec
     */
    void update(CustomLabelSpec updateSpec);
 
    /**
     * Set listener to be called when this object is updated
     * @param statusItemListener
     */
    void setLabelItemListener(StatusItemListener statusItemListener);

    /**
     * Add listener
     * @param eventType Listener event type 
     * @param listener Listener object
     */
    void setEventListener(int eventType, Listener listener); 

 
    void setLabelListener(LabelListener labelListener);
    
    /**
     * Set tooltip
     * @param tooltip Text to appear on mouse over item - null or empty String disables feature
     */
    void setTooltip(String tooltip);

    /**
     * @return the tooltep
     */
    String getTooltip();

    /**
     * Set context menu, which will pop up when the item is right clicked
     * @param menu Menu object
     */
    void setMenu(Menu menu);
 
    /**
     * @return the context memu or null if none
     */
    Menu getMenu();

    /**
     * Return custom label initialized with values contained in this item
     * @param controlFactory SWT widget factory
     * @param parent Parent composite
     * @return
     */
    CLabel labelInstance(ControlFactory controlFactory, Composite parent);
}
