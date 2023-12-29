
package ui;

import model.FantasyTeam;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FantasyGUI extends JFrame implements ListSelectionListener {


    private JList list;
    private DefaultListModel listModel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/fantasyteam.json";

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String tdString = "Add TD";

    private FantasyTeam ft;

    private JPanel playerPanel;
    private JPanel optionPanel;

    private JScrollPane playerList;
    private JTextArea entry;

    private JButton addButton;
    private JButton removeButton;
    private JButton tdButton;
    private JTextField playerName;
    private JTextField playerTd;

    //FantasyTeam GUI application
    public FantasyGUI() throws FileNotFoundException {
        super("Fantasy Team");
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        ft = new FantasyTeam("my team");
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        playerList = new JScrollPane(list);
        playerName = new JTextField(10);
        playerTd = new JTextField(5);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        fbImg();
        leftPanel();
        setOptionPanel();
        setPlayerPanel();
        rightPanel();
        filePanel();


        this.setSize(912,440);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(243,218,182));
        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates the panel with add/remove functions
    private void leftPanel() {
        JLabel playerLabel = new JLabel();
        playerLabel.setText("Add Player");
        playerLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JLabel touchdownsLabel = new JLabel();
        touchdownsLabel.setText("Remove Player");
        touchdownsLabel.setFont(new Font("Serif", Font.BOLD, 30));

        JPanel addPanel = new JPanel();
        addPanel.setBackground(Color.blue);
        addPanel.setBounds(275,100, 200, 50);
        addPanel.add(playerLabel);

        JPanel viewPanel = new JPanel();
        viewPanel.setBackground(Color.red);
        viewPanel.setBounds(275,200,200,50);
        viewPanel.add(touchdownsLabel);

        add(addPanel);
        add(viewPanel);
        textPanel();

    }

    //MODIFIES: this
    //EFFECTS: creates panels that display ADD/REMOVE with description
    private void textPanel() {
        JLabel addLabel = new JLabel();
        addLabel.setText("Type in player name and click Add!");
        addLabel.setFont(new Font("Serif", Font.BOLD, 15));

        JLabel viewLabel = new JLabel();
        viewLabel.setText("Click player name then click remove!");
        viewLabel.setFont(new Font("Serif", Font.BOLD, 15));

        JPanel addTextPanel = new JPanel();
        addTextPanel.setBackground(Color.GRAY);
        addTextPanel.setBounds(275,150,250,50);
        addTextPanel.add(addLabel);

        JPanel viewTextPanel = new JPanel();
        viewTextPanel.setBackground(Color.GRAY);
        viewTextPanel.setBounds(275,250,250,50);
        viewTextPanel.add(viewLabel);

        add(addTextPanel);
        add(viewTextPanel);
        tdText();
    }

    //MODIFIES: this
    //EFFECTS: creates text panel for Add TDS
    private void tdText() {
        JLabel tdLabel = new JLabel();
        tdLabel.setText("<html> Type in player you want to add TDs to<br>"
                + "then click Add Td, and remove the previous <br>"
                + "duplicate player when TDs are added!");
        tdLabel.setFont(new Font("Serif", Font.BOLD, 12));


        JPanel tdTextPanel = new JPanel();
        tdTextPanel.setBackground(Color.GRAY);
        tdTextPanel.setBounds(300,320,300,50);
        tdTextPanel.add(tdLabel);

        add(tdTextPanel);
    }


    //MODIFIES: this
    //EFFECTS: Creates panel with GIF
    private void fbImg() {
        //ImageIcon fb = new ImageIcon("src/images/fblogo.png");
        ImageIcon fb = new ImageIcon(getClass().getResource("fbminions1.gif"));
        JLabel fbLabel = new JLabel(fb);


        JPanel fbIcon = new JPanel();
        fbIcon.add(fbLabel);
        fbIcon.setBounds(0,0,275,225);
        fbIcon.setBackground(Color.DARK_GRAY);


        add(fbIcon);

    }

    //MODIFIES: this
    //EFFECTS: creates panel that holds the SAVE/LOAD/AddTD buttons
    //REQUIRES: AddTD requires an int to be passed in text field.
    private void filePanel() {
        JButton saveButton = new JButton(saveString);
        SaveTeamListener saveTeamListener = new SaveTeamListener(saveButton);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(saveTeamListener);

        JButton loadButton = new JButton(loadString);
        LoadTeamListener loadTeamListener = new LoadTeamListener(loadButton);
        loadButton.setActionCommand(saveString);
        loadButton.addActionListener(loadTeamListener);

        JButton tdButton = new JButton(tdString);
        AddTdListener addTdListener = new AddTdListener(tdButton);
        tdButton.setActionCommand(tdString);
        tdButton.addActionListener(addTdListener);



        JPanel optionP = new JPanel(new GridLayout(4,4,4,4));
        optionP.setBounds(0, 225,275,185);
        optionP.setBackground(Color.DARK_GRAY);
        optionP.add(saveButton);
        optionP.add(loadButton);
        optionP.add(tdButton);
        optionP.add(playerTd);

        add(optionP);
    }


    //MODIFIES: this
    //EFFECTS: Creates panel that holds list of players
    private void rightPanel() {

        playerPanel = new JPanel();
        playerPanel.setBackground(Color.black);
        playerPanel.setBounds(600,0, 310, 410);
        //setPlayerPanel();
        setListModel();

        add(playerPanel);
    }

    //MODIFIES: this
    //EFFECTS: creates the panel that holds the REMOVE/ADD functions
    //REQUIRES: add player must be not already on team.
    private void setPlayerPanel() {
        JButton addButton = new JButton(addString);
        AddPlayerListener addPlayerListener = new AddPlayerListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addPlayerListener);
        playerName.addActionListener(addPlayerListener);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(playerName);
        buttonPanel.add(addButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        buttonPanel.setSize(400,200);
        optionPanel.add(buttonPanel,BorderLayout.EAST);

    }


    //MODIFIES: this
    //EFFECTS: creates the background panel ADD/REMOVE
    private void setOptionPanel() {
        optionPanel = new JPanel();
        optionPanel.setBackground(Color.GRAY);
        optionPanel.setBounds(275,0,325,410);

        add(optionPanel);
    }

    //MODIFIES: this
    //EFFECTS: creates the JScrollPane that holds all players
    private void setListModel() {

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(300,400));

        playerPanel.add(listScrollPane, BorderLayout.CENTER);
    }

    //class that holds add touchdowns function
    class AddTdListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;


        public AddTdListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = playerTd.getText();
            //Player playerC = new Player(name);

            int index = list.getSelectedIndex();

            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            String tds = JOptionPane.showInputDialog("How Many TouchDowns to add?");
            int td = Integer.parseInt(tds);



            for (Player player : ft.getPlayers()) {
                if (name.equals(player.getName())) {
                    player.addTouchdowns(td);
                    listModel.addElement((player.getName() + " " + " TDS:" + player.getTouchDowns()));
                }
//                } else {
//                    return;
//                }
            }

            playerName.requestFocusInWindow();
            playerName.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

    }

    //class that holds saving function
    class SaveTeamListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public SaveTeamListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button) {
                saveFantasyTeam();
                JOptionPane.showMessageDialog(null,"Team Saved!",
                                "Fantasy Team", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //class that holds loading function
    class LoadTeamListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public LoadTeamListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button) {
                loadFantasyTeam();

                for (Player player : ft.getPlayers()) {
                    listModel.addElement((player.getName() + " " +  " TDS:" + player.getTouchDowns()));
                }
                JOptionPane.showMessageDialog(null,"Team Loaded!",
                        "Fantasy Team", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //class that holds add function
    class AddPlayerListener implements ActionListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddPlayerListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            String name = playerName.getText();
            Player player = new Player(name);

            //check unique
            if (name.equals("") || alreadyInList(name)) {
                playerName.requestFocusInWindow();
                playerName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            ft.addPlayer(player);
            listModel.addElement((player.getName() + " " +  " TDS:" + player.getTouchDowns()));

            playerName.requestFocusInWindow();
            playerName.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }
    }

    //class that holds remove function
    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String name = playerName.getText();
            Player player = new Player(name);

            ft.removePlayer(player);
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { // no players
                removeButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS saves FantasyTeam to file
    private void saveFantasyTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(ft);
            jsonWriter.close();
            System.out.println("Saved " + ft.getTeamName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads FantasyTeam from file
    private void loadFantasyTeam() {
        try {
            ft = jsonReader.read();
            System.out.println("Loaded " + ft.getTeamName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }




    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                //No selection, disable td button.
                removeButton.setEnabled(false);
            } else {
                //Selection, enable the td button.
                removeButton.setEnabled(true);
            }
        }
    }
}
