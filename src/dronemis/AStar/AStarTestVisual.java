/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.AStar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import physicsLibrary.Edge3D;
import physicsLibrary.Model3D;
import physicsLibrary.V3;

/**
 *
 * @author Marcus Persson
 */
public class AStarTestVisual extends JPanel implements KeyListener {

    JFrame frame;
    AStarRenderSystem asrs;
    AStar astar;

    public static void main(String[] args) {
        new AStarTestVisual();
    }

    AStarTestVisual() {
        frame = new JFrame();
        frame.setTitle("Line x Ellipse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.add(this);
        frame.setVisible(true);
        frame.addKeyListener(this);
        this.setBackground(Color.BLACK);
        frame.createBufferStrategy(2);
        buffer = frame.getBufferStrategy();
        asrs = new AStarRenderSystem();
        astar = new AStar();
        timeLast = System.currentTimeMillis();
        start();
    }

    long timeLast;
    int timeReq = 100000;
    int timeAccumulator;

    public void update() {
        timeAccumulator += System.currentTimeMillis() - timeLast;
        if (timeAccumulator >= timeReq) {
            timeAccumulator -= timeReq;
            astar.update();
            System.out.println("UPDATE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            if (astar.finished) {
                List<Model3D> models = new ArrayList<Model3D>();
                for (ANode node : astar.path.path) {
                    if (node.parent == null) {
                        continue;
                    }
                    V3[] points = new V3[2];
                    points[0] = new V3(node.x, node.y, node.z).mul(50);
                    points[1] = new V3(node.parent.x, node.parent.y, node.parent.z).mul(50);
                    Edge3D[] edges = new Edge3D[1];
                    edges[0] = new Edge3D(0, 1, Color.WHITE);
                    Model3D model = new Model3D(points, edges, null, null);
                    models.add(model);
                }
                asrs.models = models;
            } else {
//                System.out.println(astar.openSet);
                List<Model3D> models = new ArrayList<Model3D>();
                for (ANode node : astar.openSet) {
                    if (node.parent == null) {
                        continue;
                    }
                    V3[] points = new V3[2];
                    points[0] = new V3(node.x, node.y, node.z).mul(50);
                    points[1] = new V3(node.parent.x, node.parent.y, node.parent.z).mul(50);
                    Edge3D[] edges = new Edge3D[1];
//                    edges[0] = new Edge3D(0, 1, Color.WHITE);
                    edges[0] = new Edge3D(0, 1, new Color((int) node.fScore * 8, 50, 50));
                    Model3D model = new Model3D(points, edges, null, null);
                    System.out.println(node.fScore);
                    models.add(model);
                }
                ANode node = astar.current;
                if (node.parent != null) {
                    V3[] points = new V3[2];
                    points[0] = new V3(node.x, node.y, node.z).mul(50);
                    points[1] = new V3(node.parent.x, node.parent.y, node.parent.z).mul(50);
                    Edge3D[] edges = new Edge3D[1];
                    edges[0] = new Edge3D(0, 1, Color.RED);
                    Model3D model = new Model3D(points, edges, null, null);
                    models.add(model);
                }

                asrs.models = models;
            }
        }
    }
    BufferStrategy buffer;
    BufferedImage bi;
    Graphics g;

    public void render() {
        if (bi == null) {
            bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        if (bi.getWidth() != getWidth() || bi.getHeight() != getHeight()) {
            bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        g = bi.createGraphics();
        // g.setColor(background);
//        g.fillRect(0, 0, getWidth(), getHeight());
        // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        // RenderingHints.VALUE_ANTIALIAS_ON);
        asrs.render(bi);
        g = (Graphics2D) buffer.getDrawGraphics();
        g.drawImage(bi, 0, 0, null);
        if (!buffer.contentsLost()) {
            buffer.show();
        }
    }

    public void start() {
        double previous = System.nanoTime() / 1000000000.0;
        double lag = 0.0;
        double MS_PER_UPDATE = 1.0 / 100.0;
        while (true) {
            double current = System.nanoTime() / 1000000000.0;
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;
            // System.out.println(lag);

            while (lag >= MS_PER_UPDATE) {
                update();
                lag -= MS_PER_UPDATE;
            }

//            render(lag / MS_PER_UPDATE);
            render();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    double rotScale = 1;
    double speedScale = 2;
    double forward_speed = speedScale,
            vertical_speed = speedScale,
            horizontal_speed = speedScale;
    double pitch_speed = 0.000005 * rotScale, yaw_speed = 0.000005 * rotScale, roll_speed = 0.000005 * rotScale;

    @Override
    public void keyPressed(KeyEvent e) {
        double forward = 0, vertical = 0, horizontal = 0, pitch = 0, yaw = 0, roll = 0;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                forward = forward_speed; // forward
                break;
            case KeyEvent.VK_S:
                forward = -forward_speed; // backwards
                break;
            case KeyEvent.VK_A:
                vertical = -vertical_speed; // left
                break;
            case KeyEvent.VK_D:
                vertical = vertical_speed; // right
                break;
            case KeyEvent.VK_CONTROL:
                horizontal = horizontal_speed; // backwards
                break;
            case KeyEvent.VK_SHIFT:
                horizontal = -horizontal_speed; // right
                break;
        }

        V3 move = asrs.camera.D.mul(forward).add(asrs.camera.R.mul(vertical)).add(asrs.camera.U.mul(horizontal));
        asrs.camera.moveTo(asrs.camera.E.add(move));
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
