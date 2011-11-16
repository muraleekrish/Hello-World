<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.facade.SessionReworkLogDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionReworkLogDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reworklog.ReworkLogDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="27"/>
<%
	EJBLocator obj = new EJBLocator(); /* EjbLocator for Session Bean */
	/* Details object to ReworkLogDetails */
	ReworkLogDetails objReworkLogDetails = new ReworkLogDetails();
	int cnt = 1;
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionReworkLogDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionReworkLogDetailsManagerHome rwkLogHomeObj = (SessionReworkLogDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkLogDetailsManagerHome.class);
		SessionReworkLogDetailsManager rwkLogObj = (SessionReworkLogDetailsManager)PortableRemoteObject.narrow(rwkLogHomeObj.create(),SessionReworkLogDetailsManager.class);
		if (BuildConfig.DMODE)
			System.out.println("Id :"+ id);
		objReworkLogDetails = rwkLogObj.getReworkLogDetails(Integer.parseInt(id));
		Vector rwkLogJbQtyDet = objReworkLogDetails.getVecRwkLogJbQtyDetails();
		pageContext.setAttribute("rwkLogJbQtyDet", rwkLogJbQtyDet);
		
		Vector rwkLogEmpDet = objReworkLogDetails.getVecRwkLogEmpDetails();
		pageContext.setAttribute("rwkLogEmpDet", rwkLogEmpDet);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error in ReworkLog View.Jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
function chkCmbJobName(val)
	{
		if(document.forms[0].cmbWrkOrdNum.value > "0")
			{
				if(val > "0")
					{
						tblJobType.style.display = "block";
						tblOprType.style.display = "none";
					}
			}
		else
			{
				alert("Please Select WorkOrder Number");
				document.frmRewrkLogAdd.cmbWrkOrdNum.focus();
				return true;
			}
	}
</script>
<script>
	var isValid = '<%= objReworkLogDetails.isRwkIsvalid() %>';

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/ReworkLog/ReworkLogAdd.jsp';
		document.forms[0].submit();
	}

	function modifyItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/ReworkLog/ReworkLogEdit.jsp';
		document.forms[0].submit();
	}

	function submitItem()
	{
		temp = document.forms[0];
		if ((temp.fromDate.value != "" ) || (temp.toDate.value != "") || (temp.jbName.value != "") || (temp.srchJbName.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmRewrkLogList.do?formAction=afterView&fromDate='+temp.fromDate.value+'&toDate='+temp.toDate.value+'&jbName='+temp.jbName.value+'&srchJbName='+temp.srchJbName.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/ReworkLog/ReworkLogList.jsp';
			temp.submit();
		}
	}

	function chkValid()
	{
		temp = document.forms[0];
		temp.fromDate.value = '<%= session.getAttribute("rwkLogFromDate") %>';
		temp.toDate.value = '<%= session.getAttribute("rwkLogToDate") %>';
		temp.jbName.value = '<%= session.getAttribute("listJobName") %>';
		temp.srchJbName.value = '<%= session.getAttribute("jobSoryBy") %>';
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 'true')
			{
				mkValid.style.display = 'none';
				mkInValid.style.display = 'block';
			}
			else
			{
				mkValid.style.display = 'block';
				mkInValid.style.display = 'none';
			}
		}
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmRewrkLogEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmRewrkLogEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

</script>
</head>

<body onload="chkValid(); loadToHidden();">
<html:form action="frmRewrkLogEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="fromDate"/>
<input type="hidden" name="toDate"/>
<input type="hidden" name="jbName"/>
<input type="hidden" name="srchJbName"/>
<input type="hidden" name="viewAll"/>

