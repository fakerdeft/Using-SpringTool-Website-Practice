package com.kh.spring.common.Template;

import com.kh.spring.common.Model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getPageinfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		int maxPage=(int)(Math.ceil(((double)listCount/boardLimit)));
		int startPage = (currentPage -1)/pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		return new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
	}
}


















