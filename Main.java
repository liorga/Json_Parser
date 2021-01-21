package ex3_json;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        JsonBuilder avraham = null;
        try
        {
            avraham = new JsonBuilder( new File( args[0] ));
            System.out.println( avraham );
            System.out.println( avraham.get( "issue" ).get( "Ketura" ).get( 2 ) );
        }

        catch( SyntaxException | QueryException | FileNotFoundException e )
        {
            e.printStackTrace();
        }

    }
}

