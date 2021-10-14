package ru.mipt.bit.platformer.launcher;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.controllers.PlayerController;
import ru.mipt.bit.platformer.controllers.PlayerControllerImpl;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.graphic.LogicObjectsWrapper;
import ru.mipt.bit.platformer.graphic.LibGdxGraphicObjectRender;
import ru.mipt.bit.platformer.graphic.LibGdxLevelRender;
import ru.mipt.bit.platformer.graphic.object.placement.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.graphic.object.placement.LogicObjectPositionsGenerator;
import ru.mipt.bit.platformer.graphic.object.placement.impl.LibGdxGameFieldAndTextureParams;
import ru.mipt.bit.platformer.graphic.object.placement.impl.LogicObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.keyboard.LibGdxKeyboardChecker;
import ru.mipt.bit.platformer.movables.Tank;
import ru.mipt.bit.platformer.movement.LibGdxMovementService;
import ru.mipt.bit.platformer.movement.LibGdxMovementServiceImpl;
import ru.mipt.bit.platformer.service.ActionMapper;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;
import ru.mipt.bit.platformer.service.impl.ActionMapperImpl;
import ru.mipt.bit.platformer.service.impl.CollisionDetectionManagerImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static ru.mipt.bit.platformer.util.GdxGameUtils.DEFAULT_KEY_MAPPING;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private LevelRender levelRender;
    private PlayerController playerController;

    @Override
    public void create() {
        Batch batch = new SpriteBatch();
        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer tileLayer = getSingleLayer(level);

        levelRender = new LibGdxLevelRender(batch, levelRenderer);
        LibGdxMovementService movementService = new LibGdxMovementServiceImpl(Interpolation.smooth);
        CollisionDetectionManager collisionDetectionManager = new CollisionDetectionManagerImpl();

        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");

        System.out.println("Texture height = " + greenTreeTexture.getHeight());
        System.out.println("Texture width = " + greenTreeTexture.getWidth());

        GameFieldAndTextureParams params = new LibGdxGameFieldAndTextureParams(tileLayer, greenTreeTexture);

        LogicObjectPositionsGenerator logicObjectPositionsGenerator = new LogicObjectPositionsFileGenerator(
                "src/main/resources/location/graphic_objects_location.txt",
                params
        );

        LogicObjectsWrapper wrapper = logicObjectPositionsGenerator.generateGraphicObjects();

        List<Tank> tanks = wrapper.getTanks().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, blueTankTexture, logicObject))
                .map(graphicObject -> {
                    levelRender.addRenderer(new LibGdxGraphicObjectRender(graphicObject, batch));
                    collisionDetectionManager.addColliding(graphicObject);
                    return new Tank(MOVEMENT_SPEED, movementService, graphicObject);
                })
                .collect(Collectors.toList());

        wrapper.getTrees().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, greenTreeTexture, logicObject))
                .forEach(tree -> {
                    levelRender.addRenderer(new LibGdxGraphicObjectRender(tree, batch));
                    collisionDetectionManager.addColliding(tree);
                });

        ActionMapper actionMapper = new ActionMapperImpl(DEFAULT_KEY_MAPPING);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);

        if (tanks.size() == 0) {
            throw new RuntimeException("Number of tanks is zero");
        }
        playerController = new PlayerControllerImpl(actionMapper, keyboardChecker, tanks.get(0), collisionDetectionManager);

    }

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        playerController.movePlayer(deltaTime);

        levelRender.renderAll();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        levelRender.dispose();
    }

    private void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
