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


public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {
    private final String TITLE_TEXT = "Schedule an event to change the CO2 class of a vehicle after a given number of \nsimulation ticks from now.\n";
    private final String VEHICLE_TEXT = "Vehicle: ";
    private final String CO2_TEXT = "CO2 Class: ";
    private final String TICKS_TEXT = "Ticks: ";
    private final int MAIN_WINDOW_WIDTH = 700;
    private final int MAIN_WINDOW_HEIGHT = 226;
    private final int COMBOBOX_WIDTH = 100;
    private final int COMBOBOX_HEIGHT = 75;
    private final int TEXT_SIZE = 18;
    private final int MAX_NUM_ARRAY = 10;

    private List<Vehicle> vehicleList;
    private Vehicle[] vehicleArray;
    private Integer[] numArray= new Integer[MAX_NUM_ARRAY];
    private Controller ctrl;
    private int ticksActuales;

    private JComboBox<Vehicle> boxVehicle;
    private JComboBox<Integer> boxCO2;
    private JSpinner spinner;

    public ChangeCO2ClassDialog(Controller ctrl) {
        super((JFrame) null,"Change CO2 Class",true);
        this.ctrl = ctrl;
        init();
    }
    private void init(){
        //Parametros JDialog
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(MAIN_WINDOW_WIDTH,MAIN_WINDOW_HEIGHT));
        this.setResizable(false);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-(MAIN_WINDOW_WIDTH/2),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-(MAIN_WINDOW_HEIGHT/2));

        //Parametros título (NORTH)
        iniTitle();

        //Inicializo el array de la contaminación
        for(int i =0; i< 10;i++){
            numArray[i]= i;
        }

        //Parametros elección valores (CENTRE)
        initCentralBut();

        //Inicialización parte Sur
        initSouthBut();


        this.ctrl.addObserver(this);
        this.setVisible(true);
    }


    private void iniTitle(){
        JTextArea text = new JTextArea(TITLE_TEXT);
        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,TEXT_SIZE));
        text.setHighlighter(null);
        text.setBackground(null);
        text.setEditable(false);
        this.add(text,BorderLayout.NORTH);
    }

    private void initCentralBut(){

        JPanel panelCentral = new JPanel();
        this.add(panelCentral,BorderLayout.CENTER);

        //Texto ComboBox Vehicle
        JLabel textVehicle = new JLabel(VEHICLE_TEXT);
        textVehicle.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,TEXT_SIZE));

        //ComboBox Vehicle
        boxVehicle = new JComboBox<Vehicle>();
        boxVehicle.setMaximumSize(new Dimension(COMBOBOX_WIDTH,COMBOBOX_HEIGHT));
        panelCentral.add(textVehicle);
        panelCentral.add(boxVehicle);

        //Texto ComboBox CO2
        JLabel textCO2 = new JLabel(CO2_TEXT);
        textCO2.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,TEXT_SIZE));

        //ComboBox CO2
        boxCO2 = new JComboBox<Integer>();
        boxCO2.setModel(new DefaultComboBoxModel<>(numArray));
        boxCO2.setMaximumSize(new Dimension( COMBOBOX_WIDTH,COMBOBOX_HEIGHT));
        panelCentral.add(textCO2);
        panelCentral.add(boxCO2);

        //Texto Spinner Ticks
        JLabel textTick = new JLabel(TICKS_TEXT);
        textTick.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,TEXT_SIZE));

        //Spinner Ticks
        spinner = new JSpinner(new SpinnerNumberModel(1,1,null,1)); //Preguntar profesor maximo
        spinner.setPreferredSize(new Dimension(80, 26));
        panelCentral.add(textTick);
        panelCentral.add(spinner);
    }

    private void initSouthBut(){
        JPanel panelInferior = new JPanel();
        this.add(panelInferior,BorderLayout.SOUTH);

        //Cancel Button
        JButton cancel = new JButton("Cancel");
        panelInferior.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeOption();
            }
        });

        //Ok Button
        JButton ok = new JButton("OK");
        panelInferior.add(ok);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vehicle vehiculo = (Vehicle)boxVehicle.getSelectedItem();
                int CO2 = (int)boxCO2.getSelectedItem();
                int ticks = (int)spinner.getValue();
                okOption(vehiculo,CO2,ticks);
                closeOption();
            }
        });
    }

    private void closeOption(){
        this.dispose();
    }


    private void okOption(Vehicle vehiculo,int CO2,int ticks){
        List<Pair<String,Integer>> listPar = new ArrayList<>();
        Pair<String,Integer> par = new Pair<>(vehiculo.getId(),CO2);
        listPar.add(par);
        ctrl.addEvent(new SetContClassEvent(ticksActuales + ticks,listPar));

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
        ticksActuales = time;

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
        ticksActuales = time;

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

        vehicleList = new ArrayList<Vehicle>();
        vehicleArray = new Vehicle[0];
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
        ticksActuales = 0;

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
        ticksActuales = time;

    }

    @Override
    public void onError(String err) {

    }
}
