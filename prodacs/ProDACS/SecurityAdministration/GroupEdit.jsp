<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="1029"/>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8909-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script>
	function listGroup()
	{
		document.forms[0].action='<bean:message key="context"/>/SecurityAdministration/GroupList.jsp';
		document.forms[0].submit();
	}
	
	function addGroup()
	{
		document.forms[0].action='<bean:message key="context"/>/SecurityAdministration/GroupAdd.jsp';
		document.forms[0].submit();
	}
	
	function callCk(ckName,stCkValue,countValue)
	{
		var temp = document.forms[0];
		var stCk = parseInt(stCkValue, 10);
		var count = parseInt(countValue, 10);
	
		if(ckName.checked)
		{
			for(var i=0;i<count;i++)
			{
				temp.selectedResource[stCk+(i*2)].checked = true;
			}
		}
		else
		{
			for(var i=0;i<count;i++)
			{
				temp.selectedResource[stCk+(i*2)].checked = false;
			}
		}	
	}
	
	function mkRights(show)
	{
		if(show.style.display == "none")
		{
			show.style.display = "block";
		}
		else
		{
			show.style.display = "none";
		}
	}
	
	function loadDefault()
	{
		var temp = document.forms[0];	
		var ckResult = true;
		var stValue = 0 ;
	
		// loop for Customer
		stValue = 0;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.customerRead.checked = true;
		}
	
		ckResult = true;
		stValue = 1;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.customerWrite.checked = true;
		}
	
		// loop for Employee
		stValue = 4;
		ckResult = true;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.employeeRead.checked = true;
		}
	
		ckResult = true;
		stValue = 5;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.employeeWrite.checked = true;
		}
	
		// loop for Machine
		stValue = 8;
		ckResult = true;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.machineRead.checked = true;
		}
	
		ckResult = true;
		stValue = 9;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.machineWrite.checked = true;
		}
	
		// loop for Work Calendar
		stValue = 14;
		ckResult = true;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.workCalendarRead.checked = true;
		}
	
		ckResult = true;
		stValue = 15;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.workCalendarWrite.checked = true;
		}

		// loop for rules
		stValue = 20;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.rulesRead.checked = true;
		}
	
		ckResult = true;
		stValue = 21;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.rulesWrite.checked = true;
		}
	
	
		// loop for Work Order
		stValue = 30;
		ckResult = true;
		for(var i=0;i<4;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.workOrderRead.checked = true;
		}
	
		ckResult = true;
		stValue = 31;
		for(var i=0;i<4;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.workOrderWrite.checked = true;
		}
	
		// loop for Production
		stValue = 40;
		ckResult = true;
		for(var i=0;i<6;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.productionRead.checked = true;
		}
	
		ckResult = true;
		stValue = 41;
		for(var i=0;i<6;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.productionWrite.checked = true;
		}

		// loop for Production Final
		stValue = 48;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.fproductionRead.checked = true;
		}
	
		ckResult = true;
		stValue = 49;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.fproductionWrite.checked = true;
		}

		// loop for Production Shipment
		stValue = 54;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.shipmentRead.checked = true;
		}
	
		ckResult = true;
		stValue = 55;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.shipmentWrite.checked = true;
		}
	
		// loop for PayRoll Administration
		stValue = 60;
		ckResult = true;
		for(var i=0;i<5;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.payrollRead.checked = true;
		}
	
		ckResult = true;
		stValue = 61;
		for(var i=0;i<5;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.payrollWrite.checked = true;
		}

		// loop for Security Administration
		stValue = 72;
		ckResult = true;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.securityRead.checked = true;
		}
	
		ckResult = true;
		stValue = 73;
		for(var i=0;i<2;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.securityWrite.checked = true;
		}

		// loop for Reports
		stValue = 80;
		ckResult = true;
		for(var i=0;i<13;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.reportsRead.checked = true;
		}
	
		ckResult = true;
		stValue = 81;
		for(var i=0;i<13;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.reportsWrite.checked = true;
		}

		// loop for Graphs
		stValue = 108;
		ckResult = true;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.graphsRead.checked = true;
		}
	
		ckResult = true;
		stValue = 109;
		for(var i=0;i<3;i++)
		{
			if(!temp.selectedResource[stValue + (i*2)].checked)
			{
				ckResult = false;
			}
		}
		if(ckResult)
		{
			temp.graphsWrite.checked = true;
		}
	}
	
	function modifyGroup()
	{
		var temp = document.forms[0];
		temp.formAction.value = "update";
		temp.submit();
	}
	
	function loadToHidden()
	{
		var temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}
</script>
</head>

<body onLoad="init(); loadToHidden(); loadDefault();">
<html:form action="frmGroupEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>


<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.group.groupedit"/></td>
	</tr>
	</table><br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Group Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1029" text="[ Add ]" classId="TopLnk" onClick="javaScript:addGroup();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Group Info'; return true"  onMouseOut="window.status=''; return true" resourceId="29" text="[ List ]" classId="TopLnk" onClick="javaScript:listGroup();"/>
	</tr>
	</table>
      <font class="message">
	 	<html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
	   	</html:messages>
	</font>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
