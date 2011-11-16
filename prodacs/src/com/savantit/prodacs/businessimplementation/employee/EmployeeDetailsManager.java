/*
 * Created on Oct 26, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.DBNull;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeDetailsManager
{

	DBNull dbNull = new DBNull();
	Object objDBNull = dbNull;

	static Logger logger = Logger.getLogger(EmployeeDetailsManager.class);

	public EmployeeDetailsManager()
	{
		logger.addAppender(LoggerOutput.getAppender());
		logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
	}

	public boolean addEmployeeDetails(EmployeeDetails objEmployeeDetails) throws SQLException, EmployeeException
	{
		boolean result = false;
		Hashtable ht_Emp_Add = new Hashtable();
		Vector vec_Emp_Add = new Vector();
		int res = 0;
		DBConnection con = null;


		logger.info("ADD EMPLOYEE DETAILS STARTED");

		if(objEmployeeDetails == null)
		{

			logger.error("EMPLOYEE DETAILS OBJECT IS NULL");
			throw new EmployeeException("EMEC001","EMPLOYEE DETAILS OBJECT IS NULL","");
		}
		if(objEmployeeDetails.getEmp_Name().trim().length() == 0)
		{


			logger.error("EMPLOYEE NAME IS REQUIRED");
			throw new EmployeeException("EMEC002","EMPLOYEE NAME IS REQUIRED","");
		}
		if(objEmployeeDetails.getEmp_Code().trim().length() == 0)
		{


			logger.error("EMPLOYEE CODE IS REQUIRED");
			throw new EmployeeException("EMEC002","EMPLOYEE CODE IS REQUIRED","");
		}

		con = DBConnectionFactory.getConnection();


		/* ADD EMPLOYEE DETAILS */

		//Store the Employee Details into Hashtable
		ht_Emp_Add.put("EMP_NAME",objEmployeeDetails.getEmp_Name());
		ht_Emp_Add.put("EMP_CDE",objEmployeeDetails.getEmp_Code());
		ht_Emp_Add.put("EMP_TYP_ID",new Integer(objEmployeeDetails.getEmp_Typ_Id()));
		ht_Emp_Add.put("EMP_STAT_ID",new Integer(objEmployeeDetails.getEmp_Stat_Id()));
		ht_Emp_Add.put("EMP_INSRVCE",objEmployeeDetails.getEmp_Insrvce() ? "1": "0");//: objEmployeeDetails.getEmp_Insrvce());
		ht_Emp_Add.put("EMP_DOJ",objEmployeeDetails.getEmp_Doj() == null ? objDBNull : objEmployeeDetails.getEmp_Doj());
		ht_Emp_Add.put("EMP_DOB", objEmployeeDetails.getEmp_Dob() == null ? objDBNull : objEmployeeDetails.getEmp_Dob());
		ht_Emp_Add.put("EMP_CNTCT_ADDR1",objEmployeeDetails.getEmp_Cntct_Addr1() == null ? "" : objEmployeeDetails.getEmp_Cntct_Addr1());
		ht_Emp_Add.put("EMP_CNTCT_ADDR2",objEmployeeDetails.getEmp_Cntct_Addr2() == null ? "" : objEmployeeDetails.getEmp_Cntct_Addr2());
		ht_Emp_Add.put("EMP_CNTCT_CITY",objEmployeeDetails.getEmp_Cntct_City() == null ? "" : objEmployeeDetails.getEmp_Cntct_City());
		ht_Emp_Add.put("EMP_CNTCT_STATE",objEmployeeDetails.getEmp_Cntct_State() == null ? "" : objEmployeeDetails.getEmp_Cntct_State());
		ht_Emp_Add.put("EMP_CNTCT_PCODE",objEmployeeDetails.getEmp_Cntct_Pcode() == null ? "" : objEmployeeDetails.getEmp_Cntct_Pcode());
		ht_Emp_Add.put("EMP_CNTCT_PHONE1",objEmployeeDetails.getEmp_Cntct_Phone1() == null ? "" : objEmployeeDetails.getEmp_Cntct_Phone1());
		ht_Emp_Add.put("EMP_CNTCT_PHONE2",objEmployeeDetails.getEmp_Cntct_Phone2() == null ? "" : objEmployeeDetails.getEmp_Cntct_Phone2());
		ht_Emp_Add.put("EMP_CNTCT_NAME",objEmployeeDetails.getEmp_Cntct_Name() == null ? "" : objEmployeeDetails.getEmp_Cntct_Name());
		ht_Emp_Add.put("EMP_BLOOD_GP",objEmployeeDetails.getEmp_BloodGp() == null ? "" : objEmployeeDetails.getEmp_BloodGp());
		ht_Emp_Add.put("EMP_PERMNT_ADDR1",objEmployeeDetails.getEmp_Permnt_Addr1() == null ? "" : objEmployeeDetails.getEmp_Permnt_Addr1());
		ht_Emp_Add.put("EMP_PERMNT_ADDR2",objEmployeeDetails.getEmp_Permnt_Addr2() == null ? "" : objEmployeeDetails.getEmp_Permnt_Addr2());
		ht_Emp_Add.put("EMP_PERMNT_CITY",objEmployeeDetails.getEmp_Permnt_City() == null ? "" : objEmployeeDetails.getEmp_Permnt_City());
		ht_Emp_Add.put("EMP_PERMNT_STATE",objEmployeeDetails.getEmp_Permnt_State() == null ? "" : objEmployeeDetails.getEmp_Permnt_State());
		ht_Emp_Add.put("EMP_PERMNT_PCODE",objEmployeeDetails.getEmp_Permnt_Pcode() == null ? "" : objEmployeeDetails.getEmp_Permnt_Pcode());
		ht_Emp_Add.put("EMP_PERMNT_PHONE1",objEmployeeDetails.getEmp_Permnt_Phone1() == null ? "" : objEmployeeDetails.getEmp_Permnt_Phone1());
		ht_Emp_Add.put("EMP_PERMNT_PHONE2",objEmployeeDetails.getEmp_Permnt_Phone2() == null ? "" : objEmployeeDetails.getEmp_Permnt_Phone2());
		
		

		try
		{
			//execute the preparedstatement
			res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_DETAILS_ADD_QUERY,ht_Emp_Add);
			if(res>=1)
			{


				logger.debug("EMPLOYEE RECORD ADDED: " + vec_Emp_Add);
				if(BuildConfig.DMODE)
				{
					System.out.println("EMPLOYEE RECORD ADDED");
				}
				result = true;

			}
			else
			{
				if(BuildConfig.DMODE)
				{
					System.out.println("EMPLOYEE RECORD NOT ADDED");
				}

				logger.debug("EMPLOYEE RECORD NOT ADDED");


			}

		}
		catch(SQLException ex)
		{

			logger.error("SQL ERROR", ex);
			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();

			}
			result = false;
			if(BuildConfig.DBASE==BuildConfig.ORACLE)
			{
				if(ex.toString().indexOf("FK_EM_EMPTYPID")>=0)
				{
					throw new EmployeeException("EMEC003","PARENT KEY EMPLOYEE TYPE NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("FK_EM_EMPSTATID")>=0)
				{
					throw new EmployeeException("EMEC004","PARENT KEY EMPLOYEE STATUS NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("UK_EMPNAME")>=0)
				{
					logger.error("EMPLOYEE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC010","EMPLOYEE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{
				    throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
				if(ex.toString().indexOf("foreign key constraint")>=0)
				{
					throw new EmployeeException("EMEC003","PARENT KEY EMPLOYEE TYPE OR EMPLOYEE STATUS NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("Duplicate entry")>=0)
				{
					logger.error("EMPLOYEE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC004","EMPLOYEE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}

			}
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}



		return result;

	}
	//UPDATE EMPLOYEE DETAILS

	public boolean updateEmployeeDetails(EmployeeDetails objEmployeeDetails) throws SQLException, EmployeeException
	{

		DBConnection con = null;
		boolean result = false;
		Hashtable ht_Emp_Update = new Hashtable();
		Vector vec_Emp_Update = new Vector();
		//ResultSet rs = null;
		Object objDBNull = dbNull;
		int res = 0;


		logger.info("UPDATE EMPLOYEE DETAILS STARTED");

		if(objEmployeeDetails == null)
		{

			logger.error("EMPLOYEE DETAILS OBJECT IS NULL");
			throw new EmployeeException("EMEC005","EMPLOYEE DETAILS OBJECT IS NULL","");
		}


		con = DBConnectionFactory.getConnection();

		//update Employee Details

		ht_Emp_Update.put("EMP_ID",new Integer(objEmployeeDetails.getEmp_Id()));
		ht_Emp_Update.put("EMP_NAME",objEmployeeDetails.getEmp_Name());
		ht_Emp_Update.put("EMP_CDE",objEmployeeDetails.getEmp_Code());
		ht_Emp_Update.put("EMP_TYP_ID",new Integer(objEmployeeDetails.getEmp_Typ_Id()));
		ht_Emp_Update.put("EMP_STAT_ID",new Integer(objEmployeeDetails.getEmp_Stat_Id()));
		ht_Emp_Update.put("EMP_INSRVCE",objEmployeeDetails.getEmp_Insrvce() ? "1" : "0");//"" : objEmployeeDetails.getEmp_Insrvce());
		ht_Emp_Update.put("EMP_DOJ",objEmployeeDetails.getEmp_Doj() == null ? objDBNull : objEmployeeDetails.getEmp_Doj());
		ht_Emp_Update.put("EMP_DOB", objEmployeeDetails.getEmp_Dob() == null ? objDBNull : objEmployeeDetails.getEmp_Dob());
		ht_Emp_Update.put("EMP_CNTCT_ADDR1",objEmployeeDetails.getEmp_Cntct_Addr1() == null ? "" : objEmployeeDetails.getEmp_Cntct_Addr1());
		ht_Emp_Update.put("EMP_CNTCT_ADDR2",objEmployeeDetails.getEmp_Cntct_Addr2() == null ? "" : objEmployeeDetails.getEmp_Cntct_Addr2());
		ht_Emp_Update.put("EMP_CNTCT_CITY",objEmployeeDetails.getEmp_Cntct_City() == null ? "" : objEmployeeDetails.getEmp_Cntct_City());
		ht_Emp_Update.put("EMP_CNTCT_STATE",objEmployeeDetails.getEmp_Cntct_State() == null ? "" : objEmployeeDetails.getEmp_Cntct_State());
		ht_Emp_Update.put("EMP_CNTCT_PCODE",objEmployeeDetails.getEmp_Cntct_Pcode() == null ? "" : objEmployeeDetails.getEmp_Cntct_Pcode());
		ht_Emp_Update.put("EMP_CNTCT_PHONE1",objEmployeeDetails.getEmp_Cntct_Phone1() == null ? "" : objEmployeeDetails.getEmp_Cntct_Phone1());
		ht_Emp_Update.put("EMP_CNTCT_PHONE2",objEmployeeDetails.getEmp_Cntct_Phone2() == null ? "" : objEmployeeDetails.getEmp_Cntct_Phone2());
		ht_Emp_Update.put("EMP_CNTCT_NAME",objEmployeeDetails.getEmp_Cntct_Name() == null ? "" : objEmployeeDetails.getEmp_Cntct_Name());
		ht_Emp_Update.put("EMP_BLOOD_GP",objEmployeeDetails.getEmp_BloodGp() == null ? "" : objEmployeeDetails.getEmp_BloodGp());
		ht_Emp_Update.put("EMP_PERMNT_ADDR1",objEmployeeDetails.getEmp_Permnt_Addr1() == null ? "" : objEmployeeDetails.getEmp_Permnt_Addr1());
		ht_Emp_Update.put("EMP_PERMNT_ADDR2",objEmployeeDetails.getEmp_Permnt_Addr2() == null ? "" : objEmployeeDetails.getEmp_Permnt_Addr2());
		ht_Emp_Update.put("EMP_PERMNT_CITY",objEmployeeDetails.getEmp_Permnt_City() == null ? "" : objEmployeeDetails.getEmp_Permnt_City());
		ht_Emp_Update.put("EMP_PERMNT_STATE",objEmployeeDetails.getEmp_Permnt_State() == null ? "" : objEmployeeDetails.getEmp_Permnt_State());
		ht_Emp_Update.put("EMP_PERMNT_PCODE",objEmployeeDetails.getEmp_Permnt_Pcode() == null ? "" : objEmployeeDetails.getEmp_Permnt_Pcode());
		ht_Emp_Update.put("EMP_PERMNT_PHONE1",objEmployeeDetails.getEmp_Permnt_Phone1() == null ? "" : objEmployeeDetails.getEmp_Permnt_Phone1());
		ht_Emp_Update.put("EMP_PERMNT_PHONE2",objEmployeeDetails.getEmp_Permnt_Phone2() == null ? "" : objEmployeeDetails.getEmp_Permnt_Phone2());
		


		try
		{
			//execute the preparedstatement
			res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_DETAILS_UPDATE_QUERY,ht_Emp_Update);

		}
		catch(SQLException ex)
		{


			logger.error("SQLERROR",ex);

			if(BuildConfig.DMODE)
			{
				System.out.println("EXCEPTION WHILE UPDATING EMPLOYEE RECORD");
				ex.printStackTrace();
			}
			result = false;
			if(BuildConfig.DBASE==BuildConfig.ORACLE)
			{
				if(ex.toString().indexOf("FK_EM_EMPTYPID")>=0)
				{
					throw new EmployeeException("EMEC003","PARENT KEY EMPLOYEE TYPE NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("FK_EM_EMPSTATID")>=0)
				{
					throw new EmployeeException("EMEC004","PARENT KEY EMPLOYEE STATUS NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("UK_EMPNAME")>=0)
				{
					logger.error("EMPLOYEE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC010","EMPLOYEE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
				if(ex.toString().indexOf("foreign key constraint")>=0)
				{
					throw new EmployeeException("EMEC003","PARENT KEY EMPLOYEE TYPE OR EMPLOYEE STATUS NOT FOUND",ex.toString());
				}
				else if(ex.toString().indexOf("Duplicate entry")>=0)
				{
					logger.error("EMPLOYEE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC004","EMPLOYEE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}

			}
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("CONNECTION ERROR : EXCEPTION WHILE CLOSING CONNECTION", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		if(res>=1)
		{


			logger.debug("EMPLOYEE RECORD UPDATED: " + vec_Emp_Update);
			if(BuildConfig.DMODE)
			{
				System.out.println("EMPLOYEE RECORD UPDATED");
			}
			result = true;
		}

		else
		{


			logger.debug("EMPLOYEE RECORD NOT UPDATED" + vec_Emp_Update);

			result = false;
			if(BuildConfig.DMODE)
			{
				System.out.println("EMPLOYEE RECORD NOT UPDATED");
			}

		}

		return result;
	}

	//GET ALL EMPLOYEE IDS
	public LinkedHashMap getAllEmployees() throws EmployeeException, SQLException
	{
		DBConnection con = null;
		ResultSet rs_Get_All_Emp = null;
		PreparedStatement ps = null;
		LinkedHashMap hm_Get_All_Emp = new LinkedHashMap();

		logger.info("GET ALL EMPLOYEES STARTED");

		try
		{
			con = DBConnectionFactory.getConnection();

			//execute the query
			ps = con.executeStatement(SQLMaster.GET_ALL_EMPLOYEES_QUERY);
			rs_Get_All_Emp = ps.executeQuery();
			while(rs_Get_All_Emp.next())
			{
				hm_Get_All_Emp.put(new Integer(rs_Get_All_Emp.getInt("EMP_ID")),rs_Get_All_Emp.getString("EMP_NAME")+" ~ "+rs_Get_All_Emp.getString("EMP_CDE"));
			}
			rs_Get_All_Emp.close();
			ps.close();
		}
		catch(SQLException e)
		{

			logger.error("GENERAL SQL ERROR", e);

			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("GET ALL EMPLOYEES ENDED");

		if(BuildConfig.DMODE)
		{
			System.out.println("GET ALL EMPLOYEES ENDED");
		}
		return hm_Get_All_Emp;
	}

	public LinkedHashMap getAllEmployeesByType(int empTypeId) throws EmployeeException, SQLException
	{
		DBConnection con = null;

		ResultSet rs_Get_All_Emp = null;
		Hashtable ht_Emp_Det = new Hashtable();



		LinkedHashMap hm_Get_All_Emp = new LinkedHashMap();
		if(BuildConfig.DMODE)
		{

			logger.info("Employee Details Manager GET ALL EMPLOYEES BY TYPE STARTED");
		}

		con = DBConnectionFactory.getConnection();
		ht_Emp_Det.put("EMP_TYP_ID",new Integer(empTypeId));
		try
		{
			PreparedStatement ps = con.executeStatement(SQLMaster.GET_EMPLOYEE_BY_TYPE_QUERY,ht_Emp_Det);
			rs_Get_All_Emp = ps.executeQuery();
			while(rs_Get_All_Emp.next())
			{
				hm_Get_All_Emp.put(new Integer(rs_Get_All_Emp.getInt("EMP_ID")),rs_Get_All_Emp.getString("EMP_NAME")+" ~ "+rs_Get_All_Emp.getString("EMP_CDE"));
			}
			rs_Get_All_Emp.close();
			ps.close();
		}
		catch(SQLException ex)
		{


			logger.error("SQL ERROR", ex);
			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
		}

		finally
		{
			try
			{
				if(con!=null)
				{
					if(con.getConnection()!=null)
					{
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
					}
				}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("GET ALL EMPLOYEES ENDED");
		if(BuildConfig.DMODE)
		{
			System.out.println("GET ALL EMPLOYEES ENDED");
		}
		return hm_Get_All_Emp;
	}

	//GET DETAILS OF THE SELECTED EMPLOYEE ID
	public EmployeeDetails getEmployeeDetails(int empId) throws SQLException,EmployeeException
	{
		EmployeeDetails obj_Emp_Det = new EmployeeDetails();
		DBConnection con = null;
		Hashtable ht_Emp_Det = new Hashtable();
		ResultSet rs_Emp_Det = null;
		String st="";


		logger.info("GET EMPLOYEE DETAIL OF " + empId + " STARTED");


		con = DBConnectionFactory.getConnection();

		//put EMP_ID into Hashtable
		ht_Emp_Det.put("EMP_ID",new Integer(empId));

		try
		{
			PreparedStatement ps = con.executeStatement(SQLMaster.GET_EMPLOYEE_DETAILS_QUERY,ht_Emp_Det);
			rs_Emp_Det = ps.executeQuery();
			if(rs_Emp_Det.next())
			{

				obj_Emp_Det.setEmp_Id(rs_Emp_Det.getInt("EMP_ID"));
				obj_Emp_Det.setEmp_Name(rs_Emp_Det.getString("EMP_NAME"));
				obj_Emp_Det.setEmp_Code(rs_Emp_Det.getString("EMP_CDE"));
				obj_Emp_Det.setEmp_Typ_Id(rs_Emp_Det.getInt("EMP_TYP_ID"));
				obj_Emp_Det.setEmp_Typ_Name(rs_Emp_Det.getString("EMP_TYP_NAME"));
				obj_Emp_Det.setEmp_Stat_Id(rs_Emp_Det.getInt("EMP_STAT_ID"));
				obj_Emp_Det.setEmp_Stat_Name(rs_Emp_Det.getString("EMP_STAT_NAME"));
				obj_Emp_Det.setEmp_Insrvce(rs_Emp_Det.getString("EMP_INSRVCE").equals("0") ? false:true);
				obj_Emp_Det.setEmp_Doj(rs_Emp_Det.getTimestamp("EMP_DOJ"));
				obj_Emp_Det.setEmp_Dob(rs_Emp_Det.getTimestamp("EMP_DOB"));
				obj_Emp_Det.setEmp_Cntct_Addr1((st=rs_Emp_Det.getString("EMP_CNTCT_ADDR1")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_Addr2((st=rs_Emp_Det.getString("EMP_CNTCT_ADDR2")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_City((st=rs_Emp_Det.getString("EMP_CNTCT_CITY")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_State((st=rs_Emp_Det.getString("EMP_CNTCT_STATE")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_Pcode((st=rs_Emp_Det.getString("EMP_CNTCT_PCODE")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_Phone1((st=rs_Emp_Det.getString("EMP_CNTCT_PHONE1")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_Phone2((st=rs_Emp_Det.getString("EMP_CNTCT_PHONE2")) == null ? "":st);
				obj_Emp_Det.setEmp_Cntct_Name((st=rs_Emp_Det.getString("EMP_CNTCT_NAME")) == null ? "":st);
				obj_Emp_Det.setEmp_BloodGp((st=rs_Emp_Det.getString("EMP_BLOOD_GP")) == null ? "":st);
				
				obj_Emp_Det.setEmp_Permnt_Addr1((st=rs_Emp_Det.getString("EMP_PERMNT_ADDR1")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_Addr2((st=rs_Emp_Det.getString("EMP_PERMNT_ADDR2")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_City((st=rs_Emp_Det.getString("EMP_PERMNT_CITY")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_State((st=rs_Emp_Det.getString("EMP_PERMNT_STATE")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_Pcode((st=rs_Emp_Det.getString("EMP_PERMNT_PCODE")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_Phone1((st=rs_Emp_Det.getString("EMP_PERMNT_PHONE1")) == null ? "":st);
				obj_Emp_Det.setEmp_Permnt_Phone2((st=rs_Emp_Det.getString("EMP_PERMNT_PHONE2")) == null ? "":st);
				
				
				obj_Emp_Det.setEmp_DateStamp(rs_Emp_Det.getTimestamp("EMP_DATESTAMP"));
				obj_Emp_Det.setEmp_Isvalid(rs_Emp_Det.getInt("EMP_ISVALID"));


				logger.debug("GET EMPLOYEE DETAILS FINISHED");

			}
			else
			{
				if(BuildConfig.DMODE)
					System.out.println("EMPLOYEE RECORD NOT FOUND");

				logger.error("EMPLOYEE RECORD NOT FOUND");
				throw new EmployeeException("EMEC008","EMPLOYEE RECORD NOT FOUND","");

			}
			rs_Emp_Det.close();
			ps.close();
		}
		catch(SQLException ex)
		{


			logger.error("SQL ERROR", ex);

			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
				System.out.println("EXCEPTION WHILE SELECTING EMPLOYEE DETAILS");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}
		return obj_Emp_Det;
	}
	//MAKE EMPLOYEES VALID
	public HashMap makeEmployeesValid(Vector empIds) throws SQLException,EmployeeException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hmp_Emp_MkValid = new HashMap();


		logger.info("MAKE EMPLOYEE VALID STARTED");

		if(empIds == null)
		{

			logger.error("EMPLOYEE VECTOR IS NULL");
			throw new EmployeeException("EMEC009","EMPLOYEE VECTOR OBJECT IS NULL","");
		}
		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();
			for(int i=0;i<empIds.size();i++)
			{
				Hashtable ht_EmpId = new Hashtable();

				//put the EMP_ID into Hashtable
				ht_EmpId.put("EMP_ID",(Integer)empIds.get(i));


				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_MAKE_VALID_QUERY,ht_EmpId);
				if(res>=1)
				{
					hmp_Emp_MkValid.put(empIds.get(i),new Integer(0));
					if(BuildConfig.DMODE)
					{
						System.out.println("EMPLOYEE RECORD VALIDATED");
					}

					logger.debug("EMPLOYEE RECORD VALIDATED");
					con.commitTransaction();
				}
				else
				{
					hmp_Emp_MkValid.put(empIds.get(i),new Integer(1));
					con.rollBackTransaction();
					if(BuildConfig.DMODE)
					{
						System.out.println("EMPLOYEE RECORD NOT VALIDATED");
					}

					logger.debug("EMPLOYEE RECORD NOT VALIDATED :");

				}

			}
		}
		catch(SQLException e)
		{

			logger.error("GENERAL SQL ERROR",e);
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		return hmp_Emp_MkValid;

	}

	//MAKE EMPLOYEES INVALID
	public HashMap makeEmployeesInValid(Vector empIds) throws SQLException,EmployeeException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hm_Emp_MkInValid = new HashMap();


		logger.info("MAKE EMPLOYEE INVALID STARTED");

		if(empIds == null)
		{

			logger.error("EMPLOYEE VECTOR OBJECT IS NULL");
			throw new EmployeeException("EMEC010","EMPLOYEE VECTOR OBJECT IS NULL","");
		}

		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();

			for(int i=0;i<empIds.size();i++)
			{
				Hashtable ht_EmpId = new Hashtable();
				ht_EmpId.put("EMP_ID",(Integer)empIds.get(i));

				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_MAKE_INVALID_QUERY,ht_EmpId);

				//if record updated successfully commit the transanction
				if(res>=1)
				{
					hm_Emp_MkInValid.put(empIds.get(i),new Integer(0));

					logger.debug("EMPLOYEE RECORD INVALIDATED");
					if(BuildConfig.DMODE)
						System.out.println("EMPLOYEE RECORD INVALIDATED");
					con.commitTransaction();
				}
				//otherwise rollback the transaction
				else
				{
					hm_Emp_MkInValid.put(empIds.get(i),new Integer(1));

					logger.debug("EMPLOYEE RECORD NOT INVALIDATED :");

					if(BuildConfig.DMODE)
					{
						System.out.println("EMPLOYEE RECORD NOT INVALIDATED");
					}
					con.rollBackTransaction();
				}
			}
		}
		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}

			logger.error("GENERAL SQL ERROR",e);

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{

				if(con!=null)
				{
					if(con.getConnection()!=null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
				}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("EMPLOYEE RECORD(S) INVALIDATION  ENDS");

		return hm_Emp_MkInValid;

	}

	//EMPLOYEE TYPE MANAGER STARTS HERE

	//ADD A NEW EMPLOYEE TYPE//

	public boolean addEmployeeTypeDetails(EmployeeTypeDetails empTypDetObj) throws SQLException, EmployeeException
	{
		Hashtable ht_Emp_Typ_Add = new Hashtable();
		boolean result = false;
		ResultSet rs_Emp_Typ_Id= null;
		PreparedStatement ps = null;
		int emp_Typ_Id ;
		DBConnection con = null;
		int res = 0;


		logger.info("ADD EMPLOYEE TYPE DETAILS STARTED");

		if(empTypDetObj == null)
		{

			logger.error("EMPLOYEE TYPE DETAILS OBJECT IS NULL");
			throw new EmployeeException("EMEC011","EMPLOYEE TYPE DETAILS OBJECT IS NULL","");
		}
		if(empTypDetObj.getEmp_Typ_Name().trim().length()== 0)
		{

			logger.error("EMPLOYE TYPE NAME IS REQUIRED");
			throw new EmployeeException("EMEC012","EMPLOYEE TYPE NAME IS REQUIRED","");
		}
		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();

			ht_Emp_Typ_Add.put("EMP_TYP_NAME",empTypDetObj.getEmp_Typ_Name());
			ht_Emp_Typ_Add.put("EMP_TYP_DESC",empTypDetObj.getEmp_Typ_Desc() == null ? "" : empTypDetObj.getEmp_Typ_Desc());
			ht_Emp_Typ_Add.put("EMP_TYP_DT",empTypDetObj.isEmp_Typ_Dt()?"1":"0");
			ht_Emp_Typ_Add.put("EMP_TYP_OT",empTypDetObj.isEmp_Typ_Ot()?"1":"0");
			ht_Emp_Typ_Add.put("EMP_TYP_INCENTIVE",empTypDetObj.isEmp_Typ_Incentive()?"1":"0");
			try
			{
				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TYPE_DETAILS_ADD_QUERY,ht_Emp_Typ_Add);
			}
			catch(SQLException ex)
			{
				con.rollBackTransaction();
				if(BuildConfig.DMODE)
				{
					ex.printStackTrace();
				}

				if(BuildConfig.DBASE==BuildConfig.ORACLE)
				{
					if(ex.toString().indexOf("UK_EMPTYPNAME")>=0)
					{
						logger.error("EMPLOYEE TYPE NAME ALREADY EXISTS",ex);
						throw new EmployeeException("EMEC013","EMPLOYEE TYPE NAME ALREADY EXISTS",ex.toString());
					}
					else
					{

						logger.error("GENERAL SQL ERROR  ",ex);
						throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
					}
				}
				if(BuildConfig.DBASE==BuildConfig.MYSQL)
				{
					if(ex.toString().indexOf("Duplicate entry")>=0)
					{
						logger.error("EMPLOYEE TYPE NAME ALREADY EXISTS",ex);
						throw new EmployeeException("EMEC013","EMPLOYEE TYPE NAME ALREADY EXISTS",ex.toString());
					}
					else
					{

						logger.error("GENERAL SQL ERROR  ",ex);
						throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
					}
				}


			}

			if(res>=1)
			{


				logger.debug("EMPLOYEE TYPE RECORD ADDED");


				try
				{
					ps = con.executeStatement(SQLMaster.EMPLOYEE_TYP_GET_CURR_ID_QUERY);
					rs_Emp_Typ_Id = ps.executeQuery();
				}
				catch(SQLException excep)
				{
					if(BuildConfig.DMODE)
					{
						excep.printStackTrace();
						System.out.println("EXCEPTION WHILE GETTING EMPLOYEE TYPE ID");
					}

					logger.error("EXCEPTION WHILE GETTING EMPLOYEE TYPE ID",excep);
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",excep.toString());

				}

				//ADDING EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY

				if(rs_Emp_Typ_Id .next())
				{
					emp_Typ_Id = rs_Emp_Typ_Id.getInt(1);

					Hashtable ht_Emp_Team = new Hashtable();

					//put emp_typ_id and min_rqd_qty into hashtable

					ht_Emp_Team.put("EMP_TYP_ID",new Integer(emp_Typ_Id));
					ht_Emp_Team.put("MIN_RQD_QTY",new Integer(0));
					//EXECUTING EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY
					int emp_Team_Add_Result = 0;
					try
					{
						emp_Team_Add_Result = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TEAM_MINREQQTY_ADD,ht_Emp_Team);
					}
					catch(SQLException ex)
					{
						if(BuildConfig.DMODE)
						{
							ex.printStackTrace();
							System.out.println("EXCEPTION WHILE ADDING EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY");
						}

						logger.error("EXCEPTION WHILE ADDING EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY",ex);
						con.rollBackTransaction();
						throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
					}
					if(emp_Team_Add_Result >= 1)
					{
						result = true;
						if(BuildConfig.DMODE)
							System.out.println("EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY ADDED");

						logger.debug("EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY ADDED");
						con.commitTransaction();
					}
					else
					{
						if(BuildConfig.DMODE)
							System.out.println("EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY NOT ADDED");

						logger.debug("EMPLOYEE TEAM MINIMUM REQUIRED QUANTITY NOT ADDED");
						con.rollBackTransaction();
					}
				}
				rs_Emp_Typ_Id.close();
				ps.close();
			}
		}

		catch(SQLException excep)
		{
			con.rollBackTransaction();
			logger.error("GENERAL SQL ERROR",excep);
			if(BuildConfig.DMODE)
			{
				excep.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",excep.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		logger.info("EMPLOYEE TYPE RECORD ADD END");

		return result;

	}

	//UPDATE EMPLOYEE TYPE DETAILS

	public boolean updateEmployeeTypeDetails(EmployeeTypeDetails empTypDetObj) throws SQLException,EmployeeException
	{
		Hashtable ht_Emp_Typ_Update = new Hashtable();
		boolean result = false;
		DBConnection con = null;
		//Statement stmt = con.getConnection().createStatement();
		int res = 0;

		logger.info("UPDATE EMPLOYEE TYPE DETAILS STARTED");
		if(empTypDetObj == null)
		{

			logger.error("EMPLOYEE TYPE DETAILS OBJECT IS NULL");
			throw new EmployeeException("EMEC014","EMPLOYEE TYPE DETAILS OBJECT IS NULL","");
		}


		if(empTypDetObj.getEmp_Typ_Name().trim().length()== 0)
		{

			logger.error("EMPLOYE TYPE NAME IS REQUIRED");
			throw new EmployeeException("EMEC015","EMPLOYEE TYPE NAME IS REQUIRED","");
		}
		con = DBConnectionFactory.getConnection();
		con.startTransaction();
		//Employee Type Updation

		ht_Emp_Typ_Update.put("EMP_TYP_ID",empTypDetObj.getEmp_Typ_Id() == 0 ? objDBNull : new Integer(empTypDetObj.getEmp_Typ_Id()));
		ht_Emp_Typ_Update.put("EMP_TYP_NAME",empTypDetObj.getEmp_Typ_Name());
		ht_Emp_Typ_Update.put("EMP_TYP_DT",empTypDetObj.isEmp_Typ_Dt()?"1":"0");
		ht_Emp_Typ_Update.put("EMP_TYP_OT",empTypDetObj.isEmp_Typ_Ot()?"1":"0");
		ht_Emp_Typ_Update.put("EMP_TYP_INCENTIVE",empTypDetObj.isEmp_Typ_Incentive()?"1":"0");
		ht_Emp_Typ_Update.put("EMP_TYP_DESC",empTypDetObj.getEmp_Typ_Desc() == null ? "" : empTypDetObj.getEmp_Typ_Desc());

		//execute the query
		try
		{
			res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TYPE_DETAILS_UPDATE_QUERY,ht_Emp_Typ_Update);
			if(res>=1)
			{
				result = true;

				logger.debug("EMPLOYEE TYPE RECORD UPDATED");

				con.commitTransaction();

			}
			else
			{
				if(BuildConfig.DMODE)
					System.out.println("EMPLOYEE TYPE RECORD NOT FOUND");
				logger.error("EMPLOYEE TYPE RECORD NOT UPDATED");
				throw new EmployeeException("EMEC016","EMPLOYEE TYPE RECORD NOT FOUND","");
			}
		}
		catch(SQLException ex)
		{
			con.rollBackTransaction();
			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
				System.out.println("EXCEPTION WHILE UPDATING EMPLOYEE TYPE DETAILS");
			}

			logger.error("EXCEPTION WHILE UPDATING EMPLOYEE TYPE DETAILS",ex);

			if(BuildConfig.DBASE==BuildConfig.ORACLE)
			{
				if(ex.toString().indexOf("UK_EMPTYPNAME")>=0)
				{
					logger.error("EMPLOYEE TYPE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC013","EMPLOYEE TYPE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{

					logger.error("GENERAL SQL ERROR  ",ex);
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
				if(ex.toString().indexOf("Duplicate entry")>=0)
				{
					logger.error("EMPLOYEE TYPE NAME ALREADY EXISTS",ex);
					throw new EmployeeException("EMEC013","EMPLOYEE TYPE NAME ALREADY EXISTS",ex.toString());
				}
				else
				{

					logger.error("GENERAL SQL ERROR  ",ex);
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}


		}


		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("GENERAL EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}
		return result;

	}
	public LinkedHashMap getAllEmployeeTypes() throws SQLException,EmployeeException
	{
		ResultSet rs_Get_All_EmpTyp = null;
		LinkedHashMap hm_All_Emp_Types = new LinkedHashMap();
		DBConnection con = null;


		logger.info("GET ALL EMPLOYEE TYPES STARTED");

		try
		{
			con = DBConnectionFactory.getConnection();
			Statement stmt = con.getConnection().createStatement();
			String get_All_EmpTyp_Query = SQLMaster.GET_ALL_EMPLOYEE_TYPES_QUERY;

			try
			{
				rs_Get_All_EmpTyp = stmt.executeQuery(get_All_EmpTyp_Query);
			}
			catch(SQLException ex)
			{
				if(BuildConfig.DMODE)
				{
					ex.printStackTrace();
				}

				logger.error("SQL ERROR", ex);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
			}
			while(rs_Get_All_EmpTyp.next())
			{
				hm_All_Emp_Types.put(new Integer(rs_Get_All_EmpTyp.getInt("EMP_TYP_ID")),rs_Get_All_EmpTyp.getString("EMP_TYP_NAME"));
			}
			rs_Get_All_EmpTyp.close();

		}
		catch(SQLException e)
		{

			logger.error("SQL ERROR", e);

			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("SQL ERROR", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}



		logger.info("GET ALL EMPLOYEE TYPES ENDED");
		return hm_All_Emp_Types;
	}


	//GET EMPLOYEE TYPE DETAILS
	public EmployeeTypeDetails getEmployeeTypeDetails(int empTypId) throws SQLException,EmployeeException
	{
		DBConnection con = null;
		EmployeeTypeDetails obj_Emp_Typ_Det = new EmployeeTypeDetails();
		Hashtable ht_Get_Emp_Typ = new Hashtable();
		ResultSet rs_Get_Emp_Typ_Det = null;
		PreparedStatement ps = null;


		logger.info("GET EMPLOYEE TYPE DETAIL OF " + empTypId + " STARTED");

		if(empTypId <= 0)
		{

			logger.error("EMPLOYEE TYPE ID IS NULL");
			throw new EmployeeException("EMEC017","EMPLOYEE TYPE ID IS NULL","");
		}

		try
		{
			con = DBConnectionFactory.getConnection();

			ht_Get_Emp_Typ.put("EMP_TYP_ID",new Integer(empTypId));

			try
			{
				ps = con.executeStatement(SQLMaster.GET_EMPLOYEE_TYPE_DETAILS_QUERY,ht_Get_Emp_Typ);
				rs_Get_Emp_Typ_Det = ps.executeQuery();
			}
			catch(SQLException ex)
			{

				if(BuildConfig.DMODE)
				{
					ex.printStackTrace();
					System.out.println("EXCEPTION WHILE EXECUTING EMPLOYEE TYPE QUERY");
				}

				logger.error("EXCEPTION WHILE EXECUTING EMPLOYEE TYPE QUERY",ex);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
			}
			String st="";
			if(rs_Get_Emp_Typ_Det.next())
			{
				obj_Emp_Typ_Det.setEmp_Typ_Id(rs_Get_Emp_Typ_Det.getInt("EMP_TYP_ID"));
				obj_Emp_Typ_Det.setEmp_Typ_Name(rs_Get_Emp_Typ_Det.getString("EMP_TYP_NAME"));
				obj_Emp_Typ_Det.setEmp_Typ_Dt(rs_Get_Emp_Typ_Det.getString("EMP_TYP_DT").equals("1")?true:false);
				obj_Emp_Typ_Det.setEmp_Typ_Ot(rs_Get_Emp_Typ_Det.getString("EMP_TYP_OT").equals("1")?true:false);
				obj_Emp_Typ_Det.setEmp_Typ_Incentive(rs_Get_Emp_Typ_Det.getString("EMP_TYP_INCENTIVE").equals("1")?true:false);
				obj_Emp_Typ_Det.setEmp_Typ_Desc((st=rs_Get_Emp_Typ_Det.getString("EMP_TYP_DESC")) == null ? "":st);
				obj_Emp_Typ_Det.setEmp_Typ_DateStamp(rs_Get_Emp_Typ_Det.getTimestamp("EMP_TYP_DATESTAMP"));
				obj_Emp_Typ_Det.setEmp_Typ_IsValid(rs_Get_Emp_Typ_Det.getInt("EMP_TYP_ISVALID"));
				logger.info("EMPLOYEE TYPE DETAILS SELECTED");

			}
			else
			{


				logger.error("EMPLOYEE TYPE NOT FOUND");
				throw new EmployeeException("EMEC018","EMPLOYEE TYPE NOT FOUND","");
			}
			rs_Get_Emp_Typ_Det.close();


		}
		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}

			logger.error("GENERAL ERROR", e);

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("SQL ERROR", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("EMPLOYEE TYPE DETAILS SELECTION ENDS");
		return obj_Emp_Typ_Det;

	}
	//MAKE EMPLOYEES VALID
	public HashMap makeEmployeeTypesValid(Vector empTypIds) throws SQLException, EmployeeException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hm_Result = new HashMap();

		logger.info("MAKE EMPLOYEE TYPES VALID STARTS");
		if(empTypIds == null)
		{

			logger.error("EMPLOYEE TYPE VECTOR OBJECT IS NULL");
			throw new EmployeeException("EMEC019","EMPLOYEE TYPE VECTOR OBJECT IS NULL","");
		}

		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();
			for(int i=0;i<empTypIds.size();i++)
			{
				Hashtable ht_Emp_Typ_Id = new Hashtable();

				//put the EMP_ID into Hashtable
				ht_Emp_Typ_Id.put("EMP_TYP_ID",(Integer)empTypIds.get(i));

				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TYPE_MAKE_VALID_QUERY,ht_Emp_Typ_Id);

				if(res>=1)
				{
					con.commitTransaction();
					hm_Result.put(empTypIds.get(i),new Integer(0));

					logger.info("EMPLOYEE TYPE RECORD VALIDATED : " + empTypIds.get(i));

				}
				else
				{
					con.rollBackTransaction();
					hm_Result.put(empTypIds.get(i),new Integer(1));


					logger.info("EMPLOYEE TYPE RECORD NOT VALIDATED : " + empTypIds.get(i));

				}


			}//for loop end

		}//first try end

		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}

			logger.error("GENERAL ERROR",e);

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("MAKE EMPLOYEE TYPES VALID ENDS");

		return hm_Result;

	}

	//MAKE EMPLOYEES INVALID
	public HashMap makeEmployeeTypesInValid(Vector empTypIds) throws SQLException, EmployeeException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hm_Emp_Typ_MkInValid = new HashMap();


		logger.info("MAKE EMPLOYEE TYPES INVALID STARTS");

		if(empTypIds == null)
		{

			logger.error("EMPLOYEE TYPE VECTOR OBJECT IS NULL");
			throw new EmployeeException("EMEC020","EMPLOYEE TYPE VECTOR OBJECT IS NULL","");
		}

		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();
			for(int i=0;i<empTypIds.size();i++)
			{
				Hashtable ht_Emp_Typ_Id = new Hashtable();
				ht_Emp_Typ_Id.put("EMP_TYP_ID",(Integer)empTypIds.get(i));

				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TYPE_MAKE_INVALID_QUERY,ht_Emp_Typ_Id);

				if(res>=1)
				{


					logger.info("EMPLOYEE TYPE RECORD INVALIDATED - "+empTypIds.get(i));

					hm_Emp_Typ_MkInValid.put(empTypIds.get(i),new Integer(0));

					//if record updated successfully commit the transanction
					con.commitTransaction();
				}

				else
				{

					hm_Emp_Typ_MkInValid.put(empTypIds.get(i),new Integer(1));
					con.rollBackTransaction();

					logger.info("EMPLOYEE TYPE RECORD NOT INVALIDATED -"+empTypIds.get(i));

				}

			}
		}

		catch(SQLException ex)
		{


			logger.error("GENERAL EXCEPTION"+ex.toString());

			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");

				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("MAKE EMPLOYEE TYPES INVALID ENDS");

		return hm_Emp_Typ_MkInValid;

	}

	public HashMap getAllEmployeeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, EmployeeException
	{
		DBConnection con = null;


		logger.info("GET ALL EMPLOYEE DETAILS STARTS");


		if((filters == null)||( sortBy == null))
		{

			logger.error("FILTER VALUES ARE NULL");

			throw new EmployeeException("EMEC021","FILTER VALUES ARE NULL","");
		}
		if(BuildConfig.DMODE)
			System.out.println("GET ALL EMPLOYEE DETAILS STARTS");
		con = DBConnectionFactory.getConnection();
		HashMap hash_Result = new HashMap();

		int tot_Rec_Cnt = 0;


		try
		{

			//	filters and tableName are passed to the function and receives Total Number of Records
			tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_EMPLOYEE_DETAILS_QUERY);

			//Finding the end index for the query
			int eIndex = startIndex + displayCount;

			// filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
			String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_EMPLOYEE_DETAILS_QUERY);

			if(BuildConfig.DMODE)
			{
				// total records and query
				System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
				System.out.println("QUERY : " + Query);
			}

			logger.info("records : " + tot_Rec_Cnt);
			logger.info("Query : " + Query);

			ResultSet rs  = null;

			try
			{
				rs = con.executeRownumStatement(Query);
			}
			catch(Exception sqle)
			{
				if(BuildConfig.DMODE)
				{
					sqle.printStackTrace();
				}

				logger.error("SQL ERROR", sqle);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",sqle.toString());
			}

			// vector object to store the result set values
			Vector vec_All_Emp_Det = new Vector();



			while(rs.next())
			{

				EmployeeDetails empDetObj = new EmployeeDetails();

				empDetObj.setEmp_Id(rs.getInt("EMP_ID"));
				empDetObj.setEmp_Name(rs.getString("EMP_NAME"));
				empDetObj.setEmp_Code(rs.getString("EMP_CDE"));
				empDetObj.setEmp_Stat_Id(rs.getInt("EMP_STAT_ID"));
				empDetObj.setEmp_Typ_Id(rs.getInt("EMP_TYP_ID"));
				empDetObj.setEmp_Typ_Name(rs.getString("EMP_TYP_NAME"));
				empDetObj.setEmp_Stat_Name(rs.getString("EMP_STAT_NAME"));
				empDetObj.setEmp_DateStamp(rs.getTimestamp("EMP_DATESTAMP"));
				empDetObj.setEmp_Isvalid(rs.getInt("EMP_ISVALID"));

				vec_All_Emp_Det.addElement(empDetObj);
			}
			hash_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
			hash_Result.put("EmployeeDetails", vec_All_Emp_Det);
			if(BuildConfig.DMODE)
				System.out.println("HashResult : " + hash_Result);
			rs.getStatement().close();
			rs.close();
		}
		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
			}

			logger.error("GENERAL SQL ERROR",e);

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null )
					{
						if(!con.getConnection().isClosed())
							con.closeConnection();
					}
			}
			catch(SQLException ex)
			{

				logger.error("CONNECTION ERROR", ex);
			}
		}


		logger.info("GET All EMPLOYEE DETAILS END");

		if(BuildConfig.DMODE)
			System.out.println("Get All Employee Details Over");
		return hash_Result;

	}
	public HashMap getAllEmployeeTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException, EmployeeException
	{
		DBConnection con = null;

		logger.info("GET ALL EMPLOYEE TYPE DETAILS STARTS");


		if((filters == null)||( sortBy == null))
		{

			logger.error("FILTER VALUES ARE NULL");

			throw new EmployeeException("EMEC022","FILTER VALUES ARE NULL","");
		}
		if(BuildConfig.DMODE)
			System.out.println("GET ALL EMPLOYEE TYPE DETAILS STARTS");

		con = DBConnectionFactory.getConnection();
		HashMap hash_Result = new HashMap();

		int tot_Rec_Cnt = 0;

		try
		{
			//	filters and tableName are passed to the function and receives Total Number of Records
			tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_EMPLOYEE_TYPE_DETAILS_QUERY);

			//Finding the end index for the query
			int eIndex = startIndex + displayCount;

			// filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
			String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_EMPLOYEE_TYPE_DETAILS_QUERY);

			if(BuildConfig.DMODE)
			{
				// total records and query
				System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
				System.out.println("QUERY : " + Query);
			}

			logger.info("Total Records : " + tot_Rec_Cnt);
			logger.info("Query : " + Query);



			ResultSet rs  = null;

			try
			{
				rs = con.executeRownumStatement(Query);
			}
			catch(Exception sqle)
			{
				if(BuildConfig.DMODE)
				{
					sqle.printStackTrace();
				}

				logger.error("SQL ERROR", sqle);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",sqle.toString());
			}

			// vector object to store the result set values
			Vector vec_All_Emp_Typ_Det = new Vector();



			while(rs.next())
			{

				EmployeeTypeDetails objEmpTypDet = new EmployeeTypeDetails();

				objEmpTypDet.setEmp_Typ_Id(rs.getInt("EMP_TYP_ID"));
				objEmpTypDet.setEmp_Typ_Name(rs.getString("EMP_TYP_NAME"));
				objEmpTypDet.setEmp_Typ_Dt(rs.getString("EMP_TYP_DT").equals("1")?true:false);
				objEmpTypDet.setEmp_Typ_Ot(rs.getString("EMP_TYP_OT").equals("1")?true:false);
				objEmpTypDet.setEmp_Typ_Incentive(rs.getString("EMP_TYP_INCENTIVE").equals("1")?true:false);
				objEmpTypDet.setEmp_Typ_DateStamp(rs.getTimestamp("EMP_TYP_DATESTAMP"));
				objEmpTypDet.setEmp_Typ_IsValid(rs.getInt("EMP_TYP_ISVALID"));
				objEmpTypDet.setEmp_Typ_Desc(rs.getString("EMP_TYP_DESC"));
				vec_All_Emp_Typ_Det.addElement(objEmpTypDet);
			}
			hash_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
			hash_Result.put("EmployeeTypeDetails", vec_All_Emp_Typ_Det);
			if(BuildConfig.DMODE)
				System.out.println("HashResult : " + hash_Result);
			rs.getStatement().close();
			rs.close();
			
			
		}
		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
			}

			logger.error("GENERAL SQL ERROR");

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("CONNECTION ERROR", ex);
			}
		}


		logger.info("GET All EMPLOYEE TYPE DETAILS END");

		if(BuildConfig.DMODE)
			System.out.println("Get All Employee Type Details Over");
		return hash_Result;

	}

	public EmployeeTypeDetails[] getEmployeeTypeWithTeam() throws EmployeeException, SQLException
	{
		DBConnection con = null;
		Vector vec_Result = new Vector();
		EmployeeTypeDetails[]  objEmployeeTypeDetailsList = null;

		ResultSet rs_Get_Emp_Typ_Det = null;
		PreparedStatement ps = null;


		try
		{
			con = DBConnectionFactory.getConnection();

			try
			{
				ps = con.executeStatement(SQLMaster.GET_EMPLOYEE_TYPE_DETAILS_WITH_TEAM_QUERY);
				rs_Get_Emp_Typ_Det = ps.executeQuery();
			}
			catch(SQLException ex)
			{

				if(BuildConfig.DMODE)
				{
					ex.printStackTrace();
					System.out.println("EXCEPTION WHILE EXECUTING EMPLOYEE TYPE QUERY");
				}

				logger.error("EXCEPTION WHILE EXECUTING EMPLOYEE TYPE QUERY",ex);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
			}
			String st="";
			while(rs_Get_Emp_Typ_Det.next())
			{
				EmployeeTypeDetails obj_Emp_Typ_Det = new EmployeeTypeDetails();
				obj_Emp_Typ_Det.setEmp_Typ_Id(rs_Get_Emp_Typ_Det.getInt("EMP_TYP_ID"));
				obj_Emp_Typ_Det.setMin_Rqd_Qty(rs_Get_Emp_Typ_Det.getInt("MIN_RQD_QTY"));
				obj_Emp_Typ_Det.setEmp_Typ_Name(rs_Get_Emp_Typ_Det.getString("EMP_TYP_NAME"));
				obj_Emp_Typ_Det.setEmp_Typ_Desc((st=rs_Get_Emp_Typ_Det.getString("EMP_TYP_DESC")) == null ? "":st);
				obj_Emp_Typ_Det.setEmp_Typ_DateStamp(rs_Get_Emp_Typ_Det.getTimestamp("EMP_TYP_DATESTAMP"));
				vec_Result.add(obj_Emp_Typ_Det);

			}
			rs_Get_Emp_Typ_Det.close();
			ps.close();
			objEmployeeTypeDetailsList = new EmployeeTypeDetails[vec_Result.size()];
			vec_Result.copyInto(objEmployeeTypeDetailsList);

		}
		catch(SQLException e)
		{
			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}

			logger.error("GENERAL ERROR", e);

			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}

			}
			catch(SQLException ex)
			{

				logger.error("SQL ERROR", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		return objEmployeeTypeDetailsList;
	}


	public boolean updateEmployeeTypeDetailsWithTeam(EmployeeTypeDetails[] objEmployeeTypeDetailsList) throws SQLException, EmployeeException
	{

		Hashtable ht_Emp_Typ_Update = new Hashtable();
		boolean result = false;
		DBConnection con = null;
		int res = 0;

		logger.info("UPDATE EMPLOYEE TYPE DETAILS STARTED");


		con = DBConnectionFactory.getConnection();
		con.startTransaction();

		//Employee Type Updation


		//execute the query
		try
		{
			for(int i = 0;i < objEmployeeTypeDetailsList.length ; i++)
			{
				EmployeeTypeDetails empTypDetObj = new EmployeeTypeDetails();
				empTypDetObj = (EmployeeTypeDetails)objEmployeeTypeDetailsList[i];
				ht_Emp_Typ_Update.put("EMP_TYP_ID",empTypDetObj.getEmp_Typ_Id() == 0 ? objDBNull : new Integer(empTypDetObj.getEmp_Typ_Id()));
				ht_Emp_Typ_Update.put("MIN_RQD_QTY",new Integer(empTypDetObj.getMin_Rqd_Qty()));

				res = con.executeUpdateStatement(SQLMaster.EMPLOYEE_TEAM_MINREQQTY_UPDATE,ht_Emp_Typ_Update);
				if(res>=1)
				{
					result = true;

					logger.debug("EMPLOYEE TYPE RECORD UPDATED");

					con.commitTransaction();

				}
				else
				{
					if(BuildConfig.DMODE)
						System.out.println("EMPLOYEE TYPE RECORD NOT FOUND");
					logger.error("EMPLOYEE TYPE RECORD NOT UPDATED");
					throw new EmployeeException("EMEC016","EMPLOYEE TYPE RECORD NOT FOUND","");
				}
			}
		}
		catch(SQLException ex)
		{
			con.rollBackTransaction();
			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
				System.out.println("EXCEPTION WHILE UPDATING EMPLOYEE TYPE DETAILS");
			}

			logger.error("EXCEPTION WHILE UPDATING EMPLOYEE TYPE DETAILS",ex);

			if(BuildConfig.DBASE==BuildConfig.ORACLE)
			{
				logger.error("GENERAL SQL ERROR  ",ex);
				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());

			}
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
				logger.error("GENERAL SQL ERROR  ",ex);
				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());

			}


		}


		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("GENERAL EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		return result;


	}

	//EMPLOYEE STATUS MANAGER STATRS HERE//

	//ADD A NEW STATUS //
	public int addNewEmployeeStatus(EmployeeStatusDetails objEmpStat) throws SQLException, EmployeeException
	{
		Hashtable ht_Emp_Stat_Add = new Hashtable();
		int empStatId = 0;
		DBConnection con = null;


		logger.info("ADD EMPLOYEE STATUS DETAILS STARTED");

		if(objEmpStat == null)
		{

			logger.error("EMPLOYEE STATUS DETAILS OBJECT IS NULL");
			throw new EmployeeException("EMEC023","EMPLOYEE STATUS DETAILS OBJECT IS NULL","");
		}


		if(objEmpStat.getEmpStatName().trim().length()== 0)
		{

			logger.error("EMPLOYE STATUS NAME IS NULL");
			throw new EmployeeException("EMEC024","EMPLOYEE STATUS NAME IS NULL","");
		}

		con = DBConnectionFactory.getConnection();
		ht_Emp_Stat_Add.put("EMP_STATUS_NAME",objEmpStat.getEmpStatName());


		try
		{
			con.executeUpdateStatement(SQLMaster.EMPLOYEE_STATUS_ADD_QUERY,ht_Emp_Stat_Add);

			PreparedStatement ps = con.executeStatement(SQLMaster.EMP_STAT_ID_SELECT_SQL_QUERY);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				empStatId = rs.getInt(1);
			}
			rs.close();
			ps.close();


		}
		catch(SQLException ex)
		{
			if(BuildConfig.DBASE==BuildConfig.ORACLE)
			{
				if(ex.toString().indexOf("UK_EMPSTATNAME")>=0)
				{
					if(BuildConfig.DMODE)
					{
						ex.printStackTrace();
					}

					logger.error("EMPLOYEE STATUS NAME ALREADY EXISTS",ex);

					throw new EmployeeException("EMEC025","EMPLOYEE STATUS ALREADY EXISTS",ex.toString());
				}
				else
				{
					if(BuildConfig.DMODE)
					{
						ex.printStackTrace();
					}

					logger.error("GENERAL SQL ERROR ",ex);
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}
			if(BuildConfig.DBASE==BuildConfig.MYSQL)
			{
				if(ex.toString().indexOf("Duplicate entry")>=0)
				{
					if(BuildConfig.DMODE)
					{
						ex.printStackTrace();
					}

					logger.error("EMPLOYEE STATUS NAME ALREADY EXISTS",ex);

					throw new EmployeeException("EMEC025","EMPLOYEE STATUS ALREADY EXISTS",ex.toString());
				}
				else
				{
					if(BuildConfig.DMODE)
					{
						ex.printStackTrace();
					}

					logger.error("GENERAL SQL ERROR ",ex);
					throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
				}
			}

		}
		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");

				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("EMPLOYEE STATUS ADD ENDS");

		return empStatId;
	}
	public LinkedHashMap getAllEmployeeStatus() throws SQLException,EmployeeException
	{
		ResultSet rs_Get_All_Emp_Stat = null;
		LinkedHashMap hm_All_Emp_Stat = new LinkedHashMap();
		DBConnection con = null;


		logger.info("GET ALL EMPLOYEE STATUS STARTED");

		try
		{
			con = DBConnectionFactory.getConnection();

			Statement stmt = con.getConnection().createStatement();
			String get_All_Emp_Stat_Query = SQLMaster.GET_ALL_EMPLOYEE_STATUS_QUERY;

			try
			{
				rs_Get_All_Emp_Stat = stmt.executeQuery(get_All_Emp_Stat_Query);
			}
			catch(SQLException ex)
			{
				if(BuildConfig.DMODE)
				{
					ex.printStackTrace();
				}

				logger.error("SQL ERROR", ex);

				throw new EmployeeException("EMEC000","GENERAL SQL ERROR",ex.toString());
			}
			while(rs_Get_All_Emp_Stat.next())
			{
				hm_All_Emp_Stat.put(new Integer(rs_Get_All_Emp_Stat.getInt("EMP_STAT_ID")),rs_Get_All_Emp_Stat.getString("EMP_STAT_NAME"));

			}
			rs_Get_All_Emp_Stat.close();

		}
		catch(SQLException e)
		{

			logger.error("SQL ERROR", e);

			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new EmployeeException("EMEC000","GENERAL SQL ERROR",e.toString());
		}

		finally
		{
			try
			{
				if(con != null)
					if(con.getConnection() != null)
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
			}
			catch(SQLException ex)
			{

				logger.error("SQL ERROR", ex);
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		logger.info("GET ALL EMPLOYEE STATUS ENDED");

		return hm_All_Emp_Stat;
	}
	public static void main(String args[]) throws SQLException, EmployeeException
	{
		DBConnection dbCon = DBConnectionFactory.getConnection();
		int i = dbCon.executeUpdateStatement("set @rownum:=0");
		System.out.println("Count : "+i);

		//		EmployeeDetailsManager empmgr = new EmployeeDetailsManager();
		//		HashMap hm = empmgr.getAllEmployees();
		//	System.out.println("size "+hm.size());
		/*
		 EmployeeDetails empdetobj = new EmployeeDetails();
		 EmployeeTypeDetails empTypDetObj = new EmployeeTypeDetails();

		 EmployeeTypeDetails empTypDetObjres = new EmployeeTypeDetails();
		 boolean result1 = false;

		 HashMap result3 = new HashMap();
		 Vector ids = new Vector();
		 Vector ids1=new Vector();
		 System.out.println("Main Starts");
		 */

		//ids.addElement(new Integer(11));
		//ids.addElement(new Integer(9));

		//empdetobj.setEmp_Id(22);
		//empdetobj.setEmp_Name("Employee1");
		//empdetobj.setEmp_Typ_Id(38);
		//empdetobj.setEmp_Stat_Id(32);

		//try
		//{

		//addEmployeeDetails()
		//result1 = empmgr.addEmployeeDetails(empdetobj);

		//updateEmployeeDetails()
		//result1 = empmgr.updateEmployeeDetails(empdetobj);
		//result1 = empmgr.deleteEmployee(ids);

		//getAllEmployees()

		//ids1=empmgr.getAllEmployees();
		//for(int i=0;i<ids1.size();i++)
		//System.out.println("id :"+ids1.get(i));


		//empdetobj = empmgr.getEmployeeDetails(14);
		//System.out.println("Name : "+empdetobj.getEmp_Name());
		//System.out.println("DATESTAMP : "+empdetobj.getEmp_DateStamp())
		//empTypDetObj.setEmp_Typ_Id(26);
		//System.out.println("ISVALID : "+empdetobj.getEmp_Isvalid());
		//


		//for getEmployeeTypeDetails()

		//empTypDetObjres = empmgr.getEmployeeTypeDetails(36);
		//System.out.println("MinReqQty :"+empTypDetObjres.getMin_Rqd_Qty());
		//System.out.println("Result :"+empTypDetObjres.getEmp_Typ_Name());
		//System.out.println("Result :"+res.size());


		//getAllEmployees()

		//HashMap resHm1 = new HashMap();
		//resHm1 = empmgr.getAllEmployees();
		//System.out.println("HashMap size "+resHm1.size());

		//getEmployeeDetail()

		//empdetobj = empmgr.getEmployeeDetails(22);
		//System.out.println("Employee Name :"+empdetobj.getEmp_Name());
		//System.out.println("TYPE NAME :"+empdetobj.getEmp_Typ_Name());

		//for getAllEmployeeDetails()

		/*	HashMap hmp = new HashMap();
		 Filter filobj[] = new Filter[1];

		 filobj[0] = new Filter();
		 filobj[0].setFieldName("EMP_NAME");
		 filobj[0].setFieldValue("1");
		 filobj[0].setSpecialFunction("Starts With");
		 */
		//filobj[0].setFieldName("EMP_ISVALID");
		//filobj[0].setFieldValue("1");
		//filobj[0].setSpecialFunction("");
		//int startIndex = 1;
		//int displayCount = 4;
		//boolean ascending = true;
		//String sortBy = "EMP_NAME";
		//	hmp = empmgr.getAllEmployeeDetails(filobj,sortBy,ascending,startIndex,displayCount);
		//System.out.println("HASHMAP RESULT OF FILTER"+hmp);
		//System.out.println("Main Ends");


		//	for makeEmployeesInValid() && makeEmployeeValid()
		//Vector vecRes = new Vector();
		//vecRes =null;
		// vecRes.addElement(new Integer(18));
		// vecRes.addElement(new Integer(19));
		// vecRes.addElement(new Integer(20));
		// HashMap resHm = new HashMap();
		//resHm=empmgr.makeEmployeesInValid(vecRes);
		// resHm=empmgr.makeEmployeesValid(vecRes);
		// System.out.println("HashMap size "+resHm.size());

		//-------------------------------------//employee type//--------------------------------------------------//

		//addEmployeeType()

		// empTypDetObj.setEmp_Typ_Name("newtype");
		// empTypDetObj.setEmp_Typ_Id(39);
		// empTypDetObj.setEmp_Typ_Desc("new type of employee");
		// empTypDetObj.setMin_Rqd_Qty(25);
		//result1 = empmgr.addEmployeeTypeDetails(empTypDetObj);

		//updateEmployeeType()

		// result1=empmgr.updateEmployeeTypeDetails(empTypDetObj);

		//getAllEmployeeTypes()

		//  HashMap res = empmgr.getAllEmployeeTypes();
		// System.out.println("Result :"+res);

		//getAllEmployeeTypeDetails

		// HashMap hm = new HashMap();
		//Filter fil[] = new Filter[1];
		//fil[0] = new Filter();
		//fil[0].setFieldName("EMP_TYP_NAME");
		//fil[0].setFieldValue("a");
		/// fil[0].setSpecialFunction("Anywhere");
		// int startIndex = 1;
		// int displayCount = 4;
		/// boolean ascending = true;
		// String sortBy = "EMP_TYP_NAME";

		// hm = empmgr.getAllEmployeeTypeDetails(fil,sortBy,new Boolean(ascending).booleanValue(),startIndex,displayCount);



		//for makeEmployeesTypesInValid() && makeEmployeeTypesValid()
		//Vector vecRes = new Vector();
		//vecRes =null;
		//  vecRes.addElement(new Integer(36));
		// vecRes.addElement(new Integer(38));
		//HashMap resHm = new HashMap();
		//resHm=empmgr.makeEmployeeTypesInValid(vecRes);
		// resHm=empmgr.makeEmployeeTypesValid(vecRes);
		//System.out.println("HashMap size "+resHm);
		// EmployeeStatusDetails empStatObj = new EmployeeStatusDetails();
		// empStatObj.setEmpStatName("Regular");

		// boolean hmp = empmgr.addNewEmployeeStatus(empStatObj);
		// System.out.println("Result of EMployee status Add:"+hmp);

		// HashMap hmp = empmgr.getAllEmployeeStatus();
		// System.out.println("Result of EMployee status Add:"+hmp);

		//System.out.println("Result :"+result1);
		//}
		//catch(Exception e)
		//{
		//System.out.println("Exception in main");
		//e.printStackTrace();

		//}

	}
}



/***
 $Log: EmployeeDetailsManager.java,v $
 Revision 1.73  2005/09/15 07:36:09  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.72  2005/09/10 13:18:07  kduraisamy
 order by clause added.

 Revision 1.71  2005/08/19 10:37:10  kduraisamy
 " - " is changed to " ~ ".

 Revision 1.70  2005/08/19 04:23:57  kduraisamy
 EMP_NAME AND EMP_CDE TAKEN FOR HASHMAP.

 Revision 1.69  2005/08/17 07:50:41  kduraisamy
 EMP_MSTR AND CUST_MSTR TABLE ALTERED.

 Revision 1.68  2005/08/04 10:33:54  kduraisamy
 EMP_CDE ADDED INTO FILTER.

 Revision 1.67  2005/08/03 06:48:58  kduraisamy
 EMP_CDE IS SET TO COMBO VALUES INSTEAD OF EMP_NAME.

 Revision 1.66  2005/08/01 06:09:46  kduraisamy
 EMP_CDE IS INTRODUCED.

 Revision 1.65  2005/07/12 11:36:45  kduraisamy
 imports organized

 Revision 1.64  2005/07/08 10:05:51  kduraisamy
 EMP NAME UNIQUE CONSTRAINT ADDED.

 Revision 1.63  2005/06/29 12:42:57  smurugesan
 if con ! = null added in finally

 Revision 1.62  2005/06/17 07:20:49  kduraisamy
 DUTY OT SPECIFIED IN EMP_TYP_MSTR ITSELF.

 Revision 1.61  2005/06/17 06:44:33  kduraisamy
 DUTY OT SPECIFIED IN EMP_TYP_MSTR ITSELF.

 Revision 1.60  2005/06/17 06:05:04  kduraisamy
 DUTY OT SPECIFIED IN EMP_TYP_MSTR ITSELF.

 Revision 1.59  2005/06/16 11:35:16  kduraisamy
 con.getConnection()!=null added in finally.

 Revision 1.58  2005/05/28 05:32:25  kduraisamy
 EMP_TYP_DESC ADDED FOR LIST.

 Revision 1.57  2005/05/28 04:54:46  kduraisamy
 EMP_TYP_DESC ADDED FOR LIST.

 Revision 1.56  2005/05/27 05:41:12  kduraisamy
 con != null checking added.

 Revision 1.55  2005/05/26 07:14:27  kduraisamy
 team add and update changed.

 Revision 1.54  2005/05/26 07:13:05  kduraisamy
 team add and update changed.

 Revision 1.53  2005/05/18 09:54:46  kduraisamy
 indentation.

 Revision 1.52  2005/05/18 09:05:44  kduraisamy
 team may be 0.

 Revision 1.51  2005/05/18 08:14:02  kduraisamy
 commitTransaction() and rollbackTransaction() added.

 Revision 1.50  2005/05/16 18:34:29  kduraisamy
 specific throws addded for mysql.

 Revision 1.49  2005/05/16 15:42:43  kduraisamy
 specific throws addded for mysql.

 Revision 1.48  2005/05/14 04:45:26  kduraisamy
 con != null added.

 Revision 1.47  2005/05/13 07:56:39  kduraisamy
 connection error corrected in finally..

 Revision 1.46  2005/04/19 13:13:33  kduraisamy
 resultset properly closed.

 Revision 1.45  2005/04/18 12:27:34  kduraisamy
 executeStatement() return type changed.

 Revision 1.44  2005/04/07 09:19:58  kduraisamy
 MODE IS CHANGED TO DMODE.

 Revision 1.43  2005/04/07 07:35:30  kduraisamy
 if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

 Revision 1.42  2005/03/30 08:22:23  kduraisamy
 excecuteRownumStatement() called for filters.

 Revision 1.41  2005/03/26 10:08:04  smurugesan
 ROWNUM RESET QUERY REMOVED.

 Revision 1.40  2005/03/17 12:12:14  smurugesan
 unwanted commit and rollback removed.

 Revision 1.39  2005/03/11 11:30:47  kduraisamy
 getInt(1) added for getInt(Id).

 Revision 1.38  2005/03/10 07:26:45  kduraisamy
 CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

 Revision 1.37  2005/03/04 11:59:50  vkrishnamoorthy
 Indentation.

 Revision 1.36  2005/03/04 08:15:22  kduraisamy
 DBConnection Changes made.

 Revision 1.35  2005/02/16 10:14:11  kduraisamy
 signature modified for addNewEmpStat().

 Revision 1.34  2005/02/14 11:10:04  kduraisamy
 unwanted cast removed.

 Revision 1.33  2005/02/11 06:54:39  kduraisamy
 unused variables removed.

 Revision 1.32  2005/02/08 12:45:25  kduraisamy
 UNWANTED CREATION OF STATEMENT REMOVED.

 Revision 1.31  2005/02/08 12:27:19  kduraisamy
 unwanted exception removed.

 Revision 1.30  2005/02/01 16:10:40  kduraisamy
 exception throw added.

 Revision 1.29  2005/01/31 09:18:28  kduraisamy
 logger place changed.

 Revision 1.28  2005/01/27 14:44:29  kduraisamy
 if(BuildConfig.DMODE)
 {
 }
 properly inclued inside the catch block.

 Revision 1.27  2005/01/18 06:41:10  kduraisamy
 transaction added.

 Revision 1.26  2005/01/06 12:59:43  kduraisamy
 getAllEmployeesByType() added.

 Revision 1.25  2004/12/04 10:42:26  kduraisamy
 new Boolean(ascending).boolVaue() is replaced with just ascending.Error founded by findbugs corrected

 Revision 1.24  2004/12/03 05:31:56  sduraisamy
 Exception Codes Changed

 Revision 1.23  2004/12/01 14:19:51  sduraisamy
 all commented lines removed(unwanted code)

 Revision 1.22  2004/11/23 14:30:27  sduraisamy
 Unwanted Lines removed

 Revision 1.21  2004/11/19 14:42:04  sduraisamy
 Vector object type casted to Integer in Valid and Invalid methods

 Revision 1.20  2004/11/19 09:44:35  sduraisamy
 rs.close() and ps.close() removed

 Revision 1.19  2004/11/18 13:43:47  sduraisamy
 EMP_ID set in getEmployeeDetails()

 Revision 1.18  2004/11/18 07:59:16  sduraisamy
 EMP_ID included in Employee Details Filter method

 Revision 1.17  2004/11/18 06:12:14  sduraisamy
 Employee Filter method modified

 Revision 1.16  2004/11/18 05:28:34  sduraisamy
 EMP_STAT_ID and EMP_TYP_ID included for Employee Filter method

 Revision 1.15  2004/11/18 05:07:28  sduraisamy
 EMP_STAT_NAME included in getAllEmployeeDetails()

 Revision 1.14  2004/11/17 11:53:26  sduraisamy
 Status Name included

 Revision 1.13  2004/11/17 05:34:08  sduraisamy
 TotalRecordCount message modified in filter method

 Revision 1.11  2004/11/10 13:12:37  sduraisamy
 Modification  in  getAllEmployeeDetails()

 Revision 1.10  2004/11/10 07:40:58  sduraisamy
 Unwanted Code Removed

 Revision 1.9  2004/11/10 07:31:59  sduraisamy
 addEmployeeTypeDetails() name modified

 Revision 1.8  2004/11/10 06:59:32  sduraisamy
 makeEmployeeValid and InValid modified

 Revision 1.7  2004/11/10 06:38:22  sduraisamy
 EmployeeStatusDetails,addNewEmployeeStatus and getAllEmployeeStatus included

 Revision 1.6  2004/11/09 04:56:49  kduraisamy
 Log added.

 ***/



























