package io.github.some_example_name.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Bird {
    public float x, y;
    public float width, height;
    public float velocity = 0;
    public float gravity;
    public float jumpForce;

    private Texture[] textures;
    private int currentFrame = 0;
    private float animationTimer = 0;

    public Bird(float x, float y, float gravity, float jumpForce, float size) {
        this.x = x;
        this.y = y;
        this.gravity = gravity;
        this.jumpForce = jumpForce;
        this.width = size;
        this.height = size;

        // Загружаем кадры анимации (птичка машет крыльями)
        textures = new Texture[]{
            new Texture("pictures_for_game/bird/bird1.png"),
            new Texture("pictures_for_game/bird/bird2.png"),
            new Texture("pictures_for_game/bird/bird3.png")
        };
    }

    public void update() {
        // Применяем гравитацию
        velocity -= gravity;
        y += velocity;

        // Не даем птице улететь в космос или упасть под землю
        if (y < 0) y = 0;
        if (y > 720 - height) y = 720 - height;
    }

    public void jump() {
        // Подбрасываем птицу вверх
        velocity = jumpForce;
    }

    public void draw(Batch batch) {
        // Вычисляем угол наклона птицы в зависимости от скорости падения/взлета
        float rotation = velocity * 2f;
        if (rotation > 30) rotation = 30; // Сильно вверх не задираем
        if (rotation < -90) rotation = -90; // А вот падать можем камнем вниз

        // Машем крыльями: меняем кадр каждые 0.1 секунды
        animationTimer += Gdx.graphics.getDeltaTime();
        if (animationTimer > 0.1f) {
            animationTimer = 0;
            currentFrame = (currentFrame + 1) % textures.length;
        }

        // Рисуем птичку с учетом поворота
        batch.draw(textures[currentFrame], x, y, width / 2, height / 2, width, height, 1, 1, rotation, 0, 0, textures[currentFrame].getWidth(), textures[currentFrame].getHeight(), false, false);
    }

    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
