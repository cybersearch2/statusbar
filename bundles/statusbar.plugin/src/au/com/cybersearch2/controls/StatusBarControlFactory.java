package au.com.cybersearch2.controls;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * StatusBarControlFactory
 * Interface to SWT widget factory for testability
 * @author Andrew Bowley
 * 1 Apr 2016
 */
public interface StatusBarControlFactory
{

    /**
     * Create  control which is capable of containing other controls
     * @param parent a widget which will be the parent of the new instance (cannot be null)
     * @return Composite object
     */
    Composite compositeInstance(Composite parent);

    /**
     * Create user interface object that displays a string or image
     * @param parent a composite control which will be the parent of the new instance (cannot be null)
     * @return Label object
     */
    Label separatorInstance(Composite parent);

    /**
     * Create a Label which supports aligned text and/or an image and different border styles
     * @param parent a widget which will be the parent of the new instance (cannot be null)
     * @param text The initial text. Can be null for none.
     * @param image The initial image. Can be null for none.
     * @param widthHint Width in number of characters
     * @return CLabel object
     */
    CLabel customLabelInstance(Composite parent, CustomLabelSpec specification);

    /**
     * Create layout data for a CLabel status line contribution
     * @param label The CLabel to which the layout data will be applied (cannot be null)
     * @param text The text. Can be null for none.
     * @param widthHint Width in number of characters
     * @return LabelLayoutData object
     */
    LabelLayoutData labelLayoutDataInstance(CLabel label, CustomLabelSpec specification);
    
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
    Menu menuInstance(Control parent);
    
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
     MenuItem menuItemInstance(Menu parent, int style);

}