package solutions;

import java.util.*;

public class Day12 {

    private long testExplode(Map<Coordinate, Barrel> oldBarrels, Coordinate o, long alreadyExploded) {
        Map<Coordinate, Barrel> barrels = new HashMap<>();
        for (Map.Entry<Coordinate, Barrel> entry : oldBarrels.entrySet()) {
            Barrel old = entry.getValue();
            barrels.put(entry.getKey(), new Barrel(old.value, old.exploded));
        }
        doExplode(barrels, o);
        long finalVal = barrels.values().stream().filter(x -> x.exploded).count();
        return finalVal - alreadyExploded;
    }

    private void doExplode(Map<Coordinate, Barrel> barrels, Coordinate o) {
        Set<Coordinate> checked = new HashSet<>();
        Set<Coordinate> queue = new HashSet<>();
        queue.add(o);
        barrels.get(o).exploded = true;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            Set<Coordinate> newQueue = new HashSet<>();
            checked.addAll(queue);
            for (Coordinate c : queue) {
                int val = barrels.get(c).value;
                for (int[] dir : dirs) {
                    Coordinate neighbor = new Coordinate(c.x + dir[0], c.y + dir[1]);
                    if (!checked.contains(neighbor) && barrels.containsKey(neighbor)) {
                        Barrel b = barrels.get(neighbor);
                        if (b.value <= val) {
                            b.exploded = true;
                            newQueue.add(neighbor);
                        }
                    }
                }
            }
            queue = newQueue;
        }
    }

    private Map<Coordinate, Barrel> buildBarrels(Scanner in) {
        Map<Coordinate, Barrel> barrels = new HashMap<>();
        int row = 0;
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split("");
            for (int i = 0; i < line.length; i++) {
                barrels.put(new Coordinate(row, i), new Barrel(Integer.parseInt(line[i])));
            }
            row++;
        }
        return barrels;
    }

    private Coordinate findBestExplosion(Map<Coordinate, Barrel> barrels) {
        long alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();

        List<Map.Entry<Coordinate, Barrel>> candidates = new ArrayList<>();
        for (Map.Entry<Coordinate, Barrel> entry : barrels.entrySet()) {
            if (!entry.getValue().exploded) {
                candidates.add(entry);
            }
        }
        candidates.sort((a, b) -> Integer.compare(b.getValue().value, a.getValue().value));

        Coordinate best = null;
        long bestVal = 0;
        for (Map.Entry<Coordinate, Barrel> entry : candidates) {
            long val = testExplode(barrels, entry.getKey(), alreadyExploded);
            if (val > bestVal) {
                bestVal = val;
                best = entry.getKey();
            }
        }
        return best;
    }

    public String solve(int part, Scanner in) {
        Map<Coordinate, Barrel> barrels = buildBarrels(in);
        if (part == 1) {
            long alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            return testExplode(barrels, new Coordinate(0, 0), alreadyExploded) + "";
        }
        if (part == 2) {
            int row = barrels.keySet().stream().mapToInt(c -> c.x).max().getAsInt();
            int col = barrels.keySet().stream().mapToInt(c -> c.y).max().getAsInt();

            long alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            long first = testExplode(barrels, new Coordinate(0, 0), alreadyExploded);
            doExplode(barrels, new Coordinate(0, 0));

            alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            long second = testExplode(barrels, new Coordinate(row, col), alreadyExploded);
            return first + second + "";
        }
        if (part == 3) {
            Coordinate first = findBestExplosion(barrels);
            long alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            long firstVal = testExplode(barrels, first, alreadyExploded);
            doExplode(barrels, first);

            Coordinate second = findBestExplosion(barrels);
            alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            long secondVal = testExplode(barrels, second, alreadyExploded);
            doExplode(barrels, second);

            alreadyExploded = barrels.values().stream().filter(x -> x.exploded).count();
            long thirdVal = testExplode(barrels, findBestExplosion(barrels), alreadyExploded);
            return (firstVal + secondVal + thirdVal) + "";
        }

        return "Invalid part!";
    }
}

class Barrel {
    int value;
    boolean exploded;

    public Barrel(int v) {
        value = v;
        exploded = false;
    }

    public Barrel(int v, boolean e) {
        value = v;
        exploded = e;
    }
}

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate coord) {
            return x == coord.x && y == coord.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 10000 * x + y;
    }
}