package com.kosta.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.Community;
import com.kosta.dto.CommunityFile;
import com.kosta.dto.User;
import com.kosta.mapper.CommunityMapper;
import com.kosta.mapper.UserMapper;

@Service
public class ICommunityService implements CommunityService {

	@Autowired
	private CommunityMapper cm;
	@Autowired
	private UserMapper um;

	@Override
	public List<Community> getAllCommunity() throws Exception {

		return cm.findAll();
	}

	@Override
	public void add(Community community, int id, List<MultipartFile> files) throws Exception {
		// 작성자 id 값을 통해, 커뮤니티 모델에 작성자 정보 세팅
		User user = um.findById(id);
		community.setCreator(user);

		// 게시글 저장
		cm.save(community);
		int communityId = community.getId();

		// 이미지 저장 (커뮤니티 아이디, 원본 파일명 , 새 파일명 , 파일크기)
		if (!files.isEmpty()) {
			List<CommunityFile> fileList = new ArrayList<>();
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					// 원본 파일명을 가져오장
					String originalFilename = file.getOriginalFilename();
					String newFileName = UUID.randomUUID().toString() + "_" + originalFilename;
					long fileSize = file.getSize();

					CommunityFile cf = new CommunityFile();
					cf.setCommunityId(communityId);
					cf.setOriginFileName(newFileName);
					cf.setStoredFilePath(newFileName);
					cf.setFileSize(fileSize);

					// fileList에 cf 추가
					fileList.add(cf);
					// 컴퓨터에 파일 저장
					File dest = new File("C:\\Users\\WD\\Desktop\\images\\" + newFileName);
					file.transferTo(dest);
				}
			}
			if (!fileList.isEmpty()) {
				cm.insertFiles(fileList);
			}
		}
	}

	@Override
	public Community getCommunityById(int id) throws Exception {
		Community community = cm.findById(id);
		List<CommunityFile> fileList = cm.findFilesByCommunityId(id);
		community.setFileList(fileList);

		return community;
	}

}
