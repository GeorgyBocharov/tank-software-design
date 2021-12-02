package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import org.awesome.ai.AI;
import org.awesome.ai.strategy.NotRecommendingAI;
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
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.collision.impl.CollisionDetectorImpl;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.impl.RandomCommandsGenerator;
import ru.mipt.bit.platformer.commands.impl.ShowHealthBarsCommand;
import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.controllers.impl.BotAICommandsAdapter;
import ru.mipt.bit.platformer.controllers.impl.BotController;
import ru.mipt.bit.platformer.controllers.impl.PlayerController;
import ru.mipt.bit.platformer.commands.impl.CommandSchedulerAndExecutor;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.impl.ShootCommand;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;
import ru.mipt.bit.platformer.commands.mappers.CommandMapperImpl;
import ru.mipt.bit.platformer.objects.support.impl.VulnerableObject;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.keyboard.impl.LibGdxKeyboardChecker;
import ru.mipt.bit.platformer.level.GraphicLevel;
import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.level.impl.LibGdxGraphicLevel;
import ru.mipt.bit.platformer.level.impl.LogicLevelImpl;
import ru.mipt.bit.platformer.objects.support.LibGdxMovementService;
import ru.mipt.bit.platformer.objects.support.impl.LibGdxMovementServiceImpl;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;
import ru.mipt.bit.platformer.objects.logic.LogicTank;
import ru.mipt.bit.platformer.objects.support.impl.CoolDownTrackerImpl;
import ru.mipt.bit.platformer.placement.Position;
import ru.mipt.bit.platformer.position.generators.GameObjectPositionsGenerator;
import ru.mipt.bit.platformer.position.generators.TreesAndTanksPositionContainer;
import ru.mipt.bit.platformer.position.generators.impl.GameObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.position.generators.impl.LibGdxGameFieldAndTextureParams;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float TANK_SPEED = 1.5f;
    private static final float PROJECTILE_SPEED = 5f;
    private static final float TANK_HP = 100f;
    private static final float PROJECTILE_DAMAGE = 20f;
    private static final float COOL_DOWN = 2f;

    public static final int BOTS_START_INDEX = 1;
    public static final String GREEN_TREE_PNG = "images/greenTree.png";
    public static final String TANK_BLUE_PNG = "images/tank_blue.png";
    public static final String PROJECTILE_PNG = "images/projectile.png";

    private final Interpolation interpolation = Interpolation.smooth;

    private GraphicLevel graphicLevel;
    private Controller tankController;
    private Controller botController;
    private CommandExecutor commandExecutor;
    private Texture blueTankTexture;
    private Texture greenTreeTexture;
    private Texture projectileTexture;
    private LogicLevel logicLevel;

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

        logicLevel = new LogicLevelImpl();

        LibGdxMovementService movementService = new LibGdxMovementServiceImpl(interpolation, tileLayer);
        CollisionDetector collisionDetector = new CollisionDetectorImpl(tileLayer.getWidth(), tileLayer.getHeight());

        LibGdxGraphicLevel libGdxGraphicLevel = new LibGdxGraphicLevel(batch, levelRenderer, tileLayer, new ShapeRenderer(),
                movementService, greenTreeTexture, blueTankTexture, projectileTexture);
        this.graphicLevel = libGdxGraphicLevel;

        logicLevel.subscribe(this.graphicLevel);
        logicLevel.subscribe(collisionDetector);

        List<LogicObstacle> obstacles = getObstacles(wrapper);
        List<LogicTank> bots = getBots(wrapper, logicLevel, collisionDetector);
        LogicTank player = getPlayer(wrapper, logicLevel, collisionDetector);

        logicLevel.addGameObjects(obstacles);
        logicLevel.addGameObjects(bots);
        logicLevel.addPlayer(player);

        initCommandExecutorAndControllers(bots, obstacles, player, libGdxGraphicLevel);

    }

    @Override
    public void render() {
        clearScreen();

        if (logicLevel.isGameOver()) {
            return;
        }

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        tankController.generate(deltaTime);
        botController.generate(deltaTime);
        commandExecutor.executeAll();

        logicLevel.activateAll(deltaTime);

        graphicLevel.renderAll();
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
        graphicLevel.dispose();
    }


    private void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void loadTextures() {
        greenTreeTexture = new Texture(GREEN_TREE_PNG);
        blueTankTexture = new Texture(TANK_BLUE_PNG);
        projectileTexture = new Texture(PROJECTILE_PNG);
    }

    private List<LogicObstacle> getObstacles(TreesAndTanksPositionContainer wrapper) {
        return wrapper.getTrees().stream()
                .map(LogicObstacle::new)
                .collect(Collectors.toList());
    }

    private LogicTank getPlayer(TreesAndTanksPositionContainer wrapper, LogicLevel logicLevel, CollisionDetector collisionDetector) {
        List<Position> tankPositions = wrapper.getTanks();
        if (tankPositions.isEmpty()) {
            throw new RuntimeException("At lease one tank must exist to present player");
        }
        return getTank(tankPositions.get(0), logicLevel, collisionDetector);
    }

    private List<LogicTank> getBots(TreesAndTanksPositionContainer wrapper, LogicLevel logicLevel, CollisionDetector collisionDetector) {
        List<Position> botPositions = wrapper.getTanks();


        if (botPositions.size() <= BOTS_START_INDEX) {
            return Collections.emptyList();
        }

        return botPositions
                .subList(BOTS_START_INDEX, wrapper.getTanks().size())
                .stream()
                .map(botPosition -> getTank(botPosition, logicLevel, collisionDetector))
                .collect(Collectors.toList());
    }

    private LogicTank getTank(Position position, LogicLevel logicLevel, CollisionDetector collisionDetector) {

        return new LogicTank(
                TANK_SPEED, COOL_DOWN,
                PROJECTILE_DAMAGE, PROJECTILE_SPEED,
                new CoolDownTrackerImpl(), new VulnerableObject(TANK_HP),
                position, logicLevel, collisionDetector
        );
    }

    private void initCommandExecutorAndControllers(List<LogicTank> bots,
                                                   List<LogicObstacle> trees,
                                                   LogicTank player,
                                                   LibGdxGraphicLevel graphicLevel) {

        CommandSchedulerAndExecutor schedulerAndExecutor = new CommandSchedulerAndExecutor();
        commandExecutor = schedulerAndExecutor;

        CommandMapper commandMapper = createCommandMapper(player, graphicLevel);
        KeyboardChecker keyboardChecker = new LibGdxKeyboardChecker(Gdx.input);

        tankController = new PlayerController(commandMapper, keyboardChecker, schedulerAndExecutor);
//        botController = getBotAICommandsAdapter(
//                schedulerAndExecutor,
//                trees, player, bots
//        );
        botController = new BotController(bots, schedulerAndExecutor, new RandomCommandsGenerator());
    }

    private BotAICommandsAdapter getBotAICommandsAdapter(CommandScheduler commandScheduler,
                                                         List<LogicObstacle> trees, LogicTank player, List<LogicTank> bots) {
        AI ai = new NotRecommendingAI();
        OrientationConverter orientationConverter = new OrientationConverterImpl();
        TankToPlayerConverter tankToPlayerConverter = new TankToPlayerConverterImpl(orientationConverter);
        TankToBotConverter tankToBotConverter = new TankToBotConverterImpl(orientationConverter);
        ObstacleConverter obstacleConverter = new ObstacleConverterImpl();
        RecommendationToCommandConverter recommendationToCommandConverter = new RecommendationToCommandConverterImpl();

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
                trees,
                player
        );
    }


    private CommandMapper createCommandMapper(LogicTank player, LibGdxGraphicLevel libGdxGraphicLevel) {
        CommandMapper commandMapper = new CommandMapperImpl();
        Command moveUpCommand = new MovementCommand(player, Direction.UP);
        Command moveLeftCommand = new MovementCommand(player, Direction.LEFT);
        Command moveRightCommand = new MovementCommand(player, Direction.RIGHT);
        Command moveDownCommand = new MovementCommand(player, Direction.DOWN);
        Command shootCommand = new ShootCommand(player);
        Command showHealthBarsCommand = new ShowHealthBarsCommand(libGdxGraphicLevel);

        commandMapper.addCommandMapping(Set.of(Input.Keys.W), moveUpCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.UP), moveUpCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.S), moveDownCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.DOWN), moveDownCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.A), moveLeftCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.LEFT), moveLeftCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.D), moveRightCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.RIGHT), moveRightCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.SPACE), shootCommand);
        commandMapper.addCommandMapping(Set.of(Input.Keys.L), showHealthBarsCommand);

        return commandMapper;
    }
}
