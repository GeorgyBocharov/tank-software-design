package ru.mipt.bit.platformer.objects.placement.impl;


import ru.mipt.bit.platformer.objects.LogicObject;
import ru.mipt.bit.platformer.objects.placement.LogicObjectsWrapper;
import ru.mipt.bit.platformer.objects.placement.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.objects.placement.LogicObjectPositionsGenerator;

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

        int textureHeight = gameFieldAndTextureParams.getTextureHeight();
        int textureWidth = gameFieldAndTextureParams.getTextureWidth();

        int rowNumber = Integer.min(gameFieldAndTextureParams.getGameFieldHeight() / textureHeight, rows.size()) + 1;

        List<LogicObject> trees = new ArrayList<>();
        List<LogicObject> tanks = new ArrayList<>();


        fillLists(rows, textureHeight, textureWidth, rowNumber, trees, tanks);

        return new LogicObjectsWrapper(tanks, trees);
    }

    private void fillLists(List<String> rows, int textureHeight, int textureWidth,
                           int rowNumber, List<LogicObject> trees, List<LogicObject> tanks) {

        int maxTexturesInRow = gameFieldAndTextureParams.getGameFieldWidth() / textureWidth;
        boolean[][] field = new boolean[maxTexturesInRow][rowNumber];
        for (int y = 0; y < rowNumber; y++) {
            String row = rows.get(rows.size() - 1 - y).trim();
            int columnNumber = Integer.min(row.length(), maxTexturesInRow);
            for (int x = 0; x < columnNumber; x++) {
                char tileMarker = row.charAt(x);
                if (field[x][y] || (tileMarker != TANK_MARKER && tileMarker != TREE_MARKER)) {
                    continue;
                }
                List<LogicObject> targetList;
                if (tileMarker == TREE_MARKER) {
                    targetList = trees;
                } else {
                    targetList = tanks;
                }
                markOccupiedTiles(textureHeight, textureWidth, field, x, y);
                targetList.add(new LogicObject(x, y));
                x += textureWidth;
            }
        }
    }

    private void markOccupiedTiles(int textureHeight, int textureWidth, boolean[][] field, int x, int y) {
        for (int i = x; i < x + textureWidth; i++) {
            for (int j = y; j < y + textureHeight; j++) {
                field[i][j] = true;
            }
        }
    }

}
