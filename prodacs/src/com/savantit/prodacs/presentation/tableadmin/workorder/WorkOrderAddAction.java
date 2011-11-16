/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savantit.prodacs.businessimplementation.workorder.WOJobDetails;
import com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManager;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours;
import com.savantit.prodacs.util.CastorXML;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		String woJobList[] = null;
		String arWoJobList[] = null;
		String arWoJobOperList[] = null;
		String arWoJobOperRecList[] = null;
		String arWoJobOperDet[] = null;
		
		/* Create an instanceof WorkOrderAddForm.java*/
		if (form instanceof WorkOrderAddForm)
		{
			WorkOrderAddForm frm = (WorkOrderAddForm)form;
		    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
		    StandardHours objStdHours = new StandardHours();
		    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());

			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
    	    {
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionWorkOrderDetailsManagerBean");
				obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
				SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);

				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					/* Work Order Details Object */
					WorkOrderDetails objWorkorderDetails = new WorkOrderDetails();

					objWorkorderDetails.setWorkOrderNo(frm.getWoHash());

					/* Date Conversion for WorkOrder Date */
					StringTokenizer st = new StringTokenizer(frm.getWoDate(),"-");
					int yr = 0;
					int month = 0;
					int day = 0;
					if(st.hasMoreTokens())
					{
						yr = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						month = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						day = Integer.parseInt(st.nextToken().trim());
					}
					GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
					objWorkorderDetails.setWoCreatedDate(ge.getTime());

					/* Date Conversion for WorkOrder Completion Date */
					st = new StringTokenizer(frm.getWoEstCompletion(),"-");
					yr = 0;
					month = 0;
					day = 0;
					if(st.hasMoreTokens())
					{
						yr = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						month = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						day = Integer.parseInt(st.nextToken().trim());
					}
					ge = new GregorianCalendar(yr,month-1,day);
					objWorkorderDetails.setWoEstmdCompleteDate(ge.getTime());

					objWorkorderDetails.setContactPerson(frm.getWoContactName());
					objWorkorderDetails.setCustId(Integer.parseInt(frm.getWoCustomerName()));

					Vector vec_woJobDet = new Vector();

					/* Tokenize the Operation Details */
					StringTokenizer stWoOperList = new StringTokenizer(frm.getFinalWoOperDet(),"$");
					arWoJobOperList = new String[stWoOperList.countTokens()];
					int opCount = 0;
					while (stWoOperList.hasMoreTokens())
					{
						arWoJobOperList[opCount] = stWoOperList.nextToken();
						opCount++;
					}

					/* Tokenize the WorkOrder Details */
					StringTokenizer stWoList = new StringTokenizer(frm.getFinalWoJobDet(),"^");
					woJobList = new String[stWoList.countTokens()];
					int count = 0;
					while (stWoList.hasMoreTokens())
					{
						/* Details object for WorkOrderJobDetails */
						WOJobDetails objWOJobDetails = new WOJobDetails();

						woJobList[count] = stWoList.nextToken();

						StringTokenizer stWoListRec = new StringTokenizer(woJobList[count],"~");
						arWoJobList = new String[stWoListRec.countTokens()];
						int recCount = 0;
						while (stWoListRec.hasMoreTokens())
						{
							arWoJobList[recCount] = stWoListRec.nextToken();
							recCount++;
						}
						objWOJobDetails.setJobId(Integer.parseInt(arWoJobList[0])); // Job Id
						objWOJobDetails.setJobName(arWoJobList[1]); // Job Name
 						objWOJobDetails.setJobSerialNo(count+1); // Seriel No
						objWOJobDetails.setJobDrwgNo(arWoJobList[2]); // Drawing No
						objWOJobDetails.setJobRvsnNo(arWoJobList[3]); // Revision No
						objWOJobDetails.setJobMatlType(arWoJobList[4]); // Material Type
						objWOJobDetails.setJobQtyStartSno(Integer.parseInt(arWoJobList[5])); // Start Sno
						objWOJobDetails.setJobQty(Integer.parseInt(arWoJobList[6])); // Job Qty
						objWOJobDetails.setWoDCNo(arWoJobList[7]); // DC No.
						int y = 0;
						int m = 0;
						int d = 0;
						if (BuildConfig.DMODE)
			            {
							System.out.println("-->"+arWoJobList[8]);
			            }
						StringTokenizer stDate = new StringTokenizer(arWoJobList[8],"-");

						if (stDate.hasMoreTokens())
						{
							y = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							m = Integer.parseInt(stDate.nextToken().trim());
						}
						if (stDate.hasMoreTokens())
						{
							d = Integer.parseInt(stDate.nextToken().trim());
						}
						GregorianCalendar dcDate = new GregorianCalendar(y,m-1,d);
						if (BuildConfig.DMODE)
			            {
							System.out.println("--->"+dcDate.getTime());
			            }
						objWOJobDetails.setWoDCDate(dcDate.getTime());
						
						Vector vec_opnDet = new Vector();
						StringTokenizer stWoJobOperDet = new StringTokenizer(arWoJobOperList[count],"^");
						arWoJobOperDet = new String[stWoJobOperDet.countTokens()];
						int opnCount = 0;
						while (stWoJobOperDet.hasMoreTokens())
						{
							arWoJobOperDet[opnCount] = stWoJobOperDet.nextToken();
							
							StringTokenizer stWoJobOpnDet = new StringTokenizer(arWoJobOperDet[opnCount],"~");
							arWoJobOperRecList = new String[stWoJobOpnDet.countTokens()];
							int opnRecCount = 0;
							while (stWoJobOpnDet.hasMoreTokens())
							{
								arWoJobOperRecList[opnRecCount] = stWoJobOpnDet.nextToken();
								opnRecCount++;
							}
	
							/* Object to WorkOrder Job Operation Details */
							WOJobOpnDetails objJobOpnDetails = new WOJobOpnDetails();

							objJobOpnDetails.setOpnGpId(Integer.parseInt(arWoJobOperRecList[0]));
							objJobOpnDetails.setOpnSerialNo(Integer.parseInt(arWoJobOperRecList[1]));
							objJobOpnDetails.setOpnName(arWoJobOperRecList[3]);
							if((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()))
							{
								objJobOpnDetails.setOpnStdHrs(Float.parseFloat(arWoJobOperRecList[4]));
							}
							if ((objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
							{
								objJobOpnDetails.setOpnIncentive(arWoJobOperRecList[5].equalsIgnoreCase("true")?true:false);
							}
							vec_opnDet.add(objJobOpnDetails);

							opnCount++;
						}
						objWOJobDetails.setWOJobOpnDetails(vec_opnDet);
						vec_woJobDet.add(objWOJobDetails);
						count++;
					}
					

					objWorkorderDetails.setWOJobDetails(vec_woJobDet);

					boolean added = workOrderObj.addNewWorkOrder(objWorkorderDetails);

					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Work Order",frm.getWoHash()));

						saveMessages(request,message);
						action="success";
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";
					}
				}
			}
			catch (WorkOrderException e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				e.printStackTrace();
				action="failure";
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
				saveErrors(request,errors);
				e.printStackTrace();
				action="failure";
			}

		}
		if (BuildConfig.DMODE)
        {
			System.out.println("WorkOrder Add: "+action);
        }
		return mapping.findForward(action);
	}
}
/***
$Log: WorkOrderAddAction.java,v $
Revision 1.13  2005/08/02 12:48:53  vkrishnamoorthy
Modified as per displaying Incentive, if exists.

Revision 1.12  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.11  2005/07/25 10:18:00  vkrishnamoorthy
WO DC Date added.

Revision 1.10  2005/07/23 12:03:11  vkrishnamoorthy
WoDCNo included.

Revision 1.9  2005/06/10 12:40:22  vkrishnamoorthy
Script modified with change of delimiters while adding.

Revision 1.8  2005/02/23 10:13:29  sponnusamy
WorkOrder add Problems completed.

Revision 1.7  2005/02/22 06:06:20  sponnusamy
WorkOrder Add newly generated.

Revision 1.6  2005/02/16 10:53:56  smurugesan
Count value for woOperations corrected.

Revision 1.5  2005/01/29 10:23:56  sponnusamy
Changes Made

Revision 1.4  2005/01/20 08:17:50  sponnusamy
Work Order Exception messages are managed properly

Revision 1.3  2004/12/16 09:20:34  sponnusamy
WorkOrder Controller is fully completed.

Revision 1.2  2004/12/15 16:28:07  sponnusamy
WorkOrder controlling part is partially completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/