package petProjects;

import petProjects.controller.actions.Action;
import petProjects.controller.actions.GrassSpawnAction;
import petProjects.controller.actions.InitRandomSpawnAction;
import petProjects.controller.Simulation;
import petProjects.model.WorldMap;
import petProjects.view.ConsoleRenderer;
import petProjects.view.Renderer;

import java.util.List;

public class Main {
    public static void main(String[] args){
        WorldMap map = new WorldMap(10, 10);

        List<Action> initActions = List.of(
                new InitRandomSpawnAction(2, 2, 2, 4, 3)
        );

        List<Action> turnActions = List.of(
                new GrassSpawnAction(1)
        );

        Renderer renderer = new ConsoleRenderer();
        Simulation sim = new Simulation(map, initActions, turnActions, renderer);

        sim.startSimulation(200); // либо: for (int i=0;i<100;i++) sim.nextTurn();
    }
}