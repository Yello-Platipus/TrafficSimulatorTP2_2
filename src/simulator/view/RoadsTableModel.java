package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Road;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller ctrl;
    private List<Road> roadList;
    private final int numeroColumnas = 7;

    public RoadsTableModel(Controller ctrl){
        
        this.ctrl = ctrl;
        this.ctrl.addObserver(this);
    }
    @Override
    public int getRowCount() {
        return roadList.size();
    }

    @Override
    public int getColumnCount() {
        return numeroColumnas;
    }
    @Override
    public String getColumnName(int column) {

        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Length";
            case 2:
                return "Weather";
            case 3:
                return "Max.Speed";
            case 4:
                return "Speed Limit";
            case 5:
                return "Total CO2";
            default:
                return "CO2 Limit";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return roadList.get(rowIndex).getId();
            case 1:
                return roadList.get(rowIndex).getLength();
            case 2:
                return roadList.get(rowIndex).getWeather();
            case 3:
                return roadList.get(rowIndex).getMaxSpeed();
            case 4:
                return roadList.get(rowIndex).getSpeedLimit();
            case 5:
                return roadList.get(rowIndex).getTotalCO2();
            default:
                return roadList.get(rowIndex).getContLimit();
        }
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        this.roadList = map.getRoads();
        this.fireTableDataChanged();
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this.roadList = map.getRoads();
        this.fireTableDataChanged();
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        roadList = new ArrayList<Road>();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        this.roadList = map.getRoads();
        this.fireTableDataChanged();

    }

    @Override
    public void onError(String err) {

    }
}
