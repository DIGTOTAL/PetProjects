package petProjects.view;

import petProjects.model.Cell;
import petProjects.model.Entity;
import petProjects.model.WorldMap;
import petProjects.model.creatures.*;
import petProjects.model.objects.*;

public class ConsoleRenderer implements Renderer {
    private static final int CELL_WIDTH = 3; // ширина содержимого клетки

    @Override
    public void render(WorldMap worldMap, int turn) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();

        System.out.println("Turn: " + turn);

        int rowLabelWidth = Integer.toString(Math.max(0, height - 1)).length();

        // Header: индексы столбцов с вертикальными разделителями для выравнивания
        StringBuilder header = new StringBuilder();
        header.append(" ".repeat(rowLabelWidth)).append(" "); // место под метки строк
        for (int x = 0; x < width; x++) {
            header.append("|").append(centerPad(Integer.toString(x), CELL_WIDTH));
        }
        header.append("|");
        System.out.println(header);

        // Каждая строка: индекс + клетки с вертикальными разделителями
        for (int y = 0; y < height; y++) {
            StringBuilder row = new StringBuilder();
            row.append(padRight(Integer.toString(y), rowLabelWidth)).append(" "); // индекс строки

            for (int x = 0; x < width; x++) {
                Cell c = new Cell(x, y);
                Entity e = worldMap.getEntityAtCell(c);
                String sym = symbolForEntity(e);
                row.append("|").append(centerPad(sym, CELL_WIDTH));
            }
            row.append("|");
            System.out.println(row);
        }

        // Легенда
        System.out.println();
        System.out.println("Legend: P=Predator, H=Herbivore, R=Rock, T=Tree, G=Grass, .=empty");
        System.out.println();
    }

    private static String symbolForEntity(Entity e) {
        if (e == null) return ".";
        if (e instanceof Predator) return "P";
        if (e instanceof Herbivore) return "H";
        if (e instanceof Rock) return "R";
        if (e instanceof Tree) return "T";
        if (e instanceof Grass) return "G";
        return "?";
    }

    private static String centerPad(String s, int width) {
        if (s == null) s = "";
        if (s.length() >= width) return s.substring(0, width);
        int total = width - s.length();
        int left = total / 2;
        int right = total - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    private static String padRight(String s, int width) {
        if (s == null) s = "";
        if (s.length() >= width) return s.substring(0, width);
        return s + " ".repeat(width - s.length());
    }
}