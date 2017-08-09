package xl.learn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DuplicateSubtrees {
    private static String walk(Node curr, Set<String> hashes, Set<String> dups, List<Node> dupNodes)
    {
        if (curr == null)
            return "()";

        String l = walk(curr.left, hashes, dups, dupNodes);
        String r = walk(curr.right, hashes, dups, dupNodes);
        String hash = "(" + l + "," + curr.val + "," + r + ")";
        if (hashes.contains(hash)) {
            dups.add(hash);
            dupNodes.add(curr);
        }
        hashes.add(hash);
        return hash;
    }

    public static Collection<Node> findDups(Node root)
    {
        Set<String> hashes = new HashSet<>();
        Set<String> dups = new HashSet<>();
        List<Node> dupNodes = new ArrayList<>();
        walk(root, hashes, dups, dupNodes);

        return dupNodes;
    }
}
