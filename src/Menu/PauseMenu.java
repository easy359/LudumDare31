package Menu;

import Util.HitBox;
import Util.Location;
import Util.Resources;

public class PauseMenu {

    private boolean newGameClicked;
    private boolean optionsClicked;

    public PauseMenu() {
        init();
    }

    private HitBox newGame;
    private HitBox options;
    private boolean newGameHilight;
    private int[] newGamePixels;
    private int[] newGamePixelsHilight;
    private boolean optionHilight;
    private int[] optionsPixels;
    private int[] optionsPixelsHilight;

    private void init() {
        newGameClicked = false;
        optionsClicked = false;
        newGame = new HitBox(352, 200, 128, 64);
        options = new HitBox(352, 300, 128, 64);
        newGameHilight = false;
        newGamePixels = Engine.Main.resources.gameMenu1.getPixels();
        newGamePixelsHilight = Engine.Main.resources.gameMenu2.getPixels();
        optionHilight = false;
        optionsPixels = Engine.Main.resources.options1.getPixels();
        optionsPixelsHilight = Engine.Main.resources.options2.getPixels();
    }

    public void update() {
        Location loc = Engine.Main.mouseMotionInput.getCurrentMousePointerLocation();
        if (newGame.pointIntersects(loc)) {
            newGameHilight = true;
        } else {
            newGameHilight = false;
        }
        if (options.pointIntersects(loc)) {
            optionHilight = true;
        } else {
            optionHilight = false;
        }
        Location click = Engine.Main.mouseInput.getLastFrameClickLocation();
        if (newGame.pointIntersects(click)) {
            newGameClicked = true;
        } else {
            newGameClicked = false;
        }
        if (options.pointIntersects(click)) {
            optionsClicked = true;
        } else {
            optionsClicked = false;
        }
    }

    public void render(int[] pixels) {
        if (!newGameHilight) {
            Resources.addPixels(pixels, newGamePixels, newGame.getX(), newGame.getY(), newGame.getWidth(), newGame.getHeight());
        } else if (newGameHilight) {
            Resources.addPixels(pixels, newGamePixelsHilight, newGame.getX(), newGame.getY(), newGame.getWidth(), newGame.getHeight());

        }
        if (!optionHilight) {
            Resources.addPixels(pixels, optionsPixels, options.getX(), options.getY(), options.getWidth(), options.getHeight());
        } else if (optionHilight) {
            Resources.addPixels(pixels, optionsPixelsHilight, options.getX(), options.getY(), options.getWidth(), options.getHeight());

        }
    }

    public boolean newGame() {
        return newGameClicked;
    }

    public boolean options() {
        return optionsClicked;
    }
}
