/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sheeponthetable.gui;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import my.sheeponthetable.tools.Config;
import my.sheeponthetable.tools.WebServiceClient;

/**
 *
 * @author Håkon
 */
public class PasswordScreen extends javax.swing.JFrame {

    private Config config;
    private String username;
    private String password;
    //private ServerConnector connect;
    
    /**
     * Creates new form PasswordScreen
     */
    public PasswordScreen() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        this.config = new Config();
        if(!this.config.getUsername().equals("")) {
            this.usernameField.setText(this.config.getUsername());
            this.passwordField.setText(this.config.getPassword());
            this.rememberMeCheckbox.setSelected(true);
        }
        
        setTitle("Log in");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        titleLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        rememberMeCheckbox = new javax.swing.JCheckBox();
        sheepImageLabel = new javax.swing.JLabel();
        logOnButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        loginPanel.setBackground(new java.awt.Color(255, 255, 255));

        usernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onEnterLogon(evt);
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onEnterLogon(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLabel.setText("Sheep Finder Alpha");

        usernameLabel.setText("Username");

        passwordLabel.setText("Password");

        rememberMeCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        rememberMeCheckbox.setText("Remember me");
        rememberMeCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rememberMeCheckboxActionPerformed(evt);
            }
        });

        sheepImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/sheeponthetable/gui/resources/confused sheep.jpg"))); // NOI18N

        logOnButton.setText("Log on");
        logOnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOnButtonActionPerformed(evt);
            }
        });

        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        welcomeLabel.setText("Welcome to Sheep Finder Alpha, please log in to manage your sheeps.");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(sheepImageLabel)
                        .addGap(18, 18, 18)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel)
                            .addComponent(passwordLabel)
                            .addComponent(rememberMeCheckbox)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(logOnButton)
                                .addGap(18, 18, 18)
                                .addComponent(quitButton))))
                    .addComponent(welcomeLabel)
                    .addComponent(titleLabel))
                .addGap(31, 31, 31))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addGap(1, 1, 1)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rememberMeCheckbox)
                        .addGap(10, 10, 10)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logOnButton)
                            .addComponent(quitButton)))
                    .addComponent(sheepImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginAction () {
        this.username = this.usernameField.getText();
        this.password = this.passwordField.getText(); // Possible security threat
        
        if (this.username.isEmpty() || this.password.isEmpty()) {
            System.out.println("Warning: Username or password not set");
            return;
        }
        
        WebServiceClient.username = this.username;
        WebServiceClient.password = this.password;
        
        if (!WebServiceClient.isLoggedIn()) {
            // Login failed
            this.config.setUsername("");
            this.config.setPassword("");
            JOptionPane.showMessageDialog(null, "Wrong username or password!");
            return;
        }

        // If you have made it to this point, you have a successfull login
        
        if(this.rememberMeCheckbox.isSelected()) {
            // Checkbox is checked, saving username and password to file.
            this.config.setUsername(this.username);
            this.config.setPassword(this.password);
        } else {
            // Checkbox is NOT checked, removing username and password to file.
            this.config.setUsername("");
            this.config.setPassword("");
        }
        
        new ChooseFarm().setVisible(true);
        
        this.dispose();
    }
    
    private void rememberMeCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rememberMeCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rememberMeCheckboxActionPerformed

    private void onEnterLogon(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onEnterLogon
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginAction();
        }
    }//GEN-LAST:event_onEnterLogon

    private void logOnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOnButtonActionPerformed
       loginAction();
    }//GEN-LAST:event_logOnButtonActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logOnButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton quitButton;
    private javax.swing.JCheckBox rememberMeCheckbox;
    private javax.swing.JLabel sheepImageLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
