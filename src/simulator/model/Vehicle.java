package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject{

    private int maxSpeed;
    private int currentSpeed;
    private int location;
    private int pollutionGroup;
    private int totalPolluted;
    private int distance;
    private List<Junction> itinerary;
    private VehicleStatus status;
    private Road currentRoad;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if(maxSpeed > 0 && contClass >= 0 && contClass <= 10 && itinerary.size() >= 2){
            this.maxSpeed = maxSpeed;
            pollutionGroup = contClass;
            this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        }
    }

    @Override
    void advance(int time) {

    }

    @Override
    public JSONObject report() {
        return null;
    }
}
