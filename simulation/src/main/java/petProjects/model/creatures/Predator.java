package petProjects.model.creatures;

import petProjects.model.Cell;
import petProjects.model.Entity;
import petProjects.model.MovementUtils;
import petProjects.model.WorldMap;

import java.util.Set;

public class Predator extends Creature {
    private final int attackPower;
    private Herbivore chasedPrey = null;


    public Predator(int health, int speed, int attackPower) {
        super(health, speed);
        this.attackPower = attackPower;
    }

    public void attack(Creature target) {
        if (target.isAlive()) {
            target.takeDamage(attackPower);
        }
    }

    @Override
    public void makeTurn(WorldMap worldMap, Cell currentCell) {
        // Если есть запомненная раненая жертва — пытаемся догнать (движение = конец хода)
        if (chasedPrey != null && chasedPrey.isAlive()) {
            Cell preyCell = MovementUtils.findEntityCell(worldMap, chasedPrey);
            if (preyCell != null) {
                Set<Cell> emptyAroundPrey = MovementUtils.collectEmptyNeighborCellsAroundCell(worldMap, preyCell, currentCell);
                if (!emptyAroundPrey.isEmpty()) {
                    boolean moved = MovementUtils.moveTowardsTargets(worldMap, currentCell, emptyAroundPrey, getSpeed(), this);
                    if (moved) return;
                }
            }
            chasedPrey = null; // не удалось догнать
        }

        // Ищем соседнего травоядного — одна атака = конец хода
        for (Cell neighbor : worldMap.getNeighboringCells(currentCell)) {
            if (!worldMap.isCellInsideMap(neighbor)) continue;
            Entity entity = worldMap.getEntityAtCell(neighbor);
            if (entity instanceof Herbivore prey) {
                attack(prey);

                if (!prey.isAlive()) {
                    worldMap.removeEntityFromCell(neighbor);
                    chasedPrey = null;
                    return; // конец хода при убийстве
                }

                // Если жертва убежала с соседней клетки — запоминаем её для погони
                if (worldMap.getEntityAtCell(neighbor) != prey) {
                    chasedPrey = prey;
                } else {
                    chasedPrey = null;
                }
                return; // ход заканчивается после одной атаки
            }
        }

        // Если рядом нет травоядных — пробуем двигаться к ближайшим травоядным
        Set<Cell> emptyCellsAroundHerbivores = MovementUtils.collectEmptyNeighborCellsAroundEntities(worldMap, Herbivore.class, currentCell);
        if (!emptyCellsAroundHerbivores.isEmpty()) {
            MovementUtils.moveTowardsTargets(worldMap, currentCell, emptyCellsAroundHerbivores, getSpeed(), this);
            return; // движение = конец хода
        }

        // Если рядом нет травоядных и нечего преследовать — остаёмся на месте
    }
}