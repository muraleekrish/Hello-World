<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="39"/>
<html>
<head>
	<title>Produced Quantity Details</title>
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
		if(document.forms[0].startDate.value == "")
		{
			alert("Start Date Required!");
			return false;
	    	}
		if(document.forms[0].endDate.value == "")
		{
			alert("End Date Required!");
			return false;	     
		}
		return true;	   
	}
</script>

<body onload="init()">
<form name="frmQuantityProduced"  onsubmit="return validate()" action='<bean:message key="context"/>/Reports/QuantityProduced/QuantityProducedResult.jsp'>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td><bean:message key="prodacs.reports.quantityproduced.title"/></td>
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
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="150" class="FieldTitle"><input name="startDate" type="text" class="TextBox" readonly size="11"> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",frmQuantityProduced.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><input name="endDate" type="text" class="TextBox" readonly size="11"> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",frmQuantityProduced.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="Submit" type="submit" class="Button" value="Generate Reports">
			</td>
		</tr>
	</table>
</td>
</tr>
</table>
</form>
</body>
</html>
