/*
 * Created on Aug 16, 2005
 *
 * ClassName	:  	DataMigarationReportsDetailsManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;

/**
 * @author kduraisamy
 *
 */
public class DataMigarationReportsDetailsManager
{
    
    public MigrationReportDetails[] fetchMigrationReports() throws SQLException
    {
        MigrationReportDetails[] objMigrationReportDetailsList = null;
        Vector vecResult = new Vector();
        
        //customer 
        MigrationReportDetails objCustomerRecordCount = new MigrationReportDetails();
        objCustomerRecordCount.setTitle("CUSTOMER MASTER RECORD COUNT");
        objCustomerRecordCount.setHeader1("Result");
        objCustomerRecordCount.setHeader2("Old Customer Count");
        objCustomerRecordCount.setHeader3("New Customer Count");
        
        DBConnection con = DBConnectionFactory.getConnection();
        PreparedStatement ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount oldcustomercount,B.RecordCount newcustomercount from (select count(*) RecordCount from movedb.Customer_Master) A, (select count(*) RecordCount from CUST_MSTR)B");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            objCustomerRecordCount.setValue1(rs.getInt(1)+"");
            objCustomerRecordCount.setValue2(rs.getInt(2)+"");
            objCustomerRecordCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objCustomerRecordCount);
        
        //customer validation
        MigrationReportDetails objCustomerIdSum = new MigrationReportDetails();
        objCustomerIdSum.setTitle("CUSTOMER MASTER VALIDATION (HASH TOTAL-CUST_ID)");
        objCustomerIdSum.setHeader1("Result");
        objCustomerIdSum.setHeader2("Old Customer Id Sum");
        objCustomerIdSum.setHeader3("New Customer Id Sum");
        
        ps = con.executeStatement("select A.sumCustId = B.sumCustId Result,A.sumCustId OldSumCustId,B.sumCustId NewSumCustId from (select sum(customer_id) sumCustId from movedb.Customer_Master)A,(select sum(CUST_ID) sumCustId FROM CUST_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objCustomerIdSum.setValue1(rs.getInt(1)+"");
            objCustomerIdSum.setValue2(rs.getInt(2)+"");
            objCustomerIdSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objCustomerIdSum);
        
        
        //      employee
        MigrationReportDetails objEmployeeCount = new MigrationReportDetails();
        objEmployeeCount.setTitle("EMPLOYEE MASTER RECORD COUNT");
        objEmployeeCount.setHeader1("Result");
        objEmployeeCount.setHeader2("Old Employee Count");
        objEmployeeCount.setHeader3("New Employee Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldEmployeeCount,B.RecordCount NewEmployeeCount from (select count(*) RecordCount from movedb.Employee_Master) A, (select count(*) RecordCount from EMP_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objEmployeeCount.setValue1(rs.getInt(1)+"");
            objEmployeeCount.setValue2(rs.getInt(2)+"");
            objEmployeeCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objEmployeeCount);
        
        //      employee validation
        MigrationReportDetails objEmployeeInserviceSum = new MigrationReportDetails();
        objEmployeeInserviceSum.setTitle("EMPLOYEE MASTER VALIDATION (HASH TOTAL - EMP INSERVICE)");
        objEmployeeInserviceSum.setHeader1("Result");
        objEmployeeInserviceSum.setHeader2("Old Employee Inservice Sum");
        objEmployeeInserviceSum.setHeader3("New Employee Inservice Sum");
        
        ps = con.executeStatement("select A.sumEmpInService = B.sumEmpInService Result,A.sumEmpInService OldSumEmpInService,B.sumEmpInService NewSumEmpInService from (select sum(emp_inservice) sumEmpInService from movedb.Employee_Master)A,(select sum(EMP_INSRVCE) sumEmpInService FROM EMP_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objEmployeeInserviceSum.setValue1(rs.getInt(1)+"");
            objEmployeeInserviceSum.setValue2(rs.getInt(2)+"");
            objEmployeeInserviceSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objEmployeeInserviceSum);
        
        
        //  machine
        MigrationReportDetails objMachineCount = new MigrationReportDetails();
        objMachineCount.setTitle("MACHINE MASTER RECORD COUNT");
        objMachineCount.setHeader1("Result");
        objMachineCount.setHeader2("Old Machine Count");
        objMachineCount.setHeader3("New Machine Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldMachineCount,B.RecordCount NewMachineCount from (select count(*) RecordCount from movedb.Machine_Master) A, (select count(*) RecordCount from MC_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objMachineCount.setValue1(rs.getInt(1)+"");
            objMachineCount.setValue2(rs.getInt(2)+"");
            objMachineCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objMachineCount);
        
