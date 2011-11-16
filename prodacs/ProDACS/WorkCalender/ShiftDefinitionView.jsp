<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>

<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workcalendar.ShiftDefnDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="8"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Shift Definition View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	ShiftDefnDetails objShiftDefnDetails = null;
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome shiftDefHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager shiftDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(shiftDefHomeObj.create(),SessionWorkCalendarDetailsManager.class);

 		objShiftDefnDetails = shiftDefObj.getShiftDefinition(Integer.parseInt(id));

   }catch(Exception e)
  	{
	  	if (BuildConfig.DMODE)
		   e.printStackTrace();
  	}
%>	    

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objShiftDefnDetails.getShiftIsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';

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
		if (temp.viewAll.value != "" )
		{
			temp.action = '<bean:message key="context"/>/frmShiftDftnList.do?formAction=afterView&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/WorkCalender/ShiftDefinitionList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/ShiftDefinitionAdd.jsp';
		document.forms[0].submit();
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=modify&id='+<%= objShiftDefnDetails.getShiftId() %>;
			document.forms[0].submit();
		}	
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmShiftDftnEdit.do?formAction=delete';
		temp.submit();
	}
	
</script>
</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="frmShiftDftnEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="viewAll"/>
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
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Shift Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete Shift Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Shift Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Shift Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1008" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
        </tr>
        </table><br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objShiftDefnDetails.getShiftName() %></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftdescription"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="ViewData"><%= objShiftDefnDetails.getShiftDesc() %></td>
          </tr>
		</table>
		
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retShiftDefinitionList" styleClass="Button" onclick="javascript:listItem()">Return to Shift Definition List</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>