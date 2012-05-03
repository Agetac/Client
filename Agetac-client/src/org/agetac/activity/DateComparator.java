package org.agetac.activity;

import java.util.Comparator;
import java.util.Date;

import org.agetac.common.dto.MessageDTO;

public class DateComparator implements Comparator<MessageDTO>{

	@Override
	public int compare(MessageDTO m1, MessageDTO m2) {
		
	if(m1.getDate().before(m2.getDate())){return 1;}	
		
	else if(m1.getDate().after(m2.getDate())){return -1;}
	
	else {return 0;}
	}
	
	

}
