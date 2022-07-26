package ml.ulinom.dorm.entity.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DormQueryVO extends ObjectVO{
    private String id;
    private String dormNumber;
}
