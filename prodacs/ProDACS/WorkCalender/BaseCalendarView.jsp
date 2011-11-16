<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import = "java.util.Vector" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkCalendarDetailsManagerHome"%>
<useradmin:userrights resource="9"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Shift Definition View");
	
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	BaseCalendarDetails objBaseCalDet = null;
	Vector vec_ShiftDetails = null;
	String[] dayIndices = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkCalendarDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkCalendarDetailsManagerHome baseCalHomeObj = (SessionWorkCalendarDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkCalendarDetailsManagerHome.class);
		SessionWorkCalendarDetailsManager baseCalDefObj = (SessionWorkCalendarDetailsManager)PortableRemoteObject.narrow(baseCalHomeObj.create(),SessionWorkCalendarDetailsManager.class);
		
		objBaseCalDet = baseCalDefObj.getBaseCalendarDetails(Integer.parseInt(id));
		
		vec_ShiftDetails = (Vector)objBaseCalDet.getVec_ShiftDetails();
		pageContext.setAttribute("vec_ShiftDetails",vec_ShiftDetails);
		
		if (BuildConfig.DMODE)
			System.out.println("-->"+objBaseCalDet.getBcStartDay());
		
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
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objBaseCalDet.getBcIsValid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';

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
	
	function listItem()
	{
		temp = document.forms[0];
		if (temp.viewAll.value != "" )
		{
			temp.action = '<bean:message key="context"/>/frmBaseClndrList.do?formAction=afterView&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/WorkCalender/BaseCalendarList.jsp';
			temp.submit();
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkCalender/BaseCalendarAdd.jsp';
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
			document.forms[0].action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=modify&id='+<%= objBaseCalDet.getBcId() %>;
			document.forms[0].submit();
		}	
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=makeInvalid';
		temp.submit();
	}

function makeValidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function deleteItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmBaseClndrEdit.do?formAction=delete';
		temp.submit();
	}
</script>
</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="frmBaseClndrEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="viewAll"/>
<table width="100%" cellspacing="0" cellpadding="10">
    <tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workcalendar.basecalendar.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Delete BaseCalendar Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ Delete ]" classId="TopLnk" onClick="javaScript:deleteItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make BaseCalendar Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make BaseCalendar Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1009" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValidItem();"/>
        </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
			<tr> 
			  <td width="150" class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.basecalendarname"/></td>
			  <td width="1" class="FieldTitle">:</td>
           <td colspan="4" class="ViewData"><%= objBaseCalDet.getBcName() %></td>
			</tr>
			<tr> 
			  <td class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.startdayoftheweek"/></td>
			  <td class="FieldTitle">:</td>
			  
            <td width="100" class="ViewData"><%= dayIndices[objBaseCalDet.getBcStartDay()-1] %></td>
			  <td width="150" class="FieldTitle"><bean:message key="prodacs.workcalendar.basecalendar.enddayoftheweek"/></td>
			  <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= dayIndices[objBaseCalDet.getBcEndDay()-1] %></td>
			</tr>
		  </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td class="FieldTitle"><fieldset id="FieldSet"><legend>Shift Details</legend>
            <br>
			<!-- <div style="height:359;overflow:auto;"> -->
              	<table width="100%" cellspacing="0" cellpadding="3">
               <tr> 
	                 <td width="110" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.dayoftheweek"/></td>	                 
	                 <td class="Header"><bean:message key="prodacs.workcalendar.shiftdefinition.shiftname"/></td>
	                 <td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.starttime"/></td>
						  <td width="100" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.endtime"/></td>
						  <td width="150" class="Header"><bean:message key="prodacs.workcalendar.basecalendar.predecessorshift"/></td>
              	<logic:iterate id="bt1" name="vec_ShiftDetails" indexId="count">	            
               <tr>
			<%
				if(count.intValue()%2 == 0) 
				{
			%>		               
                  <td width="109" class="TableItems"><bean:define id="stDay" name="bt1" property="day"/><%= dayIndices[Integer.parseInt(stDay.toString())-1] %><!--bean:write name="bt1" property="day"/-->&nbsp;</td>
                  <td class="TableItems"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
                  <td width="100" class="TableItems"><bean:write name="bt1" property="startTime"/>-<bean:write name="bt1" property="startTimeDay"/>&nbsp;</td>
                  <td width="100" class="TableItems"><bean:write name="bt1" property="endTime"/>-<bean:write name="bt1" property="endTimeDay"/>&nbsp;</td>
			<td width="134" class="TableItems"><bean:write name="bt1" property="predsrShiftName"/>&nbsp;</td>
			<%
				}
				else
				{
			%>
                  <td width="109" class="TableItems2"><bean:define id="stDay1" name="bt1" property="day"/><%= dayIndices[Integer.parseInt(stDay1.toString())-1] %><!--bean:write name="bt1" property="day"/-->&nbsp;</td>
                  <td class="TableItems2"><bean:write name="bt1" property="shiftName"/>&nbsp;</td>
                  <td width="100" class="TableItems2"><bean:write name="bt1" property="startTime"/>-<bean:write name="bt1" property="startTimeDay"/>&nbsp;</td>
                  <td width="100" class="TableItems2"><bean:write name="bt1" property="endTime"/>-<bean:write name="bt1" property="endTimeDay"/>&nbsp;</td>
			<td width="134" class="TableItems2"><bean:write name="bt1" property="predsrShiftName"/>&nbsp;</td>
			<%
			}
			%>			
               </tr>
               </logic:iterate>
              </table>
			 <!--  </div> -->
              </fieldset></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retBaseCalendarList" styleClass="Button" onclick="javascript:listItem()">List</html:button>
            </td>
          </tr>
        </table>
        
      </td>
    </tr>
  </table>
</html:form>
</body>
</html:html>