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
    private int itineraryIndex;
    private List<Junction> itinerary;
    private VehicleStatus status;
    private Road currentRoad;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if(maxSpeed < 1){
            throw new IllegalArgumentException("ERROR: Negative max speed ");
        }
        if(contClass < 0 || contClass > 10){
            throw new IllegalArgumentException("ERROR: Contamination class out of bounds");
        }
        if(itinerary.size() < 2){
            throw new IllegalArgumentException("ERROR: Itinerary size lower that 2");
        }
        this.maxSpeed = maxSpeed;
        contaminationClass = contClass;
        this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        itineraryIndex = -1;
        status = VehicleStatus.PENDING;
    }
// Excepciones que vamos a usar : illegal argument, run time exception, null pointer exception
    @Override
    protected void advance(int time) {
        if(status == VehicleStatus.TRAVELING){
            int prevLocation = location;
            location = Math.min(location + currentSpeed, currentRoad.getLength());
            totalContaminated += (location - prevLocation) * contaminationClass;
            currentRoad.addContamination((location - prevLocation) * contaminationClass);
            if(location >= currentRoad.getLength()){
                itinerary.get(itineraryIndex).enter(this);
                status = VehicleStatus.WAITING;
            }
            distance += location - prevLocation;
        }
    }

    void moveToNextRoad(){
        if(status != VehicleStatus.PENDING && status != VehicleStatus.WAITING)
            throw new IllegalArgumentException("ERROR: Vehicle is moving");
        if(status != VehicleStatus.PENDING)
            currentRoad.exit(this);
        if(status == VehicleStatus.PENDING) {
            itinerary.get(0).roadTo(itinerary.get(1)).enter(this);
            itineraryIndex = 0;
            distance = 0;
            location=0;
            status = VehicleStatus.TRAVELING;
            currentRoad = itinerary.get(0).roadTo(itinerary.get(1));
            itineraryIndex++;
        }
        else if(itineraryIndex < itinerary.size() - 1) {
            location = 0;
            currentSpeed = 0;
            
            itinerary.get(itineraryIndex).roadTo(itinerary.get(itineraryIndex + 1)).enter(this);
            itineraryIndex++;
        }
        else {
            status = VehicleStatus.ARRIVED;
        }

    }

    @Override
    public JSONObject report() {
        JSONObject vehicle = new JSONObject();
        vehicle.put("id",_id);
        vehicle.put("speed",currentSpeed);
        vehicle.put("distance",distance);
        vehicle.put("co2",totalContaminated);
        vehicle.put("class",contaminationClass);
        vehicle.put("status",status.toString());
        if(status == VehicleStatus.TRAVELING || status == VehicleStatus.WAITING ) {
        	vehicle.put("location",location);
            vehicle.put("road",currentRoad);
        }
        return vehicle;
    }

    protected void setSpeed(int s){
        if (s >= 0) {
        	if(status == VehicleStatus.TRAVELING)
        		currentSpeed = Math.min(maxSpeed,s);
        }
        else  {
            throw new IllegalArgumentException("ERROR: Velocidad menor que 0");
        }

    }
    protected void setContClass(int c){
        if (0 <= c && c <= 10) {
            contaminationClass = c;
        }
        else {
            throw new IllegalArgumentException("ERROR: Contaminacion mayor que 10 o menor que 0");
        }

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
        return Collections.unmodifiableList(new ArrayList<>(itinerary));
    }
    public Road getRoad(){
        return currentRoad;
    }
}
