<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.facade.SessionReworkDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionReworkDetailsManager"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1012"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Rework Edit");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionReworkDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionReworkDetailsManagerHome reworkHomeObj = (SessionReworkDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkDetailsManagerHome.class);
		SessionReworkDetailsManager reworkObj = (SessionReworkDetailsManager)PortableRemoteObject.narrow(reworkHomeObj.create(),SessionReworkDetailsManager.class);
		
		HashMap rwkCategory = reworkObj.getAllReworkCategories();
		pageContext.setAttribute("rwkCategory",rwkCategory);
		
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.rework.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Rework/ReworkList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Rework/ReworkAdd.jsp';
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
		temp.action = '<bean:message key="context"/>/frmReworkEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onload="loadToHidden();">
<html:form action="frmReworkEdit" focus="reworkReason">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.rework.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="12" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Rework Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
        </tr>
        </table><br>
        <table>
	<tr> 
	<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.rework.reworkcategory"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><html:text property="reworkCategory" styleClass="TextBox" size="30" readonly="true"/></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.rework.reworkreason"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><html:text property="reworkReason" styleClass="TextBox" size="40" maxlength="25"/></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="modifyRework" styleClass="Button" onclick="javascript:updateItem()">Modify Rework</html:button>
              <html:reset styleClass="Button">Reset</html:reset></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>