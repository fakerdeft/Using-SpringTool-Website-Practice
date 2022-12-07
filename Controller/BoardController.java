package com.kh.spring.board.ontroller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;
import com.kh.spring.member.model.vo.Member;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="currentPage",defaultValue = "1")int currentPage,
							Model model) {
		//페이징 처리를 위해 전체 게시글 개수 조회해오기
		int listCount = boardService.selectListCount();
		//페이징 처리 pageLimit 10
		//boardLimit 5
		int pageLimit = 10;
		int boardLimit = 5;
		PageInfo pi = Pagination.getPageinfo(listCount, currentPage, pageLimit, boardLimit);
		ArrayList<Board> list = boardService.selectList(pi);
		model.addAttribute("list",list);
		model.addAttribute("pi", pi);
		return "board/boardListView";
	}
	
	@RequestMapping("detail.bo")
	public String selectBoard(@RequestParam(value="bno")int boardNo,
								HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(boardService.increaseCount(boardNo) > 0) {
			Board board = boardService.selectBoard(boardNo);
			model.addAttribute("Board",board);
			model.addAttribute("loginUser",loginUser);
			return "board/boardDetailView";
		}
		//실패시 에러페이지
		model.addAttribute("errorMsg","게시글 조회 실패!");
		return "common/errorPage";
	}
	
	@GetMapping("insert.bo")
	public String insertBoard(HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		model.addAttribute("loginUser",loginUser);
		return "board/boardEnrollView";
	}
	
	@PostMapping("insert.bo")
	public ModelAndView insertBoard(Board board, MultipartFile upfile ,
								ModelAndView mv, HttpSession session) {
		//전달된 파일이 있을 경우 - 파일명 수정 후에 서버로 업로드 - 원본명, 서버에 업로드된 경로를 이어서 다운로드 처리
		if(!upfile.getOriginalFilename().equals("")) {//파일 업로드가 되었다면
			//파일명 수정 후 서버로 업로드
			//짱구.jsp->20221202113413559593.jsp
			
			String changeName = saveFile(upfile,session);
			
			//원본명, 서버에 업로드한 경로를 Board객체에 담아주기
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		//만약 첨부파일이 없다면 - 작성자, 내용, 제목
		//첨부파일이 있다면 - 작성자, 제목, 내용, 원본이름, 저장경로
		if(boardService.insertBoard(board) > 0) {//성공 시 - 게시판 리스트 띄워주기 (list.bo 재요청)
			session.setAttribute("alertMsg", "게시글 등록 성공!");
			mv.setViewName("redirect:/list.bo");
			return mv;
		}
		//실패시 에러페이지
		mv.addObject("errorMsg", "게시글 등록 실패!").setViewName("common/errorPage");
		return mv;
	}
	
	@GetMapping("update.bo")
	public String updateBoard(int boardNo, Model model) {
		//수정하기 페이지에서 필요한 기존 게시글 정보 조회하여 보내주기
		Board board = boardService.selectBoard(boardNo);
		model.addAttribute("board",board);
		return "board/boardUpdateView";
	}
	
	@PostMapping("update.bo")
	public String updateBoard(Board board,MultipartFile upfile
							,HttpSession session,Model model) {
		if(!upfile.getOriginalFilename().equals("")) {
			if(!board.getOriginName().equals("")) {
				new File(session.getServletContext().getRealPath(board.getChangeName()));
			}
			String changeName = saveFile(upfile,session);
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName("resources/uploadFiles/" + changeName);
		}
		if(boardService.updateBoard(board) > 0) {
			session.setAttribute("alertMsg","게시판 수정 성공!");
			return "redirect:/list.bo";
		}
		model.addAttribute("errorMsg","게시판 수정 실패!");
		return "common/errorPage";
	}
	
	@RequestMapping("delete.bo")
	public String boardDelete(int boardNo,String filePath
							,HttpSession session) {
		if(boardService.deleteBoard(boardNo) > 0) {
			if(!filePath.equals("")) {//파일이 있는 경우				
				//물리적인 경로 찾기 				
				//해당 경로와 연결시켜 파일객체 생성후 삭제 메소드(해당 파일 삭제)
				new File(session.getServletContext().getRealPath(filePath)).delete();				
			}
			session.setAttribute("alertMsg","게시판 삭제 성공!");
			return "redirect:/list.bo";
		}
		session.setAttribute("errorMsg","게시판 삭제 실패!");
		return "redirect:/";
	}
	
	//현재 넘어온 첨부파일을 서버 폴더에 저장시키는 메서드(모듈)
	public String saveFile(MultipartFile upfile, HttpSession session) {
		//1.원본 파일명 뽑기
		String originName = upfile.getOriginalFilename();
		
		//2.시간형식을 문자열로 뽑기
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//3.뒤에 붙일 랜덤값 뽑기
		int ranNum = (int)(Math.random() * 90000 + 10000); //5자리 랜덤값
		
		//4.원본 파일명으로부터 확장자명 뽑기
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//5.뽑은 값들 전부 붙여서 파일명 만들기
		String changeName = currentTime + ranNum + ext;
		
		//6.업로드 하고자 하는 실제 위치경로 지정해주기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		//7.경로와 수정파일명 합쳐서 파일을 업로드해주기
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return changeName;
	}
	
	@ResponseBody
	@RequestMapping(value="viewReplyList.bo", produces="application/json; charset=UTF-8")
	public String viewReplyList(int boardNo) {
		ArrayList<Reply> replyList = boardService.viewReplyList(boardNo);
		if(replyList != null) {
			return new Gson().toJson(replyList);
		}
		return new Gson().toJson(replyList);
	}
	
	@ResponseBody
	@RequestMapping(value="insertReply.bo", produces="application/json; charset=UTF-8")
	public String insertReply(HttpSession session,int boardNo,String replyContent) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		Reply reply = new Reply();
		reply.setReplyWriter(loginUser.getUserId());
		reply.setRefBno(boardNo);
		reply.setReplyContent(replyContent);
		if(boardService.insertReply(reply) > 0) {
			return new Gson().toJson("TT");
		}
		return new Gson().toJson("FF");
	}
	
}
 
















