/*
 * Created on Jan 4, 2005
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 */
package com.savantit.prodacs.infra.dbtools;

import com.savantit.prodacs.infra.dbtools.mysql.MysqlDBConnection;
import com.savantit.prodacs.infra.dbtools.oracle.OracleDBConnection;
import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author pkrishnan
 *
 */
public class DBConnectionFactory 
{
	public static DBConnection getConnection()
	{
	    DBConnection con = null;
	    if(BuildConfig.DBASE == BuildConfig.ORACLE)
		{
			con = new OracleDBConnection();
		}
		else if(BuildConfig.DBASE == BuildConfig.MYSQL)
		{
			con = new MysqlDBConnection();
		}
		return con;
	}
	
	public static void main(String a[])
	{
		DBConnectionFactory.getConnection();
	}
}

/***
 * $Log: DBConnectionFactory.java,v $
 * Revision 1.2  2005/03/07 14:17:40  kduraisamy
 * initial commit.
 *
 * Revision 1.1  2005/03/04 07:23:20  kduraisamy
 * initial commit.
 *
 * Revision 1.8  2005/02/02 09:44:44  dsomasundaram
 * 2DO Tags renamed due to name conflict
 *
 * Revision 1.7  2005/02/02 07:55:26  pkrishnan
 * 2DO tags are replaced by Savant copy rights tag
 *
 * Revision 1.6  2005/01/30 07:03:38  pkrishnan
 * InvaliDriverException and SQLExceptions are removed
 *
 * Revision 1.5  2005/01/12 12:02:17  pkrishnan
 * InvalidDriverException has been added to the getConnection method
 *
 * Revision 1.4  2005/01/11 09:23:05  spalanisamy
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/11 07:20:16  pkrishnan
 * at the EOF Log information string has been added
 * 
 */
