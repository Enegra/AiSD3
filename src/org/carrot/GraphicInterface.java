package org.carrot;

import javax.swing.*;

/**
 * Created by agnie on 4/26/2016.
 */
public class GraphicInterface extends JFrame{

    private void prepareGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250, 450);
        this.setLocationRelativeTo(null);
        this.setTitle("Sorting");
        OptionsPanel optionsPanel = new OptionsPanel();
        this.add(optionsPanel);
        this.setVisible(true);
    }

    public GraphicInterface(){
        prepareGUI();
    }

}
