package org.echerepanov.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DungeonPool {
  private final Map<Integer, Set<Dungeon>> suitableDungeonsByEntrance;

  public DungeonPool(List<Dungeon> dungeons) {
    Set<Dungeon> uniqueOpenDungeons =
        dungeons.stream().filter(Dungeon::isOpen).collect(Collectors.toSet());
    if (uniqueOpenDungeons.size() < 2) {
      throw new RuntimeException("Minimum 2 open and unique dungeons required!");
    }

    suitableDungeonsByEntrance =
        createMapWithEmptySets(uniqueOpenDungeons.stream().findAny().get().getAreaYSize());

    addDungeonsToMap(uniqueOpenDungeons);

    if (!uniqueOpenDungeons.stream().allMatch(this::isSuitableDungeonExists)) {
      throw new RuntimeException("Not every dungeon has a comparable one!");
    }
  }

  public List<Dungeon> createXSequence(int length) {
    List<Dungeon> sequence = new ArrayList<>(length);

    Dungeon dungeon = getRandomDungeon();
    sequence.add(dungeon);

    for (int i = 1; i < length; i++) {
      dungeon = getRandomSuitableDungeon(dungeon);
      sequence.add(dungeon);
    }

    return sequence;
  }

  private Map<Integer, Set<Dungeon>> createMapWithEmptySets(int length) {
    Map<Integer, Set<Dungeon>> map = new HashMap<>(length);

    for (int i = 0; i < length; i++) {
      map.put(i, new HashSet<>());
    }

    return map;
  }

  private void addDungeonsToMap(Set<Dungeon> dungeons) {
    dungeons.forEach(
        dungeon ->
            dungeon
                .getEntrances()
                .forEach(entrance -> suitableDungeonsByEntrance.get(entrance).add(dungeon)));
  }

  private boolean isSuitableDungeonExists(Dungeon dungeon) {
    return dungeon.getExits().stream()
        .anyMatch(
            exit ->
                suitableDungeonsByEntrance.get(exit).stream().anyMatch(d -> !d.equals(dungeon)));
  }

  private Dungeon getRandomSuitableDungeon(Dungeon dungeon) {
    Set<Dungeon> suitableDungeons =
        dungeon.getExits().stream()
            .map(suitableDungeonsByEntrance::get)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    suitableDungeons.remove(dungeon);

    return Utils.getRandomElement(suitableDungeons).orElseThrow(RuntimeException::new);
  }

  private Dungeon getRandomDungeon() {
    Set<Dungeon> suitableDungeons =
        suitableDungeonsByEntrance.values().stream()
            .flatMap(Set::stream)
            .collect(Collectors.toSet());

    return Utils.getRandomElement(suitableDungeons).orElseThrow(RuntimeException::new);
  }
}
