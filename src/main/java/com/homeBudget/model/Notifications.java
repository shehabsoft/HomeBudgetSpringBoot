package com.homeBudget.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the notification_template database table.
 * 
 */
@Entity
@Table(name="notifications")
@NamedQuery(name="Notifications.findAll", query="SELECT n FROM Notifications n")
public class Notifications implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;


	public String getNotification_content() {
		return notification_content;
	}

	public void setNotification_content(String notification_content) {
		this.notification_content = notification_content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Lob
	@Column(name="notification_content")
	private String notification_content;

	@Column(name="mail_to")
	private String mail_to;

	@Column(name="status")
	private Integer status;

	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}