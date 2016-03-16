/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

import java.text.DecimalFormat;
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
        new AStarTestVisual();
    }
    AMap map;
    ANode start;
    ANode goal;
    List<ANode> closedSet;
    Queue<ANode> openSet;
    boolean finished;
    APath path;
    ANode current;
    DecimalFormat df;

    public void update() {
        if (!finished && !openSet.isEmpty()) {
            current = openSet.poll();

//            System.out.println(df.format(current.fScore) + " " + df.format(current.gScore));
            current.explored = true;
            closedSet.add(current);

            if (current == goal) {
                path = new APath(goal);
                finished = true;
                System.out.println("FINISHED !!!!!");
                System.out.println(path);
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
    }

    private Iterable<ANode> getNeighbours(ANode current) {
        List<ANode> neighbours = new ArrayList<ANode>();
        current.walkable = true;
        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (isWalkable(current.x + x, current.y + y, current.z + z)) {
                        neighbours.add(map.get(current.x + x, current.y + y, current.z + z));
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
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private boolean isWalkable(int x, int y, int z) {
        return map.get(x, y, z) == null ? false : map.get(x, y, z).walkable;
    }

    public AStar() {
        this.df = new DecimalFormat("#.##");
        map = buildMap();
//        ANode start = map.get(rnd(10), rnd(10), rnd(5));
//        ANode end = map.get(rnd(10), rnd(10), rnd(5));
        start = map.get(0, 0, 0);
        goal = map.get(9, 9, 0);
        closedSet = new ArrayList<ANode>();
        openSet = new PriorityQueue<ANode>(1, new Comparator<ANode>() {
            public int compare(ANode a, ANode b) {
                if (a.fScore < b.fScore) {
                    return 1;
                } else if (a.fScore > b.fScore) {
                    return -1;
                }
                return 0;
            }
        });
        openSet.add(start);
    }

    private AMap buildMap() {
        AMap map = new AMap();
        int width = 10, depth = 10, height = 1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < depth; y++) {
                for (int z = 0; z < height; z++) {
                    map.insert(x, y, z, new ANode(x, y, z, true));
                }
            }
        }
        return map;
    }

    private int rnd(int i) {
        return (int) (Math.random() * i);
    }

    private double heuristic_cost_estimate(ANode neighbour, ANode goal) {
        return distance(neighbour, goal);
    }

    public APath calculatePath() {
        while (!finished && !openSet.isEmpty()) {
            ANode current = openSet.poll();
            current.explored = true;
            closedSet.add(current);

            if (current == goal) {
                path = new APath(goal);
                return path;
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
        return path;
    }
}
