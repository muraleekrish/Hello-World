/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

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
public class JobMasterAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of JobMasterAddForm.java*/
		if (form instanceof JobMasterAddForm)
		{
			JobMasterAddForm frm = (JobMasterAddForm) form;
		    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
		    StandardHours objStdHours = new StandardHours();
		    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());
		    
			if (frm.getFormAction().equalsIgnoreCase("add"))
			{
				/* EJBLocator */
				EJBLocator obj = new EJBLocator();
				ActionErrors errors = new ActionErrors();
				String grpId[];
				String sno[];
				String opGrpName[];
				String opName[];
				String stdHrs[];
				String opnLevelIncntv[];
				try
				{
				/* 	Setting the JNDI name and Environment 	*/
				   obj.setJndiName("SessionJobDetailsManagerBean");
				   obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
					SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
					SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
					
				/* Setting the form values to Details Variables */
					JobDetails objJobDetails = new JobDetails();
					
					objJobDetails.setJob_Name(frm.getJobName());
					objJobDetails.setCust_Typ_Id(Integer.parseInt(frm.getCustomerType()));
					objJobDetails.setCust_Id(Integer.parseInt(frm.getCustomerName()));
					if (BuildConfig.DMODE)
						System.out.println("CustId: "+objJobDetails.getCust_Id());
					objJobDetails.setJob_Gnrl_Id(Integer.parseInt(frm.getGeneralName()));
					objJobDetails.setJob_Dwg_No(frm.getDrawing());
					objJobDetails.setJob_Rvsn_No(frm.getRevision());
					objJobDetails.setJob_Matl_Type(frm.getMaterialType());
					if (BuildConfig.DMODE)
						System.out.println("Incentive: "+frm.getIncentive());
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
					if (BuildConfig.DMODE)
						System.out.println("Date in Action: "+ objJobDetails.getJob_Created_Date());
	
						/* Split the String into Tokens and put in to array */
						if (BuildConfig.DMODE)
							System.out.println("frm.getSno(): "+frm.getSno());
						
						/*StringTokenizer stSno = new StringTokenizer(frm.getSno(),"-");
						int count = stSno.countTokens();
						if (BuildConfig.DMODE)
							System.out.println("Total No. of Records: "+ count);
						
						 For GroupId 
						grpId = new String[count];
						StringTokenizer stGrpId = new StringTokenizer(frm.getGrpId(),"-");
						int c = 0;
						while (stGrpId.hasMoreTokens())
						{
							grpId[c] = stGrpId.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("grId:"+c+". "+grpId[c]);
							c++;
						}
						
						 For OperationGroupCode 
						opGrpName = new String[count];
						c = 0;
						StringTokenizer stOpGrpName = new StringTokenizer(frm.getOpGrpName(),"-");
						while (stOpGrpName.hasMoreTokens())
						{
							opGrpName[c] = stOpGrpName.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("GrpName:"+c+". "+opGrpName[c]);
							c++;
						}
						
						 For SerialNo 
						sno = new String[count];
						c = 0;
						while (stSno.hasMoreTokens())
						{
							sno[c] = stSno.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("sno:"+c+". "+sno[c]);
							c++;
						}
	
						 For Operation Name 
						opName = new String[count];
						StringTokenizer stOpName = new StringTokenizer(frm.getOpName(),"-");
						c = 0;
						while (stOpName.hasMoreTokens())
						{
							opName[c] = stOpName.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("opName:"+c+". "+opName[c]);
							c++;
						}
		
						 For StandardHours 
						stdHrs = new String[count];
						if (objStdHours.isOpnlevelstdhrs())
						{
							c = 0;
							StringTokenizer stStdHrs= new StringTokenizer(frm.getStdHrs(),"-");
							while (stStdHrs.hasMoreTokens())
							{
								stdHrs[c] = stStdHrs.nextToken();
								if (BuildConfig.DMODE)
									System.out.println("stdHrs:"+c+". "+stdHrs[c]);
								c++;
							}
						}
						
						 For Operation Level Incentive 
						opnLevelIncntv = new String[count];
						if (objStdHours.isJobopnlevelincentive())
						{
							c = 0;
							StringTokenizer stOpnLevelIncntv = new StringTokenizer(frm.getOpnLevelIncntv(),"-");
							while (stOpnLevelIncntv.hasMoreTokens())
							{
								opnLevelIncntv[c] = stOpnLevelIncntv.nextToken();
								c++;
							}
						}
						
					 Inserting all the records one by one 	
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
	*/				////
					Vector vec_opDet = new Vector();
					StringTokenizer stStdHrs = null;
					StringTokenizer stOpnLevelIncntv = null;
					
					StringTokenizer stSno = new StringTokenizer(frm.getSno(),"~");
					StringTokenizer stGrpId = new StringTokenizer(frm.getGrpId(),"~");
					StringTokenizer stOpGrpName = new StringTokenizer(frm.getOpGrpName(),"~");
					StringTokenizer stOpName = new StringTokenizer(frm.getOpName(),"~");
					
					if (objStdHours.isOpnlevelstdhrs())
					stStdHrs= new StringTokenizer(frm.getStdHrs(),"~");
					if (objStdHours.isJobopnlevelincentive())
					stOpnLevelIncntv = new StringTokenizer(frm.getOpnLevelIncntv(),"~");
					while(stSno.hasMoreTokens())
					{
					    OperationDetails objOprDetails = new OperationDetails();
					    objOprDetails.setOpnGpId(Integer.parseInt(stGrpId.nextToken()));
					    objOprDetails.setOpnGpCode(stOpGrpName.nextToken());
					    objOprDetails.setOpnSerialNo(Integer.parseInt(stSno.nextToken()));
					    objOprDetails.setOpnName(stOpName.nextToken());
					    if (objStdHours.isOpnlevelstdhrs())
					    {
					        objOprDetails.setOpnStdHrs(Float.parseFloat(stStdHrs.nextToken()));
					    }
					    if (objStdHours.isJobopnlevelincentive())
					    {
					        objOprDetails.setOpnIncentive(stOpnLevelIncntv.nextToken().equalsIgnoreCase("true")?true:false);
					    }
					    vec_opDet.addElement(objOprDetails);
					    
					}
					
					
					
					
					
					////
					objJobDetails.setVec_OpnDetails(vec_opDet);
					if (BuildConfig.DMODE)
						System.out.println("Vector: "+objJobDetails.getVec_OpnDetails());
					
					boolean added = jobObj.addJobDetails(objJobDetails);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Job Details",frm.getJobName()));
						
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
				catch(JobException e)
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
		}
		if (BuildConfig.DMODE)
			System.out.println("JobMaster Add:"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: JobMasterAddAction.java,v $
Revision 1.9  2005/08/20 09:56:58  vkrishnamoorthy
Delimiters changed.

Revision 1.8  2005/07/27 06:08:55  kduraisamy
string tokenizer unwanted loops removed.

Revision 1.7  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.6  2005/07/05 09:58:31  vkrishnamoorthy
Job Operation Level Incentive added.

Revision 1.5  2005/06/23 13:25:05  vkrishnamoorthy
StdHrs and Incentive calculated as per XML entries.

Revision 1.4  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.3  2005/01/30 11:13:47  sponnusamy
CustomerId passed instead of customer type id

Revision 1.2  2004/12/03 06:40:48  sponnusamy
JobMaster controller is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/