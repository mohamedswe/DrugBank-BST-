import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Drug {
   public String drugBankID;
    String genericName;
    String SMILES;
    String url;
    String drugGroups;
    int score;

    public Drug(String drugBankID, String genericName, String SMILES, String url, String drugGroups,int score){
        this.drugBankID= drugBankID;
        this.genericName= genericName;
        this.SMILES=SMILES;
        this.url=url;
        this.drugGroups=drugGroups;
        this.score=score;
//        displayDrug();
    }

    /**
     * This method displays the info of a drug the reason it is not called is for reason when I do call it the code takes
     * 10 minutes
     */
    public void displayDrug(){
        try{
            File textFile = new File(("dockedApproved.tab"));
            Scanner sc = new Scanner(textFile);
            while(sc.hasNext());{
                String line = sc.nextLine();
                StringTokenizer token = new StringTokenizer(line,"\t");
                for(int i=0; i<6; i++){
                    System.out.println(token.nextToken());
                }
                System.out.println("**********************");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
