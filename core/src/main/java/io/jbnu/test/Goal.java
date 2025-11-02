package io.jbnu.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Goal {
    private Sprite sprite;
    private Rectangle bounds;

    public Goal(float x, float y, float width, float height, Texture goalTexture) {
        sprite = new Sprite(goalTexture);
        sprite.setBounds(x, y, width, height);
        bounds = new Rectangle(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

