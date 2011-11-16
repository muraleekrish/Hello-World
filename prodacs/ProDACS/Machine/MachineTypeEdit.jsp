<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="1005"/>
<html:html>
<head>
<title><bean:message key="prodacs.machine.machinetypeedit.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script language = "Javascript" type="text/Javascript">
	function listMachineType()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineTypeList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineTypeAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmMachineTypeEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onLoad="loadToHidden();">
<html:form action="frmMachineTypeEdit" focus="description">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<html:hidden property="machineId"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.machine.machinetypeedit.titleheader"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New MachineType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List MachineType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="5" text="[ List ]" classId="TopLnk" onClick="javaScript:listMachineType();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make MachineType Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
          </tr> 
        </table><br>
        <table>
        <tr>
        <td colspan='2'><font size="1px" face="Verdana"><html:errors/></td>
        </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.machine.machinetypename"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="machineTypeName" styleClass="TextBox" size="50" readonly="true" /></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:textarea property="description" cols="50" rows="5" styleClass="TextBox" maxlength="250"/></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="modifyMachineType" styleClass="Button" onclick="javascript:updateItem()">Modify Machine Type</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>