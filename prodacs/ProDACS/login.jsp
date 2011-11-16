<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<title>Prodacs - Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="styles/pmac.css" rel="stylesheet" type="text/css">
<script>
	function validateUser()
	{
		temp = document.forms[0];
		temp.formAction.value = "login";
		temp.submit();
	}
	
	function isEnter()
	{
		temp = document.forms[0];
		if (event.keyCode == 13)
		{
			temp.login.focus();
			temp.formAction.value = "login";
			temp.submit();
		}
	}

	function SystemIds()
	{
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
		if (temp.formAction.value == "loginFailed")
		{
			showLink.style.display = "block";
		}
	}

	function checkClicks()
	{
		if(event.button == 2)
		{
			alert("Right Clicking had been disabled!")
			return false;
		}
	}
</script>
</head>

<body onLoad="SystemIds();" onmouseDown="checkClicks();">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<table width="300" align="center" cellpadding="0" cellspacing="0">
		<tr> 
			<td style="padding:5"><img src="images/logo.gif" width="300" height="30"></td>
		</tr>
		<tr> 
			<td class="Header" style="padding:5; border:outset 1px #666666;">Enter User ID &amp; Password</td>
		</tr>
		<tr>
			<td>
				<html:form action="frmLogin" focus="userId" method="post">
				<html:hidden property="formAction"/>
				<html:hidden property="cpuId"/>
				<html:hidden property="machId"/>
				<html:hidden property="driveId"/>
				<table width="100%" cellpadding="0" cellspacing="0" id="Login" border="0">
				<tr> 
					<td width="65"><bean:message key="prodacs.login.userid"/></td>
					<td width="1">:</td>
					<td><html:text styleClass="TextBox" property="userId" value="" size="30"/></td>
				</tr>
				<tr> 
					<td><bean:message key="prodacs.login.password"/></td>
					<td>:</td>
					<td><html:password styleClass="TextBox" property="userPwd" value="" size="30" onkeydown="isEnter();"/></td>
				</tr>
				<tr> 
					<td colspan="3" id="BtnBg"><html:button property="login" styleClass="Button" value="Login" onclick="validateUser();"/></td>
				</tr>
				</table>
				<table>
				<tr>
					<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
				</tr>
				</table>
				</html:form>
			</td>
		</tr>
		</table>
		<table cellpadding="0" cellspacing="0" width="100%" id="showLink" style="display:none">
		<tr>
			<td class="TopLnk1">
				Your Machine is not authorized to use Prodexia, enter your unique Master User Name and Password
				<a href="MasterUser.jsp" onMouseOver="window.status='Redirecting to Master User Entry Screen'; return true"  onMouseOut="window.status=''; return true">Click Here&nbsp;</a></td>
			</td>
		</tr>
		</table>
		<table cellpadding="0" cellspacing="0" width="100%" id="enableOcx" style="display:none">
		<tr>
			<td><OBJECT CLASSID="clsid:5C7BD3A8-D640-422B-8B16-EB87F78EB51E" id="ocxLogin" codebase="/prodacsunit5/Secocx.ocx"/></td>
		</tr>
		</table>
	</td>
</tr>
</table>
</body>
</html:html>
