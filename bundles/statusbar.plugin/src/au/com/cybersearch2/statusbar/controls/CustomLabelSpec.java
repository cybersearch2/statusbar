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
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

}
