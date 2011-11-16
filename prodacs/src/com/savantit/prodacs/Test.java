/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	Test.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author kduraisamy
 *
 */
public class Test
{
    public static void main(String args[])
    {
        int month = 0;
        int year = 0;
        int date = 0;
        Date endDate = null;
        Date startDate = null;
        
       date = 30;
       GregorianCalendar gc = new GregorianCalendar(2005,0,date);
       month = gc.get(GregorianCalendar.MONTH);
       System.out.println("month:"+month); 
       year = gc.get(GregorianCalendar.YEAR);
       GregorianCalendar gc1 =new GregorianCalendar(year,month,date);
       startDate = gc1.getTime();
       gc1.add(Calendar.MONTH,1);
       gc1.add(Calendar.DAY_OF_MONTH,-1);
       endDate = gc1.getTime();
       System.out.println("startDate: "+startDate);
       System.out.println("endDate: "+endDate);
              
       
    }
}

/*
*$Log: Test.java,v $
*Revision 1.1  2005/08/23 09:24:49  kduraisamy
*CHECK EMP TYPE SQL QUERY ADDED.
*
*
*/