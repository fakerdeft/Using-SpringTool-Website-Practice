package com.kh.spring.member.Model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//@Component //bean으로 등록시키겠다.
@Service //Component보다 더 구체화 시켜서 Service로 사용할 것을 명시
public class MemberServiceImpl implements MemberService{

	//private MemberDao memberDao = new MemberDao(); 기존방식
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Member loginMember(Member m) {
		//SqlSessionTemplate 객체를 bean등록 후 @Autowired 해줌으로써
		//스프링 컨테이너가 직접 생명주기를 관리하기 때문에 따로 close필요 없다.
		return memberDao.loginMember(sqlSession,m);
	}

	@Override
	public int insertMember(Member m) {
		return memberDao.insertMember(sqlSession,m);
	}

	@Override
	public int updateMember(Member m) {
		return memberDao.updateMember(sqlSession,m);
	}

	@Override
	public int deleteMember(String userId) {
		return memberDao.deleteMember(sqlSession,userId);
	}

	@Override
	public List<Member> selectAllMember() {
		return memberDao.selectAllMember(sqlSession);
	}

	@Override
	public Member validateDuplicationId(String userId) {
		return memberDao.validateDuplicationId(sqlSession,userId);
	}

}










