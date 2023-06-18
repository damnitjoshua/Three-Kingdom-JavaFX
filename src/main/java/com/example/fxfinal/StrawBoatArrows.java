package com.example.fxfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StrawBoatArrows {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int [] men = new int [4];
        int [] times = {0, 0, 0, 0};
        System.out.println("Number of straw men");
        men[0] = 10;
        System.out.println("Front: "+men[0]);
        men[1] = 50;
        System.out.println("Left: "+men[1]);
        men[2] = 50;
        System.out.println("Right: "+men[2]);
        men[3] = 15;
        System.out.println("Back: "+men[3]);
        
        int l=0, r=0, f=0, b=0;
        String arrowInput = "[2000,1500,1000,800,600,500,300,300]";
        arrowInput = arrowInput.replaceAll("[\\[\\]]", ""); // Remove square brackets and commas
        String[] values = arrowInput.split("\\,"); // Split the input into individual values
        List<Integer> arrow = new ArrayList<>();
        List<Integer> arrowReceived = new ArrayList<>();
        List<String> direction = new ArrayList<>();
        int total=0;
        for (String value : values) {
            arrow.add(Integer.parseInt(value));
        }        
        System.out.println("Arrow: "+arrow);
        int arrowGet;
        for (int i=0; i<arrow.size(); i++){
            int index =max(men);
            direction.add(direct(index));
            arrowGet=(int)(arrow.get(i)*((double)men[index]/100));
            arrowReceived.add(arrowGet);
            total+=arrowGet;
            times[index]++;
            men[index]=efficiency(times[index], men[index]);
        }
        
        System.out.println("Boat direction: "+direction);
        System.out.println("Arrow Received: "+arrowReceived);
        System.out.println("Total = "+total);
        
        
    }
    public static int max(int [] men){
        int max=men[0], index=0;
        for (int i=1; i<men.length; i++){
            if (men[i]>max){
                max=men[i];
                index=i;
            }
        }
        return index;
    }
    
    public static int efficiency(int times, int men){
        double eff=0.0;
        if (times==1){
            eff = (int)men*0.8;
        }
        else if (times==2){
            eff = men/2;
        }
        return (int)eff;
    }
    
    public static String direct(int index){
        if (index==0){
            return "front";
        }
        else if (index==1){
            return "left";
        }
        else if (index==2){
            return "right";
        }
        else{
            return "back";
        }
    }
}