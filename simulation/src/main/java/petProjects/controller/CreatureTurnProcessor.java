package petProjects.controller;

import petProjects.model.Cell;
import petProjects.model.Entity;
import petProjects.model.MovementUtils;
import petProjects.model.WorldMap;
import petProjects.model.creatures.Creature;

import java.util.*;

public final class CreatureTurnProcessor {
    private CreatureTurnProcessor() {
    }

    public static void processAllCreaturesOnce(WorldMap worldMap, Random rnd) {
        List<Cell> creatureCells = worldMap.getCellsWithEntity(Creature.class);
        Set<Creature> creatureSet = new LinkedHashSet<>();
        for (Cell cell : creatureCells) {
            Entity e = worldMap.getEntityAtCell(cell);
            if (e instanceof Creature creature) {
                creatureSet.add(creature);
            }
        }

        List<Creature> creatures = new ArrayList<>(creatureSet);
        Collections.shuffle(creatures, rnd);

        for (Creature creature : creatures) {
            Cell current = MovementUtils.findEntityCell(worldMap, creature);
            if (current == null) continue; // погиб или удалён до хода
            creature.makeTurn(worldMap, current);
        }
    }
}
