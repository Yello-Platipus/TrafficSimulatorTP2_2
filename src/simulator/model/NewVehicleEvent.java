package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event  {
    private String id;
    private int maxSpeed, contClass;
    private List<String> itinerary;

    public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        List<Junction> aux = new ArrayList<>();//No se si es así o así ArrayList<Junction> aux = new ArrayList<>();
        for (int i = 0; i < itinerary.size();i++){
            aux.add(map.getJunction(itinerary.get(i)));
        }
        Vehicle vehicle = new Vehicle( id,  maxSpeed,  contClass, aux);
        map.addVehicle(vehicle);
        vehicle.moveToNextRoad();
    }
}
