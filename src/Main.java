import edu.princeton.cs.introcs.Picture;
import week2.home.SeamCarver;

/**
 * Created by Superman Petrovich on 3/21/14.
 */
public class Main {
    public static void main(String[] args){
        Picture inputImg = new Picture(args[0]);

        SeamCarver sc = new SeamCarver(inputImg);
    }
}
