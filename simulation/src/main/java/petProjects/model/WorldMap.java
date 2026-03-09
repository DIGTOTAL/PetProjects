package petProjects.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {
    private final int width;
    private final int height;
    private final Map<Cell, Entity> cells = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Cell> getCellsWithEntity(Class<? extends Entity> type) {
        return cells.entrySet().stream()
                .filter(entry -> entry.getValue() != null && type.isInstance(entry.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    // Возвращает соседние клетки в порядке: Up, Down, Left, Right.
    // Система координат: (0,0) — верхний левый угол; x увеличивается вправо, y увеличивается вниз.
    public List<Cell> getNeighboringCells(Cell cell) {
        return List.of(
                new Cell(cell.getX(), cell.getY() - 1), // Up (y-1)
                new Cell(cell.getX(), cell.getY() + 1), // Down (y+1)
                new Cell(cell.getX() - 1, cell.getY()), // Left
                new Cell(cell.getX() + 1, cell.getY())  // Right
        );
    }

    public boolean isCellInsideMap(Cell cell) {
        return cell.getX() >= 0 && cell.getX() < width && cell.getY() >= 0 && cell.getY() < height;
    }

    public void placeEntityOnCell(Cell cell, Entity entity) {
        if (!isCellInsideMap(cell)) return;
        // защита от перезаписи существующей сущности
        if (getEntityAtCell(cell) != null) {
            // можно логировать или бросать исключение, но по умолчанию просто игнорируем
            return;
        }
        cells.put(cell, entity);
    }

    public void removeEntityFromCell(Cell cell) {
        cells.remove(cell);
    }

    public boolean isCellPassable(Cell cell) {
        return isCellInsideMap(cell) && getEntityAtCell(cell) == null;
    }

    public Entity getEntityAtCell(Cell cell) {
        return cells.get(cell);
    }
}