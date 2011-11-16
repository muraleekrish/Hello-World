/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.rework;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

import com.savantit.prodacs.businessimplementation.job.ReworkDetails;
import com.savantit.prodacs.businessimplementation.job.ReworkException;
import com.savantit.prodacs.facade.SessionReworkDetailsManager;
import com.savantit.prodacs.facade.SessionReworkDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";

		/*Creating an instance of CreateCategoryForm.java*/
		if (form instanceof ReworkAddForm)
		{
			ReworkAddForm frm = (ReworkAddForm) form;

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionReworkDetailsManagerBean");
				obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionReworkDetailsManagerHome reworkHomeObj = (SessionReworkDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkDetailsManagerHome.class);
				SessionReworkDetailsManager reworkObj = (SessionReworkDetailsManager)PortableRemoteObject.narrow(reworkHomeObj.create(),SessionReworkDetailsManager.class);

				/* Setting the form values to Details Variables */
				ReworkDetails objReworkDetails = new ReworkDetails();
				if (BuildConfig.DMODE)
				{
					System.out.println("Rework Category: "+frm.getReworkCategory());
					System.out.println("Rework Reason: "+frm.getReworkReason());
					System.out.println("New Rework Category: "+frm.getNewReworkCategory());
				}
				if (frm.getReworkCategory().equals("-1"))
				{
					objReworkDetails.setRwk_Category(frm.getNewReworkCategory());
					boolean newCategoryAdded = reworkObj.addNewReworkCategory(objReworkDetails);
					if (BuildConfig.DMODE)
						System.out.println("Rework Category Added.");

					if (newCategoryAdded == true)
					{
						int rwkId=0;
						HashMap hm = reworkObj.getAllReworkCategories();
						if (BuildConfig.DMODE)
							System.out.println("Hashmap: "+hm);
						Set set = hm.entrySet();
						Iterator i = set.iterator();
						while (i.hasNext())
						{
							Map.Entry me = (Map.Entry) i.next();
							if (me.getValue().equals(frm.getNewReworkCategory().trim()))
							{
								rwkId = Integer.parseInt(me.getKey().toString());
								if (BuildConfig.DMODE)
									System.out.println("Id -> "+rwkId+" & Value -> "+me.getValue());
							}
						}
						objReworkDetails.setRwk_Category_Id(rwkId);
						objReworkDetails.setRwk_Rsn(frm.getReworkReason());

						boolean added = reworkObj.addNewReworkReason(objReworkDetails);
						if (BuildConfig.DMODE)
							System.out.println("Rework Reason Added");
						if (added == true)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Rework Reason",frm.getReworkReason()));

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
				else
				{
					objReworkDetails.setRwk_Category_Id(Integer.parseInt(frm.getReworkCategory().trim()));
					if (BuildConfig.DMODE)
						System.out.println("objReworkDetails.getRwk_Category_Id() : "+objReworkDetails.getRwk_Category_Id());
					objReworkDetails.setRwk_Rsn(frm.getReworkReason());

					boolean added = reworkObj.addNewReworkReason(objReworkDetails);
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Rework Reason",frm.getReworkReason()));
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
			catch(ReworkException e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
				if(e.toString().toLowerCase().indexOf("parent key not found")!=-1)
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.parentKey.notFound"));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				else
				{
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				action = "failure";
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("ReworkAdd :"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: ReworkAddAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/07/12 11:49:31  kduraisamy
imports organized

Revision 1.2  2005/04/25 09:23:29  smurugesan
NewCategoryReason index changed.

Revision 1.1  2004/11/22 10:42:28  sponnusamy
Rework forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/