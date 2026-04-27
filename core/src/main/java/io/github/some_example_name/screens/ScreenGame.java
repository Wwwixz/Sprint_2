package io.github.some_example_name.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.some_example_name.MyGdxGame;
import io.github.some_example_name.characters.Bird;
import io.github.some_example_name.characters.Tube;
import io.github.some_example_name.components.MovingBackground;
import io.github.some_example_name.components.PointCounter;

import static io.github.some_example_name.MyGdxGame.SCR_HEIGHT;
import static io.github.some_example_name.MyGdxGame.SCR_WIDTH;

public class ScreenGame implements Screen {
    MyGdxGame myGdxGame;
    Bird bird;
    Tube[] tubes;
    int tubeCount = 3;
    MovingBackground background;
    PointCounter pointCounter;
    int gamePoints = 0;

    int pointCounterMarginRight = 100;
    int pointCounterMarginTop = 50;

    float difficultyTimer = 0;
    float currentSpeed = 5;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        initTubes();
        background = new MovingBackground("pictures_for_game/background/game_bg.png");
        bird = new Bird(100, SCR_HEIGHT / 2, 0.8f, 15, 80);
        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight, SCR_HEIGHT - pointCounterMarginTop);
    }

    void initTubes() {
        if (tubes != null) {
            for (Tube tube : tubes) {
                if (tube != null) tube.dispose();
            }
        }
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
            tubes[i].speed = currentSpeed;
        }
    }

    public void restart() {
        gamePoints = 0;
        currentSpeed = 5;
        bird.y = SCR_HEIGHT / 2;
        bird.velocity = 0;
        initTubes();
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обработка ввода
        if (Gdx.input.justTouched()) {
            bird.jump();
        }

        // Логика
        bird.update();
        background.update();

        // Постепенно усложняем жизнь игроку: каждые 5 секунд трубы ускоряются
        difficultyTimer += delta;
        if (difficultyTimer > 5f) {
            difficultyTimer = 0;
            currentSpeed += 0.5f;
            if (currentSpeed > 15) currentSpeed = 15;
            for (Tube tube : tubes) tube.speed = currentSpeed;
        }

        for (Tube tube : tubes) {
            tube.update();
            // Проверка на столкновение
            if (tube.isHit(bird)) {
                myGdxGame.saveHighScore(gamePoints);
                myGdxGame.screenRestart.setGamePoints(gamePoints);
                myGdxGame.setScreen(myGdxGame.screenRestart);
                return;
            }
            // Начисление очков за пролет трубы
            if (tube.isActive && bird.x > tube.x + tube.width) {
                gamePoints++;
                tube.isActive = false;
            }
        }

        // Отрисовка
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();
        background.draw(myGdxGame.batch);
        for (Tube tube : tubes) tube.draw(myGdxGame.batch);
        bird.draw(myGdxGame.batch);
        pointCounter.draw(myGdxGame.batch, "", gamePoints);
        myGdxGame.batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        bird.dispose();
        background.dispose();
        for (Tube tube : tubes) tube.dispose();
    }
}
