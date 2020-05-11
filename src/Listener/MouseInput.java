package Listener;

import Util.Location;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private boolean[] mouseButtons;
    private boolean[] currentMouseButtons;
    private boolean[] tempMouseButtons;

    private Location lastClickLocation = new Location(-1, -1);
    private Location lastFrameClickLocation = new Location(-1, -1);
    private boolean justClicked = false;
    private boolean shouldReset = false;

    public MouseInput(int numberOfMouseButtons) {
        mouseButtons = new boolean[numberOfMouseButtons];
        currentMouseButtons = new boolean[numberOfMouseButtons];
        tempMouseButtons = new boolean[numberOfMouseButtons];
    }

    public void update() {
        for (int i = 0; i < mouseButtons.length; i++) {
            if (mouseButtons[i] == true) {
                if (tempMouseButtons[i] == false && currentMouseButtons[i] == false) {
                    currentMouseButtons[i] = true;
                    tempMouseButtons[i] = true;
                } else if (tempMouseButtons[i] == true && currentMouseButtons[i] == true) {
                    currentMouseButtons[i] = false;
                }
            } else if (mouseButtons[i] == false) {
                currentMouseButtons[i] = false;
                tempMouseButtons[i] = false;
            }
        }
        if (justClicked == true && shouldReset == false) {
            shouldReset = true;
        } else if (shouldReset == true) {
            lastFrameClickLocation.setLocation(-1, -1);
            justClicked = false;
            shouldReset = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        lastClickLocation.setLocation(e.getX(), e.getY());
        lastFrameClickLocation.setLocation(e.getX(), e.getY());
        justClicked = true;
    }

    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()] = false;
    }

    public boolean[] getMouseButtons() {
        return mouseButtons;
    }

    public boolean[] getCurrentMouseButtons() {
        return currentMouseButtons;
    }

    public Location getLastClickLocation() {
        return lastClickLocation;
    }

    public Location getLastFrameClickLocation() {
        return lastFrameClickLocation;
    }
}
