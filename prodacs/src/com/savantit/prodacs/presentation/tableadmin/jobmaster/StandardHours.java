/*
 * Created on Jun 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

import java.io.Serializable;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StandardHours implements Serializable 
{
	private boolean stdhrs = true;
	private boolean joblevelstdhrs = true; 
	private boolean opnlevelstdhrs = false;
	private boolean incentive = false;
	private boolean joblevelincentive = false;
	private boolean jobopnlevelincentive = false;
	
	
	/**
	 * @return Returns the joblevelstdhrs.
	 */
	public boolean isJoblevelstdhrs() {
		return joblevelstdhrs;
	}
	/**
	 * @param joblevelstdhrs The joblevelstdhrs to set.
	 */
	public void setJoblevelstdhrs(boolean joblevelstdhrs) {
		this.joblevelstdhrs = joblevelstdhrs;
	}
	/**
	 * @return Returns the opnlevelstdhrs.
	 */
	public boolean isOpnlevelstdhrs() {
		return opnlevelstdhrs;
	}
	/**
	 * @param opnlevelstdhrs The opnlevelstdhrs to set.
	 */
	public void setOpnlevelstdhrs(boolean opnlevelstdhrs) {
		this.opnlevelstdhrs = opnlevelstdhrs;
	}
	/**
	 * @return Returns the stdhrs.
	 */
	public boolean isStdhrs() {
		return stdhrs;
	}
	/**
	 * @param stdhrs The stdhrs to set.
	 */
	public void setStdhrs(boolean stdhrs) {
		this.stdhrs = stdhrs;
	}
	/**
	 * @return Returns the incentive.
	 */
	public boolean isIncentive() {
		return incentive;
	}
	/**
	 * @param incentive The incentive to set.
	 */
	public void setIncentive(boolean incentive) {
		this.incentive = incentive;
	}
	/**
	 * @return Returns the joblevelincentive.
	 */
	public boolean isJoblevelincentive() {
		return joblevelincentive;
	}
	/**
	 * @param joblevelincentive The joblevelincentive to set.
	 */
	public void setJoblevelincentive(boolean joblevelincentive) {
		this.joblevelincentive = joblevelincentive;
	}
	/**
	 * @return Returns the jobopnlevelincentive.
	 */
	public boolean isJobopnlevelincentive() {
		return jobopnlevelincentive;
	}
	/**
	 * @param jobopnlevelincentive The jobopnlevelincentive to set.
	 */
	public void setJobopnlevelincentive(boolean jobopnlevelincentive) {
		this.jobopnlevelincentive = jobopnlevelincentive;
	}
}

/***
$Log: StandardHours.java,v $
Revision 1.1  2005/06/23 13:25:58  vkrishnamoorthy
Initial commit on ProDACS.

***/