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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import physicsLibrary.Model3D;

import physicsLibrary.V3;
import physicsLibrary.VirtualCamera;

public class AStarRenderSystem {

    VirtualCamera camera;
    List<Model3D> models;

    AStarRenderSystem() {
        models = new ArrayList<Model3D>();
        camera = new VirtualCamera(50, 50, 450, 450);
        camera.moveTo(new V3(500, 750, 500));
        camera.focus(new V3(0, 0, 0));
    }

    protected void render(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        g.setColor(Color.white);
        for (Model3D model : models) {
            g.setColor(Color.white);
            camera.drawLine(g, model.vertices[model.edges[0].a], model.vertices[model.edges[0].b], model.edges[0].color);

        }
        // cameraystem.exit(1);

        V3 mul = camera.D.mul(20).add(camera.R.mul(-10)).add(camera.U.mul(-10));
        V3 o = new V3().add(camera.E).add(mul);

        g.setColor(Color.yellow);
//        if (camera.velocity.magnitude() < 5) {
//            camera.drawLine(g, o, camera.velocity.add(camera.E).add(mul));
//        } else {
//            camera.drawLine(g, o, camera.velocity.unit().mul(5).add(camera.E).add(mul));
//        }

        camera.drawAxes(g);
    }

}
