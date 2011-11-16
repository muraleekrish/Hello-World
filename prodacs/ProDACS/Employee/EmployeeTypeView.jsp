<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:parameter id="id" name="id" value="0"/>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails" %>
<useradmin:userrights resource="3"/>
<%
	EJBLocator obj = new EJBLocator();
	EmployeeTypeDetails objEmpTypDet = null;
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionEmpDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
		
		objEmpTypDet = empObj.getEmployeeTypeDetails(Integer.parseInt(id));
	}catch (Exception e)
	{
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.employee.employeetypeview.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objEmpTypDet.getEmp_Typ_IsValid()%>;
	
	function chkMkValid()
	{
		temp = document.forms[0];
		temp.empTypName.value = '<%= session.getAttribute("searchText") %>';
		temp.empType.value = '<%= session.getAttribute("employeeType") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';

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
	
	function listEmployeeType()
	{
		temp = document.forms[0];
		if ((temp.empTypName.value != "" ) || (temp.empType.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmEmpTypeList.do?formAction=afterView&empTypName='+temp.empTypName.value+'&empType='+temp.empType.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Employee/EmployeeTypeList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeTypeAdd.jsp';
		document.forms[0].submit();
	}

	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmEmpTypeEdit.do?formAction=modify&id='+<%= objEmpTypDet.getEmp_Typ_Id()%>;
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
			document.forms[0].action = '<bean:message key="context"/>/frmEmpTypeEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmEmpTypeEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onLoad="init();loadToHidden();chkMkValid()">
<html:form action="frmEmpTypeEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="empTypName"/>
<input type="hidden" name="empType"/>
<input type="hidden" name="viewAll"/>

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.employee.employeetype"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1003" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1003" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Employee Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1003" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make EmployeeType Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1003" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmpTypDet.getEmp_Typ_Name() %></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmpTypDet.getEmp_Typ_Desc() %></td>
          </tr>
		  </table><br>
		  <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" class="FieldTitle"><bean:message key="prodacs.payroll.duty"/></td>
            <td width="1" class="FieldTitle">:</td> 
			<%
			if (objEmpTypDet.isEmp_Typ_Dt() == true)
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/active.gif'></td>
			<%
			}
			else
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
			<%
			}
			%>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.payroll.overtime"/></td>
				<td width="1" class="FieldTitle">:</td> 
			<%
			if (objEmpTypDet.isEmp_Typ_Ot() == true)
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/active.gif'></td>
			<%
			}
			else
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
			<%
			}
			%>
				<td width="100" class="FieldTitle"><bean:message key="prodacs.job.incentive"/></td>
				<td width="1" class="FieldTitle">:</td> 
			<%
			if (objEmpTypDet.isEmp_Typ_Incentive() == true)
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/active.gif'></td>
			<%
			}
			else
			{
			%>
				<td class="ViewData"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
			<%
			}
			%>
		  </tr>
		  </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td>
            <html:button property="returnEmployeeList" styleClass="Button" onclick="javascript:listEmployeeType()">Return to Employee Type List</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
