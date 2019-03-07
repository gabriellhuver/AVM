/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author gab00
 */
public class FormUtilUI {

    public void SetIcon(JFrame frame) {
        URL iconURL = getClass().getResource("/images/logo.png");
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
    }

    public static void maximizedForm(JFrame frame) {
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public static void centralizeForm(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }

}
