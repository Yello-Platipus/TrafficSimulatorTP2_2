package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VehiclesViewTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller ctrl;
    private List<Vehicle> vehiclesList;
    private final int numeroColumnas = 8;

    public VehiclesViewTableModel(Controller ctrl){
        this.ctrl = ctrl;
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return vehiclesList.size();

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
                return "Location";
            case 2:
                return "Itinerary";
            case 3:
                return "CO2 Class";
            case 4:
                return "Max.Speed";
            case 5:
                return "Speed";
            case 6:
                return "Total CO2";
            default:
                return "Distance";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return vehiclesList.get(rowIndex).getId();
            case 1:
                return vehiclesList.get(rowIndex).getLocation();
            case 2:
                return vehiclesList.get(rowIndex).getItinerary();
            case 3:
                return vehiclesList.get(rowIndex).getContClass();
            case 4:
                return vehiclesList.get(rowIndex).getMaxSpeed();
            case 5:
                return vehiclesList.get(rowIndex).getSpeed();
            case 6:
                return vehiclesList.get(rowIndex).getTotalCO2();
            default:
                return vehiclesList.get(rowIndex).getDistance();
        }
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        this.vehiclesList = map.getVehicles();
        this.fireTableDataChanged();
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this.vehiclesList = map.getVehicles();
        this.fireTableDataChanged();
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        vehiclesList = new ArrayList<Vehicle>();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        this.vehiclesList = map.getVehicles();
        this.fireTableDataChanged();
    }

    @Override
    public void onError(String err) {

    }
}
