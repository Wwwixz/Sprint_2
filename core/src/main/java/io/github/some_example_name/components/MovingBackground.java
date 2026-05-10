package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import static io.github.some_example_name.MyGdxGame.SCR_WIDTH;

public class MovingBackground {
    private Texture texture;
    private float x1, x2;
    private float speed = 2;

    public MovingBackground(String path) {
        texture = new Texture(path);
        x1 = 0;
        x2 = SCR_WIDTH;
    }

    public void update() {
        x1 -= speed;
        x2 -= speed;
        if (x1 <= -SCR_WIDTH) x1 = SCR_WIDTH;
        if (x2 <= -SCR_WIDTH) x2 = SCR_WIDTH;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x1, 0, SCR_WIDTH, io.github.some_example_name.MyGdxGame.SCR_HEIGHT);
        batch.draw(texture, x2, 0, SCR_WIDTH, io.github.some_example_name.MyGdxGame.SCR_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}
