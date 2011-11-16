/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.infra.dbtools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.exception.InvalidDriverException;


/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class DBConnection {
 
    private Connection dbConnection = null;
	private static Logger logger = Logger.getLogger(DBConnection.class);

	/*
	 * This method returns the Database connection
	 */
	public Connection getConnection()
	{
		logger.addAppender(LoggerOutput.getAppender());
		try
		{
			if(BuildConfig.USECONNECTIONPOOL)
				return getPoolConnection();
			return getPoollessConnection();
		}
		catch(InvalidDriverException ide)
		{
		    if(BuildConfig.DMODE)
		    ide.printStackTrace();
		    
			if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("Database Connection Error", ide);
			return null;
		}
	}

	/*
	 * This method is to establish connection(without pooling) with the database with the default Connection URL, username and password
	 */
	private Connection getPoollessConnection() throws InvalidDriverException
	{
		String jdbcDriver = "";
		String connectionUrl = "";

		try
		{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("prodacsenv.properties");
			Properties p = new Properties();
			p.load(is);
			is.close();
			jdbcDriver = p.getProperty("jdbcdriver");
			connectionUrl = p.getProperty("conurl");

			if(BuildConfig.DBASE == BuildConfig.ORACLE)
			{
//				if(BuildConfig.DMODE)
//					System.out.println("Connecting to ORACLE : jdbc driver :" + jdbcDriver + " connectionUrl : " + connectionUrl);
			}
			else if(BuildConfig.DBASE == BuildConfig.MYSQL)
			{
//				if(BuildConfig.DMODE)
//					System.out.println("Connecting to MYSQL : jdbc driver :" + jdbcDriver + " connectionUrl : " + connectionUrl);
			}
			Class.forName(jdbcDriver);
			dbConnection = DriverManager.getConnection(connectionUrl,p.getProperty("username"),p.getProperty("password"));
//			if(BuildConfig.DMODE)
//				System.out.println("Connection Established");
		}
		catch(ClassNotFoundException cnfe)
		{
			throw new InvalidDriverException("IDE01", jdbcDriver + " Driver not found ", cnfe);
		}
		catch(SQLException cnfe)
		{
			throw new InvalidDriverException("IDE01", "Invalid connection URL or Username or Password", cnfe);
		}
		catch(IOException ioe)
		{
			throw new InvalidDriverException("IDE99", "Error in datricsenv.properties file", ioe);
		}
		return dbConnection;
	}
	/*
	 * This method is to establish connection with the database using pooling
	 */
	private Connection getPoolConnection() throws InvalidDriverException
	{
		try
		{
			Context initctxt = new InitialContext();
			Context envctxt = (Context)initctxt.lookup("java:comp/env");
			DataSource dsrc = (DataSource)envctxt.lookup("jdbc/BPMDB");
			dbConnection = dsrc.getConnection();
		}
		catch(NamingException ne)
		{
			ne.printStackTrace();
			if(BuildConfig.DMODE)
				System.out.println("Connection pool failed");
			throw new InvalidDriverException("IDE03","Error in databse connection pool",ne);
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			if(BuildConfig.DMODE)
				System.out.println("Connection pool failed");
			throw new InvalidDriverException("IDE03","Error in databse connection pool",sqle);
		}
		return dbConnection;
	}

	//public abstract Connection getConnection(String connectionUrl, String userName, String password);
	//public abstract PreparedStatement getPreparedStatement(String unparsedQuery, Hashtable hashQueryVariables) throws SQLException;
	//public abstract void closePreparedStatement(PreparedStatement ps);
	//public abstract ResultSet executeQuery(PreparedStatement prep);
	public abstract void startTransaction();
	public abstract void endTransaction();
	public abstract void abortTransaction();
	public abstract void commitTransaction();
	public abstract void rollBackTransaction();
	public abstract void closeConnection();
//	public abstract boolean resetConnection();
	public abstract void closeResultSet(ResultSet rs);
	public abstract PreparedStatement executeStatement(String unparsedQuery, Hashtable hashQueryVariables) throws SQLException;
	public abstract PreparedStatement executeStatement(String unparsedQuery) throws SQLException;
	public abstract ResultSet executeRownumStatement(String unparsedQuery);
	public abstract int executeUpdateStatement(String unparsedQuery, Hashtable hashQueryVariables) throws SQLException;
	public abstract int executeUpdateStatement(String unparsedQuery) throws SQLException;
	public abstract CallableStatement callableStatement(String sql);
	public abstract CallableStatement callableStatement(String sql, Vector arguments);
	public abstract String buildDynamicQuery(Filter objFilter[],int sIndex,int eIndex,String sortBy,boolean sortOrder,String sQL_QUERY);
	public abstract int getRowCountWithFilters(Filter objFilter[],String tableName)throws SQLException;
	public abstract int getRowCountWithComplexFilters(Filter objFilter[],String query)throws SQLException;
	public abstract int getRowCountWithComplexFiltersWithHashtable(Filter objFilter[],String query,Hashtable ht) throws SQLException;
}

/***
$Log: DBConnection.java,v $
Revision 1.16  2005/07/23 08:51:58  kduraisamy
getRowcountWithComplexfilterwithhashtable() added.

Revision 1.15  2005/05/10 09:09:19  vkrishnamoorthy
printstacktrace() added.

Revision 1.14  2005/04/18 11:19:43  kduraisamy
executeStatement () Return type changed.

Revision 1.13  2005/03/05 11:28:06  kduraisamy
function excecutUpdateStatement overloaded.

Revision 1.12  2005/03/05 05:43:07  kduraisamy
property file prodacsenv.properties added.

Revision 1.11  2005/03/04 07:23:20  kduraisamy
initial commit.

Revision 1.10  2005/02/16 11:46:28  kduraisamy
signature modified for addNewJbGnrlName().

Revision 1.9  2005/01/29 10:53:02  kduraisamy
data base info changed.

Revision 1.8  2004/12/21 11:49:21  sduraisamy
"DateBetween" option in getRowCountWithComplexFilters() and other methods modified by replacing <= and >= with between function

Revision 1.7  2004/12/04 10:25:21  kduraisamy
unwanted field m_poolName removed

Revision 1.6  2004/11/19 19:02:06  kduraisamy
changed the property file from dbconnection.properties to prodacsenv.properties

Revision 1.5  2004/11/08 11:43:58  kduraisamy
util date to sql date code added

Revision 1.4  2004/11/06 07:43:14  sduraisamy
Log inclusion for Filter,DBConnection,DBNull,SQLMaster,DateUtil,Debug and LoggerOutput

***/