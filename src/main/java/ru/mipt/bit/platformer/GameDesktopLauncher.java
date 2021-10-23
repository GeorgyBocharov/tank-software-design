package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.impl.CommandSchedulerAndExecutor;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.generators.impl.BotCommandsGenerator;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;
import ru.mipt.bit.platformer.commands.generators.impl.PlayerCommandsGenerator;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.objects.placement.LogicObjectsWrapper;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObjectRender;
import ru.mipt.bit.platformer.graphic.impl.LibGdxLevelRender;
import ru.mipt.bit.platformer.objects.placement.LogicObjectPositionsGenerator;
import ru.mipt.bit.platformer.objects.placement.impl.LibGdxGameFieldAndTextureParams;
import ru.mipt.bit.platformer.objects.placement.impl.LogicObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.keyboard.impl.LibGdxKeyboardChecker;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;
import ru.mipt.bit.platformer.movement.service.impl.LibGdxMovementServiceImpl;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;
import ru.mipt.bit.platformer.commands.mappers.CommandMapperImpl;
import ru.mipt.bit.platformer.collision.impl.CollisionDetectionManagerImpl;
import ru.mipt.bit.platformer.processor.GameObjectsActivator;
import ru.mipt.bit.platformer.processor.impl.GameObjectsActivatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
    public static final int PLAYER_INDEX = 0;
    public static final int BOTS_START_INDEX = 1;
    public static final String GREEN_TREE_PNG = "images/greenTree.png";
    public static final String TANK_BLUE_PNG = "images/tank_blue.png";

    private final Interpolation interpolation = Interpolation.smooth;

    private LevelRender levelRender;
    private CommandsGenerator tankController;
    private CommandsGenerator botController;
    private CommandExecutor commandExecutor;
    private Texture blueTankTexture;
    private Texture greenTreeTexture;
    private GameObjectsActivator gameObjectsActivator;

    @Override
    public void create() {
        Batch batch = new SpriteBatch();
        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer tileLayer = getSingleLayer(level);
        loadTextures();

        LibGdxLevelRender libGdxLevelRender = new LibGdxLevelRender(batch, levelRenderer);
        CollisionDetectionManagerImpl collisionManagerImpl = new CollisionDetectionManagerImpl(
                tileLayer.getWidth(),
                tileLayer.getHeight()
        );
        GameObjectsActivatorImpl activatorImpl = new GameObjectsActivatorImpl();

        LogicObjectPositionsGenerator logicObjectPositionsGenerator = new LogicObjectPositionsFileGenerator(
                "src/main/resources/location/graphic_objects_location.txt",
                new LibGdxGameFieldAndTextureParams(tileLayer, greenTreeTexture)
        );

        LogicObjectsWrapper wrapper = logicObjectPositionsGenerator.generateGraphicObjects();

        List<LibGdxGraphicObject> trees = createTrees(tileLayer, greenTreeTexture, wrapper);
        List<Tank> tanks = createTanks(tileLayer, blueTankTexture, wrapper);

        addObjectsToActivatedService(tanks, activatorImpl);
        addObjectsToActivatedService(trees, activatorImpl);
        addObjectsToCollisionDetectionManager(tanks, collisionManagerImpl);
        addObjectsToCollisionDetectionManager(trees, collisionManagerImpl);
        addObjectsToLevelRenderer(trees, batch, libGdxLevelRender);
        addObjectsToLevelRenderer(tanks.stream().map(Tank::getGraphicObject).collect(Collectors.toList()), batch, libGdxLevelRender);

        gameObjectsActivator = activatorImpl;
        levelRender = libGdxLevelRender;

        initCommandExecutorAndControllers(collisionManagerImpl, tanks);

    }

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        tankController.generate(deltaTime);
        botController.generate(deltaTime);
        commandExecutor.executeAll();

        gameObjectsActivator.activateAll(deltaTime);

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

    private List<LibGdxGraphicObject> createTrees(TiledMapTileLayer tileLayer, Texture greenTreeTexture, LogicObjectsWrapper wrapper) {
        return wrapper.getTrees().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, greenTreeTexture, logicObject))
                .collect(Collectors.toList());
    }

    private List<Tank> createTanks(TiledMapTileLayer tileLayer, Texture blueTankTexture, LogicObjectsWrapper wrapper) {
        LibGdxMovementService movementService = new LibGdxMovementServiceImpl(interpolation);

        return wrapper.getTanks().stream()
                .map(logicObject -> new LibGdxGraphicObject(tileLayer, blueTankTexture, logicObject))
                .map(graphicObject -> new Tank(MOVEMENT_SPEED, movementService, graphicObject))
                .collect(Collectors.toList());
    }

    private void initCommandExecutorAndControllers(CollisionDetectionManager collisionDetectionManager, List<Tank> tanks) {
        CommandSchedulerAndExecutor schedulerAndExecutor = new CommandSchedulerAndExecutor();
        commandExecutor = schedulerAndExecutor;

        Tank player = getPlayer(tanks);
        CommandMapper commandMapper = createCommandMapper(player, collisionDetectionManager);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);

        tankController = new PlayerCommandsGenerator(commandMapper, keyboardChecker, schedulerAndExecutor);
        botController = new BotCommandsGenerator(getBots(tanks), collisionDetectionManager, schedulerAndExecutor);
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

    private void addObjectsToActivatedService(List<? extends Activated> activatedList, GameObjectsActivatorImpl activator) {
        activatedList.forEach(activator::addActivated);
    }

    private void addObjectsToLevelRenderer(List<LibGdxGraphicObject> graphicObjects, Batch batch, LibGdxLevelRender libGdxLevelRender) {
        graphicObjects.forEach(graphicObject -> libGdxLevelRender.addRenderer(new LibGdxGraphicObjectRender(graphicObject, batch)));
    }

    private void addObjectsToCollisionDetectionManager(List<? extends Colliding> collidingList, CollisionDetectionManagerImpl collisionDetectionManager) {
        collidingList.forEach(collisionDetectionManager::addColliding);
    }

    private CommandMapper createCommandMapper(Tank player, CollisionDetectionManager collisionDetectionManager) {
        CommandMapper commandMapper = new CommandMapperImpl();
        Command moveUpCommand = new MovementCommand(collisionDetectionManager, player, Direction.UP);
        Command moveLeftCommand = new MovementCommand(collisionDetectionManager, player, Direction.LEFT);
        Command moveRightCommand = new MovementCommand(collisionDetectionManager, player, Direction.RIGHT);
        Command moveDownCommand = new MovementCommand(collisionDetectionManager, player, Direction.DOWN);

        commandMapper.addCommandMapping(Set.of(Input.Keys.W), moveUpCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.UP), moveUpCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.S), moveDownCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.DOWN), moveDownCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.A), moveLeftCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.LEFT), moveLeftCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.D), moveRightCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.RIGHT), moveRightCommand);

        return commandMapper;
    }
}
