/**
 * Author: Dhruv Rajpurohit
 * Description: This is the main program for Jukebox simulation
 */


package jukebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Jukebox {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            File fname = new File("tracks-10.txt");
            Scanner in = new Scanner(fname);

            ArrayList<Song> jukebox = new ArrayList<Song>();

            long start = System.currentTimeMillis();
            while(in.hasNextLine()){
                String line = in.nextLine();
                String[] fields = line.split("<SEP>", 4);
                String artist = fields[2];
                String song = fields[3];
                Song jk = new Song(artist, song);
                jukebox.add(jk);
            }
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;

            for (Song jk : jukebox){
                System.out.println(jk.toString());
            }

            System.out.println("Simulation took " + timeElapsed + "s");
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
