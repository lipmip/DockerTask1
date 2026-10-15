package fun.liljaksha.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "articles_seq")
    @SequenceGenerator(name = "articles_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private long id;

    @Length(min = 1, max = 50)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime publishedAt;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "article",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_email")
    )
    private Set<User> likes;

    public void like(User user) {
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(user);
    }

    public void comment(Comment comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }
        comments.add(comment);
    }
}