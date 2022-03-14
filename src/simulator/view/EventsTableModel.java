package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
    private Controller ctrl;
    private final int numeroColumnas = 2;
    private List<Event> events;
    public EventsTableModel(Controller ctrl){
        this.ctrl = ctrl;
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return numeroColumnas;
    }
    @Override
    public String getColumnName(int column) {

        switch (column) {
            case 0:
                return "Time";
            default:
                return "Desc.";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return events.get(rowIndex);
            default:
                return events.get(rowIndex).toString();
        }

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        this.events = events;
        this.fireTableDataChanged();
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this.events = events;
        this.fireTableDataChanged();
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        this.events = new ArrayList<Event>();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        this.events = events;
        this.fireTableDataChanged();
    }

    @Override
    public void onError(String err) {
        //TODO
    }
}
