package solutions;


import java.util.Scanner;

public class Day01 {

    public String solve(int part, Scanner in){
        if(part == 1){
            String[] names = in.nextLine().split(",");
            in.nextLine();
            String[] directions = in.nextLine().split(",");
            int index = 0;
            for(String direction: directions){
                int value = Integer.parseInt(direction.substring(1));
                if(direction.charAt(0) == 'R'){
                    index += value;
                    if(index >= names.length){
                        index = names.length - 1;
                    }
                }
                else{
                    index -= value;
                    if(index < 0){
                        index = 0;
                    }
                }
            }
            return names[index];

        }
        if(part == 2){
            String[] names = in.nextLine().split(",");
            in.nextLine();
            String[] directions = in.nextLine().split(",");
            int index = 0;
            for(String direction: directions){
                int value = Integer.parseInt(direction.substring(1));
                if(direction.charAt(0) == 'R'){
                    index += value;
                }
                else{
                    index -= value;
                }
            }
            index = (index % names.length + names.length)%names.length;
            return names[index];
        }
        if(part == 3){
            String[] names = in.nextLine().split(",");
            in.nextLine();
            String[] directions = in.nextLine().split(",");
            for(String direction: directions){
                int value = Integer.parseInt(direction.substring(1));
                if(direction.charAt(0) == 'R'){
                    value = (value % names.length + names.length) % names.length;
                }
                else{
                    value = (-1 * value % names.length + names.length) % names.length;
                }
                String tmp = names[value];
                names[value] = names[0];
                names[0] = tmp;
            }
            return names[0];
        }
        return "Invalid part!";
    }
}
