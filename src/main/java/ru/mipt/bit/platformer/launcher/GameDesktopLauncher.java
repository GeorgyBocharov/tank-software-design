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
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.controllers.PlayerController;
import ru.mipt.bit.platformer.controllers.PlayerControllerImpl;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.graphic.LibGdxGraphicObjectRender;
import ru.mipt.bit.platformer.graphic.LibGdxLevelRender;
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

        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");

        Tank tank = new Tank(MOVEMENT_SPEED, tileLayer, movementService, blueTankTexture, new GridPoint2(1, 1), 0f);
        LibGdxGraphicObject tree = new LibGdxGraphicObject(tileLayer, greenTreeTexture, new GridPoint2(1, 3), 0f);


        levelRender.addRenderer(new LibGdxGraphicObjectRender(tree, batch));
        levelRender.addRenderer(new LibGdxGraphicObjectRender(tank.getGraphicObject(), batch));


        ActionMapper actionMapper = new ActionMapperImpl(DEFAULT_KEY_MAPPING);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);

        CollisionDetectionManager collisionDetectionManager = new CollisionDetectionManagerImpl(List.of(tank, tree));

        playerController = new PlayerControllerImpl(actionMapper, keyboardChecker, tank, collisionDetectionManager);

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
