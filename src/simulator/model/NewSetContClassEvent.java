package simulator.model;

import simulator.misc.Pair;
import java.util.List;

public class NewSetContClassEvent extends Event{
    List<Pair<String,Integer>> cs;
    public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
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
                map.getVehicle(cs.get(i).getFirst()).setContaminationClass(cs.get(i).getSecond());
            }
            catch (Exception e){
                throw new IllegalArgumentException("The road does not exists (SetWeatherEvent");
            }


        }
    }
}
