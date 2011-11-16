/*
 * Created on Dec 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DummyWorkOrderDetails implements Serializable
{
	private int dmyWoId;
	private String dmyWoNo;
	private Date dmyWoDate;
	private int custId;
	private String custName;
	private String dmyWoJbName;
	private String dmyWoJbDrwgNo;
	private String dmyWoJbRvsnNo;
	private String dmyWoJbMatlTyp;
	private String dmyWoStat;
	private int dmyWoUsedCount;
	private Date dmyWoLastUsed;
	private int dmyWoIsValid;
	private String dmyWoGnrlName;
	private int dmyWoGnrlId;
	private int dmyOpnGpId;
	private String dmyOpnName;
	private int dmyStartOpn;
	private int dmyEndOpn;
	private int startSno;
	private int jbQty;
	private String woDCNo;
	private Date woDCDate;
	
	public DummyWorkOrderDetails()
	{
	    this.dmyWoId = 0;
	    this.dmyWoNo = "";
	    this.custId = 0;
	    this.custName = "";
	    this.dmyWoJbName = "";
	    this.dmyWoJbDrwgNo = "";
	    this.dmyWoJbRvsnNo = "";
	    this.dmyWoJbMatlTyp = "";
	    this.dmyWoStat = "";
	    this.dmyWoUsedCount = 0;
	    this.dmyWoGnrlName = "";
	    this.dmyWoGnrlId = 0;
	    this.dmyWoIsValid = 0;
	    this.dmyOpnGpId = 0;
	    this.dmyOpnName = "";
	    this.dmyStartOpn = 0;
	    this.dmyEndOpn = 0;
	    this.startSno = 0;
	    this.jbQty = 0;
		this.woDCNo = "";
		this.woDCDate = null;
	}
		
    /**
     * @return Returns the custId.
     */
    public int getCustId()
    {
        return custId;
    }
    /**
     * @param custId The custId to set.
     */
    public void setCustId(int custId)
    {
        this.custId = custId;
    }
    /**
     * @return Returns the custName.
     */
    public String getCustName()
    {
        return custName;
    }
    /**
     * @param custName The custName to set.
     */
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    /**
     * @return Returns the dmyWoDate.
     */
    public Date getDmyWoDate()
    {
        return dmyWoDate;
    }
    /**
     * @param dmyWoDate The dmyWoDate to set.
     */
    public void setDmyWoDate(Date dmyWoDate)
    {
        this.dmyWoDate = dmyWoDate;
    }
    /**
     * @return Returns the dmyWoGnrlName.
     */
    public String getDmyWoGnrlName()
    {
        return dmyWoGnrlName;
    }
    /**
     * @param dmyWoGnrlName The dmyWoGnrlName to set.
     */
    public void setDmyWoGnrlName(String dmyWoGnrlName)
    {
        this.dmyWoGnrlName = dmyWoGnrlName;
    }
    /**
     * @return Returns the dmyWoId.
     */
    public int getDmyWoId()
    {
        return dmyWoId;
    }
    /**
     * @param dmyWoId The dmyWoId to set.
     */
    public void setDmyWoId(int dmyWoId)
    {
        this.dmyWoId = dmyWoId;
    }
    /**
     * @return Returns the dmyWoIsValid.
     */
    public int getDmyWoIsValid()
    {
        return dmyWoIsValid;
    }
    /**
     * @param dmyWoIsValid The dmyWoIsValid to set.
     */
    public void setDmyWoIsValid(int dmyWoIsValid)
    {
        this.dmyWoIsValid = dmyWoIsValid;
    }
    /**
     * @return Returns the dmyWoJbDrwgNo.
     */
    public String getDmyWoJbDrwgNo()
    {
        return dmyWoJbDrwgNo;
    }
    /**
     * @param dmyWoJbDrwgNo The dmyWoJbDrwgNo to set.
     */
    public void setDmyWoJbDrwgNo(String dmyWoJbDrwgNo)
    {
        this.dmyWoJbDrwgNo = dmyWoJbDrwgNo;
    }
    /**
     * @return Returns the dmyWoJbMatlTyp.
     */
    public String getDmyWoJbMatlTyp()
    {
        return dmyWoJbMatlTyp;
    }
    /**
     * @param dmyWoJbMatlTyp The dmyWoJbMatlTyp to set.
     */
    public void setDmyWoJbMatlTyp(String dmyWoJbMatlTyp)
    {
        this.dmyWoJbMatlTyp = dmyWoJbMatlTyp;
    }
    /**
     * @return Returns the dmyWoJbName.
     */
    public String getDmyWoJbName()
    {
        return dmyWoJbName;
    }
    /**
     * @param dmyWoJbName The dmyWoJbName to set.
     */
    public void setDmyWoJbName(String dmyWoJbName)
    {
        this.dmyWoJbName = dmyWoJbName;
    }
    /**
     * @return Returns the dmyWoJbRvsnNo.
     */
    public String getDmyWoJbRvsnNo()
    {
        return dmyWoJbRvsnNo;
    }
    /**
     * @param dmyWoJbRvsnNo The dmyWoJbRvsnNo to set.
     */
    public void setDmyWoJbRvsnNo(String dmyWoJbRvsnNo)
    {
        this.dmyWoJbRvsnNo = dmyWoJbRvsnNo;
    }
    /**
     * @return Returns the dmyWoLastUsed.
     */
    public Date getDmyWoLastUsed()
    {
        return dmyWoLastUsed;
    }
    /**
     * @param dmyWoLastUsed The dmyWoLastUsed to set.
     */
    public void setDmyWoLastUsed(Date dmyWoLastUsed)
    {
        this.dmyWoLastUsed = dmyWoLastUsed;
    }
    /**
     * @return Returns the dmyWoNo.
     */
    public String getDmyWoNo()
    {
        return dmyWoNo;
    }
    /**
     * @param dmyWoNo The dmyWoNo to set.
     */
    public void setDmyWoNo(String dmyWoNo)
    {
        this.dmyWoNo = dmyWoNo;
    }
    /**
     * @return Returns the dmyWoStat.
     */
    public String getDmyWoStat()
    {
        return dmyWoStat;
    }
    /**
     * @param dmyWoStat The dmyWoStat to set.
     */
    public void setDmyWoStat(String dmyWoStat)
    {
        this.dmyWoStat = dmyWoStat;
    }
    /**
     * @return Returns the dmyWoUsedCount.
     */
    public int getDmyWoUsedCount()
    {
        return dmyWoUsedCount;
    }
    /**
     * @param dmyWoUsedCount The dmyWoUsedCount to set.
     */
    public void setDmyWoUsedCount(int dmyWoUsedCount)
    {
        this.dmyWoUsedCount = dmyWoUsedCount;
    }
    /**
     * @return Returns the dmyWoGnrlId.
     */
    public int getDmyWoGnrlId()
    {
        return dmyWoGnrlId;
    }
    /**
     * @param dmyWoGnrlId The dmyWoGnrlId to set.
     */
    public void setDmyWoGnrlId(int dmyWoGnrlId)
    {
        this.dmyWoGnrlId = dmyWoGnrlId;
    }
    /**
     * @return Returns the dmyOpnGpId.
     */
    public int getDmyOpnGpId()
    {
        return dmyOpnGpId;
    }
    /**
     * @param dmyOpnGpId The dmyOpnGpId to set.
     */
    public void setDmyOpnGpId(int dmyOpnGpId)
    {
        this.dmyOpnGpId = dmyOpnGpId;
    }
    /**
     * @return Returns the dmyOpnName.
     */
    public String getDmyOpnName()
    {
        return dmyOpnName;
    }
    /**
     * @param dmyOpnName The dmyOpnName to set.
     */
    public void setDmyOpnName(String dmyOpnName)
    {
        this.dmyOpnName = dmyOpnName;
    }
    /**
     * @return Returns the dmyEndOpn.
     */
    public int getDmyEndOpn()
    {
        return dmyEndOpn;
    }
    /**
     * @param dmyEndOpn The dmyEndOpn to set.
     */
    public void setDmyEndOpn(int dmyEndOpn)
    {
        this.dmyEndOpn = dmyEndOpn;
    }
    /**
     * @return Returns the dmyStartOpn.
     */
    public int getDmyStartOpn()
    {
        return dmyStartOpn;
    }
    /**
     * @param dmyStartOpn The dmyStartOpn to set.
     */
    public void setDmyStartOpn(int dmyStartOpn)
    {
        this.dmyStartOpn = dmyStartOpn;
    }
    /**
     * @return Returns the jbQty.
     */
    public int getJbQty()
    {
        return jbQty;
    }
    /**
     * @param jbQty The jbQty to set.
     */
    public void setJbQty(int jbQty)
    {
        this.jbQty = jbQty;
    }
    /**
     * @return Returns the startSno.
     */
    public int getStartSno()
    {
        return startSno;
    }
    /**
     * @param startSno The startSno to set.
     */
    public void setStartSno(int startSno)
    {
        this.startSno = startSno;
    }
    /**
     * @return Returns the woDCNo.
     */
    public String getWoDCNo()
    {
        return woDCNo;
    }
    /**
     * @param woDCNo The woDCNo to set.
     */
    public void setWoDCNo(String woDCNo)
    {
        this.woDCNo = woDCNo;
    }
    /**
     * @return Returns the woDCDate.
     */
    public Date getWoDCDate()
    {
        return woDCDate;
    }
    /**
     * @param woDCDate The woDCDate to set.
     */
    public void setWoDCDate(Date woDCDate)
    {
        this.woDCDate = woDCDate;
    }
}
/***
$Log: DummyWorkOrderDetails.java,v $
Revision 1.6  2005/07/25 06:36:40  kduraisamy
WOJB DCDATE ADDED.

Revision 1.5  2005/07/23 11:15:11  kduraisamy
CUSTOMER WORK ORDER DC NO ADDED.

Revision 1.4  2005/02/07 06:10:15  kduraisamy
additional fields added.

Revision 1.3  2004/12/16 10:34:46  sduraisamy
DWOIsValid and DWODate included

Revision 1.2  2004/12/09 05:54:46  kduraisamy
Log added.

***/