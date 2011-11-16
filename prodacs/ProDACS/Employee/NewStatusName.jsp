<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>

<html:html>
<head>
<title><bean:message key="prodacs.customer.customeradd.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	function closeItem()
	{
		window.close();
	}
	
	function submitItem()
	{
		document.forms[0].submit();
	}
	
	function loadTable()
	{
		var temp = document.forms[0];
		//alert(temp.id.value);
		if (temp.id.value != 0)
		{
			window.close();
			top.opener.typeValue(temp.id.value,temp.employeeStatus.value);
		}
		else
		{
			window.focus();
			temp.employeeStatus.value = "";
			temp.employeeStatus.focus();
		}
	}
</script>
</head>

<body onload="loadTable();">
<html:form action="frmNewStatusAdd" focus="employeeStatus">
<html:hidden property="id"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.employee.statusadd"/></td>
          </tr>
        </table>
        <br> 
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
            <td class="TopLnk"><a href="#" onclick="javascript:closeItem()" onMouseOver="window.status='Close this Window'; return true" onMouseOut="window.status=''; return true">Close</a></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="150" class="FieldTitle"><bean:message key="prodacs.employee.employeestatus"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="employeeStatus" styleClass="TextBox" size="20" maxlength="50" /></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="addGeneralName" styleClass="Button" onclick="submitItem()">Add</html:button>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
