package com.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.board.dto.BoardDTO;
import com.board.mapper.BoardMapper;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class BoardServiceTest {
	@Autowired
	private BoardMapper boardMapper;

	@Test
	@DisplayName("SELECT ALL BOARD TEST")
	public void selectAllBoard() throws Exception {
		List<BoardDTO> boardList = boardMapper.selectBoardList();
		assertThat(boardList.size()).isEqualTo(3);
	}
}
