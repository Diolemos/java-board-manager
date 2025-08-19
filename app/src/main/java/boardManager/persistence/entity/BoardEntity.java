package boardManager.persistence.entity;

import java.util.List;

import lombok.Data;


@Data
public class BoardEntity {
    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumns;
}