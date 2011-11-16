<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.engine.JasperPrint" %>
<%@ page import="net.sf.jasperreports.engine.JasperFillManager" %>
<%@ page import="net.sf.jasperreports.engine.JRExporterParameter" %>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.EmployeePerformanceReturnDetails" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="34"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Employee Performance Details Starts.");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	EmployeePerformanceReturnDetails[] empPerDetObj;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String empId = request.getParameter("employeeName");
	String empTypName = request.getParameter("empTypName");
	String empName = request.getParameter("empName");
	if (BuildConfig.DMODE)
	{
		System.out.println("EmpTypName: "+ empTypName);
		System.out.println("EmpName: "+ empName);
		System.out.println("Start&End Date: "+ startDate+"&"+endDate);
	}
	
	if ((startDate != "") && (endDate != ""))
	{
		String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		StringTokenizer frmDate = new StringTokenizer(startDate.trim(),"-");
		
		String tempFrmDate[] = new String[frmDate.countTokens()];
		int i = 0;
		while(frmDate.hasMoreTokens())
		{
			tempFrmDate[i] = frmDate.nextToken();
			if (BuildConfig.DMODE)
			{
				System.out.println("Frm: "+tempFrmDate[i]);
			}
			i++;
		}
		
		StringTokenizer toDate = new StringTokenizer(endDate.trim(),"-");
		String tempToDate[] = new String[toDate.countTokens()];
		i = 0;
		while (toDate.hasMoreTokens())
		{
			tempToDate[i] = toDate.nextToken();
			if (BuildConfig.DMODE)
				System.out.println("Frm: "+tempToDate[i]);
			i++;
		}
		
		String stDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
		String edDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
		
		if (BuildConfig.DMODE)
			System.out.println("--> "+stDate+"&"+edDate+"&"+empId);
		/* For Start Date */
		StringTokenizer st = new StringTokenizer(startDate,"-");
		int yr = 0;
		int mnth = 0;
		int day = 0;
		if(st.hasMoreTokens())
		{
			yr = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{
			mnth = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{		
			day = Integer.parseInt(st.nextToken().trim());
		}
		GregorianCalendar ge = new GregorianCalendar(yr,mnth-1,day);

		/* To End Date */
		StringTokenizer st1 = new StringTokenizer(endDate,"-");
		yr = 0;
		mnth = 0;
		day = 0;
		if(st1.hasMoreTokens())
		{
			yr = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{
			mnth = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{		
			day = Integer.parseInt(st1.nextToken().trim());
		}
		GregorianCalendar ge1 = new GregorianCalendar(yr,mnth-1,day);
		
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome empRefHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager empRefObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(empRefHomeObj.create(),SessionReportsDetailsManager.class);
		
			empPerDetObj = empRefObj.fetchEmployeePerformance(ge.getTime(),ge1.getTime(),Integer.parseInt(empId));
			if (BuildConfig.DMODE)
				System.out.println("Length: "+empPerDetObj.length);
			dataSource = new JRBeanArrayDataSource(empPerDetObj);
			
			parameters.put("Title", "Performance of "+empTypName+" "+empName+" from "+stDate+" to "+edDate);

			parameters.put("startDate",stDate);
			parameters.put("endDate",edDate);
			parameters.put("empId",empId);

			is = this.getClass().getClassLoader().getResourceAsStream("EmployeePerformance.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);

		}catch (Exception e)
		{
			if (BuildConfig.DMODE)
				System.out.println("Error: "+ e.toString());
		}
	}
	if (isPdf.equals("1"))
	{
		byte[] bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
		if (BuildConfig.DMODE)
			System.out.println("Length: "+ bytes.length);
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}
	else
	{
%>
		<html>
		<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
		<script>
			function printReport()
			{
				printTab.style.display="none";
				window.print();
				printTab.style.display="block";
			}
			
			function pdfReport()
			{
				document.forms[0].pdf.value = "1";
				document.forms[0].action='<bean:message key="context"/>/Reports/EmployeePerformance/EmployeePerformanceResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/EmployeePerformance/EmployeePerformance.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/EmployeePerformance/EmployeePerformanceEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmEmpPerfrmRes">
			<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
			<input type='hidden' name='endDate' value='<%=request.getParameter("endDate")%>'>
			<input type='hidden' name='employeeName' value='<%=request.getParameter("employeeName")%>'>
			<input type='hidden' name='empTypName' value='<%= request.getParameter("empTypName") %>'>
			<input type='hidden' name='empName' value='<%= request.getParameter("empName") %>'>
			<input type='hidden' name='pdf'>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="printTab">
				<tr>
					<td class="TopLnk"><a href="javascript:printReport()">Print</a> | 
					<a href="javascript:emailReport()">Email</a> | 
					<a href="javascript:pdfReport()">PDF</a> | <a href="javascript:closeReport()">Close</a></td>
					<td>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form>
		</html>    
<%
		try
		{
			JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters,dataSource);
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			exporter.exportReport(); 
		}
		catch(Exception e)
		{
		if (BuildConfig.DMODE)
			e.printStackTrace();
		}
	}
%>
