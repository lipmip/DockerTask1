package fun.liljaksha.blog.repository;

import fun.liljaksha.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a ORDER BY a.publishedDate DESC, SIZE(a.likes) DESC")
    Page<Article> findAllByOrderByPublishedDateDesc(Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY SIZE(a.likes) DESC, a.publishedDate DESC")
    Page<Article> findAllSortedByLikes(Pageable pageable);
}
