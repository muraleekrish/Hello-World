/*
 * Created on Jul 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

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

import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails;
import com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManager;
import com.savantit.prodacs.facade.SessionDespatchClearanceDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DespatchClearanceAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		if (BuildConfig.DMODE)
        {
			System.out.println("Despatch Clearance Add Action Starts...");
        }
		String action = "failure";
		EJBLocator obj = new EJBLocator();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		if (form instanceof DespatchClearanceAddForm)
		{
			DespatchClearanceAddForm frm = (DespatchClearanceAddForm) form;
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionDespatchClearanceDetailsManager");
		   		obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
				SessionDespatchClearanceDetailsManagerHome objDespatchClearance = (SessionDespatchClearanceDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionDespatchClearanceDetailsManagerHome.class);
				SessionDespatchClearanceDetailsManager objDesClear = (SessionDespatchClearanceDetailsManager)PortableRemoteObject.narrow(objDespatchClearance.create(),SessionDespatchClearanceDetailsManager.class);
				
				if (frm.getFormAction().trim().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
		            {
						System.out.println("Despatch Clearance Add action starts...");
		            }
					Vector vecDespatchClearance = new Vector();
					DespatchClearanceDetails[] objDespatchClearanceDetailsList = null;
					StringTokenizer stRowDetails = new StringTokenizer(frm.getHidProdList(),"$");
					while (stRowDetails.hasMoreTokens())
					{
						DespatchClearanceDetails objDespatchClearanceDetails = new DespatchClearanceDetails();
						StringTokenizer stColDetails = new StringTokenizer(stRowDetails.nextToken(),"#");
						String[] arColDetails = new String[stColDetails.countTokens()];
						int count = 0;
						while (stColDetails.hasMoreTokens())
						{
							arColDetails[count] = stColDetails.nextToken();
							if (BuildConfig.DMODE)
				            {
								System.out.println((count)+". "+arColDetails[count]);
				            }
							count++;
						}
						
						StringTokenizer stDate = new StringTokenizer(arColDetails[1].trim(),"-");
						int year = 0;
						int mon = 0;
						int date = 0;
						if(stDate.hasMoreTokens())
							year = Integer.parseInt(stDate.nextToken().trim());
						if(stDate.hasMoreTokens())
							mon = Integer.parseInt(stDate.nextToken().trim());
						if(stDate.hasMoreTokens())
							date = Integer.parseInt(stDate.nextToken().trim());
						GregorianCalendar gc = new GregorianCalendar(year,mon-1,date);

						objDespatchClearanceDetails.setDespatchCrntDate(gc.getTime());
						objDespatchClearanceDetails.setShiftId(Integer.parseInt(arColDetails[6]));
						objDespatchClearanceDetails.setWoJbId(Integer.parseInt(arColDetails[8]));
						objDespatchClearanceDetails.setDespatchQtySnos(arColDetails[5]);
						StringTokenizer stQtySnos = new StringTokenizer(arColDetails[5],",");
						objDespatchClearanceDetails.setDespatchTotQty(stQtySnos.countTokens());
						objDespatchClearanceDetails.setDespatchStartOpn(Integer.parseInt(arColDetails[9]));
						objDespatchClearanceDetails.setDespatchEndOpn(Integer.parseInt(arColDetails[9]));
						objDespatchClearanceDetails.setCreatedBy(frm.getHidUserId());
						
						vecDespatchClearance.add(objDespatchClearanceDetails);
					}
					objDespatchClearanceDetailsList = new DespatchClearanceDetails[vecDespatchClearance.size()];
					vecDespatchClearance.copyInto(objDespatchClearanceDetailsList);
					try
					{
						boolean result = objDesClear.addNewDespatchClearanceDetails(objDespatchClearanceDetailsList);
						if (result)
						{
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("prodacs.common.message.add","Despatch Clearance Entry"));
							if (!messages.isEmpty())
								saveMessages(request,messages);
							action = "success";
						}
					}
					catch (Exception ex)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",ex.getMessage()));
						if (!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (BuildConfig.DMODE)
	            {
					System.out.println("Error in Despatch Clearance Add Action.");
	            }
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general",e.getMessage()));
				if (!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
		}
		if (BuildConfig.DMODE)
        {
			System.out.println("Despatch Add Final Action: "+ action);
        }
		return mapping.findForward(action);
	}
}
