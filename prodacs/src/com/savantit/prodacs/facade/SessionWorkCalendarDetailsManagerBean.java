/*
 * Created on Dec 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.facade;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.ejb.SessionBean;

import com.savantit.prodacs.businessimplementation.workcalendar.AvailabilityDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.ShiftDefnDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarDetailsManager;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
/**
 * @ejb.bean name="SessionWorkCalendarDetailsManager"
 *	jndi-name="SessionWorkCalendarDetailsManagerBean"
 *	type="Stateless" 
 * 
 *--
 * This is needed for JOnAS.
 * If you are not using JOnAS you can safely remove the tags below.
 * @jonas.bean ejb-name="SessionWorkCalendarDetailsManager"
 *	jndi-name="SessionWorkCalendarDetailsManagerBean"
 * 
 *--
 **/
public abstract class SessionWorkCalendarDetailsManagerBean implements SessionBean 
{
	WorkCalendarDetailsManager objWorkCalendarDetailsManager = new WorkCalendarDetailsManager();
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addShiftDefinition(ShiftDefnDetails objShiftDefnDetails) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.addShiftDefinition(objShiftDefnDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean updateShiftDefinition(ShiftDefnDetails objShiftDefnDetails) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.updateShiftDefinition(objShiftDefnDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap deleteShiftDefinition(Vector shiftIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.deleteShiftDefinition(shiftIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public ShiftDefnDetails getShiftDefinition(int shiftId) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getShiftDefinition(shiftId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public LinkedHashMap getShiftDefnNameList() throws WorkCalendarException,SQLException
	{ 
		return objWorkCalendarDetailsManager.getShiftDefnNameList(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAllShiftDefnDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getAllShiftDefnDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeShiftDefinitionValid(Vector shiftIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeShiftDefinitionValid(shiftIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeShiftDefinitionInValid(Vector shiftIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeShiftDefinitionInValid(shiftIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addBaseCalendar(BaseCalendarDetails objBaseCalendarDetails) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.addBaseCalendar(objBaseCalendarDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean updateBaseCalendar(BaseCalendarDetails objBaseCalendarDetails) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.updateBaseCalendar(objBaseCalendarDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap deleteBaseCalendar(Vector baseCalIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.deleteBaseCalendar(baseCalIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public BaseCalendarDetails getBaseCalendarDetails(int baseCalId) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getBaseCalendarDetails(baseCalId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getBaseCalendarNameList() throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getBaseCalendarNameList(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAllBaseCalendarDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getAllBaseCalendarDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeBaseCalendarValid(Vector baseCalIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeBaseCalendarValid(baseCalIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeBaseCalendarInValid(Vector baseCalIds) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeBaseCalendarInValid(baseCalIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addAvailabilityCalendar(AvailabilityDetails objAvailabilityDetails) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.addAvailabilityCalendar(objAvailabilityDetails);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addCustomAvbltyDetails(AvailabilityDetails objAvailabilityDetails,DBConnection con) throws WorkCalendarException,SQLException
	{ 
		return objWorkCalendarDetailsManager.addCustomAvbltyDetails(objAvailabilityDetails,con);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public boolean addCustomNonAvbltyDetails(AvailabilityDetails objAvailabilityDetails,DBConnection con) throws WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.addCustomNonAvbltyDetails(objAvailabilityDetails,con);
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap deleteAvailablityCalendar(Vector avlbltyIds) throws  WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.deleteAvailablityCalendar(avlbltyIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public AvailabilityDetails getAvailabilityDetails(int avbltyId) throws  WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getAvailabilityDetails(avbltyId); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAvailabilityNameList() throws  WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.getAvailabilityNameList(); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap getAllAvailabilityDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws WorkCalendarException,SQLException
	{ 
		return objWorkCalendarDetailsManager.getAllAvailabilityDetails(filters,sortBy,ascending,startIndex,displayCount); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeAvailablityCalendarValid(Vector avlbltyIds) throws  WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeAvailablityCalendarValid(avlbltyIds); 
	}
	/**
	 * @ejb.interface-method
	 *	view-type="remote" 
	**/
	public HashMap makeAvailablityCalendarInValid(Vector avlbltyIds) throws  WorkCalendarException, SQLException
	{ 
		return objWorkCalendarDetailsManager.makeAvailablityCalendarInValid(avlbltyIds); 
	}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean updateAvailabilityCalendar(AvailabilityDetails objAvailabilityDetails) throws WorkCalendarException, SQLException{ 
 return objWorkCalendarDetailsManager.updateAvailabilityCalendar(objAvailabilityDetails);
}
/**
 * @ejb.interface-method
 *	view-type="remote" 
**/
public boolean chooseAvbltyName(int avbltyId) throws WorkCalendarException, SQLException{ 
 return objWorkCalendarDetailsManager.chooseAvbltyName(avbltyId);
}
}
/***
$Log: SessionWorkCalendarDetailsManagerBean.java,v $
Revision 1.5  2005/05/27 09:44:34  kduraisamy
signature changed.HashMap is changed to LinkedHashmap.

Revision 1.4  2005/05/05 08:49:01  kduraisamy
signature added for chooseAvbltyName(avbltyid).

Revision 1.3  2005/04/27 06:04:15  kduraisamy
signatures added for updateAvailabilityDetails().

Revision 1.2  2005/04/20 10:09:17  kduraisamy
signature changed for addCustomAvbltyDetails().

Revision 1.1  2004/12/03 06:06:22  sduraisamy
Initial commit of SessionWorkCalendarDetailsManagerBean

***/