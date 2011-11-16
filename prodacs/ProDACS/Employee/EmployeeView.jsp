<%@ page language = "java" session="true"%>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.businessimplementation.employee.EmployeeDetails" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="4"/>
<%
	EJBLocator obj = new EJBLocator();
	EmployeeDetails objEmployeeDetails = null;
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionEmpDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
		
		objEmployeeDetails = empObj.getEmployeeDetails(Integer.parseInt(id));
		if (BuildConfig.DMODE)
			System.out.println("Emp Id -> "+objEmployeeDetails.getEmp_Id());
	}catch (Exception e)
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
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objEmployeeDetails.getEmp_Isvalid()%>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.empName.value = '<%= session.getAttribute("employeeName") %>';
		temp.empSrchTab.value = '<%= session.getAttribute("employeeNameSearchTab") %>';
		temp.empCde.value = '<%= session.getAttribute("employeeCode") %>';
		temp.empCdeSrch.value = '<%= session.getAttribute("employeeCodeSearch") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';
		temp.empType.value = '<%= session.getAttribute("employeeTypeNameSearch") %>';
		temp.empStat.value = '<%= session.getAttribute("employeeStatusSearch") %>';

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
		
	function listItem()
	{
		temp = document.forms[0];
		if ((temp.empName.value != "" ) || (temp.empSrchTab.value != "") || (temp.empCde.value != "") || (temp.empCdeSrch.value != "") || (temp.viewAll.value != "") || (temp.empType.value != "") || (temp.empStat.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmEmpInfoList.do?formAction=afterView&empName='+temp.empName.value+'&empSrchTab='+temp.empSrchTab.value+'&empCde='+temp.empCde.value+'&empCdeSrch='+temp.empCdeSrch.value+'&viewAll='+temp.viewAll.value+'&empType='+temp.empType.value+'&empStat='+temp.empStat.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Employee/EmployeeList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmEmpEdit.do?formAction=modify&id='+<%= objEmployeeDetails.getEmp_Id() %>;
			document.forms[0].submit();
		}	
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function makeInvalidItem()
	{
			document.forms[0].action = '<bean:message key="context"/>/frmEmpEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmEmpEdit.do?formAction=makeValid';
		temp.submit();
	}
</script>

</head>

<body onLoad="chkMkValid();loadToHidden();">
<html:form action="frmEmpEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="empName"/>
<input type="hidden" name="empSrchTab"/>
<input type="hidden" name="empCde"/>
<input type="hidden" name="empCdeSrch"/>
<input type="hidden" name="viewAll"/>
<input type="hidden" name="empType"/>
<input type="hidden" name="empStat"/>

  <table width="100%" cellpadding="10" cellspacing="0">
  <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
         <tr>
            <td><bean:message key="prodacs.employee.employeeview.header"/></td>
          </tr> 
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Employee Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make EmployeeType Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
	    </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeecode"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Code() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeename"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Typ_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeestatus"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Stat_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.dateofjoin"/></td>
            <td class="FieldTitle">:</td>
            <%
             if (objEmployeeDetails.getEmp_Doj() == null)
             {
             %>
             	<td class="ViewData">&nbsp;</td>
             <%
             }
             else
             {
             %>
            	<td class="ViewData"><%= objEmployeeDetails.getEmp_Doj().toString().substring(0,10) %></td>
            <%
            }
            %>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.dateofbirth"/></td>
            <td class="FieldTitle">:</td>
            <%
            if (objEmployeeDetails.getEmp_Dob() == null)
            {
            %>
            	<td class="ViewData">&nbsp;</td>
             <%
             }
             else
             {
             %>		
            	<td class="ViewData"><%= objEmployeeDetails.getEmp_Dob().toString().substring(0,10) %></td>
            <%
            }
            %>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.bloodgroup"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_BloodGp() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.contactaddress"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Addr1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Addr2() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_City() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_State() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Pcode() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Phone1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Phone2() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.contactname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Cntct_Name() %></td>
          </tr>
          <tr> 
            <td width="200" class="FieldTitle"><bean:message key="prodacs.employee.permanentaddress"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_Addr1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_Addr2() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_City() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_State() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_Pcode() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_Phone1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objEmployeeDetails.getEmp_Permnt_Phone2() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td> 
              <html:button property = "returnEmployeeList" styleClass = "Button" value = "Return to Employee List" onclick = "javascript:listItem()"/></td>
          </tr>
        </table>
	</td>
  </tr>
  </table>
</html:form>
</body>
</html:html>