// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/06/06.
package cs.ohms.subsystem.viewobject;

import cs.ohms.subsystem.entity.CourseGroupEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Data
@Accessors(chain = true)
public class CourseGroupListVo implements Serializable {
    private Integer page;

    private Long count;

    private List<CourseGroupEntity> courseGroups;
}
