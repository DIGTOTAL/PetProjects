package petProjects.model;

import petProjects.model.creatures.Creature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class MovementUtils {
    private MovementUtils() {
    }


    private static void addEmptyNeighborsAroundCell(WorldMap worldMap, Cell center, Cell currentCell, Set<Cell> result) {
        if (center == null) return;
        for (Cell n : worldMap.getNeighboringCells(center)) {
            if (!worldMap.isCellInsideMap(n)) continue;
            if (n.equals(currentCell)) continue;
            if (worldMap.getEntityAtCell(n) == null) {
                result.add(n);
            }
        }
    }

    // Собрать пустые соседние клетки вокруг всех клеток, где есть сущности типа `type`
    public static Set<Cell> collectEmptyNeighborCellsAroundEntities(WorldMap worldMap, Class<? extends Entity> type, Cell currentCell) {
        Set<Cell> result = new HashSet<>();
        List<Cell> entityCells = worldMap.getCellsWithEntity(type);
        for (Cell entityCell : entityCells) {
            addEmptyNeighborsAroundCell(worldMap, entityCell, currentCell, result);
        }
        return result;
    }

    // Собрать пустые соседние клетки вокруг конкретной клетки `center`
    public static Set<Cell> collectEmptyNeighborCellsAroundCell(WorldMap worldMap, Cell center, Cell currentCell) {
        Set<Cell> result = new HashSet<>();
        addEmptyNeighborsAroundCell(worldMap, center, currentCell, result);
        return result;
    }

    // Найти текущую клетку конкретной сущности (по ссылке). Возвращает null если не найдена.
    public static Cell findEntityCell(WorldMap worldMap, Entity entity) {
        if (entity == null) return null;
        List<Cell> sameTypeCells = worldMap.getCellsWithEntity(entity.getClass());
        for (Cell c : sameTypeCells) {
            if (worldMap.getEntityAtCell(c) == entity) {
                return c;
            }
        }
        return null;
    }

    // Попытаться переместить `mover` к любому из `targets` на расстояние до `speed`.
    // Возвращает true если переместился.
    public static boolean moveTowardsTargets(WorldMap worldMap, Cell currentCell, Set<Cell> targets, int speed, Creature mover) {
        if (targets == null || targets.isEmpty()) return false;
        List<Cell> targetsList = new ArrayList<>(targets);
        List<Cell> path = PathFinder.findShortestPathTo(worldMap, currentCell, targetsList);
        if (path.isEmpty()) return false;
        int maxSteps = Math.min(speed, Math.max(0, path.size() - 1));
        if (maxSteps <= 0) return false;

        // ищем ближайшую по пути доступную для постановки клетку (от дальней к ближней)
        for (int steps = maxSteps; steps >= 1; steps--) {
            Cell candidate = path.get(steps);
            if (worldMap.isCellPassable(candidate)) {
                // применяем перемещение
                worldMap.removeEntityFromCell(currentCell);
                worldMap.placeEntityOnCell(candidate, mover);
                return true;
            }
        }
        return false;
    }
}