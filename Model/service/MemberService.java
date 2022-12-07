package com.kh.spring.member.Model.service;

import java.util.List;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	//로그인 서비스 select
	Member loginMember(Member m);
	
	//회원 가입 서비스 insert
	int insertMember(Member m);
	
	//회원 정보 수정 서비스 update
	int updateMember(Member m);
	
	//회원 탈퇴 서비스 delete
	int deleteMember(String pwd);
	
	//아이디 중복체크 서비스
	Member validateDuplicationId(String userId);
	
	//전체 회원조회
	List<Member> selectAllMember();
	
}
