package simulator.view;

import simulator.control.Controller;
import simulator.model.*;
import simulator.model.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ChangeCO2ClassDialog extends JDialog implements TrafficSimObserver {
    private List<Vehicle> vehicleList;
    private Vehicle[] vehicleArray;
    private Integer[] numArray= new Integer[10];
    private JComboBox<Vehicle> boxVehicle;
    private Vehicle seleccionVehiculo;
    private int seleccionCO2;

    public ChangeCO2ClassDialog(Controller ctrl) {
        super((JFrame) null,"Change CO2 Class",true);
        this.setLayout(new BorderLayout());
        JTextArea text = new JTextArea("Schedule an event to change the CO2 class of a vehicle after a given number of \nsimulation ticks from now");
        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        this.setSize(new Dimension(700,300));
        this.setResizable(false);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-350,(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-150);
        this.add(text,BorderLayout.NORTH);
        JPanel panelCentral = new JPanel();
        this.add(panelCentral,BorderLayout.CENTER);
        JLabel textVehicle = new JLabel("Vehicle: ");
        textVehicle.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        boxVehicle = new JComboBox<Vehicle>();
        boxVehicle.setMaximumSize(new Dimension(100,75));
        panelCentral.add(textVehicle);
        panelCentral.add(boxVehicle);
        for(int i =0; i< 10;i++){
            numArray[i]= i;
        }
        JLabel textCO2 = new JLabel("CO2 Class: ");
        textCO2.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        JComboBox<Integer> boxCO2 = new JComboBox<Integer>();
        boxCO2.setModel(new DefaultComboBoxModel<>(numArray));
        boxCO2.setMaximumSize(new Dimension(100,75));
        panelCentral.add(textCO2);
        panelCentral.add(boxCO2);
        JLabel textTick = new JLabel("Ticks: ");
        textTick.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1,1,null,1)); //Preguntar profesor maximo
        panelCentral.add(textTick);
        panelCentral.add(spinner);

        JButton cancel = new JButton("Cancel");
        cancel.setSize(new Dimension(40,30));
        this.add(cancel,BorderLayout.SOUTH);


        ctrl.addObserver(this);
        this.setVisible(true);

    }


    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

        vehicleList = new ArrayList<Vehicle>();
        vehicleArray = new Vehicle[0];
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        vehicleList = map.getVehicles();
        vehicleArray = new Vehicle[vehicleList.size()];
        for(int i = 0; i< vehicleList.size();i++){
            vehicleArray[i] = vehicleList.get(i);
        }
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
    }

    @Override
    public void onError(String err) {

    }
}
