package com.company;
import java.io.*;
/*
   un fichier  -> add to last clause (literalle )
   modify l'entete or replace nbclause+1
   execute cmd
    */
public class inference {
    static void addClause(File f, int litteral , int n) throws IOException {
       Writer file = new BufferedWriter(new FileWriter(f.getName(), true));
       file.write("");
         litteral=litteral*-1;
       String NonBut = String.valueOf(litteral);
       NonBut = NonBut+" 0\n";
       file.append(NonBut);
        file.close();
    }

    static int updateFile(File f,int litteral) throws IOException {
        File inputFile = new File("test1.cnf");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        Writer file = new BufferedWriter(new FileWriter("test2.cnf", true));
        //copy all expect toDelete
        int nbr_var=0; int nbr_clause = 0;
        //String toDelete ="p cnf 5 11";
        String currentLine;
         int i=1;String toDelete=null;
        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(i ==1) {
                 toDelete = currentLine; //System.out.println(" Todelete = "+toDelete);
                String string = trimmedLine.substring(6);
               // System.out.println(" String =" + string);
                nbr_var = Integer.valueOf( string.substring(0,1));
                if( (litteral) >= nbr_var){ nbr_var = litteral  ;}
                //System.out.println(" var = "+nbr_var);
                nbr_clause = Integer.valueOf( string.substring(2));//System.out.println(" clauses = "+nbr_clause);
                nbr_clause++;
                file.append("p cnf ");
                file.append(nbr_var+" ");
                file.append(nbr_clause+"\n");
            }//System.out.println( "trimmedLine ="+trimmedLine);
            i++;
            if(trimmedLine.equals(toDelete)) continue;
            file.write(currentLine + System.getProperty("line.separator"));
        }
        file.close();
        return nbr_var;

    }
    //tester le nv fichier
    static void testerSat(File file) throws IOException {
        try
        {
            String cmd ="ubcsat -alg saps -i test2.cnf -solve";
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            // Read the output from the command
            System.out.println("Here is the standard output of the command:");
            String s;
            while ((s = stdInput.readLine()) != null) {
                if(s.equals("# No Solution found for -target 0")) System.out.println("NON SAT");
                if(s.equals("# Solution found for -target 0")) System.out.println("SAT");
               }
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s); System.out.println("hi");
            }
        }
        catch(IOException e)
        {
            System.err.println("echec de l'execution du script: "+e);
            System.exit(1);
        }
    }
}
