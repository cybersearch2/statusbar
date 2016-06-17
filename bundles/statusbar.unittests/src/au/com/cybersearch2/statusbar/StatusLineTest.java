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

import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import au.com.cybersearch2.statusbar.controls.ControlFactory;
import au.com.cybersearch2.statusbar.controls.CustomLabelSpec;

/**
 * TestStatusLIneTest
 * @author Andrew Bowley
 * 23 Mar 2016
 */
public class StatusLineTest
{
    static final String TEST_MESSAGE = "Testing 123";
    
    @Test
    public void test_constructor()
    {
        Display display = mock(Display.class);
        Image image = new Image(display, "icons/bullet.gif");
        ControlFactory controlFactory = mock(ControlFactory.class);
        Composite parent = mock(Composite.class);
        Composite composite = mock(Composite.class);
        String text = "Status";
        when(controlFactory.compositeInstance(parent)).thenReturn(composite);
        ArgumentCaptor<AccessibleControlAdapter> adapterCaptor = ArgumentCaptor.forClass(AccessibleControlAdapter.class);
        Accessible accessible = mock(Accessible.class);
        when(composite.getAccessible()).thenReturn(accessible);
        CLabel label = mock(CLabel.class);
        when(controlFactory.customLabelInstance(composite, text, image, 18)).thenReturn(label );
        CustomLabelSpec customLabelSpec = new CustomLabelSpec(image, text, 18);
        StatusLine underTest = new StatusLine();
        Composite testComposite = underTest.getComposite(controlFactory, customLabelSpec, parent);
        assertThat(testComposite).isEqualTo(composite);
        verify(accessible).addAccessibleControlListener(adapterCaptor.capture());
        AccessibleControlEvent event = new AccessibleControlEvent(this);
        adapterCaptor.getValue().getRole(event);
        assertThat(event.detail).isEqualTo(ACC.ROLE_STATUSBAR);
        assertThat(underTest.messageText).isEqualTo(text);
        assertThat(underTest.messageImage).isEqualTo(image);
        assertThat(underTest.messageLabel).isEqualTo(label);
    }

    @Test
    public void test_trim()
    {
        assertThat(StatusLine.trim(TEST_MESSAGE + "\n")).isEqualTo(TEST_MESSAGE);
        assertThat(StatusLine.trim(TEST_MESSAGE + "\r")).isEqualTo(TEST_MESSAGE);
        assertThat(StatusLine.trim(TEST_MESSAGE + "\n\r")).isEqualTo(TEST_MESSAGE);
        assertThat(StatusLine.trim(TEST_MESSAGE + "\r\n")).isEqualTo(TEST_MESSAGE);
        assertThat(StatusLine.trim(TEST_MESSAGE + "\nx")).isEqualTo(TEST_MESSAGE);
        assertThat(StatusLine.trim(TEST_MESSAGE + "\ry")).isEqualTo(TEST_MESSAGE);
    }
    
    @Test
    public void test_setMessage()
    {
        Display display = mock(Display.class);
        Image image = new Image(display, "icons/bullet.gif");
        ControlFactory controlFactory = mock(ControlFactory.class);
        Composite parent = mock(Composite.class);
        Composite composite = mock(Composite.class);
        when(controlFactory.compositeInstance(parent)).thenReturn(composite);
        Accessible accessible = mock(Accessible.class);
        when(composite.getAccessible()).thenReturn(accessible);
        CLabel label = mock(CLabel.class);
        Color foreground  = new Color(display, new RGB(255,255,255));
        when(composite.getForeground()).thenReturn(foreground);
        when(controlFactory.customLabelInstance(composite, null, null, 18)).thenReturn(label);
        CustomLabelSpec customLabelSpec = new CustomLabelSpec(null, null, 18);
        StatusLine underTest = new StatusLine();
        Composite testComposite = underTest.getComposite(controlFactory, customLabelSpec, parent);
        assertThat(testComposite).isEqualTo(composite);
        underTest.setMessage(image, TEST_MESSAGE);
        assertThat(underTest.messageText).isEqualTo(TEST_MESSAGE);
        assertThat(underTest.messageImage).isEqualTo(image);
        verify(label).setForeground(foreground);
        verify(label).setText(TEST_MESSAGE);
        verify(label).setImage(image);

    }

    @Test
    public void test_setMessagePopup()
    {
        ControlFactory controlFactory = mock(ControlFactory.class);
        Composite parent = mock(Composite.class);
        Composite composite = mock(Composite.class);
        when(controlFactory.compositeInstance(parent)).thenReturn(composite);
        Accessible accessible = mock(Accessible.class);
        when(composite.getAccessible()).thenReturn(accessible);
        CLabel label = mock(CLabel.class);
        when(controlFactory.customLabelInstance(composite, null, null, 18)).thenReturn(label);
        CustomLabelSpec customLabelSpec = new CustomLabelSpec(null, null, 18);
        StatusLine underTest = new StatusLine();
        Composite testComposite = underTest.getComposite(controlFactory, customLabelSpec, parent);
        assertThat(testComposite).isEqualTo(composite);
        final boolean[] flagHolder = new boolean[] { false };
        underTest.setMessagePopup(new Runnable(){

            @Override
            public void run()
            {
                flagHolder[0] = true;
            }});
        ArgumentCaptor<MouseListener> listenerCaptor = ArgumentCaptor.forClass(MouseListener.class);
        verify(label).addMouseListener(listenerCaptor.capture());
        Event event = new Event();
        event.widget = mock(Widget.class);
        MouseEvent mouseEvent = new MouseEvent(event);
        mouseEvent.button = 3;
        listenerCaptor.getValue().mouseUp(mouseEvent);
        assertThat(flagHolder[0]).isTrue();
    }
}
