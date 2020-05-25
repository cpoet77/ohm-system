// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/05/22.
package cs.ohms.subsystem.service.impl;

import cs.ohms.subsystem.service.ResourceService;
import com.github.liaochong.myexcel.core.DefaultExcelReader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 * @see ResourceService
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Override
    public <T> List<T> inputStreamToTable(Class<T> clazz, InputStream in) throws Exception {
        return (DefaultExcelReader.of(clazz).sheet(0).rowFilter(row -> row.getRowNum() > 0).read(in));
    }
}
