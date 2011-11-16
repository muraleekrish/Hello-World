/*
 * Created on Jan 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.reworklog;

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

import com.savantit.prodacs.businessimplementation.reworklog.ReworkLogDetails;
import com.savantit.prodacs.businessimplementation.reworklog.RwkLogJbOpnDetails;
import com.savantit.prodacs.businessimplementation.reworklog.RwkLogJbQtyDetails;
import com.savantit.prodacs.facade.SessionReworkLogDetailsManager;
import com.savantit.prodacs.facade.SessionReworkLogDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkLogAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof ReworkLogAddForm)
		{
			ReworkLogAddForm frm = (ReworkLogAddForm) form;
			EJBLocator obj = new EJBLocator(); /* EjbLocator for Session Bean */
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
			   	obj.setJndiName("SessionReworkLogDetailsManagerBean");
		   		obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
				SessionReworkLogDetailsManagerHome rwkLogHomeObj = (SessionReworkLogDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkLogDetailsManagerHome.class);
				SessionReworkLogDetailsManager rwkLogObj = (SessionReworkLogDetailsManager)PortableRemoteObject.narrow(rwkLogHomeObj.create(),SessionReworkLogDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("add"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Rework Log Add Starts...");
					StringTokenizer stJbQtyDet = new StringTokenizer(frm.getRwkLogDetails(),"^");
					String[] arJbQtyDet = new String[stJbQtyDet.countTokens()];
					int count = 0;
					while (stJbQtyDet.hasMoreTokens())
					{
						arJbQtyDet[count] = stJbQtyDet.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("Rec. "+count+". "+ arJbQtyDet[count]);
						count++;
					}
					Vector vecJbOpnDet = new Vector();
					for (int i = 0; i < arJbQtyDet.length; i++)
					{
						StringTokenizer stJbOpnDet = new StringTokenizer(arJbQtyDet[i],"-");
						String[] arJbOpnDet = new String[stJbOpnDet.countTokens()];
						count = 0;
						while (stJbOpnDet.hasMoreTokens())
						{
							arJbOpnDet[count] = stJbOpnDet.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("["+count+"]. "+ arJbOpnDet[count]);
							count++;
						}
						/* Object to Rework Job Operation Details */
						RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
						
						objRwkLogJbOpnDetails.setWoJbOpnId(Integer.parseInt(arJbOpnDet[0]));
						objRwkLogJbOpnDetails.setOpnSno(Integer.parseInt(arJbOpnDet[1]));
						vecJbOpnDet.add(objRwkLogJbOpnDetails);
					}
					
					/* Object to Rework Job Qty Details */
					RwkLogJbQtyDetails objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();

					Vector vecJbQtyDet = new Vector();
					objRwkLogJbQtyDetails.setJbQtySno(Integer.parseInt(frm.getHidJbQtySno().trim()));
					if (BuildConfig.DMODE)
						System.out.println("Jb Qty Sno: "+ objRwkLogJbQtyDetails.getJbQtySno());
					objRwkLogJbQtyDetails.setVecRwkLogJbOpnDetails(vecJbOpnDet);
					vecJbQtyDet.add(objRwkLogJbQtyDetails);

					/* Object to Rework Log Details */
					ReworkLogDetails objReworkLogDetails = new ReworkLogDetails();
					
					objReworkLogDetails.setAuthorizedBy(frm.getAuthorizedBy());
					objReworkLogDetails.setRwkId(Integer.parseInt(frm.getReworkReason()));
					if (BuildConfig.DMODE)
						System.out.println("RwkId: "+ objReworkLogDetails.getRwkId());
					objReworkLogDetails.setWoId(Integer.parseInt(frm.getWrkOrdNum()));
					if (BuildConfig.DMODE)
						System.out.println("WoId: "+ objReworkLogDetails.getWoId());
					objReworkLogDetails.setJbId(Integer.parseInt(frm.getJobName()));
					if (BuildConfig.DMODE)
						System.out.println("JbId: "+ objReworkLogDetails.getJbId());

					objReworkLogDetails.setVecRwkLogJbQtyDetails(vecJbQtyDet);
					
					boolean added = rwkLogObj.addReworkLogDetails(objReworkLogDetails);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Rework Log",""));
						
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
				if (BuildConfig.DMODE)
					System.out.println("Error in Rework Log Add Action");
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("RwkLog Add Action: "+ action);
		return mapping.findForward(action);
	}
}

/***
$Log: ReworkLogAddAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/02/28 06:20:16  vkrishnamoorthy
Modified to add multiple entries.

Revision 1.4  2005/02/03 10:12:57  sponnusamy
JbQtySno Added.

Revision 1.3  2005/02/03 06:39:53  vkrishnamoorthy
MessageId changed appropriately for adding data.

Revision 1.2  2005/02/02 07:27:19  sponnusamy
Jsp page connected to Business methods.

***/
