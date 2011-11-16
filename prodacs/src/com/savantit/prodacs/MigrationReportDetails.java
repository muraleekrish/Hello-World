/*
 * Created on Aug 16, 2005
 *
 * ClassName	:  	MigrationReportDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 */
public class MigrationReportDetails implements Serializable
{
    private String title;
    private String header1;
    private String header2;
    private String header3;
    private String value1;
    private String value2;
    private String value3;
    public MigrationReportDetails()
    {
        title = "";
        header1 = "";
        header2 = "";
        header3 = "";
        value1 = "";
        value2 = "";
        value3 = "";
    }
    
    

    /**
     * @return Returns the header1.
     */
    public String getHeader1()
    {
        return header1;
    }
    /**
     * @param header1 The header1 to set.
     */
    public void setHeader1(String header1)
    {
        this.header1 = header1;
    }
    /**
     * @return Returns the header2.
     */
    public String getHeader2()
    {
        return header2;
    }
    /**
     * @param header2 The header2 to set.
     */
    public void setHeader2(String header2)
    {
        this.header2 = header2;
    }
    /**
     * @return Returns the header3.
     */
    public String getHeader3()
    {
        return header3;
    }
    /**
     * @param header3 The header3 to set.
     */
    public void setHeader3(String header3)
    {
        this.header3 = header3;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * @return Returns the value1.
     */
    public String getValue1()
    {
        return value1;
    }
    /**
     * @param value1 The value1 to set.
     */
    public void setValue1(String value1)
    {
        this.value1 = value1;
    }
    /**
     * @return Returns the value2.
     */
    public String getValue2()
    {
        return value2;
    }
    /**
     * @param value2 The value2 to set.
     */
    public void setValue2(String value2)
    {
        this.value2 = value2;
    }
    /**
     * @return Returns the value3.
     */
    public String getValue3()
    {
        return value3;
    }
    /**
     * @param value3 The value3 to set.
     */
    public void setValue3(String value3)
    {
        this.value3 = value3;
    }
}

/*
*$Log: MigrationReportDetails.java,v $
*Revision 1.1  2005/08/23 09:24:49  kduraisamy
*CHECK EMP TYPE SQL QUERY ADDED.
*
*
*/