<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome" %>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<useradmin:userrights resource="48"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("User Preferences");
	EJBLocator obj = new EJBLocator();	/* Object instantiation for EJBLocator */
	SecAdminUserDetails secAdminDetails = new SecAdminUserDetails();
	SecAdminGroupDetails grpDetailObj = new SecAdminGroupDetails();
	HashMap custNames = new HashMap();
	Vector vec_selectedGroups = null;
	ContactDetails cntctDetails = new ContactDetails();
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	if (BuildConfig.DMODE)
		System.out.println("User Name :"+userName);
	try
	{
		/* Setting JNDI Name and Environment */
		obj.setJndiName("SessionSecurityAdminManager");
		obj.setEnvironment();
		
		/* Instantiating Home and Remote Objects */
		SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
		SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
		
		secAdminDetails = secAdminObj.getUserDetails(userName);
		cntctDetails = secAdminDetails.getObj_Contact_Details();
		vec_selectedGroups = secAdminDetails.getUser_Rolls();
		for (int i = 0; i < vec_selectedGroups.size(); i++)
		{
			grpDetailObj = (SecAdminGroupDetails) vec_selectedGroups.get(i);
		}
		pageContext.setAttribute("grpDetailObj",grpDetailObj);
		
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
	function modifyPrefs()
	{
		temp = document.forms[0];
		temp.hidUserId.value = '<%= userName %>';
		temp.hidGroupDetails.value = '<%= grpDetailObj %>';
		temp.action = '<bean:message key="context"/>/frmUserPrefsEdit.do?formAction=modify&id='+"<%= userName %>";
		temp.submit();
	}

</script>
</head>

<body>
<html:form action="frmUserPrefsEdit">
<html:hidden property="formAction"/>
<html:hidden property="hidUserId"/> <!-- Holding the User Name -->
<html:hidden property="hidGroupDetails"/>
<input type="hidden" name="custName"/>
<input type="hidden" name="company"/>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.userpreferences.userpreferences"/></td>
		</tr>
		</table><br>
			<table width="200" cellspacing="0" cellpadding="0" align="right">
			<tr> 
		 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify User Preferences'; return true"  onMouseOut="window.status=''; return true" resourceId="1048" text="[ Change Account Info ]" classId="TopLnk" onClick="javaScript:modifyPrefs();"/>
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
			<br>
				<table width="100%" cellspacing="0" cellpadding="0">
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.username"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="ViewData"><%= secAdminDetails.getUser_Name() %></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.customer.description"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="ViewData"><%= secAdminDetails.getUser_Desc() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.title"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Title() %></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.firstname"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Fname() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.lastname"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Lname() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.employee.dateofbirth"/></td>
					<td class="FieldTitle">:</td>
					<%
					if (cntctDetails.getCntct_Dob() == null)
					{
					%>
					<td class="ViewData">&nbsp;</td>
					<%
					}
					else
					{
					%>
					<td class="ViewData"><%= cntctDetails.getCntct_Dob().toString().substring(0,10) %></td>
					<%
					}
					%>
				</tr>
				<tr>				
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.company"/></td>
					<td class="FieldTitle">:</td>
				<%
				Set set = custNames.entrySet();
				Iterator it = set.iterator();
				while(it.hasNext())
				{
					Map.Entry me = (Map.Entry) it.next();
					if (Integer.parseInt(cntctDetails.getCntct_Company()+"") == Integer.parseInt(me.getKey()+""))
					{
				%>
					<td class="ViewData"><%= me.getValue() %></td>
				<%
					}
				}
				%>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.address"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Address1() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle">&nbsp;</td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Address2() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.position"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Position() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_City() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_State() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Pincode() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.common.country"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Country() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.email"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Email() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.hphone"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_HPhone() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.wphone"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_WPhone() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.extension"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Extension() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.mobile"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Mobile() %></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.customer.fax"/></td>
					<td class="FieldTitle">:</td>
					<td class="ViewData"><%= cntctDetails.getCntct_Fax() %></td>
				</tr>
				</table><br>
	</td>
</tr>
</table>
</html:form>
</body>
</html:html>
