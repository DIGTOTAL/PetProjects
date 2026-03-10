package petProjects.controller.actions;

import petProjects.model.WorldMap;
import petProjects.controller.CreatureTurnProcessor;

import java.util.Random;

public class MoveAndActAction implements Action {
    private final Random rnd = new Random();

    @Override
    public void execute(WorldMap worldMap) {
        CreatureTurnProcessor.processAllCreaturesOnce(worldMap, rnd);
    }
}