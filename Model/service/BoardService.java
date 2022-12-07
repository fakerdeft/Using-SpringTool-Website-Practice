package com.kh.spring.board.Model.service;

import java.util.ArrayList;

import com.kh.spring.board.Model.vo.Board;
import com.kh.spring.board.Model.vo.Reply;
import com.kh.spring.common.Model.vo.PageInfo;



public interface BoardService {
	//게시글 총 개수
	int selectListCount();
	
	//게시글 리스트 조회
	ArrayList<Board> selectList(PageInfo pi);
	
	//게시글 작성
	int insertBoard(Board board);
	
	//게시글 조회수 증가
	int increaseCount(int boardNo);
	
	//게시글 상세 조회
	Board selectBoard(int boardNo);	
	
	//게시글 수정
	int updateBoard(Board board);
	
	//게시글 삭제
	int deleteBoard(int boardNo);
	
	//댓글 리스트 조회
	ArrayList<Reply> viewReplyList(int boardNo);
 	
	//댓글 작성
	int insertReply(Reply reply);
	
	//게시글 조회수top5 조회
	ArrayList<Board> viewTopBoard();

}	















