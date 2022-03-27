package simulator.view;

import com.sun.deploy.panel.JreTableModel;
import com.sun.jmx.snmp.SnmpOpaque;
import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class MainWindow extends JFrame {
    private final int TABLE_AND_MAP_WIDTH = 500;
    private final int TABLE_HEIGHT =200;
    private final int MAP_HEIGHT =400;

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
        JTable eventTable = setTablePref(new EventsTableModel(_ctrl));

        JPanel eventsView =
                createViewPanel(eventTable, "Events");
        eventsView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, TABLE_HEIGHT));
        tablesPanel.add(eventsView);

        JTable vehicleTable = setTablePref(new VehiclesViewTableModel(_ctrl));


        JPanel vehiclesView =
                createViewPanel(vehicleTable, "Vehicles");
        vehiclesView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, TABLE_HEIGHT));
        tablesPanel.add(vehiclesView);

        JTable roadTable = setTablePref(new roadsTableModel(_ctrl));


        JPanel roadsView =
                createViewPanel(roadTable, "Roads");
        roadsView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, TABLE_HEIGHT));
        tablesPanel.add(roadsView);

        JTable juntionTable = setTablePref(new junctionsTableModel(_ctrl));


        JPanel junctionsView =
                createViewPanel(juntionTable, "Junctions");
        junctionsView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, TABLE_HEIGHT));
        tablesPanel.add(junctionsView);
// ...
// maps
        JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
        mapView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, MAP_HEIGHT));
        mapsPanel.add(mapView);

        JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by Road");
        mapByRoadView.setPreferredSize(new Dimension(TABLE_AND_MAP_WIDTH, MAP_HEIGHT));
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
    private JTable setTablePref(AbstractTableModel model){
        JTable table = new JTable(model);
        table.getTableHeader().setBackground(Color.white);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.white));
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        return table;
    }
}

