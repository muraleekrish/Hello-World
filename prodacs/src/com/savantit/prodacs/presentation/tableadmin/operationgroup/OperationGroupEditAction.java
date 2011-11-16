/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.operationgroup;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

import com.savantit.prodacs.businessimplementation.job.OperationGroupDetails;
import com.savantit.prodacs.facade.SessionJobDetailsManager;
import com.savantit.prodacs.facade.SessionJobDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationGroupEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	{
		String action= "failure";
		/*Creating an instance of OperationGroupEditForm.java*/
		if (form instanceof OperationGroupEditForm)
		{
			OperationGroupEditForm frm = (OperationGroupEditForm)form;
			
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionJobDetailsManagerBean");
		   		obj.setEnvironment();

		   		/* 	Creating the Home and Remote Objects 	*/
				SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
				SessionJobDetailsManager opGpObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Details Object */
					OperationGroupDetails objOpGrpDetails = new OperationGroupDetails();
					if (BuildConfig.DMODE)
						System.out.println("-->"+frm.getId());
					objOpGrpDetails = opGpObj.getOperationGroupDetails(frm.getId());
					
					frm.setOperationGroupCode(objOpGrpDetails.getOperationGroupCode());
					frm.setMachDetsCheck(objOpGrpDetails.isMachineRelated()+"");
					if (BuildConfig.DMODE)
						System.out.println("objOpGrpDetails.isMachineRelated() :"+objOpGrpDetails.isMachineRelated());
					if (objOpGrpDetails.isMachineRelated() == true)
					{
						/* Converting HashMap details to String[] for multiple Selection */
						HashMap machine = objOpGrpDetails.getHm_MachineDetails();
						Set set = machine.entrySet();
						Iterator it = set.iterator();
						int i = 0;
						String mcCde = "";
						while (it.hasNext())
						{
							Map.Entry me = (Map.Entry) it.next();
							mcCde = mcCde + "^" + me.getKey().toString().trim();
							i++;
						}
						if (BuildConfig.DMODE)
							System.out.println(" --> "+mcCde);
						frm.setMachineName(mcCde);
					}
					else
					{
						frm.setMachineName(null);
					}
					if (BuildConfig.DMODE)
						System.out.println("Modified.");
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					/* Details Object */
					OperationGroupDetails objOpGrpDetails = new OperationGroupDetails();
					
					objOpGrpDetails.setOperationGroupId(frm.getId());
					objOpGrpDetails.setOperationGroupCode(frm.getOperationGroupCode());
					objOpGrpDetails.setMachineRelated((frm.getMachDetsCheck().equals("1"))?true:false);
					HashMap machineDetails = new HashMap();
					StringTokenizer machDets = new StringTokenizer(frm.getMachineName(),"$");
					while (machDets.hasMoreTokens())
					{
						String mcName = machDets.nextToken();
						machineDetails.put(mcName,"");
					}
					
					objOpGrpDetails.setHm_MachineDetails(machineDetails);
					
					boolean up = opGpObj.updateOperationGroupDetails(objOpGrpDetails);
					
					if (up == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Operation Group",frm.getOperationGroupCode()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Operation Group",frm.getOperationGroupCode()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
					action="success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make Valid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = opGpObj.makeOperationGroupValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeValid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Operation Group"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Operation Group"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Operation Group"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Operation Group"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Operation Group"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Operation Group"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Operation Group"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Operation Group"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Operation Group"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Operation Group"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Operation Group"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Operation Group"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";

				}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make InValid");
					
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
							System.out.println("**> "+ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						
						hm = opGpObj.makeOperationGroupInValid(v);
						if (BuildConfig.DMODE)
							System.out.println("Invalid(HM): "+ hm.size());
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeInvalid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Operation Group"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Operation Group"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Operation Group"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Operation Group"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Operation Group"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Operation Group"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Operation Group"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Operation Group"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Operation Group"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Operation Group"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Operation Group"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Operation Group"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}

		}
		if (BuildConfig.DMODE)
			System.out.println("OperationGroup Edit:"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: OperationGroupEditAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/07/14 16:20:04  vkrishnamoorthy
Modified as per MachineRelated values.

Revision 1.3  2005/03/03 09:57:57  vkrishnamoorthy
OperationGroupEdit modified.

Revision 1.2  2004/11/26 09:04:10  sponnusamy
Operation Group Forms are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/