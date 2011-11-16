package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class JobReceiptDetails implements Serializable
{
    private Vector vecOrderDetails;
    private int balanceForward;
    private int totalQty;
    public JobReceiptDetails()
    {
        this.vecOrderDetails = new Vector();
        this.balanceForward = 0;
        this.totalQty = 0;
    }
    

    /**
     * @return Returns the balanceForward.
     */
    public int getBalanceForward()
    {
        return balanceForward;
    }
    /**
     * @param balanceForward The balanceForward to set.
     */
    public void setBalanceForward(int balanceForward)
    {
        this.balanceForward = balanceForward;
    }
    /**
     * @return Returns the totalQty.
     */
    public int getTotalQty()
    {
        return totalQty;
    }
    /**
     * @param totalQty The totalQty to set.
     */
    public void setTotalQty(int totalQty)
    {
        this.totalQty = totalQty;
    }
    /**
     * @return Returns the vecOrderDetails.
     */
    public Vector getVecOrderDetails()
    {
        return vecOrderDetails;
    }
    /**
     * @param vecOrderDetails The vecOrderDetails to set.
     */
    public void setVecOrderDetails(Vector vecOrderDetails)
    {
        this.vecOrderDetails = vecOrderDetails;
    }
}

/*
*$Log: JobReceiptDetails.java,v $
*Revision 1.1  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*
*/