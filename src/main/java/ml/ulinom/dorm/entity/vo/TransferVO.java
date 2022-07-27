package ml.ulinom.dorm.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TransferVO {
    private String dormId;
    private List<Map> getData;
}
