// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cs.ohms.subsystem.utils.leafo2o;

/**
 * SimpleConvertCase
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class SimpleConvertCase implements LeafConvertCase<Object, Object>{
    @Override
    public Object convert(Object source, Object target) {
        return target;
    }

    @Override
    public Object reversal(Object source, Object target) {
        return source;
    }
}
