package simulator.view;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;
import simulator.model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChangeWeatherDialog extends JDialog implements TrafficSimObserver {

    private static final String ROAD_TEXT = "Road:";
    private static final String WEATHER_TEXT = "Weather:";
    private static final String TICKS_TEXT = "Ticks:";

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
        roadBox = new JComboBox<Road>();
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1,1,null,1));
        spinner.setPreferredSize(new Dimension(80, 26));

        // Tamaños y posicion
        this.setResizable(false);
        this.setSize(new Dimension(700,226));
        this.setLayout(new BorderLayout(20, 20));
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-350,(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-113);
        statusBox.setSize(new Dimension(80, 26));
        roadBox.setSize(new Dimension(80, 26));

        // Descripcion del funcionamiento de la pestaña
        this.add(setDescription(), BorderLayout.NORTH);

        // Añadir al panel central
        JPanel panelCentral = new JPanel();
        panelCentral.add(setLabel(ROAD_TEXT));
        panelCentral.add(roadBox);
        panelCentral.add(setLabel(WEATHER_TEXT));
        panelCentral.add(statusBox);
        panelCentral.add(setLabel(TICKS_TEXT));
        panelCentral.add(spinner);
        this.add(panelCentral,BorderLayout.CENTER);

        //Añadir al panel inferior
        JPanel panelInferior = new JPanel();
        JButton cancel = new JButton("Cancel");
        panelInferior.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeWeatherDialog.this.dispose();
            }
        });
        JButton ok = new JButton("OK");
        panelInferior.add(ok);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Road r = (Road)roadBox.getSelectedItem();
                Weather w = (Weather) statusBox.getSelectedItem();
                int ticks = (int)spinner.getValue();
                okOption(r, w, ticks);
                ChangeWeatherDialog.this.dispose();
            }
        });
        this.add(panelInferior, BorderLayout.SOUTH);

        // Mas cosas
        this.ctrl.addObserver(this);
        this.setVisible(true);
    }

    private JTextArea setDescription(){
        JTextArea text = new JTextArea("Schedule an event to change the weather of a road after a given number of simulation ticks from now");
        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        text.setHighlighter(null);
        text.setBackground(null);
        text.setEditable(false);
        return text;
    }

    private JLabel setLabel(String text){
        JLabel roadText = new JLabel(text);
        roadText.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        return roadText;
    }


    private void okOption(Road r, Weather w, int ticks){
        List<Pair<String,Weather>> listPar = new ArrayList<>();
        Pair<String,Weather> par = new Pair<>(r.getId(), w);
        listPar.add(par);
        ctrl.addEvent(new SetWeatherEvent(currTime + ticks, listPar));

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        roadBox.setModel(new DefaultComboBoxModel<Road>(map.getRoadArray()));
        currTime = time;
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        roadBox.setModel(new DefaultComboBoxModel<Road>(map.getRoadArray()));
        currTime = time;
    }

    @Override
    public void onError(String err) {

    }
}
