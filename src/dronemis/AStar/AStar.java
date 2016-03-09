/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Marcus Persson
 */
public class AStar {

    public static void main(String[] args) {
        new AStar();
    }
    ANode[][][] map;

    public APath calculatePath(ANode start, ANode goal) {
        Queue<ANode> closedSet = new PriorityQueue<ANode>();
        Queue<ANode> openSet = new PriorityQueue<ANode>(1, new Comparator<ANode>() {
            public int compare(ANode a, ANode b) {
                return (int) (a.gScore - b.gScore);
            }
        });
        openSet.add(start);
        while (!openSet.isEmpty()) {
            ANode current = openSet.poll();
            current.explored = true;
            closedSet.add(current);
            if (current == goal) {
                return new APath();
            }
            for (ANode neighbour : getNeighbours(current)) {
                if (closedSet.contains(neighbour)) {
                    continue;
                }
                double tenative_gScore = current.gScore + distance(current, neighbour);
                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                } else if (tenative_gScore >= neighbour.gScore) {
                    continue;
                }
                neighbour.parent = current;
                neighbour.gScore = tenative_gScore;
                neighbour.fScore = neighbour.gScore + heuristic_cost_estimate(neighbour, goal);

            }
        }
        return new APath();
    }

    private Iterable<ANode> getNeighbours(ANode current) {
        List<ANode> neighbours = new ArrayList<ANode>();
        current.walkable = true;
        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (isWalkable(x, y, z)) {
                        neighbours.add(map[x][y][z]);
                    }
                }
            }
        }
        current.walkable = false;
        return neighbours;
    }

    private double distance(ANode a, ANode b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dz = a.z - b.z;

        // We should avoid Math.pow or Math.hypot due to perfomance reasons
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private boolean isWalkable(int x, int y, int z) {
        return map[x][y][z].walkable;
    }

    public AStar() {
        map = buildMap();
        ANode start = map[rnd(10)][rnd(10)][rnd(5)];
        ANode end = map[rnd(10)][rnd(10)][rnd(5)];

    }

    private ANode[][][] buildMap() {
        ANode[][][] map = new ANode[10][10][5];

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                for (int z = 0; z < map[x][y].length; z++) {
                    map[x][y][z] = new ANode(x, y, z, true);
                }
            }
        }
        return map;
    }

    private int rnd(int i) {
        return (int) (Math.random() * i);
    }

    private double heuristic_cost_estimate(ANode neighbour, ANode goal) {
        return 0;
    }
}
