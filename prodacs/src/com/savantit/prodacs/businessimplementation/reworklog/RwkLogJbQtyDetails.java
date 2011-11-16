/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.reworklog;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RwkLogJbQtyDetails implements Serializable 
{
	private int jbQtySno;
	private int jbStatId;
	private String unPostedProdnSnos;
	private String postedProdnSnos;
	private Vector vecRwkLogJbOpnDetails;
	public RwkLogJbQtyDetails()
	{
		jbQtySno = 0;
		jbStatId = 0;
		unPostedProdnSnos = "";
		postedProdnSnos = "";
		vecRwkLogJbOpnDetails = null;
	}
	
	/**
	 * @return Returns the jbQtySno.
	 */
	public int getJbQtySno() {
		return jbQtySno;
	}
	/**
	 * @param jbQtySno The jbQtySno to set.
	 */
	public void setJbQtySno(int jbQtySno) {
		this.jbQtySno = jbQtySno;
	}
	/**
	 * @return Returns the vecRwkLogJbOpnDetails.
	 */
	public Vector getVecRwkLogJbOpnDetails() {
		return vecRwkLogJbOpnDetails;
	}
	/**
	 * @param vecRwkLogJbOpnDetails The vecRwkLogJbOpnDetails to set.
	 */
	public void setVecRwkLogJbOpnDetails(Vector vecRwkLogJbOpnDetails) {
		this.vecRwkLogJbOpnDetails = vecRwkLogJbOpnDetails;
	}
	/**
	 * @return Returns the jbStatId.
	 */
	public int getJbStatId() {
		return jbStatId;
	}
	/**
	 * @param jbStatId The jbStatId to set.
	 */
	public void setJbStatId(int jbStatId) {
		this.jbStatId = jbStatId;
	}
	/**
	 * @return Returns the postedProdnSnos.
	 */
	public String getPostedProdnSnos() {
		return postedProdnSnos;
	}
	/**
	 * @param postedProdnSnos The postedProdnSnos to set.
	 */
	public void setPostedProdnSnos(String postedProdnSnos) {
		this.postedProdnSnos = postedProdnSnos;
	}
	/**
	 * @return Returns the unPostedProdnSnos.
	 */
	public String getUnPostedProdnSnos() {
		return unPostedProdnSnos;
	}
	/**
	 * @param unPostedProdnSnos The unPostedProdnSnos to set.
	 */
	public void setUnPostedProdnSnos(String unPostedProdnSnos) {
		this.unPostedProdnSnos = unPostedProdnSnos;
	}
}
