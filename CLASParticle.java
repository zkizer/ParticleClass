//************************************************** 
//*--#$ String command = System.getProperty("a");
//*--#$ System.out.println(command);
//**************************************************
import j4np.physics.VectorOperator.OperatorType;

//code written by Zachary Nickischer

public class CLASParticle {
    //Bank[] banks = r.getBanks("REC::Particle","REC::Calorimeter","REC::Scintillator","REC::Cherenkov");
    
    Bank[]  banks;
    String name = "REC::Particle";
    int pindex;
    
    public enum Detectors {
        CALORIMETER,
        //need two scintillator enums one for layer on and one for layer two
        SCINTILLATOR1, //layer 1 of scint
        SCINTILLATOR2, //layer 2 of scint
        CHERENKOV,
    }
    
    ReadScintillator rsc = null;
    //ReadCalorimeter rcal = null;
    //ReadCherenkov rcher = null;
    
    
    public CLASParticle(HipoReader r){
        banks = r.getBanks(name);
        rsc = new ReadScintillator(r);
        
    }
    
    public void read(HipoReader r){
        r.nextEvent(banks);
    }
    
    public int getNParticles (){
        int rows = banks[0].getRows();
        return rows;
    }
    
    public int getPid(int row){
        int pid = banks[0].getInt("pid",row);
        return pid;
    }
    
    
    public double getEnergy (Detectors choice, int row){
        double energy = 0.0;
        
        switch (choice) {
            case SCINTILLATOR1:
                energy = rsc.getEnergy(row, ReadScintillator.Layer.ONE);
//                System.out.println(row);
//                System.out.println(energy);
//                energy = rsc.getEnergy(row, 1);
                break;
                
            case SCINTILLATOR2:
                energy = rsc.getEnergy(row, ReadScintillator.Layer.TWO);
                break;
                /*
            case CALORIMETER:
                energy = (rcal.getEnergy(row));
                break;
                
            case CHERENKOV:
                energy = (rcher.getEnergy(row));
                break;
                */
              
        }
        
        return energy;
    }
    
    
    //time
    //path
    
    
    //test
    
    public void show(){banks[0].show();}
    
    public static void run(int max){
    
        HipoReader r = new HipoReader("infile.hipo");
        CLASParticle part = new CLASParticle(r);
        
        int counter = 0;
        while(r.hasNext()){
            counter++; if(counter>max) break;
    
            part.read(r);
            part.show();
            
            int nparticles = part.getNParticles();
            
            for(int i =0; i < nparticles; i++){
                int pid = part.getPid(i);
                if(pid==11){
                    double energy = part.getEnergy(Detectors.SCINTILLATOR1,i);
                    System.out.printf("pid = %d, energy = %f\n",pid,energy);
                }
            }
        }
    }

}


