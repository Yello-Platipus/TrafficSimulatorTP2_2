package simulator.view;

import simulator.control.Controller;

import javax.swing.*;

public class ControlPanel extends JPanel {
    private Controller _ctrl;
    public ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;
    }
}
