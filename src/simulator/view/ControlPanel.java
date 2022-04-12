package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.lang.System;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

public class ControlPanel extends JPanel implements TrafficSimObserver {
    private ImageIcon file;
    private ImageIcon cont;
    private ImageIcon cond;
    private ImageIcon exec;
    private ImageIcon stop;
    private ImageIcon exit;
    private boolean stopped = false;
    private String myUnicodeSymbol = "\u00BF";

    private JButton boton1;
    private JButton boton2;
    private JButton boton3;
    private JButton boton4;
    private JButton boton5;
    private JButton boton6;
    private JSpinner spinner;

    private Controller _ctrl;
    public ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;
        _ctrl.addObserver(this);
        this.setLayout(new BorderLayout());
        initGUI();
        botoncicos();
    }
    private void initGUI(){
        file = loadImage("open.png");
        cont = loadImage("co2class.png");
        cond = loadImage("weather.png");
        exec = loadImage("run.png");
        stop = loadImage("stop.png");
        exit = loadImage("exit.png");
    }
    private void botoncicos(){
        JToolBar toolBar = new JToolBar();
        this.add(toolBar);

        boton1 = new JButton((file));
        toolBar.add(boton1);
        toolBar.addSeparator();
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("resources");
                int option = chooser.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION){
                    try{
                        File fichero = chooser.getSelectedFile();
                        InputStream file = new FileInputStream(fichero);
                        _ctrl.reset();
                        _ctrl.loadEvents(file);
                    }
                    catch (Exception exception){
                        System.out.println("El fichero no existe o saltó una excepción en loadEvents");
                    }
                }
            }
        });

        boton2 = new JButton((cont));
        toolBar.add(boton2);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeCO2ClassDialog c = new ChangeCO2ClassDialog(_ctrl);
            }
        });

        boton3 = new JButton((cond));
        toolBar.add(boton3);
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeWeatherDialog c = new ChangeWeatherDialog(_ctrl);
            }
        });
        toolBar.addSeparator();

        boton4 = new JButton((exec));
        toolBar.add(boton4);
        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopped = false;
                enableToolBar(false);
                run_sim((Integer)spinner.getValue());
            }
        });

        boton5 = new JButton((stop));
        toolBar.add(boton5);
        boton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        //Texto Spinner Ticks
        JLabel textTick = new JLabel(" Ticks: ");
        textTick.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        toolBar.add(textTick);

        //Spinner Ticks
        spinner = new JSpinner(new SpinnerNumberModel(1,1,null,1)); //Preguntar profesor maximo
        spinner.setMaximumSize(new Dimension(3499, 25));
        toolBar.add(spinner);

        boton6 = new JButton((exit));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(boton6);
        boton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,myUnicodeSymbol + "Quieres cerrar la aplicacion?","",OK_CANCEL_OPTION);
                if(option == JFileChooser.APPROVE_OPTION){
                    System.exit(0);
                }
            }
        });
    }
    private ImageIcon loadImage(String img) {
        ImageIcon i = null;
        i = new ImageIcon("resources/icons/" + img);
        return i;
    }

    private void run_sim(int n) {
        if (n > 0 && !stopped) {
            try {
                _ctrl.GUIrun(1);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has ocurred during the execution.\n");
                stopped = true;
                return;
            }
            try {
                Thread.sleep(100 / (Integer) 1);
            }
            catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "An error has ocurred during the execution.\n");
                return;
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    run_sim(n - 1);
                }
            });
        }
        else {
            enableToolBar(true);
            stopped = true;
        }
    }

    private void enableToolBar(boolean b) {
        boton1.setEnabled(b);
        boton2.setEnabled(b);
        boton3.setEnabled(b);
        boton4.setEnabled(b);
        boton6.setEnabled(b); // TODO PUEDE QUE NO HAYA QUE DESHABILITARLO
    }

    private void stop() {
        stopped = true;
    }


    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

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
