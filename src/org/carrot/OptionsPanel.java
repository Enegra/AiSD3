package org.carrot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by agnie on 4/26/2016.
 */
public class OptionsPanel extends JPanel {

    JLabel sizeLabel, variantLabel, algorithmLabel, researchLabel, iterationsLabel;
    JTextField sizeTextField, iterationsTextfield;
    JButton generateButton;
    ButtonGroup radioGroup;
    JRadioButton unsortedRadioButton, sortedRadioButton, reversedRadioButton, partlySortedRadioButton;
    JSlider sortSlider;
    JComboBox algorithmComboBox;
    JCheckBox researchCheckbox;
    Tester tester = new Tester();


    public OptionsPanel() {
        setupPanel();
    }

    private void setupPanel() {
        this.setLayout(new GridLayout(0, 1, 0, 3));
        this.setAlignmentX(LEFT_ALIGNMENT);
        setupAlgorithmLabel();
        setupAlgorithmComboBox();
        setupSizeLabel();
        setupSizeTextField();
        setupVariantLabel();
        setupRadioButtons();
        setupSortSlider();
        setupResearchLabel();
        setupResearchCheckbox();
        setupIterationsLabel();
        setupIterationsTextField();
        setupButton();
        this.setVisible(true);
    }

    private void setupSizeLabel() {
        sizeLabel = new JLabel("Set array size");
        this.add(sizeLabel);
    }

    private void setupVariantLabel() {
        variantLabel = new JLabel("Pick array variant");
        this.add(variantLabel);
    }

    private void setupRadioButtons() {
        radioGroup = new ButtonGroup();
        radioGroup.add(unsortedRadioButton = new JRadioButton("Unsorted"));
        this.add(unsortedRadioButton);
        unsortedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortSlider.setVisible(false);
            }
        });
        radioGroup.add(sortedRadioButton = new JRadioButton("Sorted"));
        this.add(sortedRadioButton);
        sortedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortSlider.setVisible(false);
            }
        });
        radioGroup.add(reversedRadioButton = new JRadioButton("Sorted descending"));
        this.add(reversedRadioButton);
        reversedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortSlider.setVisible(false);
            }
        });
        radioGroup.add(partlySortedRadioButton = new JRadioButton("Partially sorted"));
        this.add(partlySortedRadioButton);
        partlySortedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortSlider.setVisible(true);
            }
        });
    }


    private void setupSizeTextField() {
        sizeTextField = new JTextField("Set the size");
        this.add(sizeTextField);
    }

    private void setupAlgorithmComboBox() {
        String[] algorithms = {"bubble sort", "selection sort", "insertion sort", "bucket sort", "merge sort", "comb sort", "shell sort", "quick sort", "radix sort", "library sort"};
        algorithmComboBox = new JComboBox(algorithms);
        this.add(algorithmComboBox);

    }

    private void setupSortSlider() {
        sortSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
        this.add(sortSlider);
        sortSlider.createStandardLabels(25, 0);
        sortSlider.setMajorTickSpacing(25);
        sortSlider.setMinorTickSpacing(5);
        sortSlider.setSnapToTicks(true);
        sortSlider.setPaintTicks(true);
        sortSlider.setPaintLabels(true);
        sortSlider.setVisible(false);
    }

    private void setupAlgorithmLabel() {
        algorithmLabel = new JLabel("Choose algorithm");
        this.add(algorithmLabel);
    }

    private void setupResearchLabel() {
        researchLabel = new JLabel("Research");
        this.add(researchLabel);
    }

    private void setupResearchCheckbox() {
        researchCheckbox = new JCheckBox("Run multiple tests");
        researchCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (researchCheckbox.isSelected()) {
                    iterationsLabel.setVisible(true);
                    iterationsTextfield.setVisible(true);
                } else {
                    iterationsLabel.setVisible(false);
                    iterationsTextfield.setVisible(false);
                }
            }
        });
        this.add(researchCheckbox);
    }

    private void setupIterationsLabel() {
        iterationsLabel = new JLabel("Set number of iterations");
        this.add(iterationsLabel);
        iterationsLabel.setVisible(false);
    }

    private void setupIterationsTextField() {
        iterationsTextfield = new JTextField("Set the number of iterations");
        this.add(iterationsTextfield);
        iterationsTextfield.setVisible(false);
    }

    private void setupButton() {
        generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sizeTextField.getText().matches("\\d+$")) {
                    JOptionPane.showMessageDialog(getParent(),
                            "The size of desired array must be an integer.",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int algorithmIndex = algorithmComboBox.getSelectedIndex();
                    int size = Integer.parseInt(sizeTextField.getText());
                    int selectedType = 0;
                    int threshold=0;
                    if (unsortedRadioButton.isSelected()) {
                        selectedType = 1;
                    } else if (sortedRadioButton.isSelected()) {
                        selectedType = 2;
                    } else if (reversedRadioButton.isSelected()) {
                        selectedType = 3;
                    } else if (partlySortedRadioButton.isSelected()) {
                        selectedType = 4;
                        threshold = sortSlider.getValue();
                    } else {
                        JOptionPane.showMessageDialog(getParent(),
                                "You must select the desired type of input array.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (researchCheckbox.isSelected()){
                        if (!iterationsTextfield.getText().matches("\\d+$")) {
                            JOptionPane.showMessageDialog(getParent(),
                                    "The number of iterations must be an integer.",
                                    "Invalid input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            int iterations = Integer.parseInt(iterationsTextfield.getText());
                            if (selectedType==4){
                                tester.research(iterations, algorithmIndex, size, (double)(threshold/100));
                                printScores();
                            }
                            else {
                                tester.research(iterations, algorithmIndex, selectedType, size);
                                printScores();
                            }
                        }
                    }
                    else {
                        if (selectedType==4){
                            tester.test(algorithmIndex, size, (double)(threshold/100));
                            printScores();
                        }
                        else {
                            tester.test(algorithmIndex, selectedType, size);
                            printScores();
                        }
                    }
                }

            }
        });
        this.add(generateButton);
    }

    private void printScores(){
        System.out.println("Average time: " + tester.getAverageTime());
        System.out.println("Best time: " + tester.getBestTime());
        System.out.println("Worst time: " + tester.getWorstTime());
        System.out.println("Standard deviation: " + tester.getStandardDeviation());
        System.out.println();
    }

}
