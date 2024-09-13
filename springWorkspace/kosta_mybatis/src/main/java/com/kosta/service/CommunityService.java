package com.kosta.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.Community;

@Service
public interface CommunityService {

	void add(Community community, int id, List<MultipartFile> files) throws Exception;

	List<Community> getAllCommunity() throws Exception;

	Community getCommunityById(int id) throws Exception;
}
