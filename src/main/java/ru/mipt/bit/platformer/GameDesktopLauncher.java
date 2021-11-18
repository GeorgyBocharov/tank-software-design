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

import org.awesome.ai.AI;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.impl.BotAICommandsAdapter;
import ru.mipt.bit.platformer.ai.converter.ObstacleConverter;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.ai.converter.TankToBotConverter;
import ru.mipt.bit.platformer.ai.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.ai.converter.impl.ObstacleConverterImpl;
import ru.mipt.bit.platformer.ai.converter.impl.OrientationConverterImpl;
import ru.mipt.bit.platformer.ai.converter.impl.RecommendationToCommandConverterImpl;
import ru.mipt.bit.platformer.ai.converter.impl.TankToBotConverterImpl;
import ru.mipt.bit.platformer.ai.converter.impl.TankToPlayerConverterImpl;
import ru.mipt.bit.platformer.ai.creator.GameStateCreator;
import ru.mipt.bit.platformer.ai.creator.impl.GameStateCreatorImpl;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.impl.CommandSchedulerAndExecutor;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;
import ru.mipt.bit.platformer.commands.generators.impl.PlayerCommandsGenerator;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.objects.CollidingObject;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObject;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.placement.TreesAndTanksPositionContainer;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObjectRender;
import ru.mipt.bit.platformer.graphic.impl.LibGdxLevelRenderer;
import ru.mipt.bit.platformer.objects.placement.GameObjectPositionsGenerator;
import ru.mipt.bit.platformer.objects.placement.impl.LibGdxGameFieldAndTextureParams;
import ru.mipt.bit.platformer.objects.placement.impl.GameObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.keyboard.impl.LibGdxKeyboardChecker;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;
import ru.mipt.bit.platformer.movement.service.impl.LibGdxMovementServiceImpl;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;
import ru.mipt.bit.platformer.commands.mappers.CommandMapperImpl;
import ru.mipt.bit.platformer.collision.impl.CollisionDetectionManagerImpl;
import ru.mipt.bit.platformer.processor.GameObjectsActivator;
import ru.mipt.bit.platformer.processor.impl.GameObjectsActivatorImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;
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
    private Tank player;

    @Override
    public void create() {
        Batch batch = new SpriteBatch();
        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer tileLayer = getSingleLayer(level);
        loadTextures();

        GameObjectPositionsGenerator gameObjectPositionsGenerator = new GameObjectPositionsFileGenerator(
                "src/main/resources/location/graphic_objects_location.txt",
                new LibGdxGameFieldAndTextureParams(tileLayer, greenTreeTexture)
        );

        TreesAndTanksPositionContainer wrapper = gameObjectPositionsGenerator.generateTreesAndTanksPositions();

        List<Obstacle> trees = createTrees(tileLayer, greenTreeTexture, wrapper);
        List<Tank> tanks = createTanks(tileLayer, blueTankTexture, wrapper);

        gameObjectsActivator = createGameObjectActivator(tanks, trees);
        levelRender = createLibGdxLevelRenderer(tanks, trees, batch, levelRenderer);
        CollisionDetectionManager collisionManager = createCollisionDetectionManager(
                tanks,
                trees,
                tileLayer.getWidth(),
                tileLayer.getHeight()
        );

        initCommandExecutorAndControllers(collisionManager, tanks, trees);

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

    private List<Obstacle> createTrees(TiledMapTileLayer tileLayer, Texture greenTreeTexture, TreesAndTanksPositionContainer wrapper) {
        return wrapper.getTrees().stream()
                .map(collidingObject -> getObstacle(tileLayer, greenTreeTexture, collidingObject))
                .collect(Collectors.toList());
    }

    private Obstacle getObstacle(TiledMapTileLayer tileLayer, Texture greenTreeTexture, CollidingObject collidingObject) {
        LibGdxGraphicObject graphicObject = new LibGdxGraphicObject(
                tileLayer,
                greenTreeTexture,
                collidingObject
        );
        return new Obstacle(collidingObject, graphicObject);
    }


    private List<Tank> createTanks(TiledMapTileLayer tileLayer, Texture blueTankTexture, TreesAndTanksPositionContainer wrapper) {
        LibGdxMovementService movementService = new LibGdxMovementServiceImpl(interpolation);

        return wrapper.getTanks().stream()
                .map(collidingObject -> getTank(tileLayer, blueTankTexture, movementService, collidingObject))
                .collect(Collectors.toList());
    }

    private Tank getTank(TiledMapTileLayer tileLayer, Texture blueTankTexture,
                         LibGdxMovementService movementService, CollidingObject collidingObject) {

        LibGdxGraphicObject graphicObject = new LibGdxGraphicObject(
                tileLayer,
                blueTankTexture,
                collidingObject
        );
        return new Tank(MOVEMENT_SPEED, movementService, graphicObject, collidingObject);
    }

    private void initCommandExecutorAndControllers(CollisionDetectionManager collisionDetectionManager,
                                                   List<Tank> tanks,
                                                   List<Obstacle> trees) {

        CommandSchedulerAndExecutor schedulerAndExecutor = new CommandSchedulerAndExecutor();
        commandExecutor = schedulerAndExecutor;

        retrievePlayer(tanks);
        List<Tank> bots = getBots(tanks);
        CommandMapper commandMapper = createCommandMapper(player, collisionDetectionManager);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);

        tankController = new PlayerCommandsGenerator(commandMapper, keyboardChecker, schedulerAndExecutor);
        botController = getBotAICommandsAdapter(
                collisionDetectionManager,
                schedulerAndExecutor,
                trees, player, bots
        );
    }

    private void retrievePlayer(List<Tank> tanks) {
        if (tanks.isEmpty()) {
            throw new RuntimeException("At lease one tank must exist to present player");
        }
        player = tanks.get(0);
    }

    private List<Tank> getBots(List<Tank> tanks) {
        if (tanks.size() <= BOTS_START_INDEX) {
            return Collections.emptyList();
        }
        return tanks.subList(BOTS_START_INDEX, tanks.size());
    }

    private BotAICommandsAdapter getBotAICommandsAdapter(CollisionDetectionManager collisionDetectionManager,
                                                         CommandScheduler commandScheduler,
                                                         List<Obstacle> trees, Tank player, List<Tank> bots) {
        AI ai = new NotRecommendingAI();
        OrientationConverter orientationConverter = new OrientationConverterImpl();
        TankToPlayerConverter tankToPlayerConverter = new TankToPlayerConverterImpl(orientationConverter);
        TankToBotConverter tankToBotConverter = new TankToBotConverterImpl(orientationConverter);
        ObstacleConverter obstacleConverter = new ObstacleConverterImpl();
        RecommendationToCommandConverter recommendationToCommandConverter = new RecommendationToCommandConverterImpl(
                collisionDetectionManager
        );

        GameStateCreator gameStateCreator = new GameStateCreatorImpl(
                tankToPlayerConverter,
                tankToBotConverter,
                obstacleConverter
        );

        return new BotAICommandsAdapter(
                commandScheduler,
                ai,
                gameStateCreator,
                recommendationToCommandConverter,
                bots,
                trees.stream().map(Obstacle::getCollidingObject).collect(Collectors.toList()),
                player
        );
    }

    private GameObjectsActivator createGameObjectActivator(List<Tank> tanks, List<Obstacle> obstacles) {
        List<Activated> activatedList = Stream.of(
                obstacles,
                tanks
        ).flatMap(Collection::stream).collect(Collectors.toList());
        return new GameObjectsActivatorImpl(activatedList);
    }

    private LibGdxLevelRenderer createLibGdxLevelRenderer(List<Tank> tanks, List<Obstacle> obstacles, Batch batch, MapRenderer levelRenderer) {
        List<GraphicObjectRenderer> gameObjectRenderers = Stream.of(
                obstacles.stream()
                        .map(Obstacle::getLibGdxGraphicObject)
                        .map(graphicObject -> new LibGdxGraphicObjectRender(graphicObject, batch))
                        .collect(Collectors.toList()),
                tanks.stream()
                        .map(Tank::getLibGdxGraphicObject)
                        .map(graphicObject -> new LibGdxGraphicObjectRender(graphicObject, batch))
                        .collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());

        return new LibGdxLevelRenderer(batch, levelRenderer, gameObjectRenderers);
    }

    private CollisionDetectionManager createCollisionDetectionManager(List<Tank> tanks, List<Obstacle> obstacles, int maxX, int maxY) {
        List<Colliding> collidingList = Stream.of(
                tanks.stream().map(Tank::getCollidingObject).collect(Collectors.toList()),
                obstacles.stream().map(Obstacle::getCollidingObject).collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());
        return new CollisionDetectionManagerImpl(collidingList, maxX, maxY);
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
