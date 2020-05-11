package Menu;

import Util.HitBox;
import Util.Location;
import Util.Mode;
import Util.Resources;

public class OptionMenu {

    private boolean easyClicked;
    private boolean mediumClicked;
    private boolean hardClicked;

    public OptionMenu() {
        init();
    }

    private HitBox easy;
    private HitBox medium;
    private HitBox hard;
    private HitBox back;
    private boolean easyHilight;
    private int[] easyPixels;
    private int[] easyHilighted;
    private boolean mediumHilight;
    private int[] mediumPixels;
    private int[] mediumHilighted;
    private boolean hardHilight;
    private int[] hardPixels;
    private int[] hardHilighted;
    private boolean backHilight;
    private int[] backPixels;
    private int[] backHilighted;
    private boolean goBack;
    Mode mode;

    private void init() {
        easyClicked = true;
        mediumClicked = false;
        hardClicked = false;
        easy = new HitBox(352, 100, 128, 64);
        medium = new HitBox(352, 200, 128, 64);
        hard = new HitBox(352, 300, 128, 64);
        back = new HitBox(352, 400, 128, 64);
        easyPixels = Engine.Main.resources.easy1.getPixels();
        easyHilighted = Engine.Main.resources.easy2.getPixels();
        mediumPixels = Engine.Main.resources.medium1.getPixels();
        mediumHilighted = Engine.Main.resources.medium2.getPixels();
        hardPixels = Engine.Main.resources.hard1.getPixels();
        hardHilighted = Engine.Main.resources.hard2.getPixels();
        backPixels = Engine.Main.resources.back1.getPixels();
        backHilighted = Engine.Main.resources.back2.getPixels();
        easyHilight = true;
        mediumHilight = false;
        hardHilight = false;
        backHilight = false;
        goBack = false;
        mode = Mode.easy;
    }

    public void update() {
        Location click = Engine.Main.mouseInput.getLastFrameClickLocation();
        if (easy.pointIntersects(click)) {
            mode = Mode.easy;
        } else if (medium.pointIntersects(click)) {
            mode = Mode.medium;
        } else if (hard.pointIntersects(click)) {
            mode = Mode.hard;
        } else if (back.pointIntersects(click)) {
            goBack = true;
        } else if (!back.pointIntersects(click)) {
            goBack = false;
        }

        if (mode == Mode.easy) {
            easyHilight = true;
            mediumHilight = false;
            hardHilight = false;
        } else if (mode == Mode.medium) {
            easyHilight = false;
            mediumHilight = true;
            hardHilight = false;
        } else if (mode == Mode.hard) {
            easyHilight = false;
            mediumHilight = false;
            hardHilight = true;
        }

        Location loc = Engine.Main.mouseMotionInput.getCurrentMousePointerLocation();
        if (easy.pointIntersects(loc)) {
            easyHilight = true;
        } else if (medium.pointIntersects(loc)) {
            mediumHilight = true;
        } else if (hard.pointIntersects(loc)) {
            hardHilight = true;
        } else if (back.pointIntersects(loc)) {
            backHilight = true;
        } else if (!back.pointIntersects(loc)) {
            backHilight = false;
        }
    }

    public void render(int[] pixels) {
        if (!easyHilight) {
            Resources.addPixels(pixels, easyHilighted, easy.getX(), easy.getY(), easy.getWidth(), easy.getHeight());
        } else if (easyHilight) {
            Resources.addPixels(pixels, easyPixels, easy.getX(), easy.getY(), easy.getWidth(), easy.getHeight());
        }
        if (!mediumHilight) {
            Resources.addPixels(pixels, mediumHilighted, medium.getX(), medium.getY(), medium.getWidth(), medium.getHeight());
        } else if (mediumHilight) {
            Resources.addPixels(pixels, mediumPixels, medium.getX(), medium.getY(), medium.getWidth(), medium.getHeight());
        }
        if (!hardHilight) {
            Resources.addPixels(pixels, hardHilighted, hard.getX(), hard.getY(), hard.getWidth(), hard.getHeight());
        } else if (hardHilight) {
            Resources.addPixels(pixels, hardHilighted, hard.getX(), hard.getY(), hard.getWidth(), hard.getHeight());
            Resources.addPixels(pixels, hardPixels, hard.getX(), hard.getY(), hard.getWidth(), hard.getHeight());
        }
        if (backHilight) {
            Resources.addPixels(pixels, backHilighted, back.getX(), back.getY(), back.getWidth(), back.getHeight());
        } else if (!backHilight) {
            Resources.addPixels(pixels, backPixels, back.getX(), back.getY(), back.getWidth(), back.getHeight());
        }
    }

    public boolean easy() {
        return easyClicked;
    }

    public boolean medium() {
        return mediumClicked;
    }

    public boolean hard() {
        return hardClicked;
    }

    public boolean goBack() {
        return goBack;
    }

    public Mode getMode() {
        return mode;
    }

}
