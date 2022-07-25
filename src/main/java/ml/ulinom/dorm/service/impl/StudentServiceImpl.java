package ml.ulinom.dorm.service.impl;

import ml.ulinom.dorm.entity.Student;
import ml.ulinom.dorm.mapper.StudentMapper;
import ml.ulinom.dorm.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ulinom
 * @since 2022-07-25
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
