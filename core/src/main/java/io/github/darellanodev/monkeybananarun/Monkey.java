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
    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;

    private boolean facingRight = true;

    private float x;
    private float y;

    private boolean moving;

    private final float width = 2f;
    private final float height = 2f;

    private final Rectangle bounds;

    private final float speed = 4f;
    private final float WORLD_WIDTH = 16f;

    public Monkey() {
        runAnimation = null;
        idleAnimation = null;
        bounds = createBounds();
    }

    public Monkey(Texture runTexture, Texture idleTexture) {
        runAnimation = getAnimation(runTexture);
        idleAnimation = getAnimation(idleTexture);
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

    private Animation<TextureRegion> getAnimation(Texture texture) {

        int FRAME_COLS = 6;
        int FRAME_ROWS = 1;
        int FRAME_WIDTH = 128;
        int FRAME_HEIGHT = 128;
        TextureRegion[][] tmp = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<>(0.1f, frames);
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
        moving = direction != 0;
        if (direction != 0) {
            facingRight = direction > 0;
        }
        x += direction * speed * deltaTime;
        x = MathUtils.clamp(x, 0, WORLD_WIDTH - width);
        stateTime += deltaTime;
        bounds.set(x, y, width, height);
    }

    public TextureRegion getCurrentFrame() {
        if (moving){
            return runAnimation.getKeyFrame(stateTime, true);
        } else {
            return idleAnimation.getKeyFrame(stateTime, true);
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;
        currentFrame = getCurrentFrame();
        if (isFacingRight()) {
            batch.draw(currentFrame, x, y, width, height);
        } else {
            batch.draw(currentFrame, x + width, y, -width, height);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
