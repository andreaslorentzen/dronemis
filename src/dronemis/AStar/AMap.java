/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marcus Persson
 */
class AMap {

    Map<Integer, Map<Integer, Map<Integer, ANode>>> map;

    public AMap() {
        map = new HashMap<Integer, Map<Integer, Map<Integer, ANode>>>();
    }

    public void insert(int x, int y, int z, ANode node) {
        Map mapY = map.get(x);
        if (mapY == null) {
            // initialize map of x
            mapY = new HashMap<Integer, Map>();
            map.put(x, mapY);
        }
        Map mapZ = (Map) mapY.get(y);
        if (mapZ == null) {
            // initialize map of y
            mapZ = new HashMap<Integer, ANode>();
            mapY.put(y, mapZ);
        }
        mapZ.put(z, node);
    }

    ANode get(int x, int y, int z) {
        Map mapY = map.get(x);
        if (mapY == null) {
            return null;
        }
        Map mapZ = (Map) mapY.get(y);
        if (mapZ == null) {
            return null;
        }
        return (ANode) mapZ.get(z);
    }

}
