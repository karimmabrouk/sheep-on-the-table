package my.sheeponthetable.gui;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import my.sheeponthetable.tools.Config;
import my.sheeponthetable.tools.WebServiceClient;

/**
 * The screen that is opened when the program starts. Asks for the username and
 * password.
 *
 * @author Håkon
 */
public class PasswordScreen extends javax.swing.JFrame {

    private Config config;
    private String username;
    private String password;

    /**
     * Creates new form PasswordScreen
     */
    public PasswordScreen() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Log in");

        // Creats a config object to read a previously stored username and pass-
        // word, if there are any.
        this.config = new Config();
        if (!this.config.getUsername().equals("")) {
            this.usernameField.setText(this.config.getUsername());
            this.passwordField.setText(this.config.getPassword());
            this.rememberMeCheckbox.setSelected(true);
        }
    }

    /**
     * Autogenerated code
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
                keyPressedEvent(evt);
            }
        });

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keyPressedEvent(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        titleLabel.setText("Sheep Finder Alpha");

        usernameLabel.setText("Username");

        passwordLabel.setText("Password");

        rememberMeCheckbox.setBackground(new java.awt.Color(255, 255, 255));
        rememberMeCheckbox.setText("Remember me");

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

        welcomeLabel.setText("Welcome to Sheep Finder Alpha, please log in to manage your sheep.");

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
                .addGap(7, 7, 7))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when trying to log in. Tries to confirm the username and password
     * with the ones found in the database. If successful, opens a ChooseFarm-
     * dialogue as the next step in the log-in process. If an error occurs, it
     * shows the user an error message.
     */
    private void loginAction() {
        username = usernameField.getText();
        password = passwordField.getText();

        // If checkbox is checked, save username and password to file. Otherwise
        // clear whatever data was stored there already.
        if (rememberMeCheckbox.isSelected()) {
            config.setUsername(username);
            config.setPassword(password);
        } else {
            config.setUsername("");
            config.setPassword("");
        }

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password not set", "Bad login", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        WebServiceClient.setUsername(username);
        WebServiceClient.setPassword(password);

        if (!WebServiceClient.isLoggedIn()) {
            // Login failed
            if (WebServiceClient.isErrorMessage()) {
                JOptionPane.showMessageDialog(this, "The WebService returned an error:\n" + WebServiceClient.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "The username or password is incorrect.", "Bad login", JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }

        // If you have made it to this point, you have a successfull login.
        // If the user has more than one farm, open a ChooseFarm dialogue window,
        // otherwise, go directly to the SheepPanel.
        if (WebServiceClient.getFarmIds().size() == 1) {
            String farmId = WebServiceClient.getFarmIds().get(0).get("id").toString();
            WebServiceClient.setFarmId(farmId);
            dispose();
            new SheepPanel().setVisible(true);
        } else if (WebServiceClient.getFarmIds().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no farms.", "Farm selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            new ChooseFarm().setVisible(true);
        }
        dispose();
    }

    /**
     * Called when the log on-button is pressed.
     */
    private void logOnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOnButtonActionPerformed
        loginAction();
    }//GEN-LAST:event_logOnButtonActionPerformed

    /**
     * Called when the quite button is pressed.
     */
    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        dispose();
    }//GEN-LAST:event_quitButtonActionPerformed

    /**
     * Called by the keyboard listener when a key is pressed. If that key is
     * enter, try to log in. If that key is esc, quit the program.
     */
private void keyPressedEvent(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressedEvent
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        loginAction();
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        dispose();
    }
}//GEN-LAST:event_keyPressedEvent
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
