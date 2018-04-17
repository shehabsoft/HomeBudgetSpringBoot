package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the info_trace_log database table.
 * 
 */
@Entity
@Table(name="info_trace_log")
@NamedQuery(name="InfoTraceLog.findAll", query="SELECT i FROM InfoTraceLog i")
public class InfoTraceLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INFO_TRACE_LOG_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INFO_TRACE_LOG_ID_GENERATOR")
	private int id;

	@Column(name="request_data")
	private String requestData;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="request_date")
	private Date requestDate;

	@Column(name="response_data")
	private String responseData;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="response_date")
	private Date responseDate;

	public InfoTraceLog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestData() {
		return this.requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getResponseData() {
		return this.responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Date getResponseDate() {
		return this.responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

}