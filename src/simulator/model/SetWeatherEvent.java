package simulator.model;

import simulator.misc.Pair;
import java.util.List;

public class SetWeatherEvent extends Event{
    List<Pair<String,Weather>> ws;
    public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
        super(time);
        if(ws == null){
            throw new IllegalArgumentException("The weather list in SetWeatherEvent is null");
        }
        this.ws = ws;
    }

    @Override
    void execute(RoadMap map) {
        for(int i = 0; i < ws.size();i++){
            try{
                map.getRoad(ws.get(i).getFirst()).setWeather(ws.get(i).getSecond());
            }
            catch (Exception e){
                throw new IllegalArgumentException("The road does not exists (SetWeatherEvent");
            }
        }
    }
}
