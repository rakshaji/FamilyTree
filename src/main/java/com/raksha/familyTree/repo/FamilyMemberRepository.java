package com.raksha.familyTree.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raksha.familyTree.model.FamilyMember;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long>{

	List<FamilyMember> findAllByFid(long id);

	List<FamilyMember> findAllByPids(List<Long> pids);

	List<FamilyMember> findAllByMid(Long id);

}
