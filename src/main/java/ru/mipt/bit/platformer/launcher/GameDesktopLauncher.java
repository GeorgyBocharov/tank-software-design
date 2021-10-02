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
import ru.mipt.bit.platformer.entities.AgileGraphicObject;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.entities.GraphicObject;
import ru.mipt.bit.platformer.service.ActionMapper;
import ru.mipt.bit.platformer.service.impl.ActionMapperImpl;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
    private Texture greenTreeTexture;

    private AgileGraphicObject player;
    private GraphicObject tree;
    private ActionMapper actionMapper;

    @Override
    public void create() {
        batch = new SpriteBatch();
        actionMapper = new ActionMapperImpl();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        blueTankTexture = new Texture("images/tank_blue.png");
        greenTreeTexture = new Texture("images/greenTree.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture

        player = new AgileGraphicObject(blueTankTexture, new GridPoint2(1, 1), 0f);
        tree = new GraphicObject(greenTreeTexture, new GridPoint2(1, 3));
        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        performGraphicObjectsInteractions(deltaTime);

        drawGraphicObjects();
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
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    private void drawGraphicObjects() {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawGraphicObjectsUnscaled(batch, player, tree);

        // submit all drawing requests
        batch.end();
    }

    private void performGraphicObjectsInteractions(float deltaTime) {
        if (player.isMovementFinished()) {
            for (Direction direction : Direction.values()) {
                if (actionMapper.isDirectionKeyPressed(direction, Gdx.input)) {
                    if (collisionImpossible(tree, player, direction)) {
                        player.triggerMovement(direction);
                    }
                    player.setRotation(direction.getRotation());
                }
            }
        }

        tileMovement.interpolateAgileObjectCoordinates(player);
        player.move(deltaTime, MOVEMENT_SPEED);
    }

    private void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
