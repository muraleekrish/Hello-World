<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails"%>
<useradmin:userrights resource="14"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Dmy Wo Starts...");
	EJBLocator obj = new EJBLocator();
	WorkOrderDetails objWorkOrderDetails = new WorkOrderDetails();
	Vector vec_WOJobDet = new Vector();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkOrderDetailsManagerBean");
		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
		objWorkOrderDetails = workOrderObj.getDummyWorkOrderDetails(Integer.parseInt(id));
		
		vec_WOJobDet = objWorkOrderDetails.getWOJobDetails();
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Error in Dmy wo Edit.jsp: "+ e.toString());
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.workorder.dummyworkorder"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objWorkOrderDetails.getWorkOrderIsValid() %>;

	function listItem()
	{
		temp = document.forms[0];
		if ((temp.frmDate.value != "" ) || (temp.toDate.value != "") || (temp.custName.value != "") || (temp.custCombo.value != "") || (temp.woStatus.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmDumWrkOrdList.do?formAction=afterView';
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/WorkOrder/DummyWorkOrderList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/DummyWorkOrderAdd.jsp';
		document.forms[0].submit();
	}

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.frmDate.value = '<%= request.getParameter("workOrderFromDate") %>';
		temp.toDate.value = '<%= request.getParameter("workOrderToDate") %>';
		temp.custName.value = '<%= request.getParameter("customerText") %>';
		temp.custCombo.value = '<%= request.getParameter("custCombo") %>';
		temp.woStatus.value = '<%= request.getParameter("workOrderStatus") %>';
		temp.viewAll.value = '<%= request.getParameter("listValidEntries") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 0)
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

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmDumWrkOrdEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmDumWrkOrdEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

</script>
</head>

<body onLoad="chkMkValid();loadToHidden();">
<html:form action="frmDumWrkOrdEdit" method="post">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="frmDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="custName"/>
<input type="hidden" name="custCombo"/>
<input type="hidden" name="woStatus"/>
<input type="hidden" name="viewAll"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
      <tr>
      	<td><bean:message key="prodacs.workorder.dummyworkorder"/></td>
	</tr>
      </table>
      <br>
      <table width="100" cellspacing="0" cellpadding="0" align="right">
      <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New DummyWorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1014" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make DummyWorkOrder Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1014" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make DummyWorkOrder Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1014" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
      </tr>
      </table>
      <br>
      <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="110" class="FieldTitle"><bean:message key="prodacs.workorder.workorderdate"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objWorkOrderDetails.getWoCreatedDate().toString().substring(0,10) %></td>
            <td class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objWorkOrderDetails.getWorkOrderNo() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objWorkOrderDetails.getCustName() %></td>
            <td width="110" class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objWorkOrderDetails.getCustTypName() %></td>
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
		<tr>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.shipment.partyorderdate"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objJobDet.getWoDCDate().toString().substring(0,10) %></td>
		<tr>
		</table>
		<br>
    	  <table width="100%" cellspacing="0" cellpadding="3">
          <tr> 
            <td width="175" class="TableItems3"><bean:message key="prodacs.job.operationgroupname"/></td>
            <td width="110" class="TableItems3"><bean:message key="prodacs.job.operationsno"/></td>
            <td class="TableItems3"><bean:message key="prodacs.job.operationname"/></td>
            <td width="120" class="TableItems3"><bean:message key="prodacs.job.standardhrs"/></td>
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
          </tr>
<%
			}
%>       
      </table>
      <br>
<%
		}
%>          
        <br> <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr> 
            <td><html:button property="retWOList" styleClass="Button" onclick="javascript:listItem()" value = "DummyWorkOrder List" /></td>
          </tr>
        </table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>