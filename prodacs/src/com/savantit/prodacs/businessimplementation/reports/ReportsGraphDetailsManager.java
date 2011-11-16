/*
 * Created on Dec 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.businessimplementation.reports;

import java.awt.Color;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.savantit.prodacs.facade.SessionReportsDetailsManager;
import com.savantit.prodacs.facade.SessionReportsDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsGraphDetailsManager
{
	/*public String generateXYGraph(Date startDate,Date endDate,HttpSession session,PrintWriter pw)
	{
		String fileName = null;
		try
		{
			//Retrieving values for Machine Utilization for the given dates
			MachineUtilizationReportDetails[] reportDetails = objReportsDetailsManager.fetchMachineUtilization(startDate,endDate);
			System.out.println("Length :"+reportDetails.length);
			if (reportDetails.length == 0)
			{
				if (BuildConfig.DMODE)
					System.out.println("No Data found for given time period.");
				fileName = "nodata";
				throw new ReportsException("NO DATA FOUND FOR GIVEN TIME PERIOD","EC","");
			}
			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			for (int i = 0; i < reportDetails.length; i++)
			{
				System.out.println("MC CODE :"+reportDetails[i].getMcCode());
				System.out.println("MC NAME :"+reportDetails[i].getMcName());
				StringTokenizer st = new StringTokenizer(reportDetails[i].getUtiPer(),"%");
				System.out.println("Length :"+i);
				System.out.println("");
				dataSet.addValue(new Double(st.nextToken().trim()),reportDetails[i].getMcCode(),reportDetails[i].getMcCode());
			}
			//Create the chart object
	        CategoryAxis categoryAxis = new CategoryAxis("Machine Name");
	        ValueAxis valueAxis = new NumberAxis("Util Percentage");
	        BarRenderer renderer = new BarRenderer();
	        renderer.setMinimumBarLength(100);
	        renderer.setItemURLGenerator(new StandardCategoryURLGenerator("/ProDACS/Reports/MachineUtilization/MachineUtilChartResult.jsp","Util Percentage","Machine Name"));
	        renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	        
	        Plot plot = new CategoryPlot(dataSet, categoryAxis, valueAxis, renderer);
	        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	        chart.setBackgroundPaint(java.awt.Color.white);

	        //  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 900, 500, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info,false);
	        pw.flush();
	    }
		catch (ReportsException re)
		{
			fileName = "nodata";
			re.printStackTrace();
		}
		catch (Exception e)
		{
			fileName = "error";
			e.printStackTrace();
		}
		System.out.println("File Name :"+fileName);
		return fileName;
	}*/
	
	public String generateXYGraph(Date startDate,Date endDate,HttpSession session,PrintWriter pw)
	{
		String fileName = null;
		try
		{
			//Retrieving values for Machine Utilization for the given dates
		    
		    EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
	   		//ReportsDetailsManager reportsObj = new ReportsDetailsManager();
	   		MachineUtilizationReportDetails[] reportDetails = reportsObj.fetchMachineUtilization(startDate,endDate);
		
			System.out.println("Length :"+reportDetails.length);
			if (reportDetails.length == 0)
			{
				if (BuildConfig.DMODE)
					System.out.println("No Data found for given time period.");
				fileName = "nodata";
				throw new ReportsException("NO DATA FOUND FOR GIVEN TIME PERIOD","EC","");
			}
							
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
			
			for (int i = 0; i < reportDetails.length; i++)
			{
				StringTokenizer st = new StringTokenizer(reportDetails[i].getUtiPer(),"%");
				dataset.setValue(new Double(st.nextToken().trim()),reportDetails[i].getMcCode(),"");
			}
			
			
			JFreeChart chart = ChartFactory.createBarChart("Machine Utilization","Machine Code","Utilization %",dataset,PlotOrientation.VERTICAL,true,true,false);
			
		
//			 get a reference to the plot for further customisation...
		    CategoryPlot plot = chart.getCategoryPlot();
		   // plot.setBackgroundPaint(Color.lightGray);
		    plot.setDomainGridlinePaint(Color.white);
		    plot.setDomainGridlinesVisible(true);
		    plot.setRangeGridlinePaint(Color.white);
		 
		    // set the range axis to display integers only...
		    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 
		    // disable bar outlines...
		    BarRenderer renderer = (BarRenderer) plot.getRenderer();
		    DecimalFormat decimalformat1 = new DecimalFormat("##,###.00");
		    renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
		    renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",decimalformat1));
		    renderer.setItemLabelsVisible(true);
		    renderer.setBaseItemLabelsVisible(true);
		    
		    chart.getCategoryPlot().setRenderer(renderer);
		 
			
			
		    //  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info,false);
	        pw.flush();
	    }
		catch (ReportsException re)
		{
			fileName = "nodata";
			re.printStackTrace();
		}
		catch (Exception e)
		{
			fileName = "error";
			e.printStackTrace();
		}
		System.out.println("File Name :"+fileName);
		return fileName;
	}
	
	public String generateBarEmployeePerformanceByEmpType(Date startDate,Date endDate,int empTypeId,HttpSession session,PrintWriter pw)
	{

		String fileName = null;
		try
		{
		    EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
	   		//ReportsDetailsManager reportsObj = new ReportsDetailsManager();
		    //Retrieving values for Machine Utilization for the given dates
		    EmployeePerformanceReturnDetails[] empPerformance = reportsObj.fetchEmployeePerformanceByEmpType(startDate,endDate,empTypeId);
			System.out.println("Length :"+empPerformance.length);
			if (empPerformance.length == 0)
			{
				if (BuildConfig.DMODE)
					System.out.println("No Data found for given time period.");
				fileName = "nodata";
				throw new ReportsException("NO DATA FOUND FOR GIVEN TIME PERIOD","EC","");
			}
							
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
			
			boolean flg = true;
			
			for(int i = 0; i<empPerformance.length-1 && flg ;i++)
			{
			    flg = false;
			    for(int j = 0; j<empPerformance.length-i-1;j++)
			    {
			        if(((EmployeePerformanceTotDetails)((Vector)empPerformance[j].getVecEmployeePerformanceTotDetails()).get(0)).getTotOpnHrs() < ((EmployeePerformanceTotDetails)((Vector)empPerformance[j+1].getVecEmployeePerformanceTotDetails()).get(0)).getTotOpnHrs())
			        {
			            EmployeePerformanceReturnDetails objEmployeePerformanceReturnDetails = empPerformance[j];
			            empPerformance[j] = empPerformance[j+1];
			            empPerformance[j+1] = objEmployeePerformanceReturnDetails;
			            flg = true;
			        }
			        
			    }
			}
			
			
		    				   		
			for (int i = 0; i < empPerformance.length; i++)
			{
			    EmployeePerformanceReturnDetails objEmployeePerformanceReturnDetails = new EmployeePerformanceReturnDetails();
			    objEmployeePerformanceReturnDetails = empPerformance[i];
			    Vector vec = objEmployeePerformanceReturnDetails.getVecEmployeePerformanceTotDetails();
			    
			    if(vec.size()>0)
			    {
			        EmployeePerformanceTotDetails objEmployeePerformanceTotDetails = (EmployeePerformanceTotDetails)vec.get(0);
			        if(BuildConfig.DMODE)
			        {
			            System.out.println("EmpName:"+objEmployeePerformanceTotDetails.getEmpName());
			            System.out.println("EmpTotHrs:"+objEmployeePerformanceTotDetails.getTotOpnHrs());
			            System.out.println("EmpSavedHrs:"+objEmployeePerformanceTotDetails.getTotSavedHrs());
			            System.out.println("EmpexceedHrs:"+objEmployeePerformanceTotDetails.getTotExceededHrs());
			            System.out.println("Net Saved Hrs:"+(objEmployeePerformanceTotDetails.getTotSavedHrs() - objEmployeePerformanceTotDetails.getTotExceededHrs()));
			        }
			        
			        dataset.setValue(new Double(objEmployeePerformanceTotDetails.getTotOpnHrs()),"Tot Hrs",objEmployeePerformanceTotDetails.getEmpName());
			        dataset.setValue(new Double(objEmployeePerformanceTotDetails.getTotSavedHrs()),"Saved Hrs",objEmployeePerformanceTotDetails.getEmpName());
			        dataset.setValue(new Double(objEmployeePerformanceTotDetails.getTotExceededHrs()),"Exceeded Hrs",objEmployeePerformanceTotDetails.getEmpName());
			        dataset.setValue(new Double((objEmployeePerformanceTotDetails.getTotSavedHrs() - objEmployeePerformanceTotDetails.getTotExceededHrs())),"Effective Hrs",objEmployeePerformanceTotDetails.getEmpName());
			        
			    }
			}
			
			
			JFreeChart chart = ChartFactory.createBarChart("Operator Performance","EMPLOYEE NAME","PERFORMANCE",dataset,PlotOrientation.VERTICAL,true,true,false);
	        
			
			 
			    CategoryPlot plot = chart.getCategoryPlot();
			   // plot.setBackgroundPaint(Color.lightGray);
			    plot.setDomainGridlinePaint(Color.white);
			    plot.setDomainGridlinesVisible(true);
			    plot.setRangeGridlinePaint(Color.white);
			 
			    // set the range axis to display integers only...
			    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			 
			    // disable bar outlines...
			    BarRenderer renderer = (BarRenderer) plot.getRenderer();
			    DecimalFormat decimalformat1 = new DecimalFormat("##,###.00");
			    renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
			    renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",decimalformat1));
			    renderer.setItemLabelsVisible(true);
			    renderer.setBaseItemLabelsVisible(true);
			    
			    chart.getCategoryPlot().setRenderer(renderer);
			    
			
			//  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 2900, 600, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info,false);
	        pw.flush();
	    }
		catch (ReportsException re)
		{
			fileName = "nodata";
			re.printStackTrace();
		}
		catch (Exception e)
		{
			fileName = "error";
			e.printStackTrace();
		}
		System.out.println("File Name :"+fileName);
		return fileName;
	
	}
	
	public String generateWaterfallChartForIdleBrkDwn(Date fromDate,Date toDate,String machCode,HttpSession session,PrintWriter pw)
	{
		String fileName = "";
		
		try
		{
			
		    EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment */
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects */	
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
	   		//ReportsDetailsManager reportsObj = new ReportsDetailsManager();
	   				
		    
		    IdleBrkdwnDetails objIdleBrkdwnDetails = reportsObj.fetchIdleBrkdwnHrs(fromDate,toDate,machCode);

		    DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		    
			
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getElecHrs()),machCode,"Electrical");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getDrgHrs()),machCode,"Drawing");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getGaugeHrs()),machCode,"Gauge");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getInstHrs()),machCode,"Instrumental");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getJobHrs()),machCode,"Job");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getManHrs()),machCode,"Man");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getMechHrs()),machCode,"Mechanical");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getPccHrs()),machCode,"PCC");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getToolsHrs()),machCode,"Tools");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getOthersHrs()),machCode,"Others");
			dataSet.setValue(new Double(objIdleBrkdwnDetails.getDrgHrs()+objIdleBrkdwnDetails.getElecHrs()+objIdleBrkdwnDetails.getGaugeHrs()+objIdleBrkdwnDetails.getInstHrs()+objIdleBrkdwnDetails.getJobHrs()+objIdleBrkdwnDetails.getManHrs()+objIdleBrkdwnDetails.getMechHrs()+objIdleBrkdwnDetails.getPccHrs()+objIdleBrkdwnDetails.getToolsHrs()+objIdleBrkdwnDetails.getOthersHrs()),machCode,"Total Hours");
				
			
			JFreeChart chart = ChartFactory.createWaterfallChart("Idle/BreakDown for "+machCode,"Reasons","Hours",dataSet,PlotOrientation.VERTICAL,true,true,false);	        
			chart.setBackgroundPaint(java.awt.Color.white);

