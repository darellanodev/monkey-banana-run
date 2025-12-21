package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Fire {
    private final Rectangle bounds;
    private final Animation<TextureRegion> fireAnimation;
    private float stateTime = 0f;
    private float x;
    private float y;
    private final float width = 2f;
    private final float height = 2f;

    public Fire(Texture texture, float initialX, float initialY){
        fireAnimation = getAnimation(texture);
        x = initialX;
        y = initialY;
        bounds = createBounds(x, y);
    }

    private Rectangle createBounds(float x, float y) {
        return new Rectangle(x, y, width, height);
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
        stateTime += deltaTime;
        bounds.set(x, y, width, height);
    }

    public TextureRegion getCurrentFrame() {
        return fireAnimation.getKeyFrame(stateTime, true);
    }

    public void draw(SpriteBatch batch){
        batch.draw(getCurrentFrame(), x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
