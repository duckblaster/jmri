// PacketGenTest.java
package jmri.jmrix.lenz.swing.packetgen;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for the jmri.jmrix.lenz.swing.packetgen.package
 *
 * @author Paul Bender
 * @version $Revision$
 */
public class PacketGenTest extends TestCase {

    // from here down is testing infrastructure
    public PacketGenTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {PacketGenTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite("jmri.jmrix.lenz.swing.packetgen.PacketGenTest");  // no tests in this class itself
        suite.addTest(new TestSuite(PacketGenFrameTest.class));
        return suite;
    }

    static Logger log = LoggerFactory.getLogger(PacketGenTest.class.getName());

}
