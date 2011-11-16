<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="1003"/>
<html:html>
<head>
<title><bean:message key="prodacs.employee.employeetypeedit.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script language = "Javascript" type="text/Javascript">
	function listEmployeeType()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeTypeList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeTypeAdd.jsp';
		document.forms[0].submit();
	}
	
	function updateItem()
	{
		temp = document.forms[0];
		temp.formAction.value = "update";
		temp.submit();
	}
	
	function makeInvalidItem()
	{
			document.forms[0].action = '<bean:message key="context"/>/frmEmpTypeEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function isNumeric(code)
	{
		if(code>=48 && code<=57)
		{
			return true;
		}
		else
		{
		 return false;
		}		
	}

</script>
</head>

<body onLoad="loadToHidden();">
<html:form action="frmEmpTypeEdit" focus="employeeType">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<html:hidden property="employeeId"/>
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
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="3" text="[ List ]" classId="TopLnk" onClick="javaScript:listEmployeeType();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Employee Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1003" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
          </tr> 
        </table><br>
        <table>
        <tr>
        <td colspan='2'><font size="1px" face="Verdana"><html:errors/></td>
        </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="employeeType" styleClass="TextBox" size="50"/></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:textarea property="employeeDescription" cols="50" rows="5" styleClass="TextBox" maxlength="250"/></td>
          </tr><br><br>
  		  <tr>
			<td class="FieldTitle"><html:checkbox property="dutyHrs" value="0">Duty Hours</html:checkbox></td>
			<td class="FieldTitle"><html:checkbox property="otHrs" value="0">OT Hours</html:checkbox></td>
			<td class="FieldTitle"><html:checkbox property="incentiveHrs" value="0">Incentive Hours</html:checkbox></td>
    	  </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="modifyEmployeeType" styleClass="Button" onclick="javascript:updateItem()">Modify Employee Type</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
