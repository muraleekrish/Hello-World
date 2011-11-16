/*
 * Created on Jul 22, 2005
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

import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetails;
import com.savantit.prodacs.facade.SessionShipmentDetailsManager;
import com.savantit.prodacs.facade.SessionShipmentDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;


/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShipmentReferenceAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";

		/*Creating an instance of ShipmentReferenceAddForm.java*/
		if (form instanceof ShipmentReferenceAddForm)
		{
			ShipmentReferenceAddForm frm = (ShipmentReferenceAddForm) form;
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			ActionMessages messages = new ActionMessages();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionShipmentDetailsManager");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionShipmentDetailsManagerHome objShipmentHome = (SessionShipmentDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionShipmentDetailsManagerHome.class);
				SessionShipmentDetailsManager objShipment = (SessionShipmentDetailsManager)PortableRemoteObject.narrow(objShipmentHome.create(),SessionShipmentDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Shipment Reference Add Starts...");
					}
					Vector vecShipmentReference = new Vector();
					ShipmentDetails[] objShipmentDetails = null; 
					if (BuildConfig.DMODE)
					{
						System.out.println("Details: "+ frm.getHidProdList());
					}
					StringTokenizer stRowDetails = new StringTokenizer(frm.getHidProdList(),"$");
					while (stRowDetails.hasMoreTokens())
					{
						ShipmentDetails objShipmentDets = new ShipmentDetails();
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
						
						objShipmentDets.setShipmentCrntDate(gc.getTime());
						objShipmentDets.setShiftId(Integer.parseInt(arColDetails[6]));
						objShipmentDets.setWoJbId(Integer.parseInt(arColDetails[8]));
						objShipmentDets.setShipmentQtySnos(arColDetails[5]);
						StringTokenizer stQtySnos = new StringTokenizer(arColDetails[5],",");
						objShipmentDets.setShipmentTotQty(stQtySnos.countTokens());
						objShipmentDets.setShipmentStartOpn(Integer.parseInt(arColDetails[9]));
						objShipmentDets.setShipmentEndOpn(Integer.parseInt(arColDetails[9]));
						objShipmentDets.setDeliveryChallanNo(arColDetails[10]);
						objShipmentDets.setCreatedBy(frm.getHidUserId());
						
						vecShipmentReference.add(objShipmentDets);
					}
					objShipmentDetails = new ShipmentDetails[vecShipmentReference.size()];
					vecShipmentReference.copyInto(objShipmentDetails);
					try
					{
						boolean result = objShipment.addNewShipmentDetails(objShipmentDetails);
						if (result)
						{
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("prodacs.common.message.add","Shipment Reference Entry"));
							if (!messages.isEmpty())
								saveMessages(request,messages);
							action = "success";
						}
					}
					catch (ProductionException ex)
					{
						if (BuildConfig.DMODE)
						{
							ex.printStackTrace();
						}
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.production.error",ex.getMessage()));
						if (!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
				}
			}
			catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";
				if (BuildConfig.DMODE)
				{
					e.printStackTrace();
					System.out.println("Problem in ShipmentReference Add Action.");
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("ProductionFinal Add Action :"+action);
		}
		return mapping.findForward(action);
	}
			
}


/***
$Log: ShipmentReferenceAddAction.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/07/23 07:05:40  vkrishnamoorthy
Initial commit on ProDACS.

***/