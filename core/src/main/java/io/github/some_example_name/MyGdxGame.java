package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.screens.ScreenGame;
import io.github.some_example_name.screens.ScreenMenu;
import io.github.some_example_name.screens.ScreenRestart;

public class MyGdxGame extends Game {
    public static final int SCR_WIDTH = 1280;
    public static final int SCR_HEIGHT = 720;

    public SpriteBatch batch;
    public OrthographicCamera camera;

    public ScreenGame screenGame;
    public ScreenMenu screenMenu;
    public ScreenRestart screenRestart;

    public int highScore;
    private Preferences prefs;

    @Override
    public void create() {
        // Загружаем настройки: рекорды и прочее
        prefs = Gdx.app.getPreferences("FlappyBirdSettings");
        highScore = prefs.getInteger("highScore", 0);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);

        screenGame = new ScreenGame(this);
        screenMenu = new ScreenMenu(this);
        screenRestart = new ScreenRestart(this);

        // Начинаем с главного меню
        setScreen(screenMenu);
    }

    public void saveHighScore(int score) {
        // Если текущий результат лучше рекорда — сохраняем его на диск
        if (score > highScore) {
            highScore = score;
            prefs.putInteger("highScore", highScore);
            prefs.flush(); // Важно вызвать flush, чтобы данные реально записались
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
