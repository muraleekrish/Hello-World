/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

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

import com.savantit.prodacs.businessimplementation.workorder.DummyWorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManager;
import com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;
/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DummyWorkOrderAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* Create an instanceof DummyWorkOrderAddForm.java*/
		if (form instanceof DummyWorkOrderAddForm)
		{ 
			DummyWorkOrderAddForm frm = (DummyWorkOrderAddForm)form;
			
			EJBLocator obj = new EJBLocator();
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
					if (BuildConfig.DMODE)
		            {
						System.out.println("Dummy WorkOrder Add Starts...");
		            }
					
					/* Details object for DummyWorkOrderDetails */
					DummyWorkOrderDetails objDummyWorkOrderDetails = new DummyWorkOrderDetails();
					
					objDummyWorkOrderDetails.setDmyWoNo("DMY"+frm.getDyWOHash().trim());
					objDummyWorkOrderDetails.setDmyWoGnrlId(Integer.parseInt(frm.getDyGeneralName()));
					objDummyWorkOrderDetails.setCustId(Integer.parseInt(frm.getDyCustomerName()));
					objDummyWorkOrderDetails.setDmyWoJbName(frm.getDyJobName());
					objDummyWorkOrderDetails.setDmyWoJbDrwgNo(frm.getDyDrawingHash());
					objDummyWorkOrderDetails.setDmyWoJbRvsnNo(frm.getDyRevisionHash());
					objDummyWorkOrderDetails.setDmyWoJbMatlTyp(frm.getDyMaterialType());
					objDummyWorkOrderDetails.setStartSno(Integer.parseInt(frm.getDyStartSno()));
					objDummyWorkOrderDetails.setJbQty(Integer.parseInt(frm.getDyTotQty()));
					objDummyWorkOrderDetails.setWoDCNo(frm.getDyDcNo());
					/* Date Conversion for DC Date */
					StringTokenizer st = new StringTokenizer(frm.getDyDcDate(),"-");
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
					objDummyWorkOrderDetails.setWoDCDate(ge.getTime());
					objDummyWorkOrderDetails.setDmyOpnGpId(Integer.parseInt(frm.getDyOpnGrpCode()));
					objDummyWorkOrderDetails.setDmyOpnName(frm.getDyOpnName());
					objDummyWorkOrderDetails.setDmyStartOpn(Integer.parseInt(frm.getDyStartOpn()));
					objDummyWorkOrderDetails.setDmyEndOpn(Integer.parseInt(frm.getDyEndOpn()));
					
					boolean added = workOrderObj.addNewDummyWorkOrder(objDummyWorkOrderDetails);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Dummy WorkOrder","DMY"+frm.getDyWOHash()));
						
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
				action="failure";
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
				saveErrors(request,errors);
				action="failure";				  			
			}
		}
		if (BuildConfig.DMODE)
        {
			System.out.println("DummyWorkOrder Add: "+action);
        }
		return mapping.findForward(action);
	}
}
/***
$Log: DummyWorkOrderAddAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/07/25 10:34:10  vkrishnamoorthy
WO DC Date added.

Revision 1.4  2005/07/24 09:09:21  vkrishnamoorthy
DC No included.

Revision 1.3  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.2  2005/02/07 12:11:27  sponnusamy
DummyWorkOrder is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/