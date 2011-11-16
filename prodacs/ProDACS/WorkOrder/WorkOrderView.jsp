<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import = "java.util.Vector" %>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails"%>
<%@ page import="com.savantit.prodacs.util.CastorXML"%>
<%@ page import="com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours"%>

<useradmin:userrights resource="13"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("WorkOrder View");
    StandardHours objStdHours = new StandardHours();
    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());

	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	WorkOrderDetails objWODetails = null;
	Vector vec_WOJobDet = new Vector();
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkOrderDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
		objWODetails = workOrderObj.getWorkOrderDetails(Integer.parseInt(id));
		
		vec_WOJobDet = objWODetails.getWOJobDetails();
		
		
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in WorkOrder View Page.");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.workorder.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objWODetails.getWorkOrderIsValid() %>;
	
	function chkMkValid()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("workOrderFromDate") %>';
		temp.toDate.value = '<%= request.getParameter("workOrderToDate") %>';
		temp.woNo.value = '<%= request.getParameter("woNo") %>';
		temp.woCombo.value = '<%= request.getParameter("woCombo") %>';
		temp.customer.value = '<%= request.getParameter("customer") %>';
		temp.custCombo.value = '<%= request.getParameter("custCombo") %>';
		temp.woStatus.value = '<%= request.getParameter("workOrderStatus") %>';
		temp.viewAll.value = '<%= request.getParameter("listValidEntries") %>';
		temp.maxItem.value = '<%= request.getParameter("maxItems") %>';
		temp.jbName.value =  '<%= request.getParameter("jbName") %>';
		temp.jbNameCombo.value =  '<%= request.getParameter("jbNameCombo") %>';
		temp.dwgNo.value =  '<%= request.getParameter("dwgNo") %>';
		temp.dwgNoCombo.value =  '<%= request.getParameter("dwgNoCombo") %>';
		temp.pageNo.value = '<%= request.getParameter("page") %>';

		if (document.getElementById('mkValid') != null)
		{
			if(isValid == 0)
			{
				mkValid.style.display = 'block';
				mkInValid.style.display = 'none';
			}
			else
			{
				mkValid.style.display = 'none';
				mkInValid.style.display = 'block';	
			}
		}
	}
	
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.frmDate.value != "" ) || (temp.toDate.value != "") || (temp.woNo.value != "") || (temp.woCombo.value != "") || (temp.customer.value != "") || (temp.custCombo.value != "") || (temp.woStatus.value != "") || (temp.viewAll.value != "") || (temp.maxItem.value != "") || (temp.jbName.value != "") || (temp.jbNameCombo.value != "") || (temp.dwgNo.value != "") || (temp.dwgNoCombo.value != "") || (temp.pageNo.value))
		{
			temp.action = '<bean:message key="context"/>/frmWrkOrdList.do?formAction=afterView';
			temp.submit();
		}
		else
		{
			if (temp.maxItem.value == null)
			{
				temp.maxItems.value = "15";
				temp.action = '<bean:message key="context"/>/WorkOrder/WorkOrderList.jsp?maxItem='+temp.maxItem.value;
				temp.submit();
			}
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/WorkOrderAdd.jsp';
		document.forms[0].submit();
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=modify&id='+<%= objWODetails.getWorkOrderId() %>;
			document.forms[0].submit();
		}	
	}

</script>
</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="frmWorkOrderEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="woNo"/>
<input type="hidden" name="woCombo"/>
<input type="hidden" name="customer"/>
<input type="hidden" name="custCombo"/>
<input type="hidden" name="woStatus"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="maxItem"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="jbNameCombo"/>
<input type="hidden" name="dwgNo"/>
<input type="hidden" name="dwgNoCombo"/>
<input type="hidden" name="pageNo"/>

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make WorkOrder Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make WorkOrder Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
        </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="110" class="FieldTitle"><bean:message key="prodacs.workorder.workorderdate"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objWODetails.getWoCreatedDate().toString().substring(0,10) %></td>
            <td class="FieldTitle" nowrap><bean:message key="prodacs.workorder.deliverycommitment"/></td>
            <td class="FieldTitle">:</td>
            <td colspan="4" class="ViewData"><%= objWODetails.getWoEstmdCompleteDate().toString().substring(0,10) %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objWODetails.getCustName() %></td>
            <td width="110" class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objWODetails.getCustTypName() %></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.workorder.workorder"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objWODetails.getWorkOrderNo() %></td>
            <td class="FieldTitle"><bean:message key="prodacs.workorder.contactname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objWODetails.getContactPerson() %></td>
          </tr>
          <tr>
          <%
          if (objWODetails.getWorkOrderStatus().equalsIgnoreCase("c"))
          {
          %>
	            <td class="FieldTitle"><bean:message key="prodacs.workorder.wostatus"/></td>
	            <td class="FieldTitle">:</td>
	            <td class="ViewData">Completed</td>
		  <%
		   }
		   else if (objWODetails.getWorkOrderStatus().equalsIgnoreCase("a"))
		   {
		   %>
	            <td class="FieldTitle"><bean:message key="prodacs.workorder.wostatus"/></td>
	            <td class="FieldTitle">:</td>
	            <td class="ViewData">Active</td>
			<%
			}
			else
			{
			%>
	            <td class="FieldTitle"><bean:message key="prodacs.workorder.wostatus"/></td>
	            <td class="FieldTitle">:</td>
	            <td class="ViewData">Open</td>
			<%
			 }
			%>
          </tr>
        </table>
        <hr><br>
