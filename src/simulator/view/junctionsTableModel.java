package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class junctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
    private Controller ctrl;
    private List<Junction> junctionList;
    private final int numeroColumnas = 3;

    public junctionsTableModel(Controller ctrl){

        this.ctrl = ctrl;
        ctrl.addObserver(this);
    }
    @Override
    public int getRowCount() {
        return junctionList.size();
    }


    @Override
    public int getColumnCount() {
        return numeroColumnas;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return junctionList.get(rowIndex).getId();
            case 1:
                //return junctionList.get(rowIndex);
            default:
                //return roadTo(junctionList.get(rowIndex));
                return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Green";
            default:
                return "Queues";

        }
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this.junctionList = map.getJunctions();
        this.fireTableDataChanged();
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        this.junctionList = map.getJunctions();
        this.fireTableDataChanged();
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        junctionList = new ArrayList<Junction>();

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        this.junctionList = map.getJunctions();
        this.fireTableDataChanged();
    }

    @Override
    public void onError(String err) {

    }
}
