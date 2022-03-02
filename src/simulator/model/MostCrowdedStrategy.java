package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

    private int ticks;

    private int searchMaxLength(List<Road> roads, List<List<Vehicle>> qs, int startingPoint){
        int max = qs.get((startingPoint + 1) % roads.size()).size();
        int index = (startingPoint + 1) % roads.size();
        for(int i = 0; i < roads.size(); i++){
            if(qs.get((startingPoint + i) % roads.size()).size() > max) {
                max = qs.get((startingPoint + i) % roads.size()).size();
                index = (startingPoint + i) % roads.size();
            }
        }
        return index;
    }
    
    private int searchMaxLength(List<Road> roads, List<List<Vehicle>> qs){
    	int max = qs.get(0).size();
        int index = 0;
        for(int i = 0; i < roads.size(); i++){
            if(qs.get(i).size() > max) {
                max = qs.get(i).size();
                index = (i) % roads.size();
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
        else if (currGreen == -1)
            return searchMaxLength(roads, qs);
        else if(currTime - lastSwitchingTime < ticks)
            return currGreen;
        else
            return searchMaxLength(roads, qs, currGreen);
    }


}
