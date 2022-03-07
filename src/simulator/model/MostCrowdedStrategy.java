package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

    private int ticks;

    private int searchMaxLength(List<Road> roads, List<List<Vehicle>> qs, int startingPoint){
        int max = qs.get((startingPoint + 1) % roads.size()).size();
        int index = (startingPoint + 1) % roads.size();
        for(int i = 1; i < roads.size(); i++){
            if(qs.get((index + i) % roads.size()).size() > max) {
                max = qs.get((index + i) % roads.size()).size();
                index = (index + i) % roads.size();
            }
        }
        return index;
    }

    public MostCrowdedStrategy(int timeSlot){
        ticks = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if(roads.isEmpty())
            return -1;
        else if(currTime - lastSwitchingTime < ticks)
            return currGreen;
        else
            return searchMaxLength(roads, qs, currGreen);
    }


}
