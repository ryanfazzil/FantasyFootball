package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new FantasyGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }


//        try {
//            new FantasyApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }

//        JFrame frame = new JFrame();
//        frame.setTitle("Fantasy Team");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit app
//
//        frame.setSize(500,500);
//        frame.setVisible(true); //make frame visible
//
//        ImageIcon image = new ImageIcon("fblogo.png");
//        frame.setIconImage(image.getImage()); //change icon frame
//        frame.getContentPane().setBackground(new Color(243,218,182));

        //new FantasyGUI();


    }
}
