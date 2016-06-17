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
// Adapted from Eclipse workbench software which had the following copyright:
/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package au.com.cybersearch2.statusbar;

import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import au.com.cybersearch2.controls.StatusBarControlFactory;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

/**
 * StatusLine
 * A StatusLine control is a SWT Composite with a horizontal layout which hosts
 * a number of status indication controls. Typically it is situated below the
 * content area of the window. This control hosts a custom label which occupies
 * the first position on the status line and contribution items are placed to the
 * right of it.
 * <p>
 * @author Andrew Bowley
 * 24 Mar 2016
 */
public class StatusLine
{
    /** Message text */
    protected String messageText;
    /** Mmessage image */
    protected Image messageImage;
    /** Custom label */
    protected CLabel messageLabel;
    Composite composite;

    /**
     * Returns composite to add to status bar
     * @param controlFactory SWT widget factory
     * @param customLabelSpec Parameters for custom label creation 
     * @param parent Parent composite
     */
    public Composite getComposite(StatusBarControlFactory controlFactory, CustomLabelSpec customLabelSpec, Composite parent)
    {
        if (composite != null)
            composite.dispose();
        messageText = trim(customLabelSpec.getText());
        messageImage = customLabelSpec.getImage();
        composite = controlFactory.compositeInstance(parent);
        composite.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() 
        {
            @Override
            public void getRole(AccessibleControlEvent e) 
            {
                e.detail = ACC.ROLE_STATUSBAR;
            }
        });

        messageLabel = controlFactory.customLabelInstance(composite, messageText, messageImage, customLabelSpec.getWidth());
        return composite;
    }

    public Composite getComposite()
    {
        return composite;
    }

    /**
    * Sets the message text to be displayed on the status line. The image on
    * the status line is cleared.
    * @param message Error message, or <code>null</code> for no error message
    */
   public void setMessage(String message) 
   {
       setMessage(null, message);
   }

   /**
    * Sets an image and a message text to be displayed on the status line.
    *
    * @param image Image to use, or <code>null</code> for no image
    * @param message Message, or <code>null</code> for no message
    */
   public void setMessage(Image image, String message) 
   {
       messageText = trim(message);
       messageImage = image;
       updateMessageLabel();
   }

   /**
    * Set mouse listener to run popup on right button click
    * @param runnable Task to display popup
    */
   public void setMessagePopup(final Runnable runnable)
   {
       MouseListener listener = new MouseListener(){

           @Override
           public void mouseDoubleClick(MouseEvent e)
           {
           }

           @Override
           public void mouseDown(MouseEvent e)
           {
           }

           @Override
           public void mouseUp(MouseEvent e)
           {
               if (e.button == 3)
                   runnable.run();
           }};
           if (messageLabel != null)
               messageLabel.addMouseListener(listener );
   }
   
   /**
    * Updates the message label widget.
    */
   public void updateMessageLabel() 
   {
       if (messageLabel != null && !messageLabel.isDisposed()) 
       {
           messageLabel.setForeground(composite.getForeground());
           messageLabel.setText(messageText == null ? "" : messageText);
           messageLabel.setImage(messageImage);
       }
   }

    /**
     * Trims the message to be displayable in the status line. This just pulls
     * out the first line of the message. Allows null.
     */
    public static String trim(String message) 
    {
        if (message == null)
            return null;
        int cr = message.indexOf('\r');
        int lf = message.indexOf('\n');
        if (cr == -1 && lf == -1) 
            return message;
        int len;
        if (cr == -1) 
            len = lf;
        else if (lf == -1) 
            len = cr;
        else
            len = Math.min(cr, lf);
        return message.substring(0, len);
    }

}
