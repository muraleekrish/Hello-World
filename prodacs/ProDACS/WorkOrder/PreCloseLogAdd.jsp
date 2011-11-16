<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.PreCloseLogAddForm" />
<jsp:setProperty name="frm" property="*" /> 

<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.PreCloseDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.JobQtyDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WOJobOpnDetails"%>
<useradmin:userrights resource="1016"/>
<%
	/* To reset all the fields to empty when adding a new entry */
	if (frm.getFormAction().equalsIgnoreCase("addNew"))
	{
		frm.setWoId("");
		frm.setJobId("");
		frm.setWoJbStatId("");
		frm.setJbQtySno("");
		frm.setProEntOpns("");
		frm.setCompOpns("");
		frm.setReworkOpns("");
		frm.setOpenOpns("");
		frm.setOpnDetails("");
		frm.setFlagOpnDet("");
		frm.setPreClsDetVar("");
	}
	if (BuildConfig.DMODE)
		System.out.println("Pre-Close Log Add");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	try 
   {
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionWorkOrderDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		
		HashMap woNo = workOrderObj.getWorkOrderList();
		pageContext.setAttribute("woNo",woNo);
		
		/* For loading WorkOrderId to JobDetails */
		Vector vec_jobName = new Vector();
		HashMap hm_jobName = new HashMap();
		if (!frm.getWoId().equalsIgnoreCase(""))
		{
			vec_jobName = workOrderObj.getJobNameByWorkOrder(Integer.parseInt(frm.getWoId()));
			for (int i = 0; i < vec_jobName.size(); i++)
			{
				WOJobDetails objWOJobDet = new WOJobDetails();
				objWOJobDet = (WOJobDetails) vec_jobName.get(i);
				hm_jobName.put(objWOJobDet.getJobId()+"", objWOJobDet.getJobName());
			}
			pageContext.setAttribute("hm_jobName",hm_jobName);
		}
		else
		{
			pageContext.setAttribute("hm_jobName",hm_jobName);
		}
		
		/* For Loading Details by WoId and JobId */
		Vector vec_jobQtyDetails = new Vector();
		if ((!frm.getWoId().equalsIgnoreCase("")) && (!frm.getJobId().equalsIgnoreCase("")))
		{
			PreCloseDetails objPreCloseDetails = new PreCloseDetails();
			
			/* Setting the WorkOrderId and JobId to PreCloseDetails Object*/
			objPreCloseDetails.setWorkOrderId(Integer.parseInt(frm.getWoId()));
			objPreCloseDetails.setJbId(Integer.parseInt(frm.getJobId()));
			
			/* Get that Back to object as PreCloseDetails */
			objPreCloseDetails = workOrderObj.getForAddNewPreCloseLog(objPreCloseDetails);
			
			vec_jobQtyDetails = objPreCloseDetails.getJobQtyDetails();

			String woJbStatId = "";
			String jbQtySno = "";
			String proEntOpns = "";
			String compOpns = "";
			String reworkOpns = "";
			String openOpns = "";
			String opnDet = "";
			for (int i = 0 ; i < vec_jobQtyDetails.size(); i++)
			{
				JobQtyDetails objJobQtyDetails = new JobQtyDetails();
				objJobQtyDetails = (JobQtyDetails) vec_jobQtyDetails.get(i);
				Vector vec_opnDet = new Vector();
				if (i != 0)
				{
					opnDet = opnDet + "^";
				}
				if (i == 0)
				{
					woJbStatId = objJobQtyDetails.getWoJbStatId() + "";
					jbQtySno = objJobQtyDetails.getJobQtySno() + "";
					proEntOpns = objJobQtyDetails.getWOJobProductionEnteredOpnString();
					compOpns = objJobQtyDetails.getWOJobCmpltdOpnString();
					reworkOpns = objJobQtyDetails.getWOJobReworkOpnString();
					openOpns = objJobQtyDetails.getWOJobOpenOpnString();

					/* For Getting the Operation details */
					vec_opnDet = objJobQtyDetails.getWOJobOpenOpnDetails();
					for (int j = 0; j < vec_opnDet.size(); j++)
					{
						WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
						objWOJobOpnDetails = (WOJobOpnDetails) vec_opnDet.get(j);
						if (j == 0)
						{
							opnDet = opnDet + objJobQtyDetails.getJobQtySno();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getWoJbOpnId();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnSerialNo();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnName();
							opnDet = opnDet + "~" + objJobQtyDetails.getWoJbStatId();
						}
						else
						{
							opnDet = opnDet + "~" + objJobQtyDetails.getJobQtySno();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getWoJbOpnId();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnSerialNo();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnName();
							opnDet = opnDet + "~" + objJobQtyDetails.getWoJbStatId();
						}
					}
				}
				else
				{
					woJbStatId = woJbStatId + "~" + objJobQtyDetails.getWoJbStatId() + "";
					jbQtySno = jbQtySno + "~" + objJobQtyDetails.getJobQtySno() + "";
					proEntOpns = proEntOpns + "~" + objJobQtyDetails.getWOJobProductionEnteredOpnString();
					compOpns = compOpns + "~" + objJobQtyDetails.getWOJobCmpltdOpnString();
					reworkOpns = reworkOpns + "~" + objJobQtyDetails.getWOJobReworkOpnString();
					openOpns = openOpns + "~" + objJobQtyDetails.getWOJobOpenOpnString();

					/* For Getting the Operation details */
					vec_opnDet = objJobQtyDetails.getWOJobOpenOpnDetails();
					for (int j = 0; j < vec_opnDet.size(); j++)
					{
						WOJobOpnDetails objWOJobOpnDetails = new WOJobOpnDetails();
						objWOJobOpnDetails = (WOJobOpnDetails) vec_opnDet.get(j);
						if (j == 0)
						{
							opnDet = opnDet + objJobQtyDetails.getJobQtySno();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getWoJbOpnId();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnSerialNo();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnName();
							opnDet = opnDet + "~" + objJobQtyDetails.getWoJbStatId();
						}
						else
						{
							opnDet = opnDet + "~" + objJobQtyDetails.getJobQtySno();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getWoJbOpnId();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnSerialNo();
							opnDet = opnDet + "~" + objWOJobOpnDetails.getOpnName();
							opnDet = opnDet + "~" + objJobQtyDetails.getWoJbStatId();
						}
					}
				}
			}

			if (BuildConfig.DMODE)
				System.out.println("opnDet: "+opnDet);

			frm.setWoJbStatId(woJbStatId);
			frm.setJbQtySno(jbQtySno);
			frm.setProEntOpns(proEntOpns);
			frm.setCompOpns(compOpns);
			frm.setReworkOpns(reworkOpns);
			frm.setOpenOpns(openOpns);
			frm.setOpnDetails(opnDet);
			
			/* Setting the Flag */
			frm.setFlagOpnDet("1");
		}
		
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error in PreCloseLogAdd.jsp");
			e.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.workorder.precloselog"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script language="Javascript" type="text/Javascript">

	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/PreCloseLogList.jsp';
		getPreCloseDet();
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/PreCloseLogAdd.jsp?formAction=addNew';
		document.forms[0].submit();
	}

	/* To Set the Operation Table display to block */
	function chWrkJob(val)
	{
		if(val > "0")
		{
			tblWrkOrdJobType.style.display = "block";
			tblJobQty.style.display = "none";
			tblPreClose.style.display = "none";
		}
	}

	/* To load Job name by WorkOrderId*/
	function loadJobName()
	{
		var temp = document.forms[0];
		if (temp.preCloseLogWOHash.value != "0")
		{
			temp.woId.value = temp.preCloseLogWOHash.value;
			temp.formAction.value = "load";
			getPreCloseDet();
			temp.submit();
		}
	}

	/* To Load Operation Details by WorkOrderId and JobId */
	function loadPreCloseOpers()
	{
		var temp = document.forms[0];
		if (temp.preCloseLogJobName.options[temp.preCloseLogJobName.selectedIndex].value != "0")
		{
			temp.jobId.value = temp.preCloseLogJobName.options[temp.preCloseLogJobName.selectedIndex].value;
			temp.formAction.value = "load";
			getPreCloseDet();
			temp.submit();
		}
	}
	
	/* To Load Operation Details by Onload Event */
	function loadOperDet()
	{
		var temp = document.forms[0];
		if (temp.flagOpnDet.value == "1")
		{
			chWrkJob(1);
			temp.flagOpnDet.value = "0";
		}
		var obj = document.getElementById("tblWrkOrdJobType");

		/* Splitting the WO Job Status Id */
		var jbStatId = new String(temp.woJbStatId.value);
		var arJbStatId = jbStatId.split("~");

		/* Splitting the Job Qty Serial No. */
		var qtySno = new String(temp.jbQtySno.value);
		var arJbQtySno = qtySno.split("~");

		/* Splitting the Production Entered Operations */
		var proEnOpn = new String(temp.proEntOpns.value);
		var arProEnOpn = proEnOpn.split("~");

		/* Splitting the Completed Operations */
		var comOpn = new String(temp.compOpns.value);
		var arComOpn = comOpn.split("~");

		/* Splitting the Rework Operations */
		var rewrkOpn = new String(temp.reworkOpns.value);
		var arRewrkOpn = rewrkOpn.split("~");

		/* Splitting the Open Operations */
		var openOpn = new String(temp.openOpns.value);
		var arOpenOpn = openOpn.split("~");

		/* Splitting the Operation Group Details */
		var opnDetail = new String(temp.opnDetails.value); 
		var arOpnDetail = opnDetail.split("^");
		
		//alert ("OpnDetails: "+arOpnDetail);
		for (var i = 0; i < arJbStatId.length; i++)
		{
			if (i == "0") // Is First Row
			{
				obj.children(0).children(1).children(0).children(0).value = arJbStatId[i];
				obj.children(0).children(1).children(1).children(0).value = arJbQtySno[i];
				obj.children(0).children(1).children(2).children(0).value = arProEnOpn[i];
				obj.children(0).children(1).children(3).children(0).value = arComOpn[i];
				obj.children(0).children(1).children(4).children(0).value = arRewrkOpn[i];
				obj.children(0).children(1).children(5).children(0).value = arOpenOpn[i];
				obj.children(0).children(1).children(6).children(0).value = arOpnDetail[i];
			}
			else
			{
				var newNode = obj.children(0).children(1).cloneNode(true);
				obj.children(0).appendChild(newNode);
				var len = obj.children(0).children.length;

				/* Clear the text Fields */
				obj.children(0).children(len-1).children(0).children(0).value = "";
				obj.children(0).children(len-1).children(1).children(0).value = "";
				obj.children(0).children(len-1).children(2).children(0).value = "";
				obj.children(0).children(len-1).children(3).children(0).value = "";
				obj.children(0).children(len-1).children(4).children(0).value = "";
				obj.children(0).children(len-1).children(5).children(0).value = "";
				obj.children(0).children(len-1).children(6).children(0).value = "";

				obj.children(0).children(len-1).children(0).children(0).value = arJbStatId[i];
				obj.children(0).children(len-1).children(1).children(0).value = arJbQtySno[i];
				obj.children(0).children(len-1).children(2).children(0).value = arProEnOpn[i];
				obj.children(0).children(len-1).children(3).children(0).value = arComOpn[i];
				obj.children(0).children(len-1).children(4).children(0).value = arRewrkOpn[i];
				obj.children(0).children(len-1).children(5).children(0).value = arOpenOpn[i];
				obj.children(0).children(len-1).children(6).children(0).value = arOpnDetail[i];
			}
		}
	}

	function addPreCloseList()
	{
		tblWrkOrdJobType.style.display = "block";
		tblJobQty.style.display = "block";
		tblPreClose.style.display = "block";

		var temp = document.forms[0];
		var object = document.getElementById("tbl2OpnDet");
		var obj = document.getElementById("tblPreCloseLog");
		var k = 0;
		var count = 0;

		/* Checking for empty additions */
		for (var i = 0; i < object.children.length; i++)
		{
			if (object.children(i).children(0).children(0).children(0).checked)
			{
				count++;
			}
		}
		if (count == 0)
		{
			tblPreClose.style.display = "none";
			alert("No Operations Selected!");
			return false;
		}

		/* If the Operation's are empty then can't preclose */
		if ((object.children.length == 1) && (object.children(0).children(0).children(0).children(0).value == ""))
		{
			tblPreClose.style.display = "none";
			alert ("The Selected Quantity has no In-Complete Operations!");
			return false;
		}

		/* Inserting the checked Rows */
		if (temp.CheckValueDym.length > 1)
		{
			for (var i = 0; i < temp.CheckValueDym.length; i++)
			{
				if (temp.CheckValueDym[i].checked)
				{
					/* Checking the row is shifted or not */
					for (var t = 0; t < obj.children.length; t++)
					{
						var str1 = obj.children(t).children(0).children(0).children(0).value;
						var str2 = object.children(i).children(0).children(0).children(0).value;
						if (str1 == str2)
						{
							alert ("The Selected item is already Shifted!");
							temp.CheckValueDym[i].checked = false;
							return false;
						}
					}

					/* Checking any Empty Rows are Entered */
					for (var j = 0; j < object.children.length; j++)
					{
						if ((object.children(j).children(0).children(1).children(0).value == "") ||
							(object.children(j).children(0).children(2).children(0).value == "") ||
							(object.children(j).children(0).children(3).children(0).value == ""))
						{
							alert ("Empty Values are Not allowed!");
							return false;
						}
					}

					if ((obj.children.length == 1) && (k == 0) && (obj.children(0).children(0).children(1).children(0).value == "")
						&& (obj.children(0).children(0).children(2).children(0).value == "") && (obj.children(0).children(0).children(3).children(0).value == ""))
					{
						obj.children(0).children(0).children(0).children(0).value = object.children(i).children(0).children(0).children(0).value;
						obj.children(0).children(0).children(1).children(0).value = object.children(i).children(0).children(3).children(0).value;
						obj.children(0).children(0).children(2).children(0).value = object.children(i).children(0).children(1).children(0).value;
						obj.children(0).children(0).children(3).children(0).value = object.children(i).children(0).children(2).children(0).value;
						obj.children(0).children(0).children(4).children(0).value = object.children(i).children(0).children(4).children(0).value;
					}
					else
					{
						var newNode = obj.children(0).cloneNode(true);
						obj.appendChild(newNode);
						co = obj.children.length - 1;
						obj.children(co).children(0).children(0).children(0).value = object.children(i).children(0).children(0).children(0).value;
						obj.children(co).children(0).children(1).children(0).value = object.children(i).children(0).children(3).children(0).value;
						obj.children(co).children(0).children(2).children(0).value = object.children(i).children(0).children(1).children(0).value;
						obj.children(co).children(0).children(3).children(0).value = object.children(i).children(0).children(2).children(0).value;
						obj.children(co).children(0).children(4).children(0).value = object.children(i).children(0).children(4).children(0).value;
					}
					k++;
				}
			}
		}
		else
		{
				if (temp.CheckValueDym.checked)
				{
					/* Checking the row is shifted or not */
					for (var t = 0; t < obj.children.length; t++)
					{
						var str1 = obj.children(t).children(0).children(0).children(0).value;
						var str2 = object.children(i).children(0).children(0).children(0).value;
						if (str1 == str2)
						{
							alert ("The Selected item is already Shifted!");
							temp.CheckValueDym.checked = false;
							return false;
						}
					}

					/* Checking any Empty Rows are Entered */

					for (var j = 0; j < object.children.length; j++)
					{
						if ((object.children(j).children(0).children(1).children(0).value == "") ||
							(object.children(j).children(0).children(2).children(0).value == "") ||
							(object.children(j).children(0).children(3).children(0).value == ""))
						{
							alert ("Empty Values are Not allowed!");
							return false;
						}
					}

					if ((obj.children.length == 1) && (k == 0) && (obj.children(0).children(0).children(1).children(0).value == "")
						&& (obj.children(0).children(0).children(2).children(0).value == "") && (obj.children(0).children(0).children(3).children(0).value == ""))
					{
						obj.children(0).children(0).children(0).children(0).value = object.children(0).children(0).children(0).children(0).value;
						obj.children(0).children(0).children(1).children(0).value = object.children(0).children(0).children(3).children(0).value;
						obj.children(0).children(0).children(2).children(0).value = object.children(0).children(0).children(1).children(0).value;
						obj.children(0).children(0).children(3).children(0).value = object.children(0).children(0).children(2).children(0).value;
						obj.children(0).children(0).children(4).children(0).value = object.children(0).children(0).children(4).children(0).value;
					}
					else
					{
						var newNode = obj.children(0).cloneNode(true);
						obj.appendChild(newNode);
						co = obj.children.length - 1;
						obj.children(co).children(0).children(0).children(0).value = object.children(0).children(0).children(0).children(0).value;
						obj.children(co).children(0).children(1).children(0).value = object.children(0).children(0).children(3).children(0).value;
						obj.children(co).children(0).children(2).children(0).value = object.children(0).children(0).children(1).children(0).value;
						obj.children(co).children(0).children(3).children(0).value = object.children(0).children(0).children(2).children(0).value;
						obj.children(co).children(0).children(4).children(0).value = object.children(0).children(0).children(4).children(0).value;
					}
					k++;
				}
		}
	}

	function delRow()
	{
		var obj = document.getElementById("tblPreCloseLog");
		var temp = document.forms[0];
		var count = 0;
		if (obj.children.length > 1)
		{
			for (var i = 0; i < obj.children.length; i++)
			{
				if (temp.preClsCheck[i].checked)
				{
					count++;
					obj.removeChild(obj.children(i));
					i = 0;
				}
			}
			if (obj.children.length == 1)
			{
				if (temp.preClsCheck[0].checked)
				{
					obj.children(0).children(0).children(0).children(0).value = "";
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					temp.preClsCheck[0].checked = false;
					count++;
				}
			}
		}
		else
		{
			if (temp.preClsCheck.checked)
			{
				obj.children(0).children(0).children(0).children(0).value = "";
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				temp.preClsCheck.checked = false;
				count++;
			}
		}

		/* Any item's are checked or not */
		if (count == 0)
		{
			alert ("U should Check a Item/s to Delete!");
		}
	}

	function checkAllDymSub()
	{
		var temp = document.forms[0];
		if (temp.CheckAllDymSub.checked)
		{
			if (temp.preClsCheck.length > 1)
			{
				for (var i = 0; i < temp.preClsCheck.length; i++)
				{
					temp.preClsCheck[i].checked = true;
				}
			}
			else if (temp.preClsCheck.checked == "undefined")
			{
				temp.preClsCheck[0].checked = true;
			}
			else 
			{
				temp.preClsCheck.checked = true;
			}
		}
		else
		{
			if (temp.preClsCheck.length > 1)
			{
				for (var i = 0; i < temp.preClsCheck.length; i++)
				{
					temp.preClsCheck[i].checked = false;
				}
			}
			else if (temp.preClsCheck.checked == "undefined")
			{
				temp.preClsCheck[0].checked = false;
			}
			else 
			{
				temp.preClsCheck.checked = false;
			}
		}
	}

	function checkAllDym()
	{
		var temp = document.forms[0];
		if (temp.CheckAllDym.checked)
		{
			if (temp.CheckValueDym.length > 1)
			{
				for (var i = 0; i < temp.CheckValueDym.length; i++)
				{
					temp.CheckValueDym[i].checked = true;
				}
			}
			else if (temp.CheckValueDym.checked == "undefined")
			{
				temp.CheckValueDym[0].checked = true;
			}
			else 
			{
				temp.CheckValueDym.checked = true;
			}
		}
		else
		{
			if (temp.CheckValueDym.length > 1)
			{
				for (var i = 0; i < temp.CheckValueDym.length; i++)
				{
					temp.CheckValueDym[i].checked = false;
				}
			}
			else if (temp.CheckValueDym.checked == "undefined")
			{
				temp.CheckValueDym[0].checked = false;
			}
			else 
			{
				temp.CheckValueDym.checked = false;
			}
		}
	}

	function getPreCloseDet()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblPreCloseLog");

		var preClsDet = "";

		for (var i = 0; i < obj.children.length; i++)
		{
			if (i == 0)
			{
				preClsDet = obj.children(0).children(0).children(1).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(0).children(0).children(2).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(0).children(0).children(3).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(0).children(0).children(4).children(0).value;
			}
			else
			{
				preClsDet = preClsDet + "~" + obj.children(i).children(0).children(1).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(i).children(0).children(2).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(i).children(0).children(3).children(0).value;
				preClsDet = preClsDet + "~" + obj.children(i).children(0).children(4).children(0).value;
			}
		}
		if (preClsDet != "~~~")
		{
			temp.preClsDetVar.value = preClsDet;	
		}
		else
		{
			temp.preClsDetVar.value = "0";
		}
		//alert (preClsDet);
	}

	function loadPreCloseDetails()
	{
		var temp = document.forms[0];
		var preClose = new String(temp.preClsDetVar.value);
		var obj = document.getElementById("tblPreCloseLog");

		if ((preClose != "0"))
		{
			//alert (temp.preClsDetVar.value);
			var arPreClose = preClose.split("~");
			
			//tblPreClose.style.display = "block";
			tblPreClose.style.display = "none";
			var row = 0;
			for (var i = 0; i < arPreClose.length; i = i + 4)
			{
				if (i == 0)
				{
					obj.children(0).children(0).children(1).children(0).value = arPreClose[i];
					obj.children(0).children(0).children(2).children(0).value = arPreClose[i+1];
					obj.children(0).children(0).children(3).children(0).value = arPreClose[i+2];
					obj.children(0).children(0).children(4).children(0).value = arPreClose[i+3];
					row++;
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);

					obj.children(row).children(0).children(1).children(0).value = arPreClose[i];
					obj.children(row).children(0).children(2).children(0).value = arPreClose[i+1];
					obj.children(row).children(0).children(3).children(0).value = arPreClose[i+2];
					obj.children(row).children(0).children(4).children(0).value = arPreClose[i+3];
					row++;
				}
			}
		}
	}

	function submitItem()
	{
		var temp = document.forms[0];
		getPreCloseDet();
		if (temp.preCloseReason.value == "")
		{
			alert ("Pre-Close Reason is Mandatory!");
			return false;
		}
		if (temp.preClsDetVar.value != "0")
		{
			temp.formAction.value = "add";
			temp.submit();
		}
		else
		{
			alert ("Empty Values cannot be Allowed! Try Other Jobs! ");
			return false;
		}
	}

	function checkJobs()
	{
		temp = document.forms[0];
		var obj = document.getElementById("tblPreCloseLog");
		if ('<%= request.getParameter("checkJob") %>' != temp.preCloseLogJobName.options[temp.preCloseLogJobName.selectedIndex].value)
		{
			/* Deleting all the values previously entered when JobName Changes*/

			if (obj.children.length > 1)
			{
				for (var i = 0; i < obj.children.length; i++)
				{
					obj.removeChild(obj.children(i));
					i = 0;
				}
				if (obj.children.length == 1)
				{
					obj.children(0).children(0).children(0).children(0).value = "";
					obj.children(0).children(0).children(1).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
				}
			}
			else
			{
				obj.children(0).children(0).children(0).children(0).value = "";
				obj.children(0).children(0).children(1).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
			}
		}
	}

	function moveData(val)
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblWrkOrdJobType");
		var object = document.getElementById("tblPreCloseLog");

		if (obj.children(0).children.length > 1)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					temp.checkJob.value = obj.children(0).children(i).children(0).children(0).value;
					temp.checkedJob.value = obj.children(0).children(i).children(6).children(0).value;
				}
			}
		}
		else
		{
			if (obj.children(0).children(0).children(0).children(0).checked)
			{
				temp.checkJob.value = obj.children(0).children(1).children(0).children(0).value;
				temp.checkedJob.value = obj.children(0).children(1).children(6).children(0).value;
			}
		}
		for (var i = 0; i < object.children.length; i++)
		{
			if (object.children(i).children(0).children(1).children(0).value != "")
			{
				if (i != 0)
					temp.finalJobs.value = temp.finalJobs.value + "~";
				temp.finalJobs.value = temp.finalJobs.value + object.children(i).children(0).children(0).children(0).value+"~"+
										object.children(i).children(0).children(1).children(0).value+"~"+
										object.children(i).children(0).children(2).children(0).value+"~"+
										object.children(i).children(0).children(3).children(0).value+"~"+
										object.children(i).children(0).children(4).children(0).value;
			}
		}
		temp.formAction.value = "moveData";
		temp.submit();
	}

	function loadOpnDetByTbl()	
	{
		var temp = document.forms[0];
		var obj = document.getElementById("tblWrkOrdJobType");
		var object = document.getElementById("tbl2OpnDet");
		var obj1 = document.getElementById("tblPreCloseLog");
		if((temp.finalJobs.value == "") || ('<%= request.getParameter("finalJobs") %>' == ""))
			tblPreClose.style.display = 'none';
		else if (('<%= request.getParameter("finalJobs") %>' != "") && (temp.formAction.value == "moveData"))
			tblPreClose.style.display = 'block';
		var tempString = '<%= request.getParameter("finalJobs") %>';
		if ((tempString != "") && ((temp.formAction.value == "" || temp.formAction.value == "moveData")))
		{
			var temOpnDets = ('<%= request.getParameter("finalJobs") %>').split("~");
			var x = 0;
			for (var m = 0; m < temOpnDets.length; m = m + 5)
			{
				if (x == 0)
				{
					obj1.children(x).children(0).children(0).children(0).value = temOpnDets[m];
					obj1.children(x).children(0).children(1).children(0).value = temOpnDets[m+1];
					obj1.children(x).children(0).children(2).children(0).value = temOpnDets[m+2];
					obj1.children(x).children(0).children(3).children(0).value = temOpnDets[m+3];
					obj1.children(x).children(0).children(4).children(0).value = temOpnDets[m+4];
				}
				else
				{
					var newNode = obj1.children(0).cloneNode(true);
					obj1.appendChild(newNode);

					obj1.children(x).children(0).children(3).children(0).value = "";
					obj1.children(x).children(0).children(0).children(0).value = "";
					obj1.children(x).children(0).children(1).children(0).value = "";
					obj1.children(x).children(0).children(2).children(0).value = "";
					obj1.children(x).children(0).children(4).children(0).value = "";

					obj1.children(x).children(0).children(0).children(0).value = temOpnDets[m];
					obj1.children(x).children(0).children(1).children(0).value = temOpnDets[m+1];
					obj1.children(x).children(0).children(2).children(0).value = temOpnDets[m+2];
					obj1.children(x).children(0).children(3).children(0).value = temOpnDets[m+3];
					obj1.children(x).children(0).children(4).children(0).value = temOpnDets[m+4];
				}
				x++;
			}
		}
		else
		{
			obj1.children(0).children(0).children(0).children(0).value = "";
			obj1.children(0).children(0).children(1).children(0).value = "";
			obj1.children(0).children(0).children(2).children(0).value = "";
			obj1.children(0).children(0).children(3).children(0).value = "";
			obj1.children(0).children(0).children(4).children(0).value = "";
			tblPreClose.style.display = 'none';
		}

		// Deleting the Rows for entering new Operation 
		if (object.children.length > 1)
		{
			for (var i = 0; i < object.children.length; i++)
			{
				object.removeChild(object.children(i));
				i = 0;
			}
		}
		else
		{
			object.children(0).children(0).children(3).children(0).value = "";
			object.children(0).children(0).children(0).children(0).value = "";
			object.children(0).children(0).children(1).children(0).value = "";
			object.children(0).children(0).children(2).children(0).value = "";
			object.children(0).children(0).children(4).children(0).value = "";
		}

		for (var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).value == '<%= request.getParameter("checkJob") %>')
			{
				obj.children(0).children(i).children(0).children(0).checked = true;
			}
		}
		if(temp.formAction.value == "moveData")
		{
			tblJobQty.style.display = "block";
			if ('<%= request.getParameter("checkedJob") %>' != "")
			{
				var temOpnDet = '<%= request.getParameter("checkedJob") %>';
				var arTemOpnDet = temOpnDet.split("~");

				//If openOperations empty or Not 
				if (arTemOpnDet == "")
				{
					object.children(0).children(0).children(3).children(0).value = "";
					object.children(0).children(0).children(0).children(0).value = "";
					object.children(0).children(0).children(1).children(0).value = "";
					object.children(0).children(0).children(2).children(0).value = "";
					object.children(0).children(0).children(4).children(0).value = "";
				}
				
				//Updating the field values to appropriate Fields 
				var k = 0;
				for (var j = 0; j < arTemOpnDet.length; j = j + 5)
				{
					if (k == 0)
					{
						object.children(k).children(0).children(3).children(0).value = arTemOpnDet[j];
						object.children(k).children(0).children(0).children(0).value = arTemOpnDet[j+1];
						object.children(k).children(0).children(1).children(0).value = arTemOpnDet[j+2];
						object.children(k).children(0).children(2).children(0).value = arTemOpnDet[j+3];
						object.children(k).children(0).children(4).children(0).value = arTemOpnDet[j+4];
					}
					else
					{
						var newNode = object.children(0).cloneNode(true);
						object.appendChild(newNode);

						object.children(k).children(0).children(3).children(0).value = "";
						object.children(k).children(0).children(0).children(0).value = "";
						object.children(k).children(0).children(1).children(0).value = "";
						object.children(k).children(0).children(2).children(0).value = "";
						object.children(k).children(0).children(4).children(0).value = "";

						object.children(k).children(0).children(3).children(0).value = arTemOpnDet[j];
						object.children(k).children(0).children(0).children(0).value = arTemOpnDet[j+1];
						object.children(k).children(0).children(1).children(0).value = arTemOpnDet[j+2];
						object.children(k).children(0).children(2).children(0).value = arTemOpnDet[j+3];
						object.children(k).children(0).children(4).children(0).value = arTemOpnDet[j+4];
					}
					k++;
				}
			}
		}
	}

