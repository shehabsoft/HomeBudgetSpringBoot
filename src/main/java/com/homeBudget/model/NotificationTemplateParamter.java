package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the notification_template_paramters database table.
 * 
 */
@Entity
@Table(name="notification_template_paramters")
@NamedQuery(name="NotificationTemplateParamter.findAll", query="SELECT n FROM NotificationTemplateParamter n")
public class NotificationTemplateParamter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="paramter_name")
	private String paramterName;

	//bi-directional many-to-one association to NotificationTemplate
	@ManyToOne
	@JoinColumn(name="notification_template_id")
	private NotificationTemplate notificationTemplate;

	public NotificationTemplateParamter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParamterName() {
		return this.paramterName;
	}

	public void setParamterName(String paramterName) {
		this.paramterName = paramterName;
	}

	public NotificationTemplate getNotificationTemplate() {
		return this.notificationTemplate;
	}

	public void setNotificationTemplate(NotificationTemplate notificationTemplate) {
		this.notificationTemplate = notificationTemplate;
	}

}