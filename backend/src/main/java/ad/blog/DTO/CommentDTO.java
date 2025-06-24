package ad.blog.DTO;

public class CommentDTO {
    
    private Long id;
    private String content;
    private String author;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
