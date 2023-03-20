//************************************************** 
//*--#$ String command = System.getProperty("a");
//*--#$ System.out.println(command);
//**************************************************
import j4np.physics.VectorOperator.OperatorType;

//code written by Zachary Nickischer

public class ReadCherenkov {
    //Bank[] banks = r.getBanks("REC::Particle","REC::Calorimeter","REC::Scintillator","REC::Cherenkov");
    Bank[]  banks;
    String name = "REC::Cherenkov";
    int pindex;
    
    //If specific layers are wanted Layer "" needs to be added in the methods to select (1, 4, or 7)
    enum Layer {
        PCAL,
        ECALIN,
        ECALOUT,
    }
    
    public ReadCherenkov(HipoReader r){
        banks = r.getBanks(name);
    }
    
    
    public void read(HipoReader r){
        r.nextEvent(banks);
        
    }
    
    
    //nphe is energy for cherenkov
    //If two of the same pindex are present this will add their nphe
    public double getEnergy (int index){
        double energy = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int i = 0; i < banks[0].getRows(); i++){
            if(banks[0].getInt("pindex",i)==0)
                energy += banks[0].getFloat("nphe",i);
        }
        return energy;
    }
    
    
    //needs to be looked at if scientists want first or second time if there is two times with same pindex
    public double getTime (int index){
        double time = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int row = 0; row < nrows; row++){
            int pindex = banks[0].getInt("pindex",row);
            if(pindex==index) {
                time = (banks[0].getFloat("time",row));
                break;
            }
        }
            
        return time;
    }
        
    
    //Will also take the first instace of path if two of same pindex are present
    public double getPath (int index){
        double path = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int row = 0; row < nrows; row++){
            int pindex = banks[0].getInt("pindex",row);
            if(pindex==index) {
                path = (banks[0].getFloat("path",row));
                break;
            }
        }
        
        return path;
    }
    
    
    
    //test
    
    public void show(){banks[0].show();}
    public static void run(int max){
    HipoReader r = new HipoReader("infile.hipo");
        ReadCherenkov cher = new ReadCherenkov(r);
        int counter = 0;
        while(r.hasNext()){
            counter++; if(counter>max) break;
            cher.read(r);
            cher.show();
            
            
            double energy = cher.getEnergy(0);
            double time = cher.getTime(0);
            double path = cher.getPath(0);
            
            System.out.printf(">>>>>>>> energy = %f\n",energy);
            System.out.printf(">>>>>>>> time = %f\n",time);
            System.out.printf(">>>>>>>> path = %f\n",path);
            
            
        }
    }

}


