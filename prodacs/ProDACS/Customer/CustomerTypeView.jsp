<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.businessimplementation.customer.CustomerTypDetails" %>
<useradmin:userrights resource="1"/>
<%
	EJBLocator obj = new EJBLocator();
	CustomerTypDetails custTypDetObj = null;
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionCustomerDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionCustomerDetailsManagerHome custHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
		SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(custHomeObj.create(),SessionCustomerDetailsManager.class);
		
		custTypDetObj = custObj.getCustomerTypeDetails(Integer.parseInt(id));
	}catch (Exception e)
	{
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.customer.customertypeview.titleheader"/></title>
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
	var isValid = <%= custTypDetObj.getCust_Typ_IsValid()%>;
	
	function chkMkValid()
	{
		temp = document.forms[0];
		temp.custTypName.value = '<%= session.getAttribute("searchText") %>';
		temp.custCombo.value = '<%= session.getAttribute("custType") %>';
		temp.viewAll.value = '<%= session.getAttribute("viewValidEntries") %>';

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
	
	function listCustomerType()
	{
		temp = document.forms[0];
		if ((temp.custTypName.value != "" ) || (temp.custCombo.value != "") || (temp.viewAll.value != ""))
		{
			var custTypName = temp.custTypName.value;
			var custCombo = temp.custCombo.value;
			var viewAll = temp.viewAll.value;
			temp.action = '<bean:message key="context"/>/frmCustomerTypeList.do?formAction=afterView&custTypName='+temp.custTypName.value+'&custCombo='+temp.custCombo.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Customer/CustomerTypeList.jsp';
			temp.submit();
		}
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Customer/CustomerTypeAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmCustomerTypeEdit.do?formAction=modify&id='+<%= custTypDetObj.getCust_Typ_Id()%>;
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
			document.forms[0].action = '<bean:message key="context"/>/frmCustomerTypeEdit.do?formAction=makeInvalid';
			document.forms[0].submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmCustomerTypeEdit.do?formAction=makeValid';
		temp.submit();
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
</script>
</head>

<body onLoad="init();loadToHidden();chkMkValid()">
<html:form action="frmCustomerTypeEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="custTypName"/>
<input type="hidden" name="custCombo"/>
<input type="hidden" name="viewAll"/>

  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.customer.customertypeview.titleheader"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New CustomerType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1001" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify CustomerType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1001" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>          
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make CustomerType Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1001" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make CustomerType Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1001" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
        </tr>
        </table>
        <br>
        <table cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= custTypDetObj.getCust_Typ_Name() %></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="ViewData"><%= custTypDetObj.getCust_Typ_Desc() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td>
            <html:button property="returnCustomerTypeList" styleClass="Button" onclick="javascript:listCustomerType()">Return to Customer Type List</html:button>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>