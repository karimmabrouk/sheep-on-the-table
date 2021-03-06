package my.sheeponthetable.gui;

import my.sheeponthetable.tools.map.MouseClickOnWayPointListener;
import java.awt.Color;
import java.io.File;
import java.text.DateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import my.sheeponthetable.tools.*;
import my.sheeponthetable.tools.map.FancyWaypointRenderer;
import my.sheeponthetable.tools.map.MyWaypoint;
import my.sheeponthetable.tools.map.RoutePainter;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.input.CenterMapListener;
import org.jdesktop.swingx.input.PanKeyListener;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListenerCursor;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.LocalResponseCache;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

/**
 * SheepPanel is the main dialogue box of the program. It gives the user most of
 * the desired information, including lists of all the sheep on the farm, a map
 * displaying their positions, and information belonging to the selected sheep.
 *
 * @author Gruppe 7
 */
public class SheepPanel extends javax.swing.JFrame {

    private DefaultListModel sheepShow;
    private DefaultListModel sheepUpdatesShow;
    private List<Sheep> sheepList;
    private List<SheepUpdate> sheepUpdateList;
    private Set<MyWaypoint> wayPointSet;
    private Sheep selectedSheep;
    ListSelectionListener sheepListSelectionListener;
    ListSelectionListener updateListSelectionListener;
    private boolean isEditingSheep;

    /**
     * Constructs a SheepPanel with all associated patterns.
     */
    public SheepPanel() {
        sheepShow = new DefaultListModel();
        sheepUpdatesShow = new DefaultListModel();

        initComponents();
        initListSelectionListeners();

        panelSheepEdit.setVisible(false);
        this.setLocationRelativeTo(null);

        mapInitialize();
        update();
        populateFarmAndName();
        buildKeyboardShortcuts();
    }

    /**
     * Returns the list of Sheep Updates associated with the selected sheep. To
     * get the update list of a given sheep, use getSheepList() to get the full
     * list of sheep, and call getUpdates() on the desired sheep.
     *
     * @return
     */
    public List<SheepUpdate> getUpdateList() {
        return sheepUpdateList;
    }

    /**
     * Returns the list of sheep associated with the given farm.
     *
     * @return
     */
    public List<Sheep> getSheepList() {
        return sheepList;
    }

    /**
     * Returns the mapKit-object used to draw the map.
     *
     * @return
     */
    public JXMapKit getMapKit() {
        return jXSheepMap;
    }

    /**
     * Returns the set of MyWaypoints that are currently being used to draw
     * waypoints on the map. Used by the map's mouse listener.
     *
     * @return
     */
    public Set<MyWaypoint> getWayPoints() {
        return wayPointSet;
    }

