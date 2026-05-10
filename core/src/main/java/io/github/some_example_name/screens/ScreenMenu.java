package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.some_example_name.MyGdxGame;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.TextButton;

import com.badlogic.gdx.math.Vector3;

public class ScreenMenu implements Screen {
    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton startButton;
    TextButton exitButton;
    Vector3 touchPoint;

    public ScreenMenu(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new MovingBackground("pictures_for_game/background/game_bg.png");
        startButton = new TextButton("Start", MyGdxGame.SCR_WIDTH / 4 + 100, MyGdxGame.SCR_HEIGHT / 2 + 100);
        exitButton = new TextButton("Exit", MyGdxGame.SCR_WIDTH * 3 / 4 - 100, MyGdxGame.SCR_HEIGHT / 2 + 100);
        touchPoint = new Vector3();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(touchPoint);
            
            if (startButton.isClicked(touchPoint.x, touchPoint.y)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
            if (exitButton.isClicked(touchPoint.x, touchPoint.y)) {
                Gdx.app.exit();
            }
        }

        background.update();

        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        startButton.draw(myGdxGame.batch);
        exitButton.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        background.dispose();
        startButton.dispose();
        exitButton.dispose();
    }
}
