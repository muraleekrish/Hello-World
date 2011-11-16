<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
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
<%@ page import="com.savantit.prodacs.businessimplementation.reports.EmployeePayrollDetails"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<useradmin:userrights resource="47"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Monthly Payroll Report Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	EmployeePayrollDetails[] empPayrollDets;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String dates = request.getParameter("payrollId");
	String ids = request.getParameter("payrollIds");
	if (BuildConfig.DMODE)
	{
		System.out.println("Dates :"+dates);
		System.out.println("IDs :"+ids);
	}

	try
	{
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionReportsDetailsManager");
   		obj.setEnvironment();
   		
		/* 	Creating the Home and Remote Objects 	*/
		SessionReportsDetailsManagerHome payrollHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
		SessionReportsDetailsManager payrollObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(payrollHomeObj.create(),SessionReportsDetailsManager.class);
		
		empPayrollDets = payrollObj.fetchEmployeePayrollDetails(Integer.parseInt(dates.trim()));
		dataSource = new JRBeanArrayDataSource(empPayrollDets);
			
		parameters.put("title", "Monthly Payroll Details from "+ids);
		parameters.put("dates",dates);
		parameters.put("ids",ids);

		is = this.getClass().getClassLoader().getResourceAsStream("/MonthlyPayroll.jasper");
		jr = (JasperReport)JRLoader.loadObject(is);

	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
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
				document.forms[0].action='<bean:message key="context"/>/Reports/MonthlyPayroll/MonthlyPayrollResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/MonthlyPayroll/MonthlyPayroll.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/MonthlyPayroll/MonthlyPayrollEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmMonthlyPayrollRes">
			<input type='hidden' name='payrollId' value='<%=request.getParameter("payrollId")%>'>
			<input type='hidden' name='payrollIds' value='<%=request.getParameter("payrollIds")%>'>
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
		JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters,dataSource);
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.exportReport(); 
	}
%>
			
