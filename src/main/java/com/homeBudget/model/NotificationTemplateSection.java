package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the notification_template_sections database table.
 * 
 */
@Entity
@Table(name="notification_template_sections")
@NamedQuery(name="NotificationTemplateSection.findAll", query="SELECT n FROM NotificationTemplateSection n")
public class NotificationTemplateSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	@Column(name="body_section")
	private String bodySection;

	@Lob
	@Column(name="footer_section")
	private String footerSection;

	@Lob
	@Column(name="header_section")
	private String headerSection;

	@Lob
	@Column(name="table_section")
	private String tableSection;

	//bi-directional one-to-one association to NotificationTemplate
	@OneToOne(mappedBy="notificationTemplateSection")
	private NotificationTemplate notificationTemplate;

	public NotificationTemplateSection() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBodySection() {
		return this.bodySection;
	}

	public void setBodySection(String bodySection) {
		this.bodySection = bodySection;
	}

	public String getFooterSection() {
		return this.footerSection;
	}

	public void setFooterSection(String footerSection) {
		this.footerSection = footerSection;
	}

	public String getHeaderSection() {
		return this.headerSection;
	}

	public void setHeaderSection(String headerSection) {
		this.headerSection = headerSection;
	}

	public String getTableSection() {
		return this.tableSection;
	}

	public void setTableSection(String tableSection) {
		this.tableSection = tableSection;
	}

	public NotificationTemplate getNotificationTemplate() {
		return this.notificationTemplate;
	}

	public void setNotificationTemplate(NotificationTemplate notificationTemplate) {
		this.notificationTemplate = notificationTemplate;
	}

}