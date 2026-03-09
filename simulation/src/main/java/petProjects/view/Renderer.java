package petProjects.view;

import petProjects.model.WorldMap;

public interface Renderer {
    void render(WorldMap worldMap, int turn);
}
