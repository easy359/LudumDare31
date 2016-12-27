package Listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	public boolean[] keys;
	private boolean[] currentKeys;
	private boolean[] tempKeys;
	private int numberOfKeys;

	public KeyInput(int numberOfKeys) {
		this.numberOfKeys = numberOfKeys;
		keys = new boolean[numberOfKeys];
		currentKeys = new boolean[numberOfKeys];
		tempKeys = new boolean[numberOfKeys];
	}

	public void update() {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == true) {
				if (tempKeys[i] == false && currentKeys[i] == false) {
					currentKeys[i] = true;
					tempKeys[i] = true;
				} else if (currentKeys[i] == true) {
					currentKeys[i] = false;
				}
			} else if (keys[i] == false) {
				currentKeys[i] = false;
				tempKeys[i] = false;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() <= numberOfKeys)
			keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() <= numberOfKeys)
			keys[e.getKeyCode()] = false;
	}

	public boolean[] getKeys() {
		return keys;
	}

	public boolean[] getCurrentKeys() {
		return currentKeys;
	}

	public boolean isKeyPressed(int keyCode) {
		return ((keyCode >= 0) && (keyCode <= numberOfKeys)) ? keys[keyCode] : false;
	}

	public boolean isCurrentKeyPressed(int keyCode) {
		return ((keyCode >= 0) && (keyCode <= numberOfKeys)) ? currentKeys[keyCode] : false;
	}

	public int getNumberOfKeys() {
		return numberOfKeys;
	}
}
