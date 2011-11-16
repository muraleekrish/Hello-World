<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.reworklog.ReworkLogAddForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.facade.SessionReworkLogDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionReworkLogDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reworklog.RwkLogJbQtyDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reworklog.RwkLogJbOpnDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1027"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Rework Add Starts.");
	EJBLocator obj = new EJBLocator(); /* EjbLocator for Session Bean */
	String jobOpnDet = "";
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionReworkLogDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionReworkLogDetailsManagerHome rwkLogHomeObj = (SessionReworkLogDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReworkLogDetailsManagerHome.class);
		SessionReworkLogDetailsManager rwkLogObj = (SessionReworkLogDetailsManager)PortableRemoteObject.narrow(rwkLogHomeObj.create(),SessionReworkLogDetailsManager.class);
		
		/* Loading the ReworkCategory */
		HashMap hmRwkCategory = rwkLogObj.getAllReworkCategories();
		pageContext.setAttribute("hmRwkCategory",hmRwkCategory);
		
		/* Loading ReworkReason by giving ReworkCategory */
		HashMap hmRwkReason = new HashMap();
		if (!frm.getReworkCategory().equalsIgnoreCase("0"))
		{
			hmRwkReason = rwkLogObj.getAllReworkReasonsByCategory(Integer.parseInt(frm.getReworkCategory()));
			pageContext.setAttribute("hmRwkReason",hmRwkReason);
		}
		else
		{
			pageContext.setAttribute("hmRwkReason",hmRwkReason);
		}
		
		/* Loading WorkOrder No.s */
		HashMap hmWrkOrdList = rwkLogObj.getWorkOrderList();
		pageContext.setAttribute("hmWrkOrdList",hmWrkOrdList);
		
		/* Loading JobNames by giving woId */
		Vector vecJobName = new Vector();
		HashMap hmJobName = new HashMap();
		if (!frm.getWrkOrdNum().equalsIgnoreCase("0"))
		{
			vecJobName = rwkLogObj.getJobNameByWorkOrder(Integer.parseInt(frm.getWrkOrdNum()));
			for (int i = 0; i < vecJobName.size(); i++)
			{
				WOJobDetails objWOJobDet = new WOJobDetails();
				objWOJobDet = (WOJobDetails) vecJobName.get(i);
				hmJobName.put(objWOJobDet.getJobId()+"", objWOJobDet.getJobName());
			}
			pageContext.setAttribute("hmJobName",hmJobName);
		}
		else
		{
			pageContext.setAttribute("hmJobName",hmJobName);
		}
		
		/* Loading JobQty Details */
		Vector vecJbQtyDet = new Vector();
		if (BuildConfig.DMODE)
		{
			System.out.println("Wo: "+frm.getWrkOrdNum());
			System.out.println("Jb: "+frm.getJobName());
		}
		if ((!frm.getWrkOrdNum().equalsIgnoreCase("0")) && (!frm.getJobName().equalsIgnoreCase("0")))
		{
			vecJbQtyDet = rwkLogObj.getRwkJobOperationDetails(Integer.parseInt(frm.getWrkOrdNum()), Integer.parseInt(frm.getJobName()));
			if (BuildConfig.DMODE)
				System.out.println("Vec: "+ vecJbQtyDet.size());
			pageContext.setAttribute("vecJbQtyDet",vecJbQtyDet);
		}
		else
		{
			pageContext.setAttribute("vecJbQtyDet",vecJbQtyDet);
		}
		
		/* Loading the JobOperation Details */
		Vector vecJobQtyDetails = new Vector();
		Vector vecJobOpnDetails = new Vector();
		if ((!frm.getWoJbStatId().equalsIgnoreCase("0")) && (!frm.getWrkOrdNum().equalsIgnoreCase("0")) && (!frm.getJobName().equalsIgnoreCase("0")))
		{
			vecJobQtyDetails = rwkLogObj.getRwkJobOperationDetails(Integer.parseInt(frm.getWrkOrdNum()), Integer.parseInt(frm.getJobName()));
			for (int i = 0; i < vecJobQtyDetails.size(); i++)
			{
				/* Object to ReworkLog Job Qty Details */
				RwkLogJbQtyDetails objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();
				objRwkLogJbQtyDetails = (RwkLogJbQtyDetails) vecJobQtyDetails.get(i);
				
				if (objRwkLogJbQtyDetails.getJbStatId() == Integer.parseInt(frm.getWoJbStatId()))
				{
					vecJobOpnDetails = objRwkLogJbQtyDetails.getVecRwkLogJbOpnDetails();
					int sno = objRwkLogJbQtyDetails.getJbQtySno();
					for (int j = 0; j < vecJobOpnDetails.size(); j++)
					{
						/* Object to Rework Log Job Operation Details */
						RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
						objRwkLogJbOpnDetails = (RwkLogJbOpnDetails) vecJobOpnDetails.get(j);
						
						if (j != 0)
						{
							jobOpnDet = jobOpnDet + "^";
						}
						jobOpnDet = jobOpnDet + objRwkLogJbOpnDetails.getWoJbOpnId();
						jobOpnDet = jobOpnDet + "-" + objRwkLogJbOpnDetails.getOpnSno();
						jobOpnDet = jobOpnDet + "-" + objRwkLogJbOpnDetails.getOpnName();
						jobOpnDet = jobOpnDet + "-" + ((objRwkLogJbOpnDetails.isProdnEntered() == true)?"Production Entered":"Completed");
						jobOpnDet = jobOpnDet + "-" + sno;
					}
				}
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("OpnDet: "+ jobOpnDet);
			System.out.println("jobs->"+frm.getJobs());
		}
	}
	catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in Rework Log Add.jsp");
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
<script>
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/ReworkLog/ReworkLogList.jsp';
		document.forms[0].submit();
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/ReworkLog/ReworkLogAdd.jsp';
		document.forms[0].submit();
	}
	
	function loadRwkReason()
	{
		var temp = document.forms[0];
		if (temp.reworkCategory.value != "0")
		{
			temp.formAction.value = "load";
			temp.submit();
		}
	}

	function loadJobName()
	{
		var temp = document.forms[0];
		if (temp.wrkOrdNum.value != "0")
		{
			temp.formAction.value = "load";
			temp.submit();
		}
	}
	
	function loadJobQtyDetails()
	{
		var temp = document.forms[0];
		if (temp.jobName.value != "0")
		{
			temp.formAction.value = "load";
			temp.jobs.value = "";
			temp.submit();
		}
	}
	
	function loadTables()
	{
		var temp = document.forms[0];
		if ((temp.wrkOrdNum.value != "0") && (temp.jobName.value != "0"))
		{
			tblJobType.style.display = "block";
		}

		if (temp.woJbStatId.value != '0') /* Checking the Exact Job Quantity */
		{
			var obj = document.getElementById('tblJobQtyDet');
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).value == temp.woJbStatId.value)
				{
					obj.children(0).children(i).children(0).children(0).checked = true;
					tblOprType.style.display = "block";
				}
			}
		}

		if (temp.woJbStatId.value != "0") /* This is loading the JobOperation Details */
		{
			var obj = document.getElementById('oprType');
			var opnDet = ('<%= jobOpnDet %>').split("^");
			//alert (opnDet);
			for (var i = 0; i < opnDet.length; i++)
			{
				var opnPrnlDet = opnDet[i].split("-");
				if (opnPrnlDet.length < 4)
				{
					tblOprType.style.display = 'none';
					//alert ('This particular Quantity having No Operation!');
					return false;
				}
					
				if ((obj.children(0).children.length == 2) && (obj.children(0).children(1).children(0).children(0).value == "on"))
				{
					obj.children(0).children(1).children(0).children(0).value = opnPrnlDet[0];
					obj.children(0).children(1).children(1).children(0).value = opnPrnlDet[1];
					obj.children(0).children(1).children(2).children(0).value = opnPrnlDet[2];
					obj.children(0).children(1).children(3).children(0).value = opnPrnlDet[3];
					obj.children(0).children(1).children(4).children(0).value = opnPrnlDet[4];
				}
				else
				{
					var newNode = obj.children(0).children(1).cloneNode(true);
					obj.children(0).appendChild(newNode);
					var le = obj.children(0).children.length;
			
					obj.children(0).children(le-1).children(0).children(0).value = "";
					obj.children(0).children(le-1).children(1).children(0).value = "";
					obj.children(0).children(le-1).children(2).children(0).value = "";
					obj.children(0).children(le-1).children(3).children(0).value = "";
					obj.children(0).children(le-1).children(4).children(0).value = "";

					obj.children(0).children(le-1).children(0).children(0).value = opnPrnlDet[0];
					obj.children(0).children(le-1).children(1).children(0).value = opnPrnlDet[1];
					obj.children(0).children(le-1).children(2).children(0).value = opnPrnlDet[2];
					obj.children(0).children(le-1).children(3).children(0).value = opnPrnlDet[3];
					obj.children(0).children(le-1).children(4).children(0).value = opnPrnlDet[4];
				}
			}
			
		}
	}

	function loadJobOperations()
	{
		//tblOprType.style.display='block';
		var temp = document.forms[0];
		var obj = document.getElementById('tblJobQtyDet');
		temp.woJbStatId.value = "0";
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.woJbStatId.value = obj.children(0).children(i).children(0).children(0).value;
				temp.hidJbQtySno.value = obj.children(0).children(i).children(1).children(0).value;
			}
		}
		if ((temp.woJbStatId.value != "0") && (temp.hidJbQtySno.value != ""))
		{
			temp.formAction.value = "load";
			temp.submit();
		}
	}

	function addReworkLog()
	{
		var temp = document.forms[0];

		if (temp.wrkOrdNum.value == "0")
		{
			alert ("WorkOrder No. is Mandatory Field! ");
			return false;
		}

		if (temp.jobName.value == "0")
		{
			alert ("JobName is Mandatory Field! ");
			return false;
		}

		if (temp.reworkCategory.value == "0")
		{
			alert ("Rework Category is Mandatory Field!");
			return false;
		}

		if (temp.reworkReason.value == "0")
		{
			alert ("Rework Reason is Mandatory Field!");
			return false;
		}

		if (temp.authorizedBy.value == "")
		{
			alert ("Authorization is Mandatory!");
			return false;
		}
		
		var obj = document.getElementById('addBuffer');
		var rwkLogDet = "";
		var t = 0;
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (t != 0)
			{
				rwkLogDet = rwkLogDet + "^";
			}
			rwkLogDet = rwkLogDet + obj.children(0).children(i).children(0).children(0).value;
			rwkLogDet = rwkLogDet + "-" + obj.children(0).children(i).children(1).children(0).value;
			rwkLogDet = rwkLogDet + "-" + obj.children(0).children(i).children(2).children(0).value;
			rwkLogDet = rwkLogDet + "-" + obj.children(0).children(i).children(3).children(0).value;
			t++;
		}
		temp.rwkLogDetails.value = rwkLogDet;
		if (temp.rwkLogDetails.value == "")
		{
			alert ("Rework Operations are Empty! Check One or More Operations!");
			return false;
		}
		temp.formAction.value = "add";
		temp.submit();
	}
	
	function moveToBuffer()
	{
		temp = document.forms[0];
		obj = document.getElementById('oprType');
		object = document.getElementById('addBuffer');
		var jbDet = "";
		var cnt = 0;

		/* Atleast one item should be checked */
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				cnt++;
			}
		}

		if (cnt == 0)
		{
			alert ("Operations are not Checked!");
			return false;
		}

		tblBuffer.style.display = 'block';
		/* Moving the Operation Details to Buffer */
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if ((obj.children(0).children.length != 0) && (obj.children(0).children(i).children(0).children(0).checked))
			{
				for (var k=1; k < object.children(0).children.length; k++)
				{
					if (obj.children(0).children(i).children(0).children(0).value == object.children(0).children(k).children(0).children(0).value)
					{
						alert ("Details already shifted, Please try another!");
						obj.children(0).children(i).children(0).children(0).checked = false;
						return false;
					}
				}

				if ((object.children(0).children.length == 2) && (object.children(0).children(1).children(1).children(0).value == ""))
				{
					object.children(0).children(1).children(0).children(0).value = obj.children(0).children(i).children(0).children(0).value;
					object.children(0).children(1).children(1).children(0).value = obj.children(0).children(i).children(4).children(0).value;
					object.children(0).children(1).children(2).children(0).value = obj.children(0).children(i).children(1).children(0).value;
					object.children(0).children(1).children(3).children(0).value = obj.children(0).children(i).children(2).children(0).value;
					object.children(0).children(1).children(4).children(0).value = obj.children(0).children(i).children(3).children(0).value;
					obj.children(0).children(i).children(0).children(0).checked = false;
					obj.children(0).children(0).children(0).children(0).checked = false;
				}
				else
				{
					var newNode = object.children(0).children(1).cloneNode(true);
					object.children(0).appendChild(newNode);
					var le = object.children(0).children.length;

					object.children(0).children(le-1).children(0).children(0).value = "";
					object.children(0).children(le-1).children(1).children(0).value = "";
					object.children(0).children(le-1).children(2).children(0).value = "";
					object.children(0).children(le-1).children(3).children(0).value = "";
					object.children(0).children(le-1).children(4).children(0).value = "";

					object.children(0).children(le-1).children(0).children(0).value = obj.children(0).children(i).children(0).children(0).value;
					object.children(0).children(le-1).children(1).children(0).value = obj.children(0).children(i).children(4).children(0).value;
					object.children(0).children(le-1).children(2).children(0).value = obj.children(0).children(i).children(1).children(0).value;
					object.children(0).children(le-1).children(3).children(0).value = obj.children(0).children(i).children(2).children(0).value;
					object.children(0).children(le-1).children(4).children(0).value = obj.children(0).children(i).children(3).children(0).value;
					obj.children(0).children(i).children(0).children(0).checked = false;
					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
		}

		/* Buffering those values */
		for (var j = 1; j < object.children(0).children.length; j++)
		{
			if (j != 1)
			{
				jbDet = jbDet + "^";
			}
			jbDet = jbDet + object.children(0).children(j).children(0).children(0).value + "-"+
							object.children(0).children(j).children(1).children(0).value + "-"+
							object.children(0).children(j).children(2).children(0).value +"-"+
							object.children(0).children(j).children(3).children(0).value +"-"+
							object.children(0).children(j).children(4).children(0).value ;
			temp.jobs.value = jbDet;
			//alert(temp.jobs.value);
		}
	}

	function reloadValues()
	{
		temp = document.forms[0];
		//obj = document.getElementById('oprType');
		object = document.getElementById('addBuffer');
		var job = '<%= request.getParameter("jobs") %>';
		if (job == 'null')
		{
			return false;
		}
		if (job != "")
		{
			tblBuffer.style.display = "block";
			var jobDet = (job).split("^");
			for (var i = 0; i < jobDet.length; i++)
			{
				var jobQty = jobDet[i].split("-");
				if ((object.children(0).children.length == "2") && (object.children(0).children(1).children(1).children(0).value == ""))
				{
					object.children(0).children(1).children(0).children(0).value = jobQty[0];
					object.children(0).children(1).children(1).children(0).value = jobQty[1];
					object.children(0).children(1).children(2).children(0).value = jobQty[2];
					object.children(0).children(1).children(3).children(0).value = jobQty[3];
					object.children(0).children(1).children(4).children(0).value = jobQty[4];
				}
				else
				{
					var newNode = object.children(0).children(i).cloneNode(true);
					object.children(0).appendChild(newNode);

					object.children(0).children(i).children(0).children(0).value = jobQty[0];
					object.children(0).children(i).children(1).children(0).value = jobQty[1];
					object.children(0).children(i).children(2).children(0).value = jobQty[2];
					object.children(0).children(i).children(3).children(0).value = jobQty[3];
					object.children(0).children(i).children(4).children(0).value = jobQty[4];
				}
			}
		}
	}

	function delRow()
	{
		var obj=document.getElementById("addBuffer");
		var temp=document.forms[0];
		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if ((obj.children(0).children(i).children(0).children(0).checked) && (obj.children(0).children.length > 2))
			{
					obj.children(0).removeChild(obj.children(0).children(i));
					i = 1;
			}
			 if ((obj.children(0).children(1).children(0).children(0).checked) && (obj.children(0).children.length == 2))
			{
				obj.children(0).children(1).children(0).children(0).value = "";
				obj.children(0).children(1).children(1).children(0).value = "";
				obj.children(0).children(1).children(2).children(0).value = "";
				obj.children(0).children(1).children(3).children(0).value = "";
				obj.children(0).children(1).children(4).children(0).value = "";
				obj.children(0).children(1).children(0).children(0).checked = false;
				obj.children(0).children(0).children(0).children(0).checked = false;
			}
		}
	}

