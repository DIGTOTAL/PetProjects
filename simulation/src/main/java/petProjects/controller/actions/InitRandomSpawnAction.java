package petProjects.controller.actions;

import petProjects.model.RandomPlacer;
import petProjects.model.WorldMap;
import petProjects.model.creatures.Herbivore;
import petProjects.model.creatures.Predator;
import petProjects.model.objects.Grass;
import petProjects.model.objects.Rock;
import petProjects.model.objects.Tree;

import java.util.Random;

public class InitRandomSpawnAction implements Action {
    private final int grassCount;
    private final int rockCount;
    private final int treeCount;
    private final int herbivoreCount;
    private final int predatorCount;
    private final Random rnd = new Random();

    public InitRandomSpawnAction(int grassCount, int rockCount, int treeCount, int herbivoreCount, int predatorCount) {
        this.grassCount = grassCount;
        this.rockCount = rockCount;
        this.treeCount = treeCount;
        this.herbivoreCount = herbivoreCount;
        this.predatorCount = predatorCount;
    }

    @Override
    public void execute(WorldMap worldMap) {
        RandomPlacer.placeRandomEntities(worldMap, grassCount, rnd, Grass::new);
        RandomPlacer.placeRandomEntities(worldMap, rockCount, rnd, Rock::new);
        RandomPlacer.placeRandomEntities(worldMap, treeCount, rnd, Tree::new);
        RandomPlacer.placeRandomEntities(worldMap, herbivoreCount, rnd, () -> new Herbivore(10, 1));
        RandomPlacer.placeRandomEntities(worldMap, predatorCount, rnd, () -> new Predator(15, 1, 5));
    }
}