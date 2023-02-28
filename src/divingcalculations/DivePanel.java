package divingcalculations;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DivePanel {
    /**
     * Declare variables
     */
    DiveFormulas db;
    JSpinner pg;
    JSpinner fg;
    JSpinner d;
    JSpinner startO2;
    JSpinner startDep;
    JSpinner endO2;
    JSpinner endDep;
    JTextArea display;
    JPanel redPanel = new JPanel(new BorderLayout());
    JPanel greenPanel;
    JScrollPane tableContainer;
    JSlider depthSlider;
    JSlider oxySlider;
    JTable table;
    String result = "";
    String [] columnName;
    String [][] ppo2Table;
    String [][] eadTable;
    AbstractButton abstractButton;
    int SMOD;
    int MOD;
    int EAD;
    double PP;
    int fgValue;

    public DivePanel() {db = new DiveFormulas();}


    //Set the window
        public void createWindow() {
            //Creating the Frame
            JFrame frame = new JFrame("Diving calculations");
            //Add mainPanel to frame
            frame.getContentPane().add(mainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Set the size of frame
            frame.setSize(1250, 900);
            frame.setVisible(true);
        }

    //Create mainPanel to hold everything

        public Component mainPanel(){
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(null);
            mainPanel.add(getYellowPanel());
            mainPanel.add(getGreenPanel());
            mainPanel.add(getSimpleCalPanel());
            mainPanel.add(getBluePanel());
            mainPanel.add(getRedPanel());

            return mainPanel;
        }

    private JPanel getYellowPanel() {
        //Create panel to do simple calculation
        JPanel yellowPanel = new JPanel(new BorderLayout());
        yellowPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));

        //Add title label
        String space = "                                              ";
        JLabel label1 = new JLabel(space+"Standard Calculations");
        label1.setFont(new Font("Helvetica", Font.BOLD, 20));
        yellowPanel.add(label1);
        yellowPanel.setBounds(10,5,800,30);
        return yellowPanel;
        }

    private JPanel getSimpleCalPanel() {
        // Add a 1*3 panel to hold checkbox, image, and textarea
        JPanel simpleCalPlane = new JPanel();
        simpleCalPlane.setLayout(new GridLayout(1, 3));

        /**
         * Create a 5*1 panel to include 5 check box
         */
        //Create a panel for checkbox with 5*1 grid layout
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(5, 1));
        checkboxPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Calculation", TitledBorder.CENTER, TitledBorder.TOP));

        JCheckBox modCheckBox = new JCheckBox("Maximum Operating Depth");
        modCheckBox.addActionListener(new modAction());
        checkboxPanel.add(modCheckBox);

        JCheckBox smodCheckBox = new JCheckBox("Standard Maximum Operating Depth");
        checkboxPanel.add(smodCheckBox);
        smodCheckBox.addActionListener(new smodAction());

        JCheckBox bmCheckBox = new JCheckBox("Best mix");
        checkboxPanel.add(bmCheckBox);
        bmCheckBox.addActionListener(new bmAction());

        JCheckBox ppCheckBox = new JCheckBox("Partial Pressure");
        checkboxPanel.add(ppCheckBox);
        ppCheckBox.addActionListener(new ppAction());

        JCheckBox eadCheckBox = new JCheckBox("Equivalent Air Depth");
        checkboxPanel.add(eadCheckBox);
        eadCheckBox.addActionListener(new eadAction());

        simpleCalPlane.add(checkboxPanel);

        /**
         * display image in simpleCalPlane
         */
        JLabel label2;
        // Put 'Circled-T.png' to the folder which includes all classes
        //Questions: Sometimes the image will disappear from the folder
        ImageIcon image1 = new ImageIcon(divingcalculations.DivePanel.class.getResource("/divingcalculations/Circled-T.png"));
        label2 = new JLabel(image1);
        label2.setLayout( new FlowLayout() );
        simpleCalPlane.add(label2);
        /**
         * create three JSpinner for Pg Fg  P
         */
        pg= new JSpinner(new SpinnerNumberModel(1.4, 1.1, 1.6, 0.1));
        fg= new JSpinner(new SpinnerNumberModel(24, 0, 100, 1));
        d= new JSpinner(new SpinnerNumberModel(43, 0, 100, 1));
        //adjust spinner fit the image
        pg.setBorder(BorderFactory.createEmptyBorder(85, 80, 60, 65));
        fg.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
        d.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
        label2.add(pg);
        label2.add(fg);
        label2.add(d);
        //create a panel to hold textarea
        JPanel textPlane = new JPanel(new BorderLayout());
        simpleCalPlane.add(textPlane);
        /**
         * create text field and add scroll
         */
        display = new JTextArea();
        JScrollPane scroll = new JScrollPane(display);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPlane.add(scroll);
        //Create do calculation button and add actionListener
        JButton doCalculation = new JButton("Do calculations");
        doCalculation.addActionListener(new calAction());

        doCalculation.setPreferredSize(new Dimension(200, 20));
        textPlane.add(doCalculation, BorderLayout.SOUTH);
        simpleCalPlane.setBounds(10,50,900,250);
        return simpleCalPlane;
    }

    private JPanel getGreenPanel() {
        //Create greenPanel to hold two sliders and one progressbar
            greenPanel = new JPanel(new BorderLayout());
            greenPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        /**
         * create progress bar and two sliders to show the percentage of oxygen dynamically
         */
        //Add a slider for oxygen
        oxySlider = new JSlider(JSlider.VERTICAL, 0, 100, 60);
        oxySlider.setPaintTicks(true);
        oxySlider.setPaintLabels(true);
        oxySlider.setMajorTickSpacing(20);
        oxySlider.setMinorTickSpacing(5);
        oxySlider.setBorder(new TitledBorder("% O2"));
        oxySlider.setPreferredSize(new Dimension(80, 400));
        oxySlider.setMajorTickSpacing(10);
        oxySlider.setMinorTickSpacing(1);

        greenPanel.add(oxySlider, BorderLayout.EAST);

        //Add a slider for depth
        depthSlider = new JSlider(JSlider.VERTICAL, 0, 100, 60);
        depthSlider.setPaintTicks(true);
        depthSlider.setPaintLabels(true);
        depthSlider.setMajorTickSpacing(20);
        depthSlider.setMinorTickSpacing(5);
        depthSlider.setBorder(new TitledBorder("Depth (m)"));
        depthSlider.setPreferredSize(new Dimension(80, 400));
        depthSlider.setMajorTickSpacing(10);
        depthSlider.setMinorTickSpacing(1);

        greenPanel.add(depthSlider, BorderLayout.WEST);

        //Create a vertical progressBar to show the percentage of O2 and Nitrogen
        JProgressBar progressBar = new JProgressBar(JProgressBar.VERTICAL);
        progressBar.setModel(oxySlider.getModel()); // Share model with oxygen slider
        progressBar.setSize(new Dimension(80,400));
        progressBar.setStringPainted(true);

        //Add label on the top and bottom of the progress bar
        JPanel barPanel = new JPanel(new BorderLayout());
        JLabel oxygen = new JLabel("      % O2");
        JLabel nitrogen = new JLabel("     % N2");
        barPanel.add(oxygen, BorderLayout.SOUTH);
        barPanel.add(nitrogen, BorderLayout.NORTH);
        barPanel.add(progressBar, BorderLayout.CENTER);

        //Add progress bar to the green panel
        greenPanel.add(barPanel, BorderLayout.CENTER);

        greenPanel.setBounds(950,50,250,500);
            return greenPanel;
    }

    private JPanel getBluePanel() {
        //Create a bluePanel to hold Tabular Calculation
        JPanel bluePanel = new JPanel();
        bluePanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        bluePanel.setLayout(null);

        // Add title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        String space = "                                              ";
        JLabel label2 = new JLabel(space+"Tabular Calculations");
        label2.setFont(new Font("Helvetica", Font.BOLD, 20));
        titlePanel.add(label2);
        titlePanel.setBounds(10,5,900,30);
        bluePanel.add(titlePanel);

        //Create a spinnerPanel to hold all spinners
        JPanel spinnerPanel = new JPanel(new GridLayout(2,4));
        spinnerPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Enter O2 and Depth range", TitledBorder.CENTER, TitledBorder.TOP));
        //Set Spinner for start and end of Oxygen and Depth
        startO2= new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
        startDep= new JSpinner(new SpinnerNumberModel(3, 0, 100, 1));
        endO2= new JSpinner(new SpinnerNumberModel(30, 0, 100, 1));
        endDep= new JSpinner(new SpinnerNumberModel(40, 0, 100, 1));
        JLabel o2Start = new JLabel("Start O2:        ");
        JLabel o2End = new JLabel("   End O2:        ");
        JLabel depthStart = new JLabel("Start Depth:      ");
        JLabel depthEnd = new JLabel("   End Depth:    ");
        //row 1
        spinnerPanel.add(o2Start);
        spinnerPanel.add(startO2);
        spinnerPanel.add(o2End);
        spinnerPanel.add(endO2);
        //row2
        spinnerPanel.add(depthStart);
        spinnerPanel.add(startDep);
        spinnerPanel.add(depthEnd);
        spinnerPanel.add(endDep);

        spinnerPanel.setBounds(10,50,430,100);
        bluePanel.add(spinnerPanel);

        // Add checkbox for which table selected
        JPanel checkPanel = new JPanel(new GridLayout(3, 1));
        checkPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Which table", TitledBorder.CENTER, TitledBorder.TOP));

        JCheckBox ead = new JCheckBox("EAD Table");
        JCheckBox pp = new JCheckBox("PP Table");
        //Add actionlistener to CheckBox
        ead.addActionListener(new eadTableAction());
        pp.addActionListener(new ppTableAction());
        //Add button to create table
        JButton createTable = new JButton("Create Table");

        checkPanel.add(ead);
        checkPanel.add(pp);
        checkPanel.add(createTable);
        checkPanel.setBounds(450,50,200,100);
        bluePanel.add(checkPanel);
        //Add action listener to Button
        createTable.addActionListener(new showTableAction());

        //Add logo
        JLabel label;
        // Put 'sdc_logo.png' to the folder which includes all classes
        //Question: sometimes the image will disappear from the folder
        ImageIcon image2 = new ImageIcon(divingcalculations.DivePanel.class.getResource("/divingcalculations/sdc_logo.png"));
        label = new JLabel(image2);
        label.setLayout( new FlowLayout() );
        label.setBounds(670,35,200,150);
        bluePanel.add(label);

        bluePanel.setBounds(10,350,920,200);
            return bluePanel;
    }

    private JPanel getRedPanel() {
           //Set a redPanel to hold table
            redPanel.setBorder(BorderFactory.createLineBorder(Color.red));
            redPanel.setBounds(10,570,1200,250);
            redPanel.revalidate();
            return redPanel;
    }



    /**
     * Add ActionListener to five checkboxes and one "do calculation "button
     */

    /**
     * When 'Maximum Operating Depth' checkbox is selected, MOD will be calculated and results will be saved in String result
     * other four checkboxes are same as above
     */
    private class modAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if(selected){
                //get pp and oxygen value from spinner
                double pgValue = (double) pg.getValue();
                fgValue = (int)fg.getValue();
                //calculate mod
                MOD = db.modCal(pgValue, fgValue);
                //rounded down to keep within safe limits
                result += "Maximum Operating Depth (MOD) for \n a dive with "
                        +fgValue+"% O2 and a partial pressure \n of "
                        +pgValue+" is "+MOD+" metres";
                result +="\n\n===================================\n";
            }
    }
        }


    private class smodAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if(selected){
                //get oxygen and Depth value from spinner
                fgValue = (int)fg.getValue();
                //calculate mod
                SMOD = db.smodCal(fgValue);
                //rounded down to keep within safe limits
                result += "Standard Maximum Operating Depth \n (SMOD) for  a dive with "
                        +fgValue+"% O2 and a \n  partial pressure of of 1.4 ata is "
                        +SMOD +"\n metres";
                result +="\n\n===================================\n";
            }
        }
    }


    private class bmAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AbstractButton abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if(selected){
                //getoxygen value from spinner
                double pgValue = (double) pg.getValue();
                int depthValue = (int)d.getValue();

                //calculate mod
                int BM = db.bmCal(pgValue,depthValue);
                //rounded down to keep within safe limits
                result += "Best mix for a drive to "
                        +depthValue+" metres \n with a partial pressure of  "
                        +pgValue+" \nis "+BM+"% O2";
                result +="\n\n===================================\n";
            }
        }
    }

    private class ppAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AbstractButton abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if(selected){
                //get oxygen and depth value from spinner
                int fgValue = (int)fg.getValue();
                int depthValue = (int)d.getValue();
                //calculate mod
                PP = db.ppo2Cal(fgValue,depthValue);
                //rounded down to keep within safe limits
                result += "The partial pressure of oxygen for a dive \n to "
                        +depthValue+" with a percentage of oxygen of \n "
                        +fgValue+"% is "+PP+" ata";
                result +="\n\n===================================\n";
            }
        }
    }

    private class eadAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AbstractButton abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if(selected){
                //get depth and oxygen value from spinner
                int depthValue = (int)d.getValue();
                int fgValue = (int)fg.getValue();
                //calculate mod
                EAD = db.eadCal(depthValue, fgValue);
                //rounded down to keep within safe limits
                result += "Equivalent Air Depth for a dive with \n"
                        +fgValue+"% O2 to a depth of "
                        +depthValue+" metres is \n"+ EAD+" metres";
                result +="\n\n===================================\n";
            }
        }
    }
    /**
     * When 'Do calculations' Button is clicked, the result will be shown in textarea
     *
     */
    private class calAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //show the result in text field
            display.setText(result);
            //set Slider value to the results of SMOD
            depthSlider.setValue(SMOD);
            //set Slider value to the input of Oxygen
            oxySlider.setValue(fgValue);
        }
    }

    /**
     * When 'EAD Table' or 'PP Table' is selected, the table will be set
     */
    private class eadTableAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AbstractButton abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if (selected) {
                int startO2Value = (int)startO2.getValue();
                int endO2Value = (int)endO2.getValue();
                int startDepValue = (int)startDep.getValue();
                int endDepValue = (int)endDep.getValue();
                eadTable = db.eadTable(startO2Value,endO2Value,startDepValue,endDepValue);
                columnName = db.columnName(startO2Value,endO2Value);
                table = new JTable(eadTable, columnName);
            }
        }
    }

    private class ppTableAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AbstractButton abstractButton = (AbstractButton) event.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if (selected) {
                int startO2Value = (int)startO2.getValue();
                int endO2Value = (int)endO2.getValue();
                int startDepValue = (int)startDep.getValue();
                int endDepValue = (int)endDep.getValue();
                ppo2Table = db.ppTable(startO2Value,endO2Value,startDepValue,endDepValue);
                columnName = db.columnName(startO2Value,endO2Value);
                table = new JTable(ppo2Table, columnName);
            }

        }
    }
    /**
     * When 'Create Table' button is clicked, the table will be shown in redPanel
     */
    private class showTableAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //show the table on the redPanel
            table.setVisible(true);
            table.setGridColor(Color.blue);
            tableContainer = new JScrollPane(table);
            redPanel.add(tableContainer, BorderLayout.CENTER);
            redPanel.revalidate();
        }
    }


}

