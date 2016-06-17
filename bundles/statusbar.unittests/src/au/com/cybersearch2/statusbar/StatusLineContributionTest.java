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
package au.com.cybersearch2.statusbar;

import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import au.com.cybersearch2.statusbar.controls.ControlFactory;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

/**
 * StatusLineContributionTest
 * @author Andrew Bowley
 * 23 Mar 2016
 */
public class StatusLineContributionTest
{
    static final String TEST_MESSAGE = "Testing 123";
    static final String TOOL_TIP = "Tool tip";

    class TestStatusLineContribution  extends StatusLineContribution 
    {
        public String errorMessage;
        public ControlFactory controlFactory;
        
        protected TestStatusLineContribution(ControlFactory controlFactory,
                int charWidth)
        {
            super(controlFactory, new CustomLabelSpec(null, null, charWidth));
            
        }

        @Override
        protected void logError(String message)
        {
            errorMessage = message;
        }
    }

    @Test
    public void test_constructor()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        //CLabel label = mock(CLabel.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        assertThat(underTest.isVisible()).isFalse();
        // Control factory is private in StatusLineContribution
        assertThat(underTest.controlFactory).isNull();
        assertThat(underTest.text).isEmpty();
        assertThat(underTest.widthHint).isEqualTo(15);
    }

    @Test
    public void test_fill()
    {
        Display display = mock(Display.class);
        Listener listener = mock(Listener.class);
        Image image = new Image(display, "icons/bullet.gif");
        Composite parent = mock(Composite.class);
        ControlFactory controlFactory = mock(ControlFactory.class);
        Label sep = mock(Label.class);
        when(controlFactory.separatorInstance(parent)).thenReturn(sep);
        CLabel label = mock(CLabel.class);
        when(controlFactory.customLabelInstance(parent, TEST_MESSAGE, image, 15)).thenReturn(label);
        StatusLineLayoutData layoutData = new StatusLineLayoutData();
        layoutData.heightHint = 16;
        when(label.getLayoutData()).thenReturn(layoutData);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        underTest.text = TEST_MESSAGE;
        underTest.tooltip = TOOL_TIP;
        underTest.image = image;
        underTest.widthHint = 15;
        underTest.listener = listener;
        underTest.eventType = 99;
        underTest.fill(parent);
        ArgumentCaptor<StatusLineLayoutData> layoutCaptor = ArgumentCaptor.forClass(StatusLineLayoutData.class); 
        verify(sep).setLayoutData(layoutCaptor.capture());
        assertThat(layoutCaptor.getValue().heightHint).isEqualTo(16);
        assertThat(underTest.label).isEqualTo(label);
        verify(label).addListener(99, listener);
        verify(label).setToolTipText("Tool tip");
    }
    
    @Test
    public void test_setText()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setText(TEST_MESSAGE);
        verify(label).setText(TEST_MESSAGE);
        assertThat(underTest.isVisible()).isTrue();
        verify(contributionManager).update(true);
        assertThat(underTest.text).isEqualTo(TEST_MESSAGE);
    }
 
    @Test
    public void test_setText_disposed()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        when(label.isDisposed()).thenReturn(true);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setText(TEST_MESSAGE);
        verify(label, times(0)).setText(TEST_MESSAGE);
        assertThat(underTest.isVisible()).isTrue();
        verify(contributionManager).update(true);
        assertThat(underTest.text).isEqualTo(TEST_MESSAGE);
    }
 
    @Test
    public void test_setText_null()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        underTest.setText(null);
        assertThat(underTest.errorMessage).isEqualTo("Null text parameter");
    }
    
    @Test
    public void test_setImage()
    {
        Display display = mock(Display.class);
        Image image = new Image(display, "icons/bullet.gif");
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setImage(image);
        verify(label).setImage(image);
        assertThat(underTest.isVisible()).isTrue();
        verify(contributionManager).update(true);
        assertThat(underTest.image).isEqualTo(image);
    }

    @Test
    public void test_setImage_disposed()
    {
        Display display = mock(Display.class);
        Image image = new Image(display, "icons/bullet.gif");
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        when(label.isDisposed()).thenReturn(true);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setImage(image);
        verify(label, times(0)).setImage(image);
        assertThat(underTest.isVisible()).isTrue();
        verify(contributionManager).update(true);
        assertThat(underTest.image).isEqualTo(image);
    }

    @Test
    public void test_setImage_null()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        underTest.setImage(null);
        assertThat(underTest.errorMessage).isEqualTo("Null image parameter");
    }
    

    @Test
    public void test_setTooltip()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setTooltip(TOOL_TIP);
        verify(label).setToolTipText(TOOL_TIP);
        assertThat(underTest.tooltip).isEqualTo(TOOL_TIP);
    }
    
    @Test
    public void test_setTooltip_disposed()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        CLabel label = mock(CLabel.class);
        when(label.isDisposed()).thenReturn(true);
        underTest.label = label;
        StatusBarManager contributionManager = mock(StatusBarManager.class);
        underTest.setManager(contributionManager);
        underTest.setTooltip(TOOL_TIP);
        verify(label, times(0)).setToolTipText(TOOL_TIP);
        assertThat(underTest.tooltip).isEqualTo(TOOL_TIP);
    }
    
    @Test
    public void test_setTooltip_null()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        TestStatusLineContribution underTest = new TestStatusLineContribution(controlFactory, 15);
        underTest.setTooltip(null);
        assertThat(underTest.errorMessage).isEqualTo("Null tooltip parameter");
    }
    
}
