<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.businessimplementation.machine.MachineTypeDetails" %>
<useradmin:userrights resource="5"/>
<%
	EJBLocator obj = new EJBLocator();
	MachineTypeDetails machTypDetObj = null;
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionMachineDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionMachineDetailsManagerHome machHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
		SessionMachineDetailsManager machObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machHomeObj.create(),SessionMachineDetailsManager.class);
		
		machTypDetObj = machObj.getMachineTypeDetails(Integer.parseInt(id));
	}catch (Exception e)
	{
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.machine.machinetypeview.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= machTypDetObj.getMc_Typ_IsValid()%>;
	
	function chkMkValid()
	{
		temp = document.forms[0];
		temp.machTypeName.value = '<%= session.getAttribute("searchText") %>';
		temp.machTypeSrchTab.value = '<%= session.getAttribute("machineType") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';

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
	
	function listMachineType()
	{
		temp = document.forms[0];
		if ((temp.machTypeName.value != "" ) || (temp.machTypeSrchTab.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmMachineTypeList.do?formAction=afterView&machTypeName='+temp.machTypeName.value+'&machTypeSrchTab='+temp.machTypeSrchTab.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Machine/MachineTypeList.jsp';
			temp.submit();
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineTypeAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmMachineTypeEdit.do?formAction=modify&id='+<%= machTypDetObj.getMc_Typ_Id()%>;
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
			document.forms[0].action = '<bean:message key="context"/>/frmMachineTypeEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmMachineTypeEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onLoad="init();loadToHidden();chkMkValid()">
<html:form action="frmMachineTypeEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="machTypeName"/>
<input type="hidden" name="machTypeSrchTab"/>
<input type="hidden" name="viewAll"/>

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.machine.machinetypeview.titleheader"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New MachineType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify MachineType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make MachineType Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make MachineType Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1005" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.machine.machinetypename"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= machTypDetObj.getMc_Typ_Name() %></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <%
            if (machTypDetObj.getMc_Typ_Desc() == null)
            {
            %>
            <td class="ViewData">&nbsp;</td>
            <%
            }
            else
            {
            %>
            <td class="ViewData"><%= machTypDetObj.getMc_Typ_Desc() %></td>
            <%
            }
            %>
            
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td>
            <html:button property="returnMachineTypeList" styleClass="Button" onclick="javascript:listMachineType()">Return to Machine Type List</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>