package com.raksha.familyTree.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raksha.familyTree.model.FamilyMember;
import com.raksha.familyTree.model.FinalJSONResponseWrapper;
import com.raksha.familyTree.repo.FamilyMemberRepository;

@RestController
public class FamilyTreeController {
	
	@Autowired
	FamilyMemberRepository familyMemberRepository;
	
	@RequestMapping(value = "/save", method = RequestMethod.PUT)
	public @ResponseBody String save(@RequestBody String familyMemberJson) {
		FamilyMember familyMember = getObjectFromJson(familyMemberJson);
		System.out.println(familyMember);
		// update
		if(familyMember.getId() != 0) {
			familyMember = familyMemberRepository.save(familyMember);
		} else {
		// save
			familyMember = familyMemberRepository.save(familyMember);
			if(familyMember.getPids().size() > 0) {
				for(Long pid : familyMember.getPids()) {
					Optional<FamilyMember> opt =  familyMemberRepository.findById(pid);	
					FamilyMember member = opt.get();
					member.addPid(familyMember.getId());
					familyMemberRepository.save(member);
				}
			}
		}
		return fetchAllMembers();
	}
	
	@RequestMapping(value = "/fetchAll", method = RequestMethod.GET)
	public @ResponseBody String fetchAllMembers() {
		List<FamilyMember> familyMembers = familyMemberRepository.findAll();
		String jsonArray = convertFromObjectToJsonArray(new FinalJSONResponseWrapper(familyMembers));
	    // default member
	    String finalJson =  "{ \"members\": "
				    			+ "[{\r\n"
					    		+ "		\"id\": 1,\r\n"
					    		+ "		\"pids\": [],\r\n"
					    		+ "		\"name\": \"*Enter Your Name Here*\"\r\n"
					    		+ "}]" 
				    		+ "}";
	    if(familyMembers.size() != 0) {
	    	finalJson = jsonArray;
	    	finalJson = finalJson.replace("\"mid\":0", "\"mid\": \"\"");// mid=0 for UI errors so send "" same for fid
	    	finalJson = finalJson.replace("\"fid\":0", "\"fid\": \"\"");
	    }
		return finalJson;
	}
	
	private String convertFromObjectToJsonArray(FinalJSONResponseWrapper finalJSONResponse) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonArray = null;
	    try {
			jsonArray = objectMapper.writeValueAsString(finalJSONResponse);
		} catch (JsonProcessingException e) {
			System.err.println("JsonProcessingException - " + e);
		}
		return jsonArray;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody String deleteById(@RequestBody String familyMemberJson) {
		FamilyMember familyMember = getObjectFromJson(familyMemberJson);
		familyMemberRepository.deleteById(familyMember.getId());
		
		// check for children if any
		List<FamilyMember> fms = familyMemberRepository.findAllByFid(familyMember.getId());
		for(FamilyMember fm : fms) {
			familyMemberRepository.deleteById(fm.getId());
		}
		
		if("male".equalsIgnoreCase(familyMember.getGender())){
			// check for children of this father if any
			fms = familyMemberRepository.findAllByFid(familyMember.getId());
			for(FamilyMember fm : fms) {
				familyMemberRepository.deleteById(fm.getId());
			}
		} else {
			// check for children of this mother if any
			fms = familyMemberRepository.findAllByMid(familyMember.getId());
			for(FamilyMember fm : fms) {
				familyMemberRepository.deleteById(fm.getId());
			}
		}
		
		// check for partner - husband or wife if any
		List<Long> pids = new ArrayList<Long>();
		pids.add(familyMember.getId());
		fms = familyMemberRepository.findAllByPids(pids);
		for(FamilyMember fm : fms) {
			if(fm.getMid() == null && fm.getFid() == null) {
				familyMemberRepository.deleteById(fm.getId());	
			}
		}
		
		return fetchAllMembers();
	}
	
	private FamilyMember getObjectFromJson(String familyMemberJson) {
		ObjectMapper mapper = new ObjectMapper();
		FamilyMember familyMember = null;
		try {
			familyMember = mapper.readValue(familyMemberJson, FamilyMember.class);
		} catch (JsonMappingException e) {
			System.err.println("JsonMappingException - " + e);
		} catch (JsonProcessingException e) {
			System.err.println("JsonProcessingException - " + e);
		}
		return familyMember;
	}
	
	public String formatFinalJson(String jsonArray) {
		return "{ \"members\": "+ jsonArray + " }";
	}

}
