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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import au.com.cybersearch2.controls.CustomLabelSpec;
import au.com.cybersearch2.controls.StatusBarControlFactory;

/**
 * ControlFactory
 * SWT widget factory for testability
 * @author Andrew Bowley
 * 2 Mar 2016
 */
public class ControlFactory implements StatusBarControlFactory
{
    
    /**
     * @see au.com.cybersearch2.controls.StatusBarControlFactory#compositeInstance(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public Composite compositeInstance(Composite parent)
    {
        return new Composite(parent, SWT.NONE);
    }
    
    /**
     * @see au.com.cybersearch2.controls.StatusBarControlFactory#separatorInstance(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public Label separatorInstance(Composite parent)
    {
        return new Label(parent, SWT.SEPARATOR);
    }

    @Override
    public CLabel customLabelInstance(Composite parent, CustomLabelSpec specification)
    {
        CLabel label = new CLabel(parent, SWT.SHADOW_NONE);
        label.setLayoutData(new StatusItemLayoutData(label, specification));
        label.setText(specification.getText());
        label.setImage(specification.getImage());
        return label;
    }

    @Override
    public StatusItemLayoutData labelLayoutDataInstance(
            CLabel label, CustomLabelSpec specification)
    {
        return new StatusItemLayoutData(label, specification);
    }

    /**
     * Returns a detached menu.
     * Constructs a new instance of this class given its parent,
     * and sets the style for the instance so that the instance
     * will be a popup menu on the given parent's shell.
     * <p>
     * After constructing a menu, it can be set into its parent
     * using <code>parent.setMenu(menu)</code>.  In this case, the parent may
     * be any control in the same widget tree as the parent.
     * </p>
     * @param parent A control which will be the parent of the new instance (cannot be null)
     */
    @Override
    public Menu menuInstance(Control parent) 
    {
        return new Menu(parent);
    }
    
    /**
     * Returns a menu item.
     * Constructs a new instance of MenuItem given its parent
     * (which must be a <code>Menu</code>) and a style value
     * describing its behavior and appearance. The item is added
     * to the end of the items maintained by its parent.
     * <p>
     * @param parent a menu control which will be the parent of the new instance (cannot be null)
     * @param style the style of control to construct
     */
    @Override
    public MenuItem menuItemInstance(Menu parent, int style)
    {
        return new MenuItem(parent, style);
    }
}
