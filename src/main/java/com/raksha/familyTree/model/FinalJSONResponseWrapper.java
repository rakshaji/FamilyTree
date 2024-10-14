package com.raksha.familyTree.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FinalJSONResponseWrapper {
	
	private List<FamilyMember> members = new ArrayList<>();
	
	public FinalJSONResponseWrapper() {}
	
	public FinalJSONResponseWrapper(FamilyMember familyMember) {
		addMember(familyMember);
	}
	
	public FinalJSONResponseWrapper(List<FamilyMember> members) {
		this.members = members;
	}

	public List<FamilyMember> getMembers() {
		return members;
	}

	public void setMembers(List<FamilyMember> members) {
		this.members = members;
	}
	
	public void addMember(FamilyMember fm) {
		this.members.add(fm);
	}
	
}
