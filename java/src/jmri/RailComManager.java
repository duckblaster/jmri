// RailComManager.java
package jmri;

/**
 * Locate a RailCom Object representing a specific RailCom Enabled device.<br>
 * RailCom is a registered trademark of Lenz GmbH.
 * <P>
 * RailCom objects are obtained from an RailComManager, which in turn is
 * generally located from the InstanceManager. A typical call sequence might be:
 * <PRE>
 * RailCom rc = InstanceManager.getDefault(jmri.RailComManager.class).provideIdTag("23");
 * </PRE> The RailCom Manager itself is not installed unless the required
 * hardware that supports RailCom has been installed.
 * <p>
 * Although the RailCom object does extend the NamedBean, it doesn't
 * specifically use the system or user names as each RailCom device should in
 * itself be unique.
 * <P>
 * @author Kevin Dickerson Copyright (C) 2012
 * @version $Revision: 18102 $
 * @since 2.99.4
 */
public interface RailComManager extends IdTagManager {

    public RailCom provideIdTag(String name);

    public RailCom getIdTag(String name);

}

/* @(#)RailComManager.java */
