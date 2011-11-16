<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>
<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<%@ page import = "com.savantit.prodacs.businessimplementation.job.OperationGroupDetails"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="7"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Operation Group View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	OperationGroupDetails objOpnGrpDetails = null;
	HashMap machineName = new HashMap();
	if (BuildConfig.DMODE)
		System.out.println("Id: "+ id);
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionJobDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
		SessionJobDetailsManager opGpObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);

 		objOpnGrpDetails = opGpObj.getOperationGroupDetails(Integer.parseInt(id));
 		
 		machineName = objOpnGrpDetails.getHm_MachineDetails();
 		pageContext.setAttribute("machineName",machineName);
 		
   }catch(Exception e)
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
	var isValid = <%= objOpnGrpDetails.getOpnGpIsValid() %>;
	
	function chkMkValid()
	{
		temp = document.forms[0];
		temp.machName.value = '<%= session.getAttribute("txtMachineName") %>';
		temp.machSrchTab.value = '<%= session.getAttribute("machineName") %>';
		temp.viewAll.value = '<%= session.getAttribute("validEntry") %>';

		if (document.getElementById('mkValid') != null)
		{
			if(isValid == 0)
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
		if ((temp.machName.value != "" ) || (temp.machSrchTab.value != "") || (temp.viewAll.value != ""))
		{
			temp.action = '<bean:message key="context"/>/frmOprGrpList.do?formAction=afterView&machName='+temp.machName.value+'&machSrchTab='+temp.machSrchTab.value+'&viewAll='+temp.viewAll.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/OperationGroup/OperationGroupList.jsp';
			temp.submit();
		}
	}

	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=modify&id='+<%= objOpnGrpDetails.getOperationGroupId() %>;
			document.forms[0].submit();
		}	
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/OperationGroup/OperationGroupAdd.jsp';
		document.forms[0].submit();
	}

</script>
</head>

<body onload = "loadToHidden();chkMkValid();">
<html:form action="frmOprGrpEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="machName"/>
<input type="hidden" name="machSrchTab"/>
<input type="hidden" name="viewAll"/>

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.operationgroup.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New OperationGroup Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify OperationGroup Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make OperationGroup Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make OperationGroup Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="150" class="FieldTitle"><bean:message key="prodacs.operationgroup.operationgroupcode"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objOpnGrpDetails.getOperationGroupCode() %></td>
          </tr>
		  <tr>
			<td width="170" class="FieldTitle">Machine Related</td>
			<td width="1" class="FieldTitle">:</td>
			<%
				if (objOpnGrpDetails.isMachineRelated())
				{
			%>
				<td><img src='<bean:message key="context"/>/images/active.gif'></td>
			<%
				}
				else
				{
			%>
				<td><img src='<bean:message key="context"/>/images/inactive.gif'></td>
			<%
				}
			%>
		  </tr>
        </table>
		<br><br>
		<%
			if (objOpnGrpDetails.isMachineRelated())
			{
		%>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
        <tr>
           	<td><font size="2px" face="verdana"><b><bean:message key="prodacs.operationgroup.machinedetails"/></font></td>
         </tr>
	     </table>
        <br><div>
         <table width="50%" cellspacing="0" cellpadding="3" >
   		  <tr>
               <td width="180" class="Header"><bean:message key="prodacs.machine.machinecode"/></td>	                 
               <td class="Header"><bean:message key="prodacs.machine.machinename"/></td>
		  </tr>
		</table>
		</div>
		  <div id="opnGpHeader" style="width:50%; height:80; overflow:scroll; ">
		  <table width="100%" cellspacing="0" cellpadding="3" id="opnGpDetails">
<%
	Set set = machineName.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext())
	{
		Map.Entry me = (Map.Entry) it.next();
%>
		  <tr>
			<td width="180" class="TableItems">&nbsp;<%= me.getKey() %></td>
			<td class="TableItems">&nbsp;<%= me.getValue() %></td>
		  </tr>
		<%
			}
		%>		  
        </table>
		</div>
<%
}
%>		
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="retOperationGroupList" styleClass="Button" onclick="javascript:listItem()" value="Return to Operation Group List"/>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>