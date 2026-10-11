package fun.liljaksha.blog.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(long id) {
        super("Article not found: " + id);
    }
    
}
