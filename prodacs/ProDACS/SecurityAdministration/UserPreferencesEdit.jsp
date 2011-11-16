<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome" %>

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.securityadministration.UserPreferencesForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="1048"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("User Preferences Edit");
	EJBLocator obj = new EJBLocator(); /* Instantiating Object for EJBLocator */
	HashMap custNames = new HashMap();
	try
	{
		/* Setting JNDI Name and Environment */
		obj.setJndiName("SessionSecurityAdminManager");
		obj.setEnvironment();
		
		/* Instantiating Home and Remote Objects */
		SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
		SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
		if (BuildConfig.DMODE)
			System.out.println("FormAction :"+frm.getFormAction());
		
		/* Loading Customer Names */
		custNames = secAdminObj.getAllCustomers();
		if (BuildConfig.DMODE)
			System.out.println("Customer Names (HM):"+custNames);
		pageContext.setAttribute("custNames",custNames);
	}
	catch (Exception e)
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
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script>
	function loadDefault()
	{
		var temp = document.forms[0];
		if (temp.formAction.value == "modify")
		{
			<%
				Set set = custNames.entrySet();
				Iterator it = set.iterator();
				while(it.hasNext())
				{
					Map.Entry me = (Map.Entry) it.next();
			%>
			if (parseInt(temp.custName.value) == '<%= me.getKey() %>')
			{
				temp.company.value = '<%= me.getValue() %>';
			}
			<%
				}
			%>
		}
	}

	function updateUser()
	{
		var temp = document.forms[0];
		var str = temp.mailId.value;
		var pwd = temp.userPwd.value;
		var confrmPwd = temp.confirmPwd.value;
		if (pwd != confrmPwd)
		{
			alert("PassWords are incorrect!");
			return false;
		}
		temp.formAction.value = "update";
		temp.submit();
	}
</script>
</head>

<body onLoad="init(); loadDefault();">
<html:form action="frmUserPrefsEdit">
<html:hidden property="formAction"/>
<html:hidden property="cntctId"/>
<html:hidden property="hidGroupDetails"/>
<html:hidden property="custName"/>

<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.userpreferences.modifyuserpreferences"/></td>
		</tr>
		</table><br>
		      <font class="message">
			 	<html:messages id="messageid" message="true">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
			   	</html:messages>
			</font>
			<table>
			<tr>
				<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
			</tr>
			</table><br>	
				<table width="100%" cellspacing="0" cellpadding="0">
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.username"/><span class="mandatory">*</span></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="userId" styleClass="TextBox" size="25" readonly="true"/></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.password"/><span class="mandatory">*</span></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:password property="userPwd" styleClass="TextBox" size="25"/></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.confirmpassword"/><span class="mandatory">*</span></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:password property="confirmPwd" styleClass="TextBox" size="25"/></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.customer.description"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:textarea property="description" styleClass="TextBox" rows="5" cols="50"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.title"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="title" styleClass="TextBox" size="6"/></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.firstname"/><span class="mandatory">*</span></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="firstName" styleClass="TextBox" size="50"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.lastname"/><span class="mandatory">*</span></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="lastName" styleClass="TextBox"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.employee.dateofbirth"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="dateOfBirth" styleClass="TextBox" size="12"/> 
					<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("dateOfBirth",UserPreferences.dateOfBirth.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.company"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="company" styleClass="TextBox" size="50" readonly="true"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="address1" styleClass="TextBox" size="50"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle">&nbsp;</td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="address2" styleClass="TextBox" size="50"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.position"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="position" styleClass="TextBox" size="50" readonly="true"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="city" styleClass="TextBox" size="25"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="state" styleClass="TextBox" size="25"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="pinCode" styleClass="TextBox" size="10"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.country"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="country" styleClass="TextBox" size="20"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.email"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="mailId" styleClass="TextBox" size="50"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.hphone"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="housePhone" styleClass="TextBox" size="15"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.wphone"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="workPhone" styleClass="TextBox" size="15"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.extension"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="extension" styleClass="TextBox" size="13"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.mobile"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="mobile" styleClass="TextBox" size="12"/></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.customer.fax"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="mobile" styleClass="TextBox" size="12"/></td>
				</tr>
				</table><br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="modify" styleClass="Button" value="Update" onclick="updateUser();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>
