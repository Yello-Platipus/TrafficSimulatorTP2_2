package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    private Controller _ctrl;
    public MainWindow(Controller ctrl) {
        super("Traffic Simulator");
        _ctrl = ctrl;
        initGUI();
    }
    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
        mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
        JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(viewsPanel, BorderLayout.CENTER);
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(tablesPanel);
        JPanel mapsPanel = new JPanel();
        mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(mapsPanel);
// tables
        JTable eventTable = new JTable(new EventsTableModel(_ctrl));
        eventTable.setShowGrid(false);
        JPanel eventsView =
                createViewPanel(eventTable, "Events");
        eventsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(eventsView);

        JTable vehicleTable = new JTable(new VehiclesViewTableModel(_ctrl));
        vehicleTable.setShowGrid(false);
        JPanel vehiclesView =
                createViewPanel(vehicleTable, "Events");
        vehiclesView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(vehiclesView);

        JTable roadTable = new JTable(new roadsTableModel(_ctrl));
        roadTable.setShowGrid(false);
        JPanel roadsView =
                createViewPanel(roadTable, "Events");
        roadsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(roadsView);

        JTable juntionTable = new JTable(new junctionsTableModel(_ctrl));
        juntionTable.setShowGrid(false);
        JPanel junctionsView =
                createViewPanel(juntionTable, "Events");
        junctionsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(junctionsView);
// ...
// maps
        JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
        mapView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapView);

        JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
        mapByRoadView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapByRoadView);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    private JPanel createViewPanel(JComponent c, String title) {
        JPanel p = new JPanel(new BorderLayout());
        Border borde = BorderFactory.createLineBorder(Color.black,2);
        p.setBorder(BorderFactory.createTitledBorder(borde,title));
        p.add(new JScrollPane(c));
        return p;
    }
}

