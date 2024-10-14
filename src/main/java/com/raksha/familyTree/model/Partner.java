package com.raksha.familyTree.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Partner")
public class Partner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PARTNER_ID")
	private Long partner_id;
	
	@ManyToOne
	@JoinColumn(name="family_member_id", nullable=false)
	private FamilyMember familyMember;
	
	public Long getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Long partner_id) {
		this.partner_id = partner_id;
	}
	
}
