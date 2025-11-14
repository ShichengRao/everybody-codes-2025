package solutions;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 {

    public String solve(int part, Scanner in){
        if(part == 1){
            List<String[]> dnas = new ArrayList<>();
            while(in.hasNextLine()){
                String line = in.nextLine().split(":")[1];
                dnas.add(line.split(""));
            }
            long sim1 = 0;
            long sim2 = 0;
            for(int i = 0; i < dnas.get(2).length; i++){
                if(dnas.get(2)[i].equals(dnas.get(0)[i])){
                    sim1++;
                }
                if(dnas.get(2)[i].equals(dnas.get(1)[i])){
                    sim2++;
                }
            }
            return (sim1 * sim2) + "";

        }
        if(part == 2){
            long answer = 0;
            List<String[]> dnas = new ArrayList<>();
            while(in.hasNextLine()){
                String line = in.nextLine().split(":")[1];
                dnas.add(line.split(""));
            }
            for(int i = 0; i < dnas.size(); i++){
                for(int j = i + 1; j < dnas.size(); j++){
                    for(int k = j + 1; k < dnas.size(); k++){
                        ParentChild p1 = new ParentChild(i,j,k);
                        answer += p1.similarity(dnas);
                        ParentChild p2 = new ParentChild(k,i,j);
                        answer += p2.similarity(dnas);
                        ParentChild p3 = new ParentChild(j,k,i);
                        answer += p3.similarity(dnas);
                    }
                }
            }
            return answer + "";

        }
        if(part == 3){
            long answer = 0;
            List<String[]> dnas = new ArrayList<>();
            while(in.hasNextLine()){
                String line = in.nextLine().split(":")[1];
                dnas.add(line.split(""));
            }
            List<Integer> famNum = new ArrayList<>();
            for(int i = 0; i < dnas.size(); i++){
                famNum.add(i);
            }
            for(int i = 0; i < dnas.size(); i++){
                for(int j = i + 1; j < dnas.size(); j++){
                    for(int k = j + 1; k < dnas.size(); k++){
                        ParentChild p1 = new ParentChild(i,j,k);
                        if(p1.similarity(dnas) > 0){
                            connect(famNum, i,j,k);
                        }
                        ParentChild p2 = new ParentChild(k,i,j);
                        if(p2.similarity(dnas) > 0){
                            connect(famNum, i,j,k);
                        }
                        ParentChild p3 = new ParentChild(j,k,i);
                        if(p3.similarity(dnas) > 0){
                            connect(famNum, i,j,k);
                        }
                    }
                }
            }
            int maxFam = 0;
            for(int i = 0; i < famNum.size(); i++){
                int localFam = 0;
                int localSum = 0;
                for(int j = 0; j < famNum.size(); j++){
                    if(famNum.get(j) == i){
                        localSum += j + 1;
                        localFam ++;
                    }
                }
                if(localFam > maxFam){
                    maxFam = localFam;
                    answer = localSum;
                }
            }
            return answer + "";
        }
        return "Invalid part!";
    }

    private void connect(List<Integer> famNum, int p1, int p2, int c){
        int val1 = famNum.get(p1);
        int val2 = famNum.get(p2);
        int goal = famNum.get(c);
        for(int i = 0; i < famNum.size(); i++){
            if(famNum.get(i) == val1 || famNum.get(i) == val2){
                famNum.set(i, goal);
            }
        }
    }
}

class ParentChild{
    int parent1;
    int parent2;
    int child;

    public ParentChild(int p1, int p2, int c){
        parent1 = p1;
        parent2 = p2;
        child = c;
    }

    public long similarity(List<String[]> dnas){
        long sim1 = 0;
        long sim2 = 0;
        for(int i = 0; i < dnas.get(child).length; i++){
            if(dnas.get(child)[i].equals(dnas.get(parent1)[i])){
                sim1++;
            }
            if(dnas.get(child)[i].equals(dnas.get(parent2)[i])){
                sim2++;
            }
            if(!(dnas.get(child)[i].equals(dnas.get(parent1)[i])) && !(dnas.get(child)[i].equals(dnas.get(parent2)[i]))){
                return 0;
            }
        }
        return sim1 * sim2;
    }
}
