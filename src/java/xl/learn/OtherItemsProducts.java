package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;

import java.util.*;

/**
 * Created by xuelin on 9/14/17.
 * calc roducts of all other items in the list for each i.
 * assume items number is n.
 * n:
 * pair [2i, 2i+1]
 * continue pairing
 */
public class OtherItemsProducts {
    final Map<String, Double> cache = new HashMap<>();
    final double[] vals;
    int prodCount = 0;

    public OtherItemsProducts(double[] vals) {
        this.vals = vals;
    }

    private String getKey(int from, int to) {
        return "" + from + "-" + to;
    }

    private double calc(int from, int to, int exclude)
    {
        if (exclude >= from && exclude < to) {
            return calc(from, exclude, exclude) * calc(exclude + 1, to, exclude);
        }

        if (from >= to) {
            return 1.0;
        }

        if (from == to - 1) {
            return vals[from];

        }

        String key = getKey(from, to);
        Double calced = cache.get(key);
        if (calced != null)
            return calced;

        int mid = (from + to) / 2;
        double prod1 = calc(from, mid, exclude);
        double prod2 = calc(mid, to, exclude);
        double prod = prod1 * prod2;
        prodCount++;
        cache.put(key, prod);
        return prod;
    }

    public  double[] calc() {
        assert(vals.length > 1);

        //remember all products calculated, reuse when available
        //depth first

        double[] results = new double[vals.length];
        for (int i = 0; i < vals.length; i++) {
            results[i] = calc(0, vals.length, i);
        }

        System.out.println("Total multiply count: " + prodCount);
        return results;
    }

    public static void main(String[] args) {
        double[] vals = new double[]{1,7,3,4};
        double[] results = new OtherItemsProducts(vals).calc();
        System.out.println(Joiner.on(", ").join(Doubles.asList(results)));
    }
}
