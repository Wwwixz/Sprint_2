package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PointCounter {
    private BitmapFont font;
    private float x, y;

    public PointCounter(float x, float y) {
        this.x = x;
        this.y = y;
        font = new BitmapFont();
        font.getData().setScale(6); // Сделаем счет очков еще крупнее
    }

    public void draw(Batch batch, String prefix, int countOfPoints) {
        font.draw(batch, prefix + countOfPoints, x, y);
    }

    // Перегруженный метод для рисования в произвольном месте
    public void draw(Batch batch, String prefix, int countOfPoints, float customX, float customY) {
        font.draw(batch, prefix + countOfPoints, customX, customY);
    }

    public void dispose() {
        font.dispose();
    }
}
