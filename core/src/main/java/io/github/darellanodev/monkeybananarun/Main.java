package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture image;
    private Texture background;
    private Texture bananaTexture;
    private Music music;
    private Texture monkeyTexture;
    private Sprite monkeySprite;
    public static final float WORLD_WIDTH = 16f;
    public static final float WORLD_HEIGHT = 9f;
    private Array<Sprite> bananaSprites;


    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        image = new Texture("libgdx.png");
        monkeyTexture = new Texture("monkey.png");
        bananaTexture = new Texture("banana.png");
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        monkeySprite = new Sprite(monkeyTexture);
        monkeySprite.setSize(2,2);
        monkeySprite.setPosition(1f,2f);

        bananaSprites = new Array<>();

        createBanana();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void logic() {
        monkeySprite.setX(MathUtils.clamp(monkeySprite.getX(), 0, viewport.getWorldWidth() - monkeySprite.getWidth()));
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        monkeySprite.draw(batch);
        for (Sprite bananaSprite: bananaSprites) {
            bananaSprite.draw(batch);
        }

        batch.end();
    }

    private void createBanana() {
        Sprite bananaSprite = new Sprite(bananaTexture);
        bananaSprite.setSize(1f,1f);
        bananaSprite.setX(4f);
        bananaSprite.setY(5f);
        bananaSprites.add(bananaSprite);
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            monkeySprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            monkeySprite.translateX(-speed * delta);
        }

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
