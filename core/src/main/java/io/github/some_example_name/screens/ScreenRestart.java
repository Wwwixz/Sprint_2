package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.some_example_name.MyGdxGame;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;
import io.github.some_example_name.components.TextButton;

import com.badlogic.gdx.math.Vector3;

public class ScreenRestart implements Screen {
    MyGdxGame myGdxGame;
    MovingBackground background;
    TextButton restartButton;
    TextButton menuButton;
    PointCounter pointCounter;
    int gamePoints;
    Vector3 touchPoint;

    public ScreenRestart(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new MovingBackground("pictures_for_game/background/game_bg.png");
        restartButton = new TextButton("Restart", 250, MyGdxGame.SCR_HEIGHT / 2 + 100);
        menuButton = new TextButton("Menu", 250, MyGdxGame.SCR_HEIGHT / 2 - 100);
        pointCounter = new PointCounter(0, 0);
        touchPoint = new Vector3();
    }

    public void setGamePoints(int points) {
        this.gamePoints = points;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(touchPoint);

            if (restartButton.isClicked(touchPoint.x, touchPoint.y)) {
                myGdxGame.screenGame.restart();
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
            if (menuButton.isClicked(touchPoint.x, touchPoint.y)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            }
        }

        background.update();

        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        restartButton.draw(myGdxGame.batch);
        menuButton.draw(myGdxGame.batch);
        
        // Отрисовка очков справа по центру
        pointCounter.draw(myGdxGame.batch, "Score: ", gamePoints, 750, 450);
        pointCounter.draw(myGdxGame.batch, "Best: ", myGdxGame.highScore, 750, 350);
        
        myGdxGame.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        background.dispose();
        restartButton.dispose();
        menuButton.dispose();
    }
}
