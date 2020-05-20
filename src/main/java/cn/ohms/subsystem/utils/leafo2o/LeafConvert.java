// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cn.ohms.subsystem.utils.leafo2o;

/**
 * Top-level interface
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public interface LeafConvert <S, T>{
    /**
     * Implement source to target conversion
     *
     * @param source source
     * @return target
     */
    T convert(S source, T target);

    /**
     * Reverse conversion from source to target
     *
     * @param target target
     * @return source
     */
    S reversal(S source, T target);
}
