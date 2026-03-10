package petProjects.model;

import java.util.Random;
import java.util.function.Supplier;

public final class RandomPlacer {
    private RandomPlacer() {
    }

    public static void placeRandomEntities(WorldMap map, int count, Random rnd, Supplier<? extends Entity> supplier) {
        int attemptsLimit = Math.max(100, count * 10);
        int placed = 0;
        int attempts = 0;
        while (placed < count && attempts < attemptsLimit) {
            attempts++;
            int x = rnd.nextInt(map.getWidth());
            int y = rnd.nextInt(map.getHeight());
            Cell c = new Cell(x, y);
            if (map.isCellPassable(c)) {
                map.placeEntityOnCell(c, supplier.get());
                placed++;
            }
        }
    }
}
