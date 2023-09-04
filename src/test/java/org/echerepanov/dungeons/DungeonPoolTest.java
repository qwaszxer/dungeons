package org.echerepanov.dungeons;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DungeonPoolTest {

  static Stream<Arguments> badDungeonsProvider() {
    return Stream.of(
        // THREE UNIQUE, BUT ONLY ONE OPEN
        Arguments.of(
            Stream.of(
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                        }))
                .collect(Collectors.toList()),
            "Minimum 2 open and unique dungeons required!"),
        // THREE OPEN, BUT IDENTICAL
        Arguments.of(
            Stream.of(
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                        }))
                .collect(Collectors.toList()),
            "Minimum 2 open and unique dungeons required!"),
        // THREE UNIQUE AND OPEN, BUT ONLY TWO HAS A COMPARABLE ONE
        Arguments.of(
            Stream.of(
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                          {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                          {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR}
                        }),
                    new Dungeon(
                        new Dungeon.Block[][] {
                          {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                          {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                          {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                        }))
                .collect(Collectors.toList()),
            "Not every dungeon has a comparable one!"));
  }

  @ParameterizedTest
  @MethodSource("badDungeonsProvider")
  void testNegativeDungeonPool(List<Dungeon> dungeons, String expectedMessage) {
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              new DungeonPool(dungeons);
            });

    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  static Stream<List<Dungeon>> goodDungeonsProvider() {
    return Stream.of(
        Stream.of(
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }))
            .collect(Collectors.toList()),
        Stream.of(
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                    }))
            .collect(Collectors.toList()),
        Stream.of(
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.AIR}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.GROUND}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }),
                // COPY
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }),
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }),
                // CLOSED
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR}
                    }),
                // CLOSED
                new Dungeon(
                    new Dungeon.Block[][] {
                      {Dungeon.Block.AIR, Dungeon.Block.AIR, Dungeon.Block.AIR},
                      {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.AIR},
                      {Dungeon.Block.GROUND, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
                    }))
            .collect(Collectors.toList()));
  }

  @ParameterizedTest
  @MethodSource("goodDungeonsProvider")
  void testPositiveCreateXSequence(List<Dungeon> dungeons) {
    DungeonPool dungeonPool = new DungeonPool(dungeons);

    sequenceIsValid(dungeonPool.createXSequence(3), 3);
    sequenceIsValid(dungeonPool.createXSequence(20), 20);
    sequenceIsValid(dungeonPool.createXSequence(100), 100);
  }

  static void sequenceIsValid(List<Dungeon> dungeons, int expectedSize) {
    assertEquals(dungeons.size(), expectedSize);
    for (int i = 1; i < dungeons.size(); i++) {
      Dungeon previous = dungeons.get(i - 1);
      Dungeon current = dungeons.get(i);
      assertNotEquals(previous, current);
      assertTrue(
          previous.getExits().stream().anyMatch(exit -> current.getEntrances().contains(exit)));
    }
  }
}
