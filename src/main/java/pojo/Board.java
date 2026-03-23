package pojo;

import lombok.Data;

@Data
public class Board {
    private String title;
    private String description;
    private String top;
    private String left;
    private String default_card_type_id;
}
