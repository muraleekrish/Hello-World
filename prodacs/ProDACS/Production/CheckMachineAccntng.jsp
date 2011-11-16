<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.StringTokenizer" %>

<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.MachineNonAccountedDetails"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.CheckMachineAccntngForm" />
<jsp:setProperty name="frm" property="*" /> 

<useradmin:userrights resource="57"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Check Machine Accounting.jsp starts");
	HashMap machCodes = new HashMap();
	MachineNonAccountedDetails[] objMachAccntngDets = new MachineNonAccountedDetails[0];
	double prodHrs = 0;
	double nProdHrs = 0;
	double availHrs = 0;
	try
	{
		EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator

		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionProductionDetailsManagerHome proHomeObj = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
		SessionProductionDetailsManager productionObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionProductionDetailsManager.class);
		
		/* For loading Machine Codes */
		machCodes = productionObj.getAllMachines();
		pageContext.setAttribute("machCodes",machCodes);
	
		
		if ((!frm.getFrmDate().equalsIgnoreCase("")) || (!frm.getToDate().equalsIgnoreCase("")) || (!frm.getMachCode().equalsIgnoreCase("0")))
		{
			if (frm.getFormAction().equalsIgnoreCase("check"))
			{
				StringTokenizer st = new StringTokenizer(frm.getFrmDate().trim(),"-");
				int yr = 0;
				int month = 0;
				int day = 0;
				if(st.hasMoreTokens())
				{
					yr = Integer.parseInt(st.nextToken().trim());
				}
				if(st.hasMoreTokens())
				{
					month = Integer.parseInt(st.nextToken().trim());
				}
				if(st.hasMoreTokens())
				{
					day = Integer.parseInt(st.nextToken().trim());
				}
				GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
			
				StringTokenizer st1 = new StringTokenizer(frm.getToDate().trim(),"-");
				int y = 0;
				int m = 0;
				int d = 0;
				if(st1.hasMoreTokens())
				{
					y = Integer.parseInt(st1.nextToken().trim());
				}
				if(st1.hasMoreTokens())
				{
					m = Integer.parseInt(st1.nextToken().trim());
				}
				if(st1.hasMoreTokens())
				{
					d = Integer.parseInt(st1.nextToken().trim());
				}
				GregorianCalendar ge1 = new GregorianCalendar(y,m-1,d);
			
				objMachAccntngDets = productionObj.checkMachineAccounting(ge.getTime(),ge1.getTime(),frm.getMachCode());
				pageContext.setAttribute("objMachAccntngDets",objMachAccntngDets);
				
				prodHrs = productionObj.fetchProductionEnteredHrs(ge.getTime(),ge1.getTime(),frm.getMachCode());
				
				nProdHrs = productionObj.fetchNonProductionEnteredHrs(ge.getTime(),ge1.getTime(),frm.getMachCode());
				
				availHrs = productionObj.calculateMachineAvailHrs(ge.getTime(),ge1.getTime());
			}
		}
		else
		{
			pageContext.setAttribute("objMachAccntngDets",objMachAccntngDets);
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
<title><bean:message key="prodacs.productionfinal.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_search.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }

	 function showAccntng()
	 {
		 var temp = document.forms[0];
		 if ((temp.frmDate.value == "") || (temp.toDate.value == "") || (temp.machCode.value == "0"))
		 {
			 alert("Check the values before proceeding!");
			 return false;
		 }
		 else
		 {
			temp.formAction.value = "check";
			temp.submit();
		 }
	 }

	 function chkData()
	 {
		var temp = document.forms[0];
		<%
			if (objMachAccntngDets.length == 0)
			{
		%>
				showMachDets.style.display = 'none';
				temp.totNonAccHrs.value = 0.0;
				temp.totAccntdHrs.value = 0.0;

		<%
			}
			else
			{
		%>
				showMachDets.style.display = 'block';
				var nonAccData = 0.0;
				var obj = document.getElementById("machData");
				for(var i = 0; i < obj.children(0).children.length; i++)
				{
					nonAccData += parseFloat(obj.children(0).children(i).children(4).children(0).value);
				}
				temp.totNonAccHrs.value = nonAccData;
				temp.totAccntdHrs.value = (parseFloat(temp.totNonAccHrs.value)+parseFloat(temp.prodEnteredHrs.value)+parseFloat(temp.nonProdEnteredHrs.value));
		<%
			}
		%>
		if (temp.formAction.value == "check")
		{
			showAccntsHeader.style.display = 'block';
		}
	 }
</script>
</head>
<body onload="init(); chkData();">
<html:form action="frmCheckMachAccntng">
<html:hidden property="formAction"/>
<table width="100%"  border="0" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" id="PageTitle">
    <tr>
    	<td>Production Accounting Details</td>
	</tr>
    </table>
	<table>
	<tr>
		<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
	</tr>
	</table>
	<br>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.fromdate"/><span class="mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td width="200" class="FieldTitle"><html:text property="frmDate" styleClass="TextBox" size="12" readonly="true"/>
					<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("frmDate",ProductionAccntng.frmDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
		<td width="100" class="FieldTitle"><bean:message key="prodacs.workcalendar.availabilitycalendar.todate"/><span class="mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td width="180" class="FieldTitle"><html:text property="toDate" styleClass="TextBox" size="12" readonly="true"/>
					<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("toDate",ProductionAccntng.toDate.value,event.clientX,event.clientY,false);' onMouseOver="this.style.cursor='hand'"></td>
		<td width="150" class="FieldTitle"><bean:message key="prodacs.production.machinecode"/><span class="mandatory">*</span></td>
		<td width="1" class="FieldTitle">:</td>
		<td colspan="3" class="FieldTitle"><html:select property="machCode" styleClass="Combo">
											   <html:option value="0">-- Select Machine --</html:option>
											   <html:options collection="machCodes" property="key" labelProperty="key"/>
										   </html:select></td>
		</tr>
		</table>
		<br>
		<table width="100%" cellspacing="0" cellpadding="0" id="BtnBgLft">
		<tr>
			<td><html:button styleClass="Button" property="proNextBtn1" onclick="showAccntng();">Check</html:button></td>
		</tr>
		</table>
		<br>

		<table width="100%" cellspacing="0" cellpadding="0" id="showMachDets" style="display:none">
		<tr>
		<td>
			<fieldset id="FieldSet"><legend class="FieldTitle">Pending Production Details</legend><br>
			<table width="100%" cellspacing="0" cellpadding="3">
				<tr>
					<td width="80" class="Header" rowspan="2"><bean:message key="prodacs.job.date"/></td>
					<td width="80" class="Header" rowspan="2"><bean:message key="prodacs.production.shift"/></td>
					<td class="Header" colspan="2"><bean:message key="prodacs.postaccounting.accountedhours"/></td>
					<td width="120" class="Header" rowspan="2"><bean:message key="prodacs.postaccounting.nonaccountedhours"/></td>
				</tr>
				<tr>
					<td class="Header" align="center">Prod Hrs</td>
					<td class="Header" align="center">Non Prod Hrs</td>
				</tr>
				</table>
				<table width="100%" cellspacing="0" cellpadding="3" id="machData">
				<logic:iterate id="bt1" name="objMachAccntngDets" indexId="count">
				<tr>
				<%
					if(count.intValue()%2 == 0)
					{
				%>
					<td width="80" class="TableItems"><bean:define id="date1" name="bt1" property="prodDate"/>
														<% 
															Date d =  (Date)date1;
															GregorianCalendar gc = new GregorianCalendar();
															gc.setTime(d);
															String date = gc.get(GregorianCalendar.YEAR)+"-"+((gc.get(GregorianCalendar.MONTH))+1)+"-"+gc.get(GregorianCalendar.DATE);
															%><input type="text" readonly class="TextBoxFull" value='<%=date%>'/>
					</td>
					<td width="80" class="TableItems"><input type="text" class="TextBoxFull" readonly value='<bean:write name="bt1" property="shiftName"/>'/></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly value='<bean:write name="bt1" property="prodHrs"/>'/></td>
					<td class="TableItems"><input type="text" class="TextBoxFull" readonly value='<bean:write name="bt1" property="nonProdHrs"/>'/></td>
					<td width="120" class="TableItems"><input type="text" readonly class="TextBoxFull" value='<bean:write name="bt1" property="nonAccuntedHrs"/>'/></td>
				<%
					}
					else
					{
				%>
					<td width="80" class="TableItems2"><bean:define id="date2" name="bt1" property="prodDate"/>
														<% 
															Date d =  (Date)date2;
															GregorianCalendar gc = new GregorianCalendar();
															gc.setTime(d);
															String date = gc.get(GregorianCalendar.YEAR)+"-"+((gc.get(GregorianCalendar.MONTH))+1)+"-"+gc.get(GregorianCalendar.DATE);
															%><input type="text" readonly class="TextBoxFull" value='<%=date%>'/>
					</td>
					<td width="80" class="TableItems2"><input type="text" readonly class="TextBoxFull" value='<bean:write name="bt1" property="shiftName"/>'/></td>
					<td width="167" class="TableItems2"><input type="text" class="TextBoxFull" readonly value='<bean:write name="bt1" property="prodHrs"/>'/></td>
					<td class="TableItems2"><input type="text" class="TextBoxFull" readonly value='<bean:write name="bt1" property="nonProdHrs"/>'/></td>
					<td width="120" class="TableItems2"><input type="text" readonly class="TextBoxFull" value='<bean:write name="bt1" property="nonAccuntedHrs"/>'/></td>
				<%
					}
				%>
				</tr>
				</logic:iterate>
			</table>
			</fieldset>
		</td>
		</tr>
		</table>
			<br><br>
			<table width="100%" cellspacing="0" cellpadding="3" id="showAccntsHeader" style="display:none">
			<tr>
			<td>
			<fieldset id="FieldSet"><legend class="FieldTitle">Production Accounting Details</legend><br>
			<table width="100%" cellspacing="0" cellpadding="3">
				<tr class="TableItems2">
					<td width="180">&nbsp;</td>
					<td class="FieldTitle" colspan="2">Total Non Accounted Hours</td>
					<td class="FieldTitle" width="1">:</td>
					<td width="120" class="FieldTitle"><input type="text" name="totNonAccHrs" class="TextBoxFull1" readonly/></td>
				</tr>
				<tr class="TableItems">
					<td width="180">&nbsp;</td>
					<td class="FieldTitle" colspan="2">Production Entered Hours</td>
					<td class="FieldTitle" width="1">:</td>
					<td width="120" class="FieldData"><input type="text" name="prodEnteredHrs" class="TextBoxFull1" readonly value="<%= prodHrs %>"/></td>
				</tr>
				<tr class="TableItems2">
					<td width="180">&nbsp;</td>
					<td class="FieldTitle" colspan="2">Non-Production Entered Hours</td>
					<td class="FieldTitle" width="1">:</td>
					<td width="120" class="FieldData"><input type="text" name="nonProdEnteredHrs" class="TextBoxFull1" readonly value="<%= nProdHrs %>"/></td>
				</tr>
				<tr class="TableItems">
					<td width="180">&nbsp;</td>
					<td class="FieldTitle" colspan="2">Total Accounted Hours</td>
					<td class="FieldTitle" width="1">:</td>
					<td width="120" class="FieldData"><input type="text" name="totAccntdHrs" class="TextBoxFull1" readonly/></td>
				</tr>
				<tr class="TableItems2">
					<td width="180">&nbsp;</td>
					<td class="FieldTitle" colspan="2">Availble Machine Hours</td>
					<td class="FieldTitle" width="1">:</td>
					<td width="120" class="FieldData"><input type="text" name="availMachHrs" class="TextBoxFull1" readonly value="<%= availHrs %>"/></td>
				</tr>
			</table>
			</fieldset>
			</td>
			</tr>
			</table>
</html:form>
</body>
</html:html>		
