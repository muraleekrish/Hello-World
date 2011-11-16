<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionMachineDetailsManagerHome"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1006"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Machine Add");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionMachineDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionMachineDetailsManagerHome machineHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
		SessionMachineDetailsManager machineObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machineHomeObj.create(),SessionMachineDetailsManager.class);
		
		HashMap machineDetails = machineObj.getAllMachineTypes();
		
		pageContext.setAttribute("machineDetails",machineDetails);
		
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
	
%>		

<html:html>
<head>
<title><bean:message key="prodacs.machine.machinemodify.header"/></title>
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
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineAdd.jsp';
		document.forms[0].submit();
	}

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Machine/MachineList.jsp';
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
		temp.action = '<bean:message key="context"/>/frmMachEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

</script>
</head>

<body onLoad="init();loadToHidden();">
<html:form action="frmMachEdit" focus="machineType">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.machine.machinemodify.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Machine Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Machine Info'; return true"  onMouseOut="window.status=''; return true" resourceId="6" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Machine Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1006" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
	   </tr>
        </table><br>
        <table>
	<tr> 
	<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.machinecode"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="machineCode" styleClass="TextBox" size="10" readonly="true" /></td>
          </tr>
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.machine.machinename"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="machineName" styleClass="TextBox" size="50" readonly="true"/></td>            
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.machinetype"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="machineType" styleClass="Combo">
                <html:option value="0">-- Choose Type --</html:option>
  					 <html:options collection="machineDetails" property="key" labelProperty="value"/>                
              </html:select>
		</td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.installationdate"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="installationDate" styleClass="TextBox" size="12" readonly="true"/>
              <img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("installationDate",MachineEdit.installationDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.inuse"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:checkbox property="inUse" value="1" /></td>
          </tr>
          <tr> 
            <td colspan="3" class="PageSubTitle"><bean:message key="prodacs.machine.supplierdetails"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.supplier"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="supplier" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.contactperson"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="supplierContactPerson" styleClass="TextBox" size="30" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="supplierAddress" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.phonenumber"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="supplierPhoneNumber" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td colspan="3" class="PageSubTitle"><bean:message key="prodacs.machine.serviceproviderdetails"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.serviceprovider"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="serviceProvider" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.machine.contactperson"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="serviceContactPerson" styleClass="TextBox" size="30" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="serviceAddress" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.phonenumber"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="servicePhoneNumber" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td>
              <html:button property="modifyMachine" styleClass="Button" onclick="javascript:updateItem()">Modify Machine Info</html:button>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>