// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cs.ohms.subsystem.component;

import cs.ohms.subsystem.utils.leafo2o.LeafConvertException;
import cs.ohms.subsystem.utils.leafo2o.LeafO2oUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Object to Object
 * <p>没有特殊使用的情况下，使用BeanUtils.copyProperties性能更高</p>
 * <p><b>忙于使用，没有过多的进行调试和修复</b></p>
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
@Component
public class LeafO2oCMP {
    /**
     * 存储容器
     */
    private final static Map<PK<?, ?>, LeafO2oUtil<?, ?>> UTILS = new HashMap<>();

    /**
     * 正向转换
     *
     * @param source source
     * @param target target
     * @param groups groups
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 目标对象
     */
    public <S, T> T convert(@NotNull S source, @NotNull T target, Class<?>... groups) {
        PK<S, T> pk = createPK((Class<S>) source.getClass(), (Class<T>) target.getClass());
        return plumbAndGet(pk).convert(source, target, groups);
    }

    /**
     * 正向转换
     *
     * @param source source
     * @param tClass tClass
     * @param groups 转换场景
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 目标对象
     * @throws LeafConvertException LeafConvertException
     */
    public <S, T> T convert(@NotNull S source, Class<T> tClass, Class<?>... groups) throws LeafConvertException {
        PK<S, T> pk = createPK((Class<S>) source.getClass(), tClass);
        return plumbAndGet(pk).convert(source, groups);
    }

    /**
     * 反向转换
     *
     * @param source source
     * @param target target
     * @param groups 场景
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 转换得到的源对象
     */
    public <S, T> S reversal(@NotNull S source, @NotNull T target, Class<?>... groups) {
        PK<S, T> pk = createPK((Class<S>) source.getClass(), (Class<T>) target.getClass());
        return plumbAndGet(pk).reversal(source, target, groups);
    }

    /**
     * 反向转换
     *
     * @param sClass sClass
     * @param target target
     * @param groups 场景
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 转换得到的源对象
     * @throws LeafConvertException LeafConvertException
     */
    public <S, T> S reversal(@NotNull Class<S> sClass, @NotNull T target, Class<?>... groups) throws LeafConvertException {
        PK<S, T> pk = createPK(sClass, (Class<T>) target.getClass());
        return plumbAndGet(pk).reversal(target, groups);
    }

    /**
     * 查询并返回LeafO2oUtil
     * <p>不存在则实例化</p>
     *
     * @param sClass sClass
     * @param tClass tClass
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return LeafO2oUtil
     */
    public <S, T> LeafO2oUtil<S, T> getUtil(Class<S> sClass, Class<T> tClass) {
        return plumbAndGet(createPK(sClass, tClass));
    }

    /**
     * 实例化LeafO2oUtil
     *
     * @param sClass sClass
     * @param tClass tClass
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return LeafO2oUtil
     */
    @NotNull
    @Contract("_, _ -> new")
    private <S, T> LeafO2oUtil<S, T> loadUtil(Class<S> sClass, Class<T> tClass) {
        return (new LeafO2oUtil<>(sClass, tClass));
    }

    /**
     * 查询并返回
     *
     * @param pk  key
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return LeafO2oUtil工具
     */
    @NotNull
    private <S, T> LeafO2oUtil<S, T> plumbAndGet(PK<S, T> pk) {
        if (UTILS.containsKey(pk)) {
            return (LeafO2oUtil<S, T>) UTILS.get(pk);
        }
        LeafO2oUtil<S, T> leafO2oUtil = loadUtil(pk.sClass, pk.tClass);
        UTILS.put(pk, leafO2oUtil);
        return leafO2oUtil;
    }

    /**
     * 生成键对
     *
     * @param sClass sClass
     * @param tClass tClass
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return PK
     */
    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    private <S, T> PK<S, T> createPK(Class<S> sClass, Class<T> tClass) {
        return new PK<>(sClass, tClass);
    }

    /**
     * 由sClass和tClass组成的key
     *
     * @param <S> 源类型
     * @param <T> 目标类型
     */
    private final static class PK<S, T> {
        public Class<S> sClass;
        public Class<T> tClass;

        public PK(Class<S> sClass, Class<T> tClass) {
            this.sClass = sClass;
            this.tClass = tClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK<?, ?> pk = (PK<?, ?>) o;
            return Objects.equals(sClass, pk.sClass) &&
                    Objects.equals(tClass, pk.tClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sClass, tClass);
        }
    }
}
