/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

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

import com.savantit.prodacs.businessimplementation.workorder.JobQtyDetails;
import com.savantit.prodacs.businessimplementation.workorder.PreCloseDetails;
import com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails;
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
public class PreCloseLogAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* Create an instanceof PreCloseLogAddForm.java*/
		if (form instanceof PreCloseLogAddForm)
		{ 
			PreCloseLogAddForm frm = (PreCloseLogAddForm)form;
			ActionErrors errors = new ActionErrors();
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
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
					String arPreClsDet[] = null;
					if (BuildConfig.DMODE)
		            {
						System.out.println("frm.getPreClsDetVar() :"+frm.getPreClsDetVar());
		            }
					StringTokenizer stPreClsDet = new StringTokenizer(frm.getPreClsDetVar(),"-");
					arPreClsDet = new String[stPreClsDet.countTokens()];
					int count = 0;
					while (stPreClsDet.hasMoreTokens())
					{
						arPreClsDet[count] = stPreClsDet.nextToken();
						count++;
					}
					
					Vector vec_jobQty = new Vector();
					for (int i = 0; i < arPreClsDet.length; i = i + 4)
					{
						if(!arPreClsDet[i+3].equals(""))
						{
						JobQtyDetails objJobQtyDetails = new JobQtyDetails();
						objJobQtyDetails.setWoJbStatId(Integer.parseInt(arPreClsDet[i+3]));
						Vector vec_opnDet = new Vector();
						WOJobOpnDetails objWOJobOpnDetails1 = new WOJobOpnDetails();
						objWOJobOpnDetails1.setOpnSerialNo(Integer.parseInt(arPreClsDet[i+1]));
						objWOJobOpnDetails1.setOpnName(arPreClsDet[i+2]);
						if (BuildConfig.DMODE)
			            {
							System.out.println((i+1)+". "+objWOJobOpnDetails1.getOpnSerialNo());
			            }
						vec_opnDet.add(objWOJobOpnDetails1);		
										
						int cnt = 0;
						for(int j = i+4; j < arPreClsDet.length; j = j + 4)
						{
							if (BuildConfig.DMODE)
				            {
								System.out.println(arPreClsDet[i+3]+"&"+arPreClsDet[j+3]);
				            }
							 if(arPreClsDet[i+3].equals(arPreClsDet[j+3]))
					 		 {
								  WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
								  objWOJobOpnDetails.setOpnSerialNo(Integer.parseInt(arPreClsDet[j+1]));
								  objWOJobOpnDetails1.setOpnName(arPreClsDet[j+2]);
								  if (BuildConfig.DMODE)
						          {
								  	System.out.println((j+1)+". "+objWOJobOpnDetails.getOpnSerialNo());
						          }
								  vec_opnDet.add(objWOJobOpnDetails);		
								  cnt++;
								  arPreClsDet[j+1]=arPreClsDet[j+2]=arPreClsDet[j+3]="";
						 	 }
						}
						
						arPreClsDet[i+1]=arPreClsDet[i+2]=arPreClsDet[i+3]="";
						objJobQtyDetails.setWOJobOpnDetails(vec_opnDet);
												
						vec_jobQty.add(objJobQtyDetails);
						}
					}
					
					PreCloseDetails objPreClsDetails = new PreCloseDetails();
					if (BuildConfig.DMODE)
		            {
						System.out.println("size :" +vec_jobQty.size());
		            }
					objPreClsDetails.setJobQtyDetails(vec_jobQty);
					objPreClsDetails.setPreCloseReason(frm.getPreCloseReason());
					if (BuildConfig.DMODE)
		            {
						System.out.println("PreCls Reason: "+ objPreClsDetails.getPreCloseReason());
		            }
					boolean added = workOrderObj.addNewPreCloseLog(objPreClsDetails);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Pre Close","Job"));
						
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
			}catch (Exception e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action="failure";				  			
			}

		}
		if (BuildConfig.DMODE)
        {
			System.out.println("PreCloseLog Add: "+action);
        }
		return mapping.findForward(action);
	}
}
/***
$Log: PreCloseLogAddAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/04/13 10:00:15  vkrishnamoorthy
Modified for adding multiple values.

Revision 1.3  2005/02/01 04:15:40  sponnusamy
PreClose reason added.

Revision 1.2  2004/12/21 11:23:08  sponnusamy
PreCloseLog Controller is fully completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/