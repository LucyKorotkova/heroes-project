package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {

    private final int WIDTH = 27;
    private final int HEIGHT = 21;

    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        boolean[][] blocked = new boolean[WIDTH][HEIGHT];

        for (Unit unit : existingUnitList) {
            if (!unit.equals(attackUnit)) {
                int x = unit.getxCoordinate();
                int y = unit.getyCoordinate();
                if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                    blocked[x][y] = true;
                }
            }
        }

        int startX = attackUnit.getxCoordinate();
        int startY = attackUnit.getyCoordinate();
        int targetX = targetUnit.getxCoordinate();
        int targetY = targetUnit.getyCoordinate();

        Queue<Edge> queue = new LinkedList<>();
        queue.add(new Edge(startX, startY));

        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        Edge[][] parent = new Edge[WIDTH][HEIGHT];

        visited[startX][startY] = true;

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            if (current.getX() == targetX && current.getY() == targetY) {
                break;
            }

            for (int[] dir : directions) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];

                if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT)
                    continue;

                if (!visited[newX][newY] && !blocked[newX][newY]) {
                    visited[newX][newY] = true;
                    parent[newX][newY] = current;
                    queue.add(new Edge(newX, newY));
                }
            }
        }


        List<Edge> path = new ArrayList<>();
        Edge current = new Edge(targetX, targetY);

        if (!visited[targetX][targetY]) {
            return path;
        }

        while (current != null && !(current.getX() == startX && current.getY() == startY)) {
            path.add(current);
            current = parent[current.getX()][current.getY()];
        }

        path.add(new Edge(startX, startY));
        Collections.reverse(path);
        return path;
    }
}