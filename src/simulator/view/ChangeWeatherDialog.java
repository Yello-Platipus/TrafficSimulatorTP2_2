package simulator.view;

import simulator.control.Controller;
import simulator.model.*;
import simulator.model.Event;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver {

    private List<Road> roadList;
    private JComboBox<Road> roadBox;
    private JComboBox<Weather> statusBox;
    private Controller ctrl;
    private int currTime;

    public ChangeWeatherDialog(Controller ctrl){
        super((JFrame) null, "Change Road Weather", true);
        this.ctrl = ctrl;
        init();
    }

    private void init(){
        // Inicializacion de parámetros
        statusBox = new JComboBox<>(Weather.values());
        // Tamaños y posicion
        this.setSize(new Dimension(700,226));
        this.setLayout(new BorderLayout(20, 20));
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-350,(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-113);
        statusBox.setSize(new Dimension(80, 26));
        roadBox.setSize(new Dimension(80, 26));
        // Añadir cosas
        this.add(setText(), BorderLayout.NORTH);
        JPanel panelCentral = new JPanel();
        this.add(panelCentral,BorderLayout.CENTER);

        // Mas cosas
        this.ctrl.addObserver(this);
        this.setVisible(true);
    }

    private JTextArea setText(){
        JTextArea text = new JTextArea("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        text.setHighlighter(null);
        text.setBackground(null);
        text.setEditable(false);
        return text;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

        currTime = time;
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
