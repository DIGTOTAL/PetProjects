package petProjects.model.creatures;

import petProjects.model.Cell;
import petProjects.model.Entity;
import petProjects.model.MovementUtils;
import petProjects.model.WorldMap;
import petProjects.model.objects.Grass;

import java.util.Set;

public class Herbivore extends Creature {

    public Herbivore(int health, int speed) {
        super(health, speed);
    }

    @Override
    public void makeTurn(WorldMap worldMap, Cell currentCell) {
        // Если есть соседняя трава — съедаем и заканчиваем ход
        for (Cell neighbor : worldMap.getNeighboringCells(currentCell)) {
            if (!worldMap.isCellInsideMap(neighbor)) continue;
            Entity entity = worldMap.getEntityAtCell(neighbor);
            if (entity instanceof Grass) {
                worldMap.removeEntityFromCell(neighbor);
                return;
            }
        }

        // Идём к пустым клеткам вокруг травы
        Set<Cell> emptyCellsAroundGrass = MovementUtils.collectEmptyNeighborCellsAroundEntities(worldMap, Grass.class, currentCell);
        if (emptyCellsAroundGrass.isEmpty()) return;

        MovementUtils.moveTowardsTargets(worldMap, currentCell, emptyCellsAroundGrass, getSpeed(), this);
    }
}