/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.infra.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DateUtil
{

	public String sqlDate(Date d)
	{
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    int year = cal.get(Calendar.YEAR);
	   
	    return year + "-" + month + "-" + day ;
	}
}


/***
$Log: DateUtil.java,v $
Revision 1.2  2004/11/06 07:43:14  sduraisamy
Log inclusion for Filter,DBConnection,DBNull,SQLMaster,DateUtil,Debug and LoggerOutput

***/