        // machine validation
        MigrationReportDetails objMachineInUseSum = new MigrationReportDetails();
        objMachineInUseSum.setTitle("MACHINE MASTER VALIDATION (HASH TOTAL -MC IN USE)");
        objMachineInUseSum.setHeader1("Result");
        objMachineInUseSum.setHeader2("Old Machine In Use Sum");
        objMachineInUseSum.setHeader3("New Machine In Use Sum");
        
        ps = con.executeStatement("select A.sumMachineInUse = B.sumMachineInUse Result,A.sumMachineInUse OldSumMachineInUse,B.sumMachineInUse NewSumMachineInUse from (select sum(machine_inuse) sumMachineInUse from movedb.Machine_Master)A,(select sum(MC_INUSE) sumMachineInUse FROM MC_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objMachineInUseSum.setValue1(rs.getInt(1)+"");
            objMachineInUseSum.setValue2(rs.getInt(2)+"");
            objMachineInUseSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objMachineInUseSum);
        
        //opn gp machine
        MigrationReportDetails objOpnGrpMcCount = new MigrationReportDetails();
        objOpnGrpMcCount.setTitle("OPERATION GROUP MC RECORD COUNT");
        objOpnGrpMcCount.setHeader1("Result");
        objOpnGrpMcCount.setHeader2("Old Opn Group Machine Count");
        objOpnGrpMcCount.setHeader3("New Opn Group Machine Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldOperationGroupMachine,B.RecordCount NewOperationGroupMachine from (select count(*) RecordCount from movedb.Operation_Group_Master) A, (select count(*) RecordCount from OPN_GP_MC)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objOpnGrpMcCount.setValue1(rs.getInt(1)+"");
            objOpnGrpMcCount.setValue2(rs.getInt(2)+"");
            objOpnGrpMcCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objOpnGrpMcCount);
        
        //job
        MigrationReportDetails objJobCount = new MigrationReportDetails();
        objJobCount.setTitle("JOB MASTER RECORD COUNT");
        objJobCount.setHeader1("Result");
        objJobCount.setHeader2("Old Job Count");
        objJobCount.setHeader3("New Job Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldJobCount,B.RecordCount NewJobCount from (select count(*) RecordCount from movedb.Job_Master) A, (select count(*) RecordCount from JB_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objJobCount.setValue1(rs.getInt(1)+"");
            objJobCount.setValue2(rs.getInt(2)+"");
            objJobCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objJobCount);
        
//      job validation
        MigrationReportDetails objJobCustIdSum = new MigrationReportDetails();
        objJobCustIdSum.setTitle("JOB MASTER VALIDATION (HASH TOTAL -CUST_ID)");
        objJobCustIdSum.setHeader1("Result");
        objJobCustIdSum.setHeader2("Old Job Cust Id Sum");
        objJobCustIdSum.setHeader3("New Job Cust Id Sum");
        
        ps = con.executeStatement("select A.sumCustId = B.sumCustId Result,A.sumCustId OldSumCustIdInJobMaster,B.sumCustId NewSumCustIdInJobMaster from (select sum(customer_id) sumCustId from movedb.Job_Master)A,(select sum(CUST_ID) sumCustId from JB_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objJobCustIdSum.setValue1(rs.getInt(1)+"");
            objJobCustIdSum.setValue2(rs.getInt(2)+"");
            objJobCustIdSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objJobCustIdSum);
        
//      job validation
        MigrationReportDetails objJobRvsnNoSum = new MigrationReportDetails();
        objJobRvsnNoSum.setTitle("JOB MASTER VALIDATION (HASH TOTAL -JB RVSN NO)");
        objJobRvsnNoSum.setHeader1("Result");
        objJobRvsnNoSum.setHeader2("Old Job Rvsn no Sum");
        objJobRvsnNoSum.setHeader3("New Job Rvsn no Sum");
        
        ps = con.executeStatement("select A.sumRevisionNo = B.sumRevisionNo Result,A.sumRevisionNo OldsumRevisionNo,B.sumRevisionNo NewsumRevisionNo from (select sum(revision_no) sumRevisionNo from movedb.Job_Master)A,(select sum(JB_RVSN_NO) sumRevisionNo FROM JB_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objJobRvsnNoSum.setValue1(rs.getInt(1)+"");
            objJobRvsnNoSum.setValue2(rs.getInt(2)+"");
            objJobRvsnNoSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objJobRvsnNoSum);
        
        
//      job operation master 
        MigrationReportDetails objJobOpnCount = new MigrationReportDetails();
        objJobOpnCount.setTitle("JOB OPERATION MASTER RECORD COUNT");
        objJobOpnCount.setHeader1("Result");
        objJobOpnCount.setHeader2("Old Job Operation Count");
        objJobOpnCount.setHeader3("New Job Operation Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldJobOperationCount,B.RecordCount NewJobOperationCount from (select count(*) RecordCount from movedb.Job_Operation_Master) A, (select count(*) RecordCount from JB_OPN_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objJobOpnCount.setValue1(rs.getInt(1)+"");
            objJobOpnCount.setValue2(rs.getInt(2)+"");
            objJobOpnCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objJobOpnCount);
        
//      job operation master validation 
        MigrationReportDetails objJobOpnStdHrsSum = new MigrationReportDetails();
        objJobOpnStdHrsSum.setTitle("JOB OPERATION MASTER VALIDATION (HASH TOTAL - STD HRS)");
        objJobOpnStdHrsSum.setHeader1("Result");
        objJobOpnStdHrsSum.setHeader2("Old Job Operation Std Hrs Sum");
        objJobOpnStdHrsSum.setHeader3("New Job Operation Std Hrs Sum");
        
