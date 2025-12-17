package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture backgroundTexture;
    private Texture bananaTexture;
    private Sound pickUpBananaSound;

    private Monkey monkey;

    private Rectangle bananaRectangle;
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    private Array<Banana> bananaSprites;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        Texture monkeyRunTexture = new Texture("monkey_run.png");
        Texture monkeyIdleTexture = new Texture("monkey_idle.png");
        bananaTexture = new Texture("banana.png");
        backgroundTexture = new Texture("background.png");

        pickUpBananaSound = Gdx.audio.newSound(Gdx.files.internal("pickup_banana.wav"));
        createMusic();

        monkey = new Monkey(monkeyRunTexture, monkeyIdleTexture);

        bananaSprites = new Array<>();
        bananaRectangle = new Rectangle();

        createBananas();
    }

    private void createMusic() {
        Music music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        //music.play();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        monkey.update(delta);

        logic();
        draw();
    }

    private void logic() {
        for (int i = bananaSprites.size - 1; i >= 0; i--) {
            Banana banana = bananaSprites.get(i);
            bananaRectangle.set(banana.getBounds());

            if (bananaRectangle.overlaps(monkey.getBounds())) {
                bananaSprites.removeIndex(i);
                pickUpBananaSound.play();
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        monkey.draw(batch);
        for (Banana banana: bananaSprites) {
            banana.draw(batch);
        }

        batch.end();
    }

    private void createBananas() {
        addBanana(7f, 3f);
        addBanana(9f, 3f);
        addBanana(11f, 5f);
    }

    private void addBanana(float x, float y) {
        bananaSprites.add(new Banana(bananaTexture, x, y));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
