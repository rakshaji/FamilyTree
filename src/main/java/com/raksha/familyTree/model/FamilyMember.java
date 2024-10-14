package com.raksha.familyTree.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "family_member")
public class FamilyMember {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "family_member_id")
	private long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "OCCUPATION")
	private String occupation;

	// @OneToMany(mappedBy="familyMember")
	@Column(name = "PIDS")
	private List<Long> pids = new ArrayList<>();

	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "family_member_id")
	@Column(name = "MID")
	private Long mid;

	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "family_member_id")
	@Column(name = "FID")
	private Long fid;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DEATH_DATE")
	private String deathDate;

	@Column(name = "BIRTH_DATE")
	private String birthDate;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "IMG_PATH")
	private String imgPath;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "ID - " + getId() + "\n" + "NAME - " + getName() + "\n" + "GENDER - " + getGender() + "\n" + "COUNTRY - "
				+ getCountry() + "\n" + "CITY - " + getCity() + "\n" + "PHONE - " + getCity() + "\n" + "OCCUPATION - "
				+ getOccupation() + "\n"
				+ "ADDRESS - " + getAddress() + "\n" + "DEATHDATE - " + getDeathDate() + "\n" + "BIRTHDATE - "
				+ getBirthDate() + "\n" + "EMAIL - " + getEmail() + "\n" + "IMG_PATH - " + getImgPath() + "\n"
				+ "NOTES - " + getNotes();
	}

	public List<Long> getPids() {
		return pids;
	}

	public void setPids(List<Long> pids) {
		this.pids = pids;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}
	
	public void addPid(Long pid) {
		getPids().add(pid);
	}

}