        ps = con.executeStatement("select A.sumStdHrs = B.sumStdHrs Result,A.sumStdHrs OldsumStdHrs,B.sumStdHrs NewsumStdHrs from (select sum(std_hrs) sumStdHrs from movedb.Job_Operation_Master)A,(select sum(JBOPN_STDHRS) sumStdHrs from JB_OPN_MSTR)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objJobOpnStdHrsSum.setValue1(rs.getInt(1)+"");
            objJobOpnStdHrsSum.setValue2(rs.getInt(2)+"");
            objJobOpnStdHrsSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objJobOpnStdHrsSum);

//      workorder 
        MigrationReportDetails objWoNoCount = new MigrationReportDetails();
        objWoNoCount.setTitle("WORKORDER HEADER RECORD COUNT");
        objWoNoCount.setHeader1("Result");
        objWoNoCount.setHeader2("Old Workorders Count");
        objWoNoCount.setHeader3("New Workorders Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldWorkordersCount,B.RecordCount NewWorkordersCount from (select count(*) RecordCount from (select * from movedb.Workorder_Header wh where wh.wo_date between '2005-06-01' and '2005-08-15' group by wo_number)A) A, (select count(*) RecordCount from WO_HEADER)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objWoNoCount.setValue1(rs.getInt(1)+"");
            objWoNoCount.setValue2(rs.getInt(2)+"");
            objWoNoCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objWoNoCount);

//      workorder job 
        MigrationReportDetails objWoJbCount = new MigrationReportDetails();
        objWoJbCount.setTitle("WORKORDER JOB HEADER RECORD COUNT");
        objWoJbCount.setHeader1("Result");
        objWoJbCount.setHeader2("Old Workorder Jobs Count");
        objWoJbCount.setHeader3("New Workorder Jobs Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldWorkordersJobCount,B.RecordCount NewWorkordersJobCount from (select count(*) RecordCount from movedb.Workorder_Header wh where wh.wo_date between '2005-06-01' and '2005-08-15') A, (select count(*) RecordCount from WO_JB_HEADER)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objWoJbCount.setValue1(rs.getInt(1)+"");
            objWoJbCount.setValue2(rs.getInt(2)+"");
            objWoJbCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objWoJbCount);
        
//      workorder job qty 
        MigrationReportDetails objWoJbQtyCount = new MigrationReportDetails();
        objWoJbQtyCount.setTitle("WORKORDER JOB QTY RECORD COUNT");
        objWoJbQtyCount.setHeader1("Result");
        objWoJbQtyCount.setHeader2("Old Workorder Job Qty Count");
        objWoJbQtyCount.setHeader3("New Workorder Jobs Qty Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldWorkordersJobQtyCount,B.RecordCount NewWorkordersJobQtyCount from (select count(*) RecordCount from movedb.Workorder_Extension we,movedb.Workorder_Header wh where we.wo_id = wh.wo_id and wh.wo_date between '2005-06-01' and '2005-08-15') A, (select count(*) RecordCount from WO_JB_STAT)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objWoJbQtyCount.setValue1(rs.getInt(1)+"");
            objWoJbQtyCount.setValue2(rs.getInt(2)+"");
            objWoJbQtyCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objWoJbQtyCount);
        
//      workorder job opn
        MigrationReportDetails objWoJbOpnCount = new MigrationReportDetails();
        objWoJbOpnCount.setTitle("WORKORDER JOB OPERATION RECORD COUNT");
        objWoJbOpnCount.setHeader1("Result");
        objWoJbOpnCount.setHeader2("Old Workorder Job Opns Count");
        objWoJbOpnCount.setHeader3("New Workorder Job Opns Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldWorkordersJobOpnCount,B.RecordCount NewWorkordersJobOpnCount from (select count(*) RecordCount from movedb.Workorder_Extension we,movedb.Workorder_Header wh,movedb.Workorder_Operation wo where we.wo_id = wh.wo_id and wh.wo_id = wo.wo_id and wh.wo_date between '2005-06-01' and '2005-08-15') A, (select count(*) RecordCount from WO_JB_OPN)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objWoJbOpnCount.setValue1(rs.getInt(1)+"");
            objWoJbOpnCount.setValue2(rs.getInt(2)+"");
            objWoJbOpnCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objWoJbOpnCount);
    

//      workorder job opn validation
        MigrationReportDetails objWoJbOpnStdHrsSum = new MigrationReportDetails();
        objWoJbOpnStdHrsSum.setTitle("WORKORDER JOB OPERATION VALIDATION (HASH TOTAL - STD HRS)");
        objWoJbOpnStdHrsSum.setHeader1("Result");
        objWoJbOpnStdHrsSum.setHeader2("Old Workorder Job Opns StdHrs Sum");
        objWoJbOpnStdHrsSum.setHeader3("New Workorder Job Opns StdHrs Sum");
        
