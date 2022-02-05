package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject{

    private int maxSpeed;
    private int currentSpeed;
    private int location;
    private int contaminationClass;
    private int totalContaminated;
    private int distance;
    private List<Junction> itinerary;
    private VehicleStatus status;
    private Road currentRoad;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if(maxSpeed > 0 && contClass >= 0 && contClass <= 10 && itinerary.size() >= 2){
            this.maxSpeed = maxSpeed;
            contaminationClass = contClass;
            this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        }
    }

    @Override
    protected void advance(int time) {
        if(status == VehicleStatus.TRAVELING){
            if(location + currentSpeed < currentRoad.getLength())
                location += currentSpeed;
            else
                location = currentRoad.getLength();


        }
    }

    @Override
    public JSONObject report() {
        return null;
    }

    protected void setSpeed(int s){
        currentSpeed = s;
    }
    protected void setContaminationClass(int c){
        contaminationClass = c;
    }
    public int getLocation(){
        return location;
    }
    public int getSpeed(){
        return currentSpeed;
    }
    public int getMaxSpeed(){
        return maxSpeed;
    }
    public int getContClass(){
        return contaminationClass;
    }
    public VehicleStatus getStatus(){
        return status;
    }
    public int getTotalCO2(){
        return totalContaminated;
    }
    public List<Junction> getItinerary(){
        return itinerary;
    }
    public Road getRoad(){
        return currentRoad;
    }
}
