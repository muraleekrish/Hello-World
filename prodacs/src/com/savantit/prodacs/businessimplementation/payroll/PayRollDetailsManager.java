/*
 * Created on Jan 15, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.payroll;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.nfunk.jep.JEP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;



/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayRollDetailsManager
{


    static Logger logger = Logger.getLogger(PayRollDetailsManager.class);
    public PayRollDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }


    /**
     * @param cycleType
     * @param cycle
     * @return
     *//*
     public boolean createNewPayrollCycle(String cycleType,String cycle)
     {
     boolean addRESULT = false;
     DBConnection con = null;
     Scheduler objSheduler = new Scheduler();
     Hashtable ht_PyrlCycle = new Hashtable();
     try
     {
     if(BuildConfig.DMODE)
     System.out.println("starting of createNewPayrollCycle");
     con = DBConnectionFactory.getConnection();
     ht_PyrlCycle.put("CYCLE_TYPE",cycleType);
     ht_PyrlCycle.put("CYCLE",cycle);
     int result = con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_TYPE_UPDATE_SQL_QUERY,ht_PyrlCycle);
     if(result>0)
     {
     addRESULT = true;
     if(cycleType.equalsIgnoreCase("month"))
     {
     if(BuildConfig.DMODE)
     System.out.println("before calling CreateMonthlyTimer method in update");
     objSheduler.cancelMonthlyTimer();
     objSheduler.cancelWeeklyTimer();
     objSheduler.updateMonthlyTimer(Integer.parseInt(cycle));
     }
     if(cycleType.equalsIgnoreCase("week"))
     {
     objSheduler.cancelMonthlyTimer();
     objSheduler.cancelWeeklyTimer();
     objSheduler.updateWeeklyTimer(new Date());
     }
     }
     else
     {
     result = con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_TYPE_ADD_SQL_QUERY,ht_PyrlCycle);
     if(result>0)
     {
     if(cycleType.equalsIgnoreCase("month"))
     {
     if(BuildConfig.DMODE)
     System.out.println("before calling CreateMonthlyTimer method in add");
     objSheduler.cancelMonthlyTimer();
     objSheduler.cancelWeeklyTimer();
     objSheduler.createMonthlyTimer(Integer.parseInt(cycle));
     }
     if(cycleType.equalsIgnoreCase("week"))
     {
     objSheduler.cancelMonthlyTimer();
     objSheduler.cancelWeeklyTimer();
     objSheduler.createWeeklyTimer(new Date());
     }
     addRESULT = true;

     }
     }
     }
     catch(SQLException sqle)
     {
     if(BuildConfig.DMODE)
     {
     sqle.printStackTrace();
     }
     }
     return addRESULT;
     }
     public boolean createNewPayrollCycleStat()
     {

     boolean createRESULT = false;
     PreparedStatement ps = null;
     Hashtable ht_PyrlCycle = new Hashtable();
     DBConnection con = null;
     String pyrlCycleType = "";
     String pyrlCycle = "";
     int month = 0;
     int year = 0;
     int date = 0;
     Date endDate = null;

     Date startDate = null;
     try
     {
     con = DBConnectionFactory.getConnection();
     //to check the payroll cycle type..
      ps = con.executeStatement(SQLMaster.PAYROLL_CYCLE_TYPE_SELECT_SQL_QUERY);
      ResultSet rs_PyrlCycle = ps.executeQuery();
      if(rs_PyrlCycle.next())
      {
      pyrlCycleType = rs_PyrlCycle.getString("CYCLE_TYPE");
      pyrlCycle = rs_PyrlCycle.getString("CYCLE");
      }
      if(pyrlCycleType.equalsIgnoreCase("month"))
      {

      date = Integer.parseInt(pyrlCycle);
      GregorianCalendar gc = new GregorianCalendar();
      month = gc.get(GregorianCalendar.MONTH);
      year = gc.get(GregorianCalendar.YEAR);
      GregorianCalendar gc1 =new GregorianCalendar(year,month,date);
      startDate = gc1.getTime();
      switch(date)
      {
      case 1:
      date = gc1.getMaximum(GregorianCalendar.DAY_OF_MONTH);
      gc1 = new GregorianCalendar(year,month,date);
      break;

      case 30:
      case 31:
      if(month==0)
      {
      gc1.add(GregorianCalendar.MONTH,1);

      }
      else
      {
      gc1 = new GregorianCalendar(year,month+1,date-1);

      }
      break;
      default:
      gc1 = new GregorianCalendar(year,month+1,date-1);

      createRESULT = true;
      }

      endDate = gc1.getTime();
      }
      if(pyrlCycleType.equalsIgnoreCase("Week"))
      {
      GregorianCalendar gc = new GregorianCalendar();
      startDate = gc.getTime();
      gc.add(GregorianCalendar.DAY_OF_WEEK,6);
      endDate = gc.getTime();
      createRESULT = true;
      }
      if(pyrlCycleType.equalsIgnoreCase("day"))
      {
      GregorianCalendar gc = new GregorianCalendar();
      startDate = endDate = gc.getTime();
      createRESULT = true;
      }
      if(pyrlCycleType.equalsIgnoreCase("By week"))
      {
      createRESULT = true;
      }
      if(pyrlCycleType.equalsIgnoreCase("fortnight"))
      {
      createRESULT = true;
      }

      //to store the startDate and endDate into PYRL_CYCLE_STAT table..

       ht_PyrlCycle.put("FROM_DATE",startDate);
       ht_PyrlCycle.put("TO_DATE",endDate);
       con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_ADD_SQL_QUERY,ht_PyrlCycle);

       System.out.println(startDate);
       System.out.println(endDate);

       rs_PyrlCycle.close();
       ps.close();
       }
       catch(SQLException sqle)
       {
       if(BuildConfig.DMODE)
       {
       sqle.printStackTrace();
       }
       }
       return createRESULT;

       }
       *//**
       * @param cycleType
       * @param cycle
       * @return
       * @throws SQLException
       * @throws PayRollException
       */
    public boolean createNewPayrollCycle(String cycleType,String cycle) throws SQLException, PayRollException
    {
        boolean addRESULT = false;
        DBConnection con = null;
        //Scheduler objSheduler = new Scheduler();
        Hashtable ht_PyrlCycle = new Hashtable();
        try
        {
            if(BuildConfig.DMODE)
                System.out.println("starting of createNewPayrollCycle");
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            ht_PyrlCycle.put("CYCLE_TYPE",cycleType);
            ht_PyrlCycle.put("CYCLE",cycle);
            int result = con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_TYPE_UPDATE_SQL_QUERY,ht_PyrlCycle);
            if(result>0)
            {
                addRESULT = true;
                con.commitTransaction();
            }
            else
            {
                result = con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_TYPE_ADD_SQL_QUERY,ht_PyrlCycle);
                if(result>0)
                {
                    addRESULT = true;
                    con.commitTransaction();
                }
            }
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return addRESULT;
    }
    public HashMap getCurrentPayrollCycle() throws SQLException, PayRollException
    {
        DBConnection con = null;
        PreparedStatement ps = null;
        String pyrlCycleType = "";
        String pyrlCycle = "";
        HashMap hm = new HashMap();
        try
        {
            con = DBConnectionFactory.getConnection();
            //to check the payroll cycle type..
            ps = con.executeStatement(SQLMaster.PAYROLL_CYCLE_TYPE_SELECT_SQL_QUERY);
            ResultSet rs_PyrlCycle = ps.executeQuery();
            if(rs_PyrlCycle.next())
            {
                pyrlCycleType = rs_PyrlCycle.getString("CYCLE_TYPE");
                pyrlCycle = rs_PyrlCycle.getString("CYCLES");
            }

            rs_PyrlCycle.close();
            ps.close();
            hm.put(pyrlCycleType,pyrlCycle);

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return hm;


    }
    public boolean createNewPayrollCycleStat() throws PayRollException, SQLException, ParseException
    {

        boolean createRESULT = false;
        PreparedStatement ps = null;
        Hashtable ht_PyrlCycle = new Hashtable();
        DBConnection con = null;
        String pyrlCycleType = "";
        String pyrlCycle = "";
        int month = 0;
        int year = 0;
        int date = 0;
        Date endDate = null;
        Date startDate = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            //to check the payroll cycle type..
            ps = con.executeStatement(SQLMaster.PAYROLL_CYCLE_TYPE_SELECT_SQL_QUERY);
            ResultSet rs_PyrlCycle = ps.executeQuery();
            if(rs_PyrlCycle.next())
            {
                pyrlCycleType = rs_PyrlCycle.getString("CYCLE_TYPE");
                pyrlCycle = rs_PyrlCycle.getString("CYCLES");
            }
            if(pyrlCycleType.equalsIgnoreCase("month"))
            {
                date = Integer.parseInt(pyrlCycle);
                GregorianCalendar gc = new GregorianCalendar();
                month = gc.get(GregorianCalendar.MONTH);
                year = gc.get(GregorianCalendar.YEAR);
                GregorianCalendar gc1 =new GregorianCalendar(year,month,date);
                startDate = gc1.getTime();
                gc1.add(GregorianCalendar.MONTH,1);
                gc1.add(GregorianCalendar.DAY_OF_MONTH,-1);
                createRESULT = true;
                endDate = gc1.getTime();
            }
            if(pyrlCycleType.equalsIgnoreCase("Week"))
            {
                GregorianCalendar gc = new GregorianCalendar();
                startDate = gc.getTime();
                gc.add(GregorianCalendar.DAY_OF_WEEK,6);
                endDate = gc.getTime();
                createRESULT = true;
            }
            if(pyrlCycleType.equalsIgnoreCase("day"))
            {
                GregorianCalendar gc = new GregorianCalendar();
                startDate = endDate = gc.getTime();
                createRESULT = true;
            }
            if(pyrlCycleType.equalsIgnoreCase("By week"))
            {
                createRESULT = true;
            }
            if(pyrlCycleType.equalsIgnoreCase("fortnight"))
            {
                createRESULT = true;
            }

            //to check wheather PyrlId generated for this date or not ..

            PreparedStatement ps1 = con.executeStatement(SQLMaster.PYRL_ID_INFO_SELECT_SQL_QUERY);
            ResultSet rs1 = ps1.executeQuery();
            Date fromDate = null;
            Date toDate = null;

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");

            startDate = sf.parse(sf.format(startDate));

            endDate = sf.parse(sf.format(endDate));


            while(rs1.next())
            {
                fromDate = new Date(rs1.getTimestamp("PYRL_CYCLE_STAT_FROM_DATE").getTime());
                toDate = new Date(rs1.getTimestamp("PYRL_CYCLE_STAT_TO_DATE").getTime());

                fromDate = sf.parse(sf.format(fromDate));
                toDate = sf.parse(sf.format(toDate));

                if(BuildConfig.DMODE)
                {
                    System.out.println("fromDate:"+fromDate);
                    System.out.println("toDate:"+toDate);
                    System.out.println("startDate:"+startDate);
                    System.out.println("endDate:"+endDate);
                }


                if(startDate.compareTo(fromDate)>=0 && startDate.compareTo(toDate)<=0 || endDate.compareTo(fromDate)>=0 && endDate.compareTo(toDate)<=0)
                {
                    throw new PayRollException("","PYROLL ID ALREADY GENERATED","");
                }
            }

            rs1.close();
            ps1.close();

            //to store the startDate and endDate into PYRL_CYCLE_STAT table..

            if(BuildConfig.DMODE)
            {
                System.out.println("startDate:"+startDate);
                System.out.println("endDate:"+endDate);

            }

            ht_PyrlCycle.put("FROM_DATE",startDate);
            ht_PyrlCycle.put("TO_DATE",endDate);
            con.executeUpdateStatement(SQLMaster.PAYRL_CYCLE_ADD_SQL_QUERY,ht_PyrlCycle);
            rs_PyrlCycle.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return createRESULT;

    }

    public HashMap getAllPyrlCycleStatForInterface() throws SQLException, PayRollException
    {
        DBConnection con = null;
        HashMap hm_PyrlCycleStat = new HashMap();
        Date fromDate = null;
        Date toDate = null;
        String dateString = "";
        PreparedStatement ps = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            ps = con.executeStatement(SQLMaster.GET_ALL_PAYROLL_CYCLE_STAT);
            ResultSet rs_PyrlCycleStat = ps.executeQuery();

            while(rs_PyrlCycleStat.next())
            {
                fromDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_FROM_DATE");
                toDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_TO_DATE");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateString = sdf.format(fromDate) +"-"+sdf.format(toDate);
                hm_PyrlCycleStat.put(new Integer(rs_PyrlCycleStat.getInt("PYRL_CYCLE_STAT_ID")),dateString);
            }
            rs_PyrlCycleStat.close();
            ps.close();


        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return hm_PyrlCycleStat;
    }
    public HashMap getAllPyrlCycleStatForPayroll() throws SQLException, PayRollException
    {
        DBConnection con = null;
        HashMap hm_PyrlCycleStat = new HashMap();
        PreparedStatement ps = null;
        Date fromDate = null;
        Date toDate = null;
        String dateString = "";
        try
        {
            con = DBConnectionFactory.getConnection();
            ps = con.executeStatement(SQLMaster.GET_ALL_PAYROLL_CYCLE_STAT_FOR_PAYROLL);
            ResultSet rs_PyrlCycleStat = ps.executeQuery();

            while(rs_PyrlCycleStat.next())
            {
                fromDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_FROM_DATE");
                toDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_TO_DATE");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateString = sdf.format(fromDate) +"-"+sdf.format(toDate);
                hm_PyrlCycleStat.put(new Integer(rs_PyrlCycleStat.getInt("PYRL_CYCLE_STAT_ID")),dateString);
            }
            rs_PyrlCycleStat.close();
            ps.close();


        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return hm_PyrlCycleStat;
    }

    public HashMap getClosedPyrlCycleStatForPayroll() throws SQLException, PayRollException
    {
        DBConnection con = null;
        HashMap hm_PyrlCycleStat = new HashMap();
        PreparedStatement ps = null;
        Date fromDate = null;
        Date toDate = null;
        String dateString = "";
        try
        {
            con = DBConnectionFactory.getConnection();
            ps = con.executeStatement(SQLMaster.GET_CLOSED_PAYROLL_CYCLE_STAT_FOR_PAYROLL);
            ResultSet rs_PyrlCycleStat = ps.executeQuery();

            while(rs_PyrlCycleStat.next())
            {
                fromDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_FROM_DATE");
                toDate = rs_PyrlCycleStat.getTimestamp("PYRL_CYCLE_STAT_TO_DATE");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dateString = sdf.format(fromDate) +"-"+sdf.format(toDate);
                hm_PyrlCycleStat.put(new Integer(rs_PyrlCycleStat.getInt("PYRL_CYCLE_STAT_ID")),dateString);
            }
            rs_PyrlCycleStat.close();
            ps.close();


        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return hm_PyrlCycleStat;
    }

    public Vector getEmployeeDetails(int payrlStatId,int val) throws SQLException, PayRollException
    {
        Vector vec = new Vector();
        switch(val)
        {
        case 1:
            vec = getPayRollEmployeeDetails(payrlStatId);
            break;
        case 2:
            vec = getPayRollDateShiftDetails(payrlStatId);
            break;

        }

        return vec;
    }
    public Vector getPayRollEmployeeDetails(int payrlStatId) throws SQLException, PayRollException
    {
        DBConnection con = null;
        Vector vec = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            vec = this.getPayRollEmployeeDetails(payrlStatId, con);
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            throw sqle;
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        return  vec;
    }
    
    public Vector getPayRollEmployeeDetails(int payrlStatId, DBConnection con) throws SQLException, PayRollException
    {        

        Hashtable ht_pyrlStatId = new Hashtable();
        //Hashtable ht_EmpHrs = new Hashtable();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        int empId = 0;
        Vector vec_EmployeeDet = new Vector();
        try
        {
            ht_pyrlStatId.put("PYRL_CYCLE_STAT_ID",new Integer(payrlStatId));
            ps = con.executeStatement(SQLMaster.PYRL_EMPLOYEE_DETAILS_SELECT_SQL_QUERY,ht_pyrlStatId);
            ResultSet rs_PyrlCycle_Get = ps.executeQuery();

            while(rs_PyrlCycle_Get.next())
            {
                PayRollEmployeeDetails objPayRollEmployeeDetails = new PayRollEmployeeDetails();
                empId = rs_PyrlCycle_Get.getInt("EMP_ID");

                objPayRollEmployeeDetails.setEmpId(empId);
                objPayRollEmployeeDetails.setEmpName(rs_PyrlCycle_Get.getString("EMP_NAME"));
                objPayRollEmployeeDetails.setTotDtySlryHrs(rs_PyrlCycle_Get.getFloat("DT_SLRY_HRS"));
                objPayRollEmployeeDetails.setTotOtSlryHrs(rs_PyrlCycle_Get.getFloat("OT_SLRY_HRS"));
                objPayRollEmployeeDetails.setTotIncntvSlryHrs(rs_PyrlCycle_Get.getFloat("INCNTV_SLRY_HRS"));
                objPayRollEmployeeDetails.setTotDtyHrs(rs_PyrlCycle_Get.getFloat("DT_HRS"));
                objPayRollEmployeeDetails.setTotOtHrs(rs_PyrlCycle_Get.getFloat("OT_HRS"));
                ht_pyrlStatId.put("EMP_ID",new Integer(empId));
                ps1 = con.executeStatement(SQLMaster.PYRL_EMPLOYEE_HRS_DETAILS_SELECT_SQL_QUERY,ht_pyrlStatId);
                ResultSet rs_PyrlEmpHrs_Get = ps1.executeQuery();
                Vector vec_EmpHrs = new Vector();
                while(rs_PyrlEmpHrs_Get.next())
                {
                    DateShiftEmpHrsDetails objDateShiftEmpHrsDetails = new DateShiftEmpHrsDetails();
                    objDateShiftEmpHrsDetails.setDataSource(rs_PyrlEmpHrs_Get.getString("SOURCE"));
                    objDateShiftEmpHrsDetails.setProdDate(rs_PyrlEmpHrs_Get.getTimestamp("PROD_CRNT_DATE"));
                    objDateShiftEmpHrsDetails.setShiftId(rs_PyrlEmpHrs_Get.getInt("SHIFT_ID"));
                    objDateShiftEmpHrsDetails.setShiftName(rs_PyrlEmpHrs_Get.getString("SHIFT_NAME"));
                    objDateShiftEmpHrsDetails.setDtySlryHrs(rs_PyrlEmpHrs_Get.getFloat("DT_SLRY_HRS"));
                    objDateShiftEmpHrsDetails.setDtyHrs(rs_PyrlEmpHrs_Get.getFloat("DT_HRS"));
                    objDateShiftEmpHrsDetails.setOtSlryHrs(rs_PyrlEmpHrs_Get.getFloat("OT_SLRY_HRS"));
                    objDateShiftEmpHrsDetails.setOtHrs(rs_PyrlEmpHrs_Get.getFloat("OT_HRS"));
                    objDateShiftEmpHrsDetails.setIncntvSlryHrs(rs_PyrlEmpHrs_Get.getFloat("INCNTV_SLRY_HRS"));
                    vec_EmpHrs.add(objDateShiftEmpHrsDetails);
                }
                objPayRollEmployeeDetails.setDateShiftEmpHrsDetails(vec_EmpHrs);
                vec_EmployeeDet.add(objPayRollEmployeeDetails);
                rs_PyrlEmpHrs_Get.close();
                ps1.close();
            }
            rs_PyrlCycle_Get.close();
            ps.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

        }

        return vec_EmployeeDet;
    
    }
    
    /*public boolean isAllEntriesPosted(int payrlStatId) throws PayRollException, SQLException
    {
        boolean postRESULT = true;
        DBConnection con = null;
        Hashtable ht_pyrlStatId = new Hashtable();
        PreparedStatement ps = null;
        int count = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_pyrlStatId.put("PYRL_CYCLE_STAT_ID",new Integer(payrlStatId));
            ps = con.executeStatement(SQLMaster.CHECK_ALL_ENTRIES_POSTED_SQL_QUERY,ht_pyrlStatId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                count = rs.getInt(1);
            }
            if(count>0)
            {
                postRESULT = false;
            }


        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        return postRESULT;

    }*/
    public  Vector getPayRollDateShiftDetails(int payrlStatId) throws SQLException, PayRollException
    {
        Hashtable ht_pyrlStatId = new Hashtable();
        Hashtable ht_EmpHrs = new Hashtable();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        DBConnection con = null;
        Date prodDate = null;
        int shiftId = 0;

        Vector vec_DateShiftDet = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_pyrlStatId.put("PYRL_CYCLE_STAT_ID",new Integer(payrlStatId));
            ps = con.executeStatement(SQLMaster.PYRL_EMPLOYEE_DATE_SHIFT_DETAILS_SELECT_SQL_QUERY,ht_pyrlStatId);
            ResultSet rs_PyrlCycle_Get = ps.executeQuery();

            while(rs_PyrlCycle_Get.next())
            {
                PayRollDateShiftDetails objPayRollDateShiftDetails = new PayRollDateShiftDetails();
                shiftId = rs_PyrlCycle_Get.getInt("SHIFT_ID");
                prodDate = rs_PyrlCycle_Get.getTimestamp("PROD_CRNT_DATE");
                objPayRollDateShiftDetails.setProdDate(prodDate);
                objPayRollDateShiftDetails.setShiftName(rs_PyrlCycle_Get.getString("SHIFT_NAME"));
                objPayRollDateShiftDetails.setTotShiftDtySlryHrs(rs_PyrlCycle_Get.getFloat("DT_SLRY_HRS"));
                objPayRollDateShiftDetails.setTotShiftOtSlryHrs(rs_PyrlCycle_Get.getFloat("OT_SLRY_HRS"));
                objPayRollDateShiftDetails.setTotShiftIncntvSlryHrs(rs_PyrlCycle_Get.getFloat("INCNTV_SLRY_HRS"));
                objPayRollDateShiftDetails.setTotShiftDtyHrs(rs_PyrlCycle_Get.getFloat("DT_HRS"));
                objPayRollDateShiftDetails.setTotShiftOtHrs(rs_PyrlCycle_Get.getFloat("OT_HRS"));

                ht_EmpHrs.put("SHIFT_ID",new Integer(shiftId));
                ht_EmpHrs.put("PROD_CRNT_DATE",prodDate);
                ps1 = con.executeStatement(SQLMaster.PYRL_EMPLOYEE_HRS_FOR_DATE_SELECT_SQL_QUERY,ht_EmpHrs);
                ResultSet rs_PyrlEmpHrs_Get = ps1.executeQuery();
                Vector vec_EmpHrs = new Vector();
                while(rs_PyrlEmpHrs_Get.next())
                {
                    EmployeeEmpHrsDetails objEmployeeEmpHrsDetails = new EmployeeEmpHrsDetails();
                    objEmployeeEmpHrsDetails.setDataSource(rs_PyrlEmpHrs_Get.getString("SOURCE"));
                    objEmployeeEmpHrsDetails.setEmpName(rs_PyrlEmpHrs_Get.getString("EMP_NAME"));
                    objEmployeeEmpHrsDetails.setDtySlryHrs(rs_PyrlEmpHrs_Get.getFloat("DT_SLRY_HRS"));
                    objEmployeeEmpHrsDetails.setDtyHrs(rs_PyrlEmpHrs_Get.getFloat("DT_HRS"));
                    objEmployeeEmpHrsDetails.setOtSlryHrs(rs_PyrlEmpHrs_Get.getFloat("OT_SLRY_HRS"));
                    objEmployeeEmpHrsDetails.setOtHrs(rs_PyrlEmpHrs_Get.getFloat("OT_HRS"));
                    objEmployeeEmpHrsDetails.setIncntvSlryHrs(rs_PyrlEmpHrs_Get.getFloat("INCNTV_SLRY_HRS"));
                    vec_EmpHrs.add(objEmployeeEmpHrsDetails);
                }
                objPayRollDateShiftDetails.setEmployeeEmpHrsDetails(vec_EmpHrs);
                vec_DateShiftDet.add(objPayRollDateShiftDetails);
                rs_PyrlEmpHrs_Get.close();
                ps1.close();
            }
            rs_PyrlCycle_Get.close();
            ps.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return vec_DateShiftDet;
    }
    public HashMap getForPayRoll(int pyrlCycleStatId,int startIndex,int displayCnt) throws SQLException, PayRollException
    {
        DBConnection con = null;
        Hashtable ht_StatId = new Hashtable();
        Hashtable ht_PrPyrlId = new Hashtable();
        PreparedStatement ps = null;
        PreparedStatement ps1 =  null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        Vector vec_EmpDet = new Vector();
        HashMap hm_Result = new HashMap();
        int tot_Rec_Cnt = 0;
        int count = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_StatId.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));
            ht_StatId.put("START_INDEX",new Integer(startIndex));
            ht_StatId.put("DISP_COUNT",new Integer(displayCnt));


            ps = con.executeStatement(SQLMaster.GET_FOR_PYRL_SQL_QUERY,ht_StatId);
            ResultSet rs_PyrlDet = ps.executeQuery();
            ps1 = con.executeStatement(SQLMaster.GET_COUNT_FOR_PYRL_SQL_QUERY,ht_StatId);
            ResultSet rs_Cnt_Select = ps1.executeQuery();
            if(rs_Cnt_Select.next())
            {
                tot_Rec_Cnt = rs_Cnt_Select.getInt(1);
            }
            while(rs_PyrlDet.next())
            {
                PayRollDetails objPayRollDetails = new PayRollDetails();
                objPayRollDetails.setPrePyrlId(rs_PyrlDet.getInt("PREPYRL_ID"));
                objPayRollDetails.setProdDate(rs_PyrlDet.getTimestamp("PROD_CRNT_DATE"));
                objPayRollDetails.setShiftName(rs_PyrlDet.getString("SHIFT_NAME"));
                objPayRollDetails.setEmpName(rs_PyrlDet.getString("EMP_NAME"));
                objPayRollDetails.setDtyHrs(rs_PyrlDet.getFloat("PREPYRL_DT_HRS"));
                objPayRollDetails.setOtHrs(rs_PyrlDet.getFloat("PREPYRL_OT_HRS"));
                ht_PrPyrlId.put("PREPYRL_ID",new Integer(objPayRollDetails.getPrePyrlId()));
                ps2 = con.executeStatement(SQLMaster.PYRL_ID_CHECK_SQL_QUERY,ht_PrPyrlId);
                ResultSet rs_PyrlId_Check = ps2.executeQuery();
                if(rs_PyrlId_Check.next())
                {
                    count = rs_PyrlId_Check.getInt(1);
                }
                rs_PyrlId_Check.close();
                ps2.close();
                //if it is not in PYRL_ADJSTMNT..
                if(count<=0)
                {
                    objPayRollDetails.setDtySlryHrs(rs_PyrlDet.getFloat("PREPYRL_RGLRSLRY_HRS"));
                    objPayRollDetails.setOtSlryHrs(rs_PyrlDet.getFloat("PREPYRL_OTSLRY_HRS"));
                    objPayRollDetails.setIncntvSlryHrs(rs_PyrlDet.getFloat("PREPYRL_INCNTVSLRY_HRS"));

                }
                else
                {
                    ps3 = con.executeStatement(SQLMaster.PYRL_HRS_FROM_ADJSTMNT_SELECT_SQL_QUERY,ht_PrPyrlId);
                    ResultSet rs_PyrlHrs_Select = ps3.executeQuery();
                    if(rs_PyrlHrs_Select.next())
                    {
                        objPayRollDetails.setDtySlryHrs(rs_PyrlHrs_Select.getFloat("PA_CHNGD_RGLRSLRY_HRS"));
                        objPayRollDetails.setOtSlryHrs(rs_PyrlHrs_Select.getFloat("PA_CHNGD_OTSLRY_HRS"));
                        objPayRollDetails.setIncntvSlryHrs(rs_PyrlHrs_Select.getFloat("PA_CHNGD_INCNTVSLRY_HRS"));
                    }
                    rs_PyrlHrs_Select.close();
                    ps3.close();
                }
                vec_EmpDet.add(objPayRollDetails);
            }
            rs_PyrlDet.close();
            ps.close();
            rs_Cnt_Select.close();
            ps1.close();


        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
        hm_Result.put("PayrollDetails",vec_EmpDet);

        return hm_Result;
    }

    public HashMap getForClosePayRoll(int pyrlCycleStatId,int startIndex,int displayCnt) throws SQLException, PayRollException
    {
        DBConnection con = null;
        Hashtable ht_StatId = new Hashtable();
        Vector vec_EmpDet = new Vector();
        HashMap hm_Result = new HashMap();
        PreparedStatement ps_PyrlDet = null;
        PreparedStatement ps_Cnt_Select = null;
        int tot_Rec_Cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_StatId.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));
            ht_StatId.put("START_INDEX",new Integer(startIndex));
            ht_StatId.put("DISP_COUNT",new Integer(displayCnt));



            ps_PyrlDet = con.executeStatement(SQLMaster.GET_FOR_CLOSE_PYRL_SQL_QUERY,ht_StatId);
            ResultSet rs_PyrlDet = ps_PyrlDet.executeQuery();

            ps_Cnt_Select = con.executeStatement(SQLMaster.GET_COUNT_FOR_CLOSE_PYRL_SQL_QUERY,ht_StatId);
            ResultSet rs_Cnt_Select = ps_Cnt_Select.executeQuery();
            if(rs_Cnt_Select.next())
            {
                tot_Rec_Cnt = rs_Cnt_Select.getInt(1);
            }
            while(rs_PyrlDet.next())
            {
                PayRollDetails objPayRollDetails = new PayRollDetails();
                objPayRollDetails.setPrePyrlId(rs_PyrlDet.getInt("PREPYRL_ID"));
                objPayRollDetails.setProdDate(rs_PyrlDet.getTimestamp("PROD_CRNT_DATE"));
                objPayRollDetails.setShiftName(rs_PyrlDet.getString("SHIFT_NAME"));
                objPayRollDetails.setEmpName(rs_PyrlDet.getString("EMP_NAME"));
                objPayRollDetails.setDtyHrs(rs_PyrlDet.getFloat("PYRL_DT_HRS"));
                objPayRollDetails.setOtHrs(rs_PyrlDet.getFloat("PYRL_OT_HRS"));
                objPayRollDetails.setDtySlryHrs(rs_PyrlDet.getFloat("PYRL_RGLRSLRY_HRS"));
                objPayRollDetails.setOtSlryHrs(rs_PyrlDet.getFloat("PYRL_OTSLRY_HRS"));
                objPayRollDetails.setIncntvSlryHrs(rs_PyrlDet.getFloat("PYRL_INCNTVSLRY_HRS"));
                objPayRollDetails.setNoOfTimesAdjstd(rs_PyrlDet.getInt("PA_COUNT"));
                vec_EmpDet.add(objPayRollDetails);
            }

            rs_PyrlDet.close();
            ps_PyrlDet.close();
            rs_Cnt_Select.close();
            ps_Cnt_Select.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
        hm_Result.put("PayrollDetails",vec_EmpDet);

        return hm_Result;
    }

    
    /*private boolean calculateSalHrs(int pyrlCycleStatId,DBConnection con) throws SAXException, IOException, ParserConfigurationException, ProductionException, SQLException
    {

        int prodId = 0;
        int radlId = 0;
        int nprodId = 0;
        int popId = 0;
        int empId = 0;
        String empTypeincentive = "";
        String empTypeDt = "";
        String empTypeOt = "";
        String prodWorkType = "";
        String prodIncentive = "";
        float prodTotHrs = 0;
        float prodStdHrs = 0;
        String prodQtySnos = "";
        int prodStartOpn = 0;
        int prodEndOpn = 0;
        int woJbId = 0;
        float otSlryHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        float otHrs = 0;
        float dtHrs = 0;
        int currentProdId = 0;
        


       

        Hashtable ht_PyrlId = new Hashtable();
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        ht_PyrlId.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));


        //to calculate prod salary Hrs.
        PreparedStatement ps_Prod = con.executeStatement(SQLMaster.PROD_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Prod = ps_Prod.executeQuery();

        while(rs_Prod.next())
        {
            prodId = rs_Prod.getInt("PROD_ID");
            ht_PyrlId.put("PROD_ID",new Integer(prodId));
            PreparedStatement ps_ProdEmpDet = con.executeStatement(SQLMaster.PROD_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_ProdEmpDet = ps_ProdEmpDet.executeQuery();
            while(rs_ProdEmpDet.next())
            {
                otSlryHrs = 0;
                dtySlryHrs = 0;
                incntvSlryHrs = 0;
                empId = rs_ProdEmpDet.getInt("EMP_ID");
                empTypeincentive = rs_ProdEmpDet.getString("EMP_TYP_INCENTIVE");
                empTypeDt = rs_ProdEmpDet.getString("EMP_TYP_DT");
                empTypeOt = rs_ProdEmpDet.getString("EMP_TYP_OT");
                prodWorkType = rs_ProdEmpDet.getString("PROD_WORK_TYP");
                prodIncentive = rs_ProdEmpDet.getString("PROD_INCNTV_FLG");
                prodTotHrs = rs_ProdEmpDet.getFloat("PROD_TOT_HRS");
                //prodStdHrs = rs_ProdEmpDet.getFloat("PROD_STD_HRS");
                if(prodId != currentProdId)
                {
                       currentProdId = prodId;
                       woJbId = rs_ProdEmpDet.getInt("WOJB_ID");
                       prodQtySnos = rs_ProdEmpDet.getString("PROD_QTY_SNOS");
                       prodStartOpn = rs_ProdEmpDet.getInt("PROD_START_OPN");
                       prodEndOpn   = rs_ProdEmpDet.getInt("PROD_END_OPN");
                       prodStdHrs = objProductionDetailsManager.getStandardHrs(woJbId,prodStartOpn,prodEndOpn);
                       StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                       prodStdHrs = prodStdHrs * st.countTokens();
                }
                
                
                
                otHrs = rs_ProdEmpDet.getFloat("OT_HRS");
                dtHrs = rs_ProdEmpDet.getFloat("DT_HRS");
                
                
                
                if(objStdHours.isStdhrs())
                {
                    if(objStdHours.isOpnlevelstdhrs())
                    {
                        if(objStdHours.isIncentive())
                        {
                            if(objStdHours.isJobopnlevelincentive())
                            {
                                if(empTypeincentive.equalsIgnoreCase("1"))
                                {
                                    if((prodIncentive.equals("1")))
                                    {
                                        if(prodWorkType.equalsIgnoreCase("N"))
                                        {        
                                            
                                            if((dtHrs+otHrs)== prodTotHrs)
                                            {
                                                 if(dtHrs+otHrs <= prodStdHrs)
                                                 {
                                                 incntvSlryHrs = prodStdHrs;
                                                 if(empTypeOt.equals("1"))
                                                 otSlryHrs = otHrs;
                                                 }
                                                 else
                                                 {
                                                 if(empTypeDt.equals("1"))
                                                 dtySlryHrs = dtHrs;
                                                 if(empTypeOt.equals("1"))
                                                 otSlryHrs = otHrs * 2;
                                                 }
                                              
                                            }
                                            else
                                            {
                                                if(empTypeDt.equals("1"))
                                                    dtySlryHrs = dtHrs;
                                                if(empTypeOt.equals("1"))
                                                    otSlryHrs = otHrs * 2;
                                            }
                                            

                                            
                                        }
                                        else
                                        {
                                            if(empTypeDt.equals("1"))
                                                dtySlryHrs = dtHrs;
                                            if(empTypeOt.equals("1"))
                                                otSlryHrs = otHrs * 2;
                                        }
                                        
                                    }
                                    else
                                    {
                                        if(empTypeDt.equals("1"))
                                            dtySlryHrs = dtHrs;
                                        if(empTypeOt.equals("1"))
                                            otSlryHrs = otHrs * 2;
                                    }
                                }
                                else
                                {
                                    if(empTypeDt.equals("1"))
                                        dtySlryHrs = dtHrs;
                                    if(empTypeOt.equals("1"))
                                        otSlryHrs = otHrs * 2;
                                }
                            }
                        }
                        else
                        {
                            if(empTypeDt.equals("1"))
                                dtySlryHrs = dtHrs;
                            if(empTypeOt.equals("1"))
                                otSlryHrs = otHrs * 2;
                        }
                    }
                    //else if(objStdHrs.isJobLevelStdHrs()
                    //{
                    
                    //}
                }
                else
                {
                    if(empTypeDt.equals("1"))
                        dtySlryHrs = dtHrs;
                    if(empTypeOt.equals("1"))
                        otSlryHrs = otHrs * 2;
                }
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("RulesRegistry.xml"));
                Element root = doc.getDocumentElement();
                NodeList rules = root.getElementsByTagName("rule");
                
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if(condn.equalsIgnoreCase("stdHrsFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelStdHrsFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("incentiveFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelIncentiveFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(condn.equalsIgnoreCase("empTypeIncentiveFalse") && empTypeincentive.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeDtFalse") && empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeOtFalse") && empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    
                    if(condn.equalsIgnoreCase("prodIncentiveFalse") && prodIncentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("reworkIncentiveFalse") && prodWorkType.equals("R") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(BuildConfig.DMODE)
                    System.out.println("conditions :"+condn);
                    parser.parseExpression(condn);
                    
                    if(BuildConfig.DMODE)
                    System.out.println("result : " + parser.getValue());
                    if(parser.getValue()==1)
                    {
                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        break;        
                    }
                                  
                }
                if(BuildConfig.DMODE)
                {
                	System.out.println("dtsalary :"+dtySlryHrs);
                	System.out.println("otsalary :"+otSlryHrs);
                	System.out.println("incentive salary :"+incntvSlryHrs);
                }
                
                
                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                ht_PyrlId.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.PROD_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("PROD EMP TABLE UPDATED");
                }
                
                
            }
            
            rs_ProdEmpDet.close();
            ps_ProdEmpDet.close();
            
            
        }
        
        rs_Prod.close();
        ps_Prod.close();
        
        // to calculate radl salary Hrs.
        PreparedStatement ps_Radl = con.executeStatement(SQLMaster.RADL_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Radl = ps_Radl.executeQuery();

        while(rs_Radl.next())
        {
            radlId = rs_Radl.getInt("RADL_ID");
            ht_PyrlId.put("RADL_ID",new Integer(radlId));
            PreparedStatement ps_RadlEmpDet = con.executeStatement(SQLMaster.RADL_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_RadlEmpDet = ps_RadlEmpDet.executeQuery();
            while(rs_RadlEmpDet.next())
            {
                empId = rs_RadlEmpDet.getInt("EMP_ID");
                empTypeincentive = rs_RadlEmpDet.getString("EMP_TYP_INCENTIVE");
                prodWorkType = rs_RadlEmpDet.getString("RADL_WORK_TYP");
                prodIncentive = rs_RadlEmpDet.getString("RADL_INCNTV_FLG");
                prodTotHrs = rs_RadlEmpDet.getFloat("RADL_TOT_HRS");
                prodStdHrs = rs_RadlEmpDet.getFloat("RADL_STD_HRS");

                otHrs = rs_RadlEmpDet.getFloat("OT_HRS");
                dtHrs = rs_RadlEmpDet.getFloat("DT_HRS");


               if(empTypeincentive.equalsIgnoreCase("1"))
                {
                    if((prodIncentive.equals("1"))&&!(prodWorkType.equalsIgnoreCase("R")))
                    {

                        if((dtHrs+otHrs)== prodTotHrs)
                        {
                            if(dtHrs+otHrs <= prodStdHrs)
                            {
                                incntvSlryHrs = prodStdHrs;
                                otSlryHrs = otHrs;
                            }
                            else
                            {
                                dtySlryHrs = dtHrs;
                                otSlryHrs = otHrs * 2;
                            }
                        }
                        else
                        {
                            dtySlryHrs = dtHrs;
                            otSlryHrs = otHrs * 2;
                        }
                    }
                    else
                    {
                        dtySlryHrs = dtHrs;
                        otSlryHrs = otHrs * 2;

                    }
                }
                else
                {
                    dtySlryHrs = dtHrs;
                }
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("RulesRegistry.xml"));
                Element root = doc.getDocumentElement();
                NodeList rules = root.getElementsByTagName("rule");
                
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if(condn.equalsIgnoreCase("stdHrsFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelStdHrsFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("incentiveFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelIncentiveFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeDtFalse") && empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeOtFalse") && empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    if(condn.equalsIgnoreCase("empTypeIncentiveFalse") && empTypeincentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(condn.equalsIgnoreCase("prodIncentiveFalse") && prodIncentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("reworkIncentiveFalse") && prodWorkType.equals("R") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    parser.parseExpression(condn);
                    
                    if(BuildConfig.DMODE)
                    System.out.println("result : " + parser.getValue());
                    if(parser.getValue()==1)
                    {
                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        break;        
                    }
                                  
                }
                
                if(BuildConfig.DMODE)
                {
                	System.out.println("dtsalary :"+dtySlryHrs);
                	System.out.println("otsalary :"+otSlryHrs);
                	System.out.println("incentive salary :"+incntvSlryHrs);
                }

                
                
                

                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                ht_PyrlId.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));

                int result = con.executeUpdateStatement(SQLMaster.RADL_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("RADL EMP TABLE UPDATED");
                }


            }
            rs_RadlEmpDet.close();
            ps_RadlEmpDet.close();
        }
        
        rs_Radl.close();
        ps_Radl.close();
        
        // to calculate non prod salary Hrs.
        PreparedStatement ps_Nprod = con.executeStatement(SQLMaster.NPROD_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Nprod = ps_Nprod.executeQuery();

        while(rs_Nprod.next())
        {
            nprodId = rs_Nprod.getInt("NPROD_ID");
            ht_PyrlId.put("NPROD_ID",new Integer(nprodId));
            PreparedStatement ps_NprodDet = con.executeStatement(SQLMaster.NPROD_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_NprodDet = ps_NprodDet.executeQuery();
            while(rs_NprodDet.next())
            {
                empId = rs_NprodDet.getInt("EMP_ID");
                otHrs = rs_NprodDet.getFloat("OT_HRS");
                dtHrs = rs_NprodDet.getFloat("DT_HRS");
                empTypeDt = rs_NprodDet.getString("EMP_TYP_DT");
                empTypeOt = rs_NprodDet.getString("EMP_TYP_OT");
                
                
                
                
                if(!empType.equalsIgnoreCase("supervisor"))
                {

                    dtySlryHrs = dtyHrs;
                    otSlryHrs = otHrs * 2;
                }
                else
                {
                    dtySlryHrs = dtyHrs;
                }
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("RulesRegistry.xml"));
                Element root = doc.getDocumentElement();
                NodeList rules = root.getElementsByTagName("rule");
                
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if((condn.equalsIgnoreCase("empTypeDtFalseNprod"))&& empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeOtFalseNprod"))&& empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeDtOtTrueNprod")) && empTypeOt.equals("1") && empTypeOt.equals("1"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                }
                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.NPROD_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("NPROD EMP TABLE UPDATED");
                }


            }
            rs_NprodDet.close();
            ps_NprodDet.close();
            
        }
        
        rs_Nprod.close();
        ps_Nprod.close();
        
        
        
        // to calculate pop salary Hrs.
        PreparedStatement ps_Pop = con.executeStatement(SQLMaster.POP_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Pop = ps_Pop.executeQuery();

        while(rs_Pop.next())
        {
            popId = rs_Pop.getInt("POP_ID");
            ht_PyrlId.put("POP_ID",new Integer(popId));
            PreparedStatement ps_PopDet = con.executeStatement(SQLMaster.POP_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_PopDet = ps_PopDet.executeQuery();
            while(rs_PopDet.next())
            {
                empId = rs_PopDet.getInt("EMP_ID");
                otHrs = rs_PopDet.getFloat("OT_HRS");
                dtHrs = rs_PopDet.getFloat("DT_HRS");
                empTypeDt = rs_PopDet.getString("EMP_TYP_DT");
                empTypeOt = rs_PopDet.getString("EMP_TYP_OT");                    
                 if(!empType.equalsIgnoreCase("supervisor"))
                {

                    dtySlryHrs = dtyHrs;
                    otSlryHrs = otHrs * 2;
                }
                else
                {
                    dtySlryHrs = dtyHrs;
                }
                
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("RulesRegistry.xml"));
                Element root = doc.getDocumentElement();
                NodeList rules = root.getElementsByTagName("rule");
                
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if((condn.equalsIgnoreCase("empTypeDtFalsePop"))&& empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeOtFalsePop"))&& empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeDtOtTruePop")) && empTypeOt.equals("1") && empTypeOt.equals("1"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                }

 
                // to update POP_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.POP_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("POP EMP TABLE UPDATED");
                }


            }
            rs_PopDet.close();
            ps_PopDet.close();
        }
        
        rs_Pop.close();
        ps_Pop.close();

        return true;
    }
*/
    public static float roundToTwoDecimalPlaces(float f){
    	
    	String val = String.valueOf(f);
    	
    	BigDecimal val2 = new BigDecimal(val);
    	
    	val2 = val2.setScale(2, BigDecimal.ROUND_HALF_UP);
    	
    	return val2.floatValue();
    	
    }


    private boolean calculateSalHrs(int pyrlCycleStatId,DBConnection con) throws SAXException, IOException, ParserConfigurationException, ProductionException, SQLException
    {

        int prodId = 0;
        int radlId = 0;
        int nprodId = 0;
        int popId = 0;
        int empId = 0;
        String empTypeincentive = "";
        String empTypeDt = "";
        String empTypeOt = "";
        String prodWorkType = "";
        String prodIncentive = "";
        float prodTotHrs = 0;
        float prodStdHrs = 0;
        String prodQtySnos = "";
        int prodStartOpn = 0;
        int prodEndOpn = 0;
        int woJbId = 0;
        float otSlryHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        float otHrs = 0;
        float dtHrs = 0;
        int currentProdId = 0;
        


       

        Hashtable ht_PyrlId = new Hashtable();
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        ht_PyrlId.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));


        //to calculate prod salary Hrs.
        PreparedStatement ps_Prod = con.executeStatement(SQLMaster.PROD_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Prod = ps_Prod.executeQuery();
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("RulesRegistry.xml"));
        Element root = doc.getDocumentElement();
        NodeList rules = root.getElementsByTagName("rule");
        
        while(rs_Prod.next())
        {
            prodId = rs_Prod.getInt("PROD_ID");
            ht_PyrlId.put("PROD_ID",new Integer(prodId));
            PreparedStatement ps_ProdEmpDet = con.executeStatement(SQLMaster.PROD_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_ProdEmpDet = ps_ProdEmpDet.executeQuery();
            while(rs_ProdEmpDet.next())
            {
                otSlryHrs = 0;
                dtySlryHrs = 0;
                incntvSlryHrs = 0;
                empId = rs_ProdEmpDet.getInt("EMP_ID");
                empTypeincentive = rs_ProdEmpDet.getString("EMP_TYP_INCENTIVE");
                empTypeDt = rs_ProdEmpDet.getString("EMP_TYP_DT");
                empTypeOt = rs_ProdEmpDet.getString("EMP_TYP_OT");
                prodWorkType = rs_ProdEmpDet.getString("PROD_WORK_TYP");
                prodIncentive = rs_ProdEmpDet.getString("PROD_INCNTV_FLG");
                prodTotHrs = rs_ProdEmpDet.getFloat("PROD_TOT_HRS");
                //prodStdHrs = rs_ProdEmpDet.getFloat("PROD_STD_HRS");
                if(prodId != currentProdId)
                {
                       currentProdId = prodId;
                       woJbId = rs_ProdEmpDet.getInt("WOJB_ID");
                       prodQtySnos = rs_ProdEmpDet.getString("PROD_QTY_SNOS");
                       prodStartOpn = rs_ProdEmpDet.getInt("PROD_START_OPN");
                       prodEndOpn   = rs_ProdEmpDet.getInt("PROD_END_OPN");
                       prodStdHrs = objProductionDetailsManager.getStandardHrs(woJbId,prodStartOpn,prodEndOpn);
                       StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                       prodStdHrs = prodStdHrs * st.countTokens();
                }
                
                prodStdHrs = roundToTwoDecimalPlaces(prodStdHrs);
                otHrs = rs_ProdEmpDet.getFloat("OT_HRS");
                dtHrs = rs_ProdEmpDet.getFloat("DT_HRS");
                                
                
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if(condn.equalsIgnoreCase("stdHrsFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelStdHrsFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("incentiveFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelIncentiveFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(condn.equalsIgnoreCase("empTypeIncentiveFalse") && empTypeincentive.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeDtFalse") && empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeOtFalse") && empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    
                    if(condn.equalsIgnoreCase("prodIncentiveFalse") && prodIncentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("reworkIncentiveFalse") && prodWorkType.equals("R") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(BuildConfig.DMODE)
                    System.out.println("conditions :"+condn);
                    parser.parseExpression(condn);
                    
                    if(BuildConfig.DMODE)
                    System.out.println("result : " + parser.getValue());
                    if(parser.getValue()==1)
                    {
                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        break;        
                    }
                                  
                }
                if(BuildConfig.DMODE)
                {
                	System.out.println("dtsalary :"+dtySlryHrs);
                	System.out.println("otsalary :"+otSlryHrs);
                	System.out.println("incentive salary :"+incntvSlryHrs);
                }
                
                
                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                ht_PyrlId.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.PROD_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("PROD EMP TABLE UPDATED");
                }
                
                
            }
            
            rs_ProdEmpDet.close();
            ps_ProdEmpDet.close();
            
            
        }
        
        rs_Prod.close();
        ps_Prod.close();
        
        // to calculate radl salary Hrs.
        PreparedStatement ps_Radl = con.executeStatement(SQLMaster.RADL_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Radl = ps_Radl.executeQuery();

        while(rs_Radl.next())
        {
            radlId = rs_Radl.getInt("RADL_ID");
            ht_PyrlId.put("RADL_ID",new Integer(radlId));
            PreparedStatement ps_RadlEmpDet = con.executeStatement(SQLMaster.RADL_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_RadlEmpDet = ps_RadlEmpDet.executeQuery();
            while(rs_RadlEmpDet.next())
            {
                empId = rs_RadlEmpDet.getInt("EMP_ID");
                empTypeincentive = rs_RadlEmpDet.getString("EMP_TYP_INCENTIVE");
                prodWorkType = rs_RadlEmpDet.getString("RADL_WORK_TYP");
                prodIncentive = rs_RadlEmpDet.getString("RADL_INCNTV_FLG");
                prodTotHrs = rs_RadlEmpDet.getFloat("RADL_TOT_HRS");
                prodStdHrs = rs_RadlEmpDet.getFloat("RADL_STD_HRS");

                otHrs = rs_RadlEmpDet.getFloat("OT_HRS");
                dtHrs = rs_RadlEmpDet.getFloat("DT_HRS");
                              
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if(condn.equalsIgnoreCase("stdHrsFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelStdHrsFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("incentiveFalse"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("opnLevelIncentiveFalse"))
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeDtFalse") && empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("empTypeOtFalse") && empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    if(condn.equalsIgnoreCase("empTypeIncentiveFalse") && empTypeincentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    if(condn.equalsIgnoreCase("prodIncentiveFalse") && prodIncentive.equals("0") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if(condn.equalsIgnoreCase("reworkIncentiveFalse") && prodWorkType.equals("R") )
                    {

                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                    parser.parseExpression(condn);
                    
                    if(BuildConfig.DMODE)
                    System.out.println("result : " + parser.getValue());
                    if(parser.getValue()==1)
                    {
                        
                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        break;        
                    }
                                  
                }
                
                if(BuildConfig.DMODE)
                {
                	System.out.println("dtsalary :"+dtySlryHrs);
                	System.out.println("otsalary :"+otSlryHrs);
                	System.out.println("incentive salary :"+incntvSlryHrs);
                }

                
                
                

                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                ht_PyrlId.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));

                int result = con.executeUpdateStatement(SQLMaster.RADL_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("RADL EMP TABLE UPDATED");
                }


            }
            rs_RadlEmpDet.close();
            ps_RadlEmpDet.close();
        }
        
        rs_Radl.close();
        ps_Radl.close();
        
        // to calculate non prod salary Hrs.
        PreparedStatement ps_Nprod = con.executeStatement(SQLMaster.NPROD_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Nprod = ps_Nprod.executeQuery();

        while(rs_Nprod.next())
        {
            nprodId = rs_Nprod.getInt("NPROD_ID");
            ht_PyrlId.put("NPROD_ID",new Integer(nprodId));
            PreparedStatement ps_NprodDet = con.executeStatement(SQLMaster.NPROD_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_NprodDet = ps_NprodDet.executeQuery();
            while(rs_NprodDet.next())
            {
                empId = rs_NprodDet.getInt("EMP_ID");
                otHrs = rs_NprodDet.getFloat("OT_HRS");
                dtHrs = rs_NprodDet.getFloat("DT_HRS");
                empTypeDt = rs_NprodDet.getString("EMP_TYP_DT");
                empTypeOt = rs_NprodDet.getString("EMP_TYP_OT");
               
                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if((condn.equalsIgnoreCase("empTypeDtFalseNprod"))&& empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeOtFalseNprod"))&& empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeDtOtTrueNprod")) && empTypeOt.equals("1") && empTypeOt.equals("1"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                }
                // to update PROD_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.NPROD_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("NPROD EMP TABLE UPDATED");
                }


            }
            rs_NprodDet.close();
            ps_NprodDet.close();
            
        }
        
        rs_Nprod.close();
        ps_Nprod.close();
        
        
        
        // to calculate pop salary Hrs.
        PreparedStatement ps_Pop = con.executeStatement(SQLMaster.POP_ENTRIES_SELECT_SQL_QUERY,ht_PyrlId);
        ResultSet rs_Pop = ps_Pop.executeQuery();

        while(rs_Pop.next())
        {
            popId = rs_Pop.getInt("POP_ID");
            ht_PyrlId.put("POP_ID",new Integer(popId));
            PreparedStatement ps_PopDet = con.executeStatement(SQLMaster.POP_EMP_DETAILS_SELECT_SQL_QUERY,ht_PyrlId);
            ResultSet rs_PopDet = ps_PopDet.executeQuery();
            while(rs_PopDet.next())
            {
                empId = rs_PopDet.getInt("EMP_ID");
                otHrs = rs_PopDet.getFloat("OT_HRS");
                dtHrs = rs_PopDet.getFloat("DT_HRS");
                empTypeDt = rs_PopDet.getString("EMP_TYP_DT");
                empTypeOt = rs_PopDet.getString("EMP_TYP_OT");                    

                for(int i=0;i<rules.getLength();i++)
                {
                    Element rule = (Element) rules.item(i);
                    String condn = rule.getAttribute("condition");
                    String val1 = rule.getAttribute("value1");
                    if(BuildConfig.DMODE)
                    {
                    	System.out.println("condition:"+condn);
                    	System.out.println("value1:"+val1);
                    }
                    
                    String val2 = rule.getAttribute("value2");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value2:"+val2);
                    
                    String val3 = rule.getAttribute("value3");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("value3:"+val3);
                    
                    JEP parser = new JEP();
                    

                    if(condn.indexOf("dtHrs")>=0)
                        condn = condn.replaceAll("dtHrs",dtHrs+"");
                    
                    if(condn.indexOf("prodTotHrs")>=0)
                        condn = condn.replaceAll("prodTotHrs",prodTotHrs+"");
                    
                    if(condn.indexOf("otHrs")>=0)
                        condn = condn.replaceAll("otHrs",otHrs+"");
                     
                    if(condn.indexOf("prodStdHrs")>=0)
                        condn = condn.replaceAll("prodStdHrs",prodStdHrs+"");
                    
                    if(BuildConfig.DMODE)
                    System.out.println("condtn : " + condn);            
                    


                    if((condn.equalsIgnoreCase("empTypeDtFalsePop"))&& empTypeDt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeOtFalsePop"))&& empTypeOt.equals("0"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }
                    
                    if((condn.equalsIgnoreCase("empTypeDtOtTruePop")) && empTypeOt.equals("1") && empTypeOt.equals("1"))
                    {

                        //for computing value1 
                        if(val1.indexOf("dtHrs")>=0)
                            val1 = val1.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val1.indexOf("prodTotHrs")>=0)
                            val1 = val1.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val1.indexOf("otHrs")>=0)
                            val1 = val1.replaceAll("otHrs",otHrs+"");
                         
                        
                        if(val1.indexOf("prodStdHrs")>=0)
                            val1 = val1.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        StringTokenizer st = new StringTokenizer(val1,"=");
                        String leftSide  = st.nextToken();
                        String rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val1.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val1.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val2.indexOf("dtHrs")>=0)
                            val2 = val2.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val2.indexOf("otHrs")>=0)
                            val2 = val2.replaceAll("otHrs",otHrs+"");
                         
                        if(val2.indexOf("prodTotHrs")>=0)
                            val2 = val2.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val2.indexOf("prodStdHrs")>=0)
                            val2 = val2.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val2,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val2.indexOf("incntvSlryHrs")>=0)
                        {
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val2.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                        
                        if(val3.indexOf("dtHrs")>=0)
                            val3 = val3.replaceAll("dtHrs",dtHrs+"");
                        
                        if(val3.indexOf("otHrs")>=0)
                            val3 = val3.replaceAll("otHrs",otHrs+"");
                         
                        if(val3.indexOf("prodTotHrs")>=0)
                            val3 = val3.replaceAll("prodTotHrs",prodTotHrs+"");
                        
                        if(val3.indexOf("prodStdHrs")>=0)
                            val3 = val3.replaceAll("prodStdHrs",prodStdHrs+"");
                        
                        st = new StringTokenizer(val3,"=");
                        leftSide  = st.nextToken();
                        rightSide = st.nextToken();
                                         
                        
                        parser.parseExpression(rightSide);
                        
                        
                        if(val3.indexOf("incntvSlryHrs")>=0)
                        {
                            
                            incntvSlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("dtySlryHrs")>=0)
                        {
                            dtySlryHrs = (float)parser.getValue();
                        }
                        if(val3.indexOf("otSlryHrs")>=0)
                        {
                            otSlryHrs = (float)parser.getValue();
                        }
                         
                        break;
                    
                    }

                }

 
                // to update POP_EMP table
                ht_PyrlId.put("EMP_ID",new Integer(empId));
                ht_PyrlId.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                ht_PyrlId.put("OT_SLRY_HRS",new Float(otSlryHrs));
                
                int result = con.executeUpdateStatement(SQLMaster.POP_EMP_DETAILS_UPDATE_SQL_QUERY,ht_PyrlId);
                if(result>0)
                {
                	if(BuildConfig.DMODE)
                	System.out.println("POP EMP TABLE UPDATED");
                }


            }
            rs_PopDet.close();
            ps_PopDet.close();
        }
        
        rs_Pop.close();
        ps_Pop.close();

        return true;
    }

    
    public boolean createPyrlInterface(int pyrlCycleStatId,String createdBy) throws SQLException, PayRollException, SAXException, IOException, ParserConfigurationException, ProductionException
    {
        boolean createRESULT = false;
        DBConnection con = null;
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Vector vec_EmployeeDet = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            PayRollEmployeeDetails objPayRollEmployeeDetails = null;

            ht_EmpHrs_Add.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));
            //if(this.isAllEntriesPosted(pyrlCycleStatId))
            //{

                this.calculateSalHrs(pyrlCycleStatId,con);
                vec_EmployeeDet = getPayRollEmployeeDetails(pyrlCycleStatId,con);
                for(int i = 0;i<vec_EmployeeDet.size();i++)
                {
                    Vector vec_EmpHrs = new Vector();
                    objPayRollEmployeeDetails = (PayRollEmployeeDetails)vec_EmployeeDet.elementAt(i);
                    vec_EmpHrs = objPayRollEmployeeDetails.getDateShiftEmpHrsDetails();
                    ht_EmpHrs_Add.put("EMP_ID",new Integer(objPayRollEmployeeDetails.getEmpId()));
                    ht_EmpHrs_Add.put("PREPYRL_CREATEDBY",createdBy);
                    for(int j = 0;j<vec_EmpHrs.size();j++)
                    {

                        DateShiftEmpHrsDetails objDateShiftEmpHrsDetails;
                        objDateShiftEmpHrsDetails = (DateShiftEmpHrsDetails)vec_EmpHrs.elementAt(j);
                        ht_EmpHrs_Add.put("SHIFT_ID",new Integer(objDateShiftEmpHrsDetails.getShiftId()));
                        ht_EmpHrs_Add.put("PROD_CRNT_DATE",objDateShiftEmpHrsDetails.getProdDate());
                        ht_EmpHrs_Add.put("PREPYRL_DT_HRS",new Float(objDateShiftEmpHrsDetails.getDtyHrs()));
                        ht_EmpHrs_Add.put("PREPYRL_OT_HRS",new Float(objDateShiftEmpHrsDetails.getOtHrs()));
                        ht_EmpHrs_Add.put("PREPYRL_RGLRSLRY_HRS",new Float(objDateShiftEmpHrsDetails.getDtySlryHrs()));
                        ht_EmpHrs_Add.put("PREPYRL_OTSLRY_HRS",new Float(objDateShiftEmpHrsDetails.getOtSlryHrs()));
                        ht_EmpHrs_Add.put("PREPYRL_INCNTVSLRY_HRS",new Float(objDateShiftEmpHrsDetails.getIncntvSlryHrs()));
                        ht_EmpHrs_Add.put("PREPYRL_DATA_SRC",objDateShiftEmpHrsDetails.getDataSource());
                        con.executeUpdateStatement(SQLMaster.PYRL_INTERFACE_ADD_SQL_QUERY,ht_EmpHrs_Add);

                    }

                }
                //to set ISPYRL_CREATED = '1'.
                con.executeUpdateStatement(SQLMaster.PYRL_CYCLE_STAT_UPDATE_SQL_QUERY,ht_EmpHrs_Add);
                
                //to set PROD_UPDATE_PYRL = '1' ...
                con.executeUpdateStatement(SQLMaster.PROD_PYRL_UPDATE_SQL_QUERY,ht_EmpHrs_Add);
                con.executeUpdateStatement(SQLMaster.NPROD_PYRL_UPDATE_SQL_QUERY,ht_EmpHrs_Add);
                con.executeUpdateStatement(SQLMaster.RADL_PYRL_UPDATE_SQL_QUERY,ht_EmpHrs_Add);
                con.executeUpdateStatement(SQLMaster.POP_PYRL_UPDATE_SQL_QUERY,ht_EmpHrs_Add);
                
                createRESULT = true;
                con.commitTransaction();
                
            //}
            //else
            //{
             //   throw new PayRollException("PLMEC000","ALL ENTRIES SHOULD BE POSTED BEFORE CREATION OF PAYROLL","");
            //}
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return createRESULT;
    }
    public HashMap createPayRoll(Vector prePyrlIds,String createdBy) throws SQLException, PayRollException
    {
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        int count = 0;
        Hashtable ht_PyrlId = new Hashtable();
        PreparedStatement ps = null;
        ResultSet rs_PyrlId_Check = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            ht_PyrlId.put("PYRL_CREATEDBY",createdBy);
            for(int i = 0;i<prePyrlIds.size();i++)
            {
                ht_PyrlId.put("PREPYRL_ID",(Integer)prePyrlIds.elementAt(i));
                ps = con.executeStatement(SQLMaster.PYRL_ID_CHECK_SQL_QUERY,ht_PyrlId);
                rs_PyrlId_Check = ps.executeQuery();
                if(rs_PyrlId_Check.next())
                {
                    count = rs_PyrlId_Check.getInt(1);
                }
                //if it is not in PYRL_ADJSTMNT..
                int result = 0;
                if(count<=0)
                {
                    result = con.executeUpdateStatement(SQLMaster.PYRL_ADD_SQL_QUERY,ht_PyrlId);
                }
                //if it is in PYRL_ADJSTMNT..
                else
                {
                    result = con.executeUpdateStatement(SQLMaster.PYRL_ADD_WITH_ADJSTMNT_SQL_QUERY,ht_PyrlId);

                }
                if(result>0)
                {
                    hm_Result.put(prePyrlIds.elementAt(i),new Integer(1));
                }
                else
                {
                    hm_Result.put(prePyrlIds.elementAt(i),new Integer(2));
                }

                rs_PyrlId_Check.close();
                ps.close();
            }
            con.commitTransaction();
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        return hm_Result;
    }
    public boolean adjustPayRollDetails(Vector vec_PyrlDetails,String modifiedBy) throws SQLException, PayRollException
    {
        DBConnection con = null;
        boolean adjustRESULT = false;
        int prePyrlId = 0;
        Hashtable ht_PyrlDet = new Hashtable();
        PreparedStatement ps = null;
        ResultSet rs_Pyrl_Check = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            PayRollDetails objPayRollDetails = null;
            for(int i = 0;i<vec_PyrlDetails.size();i++)
            {
                objPayRollDetails = (PayRollDetails)vec_PyrlDetails.elementAt(i);
                prePyrlId = objPayRollDetails.getPrePyrlId();
                //if it is already in PYRL_ADJSTMNT...
                ht_PyrlDet.put("PREPYRL_ID",new Integer(prePyrlId));
                ht_PyrlDet.put("PYRL_CYCLE_STAT_ID",new Integer(objPayRollDetails.getPyrlCycleStatId()));
                ht_PyrlDet.put("PA_CHNGD_DATE",objPayRollDetails.getProdDate());
                ht_PyrlDet.put("PA_CHNGD_RGLRSLRY_HRS",new Float(objPayRollDetails.getDtySlryHrs()));
                ht_PyrlDet.put("PA_CHNGD_OTSLRY_HRS",new Float(objPayRollDetails.getOtSlryHrs()));
                ht_PyrlDet.put("PA_CHNGD_INCNTVSLRY_HRS",new Float(objPayRollDetails.getIncntvSlryHrs()));
                ht_PyrlDet.put("PA_COUNT",new Integer(objPayRollDetails.getNoOfTimesAdjstd()+1));
                ht_PyrlDet.put("PA_CREATEDBY",modifiedBy);

                int result = con.executeUpdateStatement(SQLMaster.PYRL_ADJSTMNT_LOG_ADD_SQL_QUERY,ht_PyrlDet);
                if(result >0)
                {
                    result = con.executeUpdateStatement(SQLMaster.PYRL_ADJSTMNT_UPDATE_SQL_QUERY,ht_PyrlDet);
                }
                else
                    result = con.executeUpdateStatement(SQLMaster.PYRL_ADJSTMNT_ADD_SQL_QUERY,ht_PyrlDet);

                if(result>0)
                {
                    int count = 0;
                    ps = con.executeStatement(SQLMaster.PYRL_CHECK_SQL_QUERY,ht_PyrlDet);
                    rs_Pyrl_Check = ps.executeQuery();
                    if(rs_Pyrl_Check.next())
                    {
                        count = rs_Pyrl_Check.getInt(1);
                    }
                    if(count>=1)
                    {
                        result = con.executeUpdateStatement(SQLMaster.PYRL_UPDATE_SQL_QUERY,ht_PyrlDet);
                    }
                }
                rs_Pyrl_Check.close();
                ps.close();
            }
            con.commitTransaction();
            adjustRESULT = true;
        }
        catch(SQLException sqle)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return adjustRESULT;
    }
    public boolean closePayrollDetails(int pyrlCycleStatId) throws PayRollException, SQLException
    {
        boolean closeRESULT = false;
        DBConnection con = null;
        Hashtable ht_StatId = new Hashtable();
        PreparedStatement ps = null;
        int count = 0;
        int result = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_StatId.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));

            ps = con.executeStatement(SQLMaster.PYRL_CYCLE_STAT_CHECK_SQL_QUERY,ht_StatId);
            ResultSet rs_Check = ps.executeQuery();
            if(rs_Check.next())
            {
                count = rs_Check.getInt(1);
            }
            if(count==0)
            {
                result = con.executeUpdateStatement(SQLMaster.PYRL_CYCLE_STAT_CLOSE_SQL_QUERY,ht_StatId);
                if(result>0)
                {
                    closeRESULT = true;
                }
            }
            else
            {
                throw new PayRollException("PRE005","PAYROLL CREATION NOT FINISHED","");
            }
            rs_Check.close();
            ps.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());

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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }

        return closeRESULT;

    }
    public HashMap getForPayRollAdjstmnt(Filter[] filters,String sortBy,boolean ascending) throws PayRollException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Pyrl_Det = new Vector();

        if(BuildConfig.DMODE)
        {
            logger.info("GET FOR PAYRL ADJSTMNT STARTS");
        }
        if(filters == null)
        {
            logger.error("FILTER VALUES ARE NULL");

            throw new PayRollException("PLMEC001","FILTER VALUES ARE NULL","");
        }



        int tot_Rec_Cnt = 0;
        DBConnection con = null;

        try
        {

            con = DBConnectionFactory.getConnection();


            //filters and tableName are passed to the function and receives Total Record Count
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.PYRL_ADJSTMNT_FILTER_SQL_QUERY);

            //Finding end index for the query
            int eIndex = tot_Rec_Cnt;


            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,1,eIndex+1,sortBy,ascending,SQLMaster.PYRL_ADJSTMNT_FILTER_SQL_QUERY);


            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);


            ResultSet rs_PyrlDetails_Get =  con.executeRownumStatement(Query);
            while(rs_PyrlDetails_Get.next())
            {
                PayRollDetails objPayRollDetails = new PayRollDetails();
                objPayRollDetails.setPrePyrlId(rs_PyrlDetails_Get.getInt("PREPYRL_ID"));
                objPayRollDetails.setProdDate(rs_PyrlDetails_Get.getTimestamp("PROD_CRNT_DATE"));
                objPayRollDetails.setShiftName(rs_PyrlDetails_Get.getString("SHIFT_NAME"));
                objPayRollDetails.setEmpName(rs_PyrlDetails_Get.getString("EMP_NAME"));
                objPayRollDetails.setDtyHrs(rs_PyrlDetails_Get.getFloat("PREPYRL_DT_HRS"));
                objPayRollDetails.setOtHrs(rs_PyrlDetails_Get.getFloat("PREPYRL_OT_HRS"));
                objPayRollDetails.setDtySlryHrs(rs_PyrlDetails_Get.getFloat("PREPYRL_RGLRSLRY_HRS"));
                objPayRollDetails.setOtSlryHrs(rs_PyrlDetails_Get.getFloat("PREPYRL_OTSLRY_HRS"));
                objPayRollDetails.setIncntvSlryHrs(rs_PyrlDetails_Get.getFloat("PREPYRL_INCNTVSLRY_HRS"));
                objPayRollDetails.setNoOfTimesAdjstd(rs_PyrlDetails_Get.getInt("PA_COUNT"));
                vec_Pyrl_Det.addElement(objPayRollDetails);
            }

            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("PyrlDetails",vec_Pyrl_Det);
            rs_PyrlDetails_Get.getStatement().close();
            rs_PyrlDetails_Get.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        logger.info("GET FOR PAYRL ADJSTMNT ENDS");


        return hm_Result;
    }
    public HashMap getAllPayRollIdsInfo(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws PayRollException, SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Pyrl_Det = new Vector();

        if(BuildConfig.DMODE)
        {
            logger.info("GET FOR PAYRL ADJSTMNT STARTS");
        }
        if(filters == null)
        {
            logger.error("FILTER VALUES ARE NULL");

            throw new PayRollException("PLMEC001","FILTER VALUES ARE NULL","");
        }



        int tot_Rec_Cnt = 0;
        DBConnection con = null;

        try
        {

            con = DBConnectionFactory.getConnection();

            //          Finding the end index for the query
            int eIndex = startIndex + displayCount;

            //filters and tableName are passed to the function and receives Total Record Count
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.PYRL_IDS_INFO_FILTER_SQL_QUERY);


            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.PYRL_IDS_INFO_FILTER_SQL_QUERY);


            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }

            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);


            ResultSet rs_PyrlDetails_Get =  con.executeRownumStatement(Query);
            while(rs_PyrlDetails_Get.next())
            {

                PayRollIdsDetails objPayRollIdsDetails = new PayRollIdsDetails();
                objPayRollIdsDetails.setFromDate(rs_PyrlDetails_Get.getTimestamp("PYRL_CYCLE_STAT_FROM_DATE"));
                objPayRollIdsDetails.setToDate(rs_PyrlDetails_Get.getTimestamp("PYRL_CYCLE_STAT_TO_DATE"));
                objPayRollIdsDetails.setPyrl_Closed(rs_PyrlDetails_Get.getString("IS_PYRL_CLOSED").equals("1"));
                objPayRollIdsDetails.setPyrl_Created(rs_PyrlDetails_Get.getString("IS_PYRL_CREATED").equals("1"));
                vec_Pyrl_Det.addElement(objPayRollIdsDetails);
            }

            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("PyrlDetails",vec_Pyrl_Det);
            rs_PyrlDetails_Get.getStatement().close();
            rs_PyrlDetails_Get.close();

        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new PayRollException("PLMEC000","GENERAL SQL ERROR",sqle.toString());
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
                if(BuildConfig.DMODE)
                {
                    logger.error("EXCEPTION WHILE CLOSING CONNECTION");
                }
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        logger.info("GET FOR PAYRL ADJSTMNT ENDS");


        return hm_Result;
    }

    public static void main(String args[]) throws PayRollException, SQLException, ParseException, ParserConfigurationException, SAXException, IOException
    {
      DBConnection con = null;
      PayRollDetailsManager objPayRollDetailsManager = new PayRollDetailsManager();
      try
      {
            con = DBConnectionFactory.getConnection();
            System.out.println(con);
            objPayRollDetailsManager.calculateSalHrs(5, con);
      }
      catch(Exception ex)
      {
            ex.printStackTrace();
      }
      finally
      {
            if(con != null)
                  con.closeConnection();
      }
    }

}
/***
 $Log: PayRollDetailsManager.java,v $
 Revision 1.79  2009/10/07 05:33:30  ppalaniappan
 RoundToTwoDecimalPlaces method included.

 Revision 1.78  2008/01/21 06:25:12  kduraisamy
 unwanted imports removed.

 Revision 1.77  2005/12/19 15:12:56  kduraisamy
 IN PYRL CYCLE CONFIG TABLE CYCLE IS REPLACED AS CYCLES.

 Revision 1.76  2005/09/30 16:37:39  kduraisamy
 startTransaction() added in create pyrl interface().

 Revision 1.75  2005/09/30 11:27:58  kduraisamy
 startTransaction() added in create pyrl interface().

 Revision 1.74  2005/09/15 07:36:09  kduraisamy
 rs.getStatement().close() added in Filters.

 Revision 1.73  2005/09/10 07:14:57  kduraisamy
 RulesRegistry.xml reading taken out of the loop.

 Revision 1.72  2005/08/26 09:08:50  kduraisamy
 in getfor pyrl(), dt hrs,ot hrs added.

 Revision 1.71  2005/08/24 12:54:16  kduraisamy
 stdHrs computation while creating pyrl interface added.

 Revision 1.70  2005/08/23 09:24:49  kduraisamy
 CHECK EMP TYPE SQL QUERY ADDED.

 Revision 1.69  2005/08/22 07:21:49  vkrishnamoorthy
 Problem in pyrl creation corrected.

 Revision 1.68  2005/08/19 04:25:42  kduraisamy
 unwanted variables commented.

 Revision 1.67  2005/08/08 08:42:23  kduraisamy
 payrollid date calculation changed.

 Revision 1.66  2005/08/01 11:57:54  kduraisamy
 UPDATE_PYRL INTRODUCED.

 Revision 1.65  2005/07/05 06:46:04  kduraisamy
 resultSet preparedStatement propery closed.

 Revision 1.64  2005/07/04 11:31:13  kduraisamy
 payroll calculate Sal Hrs() method changed for radl,nprod,pop.

 Revision 1.63  2005/07/04 10:27:47  kduraisamy
 error corrected.

 Revision 1.62  2005/07/04 09:44:57  kduraisamy
 payroll calculate Sal Hrs() method changed for radl,nprod,pop.

 Revision 1.61  2005/07/01 14:36:16  kduraisamy
 customized payroll phase1 completed.

 Revision 1.60  2005/06/29 12:43:58  smurugesan
 if con ! = null added in finally

 Revision 1.59  2005/06/29 09:35:37  kduraisamy
 StandarHours() place changed.

 Revision 1.58  2005/06/28 09:14:33  vkrishnamoorthy
 CalculateSalHrs() error corrected.

 Revision 1.57  2005/06/28 06:35:10  kduraisamy
 calculateSalHrs() added.

 Revision 1.56  2005/06/25 09:51:15  kduraisamy
 calculateSalHrs() added.

 Revision 1.55  2005/06/25 07:17:55  kduraisamy
 calculateSalHrs() added.

 Revision 1.54  2005/06/11 07:25:51  kduraisamy
 getclosedpayrollCyclesTat() added.

 Revision 1.53  2005/06/11 06:51:13  kduraisamy
 created by added in payroll.

 Revision 1.52  2005/06/09 06:47:24  kduraisamy
 PRYL IS CHANGED TO PYRL.

 Revision 1.51  2005/05/27 05:37:22  kduraisamy
 ISALLENTRIES POSTED CHECKING ADDED.

 Revision 1.50  2005/05/18 10:03:25  kduraisamy
 indentation.

 ***/
