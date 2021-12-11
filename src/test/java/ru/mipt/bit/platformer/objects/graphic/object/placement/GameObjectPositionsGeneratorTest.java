package ru.mipt.bit.platformer.objects.graphic.object.placement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.mipt.bit.platformer.placement.Position;
import ru.mipt.bit.platformer.position.generators.TreesAndTanksPositionContainer;
import ru.mipt.bit.platformer.position.generators.GameFieldAndTextureParams;
import ru.mipt.bit.platformer.position.generators.GameObjectPositionsGenerator;
import ru.mipt.bit.platformer.position.generators.impl.GameObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.position.generators.impl.GameObjectPositionsRandomGenerator;
import ru.mipt.bit.platformer.objects.graphic.object.placement.wrapper.LogicObjectNumberWrapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_EMPTY;
import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_FIRST;
import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_SECOND;

public class GameObjectPositionsGeneratorTest {

    private static GameFieldAndTextureParams gameFieldAndTextureParams;
    private static int expectedMaxNumberOfObjectsOnField;

    @BeforeAll
    public static void setUp() {
        expectedMaxNumberOfObjectsOnField = 63;
        gameFieldAndTextureParams = new GameFieldAndTextureParams() {
            @Override
            public int getTextureHeight() {
                return 1;
            }

            @Override
            public int getTextureWidth() {
                return 1;
            }

            @Override
            public int getGameFieldWidth() {
                return 10;
            }

            @Override
            public int getGameFieldHeight() {
                return 8;
            }
        };
    }

    @ParameterizedTest
    @MethodSource("provideLogicObjectsNumber")
    public void returnGraphicObjectsWrapperOnRandomGeneration(LogicObjectNumberWrapper wrapper) {

        GameObjectPositionsRandomGenerator generator = new GameObjectPositionsRandomGenerator(
                gameFieldAndTextureParams, wrapper.getTankNumber(), wrapper.getTreeNumber());

        Assertions.assertEquals(expectedMaxNumberOfObjectsOnField, generator.getMaxObjectsNumber());

        TreesAndTanksPositionContainer treesAndTanksPositionContainer = generator.generateTreesAndTanksPositions();
        System.out.println(treesAndTanksPositionContainer);

        List<Position> actualTanks = treesAndTanksPositionContainer.getTanks();
        List<Position> actualTrees = treesAndTanksPositionContainer.getTrees();

        Assertions.assertEquals(wrapper.getExpectedTankNumber(), actualTanks.size());
        Assertions.assertEquals(wrapper.getExpectedTreeNumber(), actualTrees.size());

    }


    @ParameterizedTest
    @MethodSource("providePositionTestData")
    public void returnGraphicObjectsWrapperOnLocationFile(String fileName, List<Position> expectedTanks, List<Position> expectedTrees) {

        GameObjectPositionsGenerator generator = new GameObjectPositionsFileGenerator(fileName, gameFieldAndTextureParams);
        TreesAndTanksPositionContainer treesAndTanksPositionContainer = generator.generateTreesAndTanksPositions();
        System.out.println(treesAndTanksPositionContainer);

        List<Position> actualTanks = treesAndTanksPositionContainer.getTanks();
        List<Position> actualTrees = treesAndTanksPositionContainer.getTrees();

        Assertions.assertEquals(expectedTanks, actualTanks);
        Assertions.assertEquals(expectedTrees, actualTrees);

    }

    private static Stream<Arguments> provideLogicObjectsNumber() {
        return Stream.of(
                Arguments.of(new LogicObjectNumberWrapper(5, 10,
                        5 , 10)),
                Arguments.of(new LogicObjectNumberWrapper(0, 0,
                        0, 0)),
                Arguments.of(new LogicObjectNumberWrapper(expectedMaxNumberOfObjectsOnField, 3,
                        expectedMaxNumberOfObjectsOnField, 0)),
                Arguments.of(new LogicObjectNumberWrapper(expectedMaxNumberOfObjectsOnField - 3,
                        10, expectedMaxNumberOfObjectsOnField - 3, 3))
        );
    }

    private static Stream<Arguments> providePositionTestData() {
        return Stream.of(
                Arguments.of(
                        GRAPHIC_OBJECT_LOCATIONS_FIRST,
                        List.of(new Position(5, 0), new Position(1, 5)),
                        List.of(new Position(2, 1), new Position(4, 1), new Position(8, 1),
                                new Position(4, 4), new Position(9, 4) , new Position(2, 6),
                                new Position(4, 6), new Position(8,6))
                ),
                Arguments.of(
                        GRAPHIC_OBJECT_LOCATIONS_SECOND,
                        List.of(new Position(5, 5)),
                        List.of(new Position(4, 0), new Position(8, 1),
                                new Position(4, 4), new Position(9, 4) , new Position(9, 5),
                                new Position(4, 8))
                ),
                Arguments.of(
                        GRAPHIC_OBJECT_LOCATIONS_EMPTY,
                        Collections.emptyList(),
                        Collections.emptyList()
                )

        );
    }


}
