/*
 * Created on Jul 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author ppalaniappan
 *
 * 
 * 
 */
public class WorkOrderJobStatusListForm extends ActionForm
{
    private String proFromDate = "";
	private String proToDate = "";
	private String proJobName = "";
	private String jobSelect = "0";
	private String proDrawingNo = "";
	private String drawingSelect = "0";	
	
	private String formAction = "";
	private String maxItems = "3";	
	
	private String page = "1";
	private String showPage;
	
    /**
     * @return Returns the drawingSelect.
     */
    public String getDrawingSelect()
    {
        return drawingSelect;
    }
    /**
     * @param drawingSelect The drawingSelect to set.
     */
    public void setDrawingSelect(String drawingSelect)
    {
        this.drawingSelect = drawingSelect;
    }
    /**
     * @return Returns the formAction.
     */
    public String getFormAction()
    {
        return formAction;
    }
    /**
     * @param formAction The formAction to set.
     */
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    /**
     * @return Returns the jobSelect.
     */
    public String getJobSelect()
    {
        return jobSelect;
    }
    /**
     * @param jobSelect The jobSelect to set.
     */
    public void setJobSelect(String jobSelect)
    {
        this.jobSelect = jobSelect;
    }
    /**
     * @return Returns the maxItems.
     */
    public String getMaxItems()
    {
        return maxItems;
    }
    /**
     * @param maxItems The maxItems to set.
     */
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    /**
     * @return Returns the page.
     */
    public String getPage()
    {
        return page;
    }
    /**
     * @param page The page to set.
     */
    public void setPage(String page)
    {
        this.page = page;
    }
    /**
     * @return Returns the proDrawingNo.
     */
    public String getProDrawingNo()
    {
        return proDrawingNo;
    }
    /**
     * @param proDrawingNo The proDrawingNo to set.
     */
    public void setProDrawingNo(String proDrawingNo)
    {
        this.proDrawingNo = proDrawingNo;
    }
    /**
     * @return Returns the proFromDate.
     */
    public String getProFromDate()
    {
        return proFromDate;
    }
    /**
     * @param proFromDate The proFromDate to set.
     */
    public void setProFromDate(String proFromDate)
    {
        this.proFromDate = proFromDate;
    }
    /**
     * @return Returns the proJobName.
     */
    public String getProJobName()
    {
        return proJobName;
    }
    /**
     * @param proJobName The proJobName to set.
     */
    public void setProJobName(String proJobName)
    {
        this.proJobName = proJobName;
    }
    /**
     * @return Returns the proToDate.
     */
    public String getProToDate()
    {
        return proToDate;
    }
    /**
     * @param proToDate The proToDate to set.
     */
    public void setProToDate(String proToDate)
    {
        this.proToDate = proToDate;
    }
    /**
     * @return Returns the showPage.
     */
    public String getShowPage()
    {
        return showPage;
    }
    /**
     * @param showPage The showPage to set.
     */
    public void setShowPage(String showPage)
    {
        this.showPage = showPage;
    }
}
/***
$Log: WorkOrderJobStatusListForm.java,v $
Revision 1.3  2005/07/22 11:35:47  bkannusamy
initial commit of jobstatus

Revision 1.2  2005/07/18 14:09:10  ppalaniappan
Sort field initially assigned workOrderDate

Revision 1.1  2005/07/18 13:05:36  ppalaniappan
Initial commit.

***/