<%
		for (int i = 0; i < vec_WOJobDet.size(); i++)
		{
			WOJobDetails objJobDet = new WOJobDetails();
			objJobDet = (WOJobDetails) vec_WOJobDet.get(i);
%>		
		<table width = "100%" cellspacing="0" cellpadding="3">
		<tr class="Header1"><td> <%= i+1 %> . <bean:message key="prodacs.workorder.workorderjobdetails"/></td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="3">
		<tr> 
		    <td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getJobName() %></td>

		    <td class="FieldTitle"><bean:message key="prodacs.job.drawingno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getJobDrwgNo() %></td>
        </tr>
		<tr> 
		   <td class="FieldTitle"><bean:message key="prodacs.job.revisionno"/></td>
           <td class="FieldTitle">:</td>
           <td class="ViewData"><%= objJobDet.getJobRvsnNo() %></td>

			<td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getJobMatlType() %></td>
        </tr>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.workorder.jobqtystartsno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getJobQtyStartSno() %></td>

			<td class="FieldTitle"><bean:message key="prodacs.workorder.jobqty"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getJobQty() %></td>
        </tr>
        <tr>
			<td class="FieldTitle"><bean:message key="prodacs.shipment.partyorderno"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getWoDCNo() %></td>

			<td class="FieldTitle"><bean:message key="prodacs.shipment.partyorderdate"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objJobDet.getWoDCDate().toString().substring(0,10) %></td>
        </tr>
        <tr>
        <%
        if (objJobDet.getJobStatus().equalsIgnoreCase("c"))
        {
        %>
			<td class="FieldTitle"><bean:message key="prodacs.workorder.jbstatus"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">Completed</td>
        <%
        }
        else if (objJobDet.getJobStatus().equalsIgnoreCase("a"))
        {
        %>
			<td class="FieldTitle"><bean:message key="prodacs.workorder.jbstatus"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">Active</td>
        <%
        }
        else
        {
        %>
			<td class="FieldTitle"><bean:message key="prodacs.workorder.jbstatus"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData">Open</td>
		<%
		}
		%>
		</tr>        
		</table><br>
	<%
		if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
		{
	%>
    	  <table width="100%" cellspacing="0" cellpadding="2">
          <tr> 
            <td width="175" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
            <td class="Header"><bean:message key="prodacs.job.operationname"/></td>
            <td width="90" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
			<td width="70" class="Header"><bean:message key="prodacs.job.incentive"/></td>
          </tr>
<%
			Vector vec_JobOpnDet = objJobDet.getWOJobOpnDetails();
			for (int j = 0; j < vec_JobOpnDet.size(); j++)
			{
				WOJobOpnDetails objWoJobOpnDet = new WOJobOpnDetails();
				objWoJobOpnDet = (WOJobOpnDetails) vec_JobOpnDet.get(j);
%>          
          <tr> 
            <td class="TableItems"><%= objWoJobOpnDet.getOpnGpCode() %></td>
            <td class="TableItems"><%= objWoJobOpnDet.getOpnSerialNo() %></td>
            <td class="TableItems"><%= objWoJobOpnDet.getOpnName() %></td>
            <td class="TableItems"><%= objWoJobOpnDet.getOpnStdHrs() %></td>
			<%
			if (objWoJobOpnDet.isOpnIncentive() == true)
			{
			%>
				<td class="TableItems" align="center"><img src='<bean:message key="context"/>/images/active.gif'></td>
			<%
			}
			else
			{
			%>
				<td class="TableItems" align="center"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
			<%
			}
			%>
          </tr>
		<%
		}
		%>          
       </table><br><br>
		<%
		}
		else if ((!objStdHours.isStdhrs()) && (!objStdHours.isOpnlevelstdhrs()) && (!objStdHours.isIncentive()) && (!objStdHours.isJobopnlevelincentive()))
		{
		%>
    	  <table width="100%" cellspacing="0" cellpadding="3">
          <tr> 
            <td width="175" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
            <td class="Header"><bean:message key="prodacs.job.operationname"/></td>
          </tr>
			<%
			Vector vec_JobOpnDet = objJobDet.getWOJobOpnDetails();
			for (int j = 0; j < vec_JobOpnDet.size(); j++)
			{
				WOJobOpnDetails objWoJobOpnDet = new WOJobOpnDetails();
				objWoJobOpnDet = (WOJobOpnDetails) vec_JobOpnDet.get(j);
			%>          
          <tr> 
            <td class="TableItems"><%= objWoJobOpnDet.getOpnGpCode() %></td>
            <td class="TableItems"><%= objWoJobOpnDet.getOpnSerialNo() %></td>
            <td class="TableItems"><%= objWoJobOpnDet.getOpnName() %></td>
		  </tr>
		<%
			}
		%>
       </table><br><br>
	<%
		}
	}
	%>
        <br> <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr> 
            <td><html:button property="retWOList" styleClass="Button" onclick="javascript:listItem()" value = "Return to WorkOrder List" /></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
