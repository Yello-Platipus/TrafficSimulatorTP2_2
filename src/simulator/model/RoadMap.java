package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RoadMap {
    private List<Junction> junctionList;
    private List<Road> roadList;
    private List<Vehicle> vehicleList;
    private Map<String, Junction> junctionMap;
    private Map<String, Road> roadMap;
    private Map<String, Vehicle> vehicleMap;

    protected RoadMap(){}

    private boolean checkVehicleValidity(Vehicle v){
        boolean valid = true;
        int i = 0;
        while(valid && i < v.getItinerary().size()){
            valid = v.getItinerary().get(i).connectedTo(v.getItinerary().get(i + 1));
            i++;
        }
        return valid;
    }

    public void addJunction(Junction j){
        for(Junction cosa : junctionList){
            if(cosa.getId() == j.getId())
                throw new IllegalArgumentException("Junction already in the list");
        }
        junctionList.add(j);
        junctionMap.put(j.getId(), j);
    }

    public void addRoad(Road r){
        for(Road cosa : roadList){
            if(cosa.getId() == r.getId())
                throw new IllegalArgumentException("Road already in the list");
            else if(cosa.getDest() == r.getDest() && cosa.getSrc() == r.getSrc())
                throw new IllegalArgumentException("Road connects two already connected Junctions");
        }
        roadList.add(r);
        roadMap.put(r.getId(), r);
    }

    public void addVehicle(Vehicle v){
        for(Vehicle cosa : vehicleList){
            if(cosa.getId() == v.getId())
                throw new IllegalArgumentException("Vehicle already in the list");
        }
        if(!checkVehicleValidity(v))
            throw new IllegalArgumentException("Itinerary is not connected");
        vehicleList.add(v);
        vehicleMap.put(v.getId(), v);
    }

    public Junction getJunction(String id){
        return junctionMap.get(id);
    }

    public Road getRoad(String id){
        return roadMap.get(id);
    }

    public Vehicle getVehicle(String id){
        return vehicleMap.get(id);
    }

    public List<Junction> getJunctions(){
        return Collections.unmodifiableList(new ArrayList<>(junctionList));
    }

    public List<Road> getRoads(){
        return Collections.unmodifiableList(new ArrayList<>(roadList));
    }

    public List<Vehicle> getVehicles(){
        return Collections.unmodifiableList(new ArrayList<>(vehicleList));
    }

    public void reset(){
        junctionList.clear();
        junctionMap.clear();
        roadList.clear();
        roadMap.clear();
        vehicleList.clear();
        vehicleMap.clear();
    }

    public JSONObject report(){
        return null;
        //TODO PATATA
    }

}
