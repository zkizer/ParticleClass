//************************************************** 
//*--#$ String command = System.getProperty("a");
//*--#$ System.out.println(command);
//**************************************************
import j4np.physics.VectorOperator.OperatorType;

//code written by Zachary Nickischer

public class ReadScintillator {
    //Bank[] banks = r.getBanks("REC::Particle","REC::Calorimeter","REC::Scintillator","REC::Cherenkov");
    Bank[]  banks;
    String name = "REC::Scintillator";
    int pindex;
    
    public enum Layer {
        ONE,
        TWO,
    }
    
    public ReadScintillator(HipoReader r){
        banks = r.getBanks(name);
    }
    
    
    public void read(HipoReader r){
        r.nextEvent(banks);
        
    }
    
    public double getEnergy (int index, Layer choice){
        double energy = 0.0;
        int     nrows = banks[0].getRows();
        
        switch(choice) {
            //layer 1
            case ONE:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==1)) {
                        energy = (banks[0].getFloat("energy",row));
                    }
                }
                break;
                
            //layer 2
            case TWO:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==2)) {
                        energy = (banks[0].getFloat("energy",row));
                    }
                }
                break;
               
        }
         
        return energy;
    }
    
    public double getTime (int index, Layer choice){
        double time = 0.0;
        int     nrows = banks[0].getRows();
        
        
        switch(choice) {
            //layer 1
            case ONE:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==1)) {
                        time = (banks[0].getFloat("time",row));
                    }
                }
                break;
                
            //layer 2
            case TWO:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==2)) {
                        time = (banks[0].getFloat("time",row));
                    }
                }
                break;
                
        }
        return time;
    }
    
    public double getPath (int index, Layer choice){
        double path = 0.0;
        int     nrows = banks[0].getRows();
        
        switch(choice) {
            //layer 1
            case ONE:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==1)) {
                        path = (banks[0].getFloat("path",row));
                    }
                }
                break;
                
            //layer 2
            case TWO:
                for(  int row = 0; row < nrows; row++){
                    int pindex = banks[0].getInt("pindex",row);
                    int layer = banks[0].getInt("layer",row);
                    if((pindex==index) && (layer==2)) {
                        path = (banks[0].getFloat("path",row));
                    }
                }
                break;
                
        }
        return path;
    }
   
    
    
    //test
    
    public void show(){banks[0].show();}
    public static void run(int max){
    HipoReader r = new HipoReader("infile.hipo");
        ReadScintillator scint = new ReadScintillator(r);
        int counter = 0;
        while(r.hasNext()){
            counter++; if(counter>max) break;
            scint.read(r);
            scint.show();
            
            
            double energy = scint.getEnergy(0, Layer.TWO);
            double time = scint.getTime(0, Layer.TWO);
            double path = scint.getPath(0, Layer.TWO);
            
            System.out.printf(">>>>>>>> energy = %f\n",energy);
//            System.out.printf(">>>>>>>> time = %f\n",time);
//            System.out.printf(">>>>>>>> path = %f\n",path);
            
            
        }
    }
    
}