</script>
</head>

<body onload="loadOperDet(); loadPreCloseDetails(); checkJobs(); loadOpnDetByTbl();">
<html:form action="frmPreClsLogAdd">
<html:hidden property="formAction"/>

<html:hidden property="woId" value="<%= frm.getWoId() %>"/>
<html:hidden property="jobId" value="<%= frm.getJobId() %>"/>

<html:hidden property="woJbStatId" value="<%= frm.getWoJbStatId() %>"/>
<html:hidden property="jbQtySno" value="<%= frm.getJbQtySno() %>"/>
<html:hidden property="proEntOpns" value="<%= frm.getProEntOpns() %>"/>
<html:hidden property="compOpns" value="<%= frm.getCompOpns() %>"/>
<html:hidden property="reworkOpns" value="<%= frm.getReworkOpns() %>"/>
<html:hidden property="openOpns" value="<%= frm.getOpenOpns() %>"/>
<html:hidden property="opnDetails" value="<%= frm.getOpnDetails() %>"/>
<html:hidden property="flagOpnDet" value="<%= frm.getFlagOpnDet() %>"/>
<html:hidden property="preClsDetVar" value="<%= frm.getPreClsDetVar() %>"/>
<input type="hidden" name="checkJob"/>
<input type="hidden" name="checkedJob"/>
<input type="hidden" name="finalJobs"/>
  <table width="100%" cellspacing="0" cellpadding="10">
    <tr>
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.workorder.precloselog"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1016" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List PreCloseLog Info'; return true"  onMouseOut="window.status=''; return true" resourceId="16" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
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
            <td width="120" class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td width="200" class="FieldTitle"><html:select property="preCloseLogWOHash" styleClass="Combo" onchange="loadJobName();">
                <html:option value="0">-- WorkOrder No --</html:option>
                <html:options collection="woNo" property="key" labelProperty="value"/>
              </html:select></td>
            <td width="75" class="FieldTitle"><bean:message key="prodacs.job.jobname"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:select property="preCloseLogJobName" styleClass="Combo" onchange="chWrkJob(this.value); loadPreCloseOpers();">
                <html:option value="0">-- Choose Job Name --</html:option>
                <html:options collection="hm_jobName" property="key" labelProperty="value"/>
              </html:select></td>
          </tr>
         </table>
         <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
          	<td class="FieldTitle"><bean:message key="prodacs.workorder.preclosereason"/><span class="mandatory">*</span> : 
            &nbsp;<html:text property="preCloseReason" styleClass="TextBox" size="50" maxlength="50" /></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="tblWrkOrdJobType" style="display:none">
		<tr>
			<td width="25" class="Header">&nbsp;</td>
			<td width="125" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.workorder.productionenteredoperations"/></td>
			<td width="150" class="Header"><bean:message key="prodacs.workorder.completedoperations"/></td>
			<td width="135" class="Header"><bean:message key="prodacs.workorder.reworkoperations"/></td>
			<td width="130" class="Header"><bean:message key="prodacs.workorder.incompleteoperations"/></td>
		</tr>
		<tr>
			<td width="26" class="TableItems"><input name="RadioValue" type="radio" id="RadioValue" onclick="moveData(this.value);"></td>
			<td width="126" class="TableItems"><input readonly name="qtySno" class="TextBoxFull" maxlength="10"></td>
			<td width="151" class="TableItems"><input readonly name="prEnOp" class="TextBoxFull" maxlength="10"></td>
			<td width="151" class="TableItems"><input readonly name="comOpn" class="TextBoxFull" maxlength="10"></td>
			<td width="136" class="TableItems"><input readonly name="reOpn" class="TextBoxFull" maxlength="10"></td>
			<td width="131" class="TableItems"><input readonly name="opOpn" class="TextBoxFull" maxlength="10"></td>
			<td><input type="hidden" name="hidOprDet"></td>
		</tr>
		</table>
        <br>
		<br>
        <table width="100%" cellpadding="0" cellspacing="0" id="tblJobQty" style="display:none">
          <tr>
            <td>
              <table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
					  <td width="25" class="Header"><input name="CheckAllDym" type="checkbox" id="CheckAllDym" onClick="checkAllDym(document.PreCloseLogAdd)" value="checkbox"></td>
					  <td width="150" class="Header"><bean:message key="prodacs.workorder.operationserielno"/></td>
					  <td class="Header"><bean:message key="prodacs.job.operationname"/></td>
					</tr>
			  </table>
			  <table width="100%" cellspacing="0" cellpadding="0" id="tbl2OpnDet">
                <tr> 
                  <td width="22" class="TableItems"><input name="CheckValueDym" type="checkbox" id="CheckValueDym"></td>
                  <td width="147" class="TableItems"><input readonly name="opnSerialno" class="TextBoxFull" maxlength="10"></td>
                  <td class="TableItems"><input readonly name="opnName" class="TextBoxFull" maxlength="10"></td>
				  <td><input type="hidden" name="opnId"/></td>
				  <td><input type="hidden" name="opnStatusId"/></td>
                </tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
                <tr>
                  <td><html:button property="addOperation" styleClass="Button" onclick="addPreCloseList();">Add</html:button></td>
                </tr>
              </table></td>
          </tr>
        </table> 
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="tblPreClose" style="display:none">
          <tr>
		  <td>
			<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.workorder.joboperationdetails"/></legend>
            <table width="100%" cellspacing="0" cellpadding="5">
               <tr> 
               	<td class="TopLnk">
               	[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
               </tr>
			</table>
            
            <table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
					  <td width="25" class="Header"><input name="CheckAllDymSub" type="checkbox" id="CheckAllDymSub" onClick="checkAllDymSub(document.PreCloseLogAdd)" value="checkbox"></td>
					  <td width="100" class="Header"><bean:message key="prodacs.workorder.jobqtysno"/></td>
					  <td width="200" class="Header"><bean:message key="prodacs.workorder.preclosedoperationsno"/></td>
					  <td class="Header"><bean:message key="prodacs.workorder.preclosedoperationname"/></td>
					</tr>
				</table>
				<table width="100%" cellspacing="0" cellpadding="0" id="tblPreCloseLog">
					<tr> 
					  <td width="22" class="TableItems"><input type="checkbox" name="preClsCheck" value="checkbox"></td>
					  <td width="97" class="TableItems"><input readonly name="preClsQtySno" class="TextBoxFull" maxlength="10"></td>
					  <td width="197" class="TableItems"><input readonly name="preClsOpnSno" class="TextBoxFull" maxlength="10"></td>
					  <td class="TableItems"><input readonly name="preClsOpnName" class="TextBoxFull" maxlength="10"></td>
					  <td><input type="hidden" name="preClsStatusId"/></td>
					</tr>
              </table>
              <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
                <tr>
                  <td><html:button property="remove" styleClass="Button" onclick="submitItem();">Pre-Close All</html:button>
                  </td>
                </tr>
              </table>
			  </fieldset>
			  </td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
