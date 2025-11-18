package solutions;

import java.util.*;

public class Day12 {

    private long testExplode(Map<Coordinate, Barrel> oldBarrels, Coordinate o) {
        Map<Coordinate, Barrel> barrels = new HashMap<>();
        oldBarrels.keySet().forEach(key -> {
            barrels.put(key, new Barrel(oldBarrels.get(key).value));
            if (oldBarrels.get(key).exploded) {
                barrels.get(key).exploded = true;
            }
        });
        long initialVal = barrels.values().stream().filter(x -> x.exploded).count();
        doExplode(barrels, o);
        long finalVal = barrels.values().stream().filter(x -> x.exploded).count();
        return finalVal - initialVal;
    }

    private void doExplode(Map<Coordinate, Barrel> barrels, Coordinate o) {
        Set<Coordinate> checked = new HashSet<>();
        Set<Coordinate> queue = new HashSet<>();
        queue.add(o);
        barrels.get(o).exploded = true;
        while (!queue.isEmpty()) {
            Set<Coordinate> newQueue = new HashSet<>();
            checked.addAll(queue);
            for (Coordinate c : queue) {
                int val = barrels.get(c).value;
                List<Coordinate> neighbors = c.getNeighbors();
                for (Coordinate neighbor : neighbors) {
                    if (barrels.containsKey(neighbor) && barrels.get(neighbor).value <= val && !checked.contains(neighbor)) {
                        barrels.get(neighbor).exploded = true;
                        newQueue.add(neighbor);
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

    public String solve(int part, Scanner in) {
        Map<Coordinate, Barrel> barrels = buildBarrels(in);
        if (part == 1) {
            return testExplode(barrels, new Coordinate(0, 0)) + "";
        }
        if (part == 2) {
            int row = barrels.keySet().stream().mapToInt(c -> c.x).max().getAsInt();
            int col = barrels.keySet().stream().mapToInt(c -> c.y).max().getAsInt();
            long first = testExplode(barrels, new Coordinate(0, 0));
            doExplode(barrels, new Coordinate(0, 0));
            long second = testExplode(barrels, new Coordinate(row - 1, col - 1));
            return first + second + " ";
        }
        if (part == 3) {
            Coordinate first = new Coordinate(-1, -1);
            long firstVal = 0;
            for (Coordinate c : barrels.keySet()) {
                if (testExplode(barrels, c) > firstVal) {
                    firstVal = testExplode(barrels, c);
                    first = c;
                }
            }
            doExplode(barrels, first);
            Coordinate second = new Coordinate(-1, -1);
            long secondVal = 0;
            for (Coordinate c : barrels.keySet()) {
                if (testExplode(barrels, c) > secondVal) {
                    secondVal = testExplode(barrels, c);
                    second = c;
                }
            }
            doExplode(barrels, second);
            long thirdVal = 0;
            for (Coordinate c : barrels.keySet()) {
                if (testExplode(barrels, c) > thirdVal) {
                    thirdVal = testExplode(barrels, c);
                }
            }
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
}


class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Coordinate> getNeighbors() {
        List<Coordinate> results = new ArrayList<>();
        results.add(new Coordinate(x - 1, y));
        results.add(new Coordinate(x + 1, y));
        results.add(new Coordinate(x, y - 1));
        results.add(new Coordinate(x, y + 1));
        return results;
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