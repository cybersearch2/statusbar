/**
    Copyright (C) 2015  www.cybersearch2.com.au

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
// Adapted rom Hyperbola sample code which had following copyright:
/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package au.com.cybersearch2.statusbar;

import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import au.com.cybersearch2.controls.StatusBarControlFactory;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

/**
 * StatusLineContribution
 * Sub class for items to be added to the status line. Contributes a custom label preceded by separator.
 * @author Andrew Bowley
 * 23 Mar 2016
 */
public abstract class StatusLineContribution 
{
    /** The custom label */
    protected CLabel label;
	/** SWT widget factory */
	private StatusBarControlFactory controlFactory;
	/** Image to display in label */
	Image image;
	/** Text to display in label */
	String text; 
	/** Tooltip (optional) */
    String tooltip;
    /** Width in number of characters */
	int widthHint;
	/** Listener event type */
	int eventType;
	/** Optional listener */
    Listener listener;
    /** Owner of this item */
    StatusBarManager manager;
    /** Visibility flag */
    boolean isVisible;

    /**
     * Create StatusLineContribution object
     * @param controlFactory SWT widget factory
     * @param customLabelSpec Custom label data including width in number of characters
     */
	protected StatusLineContribution(StatusBarControlFactory controlFactory, CustomLabelSpec customLabelSpec) 
	{
		this.controlFactory = controlFactory;
        // Text must not be null
        text = customLabelSpec.getText();
        if (text == null)
            text = "";
        image = customLabelSpec.getImage();
        widthHint = customLabelSpec.getWidth();
        isVisible = !text.isEmpty() || (image != null); 
	}

	/**
	 * Returns flag set true if item is visible
	 * @return boolean
	 */
	public boolean isVisible()
	{
	    return isVisible;
	}
	
	/**
     * @return the manager
     */
    public StatusBarManager getManager()
    {
        return manager;
    }


    /**
     * @param manager the manager to set
     */
    public void setManager(StatusBarManager manager)
    {
        this.manager = manager;
    }

    /**
     * The default implementation of this <code>IContributionItem</code>
     * method does nothing. Subclasses may override.
     */
    public void dispose() 
    {
    }

    /**
	 * Log message 
	 * @param message String
	 */
    protected abstract void logError(String message);

    /**
     * 
     * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Composite)
     */
	public void fill(Composite parent) 
	{
		Label sep = controlFactory.separatorInstance(parent);
		label = controlFactory.customLabelInstance(parent, text, image, widthHint);
		if (listener != null) 
			label.addListener(eventType, listener);
		if (tooltip != null) 
			label.setToolTipText(tooltip);
		// Separator is same height as label and width determine by layout setter
		StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
		statusLineLayoutData.heightHint = ((StatusLineLayoutData)label.getLayoutData()).heightHint;
		sep.setLayoutData(statusLineLayoutData);
	}

    /**
     * Add listener
     * @param eventType Listener event type 
     * @param listener Listener object
     */
	public void addListener(int eventType, Listener listener) 
	{
		this.eventType = eventType;
		this.listener = listener;
	}

	/**
	 * Returns current label text
	 * @return String
	 */
	public String getText() 
	{
		return text;
	}

	/**
	 * Set label text
	 * @param text String
	 */
	public void setText(String text) 
	{
		if (text != null)
        {
    		this.text = text;
    		setLabelText(text);
    		updateVisible(!text.isEmpty());
        }
		else
            logError("Null text parameter");
	}

	/**
	 * Set tool tip text
	 * @param tooltip String
	 */
	public void setTooltip(String tooltip) 
	{
		if (tooltip != null)
        {
    		this.tooltip = tooltip;
    		setLabelToolTip(tooltip);
        }
		else
	        logError("Null tooltip parameter");
	}

	/**
	 * Set image
	 * @param image Image object
	 */
	public void setImage(Image image) 
	{
		if (image != null)
		{
    		this.image = image;
    		setLabelImage(image);
            updateVisible(true);
		}
		else
            logError("Null image parameter");
	}

	private void setLabelText(String text)
	{
        if (label != null && !label.isDisposed())
            label.setText(text);
	}

	private void setLabelImage(Image image)
	{
        if (label != null && !label.isDisposed())
            label.setImage(image);
	}

	private void setLabelToolTip(String tooltip)
	{
        if (label != null && !label.isDisposed())
            label.setToolTipText(tooltip);
	}
	
	/**
	 * Toggle item visibility to specified state
	 * @param value Visibility flag
	 */
	private void updateVisible(boolean value)
    {
	    if (isVisible != value)
	    {
            isVisible = value;
            StatusBarManager contributionManager = getManager();
            if (contributionManager != null)
                contributionManager.update(true);
	    }
    }
}
