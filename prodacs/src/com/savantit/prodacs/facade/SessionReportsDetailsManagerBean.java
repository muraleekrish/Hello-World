/*
 * Created on Feb 19, 2005
 *
 * ClassName	:  	SessionReportsDetailsManagerBean.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.savantit.prodacs.businessimplementation.customer.CustomerDetailsManager;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.machine.MachineDetailsManager;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.payroll.PayRollDetailsManager;
import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
import com.savantit.prodacs.businessimplementation.production.CustomerMailJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetailsManager;
import com.savantit.prodacs.businessimplementation.reports.ActualHrsDetails;
import com.savantit.prodacs.businessimplementation.reports.EmployeePayrollDetails;
import com.savantit.prodacs.businessimplementation.reports.EmployeePerformanceReturnDetails;
import com.savantit.prodacs.businessimplementation.reports.HalfYearlyProductionReportDetail;
import com.savantit.prodacs.businessimplementation.reports.IdleBrkdwnDetails;
import com.savantit.prodacs.businessimplementation.reports.JobTimeDetails;
import com.savantit.prodacs.businessimplementation.reports.MachineUtilizationReportDetails;
import com.savantit.prodacs.businessimplementation.reports.MonthlyPercentageDetails;
import com.savantit.prodacs.businessimplementation.reports.MonthlyReportDetails;
import com.savantit.prodacs.businessimplementation.reports.ProductionDateWiseDetails;
import com.savantit.prodacs.businessimplementation.reports.ProductionMachineWiseDetails;
import com.savantit.prodacs.businessimplementation.reports.ProductionOfMachineDetails;
import com.savantit.prodacs.businessimplementation.reports.QuantityProducedDetails;
import com.savantit.prodacs.businessimplementation.reports.ReportsDetailsManager;
import com.savantit.prodacs.businessimplementation.reports.ReportsException;
import com.savantit.prodacs.businessimplementation.reports.SalaryReportDetails;
import com.savantit.prodacs.businessimplementation.reports.TopSheetDetails;
import com.savantit.prodacs.businessimplementation.reports.WoReferenceDetails;
import com.savantit.prodacs.infra.beans.Filter;


/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!-- lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB xmlns:j2ee="http://java.sun.com/xml/ns/j2ee" xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session> <lomboz:sessionEjb> <j2ee:display-name>SessionReportsDetailsManager</j2ee:display-name> <j2ee:ejb-name>SessionReportsDetailsManager</j2ee:ejb-name> <j2ee:ejb-class>com.savantit.prodacs.facade.SessionReportsDetailsManagerBean</j2ee:ejb-class> <j2ee:session-type>Stateless</j2ee:session-type> <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb> </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!-- begin-xdoclet-definition --> 
 * @ejb.bean  name="SessionReportsDetailsManager"	 jndi-name="SessionReportsDetailsManager" type="Stateless"  transaction-type="Container" -- This is needed for JOnAS. If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean  ejb-name="SessionReportsDetailsManager"  jndi-name="SessionReportsDetailsManager" -- <!-- end-xdoclet-definition --> 
 * @generated
 */
public abstract class SessionReportsDetailsManagerBean implements javax.ejb.SessionBean
{

    ReportsDetailsManager objReportsDetailsManager = new ReportsDetailsManager();
    PayRollDetailsManager objPayRollDetailsManager = new PayRollDetailsManager();
    ShipmentDetailsManager objShipmentDetailsManager = new ShipmentDetailsManager();
    CustomerDetailsManager objCustomerDetailsManager = new CustomerDetailsManager(); 
    MachineDetailsManager objMachineDetailsManager = new MachineDetailsManager();
    
