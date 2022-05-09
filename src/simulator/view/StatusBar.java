package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements TrafficSimObserver {
    private Controller _ctrl;
    private int finalTime;
    private JLabel tiempo;
    private JLabel evento;
    public StatusBar(Controller ctrl) {
        super();
        this.setLayout(new BorderLayout());
        ctrl.addObserver(this);
        _ctrl = ctrl;
        initGUI();
    }
    void initGUI(){
        tiempo = new JLabel(" Time: "+ finalTime);
        tiempo.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        tiempo.setPreferredSize(new Dimension(130,30));
        this.add(tiempo,BorderLayout.WEST);
        evento = new JLabel(" ");
        this.add(evento,BorderLayout.CENTER);
    }
    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        finalTime = time;
        tiempo.setText(" Time: "+ finalTime);
        evento.setText(" ");
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        finalTime = time;
        tiempo.setText(" Time: "+ finalTime);
        evento.setText("Event added ("+e.toString()+")");

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        finalTime = time;
        tiempo.setText(" Time: "+ finalTime);

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        finalTime = time;
    }

    @Override
    public void onError(String err) {
        evento.setText(err);
    }
}
