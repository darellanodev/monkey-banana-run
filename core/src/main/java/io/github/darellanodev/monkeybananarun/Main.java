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
    private Rectangle bananaRectangle;
    private Array<Banana> bananaSprites;

    private Monkey monkey;
    private Texture monkeyRunTexture;
    private Texture monkeyIdleTexture;
    private Texture monkeyBurnedTexture;

    private Texture fireTexture;
    private Fire fire;

    private Menu menu;
    private Texture menuTexture;
    private boolean shouldDisplayMenu;

    @Override
    public void create() {
        shouldDisplayMenu = true;
        batch = new SpriteBatch();
        viewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);

        createTextures();
        createSounds();
        createMusic();

        menu = new Menu(menuTexture);
        monkey = new Monkey(monkeyRunTexture, monkeyIdleTexture, monkeyBurnedTexture);
        fire = new Fire(fireTexture, 11f, 2f);
        bananaSprites = new Array<>();
        bananaRectangle = new Rectangle();
        createBananas();
    }

    private void createSounds() {
        pickUpBananaSound = Gdx.audio.newSound(Gdx.files.internal("pickup_banana.wav"));
    }

    private void createTextures() {
        monkeyRunTexture = new Texture("monkey_run.png");
        monkeyIdleTexture = new Texture("monkey_idle.png");
        monkeyBurnedTexture = new Texture("monkey_burned.png");
        bananaTexture = new Texture("banana.png");
        backgroundTexture = new Texture("background.png");
        menuTexture = new Texture("menu.png");
        fireTexture = new Texture("fire.png");
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
        fire.update(delta);
        menu.update(delta);

        logic();
        draw();
    }

    private void logic() {
        menuLogic();
        fireLogic();
        bananasLogic();
    }

    private void menuLogic() {
        if (shouldDisplayMenu && menu.isStartGame()) {
            shouldDisplayMenu = false;
        }
    }

    private void fireLogic() {
        if (shouldDisplayMenu) {
            return;
        }

        if(monkey.getBounds().overlaps(fire.getBounds())){
            monkey.burn();
        }
    }

    private void bananasLogic() {
        if (shouldDisplayMenu) {
            return;
        }
        for (int i = bananaSprites.size - 1; i >= 0; i--) {
            checkIfMonkeyCollidesWithBanana(i);
        }
    }

    private void checkIfMonkeyCollidesWithBanana(int i) {
        Banana banana = bananaSprites.get(i);
        bananaRectangle.set(banana.getBounds());
        if (bananaRectangle.overlaps(monkey.getBounds())) {
            bananaSprites.removeIndex(i);
            pickUpBananaSound.play();
        }
    }

    private void draw() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        drawAll();
        batch.end();
    }

    private void drawAll() {
        drawMenu();
        drawGame();
    }

    private void drawMenu() {
        if (shouldDisplayMenu) {
            menu.draw(batch);
        }
    }

    private void drawGame() {
        if (shouldDisplayMenu) {
            return;
        }
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        monkey.draw(batch);
        fire.draw(batch);
        for (Banana banana: bananaSprites) {
            banana.draw(batch);
        }
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
        disposeTextures();
        disposeSounds();
    }

    private void disposeTextures() {
        monkeyRunTexture.dispose();
        monkeyIdleTexture.dispose();
        monkeyBurnedTexture.dispose();
        bananaTexture.dispose();
        backgroundTexture.dispose();
        menuTexture.dispose();
    }

    private void disposeSounds() {
        pickUpBananaSound.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
