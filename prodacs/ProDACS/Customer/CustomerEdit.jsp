<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManager"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1002"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Customer Edit");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionCustomerDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionCustomerDetailsManagerHome customerHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
		SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(customerHomeObj.create(),SessionCustomerDetailsManager.class);
		
		HashMap custDetails = custObj.getCustomerTypes();
		
		pageContext.setAttribute("custDetails",custDetails);
		
	}catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
	
%>		

<html:html>
<head>
<title><bean:message key="prodacs.customer.customeredit.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language = "Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Customer/CustomerList.jsp';
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
		temp.action = '<bean:message key="context"/>/frmCustomerEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
</script>
</head>

<body onload="loadToHidden();">
<html:form action="frmCustomerEdit" focus="customerType"> 
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.customer.customeredit.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Customer Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Customer Info'; return true"  onMouseOut="window.status=''; return true" resourceId="2" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>          
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Customer Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Customer Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
          </tr>
        </table><br>
        <table>
		  <tr> 
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		   </td>
		  </tr> 
		 </table> 
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="150" class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="100" class="FieldTitle"><html:text property="customerName" styleClass="TextBox" size="50" readonly="true" /></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="customerType" styleClass="Combo">
                <html:option value="0">-- Choose Type --</html:option>
  					 <html:options collection="custDetails" property="key" labelProperty="value"/>
              </html:select></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customerinservice"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:checkbox property="customerInService" value="1"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="address1" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle"><html:text property="address2" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="city" styleClass="TextBox" size="25" maxlength="25"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="state" styleClass="TextBox" size="25" maxlength="25"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="pincode" styleClass="TextBox" size="10" maxlength="10"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.country"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="country" styleClass="TextBox" size="25" maxlength="25"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.contactfirstname"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="firstName" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.contactlastname"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="lastName" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.designation"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="designation" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phoneLine1" styleClass="TextBox" size="15" maxlength="15"/></td>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="extension1" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phoneLine2" styleClass="TextBox" size="15" maxlength="15"/></td>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="extension2" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline3"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="phoneLine3" styleClass="TextBox" size="15" maxlength="15"/></td>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="extension3" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.fax"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="fax" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.mobile"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="mobile" styleClass="TextBox" size="15" maxlength="15"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.email"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="email" styleClass="TextBox" size="25" maxlength="100"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.website"/></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="website" styleClass="TextBox" size="25" maxlength="100"/></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="modifyCustomer" styleClass="Button"  onclick="javascript:updateItem()">Modify Customer Info</html:button>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>