</script>
</head>

<body onload="loadTables(); reloadValues(); ">
<html:form action="frmRewrkLogAdd" focus="wrkOrdNum">
<html:hidden property="formAction"/>
<html:hidden property="woJbStatId" /><!-- To maintain the WorkOrderStatusId -->
<html:hidden property="rwkLogDetails" /><!-- To maintain the Rework Log Details -->
<html:hidden property="hidJbQtySno" /><!-- To maintain Job Qty Sno Details -->
<html:hidden property="jobs" value= "<%= frm.getJobs() %>"/> <!-- To maintain Jobs in the Buffer -->
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
<td>
	<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
	<tr>
		<td><bean:message key="prodacs.reworklog.createnewreworklog"/></td>
	</tr>
	</table><br>
		<table width="100" cellspacing="0" cellpadding="0" align="right">
		<tr> 
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1027" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
	 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Rework Info'; return true"  onMouseOut="window.status=''; return true" resourceId="27" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
		</tr>
		</table>
	<br>
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="70" class="FieldTitle"><html:select property="wrkOrdNum" styleClass="Combo" onchange="loadJobName();">
			<html:option value="0">-- Choose WorkOrder --</html:option>
			<html:options collection="hmWrkOrdList" property="key" labelProperty="value"/>
			</html:select></td>
			<td width="120" class="FieldTitle"><bean:message key="prodacs.job.jobname"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="jobName" styleClass="Combo" onchange="loadJobQtyDetails();">
			<html:option value="0">-- Choose JobName --</html:option>
			<html:options collection="hmJobName" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>
		<tr>
			<td class="FieldTitle"><bean:message key="prodacs.rework.reworkcategory"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="reworkCategory" styleClass="Combo" onchange="loadRwkReason();">
			<html:option value="0">-- Choose Category --</html:option>
			<html:options collection="hmRwkCategory" property="key" labelProperty="value"/>
			</html:select></td>
			<td class="FieldTitle"><bean:message key="prodacs.rework.reworkreason"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="reworkReason" styleClass="Combo">
			<html:option value="0">-- Choose Reason --</html:option>
			<html:options collection="hmRwkReason" property="key" labelProperty="value"/>
			</html:select></td>
		</tr>
		<tr> 
			<td class="FieldTitle"><bean:message key="prodacs.reworklog.authorizedby"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td colspan="4" class="ViewData"><html:text property="authorizedBy" styleClass="TextBox" size="25" maxlength="25"/></td>
		</tr>
		</table>
	<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblJobType" style="display:none">
		<tr>
		<td>
			<fieldset id="FieldSet">
			<legend class="FieldTitle">Job Quantity Details</legend><br>
			<table width="100%" cellpadding="1" cellspacing="0" id="tblJobQtyDet">
			<tr> 
				<td width="25" class="Header">&nbsp;</td>
				<td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
				<td width="300" class="Header"><bean:message key="prodacs.reworklog.productionseEnteredoperations"/></td>
				<td class="Header"><bean:message key="prodacs.workorder.completedoperations"/></td>
			</tr>
			<logic:iterate id="bt1" name="vecJbQtyDet" indexId="count">
			<tr> 
			<%
				if(count.intValue()%2 == 0) 
				{
		     	%>
				<td class="TableItems"><input name="radioBtn" type="radio" id="RadioValue" value='<bean:write name="bt1" property="jbStatId"/>' onclick="loadJobOperations();"></td>
				<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="jobQtySno" value='<bean:write name="bt1" property="jbQtySno"/>'></td>
				<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="prodEntdOpn" value='<bean:write name="bt1" property="unPostedProdnSnos"/>'></td>
				<td class="TableItems"><input type="text" class="TextBoxFull" readonly name="cmptdOpn" value='<bean:write name="bt1" property="postedProdnSnos"/>'></td>
			<%
				}
				else
				{
			%>
				<td class="TableItems2"><input name="radioBtn" type="radio" id="RadioValue" value='<bean:write name="bt1" property="jbStatId"/>' onclick="loadJobOperations();"></td>
				<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="jobQtySno" value='<bean:write name="bt1" property="jbQtySno"/>'></td>
				<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="prodEntdOpn" value='<bean:write name="bt1" property="unPostedProdnSnos"/>'></td>
				<td class="TableItems2"><input type="text" class="TextBoxFull" readonly name="cmptdOpn" value='<bean:write name="bt1" property="postedProdnSnos"/>'></td>
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
	<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblOprType" style="display:none">
		<tr>
		<td>
			<fieldset id="FieldSet">
			<legend class="FieldTitle">Operation Details</legend><br>
			<table width="100%" cellpadding="0" cellspacing="0" id="oprType">
			<tr> 
				<td width="25" class="Header"><input name="CheckAll" type="checkbox" id="CheckAll" value="checkbox" onClick="checkAll(document.ReworkLogAdd)"></td>
				<td width="120" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
				<td width="300" class="Header"><bean:message key="prodacs.job.operationname"/></td>
				<td class="Header"><bean:message key="prodacs.reworklog.productionenteredcompleted"/></td>
			</tr>
			<tr> 
				<td class="TableItems"><input name="CheckValue" type="checkbox" /></td>
				<td class="TableItems"><input type="text" name="opnSno" readonly class="TextBoxFull"/></td>
				<td class="TableItems"><input type="text" name="opnName" readonly class="TextBoxFull"/></td>
				<td class="TableItems"><input type="text" name="prodEntered" readonly class="TextBoxFull"/></td>
				<td><input type="hidden" name="jobQtySno"/></td>
			</tr>
			</table>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBgLft">
			<tr> 
				<td><html:button property="createreworklog" styleClass="Button" value="Next" onclick="moveToBuffer();"/></td><!-- addReworkLog(); -->
			</tr>
			</table>
			</fieldset>
		</td>
		</tr>
		</table>
		<br>
		<table width="100%" cellpadding="0" cellspacing="0" id="tblBuffer" style="display:none">
		<tr>
		<td>
			<fieldset id="FieldSet">
			<legend class="FieldTitle">Selected Operations</legend><br>
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="TopLnk">[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
				<td><br></td>
			</tr>
			</table>
			<table width="100%" cellpadding="0" cellspacing="0" id="addBuffer">
			<tr>
				<td width="25" class="Header"><input name="CheckAllDym" type="checkbox" id="CheckAllDym" value="checkbox" onClick="checkAllDym(document.ReworkLogAdd)"></td>
				<td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
				<td width="125" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
				<td width="265" class="Header"><bean:message key="prodacs.job.operationname"/></td>
				<td class="Header"><bean:message key="prodacs.reworklog.productionenteredcompleted"/></td>
			</tr>
			<tr>
				<td class="TableItems"><input name="CheckValue" type="checkbox" /></td>
				<td class="TableItems"><input type="text" name="jobQtySno" readonly class="TextBoxFull"/></td>
				<td class="TableItems"><input type="text" name="opnSno" readonly class="TextBoxFull"/></td>
				<td class="TableItems"><input type="text" name="opnName" readonly class="TextBoxFull"/></td>
				<td class="TableItems"><input type="text" name="prodEntered" readonly class="TextBoxFull"/></td>
			</tr>
			</table>
			</fieldset>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
			<tr> 
				<td><html:button property="createreworklog" styleClass="Button" value="Add ReworkLog" onclick="addReworkLog();"/>
			</tr>
			</table>

		</td>
		</tr>
		</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>
