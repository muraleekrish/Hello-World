<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.ReportsGraphDetailsManager"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.reports.IdleBreakDownForm"/>
<jsp:setProperty name="frm" property="*"/>
<useradmin:userrights resource="60"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Idle/Breakdown Chart Starts...");
	ReportsGraphDetailsManager objReportsGraphDetailsManager = new ReportsGraphDetailsManager();
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap machineCode = new HashMap();
	String fileName = "";
	String graphUrl = "";
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionMachineDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionMachineDetailsManagerHome machineHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
		SessionMachineDetailsManager machineObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machineHomeObj.create(),SessionMachineDetailsManager.class);
		
		/* Loading the machine Details */
		machineCode = machineObj.getAllMachines();
		pageContext.setAttribute("machineCode",machineCode);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Error: "+ e.toString());
	}
%>
<html:html>
<head>
	<title>Productions Of Machine</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
	<script src='<bean:message key="context"/>/library/jscripts.js'></script>
	<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
</head>

<script>
	var oPopup;
	function init()
	{
		oPopup = window.createPopup();
	}

	function validate()
	{
		var temp = document.forms[0];
		if(temp.startDate.value == "")
		{
			alert("Start Date Required!");
			return false;
	   	}
		if(temp.endDate.value == "")
		{
			alert("End Date Required!");
			return false;	     
		}
		temp.formAction.value = "showImage";
		var startDate = temp.startDate.value;
		var endDate = temp.endDate.value;
		if ((startDate != "") && (endDate != ""))
		{
			var url = '/ProDACS/servlet/GraphServlet';
			var value = 1;
			var param = 'startDate='+startDate+'&endDate='+endDate+'&value='+value;
			document.getElementById('wfChart').children(0).src = url+"?"+param;

			var url1 = '/ProDACS/servlet/WaterFallChart';
			var param1 = 'startDate='+startDate+'&endDate='+endDate+'&machineCode='+"PM-M-01"+'&value='+value;
			document.getElementById('stackChart').children(0).src = url1+"?"+param1;

			machineTbl.style.display ='block';
			tabTbl.style.display = 'block';
			wfTbl.style.display = 'block';
			stackTbl.style.display = 'block';
		}
	}
	
	function getStackChart()
	{
		var temp = document.forms[0];
		var startDate = temp.startDate.value;
		var endDate = temp.endDate.value;
		var machineCode = temp.machineCode.value;
		
		var url = '/ProDACS/servlet/WaterFallChart';
		var value = 1;
		var param = 'startDate='+startDate+'&endDate='+endDate+'&machineCode='+machineCode+'&value='+value;
		document.getElementById('stackChart').children(0).src = url+"?"+param;

		machineTbl.style.display ='block';
		tabTbl.style.display = 'block';
		wfTbl.style.display = 'block';
		stackTbl.style.display = 'block';
	}

	function closeReport()
	{
		var temp = document.forms[0];
		machineTbl.style.display ='none';
		tabTbl.style.display = 'none';
		wfTbl.style.display = 'none';
		stackTbl.style.display = 'none';

		temp.machineCode.value = "0";
		temp.startDate.value = "";
		temp.endDate.value = "";
	}

	function showHide()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "showImage")
		{
			wfTbl.style.display = 'block';
			stackTbl.style.display = 'block';
		}
		else
		{
			wfTbl.style.display = 'none';
			stackTbl.style.display = 'none';
		}
	}
</script>

<body onload="init(); showHide();">
<html:form action="/frmIdleBreakDownGraph">
<html:hidden property="formAction"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Idle/Breakdown - Chart</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="FieldTitle"><html:text property="startDate" styleClass="TextBox" readonly="true" size="11"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",IdleBreakDownGraph.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="FieldTitle"><html:text property="endDate" styleClass="TextBox" readonly="true" size="11"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",IdleBreakDownGraph.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="Submit" type="button" class="Button" value="Generate Chart" onclick="validate();">
			</td>
		</tr>
	</table>
	<br>
	<table width="50%" cellspacing="0" cellpadding="0" id="machineTbl" style="display:none">
	<tr>	
		<td width="90" class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td width="100" class="FieldTitle">
			<html:select property="machineCode" styleClass="Combo" onchange="getStackChart();">
				<html:options collection="machineCode" property="key" labelProperty="key"/>
			</html:select>
		</td>
	</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="0" id="tabTbl" style="display:none">
	<tr>
		<td class="TopLnk"><a href="javascript:closeReport()">Close</a></td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="stackTbl" style="display:none">
	<tr>
		<td align="center">
			<div id="stackChart"><img src="" border=0/></div>
		</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="wfTbl" style="display:none">
	<tr>
		<td align="center">
			<div id="wfChart"><img src="" border=0/></div>
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
