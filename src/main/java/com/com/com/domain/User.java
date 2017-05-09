package com.com.com.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
Why do Dates get written as numbers?

Default serializer for java.util.Date (and related, such as java.util.Calendar) serialize them using the most efficient accurate representation, so-called epoch timestamp (number of milliseconds since January 1st, 1970, UTC). Assumption is that if user does not care, we should use efficient and accurate representation to get job bdone.

One exception is java.sql.Date, which will always be represent in textual form (but note: use of java.sql.Date is strongly discouraged due to multiple issues, see below for more details)
*/

/*
Notes on java.sql.Date

(aka "Please do NOT use java.sql.Date, ever!")
*/

/*
{"id":1,"name":"spring","someTime":1464059668503,"anotherTime":"2016-05-24 11:14:28"}
*/

/* http://stackoverflow.com/questions/7556851/set-jackson-timezone-for-date-deserialization
 * 
There's a dedicated page about date & time matters in the Jackson FAQ, it might be a good idea to start with that one - it starts by stating that by default Jackon operates in the UTC time zone.

You'll also see that the more recent versions of Jackson support jodatime out of the box, and given that joda's far superior to Date it might be wise to use joda in your transfer object.
*/
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
	private long id;
	private String name;
	
	private Date someTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date anotherTime;
	
	@JsonProperty(value="Password")
	@JsonInclude(Include.NON_NULL) 
	private String password;
	
	@JsonInclude(Include.NON_NULL)
	private Integer optionalval;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getSomeTime() {
		return someTime;
	}
	public void setSomeTime(Date someTime) {
		this.someTime = someTime;
	}
	public Date getAnotherTime() {
		return anotherTime;
	}
	public void setAnotherTime(Date anotherTime) {
		this.anotherTime = anotherTime;
	}
	public Integer getOptionalval() {
		return optionalval;
	}
	public void setOptionalval(Integer optionalval) {
		this.optionalval = optionalval;
	}
	
}
