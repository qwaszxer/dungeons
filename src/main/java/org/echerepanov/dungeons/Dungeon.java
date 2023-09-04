package org.echerepanov.dungeons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Dungeon {
  private final Block[][] area;
  private final Set<Integer> entrances;
  private final Set<Integer> exits;

  public Dungeon(Block[][] area) {
    this.area = area;
    this.entrances = new HashSet<>();
    this.exits = new HashSet<>();

    for (int i = 0; i < getAreaYSize(); i++) {
      if (area[0][i] == Block.AIR) {
        entrances.add(i);
      }
      if (area[area.length - 1][i] == Block.AIR) {
        exits.add(i);
      }
    }
  }

  public Block[][] getArea() {
    return area;
  }

  public Set<Integer> getEntrances() {
    return entrances;
  }

  public Set<Integer> getExits() {
    return exits;
  }

  public boolean isOpen() {
    return entrances.size() > 0 && exits.size() > 0;
  }

  public int getAreaYSize() {
    return area[0].length;
  }

  public enum Block {
    GROUND,
    AIR
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dungeon dungeon = (Dungeon) o;
    return Arrays.deepEquals(area, dungeon.area);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(area);
  }

  @Override
  public String toString() {
    return Arrays.deepToString(area);
  }
}
