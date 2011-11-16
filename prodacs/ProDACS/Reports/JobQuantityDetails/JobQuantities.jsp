<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="56"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Job Quantity Detail Report Starts.....");
%>		

<html>
<head>
	<title>Job Quantities</title>
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
		if ('<%= request.getParameter("mesg")%>' == "success")
		{
			mailSuccess.style.display = 'block';
		}
		else if ('<%= request.getParameter("mesg")%>' == "failure")
		{
			mailFailed.style.display = 'block';
		}
		
		/* If the field elements entered, then, */
		var temp = document.forms[0];
		
		if ('<%= request.getParameter("startDate")%>' == 'null')
			temp.startDate.value = "";
		else
			temp.startDate.value = '<%= request.getParameter("startDate")%>';

		if ('<%= request.getParameter("endDate")%>' == 'null')
			temp.endDate.value = "";
		else
			temp.endDate.value = '<%= request.getParameter("endDate")%>';
		
		if ('<%= request.getParameter("jobName")%>' == 'null')
			temp.jobName.value = "";
		else
			temp.jobName.value = '<%= request.getParameter("jobName")%>';

		if ('<%= request.getParameter("dwgNo")%>' == 'null')
			temp.dwgNo.value = "";
		else
			temp.dwgNo.value = '<%= request.getParameter("dwgNo")%>';
		
		if ('<%= request.getParameter("jbSelect")%>' == 'null')
			temp.jbSelect.value = "0";
		else
			temp.jbSelect.value = '<%= request.getParameter("jbSelect")%>';

		if ('<%= request.getParameter("dwgSelect")%>' == 'null')
			temp.dwgSelect.value = "0";
		else
			temp.dwgSelect.value = '<%= request.getParameter("dwgSelect")%>';

	}

	function generateReports()
	{
		var temp = document.forms[0];
		temp.jbName.value = temp.jobName.value;
		temp.drwgNo.value = temp.dwgNo.value;
		temp.action = '<bean:message key="context"/>/Reports/JobQuantityDetails/JobQuantitiesResult.jsp';
		temp.submit();
	}
</script>

<body onload="init()">
<form name="frmJobQuantities" >
<input type="hidden" name = "jbName"/>
<input type="hidden" name = "drwgNo"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Job Quantity Details</td>
	</tr>
	</table>

	<table width="100%" cellpadding="0" cellspacing="0" id="mailSuccess" style="display:none">
	<tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
	<tr>
         	<td class="FieldTitle"> Mail Sent Successfully!</td>
	</tr>
	</table>
	<table width="100%" cellpadding="0" cellspacing="0" id="mailFailed" style="display:none">
	<tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
	<tr>
         	<td class="FieldTitle"> Mail Sending Failed!</td>
	</tr>
	</table>

	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><input name="startDate" type="text" class="TextBox" readonly size="11"> 
		<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",frmJobQuantities.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
		
		<td class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><input name="endDate" type="text" class="TextBox" readonly size="11"> 
		<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",frmJobQuantities.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.job.jobname"/></td>
		<td class="FieldTitle">:</td>
		<td>
			<input type="text" name="jobName" class="TextBox" size="25">
			<select name="jbSelect" class="combo">
				<option value="0">Starts With</option>
				<option value="1">Ends With</option>
				<option value="2">Exactly</option>
				<option value="3">AnyWhere</option>
			</select>
		</td>
		
		<td class="FieldTitle"><bean:message key="prodacs.job.drawingno"/></td>
		<td class="FieldTitle">:</td>
		<td>
			<input type="text" name="dwgNo" class="TextBox" size="25">
			<select name="dwgSelect" class="combo">
				<option value="0">Starts With</option>
				<option value="1">Ends With</option>
				<option value="2">Exactly</option>
				<option value="3">AnyWhere</option>
			</select>

		</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="GenRprt" type="button" class="Button" value="Generate Reports" onclick="generateReports();">
			</td>
		</tr>
	</table>
</td>
</tr>
</table>
</form>
</body>
</html>
