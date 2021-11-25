package ru.mipt.bit.platformer.level.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.decorator.HealthBarViewDecorator;
import ru.mipt.bit.platformer.objects.AgileObject;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.graphic.Disposable;
import ru.mipt.bit.platformer.objects.graphic.impl.LibGdxGraphicHealthBar;
import ru.mipt.bit.platformer.objects.graphic.impl.LibGdxGraphicObstacle;
import ru.mipt.bit.platformer.objects.graphic.impl.LibGdxAgileGraphicObject;
import ru.mipt.bit.platformer.level.GraphicLevel;
import ru.mipt.bit.platformer.objects.logic.LogicHealthBar;
import ru.mipt.bit.platformer.objects.logic.LogicProjectile;
import ru.mipt.bit.platformer.objects.logic.LogicTank;
import ru.mipt.bit.platformer.objects.support.LibGdxMovementService;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class LibGdxGraphicLevel implements GraphicLevel {

    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer tileLayer;
    private final LibGdxMovementService movementService;

    private final Texture greenTreeTexture;
    private final Texture blueTankTexture;
    private final Texture projectileTexture;

    private final List<GraphicObjectRenderer> renderers = new ArrayList<>();

    private boolean printHealthBars = false;


    @Override
    public void renderAll() {
        levelRenderer.render();
        batch.begin();
        renderers.forEach(renderer -> renderer.render(batch));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderers.forEach(Disposable::dispose);
    }

    @Override
    public void registerGameObjects(List<? extends GameObject> newGameObjects) {
        newGameObjects.stream().map(this::convertToRenderer).forEach(renderers::add);
    }

    @Override
    public void unregisterGameObjects(List<? extends GameObject> deletedGameObjects) {
        List<GraphicObjectRenderer> renderersToDelete =
        deletedGameObjects.stream()
                .map(
                        gameObject -> renderers.stream()
                                .filter(renderer -> renderer.hasGameObject(gameObject))
                                .findAny()
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        renderers.removeAll(renderersToDelete);
    }

    private GraphicObjectRenderer convertToRenderer(GameObject gameObject) {
        if (gameObject instanceof LogicTank) {
            LibGdxAgileGraphicObject libGdxAgileGraphicObject = new LibGdxAgileGraphicObject(
                    (AgileObject) gameObject,
                    movementService,
                    tileLayer,
                    blueTankTexture
            );
            LibGdxGraphicHealthBar libGdxGraphicHealthBar = new LibGdxGraphicHealthBar(new LogicHealthBar(), blueTankTexture);
            return new HealthBarViewDecorator(libGdxAgileGraphicObject, libGdxGraphicHealthBar, this);
        } else if (gameObject instanceof LogicProjectile) {
            return new LibGdxAgileGraphicObject((AgileObject) gameObject, movementService, tileLayer, projectileTexture);
        } else if (gameObject instanceof LogicObstacle) {
            return new LibGdxGraphicObstacle((LogicObstacle) gameObject, tileLayer, greenTreeTexture);
        }
        throw new RuntimeException(
                String.format("Unknown type of %s: %s",
                        GameObject.class, gameObject.getClass().getSuperclass())
        );
    }

    public boolean printHealthBars() {
        return printHealthBars;
    }

    public void setPrintHealthBars(boolean printHealthBars) {
        this.printHealthBars = printHealthBars;
    }
}
