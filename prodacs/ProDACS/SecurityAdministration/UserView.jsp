<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.securityadministration.UserEditForm" />
<jsp:setProperty name="frm" property="*" /> 

<bean:parameter id="id" name="id" value="0"/>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome" %>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.SecAdminUserDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.ContactDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.SecAdminGroupDetails" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="28"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("User View");
	EJBLocator obj = new EJBLocator();	/* Object instantiation for EJBLocator */
	SecAdminUserDetails secAdminDetails = new SecAdminUserDetails();
	SecAdminGroupDetails grpDetailObj = new SecAdminGroupDetails();	
	ContactDetails cntctDetails = new ContactDetails();
	Vector vec_selectedGroups = null;
	try
	{
		/* Setting JNDI Name and Environment */
		obj.setJndiName("SessionSecurityAdminManager");
		obj.setEnvironment();
		
		/* Instantiating Home and Remote Objects */
		SessionSecurityAdminManagerHome secAdminHomeObj = (SessionSecurityAdminManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionSecurityAdminManagerHome.class);
		SessionSecurityAdminManager secAdminObj = (SessionSecurityAdminManager) PortableRemoteObject.narrow(secAdminHomeObj.create(),SessionSecurityAdminManager.class);
		
		secAdminDetails = secAdminObj.getUserDetails(id);
		cntctDetails = secAdminDetails.getObj_Contact_Details();
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
	var isValid = <%= secAdminDetails.isUser_IsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.uname.value = '<%= session.getAttribute("userId") %>';
		temp.uNameSelect.value = '<%= session.getAttribute("uidSelection") %>';
		temp.fName.value = '<%= session.getAttribute("firstName") %>';
		temp.fNameSelect.value = '<%= session.getAttribute("firstNameSelect") %>';
		temp.company.value = '<%= session.getAttribute("company") %>';
		temp.companySelect.value = '<%= session.getAttribute("companySelect") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';

		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 0)
			{
				mkInValid.style.display = "none";
				mkValid.style.display = "block";
			}
			else
			{
				mkInValid.style.display = "block";
				mkValid.style.display = "none";		
			}
		}
	}
		
	function loadToHidden()
	{
		temp = document.forms[0];
		temp.ids.value = temp.id.value;
	}
	
	function listUser()
	{
		temp = document.forms[0];
		if ((temp.uname.value != "" ) || (temp.uNameSelect.value != "") || (temp.fName.value != "") || (temp.fNameSelect.value != "")  || (temp.company.value != "")  || (temp.companySelect.value != "")  || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmUserInfoList.do?formAction=afterView&uname='+temp.uname.value+'&uNameSelect='+temp.uNameSelect.value+'&fName='+temp.fName.value+'&fNameSelect='+temp.fNameSelect.value+'&company='+temp.company.value+'&companySelect='+temp.companySelect.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/SecurityAdministration/UserList.jsp';
			temp.submit();
		}
	}
	
	function addUser()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/SecurityAdministration/UserAdd.jsp';
		temp.submit();
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmUserEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmUserEdit.do?formAction=makeValid';
		temp.submit();
	}

	function editUser()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmUserEdit.do?formAction=modify&id='+"<%= secAdminDetails.getUser_Name() %>";
			document.forms[0].submit();
		}	
	}

</script>
</head>

<body onLoad="init(); chkMkValid(); loadToHidden();">
<html:form action="frmUserEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<html:hidden property="hidGroupDetails"/>
<input type="hidden" name="uname"/>
<input type="hidden" name="uNameSelect"/>
<input type="hidden" name="fName"/>
<input type="hidden" name="fNameSelect"/>
<input type="hidden" name="company"/>
<input type="hidden" name="companySelect"/>
<input type="hidden" name="viewAll"/>

<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.useradministration.viewuserinfo"/></td>
		</tr>
		</table><br>
			<table width="100" cellspacing="0" cellpadding="0" align="right">
			<tr> 
		 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ Add ]" classId="TopLnk" onClick="javaScript:addUser();"/>
		 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="28" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editUser();"/>
		 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make User Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
		 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make User Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
			</tr>
			</table><br>
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
					if (cntctDetails.getCompanyName() != null)
					{
				%>
					<td class="ViewData"><%= cntctDetails.getCompanyName() %></td>
				<%
					}
					else
					{
				%>
					<td class="ViewData">&nbsp;</td>
				<%
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
					<table width="400" cellspacing="0" cellpadding="0">
					<tr>
						<td class="PageSubTitle"><bean:message key="prodacs.useradministration.groups"/></td>
					</tr>
					</table>
						<table width="400" cellspacing="1" cellpadding="0" id="grpTable">
						
						<%
							vec_selectedGroups = secAdminDetails.getUser_Rolls();						
							for (int i = 0; i < vec_selectedGroups.size(); i++)
							{
								grpDetailObj = (SecAdminGroupDetails) vec_selectedGroups.get(i);
						%>
						<tr> 
							<td width="20" class="TableItems2"><%= i+1 %></td>
							<td class="TableItems"><%= grpDetailObj.getGroup_Name() %></td>
						</tr>
						<%
						}
						%>
						
						</table><br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="return" styleClass="Button" value="List" onclick="listUser();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>