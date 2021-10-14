package ru.mipt.bit.platformer.graphic.object.placement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.mipt.bit.platformer.entities.LogicObject;
import ru.mipt.bit.platformer.graphic.LogicObjectsWrapper;
import ru.mipt.bit.platformer.graphic.object.placement.impl.LogicObjectPositionsFileGenerator;
import ru.mipt.bit.platformer.graphic.object.placement.impl.LogicObjectPositionsRandomGenerator;
import ru.mipt.bit.platformer.graphic.object.placement.wrapper.LogicObjectNumberWrapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_EMPTY;
import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_FIRST;
import static utils.TestDataSource.GRAPHIC_OBJECT_LOCATIONS_SECOND;

public class LogicObjectPositionsGeneratorTest {

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

        LogicObjectPositionsRandomGenerator generator = new LogicObjectPositionsRandomGenerator(
                gameFieldAndTextureParams, wrapper.getTankNumber(), wrapper.getTreeNumber());

        Assertions.assertEquals(expectedMaxNumberOfObjectsOnField, generator.getMaxObjectsNumber());

        LogicObjectsWrapper logicObjectsWrapper = generator.generateGraphicObjects();
        System.out.println(logicObjectsWrapper);

        List<LogicObject> actualTanks = logicObjectsWrapper.getTanks();
        List<LogicObject> actualTrees = logicObjectsWrapper.getTrees();

        Assertions.assertEquals(wrapper.getExpectedTankNumber(), actualTanks.size());
        Assertions.assertEquals(wrapper.getExpectedTreeNumber(), actualTrees.size());

    }


    @ParameterizedTest
    @MethodSource("providePositionTestData")
    public void returnGraphicObjectsWrapperOnLocationFile(String fileName, List<LogicObject> expectedTanks, List<LogicObject> expectedTrees) {

        LogicObjectPositionsGenerator generator = new LogicObjectPositionsFileGenerator(fileName, gameFieldAndTextureParams);
        LogicObjectsWrapper logicObjectsWrapper = generator.generateGraphicObjects();
        System.out.println(logicObjectsWrapper);

        List<LogicObject> actualTanks = logicObjectsWrapper.getTanks();
        List<LogicObject> actualTrees = logicObjectsWrapper.getTrees();

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
                        List.of(new LogicObject(5, 0), new LogicObject(1, 5)),
                        List.of(new LogicObject(2, 1), new LogicObject(4, 1), new LogicObject(8, 1),
                                new LogicObject(4, 4), new LogicObject(9, 4) , new LogicObject(2, 6),
                                new LogicObject(4, 6), new LogicObject(8,6))
                ),
                Arguments.of(
                        GRAPHIC_OBJECT_LOCATIONS_SECOND,
                        List.of(new LogicObject(5, 5)),
                        List.of(new LogicObject(4, 0), new LogicObject(8, 1),
                                new LogicObject(4, 4), new LogicObject(9, 4) , new LogicObject(9, 5),
                                new LogicObject(4, 8))
                ),
                Arguments.of(
                        GRAPHIC_OBJECT_LOCATIONS_EMPTY,
                        Collections.emptyList(),
                        Collections.emptyList()
                )

        );
    }


}
