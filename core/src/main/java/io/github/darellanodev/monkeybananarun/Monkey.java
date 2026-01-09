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
    private final Animation<TextureRegion> burnedAnimation;
    private final float width = 2f;
    private final float height = 2f;
    private final float speed = 4f;
    private final float jumpSpeed = 1f; // must be multiple of maxJumpPosition
    private final float maxJumpPosition = 4f;

    private enum State { IDLE, RUNNING, BURNED, JUMPING, FALLING, JUMPING_RUNNING, FALLING_RUNNING }
    private State state = State.IDLE;

    private float stateTime = 0f;
    private boolean facingRight = true;
    private float x;
    private float y;
    private boolean moving;

    public Monkey() {
        runAnimation = null;
        idleAnimation = null;
        burnedAnimation = null;
        bounds = createBounds();
    }

    public Monkey(Texture runTexture, Texture idleTexture, Texture burnedTexture) {
        runAnimation = AnimationHelper.getAnimation(runTexture);
        idleAnimation = AnimationHelper.getAnimation(idleTexture);
        burnedAnimation = AnimationHelper.getAnimation(burnedTexture);
        bounds = createBounds();
    }

    private Rectangle createBounds() {
        x = 1f;
        y = 2f;
        return new Rectangle(x, y, width, height);
    }

    public void burn() {
        state = State.BURNED;
        moving = false;
    }

    public float getX() {
        return x;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void update(float deltaTime) {

        if (state == State.BURNED) {
            stateTime += deltaTime;
            return;
        }

        handleJump();

        int direction = getDirection();
        applyMovement(deltaTime, direction);

    }

    private void handleJump() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping() && !isFalling()) {
            state = State.JUMPING;
        }
    }

    private int getDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            return 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            return -1;
        }
        return 0;
    }

    public void applyMovement(float deltaTime, int direction) {
        applyHorizontalMovement(deltaTime, direction);
        applyJumpingMovement(deltaTime);
        applyFallingMovement(deltaTime);
        bounds.set(x, y, width, height);
    }

    private void applyHorizontalMovement(float deltaTime, int direction) {
        handleFacingRight(direction);
        stateTime += deltaTime;
        handleState(direction);
        x += direction * speed * deltaTime;
        x = MathUtils.clamp(x, 0, Config.WORLD_WIDTH - width);
    }

    private void applyJumpingMovement(float deltaTime) {
        if (!isJumping()) {
            return;
        }
        if (y >= maxJumpPosition) {
            state = State.FALLING;
            return;
        }
        y += jumpSpeed * speed * deltaTime;
    }

    private boolean isJumping() {
        return state == State.JUMPING || state == State.JUMPING_RUNNING;
    }

    private boolean isFalling() {
        return state == State.FALLING || state == State.FALLING_RUNNING;
    }

    private void applyFallingMovement(float deltaTime) {
        if (!isFalling()) {
            return;
        }
        if (y <= 2f) {
            state = State.IDLE;
            return;
        }
        y -= jumpSpeed * speed * deltaTime;
    }

    private void handleState(int direction) {
        handleStateMovement(direction);
        handleStateIdle();
    }

    private void handleStateIdle() {
        if (!isJumping() && !isFalling()) {
            state = State.IDLE;
        }
    }

    private void handleStateMovement(int direction) {
        if (direction != 0) {
            handleStateJumpingRunning();
            handleStateFallingRunning();
            handleStateRunning();
        }
    }

    private void handleStateJumpingRunning() {
        if (isJumping()) {
            state = State.JUMPING_RUNNING;
        }
    }

    private void handleStateFallingRunning() {
        if (isFalling()) {
            state = State.FALLING_RUNNING;
        }
    }

    private void handleStateRunning() {
       if (!isFalling() && !isJumping()) {
           state = State.RUNNING;
       }
    }

    private void handleFacingRight(int direction) {
        moving = direction != 0;
        if (direction != 0) {
            facingRight = direction > 0;
        }
    }

    public TextureRegion getCurrentFrame() {
        switch (state) {
            case BURNED:
                return burnedAnimation.getKeyFrame(stateTime, true);
            case RUNNING:
            case FALLING_RUNNING:
            case JUMPING_RUNNING:
                return runAnimation.getKeyFrame(stateTime, true);
            case IDLE:
            default:
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