<table width="100%" cellspacing="0" cellpadding="10">
<tr>
	<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.reworklog.viewreworklog"/></td>
	</tr>
	</table><br>
		<table width="100" cellspacing="0" cellpadding="0" align="right">
		<tr> 
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New ReworkLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1027" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
	 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make ReworkLog Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1027" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
	 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make ReworkLog Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1027" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
		</tr>
		</table><br>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="70" class="ViewData"><%= objReworkLogDetails.getWoNo() %></td>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="80" class="ViewData"><%= objReworkLogDetails.getJbName() %></td>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.rework.reworkdate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="ViewData"><%= objReworkLogDetails.getRwkDate().toString().substring(0,10) %></td>
		</tr>
		<tr>
			<td class="FieldTitle"><bean:message key="prodacs.rework.reworkcategory"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objReworkLogDetails.getRwkCategory() %></td>
			<td class="FieldTitle"><bean:message key="prodacs.rework.reworkreason"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objReworkLogDetails.getRwkReason() %></td>
			<td class="FieldTitle"><bean:message key="prodacs.reworklog.authorizedby"/></td>
			<td class="FieldTitle">:</td>
			<td class="ViewData"><%= objReworkLogDetails.getAuthorizedBy() %></td>
		</tr>
		<tr> 
		</tr>
		</table>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblJobType">
		<tr>
		<td>
		<table width="100%" cellpadding="3" cellspacing="0">
		<tr> 
			<td width="50" class="Header"><bean:message key="prodacs.common.sno"/></td>
			<td width="300" class="Header"><bean:message key="prodacs.employee.employeetype"/></td>
			<td class="Header"><bean:message key="prodacs.employee.employeename"/></td>
		</tr>
		<logic:iterate id="bt1" name="rwkLogEmpDet" indexId="count">
		<tr> 
		<%
		if ((count.intValue()%2) == 0)
		{
		%>
			<td class="TableItems"><%= cnt++ %>&nbsp;</td>
			<td class="TableItems"><bean:write name="bt1" property="empTypName"/>&nbsp;</td>
			<td class="TableItems"><bean:write name="bt1" property="empName"/>&nbsp;</td>
		<%
		}
		else
		{
		%>
			<td class="TableItems2"><%= cnt++ %>&nbsp;</td>
			<td class="TableItems2"><bean:write name="bt1" property="empTypName"/>&nbsp;</td>
			<td class="TableItems2"><bean:write name="bt1" property="empName"/>&nbsp;</td>
		<%
		}
		%>
		</tr>
		</logic:iterate>
		</table>
		</td>
		</tr>
		</table><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblOprType">
		<tr>
			<td>
		<table width="100%" cellpadding="3" cellspacing="0" id="oprType">
		<logic:iterate id="bt1" name="rwkLogJbQtyDet">
		<tr>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.workorder.jobqtysno"/>&nbsp;:&nbsp;</td>
			<td width="300" class="ViewData"><bean:write name="bt1" property="jbQtySno"/></td>
			<td></td>
		</tr>
		<tr> 
			<td class="Header"><bean:message key="prodacs.job.operationsno"/></td>
			<td class="Header"><bean:message key="prodacs.job.operationname"/></td>
			<td class="Header"><bean:message key="prodacs.reworklog.productionenteredcompleted"/></td>
		</tr>
		<bean:define id="bt2" name="bt1" property="vecRwkLogJbOpnDetails"/>
			<logic:iterate id="bt3" name="bt2" indexId="count">
			<tr> 
			<%
			if ((count.intValue())%2 == 0)
			{
			%>
				<td class="TableItems"><bean:write name="bt3" property="opnSno"/>&nbsp;</td>
				<td class="TableItems"><bean:write name="bt3" property="opnName"/>&nbsp;</td>
				<td class="TableItems">
					<logic:equal name="bt3" property="prodnEntered" value="true">Production Entered</logic:equal>
					<logic:equal name="bt3" property="prodnEntered" value="false">Completed</logic:equal>
				&nbsp;</td>
			<%
			}
			else
			{
			%>
				<td class="TableItems2"><bean:write name="bt3" property="opnSno"/>&nbsp;</td>
				<td class="TableItems2"><bean:write name="bt3" property="opnName"/>&nbsp;</td>
				<td class="TableItems2">
					<logic:equal name="bt3" property="prodnEntered" value="true">Production Entered</logic:equal>
					<logic:equal name="bt3" property="prodnEntered" value="false">Completed</logic:equal>
				&nbsp;</td>
			<%
			}
			%>
			</tr>
			</logic:iterate>
		</logic:iterate>
		</table><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
		<tr>
			<td><html:button property="viewanother" styleClass="Button" value="List" onclick="submitItem();"/></td>
		</tr>
		</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>