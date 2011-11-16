<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.workorder.WorkOrderEditForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionWorkOrderDetailsManager"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.JobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.OperationDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails"%>
<%@ page import="com.savantit.prodacs.util.CastorXML"%>
<%@ page import="com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours"%>

<useradmin:userrights resource="1013"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Work Order Edit");
    StandardHours objStdHours = new StandardHours();
    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	WorkOrderDetails objWrkOrdDetails = new WorkOrderDetails();
	JobDetails objJobDet = null;
	String stModVal[];
	if (BuildConfig.DMODE)
		System.out.println("FormAction: "+frm.getFormAction());
	try 
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionWorkOrderDetailsManagerBean");
		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionWorkOrderDetailsManagerHome woHomeObj = (SessionWorkOrderDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionWorkOrderDetailsManagerHome.class);
		SessionWorkOrderDetailsManager workOrderObj = (SessionWorkOrderDetailsManager)PortableRemoteObject.narrow(woHomeObj.create(),SessionWorkOrderDetailsManager.class);
		

		/* For Customer Type */
		HashMap customerType = workOrderObj.getCustomerTypes();
		pageContext.setAttribute("customerType",customerType);

		/* For Customer Name */
		HashMap customerName = new HashMap();
		if (BuildConfig.DMODE)
			System.out.println("CustomerTypeId: "+ frm.getWoCustomerType());
		if (!frm.getWoCustomerType().equalsIgnoreCase("0"))
		{
			customerName = workOrderObj.getCustomerNameByType(Integer.parseInt(frm.getWoCustomerType()));
			pageContext.setAttribute("customerName",customerName);
		}
		else
		{
			pageContext.setAttribute("customerName",customerName);
		}

		/* For General Name */
		HashMap generalName = new HashMap();
		if (frm.getFormAction().equalsIgnoreCase("modify"))
		{
			/*For Details Objects */
			objWrkOrdDetails = workOrderObj.getWorkOrderDetails(frm.getId());

			generalName = workOrderObj.getGeneralNameByCustomer(objWrkOrdDetails.getCustId());
			pageContext.setAttribute("generalName",generalName);
			
			customerName = workOrderObj.getCustomerNameByType(objWrkOrdDetails.getCustTypeId());
			pageContext.setAttribute("customerName",customerName);
		}
		
		if (BuildConfig.DMODE)
			System.out.println("CustomerId: "+ frm.getWoCustomerName());
		if ((!frm.getWoCustomerName().equalsIgnoreCase("0")))
		{
			generalName = workOrderObj.getGeneralNameByCustomer(Integer.parseInt(frm.getWoCustomerName()));
			pageContext.setAttribute("generalName",generalName);
		}
		else
		{
			pageContext.setAttribute("generalName",generalName);
		}
		
		/* For Job Name */
		Vector vec_jobName = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("GeneralId: "+ frm.getWoGenName());
		if ((!frm.getWoGenName().equalsIgnoreCase("-1")))
		{
			vec_jobName = workOrderObj.getJobByGeneralName(Integer.parseInt(frm.getWoGenName()),Integer.parseInt(frm.getWoCustomerName()));
			pageContext.setAttribute("vec_jobName",vec_jobName);
		}
		else
		{
			pageContext.setAttribute("vec_jobName",vec_jobName);
		}
		
		/* For Drawing No */
		Vector vec_drawingNo = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Job Name: "+ frm.getWoJobName());
		if (!frm.getWoJobName().equalsIgnoreCase("-1"))
		{
			vec_drawingNo = workOrderObj.getDrawingNoByJobName(Integer.parseInt(frm.getWoGenName()), Integer.parseInt(frm.getWoCustomerName()), frm.getWoJobName());
			pageContext.setAttribute("vec_drawingNo",vec_drawingNo);
		}
		else
		{
			pageContext.setAttribute("vec_drawingNo",vec_drawingNo);
		}
		
		/* For Revision No */
		Vector vec_revisionNo = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Drawing No: "+ frm.getWoDrawingHash());
		if (!frm.getWoDrawingHash().equalsIgnoreCase("-1"))
		{
			vec_revisionNo = workOrderObj.getRvsnNoByJobNameAndDrawingNo(Integer.parseInt(frm.getWoGenName()),Integer.parseInt(frm.getWoCustomerName()),frm.getWoJobName(),frm.getWoDrawingHash());
			if (BuildConfig.DMODE)
				System.out.println("vec_revisionNo:(Len): "+ vec_revisionNo.size());
			pageContext.setAttribute("vec_revisionNo",vec_revisionNo);
		}
		else
		{
			pageContext.setAttribute("vec_revisionNo",vec_revisionNo);
		}
		
		/* For Material Type */
		Vector vec_MaterialType = new Vector();
		if (BuildConfig.DMODE)
			System.out.println("Revision Id: "+ frm.getWoRevisionHash());
		if (!frm.getWoRevisionHash().equalsIgnoreCase("-1"))
		{
			vec_MaterialType = workOrderObj.getMatlTypByJobNameDrawingNoAndRvsnNo(Integer.parseInt(frm.getWoCustomerName()),Integer.parseInt(frm.getWoGenName()),frm.getWoJobName(),frm.getWoDrawingHash(),frm.getWoRevisionHash());
			if (BuildConfig.DMODE)
				System.out.println("MaterialType(L): "+vec_MaterialType.size());
			pageContext.setAttribute("vec_MaterialType",vec_MaterialType);
		}
		else
		{
			pageContext.setAttribute("vec_MaterialType",vec_MaterialType);
		}
		
		/* Load Job Id */
		if (BuildConfig.DMODE)
			System.out.println("Material Type: "+frm.getWoMaterialType());
		if (!frm.getWoMaterialType().equalsIgnoreCase("-1"))
		{
			int jobId = workOrderObj.getJobId(Integer.parseInt(frm.getWoGenName()),Integer.parseInt(frm.getWoCustomerName()),frm.getWoJobName(),frm.getWoDrawingHash(),frm.getWoRevisionHash(),frm.getWoMaterialType());
			frm.setJobId(jobId+"");
			if (BuildConfig.DMODE)
				System.out.println("JobId: "+ frm.getJobId());
		}
		
		/* For Operation Group Codes */
		HashMap operGrpCode = workOrderObj.getAllOperationGroupCodes();
		pageContext.setAttribute("operGrpCode",operGrpCode);
		
		/* For Operation Group Details */
		Vector vec_jobOpnDet = new Vector();
		if (!frm.getJobId().equalsIgnoreCase("0"))
		{
			objJobDet = workOrderObj.getJobDetails(Integer.parseInt(frm.getJobId()));
			vec_jobOpnDet = objJobDet.getVec_OpnDetails();
			String opnDetails = "";
			for (int i = 0; i < vec_jobOpnDet.size(); i++)
			{
				OperationDetails objOperDet = new OperationDetails();
				objOperDet = (OperationDetails) vec_jobOpnDet.get(i);
				
				if (i != 0)
				{
					opnDetails = opnDetails + "^";
				}
				opnDetails = opnDetails + objOperDet.getOpnGpId() +"~"+ objOperDet.getOpnSerialNo() +"~"+ objOperDet.getOpnGpCode() +"~"+ objOperDet.getOpnName() +"~"+ objOperDet.getOpnStdHrs()+"~"+objOperDet.isOpnIncentive();
			}
			if (BuildConfig.DMODE)
				System.out.println("OpnDetails: "+opnDetails);
			frm.setHidModFields(opnDetails);
		}
		
		if (frm.getFormAction().equalsIgnoreCase("modifyTemp"))
		{
			if (BuildConfig.DMODE)
				System.out.println("Modify Details: "+ frm.getHidModOperDet());
			StringTokenizer stModDet = new StringTokenizer(frm.getHidModOperDet(),"$");
			String[] arModDet = new String[stModDet.countTokens()];
			int count = 0;
			while (stModDet.hasMoreTokens())
			{
				arModDet[count] = stModDet.nextToken();
				if (BuildConfig.DMODE)
					System.out.println("arModDet["+count+"]: "+arModDet[count]);
				count++;
			}
			
			vec_jobName = workOrderObj.getJobByGeneralName(Integer.parseInt(arModDet[0]),Integer.parseInt(frm.getWoCustomerName()));
			pageContext.setAttribute("vec_jobName",vec_jobName);
			
			vec_drawingNo = workOrderObj.getDrawingNoByJobName(Integer.parseInt(arModDet[0]), Integer.parseInt(frm.getWoCustomerName()), arModDet[1]);
			pageContext.setAttribute("vec_drawingNo",vec_drawingNo);
			
			vec_revisionNo = workOrderObj.getRvsnNoByJobNameAndDrawingNo(Integer.parseInt(arModDet[0]),Integer.parseInt(frm.getWoCustomerName()),arModDet[1],arModDet[2]);
			pageContext.setAttribute("vec_revisionNo",vec_revisionNo);
			
			vec_MaterialType = workOrderObj.getMatlTypByJobNameDrawingNoAndRvsnNo(Integer.parseInt(frm.getWoCustomerName()),Integer.parseInt(arModDet[0]),arModDet[1],arModDet[2],arModDet[3]);
			pageContext.setAttribute("vec_MaterialType",vec_MaterialType);
			frm.setJobId(arModDet[7]);
			String opnDetails = arModDet[8];
			frm.setHidModFields(opnDetails);
		}
	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
	}
%>

