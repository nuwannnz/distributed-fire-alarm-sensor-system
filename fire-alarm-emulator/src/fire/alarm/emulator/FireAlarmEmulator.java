/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fire.alarm.emulator;

/**
 *
 * @author nuwan
 */
public class FireAlarmEmulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // change the default theme
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

        }

        // start the emulator window
        EmulatorWindow emulatorWindow = new EmulatorWindow();
        emulatorWindow.setVisible(true);

    }

}
