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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import au.com.cybersearch2.controls.StatusBarControlFactory;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

/**
 * StatusItem
 * Configures and controls one status line item, the content of which is rendered using a CLabel widget.
 * @author Andrew Bowley
 * 26 Jun 2016
 * @see org.eclipse.swt.custom.CLabel
 */
public class StatusItem implements LabelItem
{
    static final Field[] TEXT_FIELD;
    static final Field[] IMAGE_FIELD;
    static final Field[] TEXT_IMAGE_FIELDS;
    static final Field[] TOOLTIP_FIELD;
    
    /** Unique identity */
    protected int id;
    /** Visibility flag */
    protected boolean isVisible;
    /** Listener which redraws and updates this item */
    LabelItemListener labelItemListener;
    /** Listener event type */
    int eventType;
    /** Optional listener */
    Listener listener;
    
    protected Image image;
    protected String text;
    protected int width;
    protected Font font;
    protected Color bgColor;
    protected String tooltip;

    /** Field sets identify what has changed when updating */
    static
    {
        TEXT_FIELD = new Field[]{Field.text};
        IMAGE_FIELD = new Field[]{Field.image};
        TEXT_IMAGE_FIELDS = new Field[]{Field.text, Field.image};
        TOOLTIP_FIELD = new Field[]{Field.tooltip};
    }
    
    /**
     * Create StatusItem object
     * @param customLabelSpec Custom label data including width in number of characters
     * @param id Unique identity with range starting from 0 
     */
    public StatusItem(CustomLabelSpec customLabelSpec, int id)
    {
        this.id = id;
        setSpecification(customLabelSpec);
    }
    
    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#getId()
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#getImage()
     */
    @Override
    public Image getImage()
    {
        return image;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#getText()
     */
    @Override
    public String getText()
    {
        return text;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#getWidth()
     */
    @Override
    public int getWidth()
    {
        return width;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#isVisible()
     */
    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#setImage(org.eclipse.swt.graphics.Image)
     */
    @Override
    public void setImage(Image image)
    {
        this.image = image;
        if (!updateVisible(getVisibility()) && isVisible)
        {
            if (image != null)
                signalUpdate(IMAGE_FIELD);
            else // Remove image using redraw
                signalRedraw();
        }
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#setText(java.lang.String)
     */
    @Override
    public void setText(String text)
    {
        this.text = text;
        if (!updateVisible(getVisibility()) && isVisible)
        {
            if (text != null)
                signalUpdate(TEXT_FIELD);
            else
                signalRedraw();
        }
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#setWidth(int)
     */
    @Override
    public void setWidth(int width)
    {
        if (this.width != width)
        {   // Change of width requires redraw
            this.width = width;
            if (isVisible)
                signalRedraw();
        }
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#setLabel(java.lang.String, org.eclipse.swt.graphics.Image)
     */
    @Override
    public void setLabel(String text, Image image)
    {   // This method is is for case where both text and image are both displayed and being updated.
        // For other cases, using single-argument setters is preferred.
        boolean labelVisible = 
                (image != null) && 
                (text != null) &&
                !text.isEmpty() &&
                (this.image != null) && 
                (this.text != null) && 
                !this.text.isEmpty();
        this.text = text;
        this.image = image;
        if (!updateVisible(getVisibility()))
        {
            if (labelVisible)
                signalUpdate(TEXT_IMAGE_FIELDS);
            else
                signalRedraw();
        }
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#update(au.com.cybersearch2.statusbar.controls.CustomLabelSpec)
     */
    @Override
    public void update(CustomLabelSpec updateSpec)
    {
        setSpecification(updateSpec);
        signalRedraw();
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#setLabelItemListener(au.com.cybersearch2.statusbar.LabelItemListener)
     */
    @Override
    public void setLabelItemListener(LabelItemListener labelItemListener)
    {
        this.labelItemListener = labelItemListener;
    }

    /**
     * Add listener
     * @param eventType Listener event type 
     * @param listener Listener object
     */
    @Override
    public void setEventListener(int eventType, Listener listener) 
    {
        this.eventType = eventType;
        this.listener = listener;
    }

    /**
      * @see au.com.cybersearch2.statusbar.LabelItem#setTooltip(java.lang.String)
     */
    @Override
    public void setTooltip(String tooltip)
    {   // Empty tooltip triggers default behaviour
        if ((tooltip != null) && (tooltip.isEmpty()))
            tooltip = null;
        this.tooltip = tooltip;   
        if (getVisibility())
            signalUpdate(TOOLTIP_FIELD);
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#getTooltip()
     */
    @Override
    public String getTooltip()
    {
        return tooltip;
    }

    /**
     * @see au.com.cybersearch2.statusbar.LabelItem#labelInstance(au.com.cybersearch2.controls.StatusBarControlFactory, org.eclipse.swt.widgets.Composite)
     */
    @Override
    public CLabel labelInstance(StatusBarControlFactory controlFactory,
            Composite parent)
    {
        CLabel label = controlFactory.customLabelInstance(parent, new CustomLabelSpec(image, text, width));
        if (bgColor != null)
            label.setBackground(bgColor);
        if (font != null)
            label.setFont(font);
        if (listener != null) 
            label.addListener(eventType, listener);
        if (tooltip != null) 
            label.setToolTipText(tooltip);
        return label;
    }

    /**
     * Set fields from given specification
     * @param customLabelSpec The specification
     */
    protected void setSpecification(CustomLabelSpec customLabelSpec)
    {
        image = customLabelSpec.getImage();
        text = customLabelSpec.getText();
        width = customLabelSpec.getWidth();
        font = customLabelSpec.getFont();
        bgColor = customLabelSpec.getBackground();
        isVisible = getVisibility(); 
    }
 
    /**
     * Update isVisible flag if different from given value. Redraw on change of visibility.
     * @param isVisible boolean
     * @return flag set true if visibility has changed
     */
    protected boolean updateVisible(boolean isVisible)
    {
        if (this.isVisible != isVisible)
        {
            this.isVisible = isVisible;
            signalRedraw();
            return true;
        }
        return false;
    }

    /**
     * Returns visibility status
     * @return
     */
    protected boolean getVisibility()
    {
        return (image != null) || ((text != null) && !text.isEmpty());
    }

    /**
     * Redraw entire status line
     */
    protected void signalRedraw()
    {
        if (labelItemListener != null)
            labelItemListener.onRedraw(this);
    }

    /**
     * Update label only
     * @param updateFields
     */
    protected void signalUpdate(Field[] updateFields)
    {
        if (labelItemListener != null)
            labelItemListener.onUpdate(this, updateFields);
    }
}
