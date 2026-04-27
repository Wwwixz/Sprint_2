package io.github.some_example_name.characters;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import static io.github.some_example_name.MyGdxGame.SCR_HEIGHT;
import static io.github.some_example_name.MyGdxGame.SCR_WIDTH;

public class Tube {
    public float x;
    public float gapY;
    public float gapHeight = 250; // Высота пролета между трубами
    public float width = 120;
    public float speed = 5;
    public float distanceBetweenTubes;
    public boolean isActive = true; // Для начисления очков

    private Texture textureUpperTube;
    private Texture textureDownTube;
    private Random random;
    private int padding = 50; // Отступ от краев экрана для пролета

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        // Рандомно выбираем высоту центра пролета
        gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + (int)(gapHeight / 2)));
        
        // Расстояние между трубами, чтобы они равномерно распределялись по экрану
        distanceBetweenTubes = (SCR_WIDTH + width * 2) / (tubeCount);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        textureUpperTube = new Texture("pictures_for_game/tube/tube_flipped.png");
        textureDownTube = new Texture("pictures_for_game/tube/tube.png");
        isActive = true;
    }

    public void update() {
        x -= speed;
        // Если труба ушла за левый край экрана — переносим её вправо и меняем высоту пролета
        if (x < -width) {
            x += distanceBetweenTubes * 3; // 3 — количество труб на экране
            gapY = gapHeight / 2 + padding + random.nextInt(SCR_HEIGHT - 2 * (padding + (int)(gapHeight / 2)));
            isActive = true; // Снова активна для начисления очков
        }
    }

    public void draw(Batch batch) {
        // Рисуем верхнюю трубу
        batch.draw(textureUpperTube, x, gapY - gapHeight / 2 - textureUpperTube.getHeight(), width, textureUpperTube.getHeight());
        // Рисуем нижнюю трубу
        batch.draw(textureDownTube, x, gapY + gapHeight / 2, width, textureDownTube.getHeight());
    }

    public boolean isHit(Bird bird) {
        // Делаем хитбокс чуть меньше самой птицы, чтобы не было обидных касаний краем спрайта
        float birdPadding = bird.width * 0.2f;
        float birdX = bird.x + birdPadding;
        float birdY = bird.y + birdPadding;
        float birdW = bird.width - birdPadding * 2;
        float birdH = bird.height - birdPadding * 2;

        // Проверка столкновения с верхней трубой
        if (birdY <= gapY - gapHeight / 2 && birdX + birdW >= x && birdX <= x + width)
            return true;
        // Проверка столкновения с нижней трубой
        if (birdY + birdH >= gapY + gapHeight / 2 && birdX + birdW >= x && birdX <= x + width)
            return true;
        
        return false;
    }

    public void dispose() {
        textureUpperTube.dispose();
        textureDownTube.dispose();
    }
}
