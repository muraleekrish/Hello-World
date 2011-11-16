<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManager" %>
<%@ page import="com.savantit.prodacs.facade.SessionSecurityAdminManagerHome" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.securityadministration.UserAddForm" />
<jsp:setProperty name="frm" property="*" />

<useradmin:userrights resource="1028"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("User Add");
	EJBLocator obj = new EJBLocator(); /* Instantiating Object for EJBLocator */
	HashMap groupDets = new HashMap();
	HashMap empTypeDet = new HashMap();
	HashMap empDets = new HashMap();
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

		/* HashMap for loading Employee Type */
		empTypeDet = secAdminObj.getAllEmployeeTypes();
		pageContext.setAttribute("empTypeDet",empTypeDet);
		if (BuildConfig.DMODE)
			System.out.println("Employee Type Details(HM) :"+empTypeDet);
		
		/*HashMap for loading GroupNames */
		groupDets = secAdminObj.getAllGroups();
		pageContext.setAttribute("groupDets",groupDets);
		if (BuildConfig.DMODE)
			System.out.println("GroupDetails(HM) :"+groupDets);
		
		/* HashMap for loading Customer Names */
		custNames = secAdminObj.getAllCustomers();
		pageContext.setAttribute("custNames",custNames);
		if (BuildConfig.DMODE)
			System.out.println("Customer Names (HM):"+custNames);
		
		if (frm.getFormAction().equalsIgnoreCase("empDets"))
		{
			/* HashMap for loading Employee Names */
			empDets = secAdminObj.getAllEmployeesByType(Integer.parseInt(frm.getEmployeeType()));
			pageContext.setAttribute("empDets",empDets);
			if (BuildConfig.DMODE)
				System.out.println("Employee Names(HM) :"+empDets);
		}
		else 
		{
			pageContext.setAttribute("empDets",empDets);
		}
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
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script>
	function listUser()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/SecurityAdministration/UserList.jsp';
		temp.submit();
	}

	function addItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/SecurityAdministration/UserAdd.jsp';
		temp.submit();
	}

	function loadEmployeeNames()
	{
		temp = document.forms[0];
		if (temp.employeeType.value != "0")
		{
			temp.formAction.value = "empDets";
			temp.submit();
		}
	}
	
	function loadCustomers()
	{
		temp = document.forms[0];
		if (temp.company.value != "0")
		{
			temp.custName.value = temp.company.options[temp.company.selectedIndex].value;
		}
	}
	function loadEmpDets()
	{
		temp = document.forms[0];
		if (temp.employeeName.value != "0")
		{
			temp.empName.value = temp.employeeName.value;
			temp.formAction.value = "empDets";
			temp.submit();
		}
	}
	
	function adduser()
	{
		temp = document.forms[0];
		var str = temp.mailId.value;
		var pwd = temp.userPwd.value;
		var confrmPwd = temp.confirmPwd.value;
		if (pwd != confrmPwd)
		{
			alert("PassWords are incorrect!");
			return false;
		}
		if (str != "")
		{
			var at="@";
			var dot=".";
			var lat=str.indexOf(at);
			var lstr=str.length;
			var ldot=str.indexOf(dot);
			if (str.indexOf(at)==-1)
			{
			   alert("Invalid E-mail ID");
			   return false;
			}

			if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
			{
			   alert("Invalid E-mail ID");
			   return false;
			}

			if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
			{
				alert("Invalid E-mail ID");
				return false;
			}

			if (str.indexOf(at,(lat+1))!=-1)
			{
				alert("Invalid E-mail ID");
				return false;
			}

			if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
			{
				alert("Invalid E-mail ID");
				return false;
			}

			if (str.indexOf(dot,(lat+2))==-1)
			{
				alert("Invalid E-mail ID");
				return false;
			}
			
			if (str.indexOf(" ")!=-1)
			{
				alert("Invalid E-mail ID");
				return false;
			}
			temp.formAction.value = "add";
			temp.submit();
		}
		else if (str == "")
		{
			//return true;
			temp.formAction.value = "add";
			temp.submit();
		}
	}
</script>
</head>

<body onLoad="init();">
<html:form action="frmUserAdd" focus="employeeType">
<html:hidden property="formAction"/>
<html:hidden property="empName"/>
<html:hidden property="custName"/>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
<tr> 
	<td valign="top">
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.useradministration.newuserinfo"/></td>
		</tr>
		</table><br>
			<table width="100" cellspacing="0" cellpadding="0" align="right">
			<tr>
		 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1028" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
		 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List User Info'; return true"  onMouseOut="window.status=''; return true" resourceId="28" text="[ List ]" classId="TopLnk" onClick="javaScript:listUser();"/>
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
					<td width="110" class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:select property="employeeType" styleClass="Combo" onchange="loadEmployeeNames();">
					<html:option value="0">-- Employee Type --</html:option>
					<html:options collection="empTypeDet" property="key" labelProperty="value"/>
					</html:select></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.employee.employeename"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:select property="employeeName" styleClass="Combo" onchange="loadEmpDets();">
					<html:option value="0">-- Employee Name --</html:option>
					<html:options collection="empDets" property="key" labelProperty="value"/>
					</html:select></td>
				</tr>
				<tr> 
					<td width="110" class="FieldTitle"><bean:message key="prodacs.useradministration.username"/><span class="mandatory">*</span></td>
					<td width="1" class="FieldTitle">:</td>
					<td class="FieldTitle"><html:text property="userId" styleClass="TextBox" size="25"/></td>
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
					<img src='<bean:message key="context"/>/images/calendar.gif' name='calImg' align='absmiddle' onclick='show_calendar("dateOfBirth",UserAdd.dateOfBirth.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
				</tr>
				<tr> 
					<td class="FieldTitle"><bean:message key="prodacs.useradministration.company"/></td>
					<td class="FieldTitle">:</td>
					<td class="FieldTitle"><html:select property="company" styleClass="Combo" onchange="loadCustomers();">
												<html:option value="0">-- PMAC User --</html:option>
												<html:options collection="custNames" property="key" labelProperty="value"/>
										   </html:select></td>
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
					<td class="FieldTitle"><html:text property="position" styleClass="TextBox" size="50"/></td>
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
					<td class="FieldTitle"><html:text property="fax" styleClass="TextBox" size="12"/></td>
				</tr>
				</table><br>
					<table width="400" cellspacing="0" cellpadding="0">
					<tr>
						<td class="PageSubTitle"><bean:message key="prodacs.useradministration.groups"/><span class="mandatory">*</span></td>
					</tr>
					</table>
						<table width="400" cellspacing="0" cellpadding="0">
						<%
							Set set = groupDets.entrySet();
							Iterator it = set.iterator();
							while(it.hasNext())
							{
								Map.Entry me = (Map.Entry)it.next();
						%>
						<tr> 
							<td width="20" class="TableItems"><html:multibox property="groupNames" value="<%= me.getKey().toString() %>"/></td>
							<td class="TableItems"><%= me.getValue() %></td>
						</tr>
						<%
							}
						%>
						</table><br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="useradd" styleClass="Button" value="Add User" onclick="adduser();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>
