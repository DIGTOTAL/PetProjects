package petProjects.model;

import java.util.*;

public class PathFinder {
    public static List<Cell> findShortestPathTo(WorldMap worldMap, Cell start, List<Cell> targets) {

        if (targets.isEmpty()) {
            return List.of();
        }

        Set<Cell> targetSet = new HashSet<>(targets);

        if (targetSet.contains(start)) {
            return List.of(start);
        }

        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Cell> parent = new HashMap<>();
        Set<Cell> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            for (Cell neighbor : worldMap.getNeighboringCells(currentCell)) {
                if (!worldMap.isCellInsideMap(neighbor) || visited.contains(neighbor)) continue;
                Entity e = worldMap.getEntityAtCell(neighbor);
                boolean passable = (e == null);
                if (!passable) continue;


                visited.add(neighbor);
                parent.put(neighbor, currentCell);

                if (targetSet.contains(neighbor)) {
                    return reconstructPath(parent, start, neighbor);
                }
                queue.add(neighbor);
            }
        }
        return List.of();
    }

    private static List<Cell> reconstructPath(Map<Cell, Cell> parent, Cell start, Cell end) {
        LinkedList<Cell> path = new LinkedList<>();
        Cell cur = end;
        while (cur != null) {
            path.addFirst(cur);
            if (cur.equals(start)) break;
            cur = parent.get(cur);
        }
        return path;
    }
}