package au.com.cybersearch2.controls;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public interface CustomLabelSpec
{

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
     * @return the font
     */
    Font getFont();

    /**
     * Set the font
     * @param font Font object
     */
    void setFont(Font font);

    /**
      * @return the background color
      */
    Color getBackground();

    /**
     * Set the background color
     * @param bgColor Color object
     */
    void setBackground(Color bgColor);

    CustomLabelSpec dup();

}