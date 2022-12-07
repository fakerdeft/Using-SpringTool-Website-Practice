package com.kh.spring.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller //Controller타입의 어노테이션을 붙여주면 빈 스캐닝을 통해 자동으로 bean을 등록한다.
public class MemberController {
	 
	/*
	 * 기존 방법
	 * private MemberServiceImpl memberServiceImpl = MemberConstant.IMPL;
	 * 
	 * 기존 객체 생성 방식은 객체 간의 결합도가 높아진다(소스코드의 수정이 일어날 경우 하나하나 다 바꿔야하는 단점이 있음)
	 * 서비스가 동시에 많은 횟수가 요청될 경우 그만큼 많은 객체가 생성된다.
	 * 
	 * Spring의 DI (Dependency Injection)을 이용한 방식으로 객체를 생성하여 주입한다.(객체간의 결합도를 낮춤)
	 * new라는 키워드 없이 선언문만 써줘도 되지만 @Autowired라는 어노테이션을 필수로 작성.
	 * 
	 */
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptpasswordEncoder;
	
//	@RequestMapping(value="login.me")
//	public void loginMember() {
//		loginMember();
//	}
	
	/*
	 * @RequestMapping(value="매핑값") - Request타입의 어노테이션을 붙여줌으로서 HandlerMapping을 등록한다.
	 * 
	 * Spring에서 파라미터를 받는 방법
	 * 1. HttpServletRequset를 이용하여 전달받기 (기존의 jsp/servlet때의 방식)
	 * 	해당 메서드의 매개변수로 HttpServletRequest를 작성해두면
	 * 	스프링 컨테이너가 해당 매서드를 호출 시 자동으로 해당 객체 생성해서 매개변수로 주입
	 * 
	 */
	
	//value= 생략가능
	/*
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
//		return "main"; forward 방식
		return "redirect:/"; //redirect 방식
	}*/
	
	/*
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 * 	- request.getParameter("키")로 밸류를 뽑아오는 역할을 대신해주는 어노테이션
	 * 	- value 속성의 밸류로 jsp에서 작성했던 name 속성값을 담으면 알아서 해당 매개변수로 담아올 수 있다.
	 *  - 만약, 넘어온 값이 비어있는 형태라면 defaultValue 속성으로 기본값을 설정해 줄 수도 있다.
	 *  
	 * 
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="userId",defaultValue="admin")String userId,
							  @RequestParam(value="userPwd")String userPwd,
							  @RequestParam(value="email",defaultValue="ss@gmail.com")String email) {
		return "main";
	}
	 */
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 	- 단 매개변수명을 jsp의 name 속성값(요청시 전달한 키값)과 동일하게 작성하여야 자동 주입이 된다.
	 *  - 또한 위에서 사용했던 defaultValue같은 추가 속성은 작성할 수 없다.
	@RequestMapping("login.me")
	public String loginMember(String userId,String userPwd,String email,
								@RequestParam(value="userId")String userId2) {
		System.out.println("RequestParam을 생략한 방법: " + userId);
		System.out.println("RequestParam을 생략한 방법: " + userPwd);
		System.out.println("RequestParam을 생략한 방법: " + email); //없는 키값과 같은 변수를 작성하면 null값 반환
		System.out.println("RequestParam을 같이 써보기: " + userId2); //@RequestParam 어노테이션과 같이 사용할 수 있음
		return "redirect:/";
	}
	 */
	
	/*
	 * 4. 커맨드 객체 방식
	 *  - 해당 메서드의 매개변수로 요청시 전달값을 담고자 하는 VO클래스 타입을 세팅하여
	 *  - 요청시 전달값의 키값(jsp의 name)을 VO 클래스에 담고자 하는 필드명으로 작성한다.
	 *  
	 *  스프링 컨테이너가 해당 객체를 기본생성자로 생성하여 내부적으로 
	 *  setter메서드를 찾아 요청시 전달값을 해당 필드에 담아주는 원리.
	 *  
	 *  이 또한 마찬가지로 반드시 name명과 필드명이 일치하여야 한다.
	 *  
	 *  
	 *  --요청처리 후 응답 페이지로 응답 데이터를 가지고 포워딩 또는 url 재요청 하는 방법
	 *  
	 *  1. 스프링에서 제공하는 Model 객체를 이용하는 방법
	 *  - 포워딩할 응답뷰로 전달하고자 하는 데이터를 맵 형식(key-value)로 담을 수 있는 영역
	 *  - Model 객체는 requestScope이다. 단, setAttribute가 아닌 addAttribute 메서드를 이용해야 한다.
	 *  
	
	//로그인
	@RequestMapping("login.me")
	public String loginMember(Member m
							 ,HttpSession session
							 ,Model model) {
		
		Member loginUser = memberService.loginMember(m);
		if(loginUser == null) { //로그인 실패 - requestScope에 에러메세지 담고 에러페이지로
			model.addAttribute("errorMsg","로그인 실패!");
			return "common/errorPage"; //중간 경로만 작성하면 된다.
		}
		session.setAttribute("loginUser", loginUser);
		//url 재요청 방식
		//redirect:요청 url
		return "redirect:/"; //로그인 성공 - loginUser를 SessionScope에 담아서 메인페이지로
	}
	 */
	
