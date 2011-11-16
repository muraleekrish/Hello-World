<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionPayrollDetailsManager"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>

<%
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap hmCrntPayRoll = new HashMap();
	String key = "";
	String value = "";
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionPayrollDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionPayrollDetailsManagerHome objPayrollDetailsHome = (SessionPayrollDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionPayrollDetailsManagerHome.class);
		SessionPayrollDetailsManager objPayrollDetails = (SessionPayrollDetailsManager) PortableRemoteObject.narrow(objPayrollDetailsHome.create(),SessionPayrollDetailsManager.class);
		
		hmCrntPayRoll = objPayrollDetails.getCurrentPayrollCycle();
		if (BuildConfig.DMODE)
			System.out.println("hmCrntPayRoll: "+hmCrntPayRoll);
		
		for (int i = 0; i < hmCrntPayRoll.size(); i++)
		{
			key = hmCrntPayRoll.keySet().iterator().next().toString();
			value = hmCrntPayRoll.get(key).toString();
			if (BuildConfig.DMODE)
				System.out.println("--> "+ key + " & "+ value);
		}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
%>
<useradmin:userrights resource="22"/>
<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script>
	function loadPreviousCycle()
	{
		var temp = document.forms[0];
		if ('<%= key %>' == 'Day')
		{
			temp.cycleType.selectedIndex = 1;
			chkPayCycle(1);
		}
		if ('<%= key %>' == 'Week')
		{
			temp.cycleType.selectedIndex = 2;
			temp.week.selectedIndex = '<%= value %>';
			chkPayCycle(2);
		}
		if ('<%= key %>' == 'By Week')
		{
			temp.cycleType.selectedIndex = 3;
			var byweek = '<%= value %>'.split('-');
			temp.byWeek.selectedIndex = byweek[0];
			temp.byWeekSelect.selectedIndex = byweek[1];
			chkPayCycle(3);
		}
		if ('<%= key %>' == 'Month')
		{
			temp.cycleType.selectedIndex = 4;
			temp.month.selectedIndex = '<%= value %>';
			chkPayCycle(4);
		}
		if ('<%= key %>' == 'FortNight')
		{
			temp.cycleType.selectedIndex = 5;
			var fortNight = '<%= value %>'.split('-');
			temp.fortNight.selectedIndex = fortNight[0];
			temp.byFortNight.selectedIndex = fortNight[1];
			chkPayCycle(5);
		}
	}
function chkPayCycle(val)
	{
		if(val == "0")
			{
				week.style.display = "none";
				byWeek.style.display = "none";
				rowMonth.style.display = "none";
				fortNight.style.display = "none";
			}
		if(val == "1")
			{
				week.style.display = "none";
				byWeek.style.display = "none";
				rowMonth.style.display = "none";
				fortNight.style.display = "none";
			}
		if(val == "2")
			{
				week.style.display = "block";
				byWeek.style.display = "none";
				rowMonth.style.display = "none";
				fortNight.style.display = "none";
			}
		if(val == "3")
			{
				week.style.display = "none";
				byWeek.style.display = "block";
				rowMonth.style.display = "none";
				fortNight.style.display = "none";
			}
		if(val == "4")
			{
				week.style.display = "none";
				byWeek.style.display = "none";
				rowMonth.style.display = "block";
				fortNight.style.display = "none";
			}
		if(val == "5")
			{
				week.style.display = "none";
				byWeek.style.display = "none";
				rowMonth.style.display = "none";
				fortNight.style.display = "block";
			}
	}

	function createCycle()
	{
		var temp = document.forms[0];

		/* Must select a cycle type */
		if (temp.cycleType.value == "0")
		{
			alert ("U must Select a CycleType!");
			return false;
		}
		
		/* Removing the previous values */
		temp.hidCycleType.value = "";
		temp.hidCycle.value = "";

		switch (temp.cycleType.value)
		{
			case "1" :	temp.hidCycleType.value = temp.cycleType.options[temp.cycleType.selectedIndex].text;
						temp.hidCycle.value = "";
						break;

			case "2" :	if (temp.week.value == "0")
						{
							alert ("Select a Day for this Week!");
							return false;
						}
						temp.hidCycleType.value = temp.cycleType.options[temp.cycleType.selectedIndex].text;
						temp.hidCycle.value = temp.week.value;
						break;

			case "3" :	if ((temp.byWeek.value == "0") || (temp.byWeekSelect.value == "0"))
						{
							alert ("By Week Days are Empty!");
							return false;
						}
						temp.hidCycleType.value = temp.cycleType.options[temp.cycleType.selectedIndex].text;
						temp.hidCycle.value = temp.byWeek.value + "-" + temp.byWeekSelect.value;
						break;

			case "4" :	if (temp.month.value == "0")
						{
							alert ("Select a Day for this Month!");
							return false;
						}
						temp.hidCycleType.value = temp.cycleType.options[temp.cycleType.selectedIndex].text;
						temp.hidCycle.value = temp.month.value;
						break;

			case "5" :	if ((temp.fortNight.value == "0") || (temp.byFortNight.value == "0"))
						{
							alert ("FortNight Days are Empty!");
							return false;
						}
						temp.hidCycleType.value = temp.cycleType.options[temp.cycleType.selectedIndex].text;
						temp.hidCycle.value = temp.fortNight.value + "-" + temp.byFortNight.value;
						break;

			default: alert ("Invalid Entry"); return false; break;

		}

		//alert ("CycleType: "+temp.hidCycleType.value+" & "+temp.hidCycle.value);
		temp.submit();
	}

