package com.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;

public interface BoardService {
	List<BoardDTO> selectBoardList() throws Exception;

	void insertBoard(BoardDTO boardDTO, List<MultipartFile> files) throws Exception;

	BoardDTO selectBoardById(int id) throws Exception;

	void updateBoard(BoardDTO boardDTO) throws Exception;

	void deleteBoard(int id) throws Exception;

	FileDTO selectFileByIds(int id, int boardId) throws Exception;

}
