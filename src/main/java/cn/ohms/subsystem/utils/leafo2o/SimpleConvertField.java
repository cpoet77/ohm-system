// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cn.ohms.subsystem.utils.leafo2o;

/**
 * SimpleConvertField
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class SimpleConvertField implements LeafConvertField<Object, Object>{
    @Override
    public Object convert(Object source, Object target) {
        return source;
    }

    @Override
    public Object reversal(Object source, Object target) {
        return target;
    }
}
