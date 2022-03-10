package simulator.model;

public class NewJunctionEvent extends Event{
    private String id;
    private LightSwitchingStrategy lss;
    private DequeuingStrategy dqs;
    private int x, y;
    public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(time);
        this.id = id;
        lss = lsStrategy;
        dqs = dqStrategy;
        x = xCoor;
        y = yCoor;
    }

    @Override
    void execute(RoadMap map) {
        Junction j = new Junction(id, lss, dqs, x, y);
        map.addJunction(j);
    }

    @Override
    public String toString() {
        return "New Junction '" + id + "'";
    }
}
