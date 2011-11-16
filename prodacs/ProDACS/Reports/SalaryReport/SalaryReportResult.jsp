<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
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
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.SalaryReportDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<useradmin:userrights resource="30"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Salary Report Result Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	SalaryReportDetails[] objSalaryReportDetails;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String month = request.getParameter("month");
	String year = request.getParameter("year");
	String machineCode = request.getParameter("machineCode");
	if (BuildConfig.DMODE)
		System.out.println("Month&Year : "+ month+"&"+year);
	
	if ((month != "") && (year != ""))
	{
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome monthlyHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager monthlyObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(monthlyHomeObj.create(),SessionReportsDetailsManager.class);
			
			objSalaryReportDetails = monthlyObj.fetchSalaryReports(Integer.parseInt(month), Integer.parseInt(year));
			
			dataSource = new JRBeanArrayDataSource(objSalaryReportDetails);
			
			String[] mon = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			String strMonth = mon[Integer.parseInt(month)-1];
			if (BuildConfig.DMODE)
				System.out.println("Str Month: "+ strMonth);

			parameters.put("Title", "Salary Chart for "+strMonth+" ' "+year);
			parameters.put("month",month);
			parameters.put("year",year);
			
			is = this.getClass().getClassLoader().getResourceAsStream("/reports/SalaryReport.jasper");
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
				document.forms[0].action='<bean:message key="context"/>/Reports/SalaryReport/SalaryReportResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/SalaryReport/SalaryReport.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/SalaryReport/SalaryReportEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmSalaryReportResult">
			<input type='hidden' name='month' value='<%=request.getParameter("month")%>'>
			<input type='hidden' name='year' value='<%=request.getParameter("year")%>'>
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
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
%>
