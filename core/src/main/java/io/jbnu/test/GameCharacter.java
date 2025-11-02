package io.jbnu.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

public class GameCharacter {
    // 캐릭터 움직임 관리
    private float MOVE_SPEED = 500f;
    private float JUMP_FORCE = 1000f;

    // 상태 관리
    public Vector2 position; // 위치
    public Vector2 velocity; // 속도
    // 그래픽 관리
    public final int CHARACTER_WIDTH = 50;
    public final int CHARACTER_HEIGHT = 70;
    private Texture texture;
    public Sprite sprite;
    public Rectangle bounds;
    public boolean isGrounded = false; // '땅에 닿아있는가?' (점프 가능 여부)

    public GameCharacter(float startX, float startY) {
        // 물리 상태 초기화
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        // 그래픽 상태 초기화
        this.texture = new Texture("player.png");
        this.sprite = new Sprite(texture);
        this.sprite.setBounds(position.x, position.y, CHARACTER_WIDTH, CHARACTER_HEIGHT);
        this.bounds = new Rectangle(startX, startY, sprite.getWidth(), sprite.getHeight());
    }

    public void jump() {
        if (isGrounded) {
            velocity.y = JUMP_FORCE; // Y축으로 점프 속도 설정
            isGrounded = false; // 점프했으니 땅에서 떨어짐
        }
    }

    // --- 3. 행동 (메서드) ---
    public void moveRight(int speed) {
        velocity.x += speed;
        if (velocity.x > MOVE_SPEED) {
            velocity.x = MOVE_SPEED;
        }
    }
    public void moveLeft(int speed) {
        velocity.x -= speed;
        if (velocity.x < -MOVE_SPEED) {
            velocity.x = -MOVE_SPEED;
        }
    }

    public void syncSpriteToPosition() {
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(position.x, position.y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
