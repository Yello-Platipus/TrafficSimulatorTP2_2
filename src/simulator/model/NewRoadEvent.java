package simulator.model;

public abstract class NewRoadEvent extends Event{
    protected String id;
    protected String srcJun, destJun;
    protected int length, co2Limit, maxSpeed;
    protected Weather weather;
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
    public String toString() {
        return "New Road '" + id + "'";
    }

}
