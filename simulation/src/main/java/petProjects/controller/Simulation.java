package petProjects.controller;

import petProjects.view.Renderer;
import petProjects.controller.actions.Action;
import petProjects.controller.actions.MoveAndActAction;
import petProjects.model.WorldMap;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Simulation {
    private final WorldMap worldMap;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final Renderer renderer;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int turn = 0;
    private final Random rnd = new Random();

    public Simulation(WorldMap worldMap, List<Action> initActions, List<Action> turnActions, Renderer renderer) {
        this.worldMap = worldMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        this.renderer = renderer;
    }

    public void startSimulation(long millisPerTurn) {
        // Инициализация
        for (Action a : initActions) a.execute(worldMap);

        // Рендерим начальное состояние (Turn 0) перед первым ходом
        renderer.render(worldMap, turn);

        running.set(true);

        while (running.get()) {
            nextTurn();
            try {
                Thread.sleep(millisPerTurn);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void pauseSimulation() {
        running.set(false);
    }

    public void nextTurn() {
        // 1) Выполнить прочие действия, относящиеся к ходу (например, спавн травы),
        //    но пропустить внешнюю реализацию "MoveAndActAction", чтобы не дублировать ходы.
        for (Action a : turnActions) {
            if (a instanceof MoveAndActAction) continue;
            a.execute(worldMap);
        }

        // 2) Выполнить один полный раунд ходов для всех существ
        CreatureTurnProcessor.processAllCreaturesOnce(worldMap, rnd);

        // 3) Завершить turn и отрендерить состояние
        turn++;
        renderer.render(worldMap, turn);
    }
}