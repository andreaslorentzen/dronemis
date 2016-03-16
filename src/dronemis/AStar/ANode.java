/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

/**
 *
 * @author Marcus Persson
 */
class ANode {

    public int x, y, z;
    public boolean explored;
    public boolean walkable;
    public ANode parent;
    public double gScore;
    public double fScore;

    ANode(int x, int y, int z, boolean path) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.walkable = path;
    }

    Iterable<ANode> getNeighbours() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return x + ":" + y + ":" + z;
    }

}
