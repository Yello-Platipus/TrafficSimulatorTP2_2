package simulator.view;

import simulator.control.Controller;

import javax.swing.*;

public class StatusBar extends JPanel {
    private Controller _ctrl;
    public StatusBar(Controller ctrl) {
        super();
        _ctrl = ctrl;
    }
}
