package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static int children, gifts;
    static float[][] giftApproval;
    static boolean[] availableGifts;
    static int[] givenGifts;
    
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("gift_instance.csv");
        Scanner scan = new Scanner(path);
        scan.useLocale(Locale.US);
        
        children = scan.nextInt();
        gifts = scan.nextInt();
        giftApproval = new float[children][gifts];
        availableGifts = new boolean[gifts];
        givenGifts = new int[children];
        for (int i = 0; i < gifts; i++) {
            availableGifts[i] = true;
        }
        
        for(int i = 0; i < children; i++){
            for(int j = 0; j < gifts; j++){
                giftApproval[i][j] = scan.nextFloat();
            }
        }
        giveGifts();
        printResults();
    }
    
    public static void printMatrix(float a[][]){
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.print(" " + a[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void printResults(){
        float sumApproval = 0.0f;
        float avgApproval;
        for(int i = 0; i < children; i++){
            System.out.println("Child: " + i);
            System.out.println("Gift: " + givenGifts[i]);
            System.out.println("Approval: " + giftApproval[i][givenGifts[i]]);
            System.out.println("");
            sumApproval += giftApproval[i][givenGifts[i]];
        }
        System.out.println("Average approval: " + sumApproval / children);
    }
    
    public static void giveGifts(){
        for(int i = 0; i < children; i++){
            float maxApproval = -1.0f;
            float minApproval = 2.0f;
            float avg;
            for(int j = 0; j < gifts; j++){
                if(giftApproval[i][j] < minApproval){
                    minApproval = giftApproval[i][j];
                }
                if(giftApproval[i][j] > maxApproval){
                    maxApproval = giftApproval[i][j];
                }
            }
            avg = maxApproval - minApproval;
            int gift = findAvgGift(i, avg);
            availableGifts[gift] = false;
            givenGifts[i] = gift;
        }
    }
    
    public static int findAvgGift(int child, float avg){
        int avgGift = -1;
        float avgDistance = 2.0f;
        for (int i = 0; i < gifts; i++) {
            float currentDistance = (float) Math.sqrt(Math.pow(avg - giftApproval[child][i], 2));
            if(availableGifts[i] == true &&
            currentDistance < avgDistance){
               avgDistance = currentDistance;
               avgGift = i; 
            }
        }
        return avgGift;
    }
}
