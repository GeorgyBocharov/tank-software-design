package ru.mipt.bit.platformer.objects.placement.impl;

import ru.mipt.bit.platformer.objects.CollidingObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.objects.placement.TreesAndTanksPositionContainer;
import ru.mipt.bit.platformer.objects.placement.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.objects.placement.GameObjectPositionsGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameObjectPositionsRandomGenerator implements GameObjectPositionsGenerator {

    private final GameFieldAndTextureParams gameFieldAndTextureParams;
    private final int tankNumber;
    private final int treeNumber;
    private final int maxObjectsNumber;


    public GameObjectPositionsRandomGenerator(GameFieldAndTextureParams gameFieldAndTextureParams, int tankNumber, int treeNumber) {
        this.gameFieldAndTextureParams = gameFieldAndTextureParams;
        this.tankNumber = tankNumber;
        this.treeNumber = treeNumber;
        maxObjectsNumber = calculateMaxObjectsNumber();
        System.out.println("Max generate objects = " + maxObjectsNumber);
    }

    @Override
    public TreesAndTanksPositionContainer generateTreesAndTanksPositions() {
        if (maxObjectsNumber == 0) {
            System.out.println("Can't place objects due to small size of tileLayer");
            return new TreesAndTanksPositionContainer(Collections.emptyList(), Collections.emptyList());
        }
        List<CollidingObject> tanks = new ArrayList<>();
        List<CollidingObject> trees = new ArrayList<>();

        if (treeNumber + tankNumber < maxObjectsNumber / 2) {
            System.out.println("Generating via random");
            generateGraphicObjectsViaRandom(treeNumber, tankNumber, tanks, trees);
        } else {
            System.out.println("Generating via collection");
            generateGraphicObjectsViaCollection(treeNumber, tankNumber, tanks, trees);
        }

        return new TreesAndTanksPositionContainer(tanks, trees);
    }

    public int getMaxObjectsNumber() {
        return maxObjectsNumber;
    }

    private int calculateMaxObjectsNumber() {
        return (gameFieldAndTextureParams.getGameFieldHeight() / gameFieldAndTextureParams.getTextureHeight() - 1) *
                (gameFieldAndTextureParams.getGameFieldWidth() / gameFieldAndTextureParams.getTextureWidth() - 1);
    }

    private void generateGraphicObjectsViaRandom(int treeNumber, int tankNumber,
                                                 List<CollidingObject> tanks, List<CollidingObject> trees) {

        int objectsInColumn = gameFieldAndTextureParams.getGameFieldHeight() / gameFieldAndTextureParams.getTextureHeight() - 1;
        int objectsInRaw = gameFieldAndTextureParams.getGameFieldWidth() / gameFieldAndTextureParams.getTextureWidth() - 1;

        Set<Point> randomPoints = new HashSet<>();
        Random random = new Random();
        int ctr = 0;
        while (randomPoints.size() < Integer.min(treeNumber + tankNumber, maxObjectsNumber)) {
            Point point = new Point(random.nextInt(objectsInRaw), random.nextInt(objectsInColumn));
            if (randomPoints.add(point)) {
                if (ctr < tankNumber) {
                    tanks.add(new CollidingObject(point));
                } else {
                    trees.add(new CollidingObject(point));
                }
                ctr++;
            } else {
                System.out.println("Skipping alreadyAdded point");
            }
        }
    }

    private void generateGraphicObjectsViaCollection(int treeNumber, int tankNumber,
                                                     List<CollidingObject> tanks, List<CollidingObject> trees) {

        List<Point> availablePositions = generateAvailablePositions();
        Collections.shuffle(availablePositions);
        int graphicObjectsNumber = 0;
        int actualTankNumber = Integer.min(tankNumber, availablePositions.size());
        for (; graphicObjectsNumber < actualTankNumber; graphicObjectsNumber++) {
            tanks.add(new CollidingObject(availablePositions.get(graphicObjectsNumber)));
        }
        int actualTreeNumber = Integer.min(treeNumber, availablePositions.size() - graphicObjectsNumber);
        for (int i = 0; i < actualTreeNumber; i++) {
            trees.add(new CollidingObject(availablePositions.get(graphicObjectsNumber)));
            graphicObjectsNumber++;
        }
    }

    private List<Point> generateAvailablePositions() {
        List<Point> result = new ArrayList<>();
        int dx = gameFieldAndTextureParams.getTextureWidth();
        int dy = gameFieldAndTextureParams.getTextureHeight();
        int x = 0;
        while (x < gameFieldAndTextureParams.getGameFieldWidth() - dx) {
            int y = 0;
            while (y < gameFieldAndTextureParams.getGameFieldHeight() - dy) {
                result.add(new Point(x, y));
                y += dy;
            }
            x += dx;
        }
        return result;
    }
}
