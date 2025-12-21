package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Monkey {
    private final Rectangle bounds;
    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> idleAnimation;
    private final float width = 2f;
    private final float height = 2f;
    private final float speed = 4f;

    private float stateTime = 0f;
    private boolean facingRight = true;
    private float x;
    private float y;
    private boolean moving;

    public Monkey() {
        runAnimation = null;
        idleAnimation = null;
        bounds = createBounds();
    }

    public Monkey(Texture runTexture, Texture idleTexture) {
        runAnimation = AnimationHelper.getAnimation(runTexture);
        idleAnimation = AnimationHelper.getAnimation(idleTexture);
        bounds = createBounds();
    }

    private Rectangle createBounds() {
        x = 1f;
        y = 2f;
        return new Rectangle(x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void update(float deltaTime) {
        int direction = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = -1;
        }

        applyMovement(deltaTime, direction);
    }

    public void applyMovement(float deltaTime, int direction) {
        handleFacingRight(direction);
        stateTime += deltaTime;
        x += direction * speed * deltaTime;
        x = MathUtils.clamp(x, 0, Config.WORLD_WIDTH - width);
        bounds.set(x, y, width, height);
    }

    private void handleFacingRight(int direction) {
        moving = direction != 0;
        if (direction != 0) {
            facingRight = direction > 0;
        }
    }

    public TextureRegion getCurrentFrame() {
        if (moving){
            return runAnimation.getKeyFrame(stateTime, true);
        } else {
            return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void draw(SpriteBatch batch) {
        if (isFacingRight()) {
            batch.draw(getCurrentFrame(), x, y, width, height);
            return;
        }
        batch.draw(getCurrentFrame(), x + width, y, -width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
