package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private boolean stopped = false;
    private String myUnicodeSymbol = "\u00BF";

    private JButton fileChooser;
    private JButton changeC02Button;
    private JButton changeWeatherButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton exitButton;
    private JSpinner spinner;

    private Controller _ctrl;
    public ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;
        _ctrl.addObserver(this);
        this.setLayout(new BorderLayout());
        initGUI();
    }

    private void initGUI(){
        JToolBar toolBar = new JToolBar();
        this.add(toolBar);

        fileChooser = new JButton(( loadImage("open.png")));
        toolBar.add(fileChooser);
        toolBar.addSeparator();
        fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("resources/examples");
                chooser.setFileFilter(new FileNameExtensionFilter("JSON File", "json"));
                int option = chooser.showOpenDialog(null);
                if(option == JFileChooser.APPROVE_OPTION){
                    try{
                        File fichero = chooser.getSelectedFile();
                        InputStream file = new FileInputStream(fichero);
                        _ctrl.reset();
                        _ctrl.loadEvents(file);
                    }
                    catch (Exception exception){
                        JOptionPane.showMessageDialog((Frame)SwingUtilities.getWindowAncestor(ControlPanel.this),"Se produjo un error en la selección de archivos");
                    }
                }
            }
        });


        changeC02Button = new JButton((loadImage("co2class.png")));
        toolBar.add(changeC02Button);
        changeC02Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeCO2ClassDialog c = new ChangeCO2ClassDialog(_ctrl);
            }
        });


        changeWeatherButton = new JButton((loadImage("weather.png")));
        toolBar.add(changeWeatherButton);
        changeWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeWeatherDialog c = new ChangeWeatherDialog(_ctrl);
            }
        });
        toolBar.addSeparator();


        runButton = new JButton((loadImage("run.png")));
        toolBar.add(runButton);
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopped = false;
                enableToolBar(false);
                run_sim((Integer)spinner.getValue());
            }
        });


        stopButton = new JButton((loadImage("stop.png")));
        toolBar.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
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


        exitButton = new JButton((loadImage("exit.png")));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
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
        fileChooser.setEnabled(b);
        changeC02Button.setEnabled(b);
        changeWeatherButton.setEnabled(b);
        runButton.setEnabled(b);
        exitButton.setEnabled(b); // TODO PUEDE QUE NO HAYA QUE DESHABILITARLO
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
