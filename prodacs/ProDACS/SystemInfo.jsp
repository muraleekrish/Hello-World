<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>
<head>
<title>ProDACS</title>
<link href="styles/pmac.css" rel="stylesheet" type="text/css">
<script>
	function getSysInfo()
	{
		var temp = document.forms[0];
		var temp = document.forms[0];
		try
		{
			var myobject = document.getElementById("ocxLogin");
			temp.cpuId.value = myobject.Getcpuid();
			temp.machId.value = myobject.GetMACAddress(":");
			temp.driveId.value = myobject.GetDriveSerialNumber("c");
		}
		catch(e)
		{
			alert(e.message);
		}
	}

	function sendSysInfo()
	{
		var temp = document.forms[0];
		temp.formAction.value = "sendSysInfo";
		temp.submit();
	}

</script>
</head>
<body onLoad="getSysInfo();">
<html:form action="frmSysInfo" method="post">
<html:hidden property="formAction"/>
<html:hidden property="cpuId"/>
<html:hidden property="machId"/>
<html:hidden property="driveId"/>
<br><br>
<table width="100%" cellspacing="0" cellpadding="0">
<tr> 
	<td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td>Machine Registration</td>
	</tr>
</table>
<br>

<table width="100%" cellpadding="0" cellspacing="0" border="0" align="center">
<tr>
	<td class="FieldTitle" width="110">Registered By <span class="mandatory">*</span></td>
	<td class="FieldTitle" width="1">:</td>
	<td><html:text property="userName" styleClass="TextBox" maxlength="20"/></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
	<td class="FieldTitle" width="100">Description <span class="mandatory">*</span></td>
	<td class="FieldTitle" width="1">:</td>
	<td><html:textarea property="description" styleClass="TextBox" rows="5" cols="50"/></td>
</tr>
</table>
<br>
<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
<tr>
	<td align="center" colspan="3" class=""><input type="button" name="regMachine" onclick="sendSysInfo()" value="Register this machine"/></td>
</tr>
</table>
<table cellpadding="0" cellspacing="0" width="100%" id="enableOcx" style="display:none">
<tr>
	<td><OBJECT CLASSID="clsid:5C7BD3A8-D640-422B-8B16-EB87F78EB51E" id="ocxLogin" codebase="/prodacsunit5/Secocx.ocx"/></td>
</tr>
</table>
</html:form>
</body>
</html:html>
