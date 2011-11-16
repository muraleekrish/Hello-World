/*
 * Created on Jan 18, 2006
 *
 * ClassName	:  	DrawingDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 */
public class DrawingDetails implements Serializable
{
    private byte[] dwngImgBytes;
    private String dwngNo;
    public DrawingDetails()
    {
        this.dwngImgBytes = null;
        this.dwngNo = "";
    }
    

    public byte[] getDwngImgBytes()
    {
        return dwngImgBytes;
    }
    public void setDwngImgBytes(byte[] dwngImgBytes)
    {
        this.dwngImgBytes = dwngImgBytes;
    }
    public String getDwngNo()
    {
        return dwngNo;
    }
    public void setDwngNo(String dwngNo)
    {
        this.dwngNo = dwngNo;
    }
}

/*
*$Log: DrawingDetails.java,v $
*Revision 1.1  2006/01/18 13:27:45  kduraisamy
*initial commit.
*
*
*/