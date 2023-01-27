package com.pgm.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pgm.board.model.Board;
import com.pgm.board.model.User;
import com.pgm.board.service.BoardService;
import com.pgm.board.service.UserService;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	@GetMapping("register")
	public void insert() {
		
	}
	
	@PostMapping("insert")
	public String insert(Board board) {
		log.info("board.............."+board.getWriter());
		
		User user=userService.findByUsername(board.getWriter());
		log.info("User.............."+user);
		
		boardService.insert(board,user);
		return "redirect:/board/list";
	}
	
//	@GetMapping("list2")
	public String list(Model model) {
		model.addAttribute("list", boardService.boardList());
		model.addAttribute("count", boardService.count());
		return "/board/list2";
	}
	
	//전체보기(페이징)
	@GetMapping("list")
	public String listPage(Model model, 
			@PageableDefault(size=5,sort="id", 
			direction=Sort.Direction.DESC) Pageable pageable) {
		
		Page<Board> lists=boardService.findAll(pageable);
		
		long pageSize=pageable.getPageSize(); //ex)5 -> 지정한 기본값: 5
		long rowNm=boardService.count(); // ex)102 -> 게시글 총수
		long totPage=(long)Math.ceil((double)rowNm/pageSize); //ex)21 -> 게시글 총수 / 페이지 기본값:5 : 올림
		long currPage=pageable.getPageNumber(); // 현재 페이지수 인덱스 형식으로 -> 1페이지 : 0, 21페이지 : 20
		System.out.println("CurrPag=============="+currPage);
		
		long startPage=((currPage)/pageSize)*pageSize; //ex)처음 0, 다음은 5 , 원래 페이지수 -1 , 순서:0,5,10,15...
		long endPage=startPage+pageSize;  //ex) 처음 5, 다음은 10, 인덱스 번호 형식, 순서: 5,10,15...
		if(endPage>totPage) 
			endPage=totPage;
			
		boolean prev=startPage>0?true:false;  
		boolean next=endPage<totPage?true:false;
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage-1);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("count", rowNm);
		model.addAttribute("lists", lists);
		model.addAttribute("totPage", totPage);
		model.addAttribute("cp", currPage);
		
		return "board/list";
	}
	
	//검색 페이지 보기 
	   @GetMapping("search")
	   public String search(@RequestParam("word")String word, @RequestParam("field")String field,
			   Model model, @PageableDefault(size=5,sort="id", 
				direction=Sort.Direction.DESC) Pageable pageable) {       
		   Page<Board> searchList = boardService.search(word, pageable);       
			
			long pageSize=pageable.getPageSize(); //ex)5 -> 지정한 기본값: 5
			
			//먼저, 제목만 검색.
//			long rowNm=boardService.count(); // ex)102 -> 게시글 총수
			
			long rowNm=searchList.getTotalElements(); // ex)102 -> 게시글 총수
			
			long totPage=(long)Math.ceil((double)rowNm/pageSize); //ex)21 -> 게시글 총수 / 페이지 기본값:5 : 올림
			long currPage=pageable.getPageNumber(); // 현재 페이지수 인덱스 형식으로 -> 1페이지 : 0, 21페이지 : 20
			System.out.println("CurrPag=============="+currPage);
			
			long startPage=((currPage)/pageSize)*pageSize; //ex)처음 0, 다음은 5 , 원래 페이지수 -1 , 순서:0,5,10,15...
			long endPage=startPage+pageSize;  //ex) 처음 5, 다음은 10, 인덱스 번호 형식, 순서: 5,10,15...
			if(endPage>totPage) 
				endPage=totPage;
				
			boolean prev=startPage>0?true:false;  
			boolean next=endPage<totPage?true:false;
			
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage-1);
			model.addAttribute("prev", prev);
			model.addAttribute("next", next);
			model.addAttribute("count", rowNm);
			model.addAttribute("lists", searchList);
			model.addAttribute("totPage", totPage);
			model.addAttribute("cp", currPage);
			model.addAttribute("word", word);
			model.addAttribute("field", field);
			return "board/list_search";   //검색된 리스트 뿌리는 페이지 따로 만들까?
		   }
	   
	
//	@GetMapping("detail/{id}")
//	public String detail(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("board", boardService.findById(id));
//		return "/board/detail";
//	}
	
	@GetMapping("detail")
	public String detail(@RequestParam("bno") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
		return "/board/detail";
	}
	
	/*
	@GetMapping({"detail/{id}","update/{id}"})
	public void view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
	}*/
	
	@GetMapping("update/{id}")
	public String update(@PathVariable("id") Long id, Model model) {
		model.addAttribute("board", boardService.findById(id));
		return "/board/update";
	}
	
	@PostMapping("update")
	public String update(Board board) {
		boardService.update(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
	}

}
