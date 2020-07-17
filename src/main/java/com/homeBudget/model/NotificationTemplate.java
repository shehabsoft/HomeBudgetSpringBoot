package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the notification_template database table.
 * 
 */
@Entity
@Table(name="notification_template")
@NamedQuery(name="NotificationTemplate.findAll", query="SELECT n FROM NotificationTemplate n")
public class NotificationTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Integer code;

	private String name;

	//bi-directional one-to-one association to NotificationTemplateSection
	@OneToOne
	@JoinColumn(name="id", referencedColumnName="notification_template_id")
	private NotificationTemplateSection notificationTemplateSection;

	//bi-directional many-to-one association to NotificationTemplateParamter
	@OneToMany(mappedBy="notificationTemplate")
	private List<NotificationTemplateParamter> notificationTemplateParamters;

	public NotificationTemplate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NotificationTemplateSection getNotificationTemplateSection() {
		return this.notificationTemplateSection;
	}

	public void setNotificationTemplateSection(NotificationTemplateSection notificationTemplateSection) {
		this.notificationTemplateSection = notificationTemplateSection;
	}

	public List<NotificationTemplateParamter> getNotificationTemplateParamters() {
		return this.notificationTemplateParamters;
	}

	public void setNotificationTemplateParamters(List<NotificationTemplateParamter> notificationTemplateParamters) {
		this.notificationTemplateParamters = notificationTemplateParamters;
	}

	public NotificationTemplateParamter addNotificationTemplateParamter(NotificationTemplateParamter notificationTemplateParamter) {
		getNotificationTemplateParamters().add(notificationTemplateParamter);
		notificationTemplateParamter.setNotificationTemplate(this);

		return notificationTemplateParamter;
	}

	public NotificationTemplateParamter removeNotificationTemplateParamter(NotificationTemplateParamter notificationTemplateParamter) {
		getNotificationTemplateParamters().remove(notificationTemplateParamter);
		notificationTemplateParamter.setNotificationTemplate(null);

		return notificationTemplateParamter;
	}

}