package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VehiclesViewTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller ctrl;
    private List<Vehicle> vehiclesList;
    private final int numeroColumnas = 8;

    public VehiclesViewTableModel(Controller ctrl){
        this.ctrl = ctrl;
        this.ctrl.addObserver(this);
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
                VehicleStatus status = vehiclesList.get(rowIndex).getStatus();
                if(status == VehicleStatus.TRAVELING){
                    return (vehiclesList.get(rowIndex).getRoad().getId() + ":" + vehiclesList.get(rowIndex).getLocation());
                }
                else if(status== VehicleStatus.ARRIVED){
                    return "Arrived";
                }
                else if( status== VehicleStatus.WAITING){
                    return "Waiting:"+ vehiclesList.get(rowIndex).getItinerary().get(vehiclesList.get(rowIndex).getIndex());
                }


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
        update(map);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        vehiclesList = new ArrayList<Vehicle>();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {

    }
    public void update(RoadMap map){
        this.vehiclesList = map.getVehicles();
        this.fireTableDataChanged();
    }
}
