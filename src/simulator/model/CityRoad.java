package simulator.model;

public class CityRoad extends Road{

    public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {//Asegúrate de que el total de contaminación no se vuelve negativo.
        int atmCondition=0;
        switch (weather){
            case WINDY:
                atmCondition = 10;
            case STORM:
                atmCondition = 10;
                break;
            default:
                atmCondition = 2;


        }
        totalContamination = totalContamination - atmCondition;
        totalContamination = Math.max(totalContamination,0);
    }

    @Override
    void updateSpeedLimit() {
        actLimit = maxSpeed;
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {

        return ((11-v.getContClass())*actLimit)/11;
    }
}
