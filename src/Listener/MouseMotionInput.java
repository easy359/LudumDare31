package Listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import Util.Location;

public class MouseMotionInput extends MouseMotionAdapter {

	public Location currentMousePointerLocation = new Location(-1, -1);

	public MouseMotionInput() {
		currentMousePointerLocation = new Location(-1, -1);
	}

	public void mouseMoved(MouseEvent e) {
		currentMousePointerLocation = new Location(e.getX(), e.getY());
	}

	public Location getCurrentMousePointerLocation() {
		return currentMousePointerLocation;
	}

}
