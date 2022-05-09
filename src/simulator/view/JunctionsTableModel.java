package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
    private Controller ctrl;
    private List<Junction> junctionList;
    private final int numeroColumnas = 3;

    public JunctionsTableModel(Controller ctrl){

        this.ctrl = ctrl;
        this.ctrl.addObserver(this);
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
                if(junctionList.get(rowIndex).getGreenLightIndex() == -1){
                    return "NONE";
                }
                else{
                    return junctionList.get(rowIndex).getInRoads().get(junctionList.get(rowIndex).getGreenLightIndex());
                }
            default:
                if(junctionList.get(rowIndex).getQueueMap().isEmpty()){
                    return null;
                }
                else{
                    String ret = new String();
                    for(Road r : junctionList.get(rowIndex).getInRoads())
                        ret += r + ":" + junctionList.get(rowIndex).getQueueMap().get(r) + " ";
                    return ret;
                }
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
    public void update(RoadMap map){
        this.junctionList = map.getJunctions();
        this.fireTableDataChanged();
    }
}
