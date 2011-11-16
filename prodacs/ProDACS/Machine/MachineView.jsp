<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.machine.MachineDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="6"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Machine View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	MachineDetails objMachineDetails = null;
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionMachineDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionMachineDetailsManagerHome machineHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
		SessionMachineDetailsManager machineObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machineHomeObj.create(),SessionMachineDetailsManager.class);
		
		objMachineDetails = machineObj.getMachineDetails(id);

		if (BuildConfig.DMODE)
			System.out.println("Is Valid: "+ objMachineDetails.getMach_IsValid());
		
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
	
%>		


<html:html>
<head>
<title><bean:message key="prodacs.machine.machineview.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
var isValid = <%= objMachineDetails.getMach_IsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.machName.value = '<%= session.getAttribute("machineName") %>';
		temp.machSrchTab.value = '<%= session.getAttribute("machineSearchTab") %>';
		temp.machineType.value = '<%= session.getAttribute("machineType") %>';
		temp.viewAll.value = '<%= session.getAttribute("machineValidEntries") %>';

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
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineAdd.jsp';
		document.forms[0].submit();
	}
	
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.machName.value != "" ) || (temp.machSrchTab.value != "") || (temp.machineType.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmMachInfoList.do?formAction=afterView&machName='+temp.machName.value+'&machSrchTab='+temp.machSrchTab.value+'&machineType='+temp.machineType.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Machine/MachineList.jsp';
			temp.submit();
		}
	}

	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmMachEdit.do?formAction=modify&id=<%= objMachineDetails.getMach_Cde() %>';
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmMachEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmMachEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>

</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="frmMachEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="machName"/>
<input type="hidden" name="machSrchTab"/>
<input type="hidden" name="machineType"/>
<input type="hidden" name="viewAll"/>

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.machine.machineview.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Machine Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Machine Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Machine Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Machine Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_Cde() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.machinename"/></td>
			<td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.machinetype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_Typ_Name() %></td>
          </tr>
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.machine.installationdate"/></td>
            <td width="1" class="FieldTitle">:</td>
            <%
            if (objMachineDetails.getMach_Install_Date() == null)
            {
            %>
            <td class="ViewData">&nbsp;</td>
            <%
            }
            else
            {
            %>
            <td class="ViewData"><%= objMachineDetails.getMach_Install_Date().toString().substring(0,10) %></td>
            <%
            }
            %>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.inuse"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle">
<%
	if (objMachineDetails.getMach_InUse().equals("1"))
	{
%>
            <img src='<bean:message key="context"/>/images/active.gif'>
<%
	}else 
	{
%>           
            <img src='<bean:message key="context"/>/images/inactive.gif'>
<%
	}
%>             
				</td>
          </tr>
          <tr> 
            <td colspan="3" class="PageSubTitle"><bean:message key="prodacs.machine.supplierdetails"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.supplier"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SP_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.contactperson"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SP_Cntct_Person() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SP_Addr() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.phonenumber"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SP_Phone() %></td>
          </tr>
          <tr> 
            <td width="220" colspan="3" class="PageSubTitle"><bean:message key="prodacs.machine.serviceproviderdetails"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.serviceprovider"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SPLR_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.contactperson"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SPLR_Cntct_Person() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SPLR_Addr() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.phonenumber"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objMachineDetails.getMach_SPLR_Phone() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retMachineList" styleClass="Button" onclick="javascript:listItem()">Return to  Machine List</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>