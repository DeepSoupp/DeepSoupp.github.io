package com.kosta.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kosta.entity.Article;

@DataJpaTest // JPA 관련 테스트
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB 사용
public class BlogRepositoryTest {
	@Autowired
	private BlogRepository blogRepository;

	// 테스트 코드 패턴 : Given(데이터 주고) -> When(테스트) -> Then(검증)

	@Test
	@DisplayName("게시글 추가 테스트")
	public void savaArticle() {
		// G
		Article article = Article.builder().title("TEST title").content("TEST content").build();
		// W
		Article savedArticle = blogRepository.save(article); // // Article 객체를 저장하고, 저장된 결과를 savedArticle에 저장
		// T
		assertThat(savedArticle).isNotNull(); // 저장된 객체가 null 아님을 검증
		assertThat(savedArticle.getId()).isNotNull(); // 저장된 객체의 ID가 null 아님을 검증
		assertThat(savedArticle.getTitle()).isEqualTo("TEST title"); // 장된 객체의 제목이 예상한 값과 일치하는지 검증
		assertThat(savedArticle.getContent()).isEqualTo("TEST content"); // 저장된 객체의 내용이 예상한 값과 일치하는지 검증
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트!")
	public void findAllTest() {
		// G
		// Article 객체를 생성하고 초기화
		Article article1 = Article.builder().title("제목1").content("내용1").build();
		// 게시글을 데이터베이스에 저장
		blogRepository.save(article1);

		Article article2 = Article.builder().title("제목2").content("내용2").build();
		blogRepository.save(article2);

		// W
		// 데이터베이스에서 모든 게시글을 조회
		List<Article> articleList = blogRepository.findAll();

		// T
		assertThat(articleList).isNotNull();
		// 게시글 목록의 크기가 2 이상임
		assertThat(articleList.size()).isGreaterThanOrEqualTo(2);
		// 게시글 목록에 제목이 "제목1"인 게시글이 존재하는지
		assertThat(articleList.stream().anyMatch(article -> article.getTitle().equals("제목1"))).isTrue();
		assertThat(articleList.stream().anyMatch(article -> article.getContent().equals("내용2"))).isTrue();
	}

	@Test
	@DisplayName("특정 게시물 조회(ID)")
	public void findByIdTest() {
		// G
		Article article = Article.builder().title("새로운 글 제목").content("새로운 글 내용").build();
		Article savedArticle = blogRepository.save(article);

		// W
//		Optional<Article> optArticle = blogRepository.findById(savedArticle.getId());
//		Article foundArticle = optArticle.get();
		Article foundArticle = blogRepository.findById(savedArticle.getId()).get();
		// T
		assertThat(foundArticle).isNotNull();
		assertThat(foundArticle.getId()).isEqualTo(savedArticle.getId());
		assertThat(foundArticle.getTitle()).isEqualTo(savedArticle.getTitle());
		assertThat(foundArticle.getContent()).isEqualTo(savedArticle.getContent());
	}

	@Test
	@DisplayName("특정 게시물 삭제")
	public void deleteArticleById() {
		// G
		// 현재 데이터베이스의 게시물 수를 저장
		int originSize = blogRepository.findAll().size();
		Article article = Article.builder().title("새로운 글 제목").content("새로운 글 내용").build();
		Article savedArticle = blogRepository.save(article);

		// W
		// 저장된 게시물의 ID를 이용하여 게시물 삭제
		blogRepository.deleteById(savedArticle.getId());
		// 데이터베이스에서 모든 게시물의 수를 다시 확인
		int newSize = blogRepository.findAll().size();

		// T
		// 삭제 전과 삭제 후의 게시물 수가 같아야 함
		assertThat(originSize).isEqualTo(newSize);
	}

	@Test
	@DisplayName("특정 게시물 수정")
	public void updateArticle() {
		// G
		Article article = Article.builder().title("새로운 글 제목").content("새로운 글 내용").build();
		Article savedArticle = blogRepository.save(article);

		// W
		Article foundArticle = blogRepository.findById(savedArticle.getId()).get();
		foundArticle.setTitle("변경된 글 제목");
		foundArticle.setContent("변경된 글 내용");

		// T
		Article changedArticle = blogRepository.findById(savedArticle.getId()).get();
		assertThat(foundArticle.getTitle()).isEqualTo(changedArticle.getTitle());
		assertThat(foundArticle.getContent()).isEqualTo(changedArticle.getContent());
	}

	@Test
	@DisplayName("제목 또는 내용 검색 &  정렬")
	public void searchByTitleOrContent() {
		// G
		Article article1 = Article.builder().title("에스파 -Home").content("집집집집집집집집집집").build();
		Article savedArticle1 = blogRepository.save(article1);
		Article article2 = Article.builder().title("안녕하세요").content("에스파").build();
		Article savedArticle2 = blogRepository.save(article2);
		
		String keyword = "에스파";
		// W
		List<Article> resultList = blogRepository.findByTitleContainsOrContentContainsOrderByTitleAsc(keyword, keyword);
		// T
		assertThat(resultList.indexOf(savedArticle1)).isGreaterThan(resultList.indexOf(savedArticle2));
		assertThat(resultList.stream().allMatch(article -> {
			return article.getTitle().contains(keyword) || article.getContent().contains(keyword);
		})).isTrue();
	}
}
