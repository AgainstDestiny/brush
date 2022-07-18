package controller;


import javax.swing.*;
import java.io.IOException;

/**
 * @Description: java
 * @author: Axu
 * @date:2022/5/18 16:06
 */
public class Controller {
    public static void main(String[] args) throws IOException {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainInterface();
    }
}
