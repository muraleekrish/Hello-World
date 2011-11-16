/*
 * Created on Dec 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetailsManager;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;
import com.savantit.prodacs.infra.util.SortString;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductionDetailsManager
{
    
    static Logger logger = Logger.getLogger(ProductionDetailsManager.class);
    public ProductionDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    
    public boolean checkDutyEntry(Date prodDate, int shiftId, int empId, DBConnection con) throws SQLException
    {
        boolean retValue = false;
        Hashtable ht = new Hashtable();
        ht.put("PROD_DATE",prodDate);
        ht.put("SHIFT_ID",new Integer(shiftId));
        ht.put("EMP_ID",new Integer(empId));
        
        PreparedStatement ps = con.executeStatement(SQLMaster.CHECK_DUTY_ENTRY_SQL_QUERY,ht);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            if(rs.getInt(1)<=0)
                retValue = true;
        }
        rs.close();
        ps.close();
        
        return retValue;
    }
    
    public double fetchProductionEnteredHrs(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException
    {
        Hashtable ht = new Hashtable();
        DBConnection con = null;
        double prodHrs = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            ht.put("MC_CDE",mcCde);
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_PROD_HRS_DETAILS,ht);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                prodHrs = rs.getDouble("PROD_HRS");
            }
            return prodHrs;
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
    }
    
    public double fetchNonProductionEnteredHrs(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException
    {
        

        Hashtable ht = new Hashtable();
        DBConnection con = null;
        double nProdHrs = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            ht.put("MC_CDE",mcCde);
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_NON_PROD_HRS_DETAILS,ht);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
               nProdHrs = rs.getDouble("NPROD_HRS");
            }
            return nProdHrs;
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
    }
    private Vector fetchMachineAccountingDetails(Date currentDate,String mcCde) throws ProductionException, SQLException
    {
        Hashtable ht = new Hashtable();
        Vector vecResult = new Vector();
        GregorianCalendar gc = new GregorianCalendar();
        float availShiftHrs = 0;
        DBConnection con = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            
            ht.put("CURRENT_DATE",currentDate);
            ht.put("MC_CDE",mcCde);
            if (BuildConfig.DMODE)
                System.out.println("Date :"+currentDate);
            PreparedStatement ps_Date_NonAvblty = con.executeStatement(SQLMaster.CHECK_DATE_IN_CUSTOM_NON_AVBLTY_SQL_QUERY,ht);
            ResultSet rs_Date_NonAvblty = ps_Date_NonAvblty.executeQuery();
            if(rs_Date_NonAvblty.next())
            {
                int count = rs_Date_NonAvblty.getInt(1);
                if (count > 0)
                {
                    return vecResult;
                }
            }
            rs_Date_NonAvblty.close();
            ps_Date_NonAvblty.close();
            
            PreparedStatement ps_Date_Custom_Avblty = con.executeStatement(SQLMaster.CHECK_DATE_IN_CUSTOM_AVBLTY_SQL_QUERY,ht);
            ResultSet rs_Date_Avblty = ps_Date_Custom_Avblty.executeQuery();
            String avbltyStartTime = "";
            String avbltyEndTime = "";
            String startHrs = "";
            String startMins = "";
            String endHrs = "";
            String endMins = "";
            boolean flg = false;
            int shiftId = 0;
            while (rs_Date_Avblty.next())
            {
                MachineNonAccountedDetails objMachineNonAccountedDetails = new MachineNonAccountedDetails();
                objMachineNonAccountedDetails.setProdDate(currentDate);
                flg = true;
                avbltyStartTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_STARTTIME");
                avbltyEndTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_ENDTIME");
                shiftId = rs_Date_Avblty.getInt("SHIFT_ID");
                ht.put("SHIFT_ID",new Integer(shiftId));
                StringTokenizer stStartTime = new StringTokenizer(avbltyStartTime,":");
                if (stStartTime.hasMoreTokens())
                {
                    startHrs = stStartTime.nextToken();
                }
                if (stStartTime.hasMoreTokens())
                {
                    startMins = stStartTime.nextToken();
                }
                StringTokenizer stEndTime = new StringTokenizer(avbltyEndTime,":");
                if (stEndTime.hasMoreTokens())
                {
                    endHrs = stEndTime.nextToken();
                }
                if (stEndTime.hasMoreTokens())
                {
                    endMins = stEndTime.nextToken();
                }
                String startTime = startHrs+startMins;
                String endTime = endHrs+endMins;
                int diff = 0;
                int sTime = Integer.parseInt(startTime);
                int eTime = Integer.parseInt(endTime);
                if (sTime < eTime)
                    diff = eTime - sTime;
                else
                    diff = (2400-sTime)+eTime;
                
                float min = diff%100;
                float hr = diff/100;
                min = (min/60);
                
                availShiftHrs = hr + min;
                
                if(BuildConfig.DMODE)
                    System.out.println("availShiftHRs :"+availShiftHrs);
                
                
                PreparedStatement psShiftDetails = con.executeStatement(SQLMaster.SELECT_SHIFT_DETAILS,ht);
                ResultSet rsShiftDetails = psShiftDetails.executeQuery();
                if(rsShiftDetails.next())
                {
                    objMachineNonAccountedDetails.setShiftName(rsShiftDetails.getString("SHIFT_NAME"));
                }
                
                rsShiftDetails.close();
                psShiftDetails.close();
                
                PreparedStatement psAccountingDetails = con.executeStatement(SQLMaster.SELECT_MACHINE_ACCOUNTING_DETAILS,ht);
                ResultSet rsAccountingDetails = psAccountingDetails.executeQuery();
                boolean isHrsAccounted = false;
                if(rsAccountingDetails.next())
                {
                    objMachineNonAccountedDetails.setProdHrs(rsAccountingDetails.getFloat("PROD_HRS"));
                    objMachineNonAccountedDetails.setNonProdHrs(rsAccountingDetails.getFloat("NPROD_HRS"));
                    isHrsAccounted = rsAccountingDetails.getBoolean("ISHRS_ACCOUNTED");
                }
                
                rsAccountingDetails.close();
                psAccountingDetails.close();
                
                objMachineNonAccountedDetails.setNonAccuntedHrs(availShiftHrs - objMachineNonAccountedDetails.getProdHrs() - objMachineNonAccountedDetails.getNonProdHrs());
                
                if(!isHrsAccounted)
                    vecResult.add(objMachineNonAccountedDetails);
                
                
                
                
            }
            rs_Date_Avblty.close();
            ps_Date_Custom_Avblty.close();
            if (BuildConfig.DMODE)
                System.out.println("Flg :"+flg);
            if (flg)
            {
                return vecResult;
            }
            
            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
            if (BuildConfig.DMODE)
                System.out.println("Day :"+day);
            ht.put("DAY",new Integer(day));
            PreparedStatement ps_Date_BaseCal = con.executeStatement(SQLMaster.CHECK_DATE_IN_BASE_CAL,ht);
            ResultSet rs_Date_BaseCal = ps_Date_BaseCal.executeQuery();
            
            while (rs_Date_BaseCal.next())
            {
                MachineNonAccountedDetails objMachineNonAccountedDetails = new MachineNonAccountedDetails();
                objMachineNonAccountedDetails.setProdDate(currentDate);
                avbltyStartTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_STARTTIME");
                avbltyEndTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_ENDTIME");
                shiftId = rs_Date_BaseCal.getInt("SHIFT_ID");
                ht.put("SHIFT_ID",new Integer(shiftId));
                if (BuildConfig.DMODE)
                {
                    System.out.println("St Time :"+avbltyStartTime);
                    System.out.println("Ed Time :"+avbltyEndTime);
                }
                StringTokenizer stStartTime = new StringTokenizer(avbltyStartTime,":");
                if (stStartTime.hasMoreTokens())
                {
                    startHrs = stStartTime.nextToken();
                }
                if (stStartTime.hasMoreTokens())
                {
                    startMins = stStartTime.nextToken();
                }
                StringTokenizer stEndTime = new StringTokenizer(avbltyEndTime,":");
                if (stEndTime.hasMoreTokens())
                {
                    endHrs = stEndTime.nextToken();
                }
                if (stEndTime.hasMoreTokens())
                {
                    endMins = stEndTime.nextToken();
                }
                String startTime = startHrs+startMins;
                String endTime = endHrs+endMins;
                int diff = 0;
                int sTime = Integer.parseInt(startTime);
                int eTime = Integer.parseInt(endTime);
                if (sTime < eTime)
                    diff = eTime - sTime;
                else
                    diff = (2400-sTime)+eTime;
                
                float min = diff%100;
                float hr = diff/100;
                min = (min/60);
                availShiftHrs = hr + min;
                
                if(BuildConfig.DMODE)
                    System.out.println("availShiftHRs :"+availShiftHrs);
                
                
                PreparedStatement psShiftDetails = con.executeStatement(SQLMaster.SELECT_SHIFT_DETAILS,ht);
                ResultSet rsShiftDetails = psShiftDetails.executeQuery();
                if(rsShiftDetails.next())
                {
                    objMachineNonAccountedDetails.setShiftName(rsShiftDetails.getString("SHIFT_NAME"));
                }
                
                rsShiftDetails.close();
                psShiftDetails.close();
                
                PreparedStatement psAccountingDetails = con.executeStatement(SQLMaster.SELECT_MACHINE_ACCOUNTING_DETAILS,ht);
                ResultSet rsAccountingDetails = psAccountingDetails.executeQuery();
                boolean isHrsAccounted = false;
                if(rsAccountingDetails.next())
                {
                    objMachineNonAccountedDetails.setProdHrs(rsAccountingDetails.getFloat("PROD_HRS"));
                    objMachineNonAccountedDetails.setNonProdHrs(rsAccountingDetails.getFloat("NPROD_HRS"));
                    isHrsAccounted = rsAccountingDetails.getBoolean("ISHRS_ACCOUNTED");
                }
                
                rsAccountingDetails.close();
                psAccountingDetails.close();
                
                objMachineNonAccountedDetails.setNonAccuntedHrs(availShiftHrs - objMachineNonAccountedDetails.getProdHrs() - objMachineNonAccountedDetails.getNonProdHrs());
                
                if(!isHrsAccounted)
                    vecResult.add(objMachineNonAccountedDetails);
                
            }
            rs_Date_BaseCal.close();
            ps_Date_BaseCal.close();
            
            return vecResult;
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
    }
    public MachineNonAccountedDetails[] checkMachineAccounting(Date fromDate,Date toDate,String mcCde) throws ProductionException, SQLException
    {
        GregorianCalendar gc = new GregorianCalendar();
        Date currentDate = null;
        MachineNonAccountedDetails[] objMachineNonAccountedDetailsList = null;
        Vector vecResult = new Vector();
        gc.setTime(fromDate);
        for (currentDate = gc.getTime();currentDate.compareTo(toDate)<=0;currentDate = gc.getTime())
        {
            Vector vecDetails = new Vector();
            vecDetails = this.fetchMachineAccountingDetails(gc.getTime(),mcCde);
            
            vecResult.addAll(vecDetails);
            
            gc.add(GregorianCalendar.DATE,1);
            
            
        }
        objMachineNonAccountedDetailsList = new MachineNonAccountedDetails[vecResult.size()];
        vecResult.copyInto(objMachineNonAccountedDetailsList);
        return objMachineNonAccountedDetailsList;
    }
    
    
    public HashMap addNewProductionDetails(ProductionDetails[] objProductionDetailsList) throws ProductionException, SQLException
    {
        
        int result = 0;
        int prodId = 0;
        boolean incntvFlg = false;
        int empId = 0;
        int empTypId = 0;
        String empType = "";
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        int totQty = 0;
        HashMap hm_Result = new HashMap();
        Vector vec_Duplicate = new Vector();
        Vector vec_EmpFail = new Vector();
        Vector vec_MachineFail = new Vector();
        Vector vecDutyFail = new Vector();
        Vector vec_Success = new Vector();
        PreparedStatement ps = null;
        int startOpn = 0;
        int endOpn = 0;
        float stdHrs = 0;
        float totHrs = 0;
        Vector vec_EmpHrs = new Vector();
        Hashtable ht_Prodn_Add = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Hashtable ht_ProdAccntng_Add = new Hashtable();
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        PreparedStatement psEmpTypCheck = null;
        ResultSet rs = null;
        
        logger.info("PRODUCTION ADD STARTS");
        
        if(BuildConfig.DMODE)
            System.out.println("ADD PRODUCTION DETAILS");
        
        if(objProductionDetailsList.length == 0)
        {
            
            logger.error("PRODUCTION DETAILS OBJ NULL");
            throw new ProductionException("PMEC001","PRODUCTION DETAILS OBJ NULL","");
        }
        try
        {
            
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<objProductionDetailsList.length;i++)
            {
                startOpn = objProductionDetailsList[i].getProdStartOpn();
                endOpn = objProductionDetailsList[i].getProdEndOpn();
                
                
                stdHrs = getStandardHrs(objProductionDetailsList[i].getWoJbId(),startOpn,endOpn);
                totQty =  objProductionDetailsList[i].getProdTotQty();
                
                //to find total standard hrs...
                stdHrs = totQty * stdHrs;
                
                ht_Prodn_Add.put("MC_CDE",objProductionDetailsList[i].getMcCode());
                ht_Prodn_Add.put("PROD_CRNT_DATE",objProductionDetailsList[i].getProdCrntDate());
                ht_Prodn_Add.put("SHIFT_ID",new Integer(objProductionDetailsList[i].getShiftId()));
                ht_Prodn_Add.put("PROD_WORK_TYP",objProductionDetailsList[i].getProdWorkType());
                ht_Prodn_Add.put("PROD_INCNTV_FLG",(this.checkIncentive(objProductionDetailsList[i].getWoJbId(),startOpn,endOpn))?"1":"0");
                ht_Prodn_Add.put("WOJB_ID",new Integer(objProductionDetailsList[i].getWoJbId()));
                ht_Prodn_Add.put("PROD_QTY_SNOS",objProductionDetailsList[i].getProdQtySnos());
                ht_Prodn_Add.put("PROD_TOT_QTY",new Integer(objProductionDetailsList[i].getProdTotQty()));
                ht_Prodn_Add.put("PROD_START_OPN",new Integer(startOpn));
                ht_Prodn_Add.put("PROD_END_OPN",new Integer(endOpn));
                ht_Prodn_Add.put("PROD_STD_HRS",new Float(stdHrs));
                ht_Prodn_Add.put("PROD_TOT_HRS",new Float(objProductionDetailsList[i].getProdTotHrs()));
                ht_Prodn_Add.put("PROD_CREATEDBY",objProductionDetailsList[i].getCreatedBy());
                //prodQtySnos = objProductionDetailsList[i].getProdQtySnos();
                
                try
                {
                    if(this.checkDuplicateProd(ht_Prodn_Add,con))
                    {
                        vec_Duplicate.addElement(objProductionDetailsList[i]);
                        continue;
                    }
                    
                    result = con.executeUpdateStatement(SQLMaster.PRODN_DETAILS_ADD_SQL_QUERY,ht_Prodn_Add);
                    if(result > 0)
                    {
                        //to set WO_ISPROD_ENTERED to 1..
                        
                        con.executeUpdateStatement(SQLMaster.WORKORDER_JB_TABLE_UPDATE_SQL_QUERY,ht_Prodn_Add);
                        int j = 0;
                        ps = con.executeStatement(SQLMaster.PROD_ID_SELECT_SQL_QUERY);
                        ResultSet rs_ProdId_Get = ps.executeQuery();
                        if(rs_ProdId_Get.next())
                        {
                            prodId = rs_ProdId_Get.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("PROD ID :"+prodId);
                        }
                        rs_ProdId_Get.close();
                        ps.close();
                        vec_EmpHrs = objProductionDetailsList[i].getProdnEmpHrsDetails();
                        incntvFlg = objProductionDetailsList[i].isProdIncntvFlag();
                        String prodWorkType = objProductionDetailsList[i].getProdWorkType();
                        totHrs = objProductionDetailsList[i].getProdTotHrs();
                        
                        boolean flag = true;
                        for(j = 0;j<vec_EmpHrs.size();j++)
                        {
                            
                            otHrs = 0;
                            otSlryHrs = 0;
                            dtyHrs = 0;
                            dtySlryHrs = 0;
                            incntvSlryHrs = 0;
                            EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                            objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(j);
                            dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                            otHrs = objEmployeeDtyOtDetails.getOtHrs();
                            empId = objEmployeeDtyOtDetails.getEmpId();
                            
                            empTypId = objEmployeeDtyOtDetails.getEmpTypdId();
                            ht.put("EMP_TYP_ID",new Integer(empTypId));
                            psEmpTypCheck = con.executeStatement(SQLMaster.CHECK_EMP_TYPE_SQL_QUERY,ht);
                            rs = psEmpTypCheck.executeQuery();
                            int check = 0;
                            int val = 0;
                            if(rs.next())
                            {
                                val = rs.getInt(1);
                            }
                            rs.close();
                            psEmpTypCheck.close();
                            if(val == 0)
                            if(dtyHrs >0)
                            if(!this.checkDutyEntry(objProductionDetailsList[i].getProdCrntDate(), objProductionDetailsList[i].getShiftId(), empId, con))
                            {
                                con.rollBackTransaction();
                                flag = false;
                                HashMap hm = new HashMap();
                                hm.put(objEmployeeDtyOtDetails.getEmpName(),objProductionDetailsList[i]);
                                vecDutyFail.addElement(hm);
                                break;
                            }
                                
                            if(val == 0)
                            check = checkAvailableEmpHrs(empId,objProductionDetailsList[i].getProdCrntDate(),objProductionDetailsList[i].getShiftId(),dtyHrs+otHrs,0,0,0,con);
                            if(check==0)
                            {
                                
                                ht_EmpHrs_Add.put("PROD_ID",new Integer(prodId));
                                ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                                ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                                ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                                ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                                ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                                ht_EmpHrs_Add.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));
                                ht_EmpHrs_Add.put("PROD_MODIFYCOUNT",new Integer(0));
                                result = con.executeUpdateStatement(SQLMaster.PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                                if(result>0)
                                {
                                    if(BuildConfig.DMODE)
                                        System.out.println("Employee Hrs Details Added");
                                    
                                }
                                
                            }
                            else
                            {
                                con.rollBackTransaction();
                                flag = false;
                                HashMap hm = new HashMap();
                                hm.put(objEmployeeDtyOtDetails.getEmpName(),objProductionDetailsList[i]);
                                vec_EmpFail.addElement(hm);
                                break;
                            }
                        }
                        if(flag)
                        {
                            
                            ht_ProdAccntng_Add.put("MC_CDE",objProductionDetailsList[i].getMcCode());
                            ht_ProdAccntng_Add.put("PROD_DATE",objProductionDetailsList[i].getProdCrntDate());
                            ht_ProdAccntng_Add.put("SHIFT_ID",new Integer(objProductionDetailsList[i].getShiftId()));
                            int check = checkHrsAccounted(objProductionDetailsList[i].getMcCode(),objProductionDetailsList[i].getProdCrntDate(),objProductionDetailsList[i].getShiftId(),objProductionDetailsList[i].getProdTotHrs(),0,con);
                            if(check==0)
                            {
                                ht_ProdAccntng_Add.put("PROD_HRS",new Float(objProductionDetailsList[i].getProdTotHrs()));
                                ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","1");
                            }
                            else if(check ==1)
                            {
                                ht_ProdAccntng_Add.put("PROD_HRS",new Float(objProductionDetailsList[i].getProdTotHrs()));
                                ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","0");
                            }
                            else
                            {
                                con.rollBackTransaction();
                                vec_MachineFail.addElement(objProductionDetailsList[i]);
                                continue;
                            }
                            result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_PRODHRS_SQL_QUERY,ht_ProdAccntng_Add);
                            
                            if(result<=0)
                            {
                                result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_PRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Add);
                                if(result>0)
                                {
                                    if(BuildConfig.DMODE)
                                        System.out.println("Production Accounting Added");
                                }
                                
                            }
                            else
                            {
                                if(BuildConfig.DMODE)
                                    System.out.println("Production Accounting Updated");
                            }
                            
                        }
                        else
                        {
                            continue;
                        }
                        
                    }
                    
                    
                }
                catch(SQLException sqle)
                {
                    if(BuildConfig.DMODE)
                    {
                        sqle.printStackTrace();
                    }
                    con.rollBackTransaction();
                    if(BuildConfig.DBASE==BuildConfig.ORACLE)
                    {
                        if(sqle.toString().indexOf("PK_PRODID")>=0)
                        {
                            throw new ProductionException("PMEC002","PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                        }
                        else if (sqle.toString().indexOf("FK_PROD_MCCDE") >= 0)
                        {
                            throw new ProductionException("PMEC004","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                        }
                        else if (sqle.toString().indexOf("FK_PROD_SHIFTID") >= 0)
                        {
                            throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                        }
                        else if (sqle.toString().indexOf("FK_PROD_WOJBID") >= 0)
                        {
                            throw new ProductionException("PMEC008","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
                        }
                        else
                        {
                            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                        }
                    }
                    if(BuildConfig.DBASE==BuildConfig.MYSQL)
                    {
                        if(sqle.toString().indexOf("Duplicate entry")>=0)
                        {
                            throw new ProductionException("PMEC002","PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                        }
                        else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                        {
                            throw new ProductionException("PMEC004","PARENT KEY MACHINE CODE OR SHIFT ID OR WORKORDER JOB ID NOT FOUND",sqle.toString());
                        }
                        else
                        {
                            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                        }
                    }
                    
                }
                
                con.commitTransaction();
                vec_Success.addElement(new Integer(prodId));
                
                
            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("PRODUCTION ADD ENDS");
        
        
        
        hm_Result.put("Success",vec_Success);
        hm_Result.put("EmpFailure",vec_EmpFail);
        hm_Result.put("MachineFailure",vec_MachineFail);
        hm_Result.put("DuplicateEntry",vec_Duplicate);
        hm_Result.put("DutyFailure",vecDutyFail);	
        return hm_Result;
    }
    private boolean checkDuplicateProd(Hashtable ht,DBConnection con) throws SQLException
    {
        boolean returnResult = false;
        String prodQtySnos = ht.get("PROD_QTY_SNOS").toString();
        Hashtable htProcess = new Hashtable();
        htProcess.put("WOJB_ID",ht.get("WOJB_ID"));
        htProcess.put("PROD_START_OPN",ht.get("PROD_START_OPN"));
        htProcess.put("PROD_END_OPN",ht.get("PROD_END_OPN"));
        StringTokenizer st = new StringTokenizer(prodQtySnos,",");
        while(st.hasMoreTokens())
        {
            htProcess.put("PROD_QTY_SNOS",st.nextToken());
            PreparedStatement ps_DuplicateCheck = con.executeStatement(SQLMaster.PROD_ENTRY_DUPLICATE_CHECK_SQL_QUERY,htProcess);
            ResultSet rs_DuplicateCheck = ps_DuplicateCheck.executeQuery();
            if(rs_DuplicateCheck.next())
            {
                if(rs_DuplicateCheck.getInt(1) > 0)
                {
                    returnResult = true;
                    break;
                }
            }
                       
        }
        return returnResult;
    }
    
    private boolean checkDuplicateProdForUpdate(Hashtable ht,DBConnection con) throws SQLException
    {
        boolean returnResult = false;
        String prodQtySnos = ht.get("PROD_QTY_SNOS").toString();
        StringTokenizer st = new StringTokenizer(prodQtySnos,",");
        Hashtable htProcess = new Hashtable();
        htProcess.put("WOJB_ID",ht.get("WOJB_ID"));
        htProcess.put("PROD_START_OPN",ht.get("PROD_START_OPN"));
        htProcess.put("PROD_END_OPN",ht.get("PROD_END_OPN"));
        htProcess.put("PROD_ID",ht.get("PROD_ID"));
        
        while(st.hasMoreTokens())
        {
            htProcess.put("PROD_QTY_SNOS",st.nextToken());
            PreparedStatement ps_DuplicateCheck = con.executeStatement(SQLMaster.PROD_ENTRY_DUPLICATE_CHECK_FOR_UPDATE_SQL_QUERY,htProcess);
            ResultSet rs_DuplicateCheck = ps_DuplicateCheck.executeQuery();
            if(rs_DuplicateCheck.next())
            {
                if(rs_DuplicateCheck.getInt(1) > 0)
                {
                    returnResult = true;
                    break;
                }
            }
                       
        }
        
        return returnResult;
    }
    
    public OperationDetails[] viewOperations(int woJbId) throws SQLException, ProductionException
    {
        OperationDetails[] objOperationDetailsList = null;
        DBConnection con = null;
        Hashtable ht_WojbId = new Hashtable();
        Vector vec_OpnDet = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_WojbId.put("WOJB_ID",new Integer(woJbId));
            PreparedStatement ps_OpnDet = con.executeStatement(SQLMaster.OPERATION_DETAILS_SELECT_SQL_QUERY,ht_WojbId);
            ResultSet rs_OpnDet = ps_OpnDet.executeQuery();
            
            while(rs_OpnDet.next())
            {
                OperationDetails objOperationDetails = new OperationDetails();
                objOperationDetails.setOpnGpCode(rs_OpnDet.getString("OPN_GP_CODE"));
                objOperationDetails.setOpnSerialNo(rs_OpnDet.getInt("WOJBOPN_OPN_SNO"));
                objOperationDetails.setOpnName(rs_OpnDet.getString("WOJBOPN_OPN_NAME"));
                objOperationDetails.setOpnStdHrs(rs_OpnDet.getFloat("WOJBOPN_OPN_STDHRS"));
                objOperationDetails.setOpnIncentive(rs_OpnDet.getString("WOJBOPN_INCENTIVE").equals("1"));
                vec_OpnDet.add(objOperationDetails);
            }
            rs_OpnDet.close();
            ps_OpnDet.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }
            
            logger.error("GENERAL SQL ERROR",sqle);
            
            
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        
        finally
        {
            try
            {
                if(!con.getConnection().isClosed())
                    con.closeConnection();
            }
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        objOperationDetailsList = new OperationDetails[vec_OpnDet.size()];
        vec_OpnDet.copyInto(objOperationDetailsList);
        return objOperationDetailsList;
        
    }
    public ProductionAccountingDateDetails checkPostingByOnlyMachine(String mcCde,Date prodDate) throws ProductionException, SQLException
    {
        boolean checkRESULT = false;
        DBConnection con = null;
        Hashtable ht_McCde = new Hashtable();
        ProductionAccountingDateDetails objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        String mcTypName = "";
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_McCde.put("MC_CDE",mcCde);
            while(true)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(prodDate);
                gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
                prodDate = gc.getTime();
                ht_McCde.put("PROD_CRNT_DATE",prodDate);
                if(BuildConfig.DMODE)
                    System.out.println("Date :"+prodDate);
                //to check Custom_Non_Avblty table
                PreparedStatement ps_Date_NACheck = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_McCde);
                ResultSet rs_Date_NACheck = ps_Date_NACheck.executeQuery();
                if(rs_Date_NACheck.next())
                {
                    int count = rs_Date_NACheck.getInt(1);
                    if(BuildConfig.DMODE)
                        System.out.println("Count1:"+count);
                    if(count==0)
                    {
                        
                        //to check in custom availability...
                        PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McCde);
                        ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                        if(rs_Date_AvbCheck.next())
                        {
                            if(rs_Date_AvbCheck.getInt(1) != 0)
                            {
                                break;
                            }
                        }
                        
                        rs_Date_AvbCheck.close();
                        ps_Date_AvbCheck.close();
                        
                        
                        //to check Base_Cal
                        gc.setTime(prodDate);
                        
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        ht_McCde.put("DAY",new Integer(day));
                        PreparedStatement ps_Date_BCCheck = con.executeStatement(SQLMaster.CHECK_DAY_IN_BASECAL_TABL_SQL_QUERY,ht_McCde);
                        ResultSet rs_Date_BCCheck = ps_Date_BCCheck.executeQuery();
                        if(rs_Date_BCCheck.next())
                        {
                            int totRows = rs_Date_BCCheck.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("TRows :"+totRows);
                            if(totRows!=0)
                            {
                                break;
                            }
                        }
                        rs_Date_BCCheck.close();
                        ps_Date_BCCheck.close();
                    }
                    
                }
                rs_Date_NACheck.close();
                ps_Date_NACheck.close();
            }
            
            
            // to check given machine is radl machine or normal..
            PreparedStatement ps_McTyp_Name = null;
            ps_McTyp_Name = con.executeStatement(SQLMaster.MC_TYP_NAME_SELECT_SQL_QUERY,ht_McCde);
            ResultSet rs_McTyp_Name = ps_McTyp_Name.executeQuery();
            
            if(rs_McTyp_Name.next())
            {
                mcTypName = rs_McTyp_Name.getString("MC_TYP_NAME");
            }
            rs_McTyp_Name.close();
            ps_McTyp_Name.close();
            
            PreparedStatement ps_McCde_PostCheck = null;
            boolean rdlFlg = false;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
            
            if(!mcTypName.equalsIgnoreCase("radial"))
            {
                ps_McCde_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MACHINE_SQL_QUERY,ht_McCde);
                ResultSet rs_McCde_PostCheck = ps_McCde_PostCheck.executeQuery();
                
                if(rs_McCde_PostCheck.next())
                {
                    int count = rs_McCde_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                        PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_McCde);
                        ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                        if(rs_Previousday_EntryCheck.next())
                        {
                            count = rs_Previousday_EntryCheck.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("Count inside prod :"+count);
                            if(count==0)
                            {
                                throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                            }
                            else
                            {
                                PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                                ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                                Vector vec_MachineResult = new Vector();
                                while(rs_Valid_Machines.next())
                                {
                                    Vector vec_ShiftResult = new Vector();
                                    ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                    String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                    String mcName = rs_Valid_Machines.getString("MC_NAME");
                                    objProductionAccountingMachineDetails.setMcName(mcName);
                                    objProductionAccountingMachineDetails.setMcCode(mcCode);
                                    ht_McCde.put("MC_CDE",mcCode);
                                    PreparedStatement ps_Valid_Shifts = con.executeStatement(SQLMaster.VALID_SHIFTS_SELECT_SQL_QUERY);
                                    ResultSet rs_Valid_Shifts = ps_Valid_Shifts.executeQuery();
                                    while(rs_Valid_Shifts.next())
                                    {
                                        
                                        int shift_Id = rs_Valid_Shifts.getInt("SHIFT_ID");
                                        ht_McCde.put("SHIFT_ID",new Integer(shift_Id));
                                        PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_McCde);
                                        ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                        if(rs_Prod_Accounting.next())
                                        {
                                            int check = rs_Prod_Accounting.getInt(1);
                                            if(BuildConfig.DMODE)
                                            {
                                                System.out.println("check "+check);
                                                System.out.println("shiftId "+shift_Id);
                                                System.out.println("Mc code "+mcCode);
                                            }
                                            prodHrs = 0;
                                            nprodHrs = 0;
                                            
                                            if(check==0)
                                            {  //if(check==0)
                                                PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_McCde);
                                                ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                                if(rs_ProdHrs_Select.next())
                                                {
                                                    prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                                    nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                                }
                                                
                                                rs_ProdHrs_Select.close();
                                                ps_ProdHrs_Select.close();
                                                
                                                
                                                //to calculate the available hrs..
                                                String startHrs = "";
                                                String startMinute = "";
                                                String endHrs = "";
                                                String endMinute = "";
                                                
                                                GregorianCalendar gc = new GregorianCalendar();
                                                PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McCde);
                                                int cnt = 0;
                                                ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                                if(rs_CheckAvblty.next())
                                                    cnt = rs_CheckAvblty.getInt(1);
                                                
                                                rs_CheckAvblty.close();
                                                ps_CheckAvblty.close();
                                                String shiftName = "";
                                                if(cnt >0)
                                                {
                                                    
                                                    PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_McCde);
                                                    ResultSet rs = ps.executeQuery();
                                                    if(rs.next())
                                                    {
                                                        startHrs = "";
                                                        startMinute = "";
                                                        endHrs = "";
                                                        endMinute = "";
                                                        availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                        availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                        StringTokenizer st;
                                                        st = new StringTokenizer(availStartTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startMinute = st.nextToken();
                                                        }
                                                        st = new StringTokenizer(availEndTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endMinute = st.nextToken();
                                                        }
                                                        
                                                        String startTime = startHrs+startMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("stTime"+startTime);
                                                        String endTime = endHrs+endMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("endTime"+endTime);
                                                        int diff = 0;
                                                        int i = Integer.parseInt(startTime);
                                                        int j = Integer.parseInt(endTime);
                                                        if(i<j)
                                                            diff = j - i;
                                                        else
                                                            diff = (2400-i)+j;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("diff:"+diff);
                                                        float min = diff%100;
                                                        float hr = diff/100;
                                                        if(BuildConfig.DMODE)
                                                        {
                                                            System.out.println("min"+min);
                                                            System.out.println("hr"+hr);
                                                        }
                                                        min = (min/60);
                                                        if(BuildConfig.DMODE)
                                                            System.out.println(min);
                                                        availShiftHrs = hr + min;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("availShiftHRs :"+availShiftHrs);
                                                        
                                                        //to select the shiftName...
                                                        PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McCde);
                                                        ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                        if(rs_ShiftNameSel.next())
                                                        {
                                                            shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                        }
                                                        
                                                        rs_ShiftNameSel.close();
                                                        ps_ShiftNameSel.close();
                                                        
                                                        
                                                        
                                                    }
                                                    rs.close();
                                                    ps.close();
                                                    
                                                }
                                                else
                                                {
                                                    
                                                    
                                                    gc.setTime(prodDate);
                                                    int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                                    
                                                    ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shift_Id));
                                                    ht_AvailHrs_Get.put("DAY",new Integer(day));
                                                    
                                                    PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                                    ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                                    //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                                    if(rs_AvailHrs_Get.next())
                                                    {
                                                        startHrs = "";
                                                        startMinute = "";
                                                        endHrs = "";
                                                        endMinute = "";
                                                        availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                        availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                        StringTokenizer st;
                                                        st = new StringTokenizer(availStartTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startMinute = st.nextToken();
                                                        }
                                                        st = new StringTokenizer(availEndTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endMinute = st.nextToken();
                                                        }
                                                        
                                                        String startTime = startHrs+startMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("stTime"+startTime);
                                                        String endTime = endHrs+endMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("endTime"+endTime);
                                                        int diff = 0;
                                                        int i = Integer.parseInt(startTime);
                                                        int j = Integer.parseInt(endTime);
                                                        if(i<j)
                                                            diff = j - i;
                                                        else
                                                            diff = (2400-i)+j;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("diff:"+diff);
                                                        float min = diff%100;
                                                        float hr = diff/100;
                                                        if(BuildConfig.DMODE)
                                                        {
                                                            System.out.println("min"+min);
                                                            System.out.println("hr"+hr);
                                                        }
                                                        min = (min/60);
                                                        if(BuildConfig.DMODE)
                                                            System.out.println(min);
                                                        availShiftHrs = hr + min;
                                                        
                                                    }
                                                    rs_AvailHrs_Get.close();
                                                    ps_AvailHrs_Get.close();
                                                    
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("availShiftHRs :"+availShiftHrs);
                                                    
                                                    //                                    	                      to select the shiftName...
                                                    PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McCde);
                                                    ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                    if(rs_ShiftNameSel.next())
                                                    {
                                                        shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                    }
                                                    
                                                    rs_ShiftNameSel.close();
                                                    ps_ShiftNameSel.close();
                                                    
                                                    
                                                }
                                                
                                                
                                                ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                                availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                                objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                                objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                                objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                                objProductionAccountingShiftDetails.setShiftName(shiftName);
                                                vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                                
                                                
                                                
                                            }//if(check==0) end
                                            
                                        }
                                        rs_Prod_Accounting.close();
                                        ps_Prod_Accounting.close();
                                    }
                                    
                                    rs_Valid_Shifts.close();
                                    ps_Valid_Shifts.close();
                                    objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                    vec_MachineResult.add(objProductionAccountingMachineDetails);
                                }
                                rs_Valid_Machines.close();
                                ps_Valid_Machines.close();
                                
                                objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                                
                            }
                        }
                        rs_Previousday_EntryCheck.close();
                        ps_Previousday_EntryCheck.close();
                        
                    }
                }
                rs_McCde_PostCheck.close();
                ps_McCde_PostCheck.close();
            }
            else
            {
                ps_McCde_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_RADIAL_MACHINE_SQL_QUERY,ht_McCde);
                ResultSet rs_McCde_PostCheck = ps_McCde_PostCheck.executeQuery();
                
                if(rs_McCde_PostCheck.next())
                {
                    int count = rs_McCde_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                        PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_McCde);
                        ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                        if(rs_Previousday_EntryCheck.next())
                        {
                            count = rs_Previousday_EntryCheck.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("Count inside prod :"+count);
                            if(count==0)
                            {
                                throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                            }
                            else
                            {
                                PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                                ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                                Vector vec_MachineResult = new Vector();
                                while(rs_Valid_Machines.next())
                                {
                                    Vector vec_ShiftResult = new Vector();
                                    ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                    String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                    String mcName = rs_Valid_Machines.getString("MC_NAME");
                                    objProductionAccountingMachineDetails.setMcName(mcName);
                                    objProductionAccountingMachineDetails.setMcCode(mcCode);
                                    ht_McCde.put("MC_CDE",mcCode);
                                    PreparedStatement ps_Valid_Shifts = con.executeStatement(SQLMaster.VALID_SHIFTS_SELECT_SQL_QUERY);
                                    ResultSet rs_Valid_Shifts = ps_Valid_Shifts.executeQuery();
                                    while(rs_Valid_Shifts.next())
                                    {
                                        int shift_Id = rs_Valid_Shifts.getInt("SHIFT_ID");
                                        String shift_Name = rs_Valid_Shifts.getString("SHIFT_NAME");
                                        ht_McCde.put("SHIFT_ID",new Integer(shift_Id));
                                        PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_McCde);
                                        ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                        if(rs_Prod_Accounting.next())
                                        {
                                            int check = rs_Prod_Accounting.getInt(1);
                                            if(BuildConfig.DMODE)
                                            {
                                                System.out.println("check "+check);
                                                System.out.println("shiftId "+shift_Id);
                                                System.out.println("Mc code "+mcCode);
                                            }
                                            prodHrs = 0;
                                            nprodHrs = 0;
                                            
                                            if(check==0)
                                            {  //if(check==0)
                                                PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_McCde);
                                                ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                                if(rs_ProdHrs_Select.next())
                                                {
                                                    prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                                    nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                                }
                                                
                                                rs_ProdHrs_Select.close();
                                                ps_ProdHrs_Select.close();
                                                
                                                
                                                //to calculate the available hrs..
                                                String startHrs = "";
                                                String startMinute = "";
                                                String endHrs = "";
                                                String endMinute = "";
                                                
                                                GregorianCalendar gc = new GregorianCalendar();
                                                PreparedStatement ps =  con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_McCde);
                                                ResultSet rs = ps.executeQuery();
                                                if(rs.next())
                                                {
                                                    int counts = rs.getInt(1);
                                                    if(counts<=0)
                                                    {
                                                        PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McCde);
                                                        int cnt = 0;
                                                        ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                                        if(rs_CheckAvblty.next())
                                                            cnt = rs_CheckAvblty.getInt(1);
                                                        
                                                        rs_CheckAvblty.close();
                                                        ps_CheckAvblty.close();
                                                        if(cnt >0)
                                                        {
                                                            
                                                            ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_McCde);
                                                            rs = ps.executeQuery();
                                                            if(rs.next())
                                                            {
                                                                startHrs = "";
                                                                startMinute = "";
                                                                endHrs = "";
                                                                endMinute = "";
                                                                availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                                availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                                StringTokenizer st;
                                                                st = new StringTokenizer(availStartTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startMinute = st.nextToken();
                                                                }
                                                                st = new StringTokenizer(availEndTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endMinute = st.nextToken();
                                                                }
                                                                
                                                                String startTime = startHrs+startMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("stTime"+startTime);
                                                                String endTime = endHrs+endMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("endTime"+endTime);
                                                                int diff = 0;
                                                                int i = Integer.parseInt(startTime);
                                                                int j = Integer.parseInt(endTime);
                                                                if(i<j)
                                                                    diff = j - i;
                                                                else
                                                                    diff = (2400-i)+j;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("diff:"+diff);
                                                                float min = diff%100;
                                                                float hr = diff/100;
                                                                if(BuildConfig.DMODE)
                                                                {
                                                                    System.out.println("min"+min);
                                                                    System.out.println("hr"+hr);
                                                                }
                                                                min = (min/60);
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println(min);
                                                                availShiftHrs = hr + min;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                                
                                                            }
                                                            else
                                                            {
                                                                availShiftHrs = 0;
                                                            }
                                                            rs.close();
                                                            ps.close();
                                                            if(BuildConfig.DMODE)
                                                            {
                                                                //   System.out.println("table ProdHrs :"+tableProdHrs);
                                                                //   System.out.println("table NProdHrs :"+tableNprodHrs);
                                                                System.out.println("ProdHrs :"+prodHrs);
                                                                System.out.println("nProdHrs :"+nprodHrs);
                                                            }
                                                        }
                                                        else
                                                        {
                                                            
                                                            
                                                            gc.setTime(prodDate);
                                                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                                            
                                                            ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shift_Id));
                                                            ht_AvailHrs_Get.put("DAY",new Integer(day));
                                                            
                                                            PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                                            ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                                            //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                                            if(rs_AvailHrs_Get.next())
                                                            {
                                                                startHrs = "";
                                                                startMinute = "";
                                                                endHrs = "";
                                                                endMinute = "";
                                                                availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                                availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                                StringTokenizer st;
                                                                st = new StringTokenizer(availStartTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startMinute = st.nextToken();
                                                                }
                                                                st = new StringTokenizer(availEndTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endMinute = st.nextToken();
                                                                }
                                                                
                                                                String startTime = startHrs+startMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("stTime"+startTime);
                                                                String endTime = endHrs+endMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("endTime"+endTime);
                                                                int diff = 0;
                                                                int i = Integer.parseInt(startTime);
                                                                int j = Integer.parseInt(endTime);
                                                                if(i<j)
                                                                    diff = j - i;
                                                                else
                                                                    diff = (2400-i)+j;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("diff:"+diff);
                                                                float min = diff%100;
                                                                float hr = diff/100;
                                                                if(BuildConfig.DMODE)
                                                                {
                                                                    System.out.println("min"+min);
                                                                    System.out.println("hr"+hr);
                                                                }
                                                                min = (min/60);
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println(min);
                                                                availShiftHrs = hr + min;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                                
                                                            }
                                                            
                                                            rs_AvailHrs_Get.close();
                                                            ps_AvailHrs_Get.close();
                                                        }
                                                    }
                                                }
                                                
                                                rs.close();
                                                ps.close();
                                                
                                                availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                                ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                                objProductionAccountingShiftDetails.setShiftName(shift_Name);
                                                objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                                objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                                
                                                objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                                vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                            }//if(check==0) end
                                            
                                        }
                                        rs_Prod_Accounting.close();
                                        ps_Prod_Accounting.close();
                                    }
                                    rs_Valid_Shifts.close();
                                    ps_Valid_Shifts.close();
                                    objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                    vec_MachineResult.add(objProductionAccountingMachineDetails);
                                }
                                
                                
                                rs_Valid_Machines.close();
                                ps_Valid_Machines.close();
                                
                                objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                                
                            }
                        }
                        rs_Previousday_EntryCheck.close();
                        ps_Previousday_EntryCheck.close();
                        
                    }
                }
                rs_McCde_PostCheck.close();
                ps_McCde_PostCheck.close();
                rdlFlg = true;
            }
            if(checkRESULT)
            {
                PreparedStatement ps = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MACHINE_IN_NPROD_SQL_QUERY,ht_McCde);
                ResultSet rs_McCde_PostCheck = ps.executeQuery();
                
                if(rs_McCde_PostCheck.next())
                {
                    int count = rs_McCde_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                    }
                    else
                    {
                        checkRESULT = false;
                    }
                }
                rs_McCde_PostCheck.close();
                ps.close();
                /*if(checkRESULT)
                 {
                 ps = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MACHINE_IN_POP_SQL_QUERY,ht_McCde);
                 rs_McCde_PostCheck = ps.executeQuery();
                 
                 if(rs_McCde_PostCheck.next())
                 {
                 int count = rs_McCde_PostCheck.getInt(1);
                 if(count==0)
                 {
                 checkRESULT = true;
                 }
                 else
                 {
                 checkRESULT = false;
                 }
                 }
                 rs_McCde_PostCheck.close();
                 ps.close();
                 if(!checkRESULT)
                 {
                 throw new ProductionException("PMEC009","PAY OUTSIDE PRODUCTION ENTRY NOT POSTED","");
                 }
                 
                 }*/
                if(!checkRESULT)
                {
                    throw new ProductionException("PMEC010","NON PRODUCTION ENTRY NOT POSTED","");
                }
            }
            else if(rdlFlg)
            {
                throw new ProductionException("PMEC011","RADL PRODUCTION ENTRY NOT POSTED","");
            }
            else
            {
                throw new ProductionException("PMEC011","PRODUCTION ENTRY NOT POSTED","");
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    
    public ProductionAccountingDateDetails checkPostingByOnlyDate(Date prodDate) throws ProductionException, ParseException, SQLException
    {
        boolean checkRESULT = false;
        DBConnection con=null;
        Hashtable ht_ProdDate = new Hashtable();
        Hashtable ht_ProdDay = new Hashtable();
        
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        ProductionAccountingDateDetails objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        try
        {
            con = DBConnectionFactory.getConnection();
            while(true)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(prodDate);
                gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
                prodDate = gc.getTime();
                ht_ProdDate.put("PROD_CRNT_DATE",prodDate);
                if(BuildConfig.DMODE)
                    System.out.println("Date :"+prodDate);
                //to check Custom_Non_Avblty table
                PreparedStatement ps_Date_NACheck = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_ProdDate);
                ResultSet rs_Date_NACheck = ps_Date_NACheck.executeQuery();
                if(rs_Date_NACheck.next())
                {
                    int count = rs_Date_NACheck.getInt(1);
                    if(BuildConfig.DMODE)
                        System.out.println("Count1:"+count);
                    
                    if(count==0)
                    {
                        
                        //to check in custom availability...
                        PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_ProdDate);
                        ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                        if(rs_Date_AvbCheck.next())
                        {
                            if(rs_Date_AvbCheck.getInt(1) != 0)
                            {
                                break;
                            }
                        }
                        
                        rs_Date_AvbCheck.close();
                        ps_Date_AvbCheck.close();
                        
                        
                        //to check Base_Cal
                        gc.setTime(prodDate);
                        
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        ht_ProdDay.put("DAY",new Integer(day));
                        PreparedStatement ps_Date_BCCheck = con.executeStatement(SQLMaster.CHECK_DAY_IN_BASECAL_TABL_SQL_QUERY,ht_ProdDay);
                        ResultSet rs_Date_BCCheck = ps_Date_BCCheck.executeQuery();
                        if(rs_Date_BCCheck.next())
                        {
                            int totRows = rs_Date_BCCheck.getInt(1);
                            if(BuildConfig.DMODE)
                                System.out.println("TRows :"+totRows);
                            if(totRows!=0)
                            {
                                break;
                            }
                        }
                        rs_Date_BCCheck.close();
                        ps_Date_BCCheck.close();
                    }
                    
                }
                rs_Date_NACheck.close();
                ps_Date_NACheck.close();
            }
            
            PreparedStatement ps_ProdDate_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_SQL_QUERY,ht_ProdDate);
            ResultSet rs_ProdDate_PostCheck = ps_ProdDate_PostCheck.executeQuery();
            
            if(rs_ProdDate_PostCheck.next())
            {
                int count = rs_ProdDate_PostCheck.getInt(1);
                if(BuildConfig.DMODE)
                    System.out.println("Count in Prod :"+count);
                if(count==0)
                {
                    checkRESULT = true;
                    PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_ProdDate);
                    ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                    if(rs_Previousday_EntryCheck.next())
                    {
                        count = rs_Previousday_EntryCheck.getInt(1);
                        if(BuildConfig.DMODE)
                            System.out.println("Count inside prod :"+count);
                        if(count==0)
                        {
                            throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                        }
                        else
                        {
                            
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                            
                            objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
                            
                            Vector vec_MachineResult = new Vector();
                            PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                            ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                            
                            while(rs_Valid_Machines.next())
                            {
                                Vector vec_ShiftResult = new Vector();
                                ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                
                                String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                String mcName = rs_Valid_Machines.getString("MC_NAME");
                                objProductionAccountingMachineDetails.setMcName(mcName);
                                objProductionAccountingMachineDetails.setMcCode(mcCode);
                                
                                ht_ProdDate.put("MC_CDE",mcCode);
                                PreparedStatement ps_Valid_Shifts = con.executeStatement(SQLMaster.VALID_SHIFTS_SELECT_SQL_QUERY);
                                ResultSet rs_Valid_Shifts = ps_Valid_Shifts.executeQuery();
                                while(rs_Valid_Shifts.next())
                                {
                                    int shift_Id = rs_Valid_Shifts.getInt("SHIFT_ID");
                                    ht_ProdDate.put("SHIFT_ID",new Integer(shift_Id));
                                    PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_ProdDate);
                                    ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                    if(rs_Prod_Accounting.next())
                                    {
                                        int check = rs_Prod_Accounting.getInt(1);
                                        if(BuildConfig.DMODE)
                                        {
                                            System.out.println("check "+check);
                                            System.out.println("shiftId "+shift_Id);
                                            System.out.println("Mc code "+mcCode);
                                        }
                                        prodHrs = 0;
                                        nprodHrs = 0;
                                        
                                        if(check==0)
                                        {  //if(check==0)
                                            PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_ProdDate);
                                            ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                            if(rs_ProdHrs_Select.next())
                                            {
                                                prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                                nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                            }
                                            
                                            rs_ProdHrs_Select.close();
                                            ps_ProdHrs_Select.close();
                                            
                                            //to calculate the available hrs..
                                            String startHrs = "";
                                            String startMinute = "";
                                            String endHrs = "";
                                            String endMinute = "";
                                            
                                            GregorianCalendar gc = new GregorianCalendar();
                                            PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_ProdDate);
                                            int cnt = 0;
                                            ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                            if(rs_CheckAvblty.next())
                                                cnt = rs_CheckAvblty.getInt(1);
                                            
                                            rs_CheckAvblty.close();
                                            ps_CheckAvblty.close();
                                            String shiftName = "";
                                            if(cnt >0)
                                            {
                                                
                                                PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_ProdDate);
                                                ResultSet rs = ps.executeQuery();
                                                if(rs.next())
                                                {
                                                    startHrs = "";
                                                    startMinute = "";
                                                    endHrs = "";
                                                    endMinute = "";
                                                    availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                    availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                    StringTokenizer st;
                                                    st = new StringTokenizer(availStartTime,":");
                                                    if(st.hasMoreTokens())
                                                    {
                                                        startHrs = st.nextToken();
                                                    }
                                                    if(st.hasMoreTokens())
                                                    {
                                                        startMinute = st.nextToken();
                                                    }
                                                    st = new StringTokenizer(availEndTime,":");
                                                    if(st.hasMoreTokens())
                                                    {
                                                        endHrs = st.nextToken();
                                                    }
                                                    if(st.hasMoreTokens())
                                                    {
                                                        endMinute = st.nextToken();
                                                    }
                                                    
                                                    String startTime = startHrs+startMinute;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("stTime"+startTime);
                                                    String endTime = endHrs+endMinute;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("endTime"+endTime);
                                                    int diff = 0;
                                                    int i = Integer.parseInt(startTime);
                                                    int j = Integer.parseInt(endTime);
                                                    if(i<j)
                                                        diff = j - i;
                                                    else
                                                        diff = (2400-i)+j;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("diff:"+diff);
                                                    float min = diff%100;
                                                    float hr = diff/100;
                                                    if(BuildConfig.DMODE)
                                                    {
                                                        System.out.println("min"+min);
                                                        System.out.println("hr"+hr);
                                                    }
                                                    min = (min/60);
                                                    if(BuildConfig.DMODE)
                                                        System.out.println(min);
                                                    availShiftHrs = hr + min;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("availShiftHRs :"+availShiftHrs);
                                                    
                                                    //to select the shiftName...
                                                    PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_ProdDate);
                                                    ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                    if(rs_ShiftNameSel.next())
                                                    {
                                                        shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                    }
                                                    
                                                }
                                                rs.close();
                                                ps.close();
                                                
                                            }
                                            else
                                            {
                                                
                                                
                                                gc.setTime(prodDate);
                                                int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                                
                                                ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shift_Id));
                                                ht_AvailHrs_Get.put("DAY",new Integer(day));
                                                
                                                PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                                ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                                //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                                if(rs_AvailHrs_Get.next())
                                                {
                                                    startHrs = "";
                                                    startMinute = "";
                                                    endHrs = "";
                                                    endMinute = "";
                                                    availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                    availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                    StringTokenizer st;
                                                    st = new StringTokenizer(availStartTime,":");
                                                    if(st.hasMoreTokens())
                                                    {
                                                        startHrs = st.nextToken();
                                                    }
                                                    if(st.hasMoreTokens())
                                                    {
                                                        startMinute = st.nextToken();
                                                    }
                                                    st = new StringTokenizer(availEndTime,":");
                                                    if(st.hasMoreTokens())
                                                    {
                                                        endHrs = st.nextToken();
                                                    }
                                                    if(st.hasMoreTokens())
                                                    {
                                                        endMinute = st.nextToken();
                                                    }
                                                    
                                                    String startTime = startHrs+startMinute;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("stTime"+startTime);
                                                    String endTime = endHrs+endMinute;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("endTime"+endTime);
                                                    int diff = 0;
                                                    int i = Integer.parseInt(startTime);
                                                    int j = Integer.parseInt(endTime);
                                                    if(i<j)
                                                        diff = j - i;
                                                    else
                                                        diff = (2400-i)+j;
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("diff:"+diff);
                                                    float min = diff%100;
                                                    float hr = diff/100;
                                                    if(BuildConfig.DMODE)
                                                    {
                                                        System.out.println("min"+min);
                                                        System.out.println("hr"+hr);
                                                    }
                                                    min = (min/60);
                                                    if(BuildConfig.DMODE)
                                                        System.out.println(min);
                                                    availShiftHrs = hr + min;
                                                    
                                                }
                                                
                                                rs_AvailHrs_Get.close();
                                                ps_AvailHrs_Get.close();
                                                
                                                if(BuildConfig.DMODE)
                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                
                                                //to select the shiftName...
                                                PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_ProdDate);
                                                ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                
                                                if(rs_ShiftNameSel.next())
                                                {
                                                    shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                }
                                                
                                                rs_ShiftNameSel.close();
                                                ps_ShiftNameSel.close();
                                                
                                            }
                                            ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                            
                                            availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                            objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                            objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                            objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                            objProductionAccountingShiftDetails.setShiftName(shiftName);
                                            vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                            
                                            
                                            
                                        }//if(check==0) end
                                        
                                    }
                                    rs_Prod_Accounting.close();
                                    ps_Prod_Accounting.close();
                                }
                                rs_Valid_Shifts.close();
                                ps_Valid_Shifts.close();
                                objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                vec_MachineResult.add(objProductionAccountingMachineDetails);
                            }
                            rs_Valid_Machines.close();
                            ps_Valid_Machines.close();
                            objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                            
                            
                            
                        }
                    }
                    rs_Previousday_EntryCheck.close();
                    ps_Previousday_EntryCheck.close();
                }
                
            }
            rs_ProdDate_PostCheck.close();
            ps_ProdDate_PostCheck.close();
            
            if(checkRESULT)
            {
                PreparedStatement ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_RADL_DATE_SQL_QUERY,ht_ProdDate);
                ResultSet rs_PostCheck = ps_PostCheck.executeQuery();
                if(rs_PostCheck.next())
                {
                    int count = rs_PostCheck.getInt(1);
                    if(BuildConfig.DMODE)
                        System.out.println("Count in rdlprod :"+count);
                    if(count==0)
                    {
                        checkRESULT = true;
                        /*  PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_ProdDate);
                         ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                         if(rs_Previousday_EntryCheck.next())
                         {
                         count = rs_Previousday_EntryCheck.getInt(1);
                         System.out.println("Count inside rdlprod :"+count);
                         if(count==0 && prodFlg)
                         {
                         throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                         }
                         if(count>0)
                         {
                         radlFlg = false;
                         }
                         }
                         rs_Previousday_EntryCheck.close();
                         ps_Previousday_EntryCheck.close();
                         */
                    }
                    else
                    {
                        checkRESULT = false;
                    }
                }
                rs_PostCheck.close();
                ps_PostCheck.close();
                if(checkRESULT)
                {
                    ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_IN_NPROD_SQL_QUERY,ht_ProdDate);
                    rs_PostCheck = ps_PostCheck.executeQuery();
                    if(rs_PostCheck.next())
                    {
                        int count = rs_PostCheck.getInt(1);
                        if(BuildConfig.DMODE)
                            System.out.println("Count in nprod :"+count);
                        if(count==0)
                        {
                            checkRESULT = true;
                            /*    PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_ProdDate);
                             ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                             if(rs_Previousday_EntryCheck.next())
                             {
                             count = rs_Previousday_EntryCheck.getInt(1);
                             System.out.println("Count inside nprod :"+count);
                             if(count==0&&prodFlg&&radlFlg)
                             {
                             throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                             }
                             if(count>0)
                             {
                             nprodFlg = false;
                             }
                             }
                             rs_Previousday_EntryCheck.close();
                             ps_Previousday_EntryCheck.close();
                             
                             */
                        }
                        else
                        {
                            checkRESULT = false;
                        }
                    }
                    rs_PostCheck.close();
                    ps_PostCheck.close();
                    if(checkRESULT)
                    {
                        ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_IN_POP_SQL_QUERY,ht_ProdDate);
                        rs_PostCheck = ps_PostCheck.executeQuery();
                        if(rs_PostCheck.next())
                        {
                            int count = rs_PostCheck.getInt(1);
                            if(count==0)
                            {
                                checkRESULT = true;
                                /*PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_ProdDate);
                                 ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                                 if(rs_Previousday_EntryCheck.next())
                                 {
                                 count = rs_Previousday_EntryCheck.getInt(1);
                                 if(count==0&&prodFlg&&radlFlg&&nprodFlg)
                                 {
                                 throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                                 }
                                 
                                 }
                                 rs_Previousday_EntryCheck.close();
                                 ps_Previousday_EntryCheck.close();
                                 */
                            }
                            else
                            {
                                checkRESULT = false;
                            }
                        }
                        rs_PostCheck.close();
                        ps_PostCheck.close();
                        
                        if(checkRESULT)
                        {
                            ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_IN_FPROD_SQL_QUERY,ht_ProdDate);
                            rs_PostCheck = ps_PostCheck.executeQuery();
                            if(rs_PostCheck.next())
                            {
                                int count = rs_PostCheck.getInt(1);
                                if(count==0)
                                {
                                    checkRESULT = true;
                                }
                                else
                                {
                                    checkRESULT = false;
                                }
                            }
                            if(!checkRESULT)
                            {
                                throw new ProductionException("PMEC014","FINAL PRODUCTION ENTRY NOT POSTED","");
                            }
                            
                            
                        }
                        else
                        {
                            throw new ProductionException("PMEC009","PAY OUTSIDE PRODUCTION ENTRY NOT POSTED","");
                        }
                        
                    }
                    else
                    {
                        throw new ProductionException("PMEC010","NON PRODUCTION ENTRY NOT POSTED","");
                    }
                }
                else
                {
                    throw new ProductionException("PMEC012","RADIAL PRODUCTION ENTRY NOT POSTED","");
                }
                
            }
            else
            {
                throw new ProductionException("PMEC013","PRODUCTION ENTRY NOT POSTED","");
            }
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    
    public ProductionAccountingDateDetails checkPostingByMachineAndDate(String mcCde,Date prodDate) throws ProductionException, SQLException
    {
        boolean checkRESULT = false;
        DBConnection con=null;
        Hashtable ht_McAndDate = new Hashtable();
        Hashtable ht_ProdDay = new Hashtable();
        String mcTypName = "";
        ProductionAccountingDateDetails objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        boolean rdlFlg = false;
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            while(true)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(prodDate);
                gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
                prodDate = gc.getTime();
                ht_McAndDate.put("PROD_CRNT_DATE",prodDate);
                
                //to check Custom_Non_Avblty table
                PreparedStatement ps_Date_NACheck = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_McAndDate);
                ResultSet rs_Date_NACheck = ps_Date_NACheck.executeQuery();
                if(rs_Date_NACheck.next())
                {
                    int count = rs_Date_NACheck.getInt(1);
                    if(count==0)
                    {
                        
                        //to check in custom availability...
                        PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McAndDate);
                        ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                        if(rs_Date_AvbCheck.next())
                        {
                            if(rs_Date_AvbCheck.getInt(1) != 0)
                            {
                                break;
                            }
                        }
                        
                        rs_Date_AvbCheck.close();
                        ps_Date_AvbCheck.close();
                        
                        
                        //						//to check Base_Cal
                        gc.setTime(prodDate);
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        ht_ProdDay.put("DAY",new Integer(day));
                        PreparedStatement ps_Date_BCCheck = con.executeStatement(SQLMaster.CHECK_DAY_IN_BASECAL_TABL_SQL_QUERY,ht_ProdDay);
                        ResultSet rs_Date_BCCheck = ps_Date_BCCheck.executeQuery();
                        if(rs_Date_BCCheck.next())
                        {
                            int totRows = rs_Date_BCCheck.getInt(1);
                            if(totRows!=0)
                            {
                                break;
                            }
                        }
                        rs_Date_BCCheck.close();
                        ps_Date_BCCheck.close();
                    }
                    
                }
                rs_Date_NACheck.close();
                ps_Date_NACheck.close();
            }
            ht_McAndDate.put("MC_CDE",mcCde);
            // to check given machine is radl machine or normal..
            PreparedStatement ps_McTyp_Name = con.executeStatement(SQLMaster.MC_TYP_NAME_SELECT_SQL_QUERY,ht_McAndDate);
            ResultSet rs_McTyp_Name = ps_McTyp_Name.executeQuery();
            if(rs_McTyp_Name.next())
            {
                mcTypName = rs_McTyp_Name.getString("MC_TYP_NAME");
            }
            rs_McTyp_Name.close();
            ps_McTyp_Name.close();
            
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
            
            if(!mcTypName.equalsIgnoreCase("radial"))
            {
                PreparedStatement ps_Prod_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_AND_DATE_SQL_QUERY,ht_McAndDate);
                ResultSet rs_Prod_PostCheck = ps_Prod_PostCheck.executeQuery();
                
                if(rs_Prod_PostCheck.next())
                {
                    
                    int count = rs_Prod_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                        PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_McAndDate);
                        ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                        if(rs_Previousday_EntryCheck.next())
                        {
                            count = rs_Previousday_EntryCheck.getInt(1);
                            if(count==0)
                            {
                                throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                            }
                            else
                            {
                                PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                                ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                                Vector vec_MachineResult = new Vector();
                                while(rs_Valid_Machines.next())
                                {
                                    Vector vec_ShiftResult = new Vector();
                                    ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                    String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                    String mcName = rs_Valid_Machines.getString("MC_NAME");
                                    objProductionAccountingMachineDetails.setMcName(mcName);
                                    objProductionAccountingMachineDetails.setMcCode(mcCode);
                                    ht_McAndDate.put("MC_CDE",mcCode);
                                    PreparedStatement ps_Valid_Shifts = con.executeStatement(SQLMaster.VALID_SHIFTS_SELECT_SQL_QUERY);
                                    ResultSet rs_Valid_Shifts = ps_Valid_Shifts.executeQuery();
                                    while(rs_Valid_Shifts.next())
                                    {
                                        int shift_Id = rs_Valid_Shifts.getInt("SHIFT_ID");
                                        ht_McAndDate.put("SHIFT_ID",new Integer(shift_Id));
                                        PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_McAndDate);
                                        ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                        if(rs_Prod_Accounting.next())
                                        {
                                            int check = rs_Prod_Accounting.getInt(1);
                                            if(BuildConfig.DMODE)
                                            {
                                                System.out.println("check "+check);
                                                System.out.println("shiftId "+shift_Id);
                                                System.out.println("Mc code "+mcCode);
                                            }
                                            prodHrs = 0;
                                            nprodHrs = 0;
                                            
                                            if(check==0)
                                            {  //if(check==0)
                                                PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_McAndDate);
                                                ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                                if(rs_ProdHrs_Select.next())
                                                {
                                                    prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                                    nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                                }
                                                
                                                rs_ProdHrs_Select.close();
                                                ps_ProdHrs_Select.close();
                                                
                                                //to calculate the available hrs..
                                                String startHrs = "";
                                                String startMinute = "";
                                                String endHrs = "";
                                                String endMinute = "";
                                                
                                                GregorianCalendar gc = new GregorianCalendar();
                                                PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McAndDate);
                                                int cnt = 0;
                                                ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                                if(rs_CheckAvblty.next())
                                                    cnt = rs_CheckAvblty.getInt(1);
                                                
                                                rs_CheckAvblty.close();
                                                ps_CheckAvblty.close();
                                                String shiftName = "";
                                                if(cnt >0)
                                                {
                                                    
                                                    PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_McAndDate);
                                                    ResultSet rs = ps.executeQuery();
                                                    if(rs.next())
                                                    {
                                                        startHrs = "";
                                                        startMinute = "";
                                                        endHrs = "";
                                                        endMinute = "";
                                                        availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                        availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                        StringTokenizer st;
                                                        st = new StringTokenizer(availStartTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startMinute = st.nextToken();
                                                        }
                                                        st = new StringTokenizer(availEndTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endMinute = st.nextToken();
                                                        }
                                                        
                                                        String startTime = startHrs+startMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("stTime"+startTime);
                                                        String endTime = endHrs+endMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("endTime"+endTime);
                                                        int diff = 0;
                                                        int i = Integer.parseInt(startTime);
                                                        int j = Integer.parseInt(endTime);
                                                        if(i<j)
                                                            diff = j - i;
                                                        else
                                                            diff = (2400-i)+j;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("diff:"+diff);
                                                        float min = diff%100;
                                                        float hr = diff/100;
                                                        if(BuildConfig.DMODE)
                                                        {
                                                            System.out.println("min"+min);
                                                            System.out.println("hr"+hr);
                                                        }
                                                        min = (min/60);
                                                        if(BuildConfig.DMODE)
                                                            System.out.println(min);
                                                        availShiftHrs = hr + min;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("availShiftHRs :"+availShiftHrs);
                                                        
                                                        //to select the shiftName...
                                                        PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McAndDate);
                                                        ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                        
                                                        if(rs_ShiftNameSel.next())
                                                        {
                                                            shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                        }
                                                        
                                                        rs_ShiftNameSel.close();
                                                        ps_ShiftNameSel.close();
                                                        
                                                        
                                                    }
                                                    rs.close();
                                                    ps.close();
                                                    
                                                }
                                                else
                                                {
                                                    
                                                    
                                                    gc.setTime(prodDate);
                                                    int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                                    
                                                    ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shift_Id));
                                                    ht_AvailHrs_Get.put("DAY",new Integer(day));
                                                    
                                                    PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                                    ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                                    //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                                    if(rs_AvailHrs_Get.next())
                                                    {
                                                        startHrs = "";
                                                        startMinute = "";
                                                        endHrs = "";
                                                        endMinute = "";
                                                        availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                        availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                        StringTokenizer st;
                                                        st = new StringTokenizer(availStartTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            startMinute = st.nextToken();
                                                        }
                                                        st = new StringTokenizer(availEndTime,":");
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endHrs = st.nextToken();
                                                        }
                                                        if(st.hasMoreTokens())
                                                        {
                                                            endMinute = st.nextToken();
                                                        }
                                                        
                                                        String startTime = startHrs+startMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("stTime"+startTime);
                                                        String endTime = endHrs+endMinute;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("endTime"+endTime);
                                                        int diff = 0;
                                                        int i = Integer.parseInt(startTime);
                                                        int j = Integer.parseInt(endTime);
                                                        if(i<j)
                                                            diff = j - i;
                                                        else
                                                            diff = (2400-i)+j;
                                                        if(BuildConfig.DMODE)
                                                            System.out.println("diff:"+diff);
                                                        float min = diff%100;
                                                        float hr = diff/100;
                                                        if(BuildConfig.DMODE)
                                                        {
                                                            System.out.println("min"+min);
                                                            System.out.println("hr"+hr);
                                                        }
                                                        min = (min/60);
                                                        if(BuildConfig.DMODE)
                                                            System.out.println(min);
                                                        availShiftHrs = hr + min;
                                                        
                                                    }
                                                    
                                                    rs_AvailHrs_Get.close();
                                                    ps_AvailHrs_Get.close();
                                                    
                                                    if(BuildConfig.DMODE)
                                                        System.out.println("availShiftHRs :"+availShiftHrs);
                                                    
                                                    //to select the shiftName...
                                                    PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McAndDate);
                                                    ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                    
                                                    if(rs_ShiftNameSel.next())
                                                    {
                                                        shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                    }
                                                    
                                                    rs_ShiftNameSel.close();
                                                    ps_ShiftNameSel.close();
                                                    
                                                    
                                                }
                                                
                                                ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                                availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                                objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                                
                                                objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                                objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                                objProductionAccountingShiftDetails.setShiftName(shiftName);
                                                vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                                
                                                
                                            }//if(check==0) end
                                            
                                        }
                                        rs_Prod_Accounting.close();
                                        ps_Prod_Accounting.close();
                                    }
                                    rs_Valid_Shifts.close();
                                    ps_Valid_Shifts.close();
                                    objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                    vec_MachineResult.add(objProductionAccountingMachineDetails);
                                }
                                rs_Valid_Machines.close();
                                ps_Valid_Machines.close();
                                objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                                
                            }
                        }
                        rs_Previousday_EntryCheck.close();
                        ps_Previousday_EntryCheck.close();
                    }
                }
                rs_Prod_PostCheck.close();
                ps_Prod_PostCheck.close();
                
                
            }
            else
            {
                PreparedStatement ps_Prod_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_RADL_MC_AND_DATE_SQL_QUERY,ht_McAndDate);
                ResultSet rs_Prod_PostCheck = ps_Prod_PostCheck.executeQuery();
                
                if(rs_Prod_PostCheck.next())
                {
                    
                    int count = rs_Prod_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                        PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_McAndDate);
                        ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                        if(rs_Previousday_EntryCheck.next())
                        {
                            count = rs_Previousday_EntryCheck.getInt(1);
                            if(count==0)
                            {
                                throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                            }
                            else
                            {
                                
                                PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                                ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                                
                                Vector vec_MachineResult = new Vector();
                                while(rs_Valid_Machines.next())
                                {
                                    Vector vec_ShiftResult = new Vector();
                                    ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                    
                                    String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                    String mcName = rs_Valid_Machines.getString("MC_NAME");
                                    objProductionAccountingMachineDetails.setMcName(mcName);
                                    objProductionAccountingMachineDetails.setMcCode(mcCode);
                                    ht_McAndDate.put("MC_CDE",mcCode);
                                    PreparedStatement ps_Valid_Shifts = con.executeStatement(SQLMaster.VALID_SHIFTS_SELECT_SQL_QUERY);
                                    ResultSet rs_Valid_Shifts = ps_Valid_Shifts.executeQuery();
                                    while(rs_Valid_Shifts.next())
                                    {
                                        String shiftName = "";
                                        int shift_Id = rs_Valid_Shifts.getInt("SHIFT_ID");
                                        //shiftName = rs_Valid_Shifts.getString("SHIFT_NAME");
                                        ht_McAndDate.put("SHIFT_ID",new Integer(shift_Id));
                                        PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_McAndDate);
                                        ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                        if(rs_Prod_Accounting.next())
                                        {
                                            int check = rs_Prod_Accounting.getInt(1);
                                            if(BuildConfig.DMODE)
                                            {
                                                System.out.println("check "+check);
                                                System.out.println("shiftId "+shift_Id);
                                                System.out.println("Mc code "+mcCode);
                                            }
                                            prodHrs = 0;
                                            nprodHrs = 0;
                                            
                                            if(check==0)
                                            {  //if(check==0)
                                                PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_McAndDate);
                                                ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                                if(rs_ProdHrs_Select.next())
                                                {
                                                    prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                                    nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                                    
                                                }
                                                rs_ProdHrs_Select.close();
                                                ps_ProdHrs_Select.close();
                                                
                                                
                                                //to calculate the available hrs..
                                                String startHrs = "";
                                                String startMinute = "";
                                                String endHrs = "";
                                                String endMinute = "";
                                                
                                                GregorianCalendar gc = new GregorianCalendar();
                                                PreparedStatement ps =  con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_McAndDate);
                                                ResultSet rs = ps.executeQuery();
                                                if(rs.next())
                                                {
                                                    int counts = rs.getInt(1);
                                                    if(counts<=0)
                                                    {
                                                        PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_McAndDate);
                                                        int cnt = 0;
                                                        ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                                        if(rs_CheckAvblty.next())
                                                            cnt = rs_CheckAvblty.getInt(1);
                                                        rs_CheckAvblty.close();
                                                        ps_CheckAvblty.close();
                                                        
                                                        if(cnt >0)
                                                        {
                                                            
                                                            ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_McAndDate);
                                                            rs = ps.executeQuery();
                                                            if(rs.next())
                                                            {
                                                                startHrs = "";
                                                                startMinute = "";
                                                                endHrs = "";
                                                                endMinute = "";
                                                                availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                                availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                                StringTokenizer st;
                                                                st = new StringTokenizer(availStartTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startMinute = st.nextToken();
                                                                }
                                                                st = new StringTokenizer(availEndTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endMinute = st.nextToken();
                                                                }
                                                                
                                                                String startTime = startHrs+startMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("stTime"+startTime);
                                                                String endTime = endHrs+endMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("endTime"+endTime);
                                                                int diff = 0;
                                                                int i = Integer.parseInt(startTime);
                                                                int j = Integer.parseInt(endTime);
                                                                if(i<j)
                                                                    diff = j - i;
                                                                else
                                                                    diff = (2400-i)+j;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("diff:"+diff);
                                                                float min = diff%100;
                                                                float hr = diff/100;
                                                                if(BuildConfig.DMODE)
                                                                {
                                                                    System.out.println("min"+min);
                                                                    System.out.println("hr"+hr);
                                                                }
                                                                min = (min/60);
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println(min);
                                                                availShiftHrs = hr + min;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                                
                                                                //to select the shiftName...
                                                                PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McAndDate);
                                                                ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                                if(rs_ShiftNameSel.next())
                                                                {
                                                                    shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                                }
                                                                
                                                            }
                                                            else
                                                            {
                                                                availShiftHrs = 0;
                                                            }
                                                            rs.close();
                                                            ps.close();
                                                            if(BuildConfig.DMODE)
                                                            {
                                                                //   System.out.println("table ProdHrs :"+tableProdHrs);
                                                                //   System.out.println("table NProdHrs :"+tableNprodHrs);
                                                                System.out.println("ProdHrs :"+prodHrs);
                                                                System.out.println("nProdHrs :"+nprodHrs);
                                                            }
                                                        }
                                                        else
                                                        {
                                                            
                                                            
                                                            gc.setTime(prodDate);
                                                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                                            
                                                            ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shift_Id));
                                                            ht_AvailHrs_Get.put("DAY",new Integer(day));
                                                            
                                                            PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                                            ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                                            //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                                            if(rs_AvailHrs_Get.next())
                                                            {
                                                                startHrs = "";
                                                                startMinute = "";
                                                                endHrs = "";
                                                                endMinute = "";
                                                                availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                                availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                                StringTokenizer st;
                                                                st = new StringTokenizer(availStartTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    startMinute = st.nextToken();
                                                                }
                                                                st = new StringTokenizer(availEndTime,":");
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endHrs = st.nextToken();
                                                                }
                                                                if(st.hasMoreTokens())
                                                                {
                                                                    endMinute = st.nextToken();
                                                                }
                                                                
                                                                String startTime = startHrs+startMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("stTime"+startTime);
                                                                String endTime = endHrs+endMinute;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("endTime"+endTime);
                                                                int diff = 0;
                                                                int i = Integer.parseInt(startTime);
                                                                int j = Integer.parseInt(endTime);
                                                                if(i<j)
                                                                    diff = j - i;
                                                                else
                                                                    diff = (2400-i)+j;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("diff:"+diff);
                                                                float min = diff%100;
                                                                float hr = diff/100;
                                                                if(BuildConfig.DMODE)
                                                                {
                                                                    System.out.println("min"+min);
                                                                    System.out.println("hr"+hr);
                                                                }
                                                                min = (min/60);
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println(min);
                                                                availShiftHrs = hr + min;
                                                                if(BuildConfig.DMODE)
                                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                                //to select the shiftName...
                                                                PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_McAndDate);
                                                                ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                                if(rs_ShiftNameSel.next())
                                                                {
                                                                    shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                                }
                                                                
                                                            }
                                                            rs_AvailHrs_Get.close();
                                                            ps_AvailHrs_Get.close();
                                                        }
                                                    }
                                                }
                                                rs.close();
                                                ps.close();
                                                
                                                availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                                ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                                objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                                objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                                objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                                objProductionAccountingShiftDetails.setShiftName(shiftName);
                                                vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                            }//if(check==0) end
                                            
                                        }
                                        rs_Prod_Accounting.close();
                                        ps_Prod_Accounting.close();
                                    }
                                    rs_Valid_Shifts.close();
                                    ps_Valid_Shifts.close();
                                    
                                    objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                    vec_MachineResult.add(objProductionAccountingMachineDetails);
                                }
                                
                                rs_Valid_Machines.close();
                                ps_Valid_Machines.close();
                                
                                objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                                
                                
                                
                            }
                        }
                        rs_Previousday_EntryCheck.close();
                        ps_Previousday_EntryCheck.close();
                        
                    }
                }
                rs_Prod_PostCheck.close();
                ps_Prod_PostCheck.close();
                
                rdlFlg = true;
                
            }
            if(checkRESULT)
            {
                PreparedStatement ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_AND_DATE_IN_NPROD_SQL_QUERY,ht_McAndDate);
                ResultSet rs_PostCheck = ps_PostCheck.executeQuery();
                if(rs_PostCheck.next())
                {
                    int count = rs_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                    }
                    else
                    {
                        checkRESULT = false;
                    }
                }
                rs_PostCheck.close();
                ps_PostCheck.close();
                /*if(checkRESULT)
                 {
                 ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_AND_DATE_IN_POP_SQL_QUERY,ht_McAndDate);
                 rs_PostCheck = ps_PostCheck.executeQuery();
                 
                 if(rs_PostCheck.next())
                 {
                 int count = rs_PostCheck.getInt(1);
                 if(count==0)
                 {
                 checkRESULT = true;
                 }
                 else
                 {
                 checkRESULT = false;
                 }
                 }
                 rs_PostCheck.close();
                 ps_PostCheck.close();
                 if(!checkRESULT)
                 {
                 throw new ProductionException("PMEC009","PAY OUTSIDE PRODUCTION ENTRY NOT POSTED","");
                 }
                 
                 }
                 else*/
                if(!checkRESULT)
                {
                    throw new ProductionException("PMEC010","NON PRODUCTION ENTRY NOT POSTED","");
                }
            }
            else if(rdlFlg)
            {
                throw new ProductionException("PMEC011","RADL PRODUCTION ENTRY NOT POSTED","");
            }
            else
            {
                throw new ProductionException("PMEC011","PRODUCTION ENTRY NOT POSTED","");
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    public ProductionAccountingDateDetails checkPostingByDateAndShift(Date prodDate,int shiftId) throws ProductionException, ParseException, SQLException
    {
        boolean checkRESULT = false;
        DBConnection con=null;
        int predsrShift = 0;
        Hashtable ht_DateAndShift = new Hashtable();
        Hashtable ht_DayAndShift = new Hashtable();
        Hashtable ht_ProdDay = new Hashtable();
        
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        ProductionAccountingDateDetails objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        
        boolean flg = false;
        try
        {
            con = DBConnectionFactory.getConnection();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(prodDate);
            int prodDay = gc.get(GregorianCalendar.DAY_OF_WEEK);
            
            
            ht_DayAndShift.put("DAY",new Integer(prodDay));
            ht_DayAndShift.put("SHIFT_ID",new Integer(shiftId));
            PreparedStatement ps_PredsrShift_check = con.executeStatement(SQLMaster.PREDSR_SHIFT_SELECT_SQL_QUERY,ht_DayAndShift);
            ResultSet rs_PredsrShift_check = ps_PredsrShift_check.executeQuery();
            if(rs_PredsrShift_check.next())
            {
                predsrShift = rs_PredsrShift_check.getInt("BASE_CAL_VAR_PREDSRSHIFT");
                if(predsrShift==0)
                {
                    flg = true;
                }
            }
            rs_PredsrShift_check.close();
            ps_PredsrShift_check.close();
            if(flg)
            {
                while(true)
                {
                    gc.setTime(prodDate);
                    gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
                    prodDate = gc.getTime();
                    ht_DateAndShift.put("PROD_CRNT_DATE",prodDate);
                    
                    //to check Custom_Non_Avblty table
                    
                    PreparedStatement ps_Date_NACheck = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_DateAndShift);
                    ResultSet rs_Date_NACheck = ps_Date_NACheck.executeQuery();
                    if(rs_Date_NACheck.next())
                    {
                        int count = rs_Date_NACheck.getInt(1);
                        if(count==0)
                        {
                            
                            //to check in custom availability...
                            PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_DateAndShift);
                            ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                            if(rs_Date_AvbCheck.next())
                            {
                                if(rs_Date_AvbCheck.getInt(1) != 0)
                                {
                                    break;
                                }
                            }
                            
                            
                            //						//to check Base_Cal
                            gc.setTime(prodDate);
                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                            ht_ProdDay.put("DAY",new Integer(day));
                            PreparedStatement ps_Date_BCCheck = con.executeStatement(SQLMaster.CHECK_DAY_IN_BASECAL_TABL_SQL_QUERY,ht_ProdDay);
                            ResultSet rs_Date_BCCheck = ps_Date_BCCheck.executeQuery();
                            if(rs_Date_BCCheck.next())
                            {
                                int totRows = rs_Date_BCCheck.getInt(1);
                                if(totRows!=0)
                                {
                                    break;
                                }
                            }
                            rs_Date_BCCheck.close();
                            ps_Date_BCCheck.close();
                        }
                        
                    }
                    rs_Date_NACheck.close();
                    ps_Date_NACheck.close();
                }
                //to find predsrShift i.e.last shift of the valid previous day..
                Hashtable ht_Day = new Hashtable();
                gc.setTime(prodDate);
                
                PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_DateAndShift);
                ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                if(rs_Date_AvbCheck.next())
                {
                    if(rs_Date_AvbCheck.getInt(1) == 0)
                    {
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        ht_Day.put("DAY",new Integer(day));
                        PreparedStatement ps_LastShift = con.executeStatement(SQLMaster.LAST_SHIFT_OF_A_DAY_SELECT_SQL_QUERY,ht_Day);
                        ResultSet rs_LastShift = ps_LastShift.executeQuery();
                        if(rs_LastShift.next())
                        {
                            predsrShift = rs_LastShift.getInt("SHIFT_ID");
                        }
                        rs_LastShift.close();
                        ps_LastShift.close();
                        
                    }
                    else
                    {
                        PreparedStatement ps_LastShift = con.executeStatement(SQLMaster.LAST_SHIFT_OF_A_DAY_IN_CUSTOM_AVBLTY_SELECT_SQL_QUERY,ht_DateAndShift);
                        ResultSet rs_LastShift = ps_LastShift.executeQuery();
                        if(rs_LastShift.next())
                        {
                            predsrShift = rs_LastShift.getInt("SHIFT_ID");
                        }
                        rs_LastShift.close();
                        ps_LastShift.close();
                        
                        
                    }
                }
                
                rs_Date_AvbCheck.close();
                ps_Date_AvbCheck.close();
                
                
                
                
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
            ht_DateAndShift.put("PROD_CRNT_DATE",prodDate);
            ht_DateAndShift.put("SHIFT_ID",new Integer(predsrShift));
            PreparedStatement ps_ProdDate_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_SHIFT_SQL_QUERY,ht_DateAndShift);
            ResultSet rs_ProdDate_PostCheck = ps_ProdDate_PostCheck.executeQuery();
            if(rs_ProdDate_PostCheck.next())
            {
                int count = rs_ProdDate_PostCheck.getInt(1);
                if(count==0)
                {
                    checkRESULT = true;
                    PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_DateAndShift);
                    ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                    if(rs_Previousday_EntryCheck.next())
                    {
                        count = rs_Previousday_EntryCheck.getInt(1);
                        if(count == 0)
                        {
                            throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                        }
                        else
                        {
                            PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                            ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                            Vector vec_MachineResult = new Vector();
                            while(rs_Valid_Machines.next())
                            {
                                Vector vec_ShiftResult = new Vector();
                                ProductionAccountingMachineDetails  objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                String mcName = rs_Valid_Machines.getString("MC_NAME");
                                objProductionAccountingMachineDetails.setMcName(mcName);
                                objProductionAccountingMachineDetails.setMcCode(mcCode);
                                ht_DateAndShift.put("MC_CDE",mcCode);
                                PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_DateAndShift);
                                ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                if(rs_Prod_Accounting.next())
                                {
                                    int check = rs_Prod_Accounting.getInt(1);
                                    if(BuildConfig.DMODE)
                                    {
                                        System.out.println("check "+check);
                                        System.out.println("Mc code "+mcCode);
                                    }
                                    prodHrs = 0;
                                    nprodHrs = 0;
                                    
                                    if(check==0)
                                    {  //if(check==0)
                                        PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_DateAndShift);
                                        ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                        if(rs_ProdHrs_Select.next())
                                        {
                                            prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                            nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                        }
                                        rs_ProdHrs_Select.close();
                                        ps_ProdHrs_Select.close();
                                        
                                        //to calculate the available hrs..
                                        String startHrs = "";
                                        String startMinute = "";
                                        String endHrs = "";
                                        String endMinute = "";
                                        
                                        PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_DateAndShift);
                                        int cnt = 0;
                                        ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                        if(rs_CheckAvblty.next())
                                            cnt = rs_CheckAvblty.getInt(1);
                                        
                                        rs_CheckAvblty.close();
                                        ps_CheckAvblty.close();
                                        String shiftName = "";
                                        if(cnt >0)
                                        {
                                            
                                            PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_DateAndShift);
                                            ResultSet rs = ps.executeQuery();
                                            if(rs.next())
                                            {
                                                startHrs = "";
                                                startMinute = "";
                                                endHrs = "";
                                                endMinute = "";
                                                availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                StringTokenizer st;
                                                st = new StringTokenizer(availStartTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    startHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    startMinute = st.nextToken();
                                                }
                                                st = new StringTokenizer(availEndTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    endHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    endMinute = st.nextToken();
                                                }
                                                
                                                String startTime = startHrs+startMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("stTime"+startTime);
                                                String endTime = endHrs+endMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("endTime"+endTime);
                                                int diff = 0;
                                                int i = Integer.parseInt(startTime);
                                                int j = Integer.parseInt(endTime);
                                                if(i<j)
                                                    diff = j - i;
                                                else
                                                    diff = (2400-i)+j;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("diff:"+diff);
                                                float min = diff%100;
                                                float hr = diff/100;
                                                if(BuildConfig.DMODE)
                                                {
                                                    System.out.println("min"+min);
                                                    System.out.println("hr"+hr);
                                                }
                                                min = (min/60);
                                                if(BuildConfig.DMODE)
                                                    System.out.println(min);
                                                availShiftHrs = hr + min;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                
                                                //to select the shiftName...
                                                PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_DateAndShift);
                                                ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                
                                                if(rs_ShiftNameSel.next())
                                                {
                                                    shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                }
                                                
                                                rs_ShiftNameSel.close();
                                                ps_ShiftNameSel.close();
                                                
                                                
                                                
                                                
                                            }
                                            rs.close();
                                            ps.close();
                                            
                                        }
                                        else
                                        {
                                            
                                            
                                            gc.setTime(prodDate);
                                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                            
                                            ht_AvailHrs_Get.put("SHIFT_ID",new Integer(predsrShift));
                                            ht_AvailHrs_Get.put("DAY",new Integer(day));
                                            
                                            PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                            ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                            //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                            if(rs_AvailHrs_Get.next())
                                            {
                                                startHrs = "";
                                                startMinute = "";
                                                endHrs = "";
                                                endMinute = "";
                                                availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                StringTokenizer st;
                                                st = new StringTokenizer(availStartTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    startHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    startMinute = st.nextToken();
                                                }
                                                st = new StringTokenizer(availEndTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    endHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    endMinute = st.nextToken();
                                                }
                                                
                                                String startTime = startHrs+startMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("stTime"+startTime);
                                                String endTime = endHrs+endMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("endTime"+endTime);
                                                int diff = 0;
                                                int i = Integer.parseInt(startTime);
                                                int j = Integer.parseInt(endTime);
                                                if(i<j)
                                                    diff = j - i;
                                                else
                                                    diff = (2400-i)+j;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("diff:"+diff);
                                                float min = diff%100;
                                                float hr = diff/100;
                                                if(BuildConfig.DMODE)
                                                {
                                                    System.out.println("min"+min);
                                                    System.out.println("hr"+hr);
                                                }
                                                min = (min/60);
                                                if(BuildConfig.DMODE)
                                                    System.out.println(min);
                                                availShiftHrs = hr + min;
                                                
                                            }
                                            rs_AvailHrs_Get.close();
                                            ps_AvailHrs_Get.close();
                                            
                                            if(BuildConfig.DMODE)
                                                System.out.println("availShiftHRs :"+availShiftHrs);
                                            
                                            //                                	                      //to select the shiftName...
                                            PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_DateAndShift);
                                            ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                            
                                            if(rs_ShiftNameSel.next())
                                            {
                                                shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                            }
                                            
                                            rs_ShiftNameSel.close();
                                            ps_ShiftNameSel.close();
                                            
                                            
                                            
                                        }
                                        
                                        ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                        
                                        availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                        objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                        
                                        objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                        objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                        objProductionAccountingShiftDetails.setShiftName(shiftName);
                                        vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                        
                                    }//if(check==0) end
                                    
                                }
                                
                                rs_Prod_Accounting.close();
                                ps_Prod_Accounting.close();
                                
                                objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                vec_MachineResult.add(objProductionAccountingMachineDetails);
                            }
                            rs_Valid_Machines.close();
                            ps_Valid_Machines.close();
                            objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                            
                            
                        }
                        
                    }
                    rs_Previousday_EntryCheck.close();
                    ps_Previousday_EntryCheck.close();
                    
                }
            }
            rs_ProdDate_PostCheck.close();
            ps_ProdDate_PostCheck.close();
            if(checkRESULT)
            {
                PreparedStatement ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_RADL_DATE_SHIFT_SQL_QUERY,ht_DateAndShift);
                ResultSet rs_PostCheck = ps_PostCheck.executeQuery();
                
                if(rs_PostCheck.next())
                {
                    int count = rs_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                        /*PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_DateAndShift);
                         ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                         if(rs_Previousday_EntryCheck.next())
                         {
                         count = rs_Previousday_EntryCheck.getInt(1);
                         if(count==0&&prodFlg)
                         {
                         throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                         }
                         if(count>0)
                         {
                         radlFlg = false;
                         }
                         }
                         rs_Previousday_EntryCheck.close();
                         ps_Previousday_EntryCheck.close();
                         */
                    }
                    else
                    {
                        checkRESULT = false;
                    }
                }
                rs_PostCheck.close();
                ps_PostCheck.close();
                if(checkRESULT)
                {
                    ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_SHIFT_IN_NPROD_SQL_QUERY,ht_DateAndShift);
                    rs_PostCheck = ps_PostCheck.executeQuery();
                    if(rs_PostCheck.next())
                    {
                        int count = rs_PostCheck.getInt(1);
                        if(count==0)
                        {
                            checkRESULT = true;
                            /*                            PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_DateAndShift);
                             ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                             if(rs_Previousday_EntryCheck.next())
                             {
                             count = rs_Previousday_EntryCheck.getInt(1);
                             if(count==0&&prodFlg&&radlFlg)
                             {
                             throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                             }
                             if(count>0)
                             {
                             nprodFlg = false;
                             }
                             }
                             rs_Previousday_EntryCheck.close();
                             ps_Previousday_EntryCheck.close();
                             */
                        }
                        else
                        {
                            checkRESULT = false;
                        }
                    }
                    rs_PostCheck.close();
                    ps_PostCheck.close();
                    if(checkRESULT)
                    {
                        ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_SHIFT_IN_POP_SQL_QUERY,ht_DateAndShift);
                        rs_PostCheck = ps_PostCheck.executeQuery();
                        
                        if(rs_PostCheck.next())
                        {
                            int count = rs_PostCheck.getInt(1);
                            if(count==0)
                            {
                                checkRESULT = true;
                                /*                                PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_DateAndShift);
                                 ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                                 if(rs_Previousday_EntryCheck.next())
                                 {
                                 count = rs_Previousday_EntryCheck.getInt(1);
                                 if(count==0&&prodFlg&&radlFlg&&nprodFlg)
                                 {
                                 throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                                 }
                                 }
                                 rs_Previousday_EntryCheck.close();
                                 ps_Previousday_EntryCheck.close();
                                 */
                            }
                            else
                            {
                                checkRESULT = false;
                            }
                        }
                        rs_PostCheck.close();
                        ps_PostCheck.close();
                        if(checkRESULT)
                        {
                            ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_DATE_SHIFT_IN_FPROD_SQL_QUERY,ht_DateAndShift);
                            rs_PostCheck = ps_PostCheck.executeQuery();
                            
                            if(rs_PostCheck.next())
                            {
                                int count = rs_PostCheck.getInt(1);
                                if(count==0)
                                {
                                    checkRESULT = true;
                                }
                                else
                                {
                                    checkRESULT = false;
                                }
                            }
                            rs_PostCheck.close();
                            ps_PostCheck.close();
                            
                            if(!checkRESULT)
                            {
                                throw new ProductionException("PMEC009","FINAL PRODUCTION ENTRY NOT POSTED","");
                            }
                            
                        }
                        else
                        {
                            throw new ProductionException("PMEC009","PAY OUTSIDE PRODUCTION ENTRY NOT POSTED","");
                        }
                        
                    }
                    else
                    {
                        throw new ProductionException("PMEC010","NON PRODUCTION ENTRY NOT POSTED","");
                    }
                }
                else
                {
                    throw new ProductionException("PMEC012","RADIAL PRODUCTION ENTRY NOT POSTED","");
                }
                
            }
            else
            {
                throw new ProductionException("PMEC013","PRODUCTION ENTRY NOT POSTED","");
            }
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    public ProductionAccountingDateDetails checkPostingByMachineAndDateAndShift(String mcCde,Date prodDate,int shiftId) throws ProductionException, SQLException
    {
        boolean checkRESULT = false;
        DBConnection con=null;
        int predsrShift = 0;
        // Hashtable ht_DateAndShift = new Hashtable();
        Hashtable ht_MachineDateAndShift = new Hashtable();
        Hashtable ht_DayAndShift = new Hashtable();
        Hashtable ht_ProdDay = new Hashtable();
        boolean flg = false;
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        ProductionAccountingDateDetails objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        try
        {
            con = DBConnectionFactory.getConnection();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(prodDate);
            int prodDay = gc.get(GregorianCalendar.DAY_OF_WEEK);
            ht_DayAndShift.put("DAY",new Integer(prodDay));
            ht_DayAndShift.put("SHIFT_ID",new Integer(shiftId));
            
            PreparedStatement ps_PredsrShift_check = con.executeStatement(SQLMaster.PREDSR_SHIFT_SELECT_SQL_QUERY,ht_DayAndShift);
            ResultSet rs_PredsrShift_check = ps_PredsrShift_check.executeQuery();
            if(rs_PredsrShift_check.next())
            {
                predsrShift = rs_PredsrShift_check.getInt("BASE_CAL_VAR_PREDSRSHIFT");
                if(predsrShift==0)
                {
                    flg = true;
                }
            }
            rs_PredsrShift_check.close();
            ps_PredsrShift_check.close();
            if(flg)
            {
                while(true)
                {
                    gc.setTime(prodDate);
                    gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
                    prodDate = gc.getTime();
                    ht_MachineDateAndShift.put("PROD_CRNT_DATE",prodDate);
                    
                    //to check Custom_Non_Avblty table
                    
                    PreparedStatement ps_Date_NACheck = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_MachineDateAndShift);
                    ResultSet rs_Date_NACheck = ps_Date_NACheck.executeQuery();
                    if(rs_Date_NACheck.next())
                    {
                        int count = rs_Date_NACheck.getInt(1);
                        if(count==0)
                        {
                            
                            //to check in custom availability...
                            PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_MachineDateAndShift);
                            ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                            if(rs_Date_AvbCheck.next())
                            {
                                if(rs_Date_AvbCheck.getInt(1) != 0)
                                {
                                    break;
                                }
                            }
                            
                            rs_Date_AvbCheck.close();
                            ps_Date_AvbCheck.close();
                            
                            
                            //						//to check Base_Cal
                            gc.setTime(prodDate);
                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                            ht_ProdDay.put("DAY",new Integer(day));
                            
                            PreparedStatement ps_Date_BCCheck = con.executeStatement(SQLMaster.CHECK_DAY_IN_BASECAL_TABL_SQL_QUERY,ht_ProdDay);
                            ResultSet rs_Date_BCCheck = ps_Date_BCCheck.executeQuery();
                            if(rs_Date_BCCheck.next())
                            {
                                int totRows = rs_Date_BCCheck.getInt(1);
                                if(totRows!=0)
                                {
                                    break;
                                }
                            }
                            rs_Date_BCCheck.close();
                            ps_Date_BCCheck.close();
                        }
                        
                    }
                    rs_Date_NACheck.close();
                    ps_Date_NACheck.close();
                }
                //to find predsrShift i.e.last shift of the valid previous day..
                Hashtable ht_Day = new Hashtable();
                gc.setTime(prodDate);
                
                
                PreparedStatement ps_Date_AvbCheck = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_MachineDateAndShift);
                ResultSet rs_Date_AvbCheck = ps_Date_AvbCheck.executeQuery();
                if(rs_Date_AvbCheck.next())
                {
                    if(rs_Date_AvbCheck.getInt(1) == 0)
                    {
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        ht_Day.put("DAY",new Integer(day));
                        PreparedStatement ps_LastShift = con.executeStatement(SQLMaster.LAST_SHIFT_OF_A_DAY_SELECT_SQL_QUERY,ht_Day);
                        ResultSet rs_LastShift = ps_LastShift.executeQuery();
                        if(rs_LastShift.next())
                        {
                            predsrShift = rs_LastShift.getInt("SHIFT_ID");
                        }
                        rs_LastShift.close();
                        ps_LastShift.close();
                        
                    }
                    else
                    {
                        PreparedStatement ps_LastShift = con.executeStatement(SQLMaster.LAST_SHIFT_OF_A_DAY_IN_CUSTOM_AVBLTY_SELECT_SQL_QUERY,ht_MachineDateAndShift);
                        ResultSet rs_LastShift = ps_LastShift.executeQuery();
                        if(rs_LastShift.next())
                        {
                            predsrShift = rs_LastShift.getInt("SHIFT_ID");
                        }
                        rs_LastShift.close();
                        ps_LastShift.close();
                        
                        
                    }
                }
                rs_Date_AvbCheck.close();
                ps_Date_AvbCheck.close();
                
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            
            objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
            
            ht_MachineDateAndShift.put("PROD_CRNT_DATE",prodDate);
            ht_MachineDateAndShift.put("SHIFT_ID",new Integer(predsrShift));
            ht_MachineDateAndShift.put("MC_CDE",mcCde);
            
            
            
            PreparedStatement ps_ProdDate_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_DATE_SHIFT_SQL_QUERY,ht_MachineDateAndShift);
            ResultSet rs_ProdDate_PostCheck = ps_ProdDate_PostCheck.executeQuery();
            
            if(rs_ProdDate_PostCheck.next())
            {
                int count = rs_ProdDate_PostCheck.getInt(1);
                if(count==0)
                {
                    checkRESULT = true;
                    PreparedStatement ps_Previousday_EntryCheck = con.executeStatement(SQLMaster.ENTRY_CHECK_IN_ALL_THE_TABLE_SQL_QUERY,ht_MachineDateAndShift);
                    ResultSet rs_Previousday_EntryCheck = ps_Previousday_EntryCheck.executeQuery();
                    if(rs_Previousday_EntryCheck.next())
                    {
                        count = rs_Previousday_EntryCheck.getInt(1);
                        if(count==0)
                        {
                            throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
                        }
                        else
                        {
                            PreparedStatement ps_Valid_Machines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
                            ResultSet rs_Valid_Machines = ps_Valid_Machines.executeQuery();
                            Vector vec_MachineResult = new Vector();
                            while(rs_Valid_Machines.next())
                            {
                                ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                                Vector vec_ShiftResult = new Vector();
                                String mcCode = rs_Valid_Machines.getString("MC_CDE");
                                String mcName = rs_Valid_Machines.getString("MC_NAME");
                                objProductionAccountingMachineDetails.setMcName(mcName);
                                objProductionAccountingMachineDetails.setMcCode(mcCode);
                                ht_MachineDateAndShift.put("MC_CDE",mcCode);
                                PreparedStatement ps_Prod_Accounting = con.executeStatement(SQLMaster.PROD_ACCOUNTING_SELECT_SQL_QUERY,ht_MachineDateAndShift);
                                ResultSet rs_Prod_Accounting = ps_Prod_Accounting.executeQuery();
                                if(rs_Prod_Accounting.next())
                                {
                                    int check = rs_Prod_Accounting.getInt(1);
                                    if(BuildConfig.DMODE)
                                    {
                                        System.out.println("check "+check);
                                        System.out.println("Mc code "+mcCode);
                                    }
                                    prodHrs = 0;
                                    nprodHrs = 0;
                                    
                                    if(check==0)
                                    {  //if(check==0)
                                        PreparedStatement ps_ProdHrs_Select = con.executeStatement(SQLMaster.PROD_HRS_SELECT_SQL_QUERY,ht_MachineDateAndShift);
                                        ResultSet rs_ProdHrs_Select = ps_ProdHrs_Select.executeQuery();
                                        if(rs_ProdHrs_Select.next())
                                        {
                                            prodHrs = rs_ProdHrs_Select.getFloat("PROD_HRS");
                                            nprodHrs = rs_ProdHrs_Select.getFloat("NPROD_HRS");
                                        }
                                        
                                        rs_ProdHrs_Select.close();
                                        ps_ProdHrs_Select.close();
                                        
                                        //to calculate the available hrs..
                                        String startHrs = "";
                                        String startMinute = "";
                                        String endHrs = "";
                                        String endMinute = "";
                                        
                                        PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_MachineDateAndShift);
                                        int cnt = 0;
                                        ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                                        if(rs_CheckAvblty.next())
                                            cnt = rs_CheckAvblty.getInt(1);
                                        rs_CheckAvblty.close();
                                        ps_CheckAvblty.close();
                                        String shiftName = "";
                                        if(cnt >0)
                                        {
                                            
                                            PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_MachineDateAndShift);
                                            ResultSet rs = ps.executeQuery();
                                            if(rs.next())
                                            {
                                                startHrs = "";
                                                startMinute = "";
                                                endHrs = "";
                                                endMinute = "";
                                                availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                                                availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                                                StringTokenizer st;
                                                st = new StringTokenizer(availStartTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    startHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    startMinute = st.nextToken();
                                                }
                                                st = new StringTokenizer(availEndTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    endHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    endMinute = st.nextToken();
                                                }
                                                
                                                String startTime = startHrs+startMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("stTime"+startTime);
                                                String endTime = endHrs+endMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("endTime"+endTime);
                                                int diff = 0;
                                                int i = Integer.parseInt(startTime);
                                                int j = Integer.parseInt(endTime);
                                                if(i<j)
                                                    diff = j - i;
                                                else
                                                    diff = (2400-i)+j;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("diff:"+diff);
                                                float min = diff%100;
                                                float hr = diff/100;
                                                if(BuildConfig.DMODE)
                                                {
                                                    System.out.println("min"+min);
                                                    System.out.println("hr"+hr);
                                                }
                                                min = (min/60);
                                                if(BuildConfig.DMODE)
                                                    System.out.println(min);
                                                availShiftHrs = hr + min;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("availShiftHRs :"+availShiftHrs);
                                                
                                                //to select the shiftName...
                                                PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_MachineDateAndShift);
                                                ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                                
                                                if(rs_ShiftNameSel.next())
                                                {
                                                    shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                                }
                                                rs_ShiftNameSel.close();
                                                ps_ShiftNameSel.close();
                                                
                                                
                                                
                                                
                                            }
                                            rs.close();
                                            ps.close();
                                            
                                        }
                                        else
                                        {
                                            
                                            
                                            gc.setTime(prodDate);
                                            int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                                            
                                            ht_AvailHrs_Get.put("SHIFT_ID",new Integer(predsrShift));
                                            ht_AvailHrs_Get.put("DAY",new Integer(day));
                                            
                                            PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                                            ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                                            //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                                            if(rs_AvailHrs_Get.next())
                                            {
                                                startHrs = "";
                                                startMinute = "";
                                                endHrs = "";
                                                endMinute = "";
                                                availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                                                availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                                                StringTokenizer st;
                                                st = new StringTokenizer(availStartTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    startHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    startMinute = st.nextToken();
                                                }
                                                st = new StringTokenizer(availEndTime,":");
                                                if(st.hasMoreTokens())
                                                {
                                                    endHrs = st.nextToken();
                                                }
                                                if(st.hasMoreTokens())
                                                {
                                                    endMinute = st.nextToken();
                                                }
                                                
                                                String startTime = startHrs+startMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("stTime"+startTime);
                                                String endTime = endHrs+endMinute;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("endTime"+endTime);
                                                int diff = 0;
                                                int i = Integer.parseInt(startTime);
                                                int j = Integer.parseInt(endTime);
                                                if(i<j)
                                                    diff = j - i;
                                                else
                                                    diff = (2400-i)+j;
                                                if(BuildConfig.DMODE)
                                                    System.out.println("diff:"+diff);
                                                float min = diff%100;
                                                float hr = diff/100;
                                                if(BuildConfig.DMODE)
                                                {
                                                    System.out.println("min"+min);
                                                    System.out.println("hr"+hr);
                                                }
                                                min = (min/60);
                                                if(BuildConfig.DMODE)
                                                    System.out.println(min);
                                                availShiftHrs = hr + min;
                                                
                                            }
                                            rs_AvailHrs_Get.close();
                                            ps_AvailHrs_Get.close();
                                            
                                            if(BuildConfig.DMODE)
                                                System.out.println("availShiftHRs :"+availShiftHrs);
                                            
                                            //                                	                      //to select the shiftName...
                                            PreparedStatement ps_ShiftNameSel = con.executeStatement(SQLMaster.SHIFT_NAME_SELECT_SQL_QUERY,ht_MachineDateAndShift);
                                            ResultSet rs_ShiftNameSel = ps_ShiftNameSel.executeQuery();
                                            
                                            if(rs_ShiftNameSel.next())
                                            {
                                                shiftName = rs_ShiftNameSel.getString("SHIFT_NAME");
                                            }
                                            rs_ShiftNameSel.close();
                                            ps_ShiftNameSel.close();
                                            
                                            
                                            
                                        }
                                        ProductionAccountingShiftDetails  objProductionAccountingShiftDetails = new ProductionAccountingShiftDetails();
                                        availShiftHrs = availShiftHrs-prodHrs-nprodHrs;
                                        objProductionAccountingShiftDetails.setAvailHrs(availShiftHrs);
                                        
                                        objProductionAccountingShiftDetails.setProdHrs(prodHrs);
                                        objProductionAccountingShiftDetails.setNprodHrs(nprodHrs);
                                        objProductionAccountingShiftDetails.setShiftName(shiftName);
                                        vec_ShiftResult.add(objProductionAccountingShiftDetails);
                                        
                                    }//if(check==0) end
                                    
                                }
                                rs_Prod_Accounting.close();
                                ps_Prod_Accounting.close();
                                objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                                vec_MachineResult.add(objProductionAccountingMachineDetails);
                                
                            }
                            rs_Valid_Machines.close();
                            ps_Valid_Machines.close();
                            objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec_MachineResult);
                        }
                    }
                    rs_Previousday_EntryCheck.close();
                    ps_Previousday_EntryCheck.close();
                    
                }
                
            }
            rs_ProdDate_PostCheck.close();
            ps_ProdDate_PostCheck.close();
            if(checkRESULT)
            {
                PreparedStatement ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_RADL_DATE_SHIFT_SQL_QUERY,ht_MachineDateAndShift);
                ResultSet rs_PostCheck = ps_PostCheck.executeQuery();
                if(rs_PostCheck.next())
                {
                    int count = rs_PostCheck.getInt(1);
                    if(count==0)
                    {
                        checkRESULT = true;
                    }
                    else
                    {
                        checkRESULT = false;
                    }
                }
                rs_PostCheck.close();
                ps_PostCheck.close();
                if(checkRESULT)
                {
                    ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_DATE_SHIFT_IN_NPROD_SQL_QUERY,ht_MachineDateAndShift);
                    rs_PostCheck = ps_PostCheck.executeQuery();
                    
                    if(rs_PostCheck.next())
                    {
                        int count = rs_PostCheck.getInt(1);
                        if(count==0)
                        {
                            checkRESULT = true;
                        }
                        else
                        {
                            checkRESULT = false;
                        }
                    }
                    rs_PostCheck.close();
                    ps_PostCheck.close();
                    /*if(checkRESULT)
                     {
                     ps_PostCheck = con.executeStatement(SQLMaster.POSTING_CHECK_BY_MC_DATE_SHIFT_IN_POP_SQL_QUERY,ht_MachineDateAndShift);
                     rs_PostCheck = ps_PostCheck.executeQuery();
                     
                     if(rs_PostCheck.next())
                     {
                     int count = rs_PostCheck.getInt(1);
                     if(count==0)
                     {
                     checkRESULT = true;
                     }
                     else
                     {
                     checkRESULT = false;
                     }
                     }
                     rs_PostCheck.close();
                     ps_PostCheck.close();
                     if(!checkRESULT)
                     {
                     throw new ProductionException("PMEC009","PAY OUTSIDE PRODUCTION ENTRY NOT POSTED","");
                     }
                     
                     }
                     else*/
                    if(!checkRESULT)
                    {
                        throw new ProductionException("PMEC010","NON PRODUCTION ENTRY NOT POSTED","");
                    }
                }
                else
                {
                    throw new ProductionException("PMEC012","RADIAL PRODUCTION ENTRY NOT POSTED","");
                }
                
            }
            else
            {
                throw new ProductionException("PMEC013","PRODUCTION ENTRY NOT POSTED","");
            }
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    
    public boolean updateProductionDetails(ProductionDetails objProductionDetails) throws ProductionException, SQLException
    {
        boolean updateRESULT = false;
        //boolean incntvFlg = false;
        int prodId = 0;
        DBConnection con=null;
        Hashtable ht_EmpHrs_Del = new Hashtable();
        Hashtable ht_MacHrs_Reduce = new Hashtable();
        Hashtable ht_Prod_Update = new Hashtable();
        Hashtable ht_EmpHrs_Add = new Hashtable();
        Hashtable ht_ProdAccntng_Add = new Hashtable();
        Vector vec_EmpHrs = new Vector();
        PreparedStatement psEmpTypCheck = null;
        ResultSet rsEmpTypCheck = null;
        Hashtable ht = new Hashtable();
        int result;
        float otHrs = 0;
        float otSlryHrs = 0;
        float dtyHrs = 0;
        float dtySlryHrs = 0;
        float incntvSlryHrs = 0;
        int totQty = 0;
        
        int startOpn = 0;
        int endOpn = 0;
        float stdHrs = 0;
        //float totHrs = 0;
        int empId = 0;
        //String empType = "";
        int empTypId = 0;
        
        //delete PROD_EMP table.
        prodId = objProductionDetails.getProdId();
        con = DBConnectionFactory.getConnection();
        con.startTransaction();
        ht_EmpHrs_Del.put("PROD_ID",new Integer(prodId));
        
        startOpn = objProductionDetails.getProdStartOpn();
        endOpn = objProductionDetails.getProdEndOpn();
        
        stdHrs = getStandardHrs(objProductionDetails.getWoJbId(),startOpn,endOpn);
        totQty =  objProductionDetails.getProdTotQty();
        
        //to find total standard hrs...
        stdHrs = totQty * stdHrs;
        
        try
        {
            
//          Updation of PROD table.
            
            ht_Prod_Update.put("PROD_ID",new Integer(prodId));
            ht_Prod_Update.put("MC_CDE",objProductionDetails.getMcCode());
            ht_Prod_Update.put("PROD_CRNT_DATE",objProductionDetails.getProdCrntDate());
            ht_Prod_Update.put("SHIFT_ID",new Integer(objProductionDetails.getShiftId()));
            ht_Prod_Update.put("PROD_WORK_TYP",objProductionDetails.getProdWorkType());
            ht_Prod_Update.put("PROD_INCNTV_FLG",(this.checkIncentive(objProductionDetails.getWoJbId(),startOpn,endOpn)?"1":"0"));
            ht_Prod_Update.put("WOJB_ID",new Integer(objProductionDetails.getWoJbId()));
            ht_Prod_Update.put("PROD_QTY_SNOS",objProductionDetails.getProdQtySnos());
            ht_Prod_Update.put("PROD_TOT_QTY",new Integer(objProductionDetails.getProdTotQty()));
            ht_Prod_Update.put("PROD_START_OPN",new Integer(objProductionDetails.getProdStartOpn()));
            ht_Prod_Update.put("PROD_END_OPN",new Integer(objProductionDetails.getProdEndOpn()));
            ht_Prod_Update.put("PROD_STD_HRS",new Float(stdHrs));
            ht_Prod_Update.put("PROD_TOT_HRS",new Float(objProductionDetails.getProdTotHrs()));
            ht_Prod_Update.put("PROD_UPDATE_PYRL","0");
            ht_Prod_Update.put("PROD_UPDATE_WO","0");
            ht_Prod_Update.put("PROD_POST_FLG","0");
            ht_Prod_Update.put("IS_DLTD","0");
            ht_Prod_Update.put("PROD_CREATEDBY",objProductionDetails.getModifiedBy());
            
            if(this.checkDuplicateProdForUpdate(ht_Prod_Update,con))
            {
                throw new ProductionException("PMEC010","PRODUCTION ENTRY ALREADY MADE","");
            }
            
            //to store Emp Hours entries into Log..
            result = con.executeUpdateStatement(SQLMaster.LOG_PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Del);
            if(result >0)
            {
                result = con.executeUpdateStatement(SQLMaster.PRODN_EMP_HRS_DELETE_SQL_QUERY,ht_EmpHrs_Del);
                if(result>0)
                {
                    if(BuildConfig.DMODE)
                        System.out.println("PROD EMP HRS DELETED");
                }
            }
            //to select PREVIOUS PROD HRS...FOR
            PreparedStatement ps = con.executeStatement(SQLMaster.PRODN_TOT_HRS_SELECT_SQL_QUERY,ht_EmpHrs_Del);
            ResultSet rs = ps.executeQuery();
            
            float prodTotHrs = 0;
            if(rs.next())
            {
                prodTotHrs = rs.getFloat("PROD_TOT_HRS");
                if(BuildConfig.DMODE)
                    System.out.println("prodTotHrs :"+prodTotHrs);
            }
            rs.close();
            ps.close();
            
            //subtract Machine Hrs in PROD_ACCNTNG table.
            //ht_MacHrs_Reduce.put("MC_CDE",objProductionDetails.getMcCode());
            //ht_MacHrs_Reduce.put("PROD_DATE",objProductionDetails.getProdCrntDate());
            //ht_MacHrs_Reduce.put("SHIFT_ID",new Integer(objProductionDetails.getShiftId()));
            ht_MacHrs_Reduce.put("PROD_ID",new Integer(prodId));
            ht_MacHrs_Reduce.put("PROD_HRS",new Float(prodTotHrs));
            ht_MacHrs_Reduce.put("ISHRS_ACCOUNTED","0");
            result = con.executeUpdateStatement(SQLMaster.PRODN_MC_HRS_REDUCE_SQL_QUERY,ht_MacHrs_Reduce);
            if(result>0)
            {
                if(BuildConfig.DMODE)
                    System.out.println("PROD MC HRS SUBTRACTED");
            }
            startOpn = objProductionDetails.getProdStartOpn();
            endOpn = objProductionDetails.getProdEndOpn();
            stdHrs = getStandardHrs(objProductionDetails.getWoJbId(),startOpn,endOpn);
            totQty =  objProductionDetails.getProdTotQty();
            //to find total standard hrs...
            stdHrs = totQty * stdHrs;
            
            
            // to store Prod Entries to Log..
            
            result = con.executeUpdateStatement(SQLMaster.PRODN_DETAILS_LOG_ADD_SQL_QUERY,ht_Prod_Update);
            
            if(result > 0)
            {
                con.executeUpdateStatement(SQLMaster.PRODN_DETAILS_UPDATE_SQL_QUERY,ht_Prod_Update);
                PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.PROD_MODIFY_COUNT_SELECT,ht_Prod_Update);
                ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                int modifyCount = 0;
                if(rs_ModifyCnt_Sel.next())
                {
                    modifyCount = rs_ModifyCnt_Sel.getInt("PROD_MODIFYCOUNT");
                }
                ht_Prod_Update.put("PROD_MODIFYCOUNT",new Integer(modifyCount));
                
                
                
                vec_EmpHrs = objProductionDetails.getProdnEmpHrsDetails();
                //incntvFlg = objProductionDetails.isProdIncntvFlag();
                //String prodWorkType = objProductionDetails.getProdWorkType();
                //totHrs = objProductionDetails.getProdTotHrs();
                
                ht_EmpHrs_Add.put("PROD_MODIFYCOUNT",new Integer(modifyCount));
                
                for(int i = 0;i<vec_EmpHrs.size();i++)
                {
                    otHrs = 0;
                    otSlryHrs = 0;
                    dtyHrs = 0;
                    dtySlryHrs = 0;
                    incntvSlryHrs = 0;
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    objEmployeeDtyOtDetails = (EmployeeDtyOtDetails)vec_EmpHrs.elementAt(i);
                    dtyHrs = objEmployeeDtyOtDetails.getDutyHrs();
                    otHrs = objEmployeeDtyOtDetails.getOtHrs();
                    empId = objEmployeeDtyOtDetails.getEmpId();
                    empTypId = objEmployeeDtyOtDetails.getEmpTypdId();
                    ht.put("EMP_TYP_ID",new Integer(empTypId));
                    psEmpTypCheck = con.executeStatement(SQLMaster.CHECK_EMP_TYPE_SQL_QUERY,ht);
                    rsEmpTypCheck = psEmpTypCheck.executeQuery();
                    int check = 0;
                    int val = 0;
                    if(rsEmpTypCheck.next())
                    {
                        val = rsEmpTypCheck.getInt(1);
                    }
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("EmpTypeId:"+empTypId);
                        System.out.println("EmpId:"+empId);
                        System.out.println("Empval:"+val);
                    }
                    
                    if(val == 0)
                        if(dtyHrs >0)
                        if(!this.checkDutyEntry(objProductionDetails.getProdCrntDate(), objProductionDetails.getShiftId(), empId, con))
                        {
                            con.rollBackTransaction();
                            throw new ProductionException("WOEC00","EMPLOYEE DUTY NOT POSSIBLE FOR MULTIPLE SHIFTS : "+objEmployeeDtyOtDetails.getEmpName(),"");
                        }
                        
                    
                    if(val == 0)
                        check = checkAvailableEmpHrs(empId,objProductionDetails.getProdCrntDate(),objProductionDetails.getShiftId(),dtyHrs+otHrs,0,0,0,con);
                    if(check==0)
                    {
                        ht_EmpHrs_Add.put("PROD_ID",new Integer(prodId));
                        ht_EmpHrs_Add.put("EMP_ID",new Integer(empId));
                        ht_EmpHrs_Add.put("DT_HRS",new Float(dtyHrs));
                        ht_EmpHrs_Add.put("OT_HRS",new Float(otHrs));
                        ht_EmpHrs_Add.put("DT_SLRY_HRS",new Float(dtySlryHrs));
                        ht_EmpHrs_Add.put("OT_SLRY_HRS",new Float(otSlryHrs));
                        ht_EmpHrs_Add.put("INCNTV_SLRY_HRS",new Float(incntvSlryHrs));
                        
                        result = con.executeUpdateStatement(SQLMaster.PRODN_EMPHRS_ADD_SQL_QUERY,ht_EmpHrs_Add);
                        if(result>0)
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("Employee Hrs Details Added");
                            
                            
                        }
                        
                    }
                    else
                    {
                        con.rollBackTransaction();
                        throw new ProductionException("WOEC00","EMPLOYEE HRS IS INVALID : "+objEmployeeDtyOtDetails.getEmpName(),"");
                    }
                }
                
                ht_ProdAccntng_Add.put("MC_CDE",objProductionDetails.getMcCode());
                ht_ProdAccntng_Add.put("PROD_DATE",objProductionDetails.getProdCrntDate());
                ht_ProdAccntng_Add.put("SHIFT_ID",new Integer(objProductionDetails.getShiftId()));
                int check = checkHrsAccounted(objProductionDetails.getMcCode(),objProductionDetails.getProdCrntDate(),objProductionDetails.getShiftId(),objProductionDetails.getProdTotHrs(),0,con);
                if(check==0)
                {
                    ht_ProdAccntng_Add.put("PROD_HRS",new Float(objProductionDetails.getProdTotHrs()));
                    ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","1");
                }
                else if(check ==1)
                {
                    ht_ProdAccntng_Add.put("PROD_HRS",new Float(objProductionDetails.getProdTotHrs()));
                    ht_ProdAccntng_Add.put("ISHRS_ACCOUNTED","0");
                }
                else
                {
                    
                    con.rollBackTransaction();
                    throw new ProductionException("WOEC00","PROD HRS IS INVALID","");
                }
                
                
                
                result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_UPDATE_BY_PRODHRS_SQL_QUERY,ht_ProdAccntng_Add);
                
                if(result<=0)
                {
                    result = con.executeUpdateStatement(SQLMaster.PRODN_ACCNTNG_PRODHRS_ADD_SQL_QUERY,ht_ProdAccntng_Add);
                    if(result>0)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("Production Accounting Added");
                    }
                    
                }
                else
                {
                    if(BuildConfig.DMODE)
                        System.out.println("Production Accounting Updated");
                }
                updateRESULT = true;
                con.commitTransaction();
            }
        }
        
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            con.rollBackTransaction();
            if(BuildConfig.DBASE==BuildConfig.ORACLE)
            {
                if(sqle.toString().indexOf("PK_PRODID")>=0)
                {
                    throw new ProductionException("PMEC002","PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_PROD_MCCDE") >= 0)
                {
                    throw new ProductionException("PMEC004","PARENT KEY MACHINE CODE NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_PROD_SHIFTID") >= 0)
                {
                    throw new ProductionException("PMEC007","PARENT KEY SHIFT ID NOT FOUND",sqle.toString());
                }
                else if (sqle.toString().indexOf("FK_PROD_WOJBID") >= 0)
                {
                    throw new ProductionException("PMEC008","PARENT KEY WORK ORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                }
            }
            if(BuildConfig.DBASE==BuildConfig.MYSQL)
            {
                if(sqle.toString().indexOf("Duplicate entry")>=0)
                {
                    throw new ProductionException("PMEC002","PRODUCTION ID IS UNIQUE.UNIQUE CONSTRAINT VIOLATED",sqle.toString());
                }
                else if (sqle.toString().indexOf("foreign key constraint") >= 0)
                {
                    throw new ProductionException("PMEC004","PARENT KEY MACHINE CODE OR SHIFT ID OR WORKORDER JOB ID NOT FOUND",sqle.toString());
                }
                else
                {
                    throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("PRODUCTION UPDATE ENDS");
        
        return updateRESULT;
    }
    public int checkAvailableEmpHrs(int empId,Date crntDate,int shiftId,float prodHrs,float nonProdHrs,float radlProdHrs,float popHrs,DBConnection con) throws ProductionException
    {
        if(BuildConfig.DMODE)
            System.out.println("check AvailEmpHrs Starts");
        int check = 10;
        float tablProdHrs =0 ;
        float tablNprodHrs = 0;
        float tablRadlProdHrs = 0;
        float tablPopHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        try
        {
            
            //DBConnection con = DBConnectionFactory.getConnection();
            Hashtable ht_DateShiftEmp = new Hashtable();
            ht_DateShiftEmp.put("PROD_CRNT_DATE",crntDate);
            ht_DateShiftEmp.put("SHIFT_ID",new Integer(shiftId));
            ht_DateShiftEmp.put("EMP_ID",new Integer(empId));
            
            //to fetch prodHrs for particular employee...from PROD_EMP table
            PreparedStatement ps_ProdHrs_Get = con.executeStatement(SQLMaster.PRODN_HRS_GET_SQL_QUERY,ht_DateShiftEmp);
            ResultSet rs_ProdHrs_Get = ps_ProdHrs_Get.executeQuery();
            
            while(rs_ProdHrs_Get.next())
            {
                tablProdHrs = tablProdHrs + rs_ProdHrs_Get.getFloat("PROD_HRS");
            }
            rs_ProdHrs_Get.close();
            ps_ProdHrs_Get.close();
            //to fetch nprodHrs for particular employee...from NPROD_EMP table
            PreparedStatement ps_NProdHrs_Get = con.executeStatement(SQLMaster.NONPRODN_HRS_GET_SQL_QUERY,ht_DateShiftEmp);
            ResultSet rs_NProdHrs_Get = ps_NProdHrs_Get.executeQuery();
            while(rs_NProdHrs_Get.next())
            {
                tablNprodHrs = tablNprodHrs + rs_NProdHrs_Get.getFloat("NPROD_HRS");
            }
            rs_NProdHrs_Get.close();
            //to fetch radlprodHrs for particular employee...from RADL_EMP table
            PreparedStatement ps_RProdHrs_Get = con.executeStatement(SQLMaster.RADLPRODN_HRS_GET_SQL_QUERY,ht_DateShiftEmp);
            ResultSet rs_RProdHrs_Get = ps_RProdHrs_Get.executeQuery();
            while(rs_RProdHrs_Get.next())
            {
                tablRadlProdHrs = tablRadlProdHrs + rs_RProdHrs_Get.getFloat(1);
            }
            rs_RProdHrs_Get.close();
            ps_RProdHrs_Get.close();
            //to fetch popprodHrs for particular employee...from POP_EMP table
            PreparedStatement ps_PopHrs_Get = con.executeStatement(SQLMaster.POP_HRS_GET_SQL_QUERY,ht_DateShiftEmp);
            ResultSet rs_PopHrs_Get = ps_PopHrs_Get.executeQuery();
            while(rs_PopHrs_Get.next())
            {
                tablPopHrs = tablPopHrs + rs_PopHrs_Get.getFloat("POP_HRS");
            }
            rs_PopHrs_Get.close();
            ps_PopHrs_Get.close();
            
            
            float value = prodHrs+nonProdHrs+radlProdHrs+popHrs;
            if(BuildConfig.DMODE)
            {
                System.out.println("prodHrs :"+prodHrs);
                System.out.println("nprodHrs :"+nonProdHrs);
                System.out.println("radprodHrs :"+radlProdHrs);
                System.out.println("popHrs :"+popHrs);
            }
            float tablValue = tablProdHrs + tablNprodHrs + tablRadlProdHrs + tablPopHrs;
            if(BuildConfig.DMODE)
            {
                System.out.println("tprodHrs :"+tablProdHrs);
                System.out.println("tnprodHrs :"+tablNprodHrs);
                System.out.println("tradprodHrs :"+tablRadlProdHrs);
                System.out.println("tpopHrs :"+tablPopHrs);
            }
            float totValue = value + tablValue;
            if(BuildConfig.DMODE)
                System.out.println("totValue :"+totValue);
            GregorianCalendar gc = new GregorianCalendar();
            String startHrs = "";
            String startMinute = "";
            String endHrs = "";
            String endMinute = "";
            
            PreparedStatement ps = con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_DateShiftEmp);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                int count = rs.getInt(1);
                if(count<=0)
                {
                    PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_DateShiftEmp);
                    int cnt = 0;
                    ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                    if(rs_CheckAvblty.next())
                        cnt = rs_CheckAvblty.getInt(1);
                    if(cnt >0)
                    {
                        ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_DateShiftEmp);
                        rs = ps.executeQuery();
                        if(rs.next())
                        {
                            startHrs = "";
                            startMinute = "";
                            endHrs = "";
                            endMinute = "";
                            availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                            availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                            StringTokenizer st;
                            st = new StringTokenizer(availStartTime,":");
                            if(st.hasMoreTokens())
                            {
                                startHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                startMinute = st.nextToken();
                            }
                            st = new StringTokenizer(availEndTime,":");
                            if(st.hasMoreTokens())
                            {
                                endHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                endMinute = st.nextToken();
                            }
                            
                            String startTime = startHrs+startMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("stTime"+startTime);
                            String endTime = endHrs+endMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("endTime"+endTime);
                            int diff = 0;
                            int i = Integer.parseInt(startTime);
                            int j = Integer.parseInt(endTime);
                            if(i<j)
                                diff = j - i;
                            else
                                diff = (2400-i)+j;
                            
                            if(BuildConfig.DMODE)
                                System.out.println("diff:"+diff);
                            
                            float min = diff%100;
                            float hr = diff/100;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("min"+min);
                                System.out.println("hr"+hr);
                            }
                            min = (min/60);
                            if(BuildConfig.DMODE)
                                System.out.println(min);
                            availShiftHrs = hr + min;
                            
                            if(BuildConfig.DMODE)
                                System.out.println("availShiftHRs :"+availShiftHrs);
                            
                        }
                        else
                        {
                            availShiftHrs = 0;
                        }
                    }
                    else
                    {
                        
                        gc.setTime(crntDate);
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        
                        
                        ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shiftId));
                        ht_AvailHrs_Get.put("DAY",new Integer(day));
                        PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                        ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                        
                        //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                        if(rs_AvailHrs_Get.next())
                        {
                            startHrs = "";
                            startMinute = "";
                            endHrs = "";
                            endMinute = "";
                            availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                            availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                            StringTokenizer st;
                            st = new StringTokenizer(availStartTime,":");
                            if(st.hasMoreTokens())
                            {
                                startHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                startMinute = st.nextToken();
                            }
                            
                            st = new StringTokenizer(availEndTime,":");
                            if(st.hasMoreTokens())
                            {
                                endHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                endMinute = st.nextToken();
                            }
                            
                            String startTime = startHrs+startMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("stTime"+startTime);
                            String endTime = endHrs+endMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("endTime"+endTime);
                            int diff = 0;
                            int i = Integer.parseInt(startTime);
                            int j = Integer.parseInt(endTime);
                            if(i<j)
                                diff = j - i;
                            else
                                diff = (2400-i)+j;
                            if(BuildConfig.DMODE)
                                System.out.println("diff:"+diff);
                            float min = diff%100;
                            float hr = diff/100;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("min"+min);
                                System.out.println("hr"+hr);
                            }
                            min = (min/60);
                            if(BuildConfig.DMODE)
                                System.out.println(min);
                            availShiftHrs = hr + min;
                            if(BuildConfig.DMODE)
                                System.out.println("availShiftHRs :"+availShiftHrs);
                        }
                        
                    }
                }
            }
            rs.close();
            ps.close();
            if(BuildConfig.DMODE)
                System.out.println("availHrs :"+availShiftHrs);
            if(totValue<=availShiftHrs)
            {
                check = 0;
            }
            else
            {
                check = 1;
            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        if(BuildConfig.DMODE)
            System.out.println("check AvailEmpHrs Ends");
        return check;
        
    }
    public int checkHrsAccounted(String mcCode,Date crntDate,int shiftId,float prodHrs,float nprodHrs,DBConnection con) throws ProductionException, SQLException
    {
        if(BuildConfig.DMODE)
            System.out.println("check Machine Hrs Accounted Starts");
        int result = 10;
        float tableProdHrs = 0;
        float tableNprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_Hrs_Get = new Hashtable();
        Hashtable ht_AvailHrs_Get = new Hashtable();
        
        logger.info("CHECK HOURS ACCOUNTED STARTS");
        
        ht_Hrs_Get.put("MC_CDE",mcCode);
        ht_Hrs_Get.put("PROD_CRNT_DATE",crntDate);
        ht_Hrs_Get.put("SHIFT_ID",new Integer(shiftId));
        try
        {
            PreparedStatement ps_HrsAccntdCheck = con.executeStatement(SQLMaster.PRODN_HRS_ACCOUNTED_FLG_CHECK_SQL_QUERY,ht_Hrs_Get);
            ResultSet rs_HrsAccntdCheck = ps_HrsAccntdCheck.executeQuery();
            if(rs_HrsAccntdCheck.next())
            {
                
                logger.info("CHECK HOURS ACCOUNTED ENDS HRS FLG ALREADY ACCOUNTED");
                
                result = 2;
                return result;
            }
            rs_HrsAccntdCheck.close();
            ps_HrsAccntdCheck.close();
            PreparedStatement ps_Hrs_Get = con.executeStatement(SQLMaster.PRODN_NPRODN_HRS_GET_SQL_QUERY,ht_Hrs_Get);
            ResultSet rs_Hrs_Get = ps_Hrs_Get.executeQuery();
            if(rs_Hrs_Get.next())
            {
                tableProdHrs = rs_Hrs_Get.getFloat("PROD_HRS");
                tableNprodHrs = rs_Hrs_Get.getFloat("NPROD_HRS");
                
            }
            rs_Hrs_Get.close();
            ps_Hrs_Get.close();
            
            String startHrs = "";
            String startMinute = "";
            String endHrs = "";
            String endMinute = "";
            
            GregorianCalendar gc = new GregorianCalendar();
            PreparedStatement ps =  con.executeStatement(SQLMaster.CUSTOM_NONAVBLTY_CHECK_SQL_QUERY,ht_Hrs_Get);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                int count = rs.getInt(1);
                if(count<=0)
                {
                    PreparedStatement ps_CheckAvblty = con.executeStatement(SQLMaster.CUSTOM_AVBLTYCHECK_SQL_QUERY,ht_Hrs_Get);
                    int cnt = 0;
                    ResultSet rs_CheckAvblty = ps_CheckAvblty.executeQuery();
                    if(rs_CheckAvblty.next())
                        cnt = rs_CheckAvblty.getInt(1);
                    if(cnt >0)
                    {
                        
                        ps = con.executeStatement(SQLMaster.CUSTOM_AVBLTY_CHECK_SQL_QUERY,ht_Hrs_Get);
                        rs = ps.executeQuery();
                        if(rs.next())
                        {
                            startHrs = "";
                            startMinute = "";
                            endHrs = "";
                            endMinute = "";
                            availStartTime = rs.getString("CUSTOM_AVBLTY_STARTTIME");
                            availEndTime = rs.getString("CUSTOM_AVBLTY_ENDTIME");
                            StringTokenizer st;
                            st = new StringTokenizer(availStartTime,":");
                            if(st.hasMoreTokens())
                            {
                                startHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                startMinute = st.nextToken();
                            }
                            st = new StringTokenizer(availEndTime,":");
                            if(st.hasMoreTokens())
                            {
                                endHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                endMinute = st.nextToken();
                            }
                            
                            String startTime = startHrs+startMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("stTime"+startTime);
                            String endTime = endHrs+endMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("endTime"+endTime);
                            int diff = 0;
                            int i = Integer.parseInt(startTime);
                            int j = Integer.parseInt(endTime);
                            if(i<j)
                                diff = j - i;
                            else
                                diff = (2400-i)+j;
                            if(BuildConfig.DMODE)
                                System.out.println("diff:"+diff);
                            float min = diff%100;
                            float hr = diff/100;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("min"+min);
                                System.out.println("hr"+hr);
                            }
                            min = (min/60);
                            if(BuildConfig.DMODE)
                                System.out.println(min);
                            availShiftHrs = hr + min;
                            if(BuildConfig.DMODE)
                                System.out.println("availShiftHRs :"+availShiftHrs);
                            
                        }
                        else
                        {
                            availShiftHrs = 0;
                        }
                        rs.close();
                        ps.close();
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("table ProdHrs :"+tableProdHrs);
                            System.out.println("table NProdHrs :"+tableNprodHrs);
                            System.out.println("ProdHrs :"+prodHrs);
                            System.out.println("nProdHrs :"+nprodHrs);
                        }
                    }
                    else
                    {
                        
                        
                        gc.setTime(crntDate);
                        int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                        
                        ht_AvailHrs_Get.put("SHIFT_ID",new Integer(shiftId));
                        ht_AvailHrs_Get.put("DAY",new Integer(day));
                        
                        PreparedStatement ps_AvailHrs_Get = con.executeStatement(SQLMaster.AVAILABLE_SHIFT_HRS_GET_SQL_QUERY,ht_AvailHrs_Get);
                        ResultSet rs_AvailHrs_Get = ps_AvailHrs_Get.executeQuery();
                        //start time and end time are Strings with : so,we have to convert hrs:min into hrs.min
                        if(rs_AvailHrs_Get.next())
                        {
                            startHrs = "";
                            startMinute = "";
                            endHrs = "";
                            endMinute = "";
                            availStartTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_STARTTIME");
                            availEndTime = rs_AvailHrs_Get.getString("BASE_CAL_VAR_ENDTIME");
                            StringTokenizer st;
                            st = new StringTokenizer(availStartTime,":");
                            if(st.hasMoreTokens())
                            {
                                startHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                startMinute = st.nextToken();
                            }
                            st = new StringTokenizer(availEndTime,":");
                            if(st.hasMoreTokens())
                            {
                                endHrs = st.nextToken();
                            }
                            if(st.hasMoreTokens())
                            {
                                endMinute = st.nextToken();
                            }
                            
                            String startTime = startHrs+startMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("stTime"+startTime);
                            String endTime = endHrs+endMinute;
                            if(BuildConfig.DMODE)
                                System.out.println("endTime"+endTime);
                            int diff = 0;
                            int i = Integer.parseInt(startTime);
                            int j = Integer.parseInt(endTime);
                            if(i<j)
                                diff = j - i;
                            else
                                diff = (2400-i)+j;
                            if(BuildConfig.DMODE)
                                System.out.println("diff:"+diff);
                            float min = diff%100;
                            float hr = diff/100;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("min"+min);
                                System.out.println("hr"+hr);
                            }
                            min = (min/60);
                            if(BuildConfig.DMODE)
                                System.out.println(min);
                            availShiftHrs = hr + min;
                            if(BuildConfig.DMODE)
                                System.out.println("availShiftHRs :"+availShiftHrs);
                        }
                        rs_AvailHrs_Get.close();
                        ps_AvailHrs_Get.close();
                        
                    }
                }
            }
            rs.close();
            ps.close();
            if(BuildConfig.DMODE)
                System.out.println("avail Hrs :"+availShiftHrs);
            if((tableProdHrs+prodHrs+tableNprodHrs+nprodHrs)==availShiftHrs)
            {
                result = 0;
            }
            else if((tableProdHrs+prodHrs+tableNprodHrs+nprodHrs)<availShiftHrs)
            {
                result = 1;
            }
            else
            {
                result = 2;
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
        }
        
        logger.info("CHECK HOURS ACCOUNTED ENDS");
        if(BuildConfig.DMODE)
            System.out.println("check Machine Hrs Accounted Ends");
        return result;
    }
    public float getStandardHrs(int woJbId,int startOpn,int endOpn) throws ProductionException, SQLException
    {
        float stdHrs = 0;
        PreparedStatement ps_WoStdHrs_Get = null;
        ResultSet rs_WoStdHrs_Get = null;
        ResultSet rs_WoJbStatId_Get = null;
        Hashtable ht_WoStdHrs_Get = new Hashtable();
        Hashtable ht_WoJbStatId_Get = new Hashtable();
        DBConnection con = null;
        int woJbStatId=0;
        
        
        logger.info("GET STD-HRS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_WoJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
        try
        {
            PreparedStatement ps_WoJbStatId_Get = con.executeStatement(SQLMaster.GET_WOJBSTATID_SQL_QUERY,ht_WoJbStatId_Get);
            rs_WoJbStatId_Get = ps_WoJbStatId_Get.executeQuery();
            if(rs_WoJbStatId_Get.next())
            {
                woJbStatId = rs_WoJbStatId_Get.getInt("WOJBSTAT_ID");
                for(int i = startOpn;i<=endOpn ;i++)
                {
                    ht_WoStdHrs_Get.put("WOJBSTAT_ID",new Integer(woJbStatId));
                    ht_WoStdHrs_Get.put("WOJBOPN_OPN_SNO",new Integer(i));
                    ps_WoStdHrs_Get = con.executeStatement(SQLMaster.GET_WORKORDER_STDHRS_SQL_QUERY,ht_WoStdHrs_Get);
                    rs_WoStdHrs_Get = ps_WoStdHrs_Get.executeQuery();
                    if(rs_WoStdHrs_Get.next())
                    {
                        stdHrs = stdHrs + rs_WoStdHrs_Get.getFloat("WOJBOPN_OPN_STDHRS");
                    }
                    rs_WoStdHrs_Get.close();
                    ps_WoStdHrs_Get.close();
                    
                }
            }
            else
            {
                if(BuildConfig.DMODE)
                {
                    System.out.println("RECORD NOT FOUND");
                }
                throw new ProductionException("PMEC014","RECORD NOT FOUND","");
            }
            rs_WoJbStatId_Get.close();
            ps_WoJbStatId_Get.close();
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.error("EXCEPTION WHILE SELECTING STANDARD HOURS FOR A WORKORDER JOB");
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("GET STD-HRS ENDS");
        
        
        return stdHrs;
    }
    public Vector viewUnPostedProductionDetails(Date fromDate,Date toDate) throws SQLException, ProductionException
    {
        Vector vec_Result = new Vector();
        DBConnection con = null;
        Hashtable ht_UnPostedJbDet_Get = new Hashtable();
        Hashtable ht_UnPostedDet_Get = new Hashtable();
        int prodId = 0;
        int woJbId = 0;
        
        
        logger.info("GET UNPOSTED PRODN DETAILS STARTS");
        
        
        con = DBConnectionFactory.getConnection();
        ht_UnPostedDet_Get.put("FROM_DATE",fromDate);
        ht_UnPostedDet_Get.put("TO_DATE",toDate);
        try
        {
            PreparedStatement ps_UnpostedProdId_Get = con.executeStatement(SQLMaster.UNPOSTED_PROD_ID_SELECT_SQL_QUERY,ht_UnPostedDet_Get);
            ResultSet rs_UnpostedProdId_Get = ps_UnpostedProdId_Get.executeQuery();
            while(rs_UnpostedProdId_Get.next())
            {
                UnPostedProductionDetails objUnPostedProductionDetails = new UnPostedProductionDetails();
                prodId = rs_UnpostedProdId_Get.getInt("PROD_ID");
                objUnPostedProductionDetails.setProdId(prodId);
                objUnPostedProductionDetails.setMcCode(rs_UnpostedProdId_Get.getString("MC_CDE"));
                objUnPostedProductionDetails.setProdCrntDate(rs_UnpostedProdId_Get.getTimestamp("PROD_CRNT_DATE"));
                objUnPostedProductionDetails.setShiftName(rs_UnpostedProdId_Get.getString("SHIFT_NAME"));
                objUnPostedProductionDetails.setProdWorkType(rs_UnpostedProdId_Get.getString("PROD_WORK_TYP"));
                objUnPostedProductionDetails.setProdIncntvFlag(rs_UnpostedProdId_Get.getString("PROD_INCNTV_FLG").equals("1")?true:false);
                objUnPostedProductionDetails.setProdStartOpn(rs_UnpostedProdId_Get.getInt("PROD_START_OPN"));
                objUnPostedProductionDetails.setProdEndOpn(rs_UnpostedProdId_Get.getInt("PROD_END_OPN"));
                objUnPostedProductionDetails.setProdStdHrs(rs_UnpostedProdId_Get.getFloat("PROD_STD_HRS"));
                objUnPostedProductionDetails.setProdTotHrs(rs_UnpostedProdId_Get.getFloat("PROD_TOT_HRS"));
                objUnPostedProductionDetails.setJobQtySnos(rs_UnpostedProdId_Get.getString("PROD_QTY_SNOS"));
                objUnPostedProductionDetails.setJobQty(rs_UnpostedProdId_Get.getInt("PROD_TOT_QTY"));
                woJbId = rs_UnpostedProdId_Get.getInt("WOJB_ID");
                ht_UnPostedJbDet_Get.put("WOJB_ID",new Integer(woJbId));
                
                PreparedStatement ps_UnPostedJbDet_Get = con.executeStatement(SQLMaster.UNPOSTED_JOB_DETAILS_GET_SQL_QUERY,ht_UnPostedJbDet_Get);
                ResultSet rs_UnPostedJbDet_Get =  ps_UnPostedJbDet_Get.executeQuery();
                if(rs_UnPostedJbDet_Get.next())
                {
                    objUnPostedProductionDetails.setJobName(rs_UnPostedJbDet_Get.getString("JB_NAME"));
                    objUnPostedProductionDetails.setJobDrwgNo(rs_UnPostedJbDet_Get.getString("JB_DWG_NO"));
                    objUnPostedProductionDetails.setJobRvsnNo(rs_UnPostedJbDet_Get.getString("JB_RVSN_NO"));
                    objUnPostedProductionDetails.setJobMatlType(rs_UnPostedJbDet_Get.getString("JB_MATL_TYP"));
                    objUnPostedProductionDetails.setWoNo(rs_UnPostedJbDet_Get.getString("WO_NO"));
                    
                }
                rs_UnPostedJbDet_Get.close();
                ps_UnPostedJbDet_Get.close();
                vec_Result.addElement(objUnPostedProductionDetails);
            }
            rs_UnpostedProdId_Get.close();
            ps_UnpostedProdId_Get.close();
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET UNPOSTED PRODN DETAILS ENDS");
        
        
        return vec_Result;
    }
    public HashMap postProductionDetails(Vector vec_ProdIds) throws ProductionException, SQLException
    {
        
        int prodId = 0;
        Hashtable ht_PostProdn = new Hashtable();
        Hashtable ht_woJbStatId_Get = new Hashtable();
        Hashtable ht_OpnsStatus_Change = new Hashtable();
        Hashtable ht_Status_Change = new Hashtable();
        HashMap hm_Result = new HashMap();
        DBConnection con = null;
        int woJbStatId = 0;
        
        String qtySnos = "";
        int startOpn = 0;
        int endOpn = 0;
        int woJbId = 0;
        int woId = 0;
        String workType = "";
        
        logger.info("POST PRODUCTION DETAILS STARTS");
        
        if(vec_ProdIds == null)
        {
            throw new ProductionException("PMEC005","PRODUCTION IDs VECTOR OBJECT IS NULL","");
            
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i = 0;i<vec_ProdIds.size();i++)
            {
                prodId = ((Integer)vec_ProdIds.elementAt(i)).intValue();
                ht_PostProdn.put("PROD_ID",new Integer(prodId));
                int result = con.executeUpdateStatement(SQLMaster.PRODN_DETAILS_POST_SQL_QUERY,ht_PostProdn);
                if(result>=1)
                {
                    hm_Result.put(vec_ProdIds.get(i),new Integer(0));
                    logger.info(vec_ProdIds.get(i)+" : PRODUCTION RECORD POSTED");
                    
                    PreparedStatement ps_ProdnDet = con.executeStatement(SQLMaster.PRODN_DETAILS_SELECT_SQL_QUERY,ht_PostProdn);
                    ResultSet rs_ProdnDet = ps_ProdnDet.executeQuery();
                    if(rs_ProdnDet.next())
                    {
                        woJbId = rs_ProdnDet.getInt("WOJB_ID");
                        qtySnos = rs_ProdnDet.getString("PROD_QTY_SNOS");
                        //totQty = rs_ProdnDet.getInt("PROD_TOT_QTY");
                        startOpn = rs_ProdnDet.getInt("PROD_START_OPN");
                        endOpn = rs_ProdnDet.getInt("PROD_END_OPN");
                        workType = rs_ProdnDet.getString("PROD_WORK_TYP");
                        //production may be for more than one job quantity...so loop comes
                        StringTokenizer stn = new StringTokenizer(qtySnos.trim(),",");
                        if(workType.equalsIgnoreCase("n"))
                        {
                            while(stn.hasMoreTokens())
                            {
                                int j = Integer.parseInt(stn.nextToken().trim());
                                ht_woJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
                                ht_woJbStatId_Get.put("WOJBSTAT_SNO",new Integer(j));
                                
                                PreparedStatement ps_woJbStatId = con.executeStatement(SQLMaster.PRODN_WOJBSTATID_GET_SQL_QUERY,ht_woJbStatId_Get);
                                ResultSet rs_woJbStatId = ps_woJbStatId.executeQuery();
                                if(rs_woJbStatId.next())
                                {
                                    woJbStatId = rs_woJbStatId.getInt("WOJBSTAT_ID");
                                }
                                rs_woJbStatId.close();
                                ps_woJbStatId.close();
                                for(int k =startOpn;k<=endOpn;k++)
                                {
                                    ht_OpnsStatus_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_OpnsStatus_Change.put("WOJBOPN_OPN_SNO",new Integer(k));
                                    
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_OPN_STATUS_CHANGE_SQL_QUERY,ht_OpnsStatus_Change);
                                }
                                
                                //status checking for remaining operations of particular quantity//
                                PreparedStatement ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_SQL_QUERY,ht_OpnsStatus_Change);
                                ResultSet rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();
                                
                                //if some value comes,all the operations for the particular quantity is not completed.
                                if(rs_OpnsStatus_Check.next())
                                {
                                    
                                    //to get woJbId and  woId for that particular quantity
                                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                    
                                    if(rs_Ids_Get.next())
                                    {
                                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                        woId = rs_Ids_Get.getInt("WO_ID");
                                    }
                                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                    ht_Status_Change.put("WO_ID",new Integer(woId));
                                    
                                    //to make particular quantity sno status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    //to make particular job status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    //to make particular work order status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                }
                                //all the operations for the particular quantity are completed..so we have to check another quantities of that job..
                                else
                                {
                                    //to get woJbId and  woId for that particular quantity
                                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                    if(rs_Ids_Get.next())
                                    {
                                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                        woId = rs_Ids_Get.getInt("WO_ID");
                                    }
                                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                    ht_Status_Change.put("WO_ID",new Integer(woId));
                                    
                                    //to make particular quantity sno status to 'C'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                    
                                    //status checking for remaining job quantities of particular job//
                                    
                                    PreparedStatement ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                    ResultSet rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();
                                    
                                    if(rs_WoJobQtysStatus_Check.next())
                                    {
                                        //to make particular job status to 'A'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        
                                        //to make particular work order status to 'A'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        
                                    }
                                    else
                                    {
                                        //to make particular job status to 'C'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        
                                        //status checking for remaining jobs of particular work order//
                                        PreparedStatement ps_WoJobStatus_Check = con.executeStatement(SQLMaster.PRODN_JOB_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                        ResultSet rs_WoJobStatus_Check = ps_WoJobStatus_Check.executeQuery();
                                        if(rs_WoJobStatus_Check.next())
                                        {
                                            //to make particular work order status to 'A'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        }
                                        else
                                        {
                                            //to make particular work order status to 'C'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        }
                                        rs_WoJobStatus_Check.close();
                                        ps_WoJobStatus_Check.close();
                                    }
                                    rs_WoJobQtysStatus_Check.close();
                                    ps_WoJobQtysStatus_Check.close();
                                    
                                }
                                rs_OpnsStatus_Check.close();
                                ps_OpnsStatus_Check.close();
                            }
                        }
                        else
                        {
                            while(stn.hasMoreTokens())
                            {
                                int j = Integer.parseInt(stn.nextToken().trim());
                                ht_woJbStatId_Get.put("WOJB_ID",new Integer(woJbId));
                                ht_woJbStatId_Get.put("WOJBSTAT_SNO",new Integer(j));
                                
                                PreparedStatement ps_woJbStatId = con.executeStatement(SQLMaster.PRODN_WOJBSTATID_GET_SQL_QUERY,ht_woJbStatId_Get);
                                ResultSet rs_woJbStatId = ps_woJbStatId.executeQuery();
                                if(rs_woJbStatId.next())
                                {
                                    woJbStatId = rs_woJbStatId.getInt("WOJBSTAT_ID");
                                }
                                rs_woJbStatId.close();
                                ps_woJbStatId.close();
                                for(int k =startOpn;k<=endOpn;k++)
                                {
                                    ht_OpnsStatus_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_OpnsStatus_Change.put("WOJBOPN_OPN_SNO",new Integer(k));
                                    
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_OPN_STATUS_CHANGE_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
                                }
                                
                                //status checking for remaining operations of particular quantity//
                                PreparedStatement ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
                                ResultSet rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();
                                
                                //if some value comes,all the operations for the particular quantity is not completed.
                                if(rs_OpnsStatus_Check.next())
                                {
                                    
                                    //to get woJbId and  woId for that particular quantity
                                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                    
                                    if(rs_Ids_Get.next())
                                    {
                                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                        woId = rs_Ids_Get.getInt("WO_ID");
                                    }
                                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                    ht_Status_Change.put("WO_ID",new Integer(woId));
                                    
                                    //to make particular quantity sno status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    //to make particular job status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    //to make particular work order status to 'A'
                                    result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                    
                                    
                                }
                                //all the operations for the particular quantity are completed..so we have to check another quantities of that job..
                                else
                                {
                                    //to get woJbId and  woId for that particular quantity
                                    
                                    PreparedStatement ps_Ids_Get = con.executeStatement(SQLMaster.PRODN_WOJBID_WOID_SELECT_SQL_QUERY,ht_OpnsStatus_Change);
                                    ResultSet rs_Ids_Get = ps_Ids_Get.executeQuery();
                                    if(rs_Ids_Get.next())
                                    {
                                        woJbId = rs_Ids_Get.getInt("WOJB_ID");
                                        woId = rs_Ids_Get.getInt("WO_ID");
                                    }
                                    ht_Status_Change.put("WOJBSTAT_ID",new Integer(woJbStatId));
                                    ht_Status_Change.put("WOJB_ID",new Integer(woJbId));
                                    ht_Status_Change.put("WO_ID",new Integer(woId));
                                    
                                    ps_OpnsStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_F_FOR_REWORK_SQL_QUERY,ht_OpnsStatus_Change);
                                    rs_OpnsStatus_Check = ps_OpnsStatus_Check.executeQuery();
                                    if(rs_OpnsStatus_Check.next())
                                    {
                                        //to make particular quantity sno status to 'C'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        
                                    }
                                    else
                                    {
                                        //to make particular quantity sno status to 'F'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                    }
                                    //status checking for remaining job quantities of particular job//
                                    PreparedStatement ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                    ResultSet rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();
                                    
                                    if(rs_WoJobQtysStatus_Check.next())
                                    {
                                        //
                                        //to make particular job status to 'A'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        
                                        //to make particular work order status to 'A'
                                        result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        
                                    }
                                    else
                                    {
                                        ps_WoJobQtysStatus_Check = con.executeStatement(SQLMaster.PRODN_WOJBSTATSNO_STATUS_F_CHECK_SQL_QUERY,ht_Status_Change);
                                        rs_WoJobQtysStatus_Check = ps_WoJobQtysStatus_Check.executeQuery();
                                        if(rs_WoJobQtysStatus_Check.next())
                                        {
                                            //to make particular job status to 'C'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        }
                                        else
                                        {
                                            //											to make particular job status to 'F'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WOJB_STATUS_CHANGETOF_SQL_QUERY,ht_Status_Change);
                                        }
                                        rs_WoJobQtysStatus_Check.close();
                                        ps_WoJobQtysStatus_Check.close();
                                        
                                        //status checking for remaining jobs of particular work order//
                                        PreparedStatement ps_WoJobStatus_Check = con.executeStatement(SQLMaster.PRODN_JOB_STATUS_CHECK_SQL_QUERY,ht_Status_Change);
                                        ResultSet rs_WoJobStatus_Check = ps_WoJobStatus_Check.executeQuery();
                                        if(rs_WoJobStatus_Check.next())
                                        {
                                            //to make particular work order status to 'A'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOA_SQL_QUERY,ht_Status_Change);
                                        }
                                        else
                                        {
                                            //										to make particular work order status to 'C'
                                            result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGETOC_SQL_QUERY,ht_Status_Change);
                                        }
                                        rs_WoJobStatus_Check.close();
                                        ps_WoJobStatus_Check.close();
                                    }
                                    
                                }
                                rs_OpnsStatus_Check.close();
                                ps_OpnsStatus_Check.close();
                            }
                            
                        }
                    }
                    rs_ProdnDet.close();
                    ps_ProdnDet.close();
                    /******to update Emp_Mstr table...  ****//*
                     // to get Emp Hrs Details from PROD_EMP..
                      
                      vec_Obj = con.getProductVector(SQLMaster.PROD_DATE_SELECT_SQL_QUERY,ht_PostProdn);
                      PrepareStatement_Query = con.getProductQuery(SQLMaster.PROD_DATE_SELECT_SQL_QUERY);
                      ps_ProdDate = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                      ResultSet rs_ProdDate = ps_ProdDate.executeQuery();
                      Date prodCrntDate;
                      if(rs_ProdDate.next())
                      {
                      prodCrntDate = rs_ProdDate.getTimestamp("PROD_CRNT_DATE");
                      }
                      GregorianCalendar gc = new GregorianCalendar();
                      gc.setTime(prodCrntDate);
                      
                      int month = gc.get(GregorianCalendar.MONTH);
                      int year = gc.get(GregorianCalendar.YEAR);
                      
                      while(rs_EmployeeHrsDet.next())
                      {
                      int empId = rs_EmployeeHrsDet.getInt("EMP_ID");
                      float empDtySlryHrs = rs_EmployeeHrsDet.getFloat("DT_SLRY_HRS");
                      float empOtSlryHrs = rs_EmployeeHrsDet.getFloat("OT_SLRY_HRS");
                      float empIncntvSlryHrs = rs_EmployeeHrsDet.getFloat("INCNTV_SLRY_HRS");
                      ht_EmpHrs_Det.put("EMP_ID",new Integer(empId));
                      ht_EmpHrs_Det.put("DT_SLRY_HRS",new Float(empDtySlryHrs));
                      ht_EmpHrs_Det.put("OT_SLRY_HRS",new Float(empOtSlryHrs));
                      ht_EmpHrs_Det.put("INCNTV_SLRY_HRS",new Float(empIncntvSlryHrs));
                      
                      //to update EMP_MSTR table
                       
                       vec_Obj = con.getProductVector(SQLMaster.EMPLOYEE_HRS_UPDATE_SQL_QUERY,ht_EmpHrs_Det);
                       PrepareStatement_Query = con.getProductQuery(SQLMaster.EMPLOYEE_HRS_UPDATE_SQL_QUERY);
                       ps_EmployeeHrsUpdate = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                       result = ps_EmployeeHrsUpdate.executeUpdate();
                       if(result>0)
                       {
                       System.out.println("Employee Hrs Updated");
                       }
                       
                       }
                       
                       
                       
                       
                       *//***** to update Mc_Mstr table... *****//*
                       
                       // to get mcCde and TotHrs from PROD table...
                        
                        vec_Obj = con.getProductVector(SQLMaster.MC_CDE_AND_TOT_HRS_SELECT_SQL_QUERY,ht_PostProdn);
                        PrepareStatement_Query = con.getProductQuery(SQLMaster.MC_CDE_AND_TOT_HRS_SELECT_SQL_QUERY);
                        ps_McDet = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                        ResultSet rs_McDet = ps_McDet.executeQuery();
                        if(rs_McDet.next())
                        {
                        String mcCde = rs_McDet.getString("MC_CDE");
                        float totHrs = rs_McDet.getFloat("TOT_HRS");
                        
                        ht_McDet.put("MC_CDE",mcCde);
                        ht_McDet.put("TOT_HRS",new Float(totHrs));
                        // to update MC_MSTR tabl..
                         
                         vec_Obj = con.getProductVector(SQLMaster.MC_USED_HRS_UPDATE_SQL_QUERY,ht_McDet);
                         PrepareStatement_Query = con.getProductQuery(SQLMaster.MC_USED_HRS_UPDATE_SQL_QUERY);
                         ps_McHrsUpdate = con.executeRawQuery(PrepareStatement_Query,vec_Obj);
                         result = ps_McHrsUpdate.executeUpdate();
                         if(result>0)
                         {
                         System.out.println("Employee Hrs Updated");
                         }
                         }
                         */
                    
                    con.commitTransaction();
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(vec_ProdIds.get(i),new Integer(1));
                    logger.info(vec_ProdIds.get(i)+" : PRODUCTION RECORD NOT POSTED");
                }
            }
        }
        catch (SQLException e)
        {
            con.rollBackTransaction();
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",e.toString());
            
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        logger.info("POST PRODUCTION DETAILS ENDS");
        
        return hm_Result;
        
    }
    public boolean isReworkOperations(int woJbStatId[],int startOpn,int endOpn) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        int count = 0;
        Hashtable ht_OpnsCheck = new Hashtable();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int j = 0;j<woJbStatId.length;j++)
            {
                
                
                ht_OpnsCheck.put("WOJBSTAT_ID",new Integer(woJbStatId[j]));
                ResultSet rs_Result = null;
                PreparedStatement ps_Result = null;
                for(int i = startOpn ;i<endOpn;i++)
                {
                    ht_OpnsCheck.put("WOJBOPN_OPN_SNO",new Integer(i));
                    ps_Result = con.executeStatement(SQLMaster.CHECK_OPERATIONS_FOR_REWORK,ht_OpnsCheck);
                    rs_Result = ps_Result.executeQuery();
                    if(rs_Result.next())
                    {
                        count = rs_Result.getInt(1);
                    }
                    rs_Result.close();
                    ps_Result.close();
                    if(count==0)
                    {
                        throw new ProductionException("PEC006","OPERATION NOT IN REWORK LOG","");
                    }
                }
            }
            retVal = true;
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return retVal;
    }
    
    public Vector getProdnJobDetailsForUpdateByWorkOrder(int woId,int prodId,int radlId,int finalProdId,int despatchId,int shipmentId) throws SQLException,ProductionException
    {
        Vector vec_Result = new Vector();
        int woJbId = 0;
        Hashtable ht_Prod_JbDet_Get = new Hashtable();
        Hashtable ht_Qty_Snos = new Hashtable();
        Hashtable ht_Opn_Det = new Hashtable();
        ResultSet rs_Prodn_JbDet_Get = null;
        ResultSet rs_PostedQty_Snos = null;
        ResultSet rs_LPdnDate_Shift = null;
        String pndQty_Snos = "";
        String unPostedQty_Snos = "";
        
        int woJbStatId = 0;
        int jbQtySno = 0;
        DBConnection con = null;
        SortString ss = new SortString();
        
        logger.info("GET PRODUCTION JOB DETAILS BY WORKORDER STARTS");
        
        con = DBConnectionFactory.getConnection();
        //put WO_ID into Hashtable
        ht_Prod_JbDet_Get.put("WO_ID",new Integer(woId));
        try
        {
            //get job details by giving WO_ID
            PreparedStatement ps_Prodn_JbDet_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBDETAILS_BY_WORKORDER,ht_Prod_JbDet_Get);
            rs_Prodn_JbDet_Get = ps_Prodn_JbDet_Get.executeQuery();
            ResultSet rs_Qty_Snos = null;
            PreparedStatement ps_Qty_Snos = null;
            
            while(rs_Prodn_JbDet_Get.next())
            {
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobId(rs_Prodn_JbDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_Prodn_JbDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_Prodn_JbDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_Prodn_JbDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_Prodn_JbDet_Get.getString("JB_MATL_TYP"));
                objProductionJobDetails.setTotQty(rs_Prodn_JbDet_Get.getInt("WOJB_QTY"));
                woJbId = rs_Prodn_JbDet_Get.getInt("WOJB_ID");
                objProductionJobDetails.setWoJbId(woJbId);
                
                
                pndQty_Snos = "";
                unPostedQty_Snos = "";
                
                //TO GET PENDING QUANTITY SNOS.....
                ht_Qty_Snos.put("WOJB_ID",new Integer(woJbId));
                ht_Qty_Snos.put("PROD_ID",new Integer(prodId));
                ht_Qty_Snos.put("RADL_ID",new Integer(radlId));
                ht_Qty_Snos.put("FPROD_ID",new Integer(finalProdId));
                ht_Qty_Snos.put("DESPATCH_ID",new Integer(despatchId));
                ht_Qty_Snos.put("SHIPMENT_ID",new Integer(shipmentId));
                
                ps_Qty_Snos = con.executeStatement(SQLMaster.GET_QUANTITY_SNOS,ht_Qty_Snos);
                rs_Qty_Snos = ps_Qty_Snos.executeQuery();
                while(rs_Qty_Snos.next())
                {
                    woJbStatId = rs_Qty_Snos.getInt("WOJBSTAT_ID");
                    jbQtySno = rs_Qty_Snos.getInt("WOJBSTAT_SNO");
                    
                    //to take all Operations from PROD table for this particular Quantity...
                    PreparedStatement ps_Prod_Det = con.executeStatement(SQLMaster.GET_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_Prod_Det = ps_Prod_Det.executeQuery();
                    String totOpns = "";
                    while(rs_Prod_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                                int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_Prod_Det.close();
                    ps_Prod_Det.close();
                    //to take all Operations from RADL table for this particular Quantity...
                    PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                    while(rs_RadlProd_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                                int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_RadlProd_Det.close();
                    ps_RadlProd_Det.close();
                    
                    //to take all Operations from F_PROD table for this particular Quantity...
                    PreparedStatement ps_Fprod_Det = con.executeStatement(SQLMaster.GET_FINAL_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_Fprod_Det = ps_Fprod_Det.executeQuery();
                    
                    while(rs_Fprod_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_Fprod_Det.getString("FPROD_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_Fprod_Det.getInt("FPROD_START_OPN");
                                int endOpn = rs_Fprod_Det.getInt("FPROD_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_Fprod_Det.close();
                    ps_Fprod_Det.close();
                    
                    //to take all Operations from DESPATCH table for this particular Quantity...
                    PreparedStatement ps_Despatch = con.executeStatement(SQLMaster.GET_DESPATCH_DETAILS_FOR_UPDATE_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_Despatch = ps_Despatch.executeQuery();
                    
                    while(rs_Despatch.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_Despatch.getString("DESPATCH_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_Despatch.getInt("DESPATCH_START_OPN");
                                int endOpn = rs_Despatch.getInt("DESPATCH_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_Despatch.close();
                    ps_Despatch.close();
                    
                    //to take all Operations from SHIPMENT table for this particular Quantity...
                    PreparedStatement ps_Shipment = con.executeStatement(SQLMaster.GET_SHIPMENT_DETAILS_FOR_UPDATE_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_Shipment = ps_Shipment.executeQuery();
                    
                    while(rs_Shipment.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_Shipment.getString("SHIPMENT_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_Shipment.getInt("SHIPMENT_START_OPN");
                                int endOpn = rs_Shipment.getInt("SHIPMENT_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_Shipment.close();
                    ps_Shipment.close();
                    
                    
                    
                    //to take all Operations from WO_JB_OPN table....
                    ht_Opn_Det.put("WOJBSTAT_ID",new Integer(woJbStatId));
                    PreparedStatement ps_Opn_Det = con.executeStatement(SQLMaster.GET_OPNDETAILS_SQL_QUERY,ht_Opn_Det);
                    ResultSet rs_Opn_Det = ps_Opn_Det.executeQuery();
                    int fg = 1;
                    String sumOpns = "";
                    while(rs_Opn_Det.next())
                    {
                        if(fg==1)
                        {
                            sumOpns = rs_Opn_Det.getInt("WOJBOPN_OPN_SNO")+"";
                        }
                        else
                        {
                            sumOpns = sumOpns +","+rs_Opn_Det.getInt("WOJBOPN_OPN_SNO");
                        }
                        fg = 0;
                    }
                    rs_Opn_Det.close();
                    ps_Opn_Det.close();
                    totOpns = ss.sortString(totOpns);
                    sumOpns = ss.sortString(sumOpns);
                    if(totOpns.equals(sumOpns)&& totOpns.length()>0)
                    {
                        if(unPostedQty_Snos.equals(""))
                            unPostedQty_Snos = jbQtySno +"";
                        else
                            unPostedQty_Snos = unPostedQty_Snos +","+jbQtySno;
                        
                    }
                    else
                    {
                        
                        
                        //to take all Rework Operations from PROD table for this particular Quantity...
                        ps_Prod_Det = con.executeStatement(SQLMaster.GET_REWORK_UPDATE_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                        rs_Prod_Det = ps_Prod_Det.executeQuery();
                        totOpns = "";
                        while(rs_Prod_Det.next())
                        {
                            String qtySnos = "";
                            qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                            StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                            while(st.hasMoreTokens())
                            {
                                int sno = Integer.parseInt(st.nextToken().trim());
                                if(sno == jbQtySno)
                                {
                                    int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                                    int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                                    for(int j = stOpn ;j<=endOpn ; j++)
                                    {
                                        if(totOpns.equals(""))
                                        {
                                            totOpns = j+"";
                                        }
                                        else
                                        {
                                            totOpns = totOpns +","+j;
                                        }
                                        
                                    }
                                    
                                }
                            }
                            
                        }
                        rs_Prod_Det.close();
                        ps_Prod_Det.close();
                        //to take all Rework Operations from RADL table for this particular Quantity...
                        ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_REWORK_UPDATE_RADL_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                        rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                        while(rs_RadlProd_Det.next())
                        {
                            String qtySnos = "";
                            qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                            StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                            while(st.hasMoreTokens())
                            {
                                int sno = Integer.parseInt(st.nextToken().trim());
                                if(sno == jbQtySno)
                                {
                                    int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                                    int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                                    for(int j = stOpn ;j<=endOpn ; j++)
                                    {
                                        if(totOpns.equals(""))
                                        {
                                            totOpns = j+"";
                                        }
                                        else
                                        {
                                            totOpns = totOpns +","+j;
                                        }
                                        
                                    }
                                    
                                }
                            }
                            
                        }
                        rs_RadlProd_Det.close();
                        ps_RadlProd_Det.close();
                        
                        //to take all Rework Operations from WO_JB_OPN table....
                        ht_Opn_Det.put("WOJBSTAT_ID",new Integer(woJbStatId));
                        ps_Opn_Det = con.executeStatement(SQLMaster.GET_OPNDETAILS_FOR_REWORK_SQL_QUERY,ht_Opn_Det);
                        rs_Opn_Det = ps_Opn_Det.executeQuery();
                        fg = 1;
                        sumOpns = "";
                        while(rs_Opn_Det.next())
                        {
                            if(fg==1)
                            {
                                sumOpns = rs_Opn_Det.getInt("WOJBOPN_OPN_SNO")+"";
                            }
                            else
                            {
                                sumOpns = sumOpns +","+rs_Opn_Det.getInt("WOJBOPN_OPN_SNO");
                            }
                            fg = 0;
                        }
                        rs_Opn_Det.close();
                        ps_Opn_Det.close();
                        totOpns = ss.sortString(totOpns);
                        sumOpns = ss.sortString(sumOpns);
                        if(totOpns.equals(sumOpns)&& totOpns.length()>0)
                        {
                            if(unPostedQty_Snos.equals(""))
                                unPostedQty_Snos = jbQtySno +"";
                            else
                                unPostedQty_Snos = unPostedQty_Snos +","+jbQtySno;
                            
                        }
                        else
                        {
                            if(pndQty_Snos.equals(""))
                                pndQty_Snos = jbQtySno +"";
                            else
                                pndQty_Snos = pndQty_Snos +","+jbQtySno;
                        }
                        
                        
                        
                    }
                    
                }
                rs_Qty_Snos.close();
                ps_Qty_Snos.close();
                //get Posted Quantity Serial Numbers
                PreparedStatement ps_PostedQty_Snos = con.executeStatement(SQLMaster.GET_POSTED_QUANTITY_SNOS,ht_Qty_Snos);
                rs_PostedQty_Snos =  ps_PostedQty_Snos.executeQuery();
                String postedQty_Snos = "";
                int i1=0;
                while(rs_PostedQty_Snos.next())
                {
                    if(i1 == 0)
                    {
                        postedQty_Snos = rs_PostedQty_Snos.getInt("WOJBSTAT_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedQty_Snos = postedQty_Snos +","+rs_PostedQty_Snos.getInt("WOJBSTAT_SNO");
                    }
                }
                rs_PostedQty_Snos.close();
                ps_PostedQty_Snos.close();
                objProductionJobDetails.setPostedQtySnos(ss.sortString(postedQty_Snos));
                objProductionJobDetails.setUnPostedQtySnos(ss.sortString(unPostedQty_Snos));
                objProductionJobDetails.setPendingQtySnos(ss.sortString(pndQty_Snos));
                
                //get Last Production Date and
                PreparedStatement ps_LPdnDate_Shift = con.executeStatement(SQLMaster.GET_UPDATE_LASTPRODNDATE_SHIFT,ht_Qty_Snos);
                rs_LPdnDate_Shift = ps_LPdnDate_Shift.executeQuery();
                if(rs_LPdnDate_Shift.next())
                {
                    objProductionJobDetails.setLastProdnDate(rs_LPdnDate_Shift.getTimestamp("PROD_CRNT_DATE"));
                    
                }
                rs_LPdnDate_Shift.close();
                ps_LPdnDate_Shift.close();
                
                //if pending qty snos is " " , no need to set into Vector.
                if(!objProductionJobDetails.getPendingQtySnos().equals(""))
                    vec_Result.addElement(objProductionJobDetails);
                
            }
            rs_Prodn_JbDet_Get.close();
            ps_Prodn_JbDet_Get.close();
            
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
            
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        logger.info("GET PRODUCTION JOB DETAILS FOR REWORK BY WORKORDER ENDS");
        
        
        return vec_Result;
        
    }
    public Vector getProdnJobOperationDetailsForUpdate(int woJbId,int prodId,int radlId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET PRODUCTION JOB OPERATION DETAILS FOR REWORK STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        ht_ProdnJbOpn_Get.put("PROD_ID",new Integer(prodId));
        ht_ProdnJbOpn_Get.put("RADL_ID",new Integer(radlId));
        
        
        try
        {
            PreparedStatement ps_ProdnJbOpn_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY,ht_ProdnJbOpn_Get);
            rs_ProdnJbOpn_Get = ps_ProdnJbOpn_Get.executeQuery();
            ResultSet rs_Prod_Det = null;
            PreparedStatement ps_Prod_Det = null;
            while(rs_ProdnJbOpn_Get.next())
            {
                ProductionJobQtyDetails objProductionJobQtyDetails = new ProductionJobQtyDetails();
                objProductionJobQtyDetails.setJobStatId(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_ID"));
                objProductionJobQtyDetails.setJobQtySno(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_SNO"));
                int jbQtySno = objProductionJobQtyDetails.getJobQtySno();
                
                ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                //to take all Operations from RADL table for this particular Quantity...
                PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                while(rs_RadlProd_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                            int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                                
                            }
                            
                        }
                    }
                    
                }
                
                if(BuildConfig.DMODE)
                    System.out.println("totOpns"+totOpns);
                totOpns = ss.sortString(totOpns);
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_PendStat_OpnSnos = ps_PendStat_OpnSnos.executeQuery();
                String pndOpn_Snos = "";
                String UnpostedOpn_Snos = "";
                
                
                while(rs_PendStat_OpnSnos.next())
                {
                    
                    int woJbOpnSno = rs_PendStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(totOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                                
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                    }
                    
                }
                
                //to get Rework Entered Operations from Prod and Radl..
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_REWORK_UPDATE_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String sumOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(sumOpns.equals(""))
                                {
                                    sumOpns = j+"";
                                }
                                else
                                {
                                    sumOpns = sumOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                
                
                
                ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_REWORK_UPDATE_RADL_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                while(rs_RadlProd_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                            int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(sumOpns.equals(""))
                                {
                                    sumOpns = j+"";
                                }
                                else
                                {
                                    sumOpns = sumOpns +","+j;
                                }
                                
                            }
                            
                        }
                    }
                    
                }
                
                PreparedStatement ps_Rework_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_PRODN_JOBSTAT_REWORK_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_Rework_OpnSnos = ps_Rework_OpnSnos.executeQuery();
                String rwkOpns = "";
                while(rs_Rework_OpnSnos.next())
                {
                    int woJbOpnSno = rs_Rework_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(sumOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                                
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                        if(rwkOpns.equals(""))
                        {
                            rwkOpns = woJbOpnSno+"";
                        }
                        else
                        {
                            rwkOpns = rwkOpns +","+ woJbOpnSno;
                        }
                        
                    }
                    
                }
                
                objProductionJobQtyDetails.setRwkOpns(ss.sortString(rwkOpns));
                objProductionJobQtyDetails.setPendingOpnSnos(ss.sortString(pndOpn_Snos));
                objProductionJobQtyDetails.setUnPostedOpnSnos(ss.sortString(UnpostedOpn_Snos));
                
                ht_JbPstdStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
                ResultSet rs_PostedStat_OpnSnos = ps_PostedStat_OpnSnos.executeQuery();
                
                String postedOpn_Snos = "";
                int i1 =0;
                
                while(rs_PostedStat_OpnSnos.next())
                {
                    if(i1==0)
                    {
                        postedOpn_Snos = rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedOpn_Snos = postedOpn_Snos + ","+rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    }
                }
                
                objProductionJobQtyDetails.setPostedOpnSnos(ss.sortString(postedOpn_Snos));
                
                //to get Last Production Date for a particular quantity...
                Date lastProdDate = null;
                Date tempDate = new Date();
                PreparedStatement ps_GetProdDet = con.executeStatement(SQLMaster.GET_LAST_PRODN_DATE_FOR_UPDATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                ResultSet rs_GetProdDet = ps_GetProdDet.executeQuery();
                
                while(rs_GetProdDet.next())
                {
                    String prodQtySnos = rs_GetProdDet.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                    while(st.hasMoreTokens())
                    {
                        
                        int prodQtySno = Integer.parseInt(st.nextToken().trim());
                        if(prodQtySno==jbQtySno)
                        {
                            tempDate = rs_GetProdDet.getTimestamp("PROD_CRNT_DATE");
                            
                            if(lastProdDate == null)
                            {
                                lastProdDate = tempDate;
                                
                            }
                            else
                            {
                                if(tempDate.after(lastProdDate))
                                    lastProdDate = tempDate;
                            }
                            break;
                        }
                    }
                }
                
                
                
                objProductionJobQtyDetails.setLastProdDate(lastProdDate);
                
                //if pending Opn Snos is " " , no need to set into Vector..
                if(!objProductionJobQtyDetails.getPendingOpnSnos().equals(""))
                    vec_Result.addElement(objProductionJobQtyDetails);
                
            }
            rs_ProdnJbOpn_Get.close();
            ps_ProdnJbOpn_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        
        logger.info("GET PRODUCTION JOB OPERATION DETAILS FOR REWORK ENDS");
        
        return vec_Result;
    }
    public Vector getProdnJobDetailsByWorkOrder(int woId) throws SQLException,ProductionException
    {
        Vector vec_Result = new Vector();
        int woJbId = 0;
        Hashtable ht_Prod_JbDet_Get = new Hashtable();
        Hashtable ht_Qty_Snos = new Hashtable();
        Hashtable ht_Opn_Det = new Hashtable();
        ResultSet rs_Prodn_JbDet_Get = null;
        ResultSet rs_PostedQty_Snos = null;
        ResultSet rs_LPdnDate_Shift = null;
        String pndQty_Snos = "";
        String unPostedQty_Snos = "";
        
        int woJbStatId = 0;
        int jbQtySno = 0;
        DBConnection con = null;
        SortString ss = new SortString();
        
        logger.info("GET PRODUCTION JOB DETAILS BY WORKORDER STARTS");
        
        con = DBConnectionFactory.getConnection();
        //put WO_ID into Hashtable
        ht_Prod_JbDet_Get.put("WO_ID",new Integer(woId));
        try
        {
            //get job details by giving WO_ID
            PreparedStatement ps_Prodn_JbDet_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBDETAILS_BY_WORKORDER,ht_Prod_JbDet_Get);
            rs_Prodn_JbDet_Get = ps_Prodn_JbDet_Get.executeQuery();
            PreparedStatement ps_Qty_Snos = null;
            ResultSet rs_Qty_Snos = null;
            while(rs_Prodn_JbDet_Get.next())
            {
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobId(rs_Prodn_JbDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_Prodn_JbDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_Prodn_JbDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_Prodn_JbDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_Prodn_JbDet_Get.getString("JB_MATL_TYP"));
                objProductionJobDetails.setTotQty(rs_Prodn_JbDet_Get.getInt("WOJB_QTY"));
                woJbId = rs_Prodn_JbDet_Get.getInt("WOJB_ID");
                objProductionJobDetails.setWoJbId(woJbId);
                
                pndQty_Snos = "";
                unPostedQty_Snos = "";
                
                //TO GET PENDING QUANTITY SNOS.....
                ht_Qty_Snos.put("WOJB_ID",new Integer(woJbId));
                ps_Qty_Snos = con.executeStatement(SQLMaster.GET_QUANTITY_SNOS,ht_Qty_Snos);
                rs_Qty_Snos = ps_Qty_Snos.executeQuery();
                ResultSet rs_Prod_Det = null;
                PreparedStatement ps_Prod_Det = null;
                while(rs_Qty_Snos.next())
                {
                    woJbStatId = rs_Qty_Snos.getInt("WOJBSTAT_ID");
                    jbQtySno = rs_Qty_Snos.getInt("WOJBSTAT_SNO");
                    
                    //to take all Operations from PROD table for this particular Quantity...
                    
                    ps_Prod_Det = con.executeStatement(SQLMaster.GET_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                    rs_Prod_Det = ps_Prod_Det.executeQuery();
                    String totOpns = "";
                    while(rs_Prod_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                                int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    
                    //to take all Operations from RADL table for this particular Quantity...
                    PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                    while(rs_RadlProd_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                                int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    
                    //to take all Operations from F_PROD table for this particular Quantity...
                    PreparedStatement ps_FProd_Det = con.executeStatement(SQLMaster.GET_FINAL_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                    ResultSet rs_FProd_Det = ps_FProd_Det.executeQuery();
                    while(rs_FProd_Det.next())
                    {
                        String qtySnos = "";
                        qtySnos = rs_FProd_Det.getString("FPROD_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                        while(st.hasMoreTokens())
                        {
                            int sno = Integer.parseInt(st.nextToken().trim());
                            if(sno == jbQtySno)
                            {
                                int stOpn = rs_FProd_Det.getInt("FPROD_START_OPN");
                                int endOpn = rs_FProd_Det.getInt("FPROD_END_OPN");
                                for(int j = stOpn ;j<=endOpn ; j++)
                                {
                                    if(totOpns.equals(""))
                                    {
                                        totOpns = j+"";
                                    }
                                    else
                                    {
                                        totOpns = totOpns +","+j;
                                    }
                                    
                                }
                                
                            }
                        }
                        
                    }
                    rs_FProd_Det.close();
                    ps_FProd_Det.close();
                    
                    //to take all Operations from WO_JB_OPN table....
                    ht_Opn_Det.put("WOJBSTAT_ID",new Integer(woJbStatId));
                    PreparedStatement ps_Opn_Det = con.executeStatement(SQLMaster.GET_OPNDETAILS_SQL_QUERY,ht_Opn_Det);
                    ResultSet rs_Opn_Det = ps_Opn_Det.executeQuery();
                    int fg = 1;
                    String sumOpns = "";
                    while(rs_Opn_Det.next())
                    {
                        if(fg==1)
                        {
                            sumOpns = rs_Opn_Det.getInt("WOJBOPN_OPN_SNO")+"";
                        }
                        else
                        {
                            sumOpns = sumOpns +","+rs_Opn_Det.getInt("WOJBOPN_OPN_SNO");
                        }
                        fg = 0;
                    }
                    rs_Opn_Det.close();
                    ps_Opn_Det.close();
                    totOpns = ss.sortString(totOpns);
                    sumOpns = ss.sortString(sumOpns);
                    if(totOpns.equals(sumOpns)&& totOpns.length()>0)
                    {
                        if(unPostedQty_Snos.equals(""))
                            unPostedQty_Snos = jbQtySno +"";
                        else
                            unPostedQty_Snos = unPostedQty_Snos +","+jbQtySno;
                        
                    }
                    else
                    {
                        
                        //to take all Rework Operations from PROD table for this particular Quantity...
                        ps_Prod_Det = con.executeStatement(SQLMaster.GET_REWORK_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                        rs_Prod_Det = ps_Prod_Det.executeQuery();
                        totOpns = "";
                        while(rs_Prod_Det.next())
                        {
                            String qtySnos = "";
                            qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                            StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                            while(st.hasMoreTokens())
                            {
                                int sno = Integer.parseInt(st.nextToken().trim());
                                if(sno == jbQtySno)
                                {
                                    int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                                    int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                                    for(int j = stOpn ;j<=endOpn ; j++)
                                    {
                                        if(totOpns.equals(""))
                                        {
                                            totOpns = j+"";
                                        }
                                        else
                                        {
                                            totOpns = totOpns +","+j;
                                        }
                                        
                                    }
                                    
                                }
                            }
                            
                        }
                        rs_Prod_Det.close();
                        ps_Prod_Det.close();
                        
                        //to take all Rework Operations from RADL table for this particular Quantity...
                        ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_REWORK_RADL_PRODDETAILS_SQL_QUERY,ht_Qty_Snos);
                        rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                        while(rs_RadlProd_Det.next())
                        {
                            String qtySnos = "";
                            qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                            StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                            while(st.hasMoreTokens())
                            {
                                int sno = Integer.parseInt(st.nextToken().trim());
                                if(sno == jbQtySno)
                                {
                                    int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                                    int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                                    for(int j = stOpn ;j<=endOpn ; j++)
                                    {
                                        if(totOpns.equals(""))
                                        {
                                            totOpns = j+"";
                                        }
                                        else
                                        {
                                            totOpns = totOpns +","+j;
                                        }
                                        
                                    }
                                    
                                }
                            }
                            
                        }
                        rs_RadlProd_Det.close();
                        ps_RadlProd_Det.close();
                        //to take all Rework Operations from WO_JB_OPN table....
                        ht_Opn_Det.put("WOJBSTAT_ID",new Integer(woJbStatId));
                        ps_Opn_Det = con.executeStatement(SQLMaster.GET_OPNDETAILS_FOR_REWORK_SQL_QUERY,ht_Opn_Det);
                        rs_Opn_Det = ps_Opn_Det.executeQuery();
                        fg = 1;
                        sumOpns = "";
                        while(rs_Opn_Det.next())
                        {
                            if(fg==1)
                            {
                                sumOpns = rs_Opn_Det.getInt("WOJBOPN_OPN_SNO")+"";
                            }
                            else
                            {
                                sumOpns = sumOpns +","+rs_Opn_Det.getInt("WOJBOPN_OPN_SNO");
                            }
                            fg = 0;
                        }
                        rs_Opn_Det.close();
                        ps_Opn_Det.close();
                        totOpns = ss.sortString(totOpns);
                        sumOpns = ss.sortString(sumOpns);
                        if(totOpns.equals(sumOpns)&& totOpns.length()>0)
                        {
                            if(unPostedQty_Snos.equals(""))
                                unPostedQty_Snos = jbQtySno +"";
                            else
                                unPostedQty_Snos = unPostedQty_Snos +","+jbQtySno;
                            
                        }
                        else
                        {
                            if(pndQty_Snos.equals(""))
                                pndQty_Snos = jbQtySno +"";
                            else
                                pndQty_Snos = pndQty_Snos +","+jbQtySno;
                        }
                        
                        
                    }
                    
                }
                rs_Qty_Snos.close();
                ps_Qty_Snos.close();
                //get Posted Quantity Serial Numbers
                PreparedStatement ps_PostedQty_Snos = con.executeStatement(SQLMaster.GET_POSTED_QUANTITY_SNOS,ht_Qty_Snos);
                rs_PostedQty_Snos = ps_PostedQty_Snos.executeQuery();
                String postedQty_Snos = "";
                int i1=0;
                while(rs_PostedQty_Snos.next())
                {
                    if(i1 == 0)
                    {
                        postedQty_Snos = rs_PostedQty_Snos.getInt("WOJBSTAT_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedQty_Snos = postedQty_Snos +","+rs_PostedQty_Snos.getInt("WOJBSTAT_SNO");
                    }
                }
                rs_PostedQty_Snos.close();
                ps_PostedQty_Snos.close();
                objProductionJobDetails.setPostedQtySnos(ss.sortString(postedQty_Snos));
                objProductionJobDetails.setUnPostedQtySnos(ss.sortString(unPostedQty_Snos));
                objProductionJobDetails.setPendingQtySnos(ss.sortString(pndQty_Snos));
                
                
                //get Last Production Date and
                PreparedStatement ps_LPdnDate_Shift = con.executeStatement(SQLMaster.GET_LASTPRODNDATE_SHIFT,ht_Qty_Snos);
                rs_LPdnDate_Shift = ps_LPdnDate_Shift.executeQuery();
                if(rs_LPdnDate_Shift.next())
                {
                    objProductionJobDetails.setLastProdnDate(rs_LPdnDate_Shift.getTimestamp("PROD_CRNT_DATE"));
                }
                rs_LPdnDate_Shift.close();
                ps_LPdnDate_Shift.close();
                
                //if pending qty snos is " " , no need to set into Vector.
                if(!objProductionJobDetails.getPendingQtySnos().equals(""))
                    vec_Result.addElement(objProductionJobDetails);
                
            }
            rs_Prodn_JbDet_Get.close();
            ps_Prodn_JbDet_Get.close();
            
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
            
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        logger.info("GET PRODUCTION JOB DETAILS BY WORKORDER ENDS");
        
        
        return vec_Result;
        
    }
    public Vector getProdnJobOperationDetails(int woJbId) throws ProductionException,SQLException
    {
        Hashtable ht_ProdnJbOpn_Get = new Hashtable();
        Hashtable ht_JbStatSno_Get = new Hashtable();
        Hashtable ht_JbPstdStatSno_Get = new Hashtable();
        ResultSet rs_ProdnJbOpn_Get = null;
        Vector vec_Result = new Vector();
        DBConnection con=null;
        SortString ss = new SortString();
        
        logger.info("GET PRODUCTION JOB OPERATION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
        
        try
        {
            PreparedStatement ps_ProdnJbOpn_Get = con.executeStatement(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY,ht_ProdnJbOpn_Get);
            rs_ProdnJbOpn_Get = ps_ProdnJbOpn_Get.executeQuery();
            PreparedStatement ps_Prod_Det = null;
            ResultSet rs_Prod_Det = null;
            while(rs_ProdnJbOpn_Get.next())
            {
                ProductionJobQtyDetails objProductionJobQtyDetails = new ProductionJobQtyDetails();
                objProductionJobQtyDetails.setJobStatId(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_ID"));
                objProductionJobQtyDetails.setJobQtySno(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_SNO"));
                int jbQtySno = objProductionJobQtyDetails.getJobQtySno();
                
                ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String totOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                
                
                //to take all Operations from RADL table for this particular Quantity...
                
                PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                while(rs_RadlProd_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                            int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(totOpns.equals(""))
                                {
                                    totOpns = j+"";
                                }
                                else
                                {
                                    totOpns = totOpns +","+j;
                                }
                                
                            }
                            
                        }
                    }
                    
                }
                
                if(BuildConfig.DMODE)
                    System.out.println("totOpns"+totOpns);
                totOpns = ss.sortString(totOpns);
                PreparedStatement ps_PendStat_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_PendStat_OpnSnos = ps_PendStat_OpnSnos.executeQuery();
                String pndOpn_Snos = "";
                String UnpostedOpn_Snos = "";
                
                
                while(rs_PendStat_OpnSnos.next())
                {
                    
                    int woJbOpnSno = rs_PendStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(totOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                                
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                    }
                    
                }
                
                //to get Rework Entered Operations from Prod and Radl..
                ps_Prod_Det = con.executeStatement(SQLMaster.GET_REWORK_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_Prod_Det = ps_Prod_Det.executeQuery();
                String sumOpns = "";
                while(rs_Prod_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
                            int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(sumOpns.equals(""))
                                {
                                    sumOpns = j+"";
                                }
                                else
                                {
                                    sumOpns = sumOpns +","+j;
                                }
                            }
                        }
                        
                    }
                }
                
                
                
                ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_REWORK_RADL_PRODDETAILS_SQL_QUERY,ht_ProdnJbOpn_Get);
                rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
                while(rs_RadlProd_Det.next())
                {
                    String qtySnos = "";
                    qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
                    while(st.hasMoreTokens())
                    {
                        int sno = Integer.parseInt(st.nextToken().trim());
                        if(sno == jbQtySno)
                        {
                            int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
                            int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
                            for(int j = stOpn ;j<=endOpn ; j++)
                            {
                                if(sumOpns.equals(""))
                                {
                                    sumOpns = j+"";
                                }
                                else
                                {
                                    sumOpns = sumOpns +","+j;
                                }
                                
                            }
                            
                        }
                    }
                    
                }
                
                PreparedStatement ps_Rework_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_PRODN_JOBSTAT_REWORK_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
                ResultSet rs_Rework_OpnSnos = ps_Rework_OpnSnos.executeQuery();
                String rwkOpns = "";
                while(rs_Rework_OpnSnos.next())
                {
                    int woJbOpnSno = rs_Rework_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    int fl = 0;
                    StringTokenizer st = new StringTokenizer(sumOpns,",");
                    while(st.hasMoreTokens())
                    {
                        
                        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
                        {
                            fl = 1;
                            if(UnpostedOpn_Snos.equals(""))
                            {
                                UnpostedOpn_Snos = woJbOpnSno+"";
                                
                            }
                            else
                            {
                                UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
                            }
                        }
                    }
                    if(fl==0)
                    {
                        if(pndOpn_Snos.equals(""))
                        {
                            pndOpn_Snos = woJbOpnSno+"";
                            
                        }
                        else
                        {
                            pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
                        }
                        if(rwkOpns.equals(""))
                        {
                            rwkOpns = woJbOpnSno+"";
                        }
                        else
                        {
                            rwkOpns = rwkOpns +","+ woJbOpnSno;
                        }
                        
                    }
                    
                }
                rs_Rework_OpnSnos.close();
                ps_Rework_OpnSnos.close();
                
                objProductionJobQtyDetails.setRwkOpns(ss.sortString(rwkOpns));
                objProductionJobQtyDetails.setPendingOpnSnos(ss.sortString(pndOpn_Snos));
                objProductionJobQtyDetails.setUnPostedOpnSnos(ss.sortString(UnpostedOpn_Snos));
                
                ht_JbPstdStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
                
                PreparedStatement ps_PostedStat_OpnSnos = con.executeStatement(SQLMaster.GET_POSTED_PRODN_JOBSTAT_OPERATION_SNOS_SQL_QUERY,ht_JbPstdStatSno_Get);
                ResultSet rs_PostedStat_OpnSnos = ps_PostedStat_OpnSnos.executeQuery();
                
                String postedOpn_Snos = "";
                int i1 =0;
                
                while(rs_PostedStat_OpnSnos.next())
                {
                    if(i1==0)
                    {
                        postedOpn_Snos = rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO")+"";
                        i1++;
                    }
                    else
                    {
                        postedOpn_Snos = postedOpn_Snos + ","+rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
                    }
                }
                rs_PostedStat_OpnSnos.close();
                ps_PostedStat_OpnSnos.close();
                objProductionJobQtyDetails.setPostedOpnSnos(ss.sortString(postedOpn_Snos));
                
                //to get Last Production Date for a particular quantity...
                Date lastProdDate = null;
                Date tempDate = new Date();
                
                PreparedStatement ps_GetProdDet = con.executeStatement(SQLMaster.GET_LAST_PRODN_DATE_SQL_QUERY,ht_ProdnJbOpn_Get);
                ResultSet rs_GetProdDet = ps_GetProdDet.executeQuery();
                
                while(rs_GetProdDet.next())
                {
                    String prodQtySnos = rs_GetProdDet.getString("PROD_QTY_SNOS");
                    StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                    while(st.hasMoreTokens())
                    {
                        int prodQtySno = Integer.parseInt(st.nextToken().trim());
                        if(prodQtySno==jbQtySno)
                        {
                            tempDate = rs_GetProdDet.getTimestamp("PROD_CRNT_DATE");
                            
                            if(lastProdDate == null)
                            {
                                lastProdDate = tempDate;
                                
                            }
                            else
                            {
                                if(tempDate.after(lastProdDate))
                                    lastProdDate = tempDate;
                            }
                            break;
                        }
                    }
                }
                rs_GetProdDet.close();
                ps_GetProdDet.close();
                
                if(BuildConfig.DMODE)
                    System.out.println("lpdate :"+lastProdDate);
                objProductionJobQtyDetails.setLastProdDate(lastProdDate);
                
                //if pending Opn Snos is " " , no need to set into Vector..
                if(!objProductionJobQtyDetails.getPendingOpnSnos().equals(""))
                    vec_Result.addElement(objProductionJobQtyDetails);
                
                
            }
            rs_ProdnJbOpn_Get.close();
            ps_ProdnJbOpn_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        if(BuildConfig.DMODE)
        {
            logger.info("GET PRODUCTION JOB OPERATION DETAILS ENDS");
        }
        return vec_Result;
    }
    
    
    /*public Vector getProdnJobDetailsByWorkOrderForRework(int woId) throws SQLException,ProductionException
     {
     Vector vec_Result = new Vector();
     int woJbId = 0;
     Hashtable ht_Prod_JbDet_Get = new Hashtable();
     Hashtable ht_ProdDet_Get = new Hashtable();
     Hashtable ht_Qty_Snos = new Hashtable();
     Hashtable ht_Opn_Det = new Hashtable();
     ResultSet rs_Prodn_JbDet_Get = null;
     ResultSet rs_PndQty_Snos = null;
     ResultSet rs_PostedQty_Snos = null;
     ResultSet rs_UnPostedQty_Snos = null;
     ResultSet rs_LPdnDate_Shift = null;
     String pndQty_Snos = "";
     String unPostedQty_Snos = "";
     
     Vector vec_Obj = new Vector();
     int i = 0;
     int woJbStatId = 0;
     int jbQtySno = 0;
     DBConnection con = null;
     SortString ss = new SortString();
     if(BuildConfig.DMODE)
     {
     logger.info("GET PRODUCTION JOB DETAILS BY WORKORDER  FOR REWORK STARTS");
     }
     try
     {
     con = DBConnectionFactory.getConnection();
     //put WO_ID into Hashtable
      ht_Prod_JbDet_Get.put("WO_ID",new Integer(woId));
      Vector vec_Prodn_JbDet_Get = con.getProductVector(SQLMaster.GET_PRODUCTION_JOBDETAILS_BY_WORKORDER_FOR_REWORK,ht_Prod_JbDet_Get);
      String query = con.getProductQuery(SQLMaster.GET_PRODUCTION_JOBDETAILS_BY_WORKORDER_FOR_REWORK);
      PreparedStatement ps_Prodn_JbDet_Get = con.executeRawQuery(query,vec_Prodn_JbDet_Get);
      
      try
      {
      //get job details by giving WO_ID
       rs_Prodn_JbDet_Get = ps_Prodn_JbDet_Get.executeQuery();
       
       while(rs_Prodn_JbDet_Get.next())
       {
       ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
       objProductionJobDetails.setJobId(rs_Prodn_JbDet_Get.getInt("JB_ID"));
       objProductionJobDetails.setJobName(rs_Prodn_JbDet_Get.getString("JB_NAME"));
       objProductionJobDetails.setDwgNo(rs_Prodn_JbDet_Get.getString("JB_DWG_NO"));
       objProductionJobDetails.setRvsnNo(rs_Prodn_JbDet_Get.getString("JB_RVSN_NO"));
       objProductionJobDetails.setMatlType(rs_Prodn_JbDet_Get.getString("JB_MATL_TYP"));
       objProductionJobDetails.setTotQty(rs_Prodn_JbDet_Get.getInt("WOJB_QTY"));
       woJbId = rs_Prodn_JbDet_Get.getInt("WOJB_ID");
       objProductionJobDetails.setWoJbId(woJbId);
       
       
       pndQty_Snos = "";
       unPostedQty_Snos = "";
       
       //TO GET PENDING QUANTITY SNOS.....
        ht_Qty_Snos.put("WOJB_ID",new Integer(woJbId));
        Vector vec_Qty_Snos = con.getProductVector(SQLMaster.GET_QUANTITY_SNOS,ht_Qty_Snos);
        String query_Qty_Snos = con.getProductQuery(SQLMaster.GET_QUANTITY_SNOS);
        PreparedStatement ps_Qty_Snos = con.executeRawQuery(query_Qty_Snos,vec_Qty_Snos);
        ResultSet rs_Qty_Snos = ps_Qty_Snos.executeQuery();
        int flag = 0;
        while(rs_Qty_Snos.next())
        {
        woJbStatId = rs_Qty_Snos.getInt("WOJBSTAT_ID");
        jbQtySno = rs_Qty_Snos.getInt("WOJBSTAT_SNO");
        
        //to take all Operations from PROD table for this particular Quantity...
         
         vec_Obj = con.getProductVector(SQLMaster.GET_PRODDETAILS_FOR_REWORK_SQL_QUERY,ht_Qty_Snos);
         query = con.getProductQuery(SQLMaster.GET_PRODDETAILS_FOR_REWORK_SQL_QUERY);
         PreparedStatement ps_Prod_Det = con.executeRawQuery(query,vec_Obj);
         ResultSet rs_Prod_Det = ps_Prod_Det.executeQuery();
         String totOpns = "";
         while(rs_Prod_Det.next())
         {
         String qtySnos = "";
         qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
         StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
         while(st.hasMoreTokens())
         {
         int sno = Integer.parseInt(st.nextToken().trim());
         if(sno == jbQtySno)
         {
         int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
         int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
         for(int j = stOpn ;j<=endOpn ; j++)
         {
         if(totOpns.equals(""))
         {
         totOpns = j+"";
         }
         else
         {
         totOpns = totOpns +","+j;
         }
         
         }
         
         }
         }
         
         }
         
         //to take all Operations from RADL table for this particular Quantity...
          
          vec_Obj = con.getProductVector(SQLMaster.GET_RADL_PRODDETAILS_FOR_REWORK_SQL_QUERY,ht_Qty_Snos);
          query = con.getProductQuery(SQLMaster.GET_RADL_PRODDETAILS_FOR_REWORK_SQL_QUERY);
          PreparedStatement ps_RadlProd_Det = con.executeRawQuery(query,vec_Obj);
          ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
          while(rs_RadlProd_Det.next())
          {
          String qtySnos = "";
          qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
          StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
          while(st.hasMoreTokens())
          {
          int sno = Integer.parseInt(st.nextToken().trim());
          if(sno == jbQtySno)
          {
          int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
          int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
          for(int j = stOpn ;j<=endOpn ; j++)
          {
          if(totOpns.equals(""))
          {
          totOpns = j+"";
          }
          else
          {
          totOpns = totOpns +","+j;
          }
          
          }
          
          }
          }
          
          }
          
          
          
          //to take all Operations from WO_JB_OPN table....
           ht_Opn_Det.put("WOJBSTAT_ID",new Integer(woJbStatId));
           vec_Obj = con.getProductVector(SQLMaster.GET_OPNDETAILS_FOR_REWORK_SQL_QUERY,ht_Opn_Det);
           query = con.getProductQuery(SQLMaster.GET_OPNDETAILS_FOR_REWORK_SQL_QUERY);
           PreparedStatement ps_Opn_Det = con.executeRawQuery(query,vec_Obj);
           ResultSet rs_Opn_Det = ps_Opn_Det.executeQuery();
           int fg = 1;
           String sumOpns = "";
           while(rs_Opn_Det.next())
           {
           if(fg==1)
           {
           sumOpns = rs_Opn_Det.getInt("WOJBOPN_OPN_SNO")+"";
           }
           else
           {
           sumOpns = sumOpns +","+rs_Opn_Det.getInt("WOJBOPN_OPN_SNO");
           }
           fg = 0;
           }
           totOpns = ss.sortString(totOpns);
           sumOpns = ss.sortString(sumOpns);
           if(totOpns.equals(sumOpns)&& totOpns.length()>0)
           {
           if(unPostedQty_Snos.equals(""))
           unPostedQty_Snos = jbQtySno +"";
           else
           unPostedQty_Snos = unPostedQty_Snos +","+jbQtySno;
           
           }
           else
           {
           //if particular Qty is completed.that will also come here.so we have to eliminate...
            if(fg==0)
            {
            
            if(pndQty_Snos.equals(""))
            pndQty_Snos = jbQtySno +"";
            else
            pndQty_Snos = pndQty_Snos +","+jbQtySno;
            }
            }
            
            }
            //get Posted Quantity Serial Numbers
             Vector vec_PostedQty_Snos = con.getProductVector(SQLMaster.GET_POSTED_QUANTITY_SNOS,ht_Qty_Snos);
             String query_PostedQty_Snos = con.getProductQuery(SQLMaster.GET_POSTED_QUANTITY_SNOS);
             PreparedStatement ps_PostedQty_Snos = con.executeRawQuery(query_PostedQty_Snos,vec_PostedQty_Snos);
             
             rs_PostedQty_Snos = ps_PostedQty_Snos.executeQuery();
             String postedQty_Snos = "";
             int i1=0;
             while(rs_PostedQty_Snos.next())
             {
             if(i1 == 0)
             {
             postedQty_Snos = rs_PostedQty_Snos.getInt("WOJBSTAT_SNO")+"";
             i1++;
             }
             else
             {
             postedQty_Snos = postedQty_Snos +","+rs_PostedQty_Snos.getInt("WOJBSTAT_SNO");
             }
             }
             objProductionJobDetails.setPostedQtySnos(ss.sortString(postedQty_Snos));
             objProductionJobDetails.setUnPostedQtySnos(ss.sortString(unPostedQty_Snos));
             objProductionJobDetails.setPendingQtySnos(ss.sortString(pndQty_Snos));
             //get Last Production Date and
              Vector vec_LPdnDate_Shift= con.getProductVector(SQLMaster.GET_LASTPRODNDATE_SHIFT,ht_PndQty_Snos);
              String query_LPdnDate_Shift = con.getProductQuery(SQLMaster.GET_LASTPRODNDATE_SHIFT);
              PreparedStatement ps_LPdnDate_Shift = con.executeRawQuery(query_LPdnDate_Shift,vec_LPdnDate_Shift);
              rs_LPdnDate_Shift = ps_LPdnDate_Shift.executeQuery();
              
              
              vec_Result.addElement(objProductionJobDetails);
              
              }
              
              
              
              }
              catch(SQLException sqle)
              {
              if(BuildConfig.DMODE)
              {
              sqle.printStackTrace();
              }
              throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
              
              }
              }
              catch(SQLException sqle)
              {
              if(BuildConfig.DMODE)
              {
              sqle.printStackTrace();
              }
              throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
              
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
              
              logger.error("CONNECTION CLOSING EXCEPTION", clse);
              throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
              }
              }
              if(BuildConfig.DMODE)
              {
              logger.info("GET PRODUCTION JOB DETAILS BY WORKORDER ENDS");
              }
              
              
              return vec_Result;
              
              }
              public Vector getProdnJobOperationDetailsForRework(int woJbId) throws ProductionException,SQLException
              {
              Hashtable ht_ProdnJbOpn_Get = new Hashtable();
              Hashtable ht_JbStatSno_Get = new Hashtable();
              Hashtable ht_JbPstdStatSno_Get = new Hashtable();
              ResultSet rs_ProdnJbOpn_Get = null;
              Vector vec_Result = new Vector();
              DBConnection con=null;
              SortString ss = new SortString();
              if(BuildConfig.DMODE)
              {
              logger.info("GET PRODUCTION JOB OPERATION DETAILS STARTS");
              }
              try
              {
              con = DBConnectionFactory.getConnection();
              ht_ProdnJbOpn_Get.put("WOJB_ID",new Integer(woJbId));
              
              Vector vec_Obj = con.getProductVector(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY,ht_ProdnJbOpn_Get);
              String query = con.getProductQuery(SQLMaster.GET_PRODUCTION_JOBSTATSNO_SQL_QUERY);
              PreparedStatement ps_ProdnJbOpn_Get = con.executeRawQuery(query,vec_Obj);
              try
              {
              rs_ProdnJbOpn_Get = ps_ProdnJbOpn_Get.executeQuery();
              
              while(rs_ProdnJbOpn_Get.next())
              {
              ProductionJobQtyDetails objProductionJobQtyDetails = new ProductionJobQtyDetails();
              objProductionJobQtyDetails.setJobStatId(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_ID"));
              objProductionJobQtyDetails.setJobQtySno(rs_ProdnJbOpn_Get.getInt("WOJBSTAT_SNO"));
              int jbQtySno = objProductionJobQtyDetails.getJobQtySno();
              
              ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
              
              
              vec_Obj = con.getProductVector(SQLMaster.GET_PRODDETAILS_FOR_REWORK_SQL_QUERY,ht_ProdnJbOpn_Get);
              query = con.getProductQuery(SQLMaster.GET_PRODDETAILS_FOR_REWORK_SQL_QUERY);
              PreparedStatement ps_Prod_Det = con.executeRawQuery(query,vec_Obj);
              ResultSet rs_Prod_Det = ps_Prod_Det.executeQuery();
              String totOpns = "";
              while(rs_Prod_Det.next())
              {
              String qtySnos = "";
              qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
              StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
              while(st.hasMoreTokens())
              {
              int sno = Integer.parseInt(st.nextToken().trim());
              if(sno == jbQtySno)
              {
              int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
              int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
              for(int j = stOpn ;j<=endOpn ; j++)
              {
              if(totOpns.equals(""))
              {
              totOpns = j+"";
              }
              else
              {
              totOpns = totOpns +","+j;
              }
              }
              }
              
              }
              }
              //to take all Operations from RADL table for this particular Quantity...
               
               vec_Obj = con.getProductVector(SQLMaster.GET_RADL_PRODDETAILS_FOR_REWORK_SQL_QUERY,ht_ProdnJbOpn_Get);
               query = con.getProductQuery(SQLMaster.GET_RADL_PRODDETAILS_FOR_REWORK_SQL_QUERY);
               PreparedStatement ps_RadlProd_Det = con.executeRawQuery(query,vec_Obj);
               ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
               while(rs_RadlProd_Det.next())
               {
               String qtySnos = "";
               qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
               StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
               while(st.hasMoreTokens())
               {
               int sno = Integer.parseInt(st.nextToken().trim());
               if(sno == jbQtySno)
               {
               int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
               int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
               for(int j = stOpn ;j<=endOpn ; j++)
               {
               if(totOpns.equals(""))
               {
               totOpns = j+"";
               }
               else
               {
               totOpns = totOpns +","+j;
               }
               
               }
               
               }
               }
               
               }
               
               
               System.out.println("totOpns"+totOpns);
               totOpns = ss.sortString(totOpns);
               Vector vec_Obj_PendStat_Opn = con.getProductVector(SQLMaster.GET_PENDING_PRODN_JOBSTAT_OPERATION_SNOS_FOR_REWORK_SQL_QUERY,ht_JbStatSno_Get);
               String query_PendStat_Opn = con.getProductQuery(SQLMaster.GET_PENDING_PRODN_JOBSTAT_OPERATION_SNOS_FOR_REWORK_SQL_QUERY);
               PreparedStatement ps_PendStat_Opn = con.executeRawQuery(query_PendStat_Opn,vec_Obj_PendStat_Opn);
               
               ResultSet rs_PendStat_OpnSnos = ps_PendStat_Opn.executeQuery();
               String pndOpn_Snos = "";
               String UnpostedOpn_Snos = "";
               
               
               while(rs_PendStat_OpnSnos.next())
               {
               
               int woJbOpnSno = rs_PendStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
               int fl = 0;
               StringTokenizer st = new StringTokenizer(totOpns,",");
               while(st.hasMoreTokens())
               {
               
               if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
               {
               fl = 1;
               if(UnpostedOpn_Snos.equals(""))
               {
               UnpostedOpn_Snos = woJbOpnSno+"";
               
               }
               else
               {
               UnpostedOpn_Snos = UnpostedOpn_Snos + ","+woJbOpnSno;
               }
               }
               }
               if(fl==0)
               {
               if(pndOpn_Snos.equals(""))
               {
               pndOpn_Snos = woJbOpnSno+"";
               
               }
               else
               {
               pndOpn_Snos = pndOpn_Snos + ","+woJbOpnSno;
               }
               }
               
               }
               objProductionJobQtyDetails.setPendingOpnSnos(ss.sortString(pndOpn_Snos));
               objProductionJobQtyDetails.setUnPostedOpnSnos(ss.sortString(UnpostedOpn_Snos));
               
               ht_JbPstdStatSno_Get.put("WOJBSTAT_ID",new Integer(objProductionJobQtyDetails.getJobStatId()));
               
               
               Vector vec_Obj_PostedStat_Opn = con.getProductVector(SQLMaster.GET_POSTED_PRODN_JOBSTAT_OPERATION_SNOS_FOR_REWORK_SQL_QUERY,ht_JbPstdStatSno_Get);
               String query_PostedStat_Opn = con.getProductQuery(SQLMaster.GET_POSTED_PRODN_JOBSTAT_OPERATION_SNOS_FOR_REWORK_SQL_QUERY);
               PreparedStatement ps_PostedStat_Opn = con.executeRawQuery(query_PostedStat_Opn,vec_Obj_PostedStat_Opn);
               
               ResultSet rs_PostedStat_OpnSnos = ps_PostedStat_Opn.executeQuery();
               
               String postedOpn_Snos = "";
               int i1 =0;
               
               while(rs_PostedStat_OpnSnos.next())
               {
               if(i1==0)
               {
               postedOpn_Snos = rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO")+"";
               i1++;
               }
               else
               {
               postedOpn_Snos = postedOpn_Snos + ","+rs_PostedStat_OpnSnos.getInt("WOJBOPN_OPN_SNO");
               }
               }
               
               objProductionJobQtyDetails.setPostedOpnSnos(ss.sortString(postedOpn_Snos));
               
               
               vec_Result.addElement(objProductionJobQtyDetails);
               //							unposted production operations over//
                //objProductionJobQtyDetails.setShiftId(rs_ProdnJbOpn_Get.getInt());
                 //objProductionJobQtyDetails.setProdnDate(rs_ProdnJbOpn_Get.getTimestamp());
                  }
                  }
                  catch(SQLException sqle)
                  {
                  if(BuildConfig.DMODE)
                  {
                  sqle.printStackTrace();
                  }
                  throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
                  }
                  }
                  catch(SQLException ex)
                  {
                  if(BuildConfig.DMODE)
                  {
                  ex.printStackTrace();
                  }
                  throw new ProductionException("PMEC000","GENERAL SQL ERROR",ex.toString());
                  }
                  if(BuildConfig.DMODE)
                  {
                  logger.info("GET PRODUCTION JOB OPERATION DETAILS ENDS");
                  }
                  return vec_Result;
                  }
                  */
    
    public ProductionDetails getProductionDetails(int prodId) throws ProductionException,SQLException
    {
        ProductionDetails objProductionDetails = new ProductionDetails();
        ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
        Hashtable ht_ProdDet_Get = new Hashtable();
        Vector vec_Emp_Det = new Vector();
        DBConnection con = null;
        
        logger.info("GET PRODUCTION DETAILS STARTS");
        
        con = DBConnectionFactory.getConnection();
        
        ht_ProdDet_Get.put("PROD_ID", new Integer(prodId));
        try
        {
            PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_PROD_DETAILS_SQL_QUERY,ht_ProdDet_Get);
            ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
            
            if(rs_ProdDet_Get.next())
            {
                //set Production Details //
                objProductionDetails.setProdId(prodId);
                objProductionDetails.setMcCode(rs_ProdDet_Get.getString("MC_CDE"));
                objProductionDetails.setProdCrntDate(rs_ProdDet_Get.getTimestamp("PROD_CRNT_DATE"));
                objProductionDetails.setShiftId(rs_ProdDet_Get.getInt("SHIFT_ID"));
                objProductionDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                objProductionDetails.setProdWorkType(rs_ProdDet_Get.getString("PROD_WORK_TYP"));
                objProductionDetails.setProdIncntvFlag(rs_ProdDet_Get.getString("PROD_INCNTV_FLG").equals("1"));
                objProductionDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                objProductionDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                objProductionDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                objProductionDetails.setProdQtySnos(rs_ProdDet_Get.getString("PROD_QTY_SNOS"));
                objProductionDetails.setProdTotQty(rs_ProdDet_Get.getInt("PROD_TOT_QTY"));
                objProductionDetails.setProdStartOpn(rs_ProdDet_Get.getInt("PROD_START_OPN"));
                objProductionDetails.setProdEndOpn(rs_ProdDet_Get.getInt("PROD_END_OPN"));
                objProductionDetails.setProdStdHrs(rs_ProdDet_Get.getFloat("PROD_STD_HRS"));
                objProductionDetails.setProdTotHrs(rs_ProdDet_Get.getFloat("PROD_TOT_HRS"));
                objProductionDetails.setProdUpdatePyrl(rs_ProdDet_Get.getString("PROD_UPDATE_PYRL").equals("1"));
                objProductionDetails.setProdUpdateWo(rs_ProdDet_Get.getString("PROD_UPDATE_WO").equals("1"));
                objProductionDetails.setProdPostFlg(rs_ProdDet_Get.getString("PROD_POST_FLG").equals("1"));
                objProductionDetails.setDeleted(rs_ProdDet_Get.getString("IS_DLTD").equals("1"));
                objProductionDetails.setModifyCount(rs_ProdDet_Get.getInt("PROD_MODIFYCOUNT"));
                if(objProductionDetails.getModifyCount()== 0)
                objProductionDetails.setCreatedBy(rs_ProdDet_Get.getString("PROD_CREATEDBY"));
                else
                objProductionDetails.setModifiedBy(rs_ProdDet_Get.getString("PROD_CREATEDBY"));	
                objProductionDetails.setProdDateStamp(rs_ProdDet_Get.getTimestamp("PROD_DATESTAMP"));
                objProductionDetails.setProdIsValid(rs_ProdDet_Get.getInt("PROD_ISVALID"));
                
                ///set Job Details - starts///
                //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));
                
                objProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                //set Job Details - Ends///
                
                //get All Employee Details of the Above Production Record
                PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_PROD_EMP_DETAILS_SQL_QUERY,ht_ProdDet_Get);
                ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                
                while(rs_ProdEmp_Det.next())
                {
                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                    
                    objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                    objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
                    objEmployeeDtyOtDetails.setEmpCode(rs_ProdEmp_Det.getString("EMP_CDE"));
                    objEmployeeDtyOtDetails.setEmpTypdId(rs_ProdEmp_Det.getInt("EMP_TYP_ID"));
                    objEmployeeDtyOtDetails.setEmpType(rs_ProdEmp_Det.getString("EMP_TYP_NAME"));
                    objEmployeeDtyOtDetails.setDutyHrs(rs_ProdEmp_Det.getFloat("DT_HRS"));
                    objEmployeeDtyOtDetails.setOtHrs(rs_ProdEmp_Det.getFloat("OT_HRS"));
                    objEmployeeDtyOtDetails.setDutySlryHrs(rs_ProdEmp_Det.getFloat("DT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setOtSlryHrs(rs_ProdEmp_Det.getFloat("OT_SLRY_HRS"));
                    objEmployeeDtyOtDetails.setIncntvSlryHrs(rs_ProdEmp_Det.getFloat("INCNTV_SLRY_HRS"));
                    
                    //Add Each Employee Details into Vector
                    vec_Emp_Det.addElement(objEmployeeDtyOtDetails);
                    
                    
                }
                //set Employee Details Vector into Prod Object
                objProductionDetails.setProdnEmpHrsDetails(vec_Emp_Det);
            }
            else
            {
                throw new ProductionException("PMEC000","RECORD NOT FOUND","");
            }
            rs_ProdDet_Get.close();
            ps_ProdDet_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        logger.info("GET PRODUCTION DETAILS ENDS");
        
        return objProductionDetails;
    }
    
    public HashMap getAllProductionDetails(Filter filters[],String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException,SQLException
    {
        HashMap hm_Result = new HashMap();
        Vector vec_Prod_Det = new Vector();
        DBConnection con=null;
        
        logger.info("GET ALL PRODUCTION DETAILS FILTER STARTS");
        
        if((filters == null)||( sortBy == null))
        {
            logger.error("FILTER VALUES ARE NULL");
            
            throw new ProductionException("PMEC006","FILTER VALUES ARE NULL","");
        }
        
        con = DBConnectionFactory.getConnection();
        
        int tot_Rec_Cnt = 0;
        
        
        try
        {
            
            //filters and tableName are passed to the function and receives Total Record Count
            tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters, SQLMaster.GET_ALL_PRODUCTION_DETAILS_FILTER_QUERY);
            
            //Finding end index for the query
            int eIndex = startIndex + displayCount;
            
            
            // filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
            String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_PRODUCTION_DETAILS_FILTER_QUERY);
            if(BuildConfig.DMODE)
            {
                // total records and query
                System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
                System.out.println("QUERY : " + Query);
            }
            
            logger.info("Total Records : " + tot_Rec_Cnt);
            logger.info("Query : " + Query);
            
            
            ResultSet rs_Prodn_Details_Get =  con.executeRownumStatement(Query);
            double totProdHrs = 0;
            while(rs_Prodn_Details_Get.next())
            {
                ProductionDetails objProductionDetails = new ProductionDetails();
                objProductionDetails.setProdId(rs_Prodn_Details_Get.getInt("PROD_ID"));
                objProductionDetails.setMcCode(rs_Prodn_Details_Get.getString("MC_CDE"));
                objProductionDetails.setProdCrntDate(rs_Prodn_Details_Get.getTimestamp("PROD_CRNT_DATE"));
                objProductionDetails.setShiftId(rs_Prodn_Details_Get.getInt("SHIFT_ID"));
                objProductionDetails.setShiftName(rs_Prodn_Details_Get.getString("SHIFT_NAME"));
                objProductionDetails.setProdTotHrs(rs_Prodn_Details_Get.getFloat("PROD_TOT_HRS"));
                objProductionDetails.setProdWorkType(rs_Prodn_Details_Get.getString("PROD_WORK_TYP"));
                //objProductionDetails.setProdIncntvFlag(rs_Prodn_Details_Get.getString("PROD_INCNTV_FLG").equals("1")?true:false);
                objProductionDetails.setWoNo(rs_Prodn_Details_Get.getString("WO_NO"));
                //objProductionDetails.setProdQtySnos(rs_Prodn_Details_Get.getString("PROD_QTY_SNOS"));
                //objProductionDetails.setProdTotQty(rs_Prodn_Details_Get.getInt("PROD_TOT_QTY"));
                //objProductionDetails.setProdStartOpn(rs_Prodn_Details_Get.getInt("PROD_START_OPN"));
                //objProductionDetails.setProdEndOpn(rs_Prodn_Details_Get.getInt("PROD_END_OPN"));
                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                objProductionJobDetails.setJobName(rs_Prodn_Details_Get.getString("JB_NAME"));
                //objProductionJobDetails.setDwgNo(rs_Prodn_Details_Get.getString("JB_DWG_NO"));
                //objProductionJobDetails.setRvsnNo(rs_Prodn_Details_Get.getString("JB_RVSN_NO"));
                //objProductionJobDetails.setMatlType(rs_Prodn_Details_Get.getString("JB_MATL_TYP"));
                
                objProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                
                objProductionDetails.setProdDateStamp(rs_Prodn_Details_Get.getTimestamp("PROD_DATESTAMP"));
                objProductionDetails.setProdIsValid(rs_Prodn_Details_Get.getInt("PROD_ISVALID"));
                totProdHrs = totProdHrs + objProductionDetails.getProdTotHrs();
                vec_Prod_Det.addElement(objProductionDetails);
            }
            hm_Result.put("TotalProdHrs",new Double(totProdHrs));
            hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
            hm_Result.put("ProductionDetails",vec_Prod_Det);
            rs_Prodn_Details_Get.getStatement().close();
            rs_Prodn_Details_Get.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.info("GENERAL EXCEPTION");
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        logger.info("GET ALL PRODUCTION DETAILS FILTER ENDS");
        
        return hm_Result;
    }
    
    public LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException
    {
        int empTypeId = 0;
        String empTypeName = "";
        int empId = 0;
        String empCode = "";
        String empName = "";
        DBConnection con = null;
        EmployeeDetailsManager objEmployeeDetailsManager = new EmployeeDetailsManager();
        Hashtable ht_EmpTypId = new Hashtable();
        
        
        logger.info("GET ALL EMPLOYEES BY TYPES STARTED");
        
        LinkedHashMap hm_EmpTypes = new LinkedHashMap();
        LinkedHashMap hm_Return = new LinkedHashMap();
        
        
        
        hm_EmpTypes = objEmployeeDetailsManager.getAllEmployeeTypes();
        try
        {
            con = DBConnectionFactory.getConnection();
            Iterator itr = hm_EmpTypes.keySet().iterator();
            ResultSet rs_Emp_Det = null;
            PreparedStatement ps_Emp_Det = null;
            while(itr.hasNext())
            {
                Vector vecResult = new Vector();
                LinkedHashMap hm_EmpTyp_Det = new LinkedHashMap();
                LinkedHashMap hm_DtOt = new LinkedHashMap();
                empTypeId = ((Integer)itr.next()).intValue();
                empTypeName = hm_EmpTypes.get(new Integer(empTypeId)).toString();
                
                //to set emp type id and type name into first Hashmap..
                hm_EmpTyp_Det.put(new Integer(empTypeId),empTypeName);
                
                ht_EmpTypId.put("EMP_TYP_ID",new Integer(empTypeId));
                
                ps_Emp_Det = con.executeStatement(SQLMaster.SELECT_ALL_EMP_BY_TYPE_SQL_QUERY,ht_EmpTypId);
                rs_Emp_Det = ps_Emp_Det.executeQuery();
                
                LinkedHashMap hm_Emp_Det = new LinkedHashMap();
                while(rs_Emp_Det.next())
                {
                    empId = rs_Emp_Det.getInt("EMP_ID");
                    empCode = rs_Emp_Det.getString("EMP_CDE");
                    empName = rs_Emp_Det.getString("EMP_NAME");
                    hm_Emp_Det.put(new Integer(empId),empName+" ~ "+empCode);
                }
                rs_Emp_Det.close();
                ps_Emp_Det.close();
                
                boolean dtHrs = false;
                boolean otHrs = false;
                boolean incentiveHrs = false;
                PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_EMP_TYP_HRS_DETAILS_SQL_QUERY,ht_EmpTypId);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                    dtHrs = rs.getString("EMP_TYP_DT").equals("1");
                    otHrs = rs.getString("EMP_TYP_OT").equals("1");
                    incentiveHrs = rs.getString("EMP_TYP_INCENTIVE").equals("1");
                    
                }
                hm_DtOt.put("DT",new Boolean(dtHrs));
                hm_DtOt.put("OT",new Boolean(otHrs));
                hm_DtOt.put("INCENTIVE",new Boolean(incentiveHrs));
                vecResult.add(hm_Emp_Det);
                vecResult.add(hm_DtOt);
                
                hm_Return.put(hm_EmpTyp_Det,vecResult);
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new ProductionException("PREC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("GET ALL EMPLOYEES BY TYPES ENDED");
        
        return hm_Return;
    }
    
    public LinkedHashMap getAllEmployeesByTypes(int empTypId) throws EmployeeException, SQLException, ProductionException
    {
        DBConnection con = null;
        boolean dtHrs = false;
        boolean otHrs = false;
        boolean incentiveHrs = false;
        LinkedHashMap hm_DtOt = new LinkedHashMap();
        Hashtable ht_EmpTypId = new Hashtable();
        logger.info("GET ALL EMPLOYEES BY TYPES STARTED");
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_EmpTypId.put("EMP_TYP_ID",new Integer(empTypId));
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_EMP_TYP_HRS_DETAILS_SQL_QUERY,ht_EmpTypId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                dtHrs = rs.getString("EMP_TYP_DT").equals("1");
                otHrs = rs.getString("EMP_TYP_OT").equals("1");
                incentiveHrs = rs.getString("EMP_TYP_INCENTIVE").equals("1");
                
            }
            hm_DtOt.put("DT",new Boolean(dtHrs));
            hm_DtOt.put("OT",new Boolean(otHrs));
            hm_DtOt.put("INCENTIVE",new Boolean(incentiveHrs));
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
                logger.info("GENERAL EXCEPTION");
            }
            throw new ProductionException("PREC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("GET ALL EMPLOYEES BY TYPES ENDED");
        
        return hm_DtOt;
    }
    
    
    public HashMap minReqdEmployees() throws SQLException, ProductionException
    {
        DBConnection con = null;
        HashMap hm_MinReqdEmp = new HashMap();
        int empTypeId = 0;
        int minReqdQty = 0;
        String empTypName = "";
        
        
        logger.info("GET ALL EMPLOYEES MIN REQD QTY STARTED");
        
        try
        {
            con =  DBConnectionFactory.getConnection();
            PreparedStatement ps_MinReqd_Emp = con.executeStatement(SQLMaster.MIN_REQD_EMPLOYEES_SELECT_SQL_QUERY);
            ResultSet rs_MinReqd_Emp = ps_MinReqd_Emp.executeQuery();
            while(rs_MinReqd_Emp.next())
            {
                
                Vector vec = new Vector();
                empTypeId = rs_MinReqd_Emp.getInt("EMP_TYP_ID");
                empTypName = rs_MinReqd_Emp.getString("EMP_TYP_NAME");
                minReqdQty = rs_MinReqd_Emp.getInt("MIN_RQD_QTY");
                vec.addElement(new Integer(minReqdQty));
                vec.addElement(empTypName);
                hm_MinReqdEmp.put(new Integer(empTypeId),vec);
            }
            rs_MinReqd_Emp.close();
            ps_MinReqd_Emp.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            logger.info("GENERAL EXCEPTION");
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
        
        
        logger.info("GET ALL EMPLOYEES MIN REQD QTY ENDS");
        
        return hm_MinReqdEmp;
    }
    public ProductionAccountingDateDetails isPosted(String mcCde,Date prodDate,int shiftId) throws ProductionException, ParseException, SQLException
    {
        //boolean checkRESULT = false;
        DBConnection con = null;
        int pstingRuleId = 0;
        
        ProductionAccountingDateDetails  objProductionAccountingDateDetails = new ProductionAccountingDateDetails();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps_PstingRuleId = con.executeStatement(SQLMaster.PSTING_RULE_ID_SELECT_SQL_QUERY);
            ResultSet rs_PstingRuleId = ps_PstingRuleId.executeQuery();
            if(rs_PstingRuleId.next())
            {
                pstingRuleId = rs_PstingRuleId.getInt("PSTING_RULE_ID");
            }
            
            rs_PstingRuleId.close();
            ps_PstingRuleId.close();
            /*
             GregorianCalendar gc = new GregorianCalendar();
             gc.setTime(prodDate);
             gc.add(GregorianCalendar.DATE,-1);
             // to check previous day...
              Hashtable ht = new Hashtable();
              if(BuildConfig.DMODE)
              System.out.println("Date :"+gc.getTime());
              ht.put("PROD_DATE",gc.getTime());
              PreparedStatement ps = con.executeStatement(SQLMaster.PREVIOUS_DAY_CHECK_SQL_QUERY,ht);
              ResultSet rs = ps.executeQuery();
              int count = 0;
              if(rs.next())
              {
              count = rs.getInt(1);
              }
              if(BuildConfig.DMODE)
              System.out.println("Count :"+count);
              if(count <= 0)
              {
              throw new ProductionException("PMEC015","ENTRIES SHOULD BE MADE FOR PREVIOUS DAY","");
              }*/
            
            switch(pstingRuleId)
            {
            case 1:
                //ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                //Vector vec = new Vector();
                //vec.add(objProductionAccountingMachineDetails);
                //objProductionAccountingDateDetails.setVecProductionAccntngMachineDetails(vec);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                objProductionAccountingDateDetails.setProdDate(sf.format(prodDate));
                break;
                
            case 2:
                objProductionAccountingDateDetails = checkPostingByMachineAndDateAndShift(mcCde,prodDate,shiftId);
                break;
                
            case 3:
                objProductionAccountingDateDetails = checkPostingByOnlyMachine(mcCde,prodDate);
                break;
                
            case 4:
                objProductionAccountingDateDetails = checkPostingByOnlyDate(prodDate);
                break;
                
            case 5:
                objProductionAccountingDateDetails = checkPostingByMachineAndDate(mcCde,prodDate);
                break;
                
            case 6:
                objProductionAccountingDateDetails = checkPostingByDateAndShift(prodDate,shiftId);
                break;
            }
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
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
            catch(SQLException clse)
            {
                
                logger.error("CONNECTION CLOSING EXCEPTION", clse);
                throw new SQLException("CONNECTION CLOSING EXCEPTION" + clse.toString());
            }
        }
        
        return objProductionAccountingDateDetails;
    }
    public HashMap makeProductionValid(Vector prodIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE PRODUCTION VALID STARTS");
        
        if(prodIds == null)
        {
            logger.error("PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","PRODUCTION VECTOR OBJECT IS NULL","");
        }
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<prodIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("PROD_ID",(Integer)prodIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.PROD_MAKE_VALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(prodIds.get(i),new Integer(0));
                    logger.info(prodIds.get(i)+" : PRODUCTION RECORD VALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(prodIds.get(i),new Integer(1));
                    logger.info(prodIds.get(i)+" : PRODUCTION RECORD NOT VALIDATED");
                    
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
            throw new ProductionException("PREC000","GENERAL SQL ERROR",e.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("MAKE PRODUCTION VALID ENDS");
        
        return hm_Result;
        
    }
    public HashMap makeProductionInValid(Vector prodIds) throws ProductionException, SQLException
    {
        int result = 0;
        DBConnection con = null;
        HashMap hm_Result = new HashMap();
        
        logger.info("MAKE PRODUCTION INVALID STARTS");
        
        if(prodIds == null)
        {
            logger.error("PRODUCTION VECTOR OBJECT IS NULL");
            throw new ProductionException("PREC00","PRODUCTION VECTOR OBJECT IS NULL","");
        }
        
        try
        {
            con = DBConnectionFactory.getConnection();
            con.startTransaction();
            for(int i=0;i<prodIds.size();i++)
            {
                Hashtable ht_Prod_Id = new Hashtable();
                
                
                ht_Prod_Id.put("PROD_ID",(Integer)prodIds.get(i));
                
                result = con.executeUpdateStatement(SQLMaster.PROD_MAKE_INVALID_SQL_QUERY,ht_Prod_Id);
                
                if(result>=1)
                {
                    con.commitTransaction();
                    hm_Result.put(prodIds.get(i),new Integer(0));
                    logger.info(prodIds.get(i)+" : PRODUCTION RECORD INVALIDATED");
                    
                }
                else
                {
                    con.rollBackTransaction();
                    hm_Result.put(prodIds.get(i),new Integer(1));
                    logger.info(prodIds.get(i)+" : PRODUCTION RECORD NOT INVALIDATED");
                    
                }
            }
        }
        catch(SQLException ex)
        {
            
            if(BuildConfig.DMODE)
            {
                ex.printStackTrace();
                System.out.println("EXCEPTION WHILE INVALIDATING PRODUCTION RECORD");
            }
            logger.error("EXCEPTION WHILE INVALIDATING PRODUCTION RECORD",ex);
            throw new ProductionException("PREC000","GENERAL SQL ERROR",ex.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        logger.info("MAKE PRODUCTION INVALID ENDS");
        
        return hm_Result;
        
    }
    public boolean isModify(int prodId) throws ProductionException, SQLException
    {
        boolean retVal = false;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        int cnt = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_Prod.put("PROD_ID",new Integer(prodId));
            PreparedStatement ps_Prod = con.executeStatement(SQLMaster.CHECK_PROD_FOR_MODIFY_SQL_QUERY,ht_Prod);
            ResultSet rs_Prod = ps_Prod.executeQuery();
            if(rs_Prod.next())
            {
                cnt = rs_Prod.getInt(1);
                if(cnt==0)
                {
                    throw new ProductionException("","POSTED PRODUCTION CANNOT BE MODIFIED","");
                }
                else
                {
                    retVal = true;
                }
            }
            rs_Prod.close();
            ps_Prod.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        return retVal;
    }
    public boolean isRadlOperation(int woJbStatId,int startOpn,int endOpn,int value) throws ProductionException, SQLException
    {
        boolean retValue = false;
        DBConnection con = null;
        Hashtable ht_OpnDet = new Hashtable();
        String mcTypName = "";
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_OpnDet.put("WOJBSTAT_ID",new Integer(woJbStatId));
            ResultSet rs_OpnDet = null;
            PreparedStatement ps_OpnDet = null;
            for(int i = startOpn ; i<=endOpn ; i++)
            {
                ht_OpnDet.put("WOJBOPN_OPN_SNO",new Integer(i));
                ps_OpnDet = con.executeStatement(SQLMaster.CHECK_OPERATIONS_SQL_QUERY,ht_OpnDet);
                rs_OpnDet = ps_OpnDet.executeQuery();
                while(rs_OpnDet.next())
                {
                    mcTypName = rs_OpnDet.getString("MC_TYP_NAME");
                    if(value==0)
                    {
                        if(!mcTypName.equalsIgnoreCase("radial"))
                        {
                            throw new ProductionException("PEC006","OPERATION NOT A RADIAL TYPE","");
                        }
                    }
                    else
                    {
                        if(mcTypName.equalsIgnoreCase("radial"))
                        {
                            throw new ProductionException("PEC006","RADIAL TYPE OPERATIONS","");
                        }
                        
                    }
                }
                
                rs_OpnDet.close();
                ps_OpnDet.close();
                
                retValue = true;
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        return retValue;
    }
    public LinkedHashMap currentPostingRule() throws ProductionException, SQLException
    {
        DBConnection con = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_POSTING_RULE_DETAILS_SQL_QUERY);
            ResultSet rs = ps.executeQuery();
            LinkedHashMap hm_Result = new LinkedHashMap();
            while(rs.next())
            {
                hm_Result.put(new Integer(rs.getInt("PSTING_RULE_ID")),rs.getString("PSTING_RULE_DESC"));
            }
            PreparedStatement ps_CurrentRule = con.executeStatement(SQLMaster.CURRENT_POSTING_RULE_ID_SELECT_SQL_QUERY);
            ResultSet rs_CurrentRule = ps_CurrentRule.executeQuery();
            if(rs_CurrentRule.next())
            {
                hm_Result.put("Id",new Integer(rs_CurrentRule.getInt(1)));
            }
            return hm_Result;
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
            
        }
    }
    
    public boolean changePostingRule(int ruleId) throws ProductionException, SQLException
    {
        DBConnection con = null;
        try
        {
            int count = 0;
            boolean changeRuleRESULT = false;
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.CHECK_POSTING_SQL_QUERY);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                count = rs.getInt(1);
            }
            if(count<=0)
            {
                Hashtable ht_PstingRuleChange = new Hashtable();
                ht_PstingRuleChange.put("PSTING_RULE_ID",new Integer(ruleId));
                PreparedStatement ps_PstingRuleChange = con.executeStatement(SQLMaster.CHANGE_POSTING_RULE_SQL_QUERY,ht_PstingRuleChange);
                int result = ps_PstingRuleChange.executeUpdate();
                if(result>0)
                {
                    changeRuleRESULT = true;
                }
                
            }
            else
            {
                throw new ProductionException("PEC006","ALL THE ENTRIES SHOULD BE POSTED BEFORE CHANGING OF RULE","");
            }
            return changeRuleRESULT;
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
            {
                sqle.printStackTrace();
            }
            throw new ProductionException("PMEC000","GENERAL SQL ERROR",sqle.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
            
        }
        
    }
    
    public boolean checkIncentive(int woJbId,int startOpn,int endOpn) throws ProductionException, SQLException
    {
        boolean RESULT = false;
        Hashtable ht_CheckIncntv_Get = new Hashtable();
        
        DBConnection con = null;
        con = DBConnectionFactory.getConnection();
        ResultSet rs_Result = null;
        String incentive = "";
        int count = 0;
        ht_CheckIncntv_Get.put("WOJB_ID",new Integer(woJbId));
        ht_CheckIncntv_Get.put("START_OPN",new Integer(startOpn));
        ht_CheckIncntv_Get.put("END_OPN",new Integer(endOpn));
        try
        {
            
            PreparedStatement ps_WoJbStatId_Get = con.executeStatement(SQLMaster.CHECK_INCENTIVE_FOR_PRODUCTION,ht_CheckIncntv_Get);
            rs_Result =  ps_WoJbStatId_Get.executeQuery();
            if(rs_Result.next())
            {
                count = rs_Result.getInt(1);
                incentive = rs_Result.getString("INCENTIVE_FLG");
                
            }
            if(count == 1)
            {
                if(incentive.equals("1"))
                    RESULT = true;
                
            }
            else if(count > 1)
            {
                throw new ProductionException("EC","ALL OPERATIONS SHOULD EITHER GIVEN WITH INCENTIVE OR WITHOUT INCENTIVE","");
            }
            
        }
        catch(SQLException ex)
        {
            throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
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
                throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
            }
        }
        
        return RESULT;
    }
    
    public static void main(String args[]) throws SQLException, ProductionException, ParseException
    {
        //System.out.println(Float.parseFloat("15.59")-Float.parseFloat("8.00"));
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        
        MachineNonAccountedDetails[] objMachineNonAccountedDetailsList = null;
        objMachineNonAccountedDetailsList = objProductionDetailsManager.checkMachineAccounting(new Date("01-oct-05"),new Date("31-oct-05"),"PM-M-02");
        for(int i = 0 ; i<objMachineNonAccountedDetailsList.length;i++)
        {
            MachineNonAccountedDetails objMachineNonAccountedDetails = objMachineNonAccountedDetailsList[i];
            System.out.println("Date:"+objMachineNonAccountedDetails.getProdDate());
            System.out.println("ShiftName:"+objMachineNonAccountedDetails.getShiftName());
            System.out.println("ProdHrs:"+objMachineNonAccountedDetails.getProdHrs());
            System.out.println("NprodHrs:"+objMachineNonAccountedDetails.getNonProdHrs());
            System.out.println("NonAccounted:"+objMachineNonAccountedDetails.getNonAccuntedHrs());
        }
        //System.out.println(objProductionDetailsManager.checkIncentive(1810,18,22));
        //objProductionDetailsManager.checkAvailableEmpHrs(223,new Date("05-mar-05"),206,0,0,0,0,DBConnectionFactory.getConnection());
        //objProductionDetailsManager.checkHrsAccounted("PM-M-01",new Date("10-mar-05"),206,0,0,DBConnectionFactory.getConnection());
        //Vector vec = new Vector();
        //vec = objProductionDetailsManager.checkPostingByDateAndShift(new Date("14-apr-05"),1);
        // System.out.println("hai "+vec.size());
        /*
         
         ProductionDetails objProductionDetails = new ProductionDetails();
         objProductionDetails.setMcCode("mc102");
         //objProductionDetails.setProdCrntDate(new Date("14-NOV-04"));
          objProductionDetails.setWoJbId(216);
          objProductionDetails.setProdQtySnos("1,2");
          objProductionDetails.setProdTotQty(2);
          
          objProductionDetails.setProdStartOpn(1);
          objProductionDetails.setProdEndOpn(3);
          
          
          objProductionDetails.setProdIncntvFlag(true);
          objProductionDetails.setProdTotHrs(3.0f);
          objProductionDetails.setProdStdHrs(2.0f);
          
          objProductionDetails.setProdWorkType("R");
          objProductionDetails.setShiftId(41);
          
          Vector v = new Vector();
          EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
          objEmployeeDtyOtDetails.setEmpId(100);
          objEmployeeDtyOtDetails.setDutyHrs(1.0f);
          objEmployeeDtyOtDetails.setEmpType("Operator");
          v.addElement(objEmployeeDtyOtDetails);
          EmployeeDtyOtDetails objEmployeeDtyOtDetails1 = new EmployeeDtyOtDetails();
          objEmployeeDtyOtDetails1.setEmpId(101);
          objEmployeeDtyOtDetails1.setOtHrs(1.0f);
          objEmployeeDtyOtDetails1.setEmpType("Helper");
          v.addElement(objEmployeeDtyOtDetails1);
          objProductionDetails.setProdnEmpHrsDetails(v);
          //objProductionDetailsManager.check();
           //System.out.println(objProductionDetailsManager.isModify(225));
            //HashMap hm =  new HashMap();
             boolean x = objProductionDetailsManager.isRadlOperation(1816,3,4,1);
             System.out.println("x :"+x);
             //hm = objProductionDetailsManager.minReqdEmployees();
              //System.out.println(hm.size());
               //objProductionDetailsManager.checkPostingByDateAndShift(new Date("09-JAN-05"),2);
                //objProductionDetailsManager.addNewProductionDetails(objProductionDetails);
                 //Vector v0 = objProductionDetailsManager.getProdnJobOperationDetails(233);
                  for(int p = 0;p<v0.size();p++)
                  {
                  ProductionJobQtyDetails objProductionJobDetails = new ProductionJobQtyDetails();
                  objProductionJobDetails = (ProductionJobQtyDetails)v0.elementAt(p);
                  System.out.println("comltd"+objProductionJobDetails.getPostedOpnSnos());
                  System.out.println("pend"+objProductionJobDetails.getPendingOpnSnos());
                  System.out.println("unposted"+objProductionJobDetails.getUnPostedOpnSnos());
                  }
                  
                  
                  Vector v1 = new Vector();
                  //	System.out.println(objProductionDetailsManager.viewUnPostedProductionDetailsrs(217,1,3));
                   //HashMap h = objProductionDetailsManager.getAllIdlBrkDwnRsns();
                    //System.out.println(h.size());
                     //ProductionJobDetails  objProductionJobDetails = new ProductionJobDetails();
                      v1 = objProductionDetailsManager.getProdnJobOperationDetails(390);
                      
                      for(int i = 0;i<v1.size();i++)
                      {
                      ProductionJobQtyDetails  objProductionJobDetails = new ProductionJobQtyDetails();
                      objProductionJobDetails = (ProductionJobQtyDetails)v1.elementAt(i);
                      System.out.println("unposted :"+objProductionJobDetails.getUnPostedOpnSnos());
                      System.out.println("pending :"+objProductionJobDetails.getPendingOpnSnos());
                      System.out.println("posted :"+objProductionJobDetails.getPostedOpnSnos());
                      System.out.println("lastProDate :"+objProductionJobDetails.getLastProdDate());
                      }
                      //v1.addElement(new Integer(169));
                       //v1.addElement(new Integer(119));
                        //objProductionDetailsManager.postProductionDetails(v1);
                         //objProductionDetailsManager.addNewProductionDetails(objProductionDetails);
                          
                          /////////////////////////////////getStandardHrs()///////////////////////
                           //float result = objProductionDetailsManager.getStandardHrs(184);
                            //System.out.println("TOTAL STDHRS :"+result);
                             
                             ////////////////////////////////getProductionDetails()////////////////
                              
                              objProductionDetails = objProductionDetailsManager.getProductionDetails(132);
                              System.out.println(objProductionDetails.getMcCode());
                              System.out.println(objProductionDetails.getProdDateStamp());
                              System.out.println(objProductionDetails.getProdIsValid());
                              ProductionJobDetails objProductionJobDetails = objProductionDetails.getObjProductionJobDetails();
                              objProductionJobDetails.getMatlType();
                              Vector vec = objProductionDetails.getProdnEmpHrsDetails();
                              for(int i=0;i<vec.size();i++)
                              {
                              EmployeeDtyOtDetails objEmpDet = (EmployeeDtyOtDetails)vec.get(i);
                              System.out.println(objEmpDet.getEmpName());
                              System.out.println(objEmpDet.getEmpType());
                              System.out.println(objEmpDet.getOtHrs());
                              System.out.println(objEmpDet.getOtSlryHrs());
                              System.out.println(objEmpDet.getIncntvSlryHrs());
                              System.out.println(objEmpDet.getDutyHrs());
                              System.out.println(objEmpDet.getDutySlryHrs());
                              
                              }
                              
                              ////////////////////////////getAllProductionDetails()  [filter]///////////////
                               
                               Filter[] filters = new Filter[2];
                               filters[0] = new Filter();
                               filters[0].setFieldName("JB_NAME");
                               filters[0].setFieldValue("j");
                               filters[0].setSpecialFunction("AnyWhere");
                               
                               
                               filters[1] = new Filter();
                               filters[1].setFieldName("PROD_CRNT_DATE");
                               filters[1].setFieldValue("01-NOV-2004$31-DEC-2004");
                               filters[1].setSpecialFunction("DateBetween");
                               
                               boolean ascending = true;
                               int startIndex = 0;
                               String sortBy = "MC_CDE";
                               int displayCount = 10;
                               HashMap hm = objProductionDetailsManager.getAllProductionDetails(filters,sortBy,ascending,startIndex,displayCount);
                               System.out.println("Result :"+hm);
                               
                               
                               ////////////////////////////////getProductionJobDetails()/////////////////
                                //objProductionDetailsManager.getProdnJobDetailsByWorkOrder(178);
                                 //objProductionDetailsManager.getProdnJobOperationDetails(215);
                                  //System.out.println("OVER");
                                   
                                   */}
}

/***

$Log: ProductionDetailsManager.java,v $
Revision 1.220  2005/12/28 07:45:06  kduraisamy
in emp Hrs invalid  emp code included.

Revision 1.219  2005/12/28 07:29:02  kduraisamy
in emp Hrs invalid  emp code included.

Revision 1.218  2005/12/28 06:28:47  kduraisamy
in emp Hrs invalid  emp code included.

Revision 1.217  2005/11/10 07:08:57  kduraisamy
ProductionAccounting Added.

Revision 1.216  2005/11/10 07:03:45  kduraisamy
ProductionAccounting Added.

Revision 1.215  2005/11/08 14:42:18  vkrishnamoorthy
FieldName changed in Value object.

Revision 1.214  2005/11/07 13:40:56  kduraisamy
ProductionAccounting Added.

Revision 1.213  2005/10/06 10:31:12  vkrishnamoorthy
Modified By in View, error corrected.

Revision 1.212  2005/10/04 15:15:48  kduraisamy
prodTotHrs selection added for updation of nprod.

Revision 1.211  2005/10/04 06:25:43  kduraisamy
prodTotHrs added in Prod Filter.

Revision 1.210  2005/10/04 05:08:54  kduraisamy
prodTotHrs added in Prod Filter.

Revision 1.209  2005/09/30 16:38:35  kduraisamy
startOpn and EndOpn missing error corrected in Prod Update.

Revision 1.208  2005/09/30 11:02:30  vkrishnamoorthy
Update payroll set to 0 in update.

Revision 1.207  2005/09/26 14:46:03  kduraisamy
duty 8 hrs validation added.

Revision 1.206  2005/09/26 11:09:03  kduraisamy
duty 8 hrs validation added.

Revision 1.205  2005/09/26 07:29:54  kduraisamy
duty 8 hrs validation added.

Revision 1.204  2005/09/26 07:21:56  kduraisamy
duty 8 hrs validation added.

Revision 1.203  2005/09/21 14:02:41  kduraisamy
PROD_QTY_SNOS ERROR CORRECTED.

Revision 1.202  2005/09/21 13:59:13  kduraisamy
PROD_QTY_SNOS ERROR CORRECTED.

Revision 1.201  2005/09/21 11:41:31  kduraisamy
PROD_QTY_SNOS ERROR CORRECTED.

Revision 1.200  2005/09/21 11:33:29  kduraisamy
PROD_QTY_SNOS ERROR CORRECTED.

Revision 1.199  2005/09/21 10:22:35  kduraisamy
Wo List sub menu added.

Revision 1.198  2005/09/19 11:50:40  kduraisamy
Duplicate prod entry validation included.

Revision 1.197  2005/09/19 09:07:32  kduraisamy
Duplicate prod entry validation included.

Revision 1.195  2005/09/19 06:18:42  kduraisamy
Duplicate prod entry validation included.

Revision 1.194  2005/09/19 05:13:48  kduraisamy
Duplicate prod entry validation included.

Revision 1.193  2005/09/15 10:24:25  kduraisamy
code optimized.

Revision 1.192  2005/09/15 07:36:09  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.191  2005/09/12 14:46:33  kduraisamy
hashMap is changed to Linked Hashmap.

Revision 1.190  2005/09/10 13:18:42  kduraisamy
order by clause added.

Revision 1.189  2005/09/07 14:43:05  kduraisamy
result Set properly closed.

Revision 1.188  2005/09/03 10:34:51  kduraisamy
connection closing properly added.

Revision 1.187  2005/08/23 09:24:49  kduraisamy
CHECK EMP TYPE SQL QUERY ADDED.

Revision 1.186  2005/08/23 07:31:54  kduraisamy
CHECK EMP TYPE SQL QUERY ADDED.

Revision 1.185  2005/08/19 10:37:10  kduraisamy
" - " is changed to " ~ ".

Revision 1.184  2005/08/19 04:48:03  kduraisamy
EMP_NAME AND EMP_CDE TAKEN FOR HASHMAP.

Revision 1.183  2005/08/05 06:44:18  kduraisamy
view operations taken from wo_jb_opn.

Revision 1.182  2005/08/03 06:49:06  kduraisamy
EMP_CDE IS SET TO COMBO VALUES INSTEAD OF EMP_NAME.

Revision 1.181  2005/08/01 04:38:05  kduraisamy
wo order modification changed.

Revision 1.180  2005/07/23 08:39:51  kduraisamy
getProdJobDetailsForUpdate() modified.

Revision 1.179  2005/07/20 09:22:21  kduraisamy
getProdnJobOperationDetails() name changed as getFinalProdn*().

Revision 1.178  2005/07/15 19:43:06  kduraisamy
indentation.

Revision 1.177  2005/07/15 17:00:17  kduraisamy
getProdnJobDetailsByWorkOrder() and getProdnJobDetailsForUpdateByWorkOrder() changed because of FINAL PRODUCTION.

Revision 1.176  2005/07/12 11:37:26  kduraisamy
imports organized

Revision 1.175  2005/07/12 11:04:39  kduraisamy
finally included in checkIncentive().

Revision 1.174  2005/07/06 10:30:42  vkrishnamoorthy
prod incentive flg is varchar.

Revision 1.173  2005/07/06 06:39:59  kduraisamy
checkincentive() added.

Revision 1.172  2005/07/06 06:27:39  kduraisamy
CHKINCENTIVE() ADDED.

Revision 1.171  2005/07/06 05:29:21  smurugesan
checkIncentive method is  added for production()

Revision 1.170  2005/07/04 09:50:51  kduraisamy
payroll calculate Sal Hrs() method changed for radl,nprod,pop.

Revision 1.169  2005/06/30 07:18:31  kduraisamy
viewOpeations() added.

Revision 1.168  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.167  2005/06/29 09:56:01  kduraisamy
dtslryHrs,otSlryHrs,incntvSlryHrs set into Hashtable.

Revision 1.166  2005/06/28 09:15:52  vkrishnamoorthy
Error corrected.

Revision 1.165  2005/06/25 07:18:18  kduraisamy
calculateSalHrs() added.

Revision 1.164  2005/06/22 05:24:06  kduraisamy
method getAllEmployeesByType(empTypId).

Revision 1.163  2005/06/21 12:05:04  vkrishnamoorthy
EmpType Dt Ot hrs added.

Revision 1.162  2005/06/21 06:57:39  kduraisamy
SELECT EMP_HRS_FOR_EMP_TYP QUERY ADDED.

Revision 1.161  2005/06/16 05:39:17  kduraisamy
MC_CDE REMOVED IN POP.SO POSTING RULE ,ACCOUNTING CHECKING MODIFIED.

Revision 1.160  2005/06/02 10:10:24  kduraisamy
PROD_MODIFYCOUNT ADDED.

Revision 1.159  2005/06/02 09:59:48  kduraisamy
created by added for getProdDetails().

Revision 1.158  2005/06/02 04:24:59  kduraisamy
isposted case 1 modified.

Revision 1.157  2005/06/02 04:21:31  kduraisamy
isposted case 1 modified.

Revision 1.156  2005/06/02 03:43:19  kduraisamy
mc Name added for display.

Revision 1.155  2005/06/01 14:37:15  kduraisamy
log adding changed.

Revision 1.154  2005/06/01 11:25:25  kduraisamy
FOREING CONSTRAINT ADDED FOR USER NAME IN PROD TABLES.

Revision 1.153  2005/05/31 09:28:48  kduraisamy
prod accounting object hierarchy changed.

Revision 1.152  2005/05/31 06:40:06  kduraisamy
prod accounting object hierarchy changed.

Revision 1.151  2005/05/30 10:28:30  kduraisamy
PROD_DUMMYCOUNT ADDED.

Revision 1.150  2005/05/30 07:05:46  kduraisamy
result set closed properly.

Revision 1.149  2005/05/30 06:31:57  kduraisamy
loging of  prod entries added.

Revision 1.148  2005/05/26 05:35:46  kduraisamy
unused variables removed.

Revision 1.147  2005/05/26 05:09:59  smurugesan
customavbltycheck added in posting by onlymachine().

Revision 1.146  2005/05/25 10:23:07  kduraisamy
resultset and preparedStatement properly closed.

Revision 1.145  2005/05/20 11:00:07  kduraisamy
to find valid previous day is changed.

Revision 1.144  2005/05/20 10:41:58  kduraisamy
to find valid previous day is changed.

Revision 1.143  2005/05/20 07:17:03  kduraisamy
posting rule corrected.

Revision 1.142  2005/05/20 06:29:04  kduraisamy
posting rule corrected.

Revision 1.141  2005/05/20 05:53:34  kduraisamy
posting rule corrected.

Revision 1.140  2005/05/20 05:13:05  kduraisamy
custom_nonavblty_check sql query changed.

Revision 1.139  2005/05/20 04:53:58  vkrishnamoorthy
field date is changed to text.

Revision 1.138  2005/05/19 14:29:49  kduraisamy
simpleDateFormat added.

Revision 1.137  2005/05/19 12:43:56  kduraisamy
DATE AND SHIFT POSTING RULE ERROR CORRECTED.

Revision 1.136  2005/05/19 12:34:10  kduraisamy
DATE AND SHIFT POSTING RULE ERROR CORRECTED.

Revision 1.135  2005/05/19 10:45:56  kduraisamy
buildConfig.DMODE ADDED.

Revision 1.134  2005/05/19 09:38:01  kduraisamy
posting rule based on previous day corrected.

Revision 1.133  2005/05/18 13:00:38  kduraisamy
radial flg added and error throws specified.

Revision 1.132  2005/05/18 12:14:25  kduraisamy
posting rule error corrected.

Revision 1.131  2005/05/18 08:29:32  kduraisamy
commitTransaction() and rollbackTransaction() added.

Revision 1.130  2005/05/17 09:31:53  kduraisamy
unwanted   checking removed.

Revision 1.129  2005/05/17 08:53:00  vkrishnamoorthy
day basis posting rule error corrected.

Revision 1.128  2005/05/16 18:35:05  kduraisamy
specific throws addded for mysql.

Revision 1.127  2005/05/16 15:42:43  kduraisamy
specific throws addded for mysql.

Revision 1.126  2005/05/15 20:00:18  kduraisamy
posting rule error corrected..

Revision 1.125  2005/05/14 06:35:50  smurugesan
appropriate hashtable is sent to con.executeStatement().

Revision 1.124  2005/05/13 07:42:52  kduraisamy
PREVIOUS DAY ENTRY SHOULD BE MADE INCLUDE.

Revision 1.123  2005/05/13 07:03:57  kduraisamy
PREVIOUS DAY ENTRY SHOULD BE MADE INCLUDE.

Revision 1.122  2005/05/13 05:36:35  kduraisamy
PREVIOUS DAY ENTRY SHOULD BE MADE INCLUDE.

Revision 1.121  2005/05/13 04:35:06  vkrishnamoorthy
IF(BuildConfig.DMODE) added.

Revision 1.120  2005/05/12 13:29:16  kduraisamy
previous day checking added.

Revision 1.119  2005/05/12 07:54:08  kduraisamy
two methods added for posting rule.

Revision 1.118  2005/04/28 14:23:48  kduraisamy
if(con!=null) added.

Revision 1.117  2005/04/26 10:09:15  kduraisamy
availability current added.

Revision 1.116  2005/04/19 13:13:33  kduraisamy
resultset properly closed.

Revision 1.115  2005/04/18 12:28:06  kduraisamy
executeStatement() return type changed.

Revision 1.114  2005/04/07 09:20:19  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.113  2005/04/07 07:36:10  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.112  2005/03/30 08:23:40  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.111  2005/03/26 11:35:09  smurugesan
ERROR CODES ARE ALTERED.

Revision 1.110  2005/03/26 10:09:00  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.109  2005/03/21 15:28:11  smurugesan
message print added.

Revision 1.108  2005/03/21 13:52:30  smurugesan
avail hrs calculation in checkHrsAccounted() changed.

Revision 1.107  2005/03/15 15:51:47  kduraisamy
CUSTOM_AVBLTY ADDED IN CHECK EMP HRS AND MACHINE ACCOUNTED.

Revision 1.106  2005/03/11 06:20:16  kduraisamy
RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

Revision 1.105  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.104  2005/03/07 06:31:32  kduraisamy
vec_EmpFail,vec_MachineFail added into return type of addNewProductionDetails().

Revision 1.103  2005/03/07 05:43:22  kduraisamy
close connection removed inside the checkHrsAccounted().

Revision 1.102  2005/03/05 09:52:22  kduraisamy
signature changed for addProductionDetails().

Revision 1.101  2005/03/04 12:33:57  kduraisamy
DBConnection Changes made.

Revision 1.100  2005/03/02 11:41:02  kduraisamy
addNewProductionDetails() modified.

Revision 1.99  2005/03/01 03:40:45  kduraisamy
custom non avblty check added.

Revision 1.98  2005/02/16 11:07:43  kduraisamy
trim() added.

Revision 1.97  2005/02/15 09:15:47  smurugesan
connection object added as a checkempHrs()'s parameter.

Revision 1.96  2005/02/15 06:26:59  kduraisamy
PROD UPDATE MODIFIED.

Revision 1.95  2005/02/14 11:10:04  kduraisamy
unwanted cast removed.

Revision 1.94  2005/02/14 05:30:04  kduraisamy
rework operations open and prod entered added.

Revision 1.93  2005/02/12 09:26:47  kduraisamy
pending qty snos and pending opn snos empty condition checked.

Revision 1.92  2005/02/11 18:43:57  kduraisamy
code formated.

Revision 1.91  2005/02/11 06:57:44  kduraisamy
unused variables removed.

Revision 1.90  2005/02/09 06:31:59  sponnusamy
Trim added for extra whitespace.

Revision 1.89  2005/02/09 06:04:02  kduraisamy
unwanted method commented.

Revision 1.88  2005/02/08 14:39:29  kduraisamy
in getProdnOperationDetails()  lastProdDate included.

Revision 1.87  2005/02/08 10:01:24  sponnusamy
Depricated methods removed in main.

Revision 1.86  2005/02/08 06:00:34  kduraisamy
DBConnection con object passed to checkHrsAccounted().

Revision 1.85  2005/02/07 09:18:52  kduraisamy
GETJOB OPERATIO FOR UPDATE () MODIFIED.

Revision 1.84  2005/02/04 19:07:19  kduraisamy
rework entered operations get code included.

Revision 1.83  2005/02/04 10:22:46  kduraisamy
error corrected.

Revision 1.81  2005/02/04 06:36:29  kduraisamy
rwk Operations get added  in getOperation().

Revision 1.80  2005/02/03 09:27:53  sponnusamy
isRadlOpns() added.

Revision 1.79  2005/02/03 06:17:39  sponnusamy
.trim() included.

Revision 1.78  2005/02/03 06:14:07  sponnusamy
.trim() included.

Revision 1.77  2005/02/02 06:53:46  kduraisamy
PARAMETERS CHANGED.

Revision 1.76  2005/02/02 06:35:33  kduraisamy
update parameters changed.

Revision 1.75  2005/02/01 17:16:42  kduraisamy
posting method return type changed to HashMap.

Revision 1.74  2005/01/31 18:58:02  kduraisamy
LAST PRODUCTION DATE ADDED IN GETPRODNJOBDETAILS().

Revision 1.73  2005/01/31 07:25:36  kduraisamy
resultSet name changed.

Revision 1.72  2005/01/31 07:13:37  sponnusamy
GetProduction Details result set modified.

Revision 1.71  2005/01/30 13:24:20  kduraisamy
getJobDetails() and getOpnDetails() modified.Radl include.

Revision 1.70  2005/01/28 11:13:15  kduraisamy
getStandardHrs() calling included in updateProductionDetails().

Revision 1.69  2005/01/27 18:11:59  kduraisamy
algorithm changed.

Revision 1.68  2005/01/27 14:46:53  kduraisamy
if(BuildConfig.DMODE)
{
}
properly inclued inside the catch block.

Revision 1.67  2005/01/27 07:57:03  kduraisamy
POSTING METHOD MODIFIED FOR REWORK.

Revision 1.66  2005/01/25 07:46:37  kduraisamy
isRadlOperations() added.

Revision 1.64  2005/01/18 06:40:08  kduraisamy
unwanted println removed.

Revision 1.61  2005/01/17 14:52:29  kduraisamy
EXCEPTIO THROW ADDED IN ISMODIFY() .

Revision 1.60  2005/01/17 10:32:05  kduraisamy
Updation of workorder header added in addProductionDetails().

Revision 1.59  2005/01/17 09:15:37  kduraisamy
isModify() added.

Revision 1.58  2005/01/17 06:11:09  kduraisamy
make valid make invalid for production added.

Revision 1.57  2005/01/13 12:07:44  kduraisamy
individual methods added for update.

Revision 1.56  2005/01/13 10:09:28  kduraisamy
getJobOpnDetails() modified.

Revision 1.55  2005/01/13 09:47:23  kduraisamy
woId included in getProdDetails()

Revision 1.54  2005/01/13 06:56:42  kduraisamy
getProdnJobDetailsByWorkOrder() and getProdnJobOperationDetails() modified.

Revision 1.53  2005/01/12 06:57:39  kduraisamy
SHIFT NAME INCLUDED IN VIEW UNPOSTED DETAILS METHOD.

Revision 1.52  2005/01/08 10:05:49  kduraisamy
no change.commented error part.

Revision 1.51  2005/01/08 09:36:15  kduraisamy
getDay and getDay are removed.GregorianCalendar used.

Revision 1.50  2005/01/08 07:42:22  kduraisamy
getDay and getDay are removed.GregorianCalendar used.

Revision 1.49  2005/01/07 11:33:07  kduraisamy
resultSet and PreparedStatement close() included.

Revision 1.48  2005/01/07 11:31:47  kduraisamy
resultSet and PreparedStatement close() included.

Revision 1.47  2005/01/07 05:27:41  kduraisamy
isPosted() added.

Revision 1.46  2005/01/06 09:14:42  kduraisamy
checkPosting methods added.

Revision 1.45  2005/01/06 04:59:06  kduraisamy
empTypName included in minReqdQtyEmployee().

Revision 1.44  2005/01/05 13:00:41  kduraisamy
checkPostingByDateAndShift added.

Revision 1.43  2005/01/05 12:13:53  kduraisamy
checkPostingByDateAndShift added.

Revision 1.42  2005/01/05 09:08:50  kduraisamy
minReqdEmployees() added.

Revision 1.41  2005/01/03 07:28:20  kduraisamy
postRESULT = true added in postProductionDetails().

Revision 1.40  2005/01/03 06:18:22  kduraisamy
PROD_SHIFT is changed to SHIFT_ID.

Revision 1.39  2004/12/31 10:05:14  kduraisamy
PROD_QTY_SNOS added instead of PROD_QTY_STARTSNO.

Revision 1.38  2004/12/31 09:53:04  kduraisamy
std hrs = getStdHrs () method calling place changed.

Revision 1.37  2004/12/30 12:00:42  sponnusamy
stdhrs = stdhrs * totQty is taken out from for loop.

Revision 1.36  2004/12/30 09:01:32  kduraisamy
calling of  getStandardHrs() included.

Revision 1.35  2004/12/30 07:29:15  kduraisamy
method getStandardHrs() changed.

Revision 1.34  2004/12/30 05:42:48  kduraisamy
con.rollbackTransaction() included before throw exception.

Revision 1.33  2004/12/29 18:14:56  kduraisamy
ISHRS_ACCOUNTED flg checked in checkHrsAccounted().

Revision 1.32  2004/12/29 17:54:16  kduraisamy
prodQtySnos added instead of prodQtyStartSno.

Revision 1.31  2004/12/29 12:23:41  kduraisamy
getProductionDetails() modified.

Revision 1.30  2004/12/29 11:52:53  kduraisamy
unwanted println removed..

Revision 1.29  2004/12/29 11:30:08  kduraisamy
DBConnection  con = new DBConnection(). added for every method..

Revision 1.28  2004/12/29 11:24:14  kduraisamy
getProductionDetailsByWorkOrder() modified.

Revision 1.27  2004/12/28 17:57:50  kduraisamy
con.rollBackTransaction() added before throw new ProductionException()

Revision 1.26  2004/12/28 05:45:18  sduraisamy
getProdnJobDetails() modified to include sortString()

Revision 1.25  2004/12/27 10:06:36  kduraisamy
unwanted import removed.

Revision 1.24  2004/12/27 09:42:19  kduraisamy
getAllEmployeesByTypes() added..

Revision 1.23  2004/12/27 09:31:02  sduraisamy
getProductionJobOperationDetails() argument modified

Revision 1.22  2004/12/25 11:11:30  sduraisamy
Exception messages included

Revision 1.21  2004/12/24 05:48:21  kduraisamy
updateProductionDetails() added.

Revision 1.20  2004/12/23 10:11:32  sduraisamy
variable name started in uppercase modified in entityobject

Revision 1.19  2004/12/23 06:55:26  sduraisamy
Prod_Work_Typ included in filter method

Revision 1.18  2004/12/23 04:31:00  sduraisamy
Datestamp and IsValid included for filter method

Revision 1.17  2004/12/22 10:00:17  kduraisamy
addProductionDetails() modified.

Revision 1.16  2004/12/20 14:13:13  sduraisamy
filter method included

Revision 1.15  2004/12/18 10:39:09  kduraisamy
rework condition checking added for empHrs add.

Revision 1.14  2004/12/17 13:28:08  kduraisamy
main changed.

Revision 1.13  2004/12/16 12:27:55  kduraisamy
addNewNonProductionDetails() removed and placed into new Manager.

Revision 1.12  2004/12/16 09:49:59  kduraisamy
unwanted printlns removed.

Revision 1.11  2004/12/16 09:41:04  kduraisamy
method checkAvailableEmpHrs() called before insertion of PROD_EMP  and NPROD_EMP.

Revision 1.10  2004/12/16 04:44:15  kduraisamy
checkAvailableEmpHrs() added.

Revision 1.9  2004/12/15 11:57:08  kduraisamy
new String(string) is replaced with just string.

Revision 1.8  2004/12/15 11:16:36  kduraisamy
con.closeConnection() removed in checkHrsAccounted().

Revision 1.7  2004/12/15 10:25:02  sduraisamy
getStandardHrs() included

Revision 1.6  2004/12/15 10:15:46  kduraisamy
try catch properly placed.

Revision 1.5  2004/12/15 07:18:23  kduraisamy
new methods  viewUnPostedDetails(),postProductionDetails() added.

Revision 1.4  2004/12/14 14:08:32  kduraisamy
new method checkHrsAccounted() added..

Revision 1.3  2004/12/14 04:22:24  sduraisamy
getProductionJobDetailsByWorkOrder() included

***/