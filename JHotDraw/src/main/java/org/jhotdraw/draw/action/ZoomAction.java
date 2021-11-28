/*
 * @(#)ZoomAction.java  1.1 2006-04-21
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.draw.action;

import java.awt.Rectangle;
import javax.swing.*;
import javax.swing.undo.*;
import org.jhotdraw.draw.*;
/**
 * ZoomAction.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-04-21 Constructor with DrawingEditor paremeter added.
 * <br>1.0 January 16, 2006 Created.
 */
public class ZoomAction extends AbstractDrawingViewAction {
    private double scaleFactor;
    private AbstractButton button;
    private String label;
    private Rectangle rectangleTest;

    public Rectangle getRectangleTest() {
        return rectangleTest;
    }

    public void setRectangleTest(Rectangle rectangleTest) {
        this.rectangleTest = rectangleTest;
    }
    
    
    
    public double getScaleFactor() {
        return scaleFactor;
    }

    public AbstractButton getButton() {
        return button;
    }

    public String getLabel() {
        return label;
    }
    
    
    
    /**
     * Creates a new instance.
     */
    public ZoomAction(DrawingEditor editor, double scaleFactor, AbstractButton button) {
        this((DrawingView) null, scaleFactor, button);
        setEditor(editor);
    }
    /**
     * Creates a new instance.
     */
    public ZoomAction(DrawingView view, double scaleFactor, AbstractButton button) {
        super(view);
        this.scaleFactor = scaleFactor;
        this.button = button;
        label = (int) (scaleFactor * 100)+" %";
        putValue(Action.DEFAULT, label);
        putValue(Action.NAME, label);
    }
    
    public void calculateZoom(){
        final Rectangle vRect = getView().getComponent().getVisibleRect();
        final double oldFactor = getView().getScaleFactor();
        getView().setScaleFactor(getScaleFactor());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (vRect != null) {
                    vRect.x = (int) (vRect.x / oldFactor * getScaleFactor());
                    vRect.y = (int) (vRect.y / oldFactor * getScaleFactor());
                    vRect.width = (int) (vRect.width / oldFactor * getScaleFactor());
                    vRect.height = (int) (vRect.height / oldFactor * getScaleFactor());
                    vRect.x += vRect.width / 3;
                    vRect.y += vRect.height / 3;
                    vRect.width /= 3;
                    vRect.height /= 3;
                    getView().getComponent().scrollRectToVisible(vRect);
                }
            }
        });
    }
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (getButton() != null) {
            getButton().setText(getLabel());
        }
        calculateZoom();
    }
}
