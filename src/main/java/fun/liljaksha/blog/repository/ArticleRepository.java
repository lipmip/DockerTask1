package fun.liljaksha.blog.repository;

import fun.liljaksha.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a ORDER BY a.publishedAt DESC, SIZE(a.likes) DESC")
    Page<Article> findAllSortedByPublishedAt(Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY SIZE(a.likes) DESC, a.publishedAt DESC")
    Page<Article> findAllSortedByLikes(Pageable pageable);
}
