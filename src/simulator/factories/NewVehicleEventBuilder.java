package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEventBuilder extends Builder<Event>{
    NewVehicleEventBuilder() {
        super("new_vehicle");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        List<String> lista = new ArrayList<String>();
        for(int i = 0; i < data.getJSONArray("itinerary").length(); i++)
            lista.add(data.getJSONArray("itinerary").getString(i));
        return new NewVehicleEvent(
                data.getInt("time"),
                data.getString("id"),
                data.getInt("maxSpeed"),
                data.getInt("class"),
                lista
        );
    }
}
