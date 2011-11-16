/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.machine;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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
public class MachineDetailsManager
{
    static Logger logger = Logger.getLogger(MachineDetailsManager.class);
    DBConnection con = null;
    DBNull objDBNull = new DBNull();
    Object dbNull = objDBNull;
    public MachineDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }

    public int addMachineTypeDetails(MachineTypeDetails objMachTypDetails) throws SQLException,MachineException
    {

        logger.info("ADD MACHINE TYPE DETAILS STARTED");

        if(objMachTypDetails == null)
        {

            logger.error("MACHINE TYPE DETAILS OBJECT IS NULL");
            throw new MachineException("MCEC001","MACHINE TYPE DETAILS OBJECT IS NULL","");
        }
        if(objMachTypDetails.getMc_Typ_Name().length()==0)
        {

            logger.error("MACHINE TYPE NAME IS NULL");
            throw new MachineException("MCEC02","MACHINE TYPE NAME IS NULL","");

        }

        int res = 0;
        int mcTypeId = 0;

        con =  DBConnectionFactory.getConnection();
        Vector vec_Mach_Typ_Det = new Vector();
        Hashtable ht_Mach_Typ_Det = new Hashtable();
        PreparedStatement ps = null;
        ht_Mach_Typ_Det.put("MACH_TYP_NAME",objMachTypDetails.getMc_Typ_Name());
        ht_Mach_Typ_Det.put("MACH_TYP_DESC",objMachTypDetails.getMc_Typ_Desc());

        try
        {

            res = con.executeUpdateStatement(SQLMaster.MACHINE_TYPE_DETAILS_ADD_QUERY,ht_Mach_Typ_Det);
            if(BuildConfig.DMODE)
                System.out.println("NEW MACHINE TYPE ADDED");
            if(res>=1)
            {
                if(BuildConfig.DMODE)
                {

                    logger.debug("NEW MACHINE TYPE ADDED: " + vec_Mach_Typ_Det);
                }
                ps = con.executeStatement(SQLMaster.MC_TYP_ID_SELECT_SQL_QUERY);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                    mcTypeId = rs.getInt(1);
                }
                rs.close();
                ps.close();
            }
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(ex.toString().indexOf("unique")>=0)
                {

                    logger.error("MACHINE TYPE ALREADY EXISTS",ex);
                    throw new MachineException("MCEC003","MACHINE TYPE ALREADY EXISTS",ex.toString());
                }
                else
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(ex.toString().indexOf("Duplicate entry")>=0)
                {

                    logger.error("MACHINE TYPE ALREADY EXISTS",ex);
                    throw new MachineException("MCEC003","MACHINE TYPE ALREADY EXISTS",ex.toString());
                }
                else
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("ADD MACHINE TYPE DETAILS ENDS");

        return mcTypeId;

    }

    public LinkedHashMap getAllMachineTypes() throws SQLException, MachineException
    {
        ResultSet rs_Get_ALL_Mac_Types = null;
        LinkedHashMap hash_Result = new LinkedHashMap();
        if(BuildConfig.DMODE)
        {

            logger.info("GET ALL MACHINE TYPES STARTED");
        }
        try
        {
            con =  DBConnectionFactory.getConnection();

            Statement stmt = con.getConnection().createStatement();
            try
            {
                rs_Get_ALL_Mac_Types = stmt.executeQuery(SQLMaster.GET_ALL_MACHINE_TYPES_QUERY);

                while(rs_Get_ALL_Mac_Types.next())
                {
                    hash_Result.put(new Integer(rs_Get_ALL_Mac_Types.getInt("MC_TYP_ID")),rs_Get_ALL_Mac_Types.getString("MC_TYP_NAME"));
                }
                rs_Get_ALL_Mac_Types.close();
            }
            catch(SQLException e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();

                    logger.error("SQL ERROR",e);
                }
                throw new MachineException("MCEC000","GENERAL SQL ERROR",e.toString());
            }

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();

                logger.error("GENERAL SQL ERROR", ex);
                System.out.println("GENERAL EXCEPTION");
            }
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL MACHINE TYPES ENDED");

        return hash_Result;
    }
    public boolean addMachineDetails(MachineDetails objMachineDetails) throws SQLException,MachineException
    {


        logger.info("ADD MACHINE DETAILS STARTS");

        if(objMachineDetails == null)
        {

            logger.error("MACHINE DETAILS OBJECT IS NULL");
            throw new MachineException("MCEC004","MACHINE DETAILS OBJECT IS NULL","");
        }


        int res = 0;
        boolean result = false;
        Hashtable ht_Mach_Det_Add = new Hashtable();
        String st="";
        con =  DBConnectionFactory.getConnection();
        //put machine Details into Hashtable
        ht_Mach_Det_Add.put("MACH_CDE",objMachineDetails.getMach_Cde());
        ht_Mach_Det_Add.put("MACH_NAME",objMachineDetails.getMach_Name());
        ht_Mach_Det_Add.put("MACH_TYP_ID",objMachineDetails.getMach_Typ_Id()== 0 ? dbNull:new Integer(objMachineDetails.getMach_Typ_Id()));
        ht_Mach_Det_Add.put("MACH_INUSE",(st=objMachineDetails.getMach_InUse())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_INSTALLDATE",objMachineDetails.getMach_Install_Date()== null ? dbNull:objMachineDetails.getMach_Install_Date());
        ht_Mach_Det_Add.put("MACH_SPLR_NAME",(st=objMachineDetails.getMach_SPLR_Name())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SPLR_CNTCT_PERSON",(st=objMachineDetails.getMach_SPLR_Cntct_Person())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SPLR_PHONE",(st=objMachineDetails.getMach_SPLR_Phone())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SPLR_ADDR",(st=objMachineDetails.getMach_SPLR_Addr())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SP_NAME",(st=objMachineDetails.getMach_SP_Name())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SP_CNTCT_PERSON",(st=objMachineDetails.getMach_SP_Cntct_Person())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SP_PHONE",(st=objMachineDetails.getMach_SP_Phone())== null ? "":st);
        ht_Mach_Det_Add.put("MACH_SP_ADDR",(st=objMachineDetails.getMach_SP_Addr())== null ? "":st);

        try
        {
            res = con.executeUpdateStatement(SQLMaster.MACHINE_DETAILS_ADD_QUERY,ht_Mach_Det_Add);
            if(res>=1)
            {
                result = true;

                logger.debug("NEW MACHINE ADDED");

            }
            else
            {

                logger.error("NEW MACHINE NOT ADDED");

            }

        }
        catch(SQLException sqlex)
        {

            if(BuildConfig.DMODE)
            {
                sqlex.printStackTrace();
            }

            logger.error("EXCEPTION WHILE ADDING MACHINE",sqlex);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqlex.toString().indexOf("UK_MCNAME")>=0)
                {
                    throw new MachineException("MCEC005","MACHINE ALREADY EXISTS",sqlex.toString());
                }

                else if(sqlex.toString().indexOf("FK_MM_MCTYPID")>=0)
                {
                    throw new MachineException("MCEC006","PARENT KEY MACHINE TYPE NOT FOUND",sqlex.toString());
                }

                else
                {
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",sqlex.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqlex.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new MachineException("MCEC005","MACHINE ALREADY EXISTS",sqlex.toString());
                }

                else if(sqlex.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new MachineException("MCEC006","PARENT KEY MACHINE TYPE NOT FOUND",sqlex.toString());
                }

                else
                {
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",sqlex.toString());
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


        logger.info("ADD MACHINE DETAILS ENDS");

        return result;
    }

    public boolean updateMachineDetails(MachineDetails objMachineDetails) throws SQLException, MachineException
    {


        logger.info("MACHINE DETAILS UPDATION STARTS");


        if(objMachineDetails == null)
        {

            logger.error("MACHINE DETAILS OBJECT IS NULL");
            throw new MachineException("MCEC007","MACHINE DETAILS OBJECT IS NULL","");

        }
        if(objMachineDetails.getMach_Name().length() == 0)
        {

            logger.error("MACHINE NAME IS REQUIRED");
            throw new MachineException("MCEC008","MACHINE NAME IS REQUIRED","");

        }


        int res = 0;
        boolean result = false;
        Hashtable ht_Mach_Det_Update = new Hashtable();
        String st="";
        con =  DBConnectionFactory.getConnection();
        //put machine Details into Hashtable
        ht_Mach_Det_Update.put("MACH_CDE",objMachineDetails.getMach_Cde());
        ht_Mach_Det_Update.put("MACH_NAME",objMachineDetails.getMach_Name());
        ht_Mach_Det_Update.put("MACH_TYP_ID",objMachineDetails.getMach_Typ_Id()== 0 ? dbNull:new Integer(objMachineDetails.getMach_Typ_Id()));
        ht_Mach_Det_Update.put("MACH_INUSE",(st=objMachineDetails.getMach_InUse())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_INSTALLDATE",objMachineDetails.getMach_Install_Date()== null ? dbNull:objMachineDetails.getMach_Install_Date());
        ht_Mach_Det_Update.put("MACH_SPLR_NAME",(st=objMachineDetails.getMach_SPLR_Name())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SPLR_CNTCT_PERSON",(st=objMachineDetails.getMach_SPLR_Cntct_Person())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SPLR_PHONE",(st=objMachineDetails.getMach_SPLR_Phone())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SPLR_ADDR",(st=objMachineDetails.getMach_SPLR_Addr())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SP_NAME",(st=objMachineDetails.getMach_SP_Name())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SP_CNTCT_PERSON",(st=objMachineDetails.getMach_SP_Cntct_Person())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SP_PHONE",(st=objMachineDetails.getMach_SP_Phone())== null ? "":st);
        ht_Mach_Det_Update.put("MACH_SP_ADDR",(st=objMachineDetails.getMach_SP_Addr())== null ? "":st);

        try
        {
            res = con.executeUpdateStatement(SQLMaster.MACHINE_DETAILS_UPDATE_QUERY,ht_Mach_Det_Update);
            if(res>=1)
            {
                result = true;


                logger.debug("MACHINE DETAILS UPDATED");

            }
            else
            {

                logger.error("MACHINE DETAILS NOT UPDATED");

            }

        }
        catch(SQLException sqlex)
        {
            if(BuildConfig.DMODE)
            {
                sqlex.printStackTrace();

            }


            logger.error("EXCEPTION WHILE UPDATING MACHINE DETAILS", sqlex);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqlex.toString().indexOf("UK_MCNAME")>=0)
                {
                    throw new MachineException("MCEC005","MACHINE ALREADY EXISTS",sqlex.toString());
                }

                else if(sqlex.toString().indexOf("FK_MM_MCTYPID")>=0)
                {
                    throw new MachineException("MCEC006","PARENT KEY MACHINE TYPE NOT FOUND",sqlex.toString());
                }

                else
                {
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",sqlex.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqlex.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new MachineException("MCEC005","MACHINE ALREADY EXISTS",sqlex.toString());
                }

                else if(sqlex.toString().indexOf("foreign key constraint")>=0)
                {
                    throw new MachineException("MCEC006","PARENT KEY MACHINE TYPE NOT FOUND",sqlex.toString());
                }

                else
                {
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",sqlex.toString());
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
					}}
            catch(SQLException ex)
            {

                logger.error("EXCEPTION WHILE CLOSING CONNECTION", ex);
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }


        logger.debug("MACHINE DETAILS UPDATION ENDS");


        return result;
    }

    public MachineDetails getMachineDetails(String machineCode) throws SQLException,MachineException
    {
        Hashtable ht_Get_Mach_Det = new Hashtable();
        ResultSet rs_Get_Mach_Det = null;
        MachineDetails objMachDet = new MachineDetails();
        PreparedStatement ps = null;

        logger.info("GET MACHINE DETAILS STARTS");

        con =  DBConnectionFactory.getConnection();

        ht_Get_Mach_Det.put("MACH_CDE",machineCode);

        String st="";
        try
        {

            ps = con.executeStatement(SQLMaster.GET_MACHINE_DETAILS_QUERY,ht_Get_Mach_Det);
            rs_Get_Mach_Det = ps.executeQuery();
            if(rs_Get_Mach_Det.next())
            {
                objMachDet.setMach_Name(rs_Get_Mach_Det.getString("MC_NAME"));
                objMachDet.setMach_Typ_Id(rs_Get_Mach_Det.getInt("MC_TYP_ID"));
                objMachDet.setMach_Typ_Name(rs_Get_Mach_Det.getString("MC_TYP_NAME"));
                objMachDet.setMach_InUse((st = rs_Get_Mach_Det.getString("MC_INUSE")) == null ? "" : st);
                objMachDet.setMach_Install_Date(rs_Get_Mach_Det.getTimestamp("MC_INSTALLDATE"));

                objMachDet.setMach_SPLR_Name((st = rs_Get_Mach_Det.getString("MC_SPLR_NAME")) == null ? "" : st);
                objMachDet.setMach_SPLR_Cntct_Person((st = rs_Get_Mach_Det.getString("MC_SPLR_CNTCT_PERSON")) == null ? "" : st);
                objMachDet.setMach_SPLR_Phone((st = rs_Get_Mach_Det.getString("MC_SPLR_PHONE")) == null ? "" :  st);
                objMachDet.setMach_SPLR_Addr((st = rs_Get_Mach_Det.getString("MC_SPLR_ADDR")) == null ? "" :  st);

                objMachDet.setMach_SP_Name((st = rs_Get_Mach_Det.getString("MC_SP_NAME")) == null ? "" :  st);
                objMachDet.setMach_SP_Cntct_Person((st = rs_Get_Mach_Det.getString("MC_SP_CNTCT_PERSON")) == null ? "" :  st);
                objMachDet.setMach_SP_Phone((st = rs_Get_Mach_Det.getString("MC_SP_PHONE")) == null ? "" :  st);
                objMachDet.setMach_SP_Addr((st = rs_Get_Mach_Det.getString("MC_SP_ADDR")) == null ? "" :  st);
                objMachDet.setMach_Cde(rs_Get_Mach_Det.getString("MC_CDE"));
                objMachDet.setMach_Datestamp(rs_Get_Mach_Det.getTimestamp("MC_DATESTAMP"));
                objMachDet.setMach_IsValid(rs_Get_Mach_Det.getInt("MC_ISVALID"));



                logger.debug("GET MACHINE DETAILS ENDS");


            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("MACHINE NOT FOUND");

                logger.error("MACHINE NOT FOUND");
                throw new MachineException("MCEC010","MACHINE NOT FOUND","");
            }
            rs_Get_Mach_Det.close();
        }
        catch(SQLException ex)
        {

            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();

            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET MACHINE DETAILS ENDS");

        return objMachDet;

    }
    public LinkedHashMap getAllProdMachines() throws MachineException, SQLException
    {
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;
        if(BuildConfig.DMODE)
        {

            logger.info("GET ALL PROD MACHINES STARTS");
        }
        con = DBConnectionFactory.getConnection();
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_PROD_MACHINES_QUERY);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
            ps.close();

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL PROD MACHINES ENDS");

        return hm_Result;
    }
    public LinkedHashMap getAllProdMachines(Date crntDate,int shiftId) throws MachineException, SQLException
    {

        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;
        Hashtable ht_Date_Shift = new Hashtable();
        if(BuildConfig.DMODE)
        {
            logger.info("GET ALL PROD MACHINES STARTS");
        }
        con = DBConnectionFactory.getConnection();
        ht_Date_Shift.put("PROD_DATE",crntDate);
        ht_Date_Shift.put("SHIFT_ID",new Integer(shiftId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_PROD_MACHINES_WITH_ACCOUNTING_QUERY,ht_Date_Shift);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
            ps.close();

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL PROD MACHINES ENDS");

        return hm_Result;

    }
    public LinkedHashMap getAllRadlMachines(Date crntDate,int shiftId) throws MachineException, SQLException
    {

        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;
        Hashtable ht_Date_Shift = new Hashtable();
        if(BuildConfig.DMODE)
        {

            logger.info("GET ALL RADL MACHINES STARTS");
        }
        con = DBConnectionFactory.getConnection();
        ht_Date_Shift.put("PROD_DATE",crntDate);
        ht_Date_Shift.put("SHIFT_ID",new Integer(shiftId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_RADL_MACHINES_WITH_ACCOUNTING_QUERY,ht_Date_Shift);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
            ps.close();

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL RADL MACHINES ENDS");

        return hm_Result;

    }

    public LinkedHashMap getAllMachines() throws MachineException, SQLException
    {

        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;
        if(BuildConfig.DMODE)
        {

            logger.info("GET ALL MACHINES STARTS");
        }
        con =  DBConnectionFactory.getConnection();
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_MACHINES_QUERY);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);

            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL MACHINES ENDS");

        return hm_Result;
    }
    public LinkedHashMap getAllMachines(Date crntDate,int shiftId) throws MachineException, SQLException
    {

        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;
        Hashtable ht_Date_Shift = new Hashtable();
        if(BuildConfig.DMODE)
        {
            logger.info("GET ALL PROD MACHINES STARTS");
        }
        con = DBConnectionFactory.getConnection();
        ht_Date_Shift.put("PROD_DATE",crntDate);
        ht_Date_Shift.put("SHIFT_ID",new Integer(shiftId));
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_MACHINES_WITH_ACCNTNG_QUERY,ht_Date_Shift);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
            ps.close();

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL PROD MACHINES ENDS");

        return hm_Result;

    }

    public LinkedHashMap getAllRadialMachines() throws MachineException, SQLException
    {
        LinkedHashMap hm_Result = new LinkedHashMap();
        ResultSet rs_Get_All_Mach = null;
        PreparedStatement ps = null;

        logger.info("GET ALL RADIAL MACHINES STARTS");

        con =  DBConnectionFactory.getConnection();
        try
        {
            ps = con.executeStatement(SQLMaster.GET_ALL_RADL_MACHINES_QUERY);
            rs_Get_All_Mach = ps.executeQuery();
            while(rs_Get_All_Mach.next())
            {
                hm_Result.put(rs_Get_All_Mach.getString("MC_CDE"),rs_Get_All_Mach.getString("MC_NAME"));
            }
            rs_Get_All_Mach.close();
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);
            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("GET ALL RADIAL MACHINES ENDS");

        return hm_Result;
    }

    public HashMap makeMachinesValid(Vector machineCodes) throws SQLException, MachineException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE MACHINE VALID STARTS");


        if(machineCodes == null)
        {

            logger.error("MACHINE CODE VECTOR OBJECT IS NULL");
            throw new MachineException("MCEC011","MACHINE CODE VECTOR OBJECT IS NULL","");
        }
        try
        {
            con =  DBConnectionFactory.getConnection();
            con.startTransaction();

            for(int i=0;i<machineCodes.size();i++)
            {
                Hashtable ht_Mk_Mach_Valid = new Hashtable();

                ht_Mk_Mach_Valid.put("MACH_CDE",(String)machineCodes.get(i));
                res = con.executeUpdateStatement(SQLMaster.MACHINE_MKVALID_QUERY,ht_Mk_Mach_Valid);
                if(res>=1)
                {
                    hm_Result.put(machineCodes.get(i),new Integer(0));

                    logger.info("MACHINE VALIDATED");
                    if(BuildConfig.DMODE)
                        System.out.println("MACHINE VALIDATED");
                    con.commitTransaction();
                }

                else
                {
                    hm_Result.put(machineCodes.get(i),new Integer(1));
                    con.rollBackTransaction();


                    logger.debug("MACHINE NOT VALIDATED");


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

            throw new MachineException("MCEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE MACHINE VALID ENDS");

        return hm_Result;

    }
    public HashMap makeMachinesInValid(Vector machineCodes) throws SQLException, MachineException
    {
        HashMap hm_Result = new HashMap();
        int res = 0;


        logger.info("MAKE MACHINES INVALID STARTS");

        if(machineCodes == null)
        {

            logger.error("MACHINE CODE VECTOR OBJECT IS NULL");
            throw new MachineException("MCEC012","MACHINE CODE VECTOR OBJECT IS NULL","");
        }

        try
        {
            con =  DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<machineCodes.size();i++)
            {
                Hashtable ht_Mk_Mach_InValid = new Hashtable();

                ht_Mk_Mach_InValid.put("MACH_CDE",(String)machineCodes.get(i));
                res = con.executeUpdateStatement(SQLMaster.MACHINE_MKINVALID_QUERY,ht_Mk_Mach_InValid);
                if(res>=1)
                {
                    hm_Result.put(machineCodes.get(i),new Integer(0));

                    logger.info("MACHINE INVALIDATED");
                    if(BuildConfig.DMODE)
                        System.out.println("MACHINE INVALIDATED");
                    con.commitTransaction();
                }

                else
                {
                    hm_Result.put(machineCodes.get(i),new Integer(1));
                    con.rollBackTransaction();


                    logger.debug("MACHINE NOT INVALIDATED");

                    //throw new MachineException("MCEC012","MACHINE NOT FOUND","");
                }

            }
        }
        catch(SQLException e)
        {
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();

                logger.error("GENERAL SQL ERROR",e);
                System.out.println("GENERAL EXCEPTION");
            }
            throw new MachineException("MCEC000","GENERAL SQL ERROR",e.toString());
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


        logger.info("MAKE MACHINES INVALID STARTS");

        return hm_Result;

    }
    public HashMap getAllMachineDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException,MachineException
    {


        logger.info("GET All MACHINE DETAILS FILTER STARTS");


        if((filters == null)||( sortBy == null))
        {

            logger.error("FILTER VALUES ARE NULL");

            throw new MachineException("MCEC013","FILTER VALUES ARE NULL","");
        }

        HashMap hm_Result = new HashMap();
        Vector vec_Mach_Details = new Vector();
        int tot_Rec_Cnt = 0;
        ResultSet rs = null;
        int eIndex = 0;

        try
        {
            con =  DBConnectionFactory.getConnection();

            eIndex = startIndex + displayCount;

            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_MACHINE_DETAILS_QUERY);

            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_MACHINE_DETAILS_QUERY);



            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);

            try
            {
                rs = con.executeRownumStatement(Query);
            }
            catch(Exception e)
            {
                if(BuildConfig.DMODE)
                {
                    e.printStackTrace();

                    logger.error("SQL ERROR",e);
                }
                throw new MachineException("MCEC000","GENERAL SQL ERROR",e.toString());

            }
            while(rs.next())
            {
                MachineDetails objMachDet = new MachineDetails();

                objMachDet.setMach_Cde(rs.getString("MC_CDE"));
                objMachDet.setMach_Name(rs.getString("MC_NAME"));
                objMachDet.setMach_Typ_Id(rs.getInt("MC_TYP_ID"));
                objMachDet.setMach_Typ_Name(rs.getString("MC_TYP_NAME"));
                objMachDet.setMach_Install_Date(rs.getTimestamp("MC_INSTALLDATE"));
                objMachDet.setMach_IsValid(rs.getInt("MC_ISVALID"));

                vec_Mach_Details.addElement(objMachDet);
            }

            rs.getStatement().close();
            rs.close();
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("MachineDetails",vec_Mach_Details);

            if(BuildConfig.DMODE)
                System.out.println("Hash Map Result : "+hm_Result);
        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
            }

            logger.error("SQL ERROR",ex);

            throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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

                logger.error("CONNECTION ERROR", ex);
            }
        }


        logger.info("GET All MACHINE DETAILS END");

        if(BuildConfig.DMODE)
            System.out.println("Get All Machine Details Over");

        return hm_Result;

    }

    public HashMap getAllMachineTypeDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws SQLException,MachineException
    {


        logger.info("GET ALL MACHINE TYPE DETAILS STARTS");


        if((filters == null)||( sortBy == null))
        {

            logger.error("FILTER VALUES ARE NULL");
            throw new MachineException("MCEC022","FILTER VALUES ARE NULL","");
        }
        if(BuildConfig.DMODE)
            System.out.println("GET ALL MACHINE TYPE DETAILS STARTS");

        con = DBConnectionFactory.getConnection();
        HashMap hash_Result = new HashMap();

        int tot_Rec_Cnt = 0;



        try
        {

            //	filters and tableName are passed to the function and receives Total Number of Records
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_MC_TYPE_FILTER_QUERY);

            //Finding the end index for the query
            int eIndex = startIndex + displayCount;


            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_MC_TYPE_FILTER_QUERY);

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

                throw new MachineException("MCEC000","GENERAL SQL ERROR",sqle.toString());
            }

            // vector object to store the result set values
            Vector vec_All_Machine_Typ_Det = new Vector();



            while(rs.next())
            {

                MachineTypeDetails objMachineTypDetails = new MachineTypeDetails();
                objMachineTypDetails.setMc_Typ_Id(rs.getInt("MC_TYP_ID"));
                objMachineTypDetails.setMc_Typ_Name(rs.getString("MC_TYP_NAME"));
                objMachineTypDetails.setMc_Typ_Desc(rs.getString("MC_TYP_DESC"));
                objMachineTypDetails.setMc_Typ_DateStamp(rs.getTimestamp("MC_TYP_DATESTAMP"));
                objMachineTypDetails.setMc_Typ_IsValid(rs.getInt("MC_TYP_ISVALID"));
                vec_All_Machine_Typ_Det.addElement(objMachineTypDetails);
            }
            hash_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hash_Result.put("MachineTypeDetails", vec_All_Machine_Typ_Det);
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

            throw new MachineException("MCEC000","GENERAL SQL ERROR",e.toString());
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

                logger.error("CONNECTION ERROR", ex);
            }
        }


        logger.info("GET All MACHINE TYPE DETAILS END");


        return hash_Result;

    }

    //	GET MACHINE TYPE DETAILS
    public MachineTypeDetails getMachineTypeDetails(int machineTypId) throws SQLException, MachineException
    {
        MachineTypeDetails objMachineTypeDetails = new MachineTypeDetails();
        Hashtable ht_Get_Mc_Typ = new Hashtable();
        ResultSet rs_Get_Mc_Typ_Det = null;
        PreparedStatement ps = null;

        logger.info("GET MACHINE TYPE DETAILS OF " + machineTypId + " STARTED");


        if(machineTypId <= 0)
        {

            logger.error("MACHINE TYPE ID IS NULL");
            throw new  MachineException("EMEC017","MACHINE TYPE ID IS NULL","");
        }

        con = DBConnectionFactory.getConnection();

        ht_Get_Mc_Typ.put("MC_TYP_ID",new Integer(machineTypId));

        try
        {

            ps = con.executeStatement(SQLMaster.GET_MACHINE_TYPE_DETAILS_QUERY,ht_Get_Mc_Typ);
            rs_Get_Mc_Typ_Det = ps.executeQuery();
            if(rs_Get_Mc_Typ_Det.next())
            {
                objMachineTypeDetails.setMc_Typ_Id(rs_Get_Mc_Typ_Det.getInt("MC_TYP_ID"));
                objMachineTypeDetails.setMc_Typ_Name(rs_Get_Mc_Typ_Det.getString("MC_TYP_NAME"));
                objMachineTypeDetails.setMc_Typ_DateStamp(rs_Get_Mc_Typ_Det.getTimestamp("MC_TYP_DATESTAMP"));
                objMachineTypeDetails.setMc_Typ_IsValid(rs_Get_Mc_Typ_Det.getInt("MC_TYP_ISVALID"));
                objMachineTypeDetails.setMc_Typ_Desc(rs_Get_Mc_Typ_Det.getString("MC_TYP_DESC"));

                if(BuildConfig.DMODE)
                {

                    logger.info("MACHINE TYPE DETAILS SELECTED");
                }
            }
            else
            {

                if(BuildConfig.DMODE)
                {

                    logger.error("MACHINE TYPE NOT FOUND");
                }
                throw new MachineException("MCEC018","MACHINE TYPE NOT FOUND","");
            }
            rs_Get_Mc_Typ_Det.close();
        }
        catch(SQLException ex)
        {

            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
                System.out.println("EXCEPTION WHILE EXECUTING MACHINE TYPE QUERY");
            }

            logger.error("EXCEPTION WHILE EXECUTING MACHINE TYPE QUERY",ex);

            throw new MachineException("EMEC000","GENERAL SQL ERROR",ex.toString());
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


        logger.info("MACHINE TYPE DETAILS SELECTION ENDS");

        return objMachineTypeDetails;

    }

    public boolean updateMachineTypeDetails(MachineTypeDetails machineTypDetObj) throws SQLException, MachineException
    {
        Hashtable ht_Mc_Typ_Update = new Hashtable();
        boolean result = false;
        DBConnection con = null;
        int res = 0;


        logger.info("UPDATE MACHINE TYPE DETAILS STARTED");

        if(machineTypDetObj == null)
        {

            logger.error("MACHINE TYPE DETAILS OBJECT IS NULL");
            throw new MachineException("CMEC014","MACHINE TYPE DETAILS OBJECT IS NULL","");
        }


        if(machineTypDetObj.getMc_Typ_Name().trim().length()== 0)
        {

            logger.error("MACHINE TYPE NAME IS REQUIRED");
            throw new MachineException("CMEC015","MACHINE TYPE NAME IS REQUIRED","");
        }
        con = DBConnectionFactory.getConnection();
        ht_Mc_Typ_Update.put("MC_TYP_ID",new Integer(machineTypDetObj.getMc_Typ_Id()));
        ht_Mc_Typ_Update.put("MC_TYP_NAME",machineTypDetObj.getMc_Typ_Name());
        ht_Mc_Typ_Update.put("MC_TYP_DESC",machineTypDetObj.getMc_Typ_Desc() == null ? "" : machineTypDetObj.getMc_Typ_Desc());

        //execute the query
        try
        {
            res = con.executeUpdateStatement(SQLMaster.MACHINE_TYPE_DETAILS_UPDATE_QUERY,ht_Mc_Typ_Update);
            if(res>=1)
            {
                result = true;
            }

        }
        catch(SQLException ex)
        {
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
                System.out.println("EXCEPTION WHILE UPDATING MACHINE TYPE DETAILS");
            }

            logger.error("EXCEPTION WHILE UPDATING MACHINE TYPE DETAILS",ex);

            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(ex.toString().indexOf("unique")>=0)
                {

                    logger.error("MACHINE TYPE ALREADY EXISTS",ex);
                    throw new MachineException("MCEC003","MACHINE TYPE ALREADY EXISTS",ex.toString());
                }
                else
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(ex.toString().indexOf("Duplicate entry")>=0)
                {

                    logger.error("MACHINE TYPE ALREADY EXISTS",ex);
                    throw new MachineException("MCEC003","MACHINE TYPE ALREADY EXISTS",ex.toString());
                }
                else
                    throw new MachineException("MCEC000","GENERAL SQL ERROR",ex.toString());
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

        logger.info("UPDATE MACHINE TYPE DETAILS STARTED");

        return result;

    }
    public HashMap makeMachineTypValid(Vector ids) throws MachineException, SQLException
    {


        logger.info("MACHINE TYPE MAKE VALID STARTS");


        HashMap hm_Mc_MakeValid = new HashMap();

        if(ids == null)
        {

            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new MachineException("","VECTOR NULL IN MAKE INVALID","");
        }
        if(BuildConfig.DMODE)
            System.out.println("MAKE VALID - MACHINE TYPE");
        int result = 0;

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            //Creating the hashtable object
            Hashtable ht_Mc_MakeValid = new Hashtable();


            //Get the prepareStatement skeleton query

            for(int i=0;i<ids.size();i++)
            {
                ht_Mc_MakeValid.put("MC_TYP_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.MACHINE_TYPE_MAKE_VALID,ht_Mc_MakeValid);
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Mc_MakeValid.put(ids.get(i), new Integer(0));

                    logger.info("Record made valid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Mc_MakeValid.put(ids.get(i), new Integer(1));

                    logger.info("Record not made valid : "+ids.get(i));
                }

            }
        }
        catch(SQLException e)
        {

            logger.error("SQL ERROR", e);
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();

            }
            throw new MachineException("CMEC000","GENERAL SQL ERROR",e.toString());
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
            catch(SQLException clse)
            {

                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }


        logger.info("MACHINE TYPE MAKE VALID ENDS");

        return hm_Mc_MakeValid;
    }
    public HashMap makeMachineTypInValid(Vector ids) throws MachineException, SQLException
    {


        logger.info("MACHINE TYPE MAKE INVALID STARTS");


        HashMap hm_Mc_MakeInValid = new HashMap();

        if(ids == null)
        {

            logger.error("VECTOR NULL IN MAKE INVALID");
            throw new MachineException("","VECTOR NULL IN MAKE INVALID","");
        }
        if(BuildConfig.DMODE)
            System.out.println("MAKE INVALID - MACHINE TYPE");
        int result = 0;

        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();

            //Creating the hashtable object
            Hashtable ht_Mc_MakeInValid = new Hashtable();


            //Get the prepareStatement skeleton query

            for(int i=0;i<ids.size();i++)
            {
                ht_Mc_MakeInValid.put("MC_TYP_ID",(Integer)ids.elementAt(i));
                //Store the details from hash table to vector as objects
                result = con.executeUpdateStatement(SQLMaster.MACHINE_TYPE_MAKE_INVALID,ht_Mc_MakeInValid);
                if(result >= 1)
                {
                    con.commitTransaction();
                    hm_Mc_MakeInValid.put(ids.get(i), new Integer(0));

                    logger.info("Record made invalid : " + ids.get(i));
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Mc_MakeInValid.put(ids.get(i), new Integer(1));

                    logger.info("Record not made invalid : "+ids.get(i));
                }

            }
        }
        catch(SQLException e)
        {

            logger.error("SQL ERROR", e);
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();

            }
            throw new MachineException("CMEC000","GENERAL SQL ERROR",e.toString());
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
            catch(SQLException clse)
            {

                logger.error("CONNECTION CLOSING ERROR", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION"+clse.toString());
            }
        }


        logger.info("MACHINE TYPE MAKE INVALID ENDS");


        return hm_Mc_MakeInValid;
    }

    public static void main(String args[]) throws Exception
    {
        MachineDetailsManager macDetMgr = new MachineDetailsManager();
        //MachineTypeDetails obj = new MachineTypeDetails();
        //obj.setMc_Typ_Id(100);
        //obj.setMc_Typ_Name("Radial");
        //macDetMgr.addMachineTypeDetails(obj);
        //macDetMgr.getAllProdMachines();

        /*   MachineDetailsManager macDetMgr = new MachineDetailsManager();
         */
        //MachineTypeDetails macTypObj = new MachineTypeDetails();
        //MachineDetails macObj = new MachineDetails();
        //boolean result = false;

        HashMap hm = macDetMgr.getAllProdMachines(new Date("26-APR-2005"),125);
        System.out.println(hm);
    }

    // addMachineTypeDetails()
    /*macTypObj.setMc_Typ_Name("RADIAL");
     result = macDetMgr.addMachineTypeDetails(macTypObj);
     System.out.println("Result Of Machine Type Add :"+result);*/

    //getAllMachineTypes()
    /*HashMap hmp = macDetMgr.getAllMachineTypes();
     System.out.println("HashMap :"+hmp);*/

    //addMachineDetails()
    //macObj = null;
    /*macObj.setMach_Cde("MC1");
     macObj.setMach_Typ_Id(9);
     macObj.setMach_Install_Date("1-nov-04");
     macObj.setMach_InUse("Y");
     macObj.setMach_Name("MACH1");

     macObj.setMach_SPLR_Name("SYQD");
     macObj.setMach_SPLR_Cntct_Person("XXXX");
     macObj.setMach_SPLR_Phone("41425243");
     macObj.setMach_SPLR_Addr("QWERQW");
     macObj.setMach_SP_Name("ADFASD");

     macObj.setMach_SP_Cntct_Person("QWERQW");
     macObj.setMach_SP_Phone("23412");
     macObj.setMach_SP_Addr("ADFASF");


     result = macDetMgr.addMachineDetails(macObj);
     System.out.println("Result Of New Machine Add :"+result);*/


    //updateMachineDetails()
    //macObj = null;
    /*macObj.setMach_Cde("MC1");
     macObj.setMach_Typ_Id(1);
     macObj.setMach_Install_Date(new Date("10-nov-04"));
     macObj.setMach_InUse("Y");
     macObj.setMach_Name("MACH11");

     macObj.setMach_SPLR_Name("SUPPLIER1");
     macObj.setMach_SPLR_Cntct_Person("SWARAJ");
     macObj.setMach_SPLR_Phone("41425243");
     macObj.setMach_SPLR_Addr("TPCNE");
     macObj.setMach_SP_Name("SERVICEPROVIDER1");

     macObj.setMach_SP_Cntct_Person("RANIL");
     macObj.setMach_SP_Phone("23412");
     macObj.setMach_SP_Addr("CHEKPET");
     result = macDetMgr.updateMachineDetails(macObj);
     System.out.println("Result Of New Machine Update :"+result);*/

    //getMachineDetails()
    /*macObj = macDetMgr.getMachineDetails("MC1");
     System.out.println(macObj.getMach_Install_Date());*/

    //getAllMachines()
    /*HashMap hm = macDetMgr.getAllMachines();
     System.out.println("HashMap :"+hm);*/

    //makeMachinesValid()
    /*Vector vec = new Vector();
     vec.addElement("MC1");
     HashMap hm = macDetMgr.makeMachinesValid(vec);
     System.out.println("HashMap :"+hm);*/

    //makeMachinesInValid()
    /*	Vector vec = new Vector();
     vec.addElement("MC1");
     HashMap hm = macDetMgr.makeMachinesInValid(vec);
     System.out.println("HashMap :"+hm);*/

    /*			//getAllMachineDetails()
     z fil = new Filter[1];
     fil[0] = new Filter();
     fil[0].setFieldName("MC_TYP_NAME");
     fil[0].setFieldValue("NEWMACHINE");
     fil[0].setSpecialFunction("");
     int startIndex = 1;
     int displayCount = 5;
     boolean ascending = true;
     String sortBy = "MC_NAME";
     HashMap hmp = macDetMgr.getAllMachineDetails(fil,sortBy,ascending,startIndex,displayCount);


     }*/
}

