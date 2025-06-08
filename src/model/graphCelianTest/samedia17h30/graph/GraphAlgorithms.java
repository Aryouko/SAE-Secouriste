package model.graphCelianTest.samedia17h30.graph;

public class GraphAlgorithms {

    public static boolean isOriented(int[][] matrix) {

        int indexL = 0 ;
        for (int[] line : matrix) {

            int indexC = 0;
            for (int column : line) {

                if (column == 1 ) {
                    if ( matrix[indexC][indexL] == 1 ) {
                        return false ;
                    }
                }
                indexC++;
            }
            indexL++;
        }
        return true;
    }


    /*
    public static isConnexe() {}

     */
}
