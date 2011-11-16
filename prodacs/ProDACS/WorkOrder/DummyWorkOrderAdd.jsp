<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.DummyWorkOrderAddForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<useradmin:userrights resource="1014"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Dmy Wo Starts...");
	EJBLocator obj = new EJBLocator();
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkOrderDetailsManagerBean");
		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
		/* Loading Customer Type */
		HashMap customerTypeList = workOrderObj.getCustomerTypes();
		pageContext.setAttribute("customerTypeList",customerTypeList);
		
		/* Loading Customer Name */
		HashMap customerNameList = new HashMap();
		if (!frm.getDyCustomerType().equalsIgnoreCase("0"))
		{
			customerNameList = workOrderObj.getCustomerNameByType(Integer.parseInt(frm.getDyCustomerType()));
			pageContext.setAttribute("customerNameList",customerNameList);
		}
		else
		{
			pageContext.setAttribute("customerNameList",customerNameList);
		}
		
		/* Loading General Name */
		HashMap generalNameList = workOrderObj.getGeneralNameByCustomer(Integer.parseInt(frm.getDyCustomerName()));
		pageContext.setAttribute("generalNameList",generalNameList);
		
		/* Loading Operation Group Code */
		HashMap opnGrpCdeList = workOrderObj.getAllOperationGroupCodes();
		pageContext.setAttribute("opnGrpCdeList",opnGrpCdeList);
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Error in DmyWoAdd.jsp "+ e.toString());
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.dummyworkorder.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script language="Javascript" type="text/Javascript">
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

function listItem()
{
	document.forms[0].action = '<bean:message key="context"/>/WorkOrder/DummyWorkOrderList.jsp';
	document.forms[0].submit();
}
	
	function addItem()
	{
		var temp = document.forms[0];
		temp.formAction.value = "add";
		temp.submit();
	}
		
	function loadCustomerNameList()
	{
		var temp = document.forms[0];
		if (temp.dyCustomerType.value != "0")
		{
			temp.formAction.value = "load";
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

	function newGeneralName()
	{
		var features = "toolbars=no,status=no,resizable=no,height=350px,width=350px,top=100px,left=600px";
		window.open('<bean:message key="context"/>/JobMaster/NewGeneralName.jsp?',"NewGeneralName",features);
	}

	function typeValue(val1,val2)
	{
		var temp = document.forms[0];
		temp.dyGeneralName.options[temp.dyGeneralName.options.length] = new Option(val2,val1,false,true);
		/*document.forms[0].action = '<bean:message key="context"/>/WorkOrder/DummyWorkOrderAdd.jsp';
		document.forms[0].submit();
		location.reload(); */
	}
	
	function dmyWrkOrderAdd()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/DummyWorkOrderAdd.jsp';
		document.forms[0].submit();
	}

	function loadGeneralNameList()
	{
		var temp = document.forms[0];
		if (temp.dyCustomerName.value != "0")
		{
			temp.formAction.value = "load";
			temp.submit();
		}
	}

</script>
</head>

<body onload="init();">
<html:form action="frmDumWrkOrdAdd" focus="dyWOHash">
<html:hidden property="formAction"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
      <tr>
      	<td><bean:message key="prodacs.dummyworkorder.header"/></td>
	</tr>
      </table>
      <br>
      <table width="100" cellspacing="0" cellpadding="0" align="right">
      <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New DummyWorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1014" text="[ Add ]" classId="TopLnk" onClick="javaScript:dmyWrkOrderAdd();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List DummyWorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="14" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
	</tr>
      </table>
      <table>
		<tr><td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td></tr>
	</table>
    <br>
	<table cellspacing="0" cellpadding="0">
	<tr> 
		<td width="100" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/><span class="Mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td class="FieldTitle"><input type="text" name="dummy" readonly class="TextBox" value="DMY" size="3"/>
			<html:text property="dyWOHash" styleClass="TextBox" size="10" maxlength="7"/></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle">
			<html:select property="dyCustomerType" styleClass="Combo" onchange="loadCustomerNameList();">
				<html:option value="0">-- CustomerType --</html:option>
				<html:options collection="customerTypeList" property="key" labelProperty="value"/>
			</html:select></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.customer.customername"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle">
			<html:select property="dyCustomerName" styleClass="Combo" onchange="loadGeneralNameList();">
				<html:option value="0">-- CustomerName --</html:option>
				<html:options collection="customerNameList" property="key" labelProperty="value"/>
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.job.generalname"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle">
			<html:select property="dyGeneralName" styleClass="Combo">
				<html:option value="0">-- GeneralName --</html:option>
				<html:options collection="generalNameList" property="key" labelProperty="value"/>
			</html:select>&nbsp;<span class="TopLnk"><a href="#" onclick="newGeneralName();">New</a></span></td>
		<!--/td-->
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.job.jobname"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyJobName" styleClass="TextBox" size="30" maxlength="50"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.job.drawing"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyDrawingHash" styleClass="TextBox" size="10" maxlength="15"/></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.job.revision"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyRevisionHash" styleClass="TextBox" size="10" maxlength="5"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyMaterialType" styleClass="TextBox" size="15" maxlength="10"/></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.dummyworkorder.startsno"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyStartSno" styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" size="10" maxlength="10"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.production.totalqty"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyTotQty" styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" size="10" maxlength="10"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.shipment.partyorderno"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyDcNo" styleClass="TextBox" size="10" maxlength="10"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.shipment.partyorderdate"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyDcDate" styleClass="TextBox" size="10" maxlength="10"/>&nbsp;<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("dyDcDate",DummyWorkOrderAdd.dyDcDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.operationgroup.operationgroupcode"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle">
			<html:select property="dyOpnGrpCode" styleClass="Combo">
				<html:option value="0">-- OpnGrpCode --</html:option>
				<html:options collection="opnGrpCdeList" property="key" labelProperty="value"/>
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.job.operationname"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyOpnName" styleClass="TextBox" size="25" maxlength="50"/></td>
	</tr>
	<tr> 
		<td class="FieldTitle"><bean:message key="prodacs.production.startoperation"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyStartOpn" styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" size="10" maxlength="10"/></td>
	</tr>
	<tr>
		<td class="FieldTitle"><bean:message key="prodacs.production.endoperation"/><span class="Mandatory">*</span></td>
		<td class="FieldTitle">:</td>
		<td class="FieldTitle"><html:text property="dyEndOpn" styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" size="10" maxlength="10"/></td>
	</tr>
	</table><br>
      <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
      <tr>
      	<td><html:button property="createDummyWO" styleClass="Button" onclick="javascript:addItem()"> Add </html:button></td>
	</tr>
      </table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
