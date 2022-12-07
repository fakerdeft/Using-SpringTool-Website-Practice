package com.kh.spring.member.Model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

//@Component
//DAO: DB(저장소)와 관련된 작업(영속성 작업)
@Repository //Dao bean으로 등록하겠다
public class MemberDao {

	public Member loginMember(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.selectOne("memberMapper.loginMember",member);
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.insert("memberMapper.insertMember",member);
	}

	public int updateMember(SqlSessionTemplate sqlSession, Member member) {
		return sqlSession.update("memberMapper.updateMember",member);
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.update("memberMapper.deleteMember",userId);
	}

	public List<Member> selectAllMember(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("memberMapper.selectAllMember");
	}

	public Member validateDuplicationId(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.selectOne("memberMapper.validateDuplicationId",userId);
	}
	
}















