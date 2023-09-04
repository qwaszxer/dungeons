package org.echerepanov.dungeons;

public class Main {
  public static void main(String[] args) {
    System.out.println(
        new Dungeon(
            new Dungeon.Block[][] {
              {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
              {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND},
              {Dungeon.Block.AIR, Dungeon.Block.GROUND, Dungeon.Block.GROUND}
            }));
  }
}
