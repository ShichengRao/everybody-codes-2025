package solutions;

import java.util.*;

public class Day19 {

    public Map<String, Integer> cache = new HashMap<>();

    public String solve(int part, Scanner in) {
        int currHeight = 0;
        int currDistance = 0;
        List<Opening> openings = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] parts = in.nextLine().split(",");
            Opening o = new Opening(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            openings.add(o);
        }
        return helper(currHeight, currDistance, openings) + "";
    }

    private int helper(int currHeight, int currDistance, List<Opening> originalOpenings) {
        String cacheKey = currHeight + " " + currDistance;
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        List<Opening> openings = new ArrayList<>(originalOpenings);
        if (openings.isEmpty()) {
            cache.put(cacheKey, 0);
            return 0;
        }
        int answer = 0;
        List<Opening> nextOpenings = new ArrayList<>();
        int currDist = openings.getFirst().distance;
        while (!openings.isEmpty() && openings.getFirst().distance == currDist) {
            nextOpenings.add(openings.getFirst());
            openings.removeFirst();
        }
        int minAdditional = 99999999;

        for (Opening potentialOpening : nextOpenings) {
            int netDist = potentialOpening.distance - currDistance;
            int netHeight = currHeight - netDist;
            int heightDelta = potentialOpening.height - netHeight;
            int newFlaps = Math.max((heightDelta + 1) / 2, 0);
            int netFlaps = 0;
            if (potentialOpening.height > netHeight) {
                netHeight += newFlaps * 2;
                netFlaps += newFlaps;
            }
            minAdditional = Math.min(minAdditional, helper(netHeight, potentialOpening.distance, openings) + netFlaps);
        }
        answer += minAdditional;
        cache.put(cacheKey, answer);
        return answer;
    }
}

class Opening {
    int distance;
    int height;
    int width;

    public Opening(int d, int h, int w) {
        distance = d;
        height = h;
        width = w;
    }

    @Override
    public String toString() {
        return distance + " " + height;
    }
}
