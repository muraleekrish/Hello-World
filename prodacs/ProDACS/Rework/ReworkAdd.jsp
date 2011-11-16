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
		System.out.println("Rework Add");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionReworkDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionReworkDetailsManagerHome reworkHomeObj = (SessionReworkDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkDetailsManagerHome.class);
		SessionReworkDetailsManager reworkObj = (SessionReworkDetailsManager)PortableRemoteObject.narrow(reworkHomeObj.create(),SessionReworkDetailsManager.class);

/*		Loading ReworkCategory */
		HashMap reworkCategory = reworkObj.getAllReworkCategories();
		pageContext.setAttribute("reworkCategory",reworkCategory);
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error in ReworkAdd.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.rework.createnewcategory"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Rework/ReworkList.jsp';
		document.forms[0].submit();
	}

	function rwkaddItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Rework/ReworkAdd.jsp';
		document.forms[0].submit();
	}

	function call()
	{
		var val = document.forms[0].reworkCategory.value;
		if (val == "-1")
		{
			newRework.style.display = 'block';
		}
		else
		{
			newRework.style.display = 'none';
		}
	}

	function addItem()
	{
		document.forms[0].submit();
	}
</script></head>

<body>
<html:form action="frmCategory" focus="reworkCategory">
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.rework.createnewcategory"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ Add ]" classId="TopLnk" onClick="javaScript:rwkaddItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
        </tr>
        </table>
       <table>
		  <tr>
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		   </td>
		  </tr>
		 </table>

        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="120" class="FieldTitle"><bean:message key="prodacs.rework.reworkcategory"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="reworkCategory" styleClass="Combo" onclick="call()">
                <html:option value="0">-- Choose Category --</html:option>
                <html:option value="-1">-- New Category --</html:option>
 					 <html:options collection="reworkCategory" property="key" labelProperty="value"/>
              </html:select></td>
          </tr>
          <tr id="newRework" style="display:none">
            <td class="FieldTitle"><bean:message key="prodacs.rework.newcategory"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="newReworkCategory" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.rework.reworkreason"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="reworkReason" styleClass="TextBox" size="25" maxlength="25" /></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="addNewCategory" styleClass="Button" onclick="javascript:addItem()">Add New Category</html:button>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>