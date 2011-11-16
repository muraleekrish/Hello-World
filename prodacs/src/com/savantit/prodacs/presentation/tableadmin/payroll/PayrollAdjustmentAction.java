/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.payroll;

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

import com.savantit.prodacs.businessimplementation.payroll.PayRollDetails;
import com.savantit.prodacs.facade.SessionPayrollDetailsManager;
import com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayrollAdjustmentAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";
		
		if (form instanceof PayrollAdjustmentForm)
		{
			/* Instance of Payroll Adjustment Form */
			PayrollAdjustmentForm frm=(PayrollAdjustmentForm) form;
			
			ActionErrors errors = new ActionErrors();
			ActionMessages message = new ActionMessages();
			String[] pyrlRecList;
			String[] pyrlRecPrnlList;
			try
			{
				EJBLocator obj = new EJBLocator();
				obj.setJndiName("SessionPayrollDetailsManagerBean");
				obj.setEnvironment();
				
				/* Creation of Home and Remote object*/
				SessionPayrollDetailsManagerHome objPayroll = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class); 
				SessionPayrollDetailsManager payrollDetailsObj = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayroll.create(),SessionPayrollDetailsManager.class);
				
				
				if (frm.getFormAction().equalsIgnoreCase("adjust"))
				{
					Vector vec_pyrlDetails = new Vector();	
					
					StringTokenizer stPyrlDet = new StringTokenizer(frm.getPayrollDet(),"^");
					pyrlRecList = new String[stPyrlDet.countTokens()];
					int count = 0;
					while (stPyrlDet.hasMoreTokens())
					{
						pyrlRecList[count] = stPyrlDet.nextToken();
						count++;
					}
					
					for (int i = 0; i < pyrlRecList.length; i++)
					{
						PayRollDetails objPayRollDetails = new PayRollDetails();
						StringTokenizer stPyrlRecList = new StringTokenizer(pyrlRecList[i],"-");
						pyrlRecPrnlList = new String[stPyrlRecList.countTokens()];
						int recCount = 0;
						while (stPyrlRecList.hasMoreTokens())
						{
							pyrlRecPrnlList[recCount] = stPyrlRecList.nextToken();
							if (BuildConfig.DMODE)
								System.out.println("-> "+ pyrlRecPrnlList[recCount]);
							recCount++;
						}

						objPayRollDetails.setPyrlCycleStatId(Integer.parseInt(frm.getPayrollCycle()));
						objPayRollDetails.setPrePyrlId(Integer.parseInt(pyrlRecPrnlList[0]));

						String pyrlDate = pyrlRecPrnlList[1]+"-"+pyrlRecPrnlList[2]+"-"+pyrlRecPrnlList[3];
						StringTokenizer st = new StringTokenizer(pyrlDate,"-");
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
						objPayRollDetails.setProdDate(ge.getTime());

						objPayRollDetails.setShiftName(pyrlRecPrnlList[4]);
						objPayRollDetails.setEmpName(pyrlRecPrnlList[5]);
						objPayRollDetails.setDtyHrs(Float.parseFloat(pyrlRecPrnlList[6]));
						objPayRollDetails.setOtHrs(Float.parseFloat(pyrlRecPrnlList[7]));
						objPayRollDetails.setDtySlryHrs(Float.parseFloat(pyrlRecPrnlList[8]));
						objPayRollDetails.setOtSlryHrs(Float.parseFloat(pyrlRecPrnlList[9]));
						objPayRollDetails.setIncntvSlryHrs(Float.parseFloat(pyrlRecPrnlList[10]));
						objPayRollDetails.setNoOfTimesAdjstd(Integer.parseInt(pyrlRecPrnlList[11]));
						
						vec_pyrlDetails.add(objPayRollDetails);
					}
					if (BuildConfig.DMODE)
						System.out.println("vec_pyrlDetails Size: "+vec_pyrlDetails.size());
					try
					{
						String userName = frm.getHidUserId();
						boolean pyrlAdjust = payrollDetailsObj.adjustPayRollDetails(vec_pyrlDetails,userName);

						if (pyrlAdjust == true)
						{
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","PayRoll",""));
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
					catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Error in Payroll AdjustMentAction.");
							e.printStackTrace();
						}
					}
				}
			}
			catch (Exception e)
			{
				if (BuildConfig.DMODE)
					System.out.println("Error: "+ e.toString());
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Payroll Adjust Action: "+ action);
		return mapping.findForward(action);
	}
}

/***
$Log: PayrollAdjustmentAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/06/11 07:01:06  vkrishnamoorthy
UserName added as parameter while passing.

Revision 1.3  2005/02/03 04:59:39  vkrishnamoorthy
Initial commit on PayrollAdjustmentAction.

***/