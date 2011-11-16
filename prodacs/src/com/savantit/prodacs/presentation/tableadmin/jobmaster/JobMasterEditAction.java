/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import com.savantit.prodacs.businessimplementation.job.JobDetails;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.facade.SessionJobDetailsManager;
import com.savantit.prodacs.facade.SessionJobDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.CastorXML;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class JobMasterEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of JobMasterEditForm.java*/
		if (form instanceof JobMasterEditForm)
		{
			JobMasterEditForm frm = (JobMasterEditForm) form;
		    InputStream is = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
		    StandardHours objStdHours = new StandardHours();
		    objStdHours = (StandardHours)CastorXML.fromXML(is,objStdHours.getClass());

			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
			/* 	Setting the JNDI name and Environment 	*/
			   obj.setJndiName("SessionJobDetailsManagerBean");
			   obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
				SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
				SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					/* Object to JobDetails */
					JobDetails objJobDetails = new JobDetails();
					
					objJobDetails = jobObj.getJobDetails(frm.getId());
						
					frm.setDate(objJobDetails.getJob_Created_Date().toString().substring(0,10));
					frm.setJobName(objJobDetails.getJob_Name());
					frm.setCustomerType(objJobDetails.getCust_Typ_Name());
					frm.setCustTypId(objJobDetails.getCust_Typ_Id()+"");
					if (BuildConfig.DMODE)
						System.out.println("frm.getCustTypId(): "+frm.getCustTypId());
					frm.setCustomerName(objJobDetails.getCust_Name());
					frm.setCustId(objJobDetails.getCust_Id()+"");
					if (BuildConfig.DMODE)
						System.out.println("frm.getCustId(): "+frm.getCustId());
					frm.setGeneralName(objJobDetails.getJob_Gnrl_Id()+"");
					frm.setDrawing(objJobDetails.getJob_Dwg_No());
					frm.setRevision(objJobDetails.getJob_Rvsn_No());
					frm.setMaterialType(objJobDetails.getJob_Matl_Type());
	            	if (objStdHours.isStdhrs())
	            	{
	            		if (objStdHours.isJoblevelstdhrs())
	            		{
	            			if (objStdHours.isIncentive())
	            			{
	            				frm.setIncentive((objJobDetails.getJob_Incntv_Flag().equalsIgnoreCase("1"))?"on":"off");
	            			}
	            		}
	            	}
	            	if (BuildConfig.DMODE)
	            		System.out.println("Std Hrs :"+frm.getStdHrs());
	            	if (objStdHours.isStdhrs())
	            	{
	            		if (objStdHours.isJoblevelstdhrs())
	            		{
	            			frm.setJobStandardHrs(objJobDetails.getJob_stdHrs()+"");
	            		}
	            	}
					Vector vec_OpDet = objJobDetails.getVec_OpnDetails();
					OperationDetails objOperDet = null;
					String grpId = "";
					String sno = "";
					String opGrpName = "";
					String opName = "";
					String stdHrs = "";
					String opnLevelIncntv = "";
					
					for (int i = 0; i < vec_OpDet.size(); i++)
					{
						objOperDet = (OperationDetails) vec_OpDet.get(i);
						if (i == 0)
						{
							grpId = objOperDet.getOpnGpId()+"";
							sno = objOperDet.getOpnSerialNo()+"";
							opGrpName = objOperDet.getOpnGpCode()+"";
							opName = objOperDet.getOpnName()+"";
							stdHrs = objOperDet.getOpnStdHrs()+"";
							opnLevelIncntv = objOperDet.isOpnIncentive()+"";
						}
						else
						{
							grpId = grpId + "~" + objOperDet.getOpnGpId();
							sno = sno + "~" + objOperDet.getOpnSerialNo();
							opGrpName = opGrpName + "~" + objOperDet.getOpnGpCode();
							opName = opName + "~" + objOperDet.getOpnName();
							stdHrs = stdHrs + "~" + objOperDet.getOpnStdHrs();
							opnLevelIncntv = opnLevelIncntv + "~" +objOperDet.isOpnIncentive();
						}
					}
					frm.setGrpId(grpId);
					frm.setSno(sno);
					frm.setOpGrpName(opGrpName);
					frm.setOpName(opName);
					frm.setOpnLevelIncntv(opnLevelIncntv);
					if (objStdHours.isOpnlevelstdhrs())
					{
						frm.setStdHrs(stdHrs);
					}
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation");
					
					/* Object to JobDetails */
					JobDetails objJobDetails = new JobDetails();
					String grpId[];
					String sno[];
					String opName[];
					String stdHrs[];
					String opGrpName[];
					String opnLevelIncntv[];
					
					/* Date Conversion for Job Posting Date */
					StringTokenizer st = new StringTokenizer(frm.getDate(),"-");
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
					objJobDetails.setJob_Created_Date(ge.getTime());
					objJobDetails.setJob_Id(frm.getId());
					if (BuildConfig.DMODE)
						System.out.println("frm.getId(): "+ frm.getId());
					objJobDetails.setJob_Name(frm.getJobName());
					objJobDetails.setCust_Typ_Id(Integer.parseInt(frm.getCustTypId()));
					objJobDetails.setCust_Id(Integer.parseInt(frm.getCustId()));
					objJobDetails.setJob_Gnrl_Id(Integer.parseInt(frm.getGeneralName()));
					objJobDetails.setJob_Dwg_No(frm.getDrawing());
					objJobDetails.setJob_Rvsn_No(frm.getRevision());
					objJobDetails.setJob_Matl_Type(frm.getMaterialType());
	            	if (objStdHours.isStdhrs())
	            	{
	            		if (objStdHours.isJoblevelstdhrs())
	            		{
	            			if (objStdHours.isIncentive())
	            			{
	            				objJobDetails.setJob_Incntv_Flag(((frm.getIncentive().equals(""))?"0":"1"));
	            			}
	            		}
	            	}
	            	if (BuildConfig.DMODE)
	            		System.out.println("Std Hrs :"+frm.getStdHrs());
	            	if (objStdHours.isStdhrs())
	            	{
	            		if (objStdHours.isJoblevelstdhrs())
	            		{
	            			objJobDetails.setJob_stdHrs(Float.parseFloat(frm.getJobStandardHrs().trim()));
	            		}
	            	}
					/* Split the String into Tokens and put in to array */
					StringTokenizer stSno = new StringTokenizer(frm.getSno(),"~");
					int count = stSno.countTokens();
					if (BuildConfig.DMODE)
						System.out.println("Total No. of Records: "+ count);
					
					/* For GroupId */
					grpId = new String[count];
					StringTokenizer stGrpId = new StringTokenizer(frm.getGrpId(),"~");
					int c = 0;
					while (stGrpId.hasMoreTokens())
					{
						grpId[c] = stGrpId.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("grId:"+c+". "+grpId[c]);
						c++;
					}
					
					/* For OperationGroupCode */
					opGrpName = new String[count];
					c = 0;
					StringTokenizer stOpGrpName = new StringTokenizer(frm.getOpGrpName(),"~");
					while (stOpGrpName.hasMoreTokens())
					{
						opGrpName[c] = stOpGrpName.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("GrpName:"+c+". "+opGrpName[c]);
						c++;
					}
					
					/* For SerielNo */
					sno = new String[count];
					c = 0;
					while (stSno.hasMoreTokens())
					{
						sno[c] = stSno.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("sno:"+c+". "+sno[c]);
						c++;
					}

					/* For Operation Name */
					opName = new String[count];
					StringTokenizer stOpName = new StringTokenizer(frm.getOpName(),"~");
					c = 0;
					while (stOpName.hasMoreTokens())
					{
						opName[c] = stOpName.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("opName:"+c+". "+opName[c]);
						c++;
					}
	
					/* For StandardHours */
					stdHrs = new String[count];
					c = 0;
					StringTokenizer stStdHrs= new StringTokenizer(frm.getStdHrs(),"~");
					while (stStdHrs.hasMoreTokens())
					{
						stdHrs[c] = stStdHrs.nextToken();
						if (BuildConfig.DMODE)
							System.out.println("stdHrs:"+c+". "+stdHrs[c]);
						c++;
					}
					
					/* For Operation Level Incentive */
					opnLevelIncntv = new String[count];
					if (objStdHours.isJobopnlevelincentive())
					{
						c = 0;
						StringTokenizer stOpnLevelIncntv = new StringTokenizer(frm.getOpnLevelIncntv(),"~");
						while (stOpnLevelIncntv.hasMoreTokens())
						{
							opnLevelIncntv[c] = stOpnLevelIncntv.nextToken();
							c++;
						}
					}
					
					/* Inserting all the records one by one */	
					Vector vec_opDet = new Vector();
					for (int i = 0; i < count; i++)
					{
						OperationDetails objOprDetails = new OperationDetails();
						objOprDetails.setOpnGpId(Integer.parseInt(grpId[i]));
						objOprDetails.setOpnGpCode(opGrpName[i]);
						objOprDetails.setOpnSerialNo(Integer.parseInt(sno[i]));
						objOprDetails.setOpnName(opName[i]);
						if (objStdHours.isOpnlevelstdhrs())
						{
							objOprDetails.setOpnStdHrs(Float.parseFloat(stdHrs[i]));
						}
						if (objStdHours.isJobopnlevelincentive())
						{
							objOprDetails.setOpnIncentive(opnLevelIncntv[i].equalsIgnoreCase("true")?true:false);
						}
						vec_opDet.addElement(objOprDetails);
					}
					objJobDetails.setVec_OpnDetails(vec_opDet);
					if (BuildConfig.DMODE)
						System.out.println("Vec_OpnDetails: "+vec_opDet);
					
					boolean up = jobObj.updateJobDetails(objJobDetails);
					
					if (up == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","JobDetails ",frm.getJobName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","JobDetails",frm.getJobName()));
						saveMessages(request,messages);
						action="success";				  			
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
					}
					HashMap hm = new HashMap();
					boolean flag = false;
					try
					{
						hm = jobObj.deleteJobDetails(delList);
						if (BuildConfig.DMODE)
							System.out.println("Delete(HM): "+hm);
					}catch (Exception e)
					{
						flag = true;
						if (BuildConfig.DMODE)
							System.out.println("Problem while Deletion in JobMasterEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.deleted", Integer.toString(success),"Job Detail"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.deleted", Integer.toString(success),"Job Details"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Job Detail"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Job Details"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Job Detail"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Job Details"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Job Detail"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Job Details"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Job Detail"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Job Details"));
					}
					if (flag == true)
					{
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.deletion","Job Detail"));
					}
					else
					{
						if(hm.size() < delList.size())
						{
							int diff = delList.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.delete.general", Integer.toString(diff),"Job Detail"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.delete.general", Integer.toString(diff),"Job Details"));
						}
					}
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("MakeValid");
					
					String ids[] = frm.getIds();
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = jobObj.makeJobValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while MakeValid in JobMasterEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Job Detail"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Job Details"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Job Detail"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Job Details"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Job Detail"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Job Details"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Job Detail"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Job Details"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Job Detail"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Job Details"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Job Detail"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Job Details"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("MakeInValid");
					
					String ids[] = frm.getIds();
					Vector tot = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						tot.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = jobObj.makeJobInValid(tot);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem while MakeInValid in JobMasterEdit Action");
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
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Job Detail"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Job Details"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Job Detail"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Job Details"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Job Detail"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Job Details"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Job Detail"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Job Details"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Job Detail"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Job Details"));
					}
					if(hm.size() < tot.size())
					{
						int diff = tot.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Job Detail"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Job Details"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
			}
			catch(JobException e)
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
				if(!errors.isEmpty())
				{
					saveErrors(request,errors);
				}
				action = "failure";
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
				{
					System.out.println("Problem while JobMasterEdit Action");
					e.printStackTrace();
				}
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("JobMaster Edit:"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: JobMasterEditAction.java,v $
Revision 1.11  2005/08/20 09:56:58  vkrishnamoorthy
Delimiters changed.

Revision 1.10  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.9  2005/07/05 11:40:26  vkrishnamoorthy
Job Operation Level Incentive added.

Revision 1.8  2005/06/24 06:08:11  vkrishnamoorthy
StdHrs and Incentive calculated as per XML entries.

Revision 1.7  2005/05/16 12:38:13  vkrishnamoorthy
Modified for delete.

Revision 1.6  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.5  2005/03/12 09:04:20  sponnusamy
Date Problems are Corrected.

Revision 1.4  2005/02/11 11:21:49  sponnusamy
Warnings removed

Revision 1.3  2005/01/27 06:01:22  vkrishnamoorthy
Appropriate JobException caught in JobMasterEditAction.

Revision 1.2  2004/12/03 06:40:48  sponnusamy
JobMaster controller is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/