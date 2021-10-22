package ru.mipt.bit.platformer.graphic.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.GraphicObjectRenderer;

@AllArgsConstructor
@Setter
public class LibGdxGraphicObjectRender implements GraphicObjectRenderer {

    private LibGdxGraphicObject graphicObject;
    private Batch batch;

    @Override
    public void render() {
        TextureRegion textureRegion = graphicObject.getTextureRegion();
        Rectangle rectangle = graphicObject.getRectangle();
        float rotation = graphicObject.getLogicObject().getRotation();

        int regionWidth = textureRegion.getRegionWidth();
        int regionHeight = textureRegion.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(
                textureRegion, rectangle.x, rectangle.y, regionOriginX, regionOriginY,
                regionWidth, regionHeight, 1f, 1f, rotation
        );
    }

    @Override
    public void dispose() {
        graphicObject.dispose();
    }
}
