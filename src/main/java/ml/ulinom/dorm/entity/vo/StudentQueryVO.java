package ml.ulinom.dorm.entity.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StudentQueryVO extends ObjectVO{
    private String id;
    private String studentName;
}