    /**
     * Called by the constructor to build the keyboard shortcuts for the menu.
     * Has to be done differently on Mac OS and the other OS's because of the
     * keyboard layout.
     */
    private void buildKeyboardShortcuts() {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            menuLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.META_MASK));
            menuCloseProgram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.META_MASK));
            menuPrefFarm.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.META_MASK));
            menuPrefUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PERIOD, java.awt.event.InputEvent.META_MASK));
            menuAddSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.META_MASK));
            menuEditSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.META_MASK));
            menuRefresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.META_MASK));
            menuChangePassword.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.META_MASK));
        } else {
            menuLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuCloseProgram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuPrefFarm.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuPrefUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PERIOD, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuAddSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuEditSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuRefresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
            menuChangePassword.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        }
    }

    /**
     * Fired by event listeners to handle the removal of a sheep. Asks the Web
     * Service Client to remove the sheep from the database, and then fetchs the
     * new sheep list. If not able to remove the sheep from the database, it
     * displays an error.
     */
    public void removeSheep(Sheep s) {
        if (WebServiceClient.removeSheep(s)) {
            deselect();
            update();
        } else {
            JOptionPane.showMessageDialog(this, "Could not remove sheep! \nPlease verify that you have the required permissions. \nIf the problem persists, contact sysadmin.");
        }
    }

    /*
     *Called upon when a HealthStatus is wanted in String format.
     * The function recives a status int and generates a Health
     * status in writing and sends it back as a String
     */
    private String getHealthStatusAlarm(int status) {
        /*1 = attack
         * 2 = health
         * 4 = stationary
         * 3 = attack, health
         * 5 = attack, stationary
         * 6 = health, stationary
         * 7 = all of them
         */
        String lblText = "None";
        if (status == 1) {
            lblText = "Attacked";
        } else if (status == 2) {
            lblText = "Health";
        } else if (status == 3) {
            lblText = "Attack and Health";
        } else if (status == 4) {
            lblText = "Stationary";
        } else if (status == 5) {
            lblText = "Attack and stationary";
        } else if (status == 6) {
            lblText = "Health and stationary";
        } else if (status == 7) {
            lblText = "Attack, stationary and health";
        }
        return lblText;
    }

    /**
     * Fired by event listeners to handle what happens when a sheep is seleced.
     * It updates the information in the display area, as well as prints
     * waypoints on the map corresponding to the sheep's position history.
     *
     * @param index: The index of the selected sheep
     */
    private void selectSheep(int index) {
        // Get the correct sheep form the sheep list
        selectedSheep = sheepList.get(index);

        // Set the value of the display fields
        setValueOfDisplayFields(selectedSheep);

        // Fill the SheepUpdate-list with the sheepUpdates corresponding to the
        // selected sheep, and at the same time, build a list of waypoints for
        // the sheep updates.
        sheepUpdatesShow.removeAllElements();

        String id = Integer.toString(selectedSheep.getID());
        List<SheepUpdate> updates = WebServiceClient.getSheepUpdate(id, "100");
        selectedSheep.setUpdates(updates);

        // A sheep has to be displayed differently depending on whether it has
        // any updates associated with it or not.
        if (!selectedSheep.getUpdates().isEmpty()) {
            // The first update is the newest one, so fill the display fields
            // with information from this update.
            double xpos = selectedSheep.getUpdates().get(0).getX();
            double ypos = selectedSheep.getUpdates().get(0).getY();
            lblSheepPosition.setText(ypos + ", " + xpos);

            Date timestamp = new Date(selectedSheep.getUpdates().get(0).getTimeStamp() * 1000);
            lblSheepUpdate.setText(timestamp.toLocaleString());

            lblSheepPulse.setText(Integer.toString(selectedSheep.getUpdates().get(0).getPulse()) + " BPM");
            lblSheepTemperature.setText(Double.toString(selectedSheep.getUpdates().get(0).getTemp()) + "C" + "\u00B0");
            // Iterate over the sheep updates, filling the SU-list and building
            // waypoints.
            Set<MyWaypoint> waypoints = new HashSet<>();
            List<GeoPosition> track = new ArrayList();

            for (int i = 0; i < selectedSheep.getUpdates().size(); i++) {
                SheepUpdate update = selectedSheep.getUpdates().get(i);
                Date formattedUpdateTimestamp = new Date(update.getTimeStamp());
                String timestring = formattedUpdateTimestamp.toLocaleString();
                sheepUpdatesShow.addElement(timestring);

                // Make the newest update waypoint red, and the others white.
                Color colour;
                if (i == 0) {
                    colour = Color.RED;
                } else {
                    colour = Color.WHITE;
                }

                GeoPosition gp = new GeoPosition(update.getY(), update.getX());
                MyWaypoint wp = new MyWaypoint(timestring, colour, gp, i, false);
                waypoints.add(wp);
                track.add(gp);
            }

            paintWaypoints(track, waypoints);
        } // If there are no updates associated with the sheep, clear the map of
        // all waypoints, and set the label information as n/a.
        else {
            lblSheepPosition.setText("Not available");
            lblSheepUpdate.setText("Not available");
            lblSheepPulse.setText("Not available");
            lblSheepTemperature.setText("Not available");
            lblSheepAlarm.setText("Not available");

            CompoundPainter<JXMapViewer> painter = new CompoundPainter<>();
            jXSheepMap.getMainMap().setOverlayPainter(painter);
        }

        sheepUpdateList = selectedSheep.getUpdates();
    }

    /**
     * Display the correct information in the display fields. Called when a
     * sheep is selected or an edit has been performed.
     */
    private void setValueOfDisplayFields(Sheep s) {
        int id = s.getID();
        DateFormat sdf = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);

        lblSheepBorn.setText(sdf.format(s.getBorn()));

        lblSheepWeight.setText(Double.toString((s.getWeight())) + " kg");
        lblSheepId.setText(Integer.toString(id));

        taSheepComment.setText(s.getComment());
        lblSheepNickname.setText(s.getName());

        if (s.isAlive()) {
            lblSheepDeceased.setText("Not Dead");
        } else {
            lblSheepDeceased.setText(sdf.format(selectedSheep.getDeceased()));
        }

        // Set the status label according to status
        //send status too getHealthStatusAlarm
        String lblText = getHealthStatusAlarm(s.getStatus());
        //recive a String
        lblSheepAlarm.setText(lblText);
    }

    /**
     * This method is called by the mouse listeners on the map, to handle what
     * happens then the user clicks on a waypoint. What this method does is to
     * select the shep on the list with the index corresponding to the input
     * value. This selection then fires the list event listeners which call the
     * selectSheep method.
     *
     * @param index
     */
    public void mapSelectSheep(int index) {
        sheepJList.setSelectedIndex(index);
        sheepJList.ensureIndexIsVisible(index);
    }

    /**
     *
     * Called by event listeners to handle what happens when a sheep update is
     * seleced. It updates the information in the display area.
     *
     * @param Index of the selected sheep.
     */
    private void selectUpdate(int index) {
        // This method is largely parallell to the selectSheep()-method
        SheepUpdate su = sheepUpdateList.get(index);

        Date formattedTimestamp = new Date(su.getTimeStamp());
        String timestring = formattedTimestamp.toLocaleString();

        lblSheepUpdate.setText(timestring);
        lblSheepPulse.setText(Integer.toString(su.getPulse()) + " BPM");
        lblSheepTemperature.setText(Double.toString(su.getTemp()) + " C" + "\u00B0");
        lblSheepPosition.setText(su.getY() + ", " + su.getX());

        // Set the status label according to status
        //send status too getHealthStatusAlarm
        String lblText = getHealthStatusAlarm(su.getAlarm());
        //recive a string
        lblSheepAlarm.setText(lblText);

        Set<MyWaypoint> waypoints = new HashSet<>();
        List<GeoPosition> track = new ArrayList();

        for (int i = 0; i < sheepUpdateList.size(); i++) {
            SheepUpdate update = sheepUpdateList.get(i);
            Date fmtTimestamp = new Date(update.getTimeStamp());
            String updTimestring = fmtTimestamp.toLocaleString();

            // Make the selected update blue, the last update red, and the other
            // updates white;
            Color colour;
            if (i == index) {
                colour = Color.BLUE;
            } else if (i == 0) {
                colour = Color.RED;
            } else {
                colour = Color.WHITE;
            }

            GeoPosition gp = new GeoPosition(update.getY(), update.getX());
            MyWaypoint wp = new MyWaypoint(updTimestring, colour, gp, i, false);
            waypoints.add(wp);
            track.add(gp);
        }

        paintWaypoints(track, waypoints);
    }

    /**
     * This method is called by the mouse listeners on the map, to when a sheep
     * update waypoint is clicked on on the map. What this method does is to
     * select the update on the list with the index corresponding to the input
     * value. This selection then fires the list event listeners which call the
     * selectUpdate method.
     *
     * @param index
     */
    public void mapSelectUpdate(int index) {
        sheepUpdateJList.setSelectedIndex(index);
        sheepUpdateJList.ensureIndexIsVisible(index);
    }

    /**
     * Autogenerated code. If you read it, your head will hurt.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        panelMain = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabelLastUpdate = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sheepJList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jXSheepMap = new org.jdesktop.swingx.JXMapKit();
        panelSheepInfo = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taSheepComment = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblSheepId = new javax.swing.JLabel();
        lblSheepNickname = new javax.swing.JLabel();
        lblSheepWeight = new javax.swing.JLabel();
        lblSheepBorn = new javax.swing.JLabel();
        lblSheepDeceased = new javax.swing.JLabel();
        lblSheepUpdate = new javax.swing.JLabel();
        lblSheepPosition = new javax.swing.JLabel();
        lblSheepTemperature = new javax.swing.JLabel();
        lblSheepPulse = new javax.swing.JLabel();
        lblSheepAlarm = new javax.swing.JLabel();
        lblFarmName = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sheepUpdateJList = new javax.swing.JList();
        panelSheepEdit = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taSheepEditComment = new javax.swing.JTextArea();
        txtSheepEditId = new javax.swing.JTextField();
        txtSheepEditNickname = new javax.swing.JTextField();
        txtSheepEditWeight = new javax.swing.JTextField();
        btnSheepEditSave = new javax.swing.JButton();
        btnSheepEditCancel = new javax.swing.JButton();
        dcSheepEditBorn = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        dcSheepEditDead = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuLogout = new javax.swing.JMenuItem();
        menuCloseProgram = new javax.swing.JMenuItem();
        menuPreferences = new javax.swing.JMenu();
        menuPrefFarm = new javax.swing.JMenuItem();
        menuPrefUser = new javax.swing.JMenuItem();
        menuChangePassword = new javax.swing.JMenuItem();
        menuSheep = new javax.swing.JMenu();
        menuAddSheep = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuEditSheep = new javax.swing.JMenuItem();
        menuRefresh = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        panelMain.setBackground(new java.awt.Color(255, 255, 255));
        panelMain.setPreferredSize(new java.awt.Dimension(1000, 600));

        jLabelLastUpdate.setText("Not Available");

        lblName.setText("username");

        sheepJList.setModel(sheepShow);
        jScrollPane1.setViewportView(sheepJList);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel2.setText("Last update:");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel1.setText("Logged in as:");

        panelSheepInfo.setBackground(new java.awt.Color(255, 255, 255));
        panelSheepInfo.setMaximumSize(new java.awt.Dimension(323, 390));
        panelSheepInfo.setMinimumSize(new java.awt.Dimension(323, 390));
        panelSheepInfo.setPreferredSize(new java.awt.Dimension(323, 390));

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setText("ID:");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel8.setText("Nickname:");

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel9.setText("Weight:");

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel10.setText("Date of death:");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel11.setText("Comment:");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel12.setText("Date of birth:");

        taSheepComment.setEditable(false);
        taSheepComment.setColumns(20);
        taSheepComment.setLineWrap(true);
        taSheepComment.setRows(5);
        taSheepComment.setMaximumSize(new java.awt.Dimension(240, 80));
        taSheepComment.setMinimumSize(new java.awt.Dimension(240, 80));
        jScrollPane3.setViewportView(taSheepComment);

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel13.setText("Position:");

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel14.setText("Temperature:");

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel15.setText("Selected Update:");

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel16.setText("Hearth Rate:");

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel17.setText("Status:");

        lblSheepId.setText("No sheep selected");

        lblSheepNickname.setText("No sheep selected");

        lblSheepWeight.setText("No sheep selected");

        lblSheepBorn.setText("No sheep selected");

        lblSheepDeceased.setText("No sheep selected");

        lblSheepUpdate.setText("No update selected");

        lblSheepPosition.setText("No update selected");

        lblSheepTemperature.setText("No update selected");

        lblSheepPulse.setText("No update selected");

        lblSheepAlarm.setText("No update selected");

        javax.swing.GroupLayout panelSheepInfoLayout = new javax.swing.GroupLayout(panelSheepInfo);
        panelSheepInfo.setLayout(panelSheepInfoLayout);
        panelSheepInfoLayout.setHorizontalGroup(
            panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSheepInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addGroup(panelSheepInfoLayout.createSequentialGroup()
                        .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSheepInfoLayout.createSequentialGroup()
                                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSheepId)
                                    .addComponent(lblSheepNickname)
                                    .addComponent(lblSheepWeight)
                                    .addComponent(lblSheepBorn)
                                    .addComponent(lblSheepDeceased)
                                    .addComponent(lblSheepTemperature)
                                    .addComponent(lblSheepPulse)
                                    .addComponent(lblSheepAlarm))
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addComponent(lblSheepPosition, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(lblSheepUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelSheepInfoLayout.setVerticalGroup(
            panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSheepInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSheepId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblSheepNickname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblSheepWeight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblSheepBorn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblSheepDeceased))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblSheepUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblSheepPosition))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblSheepTemperature))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblSheepPulse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblSheepAlarm))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblFarmName.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        lblFarmName.setText("Navnet til Sauefarmen");

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel19.setText("Sheeps:");

        sheepUpdateJList.setModel(sheepUpdatesShow);
        jScrollPane2.setViewportView(sheepUpdateJList);

        panelSheepEdit.setBackground(new java.awt.Color(255, 255, 255));
        panelSheepEdit.setMaximumSize(new java.awt.Dimension(323, 390));
        panelSheepEdit.setMinimumSize(new java.awt.Dimension(323, 390));
        panelSheepEdit.setPreferredSize(new java.awt.Dimension(323, 390));

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel18.setText("ID:");

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel20.setText("Nickname:");

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel21.setText("Weight:");

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel23.setText("Comment:");

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel24.setText("Date of birth:");

        taSheepEditComment.setColumns(20);
        taSheepEditComment.setLineWrap(true);
        taSheepEditComment.setRows(5);
        taSheepEditComment.setMaximumSize(new java.awt.Dimension(240, 80));
        taSheepEditComment.setMinimumSize(new java.awt.Dimension(240, 80));
        jScrollPane4.setViewportView(taSheepEditComment);

        txtSheepEditId.setEditable(false);

        btnSheepEditSave.setText("Save changes");
        btnSheepEditSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSheepEditSaveClicked(evt);
            }
        });

        btnSheepEditCancel.setText("Discard changes");
        btnSheepEditCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSheepEditCancelActionPerformed(evt);
            }
        });

        dcSheepEditBorn.setDateFormatString("dd.MM.yyyy");

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel25.setText("Date of death:");

        dcSheepEditDead.setDateFormatString("dd.MM.yyyy");

        javax.swing.GroupLayout panelSheepEditLayout = new javax.swing.GroupLayout(panelSheepEdit);
        panelSheepEdit.setLayout(panelSheepEditLayout);
        panelSheepEditLayout.setHorizontalGroup(
            panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSheepEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSheepEditLayout.createSequentialGroup()
                        .addComponent(btnSheepEditSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(btnSheepEditCancel))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addGroup(panelSheepEditLayout.createSequentialGroup()
                        .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcSheepEditBorn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtSheepEditWeight, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtSheepEditNickname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(txtSheepEditId, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(dcSheepEditDead, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelSheepEditLayout.setVerticalGroup(
            panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSheepEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtSheepEditId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtSheepEditNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtSheepEditWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(dcSheepEditBorn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(dcSheepEditDead, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSheepEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSheepEditSave)
                    .addComponent(btnSheepEditCancel))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jButton1.setText("Edit selected sheep");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSelectedSheepClicked(evt);
            }
        });

        jButton2.setText("Delete selected sheep");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedClicked(evt);
            }
        });

        jButton3.setText("Deselect all");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeselectAllClicked(evt);
            }
        });

        jButton4.setText("Refresh list");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshListClicked(evt);
            }
        });

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFarmName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelLastUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(panelSheepEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSheepInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXSheepMap, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXSheepMap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFarmName)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMainLayout.createSequentialGroup()
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(lblName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabelLastUpdate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelSheepInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(panelSheepEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(panelMainLayout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3)
                                    .addComponent(jButton4))))))
                .addGap(14, 14, 14))
        );

        menuFile.setText("File");

        menuLogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.META_MASK));
        menuLogout.setText("Log out");
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        menuFile.add(menuLogout);

        menuCloseProgram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.META_MASK));
        menuCloseProgram.setText("Close program");
        menuCloseProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCloseProgramActionPerformed(evt);
            }
        });
        menuFile.add(menuCloseProgram);

        jMenuBar1.add(menuFile);

        menuPreferences.setText("Preferences");

        menuPrefFarm.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.META_MASK));
        menuPrefFarm.setText("Farm");
        menuPrefFarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrefFarmActionPerformed(evt);
            }
        });
        menuPreferences.add(menuPrefFarm);

        menuPrefUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PERIOD, java.awt.event.InputEvent.META_MASK));
        menuPrefUser.setText("User");
        menuPrefUser.setActionCommand("UserProperties");
        menuPrefUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrefUserActionPerformed(evt);
            }
        });
        menuPreferences.add(menuPrefUser);

        menuChangePassword.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.META_MASK));
        menuChangePassword.setText("Change Password");
        menuChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChangePasswordActionPerformed(evt);
            }
        });
        menuPreferences.add(menuChangePassword);

        jMenuBar1.add(menuPreferences);

        menuSheep.setText("Sheep");

        menuAddSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.META_MASK));
        menuAddSheep.setText("Add new sheep");
        menuAddSheep.setToolTipText("");
        menuAddSheep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAddSheepActionPerformed(evt);
            }
        });
        menuSheep.add(menuAddSheep);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem3.setText("Deselect sheep");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDeselectSheepActionPerformed(evt);
            }
        });
        menuSheep.add(jMenuItem3);

        menuEditSheep.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.META_MASK));
        menuEditSheep.setText("Edit sheep");
        menuEditSheep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditSheepActionPerformed(evt);
            }
        });
        menuSheep.add(menuEditSheep);

        menuRefresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.META_MASK));
        menuRefresh.setText("Refresh Sheeplist");
        menuRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRefreshActionPerformed(evt);
            }
        });
        menuSheep.add(menuRefresh);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItem2.setText("Remove Sheep");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoveSheepClicked(evt);
            }
        });
        menuSheep.add(jMenuItem2);

        jMenuBar1.add(menuSheep);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called by the constructor to initialize the list selection listeners.
     */
    private void initListSelectionListeners() {

        // Make one listener for the sheep list
        sheepListSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                JList list = (JList) listSelectionEvent.getSource();
                int selectedIndex = list.getSelectedIndex();
                if (!listSelectionEvent.getValueIsAdjusting() && selectedIndex != -1) {
                    selectSheep(selectedIndex);
                }
            }
        };
        sheepJList.addListSelectionListener(sheepListSelectionListener);
        SheepListCellRenderer slcr = new SheepListCellRenderer(this);
        sheepJList.setCellRenderer(slcr);

        // Make another listener for the update lists
        updateListSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                int selectedIndex = list.getSelectedIndex();
                if (!e.getValueIsAdjusting() && selectedIndex != -1) {
                    selectUpdate(selectedIndex);
                }
            }
        };
        sheepUpdateJList.addListSelectionListener(updateListSelectionListener);
        UpdateListCellRenderer ulcr = new UpdateListCellRenderer(this);
        sheepUpdateJList.setCellRenderer(ulcr);
    }

    /**
     * Called when the logout-option in selected from the menu.
     */
    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        this.setVisible(false);
        new PasswordScreen().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuLogoutActionPerformed

    /**
     * Called when the close program-option is selected from the menu.
     */
    private void menuCloseProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCloseProgramActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuCloseProgramActionPerformed

    /**
     * Called when the edit farm-option is selected from the menu.
     */
    private void menuPrefFarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrefFarmActionPerformed
        new FarmTools().setVisible(true);
    }//GEN-LAST:event_menuPrefFarmActionPerformed

    /**
     * Called when the edit user-option is selected from the menu.
     */
    private void menuPrefUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrefUserActionPerformed
        new UserProperties(this).setVisible(true);
    }//GEN-LAST:event_menuPrefUserActionPerformed

    /**
     * Called when the add sheep-option is selected from the menu.
     */
    private void menuAddSheepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAddSheepActionPerformed
        new AddNewSheep(this).setVisible(true);
    }//GEN-LAST:event_menuAddSheepActionPerformed

    /**
     * Called when the refresh sheep list-option is selected from the menu.
     */
    private void menuRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRefreshActionPerformed
        update();
    }//GEN-LAST:event_menuRefreshActionPerformed

    /**
     * Called when the edit sheep list-option is selected from the menu.
     */
    private void menuEditSheepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditSheepActionPerformed
        editSheep();
    }//GEN-LAST:event_menuEditSheepActionPerformed

    /**
     * Called by the event handling code for the menu-option "edit sheep" and
     * for the button "edit selected sheep". What it does, is to hide all the
     * uneditable labels, and show an equvalent set of editable text fields, as
     * well as reveal the buttons to save or discard changes.
     */
    private void editSheep() {
        if (!isEditingSheep && sheepJList.getSelectedIndex() != -1) {
            // Sets the proper values of the textboxes in the Edit Sheep Panel
            txtSheepEditId.setText(Integer.toString(selectedSheep.getID()));
            txtSheepEditNickname.setText(selectedSheep.getName());
            txtSheepEditWeight.setText(Double.toString(selectedSheep.getWeight()));
            dcSheepEditBorn.setDate(selectedSheep.getBorn());
            dcSheepEditDead.setDate(selectedSheep.getDeceased());
            taSheepEditComment.setText(selectedSheep.getComment());
            panelSheepInfo.setVisible(false);
            panelSheepEdit.setVisible(true);
            sheepJList.setEnabled(false);
            isEditingSheep = true;
        } else {
            sheepJList.setEnabled(true);
            panelSheepEdit.setVisible(false);
            panelSheepInfo.setVisible(true);
            isEditingSheep = false;
        }
    }

    /**
     * Called when the "cancel edit" button is pressed.
     */
    private void btnSheepEditCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSheepEditCancelActionPerformed
        // call the menuEdit keystroke action
        menuEditSheepActionPerformed(null);
    }//GEN-LAST:event_btnSheepEditCancelActionPerformed

    /**
     * Called when the "Saves changes" button is pressed while editing the sheep
     *
     */
    private void btnSheepEditSaveClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSheepEditSaveClicked
        boolean errors = false;
        String errorMessage = "";
        try {
            if (Double.parseDouble(txtSheepEditWeight.getText()) >= 0.0) {

                selectedSheep.setName(txtSheepEditNickname.getText());
                selectedSheep.setWeight(Double.parseDouble(txtSheepEditWeight.getText()));
                selectedSheep.setBorn(dcSheepEditBorn.getDate());
                selectedSheep.setDeceased(dcSheepEditDead.getDate());
                selectedSheep.setComment(taSheepEditComment.getText());

                if (!WebServiceClient.editSheep(selectedSheep)) {
                    errors = true;
                    errorMessage = "Could not update this sheep!";
                }

            } else {
                errors = true;
                errorMessage = "Weight cannot be negative! Please correct this.";
            }

        } catch (Exception ex) {
            errors = true;
            errorMessage = "An error occured. Please check that all the fields are correct.";
        }

        if (!errors) {
            setValueOfDisplayFields(selectedSheep);
            update();
            menuEditSheepActionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSheepEditSaveClicked

    /**
     * Called when the edit password-option is selected from the menu.
     */
    private void menuChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChangePasswordActionPerformed
        new ChangePassword().setVisible(true);
    }//GEN-LAST:event_menuChangePasswordActionPerformed

    /**
     * Called when the deselect sheep-option is selected from the menu.
     */
