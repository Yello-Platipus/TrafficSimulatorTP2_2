package simulator.model;

public class InterCityRoad extends Road{


    InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {
        int atmCondition=0;
        switch (weather){
            case SUNNY:
                atmCondition = 2;
                break;
            case CLOUDY:
                atmCondition = 3;
                break;
            case RAINY:
                atmCondition = 10;
                break;
            case WINDY:
                atmCondition = 15;
                break;
            case STORM:
                atmCondition = 20;
                break;
        }
        totalContamination = ((100-atmCondition)*totalContamination)/100;
    }

    @Override
    void updateSpeedLimit() {
        if(totalContamination > contaminationLimit){
            actLimit = maxSpeed/2;
        }
        else{
            actLimit = maxSpeed;
        }
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        if(weather != Weather.STORM){
            return actLimit;
        }
        else{
            return (actLimit*8)/10;
        }
    }
}
