<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<useradmin:userrights resource="1007"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Operation Group Edit");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap machineNames = new HashMap();
	try
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionJobDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
		SessionJobDetailsManager opGpObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
		
		machineNames = opGpObj.getAllMachines();
		if (BuildConfig.DMODE)
			System.out.println("MachineNames(HM): "+ machineNames);
		//pageContext.setAttribute("machineNames",machineNames);
		
	} catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in OperationGroupAdd.jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/OperationGroup/OperationGroupList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/OperationGroup/OperationGroupAdd.jsp';
		document.forms[0].submit();
	}
	
	function submitItem()
	{
		temp = document.forms[0];
		obj = document.getElementById('opnGpDetails');
		object = document.getElementById('tblOpn');
		var machCode = "";

		if(object.children(0).children(1).children(2).children(0).checked)
		{
			if (obj.children(0).children.length != 0)
			{
				var flag=false;
				for (var i = 0; i < obj.children(0).children.length; i++)
				{
					if (obj.children(0).children(i).children(0).children(0).checked)
					{
						machCode = machCode + obj.children(0).children(i).children(1).innerText + "$";
						flag=true;
					}
				}
				if(flag == false)
				{
					alert("None of the Machines Selected!");
					return false;
				}
				temp.machineName.value = machCode;
			}
			temp.machDetsCheck.value = 1;
		}
		else
		{
			temp.machDetsCheck.value = 0;
		}
		//alert(temp.machDetsCheck.value);
		temp.formAction.value = "update";
		temp.submit();
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmOprGrpEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

	function loadValues()
	{
		temp = document.forms[0];
		obj = document.getElementById('opnGpDetails');
		var object = document.getElementById("tblOpn");
		var mcName = (temp.machineName.value).split("^");
		//alert(temp.machDetsCheck.value);
		if (temp.machDetsCheck.value == "true")
		{
			object.children(0).children(1).children(2).children(0).checked = true;
		}
		else
		{
			object.children(0).children(1).children(2).children(0).checked = false;
		}
		if (object.children(0).children(1).children(2).children(0).checked)
		{
			tblDisp.style.display = 'block';
			for (var i = 0; i < obj.children(0).children.length; i++)
			{
				for (var j = 0; j < mcName.length; j++)
				{
					if (obj.children(0).children(i).children(1).innerText == mcName[j])
					{
						obj.children(0).children(i).children(0).children(0).checked = true;
					}
				}
			}
		}
	}

	function checkAllMach(formObj)
	{
		var temp = document.forms[0];
		if (temp.CheckAllDym.checked)
		{
			if (temp.CheckValue.length > 1)
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = true;
				}
			}
			else
			{
				temp.CheckValue.checked = true;
			}
		}
		else
		{
			if (temp.CheckValue.length > 1)
			{
				for (var i = 0; i < temp.CheckValue.length; i++)
				{
					temp.CheckValue[i].checked = false;
				}
			}
			else
			{
				temp.CheckValue.checked = false;
			}
		}

	}

	function showMachDets()
	{
		temp = document.forms[0];
		var obj = document.getElementById("tblOpn");
		if (obj.children(0).children(1).children(2).children(0).checked)
		{
			tblDisp.style.display = 'block';
			temp.machDetsCheck.value = (obj.children(0).children(1).children(2).children(0).checked)?"1":"0";
		}
		else
		{
			tblDisp.style.display = 'none';
		}
	}

</script>
</head>

<body onload = "loadToHidden();loadValues();">
<html:form action="frmOprGrpEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<html:hidden property="machineName"/>
<html:hidden property="machDetsCheck"/>
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
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List OperationGroup Info'; return true"  onMouseOut="window.status=''; return true" resourceId="7" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make OperationGroup Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1007" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
          </tr>
        </table>
        <table>
		  	<tr><td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td></tr> 
		  </table>
        <br>
        <table width="100%" cellspacing="5" cellpadding="5" id="tblOpn">
          <tr> 
            <td width="170" class="FieldTitle"><bean:message key="prodacs.operationgroup.operationgroupcode"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><html:text property="operationGroupCode" styleClass="TextBox" size="10" readonly="true"/></td>
          </tr>
		  <tr>
			<td width="170" class="FieldTitle">Machine Related</td>
			<td width="1" class="FieldTitle">:</td>
			<td><html:checkbox property="machRelated" onclick="showMachDets();"/></td>
		  </tr>
		  </table>
		 <br><br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblDisp" style="display:none">
		<tr>
		<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
          	<tr>
            	<td><font size="2px" face="verdana"><b><bean:message key="prodacs.operationgroup.machinedetails"/></font></td>
          	</tr>
	      </table>
        <br>		 
	 	<div>
         <table width="50%" cellspacing="0" cellpadding="0" id="tblMachs">
   		  <tr>
               <td width="25" class="Header"><input type="checkbox" name="CheckAllDym" onClick="checkAllMach(document.OperationGroupEdit)"></td>
               <td width="180" class="Header"><bean:message key="prodacs.machine.machinecode"/></td>	                 
               <td class="Header"><bean:message key="prodacs.machine.machinename"/></td>
		  </tr>
		</table>
		</div>
		  <div id="opnGpHeader" style="width:50%; height:150; overflow:scroll; ">
		  <table width="100%" cellspacing="0" cellpadding="0" id="opnGpDetails">
<%
	Set set = machineNames.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext())
	{
		Map.Entry me = (Map.Entry) it.next();
%>
		  <tr>
			<td width="22" class="TableItems"><input type="checkbox" name="CheckValue"/></td>
			<td width="177" class="TableItems"><%= me.getKey() %></td>
			<td class="TableItems"><%= me.getValue() %></td>
		  </tr>
<%
	}
%>		  
        </table>
		</div>
        <br>
		</td>
		</tr>
		</table>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td><html:button property="addOperationGroup" styleClass="Button" onclick="javascript:submitItem()">Modify</html:button> 
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
