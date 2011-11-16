<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<%@ page import="com.savantit.prodacs.facade.SessionReworkDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionReworkDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.ReworkDetails" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="12"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Rework View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	ReworkDetails objReworkDetails = null;
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionReworkDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionReworkDetailsManagerHome reworkHomeObj = (SessionReworkDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkDetailsManagerHome.class);
		SessionReworkDetailsManager reworkObj = (SessionReworkDetailsManager)PortableRemoteObject.narrow(reworkHomeObj.create(),SessionReworkDetailsManager.class);
		
/*		Create a object to reworkDetails */
		objReworkDetails = reworkObj.getReworkDetails(Integer.parseInt(id));
		
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
	var isValid = <%= objReworkDetails.getRwk_IsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.rwkCategory.value = '<%= session.getAttribute("reworkCategory") %>';
		temp.viewAll.value = '<%= session.getAttribute("validEntry") %>';

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
	
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.rwkCategory.value != "" ) || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmReworkList.do?formAction=afterView&rwkCategory='+temp.rwkCategory.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.forms[0].action = '<bean:message key="context"/>/Rework/ReworkList.jsp';
			temp.submit();
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Rework/ReworkAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmReworkEdit.do?formAction=modify&id='+<%= objReworkDetails.getRwk_Id() %>;
			document.forms[0].submit();
		}	
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmReworkEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmReworkEdit.do?formAction=makeValid';
		temp.submit();
	}

	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	

</script>
</head>

<body onload="loadToHidden();chkMkValid();">
<html:form action="frmReworkEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="rwkCategory"/>
<input type="hidden" name="viewAll"/>
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
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Rework Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Rework Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1012" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
        </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.rework.reworkcategory"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objReworkDetails.getRwk_Category() %></td>
          </tr>
          <tr>
            <td class="FieldTitle"><bean:message key="prodacs.rework.reworkreason"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objReworkDetails.getRwk_Rsn() %></td>
          </tr>
        </table></td>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retReworkList" styleClass="Button" onclick="javascript:listItem()">Return to Rework</html:button></td>
          </tr>
        </table>
    </tr>
  </table>
</html:form>
</body>
</html:html>