	//비번 암호화 후 로그인 처리
	/*
	 * 2. 스프링에서 제공하는 ModelAndView 클래스
	 * model은 데이터를 key-value 세트로 담을 수 있는 공간
	 * view는 응답뷰에 대한 정보를 담을 수 있는 공간
	 * 이 경우에는 반환타입이 String이 아니라 ModelAndView 형태로 반환해야 한다.
	 * Model과 View가 결합된 형태 단, Model은 위에서 사용했듯이 단독사용이 가능하지만.
	 * View는 단독 사용을 할 수 없다.
	 * 
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member member
							 ,HttpSession session
							 ,ModelAndView mv) {
		Member loginUser = memberService.loginMember(member);
		//loginUser : 아이디만으로 조회해온 회원정보
		//loginUser의 userPwd 필드에는 암호화되어서 DB에 저장된 암호비밀번호가 들어있다.
		//그 암호화된 비밀번호와 사용자가 입력한 비밀번호가 암호화되었을 시에 일치하게 되는지 
		//확인해주는 메서드를 사용하여 해당 정보가 일치하는지 구분한다.
		//이때 사용하는 메서드는 BCryptPasswordEncoder 객체의 matches 메서드이다.
		//matches(평문,암호문)을 작성하면 내부적으로 복호화 작업이 이루어져
		//두 데이터가 일치하는지 확인하여 true/false로 반환한다.
		//사용자가 입력한 로그인폼에서 넘어온 비밀번호: m.getUserPwd();
		//데이터베이스에서 조회해온 암호화된 비밀번호: loginUser.getUserPwd();
		
		if(loginUser != null && bcryptpasswordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/"); //메인화면 재요청
			return mv;
		}		
		mv.addObject("errorMsg", "로그인 실패!");
		mv.setViewName("common/errorPage");
		return mv;
	}
	
	//로그아웃
	@RequestMapping("logOut.me")
	public String logOutMember(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	//회원가입 폼으로 이동
	@GetMapping("insert.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	//회원가입 등록
	@PostMapping("insert.me")
	public String insertMember(Member member,HttpSession session,Model model) {
		
		// 1. 한글 깨짐 - web.xml에서 encodingFilter를 통해 해결
		// 2. 나이에 빈값이 들어오면 typeMismatchException 발생
		//	  -Member VO에 age필드를 String자료형으로 변경하여 해결 (lombok활용)
		// 3. 비밀번호가 사용자가 입력한 그대로 저장되는 문제
		//	  -Bcypt 방식의 암호화를 통해 암호문으로 변경
		//	  -spring security module에서 제공하는 라이브러리 (pom.xml에 추가)
		//	  -BcryptPasswordEncoding 클래스를 사용하기 위해 xml파일에 등록
		//	  -web.xml에 spring-security.xml 파일을 로딩할 수 있게 작업
		String encPwd = bcryptpasswordEncoder.encode(member.getUserPwd());
		member.setUserPwd(encPwd);
		int result = memberService.insertMember(member);
		if(result > 0) {
			session.setAttribute("alertMsg","회원 가입 성공!");
			return "redirect:/";
		}
		model.addAttribute("errorMsg","회원 가입 실패!");
		return "common/errorPage";
	}
	
	//마이 페이지로 이동
	@GetMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
	
	//회원 정보 수정
	@RequestMapping("update.me")
	public String updateMember(Member member,HttpSession session,Model model) {
		int updateResult = memberService.updateMember(member);
		if(updateResult > 0) { //성공시 session에 있던 기존 loginUser	지우고 새 loginUser
			Member updateUser = memberService.loginMember(member);
			session.setAttribute("loginUser", updateUser);
			
			//마이페이지 재요청(alertMsg)
			session.setAttribute("alertMsg","수정 성공!");
			return "redirect:/myPage.me";
		}
		//실패시 에러페이지
		model.addAttribute("errorMsg","회원 정보 수정 실패!");
		return "common/errorPage";
	}
	
	//회원 정보 탈퇴
	@RequestMapping("delete.me")
	public String deleteMember(String userPwd,HttpSession session,Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		if(bcryptpasswordEncoder.matches(userPwd, loginUser.getUserPwd())) {
			int result = memberService.deleteMember(loginUser.getUserId());
			if(result > 0) { //성공
				session.removeAttribute("loginUser");
				session.setAttribute("alertMsg","탈퇴 성공!");
				return "redirect:/";
			}
		}
		//실패시 에러페이지
		model.addAttribute("errorMsg","회원 정보 탈퇴 실패!");
		return "common/errorPage";
	}
	
	@RequestMapping("selectAllMember.do")
	public String selectAllMember(Model model) {
		List<Member> memberList = new ArrayList<>();
		memberList = memberService.selectAllMember();
		model.addAttribute("memberList",memberList);
		return "member/selectAllMember";
	}
	
	@ResponseBody
	@RequestMapping(value="validate.me", produces="application/json; charset=UTF-8")
	public String validateDuplicationId(String userId) {
		if(memberService.validateDuplicationId(userId) != null) {
			return new Gson().toJson("FF");
		}
		return new Gson().toJson("TT");
	}
	
}
















