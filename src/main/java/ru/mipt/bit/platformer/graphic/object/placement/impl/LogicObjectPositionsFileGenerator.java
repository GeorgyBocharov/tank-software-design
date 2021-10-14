package ru.mipt.bit.platformer.graphic.object.placement.impl;


import ru.mipt.bit.platformer.entities.LogicObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.graphic.LogicObjectsWrapper;
import ru.mipt.bit.platformer.graphic.object.placement.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.graphic.object.placement.LogicObjectPositionsGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogicObjectPositionsFileGenerator implements LogicObjectPositionsGenerator {

    private static final char TREE_MARKER = 'T';
    private static final char TANK_MARKER = 'X';

    private final Path filePath;
    private final GameFieldAndTextureParams gameFieldAndTextureParams;

    public LogicObjectPositionsFileGenerator(String filePathString, GameFieldAndTextureParams gameFieldAndTextureParams) {
        this.gameFieldAndTextureParams = gameFieldAndTextureParams;
        filePath = Path.of(filePathString);

    }

    @Override
    public LogicObjectsWrapper generateGraphicObjects() {
        List<String> rows;
        try {
            rows = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int rowNumber = Integer.min(gameFieldAndTextureParams.getGameFieldHeight(), rows.size());

        List<LogicObject> trees = new ArrayList<>();
        List<LogicObject> tanks = new ArrayList<>();


        for (int y = 0; y <= rowNumber; y++) {
            String row = rows.get(rows.size() - 1 - y).trim();
            int columnNumber = Integer.min(row.length(), gameFieldAndTextureParams.getGameFieldWidth());
            for (int x = 0; x < columnNumber; x++) {
                switch (row.charAt(x)) {
                    case TREE_MARKER:
                        trees.add(new LogicObject(x, y));
                        break;
                    case TANK_MARKER:
                        tanks.add(new LogicObject(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        return new LogicObjectsWrapper(tanks, trees);
    }

}
