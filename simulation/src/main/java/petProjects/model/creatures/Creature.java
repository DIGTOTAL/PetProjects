package petProjects.model.creatures;

import petProjects.model.Cell;
import petProjects.model.Entity;
import petProjects.model.MovementUtils;
import petProjects.model.WorldMap;

import java.util.Set;

public abstract class Creature extends Entity {
    private int health;
    private final int speed;

    public Creature(int health, int speed) {
        this.health = health;
        this.speed = speed;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public abstract void makeTurn(WorldMap worldMap, Cell currentCell);

    protected Cell findCurrentCell(WorldMap worldMap) {
        return MovementUtils.findEntityCell(worldMap, this);
    }

    protected boolean tryMoveTowards(WorldMap worldMap, Cell currentCell, Set<Cell> targets) {
        return MovementUtils.moveTowardsTargets(worldMap, currentCell, targets, getSpeed(), this);
    }
}