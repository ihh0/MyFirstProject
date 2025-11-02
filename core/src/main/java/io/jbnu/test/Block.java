package io.jbnu.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
    private Sprite sprite;
    private Rectangle bounds;
    private int blockType;

    public Vector2 position; // 위치
    public Vector2 velocity; // 속도

    public Block(float x, float y, float width, float height, Texture blockTexture) {
        sprite = new Sprite(blockTexture);
        sprite.setBounds(x, y, width, height);
        bounds = new Rectangle(x, y, width, height);
        this.blockType = 0;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public Block(float x, float y, float width, float height, Texture blockTexture, int blockType) {
        sprite = new Sprite(blockTexture);
        sprite.setBounds(x, y, width, height);
        bounds = new Rectangle(x, y, width, height);
        this.blockType = blockType;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public void syncSpriteToPosition() {
        sprite.setPosition(position.x, position.y);
        bounds.setPosition(position.x, position.y);
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public int getProperty() {
        return blockType;
    }

}
