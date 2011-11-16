<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="1008"/>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/ShiftDefinitionList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/ShiftDefinitionAdd.jsp';
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
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=delete';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onload = "loadToHidden();">
<html:form action="frmShiftDftnEdit" focus="shiftDescription">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workcalendar.shiftdefinition.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Shift Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Shift Info'; return true"  onMouseOut="window.status=''; return true" resourceId="8" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete Shift Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Shift Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
        </tr>
        </table>
        <font class="message">
			<html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   </html:messages>
		   </font>
			<table>
				<tr> 
					<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
				</tr>
			</table>
        <br>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="shiftName" styleClass="TextBox" size="25" readonly = "true" /></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftdescription"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:textarea property="shiftDescription" cols="50" rows="4" styleClass="TextBox" maxlength = "250" /></td>
          </tr>
		</table>
		
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="modifyShiftDefinition" styleClass="Button" onclick="javascript:updateItem()" value = "Modify Shift Definition" />
            </td>
          </tr>
        </table>
        </td>
    </tr>
  </table>
</html:form>
</body>
</html:html>