private void menuDeselectSheepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDeselectSheepActionPerformed
    deselect();
}//GEN-LAST:event_menuDeselectSheepActionPerformed

    /**
     * Fired by the handling code for the event listeners on the "deselect all"
     * button and the "deselect sheep" menu button.
     *
     * Clears all the sheep info labels on the screen, and redraws the map with
     * all the sheep waypoints visible.
     */
    private void deselect() {
        selectedSheep = null;
        sheepJList.clearSelection();
        sheepUpdateJList.clearSelection();
        lblSheepId.setText("-");
        lblSheepPosition.setText("-");
        lblSheepUpdate.setText("-");
        lblSheepNickname.setText("-");
        taSheepComment.setText("");
        lblSheepPulse.setText("-");
        lblSheepTemperature.setText("-");
        lblSheepAlarm.setText("-");
        lblSheepDeceased.setText("-");
        lblSheepBorn.setText("-");
        lblSheepWeight.setText("-");
        resetSelection();
    }

    /**
     * Fired when the "Edit selected sheep"-button is pressed.
     */
private void btnEditSelectedSheepClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSelectedSheepClicked
    editSheep();
}//GEN-LAST:event_btnEditSelectedSheepClicked

    /**
     * Fired when the "deselect all"-button is clicked
     */
