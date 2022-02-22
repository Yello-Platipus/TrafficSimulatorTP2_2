package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class SetWeatherEventBuilder extends Builder<Event>{
    SetWeatherEventBuilder() {
        super("set_weather");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        List<Pair<String, Weather>> lista = new ArrayList<Pair<String, Weather>>();
        for(int i = 0; i < data.getJSONArray("info").length(); i++)
            lista.add(new Pair<String, Weather> (data.getJSONArray("info").getJSONObject(i).getString("road"), Weather.valueOf(data.getJSONArray("info").getJSONObject(i).getString("weather"))));
        return new SetWeatherEvent(
            data.getInt("time"),
            lista
        );
    }
}