    /**
     * @ejb.interface-method
     *	view-type="remote" 
     **/
    public MachineUtilizationReportDetails[] fetchMachineUtilization(Date fromDate,Date toDate)throws SQLException,ReportsException
    { 
        return objReportsDetailsManager.fetchMachineUtilization(fromDate,toDate); 
    }
/**
 * @throws SQLException
 * @throws ReportsException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public IdleBrkdwnDetails[] fetchIdleBrkdwnHrs(Date fromDate,Date toDate) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchIdleBrkdwnHrs(fromDate,toDate); 
}
/**
 * @throws ParseException
 * @throws SQLException
 * @throws ReportsException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MonthlyPercentageDetails[] fetchMonthlyPercentageOfMachine(String mcCde,int month,int year) throws ParseException, ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchMonthlyPercentageOfMachine(mcCde,month,year); 
}
/**
 * @throws SQLException
 * @throws ReportsException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public TopSheetDetails[] fetchTopSheetDetails() throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchTopSheetDetails(); 
}
/**
 * @throws ReportsException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HalfYearlyProductionReportDetail[] fetchHalfYearlyProductionReport(String mcCde,int month,int year) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchHalfYearlyProductionReport(mcCde,month,year); 
}
/**
 * @throws SQLException
 * @throws ReportsException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public SalaryReportDetails[] fetchSalaryReports(int month,int year) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchSalaryReports(month,year); 
}
/**
 * @throws ReportsException
 * @throws SQLException
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MonthlyReportDetails[] fetchMonthlyReportsDetails(int month,int year) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchMonthlyReportsDetails(month,year); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeePerformanceReturnDetails[] fetchEmployeePerformance(Date startDate,Date endDate,int empId) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchEmployeePerformance(startDate,endDate,empId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionOfMachineDetails[] fetchProductionOfMachineDetails(Date startDate,Date endDate,String mcCode) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchProductionOfMachineDetails(startDate,endDate,mcCode); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public QuantityProducedDetails[] fetchQuantityProducedDetails(Date startDate,Date endDate) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchQuantityProducedDetails(startDate,endDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public WoReferenceDetails[] fetchWoReferenceDetails(Date startDate,Date endDate,int custId) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchWoReferenceDetails(startDate,endDate,custId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
    public ProductionAccountingMachineDetails[] fetchProductionAccountingDateDetails(Date prodDate) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchProductionAccountingDateDetails(prodDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionDateWiseDetails[] fetchProductionDetails(Date fromDate,Date toDate) throws ReportsException, SQLException, ProductionException{ 
 return objReportsDetailsManager.fetchProductionDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ActualHrsDetails[] fetchActualHrsDetails() throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchActualHrsDetails(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public ProductionMachineWiseDetails[] fetchProductionMachineWiseDetails(Date fromDate,Date toDate) throws ReportsException, SQLException, ProductionException{ 
 return objReportsDetailsManager.fetchProductionMachineWiseDetails(fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public HashMap getClosedPyrlCycleStatForPayroll() throws SQLException, PayRollException{ 
 return objPayRollDetailsManager.getClosedPyrlCycleStatForPayroll(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeePayrollDetails[] fetchEmployeePayrollDetails(int pyrlCycleStatId) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchEmployeePayrollDetails(pyrlCycleStatId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
    public JobTimeDetails[] fetchJobTimeDetails(Filter[] filter,String sortBy,boolean ascending,int startIndex) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchJobTimeDetails(filter,sortBy,ascending,startIndex);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public MonthlyReportDetails[] fetchMonthlyReportsDetailsValidationReport(int month,int year) throws SQLException, ReportsException{ 
 return objReportsDetailsManager.fetchMonthlyReportsDetailsValidationReport(month,year); 
}

/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllCustomers() throws SQLException, CustomerException{ 
 return objCustomerDetailsManager.getAllCustomers(); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public CustomerMailJobDetails[] getAllJobStatusDetails(int custId,Date fromDate,Date toDate) throws ProductionException, SQLException{ 
 return objShipmentDetailsManager.getAllJobStatusDetails(custId,fromDate,toDate); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public IdleBrkdwnDetails fetchIdleBrkdwnHrs(Date fromDate,Date toDate,String mcCde) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchIdleBrkdwnHrs(fromDate,toDate,mcCde); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public EmployeePerformanceReturnDetails[] fetchEmployeePerformanceByEmpType(Date startDate,Date endDate,int empTypeId) throws ReportsException, SQLException{ 
 return objReportsDetailsManager.fetchEmployeePerformanceByEmpType(startDate,endDate,empTypeId); 
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public LinkedHashMap getAllMachines() throws MachineException, SQLException{ 
 return objMachineDetailsManager.getAllMachines(); 
}
}

/*
*$Log: SessionReportsDetailsManagerBean.java,v $
*Revision 1.30  2006/01/07 05:10:55  vkrishnamoorthy
*getAllMachines() signature added.
*
*Revision 1.29  2005/12/27 10:18:58  kduraisamy
*signature added for fetchEmployeePerformanceByType().
*
*Revision 1.28  2005/12/23 09:30:28  kduraisamy
*signature added for fetchIdle
*BrkdwnHrs(fromdate,todate,mccde). added.
*
*Revision 1.27  2005/12/20 17:57:57  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.26  2005/12/20 17:55:20  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.25  2005/11/29 05:15:16  kduraisamy
*monthly report validation report added.
*
*Revision 1.24  2005/11/03 06:07:42  kduraisamy
*unwanted parameter removed from filter of fetchQuantityDetails().
*
*Revision 1.23  2005/10/20 05:09:30  vkrishnamoorthy
*Session bean added for fetchJobTimeDetails().
*
*Revision 1.22  2005/10/11 07:20:38  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.21  2005/10/11 06:29:31  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.20  2005/10/10 09:59:52  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.19  2005/07/12 11:48:27  kduraisamy
*imports organized
*
*Revision 1.18  2005/06/11 08:08:41  kduraisamy
*signature added for fetchPayrollDetails().
*
*Revision 1.17  2005/06/11 07:25:27  kduraisamy
*signature added for getclosedpayrollcyclestat().
*
*Revision 1.16  2005/06/09 06:11:04  kduraisamy
*signature added for fetchProductionMachinewiseDetails().
*
*Revision 1.15  2005/06/03 08:20:59  kduraisamy
*signature added for fetchActualHrs().
*
*Revision 1.14  2005/06/02 09:55:53  kduraisamy
*signature added for fetchProductionDatewiseDetails
*
*Revision 1.13  2005/05/31 13:09:15  kduraisamy
*signature added for fetchProductionAccountingDetails
*
*Revision 1.12  2005/05/14 11:37:32  kduraisamy
*signature for fetchquantityProduced() added.
*
*Revision 1.11  2005/05/14 11:19:43  kduraisamy
*signature for fetchquantityProduced() added.
*
*Revision 1.10  2005/05/14 11:00:31  kduraisamy
*signature addde for employee performance reports.
*
*Revision 1.9  2005/05/14 10:15:48  kduraisamy
*signature addde for employee performance reports.
*
*Revision 1.8  2005/04/07 08:24:10  kduraisamy
*throws added.
*
*Revision 1.7  2005/02/27 06:54:36  kduraisamy
*signature added for fetchMonthlyReport().
*
*Revision 1.6  2005/02/26 12:30:30  kduraisamy
*signature added for fetchSalaryReport().
*
*Revision 1.5  2005/02/25 11:04:41  kduraisamy
*signature added for halfyearlyProdnDetails()
*
*Revision 1.4  2005/02/23 05:25:50  kduraisamy
*signature added for fetchHalfYearlyPercentageDetails().
*
*Revision 1.3  2005/02/22 06:23:32  kduraisamy
*signature added for fetchIdleBrkDwnHrs().
*
*Revision 1.2  2005/02/19 05:01:56  sponnusamy
*Warnings removed
*
*Revision 1.1  2005/02/19 04:50:36  kduraisamy
*initial commit.
*
*
*/