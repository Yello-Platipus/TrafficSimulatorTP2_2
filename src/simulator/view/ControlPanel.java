package simulator.view;

import jdk.nashorn.internal.scripts.JO;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

public class ControlPanel extends JPanel implements TrafficSimObserver {
    private ImageIcon _file;
    private ImageIcon _cont;
    private ImageIcon _cond;
    private ImageIcon _ejec;
    private ImageIcon _stop;
    private ImageIcon _exit;

    private Controller _ctrl;
    public ControlPanel(Controller ctrl) {
        super();
        this.setLayout(new BorderLayout());
        initGUI();
        _ctrl = ctrl;
        botoncicos();
    }
    private void initGUI(){
        _file = loadImage("open.png");
        _cont = loadImage("co2class.png");
        _cond = loadImage("weather.png");
        _ejec = loadImage("run.png");
        _stop = loadImage("stop.png");
        _exit = loadImage("exit.png");
    }
    private void botoncicos(){
        JToolBar toolBar = new JToolBar();
        this.add(toolBar);
        JButton boton1 = new JButton((_file));
        toolBar.add(boton1);
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
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
        JButton boton2 = new JButton((_cont));
        toolBar.add(boton2);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JButton boton3 = new JButton((_cond));
        toolBar.add(boton3);
        JButton boton4 = new JButton((_ejec));
        toolBar.add(boton4);
        JButton boton5 = new JButton((_stop));
        toolBar.add(boton5);
        JButton boton6 = new JButton((_exit));
        toolBar.add(new JSeparator());
        toolBar.add(boton6);
        boton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int option = JOptionPane.showConfirmDialog(null,"¿Quieres cerrar la aplicación?","",OK_CANCEL_OPTION);
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
