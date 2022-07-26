package ml.ulinom.dorm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ulinom
 * @since 2022-07-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生标识")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @NonNull
    @ApiModelProperty(value = "学生名")
    private String studentName;

    @NonNull
    @ApiModelProperty(value = "宿舍标识")
    private String dormId;


}
