/*
 * Created on Apr 29, 2005
 *
 * ClassName	:  	general.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.infra.util;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;

/**
 * @author kduraisamy
 *
 */
public class General
{
    	String query = "";
    	DBConnection con = null;
    	public boolean checkExistence(String tableName,String fieldName,String fieldValue) throws SQLException
    	{
    	    boolean existenceRESULT = false;
    	    con = DBConnectionFactory.getConnection();
    	    query = "select count(*) from "+tableName+" where "+fieldName+"='"+fieldValue+"'";
    	    PreparedStatement ps = con.executeStatement(query);
    	    ResultSet rs = ps.executeQuery();
    	    int count = 0;
    	    if(rs.next())
    	    {
    	        count = rs.getInt(1);
    	    }
    	    
    	    if(count>0)
    	    {
    	        existenceRESULT = true;
    	    }
    	    return existenceRESULT;
    	}
    	public static void main(String args[]) throws SQLException
    	{
    	    General objGeneral = new General();
    	    System.out.println(objGeneral.checkExistence("cust_mstr","cust_name","saravan"));
    	}
    	
}

/*
*$Log: General.java,v $
*Revision 1.1  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*
*/