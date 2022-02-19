package simulator.model;

public abstract class NewRoadEvent extends Event{
    private String id;
    private String srcJun, destJun;
    private int length, co2Limit, maxSpeed;
    private Weather weather;
    NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time);
        this.id = id;
        this.srcJun = srcJun;
        this.destJun = destJunc;
        this.length = length;
        this.co2Limit = co2Limit;
        this.maxSpeed = maxSpeed;
        this.weather = weather;

    }

    @Override
    abstract void execute(RoadMap map);
}
