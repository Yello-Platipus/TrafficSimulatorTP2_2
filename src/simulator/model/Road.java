package simulator.model;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;
import javax.print.attribute.standard.JobHoldUntil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Road extends SimulatedObject{

	protected Junction srcJunc;
	protected Junction destJunc;
	protected int length;
    protected int maxSpeed;
    protected int actLimit;
    protected int contaminationLimit;
    protected Weather weather;
    protected int totalContamination = 0;
    private List<Vehicle> vehiclesInRoad;
    private Comparator<Vehicle> c;

    public Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id);

        if(maxSpeed < 0){
            throw new IllegalArgumentException("ERROR: Negative max speed ");
        }
        if(contLimit < 0){
            throw new IllegalArgumentException("ERROR: Negative contamination limit");
        }
        if(srcJunc == null || destJunc == null){
            throw new IllegalArgumentException("ERROR: Juction = null");
        }
        if(weather == null){
            throw new IllegalArgumentException("ERROR: Weather = null");
        }
        this.maxSpeed = maxSpeed;
        this.actLimit = maxSpeed;
        this.srcJunc = srcJunc;
        this.destJunc = destJunc;
        contaminationLimit = contLimit;
        this.length = length;
        this.weather = weather;
        c = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                if(o1.getLocation() < o2.getLocation()){
                    return 1;
                }
                else if(o1.getLocation() > o2.getLocation()){
                    return -1;
                }
                return 0;
            }
        };
        vehiclesInRoad = new SortedArrayList<Vehicle>(c);
    }

    @Override
    void advance(int time) {
        reduceTotalContamination();
        updateSpeedLimit();
        for(Vehicle veh: vehiclesInRoad){
            veh.setSpeed(calculateVehicleSpeed(veh));
            veh.advance(time);
        }
        vehiclesInRoad.sort(c);
    }

    void enter(Vehicle v){
        if(v.getLocation() == 0 && v.getSpeed() == 0){
            vehiclesInRoad.add(v);
        }
        else{
            throw new IllegalArgumentException("El coche o no está en la posición 0 o la velocidad no está en 0");
        }
        // Versión más bonita:
        /*
        if(v.getLocation() != 0 || v.getSpeed() != 0)
            throw new IllegalArgumentException("El coche o no está en la posición 0 o la velocidad no está en 0");
        vehiclesInRoad.add(v);
        */
    }

    void exit(Vehicle v){
        vehiclesInRoad.remove(v);
    }

    void setWeather(Weather w){
        if(w != null){
            weather = w;
        }
        else{
            throw new NullPointerException("El weather no está definido");
        }
        // Versión más bonita:
        /*
        if(w==null)
            throw new NullPointerException("El weather no está definido");
        weather = w;
        */
    }
    void addContamination(int c){
        if(c >= 0){
            totalContamination += c;
        }
        else{
            throw new IllegalArgumentException("No se admiten valores negativos");
        }
        // Versión más bonita:
        /*
        if(c < 0)
            throw new IllegalArgumentException("No se admiten valores negativos");
        totalContamination += c;
        */
    }
    abstract void reduceTotalContamination();
    abstract void updateSpeedLimit();
    abstract int calculateVehicleSpeed(Vehicle v);

    @Override
    public JSONObject report() {
        JSONObject road = new JSONObject();
        road.put("id",_id);
        road.put("speedlimit",actLimit);
        road.put("weather",weather.toString());
        road.put("co2",totalContamination);
        road.put("vehicles",vehiclesInRoad);
        return road;
    }
    public int getLength(){
        return length;
    }
    public Junction getDest(){
        return destJunc;
    }
    public Junction getSrc(){
        return srcJunc;
    }
    public Weather getWeather(){
        return weather;
    }
    public int getContLimit(){
        return contaminationLimit;
    }
    public int getMaxSpeed(){
        return maxSpeed;
    }
    public int getTotalCO2(){
        return totalContamination;
    }
    public int getSpeedLimit(){
        return actLimit;
    }
    public List<Vehicle> getVehicles(){
        return Collections.unmodifiableList(new ArrayList<>(vehiclesInRoad));
    }

}
