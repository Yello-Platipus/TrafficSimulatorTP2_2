package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements  LightSwitchingStrategy{
    private int ticks;
    RoundRobinStrategy(int timeSlot){
        ticks = timeSlot;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        return 0;
    }
}
