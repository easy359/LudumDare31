package Util;

import java.util.ArrayList;

public class Resources {

    public Image DEFAULT_IMAGE;
    public Image METAL;
    public Image BRICK;
    public Image RGB_SHORT;
    public Image gameMenu1;
    public Image gameMenu2;
    public Image options1;
    public Image options2;
    public Image easy1;
    public Image easy2;
    public Image medium1;
    public Image medium2;
    public Image hard1;
    public Image hard2;
    public Image back1;
    public Image back2;
    public Image heart;
    public Image playerDownStill;
    public Image playerUpStill;
    public Image playerRightStill;
    public Image playerLeftStill;
    public Image playerDownShoot;
    public Image playerUpShoot;
    public Image playerRightShoot;
    public Image playerLeftShoot;
    public Image background;
    public Image bullet;
    public Image zombieLeft;
    public Image zombieRight;
    public Image leftShoot;
    public Image rightShoot;
    public ArrayList<Image> numbers = new ArrayList<Image>();

    public void load() {
        loadImages();
    }

    private void loadImages() {
        {
            gameMenu1 = new Image("/Menu/NewGame1.png", 0, 0, 128, 64);
            gameMenu2 = new Image("/Menu/NewGame2.png", 0, 0, 128, 64);
            options1 = new Image("/Menu/Options1.png", 0, 0, 128, 64);
            options2 = new Image("/Menu/Options2.png", 0, 0, 128, 64);
            easy1 = new Image("/Menu/Easy1.png", 0, 0, 128, 64);
            easy2 = new Image("/Menu/Easy2.png", 0, 0, 128, 64);
            medium1 = new Image("/Menu/Medium1.png", 0, 0, 128, 64);
            medium2 = new Image("/Menu/Medium2.png", 0, 0, 128, 64);
            hard1 = new Image("/Menu/Hard1.png", 0, 0, 128, 64);
            hard2 = new Image("/Menu/Hard2.png", 0, 0, 128, 64);
            back1 = new Image("/Menu/Back1.png", 0, 0, 128, 64);
            back2 = new Image("/Menu/Back2.png", 0, 0, 128, 64);
        }
        {
            heart = new Image("/Player/Heart.png", 0, 0, 16, 16);
            playerDownStill = new Image("/Player/PlayerStillSheet.png", 0, 0, 32, 64);
            playerDownShoot = new Image("/Player/PlayerShootSheet.png", 0, 0, 32, 64);
            playerUpStill = new Image("/Player/PlayerStillSheet.png", 32, 0, 32, 64);
            playerUpShoot = new Image("/Player/PlayerShootSheet.png", 32, 0, 32, 64);
            playerRightStill = new Image("/Player/PlayerStillSheet.png", 64, 0, 32, 64);
            playerRightShoot = new Image("/Player/PlayerShootSheet.png", 64, 0, 32, 64);
            playerLeftStill = new Image("/Player/PlayerStillSheet.png", 96, 0, 32, 64);
            playerLeftShoot = new Image("/Player/PlayerShootSheet.png", 96, 0, 32, 64);
        }
        background = new Image("/Background.png", 0, 0, 832, 640);

        {
            bullet = new Image("/Shoot/Bullet.png", 0, 0, 4, 2);
        }
        {
            zombieLeft = new Image("/Zombie/ZombieSheet.png", 32, 0, 32, 64);
            zombieRight = new Image("/Zombie/ZombieSheet.png", 0, 0, 32, 64);
            leftShoot = new Image("/Zombie/ZombieSheet.png", 96, 0, 32, 64);
            rightShoot = new Image("/Zombie/ZombieSheet.png", 64, 0, 32, 64);
        }
        {
            for (int i = 32; i < 128; i += 16) {
                numbers.add(new Image("/Text/Black.png", i, 48, 16, 16));
            }
            for (int i = 0; i < 64; i += 16) {
                numbers.add(new Image("/Text/Black.png", i, 64, 16, 16));
            }
        }
    }

    public static int[] addPixels(int[] pixels, int[] add, int addX, int addY, int addWidth, int addHeight) {
        int width = Engine.Main.WIDTH;
        int height = Engine.Main.HEIGHT;
        for (int y = 0; y < addHeight; y++) {
            for (int x = 0; x < addWidth; x++) {
                int addPos = addWidth * y + x;
                int pixelsPos = width * (addY + y) + (addX + x);
                int pixelsSize = width * height;
                if (pixelsPos >= 0 && pixelsPos <= pixelsSize) {
                    if (addX + x < width && addX + x >= 0 && addY + y < height && addY + y >= 0) {
                        if (add[addPos] != -65281) {
                            pixels[pixelsPos] = add[addPos];
                        }
                    }
                }
            }
        }
        return pixels;
    }
}
