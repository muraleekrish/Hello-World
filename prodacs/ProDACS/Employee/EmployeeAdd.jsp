<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionEmpDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1004"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Employee Add");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionEmpDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionEmpDetailsManagerHome empHomeObj = (SessionEmpDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionEmpDetailsManagerHome.class);
		SessionEmpDetailsManager empObj = (SessionEmpDetailsManager)PortableRemoteObject.narrow(empHomeObj.create(),SessionEmpDetailsManager.class);
		
/*		For Employee Type */
		HashMap employeeType = empObj.getAllEmployeeTypes();
		pageContext.setAttribute("employeeType",employeeType);

/*		For Employee Status */
		HashMap employeeStatus = empObj.getAllEmployeeStatus();
		pageContext.setAttribute("employeeStatus",employeeStatus);
		
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
	
%>		

<html:html>
<head>
<title><bean:message key="prodacs.employee.employeeadd.titleheader"/></title>
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
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeAdd.jsp';
		document.forms[0].submit();
	}

	function newGeneralName()
	{
		var features = "toolbars=no,status=no,resizable=no,height=350px,width=350px,top=100px,left=600px";
		window.open('<bean:message key="context"/>/Employee/NewStatusName.jsp?',"NewStatusName",features);
	}

	function typeValue(val1,val2)
	{
		var temp = document.forms[0];
		//alert(val1+"*"+val2);
		temp.employeeStatus.options[temp.employeeStatus.options.length] = new Option(val2,val1,false,true);
		/*document.forms[0].action = '<bean:message key="context"/>/Employee/EmployeeAdd.jsp';
		document.forms[0].submit();
		location.reload(); */
	}

	function isNumber(formObj)
	{
		if ((event.keyCode > 47) && (event.keyCode < 58) || (event.keyCode == 45))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	function checkLength(elmnt,content)
	{
		if (content.length==elmnt.maxLength)
		{
			next=elmnt.tabIndex;
			if (next<document.forms[0].elements.length)
			{
				document.forms[0].elements[next].focus();
			}
		}
	}

	function moveToHidden()
	{
		temp = document.forms[0];
		
		if (temp.dojYear.value != "" && temp.dojMonth.value != "" && temp.dojDay.value != "")
		{
			if ((temp.dojYear.value).length != 4 || ((temp.dojMonth.value).length != 2 || temp.dojMonth.value > 12) || (temp.dojDay.value).length != 2)
			{
				alert("Date not in specified format!");
				return false;
			}
			else
			{
				temp.employeeDOJ.value = temp.dojYear.value+"-"+temp.dojMonth.value+"-"+temp.dojDay.value;
				//alert(temp.employeeDOJ.value);
			}
		}	
		if (temp.dobYear.value != "" && temp.dobMonth.value != "" && temp.dobDay.value != "")
		{
			if ((temp.dobYear.value).length != 4 || ((temp.dobMonth.value).length != 2 || temp.dobMonth.value > 12) || (temp.dojDay.value).length != 2)
			{
				alert("Date not in specified format!");
				return false;
			}
			else
			{
				temp.employeeDOB.value = temp.dobYear.value+"-"+temp.dobMonth.value+"-"+temp.dobDay.value;
				//alert(temp.employeeDOB.value);
			}
		}
		dob = new Date();
		doj = new Date();
		dob = temp.employeeDOB.value;
		doj = temp.employeeDOJ.value;
		if (dob != "" || doj != "")
		{		
			if (dob >= doj)
			{
				alert("Date of Birth cannot be higher than Date of Joining");
				temp.dojYear.focus();
				return false;
			}
		}
		/*else
		{
			return false;
		}*/
		if (document.forms[0].employeeInService.checked)
			document.forms[0].employeeInService.value = "1";
		else
			document.forms[0].employeeInService.value = "";
		document.forms[0].submit();

	}
</script>
</head>

<body onLoad="init()">
<html:form action="frmEmpAdd" focus="employeeCode">
<html:hidden property="employeeDOJ"/>
<html:hidden property="employeeDOB"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.employee.employeeadd.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1004" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Employee Info'; return true"  onMouseOut="window.status=''; return true" resourceId="4" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
          </tr>
        </table>
         <table>
		   <tr> 
			 <td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		    </td>
		   </tr> 
		 	</table> 
        
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.employee.employeecode"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="employeeCode" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.employee.employeename"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="employeeName" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeetype"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="employeeType" styleClass="Combo">
                <html:option value="0">-- Choose Type --</html:option>
  					 <html:options collection="employeeType" property="key" labelProperty="value"/>
              </html:select></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeestatus"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="employeeStatus" styleClass="Combo">
                <html:option value="0">-- Choose Status --</html:option>
                <html:options collection="employeeStatus" property="key" labelProperty="value"/>
              </html:select>&nbsp;<span class="TopLnk"><a href="#" onclick="newGeneralName();">New</a></span></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.employeeinservice"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:checkbox property="employeeInService" value="1"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.dateofjoin"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><input name="dojYear" class="TextBox" size="4" tabindex="7" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value); return isNumber(this);" maxlength="4"/> -
				<input name="dojMonth" class="TextBox" size="2" tabIndex="8" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value);return isNumber(this);" maxlength="2"/> -
				<input name="dojDay" class="TextBox" size="2" tabIndex="9" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value);return isNumber(this);" maxlength="2"/>
			<font size="-5px"><bean:message key="prodacs.common.dateformat"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.dateofbirth"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><input name="dobYear" class="TextBox" size="4" tabindex="10" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value);return isNumber(this);" maxlength="4"/> -
				<input name="dobMonth" class="TextBox" size="2" tabIndex="11" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value);return isNumber(this);" maxlength="2"/> -
				<input name="dobDay" class="TextBox" size="2" tabIndex="12" onkeypress="return isNumber(this);" onkeyup="checkLength(this,this.value);return isNumber(this);" maxlength="2"/>
			<font size="-5px"><bean:message key="prodacs.common.dateformat"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.bloodgroup"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="bloodGroup" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.contactaddress"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="address1" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle"><html:text property="address2" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="city" styleClass="TextBox" size="25" maxlength="25" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="state" styleClass="TextBox" size="25" maxlength="25" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="pincode" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phone1" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phone2" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.contactname"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="contactName" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.employee.permanentaddress"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="address3" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle"><html:text property="address4" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="city1" styleClass="TextBox" size="25" maxlength="25" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="state1" styleClass="TextBox" size="25" maxlength="25" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.common.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="pincode1" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phone3" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phone4" styleClass="TextBox" size="10" maxlength="10" /></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="addEmployee" styleClass="Button" onclick="moveToHidden();" value="Add Employee" />
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
