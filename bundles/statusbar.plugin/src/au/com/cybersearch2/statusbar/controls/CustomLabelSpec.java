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

/**
 * CustomLabelSpec
 * Custom label data including width in number of characters
 * @author Andrew Bowley
 * 24 Mar 2016
 */
public class CustomLabelSpec
{
    private Image image;
    private String text;
    private int width;
    private Font font;
    private Color bgColor;
    
    /**
     * Create a CustomLabelSpec object
     * @param image Label mage or null
     * @param text Label text or null 
     * @param width Width of Label in characters
     */
    public CustomLabelSpec(Image image, String text, int width)
    {
        this.image = image;
        this.text = text;
        this.width = width;
    }

    /**
     * @return the image
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @return the width hint in number of characters
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Set image
     * @param image Image object
     */
    public void setImage(Image image)
    {
        this.image = image;
    }

    /**
     * Set the text
     * @param text String
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Set width hint
     * @param width Width in number of characters
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the font
     */
    public Font getFont()
    {
        return font;
    }

    /**
     * Set the font
     * @param font Font object
     */
    public void setFont(Font font)
    {
        this.font = font;
    }

    /**
      * @return the background color
      */
    public Color getBackground()
    {
        return bgColor;
    }

    /**
     * Set the background color
     * @param bgColor Color object
     */
    public void setBackground(Color bgColor)
    {
        this.bgColor = bgColor;
    }

    public CustomLabelSpec dup()
    {
        CustomLabelSpec copy = new CustomLabelSpec(image, text, width);
        copy.setBackground(bgColor);
        return copy;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if ((obj == null) || !(obj instanceof CustomLabelSpec))
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
