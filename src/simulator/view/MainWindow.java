package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
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
        JPanel eventsView =
                createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
        eventsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(eventsView);
        JPanel vehiclesView =
                createViewPanel(new JTable(new VehiclesViewTableModel(_ctrl)), "Events");
        vehiclesView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(vehiclesView);

        JPanel roadsView =
                createViewPanel(new JTable(new roadsTableModel(_ctrl)), "Events");
        roadsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(roadsView);

        JPanel junctionsView =
                createViewPanel(new JTable(new junctionsTableModel(_ctrl)), "Events");
        junctionsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(junctionsView);
// ...
// maps
        JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
        mapView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapView);

        JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map");
        mapByRoadView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapByRoadView);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    private JPanel createViewPanel(JComponent c, String title) {
        JPanel p = new JPanel( new BorderLayout() );
        TitledBorder titulo;
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.setBorder(BorderFactory.createTitledBorder(title));

// TODO add a framed border to p with title
        p.add(new JScrollPane(c));
        return p;
    }
}