</script>
</head>

<body onload="loadPreviousCycle();">
<html:form action="frmPayRollCycle" focus="cycleType">
<html:hidden property="hidCycleType"/>
<html:hidden property="hidCycle"/>
	<table width="100%" cellspacing="0" cellpadding="10">
	<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
		<tr>
			<td><bean:message key="prodacs.payroll.payrollcycle"/></td>
		</tr>
		</table>
		<br>
	<table width="100" cellspacing="0" cellpadding="0" align="right">
	<tr>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Create New Payroll Cycle'; return true"  onMouseOut="window.status=''; return true" resourceId="1022" text="[ Create Cycle]" classId="TopLnk" onClick="javaScript:createCycle();"/>
	</tr>
	</table>

		<font class="message">
			<html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   </html:messages>
	   	</font>

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
			<td width="100" class="FieldTitle"><bean:message key="prodacs.payroll.cycletype"/></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="cycleType" styleClass="Combo" onchange="chkPayCycle(this.value)">
			<html:option value="0">-- Choose Cycle --</html:option>
			<html:option value="1"><bean:message key="prodacs.payroll.day"/></html:option>
			<html:option value="2"><bean:message key="prodacs.payroll.week"/></html:option>
			<html:option value="3"><bean:message key="prodacs.payroll.byweek"/></html:option>
			<html:option value="4"><bean:message key="prodacs.payroll.month"/></html:option>
			<html:option value="5"><bean:message key="prodacs.payroll.fortnight"/></html:option>
			</html:select></td>
		</tr>
		<tr id="week" style="display:none"> 
			<td class="FieldTitle"><bean:message key="prodacs.payroll.week"/></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="week" styleClass="Combo">
			<html:option value="0">-- Choose Week --</html:option>
			<html:option value="1">Sunday</html:option>
			<html:option value="2">Monday</html:option>
			<html:option value="3">Tuesday</html:option>
			<html:option value="4">Wednesday</html:option>
			<html:option value="5">Thursday</html:option>
			<html:option value="6">Friday</html:option>
			<html:option value="7">Saturday</html:option>
			</html:select></td>
		</tr>
		<tr id="byWeek" style="display:none"> 
			<td class="FieldTitle"><bean:message key="prodacs.payroll.byweek"/></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="byWeek" styleClass="Combo">
			<html:option value="0">-- Choose Week --</html:option>
			<html:option value="1">Sunday</html:option>
			<html:option value="2">Monday</html:option>
			<html:option value="3">Tuesday</html:option>
			<html:option value="4">Wednesday</html:option>
			<html:option value="5">Thursday</html:option>
			<html:option value="6">Friday</html:option>
			<html:option value="7">Saturday</html:option>
			</html:select>
          		:
			<html:select property="byWeekSelect" styleClass="Combo">
			<html:option value="0">-- Choose Week --</html:option>
			<html:option value="1">Sunday</html:option>
			<html:option value="2">Monday</html:option>
			<html:option value="3">Tuesday</html:option>
			<html:option value="4">Wednesday</html:option>
			<html:option value="5">Thursday</html:option>
			<html:option value="6">Friday</html:option>
			<html:option value="7">Saturday</html:option>
			</html:select></td>
		</tr>
		<tr id="rowMonth" style="display:none"> 
			<td class="FieldTitle"><bean:message key="prodacs.payroll.month"/></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="month" styleClass="Combo">
			<html:option value="0">-- Choose Day --</html:option>
			<html:option value="1">01</html:option>
			<html:option value="2">02</html:option>
			<html:option value="3">03</html:option>
			<html:option value="4">04</html:option>
			<html:option value="5">05</html:option>
			<html:option value="6">06</html:option>
			<html:option value="7">07</html:option>
			<html:option value="8">08</html:option>
			<html:option value="9">09</html:option>
			<html:option value="10">10</html:option>
			<html:option value="11">11</html:option>
			<html:option value="12">12</html:option>
			<html:option value="13">13</html:option>
			<html:option value="14">14</html:option>
			<html:option value="15">15</html:option>
			<html:option value="16">16</html:option>
			<html:option value="17">17</html:option>
			<html:option value="18">18</html:option>
			<html:option value="19">19</html:option>
			<html:option value="20">20</html:option>
			<html:option value="21">21</html:option>
			<html:option value="22">22</html:option>
			<html:option value="23">23</html:option>
			<html:option value="24">24</html:option>
			<html:option value="25">25</html:option>
			<html:option value="26">26</html:option>
			<html:option value="27">27</html:option>
			<html:option value="28">28</html:option>
			<html:option value="29">29</html:option>
			<html:option value="30">30</html:option>
			<html:option value="31">31</html:option>
			</html:select></td>
		</tr>
		<tr id="fortNight" style="display:none">
			<td class="FieldTitle"><bean:message key="prodacs.payroll.fortnight"/></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="fortNight" styleClass="Combo">
			<html:option value="0">-- Choose Day --</html:option>
			<html:option value="1">01</html:option>
			<html:option value="2">02</html:option>
			<html:option value="3">03</html:option>
			<html:option value="4">04</html:option>
			<html:option value="5">05</html:option>
			<html:option value="6">06</html:option>
			<html:option value="7">07</html:option>
			<html:option value="8">08</html:option>
			<html:option value="9">09</html:option>
			<html:option value="10">10</html:option>
			<html:option value="11">11</html:option>
			<html:option value="12">12</html:option>
			<html:option value="13">13</html:option>
			<html:option value="14">14</html:option>
			<html:option value="15">15</html:option>
			<html:option value="16">16</html:option>
			<html:option value="17">17</html:option>
			<html:option value="18">18</html:option>
			<html:option value="19">19</html:option>
			<html:option value="20">20</html:option>
			<html:option value="21">21</html:option>
			<html:option value="22">22</html:option>
			<html:option value="23">23</html:option>
			<html:option value="24">24</html:option>
			<html:option value="25">25</html:option>
			<html:option value="26">26</html:option>
			<html:option value="27">27</html:option>
			<html:option value="28">28</html:option>
			<html:option value="29">29</html:option>
			<html:option value="30">30</html:option>
			<html:option value="31">31</html:option>
			</html:select>
			:
			<html:select property="byFortNight" styleClass="Combo">
			<html:option value="0">-- Choose Day --</html:option>
			<html:option value="1">01</html:option>
			<html:option value="2">02</html:option>
			<html:option value="3">03</html:option>
			<html:option value="4">04</html:option>
			<html:option value="5">05</html:option>
			<html:option value="6">06</html:option>
			<html:option value="7">07</html:option>
			<html:option value="8">08</html:option>
			<html:option value="9">09</html:option>
			<html:option value="10">10</html:option>
			<html:option value="11">11</html:option>
			<html:option value="12">12</html:option>
			<html:option value="13">13</html:option>
			<html:option value="14">14</html:option>
			<html:option value="15">15</html:option>
			<html:option value="16">16</html:option>
			<html:option value="17">17</html:option>
			<html:option value="18">18</html:option>
			<html:option value="19">19</html:option>
			<html:option value="20">20</html:option>
			<html:option value="21">21</html:option>
			<html:option value="22">22</html:option>
			<html:option value="23">23</html:option>
			<html:option value="24">24</html:option>
			<html:option value="25">25</html:option>
			<html:option value="26">26</html:option>
			<html:option value="27">27</html:option>
			<html:option value="28">28</html:option>
			<html:option value="29">29</html:option>
			<html:option value="30">30</html:option>
			<html:option value="31">31</html:option>
			</html:select></td>
		</tr>
		</table>
	</td>
	</tr>
	</table>
</html:form>
</body>
</html:html>
