package petProjects.controller.actions;

import petProjects.model.RandomPlacer;
import petProjects.model.WorldMap;
import petProjects.model.objects.Grass;

import java.util.Random;

public class GrassSpawnAction implements Action {
    private final int amountPerTurn;
    private final Random rnd = new Random();

    public GrassSpawnAction(int amountPerTurn) {
        this.amountPerTurn = amountPerTurn;
    }

    @Override
    public void execute(WorldMap worldMap) {
        RandomPlacer.placeRandomEntities(worldMap, amountPerTurn, rnd, Grass::new);
    }
}