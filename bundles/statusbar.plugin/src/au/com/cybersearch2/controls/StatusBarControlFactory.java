package au.com.cybersearch2.controls;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import au.com.cybersearch2.statusbar.controls.ContributionLayoutData;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

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
     * @return ContributionLayoutData object
     */
    ContributionLayoutData contributionLayoutDataInstance(CLabel label, CustomLabelSpec specification);
}