<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_dc.js'></script>
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
	
	function listItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/WorkOrderList.jsp';
		document.forms[0].submit();
	}

	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/WorkOrder/WorkOrderAdd.jsp';
		document.forms[0].submit();
	}

	function loadCustomerNames()
	{
		var temp = document.forms[0];
		if (temp.woCustomerType.value != "0")
		{
			temp.formAction.value = "load";
			loadWrkOrdJobList();
			temp.focusPoint.value = 1;
			temp.submit();
		}
	}

	function loadGeneralName()
	{
		var temp = document.forms[0];
		if (temp.woCustomerName.value != "0")
		{
			temp.formAction.value = "load";
			temp.hidFinalWoList.value = "";
			temp.hidFinalWoOperList.value = "";
			//loadWrkOrdJobList();
			temp.focusPoint.value = 2;
			temp.submit();
		}
		else
		{
			alert ("Customer Name Required!");
			return false;
		}
	}

	function loadJobNames()
	{
		var temp = document.forms[0];
		if (temp.woGenName.value != "-1")
		{
			temp.formAction.value = "load";
			loadWrkOrdJobList();
			temp.focusPoint.value = 3;
			temp.submit();
		}
		else
		{
			alert ("General Name Required!");
			return false;
		}
	}

	function loadDrawingNo()
	{
		var temp = document.forms[0];
		temp.formAction.value = "load";
		temp.woJobName.options[temp.woJobName.selectedIndex].value=temp.woJobName.options[temp.woJobName.selectedIndex].text;
		temp.woJobName.value = temp.woJobName.options[temp.woJobName.selectedIndex].text;
		//alert (temp.woJobName.value);
		loadWrkOrdJobList();
		temp.focusPoint.value = 4;
		temp.submit();
	}

	function loadRevisionNo()
	{
		var temp = document.forms[0];
		loadDrawingNo();
		temp.formAction.value = "load";
		temp.woDrawingHash.options[temp.woDrawingHash.selectedIndex].value = temp.woDrawingHash.options[temp.woDrawingHash.selectedIndex].text;
		temp.woDrawingHash.value = temp.woDrawingHash.options[temp.woDrawingHash.selectedIndex].text;
		loadWrkOrdJobList();
		temp.focusPoint.value = 5;
		temp.submit();
	}

	function loadMaterialType()
	{
		var temp = document.forms[0];
		loadDrawingNo();
		loadRevisionNo();
		temp.formAction.value = "load";
		temp.woRevisionHash.options[temp.woRevisionHash.selectedIndex].value = temp.woRevisionHash.options[temp.woRevisionHash.selectedIndex].text;
		temp.woRevisionHash.value = temp.woRevisionHash.options[temp.woRevisionHash.selectedIndex].text;
		//alert (temp.woRevisionHash.value);
		loadWrkOrdJobList();
		temp.focusPoint.value = 6;
		temp.submit();
	}

	function loadJobId()
	{
		var temp = document.forms[0];
		loadDrawingNo();
		loadRevisionNo();
		loadMaterialType();
		temp.formAction.value = "load";
		temp.woMaterialType.options[temp.woMaterialType.selectedIndex].value = temp.woMaterialType.options[temp.woMaterialType.selectedIndex].text;
		temp.woMaterialType.value = temp.woMaterialType.options[temp.woMaterialType.selectedIndex].text;
		//alert (temp.woMaterialType.value);
		loadWrkOrdJobList();
		temp.submit();
	}

	function transferWrkOrdList()
	{
		var temp = document.forms[0];
		if (temp.woGenName.value == "0")
		{
			alert ("General Name is Required!");
			return false;
		}
		if (temp.woJobName.value == "-1")
		{
			alert ("Job Name is Required!");
			return false;
		}
		if (temp.woDrawingHash.value == "-1")
		{
			alert ("Drawing No. is Required!");
			return false;
		}
		if (temp.woRevisionHash.value == "-1")
		{
			alert ("Revision No. is Required!");
			return false;
		}
		if (temp.woMaterialType.value == "-1")
		{
			alert ("Material Type is Required!");
			return false;
		}
		if (temp.woJobQtyStartNo.value == "")
		{
			alert ("Job Start S.No. Required!");
			return false;
		}
		if (temp.woJobQty.value == "")
		{
			alert ("Job Quantity Required!");
			return false;
		}
		if (temp.jobId.value == "")
		{
			alert ("JobId not Exist!");
			return false;
		}
		
		var obj=document.getElementById("tbl");

		/* Empty values are not allowed. */
		var opnDet = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if ((obj.children(i).children(0).children(0).children(0).value == "on") ||
				(obj.children(i).children(0).children(1).children(0).value == "") ||
				(obj.children(i).children(0).children(2).children(0).value == "") ||
				(obj.children(i).children(0).children(3).children(0).value == "") ||
				(obj.children(i).children(0).children(4).children(0).value == ""))
			{
				alert ("Job Operation Details having Empty Values!");
				opnDet = "";
				return false;
			}
			if (i != 0)
			{
				opnDet = opnDet + "^";
			}
			opnDet = opnDet + obj.children(i).children(0).children(0).children(0).value + "~" +
					obj.children(i).children(0).children(1).children(0).value + "~" +
					obj.children(i).children(0).children(2).children(0).value + "~" +
					obj.children(i).children(0).children(3).children(0).value + "~" +
					obj.children(i).children(0).children(4).children(0).value + "~" +
					obj.children(i).children(0).children(5).children(0).checked;;
			//alert (opnDet);
		}
		
		var obj1 = document.getElementById("wrkOrdList");
		for (var i = 0; i < obj1.children.length; i++)
		{
			if (obj1.children(i).children(0).children(0).children(0).value == temp.jobId.value)
			{
				alert ("The Entry is Already placed! Try another One!");
				return false;
			}
		}

		tblWOJobDtls.style.display = 'block';
		var proEnter = false;
		if ((obj1.children.length == "1") && (obj1.children(0).children(0).children(1).children(0).value == ""))
		{
			obj1.children(0).children(0).children(0).children(0).value = temp.jobId.value;
			obj1.children(0).children(0).children(1).children(0).value = 1;
			obj1.children(0).children(0).children(2).children(0).value = temp.woGenName.options[temp.woGenName.selectedIndex].text;
			obj1.children(0).children(0).children(3).children(0).value = temp.woJobName.options[temp.woJobName.selectedIndex].text;
			obj1.children(0).children(0).children(4).children(0).value = temp.woDrawingHash.value;
			obj1.children(0).children(0).children(5).children(0).value = temp.woRevisionHash.value;
			obj1.children(0).children(0).children(6).children(0).value = temp.woMaterialType.value;
			obj1.children(0).children(0).children(7).children(0).value = temp.woJobQtyStartNo.value;
			obj1.children(0).children(0).children(8).children(0).value = temp.woJobQty.value;
			obj1.children(0).children(0).children(9).children(0).value = temp.woGenName.value;
			obj1.children(0).children(0).children(10).children(0).value = opnDet;
			obj1.children(0).children(0).children(11).children(0).value = temp.woDcNo.value;
			obj1.children(0).children(0).children(12).children(0).value = temp.dcDate.value;
			if (temp.proEnteredValue.value == "true")
				proEnter = true;
			else
				proEnter = false;
			obj1.children(0).children(0).children(13).children(0).value = proEnter;
		}
		else
		{
			var newNode = obj1.children(0).cloneNode(true);
			obj1.appendChild(newNode);
			var len = obj1.children.length;

			obj1.children(len-1).children(0).children(0).children(0).value = temp.jobId.value;
			obj1.children(len-1).children(0).children(1).children(0).value = len;
			obj1.children(len-1).children(0).children(2).children(0).value = temp.woGenName.options[temp.woGenName.selectedIndex].text;
			obj1.children(len-1).children(0).children(3).children(0).value = temp.woJobName.options[temp.woJobName.selectedIndex].text;
			obj1.children(len-1).children(0).children(4).children(0).value = temp.woDrawingHash.value;
			obj1.children(len-1).children(0).children(5).children(0).value = temp.woRevisionHash.value;
			obj1.children(len-1).children(0).children(6).children(0).value = temp.woMaterialType.value;
			obj1.children(len-1).children(0).children(7).children(0).value = temp.woJobQtyStartNo.value;
			obj1.children(len-1).children(0).children(8).children(0).value = temp.woJobQty.value;
			obj1.children(len-1).children(0).children(9).children(0).value = temp.woGenName.value;
			obj1.children(len-1).children(0).children(10).children(0).value = opnDet;
			obj1.children(len-1).children(0).children(11).children(0).value = temp.woDcNo.value;
			obj1.children(len-1).children(0).children(12).children(0).value = temp.dcDate.value;
			if (temp.proEnteredValue.value == "true")
				proEnter = true;
			else
				proEnter = false;
			obj1.children(len-1).children(0).children(13).children(0).value = proEnter;
		}

		for (var k = 0; k < obj1.children.length; k++)
		{
			if (obj1.children(k).children(0).children(13).children(0).value == "false")
			{
				obj1.children(k).children(0).children(0).children(0).disabled = false; 
			}
		}
		
		temp.woGenName.value = 0;
		temp.woJobName.value = -1;
		temp.woDrawingHash.value = -1;
		temp.woRevisionHash.value = -1;
		temp.woMaterialType.value = -1;
		temp.woJobQtyStartNo.value = "";
		temp.woJobQty.value = "";
		temp.woDcNo.value = "";
		temp.dcDate.value = "";
		tblJobOprnDtls.style.display = 'none';
	}

	function transferWrkOrdList1()
	{
		var temp = document.forms[0];
		if (temp.woGenName.value == "0")
		{
			alert ("General Name is Required!");
			return false;
		}
		if (temp.woJobName.value == "-1")
		{
			alert ("Job Name is Required!");
			return false;
		}
		if (temp.woDrawingHash.value == "-1")
		{
			alert ("Drawing No. is Required!");
			return false;
		}
		if (temp.woRevisionHash.value == "-1")
		{
			alert ("Revision No. is Required!");
			return false;
		}
		if (temp.woMaterialType.value == "-1")
		{
			alert ("Material Type is Required!");
			return false;
		}
		if (temp.woJobQtyStartNo.value == "")
		{
			alert ("Job Start S.No. Required!");
			return false;
		}
		if (temp.woJobQty.value == "")
		{
			alert ("Job Quantity Required!");
			return false;
		}
		if (temp.jobId.value == "")
		{
			alert ("JobId not Exist!");
			return false;
		}
		
		var obj=document.getElementById("tbl1");

		/* Empty values are not allowed. */
		var opnDet = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if ((obj.children(i).children(0).children(0).children(0).value == "on") ||
				(obj.children(i).children(0).children(1).children(0).value == "") ||
				(obj.children(i).children(0).children(2).children(0).value == "") ||
				(obj.children(i).children(0).children(3).children(0).value == ""))
			{
				alert ("Job Operation Details having Empty Values!");
				opnDet = "";
				return false;
			}
			if (i != 0)
			{
				opnDet = opnDet + "^";
			}
			opnDet = opnDet + obj.children(i).children(0).children(0).children(0).value + "~" +
					obj.children(i).children(0).children(1).children(0).value + "~" +
					obj.children(i).children(0).children(2).children(0).value + "~" +
					obj.children(i).children(0).children(3).children(0).value;
			//alert (opnDet);
		}
		
		var obj1 = document.getElementById("wrkOrdList");
		for (var i = 0; i < obj1.children.length; i++)
		{
			if (obj1.children(i).children(0).children(0).children(0).value == temp.jobId.value)
			{
				alert ("The Entry is Already placed! Try another One!");
				return false;
			}
		}

		tblWOJobDtls.style.display = 'block';
		var proEnter = false;
		if ((obj1.children.length == "1") && (obj1.children(0).children(0).children(1).children(0).value == ""))
		{
			obj1.children(0).children(0).children(0).children(0).value = temp.jobId.value;
			obj1.children(0).children(0).children(1).children(0).value = 1;
			obj1.children(0).children(0).children(2).children(0).value = temp.woGenName.options[temp.woGenName.selectedIndex].text;
			obj1.children(0).children(0).children(3).children(0).value = temp.woJobName.options[temp.woJobName.selectedIndex].text;
			obj1.children(0).children(0).children(4).children(0).value = temp.woDrawingHash.value;
			obj1.children(0).children(0).children(5).children(0).value = temp.woRevisionHash.value;
			obj1.children(0).children(0).children(6).children(0).value = temp.woMaterialType.value;
			obj1.children(0).children(0).children(7).children(0).value = temp.woJobQtyStartNo.value;
			obj1.children(0).children(0).children(8).children(0).value = temp.woJobQty.value;
			obj1.children(0).children(0).children(9).children(0).value = temp.woGenName.value;
			obj1.children(0).children(0).children(10).children(0).value = opnDet;
			obj1.children(0).children(0).children(11).children(0).value = temp.woDcNo.value;
			obj1.children(0).children(0).children(12).children(0).value = temp.dcDate.value;
			if (temp.proEnteredValue.value == "true")
				proEnter = true;
			else
				proEnter = false;
			obj1.children(0).children(0).children(13).children(0).value = proEnter;
		}
		else
		{
			var newNode = obj1.children(0).cloneNode(true);
			obj1.appendChild(newNode);
			var len = obj1.children.length;

			obj1.children(len-1).children(0).children(0).children(0).value = temp.jobId.value;
			obj1.children(len-1).children(0).children(1).children(0).value = len;
			obj1.children(len-1).children(0).children(2).children(0).value = temp.woGenName.options[temp.woGenName.selectedIndex].text;
			obj1.children(len-1).children(0).children(3).children(0).value = temp.woJobName.options[temp.woJobName.selectedIndex].text;
			obj1.children(len-1).children(0).children(4).children(0).value = temp.woDrawingHash.value;
			obj1.children(len-1).children(0).children(5).children(0).value = temp.woRevisionHash.value;
			obj1.children(len-1).children(0).children(6).children(0).value = temp.woMaterialType.value;
			obj1.children(len-1).children(0).children(7).children(0).value = temp.woJobQtyStartNo.value;
			obj1.children(len-1).children(0).children(8).children(0).value = temp.woJobQty.value;
			obj1.children(len-1).children(0).children(9).children(0).value = temp.woGenName.value;
			obj1.children(len-1).children(0).children(10).children(0).value = opnDet;
			obj1.children(len-1).children(0).children(11).children(0).value = temp.woDcNo.value;
			obj1.children(len-1).children(0).children(12).children(0).value = temp.dcDate.value;
			if (temp.proEnteredValue.value == "true")
				proEnter = true;
			else
				proEnter = false;
			obj1.children(len-1).children(0).children(13).children(0).value = proEnter;
		}

		for (var k = 0; k < obj1.children.length; k++)
		{
			if (obj1.children(k).children(0).children(13).children(0).value == "false")
			{
				obj1.children(k).children(0).children(0).children(0).disabled = false; 
			}
		}
		
		temp.woGenName.value = 0;
		temp.woJobName.value = -1;
		temp.woDrawingHash.value = -1;
		temp.woRevisionHash.value = -1;
		temp.woMaterialType.value = -1;
		temp.woJobQtyStartNo.value = "";
		temp.woJobQty.value = "";
		temp.woDcNo.value = "";
		temp.dcDate.value = "";
		tblJobOprnDtls1.style.display = 'none';
	}

	function delWorkOrder()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("wrkOrdList");
		var count = 0;

		if (obj.children.length > 1)
		{
			var k = 0;

			/* Checking the multiple Rows */
			do
			{
				if (temp.woCheckValue[k].checked)
				{
					obj.removeChild(obj.children(k));
					k = 0;
					count++;
				}
				else
				{
					k++;
				}
			}while (k < obj.children.length);

			/* Ordering the Seriel No */
			for (var i = 0; i < obj.children.length; i++)
			{
				obj.children(i).children(0).children(1).children(0).value = i+1;
			}

		}
		else if (temp.woCheckValue.checked)
		{
			for (var j = 0; j < 11; j++)
			{
				obj.children(0).children(0).children(j).children(0).value = "";
				obj.children(0).children(0).children(0).children(0).checked = false;
			}
		}
		else if (count == 0)
		{
			alert ("Check a Work Order to Delete!");
		}
	}

	function loadOperationDetails()
	{
		var temp = document.forms[0];
		<%
			if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
			{
		%>
			var obj = document.getElementById("tbl");
			if (temp.woMaterialType.value == "-1")
			{
				tblJobOprnDtls.style.display = 'none';
				return false;
			}

			if (temp.jobId.value != "0")
			{
				tblJobOprnDtls.style.display = 'block';
				if (temp.hidModFields.value != "")
				{
					var opnDet = (temp.hidModFields.value).split("^");
					for (var i = 0; i < opnDet.length; i++) 
					{
						var opnPrnlDet = opnDet[i].split("~");
						if ((obj.children.length == "1") && (obj.children(0).children(0).children(2).children(0).value == ""))
						{
							obj.children(i).children(0).children(0).children(0).value = opnPrnlDet[0];
							obj.children(i).children(0).children(1).children(0).value = opnPrnlDet[1];
							obj.children(i).children(0).children(2).children(0).value = opnPrnlDet[2];
							obj.children(i).children(0).children(3).children(0).value = opnPrnlDet[3];
							obj.children(i).children(0).children(4).children(0).value = opnPrnlDet[4];
							if (opnPrnlDet[5] == "true")
								obj.children(i).children(0).children(5).children(0).checked = true;
							else
								obj.children(i).children(0).children(5).children(0).checked = false;
						}
						else
						{
							var newNode = obj.children(0).cloneNode(true);
							obj.appendChild(newNode);

							obj.children(i).children(0).children(0).children(0).value = "";
							obj.children(i).children(0).children(1).children(0).value = "";
							obj.children(i).children(0).children(2).children(0).value = "";
							obj.children(i).children(0).children(3).children(0).value = "";
							obj.children(i).children(0).children(4).children(0).value = "";
							obj.children(i).children(0).children(5).children(0).value = "";

							obj.children(i).children(0).children(0).children(0).value = opnPrnlDet[0];
							obj.children(i).children(0).children(1).children(0).value = opnPrnlDet[1];
							obj.children(i).children(0).children(2).children(0).value = opnPrnlDet[2];
							obj.children(i).children(0).children(3).children(0).value = opnPrnlDet[3];
							obj.children(i).children(0).children(4).children(0).value = opnPrnlDet[4];
							if (opnPrnlDet[5] == "true")
								obj.children(i).children(0).children(5).children(0).checked = true;
							else
								obj.children(i).children(0).children(5).children(0).checked = false;
						}
					}
				}
				else
				{
					obj.children(0).children(0).children(1).children(0).value = 1;
				}
			}
		<%
		}
		else if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
		{
		%>
			var obj = document.getElementById("tbl1");
			if (temp.woMaterialType.value == "-1")
			{
				tblJobOprnDtls1.style.display = 'none';
				return false;
			}

			if (temp.jobId.value != "0")
			{
				tblJobOprnDtls1.style.display = 'block';
				if (temp.hidModFields.value != "")
				{
					var opnDet = (temp.hidModFields.value).split("^");
					for (var i = 0; i < opnDet.length; i++) 
					{
						var opnPrnlDet = opnDet[i].split("~");
						if ((obj.children.length == "1") && (obj.children(0).children(0).children(2).children(0).value == ""))
						{
							obj.children(i).children(0).children(0).children(0).value = opnPrnlDet[0];
							obj.children(i).children(0).children(1).children(0).value = opnPrnlDet[1];
							obj.children(i).children(0).children(2).children(0).value = opnPrnlDet[2];
							obj.children(i).children(0).children(3).children(0).value = opnPrnlDet[3];
						}
						else
						{
							var newNode = obj.children(0).cloneNode(true);
							obj.appendChild(newNode);

							obj.children(i).children(0).children(0).children(0).value = "";
							obj.children(i).children(0).children(1).children(0).value = "";
							obj.children(i).children(0).children(2).children(0).value = "";
							obj.children(i).children(0).children(3).children(0).value = "";

							obj.children(i).children(0).children(0).children(0).value = opnPrnlDet[0];
							obj.children(i).children(0).children(1).children(0).value = opnPrnlDet[1];
							obj.children(i).children(0).children(2).children(0).value = opnPrnlDet[2];
							obj.children(i).children(0).children(3).children(0).value = opnPrnlDet[3];
						}
					}
				}
				else
				{
					obj.children(0).children(0).children(1).children(0).value = 1;
				}
			}
		<%
		}
		%>
	}

	function addRow(formObj)
	{
		var numFields = 0;
		var j = 1;
		var k = new Array();
		var l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
			{
				k[l] = formObj.elements[i].value;
				l++;
			}
		}
		var obj = document.getElementById("tbl");		
		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == ""||
			obj.children(i).children(0).children(4).children(0).value == "")
			{
				alert("Row Addition failed! One among the value(s) are/is empty!");
				return false;
			}
		}

		var newNode = obj.children(0).cloneNode(true);
		obj.appendChild(newNode);
		for (var i = 0; i < formObj.length; i++) // for Serial Number
		numFields++;
		for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = j;
						j++;
					}
				if (formObj.elements[i].name == 'opnGrpName')
					{
						formObj.elements[i].value = "";
					}
			}
		l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
				{
					formObj.elements[i].value = k[l];
					l++;
					if(formObj.elements[i].value == "undefined")
					formObj.elements[i].value = "";
				}
		}
		/* Remove the extra values on creation of new childRow */
		var a = obj.children.length;
		obj.children(a-1).children(0).children(2).children(0).value = "";
		obj.children(a-1).children(0).children(3).children(0).value = "";
		obj.children(a-1).children(0).children(4).children(0).value = "";
		obj.children(a-1).children(0).children(5).children(0).checked = false;
	}

	function addRow1(formObj)
	{
		var numFields = 0;
		var j = 1;
		var k = new Array();
		var l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
			{
				k[l] = formObj.elements[i].value;
				l++;
			}
		}
		var obj = document.getElementById("tbl1");		
		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == "")
			{
				alert("Row Addition failed! One among the value(s) are/is empty!");
				return false;
			}
		}

		var newNode = obj.children(0).cloneNode(true);
		obj.appendChild(newNode);
		for (var i = 0; i < formObj.length; i++) // for Serial Number
		numFields++;
		for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = j;
						j++;
					}
				if (formObj.elements[i].name == 'opnGrpName')
					{
						formObj.elements[i].value = "";
					}
			}
		l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
				{
					formObj.elements[i].value = k[l];
					l++;
					if(formObj.elements[i].value == "undefined")
					formObj.elements[i].value = "";
				}
		}
		/* Remove the extra values on creation of new childRow */
		var a = obj.children.length;
		obj.children(a-1).children(0).children(2).children(0).value = "";
		obj.children(a-1).children(0).children(3).children(0).value = "";
	}
		
	function delRow(formObj , cRows)
	{
		var numFields = 0;
		var d=1;
		var obj=document.getElementById("tbl");
		var temp=document.forms[0];
		if (obj.children.length>1)
		{
			for(var i = 0; i < obj.children.length; i++)
			{
				if(temp.CheckValue[i].checked)
				{
					obj.removeChild(obj.children(i));
					i=0;
				}
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
				{
					obj.children(0).children(0).children(0).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(4).children(0).value = "";
					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
			for (var i = 0; i < formObj.length; i++)
				numFields++;
			for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = d;
						d++;
					}
			}
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
		{
			obj.children(0).children(0).children(0).children(0).value = "";
			obj.children(0).children(0).children(2).children(0).value = "";
			obj.children(0).children(0).children(3).children(0).value = "";
			obj.children(0).children(0).children(4).children(0).value = "";
			obj.children(0).children(0).children(0).children(0).checked = false;
		}
	} 
		
	function delRow1(formObj , cRows)
	{
		var numFields = 0;
		var d=1;
		var obj=document.getElementById("tbl1");
		var temp=document.forms[0];
		if (obj.children.length>1)
		{
			for(var i = 0; i < obj.children.length; i++)
			{
				if(temp.CheckValue[i].checked)
				{
					obj.removeChild(obj.children(i));
					i=0;
				}
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
				{
					obj.children(0).children(0).children(0).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
			for (var i = 0; i < formObj.length; i++)
				numFields++;
			for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = d;
						d++;
					}
			}
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
		{
			obj.children(0).children(0).children(0).children(0).value = "";
			obj.children(0).children(0).children(2).children(0).value = "";
			obj.children(0).children(0).children(3).children(0).value = "";
			obj.children(0).children(0).children(0).children(0).checked = false;
		}
	} 

	function OpnChkAll(formObj)
	{
		var temp = document.forms[0];
		if (temp.OpnCheckAll.checked)
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

	function operName(formObj)
	{
		var temp = document.forms[0];
		var ind = formObj.parentNode.parentNode.children(1).children(0).value;
		var obj = document.getElementById("tbl");
		if (temp.selectJobOperations.value != "0")
		{
			obj.children(ind-1).children(0).children(2).children(0).value = temp.selectJobOperations.options[temp.selectJobOperations.selectedIndex].text;
			obj.children(ind-1).children(0).children(0).children(0).value = temp.selectJobOperations.value;
		}
	}

	function isNumber(formObj)
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

	function isFloat(formObj)
	{
		if ((event.keyCode > 47) && (event.keyCode < 58) || (event.keyCode == 46) || (event.keyCode == 8))
		{
			sumHrs();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	function WOCheckAllBox(formObj)
	{
		var temp = document.forms[0];
		if (temp.WOCheckAll.checked)
		{
			if (temp.woCheckValue.length > 1)
			{
				for (var i = 0; i < temp.woCheckValue.length; i++)
				{
					if (temp.woCheckValue[i].disabled == false)
					{
						temp.woCheckValue[i].checked = true;
					}
				}
			}
			else
			{
				if (temp.woCheckValue[i].disabled == false)
				{
					temp.woCheckValue.checked = true;
				}
			}
		}
		else
		{
			if (temp.woCheckValue.length > 1)
			{
				for (var i = 0; i < temp.woCheckValue.length; i++)
				{
					temp.woCheckValue[i].checked = false;
				}
			}
			else
			{
				temp.woCheckValue.checked = false;
			}
		}
	}
	
	function loadWrkOrdJobList()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("wrkOrdList");
		var wrkOrdDet = "";
		var wrkOrdJbOpns = "";
		temp.hidFinalWoList.value = "";
		temp.hidFinalWoOperList.value = "";

	   /* If the Table is empty, Exit 
		* If there is no child, Exit.
		*/
		if ((obj.children.length == 0) || (obj.children(0).children(0).children(1).children(0).value == "")) 
			return false;

		for (var i = 0; i < obj.children.length; i++)
		{
			if (i != 0)
			{
				wrkOrdDet = wrkOrdDet + "^";
				wrkOrdJbOpns = wrkOrdJbOpns + "$";
			}

			wrkOrdDet = wrkOrdDet + obj.children(i).children(0).children(0).children(0).value + "~" +
				obj.children(i).children(0).children(1).children(0).value + "~" +
				obj.children(i).children(0).children(2).children(0).value + "~" +
				obj.children(i).children(0).children(3).children(0).value + "~" +
				obj.children(i).children(0).children(4).children(0).value + "~" +
				obj.children(i).children(0).children(5).children(0).value + "~" +
				obj.children(i).children(0).children(6).children(0).value + "~" +
				obj.children(i).children(0).children(7).children(0).value + "~" +
				obj.children(i).children(0).children(8).children(0).value + "~" +
				obj.children(i).children(0).children(9).children(0).value + "~" +
				obj.children(i).children(0).children(11).children(0).value + "~" +
				obj.children(i).children(0).children(12).children(0).value + "~" +
				obj.children(i).children(0).children(13).children(0).value;

			wrkOrdJbOpns = wrkOrdJbOpns + obj.children(i).children(0).children(10).children(0).value; 
			
			if (obj.children(i).children(0).children(13).children(0).value == "true")
			{
				obj.children(i).children(0).children(0).children(0).disabled = true;
			}
		}
		temp.hidFinalWoList.value = wrkOrdDet;
		temp.hidFinalWoOperList.value = wrkOrdJbOpns;
	}

	function replaceWrkOrdList()
	{
		var temp = document.forms[0];
		//alert (temp.hidFinalWoList.value);
		//alert (temp.hidFinalWoOperList.value);
		var obj = document.getElementById("wrkOrdList");
		if (temp.hidFinalWoList.value != "")
		{
			tblWOJobDtls.style.display = 'block';

			var woDet = (temp.hidFinalWoList.value).split("^");
			var woOperDet = (temp.hidFinalWoOperList.value).split("$");
			
			for (var i = 0; i < woDet.length; i++)
			{
				var woPrnlDet = woDet[i].split("~");
				if (i == 0)
				{
					obj.children(i).children(0).children(0).children(0).value = woPrnlDet[0];
					obj.children(i).children(0).children(1).children(0).value = woPrnlDet[1];
					obj.children(i).children(0).children(2).children(0).value = woPrnlDet[2];
					obj.children(i).children(0).children(3).children(0).value = woPrnlDet[3];
					obj.children(i).children(0).children(4).children(0).value = woPrnlDet[4];
					obj.children(i).children(0).children(5).children(0).value = woPrnlDet[5];
					obj.children(i).children(0).children(6).children(0).value = woPrnlDet[6];
					obj.children(i).children(0).children(7).children(0).value = woPrnlDet[7];
					obj.children(i).children(0).children(8).children(0).value = woPrnlDet[8];
					obj.children(i).children(0).children(9).children(0).value = woPrnlDet[9];
					obj.children(i).children(0).children(10).children(0).value = woOperDet[i];
					obj.children(i).children(0).children(11).children(0).value = woPrnlDet[10];
					obj.children(i).children(0).children(12).children(0).value = woPrnlDet[11];
					obj.children(i).children(0).children(13).children(0).value = woPrnlDet[12];
				}
				else
				{
					var newNode = obj.children(0).cloneNode(true);
					obj.appendChild(newNode);

					obj.children(i).children(0).children(0).children(0).value = woPrnlDet[0];
					obj.children(i).children(0).children(1).children(0).value = woPrnlDet[1];
					obj.children(i).children(0).children(2).children(0).value = woPrnlDet[2];
					obj.children(i).children(0).children(3).children(0).value = woPrnlDet[3];
					obj.children(i).children(0).children(4).children(0).value = woPrnlDet[4];
					obj.children(i).children(0).children(5).children(0).value = woPrnlDet[5];
					obj.children(i).children(0).children(6).children(0).value = woPrnlDet[6];
					obj.children(i).children(0).children(7).children(0).value = woPrnlDet[7];
					obj.children(i).children(0).children(8).children(0).value = woPrnlDet[8];
					obj.children(i).children(0).children(9).children(0).value = woPrnlDet[9];
					obj.children(i).children(0).children(10).children(0).value = woOperDet[i];
					obj.children(i).children(0).children(11).children(0).value = woPrnlDet[10];
					obj.children(i).children(0).children(12).children(0).value = woPrnlDet[11];
					obj.children(i).children(0).children(13).children(0).value = woPrnlDet[12];
				}
				if (obj.children(i).children(0).children(13).children(0).value == "true")
				{
					obj.children(i).children(0).children(0).children(0).disabled = true;
				}
			}
		}
	}

	function modifyRow()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("wrkOrdList");
		var count = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				count++;
			}
		}
		if (count > 1)
		{
			alert ("Please Select only one Job List to Modify!");
			return false;
		}
		else if (count == 0)
		{
			alert ("Please Select a Job List to Modify!");
			return false;
		}

		for (var i = 0; i < obj.children.length; i++)
		{
			var modOperDet = "";
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				modOperDet = obj.children(i).children(0).children(9).children(0).value +"$"+
							obj.children(i).children(0).children(3).children(0).value +"$"+
							obj.children(i).children(0).children(4).children(0).value +"$"+
							obj.children(i).children(0).children(5).children(0).value +"$"+
							obj.children(i).children(0).children(6).children(0).value +"$"+
							obj.children(i).children(0).children(7).children(0).value +"$"+
							obj.children(i).children(0).children(8).children(0).value +"$"+
							obj.children(i).children(0).children(0).children(0).value +"$"+
							obj.children(i).children(0).children(10).children(0).value+"$"+
							obj.children(i).children(0).children(11).children(0).value +"$"+
							obj.children(i).children(0).children(12).children(0).value +"$"+
							obj.children(i).children(0).children(13).children(0).value;

				//alert ("-->"+modOperDet);
				obj.removeChild(obj.children(i)); /* Removing the Child */
				if (obj.children.length != 0)
				{
					for (var j = 0; j < obj.children.length; j++) /* Ordering the SNo*/
					{
						obj.children(j).children(0).children(1).children(0).value = j +1;
					}
				}

				loadWrkOrdJobList();
				temp.hidModOperDet.value = modOperDet;
				temp.formAction.value = "modifyTemp";
				temp.submit();
			}
		}
	}

	function loadModOpns()
	{
		var temp = document.forms[0];
		<%
			if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
			{
		%>
			if (temp.formAction.value == "modifyTemp")
			{
				tblJobOprnDtls.style.display = 'block';
				
				var modDet = (temp.hidModOperDet.value).split("$");
				for (var i = 1; i < temp.woGenName.options.length; i++) /* General Name by Id */
				{
					if (temp.woGenName.options[i].value == modDet[0])
					{
						temp.woGenName.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woJobName.options.length; i++) /* For Job Name*/
				{
					if (temp.woJobName.options[i].text == modDet[1])
					{
						temp.woJobName.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woDrawingHash.options.length; i++) /* For Drawing No*/
				{
					if (temp.woDrawingHash.options[i].text == modDet[2])
					{
						temp.woDrawingHash.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woRevisionHash.options.length; i++) /* For Revision Hash*/
				{
					if (temp.woRevisionHash.options[i].text == modDet[3])
					{
						temp.woRevisionHash.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woMaterialType.options.length; i++) /* For material Type */
				{
					if (temp.woMaterialType.options[i].text == modDet[4])
					{
						temp.woMaterialType.options.selectedIndex = i;
					}
				}
				temp.woJobQtyStartNo.value = modDet[5];
				temp.woJobQty.value = modDet[6]; // modDet[7] ==> Job Id
				temp.woDcNo.value = modDet[9];
				temp.dcDate.value = modDet[10];
				temp.proEnteredValue.value = modDet[11];

				/*
				* Loading the Operation Details Table
				* Format is, a~a^a~a
				*/
				var obj = document.getElementById("tbl");
				var arOperDet = modDet[8].split("^");
				for (var i = 0; i < arOperDet.length; i++)
				{
					var arOper = arOperDet[i].split("~");
					
					if (i == 0)
					{
						obj.children(i).children(0).children(0).children(0).value = arOper[0];
						obj.children(i).children(0).children(1).children(0).value = arOper[1];
						obj.children(i).children(0).children(2).children(0).value = arOper[2];
						obj.children(i).children(0).children(3).children(0).value = arOper[3];
						obj.children(i).children(0).children(4).children(0).value = arOper[4];
						if (arOper[5] == "true")
							obj.children(i).children(0).children(5).children(0).checked = true;
						else
							obj.children(i).children(0).children(5).children(0).checked = false;
					}
					else
					{
						var newNode = obj.children(0).cloneNode(true);
						obj.appendChild(newNode);
						var len = obj.children.length;

						obj.children(i).children(0).children(0).children(0).value = "";
						obj.children(i).children(0).children(1).children(0).value = "";
						obj.children(i).children(0).children(2).children(0).value = "";
						obj.children(i).children(0).children(3).children(0).value = "";
						obj.children(i).children(0).children(4).children(0).value = "";
						obj.children(i).children(0).children(5).children(0).value = "";

						obj.children(i).children(0).children(0).children(0).value = arOper[0];
						obj.children(i).children(0).children(1).children(0).value = arOper[1];
						obj.children(i).children(0).children(2).children(0).value = arOper[2];
						obj.children(i).children(0).children(3).children(0).value = arOper[3];
						obj.children(i).children(0).children(4).children(0).value = arOper[4];
						if (arOper[5] == "true")
							obj.children(i).children(0).children(5).children(0).checked = true;
						else
							obj.children(i).children(0).children(5).children(0).checked = false;
					}
				}
				sumHrs();
			}
		<%
			}
			else if ((!objStdHours.isStdhrs()) && (!objStdHours.isOpnlevelstdhrs()) && (!objStdHours.isIncentive()) && (!objStdHours.isJobopnlevelincentive()))
			{
		%>
			if (temp.formAction.value == "modifyTemp")
			{
				tblJobOprnDtls1.style.display = 'block';
				
				var modDet = (temp.hidModOperDet.value).split("$");
				for (var i = 1; i < temp.woGenName.options.length; i++) /* General Name by Id */
				{
					if (temp.woGenName.options[i].value == modDet[0])
					{
						temp.woGenName.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woJobName.options.length; i++) /* For Job Name*/
				{
					if (temp.woJobName.options[i].text == modDet[1])
					{
						temp.woJobName.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woDrawingHash.options.length; i++) /* For Drawing No*/
				{
					if (temp.woDrawingHash.options[i].text == modDet[2])
					{
						temp.woDrawingHash.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woRevisionHash.options.length; i++) /* For Revision Hash*/
				{
					if (temp.woRevisionHash.options[i].text == modDet[3])
					{
						temp.woRevisionHash.options.selectedIndex = i;
					}
				}
				for (var i = 1; i < temp.woMaterialType.options.length; i++) /* For material Type */
				{
					if (temp.woMaterialType.options[i].text == modDet[4])
					{
						temp.woMaterialType.options.selectedIndex = i;
					}
				}
				temp.woJobQtyStartNo.value = modDet[5];
				temp.woJobQty.value = modDet[6]; // modDet[7] ==> Job Id
				temp.woDcNo.value = modDet[9];
				temp.dcDate.value = modDet[10];
				temp.proEnteredValue.value = modDet[11];

				/*
				* Loading the Operation Details Table
				* Format is, a~a^a~a
				*/
				var obj = document.getElementById("tbl1");
				var arOperDet = modDet[8].split("^");
				for (var i = 0; i < arOperDet.length; i++)
				{
					var arOper = arOperDet[i].split("~");
					
					if (i == 0)
					{
						obj.children(i).children(0).children(0).children(0).value = arOper[0];
						obj.children(i).children(0).children(1).children(0).value = arOper[1];
						obj.children(i).children(0).children(2).children(0).value = arOper[2];
						obj.children(i).children(0).children(3).children(0).value = arOper[3];
					}
					else
					{
						var newNode = obj.children(0).cloneNode(true);
						obj.appendChild(newNode);
						var len = obj.children.length;

						obj.children(i).children(0).children(0).children(0).value = "";
						obj.children(i).children(0).children(1).children(0).value = "";
						obj.children(i).children(0).children(2).children(0).value = "";
						obj.children(i).children(0).children(3).children(0).value = "";

						obj.children(i).children(0).children(0).children(0).value = arOper[0];
						obj.children(i).children(0).children(1).children(0).value = arOper[1];
						obj.children(i).children(0).children(2).children(0).value = arOper[2];
						obj.children(i).children(0).children(3).children(0).value = arOper[3];
					}
				}
			}
		<%
			}
		%>
	}

	function saveWrkOrder()
	{
		var temp = document.forms[0];
		var obj = document.getElementById("wrkOrdList");

		/* Check the WoDate and Estimated Completion Date */
		if (temp.woDate.value > temp.woEstCompletion.value)
		{
			alert ("Estimated Completion Date should be higher than WorkOrder Date");
			return false;
		}

		<%
			if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
			{
		%>
			tblJobOprnDtls.style.display = 'none';
		<%
			}
			else if ((!objStdHours.isStdhrs()) && (!objStdHours.isOpnlevelstdhrs()) && (!objStdHours.isIncentive()) && (!objStdHours.isJobopnlevelincentive()))
			{
		%>
			tblJobOprnDtls1.style.display = 'none';
		<%
			}
		%>


		temp.finalWoJobDet.value = "";
		temp.finalWoOperDet.value = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (i != 0)
			{
				temp.finalWoJobDet.value = temp.finalWoJobDet.value + "^";
				temp.finalWoOperDet.value = temp.finalWoOperDet.value + "$"
			}
			temp.finalWoJobDet.value = 
					temp.finalWoJobDet.value + 
					obj.children(i).children(0).children(0).children(0).value + "~" +
					obj.children(i).children(0).children(3).children(0).value + "~" +
					obj.children(i).children(0).children(4).children(0).value + "~" +
					obj.children(i).children(0).children(5).children(0).value + "~" +
					obj.children(i).children(0).children(6).children(0).value + "~" +
					obj.children(i).children(0).children(7).children(0).value + "~" +
					obj.children(i).children(0).children(8).children(0).value + "~" +
					obj.children(i).children(0).children(11).children(0).value + "~" +
					obj.children(i).children(0).children(12).children(0).value + "~" +
					obj.children(i).children(0).children(13).children(0).value;

			temp.finalWoOperDet.value = temp.finalWoOperDet.value + 
					obj.children(i).children(0).children(10).children(0).value;
		}
		//alert ("WoList: "+temp.finalWoJobDet.value);
		//alert ("OperList: "+temp.finalWoOperDet.value);
		var empt = (temp.finalWoOperDet.value).split("~");
		for (var k = 0; k < empt.length; k++)
		{
			if (empt[k] == "")
			{
				alert ("WorkOrder Job List Details are Empty!");
				return false;
			}
		}
		loadWrkOrdJobList();
		temp.formAction.value = "update";
		temp.submit();
	}

	function setFocusPoint()
	{
		var temp = document.forms[0];
		if ('<%= request.getParameter("focusPoint") %>' == 'null')
		{
			temp.woCustomerType.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 1)
		{
			temp.woCustomerName.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 2)
		{
			temp.woGenName.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 3)
		{
			temp.woJobName.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 4)
		{
			temp.woDrawingHash.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 5)
		{
			temp.woRevisionHash.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == 6)
		{
			temp.woMaterialType.focus();
		}
		else
		{
			temp.woCustomerType.focus();
		}
	}

	function initMod() /* This fn Should be called only once when the FormAction is Modify */
	{
		var temp = document.forms[0];
		var obj = document.getElementById('wrkOrdList');
		if (temp.formAction.value != "modify")
			return false;

		tblWOJobDtls.style.display = 'block';
		var arWoDet = (temp.hidModifyRecDet.value).split("^");
		for (var i = 0; i < arWoDet.length; i++)
		{
			var woPrnlDet = arWoDet[i].split("~");
			var opnDet = "";
			if (i == 0)
			{
				obj.children(i).children(0).children(0).children(0).value = woPrnlDet[0];
				obj.children(i).children(0).children(1).children(0).value = i+1;
				obj.children(i).children(0).children(2).children(0).value = woPrnlDet[1];
				obj.children(i).children(0).children(3).children(0).value = woPrnlDet[2];
				obj.children(i).children(0).children(4).children(0).value = woPrnlDet[3];
				obj.children(i).children(0).children(5).children(0).value = woPrnlDet[4];
				obj.children(i).children(0).children(6).children(0).value = woPrnlDet[5];
				obj.children(i).children(0).children(7).children(0).value = woPrnlDet[6];
				obj.children(i).children(0).children(8).children(0).value = woPrnlDet[7];
				obj.children(i).children(0).children(9).children(0).value = woPrnlDet[8];
				for (var j = 12; j < woPrnlDet.length; j= j+6)
				{
					if (j != 12)
					{
						opnDet = opnDet + "^";
					}
					opnDet = opnDet + woPrnlDet[j] + "~" + woPrnlDet[j+1] + "~" + woPrnlDet[j+2] + "~" + woPrnlDet[j+3] + "~" +woPrnlDet[j+4] + "~" +woPrnlDet[j+5];
				}
				obj.children(i).children(0).children(10).children(0).value = opnDet;
				obj.children(i).children(0).children(11).children(0).value = woPrnlDet[9];
				obj.children(i).children(0).children(12).children(0).value = woPrnlDet[10];
				obj.children(i).children(0).children(13).children(0).value = woPrnlDet[11];
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);

				obj.children(i).children(0).children(0).children(0).value = woPrnlDet[0];
				obj.children(i).children(0).children(1).children(0).value = i+1;
				obj.children(i).children(0).children(2).children(0).value = woPrnlDet[1];
				obj.children(i).children(0).children(3).children(0).value = woPrnlDet[2];
				obj.children(i).children(0).children(4).children(0).value = woPrnlDet[3];
				obj.children(i).children(0).children(5).children(0).value = woPrnlDet[4];
				obj.children(i).children(0).children(6).children(0).value = woPrnlDet[5];
				obj.children(i).children(0).children(7).children(0).value = woPrnlDet[6];
				obj.children(i).children(0).children(8).children(0).value = woPrnlDet[7];
				obj.children(i).children(0).children(9).children(0).value = woPrnlDet[8];
				for (var j = 12; j < woPrnlDet.length; j= j+6)
				{
					if (j != 12)
					{
						opnDet = opnDet + "^";
					}
					opnDet = opnDet + woPrnlDet[j] + "~" + woPrnlDet[j+1] + "~" + woPrnlDet[j+2] + "~" + woPrnlDet[j+3] + "~" +woPrnlDet[j+4]+ "~" +woPrnlDet[j+5];
				}
				obj.children(i).children(0).children(10).children(0).value = opnDet;
				obj.children(i).children(0).children(11).children(0).value = woPrnlDet[9];
				obj.children(i).children(0).children(12).children(0).value = woPrnlDet[10];
				obj.children(i).children(0).children(13).children(0).value = woPrnlDet[11];
			}
		}

		for (var k = 0; k < obj.children.length; k++)
		{
			if (obj.children(k).children(0).children(13).children(0).value == "true")
			{
				obj.children(k).children(0).children(0).children(0).disabled = true;
			}
		}
	}

	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}

	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmWorkOrderEdit.do?formAction=makeInvalid';
		temp.submit();
	}
	
	function insertRow()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var newNode = obj.children(0).cloneNode(true);
		var opns = "";
		//alert(obj.children.length);

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == ""||
			obj.children(i).children(0).children(4).children(0).value == "")
			{
				alert("Insertion failed! One among the value(s) are/is empty!");
				return false;
			}

			if (i != 0)
			{
				opns = opns + "^";
			}

			opns = opns + obj.children(i).children(0).children(0).children(0).value+"~"+
					obj.children(i).children(0).children(1).children(0).value+"~"+
					obj.children(i).children(0).children(2).children(0).value+"~"+
					obj.children(i).children(0).children(3).children(0).value+"~"+
					obj.children(i).children(0).children(4).children(0).value+"~"+
					obj.children(i).children(0).children(5).children(0).checked;
		}

		var count = 0;
		var chkd = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				count++;
				chkd = i;
			}
		}

		if (count == 0)
		{
			alert ("Check a Row to Insert!");
			return false;
		}
		else if (count > 1)
		{
			alert ("Check Only one Item to Insert!");
			return false;
		}
		else if (count == 1)
		{
			obj.appendChild(newNode);
			var opnRec = opns.split("^");
			var k = 0;
			for (var i = 0; i < opnRec.length+1; i++)
			{
				if (i != chkd)
				{
					var opnFlds = opnRec[k].split("~");
					obj.children(i).children(0).children(0).children(0).value = opnFlds[0];
					obj.children(i).children(0).children(1).children(0).value = opnFlds[1];
					obj.children(i).children(0).children(2).children(0).value = opnFlds[2];
					obj.children(i).children(0).children(3).children(0).value = opnFlds[3];
					obj.children(i).children(0).children(4).children(0).value = opnFlds[4];
					if (opnFlds[5] == "true")
						obj.children(i).children(0).children(5).children(0).checked = true;
					else
						obj.children(i).children(0).children(5).children(0).checked = false;
					k++;
				}
				else
				{
					obj.children(i).children(0).children(0).children(0).value = "";
					obj.children(i).children(0).children(1).children(0).value = "";
					obj.children(i).children(0).children(2).children(0).value = "";
					obj.children(i).children(0).children(3).children(0).value = "";
					obj.children(i).children(0).children(4).children(0).value = "";
					obj.children(i).children(0).children(5).children(0).checked = false;
				}
			}
		}
		for (var x = 0; x < obj.children.length; x++)
		{
			obj.children(x).children(0).children(1).children(0).value = x+1;
		}
	}
	
	function insertRow1()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl1');
		var newNode = obj.children(0).cloneNode(true);
		var opns = "";
		//alert(obj.children.length);

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == "")
			{
				alert("Insertion failed! One among the value(s) are/is empty!");
				return false;
			}

			if (i != 0)
			{
				opns = opns + "^";
			}

			opns = opns + obj.children(i).children(0).children(0).children(0).value+"~"+
					obj.children(i).children(0).children(1).children(0).value+"~"+
					obj.children(i).children(0).children(2).children(0).value+"~"+
					obj.children(i).children(0).children(3).children(0).value;
		}

		var count = 0;
		var chkd = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				count++;
				chkd = i;
			}
		}

		if (count == 0)
		{
			alert ("Check a Row to Insert!");
			return false;
		}
		else if (count > 1)
		{
			alert ("Check Only one Item to Insert!");
			return false;
		}
		else if (count == 1)
		{
			obj.appendChild(newNode);
			var opnRec = opns.split("^");
			var k = 0;
			for (var i = 0; i < opnRec.length+1; i++)
			{
				if (i != chkd)
				{
					var opnFlds = opnRec[k].split("~");
					obj.children(i).children(0).children(0).children(0).value = opnFlds[0];
					obj.children(i).children(0).children(1).children(0).value = opnFlds[1];
					obj.children(i).children(0).children(2).children(0).value = opnFlds[2];
					obj.children(i).children(0).children(3).children(0).value = opnFlds[3];
					k++;
				}
				else
				{
					obj.children(i).children(0).children(0).children(0).value = "";
					obj.children(i).children(0).children(1).children(0).value = "";
					obj.children(i).children(0).children(2).children(0).value = "";
					obj.children(i).children(0).children(3).children(0).value = "";
				}
			}
		}
		for (var x = 0; x < obj.children.length; x++)
		{
			obj.children(x).children(0).children(1).children(0).value = x+1;
		}
	}

	function sumHrs()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var sum = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(4).children(0).value != "")
			{
				sum += parseFloat(obj.children(i).children(0).children(4).children(0).value,10);
			}
			if (obj.children(i).children(0).children(4).children(0).id == 'selected')
				break;
		}
		temp.totStdHrs.value = parseFloat(sum,10);
		for (var i = 0; i < obj.children.length; i++)
			obj.children(i).children(0).children(4).children(0).id = "";
	}

	function sumHrs1()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var sum = 0;
		for (var i = 0; i < obj.children.length; i++)
			sum += parseFloat(obj.children(i).children(0).children(4).children(0).value,10);
		temp.totStdHrs.value = parseFloat(sum,10);
	}

