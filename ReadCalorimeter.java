//************************************************** 
//*--#$ String command = System.getProperty("a");
//*--#$ System.out.println(command);
//**************************************************
import j4np.physics.VectorOperator.OperatorType;

//code written by Zachary Nickischer

public class ReadCalorimeter {
    //Bank[] banks = r.getBanks("REC::Particle","REC::Calorimeter","REC::Scintillator","REC::Cherenkov");
    Bank[]  banks;
    String name = "REC::Calorimeter";
    int pindex;
    
    //If specific layers are wanted Layer "" needs to be added in the methods to select (1, 4, or 7)
    enum Layer {
        PCAL,
        ECALIN,
        ECALOUT,
    }
    
    public ReadCalorimeter(HipoReader r){
        banks = r.getBanks(name);
    }
    
    
    public void read(HipoReader r){
        r.nextEvent(banks);
        
    }
    
    public double getEnergy (int index){
        double energy = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int i = 0; i < banks[0].getRows(); i++){
            if(banks[0].getInt("pindex",i)==0)
                energy += banks[0].getFloat("energy",i);
        }
        return energy;
    }
    
    
    public double getTime (int index){
        double time = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int row = 0; row < nrows; row++){
            int pindex = banks[0].getInt("pindex",row);
            int layer = banks[0].getInt("layer",row);
            
            //takes the first layer first for time
            if((pindex==index) && (layer==1)) {
                time = (banks[0].getFloat("time",row));
                break;
            }
            if((pindex==index) && (layer==4)) {
                time = (banks[0].getFloat("time",row));
                break;
            }
            if((pindex==index) && (layer==7)) {
                time = (banks[0].getFloat("time",row));
                break;
            }
        }
        return time;
    }
    
    public double getPath (int index){
        double path = 0.0;
        int     nrows = banks[0].getRows();
        
        for(int row = 0; row < nrows; row++){
            int pindex = banks[0].getInt("pindex",row);
            int layer = banks[0].getInt("layer",row);
            
            //takes the first layer first and then checks rest if layer 1 is not present
            if((pindex==index) && (layer==1)) {
                path = (banks[0].getFloat("path",row));
                break;
            }
            if((pindex==index) && (layer==4)) {
                path = (banks[0].getFloat("path",row));
                break;
            }
            if((pindex==index) && (layer==7)) {
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
        ReadCalorimeter cal = new ReadCalorimeter(r);
        int counter = 0;
        while(r.hasNext()){
            counter++; if(counter>max) break;
            cal.read(r);
            cal.show();
            
            double energy = cal.getEnergy(0);
            double time = cal.getTime(0);
            double path = cal.getPath(3);
            
            System.out.printf(">>>>>>>> energy = %f\n",energy);
            System.out.printf(">>>>>>>> time = %f\n",time);
            System.out.printf(">>>>>>>> path = %f\n",path);
            
        }
    }

}