<br>	

	<table width="100%" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.groupname"/><span class="mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="groupName" styleClass="TextBox" size="50" readonly="true"/></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.group.groupdesc"/></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:textarea property="groupDesc" styleClass="TextBox" cols="50" rows="5" maxlength="250">
		</html:textarea></td>
	</tr>
	</table><br>
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="FieldTitle">Resource Rights<span class="mandatory">*</span></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0">
	<tr class="SubTitle"> 
		<td>Resources</td>
		<td align="center"> Read </td>
		<td align="center"> Read &amp; Write</td>
	</tr>
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('customer'))">Customer</a></strong></td>
		<td align="center" width="75" class="TableItems"><input type="checkbox" name="customerRead" value="checkbox" onclick='callCk(this,"0","2")'></td>
		<td align="center" width="90" class="TableItems"><input type="checkbox" name="customerWrite" value="checkbox" onclick='callCk(this,"1","2")'></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="customer" style="display:none">
	<tr> 
		<td class="TableItems">Customer Type</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='1' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1001' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Customer</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='2' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1002' /></td>
	</tr>	
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('employee'))">Employee</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="employeeRead" value="checkbox" onclick='callCk(this,"4","2")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="employeeWrite" value="checkbox" onclick='callCk(this,"5","2")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="employee" style="display:none">
	<tr> 
		<td class="TableItems">Employee Type</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='3' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1003' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Employee</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='4' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1004' /></td>
	</tr>	
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('machine'))">Machine</a></strong></td>
		<td align="center" width="75" class="TableItems"><input type="checkbox" name="machineRead" value="checkbox" onclick='callCk(this,"8","3")'>
		</td>
		<td align="center" width="90" class="TableItems"><input type="checkbox" name="machineWrite" value="checkbox" onclick='callCk(this,"9","3")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="machine" style="display:none">
	<tr> 
		<td class="TableItems">Machine Type</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='5' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1005' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Machine</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='6' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1006' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Operation Group</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='7' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1007' /></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('workCalendar'))">Work Calendar</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="workCalendarRead" value="checkbox" onclick='callCk(this,"14","3")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="workCalendarWrite" value="checkbox" onclick='callCk(this,"15","3")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="workCalendar" style="display:none">
	<tr> 
		<td class="TableItems">Shift Definition</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='8' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1008' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Base Calendar</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='9' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1009' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Availability Calendar</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='10' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1010' /></td>
	</tr>	
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('rules'))">Rules</a></strong></td>
		<td align="center" width="75" class="TableItems"><input type="checkbox" name="rulesRead" value="checkbox" onclick='callCk(this,"20","3")'>
		</td>
		<td align="center" width="90" class="TableItems"><input type="checkbox" name="rulesWrite" value="checkbox" onclick='callCk(this,"21","3")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="rules" style="display:none">
	<tr>
		<td class="TableItems2">Team</td>
		<td align="center" width="75" class="TableItems2"><html:multibox property="selectedResource" value='43' /></td>
		<td align="center" width="90" class="TableItems2"><html:multibox property="selectedResource" value='1043' /></td>
	</tr>
	<tr>
		<td class="TableItems">Posting Rule</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='42' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1042' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Payroll Cycle</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='22' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1022' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="job">
	<tr>
		<td class="TableItems2"><strong>Job</strong></td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='11' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1011' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="rework">
	<tr>
		<td class="TableItems"><strong>Rework</strong></td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='12' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1012' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('workOrder'))">Work Order</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="workOrderRead" value="checkbox" onclick='callCk(this,"30","4")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="workOrderWrite" value="checkbox" onclick='callCk(this,"31","4")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="workOrder" style="display:none">
	<tr> 
		<td class="TableItems">WorkOrder</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='13' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1013' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Dummy WorkOrder</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='14' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1014' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">WorkOrder/JobClosing</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='15' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1015' /></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Pre-Close Log</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='16' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1016' /></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="woJobStatus">
	<tr>
		<td class="TableItems"><strong>W.O Job Status</strong></td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='55' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1055' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('production'))">Production</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="productionRead" value="checkbox" onclick='callCk(this,"40","6")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="productionWrite" value="checkbox" onclick='callCk(this,"41","6")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="production" style="display:none">
	<tr> 
		<td class="TableItems2">Production - Direct</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='17' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1017' /></td>
	</tr>
	<tr> 
		<td class="TableItems">Non-Production</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='18' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1018' /></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Radial Production</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='19' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1019' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Production - InDirect</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='20' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1020' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Production Accounting</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='57' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1057' /></td>
	</tr>	
	<tr>
		<td class="TableItems2">Post Production</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='21' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1021' /></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('productionFinal'))">Production - Final</a></strong></td>
		<td align="center" width="75" class="TableItems"><input type="checkbox" name="fproductionRead" value="checkbox" onclick='callCk(this,"52","3")'>
		</td>
		<td align="center" width="90" class="TableItems"><input type="checkbox" name="fproductionWrite" value="checkbox" onclick='callCk(this,"53","3")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="productionFinal" style="display:none">
	<tr> 
		<td class="TableItems">Production - Final</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='49' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1049' /></td>
	</tr>	
	<tr>
		<td class="TableItems2">Despatch Clearance</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='51' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1051' /></td>
	</tr>
	<tr>
		<td class="TableItems">Final Posting</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='53' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1053' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('shipment'))">Shipment</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="shipmentRead" value="checkbox" onclick='callCk(this,"58","2")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="shipmentWrite" value="checkbox" onclick='callCk(this,"59","2")'>
		</td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="shipment" style="display:none">
	<tr>
		<td class="TableItems">Shipment Reference</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='52' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1052' /></td>
	</tr>
	<tr>
		<td class="TableItems2">Shipment Posting</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='54' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1054' /></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0" id="jobStatus">
	<tr>
		<td class="TableItems"><strong>Job Status</strong></td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='50' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1050' /></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('payroll'))">Payroll Administration</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="payrollRead" value="checkbox" disabled="true" onclick='callCk(this,"64","5")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="payrollWrite" value="checkbox" onclick='callCk(this,"65","5")'>
		</td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="payroll" style="display:none">
	<tr> 
		<td class="TableItems">Payroll Id</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='41'/></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1041' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Payroll Interface</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='23' disabled="true" /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1023' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Payroll</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='24' disabled="true" /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1024' /></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Payroll Adjustment</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='25' disabled="true" /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1025' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Close Payroll</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='26' disabled="true" /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1026' /></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="reworkLog">
	<tr>
		<td class="TableItems"><strong>Rework Log</strong></td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='27' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1027' /></td>
	</tr>
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('security'))">Security Administration</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="securityRead" value="checkbox" onclick='callCk(this,"76","2")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="securityWrite" value="checkbox" onclick='callCk(this,"77","2")'>
		</td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="security" style="display:none">
	<tr> 
		<td class="TableItems">User</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='28' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1028' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Groups</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='29' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1029' /></td>
	</tr>	
	</table>

	<table width="400" cellpadding="0" cellspacing="0" id="myAccount">
	<tr>
		<td class="TableItems"><strong>My Account</strong></td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='48' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1048' /></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('reports'))">Reports</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="reportsRead" value="checkbox" disabled="true" onclick='callCk(this,"82","13")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="reportsWrite" value="checkbox" onclick='callCk(this,"83","13")'>
		</td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="reports" style="display:none">
	<tr> 
		<td class="TableItems">Salary Report</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='30' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1030' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Monthly Report</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='31' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1031' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Monthly Production</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='32' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1032' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Half Yearly Production</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='33' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1033' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Employee Performance</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='34' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1034' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Productions of Machine</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='35' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1035' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Machine Utilization</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='36' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1036' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Idle & BreakDown</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='37' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1037' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Top Sheet</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='38' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1038' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">WO - Jb Performance</td>
		<td width="75" align="center" class="TableItems2"><html:multibox property="selectedResource" value='45' /></td>
		<td width="90" align="center" class="TableItems2"><html:multibox property="selectedResource" value='1045' /></td>
	</tr>
	<tr> 
		<td class="TableItems">Quantity Produced</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='39' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1039' /></td>
	</tr>	
	<tr> 
		<td class="TableItems2">WorkOrder Reference</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='40' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1040' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Job Quantities</td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='56' /></td>
		<td align="center" class="TableItems"><html:multibox property="selectedResource" value='1056' /></td>
	</tr>	
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('graphs'))">Graphs</a></strong></td>
		<td align="center" width="75" class="TableItems2"><input type="checkbox" name="graphsRead" value="checkbox" onclick='callCk(this,"108","3")'>
		</td>
		<td align="center" width="90" class="TableItems2"><input type="checkbox" name="graphsWrite" value="checkbox" onclick='callCk(this,"109","3")'>
		</td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="graphs" style="display:none">
	<tr> 
		<td class="TableItems">Machine Utilization Chart</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='59' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1059' /></td>
	</tr>
	<tr> 
		<td class="TableItems2">Idle/BreakDown Chart</td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='60' /></td>
		<td align="center" class="TableItems2"><html:multibox property="selectedResource" value='1060' /></td>
	</tr>	
	<tr> 
		<td class="TableItems">Employee Performance Chart</td>
		<td width="75" align="center" class="TableItems"><html:multibox property="selectedResource" value='58' /></td>
		<td width="90" align="center" class="TableItems"><html:multibox property="selectedResource" value='1058' /></td>
	</tr>
	</table>
	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="edituser" styleClass="Button" value="Update Group" onclick="modifyGroup();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>