private void btnDeselectAllClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeselectAllClicked
    deselect();
}//GEN-LAST:event_btnDeselectAllClicked

    /**
     * Fired when the "delete sheep"-button is clicked
     */
private void btnDeleteSelectedClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedClicked
    if (selectedSheep != null) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove the selected sheep?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        if (choice == 0) {
            removeSheep(selectedSheep);
        }
    } else {
        JOptionPane.showMessageDialog(this, "You have to select a sheep to remove one!");
    }
}//GEN-LAST:event_btnDeleteSelectedClicked

    /**
     * Fired when the "refresh list"-button is clicked
     */
private void btnRefreshListClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshListClicked
    update();
}//GEN-LAST:event_btnRefreshListClicked

    /**
     * Fired when the "remove sheep" option is picked from the menu
     */
private void menuRemoveSheepClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoveSheepClicked
    btnDeleteSelectedClicked(evt);
}//GEN-LAST:event_menuRemoveSheepClicked

    /**
     * Called by the constructor to get and print the farm id.
     */
    private void populateFarmAndName() {
        setTitle("Sheep Finder - " + WebServiceClient.getFarmName());
        lblFarmName.setText(WebServiceClient.getFarmName());
        lblName.setText(WebServiceClient.getName());
    }

    /**
     * Queries the server for a refreshed sheep list.
     */
    public void update() {
        sheepList = WebServiceClient.getSheepList();
        jLabelLastUpdate.setText(new Date().toLocaleString());
        resetSelection();
    }

    /**
     * Display the sheep and update list, draw the sheep on the map.
     */
    private void resetSelection() {
        sheepShow.removeAllElements();
        sheepUpdatesShow.removeAllElements();

        Set<MyWaypoint> waypoints = new HashSet<>();

        if (sheepList != null) {
            for (int i = 0; i < sheepList.size(); i++) {
                Sheep sheep;
                sheep = sheepList.get(i);
                sheepShow.addElement(sheep.getID() + " - " + sheep.getName());
                if (!sheep.getUpdates().isEmpty()) {
                    GeoPosition gp = new GeoPosition(sheep.getUpdates().get(0).getY(), sheep.getUpdates().get(0).getX());
                    MyWaypoint wp = new MyWaypoint(Integer.toString(sheep.getID()), Color.WHITE, gp, i, true);
                    waypoints.add(wp);
                }
            }
        }

        paintWaypoints(null, waypoints);
    }

    /**
     * Focuses the map so that it shows all the WayPoints on the screen, by
     * setting the focus point to the centroid of all the waypoints, and zooming
     * to the appropriate level.
     *
     * @param wp - a set of waypoints to focus on
     */
    private void focusAccordingToWaypoints() {
        // If there are no waypoints to focus on, go to Oppdal
        if (wayPointSet.isEmpty()) {
            GeoPosition oppdal = new GeoPosition(62.573611, 9.608889);
            jXSheepMap.setAddressLocation(oppdal);
            return;
        }

        // Otherwise, convert to set of geoPositions
        Set<GeoPosition> gp = new HashSet<>();

        for (MyWaypoint w : wayPointSet) {
            gp.add(w.getPosition());
        }

        // Find and set the centre position by calculating the mean over all 
        // the positions.
        double x_sum = 0.0;
        double y_sum = 0.0;

        for (GeoPosition g : gp) {
            x_sum += g.getLongitude();
            y_sum += g.getLatitude();
        }

        GeoPosition focus = new GeoPosition(y_sum / gp.size(), x_sum / gp.size());
        jXSheepMap.setAddressLocation(focus);

        // Find the correct zoom. Because calculateZoomFrom finds the minimal
        // amount it has to zoom out before displaying all the geopositions, we 
        // first set zoom to level 1, to make sure we get the minimal focused zoom.
        jXSheepMap.getMainMap().setZoom(1);
        jXSheepMap.getMainMap().calculateZoomFrom(gp);
    }

    /**
     * Called by the constructor to initialize the map.
     */
    private void mapInitialize() {
        WMSService wms = new WMSService();
        wms.setLayer("topo2_WMS");
        wms.setBaseUrl("http://openwms.statkart.no/skwms1/wms.topo2?");
        DefaultTileFactory fact = new WMSTileFactory(wms);

        jXSheepMap.getMainMap().setTileFactory(fact);

        // Use 8 threads in parallel to load the tiles
        fact.setThreadPoolSize(8);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        LocalResponseCache.installResponseCache(fact.getInfo().getBaseURL(), cacheDir, false);

        jXSheepMap.getMiniMap().setVisible(false);

        // Add listeners to enable input
        MouseInputListener mia = new PanMouseInputListener(jXSheepMap.getMainMap());
        jXSheepMap.addMouseListener(mia);
        jXSheepMap.addMouseMotionListener(mia);
        jXSheepMap.addMouseListener(new CenterMapListener(jXSheepMap.getMainMap()));
        jXSheepMap.addMouseWheelListener(new ZoomMouseWheelListenerCursor(jXSheepMap.getMainMap()));
        jXSheepMap.addKeyListener(new PanKeyListener(jXSheepMap.getMainMap()));
        jXSheepMap.addMouseListener(new MouseClickOnWayPointListener(this));

    }

    /**
     * Paints the set of waypoints to the map. If the list of tracks is not
     * null, it also prints a line between the waypoints, according to the track
     * list.
     */
    private void paintWaypoints(List<GeoPosition> track, Set<MyWaypoint> waypoints) {
        List<Painter<JXMapViewer>> painters = new ArrayList<>();

        if (track != null) {
            RoutePainter routePainter = new RoutePainter(track);
            painters.add(routePainter);
        }

        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer());
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        jXSheepMap.getMainMap().setOverlayPainter(painter);

        wayPointSet = waypoints;
        focusAccordingToWaypoints();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSheepEditCancel;
    private javax.swing.JButton btnSheepEditSave;
    private com.toedter.calendar.JDateChooser dcSheepEditBorn;
    private com.toedter.calendar.JDateChooser dcSheepEditDead;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLastUpdate;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator3;
    private org.jdesktop.swingx.JXMapKit jXSheepMap;
    private javax.swing.JLabel lblFarmName;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSheepAlarm;
    private javax.swing.JLabel lblSheepBorn;
    private javax.swing.JLabel lblSheepDeceased;
    private javax.swing.JLabel lblSheepId;
    private javax.swing.JLabel lblSheepNickname;
    private javax.swing.JLabel lblSheepPosition;
    private javax.swing.JLabel lblSheepPulse;
    private javax.swing.JLabel lblSheepTemperature;
    private javax.swing.JLabel lblSheepUpdate;
    private javax.swing.JLabel lblSheepWeight;
    private javax.swing.JMenuItem menuAddSheep;
    private javax.swing.JMenuItem menuChangePassword;
    private javax.swing.JMenuItem menuCloseProgram;
    private javax.swing.JMenuItem menuEditSheep;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JMenuItem menuPrefFarm;
    private javax.swing.JMenuItem menuPrefUser;
    private javax.swing.JMenu menuPreferences;
    private javax.swing.JMenuItem menuRefresh;
    private javax.swing.JMenu menuSheep;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelSheepEdit;
    private javax.swing.JPanel panelSheepInfo;
    private javax.swing.JList sheepJList;
    private javax.swing.JList sheepUpdateJList;
    private javax.swing.JTextArea taSheepComment;
    private javax.swing.JTextArea taSheepEditComment;
    private javax.swing.JTextField txtSheepEditId;
    private javax.swing.JTextField txtSheepEditNickname;
    private javax.swing.JTextField txtSheepEditWeight;
    // End of variables declaration//GEN-END:variables
}
