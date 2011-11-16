/*
 * Created on Nov 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workcalendar;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomAvbltyDetails implements Serializable
{
	private Date forDate;
	Vector vec_ShiftDet;
	
    /**
     * @return Returns the forDate.
     */
    public Date getForDate()
    {
        return forDate;
    }
    /**
     * @param forDate The forDate to set.
     */
    public void setForDate(Date forDate)
    {
        this.forDate = forDate;
    }
    /**
     * @return Returns the vec_ShiftDet.
     */
    public Vector getVec_ShiftDet()
    {
        return vec_ShiftDet;
    }
    /**
     * @param vec_ShiftDet The vec_ShiftDet to set.
     */
    public void setVec_ShiftDet(Vector vec_ShiftDet)
    {
        this.vec_ShiftDet = vec_ShiftDet;
    }
}
/***
$Log: CustomAvbltyDetails.java,v $
Revision 1.5  2005/04/29 10:58:50  kduraisamy
custom avbltyDetails object structure changed.

Revision 1.4  2005/04/28 14:21:33  kduraisamy
presr shift added .

Revision 1.3  2005/04/28 10:01:23  kduraisamy
CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED. IN GET METHOD.

Revision 1.2  2005/04/28 09:15:00  kduraisamy
CUSTOM AVBLTY STARTDAY ,ENDDAY ADDED.

Revision 1.1  2005/04/20 10:10:10  kduraisamy
avbltyDetails() add method changed.

Revision 1.3  2004/12/09 05:51:33  kduraisamy
Log added.

***/