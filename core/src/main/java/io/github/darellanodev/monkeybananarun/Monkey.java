package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Monkey {
    private final Animation<TextureRegion> runAnimation;
    private float stateTime;

    private float x;
    private float y;

    private final float width = 2f;
    private final float height = 2f;

    private final Rectangle bounds;

    private final float speed = 4f;
    private final float WORLD_WIDTH = 16f;

    public Monkey(Texture texture) {

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

        runAnimation = new Animation<>(0.1f, frames);

        stateTime = 0f;
        x = 1f;
        y = 2f;
        bounds = new Rectangle(x, y, width, height);


    }

    public void update(float deltaTime) {
        float speed = 4f;
        boolean moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * deltaTime;
            moving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * deltaTime;
            moving = true;
        }

        x = MathUtils.clamp(x, 0, WORLD_WIDTH - width);

        if (moving) {
            stateTime += deltaTime;
        }

        bounds.set(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = runAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
