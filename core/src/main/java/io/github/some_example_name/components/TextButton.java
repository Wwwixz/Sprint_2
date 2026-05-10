package io.github.some_example_name.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextButton {
    private Texture texture;
    private BitmapFont font;
    private String text;
    private float x, y, width, height;
    private GlyphLayout layout;

    public TextButton(String text, float centerX, float centerY) {
        this.text = text;
        texture = new Texture("pictures_for_game/button/button_bg.png");
        font = new BitmapFont();
        font.getData().setScale(5); // Еще больше увеличиваем масштаб текста
        
        layout = new GlyphLayout(font, text);
        this.width = layout.width + 200; // Еще больше увеличиваем ширину кнопки
        this.height = layout.height + 120; // Еще больше увеличиваем высоту кнопки
        this.x = centerX - width / 2;
        this.y = centerY - height / 2;
    }

    public void draw(Batch batch) {
        batch.draw(texture, x, y, width, height);
        font.draw(batch, text, x + (width - layout.width) / 2, y + (height + layout.height) / 2);
    }

    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
    }

    public void dispose() {
        texture.dispose();
        font.dispose();
    }
}
