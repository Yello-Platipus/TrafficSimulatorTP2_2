package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.*;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {

    private static final long serialVersionUID = 1L;

    private static final int _JRADIUS = 10;

    private static final Color _BG_COLOR = Color.WHITE;
    private static final Color _JUNCTION_COLOR = Color.BLUE;
    private static final Color _JUNCTION_LABEL_COLOR = new Color(208,100,4, 255);
    private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
    private static final Color _RED_LIGHT_COLOR = Color.RED;

    private RoadMap _map;

    private Image _car;

    public MapByRoadComponent(Controller ctrl) {
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        setPreferredSize(new Dimension(300, 200));
        _car = loadImage("car.png");
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // clear with a background color
        g.setColor(_BG_COLOR);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (_map == null || _map.getJunctions().size() == 0) {
            g.setColor(Color.red);
            g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
        } else {
            drawMap(g);
        }
    }

    private void drawMap(Graphics g) {
        for(Road r : _map.getRoads()){
            drawRoad(g, r);
            drawVehicles(g, r);
            drawJunctions(g, r);
            drawWeather(g, r);
            drawIcon(g, r);
        }
    }

    private void drawRoad(Graphics g, Road r) {
        int x1 = 50, x2 = getWidth() - 100, y = (_map.getRoads().indexOf(r) + 1) * 50;
        g.setColor(Color.BLACK);
        g.drawString(r.getId(), 15, y);
        g.drawLine(x1, y, x2, y);
    }

    private void drawVehicles(Graphics g, Road r) {
        for (Vehicle v : r.getVehicles()) {
            if (v.getStatus() != VehicleStatus.ARRIVED) {

                int x1 = 50, x2 = getWidth() - 100, y = (_map.getRoads().indexOf(r) + 1) * 50;

                double f = ((float)v.getLocation()) / r.getLength();
                int vX = (int)(x1 + (x2 - x1) * f);

                // Choose a color for the vehcile's label and background, depending on its
                // contamination class
                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                g.setColor(new Color(0, vLabelColor, 0));

                // draw an image of a car (with circle as background) and it identifier
                g.drawImage(_car, vX, y - 6, 16, 16, this);
                g.drawString(v.getId(), vX, y - 6);
            }
        }
    }

    private void drawJunctions(Graphics g, Road r) {
        int x1 = 50, x2 = getWidth() - 100, y = (_map.getRoads().indexOf(r) + 1) * 50;

        g.setColor(_JUNCTION_COLOR);
        g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(r.getSrc().getId(), x1, y - 6);

        Color c = r.getDest().isGreen(r) ? _GREEN_LIGHT_COLOR : _RED_LIGHT_COLOR;

        g.setColor(c);
        g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);
        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(r.getDest().getId(), x2, y - 6);
    }

    private void drawWeather(Graphics g, Road r){
        int x = getWidth() - 80, y = (_map.getRoads().indexOf(r) + 1) * 50 - 10;
        Image i;
        switch(r.getWeather()){
            case SUNNY:
                i = loadImage("sun.png");
                break;
            case CLOUDY:
                i = loadImage("cloud.png");
                break;
            case RAINY:
                i = loadImage("rain.png");
                break;
            case WINDY:
                i = loadImage("wind.png");
                break;
            case STORM:
                i = loadImage("storm.png");
                break;
            default: //TODO PUEDE DAR ERROR (UNHANDLED EXCEPTION?)
                throw new IllegalStateException("Unexpected value: " + r.getWeather());
        }
        g.drawImage(i, x, y, 32, 32, this);
    }

    private void drawIcon(Graphics g, Road r){
        int x = getWidth() - 40, y = (_map.getRoads().indexOf(r) + 1) * 50 - 10;
        int c = (int) Math.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getContLimit()), 1.0) / 0.19);
        Image i;
        switch(c) {
            case 0:
                i = loadImage("cont_0.png");
                break;
            case 1:
                i = loadImage("cont_1.png");
                break;
            case 2:
                i = loadImage("cont_2.png");
                break;
            case 3:
                i = loadImage("cont_3.png");
                break;
            case 4:
                i = loadImage("cont_4.png");
                break;
            case 5:
                i = loadImage("cont_5.png");
                break;
            default: //TODO PUEDE DAR ERROR (UNHANDLED EXCEPTION?)
                throw new IllegalStateException("Unexpected value: " + c);
        }
        g.drawImage(i, x, y, 32, 32, this);
    }

    // loads an image from a file
    private Image loadImage(String img) {
        Image i = null;
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
        }
        return i;
    }

    public void update(RoadMap map) {
        SwingUtilities.invokeLater(() -> {
            _map = map;
            repaint();
        });
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {
    }

}