</script>
</head>
<body onLoad="init(); loadToHidden(); loadOperationDetails(); replaceWrkOrdList(); loadModOpns(); setFocusPoint(); initMod();">
<html:form action="frmWorkOrderEdit">
<html:hidden property="formAction"/>
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="hidModFields" value="<%= frm.getHidModFields() %>"/> <!-- Maintain JobOperationDetails -->
<html:hidden property="hidFinalWoList" value="<%= frm.getHidFinalWoList() %>"/> <!-- WrkOrd Details up to General ID -->
<html:hidden property="hidFinalWoOperList" value="<%= frm.getHidFinalWoOperList() %>"/> <!-- WrkOrd Details, only Operation Details -->
<html:hidden property="hidModOperDet" value="<%= frm.getHidModOperDet() %>"/> <!-- Modify Details -->
<html:hidden property="finalWoJobDet" value="<%= frm.getFinalWoJobDet() %>"/> <!-- Save Job Details -->
<html:hidden property="finalWoOperDet" value="<%= frm.getFinalWoOperDet() %>"/> <!-- Save Job Opn Details -->
<html:hidden property="hidModifyRecDet" value="<%= frm.getHidModifyRecDet() %>"/> <!-- Initial WoDetails /-->

  <table width="100%" cellspacing="0" cellpadding="10">
    <tr> 
      <td><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr> 
            <td><bean:message key="prodacs.workorder.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1013" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List WorkOrder Info'; return true"  onMouseOut="window.status=''; return true" resourceId="13" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
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
            <td class="FieldTitle"><bean:message key="prodacs.workorder.workorder.wono"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="woHash" styleClass="TextBox" size="15" maxlength="10"/></td>
            <td class="FieldTitle"><bean:message key="prodacs.workorder.contactname"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="woContactName" styleClass="TextBox" size="25"/></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td width="150" class="FieldTitle"><html:text property="woDate" styleClass="TextBox" size="12" readonly = "true"/> 
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar_previous("woDate",WorkOrderEdit.woDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>

			<td width="150" class="FieldTitle" nowrap><bean:message key="prodacs.workorder.deliverycommitment"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle"><html:text property="woEstCompletion" styleClass="TextBox" readonly = "true" size="12"/> 
              <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("woEstCompletion",WorkOrderEdit.woEstCompletion.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/><span class="mandatory">*</span></td>
            <td class="FieldTitle">:</td>
            <td width="150" class="FieldTitle">
		<html:select property="woCustomerType" styleClass="Combo" onchange="loadCustomerNames();">
	            <html:option value="0">-- Customer Type --</html:option>
	            <html:options collection="customerType" property="key" labelProperty="value"/>
	         </html:select></td>
            <td width="130" class="FieldTitle"><bean:message key="prodacs.customer.customername"/><span class="mandatory">*</span></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="FieldTitle">
		<html:select property="woCustomerName" styleClass="Combo" onchange="loadGeneralName();">
	                <html:option value="0">-- Customer Name --</html:option>
	                <html:options collection="customerName" property="key" labelProperty="value"/>
              	</html:select> </td>
          </tr>
        </table>
        <fieldset id="FieldSet">
        <legend class="FieldTitle"><bean:message key="prodacs.workorder.workorderjobs"/></legend>
        <table width="100%" cellspacing="0" cellpadding="2">
        <tr> 
			<td class="Header"><bean:message key="prodacs.job.generalname"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.jobname"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.drawing"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.revision"/></td>
            <td width="110" class="Header"><bean:message key="prodacs.job.materialtype"/></td>
            <td width="60" class="Header"><bean:message key="prodacs.workorder.jobqtystartsno"/></td>
            <td width="50" class="Header"><bean:message key="prodacs.workorder.jobqty"/></td>
          </tr>
          <tr>
			 <td width="150" class="TableItems"><html:select property="woGenName" styleClass="ComboFull" onchange="loadJobNames();">
						<html:option value="0">-- General Name --</html:option>
						<html:options collection="generalName" property="key" labelProperty="value" />
					  </html:select></td>
			<td width="175" class="TableItems"><html:select property="woJobName" styleClass="ComboFull" onchange="loadDrawingNo();">
					   <html:option value="-1">-- Job Name --</html:option>
					   <html:options name="vec_jobName" />
					   </html:select></td>
			 <td class="TableItems"><html:select property="woDrawingHash" styleClass="ComboFull" onchange="loadRevisionNo();">
					 <html:option value="-1">-- Drawing No --</html:option>
					 <html:options name="vec_drawingNo" />
					 </html:select></td>
			 <td class="TableItems"><html:select property="woRevisionHash" styleClass="ComboFull" onchange="loadMaterialType();">
					 <html:option value="-1">-- Revision No --</html:option>
					 <html:options name="vec_revisionNo" />
					 </html:select></td>
			 <td class="TableItems"><html:select property="woMaterialType" styleClass="ComboFull" onchange="loadJobId();">
				<html:option value="-1">-- Material Type --</html:option>
				<html:options name="vec_MaterialType"/>
				</html:select>
			</td>
				<td class="TableItems"><html:text property="woJobQtyStartNo" styleClass="thinTbox" maxlength="10" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
				<td class="TableItems"><html:text property="woJobQty" styleClass="thinTbox" maxlength="10" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
				<td><html:hidden property="jobId" value='<%= frm.getJobId() %>'/></td>
		  </tr>
		  </table>
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
	<tr>
			<td width="150" class="FieldTitle"><bean:message key="prodacs.shipment.partyorderno"/><span class="Mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="woDcNo" styleClass="TextBox" size="20"/></td>

			<td width="100" class="FieldTitle"><bean:message key="prodacs.shipment.partyorderdate"/><span class="Mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="dcDate" styleClass="TextBox" size="12" readonly="true"/>&nbsp;<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar_previous("dcDate",WorkOrderEdit.dcDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>

			<td><input type="hidden" name="proEnteredValue"/></td>
	</tr>		 
    </table>
	</fieldset>
	<%
		if ((objStdHours.isStdhrs()) && (objStdHours.isOpnlevelstdhrs()) && (objStdHours.isIncentive()) && (objStdHours.isJobopnlevelincentive()))
		{
	%>
			<table width="100%" cellpadding="0" cellspacing="0" id="tblJobOprnDtls" style="display:none">
			<tr>
				<td>
				<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.workorder.joboperationdetails"/></legend>
					<table width="100%" cellspacing="0" cellpadding="5">
					<tr> 
						<td class="TopLnk">[ <a href="#" onClick="addRow(document.forms[0]);"><bean:message key="prodacs.common.add"/></a> ]
						[ <a href="#" onClick="insertRow(document.forms[0]);"><bean:message key="prodacs.common.insert"/></a> ]
						[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
					</tr>
					<tr>
						<td class="FieldTitle"><bean:message key="prodacs.job.operationgroupname"/>&nbsp;&nbsp; : &nbsp;&nbsp;
							<html:select property="selectJobOperations" styleClass="Combo">
							<html:option value="0">-- Operation Group Name --</html:option>
							<html:options collection="operGrpCode" property="key" labelProperty="value" />
							</html:select></td>
					</tr>
					</table>
					<br>
					<table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
						<td width="27" class="Header"><input type="checkbox" name="OpnCheckAll" onClick="OpnChkAll(document.forms[0]);"/></td>
						<td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>	                 
						<td class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
						<td width="175" class="Header"><bean:message key="prodacs.job.operationname"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
						<td width="100" class="Header"><bean:message key="prodacs.job.incentive"/></td>
					</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0" id="tbl">
					<tr>
						<td width="24" class="TableItems"><input name="CheckValue" type="checkbox" id="CheckValue" /></td>
						<td width="107" class="TableItems"><input readonly name="serial" class="TextBoxFull" maxlength="10"  ></td>
						<td class="TableItems"><input readonly name="opnGrpName" class="TextBoxFull" maxlength="50"  ></td>
						<td width="172" class="TableItems"><input type="text" name="jobOperationName" class="thinTbox" size="31" onfocus="operName(this);" /></td>
						<td width="97" class="TableItems"><input type="text" name="jobStandardHrs" class="thinTbox" size="16" onkeypress="this.id='selected';return isFloat(this);" onkeyup="this.id='selected'; return isFloat(this);" onclick="this.id='selected';sumHrs();" onblur="sumHrs1();" maxlength="6" /></td>
						<td width="97" align="center" class="TableItems"><html:checkbox property="opnLevelIncentive"/></td>
					</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td colspan="1" width="100" class="FieldTitle" nowrap>Total Std. Hrs </td>
						<td colspan="3" width="1" class="FieldTitle">:</td>
						<td colspan="2"><input type="text" readonly name="totStdHrs" size="10" class="TextBox""/></td>
					</tr>
					</table><br>
					<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
					<tr>
						<td><input type="button" class="Button" onclick="transferWrkOrdList();" value="Move" /></td>
					</tr>
					</table>
				</fieldset>
			</td>
		</tr>
		</table>
		<%
			}
			else if ((!objStdHours.isStdhrs()) && (!objStdHours.isOpnlevelstdhrs()) && (!objStdHours.isIncentive()) && (!objStdHours.isJobopnlevelincentive()))
			{
		%>
			<table width="100%" cellpadding="0" cellspacing="0" id="tblJobOprnDtls1" style="display:none">
			<tr>
				<td>
				<fieldset id="FieldSet"><legend class="FieldTitle"><bean:message key="prodacs.workorder.joboperationdetails"/></legend>
					<table width="100%" cellspacing="0" cellpadding="5">
					<tr> 
						<td class="TopLnk">[ <a href="#" onClick="addRow1(document.forms[0]);"><bean:message key="prodacs.common.add"/></a> ]
						[ <a href="#" onClick="insertRow1(document.forms[0]);"><bean:message key="prodacs.common.insert"/></a> ]
						[ <a href="#" onClick="delRow1(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
					</tr>
					<tr>
						<td class="FieldTitle"><bean:message key="prodacs.job.operationgroupname"/>&nbsp;&nbsp; : &nbsp;&nbsp;
							<html:select property="selectJobOperations" styleClass="Combo">
							<html:option value="0">-- Operation Group Name --</html:option>
							<html:options collection="operGrpCode" property="key" labelProperty="value" />
							</html:select></td>
					</tr>
					</table>
					<br>
					<table width="100%" cellspacing="0" cellpadding="0">
					<tr> 
						<td width="27" class="Header"><input type="checkbox" name="OpnCheckAll" onClick="OpnChkAll(document.forms[0]);"/></td>
						<td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>	                 
						<td class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
						<td width="175" class="Header"><bean:message key="prodacs.job.operationname"/></td>
					</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0" id="tbl1">
					<tr>
						<td width="24" class="TableItems"><input name="CheckValue" type="checkbox" id="CheckValue" /></td>
						<td width="107" class="TableItems"><input readonly name="serial" class="TextBoxFull" maxlength="10"  ></td>
						<td class="TableItems"><input readonly name="opnGrpName" class="TextBoxFull" maxlength="50"  ></td>
						<td width="172" class="TableItems"><input type="text" name="jobOperationName" class="thinTbox" size="31" onfocus="operName(this);" /></td>
					</tr>
					</table>
					<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
					<tr>
						<td><input type="button" class="Button" onclick="transferWrkOrdList1();" value="Move" /></td>
					</tr>
					</table>
				</fieldset>
			</td>
		</tr>
		</table>
		<%
			}
		%>
	<table width="100%" cellpadding="0" cellspacing="0" id="tblWOJobDtls" style="display:none">
	<tr>
	<td>
		<fieldset id="FieldSet">
        <legend class="FieldTitle"><bean:message key="prodacs.workorder.workorderjoblist"/></legend>
        <table width="100%" cellspacing="0" cellpadding="5">
          <tr> 
            <td class="TopLnk"><a href="#" onclick="modifyRow();"><bean:message key="prodacs.common.modify"/></a> | 
              <a href="#" onclick="delWorkOrder();"><bean:message key="prodacs.common.delete"/></a></td>
          </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="25" class="Header"><input type="checkbox" name="WOCheckAll" onClick="WOCheckAllBox(document.forms[0]);" value="checkbox"/></td>
            <td width="25" class="Header"><bean:message key="prodacs.common.sno"/></td>
			<td width="100" class="Header"><bean:message key="prodacs.job.generalname"/></td>
            <td class="Header"><bean:message key="prodacs.job.jobname"/></td>
            <td width="100" class="Header"><bean:message key="prodacs.job.drawing"/></td>
            <td width="100" class="Header"><bean:message key="prodacs.job.revision"/></td>
            <td width="100" class="Header"><bean:message key="prodacs.job.materialtype"/></td>
            <td width="75" class="Header"><bean:message key="prodacs.workorder.jobqtystartsno"/></td>
            <td width="50" class="Header"><bean:message key="prodacs.workorder.jobqty"/></td>
          </tr>
       </table>
       <table width="100%" cellspacing="0" cellpadding="0" id = "wrkOrdList">
          <tr> 
            <td width="23" class="TableItems"><input type="checkbox" name="woCheckValue"/></td>
            <td width="25" class="TableItems"><input readonly name="serialNo" class="TextBoxFull" size="9" maxlength="10"></td>
			<td width="97" class="TableItems"><input readonly name="generalName" class="TextBoxFull" size="9" maxlength="10"></td>
			<td class="TableItems"><input readonly name="jobName" class="TextBoxFull" size="9" maxlength="10"></td>
            <td width="97" class="TableItems"><input readonly name="drawingNo" class="TextBoxFull" size="9" maxlength="10"></td>
            <td width="97" class="TableItems"><input readonly name="revisionNo" class="TextBoxFull" size="9" maxlength="10"></td>
            <td width="97" class="TableItems"><input readonly name="materialType" class="TextBoxFull" size="9" maxlength="10"></td>
            <td width="72" class="TableItems"><input readonly name="qtySerielNo" class="TextBoxFull" size="9" maxlength="10"></td>
            <td width="47" class="TableItems"><input readonly readonly name="jobQty" class="TextBoxFull" size="9" maxlength="10"></td>
			<td><input type="hidden" name="woGeneralId"/></td>
			<td><input type="hidden" name="woJobOperations"/></td>
			<td><input type="hidden" name="woDcNo1"/></td>
			<td><input type="hidden" name="dcDate1"/></td>
			<td><input type="hidden" name="proEntered"/></td>
		 </tr>
        </table>
        </fieldset><br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr> 
            <td><html:button property="modifyWorkOrder" styleClass="Button" onclick="saveWrkOrder()">Modify</html:button></td>
          </tr>
        </table>
	</td>
	</tr>
	</table>
	  </td>
    </tr>
  </table>
  <input type="hidden" name="focusPoint"/>
</html:form>
</body>
</html:html>
