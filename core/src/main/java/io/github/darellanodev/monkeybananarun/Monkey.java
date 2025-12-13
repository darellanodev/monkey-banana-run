package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Monkey {
    private final Sprite sprite;
    private final Rectangle bounds;

    public Monkey(Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(2f, 2f);
        sprite.setPosition(1f, 2f);
        bounds = new Rectangle();
    }

    public void update(float deltaTime) {
        float speed = 4f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.translateX(speed * deltaTime);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprite.translateX(-speed * deltaTime);
        }

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, 16f - sprite.getWidth()));

        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
