/*
 * Created on Dec 15, 2005
 *
 * ClassName	:  	SessionSchedulerManagerBean.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.facade;

import java.sql.SQLException;

import com.datrics.scheduler.initializer.FetchTasks;
import com.datrics.scheduler.initializer.MailScheduler;
import com.datrics.scheduler.initializer.MailSchedulerException;
import com.datrics.scheduler.valueobjects.JobDetails;

/**
 * <!-- begin-user-doc --> A generated session bean <!-- end-user-doc --> <!--
 * lomboz.beginDefinition --> <?xml version="1.0" encoding="UTF-8"?> <lomboz:EJB
 * xmlns:j2ee="http://java.sun.com/xml/ns/j2ee"
 * xmlns:lomboz="http://lomboz.objectlearn.com/xml/lomboz"> <lomboz:session>
 * <lomboz:sessionEjb>
 * <j2ee:display-name>SessionSchedulerManager</j2ee:display-name>
 * <j2ee:ejb-name>SessionSchedulerManager</j2ee:ejb-name>
 * <j2ee:ejb-class>com.savantit
 * .prodacs.facade.SessionSchedulerManagerBean</j2ee:ejb-class>
 * <j2ee:session-type>Stateless</j2ee:session-type>
 * <j2ee:transaction-type>Container</j2ee:transaction-type> </lomboz:sessionEjb>
 * </lomboz:session> </lomboz:EJB> <!-- lomboz.endDefinition --> <!--
 * begin-xdoclet-definition -->
 * 
 * @ejb.bean name="SessionSchedulerManager" jndi-name="SessionSchedulerManager"
 *           type="Stateless" transaction-type="Container" -- This is needed for
 *           JOnAS. If you are not using JOnAS you can safely remove the tags
 *           below.
 * @jonas.bean ejb-name="SessionSchedulerManager"
 *             jndi-name="SessionSchedulerManager" -- <!--
 *             end-xdoclet-definition -->
 * @generated
 */
public abstract class SessionSchedulerManagerBean implements
		javax.ejb.SessionBean {
	FetchTasks objFetchTasks = new FetchTasks();
	MailScheduler objMailScheduler = new MailScheduler();

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public Object[] fetch() {
		return objFetchTasks.fetch();
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public JobDetails[] getAllScheduleDetails() throws SQLException,
			MailSchedulerException {
		return objMailScheduler.getAllScheduleDetails();
	}

	/**
	 * @ejb.interface-method view-type="remote"
	 **/
	public boolean updateJbDetails(JobDetails objJobDetails) {
		return objMailScheduler.updateJbDetails(objJobDetails);
	}
}

/*
 * $Log: SessionSchedulerManagerBean.java,v $Revision 1.4 2006/01/10 07:34:19
 * vkrishnamoorthygetAllScheduleDetails() method modified.
 * 
 * Revision 1.3 2006/01/10 07:32:19 vkrishnamoorthygetAllScheduleDetails()
 * method modified.
 * 
 * Revision 1.1 2005/12/15 04:55:13 kduraisamysignature added for scheduler.
 */