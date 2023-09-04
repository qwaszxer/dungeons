package org.echerepanov.dungeons;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class Utils {
  static <E> Optional<E> getRandomElement(Collection<E> collection) {
    return collection.stream()
        .skip(ThreadLocalRandom.current().nextInt(collection.size()))
        .findAny();
  }
}
