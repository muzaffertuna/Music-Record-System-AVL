package MusicRecordSystemAVL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author tokta
 */
public class TestMRS {

    private static final Song[] musicList = new Song[9];
    private static final AVLTreeComparable<Integer> tree1 = new AVLTreeComparable<>();
    private static final AVLTreeComparable<String> tree2 = new AVLTreeComparable<>();
    private static final AVLTreeComparable<String> tree3 = new AVLTreeComparable<>();
    private static final Scanner input = new Scanner(System.in, "ISO-8859-9");

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("songs.txt");
        try (Scanner scan = new Scanner(file)) {
            int index = 0;

            while (scan.hasNextLine()) {
                String singleLine = scan.nextLine();
                String[] lineArray = singleLine.split(";");

                String songName = lineArray[0];
                String artist = lineArray[1];
                int ID = Integer.parseInt(lineArray[2]);
                String genre = lineArray[3];
                int year = Integer.parseInt(lineArray[4]);

                Song createSong = new Song(songName, artist, ID, genre, year);
                musicList[index++] = createSong;
            }
            System.out.println(Arrays.toString(musicList));
            insertToThreeAVL();

            String menu;

            OUTER:
            do {
                System.out.println("\nWhich task you want to do?\n"
                        + "\n"
                        + "1- Search 1(Searches by Music's songName, artist or ID.)\n"
                        + "2- Search 2(Searches according to given ID ranges and returns music information in that range.)\n"
                        + "3- Delete\n"
                        + "4- Exit\n");

                System.out.println("Please enter a digit(1-4): ");
                menu = input.nextLine();
                System.out.println("");

                switch (menu) {
                    case "1":
                        search1();
                        break;
                    case "2":
                        search2();
                        break;
                    case "3":
                        delete();
                        break;
                    default:
                        System.out.println("Exiting the program...");
                        break OUTER;
                }
                System.out.println("---------------------------------------------------"
                        + "-------------------------------------------------------------");
            } while (!menu.equals("4"));

        } catch (FileNotFoundException e) {
            System.out.println("Given file was not found please try again");
        }

    }

    private static void insertToThreeAVL() {
        for (int i = 0; i < musicList.length; i++) {
            tree1.insert(musicList[i].ID, i);
            tree2.insert(musicList[i].songName, i);
            tree3.insert(musicList[i].artist, i);
        }
    }

    public static void search1() {
        System.out.println("Enter the Song Name, Artist or ID of the music you want to Search: ");
        String s = input.nextLine();

        Integer indexTree1 = null;
        try {
            indexTree1 = tree1.search(Integer.valueOf(s));
        } catch (IllegalArgumentException e) {
        }
        Integer indexTree2 = tree2.search(s);
        Integer indexTree3 = tree3.search(s);

        if (indexTree1 != null) {
            System.out.println(musicList[indexTree1]);
        } else if (indexTree2 != null) {
            System.out.println(musicList[indexTree2]);
        } else if (indexTree3 != null) {
            System.out.println(musicList[indexTree3]);
        } else {
            System.out.println("The music you searched for was not found.");
        }
    }

    public static void search2() {
        System.out.println("Enter lower bound as song ID: ");
        String min = input.nextLine();

        System.out.println("Enter upper bound as song ID: ");
        String max = input.nextLine();

        System.out.println("\nMusic information in the given ID ranges is being printed...");
        try {
            tree1.searchGivenBoundariesOfID(Integer.parseInt(min), Integer.parseInt(max), musicList);
        } catch (NumberFormatException e) {
            System.out.println("Given numbers was unvalid.");
        }

    }

    public static void delete() {
        System.out.println("Enter the ID of the music you want to Delete: ");
        try {
            String ID = input.nextLine();
            Integer indexForDel = tree1.search(Integer.valueOf(ID));

            if (indexForDel != null) {
                tree1.delete(Integer.valueOf(ID));
                tree2.delete(musicList[indexForDel].songName);
                tree3.delete(musicList[indexForDel].artist);
                System.out.println("\nDeletion successful.");
                print();
            } else {
                System.out.println("\nThe ID of the music you want to delete could not be found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Given number was unvalid.");
        }
    }

    private static void print() {
        System.out.println("\n-------------------traverseInOrder------------------------");
        tree1.traverseInOrder();
        System.out.println("------------------------------------------------------------");
        tree2.traverseInOrder();
        System.out.println("------------------------------------------------------------");
        tree3.traverseInOrder();
    }
}
