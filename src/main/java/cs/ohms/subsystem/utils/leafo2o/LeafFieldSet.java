// The code file was created by <a href="https://www.nsleaf.cn">nsleaf</a> (email:nsleaf@foxmail.com) on 2020/5/14.
package cs.ohms.subsystem.utils.leafo2o;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * LeafFieldSet
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class LeafFieldSet extends HashSet<LeafField> {
    public LeafFieldSet() {
    }

    public LeafFieldSet(@NotNull Collection<? extends LeafField> c) {
        super(c);
    }

    public LeafFieldSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public LeafFieldSet(int initialCapacity) {
        super(initialCapacity);
    }
}