//			 get a reference to the plot for further customisation...
		    CategoryPlot plot = chart.getCategoryPlot();
		   // plot.setBackgroundPaint(Color.lightGray);
		    plot.setDomainGridlinePaint(Color.white);
		    plot.setDomainGridlinesVisible(true);
		    plot.setRangeGridlinePaint(Color.white);
		 
		    // set the range axis to display integers only...
		    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 
		    // disable bar outlines...
		    BarRenderer renderer = (BarRenderer) plot.getRenderer();
		    DecimalFormat decimalformat1 = new DecimalFormat("##,###.00");
		    renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
		    renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",decimalformat1));
		    renderer.setItemLabelsVisible(true);
		    renderer.setBaseItemLabelsVisible(true);
		    
		    chart.getCategoryPlot().setRenderer(renderer);
		    
	
			
	        //  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 300, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info, true);
	        pw.flush();

	    } 
		catch (ReportsException e)
		{
	        System.out.println(e.toString());
	        fileName = "nodata";
	    }
		catch (Exception e) 
		{
	        System.out.println("Exception - " + e.toString());
	        e.printStackTrace(System.out);
	        fileName = "error";
	    }
		return fileName;
	
	    
	}
	
	public String generateStackedChartForIdleBrkDwn(Date fromDate,Date toDate, HttpSession session,PrintWriter pw)
	{
		String fileName = "";
		
		try
		{
			
		    EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment for Reports Details Manager*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects for Reports Details Manager */
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
	   		//ReportsDetailsManager objRD = new ReportsDetailsManager();
		    //MachineDetailsManager machineObj = new MachineDetailsManager();
		    
		    HashMap hm = reportsObj.getAllMachines();
		    Iterator itr = hm.keySet().iterator();
		    DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		    
		   
		    while(itr.hasNext())
		    {
		        String mcCde = itr.next().toString();
		        IdleBrkdwnDetails objIdleBrkdwnDetails = reportsObj.fetchIdleBrkdwnHrs(fromDate,toDate,mcCde);
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getElecHrs()),mcCde,"Electrical");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getDrgHrs()),mcCde,"Drawing");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getGaugeHrs()),mcCde,"Gauge");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getInstHrs()),mcCde,"Instrumental");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getJobHrs()),mcCde,"Job");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getManHrs()),mcCde,"Man");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getMechHrs()),mcCde,"Mechanical");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getPccHrs()),mcCde,"PCC");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getToolsHrs()),mcCde,"Tools");
		        
		        dataSet.setValue(new Double(objIdleBrkdwnDetails.getOthersHrs()),mcCde,"Others");
		        
		     }
		    
		    
		    
			
			
		    
		    JFreeChart chart = ChartFactory.createStackedBarChart("Overall Idle/BreakDown","Reasons","Hours",dataSet,PlotOrientation.VERTICAL,true,true,false);
			chart.setBackgroundPaint(java.awt.Color.white);

			
