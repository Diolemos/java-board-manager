package boardManager.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public class BoardEntity {
    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<BoardColumnEntity> getBoardColumns() { return boardColumns; }
    public void setBoardColumns(List<BoardColumnEntity> boardColumns) { this.boardColumns = boardColumns; }
}