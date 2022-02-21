package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

    private Factory<LightSwitchingStrategy> lssf;
    private Factory<DequeuingStrategy> dqsf;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lss, Factory<DequeuingStrategy> dqs){
        super("new_junction");
        this.lssf = lss;
        this.dqsf = dqs;
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        return new NewJunctionEvent(
                data.getInt("time"),
                data.getString("id"),
                lssf.createInstance(data.getJSONObject("ls_strategy")),
                dqsf.createInstance(data.getJSONObject("dq_strategy")),
                data.getJSONArray("coor").getInt(0),
                data.getJSONArray("coor").getInt((1))
        );
    }
}