//			 get a reference to the plot for further customisation...
		    CategoryPlot plot = chart.getCategoryPlot();
		   // plot.setBackgroundPaint(Color.lightGray);
		    plot.setDomainGridlinePaint(Color.white);
		    plot.setDomainGridlinesVisible(true);
		    plot.setRangeGridlinePaint(Color.white);
		 
		    // set the range axis to display integers only...
		    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 
		    // disable bar outlines...
		    BarRenderer renderer = (BarRenderer) plot.getRenderer();
		    DecimalFormat decimalformat1 = new DecimalFormat("##,###.00");
		    renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
		    renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",decimalformat1));
		    renderer.setItemLabelsVisible(true);
		    renderer.setBaseItemLabelsVisible(true);
		    
		    chart.getCategoryPlot().setRenderer(renderer);
		    
	
	        //  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 800, 600, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info, true);
	        pw.flush();

	    } 
		catch (ReportsException e)
		{
	        System.out.println(e.toString());
	        fileName = "nodata";
	    }
		catch (Exception e) 
		{
	        System.out.println("Exception - " + e.toString());
	        e.printStackTrace(System.out);
	        fileName = "error";
	    }
		return fileName;
	
	    
	}
	
	public String generatePieChart(Date fromDate,Date toDate,String machCode,HttpSession session,PrintWriter pw)
	{
		String fileName = "";
		DefaultPieDataset dataSet = new DefaultPieDataset();
		try
		{
			
		    EJBLocator obj = new EJBLocator();
	        /* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
		    
		    IdleBrkdwnDetails objIdleBrkdwnDetails = reportsObj.fetchIdleBrkdwnHrs(fromDate,toDate,machCode);

			dataSet.setValue("Drawing",new Double(objIdleBrkdwnDetails.getDrgHrs()));
			dataSet.setValue("Electrical",new Double(objIdleBrkdwnDetails.getElecHrs()));
			dataSet.setValue("Gauge", new Double(objIdleBrkdwnDetails.getGaugeHrs()));
			dataSet.setValue("Instrumental",new Double(objIdleBrkdwnDetails.getInstHrs()));
			dataSet.setValue("Job",new Double(objIdleBrkdwnDetails.getJobHrs()));
			dataSet.setValue("Man",new Double(objIdleBrkdwnDetails.getManHrs()));
			dataSet.setValue("Mechanical",new Double(objIdleBrkdwnDetails.getMechHrs()));
			dataSet.setValue("Others",new Double(objIdleBrkdwnDetails.getOthersHrs()));
			dataSet.setValue("PCC",new Double(objIdleBrkdwnDetails.getPccHrs()));
			dataSet.setValue("Tools",new Double(objIdleBrkdwnDetails.getToolsHrs()));

			//  Create the chart object
	        PiePlot plot = new PiePlot(dataSet);
	        //plot.setInsets(new Insets(0, 5, 5, 5));
	        plot.setURLGenerator(new StandardPieURLGenerator("/ProDACS/Reports/IdleBreakDown/IdleBreakDownChartResult.jsp","Reason"));
	        //plot.setToolTipGenerator(new StandardPieItemLabelGenerator());
	        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	        chart.setBackgroundPaint(java.awt.Color.white);

	        //  Write the chart image to the temporary directory
	        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        fileName = ServletUtilities.saveChartAsJPEG(chart, 500, 300, info, session);

	        //  Write the image map to the PrintWriter
	        ChartUtilities.writeImageMap(pw, fileName, info, true);
	        pw.flush();

	    } 
		catch (ReportsException e)
		{
	        System.out.println(e.toString());
	        fileName = "nodata";
	    }
		catch (Exception e) 
		{
	        System.out.println("Exception - " + e.toString());
	        e.printStackTrace(System.out);
	        fileName = "error";
	    }
		return fileName;
	}

	public static void main(String args[])
	{
		ReportsGraphDetailsManager objReportsGraphDetailsManager = new ReportsGraphDetailsManager();
		PrintWriter pw = new PrintWriter(System.out);
		//String fileName = objReportsGraphDetailsManager.generateXYGraph(new Date("01-aug-05"),new Date("23-dec-05"),null,pw);
		String fileName = objReportsGraphDetailsManager.generateWaterfallChartForIdleBrkDwn(new Date("01-aug-05"),new Date("23-aug-05"),"PM-M-05",null,pw);
		//String fileName = objReportsGraphDetailsManager.generateBarEmployeePerformanceByEmpType(new Date("01-aug-05"),new Date("23-dec-05"),2,null,pw);
		//String fileName = objReportsGraphDetailsManager.generateStackedChartForIdleBrkDwn(new Date("01-nov-05"),new Date("23-nov-05"),null,pw);
		System.out.println("File Name :"+fileName);
	}
}


/***
$Log: ReportsGraphDetailsManager.java,v $
Revision 1.8  2006/01/06 07:50:20  vkrishnamoorthy
Image title name changed.

Revision 1.7  2006/01/04 07:02:13  kduraisamy
Graphs values added.

Revision 1.6  2005/12/28 09:22:12  kduraisamy
water fall chart added.

Revision 1.5  2005/12/28 09:18:38  kduraisamy
water fall chart added.

Revision 1.4  2005/12/27 10:29:58  kduraisamy
signature added for fetchEmployeePerformanceByType().

Revision 1.3  2005/12/27 10:28:55  kduraisamy
signature added for fetchEmployeePerformanceByType().

Revision 1.2  2005/12/27 07:20:51  vkrishnamoorthy
Graph image size modified.

Revision 1.1  2005/12/26 13:29:49  vkrishnamoorthy
Initial commit on ProDACS.

***/