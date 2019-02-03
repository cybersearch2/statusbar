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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import au.com.cybersearch2.controls.CustomLabelSpec;
import au.com.cybersearch2.controls.ControlFactory;
import au.com.cybersearch2.statusbar.LabelItem.Field;
import au.com.cybersearch2.statusbar.controls.ItemConfiguration;

/**
 * StatusItemTest
 * @author Andrew Bowley
 * 27 Jun 2016
 */
public class StatusItemTest
{
    static final String TEST_MESSAGE = "Testing 123";
    static final String TOOL_TIP = "Tool tip";
    
    CustomLabelSpec itemConfiguration;
    Image image;
    String text;
    int width;
    Color bgColor;

    @Before
    public void setUp()
    {
        Display display = mock(Display.class);
        image = new Image(display, "test-src/resources/bullet.gif");
        width = SWT.DEFAULT;
        itemConfiguration = new ItemConfiguration(image, TEST_MESSAGE, width);
        bgColor = new Color(mock(Device.class), 255,255,255);
        itemConfiguration.setBackground(bgColor);
    }
    
    @Test
    public void test_constructor()
    {
        
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        assertThat(underTest.getId()).isEqualTo(0);
        assertThat(underTest.getImage()).isEqualTo(image);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE);
        assertThat(underTest.getWidth()).isEqualTo(width);
        assertThat(underTest.bgColor).isEqualTo(bgColor);
        assertThat(underTest.isVisible()).isTrue();
    }
    
    @Test
    public void test_setImage()
    {
        Display display = mock(Display.class);
        Image testImage = new Image(display, "test-src/resources/black_circle.gif");
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setImage(testImage);
        assertThat(underTest.getImage()).isEqualTo(testImage);
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.image});
    }

    @Test
    public void test_setImage_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        assertThat(underTest.isVisible()).isFalse();
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setImage(image);
        assertThat(underTest.getImage()).isEqualTo(image);
        assertThat(underTest.isVisible()).isTrue();
        verify(statusItemListener).onRedraw(underTest);
    }

    @Test
    public void test_setImage_null_while_text_displayed()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setImage(null);
        assertThat(underTest.getImage()).isNull();
        assertThat(underTest.isVisible()).isTrue();
        verify(statusItemListener).onRedraw(underTest);
   }

    @Test
    public void test_setImage_null_while_no_text_displayed()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(image, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        assertThat(underTest.isVisible()).isTrue();
        underTest.setImage(null);
        assertThat(underTest.getImage()).isNull();
        assertThat(underTest.isVisible()).isFalse();
        verify(statusItemListener).onRedraw(underTest);
    }

    @Test
    public void test_setImage_null_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
         underTest.setImage(null);
        verify(statusItemListener, times(0)).onRedraw(underTest);
        verify(statusItemListener, times(0)).onUpdate(eq(underTest), any(Field[].class));
    }

    @Test
    public void test_setText()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText(TEST_MESSAGE + "1");
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE + "1");
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.text});
    }

    @Test
    public void test_setText_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        assertThat(underTest.isVisible()).isFalse();
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText(TEST_MESSAGE);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE);
        assertThat(underTest.isVisible()).isTrue();
        verify(statusItemListener).onRedraw(underTest);
    }

    @Test
    public void test_setText_null_while_image_displayed()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText(null);
        assertThat(underTest.getText()).isNull();
        assertThat(underTest.isVisible()).isTrue();
        verify(statusItemListener).onRedraw(underTest);
   }

    @Test
    public void test_setText_null_while_no_image_displayed()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, TEST_MESSAGE, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        assertThat(underTest.isVisible()).isTrue();
        underTest.setText(null);
        assertThat(underTest.getText()).isNull();
        assertThat(underTest.isVisible()).isFalse();
        verify(statusItemListener).onRedraw(underTest);
    }

    @Test
    public void test_setText_null_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
         underTest.setText(null);
        verify(statusItemListener, times(0)).onRedraw(underTest);
        verify(statusItemListener, times(0)).onUpdate(eq(underTest), any(Field[].class));
    }

    @Test
    public void test_setText_empty_while_image_displayed()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText("");
        assertThat(underTest.getText()).isEmpty();
        assertThat(underTest.isVisible()).isTrue();
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.text});
   }

    @Test
    public void test_setText_empty_while_no_image_displayed()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, TEST_MESSAGE, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        assertThat(underTest.isVisible()).isTrue();
        underTest.setText("");
        assertThat(underTest.getText()).isEmpty();
        assertThat(underTest.isVisible()).isTrue();
        assertThat(underTest.getWidth()).isEqualTo(0);
        verify(statusItemListener, times(0)).onRedraw(underTest);
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.text});
    }

    @Test
    public void test_setText_empty_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText("");
        // Special case adjusts width so onUpdate() with text input shows at least ellipsis
        assertThat(underTest.getWidth()).isEqualTo(3);
        verify(statusItemListener).onRedraw(underTest);
        verify(statusItemListener, times(0)).onUpdate(eq(underTest), any(Field[].class));
    }
    
    @Test
    public void test_setText_empty_while_invisible_with_positive_width()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 1), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setText("");
        assertThat(underTest.getWidth()).isEqualTo(1);
        verify(statusItemListener).onRedraw(underTest);
        verify(statusItemListener, times(0)).onUpdate(eq(underTest), any(Field[].class));
    }
    
    @Test
    public void test_setWidth()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setWidth(99);
        assertThat(underTest.getWidth()).isEqualTo(99);
        verify(statusItemListener).onRedraw(underTest);
    }

    @Test
    public void test_setWidth_while_invisisble()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setWidth(99);
        assertThat(underTest.getWidth()).isEqualTo(99);
        verify(statusItemListener, times(0)).onRedraw(underTest);
    }

    @Test
    public void test_setWidth_unchanged()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setWidth(width);
        verify(statusItemListener, times(0)).onRedraw(underTest);
    }
    
    @Test
    public void test_setLabel()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        Display display = mock(Display.class);
        Image testImage = new Image(display, "test-src/resources/black_circle.gif");
        underTest.setLabel(TEST_MESSAGE + "1", testImage);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE + "1");
        assertThat(underTest.getImage()).isEqualTo(testImage);
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.text, Field.image});
    }
    @Test
    public void test_setLabel_redraw()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        Display display = mock(Display.class);
        Image testImage = new Image(display, "test-src/resources/black_circle.gif");
        underTest.setLabel(TEST_MESSAGE, testImage);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE);
        verify(statusItemListener).onRedraw(underTest);
        statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setLabel(null, null);
        assertThat(underTest.getText()).isNull();
        verify(statusItemListener).onRedraw(underTest);
        statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setLabel(TEST_MESSAGE, null);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE);
        assertThat(underTest.getImage()).isNull();
        verify(statusItemListener).onRedraw(underTest);
        underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setLabel(TEST_MESSAGE + "1", testImage);
        assertThat(underTest.getText()).isEqualTo(TEST_MESSAGE + "1");
        assertThat(underTest.getImage()).isEqualTo(testImage);
        verify(statusItemListener).onRedraw(underTest);
    }
    
    @Test
    public void test_setTooltip()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setTooltip(TOOL_TIP);
        assertThat(underTest.getTooltip()).isEqualTo(TOOL_TIP);
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.tooltip});
    }

    @Test
    public void test_setTooltip_while_invisible()
    {
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setTooltip(TOOL_TIP);
        assertThat(underTest.getTooltip()).isEqualTo(TOOL_TIP);
        verify(statusItemListener, times(0)).onUpdate(eq(underTest), any(Field[].class));
    }

    @Test
    public void test_setTooltip_empty()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setTooltip("");
        assertThat(underTest.getTooltip()).isNull();
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.tooltip});
    }

    @Test
    public void test_setTooltip_null()
    {
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        StatusItemListener statusItemListener = mock(StatusItemListener.class);
        underTest.setLabelItemListener(statusItemListener);
        underTest.setTooltip(null);
        assertThat(underTest.getTooltip()).isNull();
        verify(statusItemListener).onUpdate(underTest, new Field[]{Field.tooltip});
    }
    
    @Test
    public void test_labelInstance()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        CLabel label = mock(CLabel.class);
        Composite parent = mock(Composite.class);
        when(controlFactory.customLabelInstance(eq(parent), isA(ItemConfiguration.class))).thenReturn(label);
        StatusItem underTest = new StatusItem(itemConfiguration, 0);
        Listener listener = mock(Listener.class);
        underTest.setEventListener(SWT.MenuDetect, listener);
        underTest.setTooltip(TOOL_TIP);
        assertThat(underTest.labelInstance(controlFactory, parent)).isEqualTo(label);
        verify(label).addListener(SWT.MenuDetect, listener);
        verify(label).setToolTipText(TOOL_TIP);
        verify(label).setBackground(bgColor);
        ArgumentCaptor<ItemConfiguration> specCaptor = ArgumentCaptor.forClass(ItemConfiguration.class);
        verify(controlFactory).customLabelInstance(eq(parent), specCaptor.capture());
        CustomLabelSpec spec = specCaptor.getValue();
        assertThat(spec.getText()).isEqualTo(TEST_MESSAGE);
        assertThat(spec.getImage()).isEqualTo(image);
        assertThat(spec.getWidth()).isEqualTo(width);
        
    }
    @Test
    public void test_labelInstance_nulls()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        CLabel label = mock(CLabel.class);
        Composite parent = mock(Composite.class);
        when(controlFactory.customLabelInstance(eq(parent), isA(ItemConfiguration.class))).thenReturn(label);
        StatusItem underTest = new StatusItem(new ItemConfiguration(null, null, 0), 0);
        assertThat(underTest.labelInstance(controlFactory, parent)).isEqualTo(label);
        verify(label, times(0)).addListener(anyInt(), any(Listener.class));
        verify(label, times(0)).setToolTipText(any(String.class));
        verify(label, times(0)).setBackground(any(Color.class));
        ArgumentCaptor<ItemConfiguration> specCaptor = ArgumentCaptor.forClass(ItemConfiguration.class);
        verify(controlFactory).customLabelInstance(eq(parent), specCaptor.capture());
        CustomLabelSpec spec = specCaptor.getValue();
        assertThat(spec.getText()).isNull(); // CLabel converts null to empty String
        assertThat(spec.getImage()).isNull();
        assertThat(spec.getWidth()).isEqualTo(0);
        
    }

}
