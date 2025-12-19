package io.github.darellanodev.monkeybananarun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {
    private Texture background;
    private final float WORLD_WIDTH = 16f;
    private final float WORLD_HEIGHT = 9f;
    private boolean startGame;

    public Menu(Texture texture) {
        background = texture;
        startGame = false;
    }

    public void update(float deltaTime) {
       if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
           startGame = true;
       }
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void draw(SpriteBatch batch){
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }
}
