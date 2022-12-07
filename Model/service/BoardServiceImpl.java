package com.kh.spring.board.Model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.Model.dao.BoardDao;
import com.kh.spring.board.Model.vo.Board;
import com.kh.spring.board.Model.vo.Reply;
import com.kh.spring.common.Model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int selectListCount() {
		return boardDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return boardDao.selectList(sqlSession, pi);
	}

	@Override
	public int increaseCount(int boardNo) {
		return boardDao.increaseCount(sqlSession,boardNo);
	}

	@Override
	public Board selectBoard(int boardNo) {
		return boardDao.selectBoard(sqlSession, boardNo);
	}

	@Override
	public int insertBoard(Board board) {
		return boardDao.insertBoard(sqlSession,board);
	}
	
	@Override
	public int updateBoard(Board board) {
		return boardDao.updateBoard(sqlSession,board);
	}
	
	@Override
	public int deleteBoard(int boardNo) {
		return boardDao.deleteBoard(sqlSession,boardNo);
	}

	@Override
	public ArrayList<Reply> viewReplyList(int boardNo) {
		return boardDao.viewReplyList(sqlSession, boardNo);
	}

	@Override
	public int insertReply(Reply reply) {
		return boardDao.insertReply(sqlSession, reply);
	}

	@Override
	public ArrayList<Board> viewTopBoard() {
		return boardDao.viewTopBoard(sqlSession);
	}

	
}