        ps = con.executeStatement("SELECT A.sumStdHrs=B.sumStdHrs Result,A.sumStdHrs OldWorkordersJobOpnsumStdHrs,B.sumStdHrs NewWorkordersJobOpnsumStdHrs from (select sum(wo.standard_hrs) sumStdHrs from movedb.Workorder_Extension we,movedb.Workorder_Header wh,movedb.Workorder_Operation wo where we.wo_id = wh.wo_id and wh.wo_id = wo.wo_id and wh.wo_date between '2005-06-01' and '2005-08-15') A, (select sum(WOJBOPN_OPN_STDHRS) sumStdHrs from WO_JB_OPN)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objWoJbOpnStdHrsSum.setValue1(rs.getInt(1)+"");
            objWoJbOpnStdHrsSum.setValue2(rs.getInt(2)+"");
            objWoJbOpnStdHrsSum.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objWoJbOpnStdHrsSum);

//      Prod
        MigrationReportDetails objProdCount = new MigrationReportDetails();
        objProdCount.setTitle("PRODUCTION RECORD COUNT");
        objProdCount.setHeader1("Result");
        objProdCount.setHeader2("Old Production Count");
        objProdCount.setHeader3("New Production Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldProductionCount,B.RecordCount NewProductionCount from (select count(*) RecordCount from movedb.Production p , (select wo_number from movedb.Workorder_Header wh where wh.wo_date between '2005-06-01' and '2005-08-15' group by wo_number) b where p.wo_number = b.wo_number and p.update_payroll = '0') A, (select count(*) RecordCount from PROD)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objProdCount.setValue1(rs.getInt(1)+"");
            objProdCount.setValue2(rs.getInt(2)+"");
            objProdCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objProdCount);
        
//      Non Prod
        MigrationReportDetails objNonProductionCount = new MigrationReportDetails();
        objNonProductionCount.setTitle("NON PRODUCTION RECORD COUNT");
        objNonProductionCount.setHeader1("Result");
        objNonProductionCount.setHeader2("Old Non Production Count");
        objNonProductionCount.setHeader3("New Non Production Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldNonProductionCount,B.RecordCount NewNonProductionCount from (select count(*) RecordCount from movedb.Non_Production np where np.current_date between '2005-06-01' and '2005-08-15' and np.update_payroll = '0') A, (select count(*) RecordCount from N_PROD)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objNonProductionCount.setValue1(rs.getInt(1)+"");
            objNonProductionCount.setValue2(rs.getInt(2)+"");
            objNonProductionCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objNonProductionCount);

//      POP
        MigrationReportDetails objPopCount = new MigrationReportDetails();
        objPopCount.setTitle("POP RECORD COUNT");
        objPopCount.setHeader1("Result");
        objPopCount.setHeader2("Old Pop Count");
        objPopCount.setHeader3("New Pop Count");
        
        ps = con.executeStatement("SELECT A.RecordCount=B.RecordCount Result,A.RecordCount OldPOPCount,B.RecordCount NewPOPProductionCount from (select count(*) RecordCount from movedb.POP p where p.current_date between '2005-06-01' and '2005-08-15' and p.update_payroll = '0') A, (select count(*) RecordCount from POP)B");
        rs = ps.executeQuery();
        while(rs.next())
        {
            objPopCount.setValue1(rs.getInt(1)+"");
            objPopCount.setValue2(rs.getInt(2)+"");
            objPopCount.setValue3(rs.getInt(2)+"");
        }
        vecResult.add(objPopCount);

        
        
        objMigrationReportDetailsList = new MigrationReportDetails[vecResult.size()];
        vecResult.copyInto(objMigrationReportDetailsList);
        
        return objMigrationReportDetailsList;
    }
    
    public static void main(String args[])
    {
        
        
    }
    
}

/*
*$Log: DataMigarationReportsDetailsManager.java,v $
*Revision 1.1  2005/08/23 09:24:49  kduraisamy
*CHECK EMP TYPE SQL QUERY ADDED.
*
*
*/