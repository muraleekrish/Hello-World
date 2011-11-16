<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails" %>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingShiftDetails" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.ProductionAcctngDetailsForm" />
<jsp:setProperty name="frm" property="*" /> 
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Accounting Details...");
	ProductionAccountingMachineDetails objProdAccntngMachDets = new ProductionAccountingMachineDetails();
	ProductionAccountingDateDetails objProdAccntngDateDets = new ProductionAccountingDateDetails();
	String date = (String)request.getAttribute("date");
	if (BuildConfig.DMODE)
		System.out.println("date :"+date);
	Vector vec_prodnAcctDets = new Vector();
	try
	{
		vec_prodnAcctDets = (Vector) request.getAttribute("prodAccntngDets");
		pageContext.setAttribute("vec_prodnAcctDets",vec_prodnAcctDets);
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in ProductionAcctngDetails.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<!--title><bean:message key="prodacs.customer.customerlist.titleheader"/></title-->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
	function backToPage()
	{
		var temp = document.forms[0];
		temp.submit();
	}

	function checkActions()
	{
		var temp = document.forms[0];
		temp.formAction.value = '<%= request.getAttribute("formAction") %>';
		if (temp.formAction.value == "viewAccountings")
		{
			temp.backToAdd.value = "Back To Production Add";
			temp.formAction.value = "prodAdd";
		}
		else if (temp.formAction.value == "rdlAccountings")
		{
			temp.backToAdd.value = "Back To Radial Production Add";
			temp.formAction.value = "rdlProdAdd";
		}
		if (temp.formAction.value == "nProdAccountings")
		{
			temp.backToAdd.value = "Back To Non-Production Add";
			temp.formAction.value = "nonProdAdd";
		}
		else if (temp.formAction.value == "popAccountings")
		{
			temp.backToAdd.value = "Back To PayOutside Production Add";
			temp.formAction.value = "popAdd";
		}
	}

	function printPage()
	{
		printPreview.style.display='none';
		backPage.style.display='none';
		window.print();
		printPreview.style.display='block';
		backPage.style.display='block';
	}
</script>
<body onLoad="checkActions();">
<html:form action="frmProdAcc">
<html:hidden property="formAction"/>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
        <tr>
          <td><bean:message key="prodacs.postaccounting.header"/></td>
        </tr>
      </table>
      <br><br>
	  <table width="100" cellpadding="0" cellspacing="0" align="right" id="printPreview">
	  <tr>
		<td class="TopLnk"><a href="#" onclick="javascript:printPage();" onMouseOver="window.status='Print Current Page'; return true" onMouseOut="window.status=''; return true">[ Print ]</a></td>
	  </tr>
	  </table>
	  <br>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
		<td class="FieldTitle">To proceed, you must fill the available hours for the following machines...</td>
	</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
	<td class="FieldTitle">
		<fieldset id="FieldSet"><legend>&nbsp;<%= date %>&nbsp;</legend>
		<%
			for (int i = 0; i < vec_prodnAcctDets.size(); i++)
			{
				objProdAccntngMachDets = (ProductionAccountingMachineDetails)vec_prodnAcctDets.get(i);
				Vector vec_sftDets = (Vector)objProdAccntngMachDets.getVecProductionAccountingShiftDetails();
		%>
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.machine.machinecode"/> :&nbsp;<%= objProdAccntngMachDets.getMcCode()%>&nbsp;</legend><br>
			&nbsp;<bean:message key="prodacs.machine.machinename"/> :&nbsp;<%= objProdAccntngMachDets.getMcName()%>&nbsp;
			<br><br>
			<table width="100%" cellspacing="0" cellpadding="1">
			<tr>
				<td>&nbsp;</td>
				<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.postaccounting.accountedhours"/></td>
				<td colspan="2" align="center" class="PageSubTitle" style="border-right:solid 1px #FFFFFF"><bean:message key="prodacs.postaccounting.nonaccountedhours"/></td>
			</tr>
			<tr>
				<td class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
				<td width="120" class="Header"><bean:message key="prodacs.postaccounting.prodhours"/></td>
				<td width="160" class="Header"><bean:message key="prodacs.postaccounting.nonprodhours"/></td>
				<td width="50" class="Header">&nbsp;</td>
			</tr>
			<%
				for (int j = 0; j < vec_sftDets.size(); j++)
				{
					ProductionAccountingShiftDetails objProdAcctngSftDets = (ProductionAccountingShiftDetails) vec_sftDets.get(j);

			%>			
			<tr>
				<td class="TableItems"><%= objProdAcctngSftDets.getShiftName() %>&nbsp;</td>
				<td class="TableItems"><%= objProdAcctngSftDets.getProdHrs() %>&nbsp;</td>
				<td class="TableItems"><%= objProdAcctngSftDets.getNprodHrs() %>&nbsp;</td>
				<td class="TableItems"><%= objProdAcctngSftDets.getAvailHrs() %>&nbsp;</td>
			</tr>
		<%
				}
		%>
			</table>
			<br><br>
			</fieldset>
		<%
			}					
		%>
		<br>
		</fieldset>
	</td>
	</tr>
	</table>
	
	<br>
	<table width="100%" cellspacing="0" cellpadding="0" id="backPage">
	<tr>
		<td>
			<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
			<tr>
				<td><html:button property="backToAdd" styleClass="Button" onclick="javaScript:backToPage();"/></td>
			</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
</body>
</html:html>
