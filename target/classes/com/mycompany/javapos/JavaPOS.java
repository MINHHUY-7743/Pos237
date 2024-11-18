/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javapos;

import com.mycompany.javapos.gui.LockScreen;

public class JavaPOS {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LockScreen().setVisible(true);
            }
        });
    }
}
