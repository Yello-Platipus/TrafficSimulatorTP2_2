package simulator.model;

import simulator.misc.Pair;
import java.util.List;

public class SetContClassEvent extends Event{
    List<Pair<String,Integer>> cs;
    public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
        super(time);

        if(cs == null){
            throw new IllegalArgumentException("The contamination class in NewSetContClassEvent is null");
        }
        this.cs = cs;
    }

    @Override
    void execute(RoadMap map) {
        for(int i = 0; i < cs.size();i++){
            try{
                map.getVehicle(cs.get(i).getFirst()).setContClass(cs.get(i).getSecond());
            }
            catch (Exception e){
                throw new IllegalArgumentException("The road does not exists (SetWeatherEvent");
            }


        }
    }
}
