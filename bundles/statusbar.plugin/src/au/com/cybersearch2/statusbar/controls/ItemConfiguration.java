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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import au.com.cybersearch2.controls.CustomLabelSpec;

/**
 * ItemConfiguration
 * Custom label data including width in number of characters
 * @author Andrew Bowley
 * 24 Mar 2016
 */
public class ItemConfiguration implements CustomLabelSpec
{
    private Image image;
    private String text;
    private int width;
    private Font font;
    private Color bgColor;
    
    /**
     * Create a ItemConfiguration object
     * @param image Label mage or null
     * @param text Label text or null 
     * @param width Width of Label in characters
     */
    public ItemConfiguration(Image image, String text, int width)
    {
        this.image = image;
        this.text = text;
        this.width = width;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#getImage()
     */
    @Override
    public Image getImage()
    {
        return image;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#getText()
     */
    @Override
    public String getText()
    {
        return text;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#getWidth()
     */
    @Override
    public int getWidth()
    {
        return width;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#setImage(org.eclipse.swt.graphics.Image)
     */
    @Override
    public void setImage(Image image)
    {
        this.image = image;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#setText(java.lang.String)
     */
    @Override
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#setWidth(int)
     */
    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#getFont()
     */
    @Override
    public Font getFont()
    {
        return font;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#setFont(org.eclipse.swt.graphics.Font)
     */
    @Override
    public void setFont(Font font)
    {
        this.font = font;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#getBackground()
     */
    @Override
    public Color getBackground()
    {
        return bgColor;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#setBackground(org.eclipse.swt.graphics.Color)
     */
    @Override
    public void setBackground(Color bgColor)
    {
        this.bgColor = bgColor;
    }

    /**
     * @see au.com.cybersearch2.controls.CustomLabelSpec#dup()
     */
    @Override
    public CustomLabelSpec dup()
    {
        CustomLabelSpec copy = new ItemConfiguration(image, text, width);
        copy.setBackground(bgColor);
        return copy;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if ((obj == null) || !(obj instanceof ItemConfiguration))
            return false;
        CustomLabelSpec other = (CustomLabelSpec)obj;
        if (text != null) 
        {
            if (!text.equals(other.getText()))
                return false;
        }
        else if (other.getText() != null)
            return false;
        if (image != null) 
        {
            if (!image.equals(other.getImage()))
                return false;
        }
        else if (other.getImage() != null)
            return false;
        if (font != null) 
        {
            if (!font.equals(other.getFont()))
                return false;
        }
        else if (other.getFont() != null)
            return false;
        if (bgColor != null) 
        {
            if (!bgColor.equals(other.getBackground()))
                return false;
        }
        else if (other.getBackground() != null)
            return false;
        return width == other.getWidth();
    }

    @Override
    public int hashCode()
    {
        int textHash = text == null ? 0 : text.hashCode();
        int imageHash = image == null ? 0 : image.hashCode();
        int bgColorHash = bgColor == null ? 0 : bgColor.hashCode();
        int fontHash = font == null ? 0 : font.hashCode();
        return textHash ^ imageHash ^ width ^ bgColorHash ^ fontHash;
    }

}
