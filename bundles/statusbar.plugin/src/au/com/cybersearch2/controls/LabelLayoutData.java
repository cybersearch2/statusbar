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
package au.com.cybersearch2.controls;

import org.eclipse.swt.SWT;

/**
 * LabelLayoutData
 * @author Andrew Bowley
 * 30 Jun 2016
 */
public class LabelLayoutData
{
    /**
     * The <code>widthHint</code> specifies a minimum width for
     * the <code>Control</code>. 
     */
    public int widthHint;

    /**
     * The <code>heightHint</code> specifies a minimum height for
     * the <code>Control</code>.
     */
    public int heightHint;

    /**
     * Create StatusItemLayoutData object
     */
    public LabelLayoutData()
    {
        widthHint = SWT.DEFAULT;
        heightHint = SWT.DEFAULT;
    }

}
