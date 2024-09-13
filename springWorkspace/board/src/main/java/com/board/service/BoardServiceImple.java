package com.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.mapper.BoardMapper;
import com.board.mapper.FileMapper;

@Service // 비즈니스 로직을 처리하는 서비스
@Transactional
public class BoardServiceImple implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private FileMapper fileMapper;

	@Override
	public List<BoardDTO> selectBoardList() throws Exception {
		// 사용자 요청을 처리하기 위한 비즈니스 로직(리스트 가져오기) 구현
		return boardMapper.selectBoardList();

	}

	@Override
	public void insertBoard(BoardDTO boardDTO, List<MultipartFile> files) throws Exception {
		boardMapper.insertBoard(boardDTO);
		int board_id = boardDTO.getId();

		// 첨부 파일이 있으면
		if (files != null && !files.isEmpty()) {
			// fileDTO 리스트 생성
			List<FileDTO> fileList = new ArrayList<>();

			// 파일 가져옴
			for (MultipartFile file : files) {
				// 파일이 존재하면?
				if (!file.isEmpty()) {
					// 원본 파일명
					String orginFileName = file.getOriginalFilename();
					// 새로운 파일명
					String storedFileName = UUID.randomUUID().toString() + "_" + orginFileName;
					// 파일저장 경로 + 새로운 파일명
					String storedFilePath = "C:\\Users\\WD\\board_file\\" + storedFileName;
					// 파일 크기
					long fileSize = file.getSize();

					FileDTO fileDTO = new FileDTO();
					fileDTO.setBoardId(board_id);
					fileDTO.setFileSize(fileSize);
					fileDTO.setOriginFileName(orginFileName);
					fileDTO.setStoredFilePath(storedFilePath);

					// fileDTO 리스트에 추가
					fileList.add(fileDTO);

					// 파일저장
					try {
						File dest = new File(storedFilePath);
						file.transferTo(dest);
					} catch (Exception e) {
						throw new Exception("파일 업로드중 오류가 발생");
					}
				}
			}
			if (!fileList.isEmpty()) {
				fileMapper.insertFile(fileList);
			}
		}
	}

	@Override
	public BoardDTO selectBoardById(int id) throws Exception {
		BoardDTO boardDTO = boardMapper.selectBoardById(id);
		List<FileDTO> fileList = fileMapper.selectFileListByBoardId(id);
		boardDTO.setFileList(fileList);
		boardMapper.updateHit(id);
		return boardDTO;
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) throws Exception {
		boardMapper.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(int id) throws Exception {
		boardMapper.deleteBoard(id);
	}

	@Override
	public FileDTO selectFileByIds(int id, int boardId) throws Exception {
		return fileMapper.selectFileByIds(id, boardId);
	}

}
