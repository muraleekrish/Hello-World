<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<bean:parameter id="id" name="id" value="0"/>

<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="29"/>
<%
	/* Creation of Object to EJBLocator */
	EJBLocator obj = new EJBLocator();
	HashMap hmGrpDetails = null;

	SecAdminGroupDetails secAdminDetailsObj = new SecAdminGroupDetails();
	
	try
	{
		/* Setting JNDI Name and Environment */
		obj.setJndiName("SessionSecurityAdminManager");
		obj.setEnvironment();
		
		/* Creation of Home and Remote Objects */
		SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
		SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
		
		secAdminDetailsObj = secAdminObj.getGroupDetails(Integer.parseInt(id));
		
		Vector vecGrpDetails = secAdminDetailsObj.getVResources();
		hmGrpDetails = secAdminDetailsObj.getHmResources();
		
		pageContext.setAttribute("hmGrpDetails",hmGrpDetails);
		if (BuildConfig.DMODE)
		{
			System.out.println("HM values :"+hmGrpDetails);
			System.out.println("Group View.jsp Ends");
		}
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Group Details View");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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

	var isValid = '<%= secAdminDetailsObj.getGroup_IsValid() %>';
	
	function listGroup()
	{
		temp = document.forms[0];
		if ((temp.gname.value != "" ) || (temp.gNameSelect.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmGroupInfoList.do?formAction=afterView&gname='+temp.gname.value+'&gNameSelect='+temp.gNameSelect.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action='<bean:message key="context"/>/SecurityAdministration/GroupList.jsp';
			temp.submit();
		}
	}

	function addGroup()
	{
		document.forms[0].action='<bean:message key="context"/>/SecurityAdministration/GroupAdd.jsp';
		document.forms[0].submit();
	}

	function chkValid()
	{
		temp = document.forms[0];
		temp.gname.value = '<%= session.getAttribute("groupName") %>';
		temp.gNameSelect.value = '<%= session.getAttribute("groupSelection") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 1)
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
		temp.action = '<bean:message key="context"/>/frmGroupEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmGroupEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
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
	
	function editGroup()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmGroupEdit.do?formAction=modify&id='+<%= secAdminDetailsObj.getGroup_Id() %>;
			document.forms[0].submit();
		}	
	}
	
</script>
</head>

<body onload="chkValid(); loadToHidden();">
<html:form action="frmGroupEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="gname"/>
<input type="hidden" name="gNameSelect"/>
<input type="hidden" name="viewAll"/>

<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.group.groupview"/></td>
	</tr>
	</table><br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Group Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1029" text="[ Add ]" classId="TopLnk" onClick="javaScript:addGroup();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Group Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1029" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editGroup();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Group Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1029" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Group Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1029" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
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
		<td width="150" class="FieldTitle"><bean:message key="prodacs.useradministration.groupname"/></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="ViewData"><%= secAdminDetailsObj.getGroup_Name() %></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.group.groupdesc"/></td>
		<td class="FieldTitle">:</td>
		<td class="ViewData"><%= secAdminDetailsObj.getGroup_Desc() %></td>
	</tr>
	</table><br>
	<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="FieldTitle">Resource Rights</td>
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
		<td align="center" width="75" class="TableItems"><%if ((hmGrpDetails.containsKey("1") && hmGrpDetails.containsKey("2")) || (hmGrpDetails.containsKey("1001") && hmGrpDetails.containsKey("1002"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("1") || hmGrpDetails.containsKey("2")) || (hmGrpDetails.containsKey("1001") || hmGrpDetails.containsKey("1002"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems"><%if (hmGrpDetails.containsKey("1001") && hmGrpDetails.containsKey("1002")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1001") || hmGrpDetails.containsKey("1002")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="SubActive"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="customer" style="display:none">
	<tr> 
		<td class="TableItems2">Customer Type</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1") || hmGrpDetails.containsKey("1001")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1001")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Customer</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("2") || hmGrpDetails.containsKey("1002")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1002")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('employee'))">Employee</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("3") && hmGrpDetails.containsKey("4")) || (hmGrpDetails.containsKey("1003") && hmGrpDetails.containsKey("1004"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("3") || hmGrpDetails.containsKey("4")) || (hmGrpDetails.containsKey("1003") || hmGrpDetails.containsKey("1004"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1003") && hmGrpDetails.containsKey("1004")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1003") || hmGrpDetails.containsKey("1004")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="employee" style="display:none">
	<tr> 
		<td class="TableItems">Employee Type</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("3") || hmGrpDetails.containsKey("1003")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1003")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Employee</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("4") || hmGrpDetails.containsKey("1004")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1004")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('machine'))">Machine</a></strong></td>
		<td align="center" width="75" class="TableItems"><%if ((hmGrpDetails.containsKey("5") && hmGrpDetails.containsKey("6") && hmGrpDetails.containsKey("7")) || (hmGrpDetails.containsKey("1005") && hmGrpDetails.containsKey("1006") && hmGrpDetails.containsKey("1007"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("5") || hmGrpDetails.containsKey("6") || hmGrpDetails.containsKey("7")) || (hmGrpDetails.containsKey("1005") || hmGrpDetails.containsKey("1006") || hmGrpDetails.containsKey("1007"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems"><%if (hmGrpDetails.containsKey("1005") && hmGrpDetails.containsKey("1006") && hmGrpDetails.containsKey("1007")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1005") || hmGrpDetails.containsKey("1006") || hmGrpDetails.containsKey("1007")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="machine" style="display:none">
	<tr> 
		<td class="TableItems2">Machine Type</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("5") || hmGrpDetails.containsKey("1005")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1005")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Machine</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("6") || hmGrpDetails.containsKey("1006")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1006")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Operation Group</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("7") || hmGrpDetails.containsKey("1007")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1007")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('workCalendar'))">Work Calendar</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("8") && hmGrpDetails.containsKey("9") && hmGrpDetails.containsKey("10")) || (hmGrpDetails.containsKey("1008") && hmGrpDetails.containsKey("1009") && hmGrpDetails.containsKey("1010"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("8") || hmGrpDetails.containsKey("9") || hmGrpDetails.containsKey("10")) || (hmGrpDetails.containsKey("1008") || hmGrpDetails.containsKey("1009") || hmGrpDetails.containsKey("1010"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1008") && hmGrpDetails.containsKey("1009") && hmGrpDetails.containsKey("1010")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1008") || hmGrpDetails.containsKey("1009") || hmGrpDetails.containsKey("1010")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="workCalendar" style="display:none">
	<tr> 
		<td class="TableItems2">Shift Definition</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("8") || hmGrpDetails.containsKey("1008")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1008")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Base Calendar</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("9") || hmGrpDetails.containsKey("1009")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1009")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Availability Calendar</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("10") || hmGrpDetails.containsKey("1010")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1010")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('rules'))">Rules</a></strong></td>
		<td align="center" width="75" class="TableItems"><%if ((hmGrpDetails.containsKey("43") && hmGrpDetails.containsKey("22") && hmGrpDetails.containsKey("42")) || (hmGrpDetails.containsKey("1042") && hmGrpDetails.containsKey("1022") && hmGrpDetails.containsKey("1043"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("43") || hmGrpDetails.containsKey("22") || hmGrpDetails.containsKey("42")) || (hmGrpDetails.containsKey("1042") || hmGrpDetails.containsKey("1022") || hmGrpDetails.containsKey("1043"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems"><%if (hmGrpDetails.containsKey("1043") && hmGrpDetails.containsKey("1022") && hmGrpDetails.containsKey("1042")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1043") || hmGrpDetails.containsKey("1022") || hmGrpDetails.containsKey("1042")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="rules" style="display:none">
	<tr>
		<td class="TableItems2">Team</td>
		<td align="center" width="75" class="TableItems2"><%if (hmGrpDetails.containsKey("43") || hmGrpDetails.containsKey("1043")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1043")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr>
		<td class="TableItems">Posting Rule</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("42") || hmGrpDetails.containsKey("1042")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1042")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Payroll Cycle</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("22") || hmGrpDetails.containsKey("1022")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1022")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="job">
	<tr>
		<td class="TableItems2"><strong>Job</strong></td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("11") || hmGrpDetails.containsKey("1011")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1011")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="rework">
	<tr>
		<td class="TableItems"><strong>Rework</strong></td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("12") || hmGrpDetails.containsKey("1012")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1012")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('workOrder'))">Work Order</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("13") && hmGrpDetails.containsKey("14") && hmGrpDetails.containsKey("15") && hmGrpDetails.containsKey("16")) || (hmGrpDetails.containsKey("1013") && hmGrpDetails.containsKey("1014") && hmGrpDetails.containsKey("1015") && hmGrpDetails.containsKey("1016"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("13") || hmGrpDetails.containsKey("14") || hmGrpDetails.containsKey("15") || hmGrpDetails.containsKey("16")) || (hmGrpDetails.containsKey("1013") || hmGrpDetails.containsKey("1014") || hmGrpDetails.containsKey("1015") || hmGrpDetails.containsKey("1016"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1013") && hmGrpDetails.containsKey("1014") && hmGrpDetails.containsKey("1015") && hmGrpDetails.containsKey("1016")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1013") || hmGrpDetails.containsKey("1014") || hmGrpDetails.containsKey("1015") || hmGrpDetails.containsKey("1016")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="workOrder" style="display:none">
	<tr> 
		<td class="TableItems">WorkOrder</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("13") || hmGrpDetails.containsKey("1013")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1013")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Dummy WorkOrder</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("14") || hmGrpDetails.containsKey("1014")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1014")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems">WorkOrder/JobClosing</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("15") || hmGrpDetails.containsKey("1015")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1015")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Pre-Close Log</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("16") || hmGrpDetails.containsKey("1016")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1016")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="woJobStatus">
	<tr>
		<td class="TableItems"><strong>W.O Job Status</strong></td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("55") || hmGrpDetails.containsKey("1055")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1055")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('production'))">Production</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("17") && hmGrpDetails.containsKey("18") && hmGrpDetails.containsKey("19") && hmGrpDetails.containsKey("20") && hmGrpDetails.containsKey("21")) || (hmGrpDetails.containsKey("1017") && hmGrpDetails.containsKey("1018") && hmGrpDetails.containsKey("1019") && hmGrpDetails.containsKey("1020") && hmGrpDetails.containsKey("1021"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("17") || hmGrpDetails.containsKey("18") || hmGrpDetails.containsKey("19") || hmGrpDetails.containsKey("20") || hmGrpDetails.containsKey("21")) || (hmGrpDetails.containsKey("1017") || hmGrpDetails.containsKey("1018") || hmGrpDetails.containsKey("1019") || hmGrpDetails.containsKey("1020") || hmGrpDetails.containsKey("1021"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1017") && hmGrpDetails.containsKey("1018") && hmGrpDetails.containsKey("1019") && hmGrpDetails.containsKey("1020") && hmGrpDetails.containsKey("1021")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1017") || hmGrpDetails.containsKey("1018") || hmGrpDetails.containsKey("1019") || hmGrpDetails.containsKey("1020") || hmGrpDetails.containsKey("1021")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="production" style="display:none">
	<tr> 
		<td class="TableItems2">Production - Direct</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("17") || hmGrpDetails.containsKey("1017")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1017")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Non-Production</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("18") || hmGrpDetails.containsKey("1018")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1018")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Radial Production</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("19") || hmGrpDetails.containsKey("1019")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1019")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems">Production - InDirect</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("20") || hmGrpDetails.containsKey("1020")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1020")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems">Production Accounting</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("57") || hmGrpDetails.containsKey("1057")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1057")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Post Production</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("21") || hmGrpDetails.containsKey("1021")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1021")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems"><strong><a href="javascript:mkRights(document.getElementById('productionFinal'))">Production - Final</a></strong></td>
		<td align="center" width="75" class="TableItems"><%if ((hmGrpDetails.containsKey("49") && hmGrpDetails.containsKey("51") && hmGrpDetails.containsKey("53")) || (hmGrpDetails.containsKey("1049") && hmGrpDetails.containsKey("1051") && hmGrpDetails.containsKey("1053"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("49") && hmGrpDetails.containsKey("51") && hmGrpDetails.containsKey("53")) || (hmGrpDetails.containsKey("1049") && hmGrpDetails.containsKey("1051") && hmGrpDetails.containsKey("1053"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems"><%if (hmGrpDetails.containsKey("1049") && hmGrpDetails.containsKey("1051") && hmGrpDetails.containsKey("1053")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1049") || hmGrpDetails.containsKey("1051") || hmGrpDetails.containsKey("1053")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="productionFinal" style="display:none">
	<tr> 
		<td class="TableItems2">Production - Final</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("49") || hmGrpDetails.containsKey("1049")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1049")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr>
		<td class="TableItems">Despatch Clearance</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("51") || hmGrpDetails.containsKey("1051")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1051")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr>
		<td class="TableItems2">Final Posting</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("53") || hmGrpDetails.containsKey("1053")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1053")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('shipment'))">Shipment</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("52") && hmGrpDetails.containsKey("54")) || (hmGrpDetails.containsKey("1052") && hmGrpDetails.containsKey("1054"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("52") || hmGrpDetails.containsKey("54")) || (hmGrpDetails.containsKey("1052") || hmGrpDetails.containsKey("1054"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1052") && hmGrpDetails.containsKey("1054")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1052") || hmGrpDetails.containsKey("1054")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="shipment" style="display:none">
	<tr> 
		<td class="TableItems">Shipment Reference</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("52") || hmGrpDetails.containsKey("1052")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1052")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Shipment Posting</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("54") || hmGrpDetails.containsKey("1054")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1054")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="jobStatus">
	<tr> 
		<td class="TableItems"><strong>Job Status</strong></td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("50") || hmGrpDetails.containsKey("1050")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1050")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('payroll'))">Payroll Administration</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("22") && hmGrpDetails.containsKey("23") && hmGrpDetails.containsKey("24") && hmGrpDetails.containsKey("25") && hmGrpDetails.containsKey("26") && hmGrpDetails.containsKey("41")) || (hmGrpDetails.containsKey("1022") && hmGrpDetails.containsKey("1023") && hmGrpDetails.containsKey("1024") && hmGrpDetails.containsKey("1025") && hmGrpDetails.containsKey("1026") && hmGrpDetails.containsKey("1041"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("22") || hmGrpDetails.containsKey("23") || hmGrpDetails.containsKey("24") || hmGrpDetails.containsKey("25") || hmGrpDetails.containsKey("26") || hmGrpDetails.containsKey("41")) || (hmGrpDetails.containsKey("1022") || hmGrpDetails.containsKey("1023") || hmGrpDetails.containsKey("1024") || hmGrpDetails.containsKey("1025") || hmGrpDetails.containsKey("1026") || hmGrpDetails.containsKey("1041"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1022") && hmGrpDetails.containsKey("1023") && hmGrpDetails.containsKey("1024") && hmGrpDetails.containsKey("1025") && hmGrpDetails.containsKey("1026") && hmGrpDetails.containsKey("1041")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1022") || hmGrpDetails.containsKey("1023") || hmGrpDetails.containsKey("1024") || hmGrpDetails.containsKey("1025") || hmGrpDetails.containsKey("1026") || hmGrpDetails.containsKey("1041")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellspacing="0" cellpadding="0" id="payroll" style="display:none">
	<tr> 
		<td class="TableItems">Payroll Id</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("41") || hmGrpDetails.containsKey("1041")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1041")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Payroll Interface</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("23") || hmGrpDetails.containsKey("1023")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1023")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems">Payroll</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("24") || hmGrpDetails.containsKey("1024")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1024")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Payroll Adjustment</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("25") || hmGrpDetails.containsKey("1025")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1025")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems">Close Payroll</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("26") || hmGrpDetails.containsKey("1026")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1026")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>
	
	<table width="400" cellpadding="0" cellspacing="0" id="reworkLog">
	<tr>
		<td class="TableItems"><strong>Rework Log</strong></td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("27") || hmGrpDetails.containsKey("1027")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1027")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>
	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('security'))">Security Administration</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("28") && hmGrpDetails.containsKey("29")) || (hmGrpDetails.containsKey("1028") && hmGrpDetails.containsKey("1029"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("28") || hmGrpDetails.containsKey("29")) || (hmGrpDetails.containsKey("1028") || hmGrpDetails.containsKey("1029"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1028") && hmGrpDetails.containsKey("1029")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1028") || hmGrpDetails.containsKey("1029")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="security" style="display:none">
	<tr> 
		<td class="TableItems">User</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("28") || hmGrpDetails.containsKey("1028")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1028")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems2">Groups</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("29") || hmGrpDetails.containsKey("1029")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1029")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="myAccount">
	<tr> 
		<td class="TableItems"><strong>My Account</strong></td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("48") || hmGrpDetails.containsKey("1048")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1048")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('reports'))">Reports</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("30") && hmGrpDetails.containsKey("31") && hmGrpDetails.containsKey("32") && hmGrpDetails.containsKey("33") && hmGrpDetails.containsKey("34") && hmGrpDetails.containsKey("35") && hmGrpDetails.containsKey("36") && hmGrpDetails.containsKey("37") && hmGrpDetails.containsKey("38") && hmGrpDetails.containsKey("39") && hmGrpDetails.containsKey("40")) ||(hmGrpDetails.containsKey("1030") && hmGrpDetails.containsKey("1031") && hmGrpDetails.containsKey("1032") && hmGrpDetails.containsKey("1033") && hmGrpDetails.containsKey("1034") && hmGrpDetails.containsKey("1035") && hmGrpDetails.containsKey("1036") && hmGrpDetails.containsKey("1037") && hmGrpDetails.containsKey("1038") && hmGrpDetails.containsKey("1039") && hmGrpDetails.containsKey("1040"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("30") || hmGrpDetails.containsKey("31") || hmGrpDetails.containsKey("32") || hmGrpDetails.containsKey("33") || hmGrpDetails.containsKey("34") || hmGrpDetails.containsKey("35") || hmGrpDetails.containsKey("36") || hmGrpDetails.containsKey("37") || hmGrpDetails.containsKey("38") || hmGrpDetails.containsKey("39") || hmGrpDetails.containsKey("40")) ||(hmGrpDetails.containsKey("1030") || hmGrpDetails.containsKey("1031") || hmGrpDetails.containsKey("1032") || hmGrpDetails.containsKey("1033") || hmGrpDetails.containsKey("1034") || hmGrpDetails.containsKey("1035") || hmGrpDetails.containsKey("1036") || hmGrpDetails.containsKey("1037") || hmGrpDetails.containsKey("1038") || hmGrpDetails.containsKey("1039") || hmGrpDetails.containsKey("1040"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1030") && hmGrpDetails.containsKey("1031") && hmGrpDetails.containsKey("1032") && hmGrpDetails.containsKey("1033") && hmGrpDetails.containsKey("1034") && hmGrpDetails.containsKey("1035") && hmGrpDetails.containsKey("1036") && hmGrpDetails.containsKey("1037") && hmGrpDetails.containsKey("1038") && hmGrpDetails.containsKey("1039") && hmGrpDetails.containsKey("1040")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1030") || hmGrpDetails.containsKey("1031") || hmGrpDetails.containsKey("1032") || hmGrpDetails.containsKey("1033") || hmGrpDetails.containsKey("1034") || hmGrpDetails.containsKey("1035") || hmGrpDetails.containsKey("1036") || hmGrpDetails.containsKey("1037") || hmGrpDetails.containsKey("1038") || hmGrpDetails.containsKey("1039") || hmGrpDetails.containsKey("1040")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="reports" style="display:none">
	<tr> 
		<td class="TableItems2">Salary Report</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("30") || hmGrpDetails.containsKey("1030")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1030")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Monthly Report</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("31") || hmGrpDetails.containsKey("1031")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1031")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Monthly Production</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("32") || hmGrpDetails.containsKey("1032")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1032")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Half Yearly Production</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("33") || hmGrpDetails.containsKey("1033")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1033")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Employee Performance</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("34") || hmGrpDetails.containsKey("1034")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1034")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Productions of Machine</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("35") || hmGrpDetails.containsKey("1035")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1035")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Machine Utilization</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("36") || hmGrpDetails.containsKey("1036")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1036")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Idle & BreakDown</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("37") || hmGrpDetails.containsKey("1037")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1037")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Top Sheet</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("38") || hmGrpDetails.containsKey("1038")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1038")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">WO - Jb Performance</td>
		<td width="75" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("45") || hmGrpDetails.containsKey("1045")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1045")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Quantity Produced</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("39") || hmGrpDetails.containsKey("1039")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1039")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">WorkOrder Reference</td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("40") || hmGrpDetails.containsKey("1040")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1040")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Job Quantities</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("56") || hmGrpDetails.containsKey("1056")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1056")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellpadding="0" cellspacing="0">
	<tr>
		<td class="TableItems2"><strong><a href="javascript:mkRights(document.getElementById('graphs'))">Graphs</a></strong></td>
		<td align="center" width="75" class="TableItems2"><%if ((hmGrpDetails.containsKey("58") && hmGrpDetails.containsKey("59") && hmGrpDetails.containsKey("60")) ||(hmGrpDetails.containsKey("1058") && hmGrpDetails.containsKey("1059") && hmGrpDetails.containsKey("1060"))){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if ((hmGrpDetails.containsKey("58") || hmGrpDetails.containsKey("59") || hmGrpDetails.containsKey("60")) ||(hmGrpDetails.containsKey("1058") || hmGrpDetails.containsKey("1059") || hmGrpDetails.containsKey("1060"))){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" width="90" class="TableItems2"><%if (hmGrpDetails.containsKey("1058") && hmGrpDetails.containsKey("1059") && hmGrpDetails.containsKey("1060")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else if (hmGrpDetails.containsKey("1058") || hmGrpDetails.containsKey("1059") || hmGrpDetails.containsKey("1060")){%><img src='<bean:message key="context"/>/images/subactive.gif' alt="Partially Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<table width="400" cellspacing="0" cellpadding="0" id="graphs" style="display:none">
	<tr> 
		<td class="TableItems2">Machine Utilization Chart</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("59") || hmGrpDetails.containsKey("1059")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1059")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	<tr> 
		<td class="TableItems">Idle/BreakDown Chart</td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("60") || hmGrpDetails.containsKey("1060")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td align="center" class="TableItems"><%if (hmGrpDetails.containsKey("1060")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>	
	<tr> 
		<td class="TableItems2">Employee Performance Chart</td>
		<td width="75" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("58") || hmGrpDetails.containsKey("1058")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
		<td width="90" align="center" class="TableItems2"><%if (hmGrpDetails.containsKey("1058")){%><img src='<bean:message key="context"/>/images/active.gif' alt="Active"><%}else{%><img src='<bean:message key="context"/>/images/inactive.gif' alt="InActive"><%}%></td>
	</tr>
	</table>

	<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="list" styleClass="Button" value="List" onclick="listGroup();"/>
	</tr>
	</table>
	</td>
</tr>
</table>
</html:form>
</body>
</html:html>
