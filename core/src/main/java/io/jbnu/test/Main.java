package io.jbnu.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    GameWorld world;

    // Sounds
    Sound effectSound;

    // Textures
    private Texture mainmenuTexture;
    private Texture pauseTexture;
    private Texture clearTexture_background;
    private Texture clearTexture_newRecord;

    // Sprites
    private Sprite backgroundSprite;
    private Sprite mainmenuSprite;
    private Sprite pauseSprite;
    private Sprite clearSprite_background;
    private Sprite clearSprite_newRecord;

    // Fonts
    private BitmapFont mainMenuFont;
    private BitmapFont levelFont;
    private BitmapFont stopWatchFont;
    private BitmapFont recordFont;
    private BitmapFont bestRecordFont;


    private final float WORLD_WIDTH = 1280;
    private final float WORLD_HEIGHT = 720;

    // 내부 상태
    private int level_code;
    private float stopWatch;
    private float[] records;
    private boolean isNewRecord;


    public enum GameState {
        MAIN_MENU,
        RUNNING,
        PAUSED,
        CLEAR
    }
    private GameState currentState;

    @Override
    public void create() {
        batch = new SpriteBatch();

        effectSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));

        mainmenuTexture = new Texture("mainmenu.png");
        pauseTexture = new Texture("pause.png");

        mainMenuFont = new BitmapFont();
        mainMenuFont.getData().setScale(3);
        mainMenuFont.setUseIntegerPositions(true);

        levelFont = new BitmapFont();
        levelFont.getData().setScale(2);
        levelFont.setUseIntegerPositions(true);

        stopWatchFont = new BitmapFont();
        stopWatchFont.getData().setScale(2);
        stopWatchFont.setUseIntegerPositions(true);

        clearTexture_background = new Texture("levelcomplete.png");
        clearTexture_newRecord = new Texture("newrecord.png");

        recordFont = new BitmapFont();
        recordFont.getData().setScale(3);
        recordFont.setUseIntegerPositions(true);

        bestRecordFont = new BitmapFont();
        bestRecordFont.getData().setScale(3);
        bestRecordFont.setUseIntegerPositions(true);

        records = new float[3];
        for (int i=0; i<3; i++) {
            records[i] = 1000000000;
        }
        isNewRecord = false;

        level_code = 1;
        currentState = GameState.MAIN_MENU;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        input();
        logic();
        draw();
    }

    private void logic(){
        if (currentState == GameState.RUNNING) {
            if (world.isClear()) {
                startClearUI();
            }
            float delta = Gdx.graphics.getDeltaTime();
            stopWatch += delta;
            world.update(delta);
            batch.setProjectionMatrix(world.getCamera().combined);
        }
    }

    private void draw(){
        batch.begin();

        if (currentState == GameState.MAIN_MENU) {
            mainmenuSprite = new Sprite(mainmenuTexture);
            mainmenuSprite.setScale(0.2f);
            mainmenuSprite.setCenter(WORLD_WIDTH/2, WORLD_HEIGHT/2);
            mainmenuSprite.draw(batch);
        } else {
            world.getLevel().drawBG(batch);
            world.getPlayer().draw(batch);

            for (Block b : world.getLevel().getBlocks()) {
                b.draw(batch);
            }
            world.getLevel().getGoal().draw(batch);

            levelFont.draw(batch, "Level: " + level_code,
                world.getCamera().position.x - WORLD_WIDTH/2 + 5, world.getCamera().position.y + WORLD_HEIGHT/2 - 10);
            stopWatchFont.draw(batch, "Time: " + String.format("%.2f", stopWatch),
                world.getCamera().position.x - WORLD_WIDTH/2 + 5, world.getCamera().position.y + WORLD_HEIGHT/2 - 50);

            if (currentState == GameState.PAUSED) {
                pauseSprite = new Sprite(pauseTexture);
                pauseSprite.setScale(0.2f);
                pauseSprite.setCenter(world.getCamera().position.x, world.getCamera().position.y);
                pauseSprite.draw(batch);
            }

            else if (currentState == GameState.CLEAR) {
                clearSprite_background = new Sprite(clearTexture_background);
                clearSprite_background.setScale(0.2f);
                clearSprite_background.setCenter(world.getCamera().position.x, world.getCamera().position.y);
                clearSprite_background.draw(batch);

                recordFont.draw(batch, "Record: " + String.format("%.2f", stopWatch),
                    world.getCamera().position.x - 174, world.getCamera().position.y + 50);

                if (isNewRecord) {
                    clearSprite_newRecord = new Sprite(clearTexture_newRecord);
                    clearSprite_newRecord.setScale(0.15f);
                    clearSprite_newRecord.setCenter(world.getCamera().position.x + 180, world.getCamera().position.y + 30);
                    clearSprite_newRecord.draw(batch);
                } else {
                    bestRecordFont.draw(batch, "Best: " + String.format("%.2f", records[level_code - 1]),
                        world.getCamera().position.x - 120, world.getCamera().position.y - 20);
                }
            }
        }

        batch.end();
    }
    private void input() {
        if (currentState == GameState.RUNNING) {
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                world.onPlayerRight();
            } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                world.onPlayerLeft();
            }
            if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)) {
                world.onPlayerJump();
            }
            if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
                currentState = GameState.PAUSED;
            }
        }

        else if (currentState == GameState.PAUSED) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
                currentState = GameState.RUNNING;
            } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
                startMainMenuUI();
            } else if (Gdx.input.isKeyJustPressed(Keys.R)) {
                startLevel(level_code);
            }
        }

        else if (currentState == GameState.CLEAR) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                // 레벨 3일 시 메인메뉴로 이동
                if (level_code >= 3) {
                    startMainMenuUI();
                } else {
                    startLevel(++level_code);
                }
            } else if (Gdx.input.isKeyJustPressed(Keys.R)) {
                startLevel(1);
            } else if (Gdx.input.isKeyJustPressed(Keys.Q)) {
                startMainMenuUI();
            }
        }

        else if (currentState == GameState.MAIN_MENU)
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                startLevel(level_code);
        }
    }

    @Override
    public void dispose() {
        mainMenuFont.dispose();
        levelFont.dispose();
        stopWatchFont.dispose();

        batch.dispose();
    }

    public void startMainMenuUI() {
        world.closeWorld();
        batch.setProjectionMatrix(world.getCamera().combined);
        level_code = 1;
        currentState = GameState.MAIN_MENU;
    }
    public void startLevel(int level_code) {
        if (currentState != GameState.MAIN_MENU) {
            world.closeWorld();
        }
        world = new GameWorld(this.WORLD_WIDTH, this.WORLD_HEIGHT, level_code);
        isNewRecord = false;
        stopWatch = 0f;
        currentState = GameState.RUNNING;
    }

    public void startClearUI() {
        currentState = GameState.CLEAR;
        // 최고기록 저장
        if (records[level_code-1] > stopWatch) {
            records[level_code-1] = stopWatch;
            isNewRecord = true;
        }
    }
    
}
