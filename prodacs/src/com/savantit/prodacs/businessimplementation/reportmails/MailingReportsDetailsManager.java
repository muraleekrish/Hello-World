/*
 * Created on Dec 10, 2005
 *
 * ClassName	:  	MailingReportsDetailsManager.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reportmails;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.datrics.scheduler.taskrunner.TaskRunnerInterface;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails;
import com.savantit.prodacs.businessimplementation.reports.MachineUtilizationReportDetails;
import com.savantit.prodacs.facade.SessionReportsDetailsManager;
import com.savantit.prodacs.facade.SessionReportsDetailsManagerHome;
import com.savantit.prodacs.infra.util.Mail;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author kduraisamy
 *
 */
public class MailingReportsDetailsManager implements TaskRunnerInterface
{
    
    
    /* (non-Javadoc)
     * @see com.datrics.scheduler.taskrunner.TaskRunnerInterface#execute()
     */
    public boolean execute() 
    {
        boolean isSend = false;
        try
        {
	        GregorianCalendar gc = new GregorianCalendar();
	        gc.add(Calendar.DATE,-2);
	        Date prodDate = gc.getTime();
	        
	        ProductionAccountingMachineDetails[] objProductionAccountingMachineDetailsList = null;
	        EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
		
	   		objProductionAccountingMachineDetailsList = reportsObj.fetchProductionAccountingDateDetails(prodDate);
	    	
	        String mailAttachmentName = "";
			//mailAttachmentName = "Production_Accounting_Report_For_"+prodDate+".pdf";
	        GregorianCalendar gcal = new GregorianCalendar();
	        gcal.setTime(prodDate);
	        String dateString = gcal.get(Calendar.DATE)+"-"+(gcal.get(Calendar.MONTH)+1)+"-"+gcal.get(Calendar.YEAR); 
	        mailAttachmentName = "ProductionAccountingReport_"+dateString+".pdf";
	        JRBeanArrayDataSource dataSource = null;
	    	JasperReport jr = null;
	    	InputStream is = null;
	    	Map parameters = new HashMap();
	    	dataSource = new JRBeanArrayDataSource(objProductionAccountingMachineDetailsList);
	    	parameters.put("Title", "Production Accounting Report For "+dateString);
			parameters.put("date",prodDate.toString());
			
			is = this.getClass().getClassLoader().getResourceAsStream("ProductionAccounting.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
			byte[] bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
			
			//mail requirements starts..
		   	
			String mailSubject = "";
			String mailMessage = "";
			Vector vecMailTo = new Vector();
			Vector vecMailCc = new Vector();
			
			String[] mailTo = null;
			String[] mailCc = null;
			
			//vecMailTo.add("precisionbalu@hotmail.com");
			
			//vecMailCc.add("rdevarajan@savant.in");
			
			vecMailTo.add("kduraisamy@savant.in");
			//vecMailCc.add("vkrishnamoorthy@savant.in");
						
			mailTo = new String[vecMailTo.size()];
			
			vecMailTo.copyInto(mailTo);
			
			mailCc = new String[vecMailCc.size()];
			
			vecMailCc.copyInto(mailCc);
			
			
			
			ResourceBundle bundle = null;
			bundle = ResourceBundle.getBundle("ApplicationResources");
	
			String mailFrom = bundle.getString("defaultSender");
			
			   
	    	
	
			Mail mail = new Mail();
			mail.setFrom(mailFrom);
			mail.addRecipients(mailTo,"to");
			mail.addRecipients(mailCc,"cc");
			mailSubject = "ProDACS Daily Report-"+dateString;
			if(mailSubject.trim().equals(""))
			{
				mail.setSubject(mailAttachmentName);
			}
			else
			{
				mail.setSubject(mailSubject);
			}
			if(mailSubject.trim().equals(""))
			{
				mail.setBodyText(mailAttachmentName);
			}
			else
			{
				mail.setBodyText(mailMessage);
			}
			mail.addAttachment(bytes,mailAttachmentName);
			
			GregorianCalendar fromDate = new GregorianCalendar();
			fromDate.setTime(prodDate);
			fromDate.set(Calendar.DATE,1);
						
			MachineUtilizationReportDetails[] objMachineUtilizationReportDetailsList = reportsObj.fetchMachineUtilization(fromDate.getTime(),prodDate);
			String fromDateString = fromDate.get(Calendar.DATE)+"-"+(fromDate.get(Calendar.MONTH)+1)+"-"+fromDate.get(Calendar.YEAR); 
	                
			String mailAttachmentName1 = "";
											
			mailAttachmentName1 = "MachineUtilizationReport_"+fromDateString+"_To_"+dateString+".pdf";
	        dataSource = null;
	    	jr = null;
	    	is = null;
	    	parameters = new HashMap();
	    	dataSource = new JRBeanArrayDataSource(objMachineUtilizationReportDetailsList);
	    	parameters.put("Title", "Machine Utilization Report From "+fromDateString+" To " +dateString);
			//parameters.put("date",prodDate.toString());
			
			//is = this.getClass().getClassLoader().getResourceAsStream("/reports/MachineUtilization.jasper");
	    	is = this.getClass().getClassLoader().getResourceAsStream("/reports/MachineUtilization.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
			bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
			mail.addAttachment(bytes,mailAttachmentName1);
			
			isSend = mail.send();
		}
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return isSend;
    }
}

/*
*$Log: MailingReportsDetailsManager.java,v $
*Revision 1.11  2006/01/10 07:21:12  kduraisamy
*mail id changed.
*
*Revision 1.10  2005/12/28 05:14:25  kduraisamy
*mail id precisionbalu@hotmail.com  included.
*
*Revision 1.9  2005/12/21 11:01:25  kduraisamy
*date included in mail subject.
*
*Revision 1.8  2005/12/20 12:47:48  kduraisamy
*mail id changed as test ids.
*
*Revision 1.7  2005/12/19 15:32:54  kduraisamy
*daily report attach name changed and mail id changed to precisionbalu@hotmail.com.
*
*Revision 1.6  2005/12/19 15:25:23  kduraisamy
*daily report attach name changed and mail id changed to precisionbalu@hotmail.com.
*
*Revision 1.5  2005/12/15 07:56:49  kduraisamy
*scheduling of reports added.
*
*Revision 1.4  2005/12/12 15:23:08  kduraisamy
*initial commit.
*
*Revision 1.3  2005/12/12 14:15:16  kduraisamy
*initial commit.
*
*Revision 1.2  2005/12/12 04:46:48  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/10 05:43:12  kduraisamy
*module for sending scheduled report added.
*
*
*/