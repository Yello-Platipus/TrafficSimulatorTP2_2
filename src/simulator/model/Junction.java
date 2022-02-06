package simulator.model;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject{
    private List<Road> roadList;
    private Map<Junction,Road> roadMap;
    private List<List<Vehicle >> colaList;
    //Map<Road,List<Vehicles>
    private int greenInd;
    private int lastTraficLight = 0;
    //estragegia de cambio de semáforo (de tipo LightSwitchingStrategy)
    //estrategia para extraer elementos de la cola (de tipo DequeuingStrategy)
    private int xCoor, yCoor;

    Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        if(lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0){
            this.xCoor = xCoor;
            this.yCoor = yCoor;
            //this.lsStrategy = lsStrategy;
            //this.dqStrategy = dqStrategy;
        }
        else{
            throw new IllegalArgumentException("Datos introducidos en la Junction erroneos");
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
