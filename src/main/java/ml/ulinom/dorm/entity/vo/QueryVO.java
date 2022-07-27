package ml.ulinom.dorm.entity.vo;

import lombok.Data;

@Data
abstract class QueryVO {
    long page;
    long limit;
}
