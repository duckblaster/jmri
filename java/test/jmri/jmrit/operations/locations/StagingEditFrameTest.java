//StagingEditFrameTest.java
package jmri.jmrit.operations.locations;

import java.io.File;
import java.util.List;
import java.util.Locale;
import jmri.jmrit.operations.rollingstock.engines.EngineManagerXml;
import jmri.jmrit.operations.routes.RouteManagerXml;
import jmri.jmrit.operations.setup.OperationsSetupXml;
import jmri.jmrit.operations.trains.TrainManagerXml;
import jmri.util.JmriJFrame;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.AbstractButtonFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests for the Operations Locations GUI class
 *
 * @author	Dan Boudreau Copyright (C) 2009
 * @version $Revision$
 */
public class StagingEditFrameTest extends jmri.util.SwingTestCase {

    final static int ALL = Track.EAST + Track.WEST + Track.NORTH + Track.SOUTH;

    /**
     * Staging tracks needs its own location
     */
    public void testStagingEditFrame() {
        LocationManager lManager = LocationManager.instance();
        Location l = lManager.getLocationByName("Test Loc A");
        Assert.assertNotNull("Test Loc A", l);
        StagingEditFrame f = new StagingEditFrame();
        f.setTitle("Test Staging Add Frame");
        f.setLocation(0, 0);	// entire panel must be visible for tests to work properly
        f.initComponents(l, null);

        // create four staging tracks
        f.trackNameTextField.setText("new staging track");
        f.trackLengthTextField.setText("34");
        getHelper().enterClickAndLeave(new MouseEventData(this, f.addTrackButton));

        f.trackNameTextField.setText("2nd staging track");
        f.trackLengthTextField.setText("3456");
        getHelper().enterClickAndLeave(new MouseEventData(this, f.addTrackButton));

        f.trackNameTextField.setText("3rd staging track");
        f.trackLengthTextField.setText("1");
        getHelper().enterClickAndLeave(new MouseEventData(this, f.addTrackButton));

        f.trackNameTextField.setText("4th staging track");
        f.trackLengthTextField.setText("12");
        getHelper().enterClickAndLeave(new MouseEventData(this, f.addTrackButton));

        // deselect east, west and south check boxes
        getHelper().enterClickAndLeave(new MouseEventData(this, f.northCheckBox));
        getHelper().enterClickAndLeave(new MouseEventData(this, f.westCheckBox));
        getHelper().enterClickAndLeave(new MouseEventData(this, f.southCheckBox));

        getHelper().enterClickAndLeave(new MouseEventData(this, f.saveTrackButton));

        sleep(1);	// for slow machines
        Track t = l.getTrackByName("new staging track", null);
        Assert.assertNotNull("new staging track", t);
        Assert.assertEquals("staging track length", 34, t.getLength());
        // check that the defaults are correct
        Assert.assertEquals("all directions", ALL, t.getTrainDirections());
        Assert.assertEquals("all roads", Track.ALL_ROADS, t.getRoadOption());

        t = l.getTrackByName("2nd staging track", null);
        Assert.assertNotNull("2nd staging track", t);
        Assert.assertEquals("2nd staging track length", 3456, t.getLength());
        // check that the defaults are correct
        Assert.assertEquals("all directions", ALL, t.getTrainDirections());
        Assert.assertEquals("all roads", Track.ALL_ROADS, t.getRoadOption());

        t = l.getTrackByName("3rd staging track", null);
        Assert.assertNotNull("3rd staging track", t);
        Assert.assertEquals("3rd staging track length", 1, t.getLength());
        // check that the defaults are correct
        Assert.assertEquals("all directions", ALL, t.getTrainDirections());
        Assert.assertEquals("all roads", Track.ALL_ROADS, t.getRoadOption());

        t = l.getTrackByName("4th staging track", null);
        Assert.assertNotNull("4th staging track", t);
        Assert.assertEquals("4th staging track length", 12, t.getLength());
        Assert.assertEquals("only east", Track.EAST, t.getTrainDirections());

        f.dispose();

        Location l2 = lManager.getLocationByName("Test Loc A");
        Assert.assertNotNull("Test Loc A", l2);

        LocationEditFrame fl = new LocationEditFrame();
        fl.setTitle("Test Edit Location Frame Staging");
        fl.initComponents(l2);

        // check location name
        Assert.assertEquals("name", "Test Loc A", fl.locationNameTextField.getText());

        Assert.assertEquals("number of sidings", 0, fl.spurModel.getRowCount());
        Assert.assertEquals("number of interchanges", 0, fl.interchangeModel.getRowCount());
        Assert.assertEquals("number of yards", 0, fl.yardModel.getRowCount());
        Assert.assertEquals("number of staging tracks", 4, fl.stagingModel.getRowCount());

        // is the staging only button selected?
        Assert.assertTrue("staging selected", fl.stageRadioButton.isSelected());

        fl.dispose();
    }

    @SuppressWarnings("unchecked")
    private void pressDialogButton(JmriJFrame f, String buttonName) {
        //  (with JfcUnit, not pushing this off to another thread)			                                            
        // Locate resulting dialog box
        List<javax.swing.JDialog> dialogList = new DialogFinder(null).findAll(f);
        javax.swing.JDialog d = dialogList.get(0);
        // Find the button
        AbstractButtonFinder finder = new AbstractButtonFinder(buttonName);
        javax.swing.JButton button = (javax.swing.JButton) finder.find(d, 0);
        Assert.assertNotNull("button not found", button);
        // Click button
        getHelper().enterClickAndLeave(new MouseEventData(this, button));
    }

    private void loadLocations() {
        // create 5 locations
        LocationManager lManager = LocationManager.instance();
        Location l1 = lManager.newLocation("Test Loc E");
        l1.setLength(1001);
        Location l2 = lManager.newLocation("Test Loc D");
        l2.setLength(1002);
        Location l3 = lManager.newLocation("Test Loc C");
        l3.setLength(1003);
        Location l4 = lManager.newLocation("Test Loc B");
        l4.setLength(1004);
        Location l5 = lManager.newLocation("Test Loc A");
        l5.setLength(1005);

    }

    // Ensure minimal setup for log4J
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        apps.tests.Log4JFixture.setUp();

        // set the locale to US English
        Locale.setDefault(Locale.ENGLISH);

        // Repoint OperationsSetupXml to JUnitTest subdirectory
        OperationsSetupXml.setOperationsDirectoryName("operations" + File.separator + "JUnitTest");
        // Change file names to ...Test.xml
        OperationsSetupXml.instance().setOperationsFileName("OperationsJUnitTest.xml");
        RouteManagerXml.instance().setOperationsFileName("OperationsJUnitTestRouteRoster.xml");
        EngineManagerXml.instance().setOperationsFileName("OperationsJUnitTestEngineRoster.xml");
        LocationManagerXml.instance().setOperationsFileName("OperationsJUnitTestLocationRoster.xml");
        LocationManagerXml.instance().setOperationsFileName("OperationsJUnitTestLocationRoster.xml");
        TrainManagerXml.instance().setOperationsFileName("OperationsJUnitTestTrainRoster.xml");

        // clear out previous locations
        LocationManager.instance().dispose();

        loadLocations();
    }

    public StagingEditFrameTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {"-noloading", StagingEditFrameTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(StagingEditFrameTest.class);
        return suite;
    }

    // The minimal setup for log4J
    @Override
    protected void tearDown() throws Exception {
        apps.tests.Log4JFixture.tearDown();
        super.tearDown();
    }
}
