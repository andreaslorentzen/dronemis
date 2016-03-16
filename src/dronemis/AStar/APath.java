/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcus Persson
 */
class APath {

    List<ANode> path;

    APath(ANode current) {
        path = new ArrayList<ANode>();
        path.add(current);
        while (current.parent != null) {
            current = current.parent;
            path.add(current);
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (ANode node : path) {
            string += node + "\n";
        }
        return string;
    }

}
