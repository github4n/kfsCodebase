package com.newcore.bmp.models.email;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class EmailSubscription {

	String system;
	String type;
	String emailAddress;
	String emailName;
	String englishName;

}
