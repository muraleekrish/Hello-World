package com.savantit.prodacs.infra.dbtools.mysql;

/*
 * Created on Jan 4, 2005
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 */
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBNull;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;

//import com.mysql.jdbc.Clob;

/**
 * @author pkrishnan
 *
 */
public class MysqlDBConnection extends DBConnection
{
	private Connection dbConnection;
	private static Logger logger = Logger.getLogger(MysqlDBConnection.class);
	public MysqlDBConnection()
	{
		logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);		
		dbConnection = super.getConnection();
	}

	public Connection getConnection()
	{
	    return this.dbConnection;
	}

/*	public MysqlDBConnection(String connectionUrl,String userName,String password)
	{
		dbConnection = getConnection(connectionUrl,userName,password);
	}
*/
	/*
	 * This method is to establish connection with the database for the given Connection URL, username and password
	 */
/*	public Connection getConnection(String connectionUrl,String userName,String password)
	{
		try
		{
			Class.forName("com.mysql.jdbc.driver");
			dbConnection = DriverManager.getConnection(connectionUrl,userName,password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dbConnection;
	}
*/

    /** Starts a new transaction with commit set to false
    *
    * @exception SQLException if a database access error occurs
    */
	public void startTransaction()
	{
		try
		{
			if(dbConnection != null)
			{
			    dbConnection.setAutoCommit(false);
			    dbConnection.rollback();
			    dbConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In startTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

   /** Drops all changes made since the previous commit/rollback and
    * releases any database locks currently held by this Connection.
    *
    * @exception SQLException if a database access error occurs
    */
	public void abortTransaction()
	{
		try
		{
			dbConnection.rollback();
			this.endTransaction();
		}
		catch(SQLException sqle)
		{
			logger.error("SQLException - In abortTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

   /** Releases a Connection's database and JDBC resources immediately
    * instead of waiting for them to be automatically released.
    *
    * @exception SQLException if a database access error occurs
    */
	public void endTransaction()
	{
		try
		{
			dbConnection.close();
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In endTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

   /**
    *Makes all changes made since the previous commit/rollback
    * permanent and releases any database locks currently held by the Connection
    * @exception SQLException if database error occurs
    */
	public void commitTransaction()
	{
		try
		{
			dbConnection.commit();
			if(BuildConfig.DMODE)
				System.out.println("Commit Transaction");
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In commitTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

	/*
	 * This method will revert or roll back all the transactions that are done previously
	 */
	public void rollBackTransaction()
	{
		try
		{
			dbConnection.rollback();
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In rollBackTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

	/*
	 * This method will close the database connection
	 */
	public void closeConnection()
	{
		try
		{
			if(null !=  dbConnection && !dbConnection.isClosed())
			{
				dbConnection.close();
			}
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In StartTransaction()", sqle);
		}
	}

	
	
	/*
	 * This method will execute the unparsed SQL Query by mapping the values from the hash table
	 */
	public PreparedStatement executeStatement(String unparsedQuery, Hashtable hashQueryVariables) throws SQLException
	{
		if(BuildConfig.DBASE==BuildConfig.MYSQL)
		{
			Statement setSt = dbConnection.createStatement();
			setSt.execute("set @rownum:=0");
			setSt.close();
		}
		PreparedStatement ps = getPreparedStatement(unparsedQuery,hashQueryVariables);
	    return (ps);
	}
	/*
	 * This method will execute when hash table is not involving
	 * 	 */
	public PreparedStatement executeStatement(String unparsedQuery) throws SQLException
	{
	    PreparedStatement ps = getPreparedStatement(unparsedQuery);
	    return (ps);
	}

	/*
	 * This method will execute the unparsed SQL Query(insert,update,delete) by mapping the values from the hash table
	 */
	public int executeUpdateStatement(String unparsedQuery, Hashtable hashQueryVariables) throws SQLException
	{
		return executeUpdateQuery(getPreparedStatement(unparsedQuery, hashQueryVariables));
	}

	/*
	* This method will be using when hashtable is not involving..
	*/

	public int executeUpdateStatement(String unparsedQuery) throws SQLException
	{
		return executeUpdateQuery(getPreparedStatement(unparsedQuery));
	}

	/*
	 * This method will execute the unparsed SQL Query(Select with rownum) by mapping the values from the hash table
	 */
	public ResultSet executeRownumStatement(String parsedFilterQuery)
	{
		return executeRownumQuery(parsedFilterQuery);
	}

	/*
	 * This method will return the prepared statement of the given Unparsed query and the database values in the Hashtable
	 */
	private PreparedStatement getPreparedStatement(String unparsedQuery, Hashtable hashQueryVariables)
	{
		PreparedStatement psParsedQuery = null;
		Vector vecQueryVariables = new Vector();
		StringTokenizer st=new StringTokenizer(unparsedQuery,"#");
		int count=0;
		String str1[]=new String[st.countTokens()];

		while(st.hasMoreTokens())
		{
			count+=1;
			str1[count-1]=st.nextToken();
		}
		StringBuffer input=new StringBuffer(unparsedQuery);
		for(int i=1;i<str1.length;i+=2)
		{
			String label="#"+str1[i]+"#";
			int stPos=unparsedQuery.indexOf("#");
			int endPos=unparsedQuery.indexOf("#")+label.length();
			input.replace(stPos,endPos,"?");
			unparsedQuery=new String(input);
			stPos=0;
			endPos=0;
			label="";
			vecQueryVariables.addElement(hashQueryVariables.get(str1[i]));
		}
		if(BuildConfig.DMODE)
		{
			System.out.println("parsedQuery : " + unparsedQuery);
			System.out.println("vecVariables : " + vecQueryVariables);
		}
		psParsedQuery = prepareStatement(unparsedQuery, vecQueryVariables);
		return psParsedQuery;
	}

	private PreparedStatement getPreparedStatement(String unparsedQuery) throws SQLException
	{
		PreparedStatement ps = null;
		if(dbConnection != null)
		ps = dbConnection.prepareStatement(unparsedQuery);
		return ps;
	}



	/*
	 * This method will closes the prepared statement (This method is never used)
	 */
/*	private void closePreparedStatement(PreparedStatement ps)
	{
		try
		{
			if( ps  != null)
			ps.close();
			//System.out.println("I am going to close the ps");
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In closeResultSet()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}
*/
	/*
	 * This method will closes the Result set
	 */
	public void closeResultSet(ResultSet rs)
	{
		try
		{
			if( rs  != null  )
				rs.close();
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In closeResultSet()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
		}
	}

	/*
	 * This method will rereturn the Callable Statement for the given SQL Query and vector arguments
	 */
	public CallableStatement callableStatement(String sql)
	{
		try
		{
			CallableStatement cs;
		       // NullObject nullObject = null;
				cs = dbConnection.prepareCall(sql);
			return (cs);
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In callableStatement()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			return null;
		}
	}

	/*
	 * This method will rereturn the Callable Statement for the given SQL Query and vector arguments
	 */
	public CallableStatement callableStatement(String sql, Vector arguments)
	{
		try
		{
			CallableStatement cs;
	       // NullObject nullObject = null;
			cs = dbConnection.prepareCall(sql);

			for (int i = 0, x = 1; i < arguments.size(); i++, x++)
			{
				Object o = arguments.elementAt(i);
	            if (o instanceof String)
	            {

	                // The database cannot handle empty strings, so we convert
	                // them to NULL.
	                if ("".equals(o))
	                {
	                    o = " ";
	                }
	                cs.setString(x,(String)o);
	            }
	            else if (o instanceof java.sql.Date)
				{
					//	recast Java date to SQL date to avoid Exception
					//	code broken up for readability/maintainability
					//java.sql.Date utilDate = (java.sql.Date) o;
					//java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
					//o = sqlDate;
					cs.setDate(x,(java.sql.Date)o);
				}
				else if (o instanceof Integer)
				{
					cs.setInt(x,((Integer)o).intValue());
				}
				else if(o instanceof Double)
					cs.setDouble(x,((Double)o).doubleValue());

				else if(o instanceof  Float)
					cs.setFloat(x,((Float)o).floatValue());

				else if(o instanceof  Long)
					cs.setLong(x,((Long)o).longValue());

				else if(o instanceof  Short)
					cs.setShort(x,((Short)o).shortValue());

				else if(o instanceof  oracle.sql.CLOB)
					cs.setClob(x, (oracle.sql.CLOB)o);

				else if(o instanceof DBNull)
					cs.setString(x, null);

				//else if(o instanceof CallOutInt)
				//	cs.registerOutParameter(x, java.sql.Types.INTEGER);

	/*            else if (o instanceof NullObject)
	  	        {
	  	            System.out.println("Found a null object");
	                nullObject = (NullObject) o;
	                NullFlag = true;
	            }
	            if (true == NullFlag)
	            {
			 	 	// System.out.println ("param num " + x + " " + Types.FLOAT);
	                cs.setNull(x,nullObject.getType());
	            } */
				else{}
					//	ps.setObject(x, o);
			}
			return (cs);
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In callableStatement()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			return null;
		}
	}

	/*
	 * This method executes the given Prepared statement and returns the ResultSet(only select statement)
	 
	private ResultSet executeQuery(PreparedStatement prep)
	{
		try
		{
			return prep.executeQuery();
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In executeQuery()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			return null;
		}
	}

*/	/*
	 * This method executes the given Prepared statement which uses the rownum and returns the ResultSet(only select statement)
	 */
	private ResultSet executeRownumQuery(String filterQuery)
	{
		try
		{
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
			    Statement setSt = null;
			    if(dbConnection != null)
			    {
			        setSt = dbConnection.createStatement();
			        setSt.execute("set @rownum:=0");
			        setSt.close();
			    }    
			}
			Statement filterSt = null;
			if(dbConnection != null)
			{
			    filterSt = dbConnection.createStatement();
			    return filterSt.executeQuery(filterQuery);
			}
			else
			{
			    return null;
			}
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In StartTransaction()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			return null;
		}
	}

	/*
	 * This method executes the given Prepared statement and returns the integer value and if it returns -ve value then some exceptions has occured(other than the select statement)
	 */
	private int executeUpdateQuery(PreparedStatement prep) throws SQLException
	{
		if(prep != null)	
	    return prep.executeUpdate();
		else
		return 0;    
	}

	/*
	 * This method builds the Filter query with the given filters, for the given table name
	 */
	public String buildDynamicQuery(Filter objFilter[],int sIndex,int eIndex,String sortBy,boolean sortOrder,String sQL_QUERY)
	{
		if(BuildConfig.DMODE)
	    System.out.println("Filter Count : " + objFilter.length);
		//final Query will store in filterQuery
		String filterQuery = "";
		//Get the select query from SQLMaster
		String SQL_QUERY = sQL_QUERY;
		if(BuildConfig.DMODE)
			System.out.println(SQL_QUERY);

        //Append the sql query to filteQuery string
		filterQuery = "select * from ( " + SQL_QUERY;

		if(objFilter.length>0)
				filterQuery = filterQuery + "dummyalias1 where ";

		//This following loop will be Construct filter query
		for(int i=0;i < objFilter.length; i++)
		{
			if(i!=0)
				filterQuery = filterQuery + " and " ;

			//Check if filter have any special function
			if((objFilter[i].getSpecialFunction() == null) || (objFilter[i].getSpecialFunction().length() == 0))
			{
				//If filter haven't any special function
				filterQuery = filterQuery + "UPPER(" + objFilter[i].getFieldName().trim() + ") like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";

			}
			//IF Filter have any special function
			else
			{
				String strTemp = objFilter[i].getSpecialFunction().toUpperCase().trim();
				String strLike = "";

				//Check filter type special function
				if(strTemp.equalsIgnoreCase("Starts With"))
				{
					strLike = " Like '" +objFilter[i].getFieldValue().toUpperCase().trim()+ "%'";
				}
				else if(strTemp.equalsIgnoreCase("Ends With"))
				{
					strLike = " Like '%" +objFilter[i].getFieldValue().toUpperCase().trim()+"'";
				}
				else if(strTemp.equalsIgnoreCase("Exactly"))
				{
					strLike = " Like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";
				}
				else if(strTemp.equalsIgnoreCase("Anywhere"))
				{
					strLike = " Like '%"+objFilter[i].getFieldValue().toUpperCase().trim()+"%'";
				}
				else if (strTemp.equalsIgnoreCase("Between"))
				{
					if(BuildConfig.DMODE)
						System.out.println("Between filter value : "+objFilter[i].getFieldValue());
					StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().toUpperCase().trim(),"$");
					if (st.hasMoreTokens())
						strLike = " >= "+st.nextToken().toUpperCase().trim();
					if (st.hasMoreTokens())
						strLike = strLike +" and upper("+objFilter[i].getFieldName().trim()+") <= "+st.nextToken().toUpperCase().trim();
				}
				else if (strTemp.equalsIgnoreCase("DateBetween"))
				{
					if(BuildConfig.DMODE)
						System.out.println("Between filter value : "+objFilter[i].getFieldValue());
					StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().trim(),"$");
					if (st.hasMoreTokens())
							strLike = " >= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
					if (st.hasMoreTokens())
							strLike = strLike +" and DATE("+objFilter[i].getFieldName().trim()+") <= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
				}
				else if (strTemp.equalsIgnoreCase("DateLessthan"))
				{
						strLike = " < DATE('" + objFilter[i].getFieldValue().trim() + "') ";
				}
				else if (strTemp.equalsIgnoreCase("DateGreaterthan"))
				{
						strLike = " > DATE('" + objFilter[i].getFieldValue().trim() + "') ";
				}
				else if (strTemp.equalsIgnoreCase("DateEqual"))
				{
						strLike = " = DATE('" + objFilter[i].getFieldValue().trim() + "') ";
				}
				else if(strTemp.equalsIgnoreCase("<"))
				{
					strLike = " < " + objFilter[i].getFieldValue().trim();
				}
				else if(strTemp.equalsIgnoreCase(">"))
				{
					strLike = " > " + objFilter[i].getFieldValue().trim();
				}
				else if(strTemp.equalsIgnoreCase("="))
				{
					strLike = " = " + objFilter[i].getFieldValue().trim();
				}

				if(strTemp.equalsIgnoreCase("<") || strTemp.equalsIgnoreCase(">") || strTemp.equalsIgnoreCase("="))
				{
					//Add the filter  to the query
					filterQuery = filterQuery + "UPPER("+ objFilter[i].getFieldName().trim() + ") " + strLike;
				}
				else if(strTemp.equalsIgnoreCase("DateLessthan") || strTemp.equalsIgnoreCase("DateGreaterthan") || strTemp.equalsIgnoreCase("DateEqual") || strTemp.equalsIgnoreCase("DateBetween"))
				{
					//Add the filter  to the query
						filterQuery = filterQuery +" DATE("+ objFilter[i].getFieldName().trim() + ") " + strLike;
				}
				else
				{
					//Add the filter  to the query
					filterQuery = filterQuery +" UPPER(TRIM("+ objFilter[i].getFieldName().trim() + ")) " + strLike;
				}
			}
		}//end of for loop

		//Checking the sorting order
		String order = (sortOrder)? " ASC":" DESC";

		//Append the order by and order to the query
		filterQuery = filterQuery + " ORDER BY UPPER(" + sortBy +") " + order + " )dummyalias2 " ;
		//Append the rows start and end index to the query
		filterQuery = filterQuery + ")dummyalias3 where ";

		filterQuery = filterQuery +  " r >= " + sIndex + " and r < " + eIndex;

		if(BuildConfig.DMODE)
			System.out.println(filterQuery);

		//Return Final Query
		return filterQuery;
	}

	/*
	 * This method will return the number of records in the table for the given filters
	 */
	public int getRowCountWithFilters(Filter objFilter[],String tableName)throws SQLException
	{
		int numOfRecords=0;
		try
		{
			if(BuildConfig.DMODE)
				System.out.println("Filter in rowCount : " + objFilter.length);
			//Construct basic query
			String Query = "select count(*) from " + tableName;

			//Check if filter have
			if(objFilter.length > 0)
				Query = Query + " where ";

			//Loop with all filters
			for(int i=0;i<objFilter.length;i++)
			{
				//If filter haven't any special function
				if((objFilter[i].getSpecialFunction() == null) || (objFilter[i].getSpecialFunction().length() == 0))
				{
					Query = Query + " UPPER(" + objFilter[i].getFieldName().trim() + ") like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";
				}
				//If filter have any special function
				else
				{
					String strTemp = objFilter[i].getSpecialFunction().toUpperCase().trim();
					String strLike = "";

					if(strTemp.equalsIgnoreCase("Starts With"))
					{
						strLike = " Like '" +objFilter[i].getFieldValue().toUpperCase().trim()+ "%'";
					}
					else if(strTemp.equalsIgnoreCase("Ends With"))
					{
						strLike = " Like '%" +objFilter[i].getFieldValue().toUpperCase().trim()+"'";
					}
					else if(strTemp.equalsIgnoreCase("Exactly"))
					{
						strLike = " Like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";
					}
					else if(strTemp.equalsIgnoreCase("Anywhere"))
					{
						strLike = " Like '%"+objFilter[i].getFieldValue().toUpperCase().trim()+"%'";
					}
					else if (strTemp.equalsIgnoreCase("Between"))
					{
						if(BuildConfig.DMODE)
					    System.out.println("Between filter value : "+objFilter[i].getFieldValue());
						StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().trim(),"$");
						if (st.hasMoreTokens())
							strLike = " >= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
						if (st.hasMoreTokens())
							strLike = strLike +" and UPPER("+objFilter[i].getFieldName().trim()+") <= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
					}
					else if (strTemp.equalsIgnoreCase("DateBetween"))
					{
					    if(BuildConfig.DMODE)
					    System.out.println("Between filter value : "+objFilter[i].getFieldValue());
						StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().trim(),"$");
						if (st.hasMoreTokens())
								strLike = " >= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
						if (st.hasMoreTokens())
								strLike = strLike +" and DATE("+objFilter[i].getFieldName().trim()+") <= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
					}
					else if (strTemp.equalsIgnoreCase("DateLessthan"))
					{
							strLike = " < DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if (strTemp.equalsIgnoreCase("DateGreaterthan"))
					{
							strLike = " > DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if (strTemp.equalsIgnoreCase("DateEqual"))
					{
							strLike = " = DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if(strTemp.equalsIgnoreCase("<"))
					{
						strLike = " < " + objFilter[i].getFieldValue().trim();
					}
					else if(strTemp.equalsIgnoreCase(">"))
					{
						strLike = " > " + objFilter[i].getFieldValue().trim();
					}
					else if(strTemp.equalsIgnoreCase("="))
					{
						strLike = " = " + objFilter[i].getFieldValue().trim();
					}

					if(strTemp.equalsIgnoreCase("<") || strTemp.equalsIgnoreCase(">") || strTemp.equalsIgnoreCase("="))
					{
						Query = Query + " UPPER("+ objFilter[i].getFieldName().trim() +") " + strLike;
					}
					else if(strTemp.equalsIgnoreCase("DateLessthan") || strTemp.equalsIgnoreCase("DateGreaterthan") || strTemp.equalsIgnoreCase("DateEqual") || strTemp.equalsIgnoreCase("DateBetween"))
					{
						//Add the filter  to the query
							Query = Query +" DATE("+ objFilter[i].getFieldName().trim() + ") " + strLike;
					}
					else
					{
						Query = Query + " UPPER(TRIM("+ objFilter[i].getFieldName().trim() +")) " + strLike;
					}
					//Query = Query + " upper(TRIM("+ objFilter[i].getFieldName().trim() +")) " + strLike;

				}
				if(i < objFilter.length - 1)
				{
					Query = Query +  " and ";
				}
			}
			//Displaying the query
			if(BuildConfig.DMODE)
				System.out.println(Query);

			//Creating the statement object
			Statement st = dbConnection.createStatement();
			//Executing the resultSet
			ResultSet rs = st.executeQuery(Query);
			rs.next();
			//Get the number of Records
			numOfRecords = rs.getInt(1);
			//Closing the result set
			rs.close();
		}
		catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("SQLException - In closeResultSet()", sqle);
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			throw sqle;
		}
		//Return the total records
		return numOfRecords;
	}

	/*
	 * This method will return the number of records in the table for the given complex filters
	 */
	public int getRowCountWithComplexFilters(Filter objFilter[],String query) throws SQLException
	{
		if(BuildConfig.DMODE)
		System.out.println("Filter Count : " + objFilter.length);
		//final Query will store in filterQuery
		String filterQuery = "";
		int numOfRecords = 0;
		try
		{
			//Get the select query from SQLMaster
			String SQL_QUERY = query;
			if(BuildConfig.DMODE)
				System.out.println(SQL_QUERY);

	        //Append the sql query to filteQuery string
			filterQuery = "select count(*) from ( " + SQL_QUERY;

			if(objFilter.length>0)
					filterQuery = filterQuery + "dummyalias1 where ";

			//This following loop will be Construct filter query
			for(int i=0;i < objFilter.length; i++)
			{
				if(i!=0)
					filterQuery = filterQuery + " and " ;

				//Check if filter have any special function
				if((objFilter[i].getSpecialFunction() == null) || (objFilter[i].getSpecialFunction().length() == 0))
				{
					//If filter haven't any special function
					filterQuery = filterQuery + "upper(" + objFilter[i].getFieldName().trim() + ") like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";

				}
				//IF Filter have any special function
				else
				{
					String strTemp = objFilter[i].getSpecialFunction().toUpperCase().trim();
					String strLike = "";

					//Check filter type special function
					if(strTemp.equalsIgnoreCase("Starts With"))
					{
						strLike = " Like '" +objFilter[i].getFieldValue().toUpperCase().trim()+ "%'";
					}
					else if(strTemp.equalsIgnoreCase("Ends With"))
					{
						strLike = " Like '%" +objFilter[i].getFieldValue().toUpperCase().trim()+"'";
					}
					else if(strTemp.equalsIgnoreCase("Exactly"))
					{
						strLike = " Like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";
					}
					else if(strTemp.equalsIgnoreCase("Anywhere"))
					{
						strLike = " Like '%"+objFilter[i].getFieldValue().toUpperCase().trim()+"%'";
					}
					else if (strTemp.equalsIgnoreCase("Between"))
					{
						if(BuildConfig.DMODE)
							System.out.println("Between filter value : "+objFilter[i].getFieldValue());
						StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().toUpperCase().trim(),"$");
						if (st.hasMoreTokens())
							strLike = " >= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
						if (st.hasMoreTokens())
							strLike = strLike +" and upper("+objFilter[i].getFieldName().trim()+") <= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
					}
					else if (strTemp.equalsIgnoreCase("DateBetween"))
					{
						if(BuildConfig.DMODE)
							System.out.println("Between filter value : "+objFilter[i].getFieldValue());
						StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().trim(),"$");
						if (st.hasMoreTokens())
							strLike = " >= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
						if (st.hasMoreTokens())
								strLike = strLike +" and DATE("+objFilter[i].getFieldName().trim()+") <= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
					}
					else if (strTemp.equalsIgnoreCase("DateLessthan"))
					{
							strLike = " < DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if (strTemp.equalsIgnoreCase("DateGreaterthan"))
					{
							strLike = " > DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if (strTemp.equalsIgnoreCase("DateEqual"))
					{
							strLike = " = DATE('" + objFilter[i].getFieldValue().trim() + "') ";
					}
					else if(strTemp.equalsIgnoreCase("<"))
					{
						strLike = " < " + objFilter[i].getFieldValue().trim();
					}
					else if(strTemp.equalsIgnoreCase(">"))
					{
						strLike = " > " + objFilter[i].getFieldValue().trim();
					}
					else if(strTemp.equalsIgnoreCase("="))
					{
						strLike = " = " + objFilter[i].getFieldValue().trim();
					}

					if(strTemp.equalsIgnoreCase("<") || strTemp.equalsIgnoreCase(">") || strTemp.equalsIgnoreCase("="))
					{
					//Add the filter  to the query
					filterQuery = filterQuery +" UPPER("+ objFilter[i].getFieldName().trim() + ") " + strLike;
					}
					else if(strTemp.equalsIgnoreCase("DateLessthan") || strTemp.equalsIgnoreCase("DateGreaterthan") || strTemp.equalsIgnoreCase("DateEqual") || strTemp.equalsIgnoreCase("DateBetween"))
					{
						//Add the filter  to the query
							filterQuery = filterQuery +" DATE("+ objFilter[i].getFieldName().trim() + ") " + strLike;
					}
					else
					{
						//Add the filter  to the query
						filterQuery = filterQuery +" upper(TRIM("+ objFilter[i].getFieldName().trim() + ")) " + strLike;
					}

				}
			}
				filterQuery = filterQuery + " )dummyalias2 )dummyalias3 " ;
				//Append the rows start and end index to the query

			if(BuildConfig.DMODE)
				System.out.println("filter query : " + filterQuery);
			ResultSet rs = executeRownumStatement(filterQuery);
			if(rs != null)
			if(rs.next())
			{
			    //Get the number of Records
			    numOfRecords = rs.getInt(1);
			    //Closing the result set
			    rs.close();
			}   
		}catch(SQLException sqle)
		{
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("Error in buildDynamicQuery()",sqle);
			if(BuildConfig.DMODE)
				sqle.printStackTrace();
			throw sqle;
		}
		return numOfRecords;
	}
	public int getRowCountWithComplexFiltersWithHashtable(Filter objFilter[],String query,Hashtable ht) throws SQLException
	{
		
			if(BuildConfig.DMODE)
			System.out.println("Filter Count : " + objFilter.length);
			//final Query will store in filterQuery
			String filterQuery = "";
			int numOfRecords = 0;
			try
			{
				//Get the select query from SQLMaster
				String SQL_QUERY = query;
				if(BuildConfig.DMODE)
					System.out.println(SQL_QUERY);

		        //Append the sql query to filteQuery string
				filterQuery = "select count(*) from ( " + SQL_QUERY;

				if(objFilter.length>0)
						filterQuery = filterQuery + "dummyalias1 where ";

				//This following loop will be Construct filter query
				for(int i=0;i < objFilter.length; i++)
				{
					if(i!=0)
						filterQuery = filterQuery + " and " ;

					//Check if filter have any special function
					if((objFilter[i].getSpecialFunction() == null) || (objFilter[i].getSpecialFunction().length() == 0))
					{
						//If filter haven't any special function
						filterQuery = filterQuery + "upper(" + objFilter[i].getFieldName().trim() + ") like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";

					}
					//IF Filter have any special function
					else
					{
						String strTemp = objFilter[i].getSpecialFunction().toUpperCase().trim();
						String strLike = "";

						//Check filter type special function
						if(strTemp.equalsIgnoreCase("Starts With"))
						{
							strLike = " Like '" +objFilter[i].getFieldValue().toUpperCase().trim()+ "%'";
						}
						else if(strTemp.equalsIgnoreCase("Ends With"))
						{
							strLike = " Like '%" +objFilter[i].getFieldValue().toUpperCase().trim()+"'";
						}
						else if(strTemp.equalsIgnoreCase("Exactly"))
						{
							strLike = " Like '"+objFilter[i].getFieldValue().toUpperCase().trim()+"'";
						}
						else if(strTemp.equalsIgnoreCase("Anywhere"))
						{
							strLike = " Like '%"+objFilter[i].getFieldValue().toUpperCase().trim()+"%'";
						}
						else if (strTemp.equalsIgnoreCase("Between"))
						{
							if(BuildConfig.DMODE)
								System.out.println("Between filter value : "+objFilter[i].getFieldValue());
							StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().toUpperCase().trim(),"$");
							if (st.hasMoreTokens())
								strLike = " >= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
							if (st.hasMoreTokens())
								strLike = strLike +" and upper("+objFilter[i].getFieldName().trim()+") <= "+convertToMysqlDate(st.nextToken()).toUpperCase().trim();
						}
						else if (strTemp.equalsIgnoreCase("DateBetween"))
						{
							if(BuildConfig.DMODE)
								System.out.println("Between filter value : "+objFilter[i].getFieldValue());
							StringTokenizer st = new StringTokenizer(objFilter[i].getFieldValue().trim(),"$");
							if (st.hasMoreTokens())
								strLike = " >= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
							if (st.hasMoreTokens())
									strLike = strLike +" and DATE("+objFilter[i].getFieldName().trim()+") <= '"+convertToMysqlDate(st.nextToken()).toUpperCase().trim()+"'";
						}
						else if (strTemp.equalsIgnoreCase("DateLessthan"))
						{
								strLike = " < DATE('" + objFilter[i].getFieldValue().trim() + "') ";
						}
						else if (strTemp.equalsIgnoreCase("DateGreaterthan"))
						{
								strLike = " > DATE('" + objFilter[i].getFieldValue().trim() + "') ";
						}
						else if (strTemp.equalsIgnoreCase("DateEqual"))
						{
								strLike = " = DATE('" + objFilter[i].getFieldValue().trim() + "') ";
						}
						else if(strTemp.equalsIgnoreCase("<"))
						{
							strLike = " < " + objFilter[i].getFieldValue().trim();
						}
						else if(strTemp.equalsIgnoreCase(">"))
						{
							strLike = " > " + objFilter[i].getFieldValue().trim();
						}
						else if(strTemp.equalsIgnoreCase("="))
						{
							strLike = " = " + objFilter[i].getFieldValue().trim();
						}

						if(strTemp.equalsIgnoreCase("<") || strTemp.equalsIgnoreCase(">") || strTemp.equalsIgnoreCase("="))
						{
						//Add the filter  to the query
						filterQuery = filterQuery +" UPPER("+ objFilter[i].getFieldName().trim() + ") " + strLike;
						}
						else if(strTemp.equalsIgnoreCase("DateLessthan") || strTemp.equalsIgnoreCase("DateGreaterthan") || strTemp.equalsIgnoreCase("DateEqual") || strTemp.equalsIgnoreCase("DateBetween"))
						{
							//Add the filter  to the query
								filterQuery = filterQuery +" DATE("+ objFilter[i].getFieldName().trim() + ") " + strLike;
						}
						else
						{
							//Add the filter  to the query
							filterQuery = filterQuery +" upper(TRIM("+ objFilter[i].getFieldName().trim() + ")) " + strLike;
						}

					}
				}
					filterQuery = filterQuery + " )dummyalias2 )dummyalias3 " ;
					//Append the rows start and end index to the query

					if(BuildConfig.DMODE)
						System.out.println("filter query : " + filterQuery);
					PreparedStatement ps = executeStatement(filterQuery,ht);
					
					ResultSet rs = ps.executeQuery();
					if(rs.next())
					//Get the number of Records
					numOfRecords = rs.getInt(1);
					//Closing the result set
					rs.close();
				   
			}catch(SQLException sqle)
			{
			    if(BuildConfig.USE_ERROR_LOGGER)
			        logger.error("Error in buildDynamicQuery()",sqle);
				if(BuildConfig.DMODE)
					sqle.printStackTrace();
				throw sqle;
			}
			return numOfRecords;
		
	}

	/*
	 * This method will return the Prepared staement for the given parsed query vector
	 */
    private PreparedStatement prepareStatement(String sql, Vector arguments)
    {
        try
        {
            PreparedStatement ps = null;
            // NullObject nullObject = null;
            if(dbConnection != null)
                ps = dbConnection.prepareStatement(sql);
            
            for (int i = 0, x = 1; i < arguments.size(); i++, x++)
            {
                Object o = arguments.elementAt(i);
                if(o instanceof  Clob)
                {
                    ps.setClob(x, (Clob)o);
                }
                else if(o instanceof  Clob)
                {
                    ps.setClob(x, (Clob)o);
                }
                else if (o instanceof String)
                {
                    
                    // The database cannot handle empty strings, so we convert
                    // them to NULL.
                    if ("".equals(o))
                    {
                        o = " ";
                    }
                    ps.setString(x,(String)o);
                }
                else if(o instanceof java.util.Date)
                {
                    ps.setObject(x,o);
                }
                else if (o instanceof java.sql.Date)
                {
                    ps.setDate(x,(java.sql.Date)o);
                }
                else if (o instanceof Integer)
                {
                    ps.setInt(x,((Integer)o).intValue());
                }
                else if(o instanceof Double)
                    ps.setDouble(x,((Double)o).doubleValue());
                
                else if(o instanceof  Float)
                    ps.setFloat(x,((Float)o).floatValue());
                
                else if(o instanceof  Long)
                    ps.setLong(x,((Long)o).longValue());
                
                else if(o instanceof  Short)
                    ps.setShort(x,((Short)o).shortValue());
                
                else if(o instanceof DBNull)
                    ps.setString(x, null);
                
                else if(o instanceof byte[])
                    ps.setBytes(x, (byte[])o);
                else{}
            }
            return ps;
        }
        catch(SQLException sqle)
        {
            logger.error("SQLException - In prepareStatement()", sqle);
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            return null;
        }
    }

    private String convertToMysqlDate(String tempDate)
    {
    	String year="";
    	String month="";
    	String day="";
    	StringTokenizer st = new StringTokenizer(tempDate,"/-");

    	if(st.hasMoreTokens())
    	{
    		day=st.nextToken();
    	}
    	if(st.hasMoreTokens())
    	{
    		month=st.nextToken();
    	}
    	if(st.hasMoreTokens())
    	{
    		year=st.nextToken();
    	}
		if(month.equalsIgnoreCase("jan"))
		{
			month = "01";
		}
		else if(month.equalsIgnoreCase("feb"))
		{
			month = "02";
		}
		else if(month.equalsIgnoreCase("mar"))
		{
			month = "03";
		}
		else if(month.equalsIgnoreCase("apr"))
		{
			month = "04";
		}
		else if(month.equalsIgnoreCase("may"))
		{
			month = "05";
		}
		else if(month.equalsIgnoreCase("jun"))
		{
			month = "06";
		}
		else if(month.equalsIgnoreCase("jul"))
		{
			month = "07";
		}
		else if(month.equalsIgnoreCase("aug"))
		{
			month = "08";
		}
		else if(month.equalsIgnoreCase("sep"))
		{
			month = "09";
		}
		else if(month.equalsIgnoreCase("oct"))
		{
			month = "10";
		}
		else if(month.equalsIgnoreCase("nov"))
		{
			month = "11";
		}
		else if(month.equalsIgnoreCase("dec"))
		{
			month = "12";
		}

    	return year+"-"+month+"-"+day;
    }

/*	public static void main(String ar[])
	{
		String insertQuery = "insert into EMP (ENO,ENAME,DESIG,JDATE) values (#ENO#,#ENAME#,#DESIG#,#JDATE#)";
		Hashtable ht = new Hashtable();
		GregorianCalendar gc = new GregorianCalendar(2004,11,21);
		java.util.Date date = gc.getTime();
		Date sqldate = new Date(date.getTime());
		System.out.println("DATE : " + date.toString());
		ht.put("ENO","11");
		ht.put("ENAME","Anand");
		ht.put("DESIG","Designer");
		ht.put("JDATE",sqldate);
		//ht.put("JDATE","2004/12/12");
		String oracleFilterQuery = "select rownum r, eno, ename, desig,jdate from(select distinct(eno)," +
				"ename,desig,jdate from(select employee.eno,employee.ename,employee.desig,employee.jdate,empdept.deptname FROM " +
				"EMP EMPLOYEE right join EMP_DEPARTMENT EMPDEPT on employee.eno = empdept.eno left join " +
				"EMP_RESOURCE EMPRES ON EMPDEPT.EMPRESOURCEID = EMPRES.EMPRESOURCEID)";

		String mysqlFilterQuery = "select @rownum:=@rownum+1 r, eno, ename, desig,jdate from(select distinct(eno)," +
				"ename,desig,jdate from(select employee.eno,employee.ename,employee.desig,employee.jdate,empdept.deptname FROM " +
				"EMP EMPLOYEE right join EMP_DEPARTMENT EMPDEPT on employee.eno = empdept.eno left join " +
				"EMP_RESOURCE EMPRES ON EMPDEPT.EMPRESOURCEID = EMPRES.EMPRESOURCEID)";
		try
		{
			DBConnection db = DBConnectionFactory.getConnection();
			Filter[] fil = new Filter[1];
			fil[0]= new Filter();
			fil[0].setFieldName("JDATE");
			fil[0].setFieldValue("2004-1-1$2005-1-21");
			fil[0].setSpecialFunction("DateBetween");
			//System.out.println(db.executeUpdateStatement(insertQuery,ht));
			//String buildQuery = db.buildDynamicQuery(fil,0,15,"ENAME",true,mysqlFilterQuery);
			//System.out.println(db.getRowCountWithComplexFilters(fil,oracleFilterQuery));
			System.out.println(db.getRowCountWithComplexFilters(fil,mysqlFilterQuery));
			//System.out.println("BuildQuery : " + buildQuery);
			//db.getRowCountWithComplexFilters(fil);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
*/
    
}


/***
 * $Log: MysqlDBConnection.java,v $
 * Revision 1.24  2006/01/18 13:26:53  kduraisamy
 * change made for blob.
 *
 * Revision 1.23  2006/01/05 13:22:04  kduraisamy
 * ps.setObj()  added for ps.setDate().
 *
 * Revision 1.22  2005/10/19 09:43:07  vkrishnamoorthy
 * UPPER(dateField) changed to DATE(dateField) in DateBetween method of filter.
 *
 * Revision 1.21  2005/10/19 06:16:44  kduraisamy
 * between special function error corrected.
 *
 * Revision 1.20  2005/07/25 11:06:45  kduraisamy
 * getRowcountwithcoplexfilterswithhashtable() changed for mysql.
 *
 * Revision 1.19  2005/07/23 08:52:27  kduraisamy
 * getRowcountWithComplexfilterwithhashtable() added.
 *
 * Revision 1.18  2005/07/12 11:48:37  kduraisamy
 * imports organized
 *
 * Revision 1.17  2005/06/30 05:44:55  kduraisamy
 * dbconnection != null condition checking added.
 *
 * Revision 1.16  2005/06/15 13:00:44  kduraisamy
 * executeQuery() method block comment added.
 *
 * Revision 1.15  2005/06/15 12:56:59  kduraisamy
 * r <= is changed to r < .
 *
 * Revision 1.14  2005/05/14 05:00:36  kduraisamy
 * con != null added.
 *
 * Revision 1.13  2005/05/13 08:57:02  kduraisamy
 * con != null added.
 *
 * Revision 1.12  2005/05/13 08:54:18  kduraisamy
 * con != null added.
 *
 * Revision 1.11  2005/04/27 06:11:43  kduraisamy
 * Level added.
 *
 * Revision 1.10  2005/04/26 05:40:04  kduraisamy
 * Error in DateBetween Corrected.
 *
 * Revision 1.9  2005/04/22 07:36:24  smurugesan
 * rownum reset for mysql added in excecuteStatement().
 *
 * Revision 1.8  2005/04/18 12:31:11  kduraisamy
 * executeStatement() return type changed.
 *
 * Revision 1.7  2005/04/07 11:34:12  kduraisamy
 * if(BuildConfig.DMODE) added before System.out.println.
 *
 * Revision 1.6  2005/03/21 10:28:48  smurugesan
 * build dynamic query modified.
 *
 * Revision 1.5  2005/03/17 10:40:29  smurugesan
 * throws added in excecuteUpdateQuery().
 *
 * Revision 1.4  2005/03/10 07:56:54  kduraisamy
 * convert To MysqlDate() method added.
 *
 * Revision 1.3  2005/03/10 07:21:54  klakshmanan
 * if(BUILDCONFIG.DBASE = BUILDCONFIG.MYSQL )
 * unwanted checking removed.
 *
 * Revision 1.2  2005/03/09 06:53:47  kduraisamy
 * getConnection() overriding added.
 *
 * Revision 1.1  2005/03/07 14:17:40  kduraisamy
 * initial commit.
 *
 * Revision 1.2  2005/03/05 11:28:06  kduraisamy
 * function excecutUpdateStatement overloaded.
 *
 * Revision 1.1  2005/03/04 07:23:20  kduraisamy
 * initial commit.
 *
 * Revision 1.28  2005/02/14 05:40:35  pkrishnan
 * unnessary codes has been removed
 *
 * Revision 1.27  2005/02/05 05:16:52  pkrishnan
 * comments has been redued
 *
 * Revision 1.26  2005/02/02 09:44:44  dsomasundaram
 * 2DO Tags renamed due to name conflict
 *
 * Revision 1.25  2005/02/02 07:55:26  pkrishnan
 * 2DO tags are replaced by Savant copy rights tag
 *
 * Revision 1.24  2005/01/30 07:03:38  pkrishnan
 * InvaliDriverException and SQLExceptions are removed
 *
 * Revision 1.23  2005/01/29 10:57:14  pkrishnan
 * Exception namely Invaliddriver and SQL was written to logggers
 *
 * Revision 1.22  2005/01/29 10:40:47  pkrishnan
 * Exception namely Invaliddriver and SQL was written to logggers
 *
 * Revision 1.21  2005/01/22 05:12:15  pkrishnan
 * after adding seperate date functions for oracle and mysql
 *
 * Revision 1.20  2005/01/21 14:26:20  pkrishnan
 * after adding seperate date functions for oracle and mysql
 *
 * Revision 1.19  2005/01/21 13:17:50  pkrishnan
 * after adding seperate date functions for oracle and mysql
 *
 * Revision 1.18  2005/01/21 12:59:26  pkrishnan
 * after adding seperate date functions for oracle and mysql
 *
 * Revision 1.17  2005/01/21 07:32:13  pkrishnan
 * end index of the rowcount is modified from < to <=
 *
 * Revision 1.16  2005/01/21 06:26:09  pkrishnan
 * complex filter method has been corrected
 *
 * Revision 1.15  2005/01/20 15:09:58  pkrishnan
 * complex filter method has been corrected
 *
 * Revision 1.14  2005/01/20 13:44:21  pkrishnan
 * callable statement with 1 parameter is added
 *
 * Revision 1.13  2005/01/20 11:36:35  pkrishnan
 * build dynamic query has be changed
 *
 * Revision 1.12  2005/01/20 11:28:03  pkrishnan
 * build dynamic query has be changed
 *
 * Revision 1.11  2005/01/20 11:09:09  pkrishnan
 * build dynamic query has be changed
 *
 * Revision 1.10  2005/01/19 12:21:23  pkrishnan
 * scheduler has been commited initially with createProcessInstance
 *
 * Revision 1.9  2005/01/13 12:57:41  pkrishnan
 * resetconnection() was removed from DBConnection
 *
 * Revision 1.8  2005/01/13 06:29:10  jthangaraj
 * in getrowcountwithcomplexfilters() sortby and sortorder parameter was removed
 *
 * Revision 1.7  2005/01/12 12:02:17  pkrishnan
 * InvalidDriverException has been added to the getConnection method
 *
 * Revision 1.6  2005/01/11 14:22:05  pkrishnan
 * dbConnection variable is assigned as private variable
 *
 * Revision 1.5  2005/01/11 07:20:16  pkrishnan
 * at the EOF Log information string has been added
 *
 */