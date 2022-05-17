package ui;

import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.ProbabilityMassFunction;
import model.ProbabilityPair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;

public class VisualPmfApp extends JFrame {

    private static final String JSON_STORE = "./data/savedPmfs.json";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final int INITIAL_BAR_HEIGHT = 50;

    EventLog eventLog;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JButton createPmfButton;
    private JButton generateNumbersButton;
    private JButton saveButton;
    private JButton loadButton;

    private ButtonListener createPmfListener;
    private GenerateValuesListener generateButtonListener;
    private SaveListener saveButtonListener;
    private LoadListener loadButtonListener;

    private JTextField xval;
    private JTextField yval;
    private JButton addPairButton;
    private PairAdderListener pairAdderListener;

    private JPanel buttonPanel;
    private JPanel graphPanel;
    private JPanel resultsPanel;

    private JSlider numToGenerateSlider;
    private BarChart resultsChart;

    private BarChart pmfChart;

    private JLabel userPromptNumPairs;
    private JSlider pairSlider;

    private ProbabilityMassFunction pmf;

    public VisualPmfApp() {
        super("Probability Mass Function Tool");
        eventLog = EventLog.getInstance();
        pmf = new ProbabilityMassFunction();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializePmfChart();
        initializeButtonListeners();
        initializeInteraction();
        initializeButtons();
        initializePairAdder();
        initializePanels();

        initializeFrame();
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    // MODIFIES: this
    // EFFECTS: initializes Frame by adding all components and setup
    private void initializeFrame() {
        add(buttonPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.NORTH);
        add(pmfChart, BorderLayout.CENTER);
        add(resultsPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                EventLog eventLog = EventLog.getInstance();
                for (Event e : eventLog) {
                    System.out.println(e);
                }

                dispose();
                System.exit(0);
            }
        });
        setTitle("Probability Mass Function Creator");
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes panels in JFrame and slider
    private void initializePanels() {
        initializeSlider();

        initializeGraphPanel();

        initializeButtonPanel();

        initializeResultsPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes button panel and adds listeners
    private void initializeButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.add(createPmfButton);
        buttonPanel.add(generateNumbersButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        createPmfButton.addActionListener(createPmfListener);
        addPairButton.addActionListener(pairAdderListener);
        generateNumbersButton.addActionListener(generateButtonListener);
        saveButton.addActionListener(saveButtonListener);
        loadButton.addActionListener(loadButtonListener);

    }

    // MODIFIES: this
    // EFFECTS: initializes customizable graph panel in JFrame and slider
    private void initializeGraphPanel() {
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout(0, 1));
        graphPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        graphPanel.add(userPromptNumPairs);
        graphPanel.add(pairSlider);
        graphPanel.add(xval);
        graphPanel.add(yval);
        graphPanel.add(addPairButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes results panel in JFrame
    private void initializeResultsPanel() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(0, 1));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        resultsPanel.add(numToGenerateSlider);
        resultsPanel.add(resultsChart);
        resultsPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes sliders
    private void initializeSlider() {
        userPromptNumPairs = new JLabel("How many probability pairs do you want?: ");
        pairSlider = new JSlider(0, 16);
        pairSlider.setMinorTickSpacing(2);
        pairSlider.setMajorTickSpacing(10);
        pairSlider.setPaintTicks(true);
        pairSlider.setPaintLabels(true);

        numToGenerateSlider = new JSlider(0, 1000);
        numToGenerateSlider.setMajorTickSpacing(200);
        numToGenerateSlider.setPaintTicks(true);
        numToGenerateSlider.setPaintLabels(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    private void initializeButtons() {
        createPmfButton = new JButton("Create New Pmf");
        generateNumbersButton = new JButton("Generate Numbers");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
    }

    // MODIFIES: this
    // EFFECTS: initializes listeners
    private void initializeButtonListeners() {
        createPmfListener = new ButtonListener();
        pairAdderListener = new PairAdderListener();
        generateButtonListener = new GenerateValuesListener();
        saveButtonListener = new SaveListener();
        loadButtonListener = new LoadListener();
    }

    // MODIFIES: this
    // EFFECTS: initializes pair adder tool
    private void initializePairAdder() {
        xval = new JTextField();
        yval = new JTextField();
        addPairButton = new JButton("Add");
    }

    // MODIFIES: this
    // EFFECTS: initializes charts
    private void initializePmfChart() {
        pmfChart = new BarChart();
        resultsChart = new BarChart();
        resultsChart.setVisible(false);
    }

    // listener class for making pmf
    private class ButtonListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: resets state to new state
        @Override
        public void actionPerformed(ActionEvent event) {
            graphPanel.setVisible(true);
            resultsPanel.setVisible(false);

            pmfChart.clear();
            pmf = new ProbabilityMassFunction();
            pmfChart.repaint();
            repaint();
        }
    }

    // listener class for adding pairs to pmf
    private class PairAdderListener implements ActionListener {

        // adds probability pair to pmf when add button is clicked
        @Override
        public void actionPerformed(ActionEvent ae) {
            Double xdouble = Double.valueOf(xval.getText());
            Double ydouble = Double.valueOf(yval.getText());
            if (pmf.getPmf().size() < pairSlider.getValue()) {
                pmf.addProbabilityPair(xdouble, ydouble);
                pmfChart.addBar(new Color(15 * (int) Math.round(xdouble)), ydouble);
                xval.setText(String.valueOf(xdouble + 1.0));
            }
            pmfChart.repaint();
            repaint();
        }
    }

    // listener class for generating random values and adding to chart
    private class GenerateValuesListener implements ActionListener {
        private LinkedHashMap<Double, Integer> results = new LinkedHashMap<>();

        // MODIFIES: this
        // EFFECTS: generates random values from pmf and adds to bar chart and displays it
        @Override
        public void actionPerformed(ActionEvent ae) {
            graphPanel.setVisible(false);
            resultsPanel.setVisible(true);
            resultsChart.clear();
            results.clear();

            for (int i = 0; i < numToGenerateSlider.getValue(); i++) {
                Double newVal = pmf.generateRandomValue(numToGenerateSlider.getValue(), i);
                if (results.containsKey(newVal)) {
                    results.put(newVal, results.get(newVal) + 1);
                } else {
                    results.put(newVal, 1);
                }
            }

            for (ProbabilityPair pair : pmf.getPmf()) {
                Double key = pair.getXvalue();
                resultsChart.addBar(new Color(15 * (int) Math.round(key)), results.get(key));
            }
            resultsChart.setVisible(false);
            resultsChart.setVisible(true);
            repaint();
        }
    }

    // listener class for saving file
    private class SaveListener implements ActionListener {

        // EFFECTS: saves to json file
        @Override
        public void actionPerformed(ActionEvent ae) {
            // This method is modeled from JsonSerializationDemo
            // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            // MODIFIES: this
            // EFFECTS: writes pmf to file
            try {
                jsonWriter.open();
                jsonWriter.write(pmf);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

        }
    }

    // listener class for loading file
    private class LoadListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: loads from json file
        @Override
        public void actionPerformed(ActionEvent ae) {
            // This method is modeled from JsonSerializationDemo
            // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            // MODIFIES: this
            // EFFECTS: loads pmf from file
            try {
                pmf = jsonReader.read();
                for (ProbabilityPair pair : pmf.getPmf()) {
                    Double key = pair.getXvalue();
                    pmfChart.addBar(new Color(15 * (int) Math.round(key)), pair.getYvalue());
                }
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }

        }
    }

    public static void main(String[] args) {
        new VisualPmfApp();
    }
}