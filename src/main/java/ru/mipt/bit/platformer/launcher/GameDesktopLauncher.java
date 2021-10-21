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

import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.impl.CommandSchedulerAndExecutor;
import ru.mipt.bit.platformer.controllers.BotController;
import ru.mipt.bit.platformer.controllers.TankController;
import ru.mipt.bit.platformer.controllers.PlayerController;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.graphic.LogicObjectsWrapper;
import ru.mipt.bit.platformer.graphic.LibGdxGraphicObjectRender;
import ru.mipt.bit.platformer.graphic.LibGdxLevelRender;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static ru.mipt.bit.platformer.util.GdxGameUtils.DEFAULT_KEY_MAPPING;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    public static final int PLAYER_INDEX = 0;
    public static final int BOTS_START_INDEX = 1;
    public static final String GREEN_TREE_PNG = "images/greenTree.png";
    public static final String TANK_BLUE_PNG = "images/tank_blue.png";

    private final Interpolation interpolation = Interpolation.smooth;

    private LevelRender levelRender;
    private TankController tankController;
    private TankController botController;
    private CommandExecutor commandExecutor;
    private Texture blueTankTexture;
    private Texture greenTreeTexture;

    @Override
    public void create() {
        Batch batch = new SpriteBatch();
        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer tileLayer = getSingleLayer(level);
        loadTextures();

        levelRender = new LibGdxLevelRender(batch, levelRenderer);
        CollisionDetectionManager collisionDetectionManager = new CollisionDetectionManagerImpl(
                tileLayer.getWidth(),
                tileLayer.getHeight()
        );

        LogicObjectPositionsGenerator logicObjectPositionsGenerator = new LogicObjectPositionsFileGenerator(
                "src/main/resources/location/graphic_objects_location.txt",
                new LibGdxGameFieldAndTextureParams(tileLayer, greenTreeTexture)
        );

        LogicObjectsWrapper wrapper = logicObjectPositionsGenerator.generateGraphicObjects();

        createTrees(batch, tileLayer, collisionDetectionManager, greenTreeTexture, wrapper);
        List<Tank> tanks = createTanks(batch, tileLayer, collisionDetectionManager, blueTankTexture, wrapper);

        initCommandExecutorAndControllers(collisionDetectionManager, tanks);

    }

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        tankController.movePlayer(deltaTime);
        botController.movePlayer(deltaTime);
        commandExecutor.executeAll();

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

    private void loadTextures() {
        greenTreeTexture = new Texture(GREEN_TREE_PNG);
        blueTankTexture = new Texture(TANK_BLUE_PNG);
    }

    private void createTrees(Batch batch, TiledMapTileLayer tileLayer, CollisionDetectionManager collisionDetectionManager, Texture greenTreeTexture, LogicObjectsWrapper wrapper) {
        wrapper.getTrees().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, greenTreeTexture, logicObject))
                .forEach(tree -> {
                    levelRender.addRenderer(new LibGdxGraphicObjectRender(tree, batch));
                    collisionDetectionManager.addColliding(tree);
                });
    }

    private List<Tank> createTanks(Batch batch, TiledMapTileLayer tileLayer, CollisionDetectionManager collisionDetectionManager, Texture blueTankTexture, LogicObjectsWrapper wrapper) {
        LibGdxMovementService movementService = new LibGdxMovementServiceImpl(interpolation);

        return wrapper.getTanks().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, blueTankTexture, logicObject))
                .map(graphicObject -> {
                    levelRender.addRenderer(new LibGdxGraphicObjectRender(graphicObject, batch));
                    Tank tank = new Tank(MOVEMENT_SPEED, movementService, graphicObject);
                    collisionDetectionManager.addColliding(tank);
                    return tank;
                })
                .collect(Collectors.toList());
    }

    private void initCommandExecutorAndControllers(CollisionDetectionManager collisionDetectionManager, List<Tank> tanks) {
        CommandSchedulerAndExecutor schedulerAndExecutor = new CommandSchedulerAndExecutor();
        commandExecutor = schedulerAndExecutor;

        ActionMapper actionMapper = new ActionMapperImpl(DEFAULT_KEY_MAPPING);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);
        tankController = new PlayerController(actionMapper, keyboardChecker, getPlayer(tanks), collisionDetectionManager, schedulerAndExecutor);
        botController = new BotController(getBots(tanks), collisionDetectionManager, schedulerAndExecutor);
    }

    private Tank getPlayer(List<Tank> tanks) {
        if (tanks.size() == PLAYER_INDEX) {
            throw new RuntimeException("At lease one tank must exist to present player");
        }
        return tanks.get(PLAYER_INDEX);
    }

    private List<Tank> getBots(List<Tank> tanks) {
        if (tanks.size() <= BOTS_START_INDEX) {
            return Collections.emptyList();
        }
        return tanks.subList(BOTS_START_INDEX, tanks.size());
    }
}
