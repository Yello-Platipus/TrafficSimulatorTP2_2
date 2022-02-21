package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

    private int ticks;

    private int searchMaxLength(List<Road> roads, List<List<Vehicle>> qs, int startingPoint){
        int max = qs.get(startingPoint).size();
        for(int i = 1; i < roads.size(); i++){
            if(qs.get((startingPoint + i) % roads.size()).size() > max)
                max = qs.get((startingPoint + i) % roads.size()).size();
        }
        return max;
    }

    public MostCrowdedStrategy(int timeSlot){
        ticks = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if(roads.isEmpty())
            return -1;
        else if (currGreen == -1)
            return searchMaxLength(roads, qs, 0);
        else if(currTime - lastSwitchingTime < ticks)
            return currGreen;
        else
            return searchMaxLength(roads, qs, currGreen);
    }


}
