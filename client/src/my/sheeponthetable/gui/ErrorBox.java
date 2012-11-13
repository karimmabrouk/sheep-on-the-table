package my.sheeponthetable.gui;

/**
 * General box to display errors.
 * 
 * @author knuterik
 */
public class ErrorBox extends javax.swing.JFrame {

    /**
     * Creates new form ErrorBox
     */
    public ErrorBox() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Creates new form ErrorBox, while specifying the error message.
     *
     * @param errorMessage 
     */
    public ErrorBox(String errorMessage) {
        this();
        errorMessageLabel.setText(errorMessage);
    }

    /**
     * Creates new form ErrorBox, while specifying the error message and the
     * title.
     *
     * @param errorMessage
     * @param errorTitle  
     */
    public ErrorBox(String errorMessage, String errorTitle) {
        this();
        errorMessageLabel.setText(errorMessage);
        titleLabel.setText(errorMessage);
    }

    /**
     * Autogenerated code
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        errorMessageLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        titleLabel.setText("Beeeeh!");

        imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/sheeponthetable/gui/resources/sadSheep.jpg"))); // NOI18N

        errorMessageLabel.setBackground(new java.awt.Color(255, 255, 255));
        errorMessageLabel.setText("The sheep is sad, something went wrong!");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 372, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(imageLabel)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel1Layout.createSequentialGroup()
                            .add(210, 210, 210)
                            .add(closeButton))
                        .add(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(titleLabel)
                                .add(errorMessageLabel))))
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 114, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(jPanel1Layout.createSequentialGroup()
                            .add(6, 6, 6)
                            .add(imageLabel))
                        .add(jPanel1Layout.createSequentialGroup()
                            .add(titleLabel)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(errorMessageLabel)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(closeButton)))
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when the close-button is pressed.
     * @param evt 
     */
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton closeButton;
    public javax.swing.JLabel errorMessageLabel;
    public javax.swing.JLabel imageLabel;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
