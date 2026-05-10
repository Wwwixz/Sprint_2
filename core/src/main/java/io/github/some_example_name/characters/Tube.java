package io.github.some_example_name.characters;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import static io.github.some_example_name.MyGdxGame.SCR_HEIGHT;
import static io.github.some_example_name.MyGdxGame.SCR_WIDTH;

public class Tube {
    public float x;
    public float gapY;
    public float gapHeight = 250; // Возвращаем исходный пролет
    public float width = 120; // Возвращаем исходную ширину
    public float speed = 5;
    public float distanceBetweenTubes;
    public boolean isActive = true;

    private Texture textureUpperTube;
    private Texture textureDownTube;
    private Random random;
    private int padding = 50; // Возвращаем исходный отступ

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        // Возвращаем исходную формулу расстояния
        distanceBetweenTubes = (SCR_WIDTH + width * 2) / (tubeCount);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        generateGap();

        textureUpperTube = new Texture("pictures_for_game/tube/tube_flipped.png");
        textureDownTube = new Texture("pictures_for_game/tube/tube.png");
        isActive = true;
    }

    private void generateGap() {
        // Улучшенная генерация пролета: всегда в центре игровой зоны
        int minGapY = (int) (padding + gapHeight / 2);
        int maxGapY = (int) (SCR_HEIGHT - padding - gapHeight / 2);
        gapY = minGapY + random.nextInt(maxGapY - minGapY + 1);
    }

    public void update() {
        x -= speed;
        // Плавный перенос трубы в конец очереди
        if (x < -width) {
            x += distanceBetweenTubes * 3; 
            generateGap();
            isActive = true;
        }
    }

    public void draw(Batch batch) {
        // Рисуем верхнюю трубу (перевернутую)
        batch.draw(textureUpperTube, x, gapY - gapHeight / 2 - textureUpperTube.getHeight(), width, textureUpperTube.getHeight());
        // Рисуем нижнюю трубу
        batch.draw(textureDownTube, x, gapY + gapHeight / 2, width, textureDownTube.getHeight());
    }

    public boolean isHit(Bird bird) {
        // Улучшенный хитбокс: более точное определение столкновения
        float birdPaddingW = bird.width * 0.15f;
        float birdPaddingH = bird.height * 0.15f;
        float bX = bird.x + birdPaddingW;
        float bY = bird.y + birdPaddingH;
        float bW = bird.width - birdPaddingW * 2;
        float bH = bird.height - birdPaddingH * 2;

        // Столкновение с верхней трубой
        if (bX < x + width && bX + bW > x && bY < gapY - gapHeight / 2) {
            return true;
        }
        // Столкновение с нижней трубой
        if (bX < x + width && bX + bW > x && bY + bH > gapY + gapHeight / 2) {
            return true;
        }
        
        return false;
    }

    public void dispose() {
        textureUpperTube.dispose();
        textureDownTube.dispose();
    }
}
