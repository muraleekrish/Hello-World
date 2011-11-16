/*
 * Created on Mar 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.securityadministration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails;
import com.savantit.prodacs.businessimplementation.securityadmin.SecurityAdminException;
import com.savantit.prodacs.facade.SessionSecurityAdminManager;
import com.savantit.prodacs.facade.SessionSecurityAdminManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		
		/* Instance Creation for GroupEditForm */ 
		if (form instanceof GroupEditForm)
		{
			GroupEditForm frm = (GroupEditForm) form;
			
			/* Instance Creation for EJBLocator */
			
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/* JNDI Name and Environment Setting */
				obj.setJndiName("SessionSecurityAdminManager");
				obj.setEnvironment();
				
				/* Creation of Home and Remote Objects */
				SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
				SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					try
					{
						if (BuildConfig.DMODE)
							System.out.println("Modification Starts");
						SecAdminGroupDetails secAdminGrpObj = new SecAdminGroupDetails();
						
						secAdminGrpObj = secAdminObj.getGroupDetails(frm.getId());
						
						frm.setGroupName(secAdminGrpObj.getGroup_Name());
						frm.setGroupDesc(secAdminGrpObj.getGroup_Desc());
						
						HashMap resources = new HashMap();
	
						resources = secAdminGrpObj.getHmResources();
						String[] selectedResources = new String[resources.size()];
						if (BuildConfig.DMODE)
							System.out.println("Resource Size :"+resources.size());
						int i = 0;
						for(Iterator it=resources.entrySet().iterator();it.hasNext() ;)
						{
							Map.Entry entry = (Map.Entry)it.next();
							String key = entry.getKey().toString();
							selectedResources[i] = key;
							i++;
						}
						frm.setSelectedResource(selectedResources);
						if (BuildConfig.DMODE)
						{
							System.out.println("Selected Resources :"+selectedResources);
							System.out.println("Modification Ends");
						}
					}
					catch (SecurityAdminException se)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";

					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation Starts");
					SecAdminGroupDetails secAdminGrpObj = new SecAdminGroupDetails();
					
					secAdminGrpObj.setGroup_Id(frm.getId());
					secAdminGrpObj.setGroup_Name(frm.getGroupName());
					secAdminGrpObj.setGroup_Desc(frm.getGroupDesc());
					
					Vector vecSelectedResources = new Vector();
					if (frm.getSelectedResource() != null)
					{
						String[] selectedResources = frm.getSelectedResource();
						for(int i = 0; i < selectedResources.length; i++)
						{
							if (BuildConfig.DMODE)
								System.out.println("RESOURCE ID :"+selectedResources[i]);
							vecSelectedResources.add(selectedResources[i]);
						}
					}
					secAdminGrpObj.setVResources(vecSelectedResources);
					boolean updated = secAdminObj.updateGroup(secAdminGrpObj);
					if (BuildConfig.DMODE)
						System.out.println("updated :"+updated);
					if (updated == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Group Name",frm.getGroupName()));
						saveMessages(request,message);
						action = "success";						
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action="failure";				  			
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("delete"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Delete");
					
					String ids[] = frm.getIds();
					Vector delList = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						delList.add(new Integer(ids[i]));
						if (BuildConfig.DMODE)
							System.out.println("Del List: "+ ids[i]);
					}
					HashMap hm = new HashMap();
					try
					{
						hm = secAdminObj.deleteGroups(delList);
						if (BuildConfig.DMODE)
							System.out.println("Delete(HM): "+hm);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while Deleting the Group");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.deleted", Integer.toString(success),"Group"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.deleted", Integer.toString(success),"Groups"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Group"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Groups"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Group"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Groups"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Group"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Groups"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Group"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Groups"));
					}
					if(hm.size() < delList.size())
					{
						int diff = delList.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.delete.general", Integer.toString(diff),"Group"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.delete.general", Integer.toString(diff),"Groups"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make Valid");
						System.out.println("ID's ->"+frm.getIds());
					}
					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = secAdminObj.makeGroupsValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Group"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Group"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Group"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Group"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Group"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Group"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Group"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Group"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Group"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Group"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Group"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Group"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";

				}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
					{
						System.out.println("Make InValid");
						System.out.println("ID's ->"+frm.getIds());
					}
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
						
						hm = secAdminObj.makeGroupsInValid(v);
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Group"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Group"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Group"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Group"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Group"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Group"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Group"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Group"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Group"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Group"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Group"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Group"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch (SecurityAdminException se)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",se.getExceptionMessage()));
				if(!errors.isEmpty())
					saveErrors(request,errors);
				action = "failure";
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}			
		}
		if (BuildConfig.DMODE)
			System.out.println("GroupEdit Action :"+action);
		return mapping.findForward(action);
	}
}

/***
 $Log: GroupEditAction.java,v $
 Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
 DMODE added to avid System.out.println()'s.

 Revision 1.5  2005/06/16 13:05:26  vkrishnamoorthy
 Exception caught for multiple instances Group deletion.

 Revision 1.4  2005/05/28 03:54:11  vkrishnamoorthy
 Error messages thrown appropriately.

 Revision 1.3  2005/04/06 11:44:06  vkrishnamoorthy
 Delete option added.

 Revision 1.2  2005/04/05 12:25:45  vkrishnamoorthy
 Unwanted Println's removed.

 Revision 1.1  2005/03/26 10:08:18  vkrishnamoorthy
 Initial commit on Prodacs.

 ***/
