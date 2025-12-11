package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture image;
    private Texture background;
    private Music music;
    private Texture monkeyTexture;
    private Sprite monkeySprite;
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;


    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        image = new Texture("libgdx.png");
        monkeyTexture = new Texture("monkey.png");
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        monkeySprite = new Sprite(monkeyTexture);
        monkeySprite.setSize(2,2);
        monkeySprite.setPosition(1f,2f);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        monkeySprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
