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

	function loadValues()
	{
		var temp = document.forms[0];
		temp.userId.value = '<%= request.getParameter("userId") %>';
		temp.passWord.value = '<%= request.getParameter("userPwd") %>';
	}
</script>
</head>

<body onLoad="loadValues();">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="300" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td style="padding:5"><img src="images/logo.gif" width="300" height="30"></td>
        </tr>
        <tr> 
          <td class="Header" style="padding:5; border:outset 1px #666666;">Enter Master User ID &amp; Password</td>
        </tr>
        <tr>
          <td><html:form action="frmMasterLogin" focus="masterUserId" method="post">
		  <html:hidden property="formAction"/>
		  <html:hidden property="userId"/>
		  <html:hidden property="passWord"/>
              <table width="320" cellpadding="0" cellspacing="0" id="Login" border="0">
                <tr> 
                  <td width="90"><bean:message key="prodacs.masterlogin.userid"/></td>
                  <td width="1">:</td>
                  <td><html:text styleClass="TextBox" property="masterUserId" value="" size="30"/></td>
                </tr>
                <tr> 
                  <td><bean:message key="prodacs.login.password"/></td>
                  <td>:</td>
                  <td><html:password styleClass="TextBox" property="masterUserPwd" value="" size="30" onkeydown="isEnter();"/></td>
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
            </html:form></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html:html>
