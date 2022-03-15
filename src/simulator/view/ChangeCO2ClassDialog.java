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
    private List<Vehicle> vehicleList;
    private Vehicle[] vehicleArray;
    private Integer[] numArray= new Integer[10];
    private JComboBox<Vehicle> boxVehicle;
    private Controller ctrl;
    private int ticksActuales;

    public ChangeCO2ClassDialog(Controller ctrl) {
        super((JFrame) null,"Change CO2 Class",true);
        this.ctrl = ctrl;
        this.setLayout(new BorderLayout());
        JTextArea text = new JTextArea("Schedule an event to change the CO2 class of a vehicle after a given number of \nsimulation ticks from now.\n");
        text.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,18));
        text.setBackground(null);
        this.setSize(new Dimension(700,226));
        this.setResizable(false);
        this.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-350,(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2)-113);
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
        JPanel panelInferior = new JPanel();
        this.add(panelInferior,BorderLayout.SOUTH);
        JButton cancel = new JButton("Cancel");
        panelInferior.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeOption();
            }
        });
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
        this.ctrl.addObserver(this);
        this.setVisible(true);

    }

    private void closeOption(){
        this.dispose();
    }
    private void okOption(Vehicle vehiculo,int CO2,int ticks){
        List<Pair<String,Integer>> listPar = new ArrayList<>();
        Pair<String,Integer> par = new Pair<>(vehiculo.getId(),CO2);
        listPar.add(par);
        ctrl.addEvent(new SetContClassEvent(ticksActuales+ticks,listPar));

    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        boxVehicle.setModel(new DefaultComboBoxModel<Vehicle>(vehicleArray));
        ticksActuales = time;
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
