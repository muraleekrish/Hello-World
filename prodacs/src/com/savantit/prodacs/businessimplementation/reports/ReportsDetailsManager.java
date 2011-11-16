/*
 * Created on Feb 9, 2005
 *
 * ClassName	:  	ReportsDetailsManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reports;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.businessimplementation.production.EmployeeDtyOtDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingShiftDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetailsManager;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ProductionJobDetails;
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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsDetailsManager
{
    static Logger logger = Logger.getLogger(ReportsDetailsManager.class);	
    public ReportsDetailsManager()
    {
        logger.addAppender(LoggerOutput.getAppender());
        logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
    }
    public EmployeePayrollDetails[] fetchEmployeePayrollDetails(int pyrlCycleStatId) throws ReportsException, SQLException
    {
        EmployeePayrollDetails[] objEmployeePayrollDetailsList = null;
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        ht.put("PYRL_CYCLE_STAT_ID",new Integer(pyrlCycleStatId));
        Vector vecEmployeeDet = new Vector();
        try
        {
            con = DBConnectionFactory.getConnection();
            //TO SELECT ALL EMPLOYEES CORRESPONDING TO PARTICULAR PYRL_CYCLE_STAT_ID
            
            PreparedStatement ps_Employees = con.executeStatement(SQLMaster.EMP_ID_SELECT_SQL_QUERY,ht);
            ResultSet rs_Employees = ps_Employees.executeQuery();
            
            
            while(rs_Employees.next())
            {
                int empId = rs_Employees.getInt("EMP_ID");
                String empName = rs_Employees.getString("EMP_NAME");
                ht.put("EMP_ID",new Integer(empId));
                EmployeePayrollDetails  objEmployeePayrollDetails = new EmployeePayrollDetails();
                objEmployeePayrollDetails.setEmpId(empId);
                objEmployeePayrollDetails.setEmpName(empName);
                
                PreparedStatement ps_ShiftWiseDet = con.executeStatement(SQLMaster.SHIFT_WISE_DETAILS_SELECT_SQL_QUERY,ht);
                ResultSet rs_ShiftWiseDet = ps_ShiftWiseDet.executeQuery();
                
                Vector vecShiftDet = new Vector();
                while(rs_ShiftWiseDet.next())
                {
                    
                    PayrollDetails  objPayrollDetails = new PayrollDetails();
                    String prodDate = rs_ShiftWiseDet.getDate("PROD_CRNT_DATE").toString().substring(0,10);
                    String shiftName = rs_ShiftWiseDet.getString("SHIFT_NAME");
                    float dutyHrs = rs_ShiftWiseDet.getFloat("PYRL_DT_HRS");
                    float otHrs = rs_ShiftWiseDet.getFloat("PYRL_OT_HRS");
                    float rglrSalHrs = rs_ShiftWiseDet.getFloat("PYRL_RGLRSLRY_HRS");
                    float otSalHrs = rs_ShiftWiseDet.getFloat("PYRL_OTSLRY_HRS");
                    float incentiveSalHrs = rs_ShiftWiseDet.getFloat("PYRL_INCNTVSLRY_HRS");
                    int version = rs_ShiftWiseDet.getInt("PA_COUNT");
                    
                    String createdBy = rs_ShiftWiseDet.getString("PYRL_CREATEDBY");
                    int prepyrlId = rs_ShiftWiseDet.getInt("PREPYRL_ID");
                    ht.put("PREPYRL_ID",new Integer(prepyrlId));
                    
                    objPayrollDetails.setShiftName(shiftName);
                    objPayrollDetails.setProdDate(prodDate);
                    objPayrollDetails.setDutyHrs(dutyHrs);
                    objPayrollDetails.setOtHrs(otHrs);
                    objPayrollDetails.setRglrSalHrs(rglrSalHrs);
                    objPayrollDetails.setOtSalHrs(otSalHrs);
                    objPayrollDetails.setIncentiveSalHrs(incentiveSalHrs);
                    objPayrollDetails.setVersion(version+1);
                    objPayrollDetails.setCreatedBy(createdBy);
                    
                    vecShiftDet.add(objPayrollDetails);
                    
                    
                    if(version!=0)
                    {
                        for(int i = version ; i>0 ; i--)
                        {
                            ht.put("PA_COUNT",new Integer(i-1));
                            if(i==1)
                            {
                                PreparedStatement ps_ShiftWiseDetFromPrepyrl = con.executeStatement(SQLMaster.SHIFT_WISE_DETAILS_FROM_PREPYRL_SELECT_SQL_QUERY,ht);
                                ResultSet rs_ShiftWiseDetFromPrepyrl = ps_ShiftWiseDetFromPrepyrl.executeQuery();
                                objPayrollDetails = new PayrollDetails();
                                objPayrollDetails.setShiftName(shiftName);
                                objPayrollDetails.setProdDate(prodDate);
                                objPayrollDetails.setDutyHrs(dutyHrs);
                                objPayrollDetails.setOtHrs(otHrs);
                                if(rs_ShiftWiseDetFromPrepyrl.next())
                                {
                                    rglrSalHrs = rs_ShiftWiseDetFromPrepyrl.getFloat("PREPYRL_RGLRSLRY_HRS");
                                    otSalHrs = rs_ShiftWiseDetFromPrepyrl.getFloat("PREPYRL_OTSLRY_HRS");
                                    incentiveSalHrs = rs_ShiftWiseDetFromPrepyrl.getFloat("PREPYRL_INCNTVSLRY_HRS");
                                    createdBy = rs_ShiftWiseDetFromPrepyrl.getString("PREPYRL_CREATEDBY");
                                }
                                
                                objPayrollDetails.setRglrSalHrs(rglrSalHrs);
                                objPayrollDetails.setOtSalHrs(otSalHrs);
                                objPayrollDetails.setIncentiveSalHrs(incentiveSalHrs);
                                objPayrollDetails.setVersion(i);
                                objPayrollDetails.setCreatedBy(createdBy);
                                vecShiftDet.add(objPayrollDetails);
                            }
                            else
                            {
                                PreparedStatement ps_ShiftWiseDetFromLogPA = con.executeStatement(SQLMaster.SHIFT_WISE_DETAILS_FROM_LOG_PA_SELECT_SQL_QUERY,ht);
                                ResultSet rs_ShiftWiseDetFromLogPA = ps_ShiftWiseDetFromLogPA.executeQuery();
                                objPayrollDetails = new PayrollDetails();
                                if(rs_ShiftWiseDetFromLogPA.next())
                                {
                                    rglrSalHrs = rs_ShiftWiseDetFromLogPA.getFloat("PA_CHNGD_RGLRSLRY_HRS");
                                    otSalHrs = rs_ShiftWiseDetFromLogPA.getFloat("PA_CHNGD_OTSLRY_HRS");
                                    incentiveSalHrs = rs_ShiftWiseDetFromLogPA.getFloat("PA_CHNGD_INCNTVSLRY_HRS");
                                    createdBy = rs_ShiftWiseDetFromLogPA.getString("PA_CREATEDBY");
                                }
                                objPayrollDetails.setShiftName(shiftName);
                                objPayrollDetails.setProdDate(prodDate);
                                objPayrollDetails.setDutyHrs(dutyHrs);
                                objPayrollDetails.setOtHrs(otHrs);
                                objPayrollDetails.setRglrSalHrs(rglrSalHrs);
                                objPayrollDetails.setOtSalHrs(otSalHrs);
                                objPayrollDetails.setIncentiveSalHrs(incentiveSalHrs);
                                objPayrollDetails.setVersion(i);
                                objPayrollDetails.setCreatedBy(createdBy);
                                vecShiftDet.add(objPayrollDetails);
                                
                            }
                        }
                    }
                }
                objEmployeePayrollDetails.setVecShiftWiseDetails(vecShiftDet);
                vecEmployeeDet.add(objEmployeePayrollDetails);
            }
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        objEmployeePayrollDetailsList = new EmployeePayrollDetails[vecEmployeeDet.size()];
        vecEmployeeDet.copyInto(objEmployeePayrollDetailsList);
        
        return objEmployeePayrollDetailsList;
    }
    
    
    public ProductionMachineWiseDetails[] fetchProductionMachineWiseDetails(Date fromDate,Date toDate) throws ReportsException, SQLException, ProductionException
    {
        ProductionMachineWiseDetails[] objProductionMachineWiseDetailsList = null;
        DBConnection con = null;
        Hashtable ht_Prod = new Hashtable();
        ht_Prod.put("FROM_DATE",fromDate);
        ht_Prod.put("TO_DATE",toDate);
        int prodId = 0;
        int modifyCount = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps_ValidMachines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
            ResultSet rs_ValidMachines = ps_ValidMachines.executeQuery();
            Vector vec_Machines = new Vector();
            while(rs_ValidMachines.next())
            {
                
                
                String mcCode = rs_ValidMachines.getString("MC_CDE");
                String mcName = rs_ValidMachines.getString("MC_NAME");
                if(BuildConfig.DMODE)
                    System.out.println("mc_code :"+mcCode);
                ht_Prod.put("MC_CDE",mcCode);
                PreparedStatement ps_ProdSel = con.executeStatement(SQLMaster.PROD_SELECT_BY_MACHINE,ht_Prod);
                ResultSet rs_ProdSel = ps_ProdSel.executeQuery();
                while(rs_ProdSel.next())
                {
                    
                    prodId = rs_ProdSel.getInt("PROD_ID");
                    System.out.println("prodId:"+prodId);
                    modifyCount = rs_ProdSel.getInt("PROD_MODIFYCOUNT");
                    ht_Prod.put("PROD_ID",new Integer(prodId));
                    if(BuildConfig.DMODE)
                        System.out.println("modifyCount:"+modifyCount);
                    if(modifyCount == 0)
                    {
                        
                        ProductionMachineWiseDetails objProductionMachineWiseDetails = new ProductionMachineWiseDetails();
                        objProductionMachineWiseDetails.setMcCode(mcCode);
                        objProductionMachineWiseDetails.setMcName(mcName);
                        objProductionMachineWiseDetails.setProdCrntDate(rs_ProdSel.getTimestamp("PROD_CRNT_DATE").toString().substring(0,10));
                        System.out.println("Date :"+objProductionMachineWiseDetails.getProdCrntDate());
                        objProductionMachineWiseDetails.setShiftName(rs_ProdSel.getString("SHIFT_NAME"));
                        objProductionMachineWiseDetails.setWoNo(rs_ProdSel.getString("WO_NO"));
                        objProductionMachineWiseDetails.setJobName(rs_ProdSel.getString("JB_NAME"));
                        objProductionMachineWiseDetails.setProdQtySnos(rs_ProdSel.getString("PROD_QTY_SNOS"));
                        objProductionMachineWiseDetails.setProdWorkType(rs_ProdSel.getString("PROD_WORK_TYP").equalsIgnoreCase("N")?"Normal":"Rework");
                        objProductionMachineWiseDetails.setProdIncntvFlag(rs_ProdSel.getString("PROD_INCNTV_FLG").equals("1")?"Yes":"No");
                        objProductionMachineWiseDetails.setProdStartOpn(rs_ProdSel.getInt("PROD_START_OPN"));
                        objProductionMachineWiseDetails.setProdEndOpn(rs_ProdSel.getInt("PROD_END_OPN"));
                        objProductionMachineWiseDetails.setProdStdHrs(rs_ProdSel.getFloat("PROD_STD_HRS"));
                        objProductionMachineWiseDetails.setProdTotHrs(rs_ProdSel.getFloat("PROD_TOT_HRS"));
                        objProductionMachineWiseDetails.setProdPostFlg(rs_ProdSel.getString("PROD_POST_FLG").equals("1")?"Yes":"No");
                        objProductionMachineWiseDetails.setCreatedBy(rs_ProdSel.getString("PROD_CREATEDBY"));
                        objProductionMachineWiseDetails.setProdDateStamp(rs_ProdSel.getTimestamp("PROD_DATESTAMP").toString().substring(0,10));
                        System.out.println("Dates :"+objProductionMachineWiseDetails.getProdDateStamp());
                        objProductionMachineWiseDetails.setProdIsValid(rs_ProdSel.getInt("PROD_ISVALID"));
                        objProductionMachineWiseDetails.setModifyCount(rs_ProdSel.getInt("PROD_MODIFYCOUNT")+1);
                        
                        
                        
                        PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_PROD_EMP_DETAILS_SQL_QUERY,ht_Prod);
                        ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                        Vector vec_Emp_Det = new Vector();
                        while(rs_ProdEmp_Det.next())
                        {
                            EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                            objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                            objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
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
                        if(BuildConfig.DMODE)
                            System.out.println("vecSize :"+vec_Emp_Det.size());
                        objProductionMachineWiseDetails.setVecEmployeeDetails(vec_Emp_Det);
                        vec_Machines.add(objProductionMachineWiseDetails);
                        
                        
                        
                    }
                    else
                    {
                        for(int i = 0;i <= modifyCount;i++)
                        {
                            
                            ht_Prod.put("PROD_MODIFYCOUNT",new Integer(i));
                            if(i == modifyCount)
                            {
                                
                                ProductionMachineWiseDetails objProductionMachineWiseDetails = new ProductionMachineWiseDetails();
                                objProductionMachineWiseDetails.setMcCode(mcCode);
                                objProductionMachineWiseDetails.setMcName(mcName);
                                
                                objProductionMachineWiseDetails.setProdCrntDate(rs_ProdSel.getTimestamp("PROD_CRNT_DATE").toString().substring(0,10));
                                System.out.println("Date :"+objProductionMachineWiseDetails.getProdCrntDate());
                                objProductionMachineWiseDetails.setShiftId(rs_ProdSel.getInt("SHIFT_ID"));
                                objProductionMachineWiseDetails.setShiftName(rs_ProdSel.getString("SHIFT_NAME"));
                                objProductionMachineWiseDetails.setWoNo(rs_ProdSel.getString("WO_NO"));
                                objProductionMachineWiseDetails.setJobName(rs_ProdSel.getString("JB_NAME"));
                                objProductionMachineWiseDetails.setProdQtySnos(rs_ProdSel.getString("PROD_QTY_SNOS"));
                                objProductionMachineWiseDetails.setProdWorkType(rs_ProdSel.getString("PROD_WORK_TYP").equalsIgnoreCase("N")?"Normal":"Rework");
                                objProductionMachineWiseDetails.setProdIncntvFlag(rs_ProdSel.getString("PROD_INCNTV_FLG").equals("1")?"Yes":"No");
                                objProductionMachineWiseDetails.setProdStartOpn(rs_ProdSel.getInt("PROD_START_OPN"));
                                objProductionMachineWiseDetails.setProdEndOpn(rs_ProdSel.getInt("PROD_END_OPN"));
                                objProductionMachineWiseDetails.setProdStdHrs(rs_ProdSel.getFloat("PROD_STD_HRS"));
                                objProductionMachineWiseDetails.setProdTotHrs(rs_ProdSel.getFloat("PROD_TOT_HRS"));
                                objProductionMachineWiseDetails.setProdPostFlg(rs_ProdSel.getString("PROD_POST_FLG").equals("1")?"Yes":"No");
                                objProductionMachineWiseDetails.setCreatedBy(rs_ProdSel.getString("PROD_CREATEDBY"));
                                objProductionMachineWiseDetails.setProdDateStamp(rs_ProdSel.getTimestamp("PROD_DATESTAMP").toString().substring(0,10));
                                System.out.println("Dates :"+objProductionMachineWiseDetails.getProdDateStamp());
                                objProductionMachineWiseDetails.setProdIsValid(rs_ProdSel.getInt("PROD_ISVALID"));
                                objProductionMachineWiseDetails.setModifyCount(rs_ProdSel.getInt("PROD_MODIFYCOUNT")+1);
                                
                                
                                PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_PROD_EMP_DETAILS_SQL_QUERY,ht_Prod);
                                ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                                Vector vec_Emp_Det = new Vector();
                                while(rs_ProdEmp_Det.next())
                                {
                                    if(BuildConfig.DMODE)
                                        System.out.println("employee :"+i);
                                    EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                                    objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                                    objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
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
                                objProductionMachineWiseDetails.setVecEmployeeDetails(vec_Emp_Det);
                                if(BuildConfig.DMODE)
                                    System.out.println("vecSize1 :"+vec_Emp_Det.size());
                                vec_Machines.add(objProductionMachineWiseDetails);
                            }
                            else
                            {
                                
                                PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_LOG_PROD_DETAILS_SQL_QUERY,ht_Prod);
                                ResultSet rs_ProdDet_Get = ps_ProdDet_Get.executeQuery();
                                if(rs_ProdDet_Get.next())
                                {
                                    //set Production Details //
                                    if(BuildConfig.DMODE)
                                        System.out.println("employee1 :"+i);
                                    ProductionMachineWiseDetails objProductionMachineWiseDetails = new ProductionMachineWiseDetails();
                                    objProductionMachineWiseDetails.setMcCode(mcCode);
                                    objProductionMachineWiseDetails.setMcName(mcName);
                                    objProductionMachineWiseDetails.setProdId(prodId);
                                    objProductionMachineWiseDetails.setProdCrntDate(rs_ProdDet_Get.getTimestamp("PROD_CRNT_DATE").toString().substring(0,10));
                                    objProductionMachineWiseDetails.setShiftName(rs_ProdDet_Get.getString("SHIFT_NAME"));
                                    objProductionMachineWiseDetails.setProdWorkType(rs_ProdSel.getString("PROD_WORK_TYP").equalsIgnoreCase("N")?"Normal":"Rework");
                                    objProductionMachineWiseDetails.setProdIncntvFlag(rs_ProdDet_Get.getString("PROD_INCNTV_FLG").equals("1")?"Yes":"No");
                                    objProductionMachineWiseDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                                    objProductionMachineWiseDetails.setJobName(rs_ProdSel.getString("JB_NAME"));
                                    objProductionMachineWiseDetails.setProdQtySnos(rs_ProdDet_Get.getString("PROD_QTY_SNOS"));
                                    objProductionMachineWiseDetails.setProdTotQty(rs_ProdDet_Get.getInt("PROD_TOT_QTY"));
                                    objProductionMachineWiseDetails.setProdStartOpn(rs_ProdDet_Get.getInt("PROD_START_OPN"));
                                    objProductionMachineWiseDetails.setProdEndOpn(rs_ProdDet_Get.getInt("PROD_END_OPN"));
                                    objProductionMachineWiseDetails.setProdStdHrs(rs_ProdDet_Get.getFloat("PROD_STD_HRS"));
                                    objProductionMachineWiseDetails.setProdTotHrs(rs_ProdDet_Get.getFloat("PROD_TOT_HRS"));
                                    objProductionMachineWiseDetails.setProdUpdatePyrl(rs_ProdDet_Get.getString("PROD_UPDATE_PYRL").equals("1")?true:false);
                                    objProductionMachineWiseDetails.setProdUpdateWo(rs_ProdDet_Get.getString("PROD_UPDATE_WO").equals("1")?true:false);
                                    objProductionMachineWiseDetails.setProdPostFlg(rs_ProdSel.getString("PROD_POST_FLG").equals("1")?"Yes":"No");
                                    objProductionMachineWiseDetails.setCreatedBy(rs_ProdDet_Get.getString("PROD_CREATEDBY"));
                                    objProductionMachineWiseDetails.setProdDateStamp(rs_ProdDet_Get.getTimestamp("PROD_DATESTAMP").toString().substring(0,10));
                                    objProductionMachineWiseDetails.setProdIsValid(rs_ProdDet_Get.getInt("PROD_ISVALID"));
                                    objProductionMachineWiseDetails.setModifyCount(rs_ProdDet_Get.getInt("PROD_MODIFYCOUNT")+1);
                                    
                                    //get All Employee Details of the Above Production Record
                                    PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_LOG_PROD_EMP_DETAILS_SQL_QUERY,ht_Prod);
                                    ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                                    Vector vec_Emp_Det = new Vector();
                                    while(rs_ProdEmp_Det.next())
                                    {
                                        EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                                        if(BuildConfig.DMODE)
                                            System.out.println("employee2 :"+i);
                                        objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                                        objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
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
                                    objProductionMachineWiseDetails.setVecEmployeeDetails(vec_Emp_Det);
                                    if(BuildConfig.DMODE)
                                        System.out.println("vecSize2 :"+vec_Emp_Det.size());
                                    vec_Machines.add(objProductionMachineWiseDetails);
                                }
                                else
                                {
                                    throw new ProductionException("PMEC000","RECORD NOT FOUND","");
                                }
                                rs_ProdDet_Get.close();
                                ps_ProdDet_Get.close();
                                
                                
                                
                            }
                            
                        }
                    }
                    
                    
                }
            }
            
            objProductionMachineWiseDetailsList = new ProductionMachineWiseDetails[vec_Machines.size()];
            vec_Machines.copyInto(objProductionMachineWiseDetailsList);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        return objProductionMachineWiseDetailsList;
    }
    
    public JobTimeDetails[] fetchJobTimeDetails(Filter[] filter,String sortBy,boolean ascending,int startIndex) throws SQLException, ReportsException
    {
        
    	JobTimeDetails[] objJobTimeDetailsList = null; 
        Vector vecResult = new Vector();
        DBConnection con = null;
        con = DBConnectionFactory.getConnection();
        int totRecCount = 0;
        totRecCount = con.getRowCountWithComplexFilters(filter, SQLMaster.FETCH_JOB_DETAILS_FOR_REPORT);
        String query = con.buildDynamicQuery(filter, startIndex, startIndex + totRecCount ,sortBy,ascending, SQLMaster.FETCH_JOB_DETAILS_FOR_REPORT);
        
        if(BuildConfig.DMODE)
        System.out.println("QueryResult:"+query);
        
        ResultSet rs = con.executeRownumStatement(query+" ORDER BY JB_NAME,JB_DWG_NO");
        String jbNameNow = "";
        String jbDwgNoNow = "";
        String jbNameOld = "";
        String jbDwgNoOld = "";
        int woJbId = 0;
        int woJbQty = 0;
        JobTimeDetails objJobTimeDetails = null;
        while(rs.next())
        {
        	jbNameNow = rs.getString("JB_NAME");
		    jbDwgNoNow = rs.getString("JB_DWG_NO");
		    woJbQty = rs.getInt("WOJB_QTY");
		    
        	if(jbNameNow.equals(jbNameOld) && jbDwgNoNow.equals(jbDwgNoOld))
        	{
           		objJobTimeDetails.setJbTotQty(woJbQty + objJobTimeDetails.getJbTotQty());
        	}
        	else
        	{
			    objJobTimeDetails = new JobTimeDetails();
			    objJobTimeDetails.setJbName(jbNameNow);
			    objJobTimeDetails.setDrwgNo(jbDwgNoNow);
			    objJobTimeDetails.setJbTotQty(woJbQty);
			    
        	}
        	
            jbNameOld = jbNameNow;
            jbDwgNoOld = jbDwgNoNow;
            
            woJbId = rs.getInt("WOJB_ID");
            objJobTimeDetails = this.fetchJobQuantityDetails(woJbId,objJobTimeDetails);
            
            vecResult.remove(objJobTimeDetails);
		 	vecResult.add(objJobTimeDetails);
        }
        rs.close();
        objJobTimeDetailsList = new JobTimeDetails[vecResult.size()];
        vecResult.copyInto(objJobTimeDetailsList);
        return objJobTimeDetailsList;
    }
    
    public ActualHrsDetails[] fetchActualHrsDetails() throws SQLException, ReportsException
    {
        ActualHrsDetails[] objActualHrsDetailsList = null;
        Hashtable ht = new Hashtable();
        int custId = 0;
        String custName = "";
        float vtlHrs = 0;
        float hbHrs = 0;
        float drlHrs = 0;
        int jbId = 0;
        DBConnection con = null;
        
        if(BuildConfig.DMODE)
        {
            System.out.println("ACTUAL HRS DETAILS REPORTS STARTS");
        }
        logger.info("ACTUAL HRS DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_ALL_CUSTOMER_NAME); 
            ResultSet rs = ps.executeQuery();
            Vector vec_Result = new Vector();
            ResultSet rs_JobDet_Sel = null;
            PreparedStatement ps_JobDet_Sel = null;
            while(rs.next())
            {
                
                vtlHrs = 0;
                hbHrs = 0;
                drlHrs = 0;
                custId = rs.getInt("CUST_ID");
                custName = rs.getString("CUST_NAME");
                ht.put("CUST_ID",new Integer(custId));
                ps_JobDet_Sel = con.executeStatement(SQLMaster.SELECT_WO_JOB_HSTRY_DETAILS_SQL_QUERY,ht);
                rs_JobDet_Sel = ps_JobDet_Sel.executeQuery();
                
                ResultSet rs_Hrs_Sel = null;
                PreparedStatement ps_Hrs_Sel = null;
                while(rs_JobDet_Sel.next())
                {
                    
                    ActualHrsDetails  objActualHrsDetails = new ActualHrsDetails();
                    objActualHrsDetails.setCustId(custId);
                    objActualHrsDetails.setCustName(custName);                  
                    objActualHrsDetails.setJbName(rs_JobDet_Sel.getString("JB_NAME"));
                    objActualHrsDetails.setDrwgNo(rs_JobDet_Sel.getString("JB_DWG_NO"));
                    objActualHrsDetails.setRevsnNo(rs_JobDet_Sel.getString("JB_RVSN_NO"));
                    objActualHrsDetails.setMatlType(rs_JobDet_Sel.getString("JB_MATL_TYP"));
                    objActualHrsDetails.setWoNo(rs_JobDet_Sel.getString("WO_NO"));
                    
                    jbId = rs_JobDet_Sel.getInt("JB_ID");
                    ht.put("JB_ID",new Integer(jbId));                    
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_VTL_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        vtlHrs = rs_Hrs_Sel.getFloat("VTL_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_HB_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        hbHrs = rs_Hrs_Sel.getFloat("HB_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRL_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        drlHrs = rs_Hrs_Sel.getFloat("DRL_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    
                    float totHrs = vtlHrs + hbHrs + drlHrs;
                    objActualHrsDetails.setStdHrs(totHrs);
                    
                    int woJbId = rs_JobDet_Sel.getInt("WOJB_ID");
                    int woJbQty = rs_JobDet_Sel.getInt("WOJB_QTY");
                    
                    ht.put("WOJB_ID",new Integer(woJbId));
                    
                    float actualHrs[] = new float[woJbQty];
                    PreparedStatement ps_ActualHrs = con.executeStatement(SQLMaster.PROD_DETAILS_SELECT_SQL_QUERY,ht);
                    ResultSet rs_ActualHrs = ps_ActualHrs.executeQuery();
                    while(rs_ActualHrs.next())
                    {
                        float prodTotHrs = rs_ActualHrs.getFloat("PROD_TOT_HRS");
                        String prodQtySnos = rs_ActualHrs.getString("PROD_QTY_SNOS");
                        StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                        int count = 0;
                        
                        count = st.countTokens();
                        
                        prodTotHrs = prodTotHrs/count;
                        
                        while(st.hasMoreTokens())
                        {
                            int qtySno = Integer.parseInt(st.nextElement().toString());
                            actualHrs[qtySno-1] = actualHrs[qtySno-1] + prodTotHrs;
                        }
                        
                    }
                    for(int i = 0;i<actualHrs.length;i++)
                    {
                        for(int j = i;j<actualHrs.length;j++)
                        {
                            if(actualHrs[i]>actualHrs[j])
                            {
                                actualHrs[i]=actualHrs[i]+actualHrs[j]-(actualHrs[j]=actualHrs[i]);
                            }
                        }
                    }
                    objActualHrsDetails.setMinTimeTaken(actualHrs[0]);
                    objActualHrsDetails.setMaxTimeTaken(actualHrs[woJbQty - 1]);
                    float total = 0;
                    for(int k = 0;k<woJbQty;k++)
                    {
                        total = total + actualHrs[k];
                    }
                    objActualHrsDetails.setAvgTimeTaken(total/woJbQty);
                    
                    vec_Result.add(objActualHrsDetails);
                }
                rs_JobDet_Sel.close();
                ps_JobDet_Sel.close();
                
            }
            rs.close();
            ps.close();
            objActualHrsDetailsList = new ActualHrsDetails[vec_Result.size()];
            vec_Result.copyInto(objActualHrsDetailsList);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        if(BuildConfig.DMODE)
        {
            System.out.println("ACTUAL HRS DETAILS REPORTS ENDS");
        }
        logger.info("ACTUAL HRS DETAILS REPORTS ENDS");
        
        
        return objActualHrsDetailsList;
        
    }
    
    
    public ProductionDateWiseDetails[] fetchProductionDetails(Date fromDate,Date toDate) throws ReportsException, SQLException, ProductionException
    {
        ProductionDateWiseDetails[] objProductionDateWiseDetailsList = null;
        ProductionDetailsManager objProductionDetailsManager = new ProductionDetailsManager();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime(toDate);
        gcal.add(GregorianCalendar.DATE,1);
        toDate = gcal.getTime();
        Date currDate = null;
        Hashtable ht_ProdDate = new Hashtable();
        DBConnection con = null;
        int prodId = 0;
        try
        {
            con = DBConnectionFactory.getConnection();
            Vector vec_prodDateWiseDetails = new Vector();
            for(currDate = fromDate ; currDate.before(toDate);)
            {
                
                ProductionDateWiseDetails objProductionDateWiseDetails = new ProductionDateWiseDetails();
                ht_ProdDate.put("PROD_CRNT_DATE",currDate);
                
                PreparedStatement ps_ProdDetailsSel = con.executeStatement(SQLMaster.PRODID_SELECT_FOR_REPORT_SQL_QUERY,ht_ProdDate);
                ResultSet rs_ProdDetailsSel = ps_ProdDetailsSel.executeQuery();
                while(rs_ProdDetailsSel.next())
                {
                    prodId = rs_ProdDetailsSel.getInt("PROD_ID");
                    ht_ProdDate.put("PROD_ID",new Integer(prodId));
                    PreparedStatement ps_ModifyCnt_Sel = con.executeStatement(SQLMaster.PROD_MODIFY_COUNT_SELECT,ht_ProdDate);
                    ResultSet rs_ModifyCnt_Sel = ps_ModifyCnt_Sel.executeQuery();
                    int modifyCount = 0;
                    if(rs_ModifyCnt_Sel.next())
                    {
                        modifyCount = rs_ModifyCnt_Sel.getInt("PROD_MODIFYCOUNT");
                    }
                    
                    Vector vec = new Vector();
                    ProductionVersionWiseDetails objProductionVersionWiseDetails = new ProductionVersionWiseDetails();
                    objProductionVersionWiseDetails.setVersionCount(modifyCount + 1);
                    
                    if(modifyCount == 0)
                    {
                        
                        ProductionDetails  objProductionDetails = new ProductionDetails();
                        objProductionDetails = objProductionDetailsManager.getProductionDetails(prodId);
                        vec.add(objProductionDetails);
                        objProductionVersionWiseDetails.setVecProductionDetails(vec);
                    }
                    else
                    {
                        
                        for(int i = 0; i <= modifyCount; i++)
                        {
                            ProductionDetails  objProductionDetails = new ProductionDetails();
                            if(i == modifyCount)
                            {
                                objProductionDetails = objProductionDetailsManager.getProductionDetails(prodId);
                                vec.add(objProductionDetails);
                            }
                            else
                            {
                                ht_ProdDate.put("PROD_MODIFYCOUNT",new Integer(i));
                                ProductionJobDetails objProductionJobDetails = new ProductionJobDetails();
                                PreparedStatement ps_ProdDet_Get = con.executeStatement(SQLMaster.GET_LOG_PROD_DETAILS_SQL_QUERY,ht_ProdDate);
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
                                    objProductionDetails.setProdIncntvFlag(rs_ProdDet_Get.getString("PROD_INCNTV_FLG").equals("1")?true:false);
                                    objProductionDetails.setWoJbId(rs_ProdDet_Get.getInt("WOJB_ID"));
                                    objProductionDetails.setWoNo(rs_ProdDet_Get.getString("WO_NO"));
                                    objProductionDetails.setWoId(rs_ProdDet_Get.getInt("WO_ID"));
                                    objProductionDetails.setProdQtySnos(rs_ProdDet_Get.getString("PROD_QTY_SNOS"));
                                    objProductionDetails.setProdTotQty(rs_ProdDet_Get.getInt("PROD_TOT_QTY"));
                                    objProductionDetails.setProdStartOpn(rs_ProdDet_Get.getInt("PROD_START_OPN"));
                                    objProductionDetails.setProdEndOpn(rs_ProdDet_Get.getInt("PROD_END_OPN"));
                                    objProductionDetails.setProdStdHrs(rs_ProdDet_Get.getFloat("PROD_STD_HRS"));
                                    objProductionDetails.setProdTotHrs(rs_ProdDet_Get.getFloat("PROD_TOT_HRS"));
                                    objProductionDetails.setProdUpdatePyrl(rs_ProdDet_Get.getString("PROD_UPDATE_PYRL").equals("1")?true:false);
                                    objProductionDetails.setProdUpdateWo(rs_ProdDet_Get.getString("PROD_UPDATE_WO").equals("1")?true:false);
                                    objProductionDetails.setProdPostFlg(rs_ProdDet_Get.getString("PROD_POST_FLG").equals("1")?true:false);
                                    objProductionDetails.setDeleted(rs_ProdDet_Get.getString("IS_DLTD").equals("1")?true:false);
                                    objProductionDetails.setCreatedBy(rs_ProdDet_Get.getString("PROD_CREATEDBY"));
                                    objProductionDetails.setProdDateStamp(rs_ProdDet_Get.getTimestamp("PROD_DATESTAMP"));
                                    objProductionDetails.setProdIsValid(rs_ProdDet_Get.getInt("PROD_ISVALID"));
                                    objProductionDetails.setModifyCount(rs_ProdDet_Get.getInt("PROD_MODIFYCOUNT"));
                                    ///set Job Details - starts///
                                    //objProductionJobDetails.setJobId(rs_ProdDet_Get.getInt("JB_ID"));
                                    objProductionJobDetails.setJobName(rs_ProdDet_Get.getString("JB_NAME"));
                                    objProductionJobDetails.setDwgNo(rs_ProdDet_Get.getString("JB_DWG_NO"));
                                    objProductionJobDetails.setRvsnNo(rs_ProdDet_Get.getString("JB_RVSN_NO"));
                                    objProductionJobDetails.setMatlType(rs_ProdDet_Get.getString("JB_MATL_TYP"));
                                    
                                    objProductionDetails.setObjProductionJobDetails(objProductionJobDetails);
                                    //set Job Details - Ends///
                                    
                                    //get All Employee Details of the Above Production Record
                                    PreparedStatement ps_ProdEmp_Det = con.executeStatement(SQLMaster.GET_LOG_PROD_EMP_DETAILS_SQL_QUERY,ht_ProdDate);
                                    ResultSet rs_ProdEmp_Det = ps_ProdEmp_Det.executeQuery();
                                    Vector vec_Emp_Det = new Vector();
                                    while(rs_ProdEmp_Det.next())
                                    {
                                        EmployeeDtyOtDetails objEmployeeDtyOtDetails = new EmployeeDtyOtDetails();
                                        
                                        objEmployeeDtyOtDetails.setEmpId(rs_ProdEmp_Det.getInt("EMP_ID"));
                                        objEmployeeDtyOtDetails.setEmpName(rs_ProdEmp_Det.getString("EMP_NAME"));
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
                                
                                vec.add(objProductionDetails);
                                
                            }
                        }
                        objProductionVersionWiseDetails.setVecProductionDetails(vec);
                        
                    }
                    
                    
                }
                
                vec_prodDateWiseDetails.add(objProductionDateWiseDetails);
                
                GregorianCalendar gc1 = new GregorianCalendar();
                gc1.setTime(currDate);
                gc1.add(GregorianCalendar.DATE,1);
                currDate = gc1.getTime();
                
            }
            
            objProductionDateWiseDetailsList = new ProductionDateWiseDetails[vec_prodDateWiseDetails.size()];
            vec_prodDateWiseDetails.copyInto(objProductionDateWiseDetailsList);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        return objProductionDateWiseDetailsList;
        
    }
    public ProductionAccountingMachineDetails[] fetchProductionAccountingDateDetails(Date prodDate) throws SQLException, ReportsException
    {
        
        ProductionAccountingMachineDetails[] objProductionAccountingMachineDetailsList = null;
        DBConnection con = null;
        Hashtable ht_ProdDate = new Hashtable();
        float prodHrs = 0;
        float nprodHrs = 0;
        float availShiftHrs = 0;
        String availStartTime = "";
        String availEndTime = "";
        Hashtable ht_AvailHrs_Get = new Hashtable();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            ht_ProdDate.put("PROD_CRNT_DATE",prodDate);
            PreparedStatement ps_ValidMachines = con.executeStatement(SQLMaster.VALID_MACHINES_SELECT_SQL_QUERY);
            ResultSet rs_ValidMachines = ps_ValidMachines.executeQuery();
            
            Vector vec_MachineResult = new Vector();
            while(rs_ValidMachines.next())
            {
                Vector vec_ShiftResult = new Vector();
                ProductionAccountingShiftDetails[] objProductionAccountingShiftDetailsList = null;
                ProductionAccountingMachineDetails objProductionAccountingMachineDetails = new ProductionAccountingMachineDetails();
                
                String mcCode = rs_ValidMachines.getString("MC_CDE");
                String mcName = rs_ValidMachines.getString("MC_NAME");
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
                
                objProductionAccountingShiftDetailsList = new ProductionAccountingShiftDetails[vec_ShiftResult.size()];
                vec_ShiftResult.copyInto(objProductionAccountingShiftDetailsList);
                objProductionAccountingMachineDetails.setVecProductionAccountingShiftDetails(vec_ShiftResult);
                objProductionAccountingMachineDetails.setArrProductionAccountingShiftDetails(objProductionAccountingShiftDetailsList);
                vec_MachineResult.add(objProductionAccountingMachineDetails);
            }
            
            objProductionAccountingMachineDetailsList = new ProductionAccountingMachineDetails[vec_MachineResult.size()];        
            vec_MachineResult.copyInto(objProductionAccountingMachineDetailsList);
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        return objProductionAccountingMachineDetailsList;
    }
    
    //This method is optimized.
    public SalaryReportDetails[] fetchSalaryReports(int month,int year) throws SQLException, ReportsException
    {
        SalaryReportDetails[] objSalaryReportDetailsList;
        DBConnection con = null;
        int empId = 0;
        Hashtable ht = new Hashtable();
        Vector vec_Result = new Vector();
        Date fromDate = null;
        Date toDate = null;
        int days = 0;
        
        try
        {
            
            if(BuildConfig.DMODE)
            {
                System.out.println("FETCH SALARY REPORTS STARTS");
            }
            logger.info("FETCH SALARY REPORTS STARTS");
            //          to get from date and to date of the selected month..
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            fromDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("fdate "+fromDate);
            days = gc.getActualMaximum(GregorianCalendar.DATE);
            gc.set(GregorianCalendar.DATE,days);
            toDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("tdate "+toDate);
            
            
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            PreparedStatement ps_EmpIds = con.executeStatement(SQLMaster.SELECT_EMPID_FOR_REPORT_SQL_QUERY,ht); 
            ResultSet rs_EmpIds = ps_EmpIds.executeQuery();
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(toDate);
            gcal.add(GregorianCalendar.DATE,1);
            toDate = gcal.getTime();
            
            
            while(rs_EmpIds.next())
            {
                
                SalaryReportDetails objSalaryReportDetails = new SalaryReportDetails();
                empId = rs_EmpIds.getInt("EMP_ID");
                objSalaryReportDetails.setEmpName(rs_EmpIds.getString("EMP_NAME"));
                Date tempDate;
                ht.put("EMP_ID",new Integer(empId));
                SalHrsObjDetails  objSalHrsObjDetails = new SalHrsObjDetails();
                IncHrsObjDetails  objIncHrsObjDetails = new IncHrsObjDetails();
                OtHrsObjDetails objOtHrsObjDetails = new OtHrsObjDetails();
                double rglrSalTotHrs = 0;
                double incSalTotHrs = 0;
                double otSalTotHrs = 0;
                ResultSet rs = null;
                PreparedStatement ps = null;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_EMPDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    String rglSalval = "0.0";
                    String incSalVal = "0.0";
                    String otSalVal = "0.0";
                    String dtHrs = "0.0";
                    String otHrs = "0.0";
                    if(rs.next())
                    {
                        rglSalval = rs.getDouble("RGLRSLRY")+"";
                        incSalVal = rs.getDouble("INCNTVSLRY")+"";
                        otSalVal = rs.getDouble("OTSLRY")+"";
                        dtHrs = rs.getDouble("DTHRS")+"";
                        otHrs = rs.getDouble("OTHRS")+"";
                        
                    }
                    rs.close();
                    ps.close();
                    
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    int i = g.get(GregorianCalendar.DATE);
                    
                    if(dtHrs.equals("0.0") && otHrs.equals("0.0"))
                    {
                        int day = g.get(GregorianCalendar.DAY_OF_WEEK);
                        
                        if(day != 1)
                        {
                            rglSalval =   "L";
                        }
                        else
                            rglSalval = "Sun";
                    }
                    if(!rglSalval.equals("0.0") && !rglSalval.equals("Sun") && !rglSalval.equals("L"))
                    {
                        rglrSalTotHrs = rglrSalTotHrs + Double.parseDouble(rglSalval);
                    }
                    if(!incSalVal.equals("0.0"))
                        incSalTotHrs = incSalTotHrs + Double.parseDouble(incSalVal);
                    else
                    {
                        incSalVal = " ";
                    }
                    
                    if(!otSalVal.equals("0.0"))
                        otSalTotHrs = otSalTotHrs + Double.parseDouble(otSalVal);
                    else
                    {
                        otSalVal = " ";
                    }
                    
                    switch(i)
                    {
                    
                    
                    case 1:
                        objSalHrsObjDetails.setDay1(rglSalval);
                        objIncHrsObjDetails.setDay1(incSalVal);
                        objOtHrsObjDetails.setDay1(otSalVal);
                        break;
                    case 2:
                        objSalHrsObjDetails.setDay2(rglSalval);
                        objIncHrsObjDetails.setDay2(incSalVal);
                        objOtHrsObjDetails.setDay2(otSalVal);
                        break;
                    case 3:
                        objSalHrsObjDetails.setDay3(rglSalval);
                        objIncHrsObjDetails.setDay3(incSalVal);
                        objOtHrsObjDetails.setDay3(otSalVal);
                        break;
                    case 4:
                        objSalHrsObjDetails.setDay4(rglSalval);
                        objIncHrsObjDetails.setDay4(incSalVal);
                        objOtHrsObjDetails.setDay4(otSalVal);
                        break;
                    case 5:
                        objSalHrsObjDetails.setDay5(rglSalval);
                        objIncHrsObjDetails.setDay5(incSalVal);
                        objOtHrsObjDetails.setDay5(otSalVal);
                        
                        break;
                    case 6:
                        objSalHrsObjDetails.setDay6(rglSalval);
                        objIncHrsObjDetails.setDay6(incSalVal);
                        objOtHrsObjDetails.setDay6(otSalVal);
                        
                        break;
                    case 7:
                        objSalHrsObjDetails.setDay7(rglSalval);
                        objIncHrsObjDetails.setDay7(incSalVal);
                        objOtHrsObjDetails.setDay7(otSalVal);
                        
                        break;
                    case 8:
                        objSalHrsObjDetails.setDay8(rglSalval);
                        objIncHrsObjDetails.setDay8(incSalVal);
                        objOtHrsObjDetails.setDay8(otSalVal);
                        
                        break;
                    case 9:
                        objSalHrsObjDetails.setDay9(rglSalval);
                        objIncHrsObjDetails.setDay9(incSalVal);
                        objOtHrsObjDetails.setDay9(otSalVal);
                        
                        break;
                    case 10:
                        objSalHrsObjDetails.setDay10(rglSalval);
                        objIncHrsObjDetails.setDay10(incSalVal);
                        objOtHrsObjDetails.setDay10(otSalVal);
                        
                        break;
                    case 11:
                        objSalHrsObjDetails.setDay11(rglSalval);
                        objIncHrsObjDetails.setDay11(incSalVal);
                        objOtHrsObjDetails.setDay11(otSalVal);
                        
                        break;
                    case 12:
                        objSalHrsObjDetails.setDay12(rglSalval);
                        objIncHrsObjDetails.setDay12(incSalVal);
                        objOtHrsObjDetails.setDay12(otSalVal);
                        
                        break;
                    case 13:
                        objSalHrsObjDetails.setDay13(rglSalval);
                        objIncHrsObjDetails.setDay13(incSalVal);
                        objOtHrsObjDetails.setDay13(otSalVal);
                        
                        break;
                    case 14:
                        objSalHrsObjDetails.setDay14(rglSalval);
                        objIncHrsObjDetails.setDay14(incSalVal);
                        objOtHrsObjDetails.setDay14(otSalVal);
                        
                        break;
                    case 15:
                        objSalHrsObjDetails.setDay15(rglSalval);
                        objIncHrsObjDetails.setDay15(incSalVal);
                        objOtHrsObjDetails.setDay15(otSalVal);
                        
                        break;
                    case 16:
                        objSalHrsObjDetails.setDay16(rglSalval);
                        objIncHrsObjDetails.setDay16(incSalVal);
                        objOtHrsObjDetails.setDay16(otSalVal);
                        
                        break;
                    case 17:
                        objSalHrsObjDetails.setDay17(rglSalval);
                        objIncHrsObjDetails.setDay17(incSalVal);
                        objOtHrsObjDetails.setDay17(otSalVal);
                        
                        break;
                    case 18:
                        objSalHrsObjDetails.setDay18(rglSalval);
                        objIncHrsObjDetails.setDay18(incSalVal);
                        objOtHrsObjDetails.setDay18(otSalVal);
                        
                        break;
                    case 19:
                        objSalHrsObjDetails.setDay19(rglSalval);
                        objIncHrsObjDetails.setDay19(incSalVal);
                        objOtHrsObjDetails.setDay19(otSalVal);
                        
                        break;
                    case 20:
                        objSalHrsObjDetails.setDay20(rglSalval);
                        objIncHrsObjDetails.setDay20(incSalVal);
                        objOtHrsObjDetails.setDay20(otSalVal);
                        
                        break;
                        
                    case 21:
                        objSalHrsObjDetails.setDay21(rglSalval);
                        objIncHrsObjDetails.setDay21(incSalVal);
                        objOtHrsObjDetails.setDay21(otSalVal);
                        
                        break;
                    case 22:
                        objSalHrsObjDetails.setDay22(rglSalval);
                        objIncHrsObjDetails.setDay22(incSalVal);
                        objOtHrsObjDetails.setDay22(otSalVal);
                        
                        break;
                    case 23:
                        objSalHrsObjDetails.setDay23(rglSalval);
                        objIncHrsObjDetails.setDay23(incSalVal);
                        objOtHrsObjDetails.setDay23(otSalVal);
                        
                        break;
                    case 24:
                        objSalHrsObjDetails.setDay24(rglSalval);
                        objIncHrsObjDetails.setDay24(incSalVal);
                        objOtHrsObjDetails.setDay24(otSalVal);
                        
                        break;
                    case 25:
                        objSalHrsObjDetails.setDay25(rglSalval);
                        objIncHrsObjDetails.setDay25(incSalVal);
                        objOtHrsObjDetails.setDay25(otSalVal);
                        
                        break;
                    case 26:
                        objSalHrsObjDetails.setDay26(rglSalval);
                        objIncHrsObjDetails.setDay26(incSalVal);
                        objOtHrsObjDetails.setDay26(otSalVal);
                        
                        break;
                    case 27:
                        objSalHrsObjDetails.setDay27(rglSalval);
                        objIncHrsObjDetails.setDay27(incSalVal);
                        objOtHrsObjDetails.setDay27(otSalVal);
                        
                        break;
                    case 28:
                        objSalHrsObjDetails.setDay28(rglSalval);
                        objIncHrsObjDetails.setDay28(incSalVal);
                        objOtHrsObjDetails.setDay28(otSalVal);
                        
                        break;
                    case 29:
                        objSalHrsObjDetails.setDay29(rglSalval);
                        objIncHrsObjDetails.setDay29(incSalVal);
                        objOtHrsObjDetails.setDay29(otSalVal);
                        
                        break;
                    case 30:
                        objSalHrsObjDetails.setDay30(rglSalval);
                        objIncHrsObjDetails.setDay30(incSalVal);
                        objOtHrsObjDetails.setDay30(otSalVal);
                        
                        break;
                    case 31:
                        objSalHrsObjDetails.setDay31(rglSalval);
                        objIncHrsObjDetails.setDay31(incSalVal);
                        objOtHrsObjDetails.setDay31(otSalVal);
                        
                        break;
                        
                    }
                                  
                    
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                    
                    
                }
                objSalHrsObjDetails.setTotHrs(rglrSalTotHrs+"");
                objIncHrsObjDetails.setTotHrs(incSalTotHrs+"");
                objOtHrsObjDetails.setTotHrs(otSalTotHrs+"");
                objSalaryReportDetails.setObjSalHrsDetails(objSalHrsObjDetails);
                objSalaryReportDetails.setObjIncHrsDetails(objIncHrsObjDetails);
                objSalaryReportDetails.setObjOtHrsDetails(objOtHrsObjDetails);
                vec_Result.addElement(objSalaryReportDetails);
            }
            rs_EmpIds.close();
            ps_EmpIds.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        objSalaryReportDetailsList = new SalaryReportDetails[vec_Result.size()];
        vec_Result.copyInto(objSalaryReportDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH SALARY REPORTS ENDS");
        }
        logger.info("FETCH SALARY REPORTS ENDS");
        
        return objSalaryReportDetailsList;
    }
    
    //This method is not optimized.
    /*public SalaryReportDetails[] fetchSalaryReports(int month,int year) throws SQLException, ReportsException
    {
        SalaryReportDetails[] objSalaryReportDetailsList;
        DBConnection con = null;
        int empId = 0;
        Hashtable ht = new Hashtable();
        Vector vec_Result = new Vector();
        Date fromDate = null;
        Date toDate = null;
        int days = 0;
        
        try
        {
            
            if(BuildConfig.DMODE)
            {
                System.out.println("FETCH SALARY REPORTS STARTS");
            }
            logger.info("FETCH SALARY REPORTS STARTS");
            //          to get from date and to date of the selected month..
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            fromDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("fdate "+fromDate);
            days = gc.getActualMaximum(GregorianCalendar.DATE);
            gc.set(GregorianCalendar.DATE,days);
            toDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("tdate "+toDate);
            
            
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            PreparedStatement ps_EmpIds = con.executeStatement(SQLMaster.SELECT_EMPID_FOR_REPORT_SQL_QUERY,ht); 
            ResultSet rs_EmpIds = ps_EmpIds.executeQuery();
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(toDate);
            gcal.add(GregorianCalendar.DATE,1);
            toDate = gcal.getTime();
            
            
            while(rs_EmpIds.next())
            {
                
                SalaryReportDetails objSalaryReportDetails = new SalaryReportDetails();
                empId = rs_EmpIds.getInt("EMP_ID");
                objSalaryReportDetails.setEmpName(rs_EmpIds.getString("EMP_NAME"));
                Date tempDate;
                ht.put("EMP_ID",new Integer(empId));
                SalHrsObjDetails  objSalHrsObjDetails = new SalHrsObjDetails();
                double totHrs = 0;
                ResultSet rs = null;
                PreparedStatement ps = null;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_EMPDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    String val = "0.0";
                    String dtHrs = "0.0";
                    String otHrs = "0.0";
                    if(rs.next())
                    {
                        val = rs.getDouble("RGLRSLRY")+"";
                        dtHrs = rs.getDouble("DTHRS")+"";
                        otHrs = rs.getDouble("OTHRS")+"";
                    }
                    rs.close();
                    ps.close();
                    
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    int i = g.get(GregorianCalendar.DATE);
                    
                    if(dtHrs.equals("0.0") && otHrs.equals("0.0"))
                    {
                        int day = g.get(GregorianCalendar.DAY_OF_WEEK);
                        
                        if(day != 1)
                        {
                            val =   "L";
                        }
                        else
                            val = "Sun";
                    }
                    if(!val.equals("0.0") && !val.equals("Sun") && !val.equals("L"))
                    {
                        totHrs = totHrs + Double.parseDouble(val);
                    }
                    switch(i)
                    {
                    
                    
                    case 1:
                        objSalHrsObjDetails.setDay1(val);
                        break;
                    case 2:
                        objSalHrsObjDetails.setDay2(val);
                        break;
                    case 3:
                        objSalHrsObjDetails.setDay3(val);
                        break;
                    case 4:
                        objSalHrsObjDetails.setDay4(val);
                        break;
                    case 5:
                        objSalHrsObjDetails.setDay5(val);
                        break;
                    case 6:
                        objSalHrsObjDetails.setDay6(val);
                        break;
                    case 7:
                        objSalHrsObjDetails.setDay7(val);
                        break;
                    case 8:
                        objSalHrsObjDetails.setDay8(val);
                        break;
                    case 9:
                        objSalHrsObjDetails.setDay9(val);
                        break;
                    case 10:
                        objSalHrsObjDetails.setDay10(val);
                        break;
                    case 11:
                        objSalHrsObjDetails.setDay11(val);
                        break;
                    case 12:
                        objSalHrsObjDetails.setDay12(val);
                        break;
                    case 13:
                        objSalHrsObjDetails.setDay13(val);
                        break;
                    case 14:
                        objSalHrsObjDetails.setDay14(val);
                        break;
                    case 15:
                        objSalHrsObjDetails.setDay15(val);
                        break;
                    case 16:
                        objSalHrsObjDetails.setDay16(val);
                        break;
                    case 17:
                        objSalHrsObjDetails.setDay17(val);
                        break;
                    case 18:
                        objSalHrsObjDetails.setDay18(val);
                        break;
                    case 19:
                        objSalHrsObjDetails.setDay19(val);
                        break;
                    case 20:
                        objSalHrsObjDetails.setDay20(val);
                        break;
                        
                    case 21:
                        objSalHrsObjDetails.setDay21(val);
                        break;
                    case 22:
                        objSalHrsObjDetails.setDay22(val);
                        break;
                    case 23:
                        objSalHrsObjDetails.setDay23(val);
                        break;
                    case 24:
                        objSalHrsObjDetails.setDay24(val);
                        break;
                    case 25:
                        objSalHrsObjDetails.setDay25(val);
                        break;
                    case 26:
                        objSalHrsObjDetails.setDay26(val);
                        break;
                    case 27:
                        objSalHrsObjDetails.setDay27(val);
                        break;
                    case 28:
                        objSalHrsObjDetails.setDay28(val);
                        break;
                    case 29:
                        objSalHrsObjDetails.setDay29(val);
                        break;
                    case 30:
                        objSalHrsObjDetails.setDay30(val);
                        break;
                    case 31:
                        objSalHrsObjDetails.setDay31(val);
                        break;
                        
                    }
                    
                    
                    
                    
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                    
                    
                }
                objSalHrsObjDetails.setTotHrs(totHrs+"");
                IncHrsObjDetails  objIncHrsObjDetails = new IncHrsObjDetails();
                totHrs = 0;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_EMPDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String val = "";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                        val = rs.getDouble("INCNTVSLRY")+"";
                    rs.close();
                    ps.close();
                    if(!val.equals("0.0"))
                        totHrs = totHrs + Double.parseDouble(val);
                    else
                    {
                        val = " ";
                    }
                    switch(i)
                    {
                    
                    
                    case 1:
                        objIncHrsObjDetails.setDay1(val);
                        break;
                    case 2:
                        objIncHrsObjDetails.setDay2(val);
                        break;
                    case 3:
                        objIncHrsObjDetails.setDay3(val);
                        break;
                    case 4:
                        objIncHrsObjDetails.setDay4(val);
                        break;
                    case 5:
                        objIncHrsObjDetails.setDay5(val);
                        break;
                    case 6:
                        objIncHrsObjDetails.setDay6(val);
                        break;
                    case 7:
                        objIncHrsObjDetails.setDay7(val);
                        break;
                    case 8:
                        objIncHrsObjDetails.setDay8(val);
                        break;
                    case 9:
                        objIncHrsObjDetails.setDay9(val);
                        break;
                    case 10:
                        objIncHrsObjDetails.setDay10(val);
                        break;
                    case 11:
                        objIncHrsObjDetails.setDay11(val);
                        break;
                    case 12:
                        objIncHrsObjDetails.setDay12(val);
                        break;
                    case 13:
                        objIncHrsObjDetails.setDay13(val);
                        break;
                    case 14:
                        objIncHrsObjDetails.setDay14(val);
                        break;
                    case 15:
                        objIncHrsObjDetails.setDay15(val);
                        break;
                    case 16:
                        objIncHrsObjDetails.setDay16(val);
                        break;
                    case 17:
                        objIncHrsObjDetails.setDay17(val);
                        break;
                    case 18:
                        objIncHrsObjDetails.setDay18(val);
                        break;
                    case 19:
                        objIncHrsObjDetails.setDay19(val);
                        break;
                    case 20:
                        objIncHrsObjDetails.setDay20(val);
                        break;
                        
                    case 21:
                        objIncHrsObjDetails.setDay21(val);
                        break;
                    case 22:
                        objIncHrsObjDetails.setDay22(val);
                        break;
                    case 23:
                        objIncHrsObjDetails.setDay23(val);
                        break;
                    case 24:
                        objIncHrsObjDetails.setDay24(val);
                        break;
                    case 25:
                        objIncHrsObjDetails.setDay25(val);
                        break;
                    case 26:
                        objIncHrsObjDetails.setDay26(val);
                        break;
                    case 27:
                        objIncHrsObjDetails.setDay27(val);
                        break;
                    case 28:
                        objIncHrsObjDetails.setDay28(val);
                        break;
                    case 29:
                        objIncHrsObjDetails.setDay29(val);
                        break;
                    case 30:
                        objIncHrsObjDetails.setDay30(val);
                        break;
                    case 31:
                        objIncHrsObjDetails.setDay31(val);
                        break;
                        
                    }
                    
                    
                    
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                    
                    
                    
                }
                
                objIncHrsObjDetails.setTotHrs(totHrs+"");
                
                OtHrsObjDetails objOtHrsObjDetails = new OtHrsObjDetails();
                totHrs = 0;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_EMPDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String val = "";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                        val = rs.getDouble("OTSLRY")+"";
                    rs.close();
                    ps.close();
                    if(!val.equals("0.0"))
                        totHrs = totHrs + Double.parseDouble(val);
                    else
                    {
                        val = " ";
                    }
                    switch(i)
                    {
                    
                    
                    case 1:
                        objOtHrsObjDetails.setDay1(val);
                        break;
                    case 2:
                        objOtHrsObjDetails.setDay2(val);
                        break;
                    case 3:
                        objOtHrsObjDetails.setDay3(val);
                        break;
                    case 4:
                        objOtHrsObjDetails.setDay4(val);
                        break;
                    case 5:
                        objOtHrsObjDetails.setDay5(val);
                        break;
                    case 6:
                        objOtHrsObjDetails.setDay6(val);
                        break;
                    case 7:
                        objOtHrsObjDetails.setDay7(val);
                        break;
                    case 8:
                        objOtHrsObjDetails.setDay8(val);
                        break;
                    case 9:
                        objOtHrsObjDetails.setDay9(val);
                        break;
                    case 10:
                        objOtHrsObjDetails.setDay10(val);
                        break;
                    case 11:
                        objOtHrsObjDetails.setDay11(val);
                        break;
                    case 12:
                        objOtHrsObjDetails.setDay12(val);
                        break;
                    case 13:
                        objOtHrsObjDetails.setDay13(val);
                        break;
                    case 14:
                        objOtHrsObjDetails.setDay14(val);
                        break;
                    case 15:
                        objOtHrsObjDetails.setDay15(val);
                        break;
                    case 16:
                        objOtHrsObjDetails.setDay16(val);
                        break;
                    case 17:
                        objOtHrsObjDetails.setDay17(val);
                        break;
                    case 18:
                        objOtHrsObjDetails.setDay18(val);
                        break;
                    case 19:
                        objOtHrsObjDetails.setDay19(val);
                        break;
                    case 20:
                        objOtHrsObjDetails.setDay20(val);
                        break;
                        
                    case 21:
                        objOtHrsObjDetails.setDay21(val);
                        break;
                    case 22:
                        objOtHrsObjDetails.setDay22(val);
                        break;
                    case 23:
                        objOtHrsObjDetails.setDay23(val);
                        break;
                    case 24:
                        objOtHrsObjDetails.setDay24(val);
                        break;
                    case 25:
                        objOtHrsObjDetails.setDay25(val);
                        break;
                    case 26:
                        objOtHrsObjDetails.setDay26(val);
                        break;
                    case 27:
                        objOtHrsObjDetails.setDay27(val);
                        break;
                    case 28:
                        objOtHrsObjDetails.setDay28(val);
                        break;
                    case 29:
                        objOtHrsObjDetails.setDay29(val);
                        break;
                    case 30:
                        objOtHrsObjDetails.setDay30(val);
                        break;
                    case 31:
                        objOtHrsObjDetails.setDay31(val);
                        break;
                        
                    }
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                    
                }
                objOtHrsObjDetails.setTotHrs(totHrs+"");
                
                objSalaryReportDetails.setObjSalHrsDetails(objSalHrsObjDetails);
                objSalaryReportDetails.setObjIncHrsDetails(objIncHrsObjDetails);
                objSalaryReportDetails.setObjOtHrsDetails(objOtHrsObjDetails);
                vec_Result.addElement(objSalaryReportDetails);
            }
            rs_EmpIds.close();
            ps_EmpIds.close();
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        objSalaryReportDetailsList = new SalaryReportDetails[vec_Result.size()];
        vec_Result.copyInto(objSalaryReportDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH SALARY REPORTS ENDS");
        }
        logger.info("FETCH SALARY REPORTS ENDS");
        
        return objSalaryReportDetailsList;
    }
    */
    
    //This method is optimized.
    public MonthlyReportDetails[] fetchMonthlyReportsDetailsValidationReport(int month,int year) throws SQLException, ReportsException
    {
        
        MonthlyReportDetails[] objMonthlyReportDetailsList;
        DBConnection con = null;
        int empId = 0;
        Hashtable ht = new Hashtable();
        Vector vec_Result = new Vector();
        Date fromDate = null;
        Date toDate = null;
        int days = 0;
        try
        {
            
            if(BuildConfig.DMODE)
            {
                System.out.println("FETCH MONTHLY REPORTS STARTS");
            }
            logger.info("FETCH MONTHLY REPORTS STARTS");
            
            
            //          to get from date and to date of the selected month..
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            fromDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("fdate "+fromDate);
            days = gc.getActualMaximum(GregorianCalendar.DATE);
            gc.set(GregorianCalendar.DATE,days);
            toDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("tdate "+toDate);
            
            
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            PreparedStatement ps_EmpIds = con.executeStatement(SQLMaster.SELECT_EMPID_FOR_VALIDATION_REPORT_SQL_QUERY,ht);  
            ResultSet rs_EmpIds = ps_EmpIds.executeQuery();
            
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(toDate);
            gcal.add(GregorianCalendar.DATE,1);
            toDate = gcal.getTime();
            while(rs_EmpIds.next())
            {
                MonthlyReportDetails objMonthlyReportDetails = new MonthlyReportDetails();
                empId = rs_EmpIds.getInt("EMP_ID");
                
                objMonthlyReportDetails.setEmpName(rs_EmpIds.getString("EMP_NAME"));
                //    System.out.println(objMonthlyReportDetails.getEmpName());
                Date tempDate;
                ht.put("EMP_ID",new Integer(empId));
                
                DtyHrsDetails  objDtyHrsDetails = new DtyHrsDetails();
               
                OtHrsDetails objOtHrsDetails = new OtHrsDetails();
                
                
                ResultSet rs = null;
                PreparedStatement ps = null;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    if(BuildConfig.DMODE)
                        System.out.println("tedate "+tempDate);
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_MONTHLYDETAILS_FOR_VALIDATION_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String dtVal = "0.0";
                    String otVal = "0.0";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                    {
                        dtVal = rs.getDouble("DTHRS")+"";
                        if(BuildConfig.DMODE)
                            System.out.println("val :"+dtVal);
                        otVal = rs.getDouble("OTHRS")+"";
                        
                        
                    }
                    rs.close();
                    ps.close();
                    
                    if(dtVal.equals("0.0"))
                    {
                        int day = g.get(GregorianCalendar.DAY_OF_WEEK);
                        if(BuildConfig.DMODE)
                            System.out.println("day :"+day);
                        
                        if(day != 1)
                        {
                            dtVal =   "L";
                        }
                        else
                            dtVal = "Sun";
                    }
                    
                    if(!(dtVal.equals("L")||dtVal.equals("Sun")))
                        if(new Double(dtVal).doubleValue() >= new Double(otVal).doubleValue())
                        {
                            dtVal = "";
                            otVal = "";
                        }
                        
                        if(dtVal.equals("L"))
                            if(otVal.equals("0.0"))
                            {
                                otVal = "";
                                dtVal = "";
                            }    
                            if(dtVal.equals("Sun"))
                            {
                                otVal = "";
                                dtVal = "";
                            }    
                    switch(i)
                    {
                    case 1:
                        objDtyHrsDetails.setDay1(dtVal);
                        objOtHrsDetails.setDay1(otVal);
                        break;
                    case 2:
                        objDtyHrsDetails.setDay2(dtVal);
                        objOtHrsDetails.setDay2(otVal);
                        break;
                    case 3:
                        objDtyHrsDetails.setDay3(dtVal);
                        objOtHrsDetails.setDay3(otVal);
                        break;
                    case 4:
                        objDtyHrsDetails.setDay4(dtVal);
                        objOtHrsDetails.setDay4(otVal);
                        break;
                    case 5:
                        objDtyHrsDetails.setDay5(dtVal);
                        objOtHrsDetails.setDay5(otVal);
                        break;
                    case 6:
                        objDtyHrsDetails.setDay6(dtVal);
                        objOtHrsDetails.setDay6(otVal);
                        break;
                    case 7:
                        objDtyHrsDetails.setDay7(dtVal);
                        objOtHrsDetails.setDay7(otVal);
                        break;
                    case 8:
                        objDtyHrsDetails.setDay8(dtVal);
                        objOtHrsDetails.setDay8(otVal);
                        break;
                    case 9:
                        objDtyHrsDetails.setDay9(dtVal);
                        objOtHrsDetails.setDay9(otVal);
                        break;
                    case 10:
                        objDtyHrsDetails.setDay10(dtVal);
                        objOtHrsDetails.setDay10(otVal);
                        break;
                    case 11:
                        objDtyHrsDetails.setDay11(dtVal);
                        objOtHrsDetails.setDay11(otVal);
                        break;
                    case 12:
                        objDtyHrsDetails.setDay12(dtVal);
                        objOtHrsDetails.setDay12(otVal);
                        break;
                    case 13:
                        objDtyHrsDetails.setDay13(dtVal);
                        objOtHrsDetails.setDay13(otVal);
                        break;
                    case 14:
                        objDtyHrsDetails.setDay14(dtVal);
                        objOtHrsDetails.setDay14(otVal);
                        break;
                    case 15:
                        objDtyHrsDetails.setDay15(dtVal);
                        objOtHrsDetails.setDay15(otVal);
                        break;
                    case 16:
                        objDtyHrsDetails.setDay16(dtVal);
                        objOtHrsDetails.setDay16(otVal);
                        break;
                    case 17:
                        objDtyHrsDetails.setDay17(dtVal);
                        objOtHrsDetails.setDay17(otVal);
                        break;
                    case 18:
                        objDtyHrsDetails.setDay18(dtVal);
                        objOtHrsDetails.setDay18(otVal);
                        break;
                    case 19:
                        objDtyHrsDetails.setDay19(dtVal);
                        objOtHrsDetails.setDay19(otVal);
                        break;
                    case 20:
                        objDtyHrsDetails.setDay20(dtVal);
                        objOtHrsDetails.setDay20(otVal);
                        break;
                        
                    case 21:
                        objDtyHrsDetails.setDay21(dtVal);
                        objOtHrsDetails.setDay21(otVal);
                        break;
                    case 22:
                        objDtyHrsDetails.setDay22(dtVal);
                        objOtHrsDetails.setDay22(otVal);
                        break;
                    case 23:
                        objDtyHrsDetails.setDay23(dtVal);
                        objOtHrsDetails.setDay23(otVal);
                        break;
                    case 24:
                        objDtyHrsDetails.setDay24(dtVal);
                        objOtHrsDetails.setDay24(otVal);
                        break;
                    case 25:
                        objDtyHrsDetails.setDay25(dtVal);
                        objOtHrsDetails.setDay25(otVal);
                        break;
                    case 26:
                        objDtyHrsDetails.setDay26(dtVal);
                        objOtHrsDetails.setDay26(otVal);
                        break;
                    case 27:
                        objDtyHrsDetails.setDay27(dtVal);
                        objOtHrsDetails.setDay27(otVal);
                        break;
                    case 28:
                        objDtyHrsDetails.setDay28(dtVal);
                        objOtHrsDetails.setDay28(otVal);
                        break;
                    case 29:
                        objDtyHrsDetails.setDay29(dtVal);
                        objOtHrsDetails.setDay29(otVal);
                        break;
                    case 30:
                        objDtyHrsDetails.setDay30(dtVal);
                        objOtHrsDetails.setDay30(otVal);
                        break;
                    case 31:
                        objDtyHrsDetails.setDay31(dtVal);
                        objOtHrsDetails.setDay31(otVal);
                        break;
                        
                    }
                                
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                }
                objMonthlyReportDetails.setObjDtyHrsDetails(objDtyHrsDetails);
                objMonthlyReportDetails.setObjOtHrsDetails(objOtHrsDetails);
                if(BuildConfig.DMODE)
                    System.out.println(objMonthlyReportDetails.getEmpName());
                vec_Result.addElement(objMonthlyReportDetails);
                
            }
            rs_EmpIds.close();
            ps_EmpIds.close();
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        objMonthlyReportDetailsList = new MonthlyReportDetails[vec_Result.size()];
        vec_Result.copyInto(objMonthlyReportDetailsList);
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH MONTHLY REPORTS ENDS");
        }
        logger.info("FETCH MONTHLY REPORTS ENDS");
        
        return objMonthlyReportDetailsList;
        
        
    }
   
    
    public MonthlyReportDetails[] fetchMonthlyReportsDetails(int month,int year) throws SQLException, ReportsException
    {
        
        MonthlyReportDetails[] objMonthlyReportDetailsList;
        DBConnection con = null;
        int empId = 0;
        Hashtable ht = new Hashtable();
        Vector vec_Result = new Vector();
        Date fromDate = null;
        Date toDate = null;
        int days = 0;
        try
        {
            
            if(BuildConfig.DMODE)
            {
                System.out.println("FETCH MONTHLY REPORTS STARTS");
            }
            logger.info("FETCH MONTHLY REPORTS STARTS");
            
            
            //          to get from date and to date of the selected month..
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            fromDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("fdate "+fromDate);
            days = gc.getActualMaximum(GregorianCalendar.DATE);
            gc.set(GregorianCalendar.DATE,days);
            toDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("tdate "+toDate);
            
            
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            PreparedStatement ps_EmpIds = con.executeStatement(SQLMaster.SELECT_EMPID_FOR_REPORT_SQL_QUERY,ht);  
            ResultSet rs_EmpIds = ps_EmpIds.executeQuery();
            
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(toDate);
            gcal.add(GregorianCalendar.DATE,1);
            toDate = gcal.getTime();
            while(rs_EmpIds.next())
            {
                MonthlyReportDetails objMonthlyReportDetails = new MonthlyReportDetails();
                empId = rs_EmpIds.getInt("EMP_ID");
                
                objMonthlyReportDetails.setEmpName(rs_EmpIds.getString("EMP_NAME"));
                //    System.out.println(objMonthlyReportDetails.getEmpName());
                Date tempDate;
                ht.put("EMP_ID",new Integer(empId));
                
                DtyHrsDetails  objDtyHrsDetails = new DtyHrsDetails();
               
                OtHrsDetails objOtHrsDetails = new OtHrsDetails();
                
                double dtTotHrs = 0;
                double otTotHrs = 0;
                
                ResultSet rs = null;
                PreparedStatement ps = null;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    if(BuildConfig.DMODE)
                        System.out.println("tedate "+tempDate);
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_MONTHLYDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String dtVal = "0.0";
                    String otVal = "0.0";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                    {
                        dtVal = rs.getDouble("DTHRS")+"";
                        if(BuildConfig.DMODE)
                            System.out.println("val :"+dtVal);
                        
                        otVal = rs.getDouble("OTHRS")+"";
                        
                        
                    }
                    rs.close();
                    ps.close();
                    
                    if(dtVal.equals("0.0"))
                    {
                        int day = g.get(GregorianCalendar.DAY_OF_WEEK);
                        if(BuildConfig.DMODE)
                            System.out.println("day :"+day);
                        
                        if(day != 1)
                        {
                            dtVal =   "L";
                        }
                        else
                            dtVal = "Sun";
                    }
                    else
                    {
                        dtTotHrs = dtTotHrs + Double.parseDouble(dtVal);
                    }
                    
                    if(!otVal.equals("0.0"))
                        otTotHrs = otTotHrs + Double.parseDouble(otVal);
                    else
                        otVal = "";
                    
                    switch(i)
                    {
                    case 1:
                        objDtyHrsDetails.setDay1(dtVal);
                        objOtHrsDetails.setDay1(otVal);
                        break;
                    case 2:
                        objDtyHrsDetails.setDay2(dtVal);
                        objOtHrsDetails.setDay2(otVal);
                        break;
                    case 3:
                        objDtyHrsDetails.setDay3(dtVal);
                        objOtHrsDetails.setDay3(otVal);
                        break;
                    case 4:
                        objDtyHrsDetails.setDay4(dtVal);
                        objOtHrsDetails.setDay4(otVal);
                        break;
                    case 5:
                        objDtyHrsDetails.setDay5(dtVal);
                        objOtHrsDetails.setDay5(otVal);
                        break;
                    case 6:
                        objDtyHrsDetails.setDay6(dtVal);
                        objOtHrsDetails.setDay6(otVal);
                        break;
                    case 7:
                        objDtyHrsDetails.setDay7(dtVal);
                        objOtHrsDetails.setDay7(otVal);
                        break;
                    case 8:
                        objDtyHrsDetails.setDay8(dtVal);
                        objOtHrsDetails.setDay8(otVal);
                        break;
                    case 9:
                        objDtyHrsDetails.setDay9(dtVal);
                        objOtHrsDetails.setDay9(otVal);
                        break;
                    case 10:
                        objDtyHrsDetails.setDay10(dtVal);
                        objOtHrsDetails.setDay10(otVal);
                        break;
                    case 11:
                        objDtyHrsDetails.setDay11(dtVal);
                        objOtHrsDetails.setDay11(otVal);
                        break;
                    case 12:
                        objDtyHrsDetails.setDay12(dtVal);
                        objOtHrsDetails.setDay12(otVal);
                        break;
                    case 13:
                        objDtyHrsDetails.setDay13(dtVal);
                        objOtHrsDetails.setDay13(otVal);
                        break;
                    case 14:
                        objDtyHrsDetails.setDay14(dtVal);
                        objOtHrsDetails.setDay14(otVal);
                        break;
                    case 15:
                        objDtyHrsDetails.setDay15(dtVal);
                        objOtHrsDetails.setDay15(otVal);
                        break;
                    case 16:
                        objDtyHrsDetails.setDay16(dtVal);
                        objOtHrsDetails.setDay16(otVal);
                        break;
                    case 17:
                        objDtyHrsDetails.setDay17(dtVal);
                        objOtHrsDetails.setDay17(otVal);
                        break;
                    case 18:
                        objDtyHrsDetails.setDay18(dtVal);
                        objOtHrsDetails.setDay18(otVal);
                        break;
                    case 19:
                        objDtyHrsDetails.setDay19(dtVal);
                        objOtHrsDetails.setDay19(otVal);
                        break;
                    case 20:
                        objDtyHrsDetails.setDay20(dtVal);
                        objOtHrsDetails.setDay20(otVal);
                        break;
                        
                    case 21:
                        objDtyHrsDetails.setDay21(dtVal);
                        objOtHrsDetails.setDay21(otVal);
                        break;
                    case 22:
                        objDtyHrsDetails.setDay22(dtVal);
                        objOtHrsDetails.setDay22(otVal);
                        break;
                    case 23:
                        objDtyHrsDetails.setDay23(dtVal);
                        objOtHrsDetails.setDay23(otVal);
                        break;
                    case 24:
                        objDtyHrsDetails.setDay24(dtVal);
                        objOtHrsDetails.setDay24(otVal);
                        break;
                    case 25:
                        objDtyHrsDetails.setDay25(dtVal);
                        objOtHrsDetails.setDay25(otVal);
                        break;
                    case 26:
                        objDtyHrsDetails.setDay26(dtVal);
                        objOtHrsDetails.setDay26(otVal);
                        break;
                    case 27:
                        objDtyHrsDetails.setDay27(dtVal);
                        objOtHrsDetails.setDay27(otVal);
                        break;
                    case 28:
                        objDtyHrsDetails.setDay28(dtVal);
                        objOtHrsDetails.setDay28(otVal);
                        break;
                    case 29:
                        objDtyHrsDetails.setDay29(dtVal);
                        objOtHrsDetails.setDay29(otVal);
                        break;
                    case 30:
                        objDtyHrsDetails.setDay30(dtVal);
                        objOtHrsDetails.setDay30(otVal);
                        break;
                    case 31:
                        objDtyHrsDetails.setDay31(dtVal);
                        objOtHrsDetails.setDay31(otVal);
                        break;
                        
                    }
                                
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                }
                objDtyHrsDetails.setTotHrs(dtTotHrs+"");	    
                objOtHrsDetails.setTotHrs(otTotHrs+"");
                
                objMonthlyReportDetails.setObjDtyHrsDetails(objDtyHrsDetails);
                objMonthlyReportDetails.setObjOtHrsDetails(objOtHrsDetails);
                if(BuildConfig.DMODE)
                    System.out.println(objMonthlyReportDetails.getEmpName());
                vec_Result.addElement(objMonthlyReportDetails);
                
            }
            rs_EmpIds.close();
            ps_EmpIds.close();
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        objMonthlyReportDetailsList = new MonthlyReportDetails[vec_Result.size()];
        vec_Result.copyInto(objMonthlyReportDetailsList);
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH MONTHLY REPORTS ENDS");
        }
        logger.info("FETCH MONTHLY REPORTS ENDS");
        
        return objMonthlyReportDetailsList;
        
        
    }
    //This method is not optimized.
    /*public MonthlyReportDetails[] fetchMonthlyReportsDetails(int month,int year) throws SQLException, ReportsException
    {
        
        MonthlyReportDetails[] objMonthlyReportDetailsList;
        DBConnection con = null;
        int empId = 0;
        Hashtable ht = new Hashtable();
        Vector vec_Result = new Vector();
        Date fromDate = null;
        Date toDate = null;
        int days = 0;
        try
        {
            
            if(BuildConfig.DMODE)
            {
                System.out.println("FETCH MONTHLY REPORTS STARTS");
            }
            logger.info("FETCH MONTHLY REPORTS STARTS");
            
            
            //          to get from date and to date of the selected month..
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            fromDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("fdate "+fromDate);
            days = gc.getActualMaximum(GregorianCalendar.DATE);
            gc.set(GregorianCalendar.DATE,days);
            toDate = gc.getTime();
            if(BuildConfig.DMODE)
                System.out.println("tdate "+toDate);
            
            
            con = DBConnectionFactory.getConnection();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            PreparedStatement ps_EmpIds = con.executeStatement(SQLMaster.SELECT_EMPID_FOR_REPORT_SQL_QUERY,ht);  
            ResultSet rs_EmpIds = ps_EmpIds.executeQuery();
            
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(toDate);
            gcal.add(GregorianCalendar.DATE,1);
            toDate = gcal.getTime();
            while(rs_EmpIds.next())
            {
                MonthlyReportDetails objMonthlyReportDetails = new MonthlyReportDetails();
                empId = rs_EmpIds.getInt("EMP_ID");
                
                objMonthlyReportDetails.setEmpName(rs_EmpIds.getString("EMP_NAME"));
                //    System.out.println(objMonthlyReportDetails.getEmpName());
                Date tempDate;
                ht.put("EMP_ID",new Integer(empId));
                
                DtyHrsDetails  objDtyHrsDetails = new DtyHrsDetails();
                double totHrs = 0;
                ResultSet rs = null;
                PreparedStatement ps = null;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    if(BuildConfig.DMODE)
                        System.out.println("tedate "+tempDate);
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_MONTHLYDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String val = "0.0";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                    {
                        val = rs.getDouble("DTHRS")+"";
                        if(BuildConfig.DMODE)
                            System.out.println("val :"+val);
                    }
                    rs.close();
                    ps.close();
                    
                    if(val.equals("0.0"))
                    {
                        int day = g.get(GregorianCalendar.DAY_OF_WEEK);
                        if(BuildConfig.DMODE)
                            System.out.println("day :"+day);
                        
                        if(day != 1)
                        {
                            val =   "L";
                        }
                        else
                            val = "Sun";
                    }
                    else
                    {
                        totHrs = totHrs + Double.parseDouble(val);
                    }
                    
                    switch(i)
                    {
                    case 1:
                        objDtyHrsDetails.setDay1(val);
                        break;
                    case 2:
                        objDtyHrsDetails.setDay2(val);
                        break;
                    case 3:
                        objDtyHrsDetails.setDay3(val);
                        break;
                    case 4:
                        objDtyHrsDetails.setDay4(val);
                        break;
                    case 5:
                        objDtyHrsDetails.setDay5(val);
                        break;
                    case 6:
                        objDtyHrsDetails.setDay6(val);
                        break;
                    case 7:
                        objDtyHrsDetails.setDay7(val);
                        break;
                    case 8:
                        objDtyHrsDetails.setDay8(val);
                        break;
                    case 9:
                        objDtyHrsDetails.setDay9(val);
                        break;
                    case 10:
                        objDtyHrsDetails.setDay10(val);
                        break;
                    case 11:
                        objDtyHrsDetails.setDay11(val);
                        break;
                    case 12:
                        objDtyHrsDetails.setDay12(val);
                        break;
                    case 13:
                        objDtyHrsDetails.setDay13(val);
                        break;
                    case 14:
                        objDtyHrsDetails.setDay14(val);
                        break;
                    case 15:
                        objDtyHrsDetails.setDay15(val);
                        break;
                    case 16:
                        objDtyHrsDetails.setDay16(val);
                        break;
                    case 17:
                        objDtyHrsDetails.setDay17(val);
                        break;
                    case 18:
                        objDtyHrsDetails.setDay18(val);
                        break;
                    case 19:
                        objDtyHrsDetails.setDay19(val);
                        break;
                    case 20:
                        objDtyHrsDetails.setDay20(val);
                        break;
                        
                    case 21:
                        objDtyHrsDetails.setDay21(val);
                        break;
                    case 22:
                        objDtyHrsDetails.setDay22(val);
                        break;
                    case 23:
                        objDtyHrsDetails.setDay23(val);
                        break;
                    case 24:
                        objDtyHrsDetails.setDay24(val);
                        break;
                    case 25:
                        objDtyHrsDetails.setDay25(val);
                        break;
                    case 26:
                        objDtyHrsDetails.setDay26(val);
                        break;
                    case 27:
                        objDtyHrsDetails.setDay27(val);
                        break;
                    case 28:
                        objDtyHrsDetails.setDay28(val);
                        break;
                    case 29:
                        objDtyHrsDetails.setDay29(val);
                        break;
                    case 30:
                        objDtyHrsDetails.setDay30(val);
                        break;
                    case 31:
                        objDtyHrsDetails.setDay31(val);
                        break;
                        
                    }
                    
                    
                    
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                }
                objDtyHrsDetails.setTotHrs(totHrs+"");	    
                
                OtHrsDetails objOtHrsDetails = new OtHrsDetails();
                totHrs = 0;
                for(tempDate = fromDate; tempDate.before(toDate); )
                {
                    ht.put("PROD_CRNT_DATE",tempDate);
                    ps = con.executeStatement(SQLMaster.SELECT_MONTHLYDETAILS_FOR_REPORT_SQL_QUERY,ht);
                    rs = ps.executeQuery();
                    GregorianCalendar g = new GregorianCalendar();
                    g.setTime(tempDate);
                    String val = "0.0";
                    int i = g.get(GregorianCalendar.DATE);
                    if(rs.next())
                        val = rs.getDouble("OTHRS")+"";
                    rs.close();
                    ps.close();
                    if(!val.equals("0.0"))
                        totHrs = totHrs + Double.parseDouble(val);
                    else
                        val = "";
                    switch(i)
                    {
                    
                    
                    case 1:
                        objOtHrsDetails.setDay1(val);
                        break;
                    case 2:
                        objOtHrsDetails.setDay2(val);
                        break;
                    case 3:
                        objOtHrsDetails.setDay3(val);
                        break;
                    case 4:
                        objOtHrsDetails.setDay4(val);
                        break;
                    case 5:
                        objOtHrsDetails.setDay5(val);
                        break;
                    case 6:
                        objOtHrsDetails.setDay6(val);
                        break;
                    case 7:
                        objOtHrsDetails.setDay7(val);
                        break;
                    case 8:
                        objOtHrsDetails.setDay8(val);
                        break;
                    case 9:
                        objOtHrsDetails.setDay9(val);
                        break;
                    case 10:
                        objOtHrsDetails.setDay10(val);
                        break;
                    case 11:
                        objOtHrsDetails.setDay11(val);
                        break;
                    case 12:
                        objOtHrsDetails.setDay12(val);
                        break;
                    case 13:
                        objOtHrsDetails.setDay13(val);
                        break;
                    case 14:
                        objOtHrsDetails.setDay14(val);
                        break;
                    case 15:
                        objOtHrsDetails.setDay15(val);
                        break;
                    case 16:
                        objOtHrsDetails.setDay16(val);
                        break;
                    case 17:
                        objOtHrsDetails.setDay17(val);
                        break;
                    case 18:
                        objOtHrsDetails.setDay18(val);
                        break;
                    case 19:
                        objOtHrsDetails.setDay19(val);
                        break;
                    case 20:
                        objOtHrsDetails.setDay20(val);
                        break;
                        
                    case 21:
                        objOtHrsDetails.setDay21(val);
                        break;
                    case 22:
                        objOtHrsDetails.setDay22(val);
                        break;
                    case 23:
                        objOtHrsDetails.setDay23(val);
                        break;
                    case 24:
                        objOtHrsDetails.setDay24(val);
                        break;
                    case 25:
                        objOtHrsDetails.setDay25(val);
                        break;
                    case 26:
                        objOtHrsDetails.setDay26(val);
                        break;
                    case 27:
                        objOtHrsDetails.setDay27(val);
                        break;
                    case 28:
                        objOtHrsDetails.setDay28(val);
                        break;
                    case 29:
                        objOtHrsDetails.setDay29(val);
                        break;
                    case 30:
                        objOtHrsDetails.setDay30(val);
                        break;
                    case 31:
                        objOtHrsDetails.setDay31(val);
                        break;
                        
                    }
                    
                    
                    
                    GregorianCalendar gc1 = new GregorianCalendar();
                    gc1.setTime(tempDate);
                    gc1.add(GregorianCalendar.DATE,1);
                    tempDate = gc1.getTime();
                }
                objOtHrsDetails.setTotHrs(totHrs+"");
                
                objMonthlyReportDetails.setObjDtyHrsDetails(objDtyHrsDetails);
                objMonthlyReportDetails.setObjOtHrsDetails(objOtHrsDetails);
                if(BuildConfig.DMODE)
                    System.out.println(objMonthlyReportDetails.getEmpName());
                vec_Result.addElement(objMonthlyReportDetails);
                
            }
            rs_EmpIds.close();
            ps_EmpIds.close();
            
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        objMonthlyReportDetailsList = new MonthlyReportDetails[vec_Result.size()];
        vec_Result.copyInto(objMonthlyReportDetailsList);
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH MONTHLY REPORTS ENDS");
        }
        logger.info("FETCH MONTHLY REPORTS ENDS");
        
        return objMonthlyReportDetailsList;
        
        
    }*/
   
    public MachineUtilizationReportDetails[] fetchMachineUtilization(Date fromDate,Date toDate)throws SQLException,ReportsException
    {
        
        DBConnection con = null;
        String mcCode ="";
        String mcName ="";
        double workHrs = 0;
        double breakHrs = 0;
        double idleHrs = 0;
        double utiHrs = 0;
        double utiPer = 0;
        double breakPer = 0;
        double idlePer = 0;
        Hashtable ht = new Hashtable();
        
        MachineUtilizationReportDetails[] machineUtilizationReportDetailsList;
        Vector v_Result = new Vector();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String n = "";
        
        con = DBConnectionFactory.getConnection();
        ht.put("FROM_DATE",fromDate);
        ht.put("TO_DATE",toDate);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH MACHINE UTILIZATION REPORTS STARTS");
        }
        logger.info("FETCH MACHINE UTILIZATION REPORTS STARTS");
        
        
        try
        {
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_MACHINE_DETAILS_SQL_QUERY); 
            ResultSet rs = ps.executeQuery();
            ResultSet rsProdHrs = null;
            PreparedStatement psProdHrs = null;
            while(rs.next())
            {
                MachineUtilizationReportDetails objMachineUtilDetails = new MachineUtilizationReportDetails();
                mcCode = rs.getString("MC_CDE");
                mcName = rs.getString("MC_NAME");	
                ht.put("MC_CDE",mcCode);
                psProdHrs = con.executeStatement(SQLMaster.SELECT_MACHINE_DETAILS_WITH_PROD_HOURS_SQL_QUERY,ht);
                rsProdHrs = psProdHrs.executeQuery();
                if(rsProdHrs.next())
                {
                    utiHrs = rsProdHrs.getDouble("PROD_TOT_HRS");
                    
                }
                rsProdHrs.close();
                psProdHrs.close();
                if(BuildConfig.DMODE)
                    System.out.println("utihrs"+utiHrs);
                rsProdHrs.close();
                PreparedStatement psNprodIdleHrs = con.executeStatement(SQLMaster.SELECT_MACHINE_DETAILS_WITH_NPROD_IDLE_HOURS_SQL_QUERY,ht); 
                ResultSet rsNprodIdleHrs = psNprodIdleHrs.executeQuery(); 
                if(rsNprodIdleHrs.next())
                {
                    idleHrs = rsNprodIdleHrs.getDouble("NPROD_TOT_HRS");
                    
                }
                rsNprodIdleHrs.close();
                psNprodIdleHrs.close();
                
                PreparedStatement psNprodBrkDwnHrs = con.executeStatement(SQLMaster.SELECT_MACHINE_DETAILS_WITH_NPROD_BRKDWN_HOURS_SQL_QUERY,ht); 
                ResultSet rsNprodBrkDwnHrs = psNprodBrkDwnHrs.executeQuery(); 
                if(rsNprodBrkDwnHrs.next())
                {
                    breakHrs = rsNprodBrkDwnHrs.getDouble("NPROD_TOT_HRS");
                    
                }
                rsNprodBrkDwnHrs.close();
                psNprodBrkDwnHrs.close();
                workHrs = utiHrs + idleHrs + breakHrs;
                
                if(workHrs != 0)
                {
                    utiPer = (utiHrs * 100)/workHrs;
                    breakPer = (breakHrs * 100)/workHrs;
                    idlePer = (idleHrs * 100)/workHrs;
                    n = nf.format(utiPer);
                    utiPer = Double.parseDouble(n);
                    
                    n = nf.format(breakPer);
                    breakPer = Double.parseDouble(n);
                    
                    n = nf.format(idlePer);
                    idlePer = Double.parseDouble(n);
                }
                else
                {
                    utiPer = 0;
                    breakPer = 0;
                    idlePer = 0;
                }
                
                objMachineUtilDetails.setMcCode(mcCode);
                objMachineUtilDetails.setMcName(mcName);
                objMachineUtilDetails.setWorkHrs(""+workHrs);
                objMachineUtilDetails.setBreakHrs(""+breakHrs);
                objMachineUtilDetails.setIdleHrs(""+idleHrs);
                objMachineUtilDetails.setUtiHrs(""+utiHrs);
                objMachineUtilDetails.setUtiPer(utiPer+" %");
                objMachineUtilDetails.setBreakPer(breakPer+" %");
                objMachineUtilDetails.setIdlePer(idlePer+" %");
                v_Result.addElement(objMachineUtilDetails);
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e)
        {
            
            logger.error("GENERAL SQL ERROR",e);
            
            if(BuildConfig.DMODE)
            {
                e.printStackTrace();
                System.out.println("GENERAL EXCEPTION");
            }	
            throw new ReportsException("EMEC000","GENERAL SQL ERROR",e.toString());
            
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
        
        machineUtilizationReportDetailsList = new MachineUtilizationReportDetails[v_Result.size()];
        v_Result.copyInto(machineUtilizationReportDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("FETCH MACHINE UTILIZATION REPORTS ENDS");
        }
        logger.info("FETCH MACHINE UTILIZATION REPORTS ENDS");
        
        return machineUtilizationReportDetailsList;
    }
    
    public TopSheetDetails[] fetchTopSheetDetails() throws ReportsException, SQLException
    {
        TopSheetDetails[] topSheetDetailsList = null;
        Hashtable ht = new Hashtable();
        int custId = 0;
        String custName = "";
        float vtlHrs = 0;
        float hbHrs = 0;
        float drlHrs = 0;
        int jbId = 0;
        DBConnection con = null;
        
        if(BuildConfig.DMODE)
        {
            System.out.println("TOP SHEET DETAILS REPORTS STARTS");
        }
        
        logger.info("TOP SHEET DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_ALL_CUSTOMER_NAME); 
            ResultSet rs = ps.executeQuery();
            Vector vec_Result = new Vector();
            ResultSet rs_JobDet_Sel = null;
            PreparedStatement ps_JobDet_Sel = null;
            while(rs.next())
            {
                
                vtlHrs = 0;
                hbHrs = 0;
                drlHrs = 0;
                custId = rs.getInt("CUST_ID");
                custName = rs.getString("CUST_NAME");
                ht.put("CUST_ID",new Integer(custId));
                ps_JobDet_Sel = con.executeStatement(SQLMaster.SELECT_JOB_DETAILS_SQL_QUERY,ht);
                rs_JobDet_Sel = ps_JobDet_Sel.executeQuery();
                ResultSet rs_Hrs_Sel = null;
                PreparedStatement ps_Hrs_Sel = null;
                while(rs_JobDet_Sel.next())
                {
                    
                    TopSheetDetails objTopSheetDetails = new TopSheetDetails();
                    objTopSheetDetails.setCustId(custId);
                    objTopSheetDetails.setCustName(custName);
                    objTopSheetDetails.setJobName(rs_JobDet_Sel.getString("JB_NAME"));
                    objTopSheetDetails.setDrwgNo(rs_JobDet_Sel.getString("JB_DRWG_NO"));
                    jbId = rs_JobDet_Sel.getInt("JB_ID");
                    ht.put("JB_ID",new Integer(jbId));                    
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_VTL_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        vtlHrs = rs_Hrs_Sel.getFloat("VTL_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    objTopSheetDetails.setVtlHrs(vtlHrs);
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_HB_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        hbHrs = rs_Hrs_Sel.getFloat("HB_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    objTopSheetDetails.setHbHrs(hbHrs);
                    ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRL_HRS_SQL_QUERY,ht);
                    rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                    if(rs_Hrs_Sel.next())
                    {
                        drlHrs = rs_Hrs_Sel.getFloat("DRL_HRS");
                    }
                    rs_Hrs_Sel.close();
                    ps_Hrs_Sel.close();
                    
                    objTopSheetDetails.setDrlHrs(drlHrs);
                    vec_Result.addElement(objTopSheetDetails);
                }
                rs_JobDet_Sel.close();
                ps_JobDet_Sel.close();
                
            }
            rs.close();
            ps.close();
            topSheetDetailsList = new TopSheetDetails[vec_Result.size()];
            vec_Result.copyInto(topSheetDetailsList);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        if(BuildConfig.DMODE)
        {
            System.out.println("TOP SHEET DETAILS REPORTS ENDS");
        }
        logger.info("TOP SHEET DETAILS REPORTS ENDS");
        
        return topSheetDetailsList;
    }
    
    /*public MonthlyPercentageDetails[] fetchMonthlyPercentageOfMachine(String mcCde,int month,int year) throws ParseException, ReportsException, SQLException
    {
        
        DBConnection con = null;
        int days = 0;
        MonthlyPercentageDetails[] monthlyPercentageDetailsList;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        Hashtable ht = new Hashtable();
        String n="";
        Date fromDate = null;
        Date toDate = null;
        
        
        if(BuildConfig.DMODE)
        {
            System.out.println("MONTHLY PERCENTAGE DETAILS REPORTS STARTS");
        }
        logger.info("MONTHLY PERCENTAGE DETAILS REPORTS STARTS");
        
        
        //to get from date and to date of the selected month..
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.MONTH,month-1);
        gc.set(GregorianCalendar.YEAR,year);
        gc.set(GregorianCalendar.DATE,1);
        fromDate = gc.getTime();
        if(BuildConfig.DMODE)
            System.out.println("fdate "+fromDate);
        days = gc.getActualMaximum(GregorianCalendar.DATE);
        gc.set(GregorianCalendar.DATE,days);
        toDate = gc.getTime();
        if(BuildConfig.DMODE)
            System.out.println("tdate "+toDate);
        
        double prodAvailHrs = 0;
        double nprodAvailHrs = 0;
        
        double mcAvailHrs = 0;
        double mcTotHrs = days * 21;
        
        double brkDwnMechHrs = 0;
        double brkDwnElecHrs = 0;
        double totbrkDwnHrs = 0;
        double brkDwnMechPer = 0;
        double brkDwnElecPer = 0;
        double totbrkDwnPer = 0;
        
        double totProdHrs = 0;
        double totProdPer = 0;
        
        double idlInstHrs = 0;
        double idlManHrs = 0;
        double idlJobHrs = 0;
        double idlDnoHrs = 0;
        double idlPccHrs = 0;
        double idlToolsHrs = 0;
        double idlGaugeHrs = 0;
        double idlOtherHrs = 0;
        double totIdleHrs = 0;
        
        double idlInstPer = 0;
        double idlManPer = 0;
        double idlJobPer = 0;
        double idlDnoPer = 0;
        double idlPccPer = 0;
        double idlToolsPer = 0;
        double idlGaugePer = 0;
        double idlOtherPer = 0;
        double totIdlePer = 0;
        
        
        double effProdHrs = 0;
        double perofProd = 0;
        
        
        
        Vector v_Result = new Vector();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            ht.put("MC_CDE",mcCde);
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            
            MonthlyPercentageDetails objMonthlyPercentageDetails = new MonthlyPercentageDetails();
            PreparedStatement ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PROD_MC_HRS_SQL_QUERY,ht); 
            ResultSet rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            
            
            if(rs_Hrs_Sel.next())
            {
                prodAvailHrs = rs_Hrs_Sel.getDouble("PROD_TOT_HRS");
                
            }
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_RADL_MC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            
            if(rs_Hrs_Sel.next())
            {
                prodAvailHrs = prodAvailHrs +  rs_Hrs_Sel.getDouble("RADL_TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_NPROD_MC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                nprodAvailHrs = rs_Hrs_Sel.getDouble("NPROD_TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            mcAvailHrs = prodAvailHrs + nprodAvailHrs;
            
            if(mcAvailHrs > 0)
            {
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnMechHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnElecHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                
                totbrkDwnHrs = brkDwnMechHrs + brkDwnElecHrs;
                
                brkDwnMechPer = (brkDwnMechHrs / mcAvailHrs) * 100;
                if(brkDwnMechPer > 0)
                {
                    n = nf.format(brkDwnMechPer);
                    brkDwnMechPer = Double.parseDouble(n);
                }
                
                brkDwnElecPer = (brkDwnElecHrs / mcAvailHrs) * 100;
                if(brkDwnElecPer > 0)
                {
                    n = nf.format(brkDwnElecPer);
                    brkDwnElecPer = Double.parseDouble(n);
                }
                
                totbrkDwnPer = (totbrkDwnHrs / mcAvailHrs) * 100;
                if(totbrkDwnPer > 0)
                {
                    n = nf.format(totbrkDwnPer);
                    totbrkDwnPer = Double.parseDouble(n);
                }
                
                
                totProdHrs = mcAvailHrs - totbrkDwnHrs;
                totProdPer = (totProdHrs / mcAvailHrs) * 100;
                if(totProdPer > 0)
                {
                    n = nf.format(totProdPer);
                    totProdPer = Double.parseDouble(n);
                }
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlInstHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlManHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlJobHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlDnoHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlPccHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlToolsHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlGaugeHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlOtherHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                
                totIdleHrs = idlInstHrs + idlManHrs + idlJobHrs + idlDnoHrs + idlPccHrs + idlToolsHrs + idlGaugeHrs + idlOtherHrs;
                
                idlInstPer = (idlInstHrs / totProdHrs) * 100;
                if(idlInstPer > 0)
                {
                    n = nf.format(idlInstPer);
                    idlInstPer = Double.parseDouble(n);
                }
                
                idlManPer = (idlManHrs / totProdHrs) * 100;
                if(idlManPer > 0)
                {
                    n = nf.format(idlManPer);
                    idlManPer = Double.parseDouble(n);
                }
                
                idlJobPer = (idlJobHrs / totProdHrs) * 100;
                if(idlJobPer > 0)
                {
                    n = nf.format(idlJobPer);
                    idlJobPer = Double.parseDouble(n);
                }
                
                idlDnoPer = (idlDnoHrs / totProdHrs) * 100;
                if(idlDnoPer > 0)
                {
                    n = nf.format(idlDnoPer);
                    idlDnoPer = Double.parseDouble(n);
                }
                
                idlPccPer = (idlPccHrs / totProdHrs) * 100;
                if(idlPccPer > 0)
                {
                    n = nf.format(idlPccPer);
                    idlPccPer = Double.parseDouble(n);
                }
                
                idlToolsPer = (idlToolsHrs / totProdHrs) * 100;
                if(idlToolsPer > 0)
                {
                    n = nf.format(idlToolsPer);
                    idlToolsPer = Double.parseDouble(n);
                }
                
                idlGaugePer = (idlGaugeHrs / totProdHrs) * 100;
                if(idlGaugePer > 0)
                {
                    n = nf.format(idlGaugePer);
                    idlGaugePer = Double.parseDouble(n);
                }
                
                idlOtherPer = (idlOtherHrs / totProdHrs) * 100;
                if(idlOtherPer > 0)
                {
                    n = nf.format(idlOtherPer);
                    idlOtherPer = Double.parseDouble(n);
                }
                
                totIdlePer = (totIdleHrs / totProdHrs) * 100;
                if(totIdlePer > 0)
                {
                    n = nf.format(totIdlePer);
                    totIdlePer = Double.parseDouble(n);
                }
                
                effProdHrs = totProdHrs - totIdleHrs;
                
                perofProd = (effProdHrs / totProdHrs) * 100;
                if(perofProd > 0)
                {
                    n = nf.format(perofProd);
                    perofProd = Double.parseDouble(n);
                }
                
            }
            objMonthlyPercentageDetails.setMcTotHrs(mcTotHrs);
            objMonthlyPercentageDetails.setMcAvailHrs(mcAvailHrs);
            objMonthlyPercentageDetails.setBrkDwnMechHrs(brkDwnMechHrs);
            objMonthlyPercentageDetails.setBrkDwnElecHrs(brkDwnElecHrs);	
            objMonthlyPercentageDetails.setTotBrkDwnHrs(totbrkDwnHrs);
            objMonthlyPercentageDetails.setBrkDwnMechPer(brkDwnMechPer+" %");	
            objMonthlyPercentageDetails.setBrkDwnElecPer(brkDwnElecPer+" %");	
            objMonthlyPercentageDetails.setTotBrkDwnPer(totbrkDwnPer+" %");	
            objMonthlyPercentageDetails.setTotProdHrs(totProdHrs);
            objMonthlyPercentageDetails.setTotProdPer(totProdPer+" %");
            objMonthlyPercentageDetails.setIdlInstHrs(idlInstHrs);
            objMonthlyPercentageDetails.setIdlManHrs(idlManHrs);
            objMonthlyPercentageDetails.setIdlJobHrs(idlJobHrs);
            objMonthlyPercentageDetails.setIdlDnoHrs(idlDnoHrs);
            objMonthlyPercentageDetails.setIdlPccHrs(idlPccHrs);
            objMonthlyPercentageDetails.setIdlToolsHrs(idlToolsHrs);
            objMonthlyPercentageDetails.setIdlGaugeHrs(idlGaugeHrs);
            objMonthlyPercentageDetails.setIdlOtherHrs(idlOtherHrs);
            objMonthlyPercentageDetails.setTotIdleHrs(totIdleHrs);
            
            objMonthlyPercentageDetails.setIdlInstPer(idlInstPer+" %");
            objMonthlyPercentageDetails.setIdlManPer(idlManPer+" %");
            objMonthlyPercentageDetails.setIdlJobPer(idlJobPer+" %");
            objMonthlyPercentageDetails.setIdlDnoPer(idlDnoPer+" %");
            objMonthlyPercentageDetails.setIdlPccPer(idlPccPer+" %");
            objMonthlyPercentageDetails.setIdlToolsPer(idlToolsPer+" %");
            objMonthlyPercentageDetails.setIdlGaugePer(idlGaugePer+" %");
            objMonthlyPercentageDetails.setIdlOtherPer(idlOtherPer+" %");
            objMonthlyPercentageDetails.setTotIdlPer(totIdlePer+" %");
            
            objMonthlyPercentageDetails.setEffProdHrs(effProdHrs);
            objMonthlyPercentageDetails.setPerofProd(perofProd+" %");
            
            v_Result.addElement(objMonthlyPercentageDetails);
            
        }
        catch (Exception e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",e.toString());
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
        monthlyPercentageDetailsList = new MonthlyPercentageDetails[v_Result.size()];
        v_Result.copyInto(monthlyPercentageDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("MONTHLY PERCENTAGE DETAILS REPORTS ENDS");
        }
        logger.info("MONTHLY PERCENTAGE DETAILS REPORTS ENDS");
        
        return monthlyPercentageDetailsList;
        
    }
    */public MonthlyPercentageDetails[] fetchMonthlyPercentageOfMachine(String mcCde,int month,int year) throws ParseException, ReportsException, SQLException
    {
        
        DBConnection con = null;
        int days = 0;
        MonthlyPercentageDetails[] monthlyPercentageDetailsList;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        Hashtable ht = new Hashtable();
        String n="";
        Date fromDate = null;
        Date toDate = null;
        
        
        if(BuildConfig.DMODE)
        {
            System.out.println("MONTHLY PERCENTAGE DETAILS REPORTS STARTS");
        }
        logger.info("MONTHLY PERCENTAGE DETAILS REPORTS STARTS");
        
        
        //to get from date and to date of the selected month..
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.MONTH,month-1);
        gc.set(GregorianCalendar.YEAR,year);
        gc.set(GregorianCalendar.DATE,1);
        fromDate = gc.getTime();
        if(BuildConfig.DMODE)
            System.out.println("fdate "+fromDate);
        days = gc.getActualMaximum(GregorianCalendar.DATE);
        gc.set(GregorianCalendar.DATE,days);
        toDate = gc.getTime();
        if(BuildConfig.DMODE)
            System.out.println("tdate "+toDate);
        
        //double prodAvailHrs = 0;
        //double nprodAvailHrs = 0;
        
        double mcAvailHrs = 0;
        //double mcTotHrs = days * 21;
        
        double brkDwnMechHrs = 0;
        double brkDwnElecHrs = 0;
        double totbrkDwnHrs = 0;
        double brkDwnMechPer = 0;
        double brkDwnElecPer = 0;
        double totbrkDwnPer = 0;
        
        double totProdHrs = 0;
        //double totProdPer = 0;
        
        double idlInstHrs = 0;
        double idlManHrs = 0;
        double idlJobHrs = 0;
        double idlDnoHrs = 0;
        double idlPccHrs = 0;
        double idlToolsHrs = 0;
        double idlGaugeHrs = 0;
        double idlOtherHrs = 0;
        double totIdleHrs = 0;
        
        double idlInstPer = 0;
        double idlManPer = 0;
        double idlJobPer = 0;
        double idlDnoPer = 0;
        double idlPccPer = 0;
        double idlToolsPer = 0;
        double idlGaugePer = 0;
        double idlOtherPer = 0;
        double totIdlePer = 0;
        
        
        double perofProd = 0;
        
        
        
        Vector v_Result = new Vector();
        
        try
        {
            con = DBConnectionFactory.getConnection();
            ht.put("MC_CDE",mcCde);
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            
            MonthlyPercentageDetails objMonthlyPercentageDetails = new MonthlyPercentageDetails();
            PreparedStatement ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PROD_MC_HRS_SQL_QUERY,ht); 
            ResultSet rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            
            
            if(rs_Hrs_Sel.next())
            {
                totProdHrs = rs_Hrs_Sel.getDouble("PROD_TOT_HRS");
                
            }
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_RADL_MC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            
            if(rs_Hrs_Sel.next())
            {
                totProdHrs = totProdHrs +  rs_Hrs_Sel.getDouble("RADL_TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            /*ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_NPROD_MC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                nprodAvailHrs = rs_Hrs_Sel.getDouble("NPROD_TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            */
            //mcAvailHrs = prodAvailHrs + nprodAvailHrs;
            
            mcAvailHrs = this.calculateMachineAvailHrs(month, year);
            
            if(mcAvailHrs > 0)
            {
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnMechHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnElecHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                
                totbrkDwnHrs = brkDwnMechHrs + brkDwnElecHrs;
                
                brkDwnMechPer = (brkDwnMechHrs / mcAvailHrs) * 100;
                if(brkDwnMechPer > 0)
                {
                    n = nf.format(brkDwnMechPer);
                    brkDwnMechPer = Double.parseDouble(n);
                }
                
                brkDwnElecPer = (brkDwnElecHrs / mcAvailHrs) * 100;
                if(brkDwnElecPer > 0)
                {
                    n = nf.format(brkDwnElecPer);
                    brkDwnElecPer = Double.parseDouble(n);
                }
                
                totbrkDwnPer = (totbrkDwnHrs / mcAvailHrs) * 100;
                if(totbrkDwnPer > 0)
                {
                    n = nf.format(totbrkDwnPer);
                    totbrkDwnPer = Double.parseDouble(n);
                }
                
                
                //totProdHrs = mcAvailHrs - totbrkDwnHrs;
                //totProdPer = (totProdHrs / mcAvailHrs) * 100;
                //if(totProdPer > 0)
                //{
                //   n = nf.format(totProdPer);
                //    totProdPer = Double.parseDouble(n);
                //}
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlInstHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlManHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlJobHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlDnoHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlPccHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlToolsHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlGaugeHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlOtherHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                
                totIdleHrs = idlInstHrs + idlManHrs + idlJobHrs + idlDnoHrs + idlPccHrs + idlToolsHrs + idlGaugeHrs + idlOtherHrs;
                
                idlInstPer = (idlInstHrs / mcAvailHrs) * 100;
                if(idlInstPer > 0)
                {
                    n = nf.format(idlInstPer);
                    idlInstPer = Double.parseDouble(n);
                }
                
                idlManPer = (idlManHrs / mcAvailHrs) * 100;
                if(idlManPer > 0)
                {
                    n = nf.format(idlManPer);
                    idlManPer = Double.parseDouble(n);
                }
                
                idlJobPer = (idlJobHrs / mcAvailHrs) * 100;
                if(idlJobPer > 0)
                {
                    n = nf.format(idlJobPer);
                    idlJobPer = Double.parseDouble(n);
                }
                
                idlDnoPer = (idlDnoHrs / mcAvailHrs) * 100;
                if(idlDnoPer > 0)
                {
                    n = nf.format(idlDnoPer);
                    idlDnoPer = Double.parseDouble(n);
                }
                
                idlPccPer = (idlPccHrs / mcAvailHrs) * 100;
                if(idlPccPer > 0)
                {
                    n = nf.format(idlPccPer);
                    idlPccPer = Double.parseDouble(n);
                }
                
                idlToolsPer = (idlToolsHrs / mcAvailHrs) * 100;
                if(idlToolsPer > 0)
                {
                    n = nf.format(idlToolsPer);
                    idlToolsPer = Double.parseDouble(n);
                }
                
                idlGaugePer = (idlGaugeHrs / mcAvailHrs) * 100;
                if(idlGaugePer > 0)
                {
                    n = nf.format(idlGaugePer);
                    idlGaugePer = Double.parseDouble(n);
                }
                
                idlOtherPer = (idlOtherHrs / mcAvailHrs) * 100;
                if(idlOtherPer > 0)
                {
                    n = nf.format(idlOtherPer);
                    idlOtherPer = Double.parseDouble(n);
                }
                
                totIdlePer = (totIdleHrs / mcAvailHrs) * 100;
                if(totIdlePer > 0)
                {
                    n = nf.format(totIdlePer);
                    totIdlePer = Double.parseDouble(n);
                }
                
                //effProdHrs = totProdHrs - totIdleHrs;
                
                perofProd = (totProdHrs / mcAvailHrs) * 100;
                if(perofProd > 0)
                {
                    n = nf.format(perofProd);
                    perofProd = Double.parseDouble(n);
                }
                
            }
            //objMonthlyPercentageDetails.setMcTotHrs(mcTotHrs);
            objMonthlyPercentageDetails.setMcAvailHrs(mcAvailHrs);
            objMonthlyPercentageDetails.setBrkDwnMechHrs(brkDwnMechHrs);
            objMonthlyPercentageDetails.setBrkDwnElecHrs(brkDwnElecHrs);	
            objMonthlyPercentageDetails.setTotBrkDwnHrs(totbrkDwnHrs);
            objMonthlyPercentageDetails.setBrkDwnMechPer(brkDwnMechPer+" %");	
            objMonthlyPercentageDetails.setBrkDwnElecPer(brkDwnElecPer+" %");	
            objMonthlyPercentageDetails.setTotBrkDwnPer(totbrkDwnPer+" %");	
            //objMonthlyPercentageDetails.setTotProdHrs(totProdHrs);
            //objMonthlyPercentageDetails.setTotProdPer(totProdPer+" %");
            objMonthlyPercentageDetails.setIdlInstHrs(idlInstHrs);
            objMonthlyPercentageDetails.setIdlManHrs(idlManHrs);
            objMonthlyPercentageDetails.setIdlJobHrs(idlJobHrs);
            objMonthlyPercentageDetails.setIdlDnoHrs(idlDnoHrs);
            objMonthlyPercentageDetails.setIdlPccHrs(idlPccHrs);
            objMonthlyPercentageDetails.setIdlToolsHrs(idlToolsHrs);
            objMonthlyPercentageDetails.setIdlGaugeHrs(idlGaugeHrs);
            objMonthlyPercentageDetails.setIdlOtherHrs(idlOtherHrs);
            objMonthlyPercentageDetails.setTotIdleHrs(totIdleHrs);
            
            objMonthlyPercentageDetails.setIdlInstPer(idlInstPer+" %");
            objMonthlyPercentageDetails.setIdlManPer(idlManPer+" %");
            objMonthlyPercentageDetails.setIdlJobPer(idlJobPer+" %");
            objMonthlyPercentageDetails.setIdlDnoPer(idlDnoPer+" %");
            objMonthlyPercentageDetails.setIdlPccPer(idlPccPer+" %");
            objMonthlyPercentageDetails.setIdlToolsPer(idlToolsPer+" %");
            objMonthlyPercentageDetails.setIdlGaugePer(idlGaugePer+" %");
            objMonthlyPercentageDetails.setIdlOtherPer(idlOtherPer+" %");
            objMonthlyPercentageDetails.setTotIdlPer(totIdlePer+" %");
            objMonthlyPercentageDetails.setProdAccountedHrs(totIdleHrs + totbrkDwnHrs + totProdHrs);
            objMonthlyPercentageDetails.setEffProdHrs(totProdHrs);
            objMonthlyPercentageDetails.setPerofProd(perofProd+" %");
            
            v_Result.addElement(objMonthlyPercentageDetails);
            
        }
        catch (Exception e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",e.toString());
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
        monthlyPercentageDetailsList = new MonthlyPercentageDetails[v_Result.size()];
        v_Result.copyInto(monthlyPercentageDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("MONTHLY PERCENTAGE DETAILS REPORTS ENDS");
        }
        logger.info("MONTHLY PERCENTAGE DETAILS REPORTS ENDS");
        
        return monthlyPercentageDetailsList;
        
    }
    
    
    public HalfYearlyProductionReportDetail[] fetchHalfYearlyProductionReport(String mcCde,int month,int year) throws SQLException, ReportsException
    {
        HalfYearlyProductionReportDetail halfYearly[] = new HalfYearlyProductionReportDetail[16];
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PRODUCTION REPORT DETAILS STARTS");
        }
        logger.info("HALF YEARLY PRODUCTION REPORT DETAILS STARTS");
        
        
        for(int i = 0;i < 16;i++)
            halfYearly[i] = new HalfYearlyProductionReportDetail();
        HalfYearlyPercentageDetails halfYearlyPercentage[] = this.fetchHalfYearlyPercentageOfDetails(mcCde,month,year);
        for(int i=0;i<halfYearlyPercentage.length;i++)
        {
            HalfYearlyPercentageDetails halfPer = halfYearlyPercentage[i]; 
            switch(i)
            {
            case 0:
                halfYearly[0].setMonth1(halfPer.getYearString());
                halfYearly[1].setMonth1(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth1(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth1("");
                halfYearly[4].setMonth1(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth1(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth1(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth1(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth1(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth1(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth1(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth1(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth1(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth1(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth1(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth1(halfPer.getPerOfProd());
                
                break;
                
            case 1:
                halfYearly[0].setMonth2(halfPer.getYearString());
                halfYearly[1].setMonth2(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth2(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth2("");
                halfYearly[4].setMonth2(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth2(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth2(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth2(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth2(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth2(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth2(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth2(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth2(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth2(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth2(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth2(halfPer.getPerOfProd());
                break;
                
            case 2:
                halfYearly[0].setMonth3(halfPer.getYearString());
                halfYearly[1].setMonth3(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth3(halfPer.getBrkDwnHrs()+"");
//                halfYearly[3].setMonth3(halfPer.getMcTotHrs()+"");
                halfYearly[3].setMonth3("");
                halfYearly[4].setMonth3(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth3(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth3(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth3(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth3(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth3(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth3(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth3(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth3(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth3(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth3(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth3(halfPer.getPerOfProd());
                break;
                
            case 3:
                halfYearly[0].setMonth4(halfPer.getYearString());
                halfYearly[1].setMonth4(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth4(halfPer.getBrkDwnHrs()+"");
                //halfYearly[3].setMonth4(halfPer.getMcTotHrs()+"");
                halfYearly[3].setMonth4("");
                halfYearly[4].setMonth4(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth4(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth4(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth4(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth4(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth4(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth4(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth4(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth4(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth4(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth4(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth4(halfPer.getPerOfProd());
                
                break;
                
            case 4:
                halfYearly[0].setMonth5(halfPer.getYearString());
                halfYearly[1].setMonth5(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth5(halfPer.getBrkDwnHrs()+"");
                //halfYearly[3].setMonth5(halfPer.getMcTotHrs()+"");
                halfYearly[3].setMonth5("");
                halfYearly[4].setMonth5(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth5(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth5(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth5(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth5(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth5(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth5(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth5(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth5(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth5(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth5(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth5(halfPer.getPerOfProd());
                
                break;
                
            case 5:
                halfYearly[0].setMonth6(halfPer.getYearString());
                halfYearly[1].setMonth6(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth6(halfPer.getBrkDwnHrs()+"");
                //halfYearly[3].setMonth6(halfPer.getMcTotHrs()+"");
                halfYearly[3].setMonth6("");
                halfYearly[4].setMonth6(halfPer.getIdlInstHrs()+"");
                halfYearly[5].setMonth6(halfPer.getIdlManHrs()+"");
                halfYearly[6].setMonth6(halfPer.getIdlJobHrs()+"");
                halfYearly[7].setMonth6(halfPer.getIdlDnoHrs()+"");
                halfYearly[8].setMonth6(halfPer.getIdlPccHrs()+"");
                halfYearly[9].setMonth6(halfPer.getIdlToolsHrs()+"");
                halfYearly[10].setMonth6(halfPer.getIdlGaugeHrs()+"");
                halfYearly[11].setMonth6(halfPer.getIdlOtherHrs()+"");
                halfYearly[12].setMonth6(halfPer.getTotIdlHrs()+"");
                halfYearly[13].setMonth6(halfPer.getEffProdHrs()+"");
                halfYearly[14].setMonth6(halfPer.getProdAccountedHrs()+"");
                halfYearly[15].setMonth6(halfPer.getPerOfProd());
                break;
                
                
            }
        }
        halfYearly[0].setSerialNumber("SNO");
        halfYearly[1].setSerialNumber("1");
        halfYearly[2].setSerialNumber("2");
        //halfYearly[3].setSerialNumber("3");
        halfYearly[3].setSerialNumber("3");
        halfYearly[13].setSerialNumber("4");
        halfYearly[14].setSerialNumber("5");
        halfYearly[15].setSerialNumber("6");
        
        halfYearly[0].setDesription("Description");
        halfYearly[1].setDesription("M/C Available Hrs");
        halfYearly[2].setDesription("Breakdown Hrs");
        //halfYearly[3].setDesription("Total Production Hrs");
        halfYearly[3].setDesription("Idle Hrs(Due to following reasons)");
        halfYearly[4].setDesription("a) Waiting for Instrument");
        halfYearly[5].setDesription("b) Waiting for Man");
        halfYearly[6].setDesription("c) Waiting for Job");
        halfYearly[7].setDesription("d) Waiting for Drawing No.");
        halfYearly[8].setDesription("e) Waiting for PCC");
        halfYearly[9].setDesription("f) Waiting for Tools");
        halfYearly[10].setDesription("g) Waiting for Gauges");
        halfYearly[11].setDesription("h) Waiting for Others");                        
        halfYearly[12].setDesription("Total");
        halfYearly[13].setDesription("Effective Production Hrs");
        halfYearly[14].setDesription("Production Accounted Hrs");
        halfYearly[15].setDesription("Percentage Of Production");
        
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PRODUCTION REPORT DETAILS ENDS");
        }
        logger.info("HALF YEARLY PRODUCTION REPORT DETAILS ENDS");
        
        
        return halfYearly;
    }
    /*public HalfYearlyProductionReportDetail[] fetchHalfYearlyProductionReport(String mcCde,int month,int year) throws SQLException, ReportsException
    {
        HalfYearlyProductionReportDetail halfYearly[] = new HalfYearlyProductionReportDetail[16];
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PRODUCTION REPORT DETAILS STARTS");
        }
        logger.info("HALF YEARLY PRODUCTION REPORT DETAILS STARTS");
        
        
        for(int i=0;i<16;i++)
            halfYearly[i] = new HalfYearlyProductionReportDetail();
        HalfYearlyPercentageDetails halfYearlyPercentage[] = this.fetchHalfYearlyPercentageOfDetails(mcCde,month,year);
        for(int i=0;i<halfYearlyPercentage.length;i++)
        {
            HalfYearlyPercentageDetails halfPer = halfYearlyPercentage[i]; 
            switch(i)
            {
            case 0:
                halfYearly[0].setMonth1(halfPer.getYearString());
                halfYearly[1].setMonth1(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth1(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth1(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth1("");
                halfYearly[5].setMonth1(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth1(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth1(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth1(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth1(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth1(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth1(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth1(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth1(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth1(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth1(halfPer.getPerOfProd());
                
                break;
                
            case 1:
                halfYearly[0].setMonth2(halfPer.getYearString());
                halfYearly[1].setMonth2(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth2(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth2(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth2("");
                halfYearly[5].setMonth2(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth2(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth2(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth2(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth2(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth2(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth2(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth2(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth2(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth2(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth2(halfPer.getPerOfProd());
                break;
                
            case 2:
                halfYearly[0].setMonth3(halfPer.getYearString());
                halfYearly[1].setMonth3(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth3(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth3(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth3("");
                halfYearly[5].setMonth3(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth3(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth3(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth3(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth3(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth3(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth3(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth3(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth3(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth3(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth3(halfPer.getPerOfProd());
                break;
                
            case 3:
                halfYearly[0].setMonth4(halfPer.getYearString());
                halfYearly[1].setMonth4(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth4(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth4(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth4("");
                halfYearly[5].setMonth4(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth4(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth4(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth4(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth4(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth4(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth4(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth4(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth4(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth4(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth4(halfPer.getPerOfProd());
                break;
                
            case 4:
                halfYearly[0].setMonth5(halfPer.getYearString());
                halfYearly[1].setMonth5(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth5(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth5(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth5("");
                halfYearly[5].setMonth5(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth5(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth5(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth5(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth5(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth5(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth5(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth5(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth5(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth5(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth5(halfPer.getPerOfProd());
                break;
                
            case 5:
                halfYearly[0].setMonth6(halfPer.getYearString());
                halfYearly[1].setMonth6(halfPer.getMcAvailHrs()+"");
                halfYearly[2].setMonth6(halfPer.getBrkDwnHrs()+"");
                halfYearly[3].setMonth6(halfPer.getMcTotHrs()+"");
                halfYearly[4].setMonth6("");
                halfYearly[5].setMonth6(halfPer.getIdlInstHrs()+"");
                halfYearly[6].setMonth6(halfPer.getIdlManHrs()+"");
                halfYearly[7].setMonth6(halfPer.getIdlJobHrs()+"");
                halfYearly[8].setMonth6(halfPer.getIdlDnoHrs()+"");
                halfYearly[9].setMonth6(halfPer.getIdlPccHrs()+"");
                halfYearly[10].setMonth6(halfPer.getIdlToolsHrs()+"");
                halfYearly[11].setMonth6(halfPer.getIdlGaugeHrs()+"");
                halfYearly[12].setMonth6(halfPer.getIdlOtherHrs()+"");
                halfYearly[13].setMonth6(halfPer.getTotIdlHrs()+"");
                halfYearly[14].setMonth6(halfPer.getEffProdHrs()+"");
                halfYearly[15].setMonth6(halfPer.getPerOfProd());
                break;
                
                
            }
        }
        halfYearly[0].setSerialNumber("SNO");
        halfYearly[1].setSerialNumber("1");
        halfYearly[2].setSerialNumber("2");
        halfYearly[3].setSerialNumber("3");
        halfYearly[4].setSerialNumber("4");
        halfYearly[14].setSerialNumber("5");
        halfYearly[15].setSerialNumber("6");
        
        halfYearly[0].setDesription("Description");
        halfYearly[1].setDesription("M/C Available Hrs");
        halfYearly[2].setDesription("Breakdown Hrs");
        halfYearly[3].setDesription("Total Production Hrs");
        halfYearly[4].setDesription("Idle Hrs(Due to following reasons)");
        halfYearly[5].setDesription("a) Waiting for Instrument");
        halfYearly[6].setDesription("b) Waiting for Man");
        halfYearly[7].setDesription("c) Waiting for Job");
        halfYearly[8].setDesription("d) Waiting for Drawing No.");
        halfYearly[9].setDesription("e) Waiting for PCC");
        halfYearly[10].setDesription("f) Waiting for Tools");
        halfYearly[11].setDesription("g) Waiting for Gauges");
        halfYearly[12].setDesription("h) Waiting for Others");                        
        halfYearly[13].setDesription("Total");
        halfYearly[14].setDesription("Effective Production Hrs");
        halfYearly[15].setDesription("Percentage Of Production");
        
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PRODUCTION REPORT DETAILS ENDS");
        }
        logger.info("HALF YEARLY PRODUCTION REPORT DETAILS ENDS");
        
        
        return halfYearly;
    }
    *//*
    public HalfYearlyPercentageDetails[] fetchHalfYearlyPercentageOfDetails(String mcCde,int month,int year) throws SQLException, ReportsException
    {
        
        DBConnection con = null;
        int days = 0;
        double prodAvailHrs = 0;
        double nProdAvailHrs = 0;
        double mcAvailHrs = 0;
        
        
        double mcTotHrs = 0;
        double totBrkDwnHrs = 0;
        
        double totProdHrs = 0;
        double brkDwnMechHrs = 0;
        double brkDwnElecHrs = 0;
        double idlInstHrs = 0;
        double idlManHrs = 0;
        double idlJobHrs = 0;
        double idlDnoHrs = 0;
        double idlPccHrs = 0;
        double idlToolsHrs = 0;
        double idlGaugeHrs = 0;
        double idlOtherHrs = 0;
        double totIdlHrs = 0;
        
        double effProdHrs = 0;
        double perOfProd = 0;
        Date fromDate = null;
        Date toDate = null;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String n="";
        
        HalfYearlyPercentageDetails[] objHalfYearlyPercentageDetailsList;
        Vector v_Result = new Vector();
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PERCENTAGE DETAILS REPORT STARTS");
        }
        logger.info("HALF YEARLY PERCENTAGE DETAILS REPORT STARTS");
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            Hashtable ht = new Hashtable();
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            
            for(int i = 0;i<6;i++)
            {
                
                if(i!=0)
                {
                    gc.add(GregorianCalendar.MONTH,1);
                    gc.set(GregorianCalendar.DATE,1);
                }
                fromDate = gc.getTime();
                if(BuildConfig.DMODE)
                    System.out.println("fdate "+fromDate);
                days = gc.getActualMaximum(GregorianCalendar.DATE);
                gc.set(GregorianCalendar.DATE,days);
                toDate = gc.getTime();
                if(BuildConfig.DMODE)
                    System.out.println("tdate "+toDate);
                
                mcTotHrs = days * 21;
                ht.put("FROM_DATE",fromDate);
                ht.put("TO_DATE",toDate);
                ht.put("MC_CDE",mcCde);
                
                HalfYearlyPercentageDetails objHalfYearlyPercentageDetails = new HalfYearlyPercentageDetails();
                
                String mon[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                
                objHalfYearlyPercentageDetails.setYearString(mon[gc.get(GregorianCalendar.MONTH)]+"'"+gc.get(GregorianCalendar.YEAR));
                
                
                PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_PROD_MC_HRS_SQL_QUERY,ht);
                ResultSet rs_Hrs_Sel = ps.executeQuery();
                
                
                if(rs_Hrs_Sel.next())
                {
                    prodAvailHrs = rs_Hrs_Sel.getDouble("PROD_TOT_HRS");
                    
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_RADL_MC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                
                
                if(rs_Hrs_Sel.next())
                {
                    prodAvailHrs = prodAvailHrs +  rs_Hrs_Sel.getDouble("RADL_TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_NPROD_MC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    nProdAvailHrs = rs_Hrs_Sel.getDouble("NPROD_TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                mcAvailHrs = prodAvailHrs + nProdAvailHrs;
                ps = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnMechHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnElecHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                totBrkDwnHrs = brkDwnMechHrs + brkDwnElecHrs;
                totProdHrs = mcAvailHrs - totBrkDwnHrs;
                
                ps = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlInstHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlManHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlJobHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlDnoHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlPccHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlToolsHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlGaugeHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlOtherHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                
                
                totIdlHrs = idlInstHrs + idlManHrs + idlJobHrs + idlDnoHrs + idlPccHrs + idlToolsHrs + idlGaugeHrs + idlOtherHrs;
                
                
                effProdHrs = totProdHrs - totIdlHrs;
                if(totProdHrs == 0)
                {
                    perOfProd = 0; 
                }
                else
                {
                    perOfProd = (effProdHrs / totProdHrs) * 100;
                    if(perOfProd > 0)
                    {
                        n = nf.format(perOfProd);
                        perOfProd = Double.parseDouble(n);
                    }
                }
                
                objHalfYearlyPercentageDetails.setMcTotHrs(mcTotHrs);
                objHalfYearlyPercentageDetails.setMcAvailHrs(mcAvailHrs);
                objHalfYearlyPercentageDetails.setBrkDwnHrs(totBrkDwnHrs);
                
                objHalfYearlyPercentageDetails.setTotProdHrs(totProdHrs);
                
                objHalfYearlyPercentageDetails.setIdlInstHrs(idlInstHrs);
                objHalfYearlyPercentageDetails.setIdlManHrs(idlManHrs);
                objHalfYearlyPercentageDetails.setIdlJobHrs(idlJobHrs);
                objHalfYearlyPercentageDetails.setIdlDnoHrs(idlDnoHrs);
                objHalfYearlyPercentageDetails.setIdlPccHrs(idlPccHrs);
                objHalfYearlyPercentageDetails.setIdlToolsHrs(idlToolsHrs);
                objHalfYearlyPercentageDetails.setIdlGaugeHrs(idlGaugeHrs);
                objHalfYearlyPercentageDetails.setIdlOtherHrs(idlOtherHrs);
                objHalfYearlyPercentageDetails.setTotIdlHrs(totIdlHrs);
                
                objHalfYearlyPercentageDetails.setEffProdHrs(effProdHrs);
                objHalfYearlyPercentageDetails.setPerOfProd(perOfProd+" %");
                
                v_Result.addElement(objHalfYearlyPercentageDetails);
                
                
            }	
        }
        catch (SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new ReportsException("","GENERAL SQL ERROR",e.toString());
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
        
        objHalfYearlyPercentageDetailsList = new HalfYearlyPercentageDetails[v_Result.size()];
        v_Result.copyInto(objHalfYearlyPercentageDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PERCENTAGE DETAILS REPORT ENDS");
        }
        logger.info("HALF YEARLY PERCENTAGE DETAILS REPORT ENDS");
        
        return objHalfYearlyPercentageDetailsList;
        
    }
    */
    public HalfYearlyPercentageDetails[] fetchHalfYearlyPercentageOfDetails(String mcCde,int month,int year) throws SQLException, ReportsException
    {
        
        DBConnection con = null;
        int days = 0;
        //double prodAvailHrs = 0;
        //double nProdAvailHrs = 0;
        double mcAvailHrs = 0;
        
        
        //double mcTotHrs = 0;
        double totBrkDwnHrs = 0;
        
        double totProdHrs = 0;
        double brkDwnMechHrs = 0;
        double brkDwnElecHrs = 0;
        double idlInstHrs = 0;
        double idlManHrs = 0;
        double idlJobHrs = 0;
        double idlDnoHrs = 0;
        double idlPccHrs = 0;
        double idlToolsHrs = 0;
        double idlGaugeHrs = 0;
        double idlOtherHrs = 0;
        double totIdlHrs = 0;
        //double effProdHrs = 0;
        double perOfProd = 0;
        Date fromDate = null;
        Date toDate = null;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String n="";
        
        HalfYearlyPercentageDetails[] objHalfYearlyPercentageDetailsList;
        Vector v_Result = new Vector();
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PERCENTAGE DETAILS REPORT STARTS");
        }
        logger.info("HALF YEARLY PERCENTAGE DETAILS REPORT STARTS");
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            Hashtable ht = new Hashtable();
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(GregorianCalendar.MONTH,month-1);
            gc.set(GregorianCalendar.YEAR,year);
            gc.set(GregorianCalendar.DATE,1);
            
            for(int i = 0;i<6;i++)
            {
                
                if(i!=0)
                {
                    gc.add(GregorianCalendar.MONTH,1);
                    gc.set(GregorianCalendar.DATE,1);
                }
                fromDate = gc.getTime();
                if(BuildConfig.DMODE)
                    System.out.println("fdate "+fromDate);
                days = gc.getActualMaximum(GregorianCalendar.DATE);
                gc.set(GregorianCalendar.DATE,days);
                toDate = gc.getTime();
                if(BuildConfig.DMODE)
                    System.out.println("tdate "+toDate);
                
                //mcTotHrs = days * 21;
                
                ht.put("FROM_DATE",fromDate);
                ht.put("TO_DATE",toDate);
                ht.put("MC_CDE",mcCde);
                
                HalfYearlyPercentageDetails objHalfYearlyPercentageDetails = new HalfYearlyPercentageDetails();
                
                String mon[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                
                objHalfYearlyPercentageDetails.setYearString(mon[gc.get(GregorianCalendar.MONTH)]+"'"+gc.get(GregorianCalendar.YEAR));
                
                
                PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_PROD_MC_HRS_SQL_QUERY,ht);
                ResultSet rs_Hrs_Sel = ps.executeQuery();
                
                
                if(rs_Hrs_Sel.next())
                {
                    totProdHrs = rs_Hrs_Sel.getDouble("PROD_TOT_HRS");
                    
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_RADL_MC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                
                
                if(rs_Hrs_Sel.next())
                {
                    totProdHrs = totProdHrs +  rs_Hrs_Sel.getDouble("RADL_TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                /*ps = con.executeStatement(SQLMaster.SELECT_NPROD_MC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    nProdAvailHrs = rs_Hrs_Sel.getDouble("NPROD_TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                mcAvailHrs = prodAvailHrs + nProdAvailHrs;
                */
                
                mcAvailHrs = this.calculateMachineAvailHrs(gc.get(GregorianCalendar.MONTH)+1, year);
                
                ps = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnMechHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    brkDwnElecHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                totBrkDwnHrs = brkDwnMechHrs + brkDwnElecHrs;
                //totProdHrs = mcAvailHrs - totBrkDwnHrs;
                
                ps = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlInstHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlManHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlJobHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlDnoHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlPccHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlToolsHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlGaugeHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                ps = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    idlOtherHrs = rs_Hrs_Sel.getDouble("TOT_HRS");
                }	
                
                rs_Hrs_Sel.close();
                ps.close();
                
                
                
                totIdlHrs = idlInstHrs + idlManHrs + idlJobHrs + idlDnoHrs + idlPccHrs + idlToolsHrs + idlGaugeHrs + idlOtherHrs;
                
                
                //effProdHrs = totProdHrs - totIdlHrs;
                if(totProdHrs == 0)
                {
                    perOfProd = 0; 
                }
                else
                {
                    perOfProd = (totProdHrs / mcAvailHrs) * 100;
                    if(perOfProd > 0)
                    {
                        n = nf.format(perOfProd);
                        perOfProd = Double.parseDouble(n);
                    }
                }
                
                //objHalfYearlyPercentageDetails.setMcTotHrs(mcTotHrs);
                objHalfYearlyPercentageDetails.setMcAvailHrs(mcAvailHrs);
                objHalfYearlyPercentageDetails.setBrkDwnHrs(totBrkDwnHrs);
                
                //objHalfYearlyPercentageDetails.setTotProdHrs(totProdHrs);
                
                objHalfYearlyPercentageDetails.setIdlInstHrs(idlInstHrs);
                objHalfYearlyPercentageDetails.setIdlManHrs(idlManHrs);
                objHalfYearlyPercentageDetails.setIdlJobHrs(idlJobHrs);
                objHalfYearlyPercentageDetails.setIdlDnoHrs(idlDnoHrs);
                objHalfYearlyPercentageDetails.setIdlPccHrs(idlPccHrs);
                objHalfYearlyPercentageDetails.setIdlToolsHrs(idlToolsHrs);
                objHalfYearlyPercentageDetails.setIdlGaugeHrs(idlGaugeHrs);
                objHalfYearlyPercentageDetails.setIdlOtherHrs(idlOtherHrs);
                objHalfYearlyPercentageDetails.setTotIdlHrs(totIdlHrs);
                objHalfYearlyPercentageDetails.setProdAccountedHrs(totBrkDwnHrs+totIdlHrs+totProdHrs);
                objHalfYearlyPercentageDetails.setEffProdHrs(totProdHrs);
                objHalfYearlyPercentageDetails.setPerOfProd(perOfProd+" %");
                
                v_Result.addElement(objHalfYearlyPercentageDetails);
                
                
            }	
        }
        catch (SQLException e)
        {
            if(BuildConfig.DMODE)
                e.printStackTrace();
            throw new ReportsException("","GENERAL SQL ERROR",e.toString());
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
        
        objHalfYearlyPercentageDetailsList = new HalfYearlyPercentageDetails[v_Result.size()];
        v_Result.copyInto(objHalfYearlyPercentageDetailsList);
        
        if(BuildConfig.DMODE)
        {
            System.out.println("HALF YEARLY PERCENTAGE DETAILS REPORT ENDS");
        }
        logger.info("HALF YEARLY PERCENTAGE DETAILS REPORT ENDS");
        
        return objHalfYearlyPercentageDetailsList;
        
    }
    
    public IdleBrkdwnDetails[] fetchIdleBrkdwnHrs(Date fromDate,Date toDate) throws ReportsException, SQLException
    {
        IdleBrkdwnDetails[] objIdleBrkdwnDetailsList = null;
        Hashtable ht = new Hashtable();
        String mcCode = "";
        float mechHrs = 0;
        float elecHrs = 0;
        float instHrs = 0;
        float manHrs = 0;
        float jobHrs = 0;
        float drwgHrs = 0;
        float pccHrs = 0;
        float toolsHrs = 0;
        float gaugeHrs = 0;
        float othersHrs = 0;
        float totHrs = 0;
        
        DBConnection con = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            
            if(BuildConfig.DMODE)
            {
                System.out.println("IDLE BREAK DOWN DETAILS REPORT STARTS");
            }
            logger.info("IDLE BREAK DOWN DETAILS REPORT STARTS");
            
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_ALL_MACHINES_SQL_QUERY); 
            ResultSet rs = ps.executeQuery();
            Vector vec_Result = new Vector();
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            ResultSet rs_Hrs_Sel = null;
            PreparedStatement ps_Hrs_Sel = null;
            while(rs.next())
            {
                
                
                mcCode = rs.getString("MC_CDE");
                ht.put("MC_CDE",mcCode);
                
                IdleBrkdwnDetails objIdleBrkdwnDetails = new IdleBrkdwnDetails();
                objIdleBrkdwnDetails.setMcCde(mcCode);
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    mechHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                if(BuildConfig.DMODE)
                    System.out.println("mcHrs "+mechHrs);
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    elecHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    instHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                
                if(rs_Hrs_Sel.next())
                {
                    manHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    jobHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    drwgHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    pccHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    toolsHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    gaugeHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
                rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
                if(rs_Hrs_Sel.next())
                {
                    othersHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
                }
                
                rs_Hrs_Sel.close();
                ps_Hrs_Sel.close();
                
                totHrs = mechHrs + elecHrs + instHrs + manHrs + jobHrs + drwgHrs + pccHrs + toolsHrs + gaugeHrs + othersHrs;
                
                
                objIdleBrkdwnDetails.setMechHrs(mechHrs);
                objIdleBrkdwnDetails.setElecHrs(elecHrs);
                objIdleBrkdwnDetails.setInstHrs(instHrs);
                objIdleBrkdwnDetails.setManHrs(manHrs);
                objIdleBrkdwnDetails.setJobHrs(jobHrs);
                objIdleBrkdwnDetails.setDrgHrs(drwgHrs);
                objIdleBrkdwnDetails.setPccHrs(pccHrs);
                objIdleBrkdwnDetails.setToolsHrs(toolsHrs);
                objIdleBrkdwnDetails.setGaugeHrs(gaugeHrs);
                objIdleBrkdwnDetails.setOthersHrs(othersHrs);
                objIdleBrkdwnDetails.setTotHrs(totHrs);
                vec_Result.addElement(objIdleBrkdwnDetails);
                
            }
            rs.close();
            ps.close();
            objIdleBrkdwnDetailsList = new IdleBrkdwnDetails[vec_Result.size()];
            vec_Result.copyInto(objIdleBrkdwnDetailsList);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        if(BuildConfig.DMODE)
        {
            System.out.println("IDLE BREAK DOWN DETAILS REPORT ENDS");
        }
        logger.info("IDLE BREAK DOWN DETAILS REPORT ENDS");
        
        return objIdleBrkdwnDetailsList;
    }
    
    public IdleBrkdwnDetails fetchIdleBrkdwnHrs(Date fromDate,Date toDate,String mcCde) throws ReportsException, SQLException
    {
        IdleBrkdwnDetails objIdleBrkdwnDetails = new IdleBrkdwnDetails();
        Hashtable ht = new Hashtable();
        String mcCode = "";
        float mechHrs = 0;
        float elecHrs = 0;
        float instHrs = 0;
        float manHrs = 0;
        float jobHrs = 0;
        float drwgHrs = 0;
        float pccHrs = 0;
        float toolsHrs = 0;
        float gaugeHrs = 0;
        float othersHrs = 0;
        float totHrs = 0;
        
        DBConnection con = null;
        try
        {
            con = DBConnectionFactory.getConnection();
            
            if(BuildConfig.DMODE)
            {
                System.out.println("IDLE BREAK DOWN DETAILS REPORT STARTS");
            }
            logger.info("IDLE BREAK DOWN DETAILS REPORT STARTS");
            ht.put("FROM_DATE",fromDate);
            ht.put("TO_DATE",toDate);
            ht.put("MC_CDE",mcCde);
            ResultSet rs_Hrs_Sel = null;
            PreparedStatement ps_Hrs_Sel = null;
            
            objIdleBrkdwnDetails.setMcCde(mcCode);
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MECH_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                mechHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            if(BuildConfig.DMODE)
                System.out.println("mcHrs "+mechHrs);
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_ELEC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                elecHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_INST_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                instHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_MAN_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            
            if(rs_Hrs_Sel.next())
            {
                manHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_JOB_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                jobHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_DRG_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                drwgHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_PCC_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                pccHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_TOOLS_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                toolsHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_GAUGE_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                gaugeHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            ps_Hrs_Sel = con.executeStatement(SQLMaster.SELECT_OTHERS_HRS_SQL_QUERY,ht);
            rs_Hrs_Sel = ps_Hrs_Sel.executeQuery();
            if(rs_Hrs_Sel.next())
            {
                othersHrs =  rs_Hrs_Sel.getFloat("TOT_HRS");
            }
            
            rs_Hrs_Sel.close();
            ps_Hrs_Sel.close();
            
            totHrs = mechHrs + elecHrs + instHrs + manHrs + jobHrs + drwgHrs + pccHrs + toolsHrs + gaugeHrs + othersHrs;
            
            
            objIdleBrkdwnDetails.setMechHrs(mechHrs);
            objIdleBrkdwnDetails.setElecHrs(elecHrs);
            objIdleBrkdwnDetails.setInstHrs(instHrs);
            objIdleBrkdwnDetails.setManHrs(manHrs);
            objIdleBrkdwnDetails.setJobHrs(jobHrs);
            objIdleBrkdwnDetails.setDrgHrs(drwgHrs);
            objIdleBrkdwnDetails.setPccHrs(pccHrs);
            objIdleBrkdwnDetails.setToolsHrs(toolsHrs);
            objIdleBrkdwnDetails.setGaugeHrs(gaugeHrs);
            objIdleBrkdwnDetails.setOthersHrs(othersHrs);
            objIdleBrkdwnDetails.setTotHrs(totHrs);
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        if(BuildConfig.DMODE)
        {
            System.out.println("IDLE BREAK DOWN DETAILS REPORT ENDS");
        }
        logger.info("IDLE BREAK DOWN DETAILS REPORT ENDS");
        
        return objIdleBrkdwnDetails;
    }
    
    public EmployeePerformanceReturnDetails[] fetchEmployeePerformance(Date startDate,Date endDate,int empId) throws ReportsException, SQLException
    {
        DBConnection con = null;
        //EmployeePerformaceDetails[] objEmployeePerformanceDetailsList = null;
        EmployeePerformanceReturnDetails[] objEmployeePerformanceReturnDetailsList;
        EmployeePerformanceReturnDetails objEmployeePerformanceReturnDetails = new EmployeePerformanceReturnDetails();
        EmployeePerformanceTotDetails objEmployeePerformanceTotDetails = new EmployeePerformanceTotDetails();
        Hashtable ht = new Hashtable();
        ht.put("PROD_START_DATE",startDate);
        ht.put("PROD_END_DATE",endDate);
        ht.put("EMP_ID",new Integer(empId));
        Vector vec_Result = new Vector();
        Vector vec_Return = new Vector();
        if(BuildConfig.DMODE)
        {
            System.out.println("EMPLOYEE PERFORMANCE DETAILS REPORTS STARTS");
        }
        logger.info("EMPLOYEE PERFORMANCE DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            double totStdHrs = 0;
            double totOpnHrs = 0;
            double totExceededHrs = 0;
            double totSavedHrs = 0;
            String percentageSavedHrs = "";
            String percentageExceededHrs = "";
            String netSavedHrsPercentage = "";
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_EMPLOYEE_PERFORMANCE_SQL_QUERY,ht);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                EmployeePerformaceDetails objEmployeePerformaceDetails = new EmployeePerformaceDetails();
                objEmployeePerformaceDetails.setProdCrntDate(rs.getTimestamp("PROD_CRNT_DATE"));
                objEmployeePerformaceDetails.setMcCode(rs.getString("MC_CDE"));
                objEmployeePerformaceDetails.setShiftName(rs.getString("SHIFT_NAME"));
                objEmployeePerformaceDetails.setWoNo(rs.getString("WO_NO"));
                objEmployeePerformaceDetails.setDetails(rs.getString("DETAILS"));
                objEmployeePerformaceDetails.setStartOpn(rs.getString("PROD_START_OPN"));
                objEmployeePerformaceDetails.setEndOpn(rs.getString("PROD_END_OPN"));
                objEmployeePerformaceDetails.setDtyHrs(rs.getFloat("DT_HRS"));
                objEmployeePerformaceDetails.setOtHrs(rs.getFloat("OT_HRS"));
                objEmployeePerformaceDetails.setStdHrs(rs.getFloat("PROD_STD_HRS"));
                objEmployeePerformaceDetails.setOpnHrs(rs.getFloat("OPN_HRS"));
                objEmployeePerformaceDetails.setIncntvSalHrs(rs.getFloat("INCNTV_SLRY_HRS"));
                objEmployeePerformaceDetails.setDtySalHrs(rs.getFloat("DT_SLRY_HRS"));
                objEmployeePerformaceDetails.setOtSalHrs(rs.getFloat("OT_SLRY_HRS"));
                if(objEmployeePerformaceDetails.getStdHrs() < objEmployeePerformaceDetails.getOpnHrs())
                {
                    objEmployeePerformaceDetails.setExceededHrs(objEmployeePerformaceDetails.getOpnHrs() - objEmployeePerformaceDetails.getStdHrs());
                }
                else if(objEmployeePerformaceDetails.getStdHrs() > objEmployeePerformaceDetails.getOpnHrs())
                {
                    objEmployeePerformaceDetails.setSavedHrs(objEmployeePerformaceDetails.getStdHrs() - objEmployeePerformaceDetails.getOpnHrs());
                }
                totOpnHrs = totOpnHrs + objEmployeePerformaceDetails.getOpnHrs();
                totStdHrs = totStdHrs + objEmployeePerformaceDetails.getStdHrs();
                totSavedHrs = totSavedHrs + objEmployeePerformaceDetails.getSavedHrs();
                totExceededHrs = totExceededHrs + objEmployeePerformaceDetails.getExceededHrs();
                vec_Result.add(objEmployeePerformaceDetails);
            }
            rs.close();
            ps.close();
            //objEmployeePerformanceDetailsList = new EmployeePerformaceDetails[vec_Result.size()];
            //vec_Result.copyInto(objEmployeePerformanceDetailsList);
            
            percentageSavedHrs = nf.format((totSavedHrs * 100)/totOpnHrs)+"%";
            percentageExceededHrs = nf.format((totExceededHrs * 100)/totOpnHrs)+"%";
            netSavedHrsPercentage = nf.format(((totStdHrs - totOpnHrs)*100)/totOpnHrs)+"%";
            objEmployeePerformanceTotDetails.setTotOpnHrs(totOpnHrs);
            objEmployeePerformanceTotDetails.setTotStdHrs(totStdHrs);
            objEmployeePerformanceTotDetails.setTotSavedHrs(totSavedHrs);
            objEmployeePerformanceTotDetails.setTotExceededHrs(totExceededHrs);
            objEmployeePerformanceTotDetails.setSavedHrsPercentage(percentageSavedHrs);
            objEmployeePerformanceTotDetails.setExceededHrsPercentage(percentageExceededHrs);
            objEmployeePerformanceTotDetails.setNetHrsSavedPercentage(netSavedHrsPercentage);
            
            //vec_Return.add(objEmployeePerformanceDetailsList);
            vec_Return.add(objEmployeePerformanceTotDetails);
            //vec_Result.add(objEmployeePerformanceTotDetails);
           
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        if(BuildConfig.DMODE)
        {
            System.out.println("EMPLOYEE PERFORMANCE DETAILS REPORTS ENDS");
        }
        logger.info("EMPLOYEE PERFORMANCE DETAILS REPORTS ENDS");
        
        objEmployeePerformanceReturnDetails.setVecEmployeePerformanceDetails(vec_Result);
        objEmployeePerformanceReturnDetails.setVecEmployeePerformanceTotDetails(vec_Return);
        objEmployeePerformanceReturnDetailsList = new EmployeePerformanceReturnDetails[1];
        objEmployeePerformanceReturnDetailsList[0] = objEmployeePerformanceReturnDetails;
        return objEmployeePerformanceReturnDetailsList;
    }
    
    public EmployeePerformanceReturnDetails[] fetchEmployeePerformanceByEmpType(Date startDate,Date endDate,int empTypeId) throws ReportsException, SQLException
    {
        
        DBConnection con = null;
        //EmployeePerformaceDetails[] objEmployeePerformanceDetailsList = null;
        EmployeePerformanceReturnDetails[] objEmployeePerformanceReturnDetailsList;
        
        
        Hashtable ht = new Hashtable();
        ht.put("PROD_START_DATE",startDate);
        ht.put("PROD_END_DATE",endDate);
    //    ht.put("EMP_ID",new Integer(empId));
        Vector vec = new Vector();
        if(BuildConfig.DMODE)
        {
            System.out.println("EMPLOYEE PERFORMANCE DETAILS REPORTS STARTS");
        }
        logger.info("EMPLOYEE PERFORMANCE DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            double totStdHrs = 0;
            double totOpnHrs = 0;
            double totExceededHrs = 0;
            double totSavedHrs = 0;
            String empName = "";
            String percentageSavedHrs = "";
            String percentageExceededHrs = "";
            String netSavedHrsPercentage = "";
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            ht.put("EMP_TYP_ID",new Integer(empTypeId));
            PreparedStatement ps = con.executeStatement(SQLMaster.SELECT_ALL_EMP_BY_TYPE_SQL_QUERY,ht);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                totStdHrs = 0;
                totOpnHrs = 0;
                totExceededHrs = 0;
                totSavedHrs = 0;
                
                Vector vec_Result = new Vector();
                Vector vec_Return = new Vector();
                
                ht.put("EMP_ID",new Integer(rs.getInt("EMP_ID")));
                empName = rs.getString("EMP_NAME");
                PreparedStatement psInner = con.executeStatement(SQLMaster.FETCH_EMPLOYEE_PERFORMANCE_SQL_QUERY,ht);
                ResultSet rsInner = psInner.executeQuery();
                EmployeePerformanceReturnDetails objEmployeePerformanceReturnDetails = new EmployeePerformanceReturnDetails();
                EmployeePerformanceTotDetails objEmployeePerformanceTotDetails = new EmployeePerformanceTotDetails();
                objEmployeePerformanceTotDetails.setEmpName(empName);
                while(rsInner.next())
                {
                    
                    EmployeePerformaceDetails objEmployeePerformaceDetails = new EmployeePerformaceDetails();
                    objEmployeePerformaceDetails.setProdCrntDate(rsInner.getTimestamp("PROD_CRNT_DATE"));
                    objEmployeePerformaceDetails.setMcCode(rsInner.getString("MC_CDE"));
                    objEmployeePerformaceDetails.setShiftName(rsInner.getString("SHIFT_NAME"));
                    objEmployeePerformaceDetails.setWoNo(rsInner.getString("WO_NO"));
                    objEmployeePerformaceDetails.setDetails(rsInner.getString("DETAILS"));
                    objEmployeePerformaceDetails.setStartOpn(rsInner.getString("PROD_START_OPN"));
                    objEmployeePerformaceDetails.setEndOpn(rsInner.getString("PROD_END_OPN"));
                    objEmployeePerformaceDetails.setDtyHrs(rsInner.getFloat("DT_HRS"));
                    objEmployeePerformaceDetails.setOtHrs(rsInner.getFloat("OT_HRS"));
                    objEmployeePerformaceDetails.setStdHrs(rsInner.getFloat("PROD_STD_HRS"));
                    objEmployeePerformaceDetails.setOpnHrs(rsInner.getFloat("OPN_HRS"));
                    objEmployeePerformaceDetails.setIncntvSalHrs(rsInner.getFloat("INCNTV_SLRY_HRS"));
                    objEmployeePerformaceDetails.setDtySalHrs(rsInner.getFloat("DT_SLRY_HRS"));
                    objEmployeePerformaceDetails.setOtSalHrs(rsInner.getFloat("OT_SLRY_HRS"));
                    if(objEmployeePerformaceDetails.getStdHrs() < objEmployeePerformaceDetails.getOpnHrs())
                    {
                        objEmployeePerformaceDetails.setExceededHrs(objEmployeePerformaceDetails.getOpnHrs() - objEmployeePerformaceDetails.getStdHrs());
                    }
                    else if(objEmployeePerformaceDetails.getStdHrs() > objEmployeePerformaceDetails.getOpnHrs())
                    {
                        objEmployeePerformaceDetails.setSavedHrs(objEmployeePerformaceDetails.getStdHrs() - objEmployeePerformaceDetails.getOpnHrs());
                    }
                    totOpnHrs = totOpnHrs + objEmployeePerformaceDetails.getOpnHrs();
                    totStdHrs = totStdHrs + objEmployeePerformaceDetails.getStdHrs();
                    totSavedHrs = totSavedHrs + objEmployeePerformaceDetails.getSavedHrs();
                    totExceededHrs = totExceededHrs + objEmployeePerformaceDetails.getExceededHrs();
                    vec_Result.add(objEmployeePerformaceDetails);
                }
                rsInner.close();
                psInner.close();
                //objEmployeePerformanceDetailsList = new EmployeePerformaceDetails[vec_Result.size()];
                //vec_Result.copyInto(objEmployeePerformanceDetailsList);
                
                percentageSavedHrs = nf.format((totSavedHrs * 100)/totOpnHrs)+"%";
                percentageExceededHrs = nf.format((totExceededHrs * 100)/totOpnHrs)+"%";
                
                if(totStdHrs != totOpnHrs)
                netSavedHrsPercentage = nf.format(((totStdHrs - totOpnHrs)*100)/totOpnHrs)+"%";
                else
                    netSavedHrsPercentage = "0%";
                objEmployeePerformanceTotDetails.setTotOpnHrs(totOpnHrs);
                objEmployeePerformanceTotDetails.setTotStdHrs(totStdHrs);
                objEmployeePerformanceTotDetails.setTotSavedHrs(totSavedHrs);
                objEmployeePerformanceTotDetails.setTotExceededHrs(totExceededHrs);
                objEmployeePerformanceTotDetails.setSavedHrsPercentage(percentageSavedHrs);
                objEmployeePerformanceTotDetails.setExceededHrsPercentage(percentageExceededHrs);
                objEmployeePerformanceTotDetails.setNetHrsSavedPercentage(netSavedHrsPercentage);
                
                //vec_Return.add(objEmployeePerformanceDetailsList);
                vec_Return.add(objEmployeePerformanceTotDetails);
                //vec_Result.add(objEmployeePerformanceTotDetails);
                
                objEmployeePerformanceReturnDetails.setVecEmployeePerformanceDetails(vec_Result);
                objEmployeePerformanceReturnDetails.setVecEmployeePerformanceTotDetails(vec_Return);
             
                vec.add(objEmployeePerformanceReturnDetails);
            }
            rs.close();
            ps.close();
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        if(BuildConfig.DMODE)
        {
            System.out.println("EMPLOYEE PERFORMANCE DETAILS REPORTS ENDS");
        }
        logger.info("EMPLOYEE PERFORMANCE DETAILS REPORTS ENDS");
        
        objEmployeePerformanceReturnDetailsList = new EmployeePerformanceReturnDetails[vec.size()];
        vec.copyInto(objEmployeePerformanceReturnDetailsList);
        return objEmployeePerformanceReturnDetailsList;
}
    
    public ProductionOfMachineDetails[] fetchProductionOfMachineDetails(Date startDate,Date endDate,String mcCode) throws ReportsException, SQLException
    {
        ProductionOfMachineDetails[] objProductionOfMachineDetailsList = null;
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        ht.put("PROD_START_DATE",startDate);
        ht.put("PROD_END_DATE",endDate);
        ht.put("MC_CDE",mcCode);
        Vector vec_Result = new Vector();
        if(BuildConfig.DMODE)
        {
            System.out.println("PRODUCTION OF MACHINE DETAILS REPORTS STARTS");
        }
        logger.info("PRODUCTION OF MACHINE DETAILS REPORTS STARTS");
        
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_PRODUCTION_OF_MACHINE_SQL_QUERY,ht);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ProductionOfMachineDetails objProductionOfMachineDetails = new ProductionOfMachineDetails();
                objProductionOfMachineDetails.setProdCrntDate(rs.getTimestamp("PROD_CRNT_DATE"));
                objProductionOfMachineDetails.setShiftName(rs.getString("SHIFT_NAME"));
                objProductionOfMachineDetails.setWoNo(rs.getString("WO_NO"));
                objProductionOfMachineDetails.setJbDrwgNo(rs.getString("JB_DRWG_NO"));
                objProductionOfMachineDetails.setCustName(rs.getString("CUST_NAME"));
                objProductionOfMachineDetails.setJbName(rs.getString("JB_NAME"));
                objProductionOfMachineDetails.setProdTotQty(rs.getInt("PROD_TOT_QTY"));
                objProductionOfMachineDetails.setWoJbSno(rs.getInt("WOJB_SNO"));
                objProductionOfMachineDetails.setProdStartOpn(rs.getInt("PROD_START_OPN"));
                objProductionOfMachineDetails.setProdEndOpn(rs.getInt("PROD_END_OPN"));
                objProductionOfMachineDetails.setProdStdHrs(rs.getFloat("PROD_STD_HRS"));
                objProductionOfMachineDetails.setActualHrs(rs.getFloat("ACTUAL_HRS"));
                objProductionOfMachineDetails.setEmpName(rs.getString("EMP_NAME"));
                vec_Result.add(objProductionOfMachineDetails);
                
            }
            rs.close();
            ps.close();
            objProductionOfMachineDetailsList = new ProductionOfMachineDetails[vec_Result.size()];
            vec_Result.copyInto(objProductionOfMachineDetailsList);
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        if(BuildConfig.DMODE)
        {
            System.out.println("PRODUCTION OF MACHINE DETAILS REPORTS ENDS");
        }
        logger.info("PRODUCTION OF MACHINE DETAILS REPORTS ENDS");
        
        return objProductionOfMachineDetailsList;
        
    }
    
    public QuantityProducedDetails[] fetchQuantityProducedDetails(Date startDate,Date endDate) throws ReportsException, SQLException
    {
        QuantityProducedDetails[] objQuantityProducedDetailsList = null;
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        ht.put("PROD_START_DATE",startDate);
        ht.put("PROD_END_DATE",endDate);
        
        Vector vec_Result = new Vector();
        if(BuildConfig.DMODE)
        {
            System.out.println("QUANTITY PRODUCED DETAILS REPORTS STARTS");
        }
        logger.info("QUANTITY PRODUCED DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_QUANTITY_PRODUCED_SQL_QUERY,ht);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                QuantityProducedDetails objQuantityProducedDetails = new QuantityProducedDetails();
                objQuantityProducedDetails.setJbName(rs.getString("JB_NAME"));
                objQuantityProducedDetails.setJbDrwgNo(rs.getString("JB_DRWG_NO"));
                objQuantityProducedDetails.setQuantity(rs.getInt("QTY"));
                vec_Result.add(objQuantityProducedDetails);
                
            }
            rs.close();
            ps.close();
            objQuantityProducedDetailsList = new QuantityProducedDetails[vec_Result.size()];
            vec_Result.copyInto(objQuantityProducedDetailsList);
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        if(BuildConfig.DMODE)
        {
            System.out.println("QUANTITY PRODUCED DETAILS REPORTS ENDS");
        }
        logger.info("QUANTITY PRODUCED DETAILS REPORTS ENDS");
        
        return objQuantityProducedDetailsList;
        
    }
    public WoReferenceDetails[] fetchWoReferenceDetails(Date startDate,Date endDate,int custId) throws SQLException, ReportsException
    {
        WoReferenceDetails[] objWoReferenceDetailsList = null;
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        ht.put("PROD_START_DATE",startDate);
        ht.put("PROD_END_DATE",endDate);
        ht.put("CUST_ID",new Integer(custId));
        
        Vector vec_Result = new Vector();
        if(BuildConfig.DMODE)
        {
            System.out.println("WORK ORDER REFERENCE DETAILS REPORTS STARTS");
        }
        logger.info("WORK ORDER REFERENCE DETAILS REPORTS STARTS");
        
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.FETCH_WO_REFERENCE_SQL_QUERY,ht);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                //select WO_DATE,WO_NO,JB_NAME,JB_DRWG_NO,JB_RVSN_NO,JB_MATL_TYP,WOJB_QTY from WO_HEADER WH,WO_JB_HEADER WJH,JB_MSTR JM where WH.WO_ID = WJH.WO_ID and WJH.JB_ID = JM.JB_ID and CUST_ID = #CUST_ID# and WO_DATE between #PROD_START_DATE# and #PROD_END_DATE# order by WO_DATE,WO_NO
                WoReferenceDetails objWoReferenceDetails = new WoReferenceDetails();
                objWoReferenceDetails.setWoDate(rs.getTimestamp("WO_DATE"));
                objWoReferenceDetails.setWoNo(rs.getString("WO_NO"));
                objWoReferenceDetails.setJbName(rs.getString("JB_NAME"));
                objWoReferenceDetails.setJbDrwgNo(rs.getString("JB_DRWG_NO"));
                objWoReferenceDetails.setJbRvsnNo(rs.getString("JB_RVSN_NO"));
                objWoReferenceDetails.setJbMatlType(rs.getString("JB_MATL_TYP"));
                objWoReferenceDetails.setWoJbQty(rs.getInt("WOJB_QTY"));
                vec_Result.add(objWoReferenceDetails);
                
            }
            rs.close();
            ps.close();
            objWoReferenceDetailsList = new WoReferenceDetails[vec_Result.size()];
            vec_Result.copyInto(objWoReferenceDetailsList);
            
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        if(BuildConfig.DMODE)
        {
            System.out.println("WORK ORDER REFERENCE DETAILS REPORTS ENDS");
        }
        logger.info("WORK ORDER REFERENCE DETAILS REPORTS ENDS");
        
        
        return objWoReferenceDetailsList;
    }
    
    public double calculateMachineAvailHrs(Date fromDate,Date toDate) throws ReportsException, SQLException
    {

        
        DBConnection con = null;
        double availTotHrs = 0;
        Date currentDate = null;
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(fromDate);
        Hashtable ht = new Hashtable();
        try
        {
            con = DBConnectionFactory.getConnection();
            for (currentDate = gc.getTime();currentDate.compareTo(toDate)<=0;currentDate = gc.getTime())
            {
                ht.put("CURRENT_DATE",currentDate);
                if (BuildConfig.DMODE)
                    System.out.println("Date :"+currentDate);
                PreparedStatement ps_Date_NonAvblty = con.executeStatement(SQLMaster.CHECK_DATE_IN_CUSTOM_NON_AVBLTY_SQL_QUERY,ht);
                ResultSet rs_Date_NonAvblty = ps_Date_NonAvblty.executeQuery();
                if(rs_Date_NonAvblty.next())
                {
                    int count = rs_Date_NonAvblty.getInt(1);
                    if (count > 0)
                    {
                        //gc.setTime(currentDate);
                        gc.add(GregorianCalendar.DATE,1);
                        continue;
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
                while (rs_Date_Avblty.next())
                {
                    flg = true;
                    avbltyStartTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_STARTTIME");
                    avbltyEndTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_ENDTIME");
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
                    availTotHrs += hr + min;
                    
                    if(BuildConfig.DMODE)
                        System.out.println("availShiftHRs :"+availTotHrs);
                }
                rs_Date_Avblty.close();
                ps_Date_Custom_Avblty.close();
                if (BuildConfig.DMODE)
                    System.out.println("Flg :"+flg);
                if (flg)
                {
                    //gc.setTime(currentDate);
                    gc.add(GregorianCalendar.DATE,1);
                    continue;
                }
                
                int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                if (BuildConfig.DMODE)
                    System.out.println("Day :"+day);
                ht.put("DAY",new Integer(day));
                PreparedStatement ps_Date_BaseCal = con.executeStatement(SQLMaster.CHECK_DATE_IN_BASE_CAL,ht);
                ResultSet rs_Date_BaseCal = ps_Date_BaseCal.executeQuery();
                while (rs_Date_BaseCal.next())
                {
                    avbltyStartTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_STARTTIME");
                    avbltyEndTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_ENDTIME");
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
                    availTotHrs += hr + min;
                    
                    if(BuildConfig.DMODE)
                        System.out.println("availShiftHRs :"+availTotHrs);
                }
                rs_Date_BaseCal.close();
                ps_Date_BaseCal.close();
                
                gc.add(GregorianCalendar.DATE,1);
                
            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        return availTotHrs;
    
    }
    
    private double calculateMachineAvailHrs(int month, int year) throws SQLException, ReportsException
    {
        
        DBConnection con = null;
        //CustomAvbltyDetails objCustomAvbltyDetails = new CustomAvbltyDetails();
        //CustomNonAvbltyDetails objCustomNonAvbltyDetails = new CustomNonAvbltyDetails();
        double availTotHrs = 0;
        Date currentDate = null;
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.DATE,1);
        gc.set(GregorianCalendar.MONTH,month-1);
        gc.set(GregorianCalendar.YEAR,year);
        
        int mxDays = gc.getActualMaximum(GregorianCalendar.DATE);
        Hashtable ht = new Hashtable();
        try
        {
            con = DBConnectionFactory.getConnection();
            for(int i = 0;i<mxDays ;i++)
            {
                currentDate = gc.getTime();
                ht.put("CURRENT_DATE",currentDate);
                if (BuildConfig.DMODE)
                    System.out.println("Date :"+currentDate);
                PreparedStatement ps_Date_NonAvblty = con.executeStatement(SQLMaster.CHECK_DATE_IN_CUSTOM_NON_AVBLTY_SQL_QUERY,ht);
                ResultSet rs_Date_NonAvblty = ps_Date_NonAvblty.executeQuery();
                if(rs_Date_NonAvblty.next())
                {
                    int count = rs_Date_NonAvblty.getInt(1);
                    if (count > 0)
                    {
                        gc.setTime(currentDate);
                        gc.add(GregorianCalendar.DATE,1);
                        continue;
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
                while (rs_Date_Avblty.next())
                {
                    flg = true;
                    avbltyStartTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_STARTTIME");
                    avbltyEndTime = rs_Date_Avblty.getString("CUSTOM_AVBLTY_ENDTIME");
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
                    availTotHrs += hr + min;
                    
                    if(BuildConfig.DMODE)
                        System.out.println("availShiftHRs :"+availTotHrs);
                }
                rs_Date_Avblty.close();
                ps_Date_Custom_Avblty.close();
                if (BuildConfig.DMODE)
                    System.out.println("Flg :"+flg);
                if (flg)
                {
                    gc.setTime(currentDate);
                    gc.add(GregorianCalendar.DATE,1);
                    continue;
                }
                
                int day = gc.get(GregorianCalendar.DAY_OF_WEEK);
                if (BuildConfig.DMODE)
                    System.out.println("Day :"+day);
                ht.put("DAY",new Integer(day));
                PreparedStatement ps_Date_BaseCal = con.executeStatement(SQLMaster.CHECK_DATE_IN_BASE_CAL,ht);
                ResultSet rs_Date_BaseCal = ps_Date_BaseCal.executeQuery();
                while (rs_Date_BaseCal.next())
                {
                    avbltyStartTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_STARTTIME");
                    avbltyEndTime = rs_Date_BaseCal.getString("BASE_CAL_VAR_ENDTIME");
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
                    availTotHrs += hr + min;
                    
                    if(BuildConfig.DMODE)
                        System.out.println("availShiftHRs :"+availTotHrs);
                }
                rs_Date_BaseCal.close();
                ps_Date_BaseCal.close();
                gc.setTime(currentDate);
                gc.add(GregorianCalendar.DATE,1);
            }
        }
        catch(SQLException sqle)
        {
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        return availTotHrs;
    }
    
    private JobTimeDetails fetchJobQuantityDetails(int woJbId,JobTimeDetails objJobTimeDetails) throws SQLException, ReportsException
    {
        DBConnection con = null;
        Hashtable ht = new Hashtable();
        ResultSet rs_finshedQty = null;
        if (BuildConfig.DMODE)
            System.out.println("FETCH JOB QUANTITY DETAILS STARTS");
        logger.info("FETCH JOB QUANTITY DETAILS STARTS");
        if (BuildConfig.DMODE)
            System.out.println("WO JB ID :"+woJbId);
        ht.put("WOJB_ID",new Integer(woJbId));
        try
        {
            con = DBConnectionFactory.getConnection();
            PreparedStatement ps = con.executeStatement(SQLMaster.GET_FINISHED_QUANTITY,ht);
            rs_finshedQty = ps.executeQuery();
            String qtySnos = "";
            int totQty = 0;
            while(rs_finshedQty.next())
            {
                if (qtySnos.length() == 0)
                    qtySnos = rs_finshedQty.getInt(1)+"";
                else
                    qtySnos = qtySnos + "," + rs_finshedQty.getInt(1);
                
                totQty++;
                                
            }
            objJobTimeDetails.setJbFinishedQty(totQty+objJobTimeDetails.getJbFinishedQty());
            System.out.println("QtySnos:"+qtySnos);
            System.out.println("Qty:"+totQty);
            rs_finshedQty.close();
            ps.close();
            
            HashMap hmActualHrs = new HashMap();
            PreparedStatement ps_ActualHrs = con.executeStatement(SQLMaster.PROD_DETAILS_SELECT_SQL_QUERY,ht);
            ResultSet rs_ActualHrs = ps_ActualHrs.executeQuery();
            while(rs_ActualHrs.next())
            {
                float prodTotHrs = rs_ActualHrs.getFloat("PROD_TOT_HRS");
                String prodQtySnos = rs_ActualHrs.getString("PROD_QTY_SNOS");
                if (BuildConfig.DMODE)
                    System.out.println("ProdQtySnos :"+prodQtySnos);
                StringTokenizer st = new StringTokenizer(prodQtySnos,",");
                int count = 0;
                count = st.countTokens();
                prodTotHrs = prodTotHrs/count;
                while(st.hasMoreTokens())
                {
                    int qtySno = Integer.parseInt(st.nextElement().toString());
                    if ((","+qtySnos+",").indexOf(","+qtySno+",") != -1)
                        if(hmActualHrs.containsKey(new Integer(qtySno)))
                            hmActualHrs.put(new Integer(qtySno),new Float(((Float)hmActualHrs.get(new Integer(qtySno))).floatValue()+prodTotHrs));
                        else
                            hmActualHrs.put(new Integer(qtySno),new Float(prodTotHrs));
                }
            }
            
            rs_ActualHrs.close();
            ps_ActualHrs.close();
            
            List valueList = new ArrayList(hmActualHrs.values());
            Collections.sort(valueList);
            int size = valueList.size(); 
            if(size > 0)
                if(( (Float)valueList.get(0)).floatValue() < objJobTimeDetails.getMinTimeTaken()  )
                    objJobTimeDetails.setMinTimeTaken(( (Float)valueList.get(0)).floatValue());
            if(size > 0)
                if(( (Float)valueList.get(size-1)).floatValue() > objJobTimeDetails.getMaxTimeTaken()  )
                    objJobTimeDetails.setMaxTimeTaken(( (Float)valueList.get(size-1)).floatValue());
                
                
            float total = 0;
            for(int k = 0;k<valueList.size();k++)
            {
                total = total + ((Float)valueList.get(k)).floatValue();
            }
            float avgTimeTaken = 0;
            if(totQty>0)
                avgTimeTaken = total/totQty;
            if(BuildConfig.DMODE)
            {
                System.out.println("Finished Qty :"+totQty);
                System.out.println("Avg Time Taken :"+avgTimeTaken);
            }
            if(objJobTimeDetails.getAvgTimeTaken()!=0)
                objJobTimeDetails.setAvgTimeTaken((objJobTimeDetails.getAvgTimeTaken()+avgTimeTaken)/2);
            else
                objJobTimeDetails.setAvgTimeTaken(avgTimeTaken);
            
            if(size > 0)
                if(objJobTimeDetails.getMinTimeTaken()==0)
                    objJobTimeDetails.setMinTimeTaken(( (Float)valueList.get(0)).floatValue());
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
            if(BuildConfig.DMODE)
                sqle.printStackTrace();
            throw new ReportsException("","CONNECTION CLOSING EXCEPTION",sqle.toString());
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
        
        
        return objJobTimeDetails;
    }
    
    public static void  main(String args[]) throws ParseException, ReportsException, SQLException, ProductionException
    {
        ReportsDetailsManager objReportsDetailsManager = new ReportsDetailsManager();
        /*HalfYearlyProductionReportDetail[] objHalf = objReportsDetailsManager.fetchHalfYearlyProductionReport("PM_M-01",8,2005);
        System.out.println("Length:"+objHalf.length);
        for (int i = 0; i < objHalf.length;i++)
        {
            HalfYearlyProductionReportDetail objHalfYrly = objHalf[i];
            System.out.println("Data :"+objHalfYrly.getDesription());
        }*/
        //System.out.println(objReportsDetailsManager.calculateMachineAvailHrs(new Date("01-oct-05"),new Date("31-oct-05")));
        
        JobTimeDetails objJobTimeDetails = new JobTimeDetails();
        objJobTimeDetails = objReportsDetailsManager.fetchJobQuantityDetails(1813,objJobTimeDetails);
        System.out.println(objJobTimeDetails.getMinTimeTaken());
        System.out.println(objJobTimeDetails.getMaxTimeTaken());
        System.out.println(objJobTimeDetails.getAvgTimeTaken());
        
        /*SalaryReportDetails[] objDet = objReportsDetailsManager.fetchSalaryReports(8,2005);
        for(int i = 0; i < objDet.length;i++)
        {
            SalaryReportDetails  objSalaryReportDetails = objDet[i];
            SalHrsObjDetails  objSalHrsObjDetails = new SalHrsObjDetails();
            IncHrsObjDetails  objIncHrsObjDetails = new IncHrsObjDetails();
            OtHrsObjDetails objOtHrsObjDetails = new OtHrsObjDetails();
            objSalHrsObjDetails = objSalaryReportDetails.getObjSalHrsDetails();
            objIncHrsObjDetails = objSalaryReportDetails.getObjIncHrsDetails();
            System.out.println("emp Name:"+objSalaryReportDetails.getEmpName());
            objOtHrsObjDetails = objSalaryReportDetails.getObjOtHrsDetails();
            System.out.println("day11 :"+objSalHrsObjDetails.getDay1());
            System.out.println("day21 :"+objIncHrsObjDetails.getDay1());
            System.out.println("day31 :"+objOtHrsObjDetails.getDay1());
            
            System.out.println("day12 :"+objSalHrsObjDetails.getDay2());
            System.out.println("day22 :"+objIncHrsObjDetails.getDay2());
            System.out.println("day32 :"+objOtHrsObjDetails.getDay2());
            
            System.out.println("day13 :"+objSalHrsObjDetails.getDay3());
            System.out.println("day23 :"+objIncHrsObjDetails.getDay3());
            System.out.println("day33 :"+objOtHrsObjDetails.getDay3());
            
            
            
        }
        
        //ProductionDateWiseDetails[] obj = objReportsDetailsManager.fetchProductionDetails(new Date("01-jun-05"),new Date("01-jun-05"));
        //System.out.println("len :"+obj.length);
        
        //ProductionVersionWiseDetails[] objv = obj[0].getArrProductionVersionWiseDetails();
        /* ProductionMachineWiseDetails[] obj = objReportsDetailsManager.fetchProductionMachineWiseDetails(new Date("01-jun-05"),new Date("10-jun-05"));
         for(int i = 0;i<obj.length;i++)
         {
         System.out.println("jbName :"+obj[i].getJobName());
         Vector vec = obj[i].getVecEmployeeDetails();
         
         for(int j = 0;j<vec.size();j++)
         {
         EmployeeDtyOtDetails o = (EmployeeDtyOtDetails)vec.get(j);
         System.out.println("EmpName :"+o.getEmpName());
         }
         }
         */
        /*EmployeePayrollDetails[] objEmployeeDetailsList = objReportsDetailsManager.fetchEmployeePayrollDetails(238);
        for(int i = 0;i<objEmployeeDetailsList.length;i++)
        {
            System.out.println("EmpName :"+objEmployeeDetailsList[i].getEmpName());
            
            Vector vec = objEmployeeDetailsList[i].getVecShiftWiseDetails();
            for(int j = 0;j<vec.size();j++)
            {
                PayrollDetails obj = (PayrollDetails)vec.get(j);
                System.out.println("DutyHrs :"+obj.getDutyHrs() );
                System.out.println("OtHrs :" +obj.getOtHrs());
                System.out.println("DutysalHrs :"+obj.getRglrSalHrs());
                System.out.println("OtSalHrs :"+obj.getOtSalHrs() );
                System.out.println("IncesalHrs :"+obj.getIncentiveSalHrs() );
                System.out.println("version :" +obj.getVersion());
                System.out.println("createdby :"+obj.getCreatedBy() );
                System.out.println("proddate :" +obj.getProdDate());
                System.out.println("shiftName :" +obj.getShiftName());
                
            }
        }
        */
        
        
        
        
        //SalaryReportDetails[]  objSalaryReportDetails = new SalaryReportDetails[10];
        //objSalaryReportDetails = objReportsDetailsManager.fetchSalaryReports(2,2005);
        //System.out.println(objSalaryReportDetails.length);
        //System.out.println(objSalaryReportDetails[0].getEmpName());
        //MonthlyReportDetails[] objMonthlyReportDetails = new MonthlyReportDetails[10];
        //objMonthlyReportDetails = objReportsDetailsManager.fetchMonthlyReportsDetails(2,2005);
        //System.out.println(objMonthlyReportDetails.length);
        //System.out.println(objMonthlyReportDetails[0].getEmpName());
        
        // ProductionAccountingDateDetails obj = objReportsDetailsManager.fetchProductionAccountingDateDetails(new Date("01-mar-05"));
        // System.out.println("VEctor :"+obj.getVecProductionAccountingMachineDetails().size());
        // Vector v = objReportsDetailsManager.fetchSalaryReports(new Date("01-jan-05"),new Date("01-mar-05"));
        // System.out.println(v.size());
        //  MachineUtilizationReportDetails[] MachineUtilizationReportDetailsList; 
        // MachineUtilizationReportDetailsList = objReportsDetailsManager.fetchMachineUtilization(new Date("01-jan-05"),new Date("01-mar-05"));
        // System.out.println("len :"+MachineUtilizationReportDetailsList.length);
        //System.out.println(MachineUtilizationReportDetailsList[0].getUtiPer());
        //IdleBrkdwnDetails[] objIdleBrkdwnDetails = new IdleBrkdwnDetails[2];
        //objIdleBrkdwnDetails = objReportsDetailsManager.fetchIdleBrkdwnHrs(new Date("01-jan-04"),new Date("01-jan-06"));
        //System.out.println(objIdleBrkdwnDetails[0].getMcCde());
        //System.out.println(objIdleBrkdwnDetails[0].getJobHrs());
        //MonthlyPercentageDetails[] monthlyPercentageDetailsList;
        //HalfYearlyPercentageDetails[]  obj;
        //obj = objReportsDetailsManager.fetchHalfYearlyPercentageOfDetails("PM-M-01",8,2005);
        //System.out.println(monthlyPercentageDetailsList[0].getBrkDwnElecPer());
        /*TopSheetDetails[] objTopSheetDetails = new TopSheetDetails[50];
         objTopSheetDetails = objReportsDetailsManager.fetchTopSheetDetails();
         System.out.println("len :" +objTopSheetDetails.length);
         for(int i = 0;i<objTopSheetDetails.length;i++)
         {
         System.out.println(objTopSheetDetails[i].getCustName());
         System.out.println(objTopSheetDetails[i].getJobName());
         System.out.println(objTopSheetDetails[i].getDrwgNo());
         System.out.println(objTopSheetDetails[i].getDrlHrs());
         System.out.println(objTopSheetDetails[i].getVtlHrs());
         System.out.println(objTopSheetDetails[i].getHbHrs());
         
         }    
         
         int KanN = 0;
         KanN = KanN+1;*/
        
        //HalfYearlyProductionReportDetail[] obj = new HalfYearlyProductionReportDetail[10];
        //obj = objReportsDetailsManager.fetchHalfYearlyProductionReport("PM-M-01",1,2005);
        // for(int i = 0;i<obj.length;i++)
        //{
        //   System.out.println(obj[i].getDesription());
        //  System.out.println(obj[i].getMonth1());
        //}
        
        
        //for(int i = 0;i<objTopSheetDetails.length;i++)
        //{
        //  System.out.println(objTopSheetDetails[i].getCustId());
        // System.out.println(objTopSheetDetails[i].getCustName());
        //System.out.println(objTopSheetDetails[i].getJobName());
        //System.out.println(objTopSheetDetails[i].getDrwgNo());
        //System.out.println(objTopSheetDetails[i].getVtlHrs());
        //System.out.println(objTopSheetDetails[i].getHbHrs());
        //System.out.println(objTopSheetDetails[i].getDrlHrs());
        //int i = 999999999;
        //String i = "123456789"; 
        //Integer.
    }
    
}


/*
*$Log: ReportsDetailsManager.java,v $
*Revision 1.99  2005/12/26 13:30:30  vkrishnamoorthy
*EmployeePerformance for chart added.
*
*Revision 1.98  2005/11/30 06:03:35  kduraisamy
*BuildConfig.DMODE ADDED.
*
*Revision 1.97  2005/11/29 10:56:45  kduraisamy
*monthly report validation report added.
*
*Revision 1.96  2005/11/29 08:52:02  kduraisamy
*monthly report validation report added.
*
*Revision 1.95  2005/11/29 07:06:27  kduraisamy
*monthly report validation report added.
*
*Revision 1.94  2005/11/29 06:31:48  kduraisamy
*monthly report validation report added.
*
*Revision 1.93  2005/11/29 05:51:37  kduraisamy
*monthly report validation report added.
*
*Revision 1.92  2005/11/29 05:24:32  kduraisamy
*monthly report validation report added.
*
*Revision 1.91  2005/11/29 05:14:14  kduraisamy
*monthly report validation report added.
*
*Revision 1.90  2005/11/26 07:14:26  kduraisamy
*fetchQtyDetails() method error corrected.
*
*Revision 1.89  2005/11/21 12:29:27  kduraisamy
*fetchMonthlyReport() method optimized.
*
*Revision 1.88  2005/11/21 10:53:55  kduraisamy
*fetchMonthlyReport() method optimized.
*
*Revision 1.87  2005/11/10 07:04:11  kduraisamy
*ProductionAccounting Added.
*
*Revision 1.86  2005/11/03 06:07:27  kduraisamy
*unwanted parameter removed from filter of fetchQuantityDetails().
*
*Revision 1.85  2005/10/26 11:56:29  kduraisamy
*netSavedHrs included with percentage in employeePerformance reports.
*
*Revision 1.84  2005/10/25 09:17:32  kduraisamy
*connection closed properly in fetchQuantityDetails().
*
*Revision 1.83  2005/10/20 12:38:34  vkrishnamoorthy
*Indentation.
*
*Revision 1.82  2005/10/20 12:06:05  kduraisamy
*DUPLICATE JB NAME AND DWG NO REMOVED IN JB QTY DETAILS.
*
*Revision 1.80  2005/10/20 07:05:05  kduraisamy
*DUPLICATE JB NAME AND DWG NO REMOVED IN JB QTY DETAILS.
*
*Revision 1.79  2005/10/20 06:51:51  vkrishnamoorthy
*Indentation.
*
*Revision 1.78  2005/10/20 06:45:40  kduraisamy
*DUPLICATE JB NAME AND DWG NO REMOVED IN JB QTY DETAILS.
*
*Revision 1.77  2005/10/20 06:39:39  kduraisamy
*DUPLICATE JB NAME AND DWG NO REMOVED IN JB QTY DETAILS.
*
*Revision 1.76  2005/10/20 05:08:34  vkrishnamoorthy
*Job quantities method modified for displaying data in order.
*
*Revision 1.75  2005/10/18 07:08:54  vkrishnamoorthy
*Return type changed into object array for fetchJobTimeDetails().
*
*Revision 1.74  2005/10/18 06:41:23  kduraisamy
*finished quantity added for reports.
*
*Revision 1.73  2005/10/18 06:09:09  kduraisamy
*finished quantity added for reports.
*
*Revision 1.71  2005/10/11 07:20:38  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.70  2005/10/11 06:29:38  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.69  2005/10/11 06:05:17  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.68  2005/10/10 10:49:08  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.67  2005/10/10 09:58:30  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.66  2005/09/26 09:34:12  kduraisamy
*half yearly percentage of production modified.
*
*Revision 1.65  2005/09/26 08:45:09  kduraisamy
*half yearly percentage of production modified.
*
*Revision 1.64  2005/09/24 11:15:19  kduraisamy
*monthly report calculation changed.
*
*Revision 1.63  2005/09/24 11:13:46  kduraisamy
*monthly report calculation changed.
*
*Revision 1.62  2005/09/24 10:41:42  kduraisamy
*field prodAccountedHrs added.
*
*Revision 1.61  2005/09/24 09:25:32  kduraisamy
*monthly report calculation changed.
*
*Revision 1.60  2005/09/23 14:35:00  kduraisamy
*calculateMachineAvailHrs() include.
*
*Revision 1.58  2005/09/08 12:41:28  kduraisamy
*for leave calculation dthrs is taken.
*
*Revision 1.57  2005/09/08 12:24:34  kduraisamy
*for leave calculation dthrs is taken.
*
*Revision 1.56  2005/09/08 04:57:05  kduraisamy
*val = "" is changed to val = " ".
*
*Revision 1.55  2005/09/08 04:47:42  kduraisamy
*val = "" is changed to val = " ".
*
*Revision 1.54  2005/09/07 14:52:53  kduraisamy
*0.0 hrs replaced with "".
*
*Revision 1.53  2005/07/12 11:39:20  kduraisamy
**** empty log message ***
*
*Revision 1.52  2005/06/13 11:59:13  kduraisamy
*PA_COUNT IS SET TO i-1.
*
*Revision 1.51  2005/06/13 09:37:47  vkrishnamoorthy
*resultset.next() included.
*
*Revision 1.50  2005/06/11 08:09:30  kduraisamy
*unwanted imports removed.
*
*Revision 1.49  2005/06/11 08:06:26  kduraisamy
*fetchEmployeePayrollDetails() added.
*
*Revision 1.48  2005/06/10 13:19:55  kduraisamy
*time removed in date.
*
*Revision 1.47  2005/06/09 15:14:45  kduraisamy
*modify count + 1 is added for version.
*
*Revision 1.46  2005/06/09 15:12:44  kduraisamy
*N is changed to Normal and DateStamp added to object.
*
*Revision 1.45  2005/06/09 14:29:52  kduraisamy
*report error corrected.
*
*Revision 1.44  2005/06/09 07:40:47  vkrishnamoorthy
*indentation.
*
*Revision 1.43  2005/06/09 06:17:22  kduraisamy
*WONO AND MATL TYPE SET TO OBJECT.
*
*Revision 1.42  2005/06/09 06:06:26  kduraisamy
*fetchMachineWiseDetails() added.
*
*Revision 1.41  2005/06/03 08:15:06  kduraisamy
*array index out of bound error corrected while handling array actualHrs[].
*
*Revision 1.40  2005/06/03 07:44:41  kduraisamy
*reports added for actualHrs Select .
*
*Revision 1.39  2005/06/02 09:57:18  kduraisamy
*fetchProductionDatewiseDetails() added.
*
*Revision 1.38  2005/06/02 03:49:55  kduraisamy
*mc Name added for display.
*
*Revision 1.37  2005/06/01 12:36:28  kduraisamy
*array and vector added .
*
*Revision 1.36  2005/05/31 13:09:42  kduraisamy
* fetchProductionAccountingDetails added.
*
*Revision 1.35  2005/05/15 06:30:17  kduraisamy
*println addded...
*
*Revision 1.34  2005/05/14 11:42:29  kduraisamy
*fetchquantityProduced() added.
*
*Revision 1.33  2005/05/14 11:37:41  kduraisamy
*signature for fetchquantityProduced() added.
*
*Revision 1.32  2005/05/14 11:19:18  kduraisamy
*fetchquantityProduced() added.
*
*Revision 1.31  2005/05/14 10:58:13  kduraisamy
*employee performance reports added.
*
*Revision 1.30  2005/05/14 10:16:21  kduraisamy
*employee performance reports added.
*
*Revision 1.29  2005/05/11 13:04:35  kduraisamy
*unwanted println removed.
*
*Revision 1.28  2005/05/11 12:50:02  kduraisamy
*unwanted println removed.
*
*Revision 1.27  2005/04/19 13:13:34  kduraisamy
*resultset properly closed.
*
*Revision 1.26  2005/04/18 12:28:20  kduraisamy
*executeStatement() return type changed.
*
*Revision 1.25  2005/04/16 09:26:44  kduraisamy
*Result set properly closed.
*
*Revision 1.24  2005/04/16 07:49:58  kduraisamy
*logger info and System.out.println() added.
*
*Revision 1.23  2005/04/07 09:20:36  kduraisamy
*MODE IS CHANGED TO DMODE.
*
*Revision 1.22  2005/04/07 08:17:12  kduraisamy
*indentation.
*
*Revision 1.21  2005/04/07 07:36:34  kduraisamy
*if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.
*
*Revision 1.20  2005/03/10 07:27:36  kduraisamy
*CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.
*
*getDate is changed to getTimestamp().
*
*Revision 1.19  2005/03/04 12:34:24  kduraisamy
*DBConnection Changes made.
*
*Revision 1.18  2005/02/27 11:06:17  kduraisamy
*totHrs added in value objects.
*
*Revision 1.17  2005/02/27 10:49:16  kduraisamy
*trunc added in fetchMonthlyReport().
*
*Revision 1.16  2005/02/27 10:47:18  kduraisamy
*toDate increased by 1 .
*
*Revision 1.15  2005/02/27 09:13:23  kduraisamy
*error corrected.
*
*Revision 1.14  2005/02/27 07:57:06  kduraisamy
*rs.next() added.
*
*Revision 1.13  2005/02/27 06:52:20  kduraisamy
*method added for fetchMonthlyReport().
*
*Revision 1.11  2005/02/26 12:52:38  kduraisamy
*method added for fetchSalaryReport().
*
*Revision 1.10  2005/02/26 12:36:09  kduraisamy
*method added for fetchSalaryReport().
*
*Revision 1.9  2005/02/26 08:38:45  kduraisamy
*comment removed. in HalfYearlyProductionReportDetails().
*
*Revision 1.8  2005/02/26 05:55:56  kduraisamy
*unwanted line removed.
*
*Revision 1.7  2005/02/26 05:04:54  kduraisamy
*unwanted line removed.
*
*Revision 1.6  2005/02/25 11:10:00  kduraisamy
*fetchHalfYearlyProdnDetails() added.
*
*Revision 1.5  2005/02/24 09:37:53  kduraisamy
*error in calling query changed.
*
*Revision 1.4  2005/02/23 05:29:20  kduraisamy
*initial commit of MonthlyReportsDetails and
*ReportsDatewiseDetails.
*
*Revision 1.3  2005/02/22 06:15:07  kduraisamy
*methods halfYearlyPercentageDetails() added.
*
*Revision 1.2  2005/02/14 11:10:05  kduraisamy
*unwanted cast removed.
*
*Revision 1.1  2005/02/11 06:55:40  kduraisamy
*initial commit.

*
*/