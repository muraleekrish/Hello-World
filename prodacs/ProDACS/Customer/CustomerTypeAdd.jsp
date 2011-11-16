<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<useradmin:userrights resource="1001"/>
<html:html>
<head>
<title><bean:message key="prodacs.customer.customeradd.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script language = "Javascript" type = "text/Javascript">
	function listCustomerType()
	{
		document.forms[0].action = '<bean:message key="context"/>/Customer/CustomerTypeList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Customer/CustomerTypeAdd.jsp';
		document.forms[0].submit();
	}
	function submitItem()
	{
		document.forms[0].submit();
	}
</script>
</head>

<body>
<html:form action="frmCustomerTypeAdd" focus="customerTypeName">
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.customer.customertypeadd.titleheader"/></td>
          </tr>
        </table>
        <br> <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New CustomerType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List CustomerType Info'; return true"  onMouseOut="window.status=''; return true" resourceId="2" text="[ List ]" classId="TopLnk" onClick="javaScript:listCustomerType();"/>
          </tr>
        </table>
         <table>
		   <tr> 
			 <td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		    </td>
		   </tr> 
		 	</table> 
        
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="120" class="FieldTitle"><bean:message key="prodacs.customer.customertypename"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="customerTypeName" styleClass="TextBox" size="50" maxlength="50"/></td>
          </tr>
          <tr> 
            <td valign="top" class="FieldTitle"><bean:message key="prodacs.employee.description"/></td>
            <td valign="top" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:textarea property="description" cols="50" rows="5" styleClass="TextBox" maxlength="250"/></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="addCustomerType" styleClass="Button" onclick="submitItem()">Add Customer Type</html:button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>