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
// Adapted from org.eclipse.jface.action.StatusLineManager which had the following copyright:
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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * StatusBarManager
 * Sub class for status bar, taking care of mundane contribution management tasks
 * @author Andrew Bowley
 * 23 Mar 2016
 */
public class StatusBarManager 
{
    /** The status line control composite; <code>null</code> before creation and after disposal. */
    protected Composite statusLine;

    /** The list of contribution items. */
    private List<StatusLineContribution> contributions;

    /** Indicates whether the widgets are in sync with the contributions. */
    private boolean isDirty;

    /**
     * Post construct
     * @param statusBar Transfers status line content from application to StatusBarManager
     * @param parent Parent composite
     * @param shell Active shell required to obtain main window client area
     */
    @PostConstruct
    public void createGui(IStatusBar statusBar, Composite parent, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) 
    {
        isDirty = true;
        statusLine = statusBar.getStatusLine(parent).getComposite();
        statusLine.setLayout(new StatusLineLayout(shell));
        contributions = new ArrayList<StatusLineContribution>();
        contributions.addAll(statusBar.getContributions());
        update(false);
    }

    /**
     * Dispose of status line
     */
    @PreDestroy()
    public void preDestroy()
    {
        dispose();
    }

    /**
     * Returns flag set true if the status line control is created and not disposed.
     * @return boolean
     */
    protected boolean statusLineExists() 
    {
        return statusLine != null && !statusLine.isDisposed();
    }

    /**
     * Dispose of status line
     */
    protected void dispose()
    {
        if (statusLineExists()) 
            statusLine.dispose();
        statusLine = null;
        for (StatusLineContribution item: contributions)
            item.dispose();
    }
    
    /**
     * Updates any SWT controls cached by this contribution item with any
     * changes which have been made to this contribution item since the last update.
     * Called by contribution manager update methods.
     */
    protected void update(boolean force) 
    {
        if ((isDirty || force) && statusLineExists()) 
        {
            statusLine.setRedraw(false);
            // Dispose of all status line controls which implement StatusLineContribution
            for (Control control: statusLine.getChildren())
            {
                 Object data = control.getData();
                 if (data instanceof StatusLineContribution) 
                     control.dispose();
            }
            // Set association between controls and contribution items.
            // One item may be associated with more than one control.
            int oldChildCount = statusLine.getChildren().length;
            for (StatusLineContribution item: contributions) 
            {
                if (item.isVisible()) 
                {   // Fill the given composite control with controls representing this
                    // contribution item.
                    item.fill(statusLine);
                    Control[] newChildren = statusLine.getChildren();
                    for (int i = oldChildCount; i < newChildren.length; i++) 
                        newChildren[i].setData(item);
                    oldChildCount = newChildren.length;
                }
            }
            isDirty = false;
            statusLine.layout();
            statusLine.setRedraw(true);
        }
    }

    /**
     * Add item to status line
     * @param item StatusLineContribution object
     */
    protected void add(StatusLineContribution item) 
    {
        Assert.isNotNull(item, "Item must not be null");
        contributions.add(item);
        item.setManager(this);
        isDirty = true;
    }

}
