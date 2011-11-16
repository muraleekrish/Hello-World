<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="43"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Employee Type Rule");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	EmployeeTypeDetails[] empDets= null;
	try
    {
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionEmpDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
		empDets = empObj.getEmployeeTypeWithTeam();
		pageContext.setAttribute("empDets",empDets);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			e.printStackTrace();
			System.out.println("Problem in EmployeeTypeRule.jsp");
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.employee.employeelist.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
	function updateItem()
	{
		temp = document.forms[0];
		var obj = document.getElementById('empInfoList');
		var empDetails = "";
		for(var i = 0; i < obj.children(0).children.length; i++)
		{
			if (i != 0)
			{
				empDetails = empDetails + "^";
			}
			empDetails = empDetails +obj.children(0).children(i).children(0).children(0).value + "-"+ // EmpTypeName
									 obj.children(0).children(i).children(1).children(0).value + "-"+ // MinRqdQty
									 obj.children(0).children(i).children(2).children(0).value; //EmpTypId
		
			temp.empDets.value = empDetails;
			temp.formAction.value = "update";
			temp.submit();
		}
	}

	/* Numbers only Allowed */
	function isNumber()
	{
		if ((event.keyCode > 47) && (event.keyCode < 58))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
</script>
</head>
<body>
<html:form action="frmEmpTypeRule">
<html:hidden property="formAction"/>
<html:hidden property="empDets"/>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
        <tr>
            <td><bean:message key="prodacs.employee.teaminfo"/></td>
        </tr>
      </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr>
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Employee Type Rule Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1043" text="[ Update ]" classId="TopLnk" onClick="javaScript:updateItem();"/>
        </tr>
        </table>
        <font class="message">
			<html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   </html:messages>
		   </font>
			<table>
				<tr> 
					<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
				</tr>
			</table>
        <br><br>
        <table width="70%" cellpadding="3" cellspacing="0">
        <tr>
        	<td width="188" class="SortHeader"><bean:message key="prodacs.employee.employeetype"/></td>
        	<td class="SortHeader"><bean:message key="prodacs.employee.minimumrequiredqty"/></td>
        </tr>
		</table>
        <table width="70%" cellpadding="0" cellspacing="0" id="empInfoList">
        <%
			for (int i = 0; i < empDets.length; i++)
			{
				EmployeeTypeDetails empTypeDets = (EmployeeTypeDetails) empDets[i];
				if (BuildConfig.DMODE)
					System.out.println(empTypeDets.getEmp_Typ_Name()+"-"+empTypeDets.getEmp_Typ_Id()+"-"+empTypeDets.getMin_Rqd_Qty());
        %>
        <tr>
            <td width="191" class="TableItems"><html:text property="empTypName" value='<%= empTypeDets.getEmp_Typ_Name()%>' styleClass="TextBoxFull" readonly="true"/>&nbsp;</td>
            <td class="TableItems"><html:text property="team" value='<%= empTypeDets.getMin_Rqd_Qty()+"" %>' styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="2" size="20"/>&nbsp;</td>
            <td><html:hidden property="empTypId" value='<%= empTypeDets.getEmp_Typ_Id()+"" %>'/></td>
		</tr>
		<%
			}
		%>
		</table>
</html:form>
</body>
</html:html>
