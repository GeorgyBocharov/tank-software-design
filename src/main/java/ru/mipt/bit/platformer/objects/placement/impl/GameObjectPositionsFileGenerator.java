package ru.mipt.bit.platformer.objects.placement.impl;


import ru.mipt.bit.platformer.objects.CollidingObject;
import ru.mipt.bit.platformer.objects.placement.TreesAndTanksPositionContainer;
import ru.mipt.bit.platformer.objects.placement.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.objects.placement.GameObjectPositionsGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameObjectPositionsFileGenerator implements GameObjectPositionsGenerator {

    private static final char TREE_MARKER = 'T';
    private static final char TANK_MARKER = 'X';

    private final Path filePath;
    private final GameFieldAndTextureParams gameFieldAndTextureParams;

    public GameObjectPositionsFileGenerator(String filePathString, GameFieldAndTextureParams gameFieldAndTextureParams) {
        this.gameFieldAndTextureParams = gameFieldAndTextureParams;
        filePath = Path.of(filePathString);

    }

    @Override
    public TreesAndTanksPositionContainer generateTreesAndTanksPositions() {
        List<String> rows;
        try {
            rows = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int textureHeight = gameFieldAndTextureParams.getTextureHeight();
        int textureWidth = gameFieldAndTextureParams.getTextureWidth();

        int rowNumber = Integer.min(gameFieldAndTextureParams.getGameFieldHeight() / textureHeight, rows.size()) + 1;

        List<CollidingObject> trees = new ArrayList<>();
        List<CollidingObject> tanks = new ArrayList<>();


        fillLists(rows, textureHeight, textureWidth, rowNumber, trees, tanks);

        return new TreesAndTanksPositionContainer(tanks, trees);
    }

    private void fillLists(List<String> rows, int textureHeight, int textureWidth,
                           int rowNumber, List<CollidingObject> trees, List<CollidingObject> tanks) {

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
                List<CollidingObject> targetList;
                if (tileMarker == TREE_MARKER) {
                    targetList = trees;
                } else {
                    targetList = tanks;
                }
                markOccupiedTiles(textureHeight, textureWidth, field, x, y);
                targetList.add(new CollidingObject(x, y));
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