/***
$Log: MachineDetailsManager.java,v $
Revision 1.52  2005/09/15 07:36:10  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.51  2005/09/10 13:45:43  kduraisamy
order by clause added.

Revision 1.50  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.49  2005/08/12 10:42:12  kduraisamy
unused varibles commented.

Revision 1.48  2005/07/12 11:37:01  kduraisamy
imports organized

Revision 1.47  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.46  2005/05/18 09:58:31  kduraisamy
indentation.

Revision 1.45  2005/05/16 18:34:54  kduraisamy
specific throws addded for mysql.

Revision 1.44  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.43  2005/05/16 09:44:00  kduraisamy
description added for mc type list..

Revision 1.42  2005/05/14 06:58:14  kduraisamy
GETALLMACHINES WITH ACCOUNTING.METHOD ADDEDE.

Revision 1.41  2005/04/27 15:48:28  kduraisamy
getAllRadlMachines(Date,shift_id) added.

Revision 1.40  2005/04/27 10:11:21  kduraisamy
GETALLPRODMACHINES() ADDED.

Revision 1.39  2005/04/25 05:01:49  kduraisamy
con!= null condition checking added in finally.

Revision 1.38  2005/04/19 13:13:33  kduraisamy
resultset properly closed.

Revision 1.37  2005/04/18 12:27:50  kduraisamy
executeStatement() return type changed.

Revision 1.36  2005/04/07 09:20:11  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.35  2005/04/07 07:35:53  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.34  2005/03/31 09:37:23  kduraisamy
MC TYPE INVALID QUERY CALLED CORRECTLY.ERROR CORRECTED.

Revision 1.33  2005/03/30 08:23:21  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.32  2005/03/26 10:08:39  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.31  2005/03/18 10:25:42  smurugesan
id select resultset rs.getInt(1) added.

Revision 1.30  2005/03/18 07:16:51  smurugesan
con.startTransaction() added.

Revision 1.29  2005/03/14 09:01:28  sponnusamy
Changes made

Revision 1.28  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.27  2005/03/08 06:12:34  kduraisamy
DESC ADDED IN MCTYPE ADD.

Revision 1.26  2005/03/08 05:51:53  kduraisamy
machine type make valid and invalid added.

Revision 1.25  2005/03/08 05:36:18  kduraisamy
updateMachineTypeDetails() added.

Revision 1.24  2005/03/08 05:15:57  kduraisamy
getMachineTypeDetails() added.

Revision 1.23  2005/03/06 09:06:29  kduraisamy
Machine type filter added.

Revision 1.22  2005/03/04 08:15:22  kduraisamy
DBConnection Changes made.

Revision 1.21  2005/03/02 11:37:46  kduraisamy
HashMap replaced with LinkedHashMap().

Revision 1.20  2005/02/28 04:56:18  kduraisamy
LinkedHashMap is added for HashMap.

Revision 1.19  2005/02/17 06:05:39  kduraisamy
signature modified for addNewMachineTypeDetails().

Revision 1.18  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.17  2005/02/11 06:54:56  kduraisamy
unused variables removed.

Revision 1.16  2005/01/31 05:39:57  kduraisamy
getAllProdMachines() added.

Revision 1.15  2005/01/27 14:44:50  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.14  2005/01/25 12:16:36  kduraisamy
value taken from resultSet inserted inside the try.

Revision 1.13  2005/01/25 07:51:47  kduraisamy
getAllRadialMachines() added.

Revision 1.12  2004/12/04 10:23:33  kduraisamy
unwanted dutil object removed

Revision 1.11  2004/12/03 05:32:14  sduraisamy
Exception Codes Changed

Revision 1.10  2004/12/01 14:20:20  sduraisamy
all commented lines removed(unwanted code)

Revision 1.9  2004/11/20 06:41:36  sduraisamy
hm_Result.put(machineCodes.get(i),new Integer(2)) included in makeValid and Invalid methods

Revision 1.8  2004/11/19 14:42:33  sduraisamy
Vector object type casted to String in Valid and Invalid methods

Revision 1.7  2004/11/19 09:46:52  sduraisamy
rs.close() and ps.close() removed

Revision 1.6  2004/11/19 05:30:21  sduraisamy
MC_TYP_ID set in Filter method

Revision 1.5  2004/11/10 12:35:33  sduraisamy
filter method modified for Query

Revision 1.4  2004/11/09 10:44:46  sduraisamy
getMachineDetails modified for DateStamp and MachineTypeId inclusion in object

Revision 1.3  2004/11/09 04:52:10  kduraisamy
Log added

Revision 1.2  2004/11/06 07:40:32  sduraisamy
Machine module modified for Log inclusion and change in Machine manager filter method

***/



	/*ht_Mach_Det_Add.put("MACH_INSTALLDATE",java.sql.Date.valueOf(dutil.sqlDate(objMachineDetails.getMach_Install_Date()))== null ? dbNull:java.sql.Date.valueOf(dutil.sqlDate(objMachineDetails.getMach_Install_Date())));*/




