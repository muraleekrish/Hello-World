<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.reports.MachineUtilForm"/>
<jsp:setProperty name="frm" property="*"/>
<useradmin:userrights resource="36"/>
<html:html>
<head>
	<title>Productions Of Machine</title>
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
		//temp.submit();
		var startDate = temp.startDate.value;
		var endDate = temp.endDate.value;
		if (startDate != "" && endDate != "")
		{
			var url = '/ProDACS/servlet/GraphServlet';
			var value = 3;
			var param = 'startDate='+startDate+'&endDate='+endDate+'&value='+value;
		}
			
		document.getElementById('barChart').children[0].src = url+"?"+param;
		chartTbl.style.display ='block';
	}

	function closeReport()
	{
		var temp = document.forms[0];
		chartTbl.style.display = 'none';
		temp.startDate.value = "";
		temp.endDate.value = "";
	}

	function showHide()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "showImage")
		{
			chartTbl.style.display = 'block';
		}
		else
		{
			chartTbl.style.display = 'none';
		}
	}
	
</script>

<body onload="init();showHide();">
<html:form action="/frmMachineUtil">
<html:hidden property="formAction"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
         	<td>Machine Utilization - Chart</td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.fromdate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="100" class="FieldTitle"><html:text property="startDate" styleClass="TextBox" readonly="true" size="11"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("startDate",MachineUtilGraph.startDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			
			<td width="75" class="FieldTitle"><bean:message key="prodacs.workorder.todate"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="endDate" styleClass="TextBox" readonly="true" size="11"/> 
			<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("endDate",MachineUtilGraph.endDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		<tr>
			<td>
				<input name="Submit" type="button" class="Button" value="Generate Reports" onclick="validate();">
			</td>
		</tr>
	</table>
	<br><br>
	<table width="100%" cellspacing="0" cellpadding="0" id="chartTbl" style="display:none">
	<tr>
		<td class="TopLnk"><a href="javascript:closeReport()">Close</a></td>
	</tr>
	<tr>
		<td align="center">
			<div id="barChart"><img src="" border=0/></div>
		</td>
	</tr>
	</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
