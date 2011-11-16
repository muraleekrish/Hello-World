<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="javax.rmi.PortableRemoteObject"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="47"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Monthly Payroll Report Starts.....");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap payrollIds = new HashMap();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionReportsDetailsManager");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionReportsDetailsManagerHome objReportsHome = (SessionReportsDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
		SessionReportsDetailsManager objreports = (SessionReportsDetailsManager)PortableRemoteObject.narrow(objReportsHome.create(),SessionReportsDetailsManager.class);
		payrollIds = objreports.getClosedPyrlCycleStatForPayroll();
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error :"+e.toString());
			e.printStackTrace();
		}
	}
%>
<html>
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
		if ('<%= request.getParameter("mesg")%>' == "success")
		{
			mailSuccess.style.display = 'block';
		}
		else if ('<%= request.getParameter("mesg")%>' == "failure")
		{
			mailFailed.style.display = 'block';
		}
	}

	function generateReports()
	{
		temp = document.forms[0];
		if(temp.payrollId.value == '0')
		{
			alert("Select a Payroll Id to generate Reports!");
			return false;
	   	}
		temp.payrollIds.value = temp.payrollId.options[temp.payrollId.selectedIndex].text;
		temp.action = '<bean:message key="context"/>/Reports/MonthlyPayroll/MonthlyPayrollResult.jsp';
		temp.submit();
	}

</script>
<body onload="init()">
<form name="frmMonthlyPayroll"><!-- action='<bean:message key="context"/>/Reports/MonthlyPayroll/MonthlyPayrollResult.jsp'-->
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
<input type="hidden" name="payrollIds"/>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Monthly Payroll Report</td>
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
			<td width="80" class="FieldTitle"><bean:message key="prodacs.payroll.payrollid.header"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><select name="payrollId" class="Combo">
	                		<option value="0">-- Choose Payroll ID --</option>
<%
	Set set = payrollIds.entrySet();
	Iterator i = set.iterator();
	while (i.hasNext())
	{
		Map.Entry me = (Map.Entry) i.next();
%>
				<option value='<%= me.getKey() %>'><%= me.getValue()%></option>
<%
	}
%>	                		
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
