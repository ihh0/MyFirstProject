package io.jbnu.test;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class GameLevel {
    // Objects
    private ArrayList<Block> blocks;
    private Goal goal;
    // Textures
    Texture blockTexture0;
    Texture blockTexture1;
    Texture blockTexture2;
    Texture goalTexture;

    public GameLevel(int level_code) {
        blocks = new ArrayList<>();
        blockTexture0 = new Texture("block1.png");
        blockTexture1 = new Texture("block2.png");
        blockTexture2 = new Texture("block3.png");
        goalTexture = new Texture("goal.png");
        switch(level_code) { // 받은 레벨에 따라 다른 맵 생성
            case 1:
                createLevel_1();
                break;
            case 2:
                createLevel_2();
                break;
            case 3:
                createLevel_3();
                break;
        }
    }

    private void createLevel_1() {
        blocks.add(new Block(-1000, 0, 1000, 1000, blockTexture0));
        for (int i = 0; i < 40; i++) {
            blocks.add(new Block(100*i, 0, 100, 100, blockTexture0));
        }
        blocks.add(new Block(4000, 0, 1000, 1000, blockTexture0));

        blocks.add(new Block(1500, 100, 100, 100, blockTexture0));
        blocks.add(new Block(1600, 100, 100, 100, blockTexture0));

        blocks.add(new Block(1200, 300, 100, 100, blockTexture0));
        blocks.add(new Block(1300, 300, 100, 100, blockTexture0));

        blocks.add(new Block(1800, 400, 100, 100, blockTexture0));
        blocks.add(new Block(1900, 400, 100, 100, blockTexture0));
        blocks.add(new Block(2000, 400, 100, 100, blockTexture0));
        blocks.add(new Block(2100, 400, 100, 100, blockTexture0));

        blocks.add(new Block(1900, 100, 100, 100, blockTexture0));
        blocks.add(new Block(1900, 200, 100, 100, blockTexture0));
        blocks.add(new Block(1900, 300, 100, 100, blockTexture0));

        blocks.add(new Block(2100, 200, 100, 100, blockTexture0));
        blocks.add(new Block(2200, 200, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 200, 100, 100, blockTexture0));

        blocks.add(new Block(2300, 300, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 400, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 500, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 600, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 700, 100, 100, blockTexture0));
        blocks.add(new Block(2300, 800, 100, 100, blockTexture0));

        blocks.add(new Block(2500, 100, 100, 100, blockTexture0));

        blocks.add(new Block(2800, 100, 100, 100, blockTexture0));
        blocks.add(new Block(2800, 200, 100, 100, blockTexture0));

        blocks.add(new Block(3200, 100, 100, 100, blockTexture0));
        blocks.add(new Block(3200, 200, 100, 100, blockTexture0));
        blocks.add(new Block(3200, 300, 100, 100, blockTexture0));
        blocks.add(new Block(3300, 300, 100, 100, blockTexture0));

        blocks.add(new Block(3400, 200, 100, 100, blockTexture0));
        blocks.add(new Block(3500, 100, 100, 100, blockTexture0));


        blocks.add(new Block(3800, 400, 100, 100, blockTexture0));
        blocks.add(new Block(3900, 400, 100, 100, blockTexture0));

        goal = new Goal(3900, 500, 100, 150, goalTexture);
    }

    private void createLevel_2() {
        blocks.add(new Block(-1000, 0, 1000, 1000, blockTexture1, 1));
        for (int i = 0; i < 40; i++) {
            blocks.add(new Block(100*i, 0, 100, 100, blockTexture1, 1));
        }
        blocks.add(new Block(4000, 0, 1000, 1000, blockTexture1, 1));

        blocks.add(new Block(1200, 100, 300, 100, blockTexture1, 1));
        blocks.add(new Block(1600, 300, 200, 100, blockTexture1, 1));
        blocks.add(new Block(2000, 400, 200, 100, blockTexture1, 1));
        blocks.add(new Block(2500, 500, 300, 100, blockTexture1, 1));

        blocks.add(new Block(2000, 700, 200, 100, blockTexture1, 1));
        blocks.add(new Block(1900, 900, 200, 100, blockTexture1, 1));

        blocks.add(new Block(2100, 1100, 500, 100, blockTexture1, 1));
        blocks.add(new Block(2900, 1200, 200, 100, blockTexture1, 1));
        blocks.add(new Block(3400, 1300, 200, 100, blockTexture1, 1));


        blocks.add(new Block(3900, 100, 200, 1400, blockTexture1, 1));

        goal = new Goal(3900, 1500, 200, 300, goalTexture);
    }

    private void createLevel_3() {
        blocks.add(new Block(-1000, 0, 1000, 1000, blockTexture0));
        for (int i = 0; i < 40; i++) {
            blocks.add(new Block(100*i, 0, 100, 100, blockTexture0));
        }
        blocks.add(new Block(4000, 0, 1000, 1000, blockTexture0));

        blocks.add(new Block(1500, 100, 100, 100, blockTexture2, 2));

        blocks.add(new Block(500, 300, 100, 100, blockTexture0));

        blocks.add(new Block(900, 400, 1000, 100, blockTexture1, 1));
        blocks.add(new Block(1000, 500, 100, 70, blockTexture2, 2));

        blocks.add(new Block(1500, 570, 100, 230, blockTexture0));
        blocks.add(new Block(1600, 700, 100, 100, blockTexture0));

        blocks.add(new Block(1800, 900, 1000, 100, blockTexture0));
        blocks.add(new Block(1900, 1100, 100, 100, blockTexture2, 2));

        blocks.add(new Block(2800, 1300, 100, 100, blockTexture1, 1));
        blocks.add(new Block(3200, 1400, 100, 100, blockTexture1, 1));
        blocks.add(new Block(3230, 1500, 100, 100, blockTexture2, 2));

        blocks.add(new Block(3900, 1400, 400, 100, blockTexture1, 1));
        goal = new Goal(4200, 1500, 200, 300, goalTexture);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public Goal getGoal() {
        return goal;
    }

    public void dispose() {
        blockTexture0.dispose();
        goalTexture.dispose();
    }
}
