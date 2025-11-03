package io.jbnu.test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameWorld {

    public final float WORLD_GRAVITY = -2600; // 초당 중력 값
    public final float FLOOR_LEVEL = 0;          // 바닥의 Y 좌표

    public final float worldWidth;
    public final float worldHeight;

    // --- 2. 월드 객체 ---
    private GameCharacter player;
    private GameLevel level;
    private OrthographicCamera camera;
    private Viewport viewport;
    private boolean clear = false;
    private boolean isOnIce = false;


    int level_code;

    public GameWorld(float worldWidth, float worldHeight, int level_code) {
        this.level_code = level_code;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        player = new GameCharacter(worldWidth / 2, 100);
        level = new GameLevel(level_code);
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.setToOrtho(false, 1280, 720);
    }

    public void update(float delta) {
        // 중력 적용
        player.velocity.y += WORLD_GRAVITY * delta;

        // 이전 위치
        float prevX = player.position.x;
        float prevY = player.position.y;

        // 예상 위치
        float newX = prevX + player.velocity.x * delta;
        float newY = prevY + player.velocity.y * delta;

        // 바닥(y = 0) 충돌 검사
        if (newY <= FLOOR_LEVEL) {
            newY = FLOOR_LEVEL;       // 바닥에 강제 고정
            player.velocity.y = 0;    // Y축 속도 리셋
            player.isGrounded = true; // '땅에 닿음' 상태로 변경
        } else {
            player.isGrounded = false; // 공중에 떠 있음
        }

        // 블럭 충돌 검사
        for (Block block : level.getBlocks()) {
            Rectangle p = new Rectangle(newX, newY, player.CHARACTER_WIDTH, player.CHARACTER_HEIGHT);
            Rectangle b = block.getBounds();
            if (p.overlaps(b)) {

                // y축 검사
                // 블럭을 밟을 경우
                if (newY < b.y + b.height && prevY >= b.y + b.height) {
                    player.velocity.y = 0;
                    newY = b.y + b.height;
                    player.isGrounded = true;
                    if (block.getProperty() == 1) {
                        isOnIce = true;
                    } else {
                        isOnIce = false;
                    }
                }
                // 머리가 블럭에 부딪힌 경우
                else if (newY + p.height > b.y && prevY + p.height < b.y) {
                    player.velocity.y = 0;
                    newY = b.y - player.getBounds().height;
                }

                // x축 검사
                // 오른쪽에 블럭이 있을 경우
                else if (newX + p.width > b.x && prevX + p.width <= b.x) {
                    if (block.getProperty() == 2) {
                        block.position.x += player.velocity.x * delta + 0.1f;
                        block.syncSpriteToPosition();
                    } else {
                        player.velocity.x = 0;
                        newX = b.x - player.getBounds().width;
                    }
                }
                // 왼쪽에 블럭이 있을 경우
                else if (newX < b.x + b.width && prevX >= b.x + b.width) {
                    if (block.getProperty() == 2) {
                        block.position.x += player.velocity.x * delta - 0.1f;
                        block.syncSpriteToPosition();
                    } else {
                        player.velocity.x = 0;
                        newX = b.x + b.width;
                    }
                }
            }
        }

        // --- 최종 위치 확정 ---
        player.position.set(newX, newY);

        // 감속 적용
        if (player.isGrounded) {
            if (isOnIce) {
                player.velocity.x *= 0.995;
            } else {
                player.velocity.x *= 0.8;
            }
        } else {
            player.velocity.x *= 0.98;
        }

        // 최종 위치에 따라 카메라 이동
        camera.position.x = player.position.x + player.sprite.getWidth() / 2f;
        if (player.position.y >= worldHeight/2) {
            camera.position.y = player.position.y;
        } else {
            camera.position.y = worldHeight/2;
        }
        camera.update();

        // 그래픽 동기화
        player.syncSpriteToPosition();

        // 골 지점에 도달했을 경우 상태 전환
        if (player.getBounds().overlaps(level.getGoal().getBounds())) {
            clear = true;
        }
    }

    // GameScreen으로부터 '점프' 입력을 받음
    public void onPlayerJump() {
        player.jump();
    }

    public void onPlayerLeft() {
        if (!player.isGrounded) {
            player.moveLeft(20);
        } else if (isOnIce) {
            player.moveLeft(40);
        } else {
            player.moveLeft(180);
        }
    }

    public void onPlayerRight() {
        if (!player.isGrounded || isOnIce) {
            player.moveRight(20);
        } else if (isOnIce) {
            player.moveRight(40);
        } else {
            player.moveRight(180);
        }
    }

    // GameScreen이 그릴 수 있도록 객체를 제공
    public GameCharacter getPlayer() {
        return player;
    }

    public Camera getCamera() {
        return camera;
    }

    public GameLevel getLevel() {
        return level;
    }

    public boolean isClear() {
        return clear;
    }

    public void closeWorld() {
        camera.position.x = worldWidth / 2;
        camera.position.y = worldHeight / 2;
        camera.update();
        player.syncSpriteToPosition();
        level.dispose();
        player.dispose();
    }
}
