package au.com.cybersearch2.statusbar;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import au.com.cybersearch2.controls.StatusBarControlFactory;

public class StatusBarToolControl 
{
    /** The parent of the status line controls, responsible for their layout */
    Composite composite;

    @Inject
    StatusBar statusBar;
    /** SWT widget factory */
    @Inject
    StatusBarControlFactory controlFactory;
    
    
	@PostConstruct
	void createGui(Composite parent, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) 
	{
        composite = controlFactory.compositeInstance(parent);
        composite.setLayout(new StatusLineLayout(shell));
        composite.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() 
        {
            @Override
            public void getRole(AccessibleControlEvent e) 
            {
                e.detail = ACC.ROLE_STATUSBAR;
            }
        });
        statusBar.setToolControl(this);
	}

    public void redraw(List<StatusControl> controlList)
    {
        if (composite == null)
            return;
        composite.setRedraw(false);
        // Dispose of all status line controls containing StatusControl data
        for (Control control: composite.getChildren())
             if (control.getData() instanceof StatusControl) 
                 control.dispose();
        // Set association between controls and contribution items.
        int childCount = composite.getChildren().length;
        
        boolean firstTime = true;
        for (StatusControl item: controlList) 
            if ((item != null) && item.isVisible()) 
            {
                if (firstTime)
                    firstTime = false;
                else
                    item.separate(controlFactory, composite);
                childCount = fill(item, childCount);
            }
        composite.layout();
        composite.setRedraw(true);
    }

    /**
     * Place item in StatusBar
     * @param item The item to place
     * @param childCount Number of children under StatusBar
     * @return Updated number of children under StatusBar
     */
    int fill(StatusControl item, int childCount)
    {   
        item.fill(controlFactory, composite);
        Control[] newChildren = composite.getChildren();
        for (int i = childCount; i < newChildren.length; i++) 
            newChildren[i].setData(item);
        return newChildren.length;
    }
    
    /**
     * Dispose of status line
     */
    @PreDestroy()
    void preDestroy()
    {
        statusBar.dispose();
    }

}