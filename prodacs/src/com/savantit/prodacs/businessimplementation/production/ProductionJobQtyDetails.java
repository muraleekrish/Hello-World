/*
 * Created on Dec 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductionJobQtyDetails implements Serializable 
{
	private int jobQtySno;
	private int jobStatId;
	private String pendingOpnSnos;
	private String postedOpnSnos;
	private String unPostedOpnSnos;
	private String rwkOpns;
	private Date lastProdDate;
	private int shiftId;
	
	public ProductionJobQtyDetails()
	{
		jobStatId = 0;
		jobQtySno = 0;
		pendingOpnSnos = "";
		postedOpnSnos = "";
		unPostedOpnSnos = "";
		lastProdDate = null;
		rwkOpns = "";
		shiftId = 0;
	}
	
	/**
	 * @return Returns the jobQtySno.
	 */
	public int getJobQtySno() {
		return jobQtySno;
	}
	/**
	 * @param jobQtySno The jobQtySno to set.
	 */
	public void setJobQtySno(int jobQtySno) {
		this.jobQtySno = jobQtySno;
	}
	/**
	 * @return Returns the pendingOpnSnos.
	 */
	public String getPendingOpnSnos() {
		return pendingOpnSnos;
	}
	/**
	 * @param pendingOpnSnos The pendingOpnSnos to set.
	 */
	public void setPendingOpnSnos(String pendingOpnSnos) {
		this.pendingOpnSnos = pendingOpnSnos;
	}
	/**
	 * @return Returns the postedOpnSnos.
	 */
	public String getPostedOpnSnos() {
		return postedOpnSnos;
	}
	/**
	 * @param postedOpnSnos The postedOpnSnos to set.
	 */
	public void setPostedOpnSnos(String postedOpnSnos) {
		this.postedOpnSnos = postedOpnSnos;
	}
	/**
	 * @return Returns the shiftId.
	 */
	public int getShiftId() {
		return shiftId;
	}
	/**
	 * @param shiftId The shiftId to set.
	 */
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	/**
	 * @return Returns the unPostedOpnSnos.
	 */
	public String getUnPostedOpnSnos() {
		return unPostedOpnSnos;
	}
	/**
	 * @param unPostedOpnSnos The unPostedOpnSnos to set.
	 */
	public void setUnPostedOpnSnos(String unPostedOpnSnos) {
		this.unPostedOpnSnos = unPostedOpnSnos;
	}
	/**
	 * @return Returns the jobStatId.
	 */
	public int getJobStatId() {
		return jobStatId;
	}
	/**
	 * @param jobStatId The jobStatId to set.
	 */
	public void setJobStatId(int jobStatId) {
		this.jobStatId = jobStatId;
	}
    /**
     * @return Returns the rwkOpns.
     */
    public String getRwkOpns()
    {
        return rwkOpns;
    }
    /**
     * @param rwkOpns The rwkOpns to set.
     */
    public void setRwkOpns(String rwkOpns)
    {
        this.rwkOpns = rwkOpns;
    }
    /**
     * @return Returns the lastProdDate.
     */
    public Date getLastProdDate()
    {
        return lastProdDate;
    }
    /**
     * @param lastProdDate The lastProdDate to set.
     */
    public void setLastProdDate(Date lastProdDate)
    {
        this.lastProdDate = lastProdDate;
    }
}
