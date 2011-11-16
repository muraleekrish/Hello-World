<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="31"/>
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

	function validate()
	{
		var temp = document.forms[0];
		if(temp.month.value == "0")
		{
			alert("Month Required!");
			return false;
	   	}
		if(temp.year.value == "0")
		{
			alert("Year Required!");
			return false;	     
		}
		if(temp.validator.checked)
		{
			temp.validator.value = 1;
		}
		else
		{
			temp.validator.value = 0;
		}
		temp.submit();
	}
</script>

<body onload="init()">
<form name="frmMonthlyReport" action='<bean:message key="context"/>/Reports/MonthlyReport/MonthlyReportResult.jsp'>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Monthly Report</td>
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
			<td width="30" class="FieldTitle"><bean:message key="prodacs.payroll.month"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="FieldTitle">
				<select name="month" class="Combo">
					<option value="0">-- Month --</option>
					<option value="1">  Jan  </option>
					<option value="2">  Feb  </option>
					<option value="3">  Mar  </option>
					<option value="4">  Apr  </option>
					<option value="5">  May  </option>
					<option value="6">  Jun  </option>
					<option value="7">  Jul  </option>
					<option value="8">  Aug  </option>
					<option value="9">  Sep  </option>
					<option value="10">  Oct  </option>
					<option value="11">  Nov  </option>
					<option value="12">  Dec  </option>
              	</select>
			</td>
			
			<td width="30" class="FieldTitle">Year</td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="FieldTitle">
				<select name="year" class="Combo">
					<option value="0">-- Year --</option>
<%
	for (int i = 2000; i < 2050; i++)
	{
%>
					<option value="<%= i %>">  <%= i %>  </option>
<%
	}
%>
				</select>
			</td>
			<td width="50" class="FieldTitle">Validation</td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle">
				<input type="checkbox" name="validator" value="checkbox"/>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="genReports" type="button" class="Button" value="Generate Reports" onclick="validate();">
			</td>
		</tr>
	</table>
</td>
</tr>
</table>
</form>
</body>
</html>
